/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.pressable.WTriangle;

public class WMeteorSection
extends WSection {
    @Override
    protected WSection.WHeader createHeader() {
        WMeteorSection lllllllllllllllllIllIlIllIllIIIl;
        return lllllllllllllllllIllIlIllIllIIIl.new WMeteorHeader(lllllllllllllllllIllIlIllIllIIIl.title);
    }

    public WMeteorSection(String lllllllllllllllllIllIlIllIllIllI, boolean lllllllllllllllllIllIlIllIlllIIl, WWidget lllllllllllllllllIllIlIllIllIlII) {
        super(lllllllllllllllllIllIlIllIllIllI, lllllllllllllllllIllIlIllIlllIIl, lllllllllllllllllIllIlIllIllIlII);
        WMeteorSection lllllllllllllllllIllIlIllIlllIll;
    }

    protected static class WHeaderTriangle
    extends WTriangle
    implements MeteorWidget {
        @Override
        protected void onRender(GuiRenderer lllllllllllllllllIIIIllIlIIlIllI, double lllllllllllllllllIIIIllIlIIlIlIl, double lllllllllllllllllIIIIllIlIIlIlII, double lllllllllllllllllIIIIllIlIIlIIll) {
            WHeaderTriangle lllllllllllllllllIIIIllIlIIlIIlI;
            lllllllllllllllllIIIIllIlIIlIllI.rotatedQuad(lllllllllllllllllIIIIllIlIIlIIlI.x, lllllllllllllllllIIIIllIlIIlIIlI.y, lllllllllllllllllIIIIllIlIIlIIlI.width, lllllllllllllllllIIIIllIlIIlIIlI.height, lllllllllllllllllIIIIllIlIIlIIlI.rotation, GuiRenderer.TRIANGLE, lllllllllllllllllIIIIllIlIIlIIlI.theme().textColor.get());
        }

        protected WHeaderTriangle() {
            WHeaderTriangle lllllllllllllllllIIIIllIlIIllIlI;
        }
    }

    protected class WMeteorHeader
    extends WSection.WHeader {
        private /* synthetic */ WTriangle triangle;

        @Override
        public void init() {
            WMeteorHeader lllllllllllllllllllIlIllIllIIlll;
            lllllllllllllllllllIlIllIllIIlll.add(lllllllllllllllllllIlIllIllIIlll.theme.horizontalSeparator(lllllllllllllllllllIlIllIllIIlll.title)).expandX();
            if (lllllllllllllllllllIlIllIllIIlll.WMeteorSection.this.headerWidget != null) {
                lllllllllllllllllllIlIllIllIIlll.add(lllllllllllllllllllIlIllIllIIlll.WMeteorSection.this.headerWidget);
            }
            lllllllllllllllllllIlIllIllIIlll.triangle = new WHeaderTriangle();
            lllllllllllllllllllIlIllIllIIlll.triangle.theme = lllllllllllllllllllIlIllIllIIlll.theme;
            lllllllllllllllllllIlIllIllIIlll.triangle.action = () -> lllllllllllllllllllIlIllIllIIlll.onClick();
            lllllllllllllllllllIlIllIllIIlll.add(lllllllllllllllllllIlIllIllIIlll.triangle);
        }

        public WMeteorHeader(String lllllllllllllllllllIlIllIllIlIIl) {
            WMeteorHeader lllllllllllllllllllIlIllIllIlllI;
            super(lllllllllllllllllllIlIllIllIlllI.WMeteorSection.this, lllllllllllllllllllIlIllIllIlIIl);
        }

        @Override
        protected void onRender(GuiRenderer lllllllllllllllllllIlIllIllIIIll, double lllllllllllllllllllIlIllIllIIIlI, double lllllllllllllllllllIlIllIllIIIIl, double lllllllllllllllllllIlIllIllIIIII) {
            WMeteorHeader lllllllllllllllllllIlIllIllIIlII;
            lllllllllllllllllllIlIllIllIIlII.triangle.rotation = (1.0 - lllllllllllllllllllIlIllIllIIlII.WMeteorSection.this.animProgress) * -90.0;
        }
    }
}

