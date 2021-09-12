/*
 * Decompiled with CFR 0.151.
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
    protected double animProgress;
    protected T[] values;
    protected T value;
    protected WDropdownRoot root;
    protected double maxValueWidth;
    protected boolean expanded;
    public Runnable action;

    @Override
    public boolean onCharTyped(char c) {
        if (super.onCharTyped(c)) {
            return true;
        }
        return this.expanded && this.root.charTyped(c);
    }

    @Override
    public boolean onMouseReleased(double d, double d2, int n) {
        if (super.onMouseReleased(d, d2, n)) {
            return true;
        }
        return this.expanded && this.root.mouseReleased(d, d2, n);
    }

    @Override
    protected void onPressed(int n) {
        this.expanded = !this.expanded;
    }

    public T get() {
        return this.value;
    }

    @Override
    protected void onCalculateSize() {
        double d = this.pad();
        this.maxValueWidth = 0.0;
        for (T t : this.values) {
            double d2 = this.theme.textWidth(t.toString());
            this.maxValueWidth = Math.max(this.maxValueWidth, d2);
            if (false < true) continue;
            return;
        }
        this.root.calculateSize();
        this.width = d + this.maxValueWidth + d + this.theme.textHeight() + d;
        this.height = d + this.theme.textHeight() + d;
        this.root.width = this.width;
    }

    @Override
    public void onMouseScrolled(double d) {
        super.onMouseScrolled(d);
        if (this.expanded) {
            this.root.mouseScrolled(d);
        }
    }

    @Override
    public boolean onKeyRepeated(int n, int n2) {
        if (super.onKeyRepeated(n, n2)) {
            return true;
        }
        return this.expanded && this.root.keyRepeated(n, n2);
    }

    private void lambda$render$0(GuiRenderer guiRenderer, double d, double d2, double d3) {
        guiRenderer.scissorStart(this.x, this.y + this.height, this.width, this.root.height * this.animProgress);
        this.root.render(guiRenderer, d, d2, d3);
        guiRenderer.scissorEnd();
    }

    @Override
    public boolean onKeyPressed(int n, int n2) {
        if (super.onKeyPressed(n, n2)) {
            return true;
        }
        return this.expanded && this.root.keyPressed(n, n2);
    }

    @Override
    public boolean render(GuiRenderer guiRenderer, double d, double d2, double d3) {
        boolean bl = super.render(guiRenderer, d, d2, d3);
        this.animProgress += (double)(this.expanded ? 1 : -1) * d3 * 14.0;
        this.animProgress = Utils.clamp(this.animProgress, 0.0, 1.0);
        if (!bl && this.animProgress > 0.0) {
            guiRenderer.absolutePost(() -> this.lambda$render$0(guiRenderer, d, d2, d3));
        }
        if (this.expanded && this.root.mouseOver) {
            this.theme.disableHoverColor = true;
        }
        return bl;
    }

    public WDropdown(T[] TArray, T t) {
        this.values = TArray;
        this.set(t);
    }

    @Override
    public void onMouseMoved(double d, double d2, double d3, double d4) {
        super.onMouseMoved(d, d2, d3, d4);
        if (this.expanded) {
            this.root.mouseMoved(d, d2, d3, d4);
        }
    }

    public void set(T t) {
        this.value = t;
    }

    protected abstract WDropdownRoot createRootWidget();

    @Override
    protected void onCalculateWidgetPositions() {
        super.onCalculateWidgetPositions();
        this.root.x = this.x;
        this.root.y = this.y + this.height;
        this.root.calculateWidgetPositions();
    }

    @Override
    public boolean onMouseClicked(double d, double d2, int n, boolean bl) {
        if (!this.mouseOver && !this.root.mouseOver) {
            this.expanded = false;
        }
        if (super.onMouseClicked(d, d2, n, bl)) {
            bl = true;
        }
        if (this.expanded && this.root.mouseClicked(d, d2, n, bl)) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void move(double d, double d2) {
        super.move(d, d2);
        this.root.move(d, d2);
    }

    @Override
    public void init() {
        this.root = this.createRootWidget();
        this.root.theme = this.theme;
        this.root.spacing = 0.0;
        for (int i = 0; i < this.values.length; ++i) {
            WDropdownValue wDropdownValue = this.createValueWidget();
            wDropdownValue.theme = this.theme;
            wDropdownValue.value = this.values[i];
            Cell<WDropdownValue> cell = this.root.add(wDropdownValue).padHorizontal(2.0).expandWidgetX();
            if (i < this.values.length - 1) continue;
            cell.padBottom(2.0);
        }
    }

    protected abstract WDropdownValue createValueWidget();

    protected static abstract class WDropdownRoot
    extends WVerticalList
    implements WRoot {
        @Override
        public void invalidate() {
        }

        protected WDropdownRoot() {
        }
    }

    protected abstract class WDropdownValue
    extends WPressable {
        final WDropdown this$0;
        protected T value;

        protected WDropdownValue(WDropdown wDropdown) {
            this.this$0 = wDropdown;
        }

        @Override
        protected void onPressed(int n) {
            boolean bl = !this.this$0.value.equals(this.value);
            this.this$0.value = this.value;
            this.this$0.expanded = false;
            if (bl && this.this$0.action != null) {
                this.this$0.action.run();
            }
        }
    }
}

