/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.projectile.FireworkRocketEntity
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.util.hit.EntityHitResult
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.ElytraBoost;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={FireworkRocketEntity.class})
public abstract class FireworkRocketEntityMixin {
    @Shadow
    private int life;
    @Shadow
    private int lifeTime;

    @Shadow
    protected abstract void explodeAndRemove();

    @Inject(method={"tick"}, at={@At(value="TAIL")})
    private void onTick(CallbackInfo info) {
        if (Modules.get().get(ElytraBoost.class).isFirework((FireworkRocketEntity)this) && this.life > this.lifeTime) {
            this.explodeAndRemove();
        }
    }

    @Inject(method={"onEntityHit"}, at={@At(value="HEAD")}, cancellable=true)
    private void onEntityHit(EntityHitResult entityHitResult, CallbackInfo info) {
        if (Modules.get().get(ElytraBoost.class).isFirework((FireworkRocketEntity)this)) {
            this.explodeAndRemove();
            info.cancel();
        }
    }

    @Inject(method={"onBlockHit"}, at={@At(value="HEAD")}, cancellable=true)
    private void onBlockHit(BlockHitResult blockHitResult, CallbackInfo info) {
        if (Modules.get().get(ElytraBoost.class).isFirework((FireworkRocketEntity)this)) {
            this.explodeAndRemove();
            info.cancel();
        }
    }
}

