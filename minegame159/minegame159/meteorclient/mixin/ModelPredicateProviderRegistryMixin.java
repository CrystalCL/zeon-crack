/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Freecam;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets={"net.minecraft.client.item.ModelPredicateProviderRegistry$2"})
public class ModelPredicateProviderRegistryMixin {
    @Redirect(method={"call(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/LivingEntity;)F"}, at=@At(value="FIELD", target="Lnet/minecraft/entity/LivingEntity;yaw:F"))
    private float callLivingEntityGetYaw(LivingEntity LivingEntity2) {
        if (Modules.get().isActive(Freecam.class)) {
            return MinecraftClient.getInstance().gameRenderer.getCamera().getYaw();
        }
        return LivingEntity2.yaw;
    }

    @Inject(method={"getAngleToPos(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/entity/Entity;)D"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetAngleToPos(Vec3d Vec3d2, Entity Entity2, CallbackInfoReturnable<Double> callbackInfoReturnable) {
        if (Modules.get().isActive(Freecam.class)) {
            Camera Camera2 = MinecraftClient.getInstance().gameRenderer.getCamera();
            callbackInfoReturnable.setReturnValue((Object)Math.atan2(Vec3d2.getZ() - Camera2.getPos().z, Vec3d2.getX() - Camera2.getPos().x));
        }
    }
}

