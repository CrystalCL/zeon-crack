/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.widgets.WQuad;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorQuad
extends WQuad {
    @Override
    protected void onRender(GuiRenderer guiRenderer, double d, double d2, double d3) {
        guiRenderer.quad(this.x, this.y, this.width, this.height, this.color);
    }

    public WMeteorQuad(Color color) {
        super(color);
    }
}

