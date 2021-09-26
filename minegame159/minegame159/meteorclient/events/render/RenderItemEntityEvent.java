/*
 * Decompiled with CFR 0.151.
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
    private static final RenderItemEntityEvent INSTANCE = new RenderItemEntityEvent();
    public int light;
    public ItemRenderer itemRenderer;
    public float tickDelta;
    public MatrixStack matrixStack;
    public Random random;
    public float f;
    public ItemEntity itemEntity;
    public VertexConsumerProvider vertexConsumerProvider;

    public static RenderItemEntityEvent get(ItemEntity ItemEntity2, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, Random random, ItemRenderer ItemRenderer2) {
        INSTANCE.setCancelled(false);
        RenderItemEntityEvent.INSTANCE.itemEntity = ItemEntity2;
        RenderItemEntityEvent.INSTANCE.f = f;
        RenderItemEntityEvent.INSTANCE.tickDelta = f2;
        RenderItemEntityEvent.INSTANCE.matrixStack = MatrixStack2;
        RenderItemEntityEvent.INSTANCE.vertexConsumerProvider = VertexConsumerProvider2;
        RenderItemEntityEvent.INSTANCE.light = n;
        RenderItemEntityEvent.INSTANCE.random = random;
        RenderItemEntityEvent.INSTANCE.itemRenderer = ItemRenderer2;
        return INSTANCE;
    }
}

