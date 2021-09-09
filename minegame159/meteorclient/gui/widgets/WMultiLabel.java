/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets;

import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.gui.widgets.WLabel;

public abstract class WMultiLabel
extends WLabel {
    protected /* synthetic */ double maxWidth;
    protected /* synthetic */ List<String> lines;

    @Override
    public void set(String lllIllIIIIIII) {
        WMultiLabel lllIllIIIIIIl;
        if (!lllIllIIIIIII.equals(lllIllIIIIIIl.text)) {
            lllIllIIIIIIl.invalidate();
        }
        lllIllIIIIIIl.text = lllIllIIIIIII;
    }

    public WMultiLabel(String lllIllIlIlIll, boolean lllIllIlIlIlI, double lllIllIlIIlIl) {
        super(lllIllIlIlIll, lllIllIlIlIlI);
        WMultiLabel lllIllIlIllII;
        lllIllIlIllII.lines = new ArrayList<String>(2);
        lllIllIlIllII.maxWidth = lllIllIlIIlIl;
    }

    @Override
    protected void onCalculateSize() {
        WMultiLabel lllIllIIlIllI;
        lllIllIIlIllI.lines.clear();
        String[] lllIllIIlIlIl = lllIllIIlIllI.text.split(" ");
        StringBuilder lllIllIIlIlII = new StringBuilder();
        double lllIllIIlIIll = lllIllIIlIllI.theme.textWidth(" ", 1, lllIllIIlIllI.title);
        double lllIllIIlIIlI = lllIllIIlIllI.theme.scale(lllIllIIlIllI.maxWidth);
        double lllIllIIlIIIl = 0.0;
        double lllIllIIlIIII = 0.0;
        int lllIllIIIllll = 0;
        for (int lllIllIIlIlll = 0; lllIllIIlIlll < lllIllIIlIlIl.length; ++lllIllIIlIlll) {
            double lllIllIIllIIl;
            double lllIllIIllIII = lllIllIIllIIl = lllIllIIlIllI.theme.textWidth(lllIllIIlIlIl[lllIllIIlIlll], lllIllIIlIlIl[lllIllIIlIlll].length(), lllIllIIlIllI.title);
            if (lllIllIIIllll > 0) {
                lllIllIIllIII += lllIllIIlIIll;
            }
            if (lllIllIIlIIIl + lllIllIIllIII > lllIllIIlIIlI) {
                lllIllIIlIllI.lines.add(String.valueOf(lllIllIIlIlII));
                lllIllIIlIlII.setLength(0);
                lllIllIIlIIIl = 0.0;
                lllIllIIIllll = 0;
                --lllIllIIlIlll;
                continue;
            }
            if (lllIllIIIllll > 0) {
                lllIllIIlIlII.append(' ');
                lllIllIIlIIIl += lllIllIIlIIll;
            }
            lllIllIIlIlII.append(lllIllIIlIlIl[lllIllIIlIlll]);
            lllIllIIlIIII = Math.max(lllIllIIlIIII, lllIllIIlIIIl += lllIllIIllIIl);
            ++lllIllIIIllll;
        }
        if (lllIllIIlIlII.length() > 0) {
            lllIllIIlIllI.lines.add(String.valueOf(lllIllIIlIlII));
        }
        lllIllIIlIllI.width = lllIllIIlIIII;
        lllIllIIlIllI.height = lllIllIIlIllI.theme.textHeight(lllIllIIlIllI.title) * (double)lllIllIIlIllI.lines.size();
    }
}

