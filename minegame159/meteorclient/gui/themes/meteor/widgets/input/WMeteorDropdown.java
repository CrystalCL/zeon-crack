/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets.input;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.utils.render.color.SettingColor;

public class WMeteorDropdown<T>
extends WDropdown<T>
implements MeteorWidget {
    @Override
    protected WDropdown.WDropdownValue createValueWidget() {
        WMeteorDropdown lllllllllllllllllllllIllIIlIllll;
        return lllllllllllllllllllllIllIIlIllll.new WValue();
    }

    @Override
    protected void onRender(GuiRenderer lllllllllllllllllllllIllIIlIIllI, double lllllllllllllllllllllIllIIlIIlIl, double lllllllllllllllllllllIllIIlIIlII, double lllllllllllllllllllllIllIIlIIIll) {
        WMeteorDropdown lllllllllllllllllllllIllIIlIIlll;
        MeteorGuiTheme lllllllllllllllllllllIllIIlIIIlI = lllllllllllllllllllllIllIIlIIlll.theme();
        double lllllllllllllllllllllIllIIlIIIIl = lllllllllllllllllllllIllIIlIIlll.pad();
        double lllllllllllllllllllllIllIIlIIIII = lllllllllllllllllllllIllIIlIIIlI.textHeight();
        lllllllllllllllllllllIllIIlIIlll.renderBackground(lllllllllllllllllllllIllIIlIIllI, lllllllllllllllllllllIllIIlIIlll, lllllllllllllllllllllIllIIlIIlll.pressed, lllllllllllllllllllllIllIIlIIlll.mouseOver);
        String lllllllllllllllllllllIllIIIlllll = lllllllllllllllllllllIllIIlIIlll.get().toString();
        double lllllllllllllllllllllIllIIIllllI = lllllllllllllllllllllIllIIlIIIlI.textWidth(lllllllllllllllllllllIllIIIlllll);
        lllllllllllllllllllllIllIIlIIllI.text(lllllllllllllllllllllIllIIIlllll, lllllllllllllllllllllIllIIlIIlll.x + lllllllllllllllllllllIllIIlIIIIl + lllllllllllllllllllllIllIIlIIlll.maxValueWidth / 2.0 - lllllllllllllllllllllIllIIIllllI / 2.0, lllllllllllllllllllllIllIIlIIlll.y + lllllllllllllllllllllIllIIlIIIIl, lllllllllllllllllllllIllIIlIIIlI.textColor.get(), false);
        lllllllllllllllllllllIllIIlIIllI.rotatedQuad(lllllllllllllllllllllIllIIlIIlll.x + lllllllllllllllllllllIllIIlIIIIl + lllllllllllllllllllllIllIIlIIlll.maxValueWidth + lllllllllllllllllllllIllIIlIIIIl, lllllllllllllllllllllIllIIlIIlll.y + lllllllllllllllllllllIllIIlIIIIl, lllllllllllllllllllllIllIIlIIIII, lllllllllllllllllllllIllIIlIIIII, 0.0, GuiRenderer.TRIANGLE, lllllllllllllllllllllIllIIlIIIlI.textColor.get());
    }

    public WMeteorDropdown(T[] lllllllllllllllllllllIllIIllIlII, T lllllllllllllllllllllIllIIllIIll) {
        super(lllllllllllllllllllllIllIIllIlII, lllllllllllllllllllllIllIIllIIll);
        WMeteorDropdown lllllllllllllllllllllIllIIlllIII;
    }

    @Override
    protected WDropdown.WDropdownRoot createRootWidget() {
        return new WRoot();
    }

    private static class WRoot
    extends WDropdown.WDropdownRoot
    implements MeteorWidget {
        private WRoot() {
            WRoot lllllllllllllllllIIIllIIIIlIlllI;
        }

        @Override
        protected void onRender(GuiRenderer lllllllllllllllllIIIllIIIIlIIlll, double lllllllllllllllllIIIllIIIIlIIllI, double lllllllllllllllllIIIllIIIIlIIlIl, double lllllllllllllllllIIIllIIIIlIIlII) {
            WRoot lllllllllllllllllIIIllIIIIlIlIII;
            MeteorGuiTheme lllllllllllllllllIIIllIIIIlIIIll = lllllllllllllllllIIIllIIIIlIlIII.theme();
            double lllllllllllllllllIIIllIIIIlIIIlI = lllllllllllllllllIIIllIIIIlIIIll.scale(2.0);
            SettingColor lllllllllllllllllIIIllIIIIlIIIIl = lllllllllllllllllIIIllIIIIlIIIll.outlineColor.get();
            lllllllllllllllllIIIllIIIIlIIlll.quad(lllllllllllllllllIIIllIIIIlIlIII.x, lllllllllllllllllIIIllIIIIlIlIII.y + lllllllllllllllllIIIllIIIIlIlIII.height - lllllllllllllllllIIIllIIIIlIIIlI, lllllllllllllllllIIIllIIIIlIlIII.width, lllllllllllllllllIIIllIIIIlIIIlI, lllllllllllllllllIIIllIIIIlIIIIl);
            lllllllllllllllllIIIllIIIIlIIlll.quad(lllllllllllllllllIIIllIIIIlIlIII.x, lllllllllllllllllIIIllIIIIlIlIII.y, lllllllllllllllllIIIllIIIIlIIIlI, lllllllllllllllllIIIllIIIIlIlIII.height - lllllllllllllllllIIIllIIIIlIIIlI, lllllllllllllllllIIIllIIIIlIIIIl);
            lllllllllllllllllIIIllIIIIlIIlll.quad(lllllllllllllllllIIIllIIIIlIlIII.x + lllllllllllllllllIIIllIIIIlIlIII.width - lllllllllllllllllIIIllIIIIlIIIlI, lllllllllllllllllIIIllIIIIlIlIII.y, lllllllllllllllllIIIllIIIIlIIIlI, lllllllllllllllllIIIllIIIIlIlIII.height - lllllllllllllllllIIIllIIIIlIIIlI, lllllllllllllllllIIIllIIIIlIIIIl);
        }
    }

    private class WValue
    extends WDropdown.WDropdownValue
    implements MeteorWidget {
        @Override
        protected void onCalculateSize() {
            WValue lllllllllllllllllIllIIIlIlIlIIll;
            double lllllllllllllllllIllIIIlIlIlIlII = lllllllllllllllllIllIIIlIlIlIIll.pad();
            lllllllllllllllllIllIIIlIlIlIIll.width = lllllllllllllllllIllIIIlIlIlIlII + lllllllllllllllllIllIIIlIlIlIIll.theme.textWidth(lllllllllllllllllIllIIIlIlIlIIll.value.toString()) + lllllllllllllllllIllIIIlIlIlIlII;
            lllllllllllllllllIllIIIlIlIlIIll.height = lllllllllllllllllIllIIIlIlIlIlII + lllllllllllllllllIllIIIlIlIlIIll.theme.textHeight() + lllllllllllllllllIllIIIlIlIlIlII;
        }

        private WValue() {
            WValue lllllllllllllllllIllIIIlIlIllIlI;
        }

        @Override
        protected void onRender(GuiRenderer lllllllllllllllllIllIIIlIlIIlIlI, double lllllllllllllllllIllIIIlIlIIlIIl, double lllllllllllllllllIllIIIlIlIIlIII, double lllllllllllllllllIllIIIlIlIIIlll) {
            WValue lllllllllllllllllIllIIIlIlIIlIll;
            MeteorGuiTheme lllllllllllllllllIllIIIlIlIIIllI = lllllllllllllllllIllIIIlIlIIlIll.theme();
            SettingColor lllllllllllllllllIllIIIlIlIIIlIl = lllllllllllllllllIllIIIlIlIIIllI.backgroundColor.get(lllllllllllllllllIllIIIlIlIIlIll.pressed, lllllllllllllllllIllIIIlIlIIlIll.mouseOver, true);
            int lllllllllllllllllIllIIIlIlIIIlII = lllllllllllllllllIllIIIlIlIIIlIl.a;
            lllllllllllllllllIllIIIlIlIIIlIl.a += lllllllllllllllllIllIIIlIlIIIlIl.a / 2;
            lllllllllllllllllIllIIIlIlIIIlIl.validate();
            lllllllllllllllllIllIIIlIlIIlIlI.quad(lllllllllllllllllIllIIIlIlIIlIll, lllllllllllllllllIllIIIlIlIIIlIl);
            lllllllllllllllllIllIIIlIlIIIlIl.a = lllllllllllllllllIllIIIlIlIIIlII;
            String lllllllllllllllllIllIIIlIlIIIIll = lllllllllllllllllIllIIIlIlIIlIll.value.toString();
            lllllllllllllllllIllIIIlIlIIlIlI.text(lllllllllllllllllIllIIIlIlIIIIll, lllllllllllllllllIllIIIlIlIIlIll.x + lllllllllllllllllIllIIIlIlIIlIll.width / 2.0 - lllllllllllllllllIllIIIlIlIIIllI.textWidth(lllllllllllllllllIllIIIlIlIIIIll) / 2.0, lllllllllllllllllIllIIIlIlIIlIll.y + lllllllllllllllllIllIIIlIlIIlIll.pad(), lllllllllllllllllIllIIIlIlIIIllI.textColor.get(), false);
        }
    }
}

