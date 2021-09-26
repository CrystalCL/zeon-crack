/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.Random;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.render.RenderItemEntityEvent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemEntityRenderer.class})
public abstract class ItemEntityRendererMixin {
    @Shadow
    @Final
    private Random random;
    @Shadow
    @Final
    private ItemRenderer itemRenderer;

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void render(ItemEntity ItemEntity2, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, CallbackInfo callbackInfo) {
        RenderItemEntityEvent renderItemEntityEvent = MeteorClient.EVENT_BUS.post(RenderItemEntityEvent.get(ItemEntity2, f, f2, MatrixStack2, VertexConsumerProvider2, n, this.random, this.itemRenderer));
        if (renderItemEntityEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

