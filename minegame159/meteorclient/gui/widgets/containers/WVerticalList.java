/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.containers;

import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WContainer;

public class WVerticalList
extends WContainer {
    protected /* synthetic */ double widthRemove;
    public /* synthetic */ double spacing;

    public WVerticalList() {
        WVerticalList lllllllllllllllllIlIIllIIIlIlIIl;
        lllllllllllllllllIlIIllIIIlIlIIl.spacing = 3.0;
    }

    @Override
    protected void onCalculateWidgetPositions() {
        WVerticalList lllllllllllllllllIlIIllIIIIlIIll;
        double lllllllllllllllllIlIIllIIIIlIlII = lllllllllllllllllIlIIllIIIIlIIll.y;
        for (int lllllllllllllllllIlIIllIIIIlIllI = 0; lllllllllllllllllIlIIllIIIIlIllI < lllllllllllllllllIlIIllIIIIlIIll.cells.size(); ++lllllllllllllllllIlIIllIIIIlIllI) {
            Cell lllllllllllllllllIlIIllIIIIlIlll = (Cell)lllllllllllllllllIlIIllIIIIlIIll.cells.get(lllllllllllllllllIlIIllIIIIlIllI);
            if (lllllllllllllllllIlIIllIIIIlIllI > 0) {
                lllllllllllllllllIlIIllIIIIlIlII += lllllllllllllllllIlIIllIIIIlIIll.spacing();
            }
            lllllllllllllllllIlIIllIIIIlIlll.x = lllllllllllllllllIlIIllIIIIlIIll.x + lllllllllllllllllIlIIllIIIIlIlll.padLeft();
            lllllllllllllllllIlIIllIIIIlIlll.y = lllllllllllllllllIlIIllIIIIlIlII += lllllllllllllllllIlIIllIIIIlIlll.padTop();
            lllllllllllllllllIlIIllIIIIlIlll.width = lllllllllllllllllIlIIllIIIIlIIll.width - lllllllllllllllllIlIIllIIIIlIIll.widthRemove - lllllllllllllllllIlIIllIIIIlIlll.padLeft() - lllllllllllllllllIlIIllIIIIlIlll.padRight();
            lllllllllllllllllIlIIllIIIIlIlll.height = ((WWidget)lllllllllllllllllIlIIllIIIIlIlll.widget()).height;
            lllllllllllllllllIlIIllIIIIlIlll.alignWidget();
            lllllllllllllllllIlIIllIIIIlIlII += lllllllllllllllllIlIIllIIIIlIlll.height + lllllllllllllllllIlIIllIIIIlIlll.padBottom();
        }
    }

    protected double spacing() {
        WVerticalList lllllllllllllllllIlIIllIIIlIIllI;
        return lllllllllllllllllIlIIllIIIlIIllI.theme.scale(lllllllllllllllllIlIIllIIIlIIllI.spacing);
    }

    @Override
    protected void onCalculateSize() {
        WVerticalList lllllllllllllllllIlIIllIIIIllllI;
        lllllllllllllllllIlIIllIIIIllllI.width = 0.0;
        lllllllllllllllllIlIIllIIIIllllI.height = 0.0;
        for (int lllllllllllllllllIlIIllIIIlIIIII = 0; lllllllllllllllllIlIIllIIIlIIIII < lllllllllllllllllIlIIllIIIIllllI.cells.size(); ++lllllllllllllllllIlIIllIIIlIIIII) {
            Cell lllllllllllllllllIlIIllIIIlIIIIl = (Cell)lllllllllllllllllIlIIllIIIIllllI.cells.get(lllllllllllllllllIlIIllIIIlIIIII);
            if (lllllllllllllllllIlIIllIIIlIIIII > 0) {
                lllllllllllllllllIlIIllIIIIllllI.height += lllllllllllllllllIlIIllIIIIllllI.spacing();
            }
            lllllllllllllllllIlIIllIIIIllllI.width = Math.max(lllllllllllllllllIlIIllIIIIllllI.width, lllllllllllllllllIlIIllIIIlIIIIl.padLeft() + ((WWidget)lllllllllllllllllIlIIllIIIlIIIIl.widget()).width + lllllllllllllllllIlIIllIIIlIIIIl.padRight());
            lllllllllllllllllIlIIllIIIIllllI.height += lllllllllllllllllIlIIllIIIlIIIIl.padTop() + ((WWidget)lllllllllllllllllIlIIllIIIlIIIIl.widget()).height + lllllllllllllllllIlIIllIIIlIIIIl.padBottom();
        }
    }
}

