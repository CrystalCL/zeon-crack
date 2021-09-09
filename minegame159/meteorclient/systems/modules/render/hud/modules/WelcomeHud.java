/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.NameProtect;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import minegame159.meteorclient.utils.render.color.SettingColor;

public class WelcomeHud
extends DoubleTextHudElement {
    private final /* synthetic */ Setting<SettingColor> color;
    private final /* synthetic */ SettingGroup sgGeneral;

    @Override
    protected String getRight() {
        WelcomeHud llllIIIIII;
        return String.valueOf(new StringBuilder().append(Modules.get().get(NameProtect.class).getName(llllIIIIII.mc.getSession().getUsername())).append("!"));
    }

    public WelcomeHud(HUD llllIIIlII) {
        super(llllIIIlII, "welcome", "Displays a welcome message.", "Welcome to Crystal Client, ");
        WelcomeHud llllIIIlIl;
        llllIIIlIl.sgGeneral = llllIIIlIl.settings.getDefaultGroup();
        llllIIIlIl.color = llllIIIlIl.sgGeneral.add(new ColorSetting.Builder().name("color").description("Color of welcome text.").defaultValue(new SettingColor(120, 43, 153)).build());
        llllIIIlIl.rightColor = llllIIIlIl.color.get();
    }
}

