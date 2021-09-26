/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.Target;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.math.Vec3d;

public class EndermanLook
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Mode> lookMode;

    private boolean angleCheck(Entity Entity2) {
        Vec3d Vec3d2 = this.mc.player.getRotationVec(1.0f).normalize();
        Vec3d Vec3d3 = new Vec3d(Entity2.getX() - this.mc.player.getX(), Entity2.getEyeY() - this.mc.player.getEyeY(), Entity2.getZ() - this.mc.player.getZ());
        double d = Vec3d3.length();
        double d2 = Vec3d2.dotProduct(Vec3d3 = Vec3d3.normalize());
        return d2 > 1.0 - 0.025 / d && this.mc.player.canSee(Entity2);
    }

    private boolean shouldLook() {
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2 instanceof EndermanEntity) || !Entity2.isAlive() || !this.angleCheck(Entity2)) continue;
            return true;
        }
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.lookMode.get() == Mode.LookAway) {
            if (this.mc.player.abilities.creativeMode || !this.shouldLook()) {
                return;
            }
            Rotations.rotate(this.mc.player.yaw, 90.0, -75, null);
        } else {
            for (Entity Entity2 : this.mc.world.getEntities()) {
                EndermanEntity EndermanEntity2;
                if (!(Entity2 instanceof EndermanEntity) || (EndermanEntity2 = (EndermanEntity)Entity2).isAngry() || !EndermanEntity2.isAlive() || !this.mc.player.canSee((Entity)EndermanEntity2)) continue;
                Rotations.rotate(Rotations.getYaw((Entity)EndermanEntity2), Rotations.getPitch((Entity)EndermanEntity2, Target.Head), -75, null);
                break;
            }
        }
    }

    public EndermanLook() {
        super(Categories.World, "enderman-look", "Either looks at all Endermen or prevents you from looking at Endermen.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.lookMode = this.sgGeneral.add(new EnumSetting.Builder().name("look-mode").description("How this module behaves.").defaultValue(Mode.LookAway).build());
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode LookAt = new Mode();
        public static final /* enum */ Mode LookAway = new Mode();
        private static final Mode[] $VALUES = Mode.$values();

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static Mode[] $values() {
            return new Mode[]{LookAt, LookAway};
        }
    }
}

