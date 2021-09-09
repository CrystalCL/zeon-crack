/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
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
    public /* synthetic */ double spacing;
    private final /* synthetic */ DoubleList rowHeights;
    private final /* synthetic */ IntList rowExpandCellXCounts;
    private /* synthetic */ int rowI;
    private final /* synthetic */ DoubleList rowWidths;
    private final /* synthetic */ DoubleList columnWidths;
    private final /* synthetic */ List<List<Cell<?>>> rows;

    @Override
    public void clear() {
        WTable lllllllllllllllllIIIlllIIIlllIII;
        super.clear();
        lllllllllllllllllIIIlllIIIlllIII.rows.clear();
        lllllllllllllllllIIIlllIIIlllIII.rowI = 0;
    }

    private void calculateInfo() {
        WTable lllllllllllllllllIIIllIllllllIII;
        lllllllllllllllllIIIllIllllllIII.rowHeights.clear();
        lllllllllllllllllIIIllIllllllIII.columnWidths.clear();
        lllllllllllllllllIIIllIllllllIII.rowExpandCellXCounts.clear();
        for (List<Cell<?>> lllllllllllllllllIIIllIllllllIIl : lllllllllllllllllIIIllIllllllIII.rows) {
            double lllllllllllllllllIIIllIllllllIll = 0.0;
            int lllllllllllllllllIIIllIllllllIlI = 0;
            for (int lllllllllllllllllIIIllIlllllllII = 0; lllllllllllllllllIIIllIlllllllII < lllllllllllllllllIIIllIllllllIIl.size(); ++lllllllllllllllllIIIllIlllllllII) {
                Cell<?> lllllllllllllllllIIIllIllllllllI = lllllllllllllllllIIIllIllllllIIl.get(lllllllllllllllllIIIllIlllllllII);
                lllllllllllllllllIIIllIllllllIll = Math.max(lllllllllllllllllIIIllIllllllIll, lllllllllllllllllIIIllIllllllllI.padTop() + ((WWidget)lllllllllllllllllIIIllIllllllllI.widget()).height + lllllllllllllllllIIIllIllllllllI.padBottom());
                double lllllllllllllllllIIIllIlllllllIl = lllllllllllllllllIIIllIllllllllI.padLeft() + ((WWidget)lllllllllllllllllIIIllIllllllllI.widget()).width + lllllllllllllllllIIIllIllllllllI.padRight();
                if (lllllllllllllllllIIIllIllllllIII.columnWidths.size() <= lllllllllllllllllIIIllIlllllllII) {
                    lllllllllllllllllIIIllIllllllIII.columnWidths.add(lllllllllllllllllIIIllIlllllllIl);
                } else {
                    lllllllllllllllllIIIllIllllllIII.columnWidths.set(lllllllllllllllllIIIllIlllllllII, Math.max(lllllllllllllllllIIIllIllllllIII.columnWidths.getDouble(lllllllllllllllllIIIllIlllllllII), lllllllllllllllllIIIllIlllllllIl));
                }
                if (!lllllllllllllllllIIIllIllllllllI.expandCellX) continue;
                ++lllllllllllllllllIIIllIllllllIlI;
            }
            lllllllllllllllllIIIllIllllllIII.rowHeights.add(lllllllllllllllllIIIllIllllllIll);
            lllllllllllllllllIIIllIllllllIII.rowExpandCellXCounts.add(lllllllllllllllllIIIllIllllllIlI);
        }
    }

    @Override
    protected void onCalculateSize() {
        WTable lllllllllllllllllIIIlllIIIlIlIlI;
        lllllllllllllllllIIIlllIIIlIlIlI.calculateInfo();
        lllllllllllllllllIIIlllIIIlIlIlI.rowWidths.clear();
        lllllllllllllllllIIIlllIIIlIlIlI.width = 0.0;
        lllllllllllllllllIIIlllIIIlIlIlI.height = 0.0;
        for (int lllllllllllllllllIIIlllIIIlIlIll = 0; lllllllllllllllllIIIlllIIIlIlIll < lllllllllllllllllIIIlllIIIlIlIlI.rows.size(); ++lllllllllllllllllIIIlllIIIlIlIll) {
            List<Cell<?>> lllllllllllllllllIIIlllIIIlIllIl = lllllllllllllllllIIIlllIIIlIlIlI.rows.get(lllllllllllllllllIIIlllIIIlIlIll);
            double lllllllllllllllllIIIlllIIIlIllII = 0.0;
            for (int lllllllllllllllllIIIlllIIIlIlllI = 0; lllllllllllllllllIIIlllIIIlIlllI < lllllllllllllllllIIIlllIIIlIllIl.size(); ++lllllllllllllllllIIIlllIIIlIlllI) {
                if (lllllllllllllllllIIIlllIIIlIlllI > 0) {
                    lllllllllllllllllIIIlllIIIlIllII += lllllllllllllllllIIIlllIIIlIlIlI.spacing();
                }
                lllllllllllllllllIIIlllIIIlIllII += lllllllllllllllllIIIlllIIIlIlIlI.columnWidths.getDouble(lllllllllllllllllIIIlllIIIlIlllI);
            }
            lllllllllllllllllIIIlllIIIlIlIlI.rowWidths.add(lllllllllllllllllIIIlllIIIlIllII);
            lllllllllllllllllIIIlllIIIlIlIlI.width = Math.max(lllllllllllllllllIIIlllIIIlIlIlI.width, lllllllllllllllllIIIlllIIIlIllII);
            if (lllllllllllllllllIIIlllIIIlIlIll > 0) {
                lllllllllllllllllIIIlllIIIlIlIlI.height += lllllllllllllllllIIIlllIIIlIlIlI.spacing();
            }
            lllllllllllllllllIIIlllIIIlIlIlI.height += lllllllllllllllllIIIlllIIIlIlIlI.rowHeights.getDouble(lllllllllllllllllIIIlllIIIlIlIll);
        }
    }

    @Override
    protected void onCalculateWidgetPositions() {
        WTable lllllllllllllllllIIIlllIIIIlIIlI;
        double lllllllllllllllllIIIlllIIIIlIIIl = lllllllllllllllllIIIlllIIIIlIIlI.y;
        for (int lllllllllllllllllIIIlllIIIIlIIll = 0; lllllllllllllllllIIIlllIIIIlIIll < lllllllllllllllllIIIlllIIIIlIIlI.rows.size(); ++lllllllllllllllllIIIlllIIIIlIIll) {
            List<Cell<?>> lllllllllllllllllIIIlllIIIIlIlll = lllllllllllllllllIIIlllIIIIlIIlI.rows.get(lllllllllllllllllIIIlllIIIIlIIll);
            if (lllllllllllllllllIIIlllIIIIlIIll > 0) {
                lllllllllllllllllIIIlllIIIIlIIIl += lllllllllllllllllIIIlllIIIIlIIlI.spacing();
            }
            double lllllllllllllllllIIIlllIIIIlIllI = lllllllllllllllllIIIlllIIIIlIIlI.x;
            double lllllllllllllllllIIIlllIIIIlIlIl = lllllllllllllllllIIIlllIIIIlIIlI.rowHeights.getDouble(lllllllllllllllllIIIlllIIIIlIIll);
            double lllllllllllllllllIIIlllIIIIlIlII = lllllllllllllllllIIIlllIIIIlIIlI.rowExpandCellXCounts.getInt(lllllllllllllllllIIIlllIIIIlIIll) > 0 ? (lllllllllllllllllIIIlllIIIIlIIlI.width - lllllllllllllllllIIIlllIIIIlIIlI.rowWidths.getDouble(lllllllllllllllllIIIlllIIIIlIIll)) / (double)lllllllllllllllllIIIlllIIIIlIIlI.rowExpandCellXCounts.getInt(lllllllllllllllllIIIlllIIIIlIIll) : 0.0;
            for (int lllllllllllllllllIIIlllIIIIllIII = 0; lllllllllllllllllIIIlllIIIIllIII < lllllllllllllllllIIIlllIIIIlIlll.size(); ++lllllllllllllllllIIIlllIIIIllIII) {
                Cell<?> lllllllllllllllllIIIlllIIIIllIlI = lllllllllllllllllIIIlllIIIIlIlll.get(lllllllllllllllllIIIlllIIIIllIII);
                if (lllllllllllllllllIIIlllIIIIllIII > 0) {
                    lllllllllllllllllIIIlllIIIIlIllI += lllllllllllllllllIIIlllIIIIlIIlI.spacing();
                }
                double lllllllllllllllllIIIlllIIIIllIIl = lllllllllllllllllIIIlllIIIIlIIlI.columnWidths.getDouble(lllllllllllllllllIIIlllIIIIllIII);
                lllllllllllllllllIIIlllIIIIllIlI.x = lllllllllllllllllIIIlllIIIIlIllI;
                lllllllllllllllllIIIlllIIIIllIlI.y = lllllllllllllllllIIIlllIIIIlIIIl;
                lllllllllllllllllIIIlllIIIIllIlI.width = lllllllllllllllllIIIlllIIIIllIIl + (lllllllllllllllllIIIlllIIIIllIlI.expandCellX ? lllllllllllllllllIIIlllIIIIlIlII : 0.0);
                lllllllllllllllllIIIlllIIIIllIlI.height = lllllllllllllllllIIIlllIIIIlIlIl;
                lllllllllllllllllIIIlllIIIIllIlI.alignWidget();
                lllllllllllllllllIIIlllIIIIlIllI += lllllllllllllllllIIIlllIIIIllIIl + (lllllllllllllllllIIIlllIIIIllIlI.expandCellX ? lllllllllllllllllIIIlllIIIIlIlII : 0.0);
            }
            lllllllllllllllllIIIlllIIIIlIIIl += lllllllllllllllllIIIlllIIIIlIlIl;
        }
    }

    public WTable() {
        WTable lllllllllllllllllIIIlllIIlIIlIlI;
        lllllllllllllllllIIIlllIIlIIlIlI.spacing = 3.0;
        lllllllllllllllllIIIlllIIlIIlIlI.rows = new ArrayList();
        lllllllllllllllllIIIlllIIlIIlIlI.rowHeights = new DoubleArrayList();
        lllllllllllllllllIIIlllIIlIIlIlI.columnWidths = new DoubleArrayList();
        lllllllllllllllllIIIlllIIlIIlIlI.rowWidths = new DoubleArrayList();
        lllllllllllllllllIIIlllIIlIIlIlI.rowExpandCellXCounts = new IntArrayList();
    }

    protected double spacing() {
        WTable lllllllllllllllllIIIlllIIIllIlIl;
        return lllllllllllllllllIIIlllIIIllIlIl.theme.scale(lllllllllllllllllIIIlllIIIllIlIl.spacing);
    }

    @Override
    public <T extends WWidget> Cell<T> add(T lllllllllllllllllIIIlllIIlIIIIlI) {
        WTable lllllllllllllllllIIIlllIIlIIIIll;
        Cell<T> lllllllllllllllllIIIlllIIlIIIIIl = super.add(lllllllllllllllllIIIlllIIlIIIIlI);
        if (lllllllllllllllllIIIlllIIlIIIIll.rows.size() <= lllllllllllllllllIIIlllIIlIIIIll.rowI) {
            ArrayList<Cell<T>> lllllllllllllllllIIIlllIIlIIIlII = new ArrayList<Cell<T>>();
            lllllllllllllllllIIIlllIIlIIIlII.add(lllllllllllllllllIIIlllIIlIIIIIl);
            lllllllllllllllllIIIlllIIlIIIIll.rows.add(lllllllllllllllllIIIlllIIlIIIlII);
        } else {
            lllllllllllllllllIIIlllIIlIIIIll.rows.get(lllllllllllllllllIIIlllIIlIIIIll.rowI).add(lllllllllllllllllIIIlllIIlIIIIIl);
        }
        return lllllllllllllllllIIIlllIIlIIIIIl;
    }

    public void row() {
        WTable lllllllllllllllllIIIlllIIIlllIlI;
        ++lllllllllllllllllIIIlllIIIlllIlI.rowI;
    }
}

