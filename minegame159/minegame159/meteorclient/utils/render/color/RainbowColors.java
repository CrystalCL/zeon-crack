/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render.color;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.GuiThemes;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.waypoints.Waypoint;
import minegame159.meteorclient.systems.waypoints.Waypoints;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.SettingColor;

public class RainbowColors {
    private static final List<Setting<SettingColor>> colorSettings = new ArrayList<Setting<SettingColor>>();
    private static final List<SettingColor> colors = new ArrayList<SettingColor>();

    public static void removeSetting(Setting<SettingColor> setting) {
        colorSettings.remove(setting);
    }

    public static void add(SettingColor settingColor) {
        colors.add(settingColor);
    }

    public static void addSetting(Setting<SettingColor> setting) {
        colorSettings.add(setting);
    }

    @EventHandler
    private static void onTick(TickEvent.Post post) {
        for (Setting<SettingColor> iSerializable : colorSettings) {
            if (iSerializable.module != null && !iSerializable.module.isActive()) continue;
            iSerializable.get().update();
        }
        for (SettingColor settingColor : colors) {
            settingColor.update();
        }
        for (Waypoint waypoint : Waypoints.get()) {
            waypoint.color.update();
        }
        if (Utils.mc.currentScreen instanceof WidgetScreen) {
            for (SettingGroup settingGroup : GuiThemes.get().settings) {
                for (Setting<?> setting : settingGroup) {
                    if (!(setting instanceof ColorSetting)) continue;
                    ((SettingColor)setting.get()).update();
                }
            }
        }
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(RainbowColors.class);
    }
}

