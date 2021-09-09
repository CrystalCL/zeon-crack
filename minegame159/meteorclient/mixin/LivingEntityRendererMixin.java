/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.scoreboard.AbstractTeam
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.render.entity.feature.FeatureRenderer
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.entity.model.EntityModel
 *  net.minecraft.client.network.ClientPlayerEntity
 *  net.minecraft.client.render.entity.LivingEntityRenderer
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.RenderEntityEvent;
import minegame159.meteorclient.mixininterface.ILivingEntityRenderer;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LivingEntityRenderer.class})
public abstract class LivingEntityRendererMixin
implements ILivingEntityRenderer {
    @Shadow
    protected EntityModel<Entity> model;
    @Shadow
    @Final
    protected List<FeatureRenderer<LivingEntity, EntityModel<LivingEntity>>> features;

    @Redirect(method={"hasLabel"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"))
    private Entity hasLabelGetCameraEntityProxy(MinecraftClient mc) {
        if (Modules.get().isActive(Freecam.class)) {
            return null;
        }
        return mc.getCameraEntity();
    }

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderPre(LivingEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        RenderEntityEvent.Pre event = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Pre.get((Entity)entity));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")}, cancellable=true)
    public void renderPost(LivingEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        RenderEntityEvent.Post event = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Post.get((Entity)entity));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @ModifyVariable(method={"render"}, ordinal=2, at=@At(value="STORE", ordinal=0))
    public float changeYaw(float oldValue, LivingEntity entity) {
        if (entity.equals((Object)Utils.mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverYaw;
        }
        return oldValue;
    }

    @ModifyVariable(method={"render"}, ordinal=3, at=@At(value="STORE", ordinal=0))
    public float changeHeadYaw(float oldValue, LivingEntity entity) {
        if (entity.equals((Object)Utils.mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverYaw;
        }
        return oldValue;
    }

    @ModifyVariable(method={"render"}, ordinal=5, at=@At(value="STORE", ordinal=3))
    public float changePitch(float oldValue, LivingEntity entity) {
        if (entity.equals((Object)Utils.mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverPitch;
        }
        return oldValue;
    }

    @Redirect(method={"hasLabel"}, at=@At(value="INVOKE", target="net.minecraft.client.network.ClientPlayerEntity.getScoreboardTeam()Lnet/minecraft/scoreboard/AbstractTeam;"))
    private AbstractTeam hasLabelClientPlayerEntityGetScoreboardTeamProxy(ClientPlayerEntity player) {
        if (player == null) {
            return null;
        }
        return player.getScoreboardTeam();
    }

    @Shadow
    protected abstract void setupTransforms(LivingEntity var1, MatrixStack var2, float var3, float var4, float var5);

    @Shadow
    protected abstract void scale(LivingEntity var1, MatrixStack var2, float var3);

    @Shadow
    protected abstract boolean isVisible(LivingEntity var1);

    @Shadow
    protected abstract float getAnimationCounter(LivingEntity var1, float var2);

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void render(LivingEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        RenderEntityEvent.LiveEntity event = MeteorClient.EVENT_BUS.post(RenderEntityEvent.LiveEntity.get(this.model, livingEntity, this.features, f, g, matrixStack, vertexConsumerProvider, i));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Override
    public void setupTransformsInterface(LivingEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        this.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    @Override
    public void scaleInterface(LivingEntity entity, MatrixStack matrices, float amount) {
        this.scale(entity, matrices, amount);
    }

    @Override
    public boolean isVisibleInterface(LivingEntity entity) {
        return this.isVisible(entity);
    }

    @Override
    public float getAnimationCounterInterface(LivingEntity entity, float tickDelta) {
        return this.getAnimationCounter(entity, tickDelta);
    }
}

