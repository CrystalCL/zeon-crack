/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets.pressable;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.utils.Utils;

public class WMeteorCheckbox
extends WCheckbox
implements MeteorWidget {
    private /* synthetic */ double animProgress;

    @Override
    protected void onRender(GuiRenderer lllllllllllllllllllIllllIIIIlIII, double lllllllllllllllllllIllllIIIIllIl, double lllllllllllllllllllIllllIIIIllII, double lllllllllllllllllllIllllIIIIlIll) {
        WMeteorCheckbox lllllllllllllllllllIllllIIIIllll;
        MeteorGuiTheme lllllllllllllllllllIllllIIIIlIlI = lllllllllllllllllllIllllIIIIllll.theme();
        lllllllllllllllllllIllllIIIIllll.animProgress += (double)(lllllllllllllllllllIllllIIIIllll.checked ? 1 : -1) * lllllllllllllllllllIllllIIIIlIll * 14.0;
        lllllllllllllllllllIllllIIIIllll.animProgress = Utils.clamp(lllllllllllllllllllIllllIIIIllll.animProgress, 0.0, 1.0);
        lllllllllllllllllllIllllIIIIllll.renderBackground(lllllllllllllllllllIllllIIIIlIII, lllllllllllllllllllIllllIIIIllll, lllllllllllllllllllIllllIIIIllll.pressed, lllllllllllllllllllIllllIIIIllll.mouseOver);
        if (lllllllllllllllllllIllllIIIIllll.animProgress > 0.0) {
            double lllllllllllllllllllIllllIIIlIIII = (lllllllllllllllllllIllllIIIIllll.width - lllllllllllllllllllIllllIIIIlIlI.scale(2.0)) / 1.75 * lllllllllllllllllllIllllIIIIllll.animProgress;
            lllllllllllllllllllIllllIIIIlIII.quad(lllllllllllllllllllIllllIIIIllll.x + (lllllllllllllllllllIllllIIIIllll.width - lllllllllllllllllllIllllIIIlIIII) / 2.0, lllllllllllllllllllIllllIIIIllll.y + (lllllllllllllllllllIllllIIIIllll.height - lllllllllllllllllllIllllIIIlIIII) / 2.0, lllllllllllllllllllIllllIIIlIIII, lllllllllllllllllllIllllIIIlIIII, lllllllllllllllllllIllllIIIIlIlI.checkboxColor.get());
        }
    }

    public WMeteorCheckbox(boolean lllllllllllllllllllIllllIIIllIII) {
        super(lllllllllllllllllllIllllIIIllIII);
        WMeteorCheckbox lllllllllllllllllllIllllIIIlIlll;
        lllllllllllllllllllIllllIIIlIlll.animProgress = lllllllllllllllllllIllllIIIllIII ? 1.0 : 0.0;
    }
}

