/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.utils.render.color.Color;

public abstract class WQuad
extends WWidget {
    public /* synthetic */ Color color;

    @Override
    protected void onCalculateSize() {
        WQuad lllllllllllllllllIIIllIIIIIIlIll;
        double lllllllllllllllllIIIllIIIIIIllII;
        lllllllllllllllllIIIllIIIIIIlIll.width = lllllllllllllllllIIIllIIIIIIllII = lllllllllllllllllIIIllIIIIIIlIll.theme.scale(32.0);
        lllllllllllllllllIIIllIIIIIIlIll.height = lllllllllllllllllIIIllIIIIIIllII;
    }

    public WQuad(Color lllllllllllllllllIIIllIIIIIlIIlI) {
        WQuad lllllllllllllllllIIIllIIIIIlIIll;
        lllllllllllllllllIIIllIIIIIlIIll.color = lllllllllllllllllIIIllIIIIIlIIlI;
    }
}

