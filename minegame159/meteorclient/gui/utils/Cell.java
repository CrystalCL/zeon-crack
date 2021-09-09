/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.utils;

import minegame159.meteorclient.gui.utils.AlignmentX;
import minegame159.meteorclient.gui.utils.AlignmentY;
import minegame159.meteorclient.gui.widgets.WWidget;

public class Cell<T extends WWidget> {
    public /* synthetic */ boolean expandCellX;
    private /* synthetic */ double padTop;
    private /* synthetic */ double padBottom;
    private /* synthetic */ AlignmentY alignY;
    private /* synthetic */ double padRight;
    public /* synthetic */ double y;
    private /* synthetic */ AlignmentX alignX;
    private final /* synthetic */ T widget;
    private /* synthetic */ boolean expandWidgetY;
    private /* synthetic */ double padLeft;
    public /* synthetic */ double x;
    private /* synthetic */ boolean expandWidgetX;
    public /* synthetic */ double height;
    public /* synthetic */ double width;

    public Cell<T> padLeft(double lIIIIlIlIIlllll) {
        Cell lIIIIlIlIlIIIlI;
        lIIIIlIlIlIIIlI.padLeft = lIIIIlIlIIlllll;
        return lIIIIlIlIlIIIlI;
    }

    public Cell<T> expandCellX() {
        Cell lIIIIlIIllllIII;
        lIIIIlIIllllIII.expandCellX = true;
        return lIIIIlIIllllIII;
    }

    public double padRight() {
        Cell lIIIIlIlIIIlIII;
        return lIIIIlIlIIIlIII.s(lIIIIlIlIIIlIII.padRight);
    }

    public Cell<T> expandWidgetX() {
        Cell lIIIIlIIllllllI;
        lIIIIlIIllllllI.expandWidgetX = true;
        return lIIIIlIIllllllI;
    }

    public Cell<T> expandWidgetY() {
        Cell lIIIIlIIlllllII;
        lIIIIlIIlllllII.expandWidgetY = true;
        return lIIIIlIIlllllII;
    }

    public Cell<T> expandX() {
        Cell lIIIIlIIlllIlIl;
        lIIIIlIIlllIlIl.expandWidgetX = true;
        lIIIIlIIlllIlIl.expandCellX = true;
        return lIIIIlIIlllIlIl;
    }

    public Cell<T> padTop(double lIIIIlIlIllIIIl) {
        Cell lIIIIlIlIllIIlI;
        lIIIIlIlIllIIlI.padTop = lIIIIlIlIllIIIl;
        return lIIIIlIlIllIIlI;
    }

    public double padLeft() {
        Cell lIIIIlIlIIIIIlI;
        return lIIIIlIlIIIIIlI.s(lIIIIlIlIIIIIlI.padLeft);
    }

    public Cell<T> right() {
        Cell lIIIIlIllIIIIll;
        lIIIIlIllIIIIll.alignX = AlignmentX.Right;
        return lIIIIlIllIIIIll;
    }

    public T widget() {
        Cell lIIIIlIllIllIIl;
        return lIIIIlIllIllIIl.widget;
    }

    public Cell<T> pad(double lIIIIlIlIIIllll) {
        Cell lIIIIlIlIIlIIII;
        lIIIIlIlIIlIIII.padBottom = lIIIIlIlIIlIIII.padLeft = lIIIIlIlIIIllll;
        lIIIIlIlIIlIIII.padRight = lIIIIlIlIIlIIII.padLeft;
        lIIIIlIlIIlIIII.padTop = lIIIIlIlIIlIIII.padLeft;
        return lIIIIlIlIIlIIII;
    }

    public Cell<T> top() {
        Cell lIIIIlIlIllIlll;
        lIIIIlIlIllIlll.alignY = AlignmentY.Top;
        return lIIIIlIlIllIlll;
    }

    public Cell<T> centerY() {
        Cell lIIIIlIllIIIIII;
        lIIIIlIllIIIIII.alignY = AlignmentY.Center;
        return lIIIIlIllIIIIII;
    }

    public Cell<T> padBottom(double lIIIIlIlIlIIlll) {
        Cell lIIIIlIlIlIIllI;
        lIIIIlIlIlIIllI.padBottom = lIIIIlIlIlIIlll;
        return lIIIIlIlIlIIllI;
    }

    private double s(double lIIIIlIIllIlllI) {
        Cell lIIIIlIIllIllll;
        return ((WWidget)lIIIIlIIllIllll.widget).theme.scale(lIIIIlIIllIlllI);
    }

    public void move(double lIIIIlIllIlIIII, double lIIIIlIllIIllll) {
        Cell lIIIIlIllIlIlII;
        lIIIIlIllIlIlII.x += lIIIIlIllIlIIII;
        lIIIIlIllIlIlII.y += lIIIIlIllIIllll;
        ((WWidget)lIIIIlIllIlIlII.widget).move(lIIIIlIllIlIIII, lIIIIlIllIIllll);
    }

