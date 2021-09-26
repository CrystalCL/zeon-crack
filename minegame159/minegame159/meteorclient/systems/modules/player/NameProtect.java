/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class NameProtect
extends Module {
    private final SettingGroup sgGeneral;
    private String username;
    private final Setting<String> name;

    public NameProtect() {
        super(Categories.Player, "name-protect", "Hides your name client-side.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.name = this.sgGeneral.add(new StringSetting.Builder().name("name").description("Name to be replaced with.").defaultValue("squidoodly").build());
        this.username = "If you see this, something is wrong.";
    }

    @Override
    public void onActivate() {
        this.username = this.mc.getSession().getUsername();
    }

    public String getName(String string) {
        if (this.name.get().length() > 0 && this.isActive()) {
            return this.name.get();
        }
        return string;
    }

    public String replaceName(String string) {
        if (string.contains(this.username) && this.name.get().length() > 0 && this.isActive()) {
            return string.replace(this.username, this.name.get());
        }
        return string;
    }
}

