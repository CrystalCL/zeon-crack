/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.particle.ParticleEffect
 *  net.minecraft.particle.ParticleType
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
    private final /* synthetic */ Setting<Boolean> pause;
    private final /* synthetic */ Setting<List<ParticleType<?>>> particles;
    private final /* synthetic */ SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllIllllllllIIIllll) {
        Trail llllllllllllllllIllllllllIIIlllI;
        if (llllllllllllllllIllllllllIIIlllI.pause.get().booleanValue() && llllllllllllllllIllllllllIIIlllI.mc.player.input.movementForward == 0.0f && llllllllllllllllIllllllllIIIlllI.mc.player.input.movementSideways == 0.0f && !llllllllllllllllIllllllllIIIlllI.mc.options.keyJump.isPressed()) {
            return;
        }
        for (ParticleType<?> llllllllllllllllIllllllllIIlIIIl : llllllllllllllllIllllllllIIIlllI.particles.get()) {
            llllllllllllllllIllllllllIIIlllI.mc.world.addParticle((ParticleEffect)llllllllllllllllIllllllllIIlIIIl, llllllllllllllllIllllllllIIIlllI.mc.player.getX(), llllllllllllllllIllllllllIIIlllI.mc.player.getY(), llllllllllllllllIllllllllIIIlllI.mc.player.getZ(), 0.0, 0.0, 0.0);
        }
    }

    public Trail() {
        super(Categories.Render, "trail", "Renders a customizable trail behind your player.");
        Trail llllllllllllllllIllllllllIIlIlIl;
        llllllllllllllllIllllllllIIlIlIl.sgGeneral = llllllllllllllllIllllllllIIlIlIl.settings.getDefaultGroup();
        llllllllllllllllIllllllllIIlIlIl.particles = llllllllllllllllIllllllllIIlIlIl.sgGeneral.add(new ParticleTypeListSetting.Builder().name("particles").description("Particles to draw.").defaultValue(new ArrayList(0)).build());
        llllllllllllllllIllllllllIIlIlIl.pause = llllllllllllllllIllllllllIIlIlIl.sgGeneral.add(new BoolSetting.Builder().name("pause-when-stationary").description("Whether or not to add particles when you are not moving.").defaultValue(true).build());
    }
}

