package com.fomdev.awaken.command;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.entries.raw.AwakenQuality;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.util.NBTUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AwakenCommand
{
    private static final String SIG = "awaken";

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher,
            CommandBuildContext context
    )
    {
        dispatcher.register(
                factorRootNode()
        );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorRootNode()
    {
        return Commands.literal(SIG)
                .then(
                        factorAffixNode()
                )
                .then(
                        factorDifficultyNode()
                )
                .then(
                        factorQualityNode()
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorAffixNode()
    {
        return Commands.literal("affix")
                .then(
                        factorInfixNode()
                )
                .then(
                        factorPrefixNode()
                )
                .then(
                        factorSuffixNode()
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorInfixNode$Add()
    {
        return Commands.literal("add")
                .then(
                        Commands.argument(
                                "target",
                                EntityArgument.entity()
                        )
                                .then(
                                        Commands.argument(
                                                "infix",
                                                new AwakenStringCommand(AwakenRegistries.AWAKEN_INFIX.getKeys())
                                        )
                                                .then(
                                                        Commands.argument(
                                                                "level",
                                                                IntegerArgumentType.integer()
                                                        )
                                                                .executes(AwakenCommand::forInfixNode$Add)
                                                )
                        )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorInfixNode$Extend()
    {
        return Commands.literal("extend")
                .then(
                        Commands.argument(
                                "target",
                                EntityArgument.entity()
                        )
                                .then(
                                        Commands.argument(
                                                "count",
                                                IntegerArgumentType.integer()
                                        )
                                                .executes(AwakenCommand::forInfixNode$Extend)
                        )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorInfixNode$Get()
    {
        return Commands.literal("get")
                .then(
                        Commands.argument(
                                "target",
                                EntityArgument.entity()
                        )
                                .executes(AwakenCommand::forInfixNode$Get)
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorInfixNode()
    {
        return Commands.literal("infix")
                .then(
                        factorInfixNode$Add()
                )
                .then(
                        factorInfixNode$Extend()
                )
                .then(
                        factorInfixNode$Get()
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorPrefixNode()
    {
        return Commands.literal("prefix")
                .then(
                        Commands.argument(
                                "target",
                                EntityArgument.entity()
                        )
                                .then(
                                        Commands.argument(
                                                "prefix",
                                                new AwakenStringCommand(AwakenRegistries.AWAKEN_PREFIX.getKeys())
                                        )
                                                .then(
                                                        Commands.argument(
                                                                "level",
                                                                IntegerArgumentType.integer(1, AwakenCommon.CONFIG.GENERATABLE_MAX.get())
                                                        )
                                                                .executes(AwakenCommand::forPrefixNode)
                                                )
                        )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorQualityNode()
    {
        return Commands.literal("quality")
                .then(
                        Commands.argument(
                                "target",
                                EntityArgument.entity()
                        )
                                        .then(
                                                Commands.argument(
                                                        "quality",
                                                        new AwakenStringCommand(AwakenRegistries.AWAKEN_QUALITY.getKeys())
                                                )
                                                        .executes(AwakenCommand::factorQualityNode)
                                        )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorDifficultyNode()
    {
        return Commands.literal("diff")
                .then(
                        Commands.literal("add")
                                .then(
                                        Commands.argument(
                                                "diff",
                                                StringArgumentType.string()
                                        )
                                                .executes(AwakenCommand::forDifficultyNode$Add)
                                )
                )
                .then(
                        Commands.literal("set")
                                .then(
                                        Commands.argument(
                                                "diff",
                                                StringArgumentType.string()
                                        )
                                                .executes(AwakenCommand::forDifficultyNode$Set)
                                )
                );
    }

    private static int forDifficultyNode$Add(
            CommandContext<CommandSourceStack> context
    )
    {
        String difficulty = context.getArgument("diff", String.class);
        DifficultyManager.setLevelDifficulty(context.getSource().getLevel(), DifficultyManager.getLevelDifficulty(context.getSource().getLevel()).add(new BigDecimal(difficulty)));
        return 0;
    }

    private static int forDifficultyNode$Set(
            CommandContext<CommandSourceStack> context
    )
    {
        String difficulty = context.getArgument("diff", String.class);
        DifficultyManager.setLevelDifficulty(context.getSource().getLevel(), new BigDecimal(difficulty));
        return 0;
    }

    // $cmd affix infix add <entity> <infix> <level>
    private static int forInfixNode$Add(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException
    {
        Entity entity = EntityArgument.getEntity(context, "target");
        ResourceLocation location = context.getArgument("infix", ResourceLocation.class);
        Integer level = context.getArgument("level", Integer.class);
        AwakenInfix infix = AwakenInfix.of(location);
        if (!(entity instanceof LivingEntity living))
            return -1;

        ItemStack stack = living.getMainHandItem();
        if (stack.isEmpty() || infix.isEmpty())
            return -1;

        if (!NBTUtil.addAffix$Infix(stack, new AwakenInfix.InfixInstance(infix, level)))
            return -1;

        return Command.SINGLE_SUCCESS;
    }

    // $cmd affix infix extend <entity> <count>
    private static int forInfixNode$Extend(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException
    {
        Entity entity = EntityArgument.getEntity(context, "target");
        Integer count = context.getArgument("count", Integer.class);

        if (!(entity instanceof LivingEntity living) || count <= 0)
            return -1;

        ItemStack stack = living.getMainHandItem();
        if (stack.isEmpty())
            return -1;

        NBTUtil.extendAffix$Infix(stack, count);
        return Command.SINGLE_SUCCESS;
    }

    // $cmd affix infix get <entity>
    private static int forInfixNode$Get(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException
    {
        Entity entity = EntityArgument.getEntity(context, "target");
        Player executor = context.getSource().getPlayer();
        if (!(entity instanceof LivingEntity living))
            return -1;

        ItemStack stack = living.getMainHandItem();
        if (stack.isEmpty())
            return -1;

        AwakenInfix.InfixContainer container = NBTUtil.deserializeAffix$Infix(stack);
        if (executor == null)
            return -1;
        if (container.slots().isEmpty())
            executor.sendSystemMessage(Component.literal("EMPTY Infix Container"));

        for (Map.Entry<Integer, AwakenInfix.InfixSlot> slot: container.slots().entrySet())
        {
            Integer slotId = slot.getKey();
            AwakenInfix.InfixSlot infix = slot.getValue();

            String var = String.format("Slot %d -> %s", slotId, infix.isPresent()? infix.getInfix().getRepresent().getLocation().toString(): "EMPTY");
            executor.sendSystemMessage(Component.literal(var));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int forPrefixNode(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException
    {
        Entity entity = EntityArgument.getEntity(context, "target");
        ResourceLocation location = context.getArgument("prefix", ResourceLocation.class);
        Integer level = context.getArgument("level", Integer.class);
        if (!(entity instanceof LivingEntity living))
            return -1;

        ItemStack stack = living.getMainHandItem();
        AwakenPrefix prefix = AwakenPrefix.of(location);
        if (stack.isEmpty() || prefix.isEmpty())
            return -1;

        AwakenPrefix.PrefixInstance instance = new AwakenPrefix.PrefixInstance(prefix, level);
        NBTUtil.serializeAffix$Prefix(stack, instance);

        return Command.SINGLE_SUCCESS;
    }

    private static int factorQualityNode(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException
    {
        Entity entity = EntityArgument.getEntity(context, "target");
        ResourceLocation location = context.getArgument("quality", ResourceLocation.class);
        AwakenQuality quality = AwakenRegistries.AWAKEN_QUALITY.getRegistry(location);
        if (!(entity instanceof LivingEntity living))
            return -1;

        ItemStack stack = living.getMainHandItem();
        if (stack.isEmpty() || quality == null)
            return -1;

        NBTUtil.serializeQuality(stack, quality);
        return Command.SINGLE_SUCCESS;
    }

    // $cmd affix suffix add <entity> <suffix> <args>
    private static int forSuffixNode$Add(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException
    {
        Entity entity = EntityArgument.getEntity(context, "target");
        ResourceLocation location = context.getArgument("suffix", ResourceLocation.class);
        CompoundTag args = context.getArgument("args", CompoundTag.class);
        AwakenSuffix suffix = AwakenSuffix.of(location);
        Map<String, String> argv = toMap(args);
        if (!(entity instanceof LivingEntity living))
            return -1;

        ItemStack stack = living.getMainHandItem();
        if (stack.isEmpty() || suffix.isEmpty())
            return -1;

        NBTUtil.addAffix$Suffix(stack, new AwakenSuffix.SuffixInstance(suffix, argv));
        return Command.SINGLE_SUCCESS;
    }

    // $cmd affix suffix extend <entity> <count>
    private static int forSuffixNode$Extend(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException
    {
        Entity entity = EntityArgument.getEntity(context, "target");
        Integer count = context.getArgument("count", Integer.class);

        if (!(entity instanceof LivingEntity living) || count <= 0)
            return -1;

        ItemStack stack = living.getMainHandItem();
        if (stack.isEmpty())
            return -1;

        NBTUtil.extendAffix$Suffix(stack, count);
        return Command.SINGLE_SUCCESS;
    }

    // $cmd affix suffix get <entity>
    private static int forSuffixNode$Get(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException
    {
        Entity entity = EntityArgument.getEntity(context, "target");
        Player executor = context.getSource().getPlayer();
        if (!(entity instanceof LivingEntity living))
            return -1;

        ItemStack stack = living.getMainHandItem();
        if (stack.isEmpty())
            return -1;

        AwakenSuffix.SuffixContainer container = NBTUtil.deserializeAffix$Suffix(stack);
        if (executor == null)
            return -1;
        if (container.slots().isEmpty())
            executor.sendSystemMessage(Component.literal("EMPTY Suffix Container"));

        for (Map.Entry<Integer, AwakenSuffix.SuffixSlot> slot: container.slots().entrySet())
        {
            Integer slotId = slot.getKey();
            AwakenSuffix.SuffixSlot suffix = slot.getValue();

            String var = String.format("Slot %d -> %s", slotId, suffix.isPresent()? suffix.getSuffix().suffix().getLocation().toString(): "EMPTY");
            executor.sendSystemMessage(Component.literal(var));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorSuffixNode$Add()
    {
        return Commands.literal("add")
                .then(
                        Commands.argument(
                                "target",
                                EntityArgument.entity()
                        )
                                .then(
                                        Commands.argument(
                                                "suffix",
                                                new AwakenStringCommand(AwakenRegistries.AWAKEN_SUFFIX.getKeys())
                                        )
                                                .then(
                                                        Commands.argument(
                                                                "args",
                                                                CompoundTagArgument.compoundTag()
                                                        )
                                                                .executes(AwakenCommand::forSuffixNode$Add)
                                                )
                        )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorSuffixNode$Extend()
    {
        return Commands.literal("extend")
                .then(
                        Commands.argument(
                                "target",
                                EntityArgument.entity()
                        )
                                .then(
                                        Commands.argument(
                                                "count",
                                                IntegerArgumentType.integer()
                                        )
                                                .executes(AwakenCommand::forSuffixNode$Extend)
                                )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorSuffixNode$Get()
    {
        return Commands.literal("get")
                .then(
                        Commands.argument(
                                "target",
                                EntityArgument.entity()
                        )
                                .executes(AwakenCommand::forSuffixNode$Get)
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> factorSuffixNode()
    {
        return Commands.literal("suffix")
                .then(
                        factorSuffixNode$Add()
                )
                .then(
                        factorSuffixNode$Extend()
                )
                .then(
                        factorSuffixNode$Get()
                );
    }

    // Util
    private static Map<String, String> toMap(
            CompoundTag tag
    )
    {
        Map<String, String> map = new HashMap<>();
        for (String key: tag.getAllKeys())
            map.put(key, tag.getString(key));

        return Map.copyOf(map);
    }
}