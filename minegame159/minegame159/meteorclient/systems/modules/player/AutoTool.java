/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> preferMending;
    private static final Set<Material> EMPTY_MATERIALS = new HashSet<Material>(0);
    private int prevSlot;
    private final Setting<Boolean> silkTouchForEnderChest;
    private final SettingGroup sgGeneral;
    private boolean wasPressed;
    private final Setting<Boolean> antiBreak;
    private final Setting<Integer> breakDurability;
    private final Setting<EnchantPreference> prefer;
    private static final Set<Block> EMPTY_BLOCKS = new HashSet<Block>(0);
    private final Setting<Boolean> switchBack;

    private boolean shouldStopUsing(ItemStack ItemStack2) {
        return this.antiBreak.get() != false && ItemStack2.getMaxDamage() - ItemStack2.getDamage() < this.breakDurability.get();
    }

    @EventHandler(priority=100)
    private void onStartBreakingBlock(StartBreakingBlockEvent startBreakingBlockEvent) {
        ItemStack ItemStack2;
        BlockState BlockState2 = this.mc.world.getBlockState(startBreakingBlockEvent.blockPos);
        int n = -1;
        int n2 = 0;
        int n3 = -1;
        if (BlockState2.getHardness((BlockView)this.mc.world, startBreakingBlockEvent.blockPos) < 0.0f || BlockState2.isAir()) {
            return;
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack ItemStack3 = this.mc.player.inventory.getStack(i);
            if (!this.isEffectiveOn(ItemStack3.getItem(), BlockState2) || this.shouldStopUsing(ItemStack3) || !(ItemStack3.getItem() instanceof ToolItem) || this.silkTouchForEnderChest.get().booleanValue() && BlockState2.getBlock() == Blocks.ENDER_CHEST && EnchantmentHelper.getLevel((Enchantment)Enchantments.SILK_TOUCH, (ItemStack)ItemStack3) == 0) continue;
            n2 += Math.round(ItemStack3.getMiningSpeedMultiplier(BlockState2));
            n2 += EnchantmentHelper.getLevel((Enchantment)Enchantments.UNBREAKING, (ItemStack)ItemStack3);
            n2 += EnchantmentHelper.getLevel((Enchantment)Enchantments.EFFICIENCY, (ItemStack)ItemStack3);
            if (this.preferMending.get().booleanValue()) {
                n2 += EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)ItemStack3);
            }
            if (this.prefer.get() == EnchantPreference.Fortune) {
                n2 += EnchantmentHelper.getLevel((Enchantment)Enchantments.FORTUNE, (ItemStack)ItemStack3);
            }
            if (this.prefer.get() == EnchantPreference.SilkTouch) {
                n2 += EnchantmentHelper.getLevel((Enchantment)Enchantments.SILK_TOUCH, (ItemStack)ItemStack3);
            }
            if (n2 <= n) continue;
            n = n2;
            n3 = i;
        }
        if (n3 != -1) {
            if (this.prevSlot == -1) {
                this.prevSlot = this.mc.player.inventory.selectedSlot;
            }
            this.mc.player.inventory.selectedSlot = n3;
        }
        if (this.shouldStopUsing(ItemStack2 = this.mc.player.inventory.getStack(this.mc.player.inventory.selectedSlot)) && ItemStack2.getItem() instanceof ToolItem) {
            this.mc.options.keyAttack.setPressed(false);
            startBreakingBlockEvent.setCancelled(true);
        }
    }

    public AutoTool() {
        super(Categories.Player, "auto-tool", "Automatically switches to the most effective tool when performing an action.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.prefer = this.sgGeneral.add(new EnumSetting.Builder().name("prefer").description("Either to prefer Silk Touch, Fortune, or none.").defaultValue(EnchantPreference.Fortune).build());
        this.preferMending = this.sgGeneral.add(new BoolSetting.Builder().name("prefer-mending").description("Whether or not to prefer the Mending enchantment.").defaultValue(true).build());
        this.silkTouchForEnderChest = this.sgGeneral.add(new BoolSetting.Builder().name("silk-touch-for-ender-chest").description("Mines Ender Chests only with the Silk Touch enchantment.").defaultValue(true).build());
        this.antiBreak = this.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Stops you from breaking your tool.").defaultValue(false).build());
        this.breakDurability = this.sgGeneral.add(new IntSetting.Builder().name("anti-break-durability").description("The durability to stop using a tool.").defaultValue(10).max(50).min(2).sliderMax(20).build());
        this.switchBack = this.sgGeneral.add(new BoolSetting.Builder().name("switch-back").description("Switches your hand to whatever was selected when releasing your attack key.").defaultValue(false).build());
    }

    public boolean isEffectiveOn(Item Item2, BlockState BlockState2) {
        Set<Block> set;
        Set<Material> set2;
        if (Item2.isSuitableFor(BlockState2)) {
            return true;
        }
        if (Item2 instanceof PickaxeItem) {
            set2 = EMPTY_MATERIALS;
            set = PickaxeItemAccessor.getEffectiveBlocks();
        } else if (Item2 instanceof AxeItem) {
            set2 = AxeItemAccessor.getEffectiveMaterials();
            set = AxeItemAccessor.getEffectiveBlocks();
        } else if (Item2 instanceof ShovelItem) {
            set2 = EMPTY_MATERIALS;
            set = ShovelItemAccessor.getEffectiveBlocks();
        } else if (Item2 instanceof HoeItem) {
            set2 = EMPTY_MATERIALS;
            set = HoeItemAccessor.getEffectiveBlocks();
        } else if (Item2 instanceof SwordItem) {
            set2 = EMPTY_MATERIALS;
            set = EMPTY_BLOCKS;
        } else {
            return false;
        }
        return set2.contains(BlockState2.getMaterial()) || set.contains(BlockState2.getBlock());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.switchBack.get().booleanValue() && !this.mc.options.keyAttack.isPressed() && this.wasPressed && this.prevSlot != -1) {
            this.mc.player.inventory.selectedSlot = this.prevSlot;
            this.prevSlot = -1;
        }
        this.wasPressed = this.mc.options.keyAttack.isPressed();
    }

    public static final class EnchantPreference
    extends Enum<EnchantPreference> {
        public static final /* enum */ EnchantPreference SilkTouch;
        public static final /* enum */ EnchantPreference Fortune;
        public static final /* enum */ EnchantPreference None;
        private static final EnchantPreference[] $VALUES;

        static {
            None = new EnchantPreference();
            Fortune = new EnchantPreference();
            SilkTouch = new EnchantPreference();
            $VALUES = EnchantPreference.$values();
        }

        public static EnchantPreference valueOf(String string) {
            return Enum.valueOf(EnchantPreference.class, string);
        }

        public static EnchantPreference[] values() {
            return (EnchantPreference[])$VALUES.clone();
        }

        private static EnchantPreference[] $values() {
            return new EnchantPreference[]{None, Fortune, SilkTouch};
        }
    }
}

