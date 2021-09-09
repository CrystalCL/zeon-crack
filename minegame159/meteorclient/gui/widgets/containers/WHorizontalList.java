/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.containers;

import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WContainer;

public class WHorizontalList
extends WContainer {
    protected /* synthetic */ double calculatedWidth;
    protected /* synthetic */ int fillXCount;
    public /* synthetic */ double spacing;

    public WHorizontalList() {
        WHorizontalList llllllllllllllllIlIllIIlIIllIllI;
        llllllllllllllllIlIllIIlIIllIllI.spacing = 3.0;
    }

    protected double spacing() {
        WHorizontalList llllllllllllllllIlIllIIlIIllIIlI;
        return llllllllllllllllIlIllIIlIIllIIlI.theme.scale(llllllllllllllllIlIllIIlIIllIIlI.spacing);
    }

    @Override
    protected void onCalculateWidgetPositions() {
        WHorizontalList llllllllllllllllIlIllIIlIIlIIIIl;
        double llllllllllllllllIlIllIIlIIlIIIII = llllllllllllllllIlIllIIlIIlIIIIl.x;
        double llllllllllllllllIlIllIIlIIIlllll = (llllllllllllllllIlIllIIlIIlIIIIl.width - llllllllllllllllIlIllIIlIIlIIIIl.calculatedWidth) / (double)llllllllllllllllIlIllIIlIIlIIIIl.fillXCount;
        for (int llllllllllllllllIlIllIIlIIlIIIlI = 0; llllllllllllllllIlIllIIlIIlIIIlI < llllllllllllllllIlIllIIlIIlIIIIl.cells.size(); ++llllllllllllllllIlIllIIlIIlIIIlI) {
            Cell llllllllllllllllIlIllIIlIIlIIIll = (Cell)llllllllllllllllIlIllIIlIIlIIIIl.cells.get(llllllllllllllllIlIllIIlIIlIIIlI);
            if (llllllllllllllllIlIllIIlIIlIIIlI > 0) {
                llllllllllllllllIlIllIIlIIlIIIII += llllllllllllllllIlIllIIlIIlIIIIl.spacing();
            }
            llllllllllllllllIlIllIIlIIlIIIll.x = llllllllllllllllIlIllIIlIIlIIIII += llllllllllllllllIlIllIIlIIlIIIll.padLeft();
            llllllllllllllllIlIllIIlIIlIIIll.y = llllllllllllllllIlIllIIlIIlIIIIl.y + llllllllllllllllIlIllIIlIIlIIIll.padTop();
            llllllllllllllllIlIllIIlIIlIIIll.width = ((WWidget)llllllllllllllllIlIllIIlIIlIIIll.widget()).width;
            llllllllllllllllIlIllIIlIIlIIIll.height = llllllllllllllllIlIllIIlIIlIIIIl.height - llllllllllllllllIlIllIIlIIlIIIll.padTop() - llllllllllllllllIlIllIIlIIlIIIll.padTop();
            if (llllllllllllllllIlIllIIlIIlIIIll.expandCellX) {
                llllllllllllllllIlIllIIlIIlIIIll.width += llllllllllllllllIlIllIIlIIIlllll;
            }
            llllllllllllllllIlIllIIlIIlIIIll.alignWidget();
            llllllllllllllllIlIllIIlIIlIIIII += llllllllllllllllIlIllIIlIIlIIIll.width + llllllllllllllllIlIllIIlIIlIIIll.padRight();
        }
    }

    @Override
    protected void onCalculateSize() {
        WHorizontalList llllllllllllllllIlIllIIlIIlIlIll;
        llllllllllllllllIlIllIIlIIlIlIll.width = 0.0;
        llllllllllllllllIlIllIIlIIlIlIll.height = 0.0;
        llllllllllllllllIlIllIIlIIlIlIll.fillXCount = 0;
        for (int llllllllllllllllIlIllIIlIIlIllIl = 0; llllllllllllllllIlIllIIlIIlIllIl < llllllllllllllllIlIllIIlIIlIlIll.cells.size(); ++llllllllllllllllIlIllIIlIIlIllIl) {
            Cell llllllllllllllllIlIllIIlIIlIlllI = (Cell)llllllllllllllllIlIllIIlIIlIlIll.cells.get(llllllllllllllllIlIllIIlIIlIllIl);
            if (llllllllllllllllIlIllIIlIIlIllIl > 0) {
                llllllllllllllllIlIllIIlIIlIlIll.width += llllllllllllllllIlIllIIlIIlIlIll.spacing();
            }
            llllllllllllllllIlIllIIlIIlIlIll.width += llllllllllllllllIlIllIIlIIlIlllI.padLeft() + ((WWidget)llllllllllllllllIlIllIIlIIlIlllI.widget()).width + llllllllllllllllIlIllIIlIIlIlllI.padRight();
            llllllllllllllllIlIllIIlIIlIlIll.height = Math.max(llllllllllllllllIlIllIIlIIlIlIll.height, llllllllllllllllIlIllIIlIIlIlllI.padTop() + ((WWidget)llllllllllllllllIlIllIIlIIlIlllI.widget()).height + llllllllllllllllIlIllIIlIIlIlllI.padBottom());
            if (!llllllllllllllllIlIllIIlIIlIlllI.expandCellX) continue;
            ++llllllllllllllllIlIllIIlIIlIlIll.fillXCount;
        }
        llllllllllllllllIlIllIIlIIlIlIll.calculatedWidth = llllllllllllllllIlIllIIlIIlIlIll.width;
    }
}

