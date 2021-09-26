/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.world;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.particle.ParticleEffect;

public class ParticleEvent
extends Cancellable {
    private static final ParticleEvent INSTANCE = new ParticleEvent();
    public ParticleEffect particle;

    public static ParticleEvent get(ParticleEffect ParticleEffect2) {
        INSTANCE.setCancelled(false);
        ParticleEvent.INSTANCE.particle = ParticleEffect2;
        return INSTANCE;
    }
}

