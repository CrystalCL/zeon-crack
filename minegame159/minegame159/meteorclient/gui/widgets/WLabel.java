/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.utils.render.color.Color;

public abstract class WLabel
extends WWidget {
    protected String text;
    protected boolean title;
    public Color color;

    public void set(String string) {
        if ((double)Math.round(this.theme.textWidth(string, string.length(), this.title)) != this.width) {
            this.invalidate();
        }
        this.text = string;
    }

    @Override
    protected void onCalculateSize() {
        this.width = this.theme.textWidth(this.text, this.text.length(), this.title);
        this.height = this.theme.textHeight(this.title);
    }

    public WLabel(String string, boolean bl) {
        this.text = string;
        this.title = bl;
    }

    public String get() {
        return this.text;
    }
}

