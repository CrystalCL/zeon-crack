/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.ItemEntity
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.item.ItemRenderer
 */
package minegame159.meteorclient.events.render;

import java.util.Random;
import minegame159.meteorclient.events.Cancellable;
import net.minecraft.entity.ItemEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;

public class RenderItemEntityEvent
extends Cancellable {
    public /* synthetic */ float f;
    public /* synthetic */ ItemRenderer itemRenderer;
    public /* synthetic */ VertexConsumerProvider vertexConsumerProvider;
    public /* synthetic */ ItemEntity itemEntity;
    public /* synthetic */ MatrixStack matrixStack;
    public /* synthetic */ int light;
    public /* synthetic */ Random random;
    public /* synthetic */ float tickDelta;
    private static final /* synthetic */ RenderItemEntityEvent INSTANCE;

    public static RenderItemEntityEvent get(ItemEntity lIIIIlIIIIlIllI, float lIIIIlIIIIlllIl, float lIIIIlIIIIlIlII, MatrixStack lIIIIlIIIIlIIll, VertexConsumerProvider lIIIIlIIIIlIIlI, int lIIIIlIIIIllIIl, Random lIIIIlIIIIlIIII, ItemRenderer lIIIIlIIIIIllll) {
        INSTANCE.setCancelled(false);
        RenderItemEntityEvent.INSTANCE.itemEntity = lIIIIlIIIIlIllI;
        RenderItemEntityEvent.INSTANCE.f = lIIIIlIIIIlllIl;
        RenderItemEntityEvent.INSTANCE.tickDelta = lIIIIlIIIIlIlII;
        RenderItemEntityEvent.INSTANCE.matrixStack = lIIIIlIIIIlIIll;
        RenderItemEntityEvent.INSTANCE.vertexConsumerProvider = lIIIIlIIIIlIIlI;
        RenderItemEntityEvent.INSTANCE.light = lIIIIlIIIIllIIl;
        RenderItemEntityEvent.INSTANCE.random = lIIIIlIIIIlIIII;
        RenderItemEntityEvent.INSTANCE.itemRenderer = lIIIIlIIIIIllll;
        return INSTANCE;
    }

    static {
        INSTANCE = new RenderItemEntityEvent();
    }

    public RenderItemEntityEvent() {
        RenderItemEntityEvent lIIIIlIIIlIlIII;
    }
}

