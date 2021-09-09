/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.widgets.WWidget;

public abstract class WHorizontalSeparator
extends WWidget {
    protected /* synthetic */ double textWidth;
    protected /* synthetic */ String text;

    @Override
    protected void onCalculateSize() {
        WHorizontalSeparator lllIIlIIIIllI;
        if (lllIIlIIIIllI.text != null) {
            lllIIlIIIIllI.textWidth = lllIIlIIIIllI.theme.textWidth(lllIIlIIIIllI.text);
        }
        lllIIlIIIIllI.width = 1.0;
        lllIIlIIIIllI.height = lllIIlIIIIllI.text != null ? lllIIlIIIIllI.theme.textHeight() : lllIIlIIIIllI.theme.scale(3.0);
    }

    public WHorizontalSeparator(String lllIIlIIIlIll) {
        WHorizontalSeparator lllIIlIIIlIlI;
        lllIIlIIIlIlI.text = lllIIlIIIlIll;
    }
}

