/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.AutoTool;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;

public class EChestFarmer
extends Module {
    private static final BlockState ENDER_CHEST = Blocks.ENDER_CHEST.getDefaultState();
    private int numLeft;
    private final Setting<Boolean> disableOnAmount;
    private final SettingGroup sgGeneral;
    private final Setting<Integer> amount;
    private final Setting<Integer> lowerAmount;
    private boolean stop;

    public EChestFarmer() {
        super(Categories.World, "EChest-farmer", "Places and mines Ender Chests where you're looking.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.amount = this.sgGeneral.add(new IntSetting.Builder().name("target-amount").description("The amount of obsidian to farm.").defaultValue(64).min(8).sliderMax(64).max(512).build());
        this.lowerAmount = this.sgGeneral.add(new IntSetting.Builder().name("lower-amount").description("The specified amount before this module toggles on again.").defaultValue(8).min(0).max(64).sliderMax(32).build());
        this.disableOnAmount = this.sgGeneral.add(new BoolSetting.Builder().name("disable-on-completion").description("Whether or not to disable when you reach your desired amount of stacks of obsidian.").defaultValue(true).build());
        this.stop = false;
        this.numLeft = Math.floorDiv(this.amount.get(), 8);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.lowerAmount.get() < InvUtils.findItemWithCount((Item)Items.OBSIDIAN).count) {
            this.stop = false;
        }
        if (this.stop && !this.disableOnAmount.get().booleanValue()) {
            this.stop = false;
            this.numLeft = Math.floorDiv(this.amount.get(), 8);
            return;
        }
        if (this.stop && this.disableOnAmount.get().booleanValue()) {
            this.toggle();
            return;
        }
        InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(Items.ENDER_CHEST);
        int n = -1;
        if (findItemResult.count != 0 && findItemResult.slot < 9 && findItemResult.slot != -1) {
            for (int i = 0; i < 9; ++i) {
                if (!Modules.get().get(AutoTool.class).isEffectiveOn(this.mc.player.inventory.getStack(i).getItem(), ENDER_CHEST) || EnchantmentHelper.getLevel((Enchantment)Enchantments.SILK_TOUCH, (ItemStack)this.mc.player.inventory.getStack(i)) != 0) continue;
                n = i;
                if (3 >= 0) continue;
                return;
            }
            if (n != -1 && findItemResult.slot != -1 && findItemResult.slot < 9) {
                if (this.mc.crosshairTarget.getType() != HitResult.class_240.BLOCK) {
                    return;
                }
                BlockPos BlockPos2 = ((BlockHitResult)this.mc.crosshairTarget).getBlockPos();
                if (this.mc.world.getBlockState(BlockPos2).getBlock() == Blocks.ENDER_CHEST) {
                    if (this.mc.player.inventory.selectedSlot != n) {
                        this.mc.player.inventory.selectedSlot = n;
                    }
                    this.mc.interactionManager.updateBlockBreakingProgress(BlockPos2, Direction.UP);
                    --this.numLeft;
                    if (this.numLeft == 0) {
                        this.stop = true;
                    }
                } else if (this.mc.world.getBlockState(BlockPos2.up()).getBlock() == Blocks.AIR) {
                    BlockUtils.place(BlockPos2.up(), Hand.MAIN_HAND, findItemResult.slot, false, 0, true);
                }
            }
        }
    }
}

