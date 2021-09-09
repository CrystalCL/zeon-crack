/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.screens;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.ModuleBindChangedEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WKeybind;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WContainer;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.Utils;

public class ModuleScreen
extends WindowScreen {
    private final /* synthetic */ WKeybind keybind;

    public ModuleScreen(GuiTheme lllllllllllllllllIIIIlIlllllIIlI, Module lllllllllllllllllIIIIlIlllllIIIl) {
        super(lllllllllllllllllIIIIlIlllllIIlI, lllllllllllllllllIIIIlIlllllIIIl.title);
        ModuleScreen lllllllllllllllllIIIIlIlllllIIll;
        lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIlllllIIlI.label(lllllllllllllllllIIIIlIlllllIIIl.description, (double)Utils.getWindowWidth() / 2.0));
        if (lllllllllllllllllIIIIlIlllllIIIl.settings.groups.size() > 0) {
            lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIlllllIIlI.settings(lllllllllllllllllIIIIlIlllllIIIl.settings)).expandX();
        }
        lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIlllllIIlI.horizontalSeparator()).expandX();
        WWidget lllllllllllllllllIIIIlIllllllIIl = lllllllllllllllllIIIIlIlllllIIIl.getWidget(lllllllllllllllllIIIIlIlllllIIlI);
        if (lllllllllllllllllIIIIlIllllllIIl != null) {
            Cell<WWidget> lllllllllllllllllIIIIlIlllllllIl = lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIllllllIIl);
            if (lllllllllllllllllIIIIlIllllllIIl instanceof WContainer) {
                lllllllllllllllllIIIIlIlllllllIl.expandX();
            }
            lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIlllllIIlI.horizontalSeparator()).expandX();
        }
        lllllllllllllllllIIIIlIlllllIIll.keybind = lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIlllllIIlI.keybind(lllllllllllllllllIIIIlIlllllIIIl.keybind, true)).widget();
        lllllllllllllllllIIIIlIlllllIIll.keybind.actionOnSet = () -> Modules.get().setModuleToBind(lllllllllllllllllIIIIlIlllllIIIl);
        WHorizontalList lllllllllllllllllIIIIlIllllllIII = lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIlllllIIlI.horizontalList()).widget();
        lllllllllllllllllIIIIlIllllllIII.add(lllllllllllllllllIIIIlIlllllIIlI.label("Toggle on bind release: "));
        WCheckbox lllllllllllllllllIIIIlIlllllIlll = lllllllllllllllllIIIIlIllllllIII.add(lllllllllllllllllIIIIlIlllllIIlI.checkbox(lllllllllllllllllIIIIlIlllllIIIl.toggleOnBindRelease)).widget();
        lllllllllllllllllIIIIlIlllllIlll.action = () -> {
            lllllllllllllllllIIIIlIlllIlIllI.toggleOnBindRelease = lllllllllllllllllIIIIlIlllIlIlIl.checked;
        };
        lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIlllllIIlI.horizontalSeparator()).expandX();
        WHorizontalList lllllllllllllllllIIIIlIlllllIllI = lllllllllllllllllIIIIlIlllllIIll.add(lllllllllllllllllIIIIlIlllllIIlI.horizontalList()).expandX().widget();
        lllllllllllllllllIIIIlIlllllIllI.add(lllllllllllllllllIIIIlIlllllIIlI.label("Active: "));
        WCheckbox lllllllllllllllllIIIIlIlllllIlIl = lllllllllllllllllIIIIlIlllllIllI.add(lllllllllllllllllIIIIlIlllllIIlI.checkbox(lllllllllllllllllIIIIlIlllllIIIl.isActive())).expandCellX().widget();
        lllllllllllllllllIIIIlIlllllIlIl.action = () -> {
            if (lllllllllllllllllIIIIlIlllllIIIl.isActive() != lllllllllllllllllIIIIlIlllIlllIl.checked) {
                lllllllllllllllllIIIIlIlllllIIIl.toggle(Utils.canUpdate());
            }
        };
        lllllllllllllllllIIIIlIlllllIllI.add(lllllllllllllllllIIIIlIlllllIIlI.label("Visible: "));
        WCheckbox lllllllllllllllllIIIIlIlllllIlII = lllllllllllllllllIIIIlIlllllIllI.add(lllllllllllllllllIIIIlIlllllIIlI.checkbox(lllllllllllllllllIIIIlIlllllIIIl.isVisible())).widget();
        lllllllllllllllllIIIIlIlllllIlII.action = () -> {
            if (lllllllllllllllllIIIIlIlllllIIIl.isVisible() != lllllllllllllllllIIIIlIllllIIIll.checked) {
                lllllllllllllllllIIIIlIlllllIIIl.setVisible(lllllllllllllllllIIIIlIllllIIIll.checked);
            }
        };
    }

    @EventHandler
    private void onModuleBindChanged(ModuleBindChangedEvent lllllllllllllllllIIIIlIllllIlIII) {
        ModuleScreen lllllllllllllllllIIIIlIllllIlIIl;
        lllllllllllllllllIIIIlIllllIlIIl.keybind.reset();
    }
}

