/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.containers.WWindow;

public class WMeteorWindow
extends WWindow
implements MeteorWidget {
    public WMeteorWindow(String string) {
        super(string);
    }

    @Override
    protected WWindow.WHeader header() {
        return new WMeteorHeader(this, null);
    }

    @Override
    protected void onRender(GuiRenderer guiRenderer, double d, double d2, double d3) {
        if (this.expanded || this.animProgress > 0.0) {
            guiRenderer.quad(this.x, this.y + this.header.height, this.width, this.height - this.header.height, this.theme().backgroundColor.get());
        }
    }

    private class WMeteorHeader
    extends WWindow.WHeader {
        final WMeteorWindow this$0;

        private WMeteorHeader(WMeteorWindow wMeteorWindow) {
            this.this$0 = wMeteorWindow;
            super(wMeteorWindow);
        }

        WMeteorHeader(WMeteorWindow wMeteorWindow, 1 var2_2) {
            this(wMeteorWindow);
        }

        @Override
        protected void onRender(GuiRenderer guiRenderer, double d, double d2, double d3) {
            guiRenderer.quad(this, this.this$0.theme().accentColor.get());
        }
    }
}

