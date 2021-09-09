/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.containers.WWindow;

public class WMeteorWindow
extends WWindow
implements MeteorWidget {
    public WMeteorWindow(String llllllIIlIIIlIl) {
        super(llllllIIlIIIlIl);
        WMeteorWindow llllllIIlIIlIII;
    }

    @Override
    protected WWindow.WHeader header() {
        WMeteorWindow llllllIIlIIIIll;
        return llllllIIlIIIIll.new WMeteorHeader();
    }

    @Override
    protected void onRender(GuiRenderer llllllIIIlllIIl, double llllllIIIllllIl, double llllllIIIllllII, double llllllIIIlllIll) {
        WMeteorWindow llllllIIIlllIlI;
        if (llllllIIIlllIlI.expanded || llllllIIIlllIlI.animProgress > 0.0) {
            llllllIIIlllIIl.quad(llllllIIIlllIlI.x, llllllIIIlllIlI.y + llllllIIIlllIlI.header.height, llllllIIIlllIlI.width, llllllIIIlllIlI.height - llllllIIIlllIlI.header.height, llllllIIIlllIlI.theme().backgroundColor.get());
        }
    }

    private class WMeteorHeader
    extends WWindow.WHeader {
        @Override
        protected void onRender(GuiRenderer llllllllllllllllIlIlllIllllIIlIl, double llllllllllllllllIlIlllIllllIlIIl, double llllllllllllllllIlIlllIllllIlIII, double llllllllllllllllIlIlllIllllIIlll) {
            WMeteorHeader llllllllllllllllIlIlllIllllIlIll;
            llllllllllllllllIlIlllIllllIIlIl.quad(llllllllllllllllIlIlllIllllIlIll, llllllllllllllllIlIlllIllllIlIll.WMeteorWindow.this.theme().accentColor.get());
        }

        private WMeteorHeader() {
            WMeteorHeader llllllllllllllllIlIlllIlllllIIII;
            super(llllllllllllllllIlIlllIlllllIIII.WMeteorWindow.this);
        }
    }
}

