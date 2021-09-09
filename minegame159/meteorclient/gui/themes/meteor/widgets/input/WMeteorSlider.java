/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets.input;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.input.WSlider;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorSlider
extends WSlider
implements MeteorWidget {
    @Override
    protected void onRender(GuiRenderer lllIIlIllllIIII, double lllIIlIlllIllll, double lllIIlIlllIlllI, double lllIIlIlllIllIl) {
        WMeteorSlider lllIIlIllllIIIl;
        double lllIIlIlllIllII = lllIIlIllllIIIl.valueWidth();
        lllIIlIllllIIIl.renderBar(lllIIlIllllIIII, lllIIlIlllIllII);
        lllIIlIllllIIIl.renderHandle(lllIIlIllllIIII, lllIIlIlllIllII);
    }

    public WMeteorSlider(double lllIIlIlllllIll, double lllIIlIllllIllI, double lllIIlIlllllIIl) {
        super(lllIIlIlllllIll, lllIIlIllllIllI, lllIIlIlllllIIl);
        WMeteorSlider lllIIlIllllllII;
    }

    private void renderBar(GuiRenderer lllIIlIllIlIlll, double lllIIlIllIllllI) {
        WMeteorSlider lllIIlIlllIIIII;
        MeteorGuiTheme lllIIlIllIlllIl = lllIIlIlllIIIII.theme();
        double lllIIlIllIlllII = lllIIlIllIlllIl.scale(3.0);
        double lllIIlIllIllIll = lllIIlIlllIIIII.handleSize();
        double lllIIlIllIllIlI = lllIIlIlllIIIII.x + lllIIlIllIllIll / 2.0;
        double lllIIlIllIllIIl = lllIIlIlllIIIII.y + lllIIlIlllIIIII.height / 2.0 - lllIIlIllIlllII / 2.0;
        lllIIlIllIlIlll.quad(lllIIlIllIllIlI, lllIIlIllIllIIl, lllIIlIllIllllI, lllIIlIllIlllII, lllIIlIllIlllIl.sliderLeft.get());
        lllIIlIllIlIlll.quad(lllIIlIllIllIlI + lllIIlIllIllllI, lllIIlIllIllIIl, lllIIlIlllIIIII.width - lllIIlIllIllllI - lllIIlIllIllIll, lllIIlIllIlllII, lllIIlIllIlllIl.sliderRight.get());
    }

    private void renderHandle(GuiRenderer lllIIlIllIIlIlI, double lllIIlIllIIlIIl) {
        WMeteorSlider lllIIlIllIIlIll;
        MeteorGuiTheme lllIIlIllIIlIII = lllIIlIllIIlIll.theme();
        double lllIIlIllIIIlll = lllIIlIllIIlIll.handleSize();
        lllIIlIllIIlIlI.quad(lllIIlIllIIlIll.x + lllIIlIllIIlIIl, lllIIlIllIIlIll.y, lllIIlIllIIIlll, lllIIlIllIIIlll, GuiRenderer.CIRCLE, (Color)lllIIlIllIIlIII.sliderHandle.get(lllIIlIllIIlIll.dragging, lllIIlIllIIlIll.handleMouseOver));
    }
}

