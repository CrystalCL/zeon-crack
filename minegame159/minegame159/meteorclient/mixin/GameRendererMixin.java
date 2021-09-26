/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.render.GetFovEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.rendering.Matrices;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.LiquidInteract;
import minegame159.meteorclient.systems.modules.player.NoMiningTrace;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.systems.modules.render.NoRender;
import minegame159.meteorclient.systems.modules.render.UnfocusedCPU;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.NametagUtils;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={GameRenderer.class})
public abstract class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient client;
    private boolean a = false;
    private boolean freecamSet = false;

    @Shadow
    public abstract void updateTargetedEntity(float var1);

    @Shadow
    public abstract void reset();

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderHead(float f, long l, boolean bl, CallbackInfo callbackInfo) {
        if (Modules.get().isActive(UnfocusedCPU.class) && !this.client.isWindowFocused()) {
            callbackInfo.cancel();
        }
        this.a = false;
    }

    @Inject(method={"renderWorld"}, at={@At(value="HEAD")})
    private void onRenderWorldHead(float f, long l, MatrixStack MatrixStack2, CallbackInfo callbackInfo) {
        Matrices.begin(MatrixStack2);
        Matrices.push();
        RenderSystem.pushMatrix();
        this.a = true;
    }

    @Inject(method={"renderWorld"}, at={@At(value="INVOKE_STRING", target="Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args={"ldc=hand"})}, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onRenderWorld(float f, long l, MatrixStack MatrixStack2, CallbackInfo callbackInfo, boolean bl, Camera Camera2, MatrixStack MatrixStack3, Matrix4f Matrix4f2) {
        if (!Utils.canUpdate()) {
            return;
        }
        this.client.getProfiler().push("meteor-client_render");
        RenderEvent renderEvent = RenderEvent.get(MatrixStack2, f, Camera2.getPos().x, Camera2.getPos().y, Camera2.getPos().z);
        Renderer.begin(renderEvent);
        NametagUtils.onRender(MatrixStack2, Matrix4f2);
        MeteorClient.EVENT_BUS.post(renderEvent);
        Renderer.end();
        this.client.getProfiler().pop();
    }

    @Inject(method={"updateTargetedEntity"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;")}, cancellable=true)
    private void onUpdateTargetedEntity(float f, CallbackInfo callbackInfo) {
        if (Modules.get().get(NoMiningTrace.class).canWork() && this.client.crosshairTarget.getType() == HitResult.class_240.BLOCK) {
            this.client.getProfiler().pop();
            callbackInfo.cancel();
        }
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/systems/RenderSystem;clear(IZ)V", ordinal=0)})
    private void onRenderBeforeGuiRender(float f, long l, boolean bl, CallbackInfo callbackInfo) {
        if (this.a) {
            Matrices.pop();
            RenderSystem.popMatrix();
        }
    }

    @Redirect(method={"updateTargetedEntity"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;raycast(DFZ)Lnet/minecraft/util/hit/HitResult;"))
    private HitResult updateTargetedEntityEntityRayTraceProxy(Entity Entity2, double d, float f, boolean bl) {
        if (Modules.get().isActive(LiquidInteract.class)) {
            HitResult HitResult2 = Entity2.raycast(d, f, bl);
            if (HitResult2.getType() != HitResult.class_240.MISS) {
                return HitResult2;
            }
            return Entity2.raycast(d, f, true);
        }
        return Entity2.raycast(d, f, bl);
    }

    @Inject(method={"bobViewWhenHurt"}, at={@At(value="HEAD")}, cancellable=true)
    private void onBobViewWhenHurt(MatrixStack MatrixStack2, float f, CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noHurtCam()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"showFloatingItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void onShowFloatingItem(ItemStack ItemStack2, CallbackInfo callbackInfo) {
        if (ItemStack2.getItem() == Items.TOTEM_OF_UNDYING && Modules.get().get(NoRender.class).noTotemAnimation()) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"renderWorld"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    private float applyCameraTransformationsMathHelperLerpProxy(float f, float f2, float f3) {
        if (Modules.get().get(NoRender.class).noNausea()) {
            return 0.0f;
        }
        return MathHelper.lerp((float)f, (float)f2, (float)f3);
    }

    @Inject(method={"getFov"}, at={@At(value="RETURN")}, cancellable=true)
    private void onGetFov(Camera Camera2, float f, boolean bl, CallbackInfoReturnable<Double> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((Object)MeteorClient.EVENT_BUS.post(GetFovEvent.get((double)((Double)callbackInfoReturnable.getReturnValue()).doubleValue())).fov);
    }

    @Inject(method={"updateTargetedEntity"}, at={@At(value="INVOKE")}, cancellable=true)
    private void updateTargetedEntityInvoke(float f, CallbackInfo callbackInfo) {
        Freecam freecam = Modules.get().get(Freecam.class);
        if (freecam.isActive() && this.client.getCameraEntity() != null && !this.freecamSet) {
            callbackInfo.cancel();
            Entity Entity2 = this.client.getCameraEntity();
            double d = Entity2.getX();
            double d2 = Entity2.getY();
            double d3 = Entity2.getZ();
            double d4 = Entity2.prevX;
            double d5 = Entity2.prevY;
            double d6 = Entity2.prevZ;
            float f2 = Entity2.yaw;
            float f3 = Entity2.pitch;
            float f4 = Entity2.prevYaw;
            float f5 = Entity2.prevPitch;
            ((IVec3d)Entity2.getPos()).set(freecam.pos.x, freecam.pos.y - (double)Entity2.getEyeHeight(Entity2.getPose()), freecam.pos.z);
            Entity2.prevX = freecam.prevPos.x;
            Entity2.prevY = freecam.prevPos.y - (double)Entity2.getEyeHeight(Entity2.getPose());
            Entity2.prevZ = freecam.prevPos.z;
            Entity2.yaw = freecam.yaw;
            Entity2.pitch = freecam.pitch;
            Entity2.prevYaw = freecam.prevYaw;
            Entity2.prevPitch = freecam.prevPitch;
            this.freecamSet = true;
            this.updateTargetedEntity(f);
            this.freecamSet = false;
            ((IVec3d)Entity2.getPos()).set(d, d2, d3);
            Entity2.prevX = d4;
            Entity2.prevY = d5;
            Entity2.prevZ = d6;
            Entity2.yaw = f2;
            Entity2.pitch = f3;
            Entity2.prevYaw = f4;
            Entity2.prevPitch = f5;
        }
    }

    @Inject(method={"renderHand"}, at={@At(value="INVOKE")}, cancellable=true)
    private void renderHand(MatrixStack MatrixStack2, Camera Camera2, float f, CallbackInfo callbackInfo) {
        if (!Modules.get().get(Freecam.class).renderHands()) {
            callbackInfo.cancel();
        }
    }
}

