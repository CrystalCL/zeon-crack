/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.widgets.WWidget;
import net.minecraft.client.texture.AbstractTexture;

public class WTexture
extends WWidget {
    private final double height;
    private final double rotation;
    private final double width;
    private final AbstractTexture texture;

    @Override
    protected void onCalculateSize() {
        ((WWidget)this).width = this.theme.scale(this.width);
        ((WWidget)this).height = this.theme.scale(this.height);
    }

    public WTexture(double d, double d2, double d3, AbstractTexture AbstractTexture2) {
        this.width = d;
        this.height = d2;
        this.rotation = d3;
        this.texture = AbstractTexture2;
    }

    @Override
    protected void onRender(GuiRenderer guiRenderer, double d, double d2, double d3) {
        guiRenderer.texture(this.x, this.y, ((WWidget)this).width, ((WWidget)this).height, this.rotation, this.texture);
    }
}

