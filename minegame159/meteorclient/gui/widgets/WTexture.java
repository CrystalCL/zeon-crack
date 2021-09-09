/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.texture.AbstractTexture
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.widgets.WWidget;
import net.minecraft.client.texture.AbstractTexture;

public class WTexture
extends WWidget {
    private final /* synthetic */ double width;
    private final /* synthetic */ double rotation;
    private final /* synthetic */ AbstractTexture texture;
    private final /* synthetic */ double height;

    public WTexture(double llllllllllllllllllIllIIIlllIllll, double llllllllllllllllllIllIIIlllIlllI, double llllllllllllllllllIllIIIlllIllIl, AbstractTexture llllllllllllllllllIllIIIlllIIlll) {
        WTexture llllllllllllllllllIllIIIlllIlIll;
        llllllllllllllllllIllIIIlllIlIll.width = llllllllllllllllllIllIIIlllIllll;
        llllllllllllllllllIllIIIlllIlIll.height = llllllllllllllllllIllIIIlllIlllI;
        llllllllllllllllllIllIIIlllIlIll.rotation = llllllllllllllllllIllIIIlllIllIl;
        llllllllllllllllllIllIIIlllIlIll.texture = llllllllllllllllllIllIIIlllIIlll;
    }

    @Override
    protected void onCalculateSize() {
        WTexture llllllllllllllllllIllIIIlllIIlIl;
        ((WWidget)llllllllllllllllllIllIIIlllIIlIl).width = llllllllllllllllllIllIIIlllIIlIl.theme.scale(llllllllllllllllllIllIIIlllIIlIl.width);
        ((WWidget)llllllllllllllllllIllIIIlllIIlIl).height = llllllllllllllllllIllIIIlllIIlIl.theme.scale(llllllllllllllllllIllIIIlllIIlIl.height);
    }

    @Override
    protected void onRender(GuiRenderer llllllllllllllllllIllIIIlllIIIII, double llllllllllllllllllIllIIIllIlllll, double llllllllllllllllllIllIIIllIllllI, double llllllllllllllllllIllIIIllIlllIl) {
        WTexture llllllllllllllllllIllIIIlllIIIIl;
        llllllllllllllllllIllIIIlllIIIII.texture(llllllllllllllllllIllIIIlllIIIIl.x, llllllllllllllllllIllIIIlllIIIIl.y, ((WWidget)llllllllllllllllllIllIIIlllIIIIl).width, ((WWidget)llllllllllllllllllIllIIIlllIIIIl).height, llllllllllllllllllIllIIIlllIIIIl.rotation, llllllllllllllllllIllIIIlllIIIIl.texture);
    }
}

