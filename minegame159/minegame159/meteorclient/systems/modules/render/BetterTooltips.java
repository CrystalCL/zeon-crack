/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import com.mojang.datafixers.util.Pair;
import java.io.DataOutput;
import java.io.IOException;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.GetTooltipEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.KeybindSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.ByteCountDataOutput;
import minegame159.meteorclient.utils.misc.Keybind;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Formatting;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.util.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.item.FoodComponent;
import net.minecraft.text.MutableText;

public class BetterTooltips
extends Module {
    private final Setting<Boolean> useKbIfBigEnoughEnabled;
    private final SettingGroup sgByteSize;
    public final Setting<Boolean> showVanilla;
    private final SettingGroup sgMap;
    private final SettingGroup sgGeneral;
    public final Setting<Boolean> echest;
    private final Setting<Boolean> beehive;
    private final Setting<Boolean> shulkerColorFromType;
    private final Setting<Boolean> statusEffects;
    public final Setting<Boolean> byteSize;
    public final Setting<Integer> mapsScale;
    private final Setting<Keybind> keybind;
    private final Setting<SettingColor> shulkersColor;
    private final Setting<Boolean> maps;
    private final SettingGroup sgOther;
    private final SettingGroup sgShulker;
    public final Setting<Boolean> middleClickOpen;
    private final Setting<Boolean> shulkers;
    private final Setting<DisplayWhen> displayWhen;
    private final SettingGroup sgEChest;
    public final Setting<SettingColor> echestColor;

    public boolean previewMaps() {
        return this.isActive() && this.isPressed() && this.maps.get() != false;
    }

    public boolean previewEChest() {
        return this.isActive() && this.isPressed() && this.echest.get() != false;
    }

    public Color getShulkerColor(ItemStack ItemStack2) {
        if (this.shulkerColorFromType.get().booleanValue()) {
            if (!(ItemStack2.getItem() instanceof BlockItem)) {
                return this.shulkersColor.get();
            }
            Block Block2 = ((BlockItem)ItemStack2.getItem()).getBlock();
            if (!(Block2 instanceof ShulkerBoxBlock)) {
                return this.shulkersColor.get();
            }
            ShulkerBoxBlock ShulkerBoxBlock2 = (ShulkerBoxBlock)ShulkerBoxBlock.getBlockFromItem((Item)ItemStack2.getItem());
            DyeColor DyeColor2 = ShulkerBoxBlock2.getColor();
            if (DyeColor2 == null) {
                return this.shulkersColor.get();
            }
            float[] fArray = DyeColor2.getColorComponents();
            return new Color(fArray[0], fArray[1], fArray[2], 1.0f);
        }
        return this.shulkersColor.get();
    }

    private boolean isPressed() {
        return this.keybind.get().isPressed() && this.displayWhen.get() == DisplayWhen.Keybind || this.displayWhen.get() == DisplayWhen.Always;
    }

    private void lambda$appendTooltip$0(GetTooltipEvent.Append append, Pair pair) {
        StatusEffectInstance StatusEffectInstance2 = (StatusEffectInstance)pair.getFirst();
        append.list.add(1, this.getStatusText(StatusEffectInstance2));
    }

    public boolean previewShulkers() {
        return this.isActive() && this.isPressed() && this.shulkers.get() != false;
    }

    private MutableText getStatusText(StatusEffectInstance StatusEffectInstance2) {
        TranslatableText TranslatableText2 = new TranslatableText(StatusEffectInstance2.getTranslationKey());
        if (StatusEffectInstance2.getAmplifier() != 0) {
            TranslatableText2.append(String.format(" %d (%s)", StatusEffectInstance2.getAmplifier() + 1, StatusEffectUtil.durationToString((StatusEffectInstance)StatusEffectInstance2, (float)1.0f)));
        } else {
            TranslatableText2.append(String.format(" (%s)", StatusEffectUtil.durationToString((StatusEffectInstance)StatusEffectInstance2, (float)1.0f)));
        }
        if (StatusEffectInstance2.getEffectType().isBeneficial()) {
            return TranslatableText2.formatted(Formatting.BLUE);
        }
        return TranslatableText2.formatted(Formatting.RED);
    }

    public static boolean hasItems(ItemStack ItemStack2) {
        NbtCompound NbtCompound2 = ItemStack2.getSubTag("BlockEntityTag");
        return NbtCompound2 != null && NbtCompound2.contains("Items", 9);
    }

    @EventHandler
    private void modifyTooltip(GetTooltipEvent.Modify modify) {
        if (BetterTooltips.hasItems(modify.itemStack) && this.shulkers.get().booleanValue() && this.previewShulkers() || modify.itemStack.getItem() == Items.ENDER_CHEST && this.echest.get().booleanValue() && this.previewEChest()) {
            for (int i = 0; i < modify.list.size(); ++i) {
                modify.y -= 10;
            }
            modify.y -= 4;
        }
    }

    @EventHandler
    private void appendTooltip(GetTooltipEvent.Append append) {
        NbtCompound NbtCompound2;
        NbtCompound NbtCompound3;
        int n;
        Object object;
        if (this.byteSize.get().booleanValue()) {
            try {
                append.itemStack.writeNbt(new NbtCompound()).write((DataOutput)ByteCountDataOutput.INSTANCE);
                int n2 = ByteCountDataOutput.INSTANCE.getCount();
                ByteCountDataOutput.INSTANCE.reset();
                object = this.useKbIfBigEnoughEnabled.get() != false && n2 >= 1024 ? String.format("%.2f kb", Float.valueOf((float)n2 / 1024.0f)) : String.format("%d bytes", n2);
                append.list.add(new LiteralText(String.valueOf(new StringBuilder().append(Formatting.GRAY).append((String)object))));
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        if (this.statusEffects.get().booleanValue()) {
            FoodComponent FoodComponent2;
            if (append.itemStack.getItem() == Items.SUSPICIOUS_STEW) {
                NbtCompound NbtCompound4 = append.itemStack.getTag();
                if (NbtCompound4 != null && (object = NbtCompound4.getList("Effects", 10)) != null) {
                    for (n = 0; n < object.size(); ++n) {
                        NbtCompound3 = object.getCompound(n);
                        byte by = NbtCompound3.getByte("EffectId");
                        int n3 = NbtCompound3.contains("EffectDuration") ? NbtCompound3.getInt("EffectDuration") : 160;
                        StatusEffectInstance StatusEffectInstance2 = new StatusEffectInstance(StatusEffect.byRawId((int)by), n3, 0);
                        append.list.add(1, this.getStatusText(StatusEffectInstance2));
                        if (null == null) continue;
                        return;
                    }
                }
            } else if (append.itemStack.getItem().isFood() && (FoodComponent2 = append.itemStack.getItem().getFoodComponent()) != null) {
                FoodComponent2.getStatusEffects().forEach(arg_0 -> this.lambda$appendTooltip$0(append, arg_0));
            }
        }
        if (this.beehive.get().booleanValue() && (append.itemStack.getItem() == Items.BEEHIVE || append.itemStack.getItem() == Items.BEE_NEST) && (NbtCompound2 = append.itemStack.getTag()) != null) {
            NbtCompound NbtCompound5;
            object = NbtCompound2.getCompound("BlockStateTag");
            if (object != null) {
                n = object.getInt("honey_level");
                append.list.add(1, new LiteralText(String.format("%sHoney level: %s%d%s.", Formatting.GRAY, Formatting.YELLOW, n, Formatting.GRAY)));
            }
            if ((NbtCompound5 = NbtCompound2.getCompound("BlockEntityTag")) != null) {
                NbtCompound3 = NbtCompound5.getList("Bees", 10);
                append.list.add(1, new LiteralText(String.format("%sBees: %s%d%s.", Formatting.GRAY, Formatting.YELLOW, NbtCompound3.size(), Formatting.GRAY)));
            }
        }
        if (BetterTooltips.hasItems(append.itemStack) && this.shulkers.get() != false && !this.previewShulkers() || append.itemStack.getItem() == Items.ENDER_CHEST && this.echest.get() != false && !this.previewEChest() || append.itemStack.getItem() == Items.FILLED_MAP && this.maps.get().booleanValue() && !this.previewMaps()) {
            append.list.add(new LiteralText(""));
            append.list.add(new LiteralText(String.valueOf(new StringBuilder().append("Hold ").append(Formatting.YELLOW).append(this.keybind).append(Formatting.RESET).append(" to preview"))));
        }
    }

    public BetterTooltips() {
        super(Categories.Render, "better-tooltips", "Displays more useful tooltips for certain items.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgShulker = this.settings.createGroup("Shulker");
        this.sgEChest = this.settings.createGroup("EChest");
        this.sgMap = this.settings.createGroup("Map");
        this.sgByteSize = this.settings.createGroup("Byte Size");
        this.sgOther = this.settings.createGroup("Other");
        this.displayWhen = this.sgGeneral.add(new EnumSetting.Builder().name("display-when").description("When to display previews.").defaultValue(DisplayWhen.Keybind).build());
        this.keybind = this.sgGeneral.add(new KeybindSetting.Builder().name("keybind").description("The bind for keybind mode.").defaultValue(Keybind.fromKey(342)).build());
        this.showVanilla = this.sgGeneral.add(new BoolSetting.Builder().name("show-vanilla").description("Displays the vanilla tooltip as well as the preview.").defaultValue(true).build());
        this.middleClickOpen = this.sgGeneral.add(new BoolSetting.Builder().name("middle-click-open").description("Opens a GUI window with the inventory of the storage block when you middle click the item.").defaultValue(true).build());
        this.shulkers = this.sgShulker.add(new BoolSetting.Builder().name("shulker-preview").description("Shows a preview of a shulker box when hovering over it in an inventory.").defaultValue(true).build());
        this.shulkersColor = this.sgShulker.add(new ColorSetting.Builder().name("container-color").description("The color of the preview in container mode.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.shulkerColorFromType = this.sgShulker.add(new BoolSetting.Builder().name("color-from-type").description("Color shulker preview according to the shulkers color.").defaultValue(true).build());
        this.echest = this.sgEChest.add(new BoolSetting.Builder().name("echest-preview").description("Shows a preview of your echest when hovering over it in an inventory.").defaultValue(true).build());
        this.echestColor = this.sgEChest.add(new ColorSetting.Builder().name("container-color").description("The color of the echest preview in container mode.").defaultValue(new SettingColor(0, 50, 50)).build());
        this.maps = this.sgMap.add(new BoolSetting.Builder().name("map-preview").description("Shows a preview of a map when hovering over it in an inventory.").defaultValue(true).build());
        this.mapsScale = this.sgMap.add(new IntSetting.Builder().name("scale").description("The scale of the map preview.").defaultValue(100).min(1).sliderMax(500).build());
        this.byteSize = this.sgByteSize.add(new BoolSetting.Builder().name("byte-size").description("Displays an item's size in bytes in the tooltip.").defaultValue(true).build());
        this.useKbIfBigEnoughEnabled = this.sgByteSize.add(new BoolSetting.Builder().name("use-kb-if-big-enough-enabled").description("Uses KB instead of bytes if your item's size is larger or equal to 1KB.").defaultValue(true).build());
        this.statusEffects = this.sgOther.add(new BoolSetting.Builder().name("status-effects").description("Adds list of status effects to tooltips of food items.").defaultValue(true).build());
        this.beehive = this.sgOther.add(new BoolSetting.Builder().name("beehive").description("Displays information about a beehive or bee nest.").defaultValue(true).build());
    }

    public static final class DisplayWhen
    extends Enum<DisplayWhen> {
        public static final /* enum */ DisplayWhen Keybind = new DisplayWhen();
        public static final /* enum */ DisplayWhen Always = new DisplayWhen();
        private static final DisplayWhen[] $VALUES = DisplayWhen.$values();

        private static DisplayWhen[] $values() {
            return new DisplayWhen[]{Keybind, Always};
        }

        public static DisplayWhen[] values() {
            return (DisplayWhen[])$VALUES.clone();
        }

        public static DisplayWhen valueOf(String string) {
            return Enum.valueOf(DisplayWhen.class, string);
        }
    }
}

