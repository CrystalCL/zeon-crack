/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.AxeItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.HoeItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.PickaxeItem
 *  net.minecraft.item.ShovelItem
 *  net.minecraft.item.SwordItem
 *  net.minecraft.item.ToolItem
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.enchantment.Enchantments
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.Material
 */
package minegame159.meteorclient.systems.modules.player;

import java.util.HashSet;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.StartBreakingBlockEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.AxeItemAccessor;
import minegame159.meteorclient.mixin.HoeItemAccessor;
import minegame159.meteorclient.mixin.PickaxeItemAccessor;
import minegame159.meteorclient.mixin.ShovelItemAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.world.BlockView;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;

public class AutoTool
extends Module {
    private final /* synthetic */ Setting<Boolean> switchBack;
    private final /* synthetic */ Setting<EnchantPreference> prefer;
    private final /* synthetic */ Setting<Boolean> antiBreak;
    private final /* synthetic */ Setting<Integer> breakDurability;
    private final /* synthetic */ Setting<Boolean> silkTouchForEnderChest;
    private final /* synthetic */ Setting<Boolean> preferMending;
    private static final /* synthetic */ Set<Material> EMPTY_MATERIALS;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ boolean wasPressed;
    private static final /* synthetic */ Set<Block> EMPTY_BLOCKS;
    private /* synthetic */ int prevSlot;

    @EventHandler(priority=100)
    private void onStartBreakingBlock(StartBreakingBlockEvent llllllllllllllllIllllllIIlIlIIII) {
        ItemStack llllllllllllllllIllllllIIlIlIIlI;
        AutoTool llllllllllllllllIllllllIIlIlIIIl;
        BlockState llllllllllllllllIllllllIIlIlIllI = llllllllllllllllIllllllIIlIlIIIl.mc.world.getBlockState(llllllllllllllllIllllllIIlIlIIII.blockPos);
        int llllllllllllllllIllllllIIlIlIlIl = -1;
        int llllllllllllllllIllllllIIlIlIlII = 0;
        int llllllllllllllllIllllllIIlIlIIll = -1;
        if (llllllllllllllllIllllllIIlIlIllI.getHardness((BlockView)llllllllllllllllIllllllIIlIlIIIl.mc.world, llllllllllllllllIllllllIIlIlIIII.blockPos) < 0.0f || llllllllllllllllIllllllIIlIlIllI.isAir()) {
            return;
        }
        for (int llllllllllllllllIllllllIIlIllIIl = 0; llllllllllllllllIllllllIIlIllIIl < 9; ++llllllllllllllllIllllllIIlIllIIl) {
            ItemStack llllllllllllllllIllllllIIlIllIlI = llllllllllllllllIllllllIIlIlIIIl.mc.player.inventory.getStack(llllllllllllllllIllllllIIlIllIIl);
            if (!llllllllllllllllIllllllIIlIlIIIl.isEffectiveOn(llllllllllllllllIllllllIIlIllIlI.getItem(), llllllllllllllllIllllllIIlIlIllI) || llllllllllllllllIllllllIIlIlIIIl.shouldStopUsing(llllllllllllllllIllllllIIlIllIlI) || !(llllllllllllllllIllllllIIlIllIlI.getItem() instanceof ToolItem) || llllllllllllllllIllllllIIlIlIIIl.silkTouchForEnderChest.get().booleanValue() && llllllllllllllllIllllllIIlIlIllI.getBlock() == Blocks.ENDER_CHEST && EnchantmentHelper.getLevel((Enchantment)Enchantments.SILK_TOUCH, (ItemStack)llllllllllllllllIllllllIIlIllIlI) == 0) continue;
            llllllllllllllllIllllllIIlIlIlII += Math.round(llllllllllllllllIllllllIIlIllIlI.getMiningSpeedMultiplier(llllllllllllllllIllllllIIlIlIllI));
            llllllllllllllllIllllllIIlIlIlII += EnchantmentHelper.getLevel((Enchantment)Enchantments.UNBREAKING, (ItemStack)llllllllllllllllIllllllIIlIllIlI);
            llllllllllllllllIllllllIIlIlIlII += EnchantmentHelper.getLevel((Enchantment)Enchantments.EFFICIENCY, (ItemStack)llllllllllllllllIllllllIIlIllIlI);
            if (llllllllllllllllIllllllIIlIlIIIl.preferMending.get().booleanValue()) {
                llllllllllllllllIllllllIIlIlIlII += EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)llllllllllllllllIllllllIIlIllIlI);
            }
            if (llllllllllllllllIllllllIIlIlIIIl.prefer.get() == EnchantPreference.Fortune) {
                llllllllllllllllIllllllIIlIlIlII += EnchantmentHelper.getLevel((Enchantment)Enchantments.FORTUNE, (ItemStack)llllllllllllllllIllllllIIlIllIlI);
            }
            if (llllllllllllllllIllllllIIlIlIIIl.prefer.get() == EnchantPreference.SilkTouch) {
                llllllllllllllllIllllllIIlIlIlII += EnchantmentHelper.getLevel((Enchantment)Enchantments.SILK_TOUCH, (ItemStack)llllllllllllllllIllllllIIlIllIlI);
            }
            if (llllllllllllllllIllllllIIlIlIlII <= llllllllllllllllIllllllIIlIlIlIl) continue;
            llllllllllllllllIllllllIIlIlIlIl = llllllllllllllllIllllllIIlIlIlII;
            llllllllllllllllIllllllIIlIlIIll = llllllllllllllllIllllllIIlIllIIl;
        }
        if (llllllllllllllllIllllllIIlIlIIll != -1) {
            if (llllllllllllllllIllllllIIlIlIIIl.prevSlot == -1) {
                llllllllllllllllIllllllIIlIlIIIl.prevSlot = llllllllllllllllIllllllIIlIlIIIl.mc.player.inventory.selectedSlot;
            }
            llllllllllllllllIllllllIIlIlIIIl.mc.player.inventory.selectedSlot = llllllllllllllllIllllllIIlIlIIll;
        }
        if (llllllllllllllllIllllllIIlIlIIIl.shouldStopUsing(llllllllllllllllIllllllIIlIlIIlI = llllllllllllllllIllllllIIlIlIIIl.mc.player.inventory.getStack(llllllllllllllllIllllllIIlIlIIIl.mc.player.inventory.selectedSlot)) && llllllllllllllllIllllllIIlIlIIlI.getItem() instanceof ToolItem) {
            llllllllllllllllIllllllIIlIlIIIl.mc.options.keyAttack.setPressed(false);
            llllllllllllllllIllllllIIlIlIIII.setCancelled(true);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllIllllllIIllIIlII) {
        AutoTool llllllllllllllllIllllllIIllIIIll;
        if (llllllllllllllllIllllllIIllIIIll.switchBack.get().booleanValue() && !llllllllllllllllIllllllIIllIIIll.mc.options.keyAttack.isPressed() && llllllllllllllllIllllllIIllIIIll.wasPressed && llllllllllllllllIllllllIIllIIIll.prevSlot != -1) {
            llllllllllllllllIllllllIIllIIIll.mc.player.inventory.selectedSlot = llllllllllllllllIllllllIIllIIIll.prevSlot;
            llllllllllllllllIllllllIIllIIIll.prevSlot = -1;
        }
        llllllllllllllllIllllllIIllIIIll.wasPressed = llllllllllllllllIllllllIIllIIIll.mc.options.keyAttack.isPressed();
    }

    public AutoTool() {
        super(Categories.Player, "auto-tool", "Automatically switches to the most effective tool when performing an action.");
        AutoTool llllllllllllllllIllllllIIllIlIII;
        llllllllllllllllIllllllIIllIlIII.sgGeneral = llllllllllllllllIllllllIIllIlIII.settings.getDefaultGroup();
        llllllllllllllllIllllllIIllIlIII.prefer = llllllllllllllllIllllllIIllIlIII.sgGeneral.add(new EnumSetting.Builder().name("prefer").description("Either to prefer Silk Touch, Fortune, or none.").defaultValue(EnchantPreference.Fortune).build());
        llllllllllllllllIllllllIIllIlIII.preferMending = llllllllllllllllIllllllIIllIlIII.sgGeneral.add(new BoolSetting.Builder().name("prefer-mending").description("Whether or not to prefer the Mending enchantment.").defaultValue(true).build());
        llllllllllllllllIllllllIIllIlIII.silkTouchForEnderChest = llllllllllllllllIllllllIIllIlIII.sgGeneral.add(new BoolSetting.Builder().name("silk-touch-for-ender-chest").description("Mines Ender Chests only with the Silk Touch enchantment.").defaultValue(true).build());
        llllllllllllllllIllllllIIllIlIII.antiBreak = llllllllllllllllIllllllIIllIlIII.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Stops you from breaking your tool.").defaultValue(false).build());
        llllllllllllllllIllllllIIllIlIII.breakDurability = llllllllllllllllIllllllIIllIlIII.sgGeneral.add(new IntSetting.Builder().name("anti-break-durability").description("The durability to stop using a tool.").defaultValue(10).max(50).min(2).sliderMax(20).build());
        llllllllllllllllIllllllIIllIlIII.switchBack = llllllllllllllllIllllllIIllIlIII.sgGeneral.add(new BoolSetting.Builder().name("switch-back").description("Switches your hand to whatever was selected when releasing your attack key.").defaultValue(false).build());
    }

    private boolean shouldStopUsing(ItemStack llllllllllllllllIllllllIIIlIllIl) {
        AutoTool llllllllllllllllIllllllIIIllIIII;
        return llllllllllllllllIllllllIIIllIIII.antiBreak.get() != false && llllllllllllllllIllllllIIIlIllIl.getMaxDamage() - llllllllllllllllIllllllIIIlIllIl.getDamage() < llllllllllllllllIllllllIIIllIIII.breakDurability.get();
    }

    static {
        EMPTY_MATERIALS = new HashSet<Material>(0);
        EMPTY_BLOCKS = new HashSet<Block>(0);
    }

    /*
     * WARNING - void declaration
     */
    public boolean isEffectiveOn(Item llllllllllllllllIllllllIIIllIllI, BlockState llllllllllllllllIllllllIIIllIlIl) {
        void llllllllllllllllIllllllIIIllIlll;
        void llllllllllllllllIllllllIIIlllIII;
        if (llllllllllllllllIllllllIIIllIllI.isSuitableFor(llllllllllllllllIllllllIIIllIlIl)) {
            return true;
        }
        if (llllllllllllllllIllllllIIIllIllI instanceof PickaxeItem) {
            Set<Material> llllllllllllllllIllllllIIlIIIlIl = EMPTY_MATERIALS;
            Set<Block> llllllllllllllllIllllllIIlIIIlII = PickaxeItemAccessor.getEffectiveBlocks();
        } else if (llllllllllllllllIllllllIIIllIllI instanceof AxeItem) {
            Set<Material> llllllllllllllllIllllllIIlIIIIll = AxeItemAccessor.getEffectiveMaterials();
            Set<Block> llllllllllllllllIllllllIIlIIIIlI = AxeItemAccessor.getEffectiveBlocks();
        } else if (llllllllllllllllIllllllIIIllIllI instanceof ShovelItem) {
            Set<Material> llllllllllllllllIllllllIIlIIIIIl = EMPTY_MATERIALS;
            Set<Block> llllllllllllllllIllllllIIlIIIIII = ShovelItemAccessor.getEffectiveBlocks();
        } else if (llllllllllllllllIllllllIIIllIllI instanceof HoeItem) {
            Set<Material> llllllllllllllllIllllllIIIllllll = EMPTY_MATERIALS;
            Set<Block> llllllllllllllllIllllllIIIlllllI = HoeItemAccessor.getEffectiveBlocks();
        } else if (llllllllllllllllIllllllIIIllIllI instanceof SwordItem) {
            Set<Material> llllllllllllllllIllllllIIIllllIl = EMPTY_MATERIALS;
            Set<Block> llllllllllllllllIllllllIIIllllII = EMPTY_BLOCKS;
        } else {
            return false;
        }
        return llllllllllllllllIllllllIIIlllIII.contains((Object)llllllllllllllllIllllllIIIllIlIl.getMaterial()) || llllllllllllllllIllllllIIIllIlll.contains((Object)llllllllllllllllIllllllIIIllIlIl.getBlock());
    }

    public static final class EnchantPreference
    extends Enum<EnchantPreference> {
        public static final /* synthetic */ /* enum */ EnchantPreference None;
        private static final /* synthetic */ EnchantPreference[] $VALUES;
        public static final /* synthetic */ /* enum */ EnchantPreference Fortune;
        public static final /* synthetic */ /* enum */ EnchantPreference SilkTouch;

        private static /* synthetic */ EnchantPreference[] $values() {
            return new EnchantPreference[]{None, Fortune, SilkTouch};
        }

        public static EnchantPreference valueOf(String lllIlIIIlllIllI) {
            return Enum.valueOf(EnchantPreference.class, lllIlIIIlllIllI);
        }

        public static EnchantPreference[] values() {
            return (EnchantPreference[])$VALUES.clone();
        }

        private EnchantPreference() {
            EnchantPreference lllIlIIIlllIIIl;
        }

        static {
            None = new EnchantPreference();
            Fortune = new EnchantPreference();
            SilkTouch = new EnchantPreference();
            $VALUES = EnchantPreference.$values();
        }
    }
}

