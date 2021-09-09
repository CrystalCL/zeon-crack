/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.utils.Rotation
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.PotionItem
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$class_242
 *  net.minecraft.world.RaycastContext$class_3960
 *  net.minecraft.util.hit.BlockHitResult
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
    private static final /* synthetic */ Vec3d hitPos;
    private static final /* synthetic */ MinecraftClient mc;
    private static final /* synthetic */ double diagonal;
    private static final /* synthetic */ Vec3d horizontalVelocity;

    static {
        mc = MinecraftClient.getInstance();
        hitPos = new Vec3d(0.0, 0.0, 0.0);
        diagonal = 1.0 / Math.sqrt(2.0);
        horizontalVelocity = new Vec3d(0.0, 0.0, 0.0);
    }

    public static boolean isSprinting() {
        return PlayerUtils.mc.player.isSprinting() && (PlayerUtils.mc.player.forwardSpeed != 0.0f || PlayerUtils.mc.player.sidewaysSpeed != 0.0f);
    }

    public static float[] calculateAngle(Vec3d llllllllllllllllllllIlllIlIIIIlI) {
        Vec3d llllllllllllllllllllIlllIlIIIlll = new Vec3d(PlayerUtils.mc.player.getX(), PlayerUtils.mc.player.getY() + (double)PlayerUtils.mc.player.getEyeHeight(PlayerUtils.mc.player.getPose()), PlayerUtils.mc.player.getZ());
        double llllllllllllllllllllIlllIlIIIllI = llllllllllllllllllllIlllIlIIIIlI.x - llllllllllllllllllllIlllIlIIIlll.x;
        double llllllllllllllllllllIlllIlIIIlIl = (llllllllllllllllllllIlllIlIIIIlI.y - llllllllllllllllllllIlllIlIIIlll.y) * -1.0;
        double llllllllllllllllllllIlllIlIIIlII = llllllllllllllllllllIlllIlIIIIlI.z - llllllllllllllllllllIlllIlIIIlll.z;
        double llllllllllllllllllllIlllIlIIIIll = MathHelper.sqrt((double)(llllllllllllllllllllIlllIlIIIllI * llllllllllllllllllllIlllIlIIIllI + llllllllllllllllllllIlllIlIIIlII * llllllllllllllllllllIlllIlIIIlII));
        return new float[]{(float)MathHelper.wrapDegrees((double)(Math.toDegrees(Math.atan2(llllllllllllllllllllIlllIlIIIlII, llllllllllllllllllllIlllIlIIIllI)) - 90.0)), (float)MathHelper.wrapDegrees((double)Math.toDegrees(Math.atan2(llllllllllllllllllllIlllIlIIIlIl, llllllllllllllllllllIlllIlIIIIll)))};
    }

    public static Vector2 transformStrafe(double llllllllllllllllllllIlllIIlIlIll) {
        float llllllllllllllllllllIlllIIlIlIlI = PlayerUtils.mc.player.input.movementForward;
        float llllllllllllllllllllIlllIIlIlIIl = PlayerUtils.mc.player.input.movementSideways;
        float llllllllllllllllllllIlllIIlIlIII = PlayerUtils.mc.player.prevYaw + (PlayerUtils.mc.player.yaw - PlayerUtils.mc.player.prevYaw) * mc.getTickDelta();
        if (llllllllllllllllllllIlllIIlIlIlI == 0.0f && llllllllllllllllllllIlllIIlIlIIl == 0.0f) {
            return new Vector2(0.0, 0.0);
        }
        if (llllllllllllllllllllIlllIIlIlIlI != 0.0f) {
            if (llllllllllllllllllllIlllIIlIlIIl >= 1.0f) {
                llllllllllllllllllllIlllIIlIlIII += (float)(llllllllllllllllllllIlllIIlIlIlI > 0.0f ? -45 : 45);
                llllllllllllllllllllIlllIIlIlIIl = 0.0f;
            } else if (llllllllllllllllllllIlllIIlIlIIl <= -1.0f) {
                llllllllllllllllllllIlllIIlIlIII += (float)(llllllllllllllllllllIlllIIlIlIlI > 0.0f ? 45 : -45);
                llllllllllllllllllllIlllIIlIlIIl = 0.0f;
            }
            if (llllllllllllllllllllIlllIIlIlIlI > 0.0f) {
                llllllllllllllllllllIlllIIlIlIlI = 1.0f;
            } else if (llllllllllllllllllllIlllIIlIlIlI < 0.0f) {
                llllllllllllllllllllIlllIIlIlIlI = -1.0f;
            }
        }
        double llllllllllllllllllllIlllIIlIIlIl = Math.cos(Math.toRadians(llllllllllllllllllllIlllIIlIlIII + 90.0f));
        double llllllllllllllllllllIlllIIlIIlII = Math.sin(Math.toRadians(llllllllllllllllllllIlllIIlIlIII + 90.0f));
        double llllllllllllllllllllIlllIIlIIlll = (double)llllllllllllllllllllIlllIIlIlIlI * llllllllllllllllllllIlllIIlIlIll * llllllllllllllllllllIlllIIlIIlIl + (double)llllllllllllllllllllIlllIIlIlIIl * llllllllllllllllllllIlllIIlIlIll * llllllllllllllllllllIlllIIlIIlII;
        double llllllllllllllllllllIlllIIlIIllI = (double)llllllllllllllllllllIlllIIlIlIlI * llllllllllllllllllllIlllIIlIlIll * llllllllllllllllllllIlllIIlIIlII - (double)llllllllllllllllllllIlllIIlIlIIl * llllllllllllllllllllIlllIIlIlIll * llllllllllllllllllllIlllIIlIIlIl;
        return new Vector2(llllllllllllllllllllIlllIIlIIlll, llllllllllllllllllllIlllIIlIIllI);
    }

    public static boolean shouldPause(boolean llllllllllllllllllllIlllIIlllIIl, boolean llllllllllllllllllllIlllIIlllIII, boolean llllllllllllllllllllIlllIIllIlII) {
        if (llllllllllllllllllllIlllIIlllIIl && PlayerUtils.mc.interactionManager.isBreakingBlock()) {
            return true;
        }
        if (llllllllllllllllllllIlllIIlllIII && PlayerUtils.mc.player.isUsingItem() && (PlayerUtils.mc.player.getMainHandStack().getItem().isFood() || PlayerUtils.mc.player.getOffHandStack().getItem().isFood())) {
            return true;
        }
        return llllllllllllllllllllIlllIIllIlII && PlayerUtils.mc.player.isUsingItem() && (PlayerUtils.mc.player.getMainHandStack().getItem() instanceof PotionItem || PlayerUtils.mc.player.getOffHandStack().getItem() instanceof PotionItem);
    }

    public static boolean placeBlock(BlockPos llllllllllllllllllllIllllIlIlIlI, Hand llllllllllllllllllllIllllIlIlIIl) {
        return PlayerUtils.placeBlock(llllllllllllllllllllIllllIlIlIlI, llllllllllllllllllllIllllIlIlIIl, true);
    }

    public static boolean canSeeEntity(Entity llllllllllllllllllllIlllIlIllIII) {
        Vec3d llllllllllllllllllllIlllIlIlIlll = new Vec3d(0.0, 0.0, 0.0);
        Vec3d llllllllllllllllllllIlllIlIlIllI = new Vec3d(0.0, 0.0, 0.0);
        ((IVec3d)llllllllllllllllllllIlllIlIlIlll).set(PlayerUtils.mc.player.getX(), PlayerUtils.mc.player.getY() + (double)PlayerUtils.mc.player.getStandingEyeHeight(), PlayerUtils.mc.player.getZ());
        ((IVec3d)llllllllllllllllllllIlllIlIlIllI).set(llllllllllllllllllllIlllIlIllIII.getX(), llllllllllllllllllllIlllIlIllIII.getY(), llllllllllllllllllllIlllIlIllIII.getZ());
        boolean llllllllllllllllllllIlllIlIlIlIl = PlayerUtils.mc.world.raycast(new RaycastContext(llllllllllllllllllllIlllIlIlIlll, llllllllllllllllllllIlllIlIlIllI, RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)PlayerUtils.mc.player)).getType() == Type.MISS;
        ((IVec3d)llllllllllllllllllllIlllIlIlIllI).set(llllllllllllllllllllIlllIlIllIII.getX(), llllllllllllllllllllIlllIlIllIII.getY() + (double)llllllllllllllllllllIlllIlIllIII.getStandingEyeHeight(), llllllllllllllllllllIlllIlIllIII.getZ());
        boolean llllllllllllllllllllIlllIlIlIlII = PlayerUtils.mc.world.raycast(new RaycastContext(llllllllllllllllllllIlllIlIlIlll, llllllllllllllllllllIlllIlIlIllI, RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)PlayerUtils.mc.player)).getType() == Type.MISS;
        return llllllllllllllllllllIlllIlIlIlIl || llllllllllllllllllllIlllIlIlIlII;
    }

    public static boolean isMoving() {
        return PlayerUtils.mc.player.forwardSpeed != 0.0f || PlayerUtils.mc.player.sidewaysSpeed != 0.0f;
    }

    public static boolean placeBlock(BlockPos llllllllllllllllllllIllllIIIlIIl, Hand llllllllllllllllllllIllllIIIlIII, boolean llllllllllllllllllllIllllIIIIlII) {
        if (!BlockUtils.canPlace(llllllllllllllllllllIllllIIIlIIl)) {
            return false;
        }
        for (Direction llllllllllllllllllllIllllIIIlIlI : Direction.values()) {
            BlockPos llllllllllllllllllllIllllIIIllIl = llllllllllllllllllllIllllIIIlIIl.offset(llllllllllllllllllllIllllIIIlIlI);
            Direction llllllllllllllllllllIllllIIIllII = llllllllllllllllllllIllllIIIlIlI.getOpposite();
            if (PlayerUtils.mc.world.getBlockState(llllllllllllllllllllIllllIIIllIl).isAir() || BlockUtils.isClickable(PlayerUtils.mc.world.getBlockState(llllllllllllllllllllIllllIIIllIl).getBlock())) continue;
            ((IVec3d)hitPos).set((double)llllllllllllllllllllIllllIIIllIl.getX() + 0.5 + (double)llllllllllllllllllllIllllIIIllII.getVector().getX() * 0.5, (double)llllllllllllllllllllIllllIIIllIl.getY() + 0.5 + (double)llllllllllllllllllllIllllIIIllII.getVector().getY() * 0.5, (double)llllllllllllllllllllIllllIIIllIl.getZ() + 0.5 + (double)llllllllllllllllllllIllllIIIllII.getVector().getZ() * 0.5);
            boolean llllllllllllllllllllIllllIIIlIll = PlayerUtils.mc.player.input.sneaking;
            PlayerUtils.mc.player.input.sneaking = false;
            PlayerUtils.mc.interactionManager.interactBlock(PlayerUtils.mc.player, PlayerUtils.mc.world, llllllllllllllllllllIllllIIIlIII, new BlockHitResult(hitPos, llllllllllllllllllllIllllIIIllII, llllllllllllllllllllIllllIIIllIl, false));
            if (llllllllllllllllllllIllllIIIIlII) {
                PlayerUtils.mc.player.swingHand(llllllllllllllllllllIllllIIIlIII);
            }
            PlayerUtils.mc.player.input.sneaking = llllllllllllllllllllIllllIIIlIll;
            return true;
        }
        ((IVec3d)hitPos).set((Vec3i)llllllllllllllllllllIllllIIIlIIl);
        PlayerUtils.mc.interactionManager.interactBlock(PlayerUtils.mc.player, PlayerUtils.mc.world, llllllllllllllllllllIllllIIIlIII, new BlockHitResult(hitPos, Direction.UP, llllllllllllllllllllIllllIIIlIIl, false));
        if (llllllllllllllllllllIllllIIIIlII) {
            PlayerUtils.mc.player.swingHand(llllllllllllllllllllIllllIIIlIII);
        }
        return true;
    }

    public PlayerUtils() {
        PlayerUtils llllllllllllllllllllIllllIlIlllI;
    }

    public static void centerPlayer() {
        double llllllllllllllllllllIlllIllIIIIl = (double)MathHelper.floor((double)PlayerUtils.mc.player.getX()) + 0.5;
        double llllllllllllllllllllIlllIllIIIII = (double)MathHelper.floor((double)PlayerUtils.mc.player.getZ()) + 0.5;
        PlayerUtils.mc.player.setPosition(llllllllllllllllllllIlllIllIIIIl, PlayerUtils.mc.player.getY(), llllllllllllllllllllIlllIllIIIII);
        PlayerUtils.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(PlayerUtils.mc.player.getX(), PlayerUtils.mc.player.getY(), PlayerUtils.mc.player.getZ(), PlayerUtils.mc.player.isOnGround()));
    }

    public static boolean isInHole() {
        return !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(1, 0, 0)).isAir() && !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(-1, 0, 0)).isAir() && !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(0, 0, 1)).isAir() && !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(0, 0, -1)).isAir() && !PlayerUtils.mc.world.getBlockState(PlayerUtils.mc.player.getBlockPos().add(0, -1, 0)).isAir();
    }

    public static boolean placeBlock(BlockPos llllllllllllllllllllIllllIlIIIIl, int llllllllllllllllllllIllllIlIIIII, Hand llllllllllllllllllllIllllIIlllll) {
        if (llllllllllllllllllllIllllIlIIIII == -1) {
            return false;
        }
        int llllllllllllllllllllIllllIIllllI = PlayerUtils.mc.player.inventory.selectedSlot;
        PlayerUtils.mc.player.inventory.selectedSlot = llllllllllllllllllllIllllIlIIIII;
        boolean llllllllllllllllllllIllllIIlllIl = PlayerUtils.placeBlock(llllllllllllllllllllIllllIlIIIIl, llllllllllllllllllllIllllIIlllll, true);
        PlayerUtils.mc.player.inventory.selectedSlot = llllllllllllllllllllIllllIIllllI;
        return llllllllllllllllllllIllllIIlllIl;
    }

    public static Vec3d getHorizontalVelocity(double llllllllllllllllllllIlllIlllIIll) {
        Rotation llllllllllllllllllllIlllIlllIlII;
        float llllllllllllllllllllIlllIlllIIlI = PlayerUtils.mc.player.yaw;
        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && (llllllllllllllllllllIlllIlllIlII = BaritoneUtils.getTarget()) != null) {
            llllllllllllllllllllIlllIlllIIlI = llllllllllllllllllllIlllIlllIlII.getYaw();
        }
        Vec3d llllllllllllllllllllIlllIlllIIIl = Vec3d.fromPolar((float)0.0f, (float)llllllllllllllllllllIlllIlllIIlI);
        Vec3d llllllllllllllllllllIlllIlllIIII = Vec3d.fromPolar((float)0.0f, (float)(llllllllllllllllllllIlllIlllIIlI + 90.0f));
        double llllllllllllllllllllIlllIllIllll = 0.0;
        double llllllllllllllllllllIlllIllIlllI = 0.0;
        boolean llllllllllllllllllllIlllIllIllIl = false;
        if (PlayerUtils.mc.player.input.pressingForward) {
            llllllllllllllllllllIlllIllIllll += llllllllllllllllllllIlllIlllIIIl.x / 20.0 * llllllllllllllllllllIlllIlllIIll;
            llllllllllllllllllllIlllIllIlllI += llllllllllllllllllllIlllIlllIIIl.z / 20.0 * llllllllllllllllllllIlllIlllIIll;
            llllllllllllllllllllIlllIllIllIl = true;
        }
        if (PlayerUtils.mc.player.input.pressingBack) {
            llllllllllllllllllllIlllIllIllll -= llllllllllllllllllllIlllIlllIIIl.x / 20.0 * llllllllllllllllllllIlllIlllIIll;
            llllllllllllllllllllIlllIllIlllI -= llllllllllllllllllllIlllIlllIIIl.z / 20.0 * llllllllllllllllllllIlllIlllIIll;
            llllllllllllllllllllIlllIllIllIl = true;
        }
        boolean llllllllllllllllllllIlllIllIllII = false;
        if (PlayerUtils.mc.player.input.pressingRight) {
            llllllllllllllllllllIlllIllIllll += llllllllllllllllllllIlllIlllIIII.x / 20.0 * llllllllllllllllllllIlllIlllIIll;
            llllllllllllllllllllIlllIllIlllI += llllllllllllllllllllIlllIlllIIII.z / 20.0 * llllllllllllllllllllIlllIlllIIll;
            llllllllllllllllllllIlllIllIllII = true;
        }
        if (PlayerUtils.mc.player.input.pressingLeft) {
            llllllllllllllllllllIlllIllIllll -= llllllllllllllllllllIlllIlllIIII.x / 20.0 * llllllllllllllllllllIlllIlllIIll;
            llllllllllllllllllllIlllIllIlllI -= llllllllllllllllllllIlllIlllIIII.z / 20.0 * llllllllllllllllllllIlllIlllIIll;
            llllllllllllllllllllIlllIllIllII = true;
        }
        if (llllllllllllllllllllIlllIllIllIl && llllllllllllllllllllIlllIllIllII) {
            llllllllllllllllllllIlllIllIllll *= diagonal;
            llllllllllllllllllllIlllIllIlllI *= diagonal;
        }
        ((IVec3d)horizontalVelocity).setXZ(llllllllllllllllllllIlllIllIllll, llllllllllllllllllllIlllIllIlllI);
        return horizontalVelocity;
    }
}

