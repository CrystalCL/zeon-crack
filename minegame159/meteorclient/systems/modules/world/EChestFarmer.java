/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.enchantment.Enchantments
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.block.BlockState
 *  net.minecraft.util.hit.BlockHitResult
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
    private final /* synthetic */ Setting<Integer> lowerAmount;
    private /* synthetic */ boolean stop;
    private static final /* synthetic */ BlockState ENDER_CHEST;
    private /* synthetic */ int numLeft;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Integer> amount;
    private final /* synthetic */ Setting<Boolean> disableOnAmount;

    public EChestFarmer() {
        super(Categories.World, "EChest-farmer", "Places and mines Ender Chests where you're looking.");
        EChestFarmer llIlIlIIlIlIIll;
        llIlIlIIlIlIIll.sgGeneral = llIlIlIIlIlIIll.settings.getDefaultGroup();
        llIlIlIIlIlIIll.amount = llIlIlIIlIlIIll.sgGeneral.add(new IntSetting.Builder().name("target-amount").description("The amount of obsidian to farm.").defaultValue(64).min(8).sliderMax(64).max(512).build());
        llIlIlIIlIlIIll.lowerAmount = llIlIlIIlIlIIll.sgGeneral.add(new IntSetting.Builder().name("lower-amount").description("The specified amount before this module toggles on again.").defaultValue(8).min(0).max(64).sliderMax(32).build());
        llIlIlIIlIlIIll.disableOnAmount = llIlIlIIlIlIIll.sgGeneral.add(new BoolSetting.Builder().name("disable-on-completion").description("Whether or not to disable when you reach your desired amount of stacks of obsidian.").defaultValue(true).build());
        llIlIlIIlIlIIll.stop = false;
        llIlIlIIlIlIIll.numLeft = Math.floorDiv(llIlIlIIlIlIIll.amount.get(), 8);
    }

    static {
        ENDER_CHEST = Blocks.ENDER_CHEST.getDefaultState();
    }

    @EventHandler
    private void onTick(TickEvent.Pre llIlIlIIlIIlIlI) {
        EChestFarmer llIlIlIIlIIlIll;
        if (llIlIlIIlIIlIll.lowerAmount.get() < InvUtils.findItemWithCount((Item)Items.OBSIDIAN).count) {
            llIlIlIIlIIlIll.stop = false;
        }
        if (llIlIlIIlIIlIll.stop && !llIlIlIIlIIlIll.disableOnAmount.get().booleanValue()) {
            llIlIlIIlIIlIll.stop = false;
            llIlIlIIlIIlIll.numLeft = Math.floorDiv(llIlIlIIlIIlIll.amount.get(), 8);
            return;
        }
        if (llIlIlIIlIIlIll.stop && llIlIlIIlIIlIll.disableOnAmount.get().booleanValue()) {
            llIlIlIIlIIlIll.toggle();
            return;
        }
        InvUtils.FindItemResult llIlIlIIlIIlIIl = InvUtils.findItemWithCount(Items.ENDER_CHEST);
        int llIlIlIIlIIlIII = -1;
        if (llIlIlIIlIIlIIl.count != 0 && llIlIlIIlIIlIIl.slot < 9 && llIlIlIIlIIlIIl.slot != -1) {
            for (int llIlIlIIlIIllIl = 0; llIlIlIIlIIllIl < 9; ++llIlIlIIlIIllIl) {
                if (!Modules.get().get(AutoTool.class).isEffectiveOn(llIlIlIIlIIlIll.mc.player.inventory.getStack(llIlIlIIlIIllIl).getItem(), ENDER_CHEST) || EnchantmentHelper.getLevel((Enchantment)Enchantments.SILK_TOUCH, (ItemStack)llIlIlIIlIIlIll.mc.player.inventory.getStack(llIlIlIIlIIllIl)) != 0) continue;
                llIlIlIIlIIlIII = llIlIlIIlIIllIl;
            }
            if (llIlIlIIlIIlIII != -1 && llIlIlIIlIIlIIl.slot != -1 && llIlIlIIlIIlIIl.slot < 9) {
                if (llIlIlIIlIIlIll.mc.crosshairTarget.getType() != Type.BLOCK) {
                    return;
                }
                BlockPos llIlIlIIlIIllII = ((BlockHitResult)llIlIlIIlIIlIll.mc.crosshairTarget).getBlockPos();
                if (llIlIlIIlIIlIll.mc.world.getBlockState(llIlIlIIlIIllII).getBlock() == Blocks.ENDER_CHEST) {
                    if (llIlIlIIlIIlIll.mc.player.inventory.selectedSlot != llIlIlIIlIIlIII) {
                        llIlIlIIlIIlIll.mc.player.inventory.selectedSlot = llIlIlIIlIIlIII;
                    }
                    llIlIlIIlIIlIll.mc.interactionManager.updateBlockBreakingProgress(llIlIlIIlIIllII, Direction.UP);
                    --llIlIlIIlIIlIll.numLeft;
                    if (llIlIlIIlIIlIll.numLeft == 0) {
                        llIlIlIIlIIlIll.stop = true;
                    }
                } else if (llIlIlIIlIIlIll.mc.world.getBlockState(llIlIlIIlIIllII.up()).getBlock() == Blocks.AIR) {
                    BlockUtils.place(llIlIlIIlIIllII.up(), Hand.MAIN_HAND, llIlIlIIlIIlIIl.slot, false, 0, true);
                }
            }
        }
    }
}

