/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.screens.settings;

import java.util.List;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.screens.settings.LeftRightListSettingScreen;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;

public class ModuleListSettingScreen
extends LeftRightListSettingScreen<Module> {
    @Override
    protected WWidget getValueWidget(Module llllllllllllllllllIlIIlllIIIIIlI) {
        ModuleListSettingScreen llllllllllllllllllIlIIlllIIIIlIl;
        return llllllllllllllllllIlIIlllIIIIlIl.theme.label(llllllllllllllllllIlIIlllIIIIlIl.getValueName(llllllllllllllllllIlIIlllIIIIIlI));
    }

    @Override
    protected String getValueName(Module llllllllllllllllllIlIIllIllllllI) {
        return llllllllllllllllllIlIIllIllllllI.title;
    }

    public ModuleListSettingScreen(GuiTheme llllllllllllllllllIlIIlllIIIlIIl, Setting<List<Module>> llllllllllllllllllIlIIlllIIIlIII) {
        super(llllllllllllllllllIlIIlllIIIlIIl, "Select modules", llllllllllllllllllIlIIlllIIIlIII, Modules.REGISTRY);
        ModuleListSettingScreen llllllllllllllllllIlIIlllIIIllIl;
    }
}

