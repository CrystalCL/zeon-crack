/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import baritone.api.BaritoneAPI;
import baritone.api.utils.Rotation;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.utils.misc.BaritoneUtils;
import minegame159.meteorclient.utils.misc.Vector2;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.item.PotionItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;

public class PlayerUtils {
    private static final double diagonal;
    private static final MinecraftClient mc;
    private static final Vec3d horizontalVelocity;
    private static final Vec3d hitPos;

    public static boolean placeBlock(BlockPos BlockPos2, int n, Hand Hand2) {
        if (n == -1) {
            return false;
        }
        int n2 = PlayerUtils.mc.player.inventory.selectedSlot;
        PlayerUtils.mc.player.inventory.selectedSlot = n;
        boolean bl = PlayerUtils.placeBlock(BlockPos2, Hand2, true);
        PlayerUtils.mc.player.inventory.selectedSlot = n2;
        return bl;
    }

    public static boolean shouldPause(boolean bl, boolean bl2, boolean bl3) {
        if (bl && PlayerUtils.mc.interactionManager.isBreakingBlock()) {
            return true;
        }
        if (bl2 && PlayerUtils.mc.player.isUsingItem() && (PlayerUtils.mc.player.getMainHandStack().getItem().isFood() || PlayerUtils.mc.player.getOffHandStack().getItem().isFood())) {
            return true;
        }
        return bl3 && PlayerUtils.mc.player.isUsingItem() && (PlayerUtils.mc.player.getMainHandStack().getItem() instanceof PotionItem || PlayerUtils.mc.player.getOffHandStack().getItem() instanceof PotionItem);
    }

    static {
        mc = MinecraftClient.getInstance();
        hitPos = new Vec3d(0.0, 0.0, 0.0);
        diagonal = 1.0 / Math.sqrt(2.0);
        horizontalVelocity = new Vec3d(0.0, 0.0, 0.0);
    }

    public static boolean placeBlock(BlockPos BlockPos2, Hand Hand2, boolean bl) {
        if (!BlockUtils.canPlace(BlockPos2)) {
            return false;
        }
        for (Direction Direction2 : Direction.values()) {
            BlockPos BlockPos3 = BlockPos2.offset(Direction2);
            Direction Direction3 = Direction2.getOpposite();
            if (PlayerUtils.mc.world.getBlockState(BlockPos3).isAir() || BlockUtils.isClickable(PlayerUtils.mc.world.getBlockState(BlockPos3).getBlock())) continue;
            ((IVec3d)hitPos).set((double)BlockPos3.getX() + 0.5 + (double)Direction3.getVector().getX() * 0.5, (double)BlockPos3.getY() + 0.5 + (double)Direction3.getVector().getY() * 0.5, (double)BlockPos3.getZ() + 0.5 + (double)Direction3.getVector().getZ() * 0.5);
            boolean bl2 = PlayerUtils.mc.player.input.sneaking;
            PlayerUtils.mc.player.input.sneaking = false;
            PlayerUtils.mc.interactionManager.interactBlock(PlayerUtils.mc.player, PlayerUtils.mc.world, Hand2, new BlockHitResult(hitPos, Direction3, BlockPos3, false));
            if (bl) {
                PlayerUtils.mc.player.swingHand(Hand2);
            }
            PlayerUtils.mc.player.input.sneaking = bl2;
            return true;
        }
        ((IVec3d)hitPos).set((Vec3i)BlockPos2);
        PlayerUtils.mc.interactionManager.interactBlock(PlayerUtils.mc.player, PlayerUtils.mc.world, Hand2, new BlockHitResult(hitPos, Direction.UP, BlockPos2, false));
        if (bl) {
            PlayerUtils.mc.player.swingHand(Hand2);
        }
        return true;
    }

