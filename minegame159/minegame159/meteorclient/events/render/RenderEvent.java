/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.render;

import net.minecraft.client.util.math.MatrixStack;

public class RenderEvent {
    public double offsetX;
    public float tickDelta;
    public MatrixStack matrices;
    public double offsetZ;
    public double offsetY;
    private static final RenderEvent INSTANCE = new RenderEvent();

    public static RenderEvent get(MatrixStack MatrixStack2, float f, double d, double d2, double d3) {
        RenderEvent.INSTANCE.matrices = MatrixStack2;
        RenderEvent.INSTANCE.tickDelta = f;
        RenderEvent.INSTANCE.offsetX = d;
        RenderEvent.INSTANCE.offsetY = d2;
        RenderEvent.INSTANCE.offsetZ = d3;
        return INSTANCE;
    }
}

