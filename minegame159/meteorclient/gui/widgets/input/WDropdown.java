/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.input;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WRoot;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.gui.widgets.pressable.WPressable;
import minegame159.meteorclient.utils.Utils;

public abstract class WDropdown<T>
extends WPressable {
    protected /* synthetic */ boolean expanded;
    protected /* synthetic */ WDropdownRoot root;
    protected /* synthetic */ T[] values;
    protected /* synthetic */ double animProgress;
    protected /* synthetic */ double maxValueWidth;
    public /* synthetic */ Runnable action;
    protected /* synthetic */ T value;

    @Override
    public void init() {
        WDropdown lllllllllllllllllIIllIIllIlIlllI;
        lllllllllllllllllIIllIIllIlIlllI.root = lllllllllllllllllIIllIIllIlIlllI.createRootWidget();
        lllllllllllllllllIIllIIllIlIlllI.root.theme = lllllllllllllllllIIllIIllIlIlllI.theme;
        lllllllllllllllllIIllIIllIlIlllI.root.spacing = 0.0;
        for (int lllllllllllllllllIIllIIllIllIIII = 0; lllllllllllllllllIIllIIllIllIIII < lllllllllllllllllIIllIIllIlIlllI.values.length; ++lllllllllllllllllIIllIIllIllIIII) {
            WDropdownValue lllllllllllllllllIIllIIllIllIIlI = lllllllllllllllllIIllIIllIlIlllI.createValueWidget();
            lllllllllllllllllIIllIIllIllIIlI.theme = lllllllllllllllllIIllIIllIlIlllI.theme;
            lllllllllllllllllIIllIIllIllIIlI.value = lllllllllllllllllIIllIIllIlIlllI.values[lllllllllllllllllIIllIIllIllIIII];
            Cell<WDropdownValue> lllllllllllllllllIIllIIllIllIIIl = lllllllllllllllllIIllIIllIlIlllI.root.add(lllllllllllllllllIIllIIllIllIIlI).padHorizontal(2.0).expandWidgetX();
            if (lllllllllllllllllIIllIIllIllIIII < lllllllllllllllllIIllIIllIlIlllI.values.length - 1) continue;
            lllllllllllllllllIIllIIllIllIIIl.padBottom(2.0);
        }
    }

    @Override
    public boolean onMouseReleased(double lllllllllllllllllIIllIIlIlIllIIl, double lllllllllllllllllIIllIIlIlIllIII, int lllllllllllllllllIIllIIlIlIlIIll) {
        WDropdown lllllllllllllllllIIllIIlIlIlIllI;
        if (super.onMouseReleased(lllllllllllllllllIIllIIlIlIllIIl, lllllllllllllllllIIllIIlIlIllIII, lllllllllllllllllIIllIIlIlIlIIll)) {
            return true;
        }
        return lllllllllllllllllIIllIIlIlIlIllI.expanded && lllllllllllllllllIIllIIlIlIlIllI.root.mouseReleased(lllllllllllllllllIIllIIlIlIllIIl, lllllllllllllllllIIllIIlIlIllIII, lllllllllllllllllIIllIIlIlIlIIll);
    }

    @Override
    public void move(double lllllllllllllllllIIllIIllIIIIIIl, double lllllllllllllllllIIllIIllIIIIIII) {
        WDropdown lllllllllllllllllIIllIIllIIIIIlI;
        super.move(lllllllllllllllllIIllIIllIIIIIIl, lllllllllllllllllIIllIIllIIIIIII);
        lllllllllllllllllIIllIIllIIIIIlI.root.move(lllllllllllllllllIIllIIllIIIIIIl, lllllllllllllllllIIllIIllIIIIIII);
    }

    @Override
    protected void onCalculateSize() {
        WDropdown lllllllllllllllllIIllIIllIIlllll;
        double lllllllllllllllllIIllIIllIlIIIII = lllllllllllllllllIIllIIllIIlllll.pad();
        lllllllllllllllllIIllIIllIIlllll.maxValueWidth = 0.0;
        for (T lllllllllllllllllIIllIIllIlIIIlI : lllllllllllllllllIIllIIllIIlllll.values) {
            double lllllllllllllllllIIllIIllIlIIIll = lllllllllllllllllIIllIIllIIlllll.theme.textWidth(lllllllllllllllllIIllIIllIlIIIlI.toString());
            lllllllllllllllllIIllIIllIIlllll.maxValueWidth = Math.max(lllllllllllllllllIIllIIllIIlllll.maxValueWidth, lllllllllllllllllIIllIIllIlIIIll);
        }
        lllllllllllllllllIIllIIllIIlllll.root.calculateSize();
        lllllllllllllllllIIllIIllIIlllll.width = lllllllllllllllllIIllIIllIlIIIII + lllllllllllllllllIIllIIllIIlllll.maxValueWidth + lllllllllllllllllIIllIIllIlIIIII + lllllllllllllllllIIllIIllIIlllll.theme.textHeight() + lllllllllllllllllIIllIIllIlIIIII;
        lllllllllllllllllIIllIIllIIlllll.height = lllllllllllllllllIIllIIllIlIIIII + lllllllllllllllllIIllIIllIIlllll.theme.textHeight() + lllllllllllllllllIIllIIllIlIIIII;
        lllllllllllllllllIIllIIllIIlllll.root.width = lllllllllllllllllIIllIIllIIlllll.width;
    }

    public T get() {
        WDropdown lllllllllllllllllIIllIIllIIlIIII;
        return lllllllllllllllllIIllIIllIIlIIII.value;
    }

    @Override
    public void onMouseMoved(double lllllllllllllllllIIllIIlIlIIIlll, double lllllllllllllllllIIllIIlIlIIIllI, double lllllllllllllllllIIllIIlIlIIIlIl, double lllllllllllllllllIIllIIlIlIIlIIl) {
        WDropdown lllllllllllllllllIIllIIlIlIIllIl;
        super.onMouseMoved(lllllllllllllllllIIllIIlIlIIIlll, lllllllllllllllllIIllIIlIlIIIllI, lllllllllllllllllIIllIIlIlIIIlIl, lllllllllllllllllIIllIIlIlIIlIIl);
        if (lllllllllllllllllIIllIIlIlIIllIl.expanded) {
            lllllllllllllllllIIllIIlIlIIllIl.root.mouseMoved(lllllllllllllllllIIllIIlIlIIIlll, lllllllllllllllllIIllIIlIlIIIllI, lllllllllllllllllIIllIIlIlIIIlIl, lllllllllllllllllIIllIIlIlIIlIIl);
        }
    }

    @Override
    protected void onPressed(int lllllllllllllllllIIllIIllIIlIIll) {
        WDropdown lllllllllllllllllIIllIIllIIlIlII;
        lllllllllllllllllIIllIIllIIlIlII.expanded = !lllllllllllllllllIIllIIllIIlIlII.expanded;
    }

    @Override
    public boolean onKeyPressed(int lllllllllllllllllIIllIIlIIllIllI, int lllllllllllllllllIIllIIlIIllIlIl) {
        WDropdown lllllllllllllllllIIllIIlIIlllIlI;
        if (super.onKeyPressed(lllllllllllllllllIIllIIlIIllIllI, lllllllllllllllllIIllIIlIIllIlIl)) {
            return true;
        }
        return lllllllllllllllllIIllIIlIIlllIlI.expanded && lllllllllllllllllIIllIIlIIlllIlI.root.keyPressed(lllllllllllllllllIIllIIlIIllIllI, lllllllllllllllllIIllIIlIIllIlIl);
    }

    protected abstract WDropdownValue createValueWidget();

    @Override
    public boolean onMouseClicked(double lllllllllllllllllIIllIIlIllIIlll, double lllllllllllllllllIIllIIlIllIIllI, int lllllllllllllllllIIllIIlIllIIlIl, boolean lllllllllllllllllIIllIIlIllIIlII) {
        WDropdown lllllllllllllllllIIllIIlIllIlIII;
        if (!lllllllllllllllllIIllIIlIllIlIII.mouseOver && !lllllllllllllllllIIllIIlIllIlIII.root.mouseOver) {
            lllllllllllllllllIIllIIlIllIlIII.expanded = false;
        }
        if (super.onMouseClicked(lllllllllllllllllIIllIIlIllIIlll, lllllllllllllllllIIllIIlIllIIllI, lllllllllllllllllIIllIIlIllIIlIl, lllllllllllllllllIIllIIlIllIIlII)) {
            lllllllllllllllllIIllIIlIllIIlII = true;
        }
        if (lllllllllllllllllIIllIIlIllIlIII.expanded && lllllllllllllllllIIllIIlIllIlIII.root.mouseClicked(lllllllllllllllllIIllIIlIllIIlll, lllllllllllllllllIIllIIlIllIIllI, lllllllllllllllllIIllIIlIllIIlIl, lllllllllllllllllIIllIIlIllIIlII)) {
            lllllllllllllllllIIllIIlIllIIlII = true;
        }
        return lllllllllllllllllIIllIIlIllIIlII;
    }

    public WDropdown(T[] lllllllllllllllllIIllIIllIlllIII, T lllllllllllllllllIIllIIllIlllIlI) {
        WDropdown lllllllllllllllllIIllIIllIllllII;
        lllllllllllllllllIIllIIllIllllII.values = lllllllllllllllllIIllIIllIlllIII;
        lllllllllllllllllIIllIIllIllllII.set(lllllllllllllllllIIllIIllIlllIlI);
    }

    protected abstract WDropdownRoot createRootWidget();

    public void set(T lllllllllllllllllIIllIIllIIIlIll) {
        lllllllllllllllllIIllIIllIIIlIlI.value = lllllllllllllllllIIllIIllIIIlIll;
    }

    @Override
    public boolean onCharTyped(char lllllllllllllllllIIllIIlIIlIlIII) {
        WDropdown lllllllllllllllllIIllIIlIIlIIlll;
        if (super.onCharTyped(lllllllllllllllllIIllIIlIIlIlIII)) {
            return true;
        }
        return lllllllllllllllllIIllIIlIIlIIlll.expanded && lllllllllllllllllIIllIIlIIlIIlll.root.charTyped(lllllllllllllllllIIllIIlIIlIlIII);
    }

    @Override
    protected void onCalculateWidgetPositions() {
        WDropdown lllllllllllllllllIIllIIllIIlIllI;
        super.onCalculateWidgetPositions();
        lllllllllllllllllIIllIIllIIlIllI.root.x = lllllllllllllllllIIllIIllIIlIllI.x;
        lllllllllllllllllIIllIIllIIlIllI.root.y = lllllllllllllllllIIllIIllIIlIllI.y + lllllllllllllllllIIllIIllIIlIllI.height;
        lllllllllllllllllIIllIIllIIlIllI.root.calculateWidgetPositions();
    }

    @Override
    public void onMouseScrolled(double lllllllllllllllllIIllIIlIlIIIIII) {
        WDropdown lllllllllllllllllIIllIIlIIllllll;
        super.onMouseScrolled(lllllllllllllllllIIllIIlIlIIIIII);
        if (lllllllllllllllllIIllIIlIIllllll.expanded) {
            lllllllllllllllllIIllIIlIIllllll.root.mouseScrolled(lllllllllllllllllIIllIIlIlIIIIII);
        }
    }

    @Override
    public boolean onKeyRepeated(int lllllllllllllllllIIllIIlIIllIIII, int lllllllllllllllllIIllIIlIIlIllll) {
        WDropdown lllllllllllllllllIIllIIlIIllIIIl;
        if (super.onKeyRepeated(lllllllllllllllllIIllIIlIIllIIII, lllllllllllllllllIIllIIlIIlIllll)) {
            return true;
        }
        return lllllllllllllllllIIllIIlIIllIIIl.expanded && lllllllllllllllllIIllIIlIIllIIIl.root.keyRepeated(lllllllllllllllllIIllIIlIIllIIII, lllllllllllllllllIIllIIlIIlIllll);
    }

    @Override
    public boolean render(GuiRenderer lllllllllllllllllIIllIIlIlllIIlI, double lllllllllllllllllIIllIIlIlllIIIl, double lllllllllllllllllIIllIIlIlllIllI, double lllllllllllllllllIIllIIlIllIllll) {
        WDropdown lllllllllllllllllIIllIIlIlllIIll;
        boolean lllllllllllllllllIIllIIlIlllIlII = super.render(lllllllllllllllllIIllIIlIlllIIlI, lllllllllllllllllIIllIIlIlllIIIl, lllllllllllllllllIIllIIlIlllIllI, lllllllllllllllllIIllIIlIllIllll);
        lllllllllllllllllIIllIIlIlllIIll.animProgress += (double)(lllllllllllllllllIIllIIlIlllIIll.expanded ? 1 : -1) * lllllllllllllllllIIllIIlIllIllll * 14.0;
        lllllllllllllllllIIllIIlIlllIIll.animProgress = Utils.clamp(lllllllllllllllllIIllIIlIlllIIll.animProgress, 0.0, 1.0);
        if (!lllllllllllllllllIIllIIlIlllIlII && lllllllllllllllllIIllIIlIlllIIll.animProgress > 0.0) {
            lllllllllllllllllIIllIIlIlllIIlI.absolutePost(() -> {
                WDropdown lllllllllllllllllIIllIIlIIIllIll;
                lllllllllllllllllIIllIIlIlllIIlI.scissorStart(lllllllllllllllllIIllIIlIIIllIll.x, lllllllllllllllllIIllIIlIIIllIll.y + lllllllllllllllllIIllIIlIIIllIll.height, lllllllllllllllllIIllIIlIIIllIll.width, lllllllllllllllllIIllIIlIIIllIll.root.height * lllllllllllllllllIIllIIlIIIllIll.animProgress);
                lllllllllllllllllIIllIIlIIIllIll.root.render(lllllllllllllllllIIllIIlIlllIIlI, lllllllllllllllllIIllIIlIlllIIIl, lllllllllllllllllIIllIIlIlllIllI, lllllllllllllllllIIllIIlIllIllll);
                lllllllllllllllllIIllIIlIlllIIlI.scissorEnd();
            });
        }
        if (lllllllllllllllllIIllIIlIlllIIll.expanded && lllllllllllllllllIIllIIlIlllIIll.root.mouseOver) {
            lllllllllllllllllIIllIIlIlllIIll.theme.disableHoverColor = true;
        }
        return lllllllllllllllllIIllIIlIlllIlII;
    }

    protected static abstract class WDropdownRoot
    extends WVerticalList
    implements WRoot {
        @Override
        public void invalidate() {
        }

        protected WDropdownRoot() {
            WDropdownRoot lIIlIlIlIIIlll;
        }
    }

    protected abstract class WDropdownValue
    extends WPressable {
        protected /* synthetic */ T value;

        protected WDropdownValue() {
            WDropdownValue llllllllllllllllllllIIlllllIlIll;
        }

        @Override
        protected void onPressed(int llllllllllllllllllllIIlllllIIllI) {
            WDropdownValue llllllllllllllllllllIIlllllIIlll;
            boolean llllllllllllllllllllIIlllllIIlIl = !llllllllllllllllllllIIlllllIIlll.WDropdown.this.value.equals(llllllllllllllllllllIIlllllIIlll.value);
            llllllllllllllllllllIIlllllIIlll.WDropdown.this.value = llllllllllllllllllllIIlllllIIlll.value;
            llllllllllllllllllllIIlllllIIlll.WDropdown.this.expanded = false;
            if (llllllllllllllllllllIIlllllIIlIl && llllllllllllllllllllIIlllllIIlll.WDropdown.this.action != null) {
                llllllllllllllllllllIIlllllIIlll.WDropdown.this.action.run();
            }
        }
    }
}