    public static void centerPlayer() {
        double d = (double)MathHelper.floor((double)PlayerUtils.mc.player.getX()) + 0.5;
        double d2 = (double)MathHelper.floor((double)PlayerUtils.mc.player.getZ()) + 0.5;
        PlayerUtils.mc.player.setPosition(d, PlayerUtils.mc.player.getY(), d2);
        PlayerUtils.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(PlayerUtils.mc.player.getX(), PlayerUtils.mc.player.getY(), PlayerUtils.mc.player.getZ(), PlayerUtils.mc.player.isOnGround()));
    }

    public static boolean isInHole() {
        return !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(1, 0, 0)).isAir() && !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(-1, 0, 0)).isAir() && !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(0, 0, 1)).isAir() && !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(0, 0, -1)).isAir() && !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(0, -1, 0)).isAir();
    }

    public static float[] calculateAngle(Vec3d Vec3d2) {
        Vec3d Vec3d3 = new Vec3d(PlayerUtils.mc.player.getX(), PlayerUtils.mc.player.getY() + (double)PlayerUtils.mc.player.getEyeHeight(PlayerUtils.mc.player.getPose()), PlayerUtils.mc.player.getZ());
        double d = Vec3d2.x - Vec3d3.x;
        double d2 = (Vec3d2.y - Vec3d3.y) * -1.0;
        double d3 = Vec3d2.z - Vec3d3.z;
        double d4 = MathHelper.sqrt((double)(d * d + d3 * d3));
        return new float[]{(float)MathHelper.wrapDegrees((double)(Math.toDegrees(Math.atan2(d3, d)) - 90.0)), (float)MathHelper.wrapDegrees((double)Math.toDegrees(Math.atan2(d2, d4)))};
    }

    public static boolean isSprinting() {
        return PlayerUtils.mc.player.isSprinting() && (PlayerUtils.mc.player.forwardSpeed != 0.0f || PlayerUtils.mc.player.sidewaysSpeed != 0.0f);
    }

    public static boolean placeBlock(BlockPos BlockPos2, Hand Hand2) {
        return PlayerUtils.placeBlock(BlockPos2, Hand2, true);
    }

    public static boolean canSeeEntity(Entity Entity2) {
        Vec3d Vec3d2 = new Vec3d(0.0, 0.0, 0.0);
        Vec3d Vec3d3 = new Vec3d(0.0, 0.0, 0.0);
        ((IVec3d)Vec3d2).set(PlayerUtils.mc.player.getX(), PlayerUtils.mc.player.getY() + (double)PlayerUtils.mc.player.getStandingEyeHeight(), PlayerUtils.mc.player.getZ());
        ((IVec3d)Vec3d3).set(Entity2.getX(), Entity2.getY(), Entity2.getZ());
        boolean bl = PlayerUtils.mc.world.raycast(new RaycastContext(Vec3d2, Vec3d3, RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)PlayerUtils.mc.player)).getType() == HitResult.class_240.MISS;
        ((IVec3d)Vec3d3).set(Entity2.getX(), Entity2.getY() + (double)Entity2.getStandingEyeHeight(), Entity2.getZ());
        boolean bl2 = PlayerUtils.mc.world.raycast(new RaycastContext(Vec3d2, Vec3d3, RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)PlayerUtils.mc.player)).getType() == HitResult.class_240.MISS;
        return bl || bl2;
    }

    public static boolean isMoving() {
        return PlayerUtils.mc.player.forwardSpeed != 0.0f || PlayerUtils.mc.player.sidewaysSpeed != 0.0f;
    }

    public static Vector2 transformStrafe(double d) {
        float f = PlayerUtils.mc.player.input.movementForward;
        float f2 = PlayerUtils.mc.player.input.movementSideways;
        float f3 = PlayerUtils.mc.player.prevYaw + (PlayerUtils.mc.player.yaw - PlayerUtils.mc.player.prevYaw) * mc.getTickDelta();
        if (f == 0.0f && f2 == 0.0f) {
            return new Vector2(0.0, 0.0);
        }
        if (f != 0.0f) {
            if (f2 >= 1.0f) {
                f3 += (float)(f > 0.0f ? -45 : 45);
                f2 = 0.0f;
            } else if (f2 <= -1.0f) {
                f3 += (float)(f > 0.0f ? 45 : -45);
                f2 = 0.0f;
            }
            if (f > 0.0f) {
                f = 1.0f;
            } else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        double d2 = Math.cos(Math.toRadians(f3 + 90.0f));
        double d3 = Math.sin(Math.toRadians(f3 + 90.0f));
        double d4 = (double)f * d * d2 + (double)f2 * d * d3;
        double d5 = (double)f * d * d3 - (double)f2 * d * d2;
        return new Vector2(d4, d5);
    }

    public static Vec3d getHorizontalVelocity(double d) {
        Rotation rotation;
        float f = PlayerUtils.mc.player.yaw;
        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && (rotation = BaritoneUtils.getTarget()) != null) {
            f = rotation.getYaw();
        }
        rotation = Vec3d.fromPolar((float)0.0f, (float)f);
        Vec3d Vec3d2 = Vec3d.fromPolar((float)0.0f, (float)(f + 90.0f));
        double d2 = 0.0;
        double d3 = 0.0;
        boolean bl = false;
        if (PlayerUtils.mc.player.input.pressingForward) {
            d2 += rotation.x / 20.0 * d;
            d3 += rotation.z / 20.0 * d;
            bl = true;
        }
        if (PlayerUtils.mc.player.input.pressingBack) {
            d2 -= rotation.x / 20.0 * d;
            d3 -= rotation.z / 20.0 * d;
            bl = true;
        }
        boolean bl2 = false;
        if (PlayerUtils.mc.player.input.pressingRight) {
            d2 += Vec3d2.x / 20.0 * d;
            d3 += Vec3d2.z / 20.0 * d;
            bl2 = true;
        }
        if (PlayerUtils.mc.player.input.pressingLeft) {
            d2 -= Vec3d2.x / 20.0 * d;
            d3 -= Vec3d2.z / 20.0 * d;
            bl2 = true;
        }
        if (bl && bl2) {
            d2 *= diagonal;
            d3 *= diagonal;
        }
        ((IVec3d)horizontalVelocity).setXZ(d2, d3);
        return horizontalVelocity;
    }
}

