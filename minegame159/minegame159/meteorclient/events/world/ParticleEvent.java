/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.world;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.class_2394;

public class ParticleEvent
extends Cancellable {
    private static final ParticleEvent INSTANCE = new ParticleEvent();
    public class_2394 particle;

    public static ParticleEvent get(class_2394 class_23942) {
        INSTANCE.setCancelled(false);
        ParticleEvent.INSTANCE.particle = class_23942;
        return INSTANCE;
    }
}

