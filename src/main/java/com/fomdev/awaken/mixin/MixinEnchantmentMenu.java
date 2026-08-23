package com.fomdev.awaken.mixin;

import com.fomdev.awaken.register.attribute.AwakenAttributes;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Objects;

@Mixin(EnchantmentMenu.class)
public abstract class MixinEnchantmentMenu extends AbstractContainerMenu
{
    @Shadow
    @Final
    private Container enchantSlots;

    @Shadow
    @Final
    private ContainerLevelAccess access;

    @Shadow
    @Final
    private RandomSource random;

    @Shadow
    @Final
    private DataSlot enchantmentSeed;

    @Shadow
    @Final
    public int[] costs;

    @Shadow
    @Final
    public int[] enchantClue;

    @Shadow
    @Final
    public int[] levelClue;

    @Shadow
    protected abstract List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int slot, int cost);

    protected MixinEnchantmentMenu(@Nullable MenuType<?> menuType, int containerId)
    {
        super(menuType, containerId);
    }

    /**
     * @author Fom477
     * @reason Change the logic
     */
    @Overwrite
    public void slotsChanged(@NotNull Container inventory)
    {
        if (inventory != this.enchantSlots)
            return;

        ItemStack stack = inventory.getItem(0);
        if (stack.isEmpty() || !stack.isEnchantable())
        {
            for (int i = 0; i < 3; i++)
            {
                this.costs[i] = 0;
                this.enchantClue[i] = -1;
                this.levelClue[i] = -1;
            }
            return;
        }

        this.access.execute((level, pos) -> {
            IdMap<Holder<Enchantment>> idmap = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).asHolderIdMap();

            float j = 0;
            for (BlockPos p : EnchantingTableBlock.BOOKSHELF_OFFSETS)
                if (EnchantingTableBlock.isValidBookShelf(level, pos, p))
                    j += level.getBlockState(pos.offset(p)).getEnchantPowerBonus(level, pos.offset(p));

            Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
            if (player == null)
                return;

            if (player.getAttribute(AwakenAttributes.ENCHANTMENT) != null)
                j += (float) Objects.requireNonNull(player.getAttribute(AwakenAttributes.ENCHANTMENT)).getValue();

            Records.AwakenKnowledgeComponent knowledge = NBTUtil.deserializeKnowledge(player);

            this.random.setSeed(this.enchantmentSeed.get());

            for (int k = 0; k < 3; k++)
            {
                this.costs[k] = EnchantmentHelper.getEnchantmentCost(this.random, k, (int) j, stack);
                this.enchantClue[k] = -1;
                this.levelClue[k] = -1;
                if (this.costs[k] < k + 1)
                    this.costs[k] = 0;

                this.costs[k] = net.neoforged.neoforge.event.EventHooks.onEnchantmentLevelSet(level, pos, k, (int) j, stack, costs[k]);
            }

            for (int l = 0; l < 3; l++)
            {
                if (this.costs[l] > 0)
                {
                    List<EnchantmentInstance> insts = this.getEnchantmentList(level.registryAccess(), stack, l, this.costs[l]);
                    if (insts != null && !insts.isEmpty())
                    {
                        EnchantmentInstance inst = insts.get(this.random.nextInt(insts.size()));
                        int clueCount = Math.clamp((int) (Math.pow(knowledge.insight(), 1.0 / 5.0 * insts.size())), 1, insts.size());
                        for (int i = 0; i < clueCount; i++)
                            this.enchantClue[l * clueCount + i] = idmap.getId(inst.enchantment);

                        this.levelClue[l] = inst.level;
                    }
                }
            }

            this.broadcastChanges();
        });
    }
}