/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.projectile.PersistentProjectileEntity
 *  net.minecraft.entity.projectile.ProjectileEntity
 *  net.minecraft.world.BlockView
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Box
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.shape.VoxelShapes
 *  net.minecraft.util.shape.VoxelShapes6
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.ProjectileInGroundAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShapes6;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class ArrowDodge
extends Module {
    private final /* synthetic */ List<Vec3d> possibleMoveDirections;
    private final /* synthetic */ Setting<MoveType> moveType;
    private final /* synthetic */ Setting<Boolean> groundCheck;
    private final /* synthetic */ Setting<Integer> arrowLookahead;
    private final /* synthetic */ Setting<Double> moveSpeed;
    private final /* synthetic */ SettingGroup sgMovement;
    private final /* synthetic */ SettingGroup sgGeneral;

    private void move(Vec3d lllllllllllllllllllIIIIlllIIlIII) {
        ArrowDodge lllllllllllllllllllIIIIlllIIlIIl;
        MoveType lllllllllllllllllllIIIIlllIIlIlI = lllllllllllllllllllIIIIlllIIlIIl.moveType.get();
        if (lllllllllllllllllllIIIIlllIIlIlI == MoveType.Client) {
            lllllllllllllllllllIIIIlllIIlIIl.mc.player.setVelocity(lllllllllllllllllllIIIIlllIIlIII);
        } else if (lllllllllllllllllllIIIIlllIIlIlI == MoveType.Packet) {
            Vec3d lllllllllllllllllllIIIIlllIIllIl = lllllllllllllllllllIIIIlllIIlIIl.mc.player.getPos().add(lllllllllllllllllllIIIIlllIIlIII);
            lllllllllllllllllllIIIIlllIIlIIl.mc.player.networkHandler.sendPacket((VoxelShapes6)new PositionOnly(lllllllllllllllllllIIIIlllIIllIl.x, lllllllllllllllllllIIIIlllIIllIl.y, lllllllllllllllllllIIIIlllIIllIl.z, false));
            lllllllllllllllllllIIIIlllIIlIIl.mc.player.networkHandler.sendPacket((VoxelShapes6)new PositionOnly(lllllllllllllllllllIIIIlllIIllIl.x, lllllllllllllllllllIIIIlllIIllIl.y - 0.01, lllllllllllllllllllIIIIlllIIllIl.z, true));
        }
    }

    public ArrowDodge() {
        super(Categories.Combat, "arrow-dodge", "Tries to dodge arrows coming at you");
        ArrowDodge lllllllllllllllllllIIIlIIIIIIIlI;
        lllllllllllllllllllIIIlIIIIIIIlI.sgGeneral = lllllllllllllllllllIIIlIIIIIIIlI.settings.getDefaultGroup();
        lllllllllllllllllllIIIlIIIIIIIlI.sgMovement = lllllllllllllllllllIIIlIIIIIIIlI.settings.createGroup("Movement");
        lllllllllllllllllllIIIlIIIIIIIlI.arrowLookahead = lllllllllllllllllllIIIlIIIIIIIlI.sgGeneral.add(new IntSetting.Builder().name("arrow-lookahead").description("How many steps into the future should be taken into consideration when deciding the direction").defaultValue(500).min(1).max(750).build());
        lllllllllllllllllllIIIlIIIIIIIlI.moveType = lllllllllllllllllllIIIlIIIIIIIlI.sgMovement.add(new EnumSetting.Builder().name("move-type").description("The way you are moved by this module").defaultValue(MoveType.Client).build());
        lllllllllllllllllllIIIlIIIIIIIlI.moveSpeed = lllllllllllllllllllIIIlIIIIIIIlI.sgMovement.add(new DoubleSetting.Builder().name("move-speed").description("How fast should you be when dodging arrow").defaultValue(1.0).min(0.01).sliderMax(5.0).build());
        lllllllllllllllllllIIIlIIIIIIIlI.groundCheck = lllllllllllllllllllIIIlIIIIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("ground-check").description("Tries to prevent you from falling to your death.").defaultValue(true).build());
        lllllllllllllllllllIIIlIIIIIIIlI.possibleMoveDirections = Arrays.asList(new Vec3d[]{new Vec3d(1.0, 0.0, 1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, -1.0)});
    }

    private boolean isValid(Vec3d lllllllllllllllllllIIIIllIlllIlI, List<Box> lllllllllllllllllllIIIIllIlllIIl, Box lllllllllllllllllllIIIIllIlllIII) {
        ArrowDodge lllllllllllllllllllIIIIllIlllIll;
        BlockPos lllllllllllllllllllIIIIllIllIlll = null;
        for (Box lllllllllllllllllllIIIIllIllllII : lllllllllllllllllllIIIIllIlllIIl) {
            Box lllllllllllllllllllIIIIllIllllIl;
            if (lllllllllllllllllllIIIIllIllllII.intersects(lllllllllllllllllllIIIIllIllllIl = lllllllllllllllllllIIIIllIlllIII.offset(lllllllllllllllllllIIIIllIlllIlI))) {
                return false;
            }
            lllllllllllllllllllIIIIllIllIlll = lllllllllllllllllllIIIIllIlllIll.mc.player.getBlockPos().add(lllllllllllllllllllIIIIllIlllIlI.x, lllllllllllllllllllIIIIllIlllIlI.y, lllllllllllllllllllIIIIllIlllIlI.z);
            if (lllllllllllllllllllIIIIllIlllIll.mc.world.getBlockState(lllllllllllllllllllIIIIllIllIlll).getCollisionShape((BlockView)lllllllllllllllllllIIIIllIlllIll.mc.world, lllllllllllllllllllIIIIllIllIlll) == VoxelShapes.empty()) continue;
            return false;
        }
        if (lllllllllllllllllllIIIIllIlllIll.groundCheck.get().booleanValue() && lllllllllllllllllllIIIIllIllIlll != null) {
            return lllllllllllllllllllIIIIllIlllIll.mc.world.getBlockState(lllllllllllllllllllIIIIllIllIlll.down()).getCollisionShape((BlockView)lllllllllllllllllllIIIIllIlllIll.mc.world, lllllllllllllllllllIIIIllIllIlll.down()) != VoxelShapes.empty();
        }
        return true;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllIIIIllllIIIll) {
        ArrowDodge lllllllllllllllllllIIIIllllIIlII;
        if (lllllllllllllllllllIIIIllllIIlII.mc.player == null) {
            return;
        }
        Box lllllllllllllllllllIIIIllllIIIlI = lllllllllllllllllllIIIIllllIIlII.mc.player.getBoundingBox();
        if (lllllllllllllllllllIIIIllllIIIlI == null) {
            return;
        }
        lllllllllllllllllllIIIIllllIIIlI = lllllllllllllllllllIIIIllllIIIlI.expand(0.6);
        Double lllllllllllllllllllIIIIllllIIIIl = lllllllllllllllllllIIIIllllIIlII.moveSpeed.get();
        for (Entity lllllllllllllllllllIIIIllllIIlIl : lllllllllllllllllllIIIIllllIIlII.mc.world.getEntities()) {
            if (!(lllllllllllllllllllIIIIllllIIlIl instanceof ProjectileEntity) || ((ProjectileEntity)lllllllllllllllllllIIIIllllIIlIl).getOwner() == lllllllllllllllllllIIIIllllIIlII.mc.player || lllllllllllllllllllIIIIllllIIlIl instanceof PersistentProjectileEntity && ((ProjectileInGroundAccessor)lllllllllllllllllllIIIIllllIIlIl).getInGround()) continue;
            ArrayList<Box> lllllllllllllllllllIIIIllllIIllI = new ArrayList<Box>();
            for (int lllllllllllllllllllIIIIlllllIIII = 0; lllllllllllllllllllIIIIlllllIIII < lllllllllllllllllllIIIIllllIIlII.arrowLookahead.get(); ++lllllllllllllllllllIIIIlllllIIII) {
                Vec3d lllllllllllllllllllIIIIlllllIIIl = lllllllllllllllllllIIIIllllIIlIl.getPos().add(lllllllllllllllllllIIIIllllIIlIl.getVelocity().multiply((double)((float)lllllllllllllllllllIIIIlllllIIII / 5.0f)));
                lllllllllllllllllllIIIIllllIIllI.add(new Box(lllllllllllllllllllIIIIlllllIIIl.subtract(lllllllllllllllllllIIIIllllIIlIl.getBoundingBox().getXLength() / 2.0, 0.0, lllllllllllllllllllIIIIllllIIlIl.getBoundingBox().getZLength() / 2.0), lllllllllllllllllllIIIIlllllIIIl.add(lllllllllllllllllllIIIIllllIIlIl.getBoundingBox().getXLength() / 2.0, lllllllllllllllllllIIIIllllIIlIl.getBoundingBox().getYLength(), lllllllllllllllllllIIIIllllIIlIl.getBoundingBox().getZLength() / 2.0)));
            }
            for (Box lllllllllllllllllllIIIIllllIIlll : lllllllllllllllllllIIIIllllIIllI) {
                if (!lllllllllllllllllllIIIIllllIIIlI.intersects(lllllllllllllllllllIIIIllllIIlll)) continue;
                Collections.shuffle(lllllllllllllllllllIIIIllllIIlII.possibleMoveDirections);
                boolean lllllllllllllllllllIIIIllllIlIII = false;
                for (Vec3d lllllllllllllllllllIIIIllllIlllI : lllllllllllllllllllIIIIllllIIlII.possibleMoveDirections) {
                    Vec3d lllllllllllllllllllIIIIllllIllll = lllllllllllllllllllIIIIllllIlllI.multiply(lllllllllllllllllllIIIIllllIIIIl.doubleValue());
                    if (!lllllllllllllllllllIIIIllllIIlII.isValid(lllllllllllllllllllIIIIllllIllll, lllllllllllllllllllIIIIllllIIllI, lllllllllllllllllllIIIIllllIIIlI)) continue;
                    lllllllllllllllllllIIIIllllIIlII.move(lllllllllllllllllllIIIIllllIllll);
                    lllllllllllllllllllIIIIllllIlIII = true;
                    break;
                }
                if (lllllllllllllllllllIIIIllllIlIII) continue;
                double lllllllllllllllllllIIIIllllIllIl = Math.toRadians(lllllllllllllllllllIIIIllllIIlIl.yaw);
                double lllllllllllllllllllIIIIllllIllII = Math.toRadians(lllllllllllllllllllIIIIllllIIlIl.pitch);
                double lllllllllllllllllllIIIIllllIlIll = Math.sin(lllllllllllllllllllIIIIllllIllIl) * Math.cos(lllllllllllllllllllIIIIllllIllII) * lllllllllllllllllllIIIIllllIIIIl;
                double lllllllllllllllllllIIIIllllIlIlI = Math.sin(lllllllllllllllllllIIIIllllIllII) * lllllllllllllllllllIIIIllllIIIIl;
                double lllllllllllllllllllIIIIllllIlIIl = -Math.cos(lllllllllllllllllllIIIIllllIllIl) * Math.cos(lllllllllllllllllllIIIIllllIllII) * lllllllllllllllllllIIIIllllIIIIl;
                lllllllllllllllllllIIIIllllIIlII.move(new Vec3d(lllllllllllllllllllIIIIllllIlIll, lllllllllllllllllllIIIIllllIlIlI, lllllllllllllllllllIIIIllllIlIIl));
            }
        }
    }

    public static final class MoveType
    extends Enum<MoveType> {
        public static final /* synthetic */ /* enum */ MoveType Packet;
        private static final /* synthetic */ MoveType[] $VALUES;
        public static final /* synthetic */ /* enum */ MoveType Client;

        private static /* synthetic */ MoveType[] $values() {
            return new MoveType[]{Client, Packet};
        }

        public static MoveType[] values() {
            return (MoveType[])$VALUES.clone();
        }

        public static MoveType valueOf(String lIIIllllIII) {
            return Enum.valueOf(MoveType.class, lIIIllllIII);
        }

        static {
            Client = new MoveType();
            Packet = new MoveType();
            $VALUES = MoveType.$values();
        }

        private MoveType() {
            MoveType lIIIlllIIll;
        }
    }
}

