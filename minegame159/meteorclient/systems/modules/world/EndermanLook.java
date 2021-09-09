/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.mob.EndermanEntity
 *  net.minecraft.util.math.Vec3d
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
    private final /* synthetic */ Setting<Mode> lookMode;
    private final /* synthetic */ SettingGroup sgGeneral;

    private boolean angleCheck(Entity lllllllllllllllllIllIllllllllIII) {
        EndermanLook lllllllllllllllllIllIlllllllllll;
        Vec3d lllllllllllllllllIllIlllllllllIl = lllllllllllllllllIllIlllllllllll.mc.player.getRotationVec(1.0f).normalize();
        Vec3d lllllllllllllllllIllIlllllllllII = new Vec3d(lllllllllllllllllIllIllllllllIII.getX() - lllllllllllllllllIllIlllllllllll.mc.player.getX(), lllllllllllllllllIllIllllllllIII.getEyeY() - lllllllllllllllllIllIlllllllllll.mc.player.getEyeY(), lllllllllllllllllIllIllllllllIII.getZ() - lllllllllllllllllIllIlllllllllll.mc.player.getZ());
        double lllllllllllllllllIllIllllllllIll = lllllllllllllllllIllIlllllllllII.length();
        double lllllllllllllllllIllIllllllllIlI = lllllllllllllllllIllIlllllllllIl.dotProduct(lllllllllllllllllIllIlllllllllII = lllllllllllllllllIllIlllllllllII.normalize());
        return lllllllllllllllllIllIllllllllIlI > 1.0 - 0.025 / lllllllllllllllllIllIllllllllIll && lllllllllllllllllIllIlllllllllll.mc.player.canSee(lllllllllllllllllIllIllllllllIII);
    }

    private boolean shouldLook() {
        EndermanLook lllllllllllllllllIlllIIIIIIIlIIl;
        for (Entity lllllllllllllllllIlllIIIIIIIlIlI : lllllllllllllllllIlllIIIIIIIlIIl.mc.world.getEntities()) {
            if (!(lllllllllllllllllIlllIIIIIIIlIlI instanceof EndermanEntity) || !lllllllllllllllllIlllIIIIIIIlIlI.isAlive() || !lllllllllllllllllIlllIIIIIIIlIIl.angleCheck(lllllllllllllllllIlllIIIIIIIlIlI)) continue;
            return true;
        }
        return false;
    }

    public EndermanLook() {
        super(Categories.World, "enderman-look", "Either looks at all Endermen or prevents you from looking at Endermen.");
        EndermanLook lllllllllllllllllIlllIIIIIIllIlI;
        lllllllllllllllllIlllIIIIIIllIlI.sgGeneral = lllllllllllllllllIlllIIIIIIllIlI.settings.getDefaultGroup();
        lllllllllllllllllIlllIIIIIIllIlI.lookMode = lllllllllllllllllIlllIIIIIIllIlI.sgGeneral.add(new EnumSetting.Builder().name("look-mode").description("How this module behaves.").defaultValue(Mode.LookAway).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIlllIIIIIIlIIlI) {
        EndermanLook lllllllllllllllllIlllIIIIIIlIIIl;
        if (lllllllllllllllllIlllIIIIIIlIIIl.lookMode.get() == Mode.LookAway) {
            if (lllllllllllllllllIlllIIIIIIlIIIl.mc.player.abilities.creativeMode || !lllllllllllllllllIlllIIIIIIlIIIl.shouldLook()) {
                return;
            }
            Rotations.rotate(lllllllllllllllllIlllIIIIIIlIIIl.mc.player.yaw, 90.0, -75, null);
        } else {
            for (Entity lllllllllllllllllIlllIIIIIIlIlII : lllllllllllllllllIlllIIIIIIlIIIl.mc.world.getEntities()) {
                EndermanEntity lllllllllllllllllIlllIIIIIIlIlIl;
                if (!(lllllllllllllllllIlllIIIIIIlIlII instanceof EndermanEntity) || (lllllllllllllllllIlllIIIIIIlIlIl = (EndermanEntity)lllllllllllllllllIlllIIIIIIlIlII).isAngry() || !lllllllllllllllllIlllIIIIIIlIlIl.isAlive() || !lllllllllllllllllIlllIIIIIIlIIIl.mc.player.canSee((Entity)lllllllllllllllllIlllIIIIIIlIlIl)) continue;
                Rotations.rotate(Rotations.getYaw((Entity)lllllllllllllllllIlllIIIIIIlIlIl), Rotations.getPitch((Entity)lllllllllllllllllIlllIIIIIIlIlIl, Target.Head), -75, null);
                break;
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode LookAt;
        public static final /* synthetic */ /* enum */ Mode LookAway;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String lllllllllllllllllIIlIlIlIllIIIll) {
            return Enum.valueOf(Mode.class, lllllllllllllllllIIlIlIlIllIIIll);
        }

        private Mode() {
            Mode lllllllllllllllllIIlIlIlIlIllllI;
        }

        static {
            LookAt = new Mode();
            LookAway = new Mode();
            $VALUES = Mode.$values();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{LookAt, LookAway};
        }
    }
}

