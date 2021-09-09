/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.util.math.Matrix4f
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.util.hit.HitResult
 *  net.minecraft.util.hit.HitResult$class_240
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.client.render.Camera
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.GameRenderer
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
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
    private void onRenderHead(float tickDelta, long startTime, boolean tick, CallbackInfo info) {
        if (Modules.get().isActive(UnfocusedCPU.class) && !this.client.isWindowFocused()) {
            info.cancel();
        }
        this.a = false;
    }

    @Inject(method={"renderWorld"}, at={@At(value="HEAD")})
    private void onRenderWorldHead(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo info) {
        Matrices.begin(matrix);
        Matrices.push();
        RenderSystem.pushMatrix();
        this.a = true;
    }

    @Inject(method={"renderWorld"}, at={@At(value="INVOKE_STRING", target="Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args={"ldc=hand"})}, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo info, boolean bl, Camera camera, MatrixStack matrixStack2, Matrix4f matrix4f) {
        if (!Utils.canUpdate()) {
            return;
        }
        this.client.getProfiler().push("meteor-client_render");
        RenderEvent event = RenderEvent.get(matrix, tickDelta, camera.getPos().x, camera.getPos().y, camera.getPos().z);
        Renderer.begin(event);
        NametagUtils.onRender(matrix, matrix4f);
        MeteorClient.EVENT_BUS.post(event);
        Renderer.end();
        this.client.getProfiler().pop();
    }

    @Inject(method={"updateTargetedEntity"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;")}, cancellable=true)
    private void onUpdateTargetedEntity(float tickDelta, CallbackInfo info) {
        if (Modules.get().get(NoMiningTrace.class).canWork() && this.client.crosshairTarget.getType() == HitResult.class_240.BLOCK) {
            this.client.getProfiler().pop();
            info.cancel();
        }
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/systems/RenderSystem;clear(IZ)V", ordinal=0)})
    private void onRenderBeforeGuiRender(float tickDelta, long startTime, boolean tick, CallbackInfo info) {
        if (this.a) {
            Matrices.pop();
            RenderSystem.popMatrix();
        }
    }

    @Redirect(method={"updateTargetedEntity"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;raycast(DFZ)Lnet/minecraft/util/hit/HitResult;"))
    private HitResult updateTargetedEntityEntityRayTraceProxy(Entity entity, double maxDistance, float tickDelta, boolean includeFluids) {
        if (Modules.get().isActive(LiquidInteract.class)) {
            HitResult result = entity.raycast(maxDistance, tickDelta, includeFluids);
            if (result.getType() != HitResult.class_240.MISS) {
                return result;
            }
            return entity.raycast(maxDistance, tickDelta, true);
        }
        return entity.raycast(maxDistance, tickDelta, includeFluids);
    }

    @Inject(method={"bobViewWhenHurt"}, at={@At(value="HEAD")}, cancellable=true)
    private void onBobViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noHurtCam()) {
            info.cancel();
        }
    }

    @Inject(method={"showFloatingItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void onShowFloatingItem(ItemStack floatingItem, CallbackInfo info) {
        if (floatingItem.getItem() == Items.TOTEM_OF_UNDYING && Modules.get().get(NoRender.class).noTotemAnimation()) {
            info.cancel();
        }
    }

    @Redirect(method={"renderWorld"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    private float applyCameraTransformationsMathHelperLerpProxy(float delta, float first, float second) {
        if (Modules.get().get(NoRender.class).noNausea()) {
            return 0.0f;
        }
        return MathHelper.lerp((float)delta, (float)first, (float)second);
    }

    @Inject(method={"getFov"}, at={@At(value="RETURN")}, cancellable=true)
    private void onGetFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> info) {
        info.setReturnValue((Object)MeteorClient.EVENT_BUS.post(GetFovEvent.get((double)((Double)info.getReturnValue()).doubleValue())).fov);
    }

    @Inject(method={"updateTargetedEntity"}, at={@At(value="INVOKE")}, cancellable=true)
    private void updateTargetedEntityInvoke(float tickDelta, CallbackInfo info) {
        Freecam freecam = Modules.get().get(Freecam.class);
        if (freecam.isActive() && this.client.getCameraEntity() != null && !this.freecamSet) {
            info.cancel();
            Entity camera = this.client.getCameraEntity();
            double x = camera.getX();
            double y = camera.getY();
            double z = camera.getZ();
            double prevX = camera.prevX;
            double prevY = camera.prevY;
            double prevZ = camera.prevZ;
            float yaw = camera.yaw;
            float pitch = camera.pitch;
            float prevYaw = camera.prevYaw;
            float prevPitch = camera.prevPitch;
            ((IVec3d)camera.getPos()).set(freecam.pos.x, freecam.pos.y - (double)camera.getEyeHeight(camera.getPose()), freecam.pos.z);
            camera.prevX = freecam.prevPos.x;
            camera.prevY = freecam.prevPos.y - (double)camera.getEyeHeight(camera.getPose());
            camera.prevZ = freecam.prevPos.z;
            camera.yaw = freecam.yaw;
            camera.pitch = freecam.pitch;
            camera.prevYaw = freecam.prevYaw;
            camera.prevPitch = freecam.prevPitch;
            this.freecamSet = true;
            this.updateTargetedEntity(tickDelta);
            this.freecamSet = false;
            ((IVec3d)camera.getPos()).set(x, y, z);
            camera.prevX = prevX;
            camera.prevY = prevY;
            camera.prevZ = prevZ;
            camera.yaw = yaw;
            camera.pitch = pitch;
            camera.prevYaw = prevYaw;
            camera.prevPitch = prevPitch;
        }
    }

    @Inject(method={"renderHand"}, at={@At(value="INVOKE")}, cancellable=true)
    private void renderHand(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo info) {
        if (!Modules.get().get(Freecam.class).renderHands()) {
            info.cancel();
        }
    }
}

