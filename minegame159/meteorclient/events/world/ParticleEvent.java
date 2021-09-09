/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.particle.ParticleEffect
 */
package minegame159.meteorclient.events.world;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.particle.ParticleEffect;

public class ParticleEvent
extends Cancellable {
    public /* synthetic */ ParticleEffect particle;
    private static final /* synthetic */ ParticleEvent INSTANCE;

    static {
        INSTANCE = new ParticleEvent();
    }

    public ParticleEvent() {
        ParticleEvent lllIllIIlIlIlII;
    }

    public static ParticleEvent get(ParticleEffect lllIllIIlIlIIIl) {
        INSTANCE.setCancelled(false);
        ParticleEvent.INSTANCE.particle = lllIllIIlIlIIIl;
        return INSTANCE;
    }
}

