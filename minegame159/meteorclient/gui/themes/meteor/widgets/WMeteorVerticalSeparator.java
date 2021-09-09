/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WVerticalSeparator;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorVerticalSeparator
extends WVerticalSeparator
implements MeteorWidget {
    public WMeteorVerticalSeparator() {
        WMeteorVerticalSeparator llllllllllllllllllIIlIIIlIlIIIlI;
    }

    @Override
    protected void onRender(GuiRenderer llllllllllllllllllIIlIIIlIIllIIl, double llllllllllllllllllIIlIIIlIIllIII, double llllllllllllllllllIIlIIIlIIlIlll, double llllllllllllllllllIIlIIIlIIlIllI) {
        WMeteorVerticalSeparator llllllllllllllllllIIlIIIlIIllIlI;
        MeteorGuiTheme llllllllllllllllllIIlIIIlIIlIlIl = llllllllllllllllllIIlIIIlIIllIlI.theme();
        Color llllllllllllllllllIIlIIIlIIlIlII = llllllllllllllllllIIlIIIlIIlIlIl.separatorEdges.get();
        Color llllllllllllllllllIIlIIIlIIlIIll = llllllllllllllllllIIlIIIlIIlIlIl.separatorCenter.get();
        double llllllllllllllllllIIlIIIlIIlIIlI = llllllllllllllllllIIlIIIlIIlIlIl.scale(1.0);
        double llllllllllllllllllIIlIIIlIIlIIIl = Math.round(llllllllllllllllllIIlIIIlIIllIlI.width / 2.0);
        llllllllllllllllllIIlIIIlIIllIIl.quad(llllllllllllllllllIIlIIIlIIllIlI.x + llllllllllllllllllIIlIIIlIIlIIIl, llllllllllllllllllIIlIIIlIIllIlI.y, llllllllllllllllllIIlIIIlIIlIIlI, llllllllllllllllllIIlIIIlIIllIlI.height / 2.0, llllllllllllllllllIIlIIIlIIlIlII, llllllllllllllllllIIlIIIlIIlIlII, llllllllllllllllllIIlIIIlIIlIIll, llllllllllllllllllIIlIIIlIIlIIll);
        llllllllllllllllllIIlIIIlIIllIIl.quad(llllllllllllllllllIIlIIIlIIllIlI.x + llllllllllllllllllIIlIIIlIIlIIIl, llllllllllllllllllIIlIIIlIIllIlI.y + llllllllllllllllllIIlIIIlIIllIlI.height / 2.0, llllllllllllllllllIIlIIIlIIlIIlI, llllllllllllllllllIIlIIIlIIllIlI.height / 2.0, llllllllllllllllllIIlIIIlIIlIIll, llllllllllllllllllIIlIIIlIIlIIll, llllllllllllllllllIIlIIIlIIlIlII, llllllllllllllllllIIlIIIlIIlIlII);
    }
}

