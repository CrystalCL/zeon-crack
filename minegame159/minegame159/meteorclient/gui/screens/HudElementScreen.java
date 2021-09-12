/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.screens;

import minegame159.meteorclient.events.render.Render2DEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.Utils;

public class HudElementScreen
extends WindowScreen {
    @Override
    protected void onRenderBefore(float f) {
        Modules.get().get(HUD.class).onRender(Render2DEvent.get(0, 0, f));
    }

    public HudElementScreen(GuiTheme guiTheme, HudElement hudElement) {
        super(guiTheme, hudElement.title);
        this.add(guiTheme.label(hudElement.description, (double)Utils.getWindowWidth() / 2.0));
        if (hudElement.settings.sizeGroups() > 0) {
            this.add(guiTheme.settings(hudElement.settings)).expandX();
            this.add(guiTheme.horizontalSeparator()).expandX();
        }
        WHorizontalList wHorizontalList = this.add(guiTheme.horizontalList()).expandX().widget();
        wHorizontalList.add(guiTheme.label("Active:"));
        WCheckbox wCheckbox = wHorizontalList.add(guiTheme.checkbox(hudElement.active)).widget();
        wCheckbox.action = () -> HudElementScreen.lambda$new$0(hudElement, wCheckbox);
    }

    private static void lambda$new$0(HudElement hudElement, WCheckbox wCheckbox) {
        if (hudElement.active != wCheckbox.checked) {
            hudElement.toggle();
        }
    }
}

