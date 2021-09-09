/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.model.ModelPart
 *  net.minecraft.client.render.entity.EndCrystalEntityRenderer
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
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
    private void render(EndCrystalEntity endCrystalEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        RenderEntityEvent.Crystal event = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Crystal.get(endCrystalEntity, f, g, matrixStack, vertexConsumerProvider, i, this.core, this.frame, this.bottom));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}

