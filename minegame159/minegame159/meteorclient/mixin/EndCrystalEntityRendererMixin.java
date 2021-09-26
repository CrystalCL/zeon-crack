/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.RenderEntityEvent;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EndCrystalEntityRenderer.class})
public abstract class EndCrystalEntityRendererMixin {
    @Shadow
    @Final
    private ModelPart core;
    @Shadow
    @Final
    private ModelPart frame;
    @Shadow
    @Final
    private ModelPart bottom;

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void render(EndCrystalEntity EndCrystalEntity2, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, CallbackInfo callbackInfo) {
        RenderEntityEvent.Crystal crystal = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Crystal.get(EndCrystalEntity2, f, f2, MatrixStack2, VertexConsumerProvider2, n, this.core, this.frame, this.bottom));
        if (crystal.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

