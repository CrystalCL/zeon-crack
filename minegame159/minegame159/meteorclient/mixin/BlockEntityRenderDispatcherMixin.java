/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.render.RenderBlockEntityEvent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BlockEntityRenderDispatcher.class})
public class BlockEntityRenderDispatcherMixin {
    @Inject(method={"render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private <E extends BlockEntity> void onRenderEntity(E e, float f, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, CallbackInfo callbackInfo) {
        RenderBlockEntityEvent renderBlockEntityEvent = MeteorClient.EVENT_BUS.post(RenderBlockEntityEvent.get(e));
        if (renderBlockEntityEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

