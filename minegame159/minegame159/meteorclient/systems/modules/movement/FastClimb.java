/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.util.math.Vec3d;

public class FastClimb
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Double> speed;

    public FastClimb() {
        super(Categories.Movement, "fast-climb", "Allows you to climb faster.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.speed = this.sgGeneral.add(new DoubleSetting.Builder().name("climb-speed").description("Your climb speed.").defaultValue(0.2872).min(0.0).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!this.mc.player.isClimbing() || !this.mc.player.horizontalCollision) {
            return;
        }
        if (this.mc.player.input.movementForward == 0.0f && this.mc.player.input.movementSideways == 0.0f) {
            return;
        }
        Vec3d Vec3d2 = this.mc.player.getVelocity();
        this.mc.player.setVelocity(Vec3d2.x, this.speed.get().doubleValue(), Vec3d2.z);
    }
}

