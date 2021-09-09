/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.particle.ParticleType
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.ParticleEvent;
import minegame159.meteorclient.settings.ParticleTypeListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.particle.ParticleType;

public class ParticleBlocker
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<List<ParticleType<?>>> particles;

    @EventHandler
    private void onRenderParticle(ParticleEvent lllIlllIIllll) {
        ParticleBlocker lllIlllIlIIII;
        if (lllIlllIIllll.particle != null && lllIlllIlIIII.particles.get().contains((Object)lllIlllIIllll.particle)) {
            lllIlllIIllll.cancel();
        }
    }

    public ParticleBlocker() {
        super(Categories.Render, "particle-blocker", "Stops specified particles from rendering.");
        ParticleBlocker lllIlllIlIlIl;
        lllIlllIlIlIl.sgGeneral = lllIlllIlIlIl.settings.getDefaultGroup();
        lllIlllIlIlIl.particles = lllIlllIlIlIl.sgGeneral.add(new ParticleTypeListSetting.Builder().name("particles").description("Particles to block.").defaultValue(new ArrayList(0)).build());
    }
}

