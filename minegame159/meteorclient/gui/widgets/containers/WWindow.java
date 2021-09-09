/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.containers;

import java.util.function.Consumer;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.utils.WindowConfig;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WContainer;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.gui.widgets.containers.WView;
import minegame159.meteorclient.gui.widgets.pressable.WTriangle;
import minegame159.meteorclient.utils.Utils;

public abstract class WWindow
extends WVerticalList {
    protected /* synthetic */ double animProgress;
    protected /* synthetic */ boolean expanded;
    protected /* synthetic */ boolean dragging;
    protected final /* synthetic */ String title;
    public /* synthetic */ WView view;
    protected /* synthetic */ double movedY;
    public /* synthetic */ String id;
    protected /* synthetic */ WHeader header;
    private /* synthetic */ boolean propagateEventsExpanded;
    public /* synthetic */ double padding;
    protected /* synthetic */ boolean dragged;
    protected /* synthetic */ double movedX;
    protected /* synthetic */ boolean moved;
    public /* synthetic */ Consumer<WContainer> beforeHeaderInit;
    private /* synthetic */ boolean firstTime;

    @Override
    public void init() {
        WWindow llIllIIIIllIll;
        llIllIIIIllIll.header = llIllIIIIllIll.header();
        llIllIIIIllIll.header.theme = llIllIIIIllIll.theme;
        super.add(llIllIIIIllIll.header).expandWidgetX().widget();
        llIllIIIIllIll.view = super.add(llIllIIIIllIll.theme.view()).expandX().pad(llIllIIIIllIll.padding).widget();
        if (llIllIIIIllIll.id != null) {
            llIllIIIIllIll.expanded = llIllIIIIllIll.theme.getWindowConfig((String)llIllIIIIllIll.id).expanded;
            llIllIIIIllIll.animProgress = llIllIIIIllIll.expanded ? 1.0 : 0.0;
        }
    }

    @Override
    protected void onCalculateWidgetPositions() {
        WWindow llIllIIIIIIlII;
        if (llIllIIIIIIlII.firstTime) {
            if (llIllIIIIIIlII.id != null) {
                WindowConfig llIllIIIIIIllI = llIllIIIIIIlII.theme.getWindowConfig(llIllIIIIIIlII.id);
                if (llIllIIIIIIllI.x != -1.0) {
                    llIllIIIIIIlII.x = llIllIIIIIIllI.x;
                    if (llIllIIIIIIlII.x + llIllIIIIIIlII.width > (double)Utils.getWindowWidth()) {
                        llIllIIIIIIlII.x = (double)Utils.getWindowWidth() - llIllIIIIIIlII.width;
                    }
                }
                if (llIllIIIIIIllI.y != -1.0) {
                    llIllIIIIIIlII.y = llIllIIIIIIllI.y;
                    if (llIllIIIIIIlII.y + llIllIIIIIIlII.height > (double)Utils.getWindowHeight()) {
                        llIllIIIIIIlII.y = (double)Utils.getWindowHeight() - llIllIIIIIIlII.height;
                    }
                }
            }
            llIllIIIIIIlII.firstTime = false;
        }
        super.onCalculateWidgetPositions();
        if (llIllIIIIIIlII.moved) {
            llIllIIIIIIlII.move(llIllIIIIIIlII.movedX - llIllIIIIIIlII.x, llIllIIIIIIlII.movedY - llIllIIIIIIlII.y);
        }
    }

    public WWindow(String llIllIIIIllllI) {
        WWindow llIllIIIIlllll;
        llIllIIIIlllll.padding = 8.0;
        llIllIIIIlllll.expanded = true;
        llIllIIIIlllll.animProgress = 1.0;
        llIllIIIIlllll.moved = false;
        llIllIIIIlllll.firstTime = true;
        llIllIIIIlllll.title = llIllIIIIllllI;
    }

    protected abstract WHeader header();

    @Override
    public <T extends WWidget> Cell<T> add(T llIllIIIIlIlIl) {
        WWindow llIllIIIIllIII;
        return llIllIIIIllIII.view.add(llIllIIIIlIlIl);
    }

    @Override
    protected boolean propagateEvents(WWidget llIlIlllIlIllI) {
        WWindow llIlIlllIllIIl;
        return llIlIlllIlIllI instanceof WHeader || llIlIlllIllIIl.propagateEventsExpanded;
    }

    @Override
    public boolean render(GuiRenderer llIlIllllllIlI, double llIlIlllllIIlI, double llIlIlllllIIIl, double llIlIlllllIIII) {
        boolean llIlIlllllIllI;
        WWindow llIlIlllllIlII;
        if (!llIlIlllllIlII.visible) {
            return true;
        }
        boolean bl = llIlIlllllIllI = llIlIlllllIlII.animProgress != 0.0 && llIlIlllllIlII.animProgress != 1.0 || llIlIlllllIlII.expanded && llIlIlllllIlII.animProgress != 1.0;
        if (llIlIlllllIllI) {
            llIlIllllllIlI.scissorStart(llIlIlllllIlII.x, llIlIlllllIlII.y, llIlIlllllIlII.width, (llIlIlllllIlII.height - llIlIlllllIlII.header.height) * llIlIlllllIlII.animProgress + llIlIlllllIlII.header.height);
        }
        boolean llIlIlllllIlIl = super.render(llIlIllllllIlI, llIlIlllllIIlI, llIlIlllllIIIl, llIlIlllllIIII);
        if (llIlIlllllIllI) {
            llIlIllllllIlI.scissorEnd();
        }
        return llIlIlllllIlIl;
    }

    @Override
    protected void renderWidget(WWidget llIlIllllIIIII, GuiRenderer llIlIllllIIlIl, double llIlIlllIllllI, double llIlIlllIlllIl, double llIlIlllIlllII) {
        WWindow llIlIllllIIlll;
        if (llIlIllllIIlll.expanded || llIlIllllIIlll.animProgress > 0.0 || llIlIllllIIIII instanceof WHeader) {
            llIlIllllIIIII.render(llIlIllllIIlIl, llIlIlllIllllI, llIlIlllIlllIl, llIlIlllIlllII);
        }
        llIlIllllIIlll.propagateEventsExpanded = llIlIllllIIlll.expanded;
    }

    @Override
    public void clear() {
        WWindow llIllIIIIlIIlI;
        llIllIIIIlIIlI.view.clear();
    }

    public void setExpanded(boolean llIllIIIIIllII) {
        WWindow llIllIIIIIlIll;
        llIllIIIIIlIll.expanded = llIllIIIIIllII;
        if (llIllIIIIIlIll.id != null) {
            WindowConfig llIllIIIIIlllI = llIllIIIIIlIll.theme.getWindowConfig(llIllIIIIIlIll.id);
            llIllIIIIIlllI.expanded = llIllIIIIIllII;
        }
    }

    protected abstract class WHeader
    extends WContainer {
        private /* synthetic */ WHorizontalList list;
        private /* synthetic */ WTriangle triangle;

        @Override
        public void onMouseMoved(double lllllllllllllllllIIllIlIIIllIlll, double lllllllllllllllllIIllIlIIIllIIIl, double lllllllllllllllllIIllIlIIIllIIII, double lllllllllllllllllIIllIlIIIllIlII) {
            WHeader lllllllllllllllllIIllIlIIIllIIll;
            if (lllllllllllllllllIIllIlIIIllIIll.WWindow.this.dragging) {
                lllllllllllllllllIIllIlIIIllIIll.WWindow.this.move(lllllllllllllllllIIllIlIIIllIlll - lllllllllllllllllIIllIlIIIllIIII, lllllllllllllllllIIllIlIIIllIIIl - lllllllllllllllllIIllIlIIIllIlII);
                lllllllllllllllllIIllIlIIIllIIll.WWindow.this.moved = true;
                lllllllllllllllllIIllIlIIIllIIll.WWindow.this.movedX = lllllllllllllllllIIllIlIIIllIIll.x;
                lllllllllllllllllIIllIlIIIllIIll.WWindow.this.movedY = lllllllllllllllllIIllIlIIIllIIll.y;
                if (lllllllllllllllllIIllIlIIIllIIll.WWindow.this.id != null) {
                    WindowConfig lllllllllllllllllIIllIlIIIlllIIl = lllllllllllllllllIIllIlIIIllIIll.theme.getWindowConfig(lllllllllllllllllIIllIlIIIllIIll.WWindow.this.id);
                    lllllllllllllllllIIllIlIIIlllIIl.x = lllllllllllllllllIIllIlIIIllIIll.x;
                    lllllllllllllllllIIllIlIIIlllIIl.y = lllllllllllllllllIIllIlIIIllIIll.y;
                }
                lllllllllllllllllIIllIlIIIllIIll.WWindow.this.dragged = true;
            }
        }

        @Override
        public boolean onMouseReleased(double lllllllllllllllllIIllIlIIlIIIIll, double lllllllllllllllllIIllIlIIlIIIIlI, int lllllllllllllllllIIllIlIIlIIIIIl) {
            WHeader lllllllllllllllllIIllIlIIlIIIIII;
            if (lllllllllllllllllIIllIlIIlIIIIII.WWindow.this.dragging) {
                lllllllllllllllllIIllIlIIlIIIIII.WWindow.this.dragging = false;
                if (!lllllllllllllllllIIllIlIIlIIIIII.WWindow.this.dragged) {
                    lllllllllllllllllIIllIlIIlIIIIII.WWindow.this.setExpanded(!lllllllllllllllllIIllIlIIlIIIIII.WWindow.this.expanded);
                }
            }
            return false;
        }

        @Override
        public <T extends WWidget> Cell<T> add(T lllllllllllllllllIIllIlIIlIllllI) {
            WHeader lllllllllllllllllIIllIlIIlIlllIl;
            if (lllllllllllllllllIIllIlIIlIlllIl.list != null) {
                return lllllllllllllllllIIllIlIIlIlllIl.list.add(lllllllllllllllllIIllIlIIlIllllI);
            }
            return super.add(lllllllllllllllllIIllIlIIlIllllI);
        }

        @Override
        public boolean render(GuiRenderer lllllllllllllllllIIllIlIIIlIIlll, double lllllllllllllllllIIllIlIIIlIIllI, double lllllllllllllllllIIllIlIIIlIIlIl, double lllllllllllllllllIIllIlIIIIlllll) {
            WHeader lllllllllllllllllIIllIlIIIlIlIII;
            lllllllllllllllllIIllIlIIIlIlIII.WWindow.this.animProgress += (double)(lllllllllllllllllIIllIlIIIlIlIII.WWindow.this.expanded ? 1 : -1) * lllllllllllllllllIIllIlIIIIlllll * 14.0;
            lllllllllllllllllIIllIlIIIlIlIII.WWindow.this.animProgress = Utils.clamp(lllllllllllllllllIIllIlIIIlIlIII.WWindow.this.animProgress, 0.0, 1.0);
            lllllllllllllllllIIllIlIIIlIlIII.triangle.rotation = (1.0 - lllllllllllllllllIIllIlIIIlIlIII.WWindow.this.animProgress) * -90.0;
            return super.render(lllllllllllllllllIIllIlIIIlIIlll, lllllllllllllllllIIllIlIIIlIIllI, lllllllllllllllllIIllIlIIIlIIlIl, lllllllllllllllllIIllIlIIIIlllll);
        }

        @Override
        public void init() {
            WHeader lllllllllllllllllIIllIlIIllIIIlI;
            if (lllllllllllllllllIIllIlIIllIIIlI.WWindow.this.beforeHeaderInit != null) {
                lllllllllllllllllIIllIlIIllIIIlI.list = lllllllllllllllllIIllIlIIllIIIlI.add(lllllllllllllllllIIllIlIIllIIIlI.theme.horizontalList()).expandX().widget();
                lllllllllllllllllIIllIlIIllIIIlI.list.spacing = 0.0;
                lllllllllllllllllIIllIlIIllIIIlI.WWindow.this.beforeHeaderInit.accept(lllllllllllllllllIIllIlIIllIIIlI);
            }
            lllllllllllllllllIIllIlIIllIIIlI.add(lllllllllllllllllIIllIlIIllIIIlI.theme.label(lllllllllllllllllIIllIlIIllIIIlI.WWindow.this.title, true)).expandCellX().center().pad(4.0);
            lllllllllllllllllIIllIlIIllIIIlI.triangle = lllllllllllllllllIIllIlIIllIIIlI.add(lllllllllllllllllIIllIlIIllIIIlI.theme.triangle()).pad(4.0).right().centerY().widget();
            lllllllllllllllllIIllIlIIllIIIlI.triangle.action = () -> {
                WHeader lllllllllllllllllIIllIlIIIIlllIl;
                lllllllllllllllllIIllIlIIIIlllIl.WWindow.this.setExpanded(!lllllllllllllllllIIllIlIIIIlllIl.WWindow.this.expanded);
            };
        }

        protected WHeader() {
            WHeader lllllllllllllllllIIllIlIIllIlIII;
        }

        @Override
        public boolean onMouseClicked(double lllllllllllllllllIIllIlIIlIIllII, double lllllllllllllllllIIllIlIIlIIlIll, int lllllllllllllllllIIllIlIIlIIIlll, boolean lllllllllllllllllIIllIlIIlIIIllI) {
            WHeader lllllllllllllllllIIllIlIIlIIllIl;
            if (lllllllllllllllllIIllIlIIlIIllIl.mouseOver && !lllllllllllllllllIIllIlIIlIIIllI) {
                if (lllllllllllllllllIIllIlIIlIIIlll == 1) {
                    lllllllllllllllllIIllIlIIlIIllIl.WWindow.this.setExpanded(!lllllllllllllllllIIllIlIIlIIllIl.WWindow.this.expanded);
                } else {
                    lllllllllllllllllIIllIlIIlIIllIl.WWindow.this.dragging = true;
                    lllllllllllllllllIIllIlIIlIIllIl.WWindow.this.dragged = false;
                }
                return true;
            }
            return false;
        }

        @Override
        protected void onCalculateSize() {
            WHeader lllllllllllllllllIIllIlIIlIlIlII;
            lllllllllllllllllIIllIlIIlIlIlII.width = 0.0;
            lllllllllllllllllIIllIlIIlIlIlII.height = 0.0;
            for (Cell lllllllllllllllllIIllIlIIlIlIllI : lllllllllllllllllIIllIlIIlIlIlII.cells) {
                double lllllllllllllllllIIllIlIIlIlIlll = lllllllllllllllllIIllIlIIlIlIllI.padLeft() + ((WWidget)lllllllllllllllllIIllIlIIlIlIllI.widget()).width + lllllllllllllllllIIllIlIIlIlIllI.padRight();
                if (lllllllllllllllllIIllIlIIlIlIllI.widget() instanceof WTriangle) {
                    lllllllllllllllllIIllIlIIlIlIlll *= 2.0;
                }
                lllllllllllllllllIIllIlIIlIlIlII.width += lllllllllllllllllIIllIlIIlIlIlll;
                lllllllllllllllllIIllIlIIlIlIlII.height = Math.max(lllllllllllllllllIIllIlIIlIlIlII.height, lllllllllllllllllIIllIlIIlIlIllI.padTop() + ((WWidget)lllllllllllllllllIIllIlIIlIlIllI.widget()).height + lllllllllllllllllIIllIlIIlIlIllI.padBottom());
            }
        }
    }
}

