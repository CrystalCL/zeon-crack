/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.CameraClip;
import minegame159.meteorclient.systems.modules.render.FreeRotate;
import minegame159.meteorclient.systems.modules.render.Freecam;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value={Camera.class})
public abstract class CameraMixin {
    @Shadow
    private boolean thirdPerson;
    @Unique
    private float tickDelta;

    @Inject(method={"clipToSpace"}, at={@At(value="HEAD")}, cancellable=true)
    private void onClipToSpace(double d, CallbackInfoReturnable<Double> callbackInfoReturnable) {
        if (Modules.get().isActive(CameraClip.class)) {
            callbackInfoReturnable.setReturnValue((Object)d);
        }
    }

    @Inject(method={"update"}, at={@At(value="HEAD")})
    private void onUpdateHead(BlockView BlockView2, Entity Entity2, boolean bl, boolean bl2, float f, CallbackInfo callbackInfo) {
        this.tickDelta = f;
    }

    @Inject(method={"update"}, at={@At(value="TAIL")})
    private void onUpdateTail(BlockView BlockView2, Entity Entity2, boolean bl, boolean bl2, float f, CallbackInfo callbackInfo) {
        if (Modules.get().isActive(Freecam.class)) {
            this.thirdPerson = true;
        }
    }

    @ModifyArgs(method={"update"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/Camera;setPos(DDD)V"))
    private void onUpdateSetPosArgs(Args args) {
        Freecam freecam = Modules.get().get(Freecam.class);
        if (freecam.isActive()) {
            args.set(0, (Object)freecam.getX(this.tickDelta));
            args.set(1, (Object)freecam.getY(this.tickDelta));
            args.set(2, (Object)freecam.getZ(this.tickDelta));
        }
    }

    @ModifyArgs(method={"update"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/Camera;setRotation(FF)V"))
    private void onUpdateSetRotationArgs(Args args) {
        Freecam freecam = Modules.get().get(Freecam.class);
        FreeRotate freeRotate = Modules.get().get(FreeRotate.class);
        if (freecam.isActive()) {
            args.set(0, (Object)Float.valueOf((float)freecam.getYaw(this.tickDelta)));
            args.set(1, (Object)Float.valueOf((float)freecam.getPitch(this.tickDelta)));
        }
        if (freeRotate.isActive()) {
            args.set(0, (Object)Float.valueOf(freeRotate.cameraYaw));
            args.set(1, (Object)Float.valueOf(freeRotate.cameraPitch));
        }
    }
}

