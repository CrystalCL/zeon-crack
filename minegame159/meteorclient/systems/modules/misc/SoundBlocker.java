/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.sound.SoundEvent
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
    private final /* synthetic */ Setting<List<SoundEvent>> sounds;
    private final /* synthetic */ SettingGroup sgGeneral;

    public SoundBlocker() {
        super(Categories.Misc, "sound-blocker", "Cancels out selected sounds.");
        SoundBlocker llllllllllllllllllllIIllllIllIlI;
        llllllllllllllllllllIIllllIllIlI.sgGeneral = llllllllllllllllllllIIllllIllIlI.settings.getDefaultGroup();
        llllllllllllllllllllIIllllIllIlI.sounds = llllllllllllllllllllIIllllIllIlI.sgGeneral.add(new SoundEventListSetting.Builder().name("sounds").description("Sounds to block.").defaultValue(new ArrayList<SoundEvent>(0)).build());
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent llllllllllllllllllllIIllllIlIIll) {
        SoundBlocker llllllllllllllllllllIIllllIlIIlI;
        for (SoundEvent llllllllllllllllllllIIllllIlIlIl : llllllllllllllllllllIIllllIlIIlI.sounds.get()) {
            if (!llllllllllllllllllllIIllllIlIlIl.getId().equals((Object)llllllllllllllllllllIIllllIlIIll.sound.getId())) continue;
            llllllllllllllllllllIIllllIlIIll.cancel();
            break;
        }
    }
}