    public Cell<T> center() {
        Cell lIIIIlIlIlllIlI;
        lIIIIlIlIlllIlI.alignX = AlignmentX.Center;
        lIIIIlIlIlllIlI.alignY = AlignmentY.Center;
        return lIIIIlIlIlllIlI;
    }

    public void alignWidget() {
        Cell lIIIIlIIlllIIll;
        if (lIIIIlIIlllIIll.expandWidgetX) {
            ((WWidget)lIIIIlIIlllIIll.widget).x = lIIIIlIIlllIIll.x;
            ((WWidget)lIIIIlIIlllIIll.widget).width = lIIIIlIIlllIIll.width;
        } else {
            switch (lIIIIlIIlllIIll.alignX) {
                case Left: {
                    ((WWidget)lIIIIlIIlllIIll.widget).x = lIIIIlIIlllIIll.x;
                    break;
                }
                case Center: {
                    ((WWidget)lIIIIlIIlllIIll.widget).x = lIIIIlIIlllIIll.x + lIIIIlIIlllIIll.width / 2.0 - ((WWidget)lIIIIlIIlllIIll.widget).width / 2.0;
                    break;
                }
                case Right: {
                    ((WWidget)lIIIIlIIlllIIll.widget).x = lIIIIlIIlllIIll.x + lIIIIlIIlllIIll.width - ((WWidget)lIIIIlIIlllIIll.widget).width;
                }
            }
        }
        if (lIIIIlIIlllIIll.expandWidgetY) {
            ((WWidget)lIIIIlIIlllIIll.widget).y = lIIIIlIIlllIIll.y;
            ((WWidget)lIIIIlIIlllIIll.widget).height = lIIIIlIIlllIIll.height;
        } else {
            switch (lIIIIlIIlllIIll.alignY) {
                case Top: {
                    ((WWidget)lIIIIlIIlllIIll.widget).y = lIIIIlIIlllIIll.y;
                    break;
                }
                case Center: {
                    ((WWidget)lIIIIlIIlllIIll.widget).y = lIIIIlIIlllIIll.y + lIIIIlIIlllIIll.height / 2.0 - ((WWidget)lIIIIlIIlllIIll.widget).height / 2.0;
                    break;
                }
                case Bottom: {
                    ((WWidget)lIIIIlIIlllIIll.widget).y = lIIIIlIIlllIIll.y + lIIIIlIIlllIIll.height - ((WWidget)lIIIIlIIlllIIll.widget).height;
                }
            }
        }
    }

    public Cell<T> padVertical(double lIIIIlIlIIlIIll) {
        Cell lIIIIlIlIIlIllI;
        lIIIIlIlIIlIllI.padTop = lIIIIlIlIIlIllI.padBottom = lIIIIlIlIIlIIll;
        return lIIIIlIlIIlIllI;
    }

    public Cell<T> minWidth(double lIIIIlIllIIlIll) {
        Cell lIIIIlIllIIllII;
        ((WWidget)lIIIIlIllIIllII.widget).minWidth = lIIIIlIllIIlIll;
        return lIIIIlIllIIllII;
    }

    public double padTop() {
        Cell lIIIIlIlIIIlIlI;
        return lIIIIlIlIIIlIlI.s(lIIIIlIlIIIlIlI.padTop);
    }

    public Cell<T> padHorizontal(double lIIIIlIlIIllIll) {
        Cell lIIIIlIlIIllIlI;
        lIIIIlIlIIllIlI.padRight = lIIIIlIlIIllIlI.padLeft = lIIIIlIlIIllIll;
        return lIIIIlIlIIllIlI;
    }

    public Cell(T lIIIIlIllIlllIl) {
        Cell lIIIIlIllIllllI;
        lIIIIlIllIllllI.alignX = AlignmentX.Left;
        lIIIIlIllIllllI.alignY = AlignmentY.Top;
        lIIIIlIllIllllI.widget = lIIIIlIllIlllIl;
    }

    public Cell<T> centerX() {
        Cell lIIIIlIllIIIllI;
        lIIIIlIllIIIllI.alignX = AlignmentX.Center;
        return lIIIIlIllIIIllI;
    }

    public Cell<T> bottom() {
        Cell lIIIIlIlIllllIl;
        lIIIIlIlIllllIl.alignY = AlignmentY.Bottom;
        return lIIIIlIlIllllIl;
    }

    public double padBottom() {
        Cell lIIIIlIlIIIIlIl;
        return lIIIIlIlIIIIlIl.s(lIIIIlIlIIIIlIl.padBottom);
    }

    public Cell<T> padRight(double lIIIIlIlIlIllIl) {
        Cell lIIIIlIlIlIlllI;
        lIIIIlIlIlIlllI.padRight = lIIIIlIlIlIllIl;
        return lIIIIlIlIlIlllI;
    }
}

