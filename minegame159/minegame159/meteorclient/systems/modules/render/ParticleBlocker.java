/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.class_2396;

public class ParticleBlocker
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<List<class_2396<?>>> particles;

    public ParticleBlocker() {
        super(Categories.Render, "particle-blocker", "Stops specified particles from rendering.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.particles = this.sgGeneral.add(new ParticleTypeListSetting.Builder().name("particles").description("Particles to block.").defaultValue(new ArrayList(0)).build());
    }

    @EventHandler
    private void onRenderParticle(ParticleEvent particleEvent) {
        if (particleEvent.particle != null && this.particles.get().contains(particleEvent.particle)) {
            particleEvent.cancel();
        }
    }
}

