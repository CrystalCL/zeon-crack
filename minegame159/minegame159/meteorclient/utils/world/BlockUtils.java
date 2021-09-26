/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.world;

import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.util.math.Direction;
import net.minecraft.block.NoteBlock;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.network.Packet;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;

public class BlockUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final Vec3d hitPos = new Vec3d(0.0, 0.0, 0.0);

    public static boolean place(BlockPos BlockPos2, Hand Hand2, int n, boolean bl, int n2, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        BlockPos BlockPos3;
        Vec3d Vec3d2;
        if (n == -1 || !BlockUtils.canPlace(BlockPos2, bl3)) {
            return false;
        }
        Direction Direction2 = BlockUtils.getPlaceSide(BlockPos2);
        Vec3d Vec3d3 = Vec3d2 = bl ? new Vec3d(0.0, 0.0, 0.0) : hitPos;
        if (Direction2 == null) {
            Direction2 = Direction.UP;
            BlockPos3 = BlockPos2;
            ((IVec3d)Vec3d2).set((double)BlockPos2.getX() + 0.5, (double)BlockPos2.getY() + 0.5, (double)BlockPos2.getZ() + 0.5);
        } else {
            BlockPos3 = BlockPos2.offset(Direction2.getOpposite());
            ((IVec3d)Vec3d2).set((double)BlockPos3.getX() + 0.5 + (double)Direction2.getOffsetX() * 0.5, (double)BlockPos3.getY() + 0.6 + (double)Direction2.getOffsetY() * 0.5, (double)BlockPos3.getZ() + 0.5 + (double)Direction2.getOffsetZ() * 0.5);
        }
        if (bl) {
            Direction Direction3 = Direction2;
            Rotations.rotate(Rotations.getYaw(Vec3d2), Rotations.getPitch(Vec3d2), n2, () -> BlockUtils.lambda$place$0(n, Vec3d2, Hand2, Direction3, BlockPos3, bl2, bl4, bl5));
        } else {
            BlockUtils.place(n, Vec3d2, Hand2, Direction2, BlockPos3, bl2, bl4, bl5);
        }
        return true;
    }

    public static boolean place(BlockPos BlockPos2, Hand Hand2, int n, boolean bl, int n2, boolean bl2) {
        return BlockUtils.place(BlockPos2, Hand2, n, bl, n2, true, bl2, true, true);
    }

    public static boolean canPlace(BlockPos BlockPos2, boolean bl) {
        if (BlockPos2 == null) {
            return false;
        }
        if (World.isOutOfBuildLimitVertically((BlockPos)BlockPos2)) {
            return false;
        }
        if (!BlockUtils.mc.world.getBlockState(BlockPos2).getMaterial().isReplaceable()) {
            return false;
        }
        return !bl || BlockUtils.mc.world.canPlace(Blocks.STONE.getDefaultState(), BlockPos2, ShapeContext.absent());
    }

    public static boolean isClickable(Block Block2) {
        boolean bl = Block2 instanceof CraftingTableBlock || Block2 instanceof AnvilBlock || Block2 instanceof AbstractButtonBlock || Block2 instanceof AbstractPressurePlateBlock || Block2 instanceof BlockWithEntity || Block2 instanceof FenceGateBlock || Block2 instanceof DoorBlock || Block2 instanceof NoteBlock || Block2 instanceof TrapdoorBlock;
        return bl;
    }

    private static void lambda$place$0(int n, Vec3d Vec3d2, Hand Hand2, Direction Direction2, BlockPos BlockPos2, boolean bl, boolean bl2, boolean bl3) {
        BlockUtils.place(n, Vec3d2, Hand2, Direction2, BlockPos2, bl, bl2, bl3);
    }

    public static boolean canPlace(BlockPos BlockPos2) {
        return BlockUtils.canPlace(BlockPos2, true);
    }

    private static void place(int n, Vec3d Vec3d2, Hand Hand2, Direction Direction2, BlockPos BlockPos2, boolean bl, boolean bl2, boolean bl3) {
        int n2 = BlockUtils.mc.player.inventory.selectedSlot;
        if (bl2) {
            BlockUtils.mc.player.inventory.selectedSlot = n;
        }
        boolean bl4 = BlockUtils.mc.player.input.sneaking;
        BlockUtils.mc.player.input.sneaking = false;
        BlockUtils.mc.interactionManager.interactBlock(BlockUtils.mc.player, BlockUtils.mc.world, Hand2, new BlockHitResult(Vec3d2, Direction2, BlockPos2, false));
        if (bl) {
            BlockUtils.mc.player.swingHand(Hand2);
        } else {
            mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand2));
        }
        BlockUtils.mc.player.input.sneaking = bl4;
        if (bl3) {
            BlockUtils.mc.player.inventory.selectedSlot = n2;
        }
    }

    private static Direction getPlaceSide(BlockPos BlockPos2) {
        for (Direction Direction2 : Direction.values()) {
            BlockPos BlockPos3 = BlockPos2.offset(Direction2);
            Direction Direction3 = Direction2.getOpposite();
            BlockState BlockState2 = BlockUtils.mc.world.getBlockState(BlockPos3);
            if (BlockState2.isAir() || BlockUtils.isClickable(BlockState2.getBlock()) || !BlockState2.getFluidState().isEmpty()) continue;
            return Direction3;
        }
        return null;
    }
}

