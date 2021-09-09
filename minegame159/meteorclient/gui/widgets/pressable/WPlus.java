/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.pressable;

import minegame159.meteorclient.gui.widgets.pressable.WPressable;

public abstract class WPlus
extends WPressable {
    @Override
    protected void onCalculateSize() {
        WPlus lllllllllllllllllIlIlIllllIIIIll;
        double lllllllllllllllllIlIlIllllIIIlIl = lllllllllllllllllIlIlIllllIIIIll.pad();
        double lllllllllllllllllIlIlIllllIIIlII = lllllllllllllllllIlIlIllllIIIIll.theme.textHeight();
        lllllllllllllllllIlIlIllllIIIIll.width = lllllllllllllllllIlIlIllllIIIlIl + lllllllllllllllllIlIlIllllIIIlII + lllllllllllllllllIlIlIllllIIIlIl;
        lllllllllllllllllIlIlIllllIIIIll.height = lllllllllllllllllIlIlIllllIIIlIl + lllllllllllllllllIlIlIllllIIIlII + lllllllllllllllllIlIlIllllIIIlIl;
    }

    public WPlus() {
        WPlus lllllllllllllllllIlIlIllllIIlIll;
    }
}

