/*
 * Decompiled with CFR 0.150.
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
    private static final /* synthetic */ List<SettingColor> colors;
    private static final /* synthetic */ List<Setting<SettingColor>> colorSettings;

    public static void removeSetting(Setting<SettingColor> llllllllllllllllIlllIlIIlllIllll) {
        colorSettings.remove(llllllllllllllllIlllIlIIlllIllll);
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(RainbowColors.class);
    }

    public static void addSetting(Setting<SettingColor> llllllllllllllllIlllIlIIllllIIlI) {
        colorSettings.add(llllllllllllllllIlllIlIIllllIIlI);
    }

    @EventHandler
    private static void onTick(TickEvent.Post llllllllllllllllIlllIlIIlllIIIlI) {
        for (Setting<SettingColor> llllllllllllllllIlllIlIIlllIIlll : colorSettings) {
            if (llllllllllllllllIlllIlIIlllIIlll.module != null && !llllllllllllllllIlllIlIIlllIIlll.module.isActive()) continue;
            llllllllllllllllIlllIlIIlllIIlll.get().update();
        }
        for (SettingColor llllllllllllllllIlllIlIIlllIIllI : colors) {
            llllllllllllllllIlllIlIIlllIIllI.update();
        }
        for (Waypoint llllllllllllllllIlllIlIIlllIIlIl : Waypoints.get()) {
            llllllllllllllllIlllIlIIlllIIlIl.color.update();
        }
        if (Utils.mc.currentScreen instanceof WidgetScreen) {
            for (SettingGroup llllllllllllllllIlllIlIIlllIIIll : GuiThemes.get().settings) {
                for (Setting<?> llllllllllllllllIlllIlIIlllIIlII : llllllllllllllllIlllIlIIlllIIIll) {
                    if (!(llllllllllllllllIlllIlIIlllIIlII instanceof ColorSetting)) continue;
                    ((SettingColor)llllllllllllllllIlllIlIIlllIIlII.get()).update();
                }
            }
        }
    }

    public RainbowColors() {
        RainbowColors llllllllllllllllIlllIlIIllllIllI;
    }

    public static void add(SettingColor llllllllllllllllIlllIlIIlllIllIl) {
        colors.add(llllllllllllllllIlllIlIIlllIllIl);
    }

    static {
        colorSettings = new ArrayList<Setting<SettingColor>>();
        colors = new ArrayList<SettingColor>();
    }
}

