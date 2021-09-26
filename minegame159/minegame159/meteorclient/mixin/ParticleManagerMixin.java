/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.ParticleEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.NoRender;
import minegame159.meteorclient.systems.modules.world.Nuker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ParticleManager.class})
public class ParticleManagerMixin {
    @Inject(method={"addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAddParticle(ParticleEffect ParticleEffect2, double d, double d2, double d3, double d4, double d5, double d6, CallbackInfoReturnable<Particle> callbackInfoReturnable) {
        ParticleEvent particleEvent = MeteorClient.EVENT_BUS.post(ParticleEvent.get(ParticleEffect2));
        if (particleEvent.isCancelled()) {
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method={"addBlockBreakParticles"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAddBlockBreakParticles(BlockPos BlockPos2, BlockState BlockState2, CallbackInfo callbackInfo) {
        if (Modules.get().get(Nuker.class).noParticles() || Modules.get().get(NoRender.class).noBlockBreakParticles()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"addBlockBreakingParticles"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAddBlockBreakingParticles(BlockPos BlockPos2, Direction Direction2, CallbackInfo callbackInfo) {
        if (Modules.get().get(Nuker.class).noParticles() || Modules.get().get(NoRender.class).noBlockBreakParticles()) {
            callbackInfo.cancel();
        }
    }
}

