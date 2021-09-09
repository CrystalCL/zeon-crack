/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.sound.SoundInstance
 */
package minegame159.meteorclient.events.world;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.client.sound.SoundInstance;

public class PlaySoundEvent
extends Cancellable {
    private static final /* synthetic */ PlaySoundEvent INSTANCE;
    public /* synthetic */ SoundInstance sound;

    public PlaySoundEvent() {
        PlaySoundEvent lllllIIlIlIIIl;
    }

    public static PlaySoundEvent get(SoundInstance lllllIIlIIllIl) {
        INSTANCE.setCancelled(false);
        PlaySoundEvent.INSTANCE.sound = lllllIIlIIllIl;
        return INSTANCE;
    }

    static {
        INSTANCE = new PlaySoundEvent();
    }
}

