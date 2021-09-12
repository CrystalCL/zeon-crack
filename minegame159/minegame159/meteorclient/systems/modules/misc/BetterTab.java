/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_2585;
import net.minecraft.class_5251;
import net.minecraft.class_640;

public class BetterTab
extends Module {
    private final Setting<Integer> tabSize;
    private final Setting<Boolean> self;
    private final Setting<Boolean> friends;
    private final Setting<SettingColor> selfColor;
    private final SettingGroup sgDefault;

    public BetterTab() {
        super(Categories.Misc, "better-tab", "Various improvements to the tab list.");
        this.sgDefault = this.settings.getDefaultGroup();
        this.tabSize = this.sgDefault.add(new IntSetting.Builder().name("tablist-size").description("Bypasses the 80 player limit on the tablist.").defaultValue(100).min(1).sliderMin(1).build());
        this.friends = this.sgDefault.add(new BoolSetting.Builder().name("highlight-friends").description("Highlights friends in the tablist.").defaultValue(true).build());
        this.self = this.sgDefault.add(new BoolSetting.Builder().name("highlight-self").description("Highlights yourself in the tablist.").defaultValue(true).build());
        this.selfColor = this.sgDefault.add(new ColorSetting.Builder().name("self-color").description("The color to highlight your name with.").defaultValue(new SettingColor(250, 130, 30)).build());
    }

    public int getTabSize() {
        return this.isActive() ? this.tabSize.get() : 80;
    }

    public class_2561 getPlayerName(class_640 class_6402) {
        Object object;
        Color color = null;
        class_2561 class_25612 = class_6402.method_2971();
        if (class_25612 == null) {
            class_25612 = new class_2585(class_6402.method_2966().getName());
        }
        if (class_6402.method_2966().getId().toString().equals(this.mc.field_1724.method_7334().getId().toString()) && this.self.get().booleanValue()) {
            color = this.selfColor.get();
        } else if (this.friends.get().booleanValue() && (object = Friends.get().get(class_6402.method_2966().getName())) != null) {
            color = Friends.get().getFriendColor((Friend)object);
        }
        if (color != null) {
            object = class_25612.getString();
            for (class_124 class_1242 : class_124.values()) {
                if (!class_1242.method_543()) continue;
                object = ((String)object).replace(class_1242.toString(), "");
                if (4 != 1) continue;
                return null;
            }
            class_25612 = new class_2585((String)object).method_10862(class_25612.method_10866().method_27703(new class_5251(color.getPacked())));
        }
        return class_25612;
    }
}

