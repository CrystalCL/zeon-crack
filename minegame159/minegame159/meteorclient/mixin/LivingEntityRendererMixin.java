/*
 * Decompiled with CFR 0.151.
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
    private Entity hasLabelGetCameraEntityProxy(MinecraftClient MinecraftClient2) {
        if (Modules.get().isActive(Freecam.class)) {
            return null;
        }
        return MinecraftClient2.getCameraEntity();
    }

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderPre(LivingEntity LivingEntity2, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, CallbackInfo callbackInfo) {
        RenderEntityEvent.Pre pre = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Pre.get((Entity)LivingEntity2));
        if (pre.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")}, cancellable=true)
    public void renderPost(LivingEntity LivingEntity2, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, CallbackInfo callbackInfo) {
        RenderEntityEvent.Post post = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Post.get((Entity)LivingEntity2));
        if (post.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @ModifyVariable(method={"render"}, ordinal=2, at=@At(value="STORE", ordinal=0))
    public float changeYaw(float f, LivingEntity LivingEntity2) {
        if (LivingEntity2.equals((Object)Utils.mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverYaw;
        }
        return f;
    }

    @ModifyVariable(method={"render"}, ordinal=3, at=@At(value="STORE", ordinal=0))
    public float changeHeadYaw(float f, LivingEntity LivingEntity2) {
        if (LivingEntity2.equals((Object)Utils.mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverYaw;
        }
        return f;
    }

    @ModifyVariable(method={"render"}, ordinal=5, at=@At(value="STORE", ordinal=3))
    public float changePitch(float f, LivingEntity LivingEntity2) {
        if (LivingEntity2.equals((Object)Utils.mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverPitch;
        }
        return f;
    }

    @Redirect(method={"hasLabel"}, at=@At(value="INVOKE", target="net.minecraft.client.network.ClientPlayerEntity.getScoreboardTeam()Lnet/minecraft/scoreboard/AbstractTeam;"))
    private AbstractTeam hasLabelClientPlayerEntityGetScoreboardTeamProxy(ClientPlayerEntity ClientPlayerEntity2) {
        if (ClientPlayerEntity2 == null) {
            return null;
        }
        return ClientPlayerEntity2.getScoreboardTeam();
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
    private void render(LivingEntity LivingEntity2, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, CallbackInfo callbackInfo) {
        RenderEntityEvent.LiveEntity liveEntity = MeteorClient.EVENT_BUS.post(RenderEntityEvent.LiveEntity.get(this.model, LivingEntity2, this.features, f, f2, MatrixStack2, VertexConsumerProvider2, n));
        if (liveEntity.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Override
    public void setupTransformsInterface(LivingEntity LivingEntity2, MatrixStack MatrixStack2, float f, float f2, float f3) {
        this.setupTransforms(LivingEntity2, MatrixStack2, f, f2, f3);
    }

    @Override
    public void scaleInterface(LivingEntity LivingEntity2, MatrixStack MatrixStack2, float f) {
        this.scale(LivingEntity2, MatrixStack2, f);
    }

    @Override
    public boolean isVisibleInterface(LivingEntity LivingEntity2) {
        return this.isVisible(LivingEntity2);
    }

    @Override
    public float getAnimationCounterInterface(LivingEntity LivingEntity2, float f) {
        return this.getAnimationCounter(LivingEntity2, f);
    }
}

