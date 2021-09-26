/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ParticleTypeListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class Trail
extends Module {
    private final Setting<List<ParticleType<?>>> particles;
    private final Setting<Boolean> pause;
    private final SettingGroup sgGeneral;

    public Trail() {
        super(Categories.Render, "trail", "Renders a customizable trail behind your player.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.particles = this.sgGeneral.add(new ParticleTypeListSetting.Builder().name("particles").description("Particles to draw.").defaultValue(new ArrayList(0)).build());
        this.pause = this.sgGeneral.add(new BoolSetting.Builder().name("pause-when-stationary").description("Whether or not to add particles when you are not moving.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.pause.get().booleanValue() && this.mc.player.input.movementForward == 0.0f && this.mc.player.input.movementSideways == 0.0f && !this.mc.options.keyJump.isPressed()) {
            return;
        }
        for (ParticleType<?> ParticleType2 : this.particles.get()) {
            this.mc.world.addParticle((ParticleEffect)ParticleType2, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), 0.0, 0.0, 0.0);
        }
    }
}

