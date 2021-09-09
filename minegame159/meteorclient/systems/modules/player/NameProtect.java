/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.player;

import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class NameProtect
extends Module {
    private /* synthetic */ String username;
    private final /* synthetic */ Setting<String> name;
    private final /* synthetic */ SettingGroup sgGeneral;

    @Override
    public void onActivate() {
        NameProtect llllIII;
        llllIII.username = llllIII.mc.getSession().getUsername();
    }

    public String replaceName(String lllIIll) {
        NameProtect lllIlII;
        if (lllIIll.contains(lllIlII.username) && lllIlII.name.get().length() > 0 && lllIlII.isActive()) {
            return lllIIll.replace(lllIlII.username, lllIlII.name.get());
        }
        return lllIIll;
    }

    public NameProtect() {
        super(Categories.Player, "name-protect", "Hides your name client-side.");
        NameProtect llllIlI;
        llllIlI.sgGeneral = llllIlI.settings.getDefaultGroup();
        llllIlI.name = llllIlI.sgGeneral.add(new StringSetting.Builder().name("name").description("Name to be replaced with.").defaultValue("squidoodly").build());
        llllIlI.username = "If you see this, something is wrong.";
    }

    public String getName(String llIlIll) {
        NameProtect llIlllI;
        if (llIlllI.name.get().length() > 0 && llIlllI.isActive()) {
            return llIlllI.name.get();
        }
        return llIlIll;
    }
}

