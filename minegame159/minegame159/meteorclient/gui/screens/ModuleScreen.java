/*
 * Decompiled with CFR 0.151.
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
    private final WKeybind keybind;

    private static void lambda$new$3(Module module, WCheckbox wCheckbox) {
        if (module.isVisible() != wCheckbox.checked) {
            module.setVisible(wCheckbox.checked);
        }
    }

    public ModuleScreen(GuiTheme guiTheme, Module module) {
        super(guiTheme, module.title);
        Object object;
        this.add(guiTheme.label(module.description, (double)Utils.getWindowWidth() / 2.0));
        if (module.settings.groups.size() > 0) {
            this.add(guiTheme.settings(module.settings)).expandX();
        }
        this.add(guiTheme.horizontalSeparator()).expandX();
        WWidget wWidget = module.getWidget(guiTheme);
        if (wWidget != null) {
            object = this.add(wWidget);
            if (wWidget instanceof WContainer) {
                ((Cell)object).expandX();
            }
            this.add(guiTheme.horizontalSeparator()).expandX();
        }
        this.keybind = this.add(guiTheme.keybind(module.keybind, true)).widget();
        this.keybind.actionOnSet = () -> ModuleScreen.lambda$new$0(module);
        object = this.add(guiTheme.horizontalList()).widget();
        ((WContainer)object).add(guiTheme.label("Toggle on bind release: "));
        WCheckbox wCheckbox = ((WContainer)object).add(guiTheme.checkbox(module.toggleOnBindRelease)).widget();
        wCheckbox.action = () -> ModuleScreen.lambda$new$1(module, wCheckbox);
        this.add(guiTheme.horizontalSeparator()).expandX();
        WHorizontalList wHorizontalList = this.add(guiTheme.horizontalList()).expandX().widget();
        wHorizontalList.add(guiTheme.label("Active: "));
        WCheckbox wCheckbox2 = wHorizontalList.add(guiTheme.checkbox(module.isActive())).expandCellX().widget();
        wCheckbox2.action = () -> ModuleScreen.lambda$new$2(module, wCheckbox2);
        wHorizontalList.add(guiTheme.label("Visible: "));
        WCheckbox wCheckbox3 = wHorizontalList.add(guiTheme.checkbox(module.isVisible())).widget();
        wCheckbox3.action = () -> ModuleScreen.lambda$new$3(module, wCheckbox3);
    }

    private static void lambda$new$2(Module module, WCheckbox wCheckbox) {
        if (module.isActive() != wCheckbox.checked) {
            module.toggle(Utils.canUpdate());
        }
    }

    private static void lambda$new$0(Module module) {
        Modules.get().setModuleToBind(module);
    }

    private static void lambda$new$1(Module module, WCheckbox wCheckbox) {
        module.toggleOnBindRelease = wCheckbox.checked;
    }

    @EventHandler
    private void onModuleBindChanged(ModuleBindChangedEvent moduleBindChangedEvent) {
        this.keybind.reset();
    }
}

