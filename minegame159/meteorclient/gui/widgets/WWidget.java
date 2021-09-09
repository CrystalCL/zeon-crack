/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.utils.BaseWidget;
import minegame159.meteorclient.gui.widgets.WRoot;

public abstract class WWidget
implements BaseWidget {
    public /* synthetic */ double width;
    public /* synthetic */ GuiTheme theme;
    public /* synthetic */ boolean mouseOver;
    public /* synthetic */ double x;
    public /* synthetic */ double height;
    public /* synthetic */ double minWidth;
    public /* synthetic */ String tooltip;
    public /* synthetic */ WWidget parent;
    protected /* synthetic */ double mouseOverTimer;
    public /* synthetic */ double y;
    public /* synthetic */ boolean visible;

    public boolean render(GuiRenderer llllllllllllllllllllIIllIlIIllll, double llllllllllllllllllllIIllIlIIlllI, double llllllllllllllllllllIIllIlIIllIl, double llllllllllllllllllllIIllIlIIllII) {
        WWidget llllllllllllllllllllIIllIlIIlIll;
        if (!llllllllllllllllllllIIllIlIIlIll.visible) {
            return true;
        }
        if (llllllllllllllllllllIIllIlIIlIll.isOver(llllllllllllllllllllIIllIlIIlllI, llllllllllllllllllllIIllIlIIllIl)) {
            llllllllllllllllllllIIllIlIIlIll.mouseOverTimer += llllllllllllllllllllIIllIlIIllII;
            if (llllllllllllllllllllIIllIlIIlIll.mouseOverTimer >= 1.0 && llllllllllllllllllllIIllIlIIlIll.tooltip != null) {
                llllllllllllllllllllIIllIlIIllll.tooltip(llllllllllllllllllllIIllIlIIlIll.tooltip);
            }
        } else {
            llllllllllllllllllllIIllIlIIlIll.mouseOverTimer = 0.0;
        }
        llllllllllllllllllllIIllIlIIlIll.onRender(llllllllllllllllllllIIllIlIIllll, llllllllllllllllllllIIllIlIIlllI, llllllllllllllllllllIIllIlIIllIl, llllllllllllllllllllIIllIlIIllII);
        return false;
    }

    public boolean onKeyRepeated(int llllllllllllllllllllIIlIlllIlIll, int llllllllllllllllllllIIlIlllIlIlI) {
        return false;
    }

    protected void onCalculateSize() {
    }

    public boolean mouseReleased(double llllllllllllllllllllIIllIIlIIlII, double llllllllllllllllllllIIllIIlIIIll, int llllllllllllllllllllIIllIIlIIllI) {
        WWidget llllllllllllllllllllIIllIIlIlIIl;
        return llllllllllllllllllllIIllIIlIlIIl.onMouseReleased(llllllllllllllllllllIIllIIlIIlII, llllllllllllllllllllIIllIIlIIIll, llllllllllllllllllllIIllIIlIIllI);
    }

    public void mouseMoved(double llllllllllllllllllllIIllIIIlIIlI, double llllllllllllllllllllIIllIIIlIIIl, double llllllllllllllllllllIIllIIIlIlIl, double llllllllllllllllllllIIllIIIIllll) {
        WWidget llllllllllllllllllllIIllIIIlIIll;
        llllllllllllllllllllIIllIIIlIIll.mouseOver = llllllllllllllllllllIIllIIIlIIll.isOver(llllllllllllllllllllIIllIIIlIIlI, llllllllllllllllllllIIllIIIlIIIl);
        llllllllllllllllllllIIllIIIlIIll.onMouseMoved(llllllllllllllllllllIIllIIIlIIlI, llllllllllllllllllllIIllIIIlIIIl, llllllllllllllllllllIIllIIIlIlIl, llllllllllllllllllllIIllIIIIllll);
    }

    protected void onRender(GuiRenderer llllllllllllllllllllIIllIlIIIlIl, double llllllllllllllllllllIIllIlIIIlII, double llllllllllllllllllllIIllIlIIIIll, double llllllllllllllllllllIIllIlIIIIlI) {
    }

    public void invalidate() {
        WWidget llllllllllllllllllllIIlIllIlllIl;
        WWidget llllllllllllllllllllIIlIllIllllI = llllllllllllllllllllIIlIllIlllIl.getRoot();
        if (llllllllllllllllllllIIlIllIllllI != null) {
            llllllllllllllllllllIIlIllIllllI.invalidate();
        }
    }

    public WWidget() {
        WWidget llllllllllllllllllllIIlllIIIllIl;
        llllllllllllllllllllIIlllIIIllIl.visible = true;
    }

    public void onMouseMoved(double llllllllllllllllllllIIllIIIIllIl, double llllllllllllllllllllIIllIIIIllII, double llllllllllllllllllllIIllIIIIlIll, double llllllllllllllllllllIIllIIIIlIlI) {
    }

    public void onMouseScrolled(double llllllllllllllllllllIIllIIIIIIlI) {
    }

    public boolean onMouseReleased(double llllllllllllllllllllIIllIIlIIIII, double llllllllllllllllllllIIllIIIlllll, int llllllllllllllllllllIIllIIIllllI) {
        return false;
    }

    public boolean onCharTyped(char llllllllllllllllllllIIlIlllIIIlI) {
        return false;
    }

    public boolean keyRepeated(int llllllllllllllllllllIIlIlllIlllI, int llllllllllllllllllllIIlIllllIIII) {
        WWidget llllllllllllllllllllIIlIlllIllll;
        return llllllllllllllllllllIIlIlllIllll.onKeyRepeated(llllllllllllllllllllIIlIlllIlllI, llllllllllllllllllllIIlIllllIIII);
    }

    public void init() {
    }

    @Override
    public GuiTheme getTheme() {
        WWidget llllllllllllllllllllIIllIllIIlII;
        return llllllllllllllllllllIIllIllIIlII.theme;
    }

    public boolean isOver(double llllllllllllllllllllIIlIllIlIlII, double llllllllllllllllllllIIlIllIlIIll) {
        WWidget llllllllllllllllllllIIlIllIlIIlI;
        return llllllllllllllllllllIIlIllIlIlII >= llllllllllllllllllllIIlIllIlIIlI.x && llllllllllllllllllllIIlIllIlIlII <= llllllllllllllllllllIIlIllIlIIlI.x + llllllllllllllllllllIIlIllIlIIlI.width && llllllllllllllllllllIIlIllIlIIll >= llllllllllllllllllllIIlIllIlIIlI.y && llllllllllllllllllllIIlIllIlIIll <= llllllllllllllllllllIIlIllIlIIlI.y + llllllllllllllllllllIIlIllIlIIlI.height;
    }

    public boolean charTyped(char llllllllllllllllllllIIlIlllIIllI) {
        WWidget llllllllllllllllllllIIlIlllIIlIl;
        return llllllllllllllllllllIIlIlllIIlIl.onCharTyped(llllllllllllllllllllIIlIlllIIllI);
    }

    public void move(double llllllllllllllllllllIIllIllIllIl, double llllllllllllllllllllIIllIllIlIIl) {
        WWidget llllllllllllllllllllIIllIllIlIll;
        llllllllllllllllllllIIllIllIlIll.x = Math.round(llllllllllllllllllllIIllIllIlIll.x + llllllllllllllllllllIIllIllIllIl);
        llllllllllllllllllllIIllIllIlIll.y = Math.round(llllllllllllllllllllIIllIllIlIll.y + llllllllllllllllllllIIllIllIlIIl);
    }

    public double pad() {
        WWidget llllllllllllllllllllIIllIllIIIlI;
        return llllllllllllllllllllIIllIllIIIlI.theme.pad();
    }

    public void calculateWidgetPositions() {
        WWidget llllllllllllllllllllIIllIlIllIII;
        llllllllllllllllllllIIllIlIllIII.x = Math.round(llllllllllllllllllllIIllIlIllIII.x);
        llllllllllllllllllllIIllIlIllIII.y = Math.round(llllllllllllllllllllIIllIlIllIII.y);
        llllllllllllllllllllIIllIlIllIII.onCalculateWidgetPositions();
    }

    public boolean onKeyPressed(int llllllllllllllllllllIIlIllllIlll, int llllllllllllllllllllIIlIllllIllI) {
        return false;
    }

    protected WWidget getRoot() {
        WWidget llllllllllllllllllllIIlIllIllIlI;
        return llllllllllllllllllllIIlIllIllIlI.parent != null ? llllllllllllllllllllIIlIllIllIlI.parent.getRoot() : (llllllllllllllllllllIIlIllIllIlI instanceof WRoot ? llllllllllllllllllllIIlIllIllIlI : null);
    }

    public boolean onMouseClicked(double llllllllllllllllllllIIllIIllIIIl, double llllllllllllllllllllIIllIIllIIII, int llllllllllllllllllllIIllIIlIllll, boolean llllllllllllllllllllIIllIIlIlllI) {
        return false;
    }

    public boolean keyPressed(int llllllllllllllllllllIIlIllllllIl, int llllllllllllllllllllIIlIlllllIIl) {
        WWidget llllllllllllllllllllIIlIlllllllI;
        return llllllllllllllllllllIIlIlllllllI.onKeyPressed(llllllllllllllllllllIIlIllllllIl, llllllllllllllllllllIIlIlllllIIl);
    }

    public void mouseScrolled(double llllllllllllllllllllIIllIIIIIllI) {
        WWidget llllllllllllllllllllIIllIIIIIlll;
        llllllllllllllllllllIIllIIIIIlll.onMouseScrolled(llllllllllllllllllllIIllIIIIIllI);
    }

    protected void onCalculateWidgetPositions() {
    }

    public void calculateSize() {
        WWidget llllllllllllllllllllIIllIlIllllI;
        llllllllllllllllllllIIllIlIllllI.onCalculateSize();
        double llllllllllllllllllllIIllIlIlllIl = llllllllllllllllllllIIllIlIllllI.theme.scale(llllllllllllllllllllIIllIlIllllI.minWidth);
        if (llllllllllllllllllllIIllIlIllllI.width < llllllllllllllllllllIIllIlIlllIl) {
            llllllllllllllllllllIIllIlIllllI.width = llllllllllllllllllllIIllIlIlllIl;
        }
        llllllllllllllllllllIIllIlIllllI.width = Math.round(llllllllllllllllllllIIllIlIllllI.width);
        llllllllllllllllllllIIllIlIllllI.height = Math.round(llllllllllllllllllllIIllIlIllllI.height);
    }

    public boolean mouseClicked(double llllllllllllllllllllIIllIIllIllI, double llllllllllllllllllllIIllIIlllIlI, int llllllllllllllllllllIIllIIllIlII, boolean llllllllllllllllllllIIllIIlllIII) {
        WWidget llllllllllllllllllllIIllIIllllII;
        return llllllllllllllllllllIIllIIllllII.onMouseClicked(llllllllllllllllllllIIllIIllIllI, llllllllllllllllllllIIllIIlllIlI, llllllllllllllllllllIIllIIllIlII, llllllllllllllllllllIIllIIlllIII);
    }
}

