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

public class Spider
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Double> speed;

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!this.mc.player.horizontalCollision) {
            return;
        }
        Vec3d Vec3d2 = this.mc.player.getVelocity();
        if (Vec3d2.y >= 0.2) {
            return;
        }
        this.mc.player.setVelocity(Vec3d2.x, this.speed.get().doubleValue(), Vec3d2.z);
    }

    public Spider() {
        super(Categories.Movement, "spider", "Allows you to climb walls like a spider.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.speed = this.sgGeneral.add(new DoubleSetting.Builder().name("climb-speed").description("The speed you go up blocks.").defaultValue(0.2).min(0.0).build());
    }
}

