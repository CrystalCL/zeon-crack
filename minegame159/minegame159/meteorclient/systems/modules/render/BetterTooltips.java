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
import net.minecraft.class_124;
import net.minecraft.class_1291;
import net.minecraft.class_1292;
import net.minecraft.class_1293;
import net.minecraft.class_1747;
import net.minecraft.class_1767;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2248;
import net.minecraft.class_2480;
import net.minecraft.class_2487;
import net.minecraft.class_2585;
import net.minecraft.class_2588;
import net.minecraft.class_4174;
import net.minecraft.class_5250;

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

    public Color getShulkerColor(class_1799 class_17992) {
        if (this.shulkerColorFromType.get().booleanValue()) {
            if (!(class_17992.method_7909() instanceof class_1747)) {
                return this.shulkersColor.get();
            }
            class_2248 class_22482 = ((class_1747)class_17992.method_7909()).method_7711();
            if (!(class_22482 instanceof class_2480)) {
                return this.shulkersColor.get();
            }
            class_2480 class_24802 = (class_2480)class_2480.method_9503((class_1792)class_17992.method_7909());
            class_1767 class_17672 = class_24802.method_10528();
            if (class_17672 == null) {
                return this.shulkersColor.get();
            }
            float[] fArray = class_17672.method_7787();
            return new Color(fArray[0], fArray[1], fArray[2], 1.0f);
        }
        return this.shulkersColor.get();
    }

    private boolean isPressed() {
        return this.keybind.get().isPressed() && this.displayWhen.get() == DisplayWhen.Keybind || this.displayWhen.get() == DisplayWhen.Always;
    }

    private void lambda$appendTooltip$0(GetTooltipEvent.Append append, Pair pair) {
        class_1293 class_12932 = (class_1293)pair.getFirst();
        append.list.add(1, this.getStatusText(class_12932));
    }

    public boolean previewShulkers() {
        return this.isActive() && this.isPressed() && this.shulkers.get() != false;
    }

    private class_5250 getStatusText(class_1293 class_12932) {
        class_2588 class_25882 = new class_2588(class_12932.method_5586());
        if (class_12932.method_5578() != 0) {
            class_25882.method_27693(String.format(" %d (%s)", class_12932.method_5578() + 1, class_1292.method_5577((class_1293)class_12932, (float)1.0f)));
        } else {
            class_25882.method_27693(String.format(" (%s)", class_1292.method_5577((class_1293)class_12932, (float)1.0f)));
        }
        if (class_12932.method_5579().method_5573()) {
            return class_25882.method_27692(class_124.field_1078);
        }
        return class_25882.method_27692(class_124.field_1061);
    }

    public static boolean hasItems(class_1799 class_17992) {
        class_2487 class_24872 = class_17992.method_7941("BlockEntityTag");
        return class_24872 != null && class_24872.method_10573("Items", 9);
    }

    @EventHandler
    private void modifyTooltip(GetTooltipEvent.Modify modify) {
        if (BetterTooltips.hasItems(modify.itemStack) && this.shulkers.get().booleanValue() && this.previewShulkers() || modify.itemStack.method_7909() == class_1802.field_8466 && this.echest.get().booleanValue() && this.previewEChest()) {
            for (int i = 0; i < modify.list.size(); ++i) {
                modify.y -= 10;
            }
            modify.y -= 4;
        }
    }

    @EventHandler
    private void appendTooltip(GetTooltipEvent.Append append) {
        class_2487 class_24872;
        class_2487 class_24873;
        int n;
        Object object;
        if (this.byteSize.get().booleanValue()) {
            try {
                append.itemStack.method_7953(new class_2487()).method_10713((DataOutput)ByteCountDataOutput.INSTANCE);
                int n2 = ByteCountDataOutput.INSTANCE.getCount();
                ByteCountDataOutput.INSTANCE.reset();
                object = this.useKbIfBigEnoughEnabled.get() != false && n2 >= 1024 ? String.format("%.2f kb", Float.valueOf((float)n2 / 1024.0f)) : String.format("%d bytes", n2);
                append.list.add(new class_2585(String.valueOf(new StringBuilder().append(class_124.field_1080).append((String)object))));
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        if (this.statusEffects.get().booleanValue()) {
            class_4174 class_41742;
            if (append.itemStack.method_7909() == class_1802.field_8766) {
                class_2487 class_24874 = append.itemStack.method_7969();
                if (class_24874 != null && (object = class_24874.method_10554("Effects", 10)) != null) {
                    for (n = 0; n < object.size(); ++n) {
                        class_24873 = object.method_10602(n);
                        byte by = class_24873.method_10571("EffectId");
                        int n3 = class_24873.method_10545("EffectDuration") ? class_24873.method_10550("EffectDuration") : 160;
                        class_1293 class_12932 = new class_1293(class_1291.method_5569((int)by), n3, 0);
                        append.list.add(1, this.getStatusText(class_12932));
                        if (null == null) continue;
                        return;
                    }
                }
            } else if (append.itemStack.method_7909().method_19263() && (class_41742 = append.itemStack.method_7909().method_19264()) != null) {
                class_41742.method_19235().forEach(arg_0 -> this.lambda$appendTooltip$0(append, arg_0));
            }
        }
        if (this.beehive.get().booleanValue() && (append.itemStack.method_7909() == class_1802.field_20416 || append.itemStack.method_7909() == class_1802.field_20415) && (class_24872 = append.itemStack.method_7969()) != null) {
            class_2487 class_24875;
            object = class_24872.method_10562("BlockStateTag");
            if (object != null) {
                n = object.method_10550("honey_level");
                append.list.add(1, new class_2585(String.format("%sHoney level: %s%d%s.", class_124.field_1080, class_124.field_1054, n, class_124.field_1080)));
            }
            if ((class_24875 = class_24872.method_10562("BlockEntityTag")) != null) {
                class_24873 = class_24875.method_10554("Bees", 10);
                append.list.add(1, new class_2585(String.format("%sBees: %s%d%s.", class_124.field_1080, class_124.field_1054, class_24873.size(), class_124.field_1080)));
            }
        }
        if (BetterTooltips.hasItems(append.itemStack) && this.shulkers.get() != false && !this.previewShulkers() || append.itemStack.method_7909() == class_1802.field_8466 && this.echest.get() != false && !this.previewEChest() || append.itemStack.method_7909() == class_1802.field_8204 && this.maps.get().booleanValue() && !this.previewMaps()) {
            append.list.add(new class_2585(""));
            append.list.add(new class_2585(String.valueOf(new StringBuilder().append("Hold ").append(class_124.field_1054).append(this.keybind).append(class_124.field_1070).append(" to preview"))));
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

