/*
 * Decompiled with CFR 0.150.
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
    protected void onRenderBefore(float llllllllllllllllllIlIIIIlIIIlIlI) {
        Modules.get().get(HUD.class).onRender(Render2DEvent.get(0, 0, llllllllllllllllllIlIIIIlIIIlIlI));
    }

    public HudElementScreen(GuiTheme llllllllllllllllllIlIIIIlIIlIIIl, HudElement llllllllllllllllllIlIIIIlIIlIIII) {
        super(llllllllllllllllllIlIIIIlIIlIIIl, llllllllllllllllllIlIIIIlIIlIIII.title);
        HudElementScreen llllllllllllllllllIlIIIIlIIlIlll;
        llllllllllllllllllIlIIIIlIIlIlll.add(llllllllllllllllllIlIIIIlIIlIIIl.label(llllllllllllllllllIlIIIIlIIlIIII.description, (double)Utils.getWindowWidth() / 2.0));
        if (llllllllllllllllllIlIIIIlIIlIIII.settings.sizeGroups() > 0) {
            llllllllllllllllllIlIIIIlIIlIlll.add(llllllllllllllllllIlIIIIlIIlIIIl.settings(llllllllllllllllllIlIIIIlIIlIIII.settings)).expandX();
            llllllllllllllllllIlIIIIlIIlIlll.add(llllllllllllllllllIlIIIIlIIlIIIl.horizontalSeparator()).expandX();
        }
        WHorizontalList llllllllllllllllllIlIIIIlIIlIlII = llllllllllllllllllIlIIIIlIIlIlll.add(llllllllllllllllllIlIIIIlIIlIIIl.horizontalList()).expandX().widget();
        llllllllllllllllllIlIIIIlIIlIlII.add(llllllllllllllllllIlIIIIlIIlIIIl.label("Active:"));
        WCheckbox llllllllllllllllllIlIIIIlIIlIIll = llllllllllllllllllIlIIIIlIIlIlII.add(llllllllllllllllllIlIIIIlIIlIIIl.checkbox(llllllllllllllllllIlIIIIlIIlIIII.active)).widget();
        llllllllllllllllllIlIIIIlIIlIIll.action = () -> {
            if (llllllllllllllllllIlIIIIlIIIIlll.active != llllllllllllllllllIlIIIIlIIIIlII.checked) {
                llllllllllllllllllIlIIIIlIIlIIII.toggle();
            }
        };
    }
}

