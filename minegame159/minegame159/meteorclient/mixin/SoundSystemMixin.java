/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.PlaySoundEvent;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={SoundSystem.class})
public class SoundSystemMixin {
    @Inject(method={"play(Lnet/minecraft/client/sound/SoundInstance;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void onPlay(SoundInstance SoundInstance2, CallbackInfo callbackInfo) {
        PlaySoundEvent playSoundEvent = MeteorClient.EVENT_BUS.post(PlaySoundEvent.get(SoundInstance2));
        if (playSoundEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

