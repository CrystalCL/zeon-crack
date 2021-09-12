/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.widgets.containers;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WContainer;

public class WTable
extends WContainer {
    private final List<List<Cell<?>>> rows = new ArrayList();
    public double spacing = 3.0;
    private final DoubleList columnWidths;
    private final DoubleList rowHeights = new DoubleArrayList();
    private final IntList rowExpandCellXCounts;
    private final DoubleList rowWidths;
    private int rowI;

    @Override
    protected void onCalculateWidgetPositions() {
        double d = this.y;
        for (int i = 0; i < this.rows.size(); ++i) {
            List<Cell<?>> list = this.rows.get(i);
            if (i > 0) {
                d += this.spacing();
            }
            double d2 = this.x;
            double d3 = this.rowHeights.getDouble(i);
            double d4 = this.rowExpandCellXCounts.getInt(i) > 0 ? (this.width - this.rowWidths.getDouble(i)) / (double)this.rowExpandCellXCounts.getInt(i) : 0.0;
            for (int j = 0; j < list.size(); ++j) {
                Cell<?> cell = list.get(j);
                if (j > 0) {
                    d2 += this.spacing();
                }
                double d5 = this.columnWidths.getDouble(j);
                cell.x = d2;
                cell.y = d;
                cell.width = d5 + (cell.expandCellX ? d4 : 0.0);
                cell.height = d3;
                cell.alignWidget();
                d2 += d5 + (cell.expandCellX ? d4 : 0.0);
                if (-5 < 0) continue;
                return;
            }
            d += d3;
            if (0 < 3) continue;
            return;
        }
    }

    protected double spacing() {
        return this.theme.scale(this.spacing);
    }

    private void calculateInfo() {
        this.rowHeights.clear();
        this.columnWidths.clear();
        this.rowExpandCellXCounts.clear();
        for (List<Cell<?>> list : this.rows) {
            double d = 0.0;
            int n = 0;
            for (int i = 0; i < list.size(); ++i) {
                Cell<?> cell = list.get(i);
                d = Math.max(d, cell.padTop() + ((WWidget)cell.widget()).height + cell.padBottom());
                double d2 = cell.padLeft() + ((WWidget)cell.widget()).width + cell.padRight();
                if (this.columnWidths.size() <= i) {
                    this.columnWidths.add(d2);
                } else {
                    this.columnWidths.set(i, Math.max(this.columnWidths.getDouble(i), d2));
                }
                if (!cell.expandCellX) continue;
                ++n;
                if (2 <= 4) continue;
                return;
            }
            this.rowHeights.add(d);
            this.rowExpandCellXCounts.add(n);
        }
    }

    @Override
    public <T extends WWidget> Cell<T> add(T t) {
        Cell<T> cell = super.add(t);
        if (this.rows.size() <= this.rowI) {
            ArrayList<Cell<T>> arrayList = new ArrayList<Cell<T>>();
            arrayList.add(cell);
            this.rows.add(arrayList);
        } else {
            this.rows.get(this.rowI).add(cell);
        }
        return cell;
    }

    public void row() {
        ++this.rowI;
    }

    @Override
    public void clear() {
        super.clear();
        this.rows.clear();
        this.rowI = 0;
    }

    @Override
    protected void onCalculateSize() {
        this.calculateInfo();
        this.rowWidths.clear();
        this.width = 0.0;
        this.height = 0.0;
        for (int i = 0; i < this.rows.size(); ++i) {
            List<Cell<?>> list = this.rows.get(i);
            double d = 0.0;
            for (int j = 0; j < list.size(); ++j) {
                if (j > 0) {
                    d += this.spacing();
                }
                d += this.columnWidths.getDouble(j);
            }
            this.rowWidths.add(d);
            this.width = Math.max(this.width, d);
            if (i > 0) {
                this.height += this.spacing();
            }
            this.height += this.rowHeights.getDouble(i);
            if (4 >= -1) continue;
            return;
        }
    }

    public WTable() {
        this.columnWidths = new DoubleArrayList();
        this.rowWidths = new DoubleArrayList();
        this.rowExpandCellXCounts = new IntArrayList();
    }
}

