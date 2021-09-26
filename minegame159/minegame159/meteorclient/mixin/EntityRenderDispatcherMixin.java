/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IBox;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.Hitboxes;
import minegame159.meteorclient.systems.modules.render.NoRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.math.Box;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={EntityRenderDispatcher.class})
public class EntityRenderDispatcherMixin {
    @Shadow
    public Camera camera;

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private <E extends Entity> void onRenderHead(E e, double d, double d2, double d3, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, CallbackInfo callbackInfo) {
        NoRender noRender = Modules.get().get(NoRender.class);
        if (noRender.noItems() && e instanceof ItemEntity || noRender.noFallingBlocks() && e instanceof FallingBlockEntity || noRender.noArmorStands() && e instanceof ArmorStandEntity || noRender.noXpOrbs() && e instanceof ExperienceOrbEntity || noRender.noMinecarts() && e instanceof MinecartEntity) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"drawBox"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/WorldRenderer;drawBox(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/util/math/Box;FFFF)V")}, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onDrawBox(MatrixStack MatrixStack2, VertexConsumer VertexConsumer2, Entity Entity2, float f, float f2, float f3, CallbackInfo callbackInfo, Box Box3) {
        double d = Modules.get().get(Hitboxes.class).getEntityValue(Entity2);
        if (d != 0.0) {
            ((IBox)Box3).expand(d);
        }
    }

    @Inject(method={"getSquaredDistanceToCamera(Lnet/minecraft/entity/Entity;)D"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSquaredDistanceToCameraEntity(Entity Entity2, CallbackInfoReturnable<Double> callbackInfoReturnable) {
        if (this.camera == null) {
            callbackInfoReturnable.setReturnValue((Object)0.0);
        }
    }

    @Inject(method={"getSquaredDistanceToCamera(DDD)D"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSquaredDistanceToCameraXYZ(double d, double d2, double d3, CallbackInfoReturnable<Double> callbackInfoReturnable) {
        if (this.camera == null) {
            callbackInfoReturnable.setReturnValue((Object)0.0);
        }
    }
}

