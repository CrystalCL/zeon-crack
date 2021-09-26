/*
 * Decompiled with CFR 0.151.
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
    private final Setting<MoveType> moveType;
    private final SettingGroup sgMovement;
    private final List<Vec3d> possibleMoveDirections;
    private final Setting<Integer> arrowLookahead;
    private final Setting<Double> moveSpeed;
    private final Setting<Boolean> groundCheck;
    private final SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.player == null) {
            return;
        }
        Box Box3 = this.mc.player.getBoundingBox();
        if (Box3 == null) {
            return;
        }
        Box3 = Box3.expand(0.6);
        Double d = this.moveSpeed.get();
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2 instanceof ProjectileEntity) || ((ProjectileEntity)Entity2).getOwner() == this.mc.player || Entity2 instanceof PersistentProjectileEntity && ((ProjectileInGroundAccessor)Entity2).getInGround()) continue;
            ArrayList<Box> arrayList = new ArrayList<Box>();
            for (int i = 0; i < this.arrowLookahead.get(); ++i) {
                Box Box4 = Entity2.getPos().add(Entity2.getVelocity().multiply((double)((float)i / 5.0f)));
                arrayList.add(new Box(Box4.subtract(Entity2.getBoundingBox().getXLength() / 2.0, 0.0, Entity2.getBoundingBox().getZLength() / 2.0), Box4.add(Entity2.getBoundingBox().getXLength() / 2.0, Entity2.getBoundingBox().getYLength(), Entity2.getBoundingBox().getZLength() / 2.0)));
            }
            for (Box Box4 : arrayList) {
                if (!Box3.intersects(Box4)) continue;
                Collections.shuffle(this.possibleMoveDirections);
                boolean bl = false;
                for (Vec3d Vec3d2 : this.possibleMoveDirections) {
                    Vec3d Vec3d3 = Vec3d2.multiply(d.doubleValue());
                    if (!this.isValid(Vec3d3, arrayList, Box3)) continue;
                    this.move(Vec3d3);
                    bl = true;
                    break;
                }
                if (bl) continue;
                double d2 = Math.toRadians(Entity2.yaw);
                double d3 = Math.toRadians(Entity2.pitch);
                double d4 = Math.sin(d2) * Math.cos(d3) * d;
                double d5 = Math.sin(d3) * d;
                double d6 = -Math.cos(d2) * Math.cos(d3) * d;
                this.move(new Vec3d(d4, d5, d6));
            }
        }
    }

    private void move(Vec3d Vec3d2) {
        MoveType moveType = this.moveType.get();
        if (moveType == MoveType.Client) {
            this.mc.player.setVelocity(Vec3d2);
        } else if (moveType == MoveType.Packet) {
            Vec3d Vec3d3 = this.mc.player.getPos().add(Vec3d2);
            this.mc.player.networkHandler.sendPacket((VoxelShapes6)new PlayerMoveC2SPacket.class_2829(Vec3d3.x, Vec3d3.y, Vec3d3.z, false));
            this.mc.player.networkHandler.sendPacket((VoxelShapes6)new PlayerMoveC2SPacket.class_2829(Vec3d3.x, Vec3d3.y - 0.01, Vec3d3.z, true));
        }
    }

    public ArrowDodge() {
        super(Categories.Combat, "arrow-dodge", "Tries to dodge arrows coming at you");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgMovement = this.settings.createGroup("Movement");
        this.arrowLookahead = this.sgGeneral.add(new IntSetting.Builder().name("arrow-lookahead").description("How many steps into the future should be taken into consideration when deciding the direction").defaultValue(500).min(1).max(750).build());
        this.moveType = this.sgMovement.add(new EnumSetting.Builder().name("move-type").description("The way you are moved by this module").defaultValue(MoveType.Client).build());
        this.moveSpeed = this.sgMovement.add(new DoubleSetting.Builder().name("move-speed").description("How fast should you be when dodging arrow").defaultValue(1.0).min(0.01).sliderMax(5.0).build());
        this.groundCheck = this.sgGeneral.add(new BoolSetting.Builder().name("ground-check").description("Tries to prevent you from falling to your death.").defaultValue(true).build());
        this.possibleMoveDirections = Arrays.asList(new Vec3d(1.0, 0.0, 1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, -1.0));
    }

    private boolean isValid(Vec3d Vec3d2, List<Box> list, Box Box3) {
        BlockPos BlockPos2 = null;
        for (Box Box4 : list) {
            Box Box6;
            if (Box4.intersects(Box6 = Box3.offset(Vec3d2))) {
                return false;
            }
            BlockPos2 = this.mc.player.getBlockPos().add(Vec3d2.x, Vec3d2.y, Vec3d2.z);
            if (this.mc.world.getBlockState(BlockPos2).getCollisionShape((BlockView)this.mc.world, BlockPos2) == VoxelShapes.empty()) continue;
            return false;
        }
        if (this.groundCheck.get().booleanValue() && BlockPos2 != null) {
            return this.mc.world.getBlockState(BlockPos2.down()).getCollisionShape((BlockView)this.mc.world, BlockPos2.down()) != VoxelShapes.empty();
        }
        return true;
    }

    public static final class MoveType
    extends Enum<MoveType> {
        public static final /* enum */ MoveType Client = new MoveType();
        public static final /* enum */ MoveType Packet = new MoveType();
        private static final MoveType[] $VALUES = MoveType.$values();

        public static MoveType[] values() {
            return (MoveType[])$VALUES.clone();
        }

        public static MoveType valueOf(String string) {
            return Enum.valueOf(MoveType.class, string);
        }

        private static MoveType[] $values() {
            return new MoveType[]{Client, Packet};
        }
    }
}

