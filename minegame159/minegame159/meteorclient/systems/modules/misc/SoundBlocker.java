/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.PlaySoundEvent;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.SoundEventListSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.sound.SoundEvent;

public class SoundBlocker
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<List<SoundEvent>> sounds;

    @EventHandler
    private void onPlaySound(PlaySoundEvent playSoundEvent) {
        for (SoundEvent SoundEvent2 : this.sounds.get()) {
            if (!SoundEvent2.getId().equals((Object)playSoundEvent.sound.getId())) continue;
            playSoundEvent.cancel();
            break;
        }
    }

    public SoundBlocker() {
        super(Categories.Misc, "sound-blocker", "Cancels out selected sounds.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sounds = this.sgGeneral.add(new SoundEventListSetting.Builder().name("sounds").description("Sounds to block.").defaultValue(new ArrayList<SoundEvent>(0)).build());
    }
}

