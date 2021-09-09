/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.utils.render.color.Color;

public abstract class WLabel
extends WWidget {
    public /* synthetic */ Color color;
    protected /* synthetic */ String text;
    protected /* synthetic */ boolean title;

    public void set(String lIIlIIIIIIlIIll) {
        WLabel lIIlIIIIIIlIlII;
        if ((double)Math.round(lIIlIIIIIIlIlII.theme.textWidth(lIIlIIIIIIlIIll, lIIlIIIIIIlIIll.length(), lIIlIIIIIIlIlII.title)) != lIIlIIIIIIlIlII.width) {
            lIIlIIIIIIlIlII.invalidate();
        }
        lIIlIIIIIIlIlII.text = lIIlIIIIIIlIIll;
    }

    public String get() {
        WLabel lIIlIIIIIIlIIII;
        return lIIlIIIIIIlIIII.text;
    }

    public WLabel(String lIIlIIIIIlIIIII, boolean lIIlIIIIIIlllII) {
        WLabel lIIlIIIIIlIIIIl;
        lIIlIIIIIlIIIIl.text = lIIlIIIIIlIIIII;
        lIIlIIIIIlIIIIl.title = lIIlIIIIIIlllII;
    }

    @Override
    protected void onCalculateSize() {
        WLabel lIIlIIIIIIllIlI;
        lIIlIIIIIIllIlI.width = lIIlIIIIIIllIlI.theme.textWidth(lIIlIIIIIIllIlI.text, lIIlIIIIIIllIlI.text.length(), lIIlIIIIIIllIlI.title);
        lIIlIIIIIIllIlI.height = lIIlIIIIIIllIlI.theme.textHeight(lIIlIIIIIIllIlI.title);
    }
}

