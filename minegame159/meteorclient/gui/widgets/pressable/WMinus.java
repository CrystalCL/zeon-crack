/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.pressable;

import minegame159.meteorclient.gui.widgets.pressable.WPressable;

public abstract class WMinus
extends WPressable {
    public WMinus() {
        WMinus llIllIIllllIlI;
    }

    @Override
    protected void onCalculateSize() {
        WMinus llIllIIlllIIlI;
        double llIllIIlllIlII = llIllIIlllIIlI.pad();
        double llIllIIlllIIll = llIllIIlllIIlI.theme.textHeight();
        llIllIIlllIIlI.width = llIllIIlllIlII + llIllIIlllIIll + llIllIIlllIlII;
        llIllIIlllIIlI.height = llIllIIlllIlII + llIllIIlllIIll + llIllIIlllIlII;
    }
}

