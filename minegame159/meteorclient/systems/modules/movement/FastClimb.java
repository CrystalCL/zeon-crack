/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3d
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
    private final /* synthetic */ Setting<Double> speed;
    private final /* synthetic */ SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllIlIlIlIlIlIIl) {
        FastClimb lllllllllllllllllllIlIlIlIlIIlll;
        if (!lllllllllllllllllllIlIlIlIlIIlll.mc.player.isClimbing() || !lllllllllllllllllllIlIlIlIlIIlll.mc.player.horizontalCollision) {
            return;
        }
        if (lllllllllllllllllllIlIlIlIlIIlll.mc.player.input.movementForward == 0.0f && lllllllllllllllllllIlIlIlIlIIlll.mc.player.input.movementSideways == 0.0f) {
            return;
        }
        Vec3d lllllllllllllllllllIlIlIlIlIlIII = lllllllllllllllllllIlIlIlIlIIlll.mc.player.getVelocity();
        lllllllllllllllllllIlIlIlIlIIlll.mc.player.setVelocity(lllllllllllllllllllIlIlIlIlIlIII.x, lllllllllllllllllllIlIlIlIlIIlll.speed.get().doubleValue(), lllllllllllllllllllIlIlIlIlIlIII.z);
    }

    public FastClimb() {
        super(Categories.Movement, "fast-climb", "Allows you to climb faster.");
        FastClimb lllllllllllllllllllIlIlIlIlIllIl;
        lllllllllllllllllllIlIlIlIlIllIl.sgGeneral = lllllllllllllllllllIlIlIlIlIllIl.settings.getDefaultGroup();
        lllllllllllllllllllIlIlIlIlIllIl.speed = lllllllllllllllllllIlIlIlIlIllIl.sgGeneral.add(new DoubleSetting.Builder().name("climb-speed").description("Your climb speed.").defaultValue(0.2872).min(0.0).build());
    }
}

