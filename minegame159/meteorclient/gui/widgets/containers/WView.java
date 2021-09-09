/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.containers;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.utils.Utils;

public abstract class WView
extends WVerticalList {
    public /* synthetic */ boolean scrollOnlyWhenMouseOver;
    protected /* synthetic */ boolean canScroll;
    private /* synthetic */ double targetScroll;
    private /* synthetic */ boolean moveAfterPositionWidgets;
    private /* synthetic */ double scroll;
    protected /* synthetic */ boolean handleMouseOver;
    public /* synthetic */ boolean hasScrollBar;
    public /* synthetic */ double maxHeight;
    private /* synthetic */ double actualHeight;
    protected /* synthetic */ boolean handlePressed;

    @Override
    public boolean render(GuiRenderer lllllllllllllllllIlllIllIllIlIll, double lllllllllllllllllIlllIllIllIlIlI, double lllllllllllllllllIlllIllIllIIIll, double lllllllllllllllllIlllIllIllIIIlI) {
        WView lllllllllllllllllIlllIllIllIIllI;
        lllllllllllllllllIlllIllIllIIllI.updateScroll(lllllllllllllllllIlllIllIllIIIlI);
        if (lllllllllllllllllIlllIllIllIIllI.canScroll) {
            lllllllllllllllllIlllIllIllIlIll.scissorStart(lllllllllllllllllIlllIllIllIIllI.x, lllllllllllllllllIlllIllIllIIllI.y, lllllllllllllllllIlllIllIllIIllI.width, lllllllllllllllllIlllIllIllIIllI.height);
        }
        boolean lllllllllllllllllIlllIllIllIIlll = super.render(lllllllllllllllllIlllIllIllIlIll, lllllllllllllllllIlllIllIllIlIlI, lllllllllllllllllIlllIllIllIIIll, lllllllllllllllllIlllIllIllIIIlI);
        if (lllllllllllllllllIlllIllIllIIllI.canScroll) {
            lllllllllllllllllIlllIllIllIlIll.scissorEnd();
        }
        return lllllllllllllllllIlllIllIllIIlll;
    }

    @Override
    public void init() {
        WView lllllllllllllllllIlllIlllIlIllII;
        lllllllllllllllllIlllIlllIlIllII.maxHeight = (double)Utils.getWindowHeight() - lllllllllllllllllIlllIlllIlIllII.theme.scale(128.0);
    }

    @Override
    protected void onCalculateSize() {
        WView lllllllllllllllllIlllIlllIlIlIII;
        boolean lllllllllllllllllIlllIlllIlIIlll = lllllllllllllllllIlllIlllIlIlIII.canScroll;
        lllllllllllllllllIlllIlllIlIlIII.canScroll = false;
        lllllllllllllllllIlllIlllIlIlIII.widthRemove = 0.0;
        super.onCalculateSize();
        if (lllllllllllllllllIlllIlllIlIlIII.height > lllllllllllllllllIlllIlllIlIlIII.maxHeight) {
            lllllllllllllllllIlllIlllIlIlIII.actualHeight = lllllllllllllllllIlllIlllIlIlIII.height;
            lllllllllllllllllIlllIlllIlIlIII.height = lllllllllllllllllIlllIlllIlIlIII.maxHeight;
            lllllllllllllllllIlllIlllIlIlIII.canScroll = true;
            if (lllllllllllllllllIlllIlllIlIlIII.hasScrollBar) {
                lllllllllllllllllIlllIlllIlIlIII.widthRemove = lllllllllllllllllIlllIlllIlIlIII.handleWidth() * 2.0;
                lllllllllllllllllIlllIlllIlIlIII.width += lllllllllllllllllIlllIlllIlIlIII.widthRemove;
            }
            if (lllllllllllllllllIlllIlllIlIIlll) {
                lllllllllllllllllIlllIlllIlIlIII.moveAfterPositionWidgets = true;
            }
        } else {
            lllllllllllllllllIlllIlllIlIlIII.actualHeight = lllllllllllllllllIlllIlllIlIlIII.height;
            lllllllllllllllllIlllIlllIlIlIII.scroll = 0.0;
            lllllllllllllllllIlllIlllIlIlIII.targetScroll = 0.0;
        }
    }

    protected double handleX() {
        WView lllllllllllllllllIlllIllIlIIIIll;
        return lllllllllllllllllIlllIllIlIIIIll.x + lllllllllllllllllIlllIllIlIIIIll.width - lllllllllllllllllIlllIllIlIIIIll.handleWidth();
    }

    public WView() {
        WView lllllllllllllllllIlllIlllIlIllll;
        lllllllllllllllllIlllIlllIlIllll.maxHeight = Double.MAX_VALUE;
        lllllllllllllllllIlllIlllIlIllll.scrollOnlyWhenMouseOver = true;
        lllllllllllllllllIlllIlllIlIllll.hasScrollBar = true;
    }

    @Override
    protected void onCalculateWidgetPositions() {
        WView lllllllllllllllllIlllIlllIlIIIlI;
        super.onCalculateWidgetPositions();
        if (lllllllllllllllllIlllIlllIlIIIlI.moveAfterPositionWidgets) {
            lllllllllllllllllIlllIlllIlIIIlI.targetScroll = lllllllllllllllllIlllIlllIlIIIlI.scroll = Utils.clamp(lllllllllllllllllIlllIlllIlIIIlI.scroll, 0.0, lllllllllllllllllIlllIlllIlIIIlI.actualHeight - lllllllllllllllllIlllIlllIlIIIlI.height);
            lllllllllllllllllIlllIlllIlIIIlI.moveCells(0.0, -lllllllllllllllllIlllIlllIlIIIlI.scroll);
            lllllllllllllllllIlllIlllIlIIIlI.moveAfterPositionWidgets = false;
        }
    }

    @Override
    public void onMouseMoved(double lllllllllllllllllIlllIlllIIIIIll, double lllllllllllllllllIlllIlllIIIIIlI, double lllllllllllllllllIlllIlllIIIIIIl, double lllllllllllllllllIlllIllIlllllII) {
        WView lllllllllllllllllIlllIllIlllllll;
        lllllllllllllllllIlllIllIlllllll.handleMouseOver = false;
        if (lllllllllllllllllIlllIllIlllllll.canScroll && lllllllllllllllllIlllIllIlllllll.hasScrollBar) {
            double lllllllllllllllllIlllIlllIIIlIIl = lllllllllllllllllIlllIllIlllllll.handleX();
            double lllllllllllllllllIlllIlllIIIlIII = lllllllllllllllllIlllIllIlllllll.handleY();
            if (lllllllllllllllllIlllIlllIIIIIll >= lllllllllllllllllIlllIlllIIIlIIl && lllllllllllllllllIlllIlllIIIIIll <= lllllllllllllllllIlllIlllIIIlIIl + lllllllllllllllllIlllIllIlllllll.handleWidth() && lllllllllllllllllIlllIlllIIIIIlI >= lllllllllllllllllIlllIlllIIIlIII && lllllllllllllllllIlllIlllIIIIIlI <= lllllllllllllllllIlllIlllIIIlIII + lllllllllllllllllIlllIllIlllllll.handleHeight()) {
                lllllllllllllllllIlllIllIlllllll.handleMouseOver = true;
            }
        }
        if (lllllllllllllllllIlllIllIlllllll.handlePressed) {
            double lllllllllllllllllIlllIlllIIIIlll = lllllllllllllllllIlllIllIlllllll.scroll;
            double lllllllllllllllllIlllIlllIIIIllI = lllllllllllllllllIlllIlllIIIIIlI - lllllllllllllllllIlllIllIlllllII;
            lllllllllllllllllIlllIllIlllllll.scroll += (double)Math.round(lllllllllllllllllIlllIlllIIIIllI * ((lllllllllllllllllIlllIllIlllllll.actualHeight - lllllllllllllllllIlllIllIlllllll.handleHeight() / 2.0) / lllllllllllllllllIlllIllIlllllll.height));
            lllllllllllllllllIlllIllIlllllll.targetScroll = lllllllllllllllllIlllIllIlllllll.scroll = Utils.clamp(lllllllllllllllllIlllIllIlllllll.scroll, 0.0, lllllllllllllllllIlllIllIlllllll.actualHeight - lllllllllllllllllIlllIllIlllllll.height);
            double lllllllllllllllllIlllIlllIIIIlIl = lllllllllllllllllIlllIllIlllllll.scroll - lllllllllllllllllIlllIlllIIIIlll;
            if (lllllllllllllllllIlllIlllIIIIlIl != 0.0) {
                lllllllllllllllllIlllIllIlllllll.moveCells(0.0, -lllllllllllllllllIlllIlllIIIIlIl);
            }
        }
    }

    protected double handleHeight() {
        WView lllllllllllllllllIlllIllIlIIIllI;
        return lllllllllllllllllIlllIllIlIIIllI.height / lllllllllllllllllIlllIllIlIIIllI.actualHeight * lllllllllllllllllIlllIllIlIIIllI.height;
    }

    private void updateScroll(double lllllllllllllllllIlllIllIlIllIlI) {
        WView lllllllllllllllllIlllIllIlIlIllI;
        double lllllllllllllllllIlllIllIlIllIIl = lllllllllllllllllIlllIllIlIlIllI.scroll;
        double lllllllllllllllllIlllIllIlIllIII = lllllllllllllllllIlllIllIlIlIllI.actualHeight - lllllllllllllllllIlllIllIlIlIllI.height;
        if (Math.abs(lllllllllllllllllIlllIllIlIlIllI.targetScroll - lllllllllllllllllIlllIllIlIlIllI.scroll) < 1.0) {
            lllllllllllllllllIlllIllIlIlIllI.scroll = lllllllllllllllllIlllIllIlIlIllI.targetScroll;
        } else if (lllllllllllllllllIlllIllIlIlIllI.targetScroll > lllllllllllllllllIlllIllIlIlIllI.scroll) {
            lllllllllllllllllIlllIllIlIlIllI.scroll += (double)Math.round(lllllllllllllllllIlllIllIlIlIllI.theme.scale(lllllllllllllllllIlllIllIlIllIlI * 300.0 + lllllllllllllllllIlllIllIlIllIlI * 100.0 * (Math.abs(lllllllllllllllllIlllIllIlIlIllI.targetScroll - lllllllllllllllllIlllIllIlIlIllI.scroll) / 10.0)));
            if (lllllllllllllllllIlllIllIlIlIllI.scroll > lllllllllllllllllIlllIllIlIlIllI.targetScroll) {
                lllllllllllllllllIlllIllIlIlIllI.scroll = lllllllllllllllllIlllIllIlIlIllI.targetScroll;
            }
        } else if (lllllllllllllllllIlllIllIlIlIllI.targetScroll < lllllllllllllllllIlllIllIlIlIllI.scroll) {
            lllllllllllllllllIlllIllIlIlIllI.scroll -= (double)Math.round(lllllllllllllllllIlllIllIlIlIllI.theme.scale(lllllllllllllllllIlllIllIlIllIlI * 300.0 + lllllllllllllllllIlllIllIlIllIlI * 100.0 * (Math.abs(lllllllllllllllllIlllIllIlIlIllI.targetScroll - lllllllllllllllllIlllIllIlIlIllI.scroll) / 10.0)));
            if (lllllllllllllllllIlllIllIlIlIllI.scroll < lllllllllllllllllIlllIllIlIlIllI.targetScroll) {
                lllllllllllllllllIlllIllIlIlIllI.scroll = lllllllllllllllllIlllIllIlIlIllI.targetScroll;
            }
        }
        lllllllllllllllllIlllIllIlIlIllI.scroll = Utils.clamp(lllllllllllllllllIlllIllIlIlIllI.scroll, 0.0, lllllllllllllllllIlllIllIlIllIII);
        double lllllllllllllllllIlllIllIlIlIlll = lllllllllllllllllIlllIllIlIlIllI.scroll - lllllllllllllllllIlllIllIlIllIIl;
        if (lllllllllllllllllIlllIllIlIlIlll != 0.0) {
            lllllllllllllllllIlllIllIlIlIllI.moveCells(0.0, -lllllllllllllllllIlllIllIlIlIlll);
        }
    }

    @Override
    public void onMouseScrolled(double lllllllllllllllllIlllIllIlllIlIl) {
        WView lllllllllllllllllIlllIllIlllIlII;
        if (!lllllllllllllllllIlllIllIlllIlII.scrollOnlyWhenMouseOver || lllllllllllllllllIlllIllIlllIlII.mouseOver) {
            lllllllllllllllllIlllIllIlllIlII.targetScroll -= (double)Math.round(lllllllllllllllllIlllIllIlllIlII.theme.scale(lllllllllllllllllIlllIllIlllIlIl * 40.0));
            lllllllllllllllllIlllIllIlllIlII.targetScroll = Utils.clamp(lllllllllllllllllIlllIllIlllIlII.targetScroll, 0.0, lllllllllllllllllIlllIllIlllIlII.actualHeight - lllllllllllllllllIlllIllIlllIlII.height);
        }
    }

    @Override
    public boolean onMouseClicked(double lllllllllllllllllIlllIlllIIlllIl, double lllllllllllllllllIlllIlllIIlllII, int lllllllllllllllllIlllIlllIIllIll, boolean lllllllllllllllllIlllIlllIIllIlI) {
        WView lllllllllllllllllIlllIlllIIllIIl;
        if (lllllllllllllllllIlllIlllIIllIIl.handleMouseOver && lllllllllllllllllIlllIlllIIllIll == 0 && !lllllllllllllllllIlllIlllIIllIlI) {
            lllllllllllllllllIlllIlllIIllIIl.handlePressed = true;
            return true;
        }
        return false;
    }

    protected double handleY() {
        WView lllllllllllllllllIlllIllIlIIIIII;
        return lllllllllllllllllIlllIllIlIIIIII.y + (lllllllllllllllllIlllIllIlIIIIII.height - lllllllllllllllllIlllIllIlIIIIII.handleHeight()) * (lllllllllllllllllIlllIllIlIIIIII.scroll / (lllllllllllllllllIlllIllIlIIIIII.actualHeight - lllllllllllllllllIlllIllIlIIIIII.height));
    }

    protected double handleWidth() {
        WView lllllllllllllllllIlllIllIlIIlIIl;
        return lllllllllllllllllIlllIllIlIIlIIl.theme.scale(6.0);
    }

    @Override
    public boolean onMouseReleased(double lllllllllllllllllIlllIlllIIlIlII, double lllllllllllllllllIlllIlllIIlIIll, int lllllllllllllllllIlllIlllIIlIIlI) {
        WView lllllllllllllllllIlllIlllIIlIlIl;
        if (lllllllllllllllllIlllIlllIIlIlIl.handlePressed) {
            lllllllllllllllllIlllIlllIIlIlIl.handlePressed = false;
        }
        return false;
    }

    @Override
    protected boolean propagateEvents(WWidget lllllllllllllllllIlllIllIlIIllII) {
        WView lllllllllllllllllIlllIllIlIIllIl;
        return lllllllllllllllllIlllIllIlIIllII.y >= lllllllllllllllllIlllIllIlIIllIl.y && lllllllllllllllllIlllIllIlIIllII.y <= lllllllllllllllllIlllIllIlIIllIl.y + lllllllllllllllllIlllIllIlIIllIl.height || lllllllllllllllllIlllIllIlIIllII.y + lllllllllllllllllIlllIllIlIIllII.height >= lllllllllllllllllIlllIllIlIIllIl.y && lllllllllllllllllIlllIllIlIIllII.y + lllllllllllllllllIlllIllIlIIllII.height <= lllllllllllllllllIlllIllIlIIllIl.y + lllllllllllllllllIlllIllIlIIllIl.height || lllllllllllllllllIlllIllIlIIllIl.y >= lllllllllllllllllIlllIllIlIIllII.y && lllllllllllllllllIlllIllIlIIllIl.y <= lllllllllllllllllIlllIllIlIIllII.y + lllllllllllllllllIlllIllIlIIllII.height || lllllllllllllllllIlllIllIlIIllIl.y + lllllllllllllllllIlllIllIlIIllIl.height >= lllllllllllllllllIlllIllIlIIllII.y && lllllllllllllllllIlllIllIlIIllIl.y + lllllllllllllllllIlllIllIlIIllIl.height <= lllllllllllllllllIlllIllIlIIllII.y + lllllllllllllllllIlllIllIlIIllII.height;
    }
}

