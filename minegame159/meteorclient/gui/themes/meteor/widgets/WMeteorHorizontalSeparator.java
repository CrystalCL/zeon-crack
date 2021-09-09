/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WHorizontalSeparator;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorHorizontalSeparator
extends WHorizontalSeparator
implements MeteorWidget {
    private void renderWithText(GuiRenderer lIllIIlIIlllIl) {
        WMeteorHorizontalSeparator lIllIIlIIllllI;
        MeteorGuiTheme lIllIIlIIlllII = lIllIIlIIllllI.theme();
        double lIllIIlIIllIll = lIllIIlIIlllII.scale(2.0);
        double lIllIIlIIllIlI = lIllIIlIIlllII.scale(1.0);
        double lIllIIlIIllIIl = Math.round(lIllIIlIIllllI.width / 2.0 - lIllIIlIIllllI.textWidth / 2.0 - lIllIIlIIllIll);
        double lIllIIlIIllIII = lIllIIlIIllIll + lIllIIlIIllIIl + lIllIIlIIllllI.textWidth + lIllIIlIIllIll;
        double lIllIIlIIlIlll = Math.round(lIllIIlIIllllI.height / 2.0);
        lIllIIlIIlllIl.quad(lIllIIlIIllllI.x, lIllIIlIIllllI.y + lIllIIlIIlIlll, lIllIIlIIllIIl, lIllIIlIIllIlI, lIllIIlIIlllII.separatorEdges.get(), (Color)lIllIIlIIlllII.separatorCenter.get());
        lIllIIlIIlllIl.text(lIllIIlIIllllI.text, lIllIIlIIllllI.x + lIllIIlIIllIIl + lIllIIlIIllIll, lIllIIlIIllllI.y, lIllIIlIIlllII.separatorText.get(), false);
        lIllIIlIIlllIl.quad(lIllIIlIIllllI.x + lIllIIlIIllIII, lIllIIlIIllllI.y + lIllIIlIIlIlll, lIllIIlIIllllI.width - lIllIIlIIllIII, lIllIIlIIllIlI, lIllIIlIIlllII.separatorCenter.get(), (Color)lIllIIlIIlllII.separatorEdges.get());
    }

    @Override
    protected void onRender(GuiRenderer lIllIIlIllIllI, double lIllIIlIlllIlI, double lIllIIlIlllIIl, double lIllIIlIlllIII) {
        WMeteorHorizontalSeparator lIllIIlIllIlll;
        if (lIllIIlIllIlll.text == null) {
            lIllIIlIllIlll.renderWithoutText(lIllIIlIllIllI);
        } else {
            lIllIIlIllIlll.renderWithText(lIllIIlIllIllI);
        }
    }

    public WMeteorHorizontalSeparator(String lIllIIllIIIIIl) {
        super(lIllIIllIIIIIl);
        WMeteorHorizontalSeparator lIllIIllIIIIlI;
    }

    private void renderWithoutText(GuiRenderer lIllIIlIlIlIlI) {
        WMeteorHorizontalSeparator lIllIIlIlIlIll;
        MeteorGuiTheme lIllIIlIlIlllI = lIllIIlIlIlIll.theme();
        double lIllIIlIlIllIl = lIllIIlIlIlllI.scale(1.0);
        double lIllIIlIlIllII = lIllIIlIlIlIll.width / 2.0;
        lIllIIlIlIlIlI.quad(lIllIIlIlIlIll.x, lIllIIlIlIlIll.y + lIllIIlIlIllIl, lIllIIlIlIllII, lIllIIlIlIllIl, lIllIIlIlIlllI.separatorEdges.get(), (Color)lIllIIlIlIlllI.separatorCenter.get());
        lIllIIlIlIlIlI.quad(lIllIIlIlIlIll.x + lIllIIlIlIllII, lIllIIlIlIlIll.y + lIllIIlIlIllIl, lIllIIlIlIllII, lIllIIlIlIllIl, lIllIIlIlIlllI.separatorCenter.get(), (Color)lIllIIlIlIlllI.separatorEdges.get());
    }
}

