/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.containers.WView;

public class WMeteorView
extends WView
implements MeteorWidget {
    public WMeteorView() {
        WMeteorView lIlIIIIlIIIlIll;
    }

    @Override
    protected void onRender(GuiRenderer lIlIIIIlIIIIIlI, double lIlIIIIlIIIIllI, double lIlIIIIlIIIIlIl, double lIlIIIIlIIIIlII) {
        WMeteorView lIlIIIIlIIIlIII;
        if (lIlIIIIlIIIlIII.canScroll && lIlIIIIlIIIlIII.hasScrollBar) {
            lIlIIIIlIIIIIlI.quad(lIlIIIIlIIIlIII.handleX(), lIlIIIIlIIIlIII.handleY(), lIlIIIIlIIIlIII.handleWidth(), lIlIIIIlIIIlIII.handleHeight(), lIlIIIIlIIIlIII.theme().scrollbarColor.get(lIlIIIIlIIIlIII.handlePressed, lIlIIIIlIIIlIII.handleMouseOver));
        }
    }
}

