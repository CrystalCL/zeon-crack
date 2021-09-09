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

public class Spider
extends Module {
    private final /* synthetic */ Setting<Double> speed;
    private final /* synthetic */ SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllllIllIllIlllI) {
        Spider lllllllllllllllllllllIllIllIllII;
        if (!lllllllllllllllllllllIllIllIllII.mc.player.horizontalCollision) {
            return;
        }
        Vec3d lllllllllllllllllllllIllIllIllIl = lllllllllllllllllllllIllIllIllII.mc.player.getVelocity();
        if (lllllllllllllllllllllIllIllIllIl.y >= 0.2) {
            return;
        }
        lllllllllllllllllllllIllIllIllII.mc.player.setVelocity(lllllllllllllllllllllIllIllIllIl.x, lllllllllllllllllllllIllIllIllII.speed.get().doubleValue(), lllllllllllllllllllllIllIllIllIl.z);
    }

    public Spider() {
        super(Categories.Movement, "spider", "Allows you to climb walls like a spider.");
        Spider lllllllllllllllllllllIllIlllIIlI;
        lllllllllllllllllllllIllIlllIIlI.sgGeneral = lllllllllllllllllllllIllIlllIIlI.settings.getDefaultGroup();
        lllllllllllllllllllllIllIlllIIlI.speed = lllllllllllllllllllllIllIlllIIlI.sgGeneral.add(new DoubleSetting.Builder().name("climb-speed").description("The speed you go up blocks.").defaultValue(0.2).min(0.0).build());
    }
}

