/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.ExperienceOrbEntity
 *  net.minecraft.entity.decoration.ArmorStandEntity
 *  net.minecraft.entity.FallingBlockEntity
 *  net.minecraft.entity.ItemEntity
 *  net.minecraft.entity.vehicle.MinecartEntity
 *  net.minecraft.util.math.Box
 *  net.minecraft.client.render.Camera
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.entity.EntityRenderDispatcher
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
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
    private <E extends Entity> void onRenderHead(E entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        NoRender noRender = Modules.get().get(NoRender.class);
        if (noRender.noItems() && entity instanceof ItemEntity || noRender.noFallingBlocks() && entity instanceof FallingBlockEntity || noRender.noArmorStands() && entity instanceof ArmorStandEntity || noRender.noXpOrbs() && entity instanceof ExperienceOrbEntity || noRender.noMinecarts() && entity instanceof MinecartEntity) {
            info.cancel();
        }
    }

    @Inject(method={"drawBox"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/WorldRenderer;drawBox(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/util/math/Box;FFFF)V")}, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onDrawBox(MatrixStack matrix, VertexConsumer vertices, Entity entity, float red, float green, float blue, CallbackInfo info, Box box) {
        double v = Modules.get().get(Hitboxes.class).getEntityValue(entity);
        if (v != 0.0) {
            ((IBox)box).expand(v);
        }
    }

    @Inject(method={"getSquaredDistanceToCamera(Lnet/minecraft/entity/Entity;)D"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSquaredDistanceToCameraEntity(Entity entity, CallbackInfoReturnable<Double> info) {
        if (this.camera == null) {
            info.setReturnValue((Object)0.0);
        }
    }

    @Inject(method={"getSquaredDistanceToCamera(DDD)D"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSquaredDistanceToCameraXYZ(double x, double y, double z, CallbackInfoReturnable<Double> info) {
        if (this.camera == null) {
            info.setReturnValue((Object)0.0);
        }
    }
}

