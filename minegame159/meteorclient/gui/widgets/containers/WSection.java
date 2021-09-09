/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.containers;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.utils.Utils;

public abstract class WSection
extends WVerticalList {
    protected /* synthetic */ boolean expanded;
    private /* synthetic */ double actualHeight;
    private /* synthetic */ double forcedHeight;
    private /* synthetic */ boolean firstTime;
    private /* synthetic */ double actualWidth;
    protected /* synthetic */ double animProgress;
    public /* synthetic */ Runnable action;
    private /* synthetic */ WHeader header;
    protected /* synthetic */ String title;
    protected final /* synthetic */ WWidget headerWidget;

    @Override
    protected void renderWidget(WWidget llllllIIlIIII, GuiRenderer llllllIIlIlIl, double llllllIIIlllI, double llllllIIlIIll, double llllllIIIllII) {
        WSection llllllIIlIlll;
        if (llllllIIlIlll.expanded || llllllIIlIlll.animProgress > 0.0 || llllllIIlIIII instanceof WHeader) {
            llllllIIlIIII.render(llllllIIlIlIl, llllllIIIlllI, llllllIIlIIll, llllllIIIllII);
        }
    }

    public void setExpanded(boolean llllllIlllllI) {
        llllllIllllll.expanded = llllllIlllllI;
    }

    public WSection(String lllllllIIllIl, boolean lllllllIIllII, WWidget lllllllIIlIll) {
        WSection lllllllIlIIlI;
        lllllllIlIIlI.forcedHeight = -1.0;
        lllllllIlIIlI.firstTime = true;
        lllllllIlIIlI.title = lllllllIIllIl;
        lllllllIlIIlI.expanded = lllllllIIllII;
        lllllllIlIIlI.headerWidget = lllllllIIlIll;
        lllllllIlIIlI.animProgress = lllllllIIllII ? 1.0 : 0.0;
    }

    @Override
    public <T extends WWidget> Cell<T> add(T lllllllIIIIlI) {
        WSection lllllllIIIlIl;
        return super.add(lllllllIIIIlI).padHorizontal(6.0);
    }

    public boolean isExpanded() {
        WSection llllllIlllIIl;
        return llllllIlllIIl.expanded;
    }

    @Override
    public void init() {
        WSection lllllllIIlIIl;
        lllllllIIlIIl.header = lllllllIIlIIl.createHeader();
        lllllllIIlIIl.header.theme = lllllllIIlIIl.theme;
        super.add(lllllllIIlIIl.header).expandX();
    }

    @Override
    protected void onCalculateSize() {
        WSection llllllIllIlll;
        if (llllllIllIlll.forcedHeight == -1.0) {
            super.onCalculateSize();
            llllllIllIlll.actualWidth = llllllIllIlll.width;
            llllllIllIlll.actualHeight = llllllIllIlll.height;
        } else {
            llllllIllIlll.width = llllllIllIlll.actualWidth;
            llllllIllIlll.height = llllllIllIlll.forcedHeight;
            if (llllllIllIlll.animProgress == 1.0) {
                llllllIllIlll.forcedHeight = -1.0;
            }
        }
        if (llllllIllIlll.firstTime) {
            llllllIllIlll.firstTime = false;
            llllllIllIlll.forcedHeight = (llllllIllIlll.actualHeight - llllllIllIlll.header.height) * llllllIllIlll.animProgress + llllllIllIlll.header.height;
            llllllIllIlll.onCalculateSize();
        }
    }

    @Override
    public boolean render(GuiRenderer llllllIlIllII, double llllllIlIIIll, double llllllIlIIIlI, double llllllIlIIIIl) {
        boolean llllllIlIIlll;
        WSection llllllIlIIlIl;
        if (!llllllIlIIlIl.visible) {
            return true;
        }
        double llllllIlIlIII = llllllIlIIlIl.animProgress;
        llllllIlIIlIl.animProgress += (double)(llllllIlIIlIl.expanded ? 1 : -1) * llllllIlIIIIl * 14.0;
        llllllIlIIlIl.animProgress = Utils.clamp(llllllIlIIlIl.animProgress, 0.0, 1.0);
        if (llllllIlIIlIl.animProgress != llllllIlIlIII) {
            llllllIlIIlIl.forcedHeight = (llllllIlIIlIl.actualHeight - llllllIlIIlIl.header.height) * llllllIlIIlIl.animProgress + llllllIlIIlIl.header.height;
            llllllIlIIlIl.invalidate();
        }
        boolean bl = llllllIlIIlll = llllllIlIIlIl.animProgress != 0.0 && llllllIlIIlIl.animProgress != 1.0 || llllllIlIIlIl.expanded && llllllIlIIlIl.animProgress != 1.0;
        if (llllllIlIIlll) {
            llllllIlIllII.scissorStart(llllllIlIIlIl.x, llllllIlIIlIl.y, llllllIlIIlIl.width, (llllllIlIIlIl.height - llllllIlIIlIl.header.height) * llllllIlIIlIl.animProgress + llllllIlIIlIl.header.height);
        }
        boolean llllllIlIIllI = super.render(llllllIlIllII, llllllIlIIIll, llllllIlIIIlI, llllllIlIIIIl);
        if (llllllIlIIlll) {
            llllllIlIllII.scissorEnd();
        }
        return llllllIlIIllI;
    }

    @Override
    protected boolean propagateEvents(WWidget llllllIIIIllI) {
        WSection llllllIIIIlll;
        return llllllIIIIlll.expanded || llllllIIIIllI instanceof WHeader;
    }

    protected abstract WHeader createHeader();

    protected abstract class WHeader
    extends WHorizontalList {
        protected /* synthetic */ String title;

        protected void onClick() {
            WHeader lIllIlIIIIlIlI;
            lIllIlIIIIlIlI.WSection.this.setExpanded(!lIllIlIIIIlIlI.WSection.this.expanded);
            if (lIllIlIIIIlIlI.WSection.this.action != null) {
                lIllIlIIIIlIlI.WSection.this.action.run();
            }
        }

        @Override
        public boolean onMouseClicked(double lIllIlIIIlIIlI, double lIllIlIIIlIIIl, int lIllIlIIIIllIl, boolean lIllIlIIIIllll) {
            WHeader lIllIlIIIlIIll;
            if (lIllIlIIIlIIll.mouseOver && lIllIlIIIIllIl == 0 && !lIllIlIIIIllll) {
                lIllIlIIIlIIll.onClick();
                return true;
            }
            return false;
        }

        public WHeader(String lIllIlIIIllIlI) {
            WHeader lIllIlIIIllIIl;
            lIllIlIIIllIIl.title = lIllIlIIIllIlI;
        }
    }
}

