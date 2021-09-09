/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets.input;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.utils.CharFilter;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.utils.Utils;

public class WMeteorTextBox
extends WTextBox
implements MeteorWidget {
    private /* synthetic */ double cursorTimer;
    private /* synthetic */ double animProgress;
    private /* synthetic */ boolean cursorVisible;

    @Override
    protected void onCursorChanged() {
        lIlIlIIlIllIII.cursorVisible = true;
        lIlIlIIlIllIII.cursorTimer = 0.0;
    }

    public WMeteorTextBox(String lIlIlIIlIllllI, CharFilter lIlIlIIlIllIlI) {
        super(lIlIlIIlIllllI, lIlIlIIlIllIlI);
        WMeteorTextBox lIlIlIIlIlllll;
    }

    @Override
    protected void onRender(GuiRenderer lIlIlIIlIlIIlI, double lIlIlIIlIlIIIl, double lIlIlIIlIlIIII, double lIlIlIIlIIllII) {
        WMeteorTextBox lIlIlIIlIIlllI;
        if (lIlIlIIlIIlllI.cursorTimer >= 1.0) {
            lIlIlIIlIIlllI.cursorVisible = !lIlIlIIlIIlllI.cursorVisible;
            lIlIlIIlIIlllI.cursorTimer = 0.0;
        } else {
            lIlIlIIlIIlllI.cursorTimer += lIlIlIIlIIllII * 1.75;
        }
        lIlIlIIlIIlllI.renderBackground(lIlIlIIlIlIIlI, lIlIlIIlIIlllI, false, false);
        lIlIlIIlIIlllI.renderTextAndCursor(lIlIlIIlIlIIlI, lIlIlIIlIIllII);
    }

    private void renderTextAndCursor(GuiRenderer lIlIlIIIlllllI, double lIlIlIIIllllIl) {
        WMeteorTextBox lIlIlIIlIIIlIl;
        MeteorGuiTheme lIlIlIIlIIIIlI = lIlIlIIlIIIlIl.theme();
        double lIlIlIIlIIIIIl = lIlIlIIlIIIlIl.pad();
        double lIlIlIIlIIIIII = lIlIlIIlIIIlIl.getOverflowWidthForRender();
        if (!lIlIlIIlIIIlIl.text.isEmpty()) {
            lIlIlIIIlllllI.scissorStart(lIlIlIIlIIIlIl.x + lIlIlIIlIIIIIl, lIlIlIIlIIIlIl.y + lIlIlIIlIIIIIl, lIlIlIIlIIIlIl.width - lIlIlIIlIIIIIl * 2.0, lIlIlIIlIIIlIl.height - lIlIlIIlIIIIIl * 2.0);
            lIlIlIIIlllllI.text(lIlIlIIlIIIlIl.text, lIlIlIIlIIIlIl.x + lIlIlIIlIIIIIl - lIlIlIIlIIIIII, lIlIlIIlIIIlIl.y + lIlIlIIlIIIIIl, lIlIlIIlIIIIlI.textColor.get(), false);
            lIlIlIIIlllllI.scissorEnd();
        }
        lIlIlIIlIIIlIl.animProgress += lIlIlIIIllllIl * 10.0 * (double)(lIlIlIIlIIIlIl.focused && lIlIlIIlIIIlIl.cursorVisible ? 1 : -1);
        lIlIlIIlIIIlIl.animProgress = Utils.clamp(lIlIlIIlIIIlIl.animProgress, 0.0, 1.0);
        if (lIlIlIIlIIIlIl.focused && lIlIlIIlIIIlIl.cursorVisible || lIlIlIIlIIIlIl.animProgress > 0.0) {
            lIlIlIIIlllllI.setAlpha(lIlIlIIlIIIlIl.animProgress);
            lIlIlIIIlllllI.quad(lIlIlIIlIIIlIl.x + lIlIlIIlIIIIIl + lIlIlIIlIIIlIl.getCursorTextWidth() - lIlIlIIlIIIIII, lIlIlIIlIIIlIl.y + lIlIlIIlIIIIIl, lIlIlIIlIIIIlI.scale(1.0), lIlIlIIlIIIIlI.textHeight(), lIlIlIIlIIIIlI.textColor.get());
            lIlIlIIIlllllI.setAlpha(1.0);
        }
    }
}

