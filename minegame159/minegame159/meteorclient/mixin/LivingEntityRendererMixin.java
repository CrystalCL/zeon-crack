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
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_270;
import net.minecraft.class_310;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_583;
import net.minecraft.class_746;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_922.class})
public abstract class LivingEntityRendererMixin
implements ILivingEntityRenderer {
    @Shadow
    protected class_583<class_1297> field_4737;
    @Shadow
    @Final
    protected List<class_3887<class_1309, class_583<class_1309>>> field_4738;

    @Redirect(method={"hasLabel"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"))
    private class_1297 hasLabelGetCameraEntityProxy(class_310 class_3102) {
        if (Modules.get().isActive(Freecam.class)) {
            return null;
        }
        return class_3102.method_1560();
    }

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderPre(class_1309 class_13092, float f, float f2, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        RenderEntityEvent.Pre pre = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Pre.get((class_1297)class_13092));
        if (pre.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")}, cancellable=true)
    public void renderPost(class_1309 class_13092, float f, float f2, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        RenderEntityEvent.Post post = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Post.get((class_1297)class_13092));
        if (post.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @ModifyVariable(method={"render"}, ordinal=2, at=@At(value="STORE", ordinal=0))
    public float changeYaw(float f, class_1309 class_13092) {
        if (class_13092.equals((Object)Utils.mc.field_1724) && Rotations.rotationTimer < 10) {
            return Rotations.serverYaw;
        }
        return f;
    }

    @ModifyVariable(method={"render"}, ordinal=3, at=@At(value="STORE", ordinal=0))
    public float changeHeadYaw(float f, class_1309 class_13092) {
        if (class_13092.equals((Object)Utils.mc.field_1724) && Rotations.rotationTimer < 10) {
            return Rotations.serverYaw;
        }
        return f;
    }

    @ModifyVariable(method={"render"}, ordinal=5, at=@At(value="STORE", ordinal=3))
    public float changePitch(float f, class_1309 class_13092) {
        if (class_13092.equals((Object)Utils.mc.field_1724) && Rotations.rotationTimer < 10) {
            return Rotations.serverPitch;
        }
        return f;
    }

    @Redirect(method={"hasLabel"}, at=@At(value="INVOKE", target="net.minecraft.client.network.ClientPlayerEntity.getScoreboardTeam()Lnet/minecraft/scoreboard/AbstractTeam;"))
    private class_270 hasLabelClientPlayerEntityGetScoreboardTeamProxy(class_746 class_7462) {
        if (class_7462 == null) {
            return null;
        }
        return class_7462.method_5781();
    }

    @Shadow
    protected abstract void method_4058(class_1309 var1, class_4587 var2, float var3, float var4, float var5);

    @Shadow
    protected abstract void method_4042(class_1309 var1, class_4587 var2, float var3);

    @Shadow
    protected abstract boolean method_4056(class_1309 var1);

    @Shadow
    protected abstract float method_23185(class_1309 var1, float var2);

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void render(class_1309 class_13092, float f, float f2, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        RenderEntityEvent.LiveEntity liveEntity = MeteorClient.EVENT_BUS.post(RenderEntityEvent.LiveEntity.get(this.field_4737, class_13092, this.field_4738, f, f2, class_45872, class_45972, n));
        if (liveEntity.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Override
    public void setupTransformsInterface(class_1309 class_13092, class_4587 class_45872, float f, float f2, float f3) {
        this.method_4058(class_13092, class_45872, f, f2, f3);
    }

    @Override
    public void scaleInterface(class_1309 class_13092, class_4587 class_45872, float f) {
        this.method_4042(class_13092, class_45872, f);
    }

    @Override
    public boolean isVisibleInterface(class_1309 class_13092) {
        return this.method_4056(class_13092);
    }

    @Override
    public float getAnimationCounterInterface(class_1309 class_13092, float f) {
        return this.method_23185(class_13092, f);
    }
}

