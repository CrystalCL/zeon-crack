/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.particle.FireworksSparkParticle.Explosion
 *  net.minecraft.client.particle.FireworksSparkParticle.FireworkParticle
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package minegame159.meteorclient.mixin;

import net.minecraft.client.particle.FireworksSparkParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={FireworkParticle.class})
public class FireworksSparkParticleMixin {
    @Inject(method={"addExplosionParticle"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/particle/FireworksSparkParticle$Explosion;setTrail(Z)V")}, cancellable=true, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onAddExplosion(double x, double y, double z, double velocityX, double velocityY, double velocityZ, int[] colors, int[] fadeColors, boolean trail, boolean flicker, CallbackInfo info, Explosion explosion) {
        if (explosion == null) {
            info.cancel();
        }
    }
}

