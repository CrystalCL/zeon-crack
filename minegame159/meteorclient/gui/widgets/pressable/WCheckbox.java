/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.pressable;

import minegame159.meteorclient.gui.widgets.pressable.WPressable;

public abstract class WCheckbox
extends WPressable {
    public /* synthetic */ boolean checked;

    @Override
    protected void onCalculateSize() {
        WCheckbox lIIlllIllllIlII;
        double lIIlllIllllIllI = lIIlllIllllIlII.pad();
        double lIIlllIllllIlIl = lIIlllIllllIlII.theme.textHeight();
        lIIlllIllllIlII.width = lIIlllIllllIllI + lIIlllIllllIlIl + lIIlllIllllIllI;
        lIIlllIllllIlII.height = lIIlllIllllIllI + lIIlllIllllIlIl + lIIlllIllllIllI;
    }

    @Override
    protected void onPressed(int lIIlllIlllIllll) {
        WCheckbox lIIlllIllllIIII;
        lIIlllIllllIIII.checked = !lIIlllIllllIIII.checked;
    }

    public WCheckbox(boolean lIIlllIllllllIl) {
        WCheckbox lIIlllIllllllII;
        lIIlllIllllllII.checked = lIIlllIllllllIl;
    }
}

