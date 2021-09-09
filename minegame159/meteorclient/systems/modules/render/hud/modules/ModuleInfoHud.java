/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.ModuleListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AnchorAura;
import minegame159.meteorclient.systems.modules.combat.BedAura;
import minegame159.meteorclient.systems.modules.combat.CrystalAura;
import minegame159.meteorclient.systems.modules.combat.KillAura;
import minegame159.meteorclient.systems.modules.combat.Surround;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;

public class ModuleInfoHud
extends HudElement {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> offColor;
    private final /* synthetic */ Setting<SettingColor> onColor;
    private final /* synthetic */ Setting<Boolean> info;
    private final /* synthetic */ Setting<List<Module>> modules;

    private double getModuleWidth(HudRenderer lllllllllllllllllIIIlIlIlIllllIl, Module lllllllllllllllllIIIlIlIlIllllII) {
        ModuleInfoHud lllllllllllllllllIIIlIlIllIIIIlI;
        double lllllllllllllllllIIIlIlIlIllllll = lllllllllllllllllIIIlIlIlIllllIl.textWidth(lllllllllllllllllIIIlIlIlIllllII.title);
        if (lllllllllllllllllIIIlIlIllIIIIlI.info.get().booleanValue()) {
            lllllllllllllllllIIIlIlIlIllllll += lllllllllllllllllIIIlIlIlIllllIl.textWidth(" ") + lllllllllllllllllIIIlIlIlIllllIl.textWidth(lllllllllllllllllIIIlIlIllIIIIlI.getModuleInfo(lllllllllllllllllIIIlIlIlIllllII));
        }
        return lllllllllllllllllIIIlIlIlIllllll;
    }

    private String getModuleInfo(Module lllllllllllllllllIIIlIlIlIllIlll) {
        ModuleInfoHud lllllllllllllllllIIIlIlIlIlllIII;
        if (lllllllllllllllllIIIlIlIlIllIlll.getInfoString() != null && lllllllllllllllllIIIlIlIlIllIlll.isActive() && lllllllllllllllllIIIlIlIlIlllIII.info.get().booleanValue()) {
            return lllllllllllllllllIIIlIlIlIllIlll.getInfoString();
        }
        if (lllllllllllllllllIIIlIlIlIllIlll.isActive()) {
            return "ON";
        }
        return "OFF";
    }

    @Override
    public void render(HudRenderer lllllllllllllllllIIIlIlIllIlllIl) {
        ModuleInfoHud lllllllllllllllllIIIlIlIllIllllI;
        double lllllllllllllllllIIIlIlIlllIIIII = lllllllllllllllllIIIlIlIllIllllI.box.getX();
        double lllllllllllllllllIIIlIlIllIlllll = lllllllllllllllllIIIlIlIllIllllI.box.getY();
        if (Modules.get() == null || lllllllllllllllllIIIlIlIllIllllI.modules.get().isEmpty()) {
            lllllllllllllllllIIIlIlIllIlllIl.text("Module Info", lllllllllllllllllIIIlIlIlllIIIII, lllllllllllllllllIIIlIlIllIlllll, lllllllllllllllllIIIlIlIllIllllI.hud.primaryColor.get());
            return;
        }
        for (Module lllllllllllllllllIIIlIlIlllIIIll : lllllllllllllllllIIIlIlIllIllllI.modules.get()) {
            lllllllllllllllllIIIlIlIllIllllI.renderModule(lllllllllllllllllIIIlIlIllIlllIl, lllllllllllllllllIIIlIlIlllIIIll, lllllllllllllllllIIIlIlIlllIIIII + lllllllllllllllllIIIlIlIllIllllI.box.alignX(lllllllllllllllllIIIlIlIllIllllI.getModuleWidth(lllllllllllllllllIIIlIlIllIlllIl, lllllllllllllllllIIIlIlIlllIIIll)), lllllllllllllllllIIIlIlIllIlllll);
            lllllllllllllllllIIIlIlIllIlllll += 2.0 + lllllllllllllllllIIIlIlIllIlllIl.textHeight();
        }
    }

    private void renderModule(HudRenderer lllllllllllllllllIIIlIlIllIIlIll, Module lllllllllllllllllIIIlIlIllIlIIII, double lllllllllllllllllIIIlIlIllIIllll, double lllllllllllllllllIIIlIlIllIIlllI) {
        ModuleInfoHud lllllllllllllllllIIIlIlIllIlIIlI;
        lllllllllllllllllIIIlIlIllIIlIll.text(lllllllllllllllllIIIlIlIllIlIIII.title, lllllllllllllllllIIIlIlIllIIllll, lllllllllllllllllIIIlIlIllIIlllI, lllllllllllllllllIIIlIlIllIlIIlI.hud.primaryColor.get());
        String lllllllllllllllllIIIlIlIllIIllIl = lllllllllllllllllIIIlIlIllIlIIlI.getModuleInfo(lllllllllllllllllIIIlIlIllIlIIII);
        lllllllllllllllllIIIlIlIllIIlIll.text(lllllllllllllllllIIIlIlIllIIllIl, lllllllllllllllllIIIlIlIllIIllll + lllllllllllllllllIIIlIlIllIIlIll.textWidth(lllllllllllllllllIIIlIlIllIlIIII.title) + lllllllllllllllllIIIlIlIllIIlIll.textWidth(" "), lllllllllllllllllIIIlIlIllIIlllI, lllllllllllllllllIIIlIlIllIlIIII.isActive() ? (Color)lllllllllllllllllIIIlIlIllIlIIlI.onColor.get() : (Color)lllllllllllllllllIIIlIlIllIlIIlI.offColor.get());
    }

    public ModuleInfoHud(HUD lllllllllllllllllIIIlIllIIIIIIII) {
        super(lllllllllllllllllIIIlIllIIIIIIII, "module-info", "Displays if selected modules are enabled or disabled.");
        ModuleInfoHud lllllllllllllllllIIIlIlIllllllll;
        lllllllllllllllllIIIlIlIllllllll.sgGeneral = lllllllllllllllllIIIlIlIllllllll.settings.getDefaultGroup();
        lllllllllllllllllIIIlIlIllllllll.modules = lllllllllllllllllIIIlIlIllllllll.sgGeneral.add(new ModuleListSetting.Builder().name("modules").description("Which modules to display").defaultValue(ModuleInfoHud.getDefaultModules()).build());
        lllllllllllllllllIIIlIlIllllllll.info = lllllllllllllllllIIIlIlIllllllll.sgGeneral.add(new BoolSetting.Builder().name("additional-info").description("Shows additional info from the module next to the name in the module info list.").defaultValue(true).build());
        lllllllllllllllllIIIlIlIllllllll.onColor = lllllllllllllllllIIIlIlIllllllll.sgGeneral.add(new ColorSetting.Builder().name("on-color").description("Color when module is on.").defaultValue(new SettingColor(25, 225, 25)).build());
        lllllllllllllllllIIIlIlIllllllll.offColor = lllllllllllllllllIIIlIlIllllllll.sgGeneral.add(new ColorSetting.Builder().name("off-color").description("Color when module is off.").defaultValue(new SettingColor(225, 25, 25)).build());
    }

    private static List<Module> getDefaultModules() {
        ArrayList<Module> lllllllllllllllllIIIlIlIlIllIIll = new ArrayList<Module>(5);
        lllllllllllllllllIIIlIlIlIllIIll.add(Modules.get().get(KillAura.class));
        lllllllllllllllllIIIlIlIlIllIIll.add(Modules.get().get(CrystalAura.class));
        lllllllllllllllllIIIlIlIlIllIIll.add(Modules.get().get(AnchorAura.class));
        lllllllllllllllllIIIlIlIlIllIIll.add(Modules.get().get(BedAura.class));
        lllllllllllllllllIIIlIlIlIllIIll.add(Modules.get().get(Surround.class));
        return lllllllllllllllllIIIlIlIlIllIIll;
    }

    @Override
    public void update(HudRenderer lllllllllllllllllIIIlIlIllllIlII) {
        ModuleInfoHud lllllllllllllllllIIIlIlIllllIlIl;
        if (Modules.get() == null || lllllllllllllllllIIIlIlIllllIlIl.modules.get().isEmpty()) {
            lllllllllllllllllIIIlIlIllllIlIl.box.setSize(lllllllllllllllllIIIlIlIllllIlII.textWidth("Module Info"), lllllllllllllllllIIIlIlIllllIlII.textHeight());
            return;
        }
        double lllllllllllllllllIIIlIlIllllIIll = 0.0;
        double lllllllllllllllllIIIlIlIllllIIlI = 0.0;
        int lllllllllllllllllIIIlIlIllllIIIl = 0;
        for (Module lllllllllllllllllIIIlIlIllllIllI : lllllllllllllllllIIIlIlIllllIlIl.modules.get()) {
            lllllllllllllllllIIIlIlIllllIIll = Math.max(lllllllllllllllllIIIlIlIllllIIll, lllllllllllllllllIIIlIlIllllIlIl.getModuleWidth(lllllllllllllllllIIIlIlIllllIlII, lllllllllllllllllIIIlIlIllllIllI));
            lllllllllllllllllIIIlIlIllllIIlI += lllllllllllllllllIIIlIlIllllIlII.textHeight();
            if (lllllllllllllllllIIIlIlIllllIIIl > 0) {
                lllllllllllllllllIIIlIlIllllIIlI += 2.0;
            }
            ++lllllllllllllllllIIIlIlIllllIIIl;
        }
        lllllllllllllllllIIIlIlIllllIlIl.box.setSize(lllllllllllllllllIIIlIlIllllIIll, lllllllllllllllllIIIlIlIllllIIlI);
    }
}

