/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.util.math.MatrixStack
 */
package minegame159.meteorclient.events.render;

import net.minecraft.client.util.math.MatrixStack;

public class RenderEvent {
    public /* synthetic */ MatrixStack matrices;
    public /* synthetic */ double offsetY;
    public /* synthetic */ double offsetX;
    private static final /* synthetic */ RenderEvent INSTANCE;
    public /* synthetic */ double offsetZ;
    public /* synthetic */ float tickDelta;

    public static RenderEvent get(MatrixStack llllllllllllllllllIIlIIIIIIIIlII, float llllllllllllllllllIIIllllllllllI, double llllllllllllllllllIIlIIIIIIIIIlI, double llllllllllllllllllIIlIIIIIIIIIIl, double llllllllllllllllllIIIllllllllIll) {
        RenderEvent.INSTANCE.matrices = llllllllllllllllllIIlIIIIIIIIlII;
        RenderEvent.INSTANCE.tickDelta = llllllllllllllllllIIIllllllllllI;
        RenderEvent.INSTANCE.offsetX = llllllllllllllllllIIlIIIIIIIIIlI;
        RenderEvent.INSTANCE.offsetY = llllllllllllllllllIIlIIIIIIIIIIl;
        RenderEvent.INSTANCE.offsetZ = llllllllllllllllllIIIllllllllIll;
        return INSTANCE;
    }

    public RenderEvent() {
        RenderEvent llllllllllllllllllIIlIIIIIIIlIlI;
    }

    static {
        INSTANCE = new RenderEvent();
    }
}

