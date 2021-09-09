/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.input;

import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.utils.Utils;

public abstract class WSlider
extends WWidget {
    public /* synthetic */ Runnable actionOnRelease;
    protected /* synthetic */ boolean handleMouseOver;
    protected /* synthetic */ boolean dragging;
    protected /* synthetic */ double valueAtDragStart;
    protected /* synthetic */ double min;
    protected /* synthetic */ double max;
    protected /* synthetic */ double value;
    public /* synthetic */ Runnable action;

    public double get() {
        WSlider lIlIlIIIlIllllI;
        return lIlIlIIIlIllllI.value;
    }

    @Override
    protected void onCalculateSize() {
        WSlider lIlIlIIlIIlllII;
        double lIlIlIIlIIllIll;
        lIlIlIIlIIlllII.width = lIlIlIIlIIllIll = lIlIlIIlIIlllII.handleSize();
        lIlIlIIlIIlllII.height = lIlIlIIlIIllIll;
    }

    public void set(double lIlIlIIIllIIIII) {
        WSlider lIlIlIIIllIIIIl;
        lIlIlIIIllIIIIl.value = Utils.clamp(lIlIlIIIllIIIII, lIlIlIIIllIIIIl.min, lIlIlIIIllIIIIl.max);
    }

    protected double handleSize() {
        WSlider lIlIlIIlIlIIIII;
        return lIlIlIIlIlIIIII.theme.textHeight();
    }

    @Override
    public boolean onMouseClicked(double lIlIlIIlIIIlIll, double lIlIlIIlIIIllll, int lIlIlIIlIIIlllI, boolean lIlIlIIlIIIllIl) {
        WSlider lIlIlIIlIIlIIIl;
        if (lIlIlIIlIIlIIIl.mouseOver && !lIlIlIIlIIIllIl) {
            lIlIlIIlIIlIIIl.valueAtDragStart = lIlIlIIlIIlIIIl.value;
            double lIlIlIIlIIlIIll = lIlIlIIlIIlIIIl.handleSize();
            double lIlIlIIlIIlIIlI = lIlIlIIlIIIlIll - (lIlIlIIlIIlIIIl.x + lIlIlIIlIIlIIll / 2.0);
            lIlIlIIlIIlIIIl.set(lIlIlIIlIIlIIlI / (lIlIlIIlIIlIIIl.width - lIlIlIIlIIlIIll) * (lIlIlIIlIIlIIIl.max - lIlIlIIlIIlIIIl.min) + lIlIlIIlIIlIIIl.min);
            if (lIlIlIIlIIlIIIl.action != null) {
                lIlIlIIlIIlIIIl.action.run();
            }
            lIlIlIIlIIlIIIl.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void onMouseMoved(double lIlIlIIIlllllIl, double lIlIlIIIlllllII, double lIlIlIIIllllIll, double lIlIlIIIllllIlI) {
        WSlider lIlIlIIIlllIlII;
        double lIlIlIIIllllIIl = lIlIlIIIlllIlII.valueWidth();
        double lIlIlIIIllllIII = lIlIlIIIlllIlII.handleSize();
        double lIlIlIIIlllIlll = lIlIlIIIllllIII / 2.0;
        double lIlIlIIIlllIllI = lIlIlIIIlllIlII.x + lIlIlIIIlllIlll + lIlIlIIIllllIIl - lIlIlIIIlllIlII.height / 2.0;
        lIlIlIIIlllIlII.handleMouseOver = lIlIlIIIlllllIl >= lIlIlIIIlllIllI && lIlIlIIIlllllIl <= lIlIlIIIlllIllI + lIlIlIIIlllIlII.height && lIlIlIIIlllllII >= lIlIlIIIlllIlII.y && lIlIlIIIlllllII <= lIlIlIIIlllIlII.y + lIlIlIIIlllIlII.height;
        boolean lIlIlIIIlllIlIl = lIlIlIIIlllllIl >= lIlIlIIIlllIlII.x + lIlIlIIIlllIlll && lIlIlIIIlllllIl <= lIlIlIIIlllIlII.x + lIlIlIIIlllIlll + lIlIlIIIlllIlII.width - lIlIlIIIllllIII;
        boolean bl = lIlIlIIIlllIlII.mouseOver = lIlIlIIIlllIlIl && lIlIlIIIlllllII >= lIlIlIIIlllIlII.y && lIlIlIIIlllllII <= lIlIlIIIlllIlII.y + lIlIlIIIlllIlII.height;
        if (lIlIlIIIlllIlII.dragging) {
            if (lIlIlIIIlllIlIl) {
                lIlIlIIIllllIIl += lIlIlIIIlllllIl - lIlIlIIIllllIll;
                lIlIlIIIllllIIl = Utils.clamp(lIlIlIIIllllIIl, 0.0, lIlIlIIIlllIlII.width - lIlIlIIIllllIII);
                lIlIlIIIlllIlII.set(lIlIlIIIllllIIl / (lIlIlIIIlllIlII.width - lIlIlIIIllllIII) * (lIlIlIIIlllIlII.max - lIlIlIIIlllIlII.min) + lIlIlIIIlllIlII.min);
                if (lIlIlIIIlllIlII.action != null) {
                    lIlIlIIIlllIlII.action.run();
                }
            } else if (lIlIlIIIlllIlII.value > lIlIlIIIlllIlII.min && lIlIlIIIlllllIl < lIlIlIIIlllIlII.x + lIlIlIIIlllIlll) {
                lIlIlIIIlllIlII.value = lIlIlIIIlllIlII.min;
                if (lIlIlIIIlllIlII.action != null) {
                    lIlIlIIIlllIlII.action.run();
                }
            } else if (lIlIlIIIlllIlII.value < lIlIlIIIlllIlII.max && lIlIlIIIlllllIl > lIlIlIIIlllIlII.x + lIlIlIIIlllIlll + lIlIlIIIlllIlII.width - lIlIlIIIllllIII) {
                lIlIlIIIlllIlII.value = lIlIlIIIlllIlII.max;
                if (lIlIlIIIlllIlII.action != null) {
                    lIlIlIIIlllIlII.action.run();
                }
            }
        }
    }

    public WSlider(double lIlIlIIlIlIlIII, double lIlIlIIlIlIIIll, double lIlIlIIlIlIIIlI) {
        WSlider lIlIlIIlIlIlIIl;
        lIlIlIIlIlIlIIl.value = Utils.clamp(lIlIlIIlIlIlIII, lIlIlIIlIlIIIll, lIlIlIIlIlIIIlI);
        lIlIlIIlIlIlIIl.min = lIlIlIIlIlIIIll;
        lIlIlIIlIlIlIIl.max = lIlIlIIlIlIIIlI;
    }

    protected double valueWidth() {
        WSlider lIlIlIIIlIllIlI;
        double lIlIlIIIlIllIIl = (lIlIlIIIlIllIlI.value - lIlIlIIIlIllIlI.min) / (lIlIlIIIlIllIlI.max - lIlIlIIIlIllIlI.min);
        return lIlIlIIIlIllIIl * (lIlIlIIIlIllIlI.width - lIlIlIIIlIllIlI.handleSize());
    }

    @Override
    public boolean onMouseReleased(double lIlIlIIIllIlIIl, double lIlIlIIIllIlIII, int lIlIlIIIllIIlll) {
        WSlider lIlIlIIIllIIllI;
        if (lIlIlIIIllIIllI.dragging) {
            if (lIlIlIIIllIIllI.value != lIlIlIIIllIIllI.valueAtDragStart && lIlIlIIIllIIllI.actionOnRelease != null) {
                lIlIlIIIllIIllI.actionOnRelease.run();
            }
            lIlIlIIIllIIllI.dragging = false;
            return true;
        }
        return false;
    }
}

