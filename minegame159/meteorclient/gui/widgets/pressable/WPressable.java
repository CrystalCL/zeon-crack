/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.pressable;

import minegame159.meteorclient.gui.widgets.WWidget;

public abstract class WPressable
extends WWidget {
    public /* synthetic */ Runnable action;
    protected /* synthetic */ boolean pressed;

    protected void onPressed(int lllllllllllllllllIlIlIlIIlllIlIl) {
    }

    @Override
    public boolean onMouseReleased(double lllllllllllllllllIlIlIlIIllllIll, double lllllllllllllllllIlIlIlIIllllIlI, int lllllllllllllllllIlIlIlIIlllIlll) {
        WPressable lllllllllllllllllIlIlIlIIlllllII;
        if (lllllllllllllllllIlIlIlIIlllllII.pressed) {
            lllllllllllllllllIlIlIlIIlllllII.onPressed(lllllllllllllllllIlIlIlIIlllIlll);
            if (lllllllllllllllllIlIlIlIIlllllII.action != null) {
                lllllllllllllllllIlIlIlIIlllllII.action.run();
            }
            lllllllllllllllllIlIlIlIIlllllII.pressed = false;
        }
        return false;
    }

    @Override
    public boolean onMouseClicked(double lllllllllllllllllIlIlIlIlIIIIlIl, double lllllllllllllllllIlIlIlIlIIIIlII, int lllllllllllllllllIlIlIlIlIIIIIII, boolean lllllllllllllllllIlIlIlIIlllllll) {
        WPressable lllllllllllllllllIlIlIlIlIIIIIIl;
        if (lllllllllllllllllIlIlIlIlIIIIIIl.mouseOver && (lllllllllllllllllIlIlIlIlIIIIIII == 0 || lllllllllllllllllIlIlIlIlIIIIIII == 1) && !lllllllllllllllllIlIlIlIIlllllll) {
            lllllllllllllllllIlIlIlIlIIIIIIl.pressed = true;
        }
        return lllllllllllllllllIlIlIlIlIIIIIIl.pressed;
    }

    public WPressable() {
        WPressable lllllllllllllllllIlIlIlIlIIIlIlI;
    }
}

