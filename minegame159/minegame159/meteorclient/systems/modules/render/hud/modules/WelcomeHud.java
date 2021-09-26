/*
 * Decompiled with CFR 0.151.
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
    private final Setting<SettingColor> color;
    private final SettingGroup sgGeneral;

    public WelcomeHud(HUD hUD) {
        super(hUD, "welcome", "Displays a welcome message.", "Welcome to Crystal Client, ");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.color = this.sgGeneral.add(new ColorSetting.Builder().name("color").description("Color of welcome text.").defaultValue(new SettingColor(120, 43, 153)).build());
        this.rightColor = this.color.get();
    }

    @Override
    protected String getRight() {
        return String.valueOf(new StringBuilder().append(Modules.get().get(NameProtect.class).getName(this.mc.getSession().getUsername())).append("!"));
    }
}

