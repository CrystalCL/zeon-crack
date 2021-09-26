/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.world;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.client.sound.SoundInstance;

public class PlaySoundEvent
extends Cancellable {
    public SoundInstance sound;
    private static final PlaySoundEvent INSTANCE = new PlaySoundEvent();

    public static PlaySoundEvent get(SoundInstance SoundInstance2) {
        INSTANCE.setCancelled(false);
        PlaySoundEvent.INSTANCE.sound = SoundInstance2;
        return INSTANCE;
    }
}

