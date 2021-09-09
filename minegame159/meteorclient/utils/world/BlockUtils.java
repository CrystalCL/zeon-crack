/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.world.World
 *  net.minecraft.block.AnvilBlock
 *  net.minecraft.block.AbstractPressurePlateBlock
 *  net.minecraft.block.BlockWithEntity
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.block.AbstractButtonBlock
 *  net.minecraft.block.CraftingTableBlock
 *  net.minecraft.block.DoorBlock
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.block.FenceGateBlock
 *  net.minecraft.util.math.Direction
 *  net.minecraft.block.NoteBlock
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.TrapdoorBlock
 *  net.minecraft.network.Packet
 *  net.minecraft.block.BlockState
 *  net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.block.ShapeContext
 *  net.minecraft.util.hit.BlockHitResult
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
    private static final /* synthetic */ MinecraftClient mc;
    private static final /* synthetic */ Vec3d hitPos;

    static {
        mc = MinecraftClient.getInstance();
        hitPos = new Vec3d(0.0, 0.0, 0.0);
    }

    public static boolean place(BlockPos llllllllllllllllllIIlIIllIllllII, Hand llllllllllllllllllIIlIIlllIIIlll, int llllllllllllllllllIIlIIllIlllIlI, boolean llllllllllllllllllIIlIIllIlllIIl, int llllllllllllllllllIIlIIlllIIIlII, boolean llllllllllllllllllIIlIIllIllIlll, boolean llllllllllllllllllIIlIIlllIIIIlI, boolean llllllllllllllllllIIlIIllIllIlIl, boolean llllllllllllllllllIIlIIlllIIIIII) {
        BlockPos llllllllllllllllllIIlIIllIlllllI;
        Vec3d llllllllllllllllllIIlIIllIllllIl;
        if (llllllllllllllllllIIlIIllIlllIlI == -1 || !BlockUtils.canPlace(llllllllllllllllllIIlIIllIllllII, llllllllllllllllllIIlIIlllIIIIlI)) {
            return false;
        }
        Direction llllllllllllllllllIIlIIllIllllll = BlockUtils.getPlaceSide(llllllllllllllllllIIlIIllIllllII);
        Vec3d Vec3d2 = llllllllllllllllllIIlIIllIllllIl = llllllllllllllllllIIlIIllIlllIIl ? new Vec3d(0.0, 0.0, 0.0) : hitPos;
        if (llllllllllllllllllIIlIIllIllllll == null) {
            llllllllllllllllllIIlIIllIllllll = Direction.UP;
            BlockPos llllllllllllllllllIIlIIlllIIlIlI = llllllllllllllllllIIlIIllIllllII;
            ((IVec3d)llllllllllllllllllIIlIIllIllllIl).set((double)llllllllllllllllllIIlIIllIllllII.getX() + 0.5, (double)llllllllllllllllllIIlIIllIllllII.getY() + 0.5, (double)llllllllllllllllllIIlIIllIllllII.getZ() + 0.5);
        } else {
            llllllllllllllllllIIlIIllIlllllI = llllllllllllllllllIIlIIllIllllII.offset(llllllllllllllllllIIlIIllIllllll.getOpposite());
            ((IVec3d)llllllllllllllllllIIlIIllIllllIl).set((double)llllllllllllllllllIIlIIllIlllllI.getX() + 0.5 + (double)llllllllllllllllllIIlIIllIllllll.getOffsetX() * 0.5, (double)llllllllllllllllllIIlIIllIlllllI.getY() + 0.6 + (double)llllllllllllllllllIIlIIllIllllll.getOffsetY() * 0.5, (double)llllllllllllllllllIIlIIllIlllllI.getZ() + 0.5 + (double)llllllllllllllllllIIlIIllIllllll.getOffsetZ() * 0.5);
        }
        if (llllllllllllllllllIIlIIllIlllIIl) {
            Direction llllllllllllllllllIIlIIlllIIlIIl = llllllllllllllllllIIlIIllIllllll;
            Rotations.rotate(Rotations.getYaw(llllllllllllllllllIIlIIllIllllIl), Rotations.getPitch(llllllllllllllllllIIlIIllIllllIl), llllllllllllllllllIIlIIlllIIIlII, () -> BlockUtils.place(llllllllllllllllllIIlIIllIlllIlI, llllllllllllllllllIIlIIllIllllIl, llllllllllllllllllIIlIIlllIIIlll, llllllllllllllllllIIlIIlllIIlIIl, llllllllllllllllllIIlIIllIlllllI, llllllllllllllllllIIlIIllIllIlll, llllllllllllllllllIIlIIllIllIlIl, llllllllllllllllllIIlIIlllIIIIII));
        } else {
            BlockUtils.place(llllllllllllllllllIIlIIllIlllIlI, llllllllllllllllllIIlIIllIllllIl, llllllllllllllllllIIlIIlllIIIlll, llllllllllllllllllIIlIIllIllllll, llllllllllllllllllIIlIIllIlllllI, llllllllllllllllllIIlIIllIllIlll, llllllllllllllllllIIlIIllIllIlIl, llllllllllllllllllIIlIIlllIIIIII);
        }
        return true;
    }

    private static void place(int llllllllllllllllllIIlIIllIIIlIIl, Vec3d llllllllllllllllllIIlIIllIIlIIlI, Hand llllllllllllllllllIIlIIllIIIIlll, Direction llllllllllllllllllIIlIIllIIIIllI, BlockPos llllllllllllllllllIIlIIllIIIllll, boolean llllllllllllllllllIIlIIllIIIIlII, boolean llllllllllllllllllIIlIIllIIIllIl, boolean llllllllllllllllllIIlIIllIIIIIlI) {
        int llllllllllllllllllIIlIIllIIIlIll = BlockUtils.mc.player.inventory.selectedSlot;
        if (llllllllllllllllllIIlIIllIIIllIl) {
            BlockUtils.mc.player.inventory.selectedSlot = llllllllllllllllllIIlIIllIIIlIIl;
        }
        boolean llllllllllllllllllIIlIIllIIIlIlI = BlockUtils.mc.player.input.sneaking;
        BlockUtils.mc.player.input.sneaking = false;
        BlockUtils.mc.interactionManager.interactBlock(BlockUtils.mc.player, BlockUtils.mc.world, llllllllllllllllllIIlIIllIIIIlll, new BlockHitResult(llllllllllllllllllIIlIIllIIlIIlI, llllllllllllllllllIIlIIllIIIIllI, llllllllllllllllllIIlIIllIIIllll, false));
        if (llllllllllllllllllIIlIIllIIIIlII) {
            BlockUtils.mc.player.swingHand(llllllllllllllllllIIlIIllIIIIlll);
        } else {
            mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(llllllllllllllllllIIlIIllIIIIlll));
        }
        BlockUtils.mc.player.input.sneaking = llllllllllllllllllIIlIIllIIIlIlI;
        if (llllllllllllllllllIIlIIllIIIIIlI) {
            BlockUtils.mc.player.inventory.selectedSlot = llllllllllllllllllIIlIIllIIIlIll;
        }
    }

    public static boolean canPlace(BlockPos llllllllllllllllllIIlIIlIllllIII) {
        return BlockUtils.canPlace(llllllllllllllllllIIlIIlIllllIII, true);
    }

    private static Direction getPlaceSide(BlockPos llllllllllllllllllIIlIIlIllIIIll) {
        for (Direction llllllllllllllllllIIlIIlIllIIlIl : Direction.values()) {
            BlockPos llllllllllllllllllIIlIIlIllIlIII = llllllllllllllllllIIlIIlIllIIIll.offset(llllllllllllllllllIIlIIlIllIIlIl);
            Direction llllllllllllllllllIIlIIlIllIIlll = llllllllllllllllllIIlIIlIllIIlIl.getOpposite();
            BlockState llllllllllllllllllIIlIIlIllIIllI = BlockUtils.mc.world.getBlockState(llllllllllllllllllIIlIIlIllIlIII);
            if (llllllllllllllllllIIlIIlIllIIllI.isAir() || BlockUtils.isClickable(llllllllllllllllllIIlIIlIllIIllI.getBlock()) || !llllllllllllllllllIIlIIlIllIIllI.getFluidState().isEmpty()) continue;
            return llllllllllllllllllIIlIIlIllIIlll;
        }
        return null;
    }

    public static boolean place(BlockPos llllllllllllllllllIIlIIllIlIlIIl, Hand llllllllllllllllllIIlIIllIlIlIII, int llllllllllllllllllIIlIIllIlIIIIl, boolean llllllllllllllllllIIlIIllIlIIllI, int llllllllllllllllllIIlIIllIIlllll, boolean llllllllllllllllllIIlIIllIIllllI) {
        return BlockUtils.place(llllllllllllllllllIIlIIllIlIlIIl, llllllllllllllllllIIlIIllIlIlIII, llllllllllllllllllIIlIIllIlIIIIl, llllllllllllllllllIIlIIllIlIIllI, llllllllllllllllllIIlIIllIIlllll, true, llllllllllllllllllIIlIIllIIllllI, true, true);
    }

    public static boolean isClickable(Block llllllllllllllllllIIlIIlIlllIIlI) {
        boolean llllllllllllllllllIIlIIlIlllIIll = llllllllllllllllllIIlIIlIlllIIlI instanceof CraftingTableBlock || llllllllllllllllllIIlIIlIlllIIlI instanceof AnvilBlock || llllllllllllllllllIIlIIlIlllIIlI instanceof AbstractButtonBlock || llllllllllllllllllIIlIIlIlllIIlI instanceof AbstractPressurePlateBlock || llllllllllllllllllIIlIIlIlllIIlI instanceof BlockWithEntity || llllllllllllllllllIIlIIlIlllIIlI instanceof FenceGateBlock || llllllllllllllllllIIlIIlIlllIIlI instanceof DoorBlock || llllllllllllllllllIIlIIlIlllIIlI instanceof NoteBlock || llllllllllllllllllIIlIIlIlllIIlI instanceof TrapdoorBlock;
        return llllllllllllllllllIIlIIlIlllIIll;
    }

    public BlockUtils() {
        BlockUtils llllllllllllllllllIIlIIlllIllIII;
    }

    public static boolean canPlace(BlockPos llllllllllllllllllIIlIIlIlllllIl, boolean llllllllllllllllllIIlIIlIllllIlI) {
        if (llllllllllllllllllIIlIIlIlllllIl == null) {
            return false;
        }
        if (World.isOutOfBuildLimitVertically((BlockPos)llllllllllllllllllIIlIIlIlllllIl)) {
            return false;
        }
        if (!BlockUtils.mc.world.getBlockState(llllllllllllllllllIIlIIlIlllllIl).getMaterial().isReplaceable()) {
            return false;
        }
        return !llllllllllllllllllIIlIIlIllllIlI || BlockUtils.mc.world.canPlace(Blocks.STONE.getDefaultState(), llllllllllllllllllIIlIIlIlllllIl, ShapeContext.absent());
    }
}

