package com.fomdev.awaken.command;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AwakenStringCommand implements ArgumentType<ResourceLocation>
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Collection<ResourceLocation> acceptedValues;

    public AwakenStringCommand(
            Collection<ResourceLocation> acceptedValues
    )
    {
        this.acceptedValues = acceptedValues;
    }

    @Override
    public ResourceLocation parse(
            StringReader reader
    ) throws CommandSyntaxException
    {
        reader.skipWhitespace();
        int start = reader.getCursor();
        while (reader.canRead() && !Character.isWhitespace(reader.peek()))
            reader.read();

        String var = reader.getString().substring(start, reader.getCursor());
        ResourceLocation location = ResourceLocation.parse(var);
        if (!this.acceptedValues.contains(location))
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();

        return location;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            CommandContext<S> context,
            SuggestionsBuilder builder
    )
    {
        for (ResourceLocation value: this.acceptedValues)
            if (value.toString().startsWith(builder.getRemainingLowerCase()))
                builder.suggest(value.toString());

        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples()
    {
        return this.acceptedValues.stream().map(ResourceLocation::toString).toList();
    }

    public static class AwakenStringCommandInfo implements ArgumentTypeInfo<AwakenStringCommand, AwakenStringCommandInfo.Template>
    {
        public void serializeToNetwork(
                AwakenStringCommandInfo.Template template,
                @NotNull FriendlyByteBuf buffer
        )
        {
            JsonArray array = new JsonArray();
            for (ResourceLocation location: template.locations)
                array.add(location.toString());

            String data = GSON.toJson(array);

            buffer.writeUtf(data);
        }

        public AwakenStringCommandInfo.@NotNull Template deserializeFromNetwork(
                FriendlyByteBuf buffer
        )
        {
            String data = buffer.readUtf();
            List<String> raw = GSON.fromJson(data, TypeToken.get(JsonArray.class)).asList().stream().map(JsonElement::getAsString).toList();
            return new Template(raw.stream().map(ResourceLocation::parse).toList());
        }

        public void serializeToJson(
                AwakenStringCommandInfo.Template template,
                @NotNull JsonObject json
        )
        {
            JsonArray array = new JsonArray();
            for (ResourceLocation location: template.locations)
                array.add(location.toString());

            json.add("values", array);
        }

        public AwakenStringCommandInfo.@NotNull Template unpack(
                AwakenStringCommand argument
        )
        {
            return new Template(List.copyOf(argument.acceptedValues));
        }

        public class Template implements ArgumentTypeInfo.Template<AwakenStringCommand>
        {
            final List<ResourceLocation> locations;

            Template(List<ResourceLocation> locations)
            {
                this.locations = locations;
            }

            public @NotNull AwakenStringCommand instantiate(
                    @NotNull CommandBuildContext context
            )
            {
                return new AwakenStringCommand(this.locations);
            }

            public @NotNull ArgumentTypeInfo<AwakenStringCommand, ?> type()
            {
                return AwakenStringCommandInfo.this;
            }
        }
    }
}