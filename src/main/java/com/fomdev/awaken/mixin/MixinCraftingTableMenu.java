package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.knowledge.KnowledgeHelper;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Mixin(CraftingMenu.class)
public class MixinCraftingTableMenu
{
    /**
     * @author Fom477
     * @reason Change the crafting results
     */
    @Overwrite
    protected static void slotChangedCraftingGrid(
            AbstractContainerMenu menu,
            Level level,
            Player player,
            CraftingContainer craftSlots,
            ResultContainer resultSlots,
            @Nullable RecipeHolder<CraftingRecipe> recipe
    )
    {
        if (level.isClientSide)
            return;

        CraftingInput craftinginput = craftSlots.asCraftInput();
        ServerPlayer serverplayer = (ServerPlayer)player;
        ItemStack stack = ItemStack.EMPTY;
        Optional<RecipeHolder<CraftingRecipe>> holder = Objects.requireNonNull(level.getServer()).getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftinginput, level, recipe);
        if (holder.isPresent())
        {
            RecipeHolder<CraftingRecipe> recipeHolder = holder.get();
            CraftingRecipe craftingrecipe = recipeHolder.value();
            if (resultSlots.setRecipeUsed(level, serverplayer, recipeHolder))
            {
                ItemStack result = craftingrecipe.assemble(craftinginput, level.registryAccess());
                if (result.isItemEnabled(level.enabledFeatures()))
                    stack = result;
            }
        }

        stack = KnowledgeHelper.getResult(player, stack);
        BigDecimal awakenLevel = NBTUtil.deserializeAwakenLevel(player);
        BigDecimal difficulty = DifficultyManager.getLevelDifficulty((ServerLevel) level);
        NBTUtil.serializeEpoch(
                stack,
                new Records.AwakenEpochComponent(
                        awakenLevel,
                        difficulty
                )
        );

        resultSlots.setItem(0, stack);
        menu.setRemoteSlot(0, stack);
        serverplayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, stack));
    }
}