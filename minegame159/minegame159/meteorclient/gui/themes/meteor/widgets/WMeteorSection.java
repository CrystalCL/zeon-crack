/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.pressable.WTriangle;

public class WMeteorSection
extends WSection {
    static double access$200(WMeteorSection wMeteorSection) {
        return wMeteorSection.animProgress;
    }

    static WWidget access$000(WMeteorSection wMeteorSection) {
        return wMeteorSection.headerWidget;
    }

    @Override
    protected WSection.WHeader createHeader() {
        return new WMeteorHeader(this, this.title);
    }

    static WWidget access$100(WMeteorSection wMeteorSection) {
        return wMeteorSection.headerWidget;
    }

    public WMeteorSection(String string, boolean bl, WWidget wWidget) {
        super(string, bl, wWidget);
    }

    protected static class WHeaderTriangle
    extends WTriangle
    implements MeteorWidget {
        protected WHeaderTriangle() {
        }

        @Override
        protected void onRender(GuiRenderer guiRenderer, double d, double d2, double d3) {
            guiRenderer.rotatedQuad(this.x, this.y, this.width, this.height, this.rotation, GuiRenderer.TRIANGLE, this.theme().textColor.get());
        }
    }

    protected class WMeteorHeader
    extends WSection.WHeader {
        private WTriangle triangle;
        final WMeteorSection this$0;

        @Override
        public void init() {
            this.add(this.theme.horizontalSeparator(this.title)).expandX();
            if (WMeteorSection.access$000(this.this$0) != null) {
                this.add(WMeteorSection.access$100(this.this$0));
            }
            this.triangle = new WHeaderTriangle();
            this.triangle.theme = this.theme;
            this.triangle.action = () -> WMeteorHeader.lambda$init$0(this);
            this.add(this.triangle);
        }

        private static void lambda$init$0(WMeteorHeader wMeteorHeader) {
            wMeteorHeader.onClick();
        }

        @Override
        protected void onRender(GuiRenderer guiRenderer, double d, double d2, double d3) {
            this.triangle.rotation = (1.0 - WMeteorSection.access$200(this.this$0)) * -90.0;
        }

        public WMeteorHeader(WMeteorSection wMeteorSection, String string) {
            this.this$0 = wMeteorSection;
            super(wMeteorSection, string);
        }
    }
}

