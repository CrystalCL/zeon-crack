/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets.pressable;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;

public class WMeteorMinus
extends WMinus
implements MeteorWidget {
    @Override
    protected void onRender(GuiRenderer lllllllllllllllllllIIIllllIIIlII, double lllllllllllllllllllIIIllllIIlIlI, double lllllllllllllllllllIIIllllIIlIIl, double lllllllllllllllllllIIIllllIIlIII) {
        WMeteorMinus lllllllllllllllllllIIIllllIIllII;
        double lllllllllllllllllllIIIllllIIIlll = lllllllllllllllllllIIIllllIIllII.pad();
        double lllllllllllllllllllIIIllllIIIllI = lllllllllllllllllllIIIllllIIllII.theme.scale(3.0);
        lllllllllllllllllllIIIllllIIllII.renderBackground(lllllllllllllllllllIIIllllIIIlII, lllllllllllllllllllIIIllllIIllII, lllllllllllllllllllIIIllllIIllII.pressed, lllllllllllllllllllIIIllllIIllII.mouseOver);
        lllllllllllllllllllIIIllllIIIlII.quad(lllllllllllllllllllIIIllllIIllII.x + lllllllllllllllllllIIIllllIIIlll, lllllllllllllllllllIIIllllIIllII.y + lllllllllllllllllllIIIllllIIllII.height / 2.0 - lllllllllllllllllllIIIllllIIIllI / 2.0, lllllllllllllllllllIIIllllIIllII.width - lllllllllllllllllllIIIllllIIIlll * 2.0, lllllllllllllllllllIIIllllIIIllI, lllllllllllllllllllIIIllllIIllII.theme().minusColor.get());
    }

    public WMeteorMinus() {
        WMeteorMinus lllllllllllllllllllIIIllllIlIIIl;
    }
}

