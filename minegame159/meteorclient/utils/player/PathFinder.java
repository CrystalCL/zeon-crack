/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.command.argument.EntityAnchorArgumentType.EntityAnchor
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.utils.player;

import java.util.ArrayList;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import net.minecraft.entity.Entity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;

public class PathFinder {
    private static final /* synthetic */ int QUAD_1;
    private static final /* synthetic */ int NORTH;
    private static final /* synthetic */ int PATH_AHEAD;
    private /* synthetic */ PathBlock currentPathBlock;
    private final /* synthetic */ ArrayList<PathBlock> path;
    private /* synthetic */ Entity target;
    private static final /* synthetic */ int SOUTH;
    private final /* synthetic */ MinecraftClient mc;
    private static final /* synthetic */ int QUAD_2;

    static {
        QUAD_1 = 1;
        PATH_AHEAD = 3;
        SOUTH = 0;
        NORTH = 180;
        QUAD_2 = 2;
    }

    @EventHandler
    private void moveEventListener(PlayerMoveEvent llllllllllllllllIlllIllIIlllIIlI) {
        PathFinder llllllllllllllllIlllIllIIlllIIIl;
        if (llllllllllllllllIlllIllIIlllIIIl.target != null && llllllllllllllllIlllIllIIlllIIIl.mc.player != null) {
            if (llllllllllllllllIlllIllIIlllIIIl.mc.player.distanceTo(llllllllllllllllIlllIllIIlllIIIl.target) > 3.0f) {
                if (llllllllllllllllIlllIllIIlllIIIl.currentPathBlock == null) {
                    llllllllllllllllIlllIllIIlllIIIl.currentPathBlock = llllllllllllllllIlllIllIIlllIIIl.getNextPathBlock();
                }
                Vec3d Vec3d2 = new Vec3d((double)llllllllllllllllIlllIllIIlllIIIl.currentPathBlock.blockPos.getX(), (double)llllllllllllllllIlllIllIIlllIIIl.currentPathBlock.blockPos.getY(), (double)llllllllllllllllIlllIllIIlllIIIl.currentPathBlock.blockPos.getZ());
                if (llllllllllllllllIlllIllIIlllIIIl.mc.player.getPos().distanceTo(Vec3d2) < 0.1) {
                    llllllllllllllllIlllIllIIlllIIIl.currentPathBlock = llllllllllllllllIlllIllIIlllIIIl.getNextPathBlock();
                }
                llllllllllllllllIlllIllIIlllIIIl.lookAtDestination(llllllllllllllllIlllIllIIlllIIIl.currentPathBlock);
                if (!llllllllllllllllIlllIllIIlllIIIl.mc.options.keyForward.isPressed()) {
                    llllllllllllllllIlllIllIIlllIIIl.mc.options.keyForward.setPressed(true);
                }
            } else {
                if (llllllllllllllllIlllIllIIlllIIIl.mc.options.keyForward.isPressed()) {
                    llllllllllllllllIlllIllIIlllIIIl.mc.options.keyForward.setPressed(false);
                }
                llllllllllllllllIlllIllIIlllIIIl.path.clear();
                llllllllllllllllIlllIllIIlllIIIl.currentPathBlock = null;
            }
        }
    }

    public int getYawToTarget() {
        PathFinder llllllllllllllllIlllIllIlIlIllll;
        if (llllllllllllllllIlllIllIlIlIllll.target == null || llllllllllllllllIlllIllIlIlIllll.mc.player == null) {
            return Integer.MAX_VALUE;
        }
        Vec3d llllllllllllllllIlllIllIlIllIlII = llllllllllllllllIlllIllIlIlIllll.target.getPos();
        Vec3d llllllllllllllllIlllIllIlIllIIll = llllllllllllllllIlllIllIlIlIllll.mc.player.getPos();
        int llllllllllllllllIlllIllIlIllIIlI = 0;
        int llllllllllllllllIlllIllIlIllIIIl = llllllllllllllllIlllIllIlIlIllll.getDirection();
        double llllllllllllllllIlllIllIlIllIIII = (llllllllllllllllIlllIllIlIllIlII.z - llllllllllllllllIlllIllIlIllIIll.z) / (llllllllllllllllIlllIllIlIllIlII.x - llllllllllllllllIlllIllIlIllIIll.x);
        if (llllllllllllllllIlllIllIlIllIIIl == 1) {
            llllllllllllllllIlllIllIlIllIIlI = (int)(1.5707963267948966 - Math.atan(llllllllllllllllIlllIllIlIllIIII));
        } else if (llllllllllllllllIlllIllIlIllIIIl == 2) {
            llllllllllllllllIlllIllIlIllIIlI = (int)(-1.5707963267948966 - Math.atan(llllllllllllllllIlllIllIlIllIIII));
        } else {
            return llllllllllllllllIlllIllIlIllIIIl;
        }
        return llllllllllllllllIlllIllIlIllIIlI;
    }

    public void lookAtDestination(PathBlock llllllllllllllllIlllIllIIlllIlll) {
        PathFinder llllllllllllllllIlllIllIIlllIllI;
        if (llllllllllllllllIlllIllIIlllIllI.mc.player != null) {
            llllllllllllllllIlllIllIIlllIllI.mc.player.lookAt(EntityAnchor.EYES, new Vec3d((double)llllllllllllllllIlllIllIIlllIlll.blockPos.getX(), (double)((float)llllllllllllllllIlllIllIIlllIlll.blockPos.getY() + llllllllllllllllIlllIllIIlllIllI.mc.player.getStandingEyeHeight()), (double)llllllllllllllllIlllIllIIlllIlll.blockPos.getZ()));
        }
    }

    public BlockState getBlockStateAtPos(BlockPos llllllllllllllllIlllIllIlIIllIll) {
        PathFinder llllllllllllllllIlllIllIlIIlllII;
        if (llllllllllllllllIlllIllIlIIlllII.mc.world != null) {
            return llllllllllllllllIlllIllIlIIlllII.mc.world.getBlockState(llllllllllllllllIlllIllIlIIllIll);
        }
        return null;
    }

    public PathFinder() {
        PathFinder llllllllllllllllIlllIllIllIlllIl;
        llllllllllllllllIlllIllIllIlllIl.path = new ArrayList(3);
        llllllllllllllllIlllIllIllIlllIl.mc = MinecraftClient.getInstance();
    }

    public BlockState getBlockStateAtPos(int llllllllllllllllIlllIllIlIIlIIIl, int llllllllllllllllIlllIllIlIIlIlII, int llllllllllllllllIlllIllIlIIIllll) {
        PathFinder llllllllllllllllIlllIllIlIIlIllI;
        if (llllllllllllllllIlllIllIlIIlIllI.mc.world != null) {
            return llllllllllllllllIlllIllIlIIlIllI.mc.world.getBlockState(new BlockPos(llllllllllllllllIlllIllIlIIlIIIl, llllllllllllllllIlllIllIlIIlIlII, llllllllllllllllIlllIllIlIIIllll));
        }
        return null;
    }

    public Vec3d getNextStraightPos() {
        PathFinder llllllllllllllllIlllIllIlIlllllI;
        Vec3d llllllllllllllllIlllIllIllIIIIII = new Vec3d(llllllllllllllllIlllIllIlIlllllI.mc.player.getX(), llllllllllllllllIlllIllIlIlllllI.mc.player.getY(), llllllllllllllllIlllIllIlIlllllI.mc.player.getZ());
        double llllllllllllllllIlllIllIlIllllll = 1.0;
        while (llllllllllllllllIlllIllIllIIIIII == llllllllllllllllIlllIllIlIlllllI.mc.player.getPos()) {
            llllllllllllllllIlllIllIllIIIIII = new Vec3d((double)((int)(llllllllllllllllIlllIllIlIlllllI.mc.player.getX() + llllllllllllllllIlllIllIlIllllll * Math.cos(Math.toRadians(llllllllllllllllIlllIllIlIlllllI.mc.player.yaw)))), (double)((int)llllllllllllllllIlllIllIlIlllllI.mc.player.getY()), (double)((int)(llllllllllllllllIlllIllIlIlllllI.mc.player.getZ() + llllllllllllllllIlllIllIlIllllll * Math.sin(Math.toRadians(llllllllllllllllIlllIllIlIlllllI.mc.player.yaw)))));
            llllllllllllllllIlllIllIlIllllll += 0.1;
        }
        return llllllllllllllllIlllIllIllIIIIII;
    }

    public boolean isWater(Block llllllllllllllllIlllIllIIllllIll) {
        return llllllllllllllllIlllIllIIllllIll.is(Blocks.WATER);
    }

    public boolean isSolidFloor(BlockPos llllllllllllllllIlllIllIlIIIIlIl) {
        PathFinder llllllllllllllllIlllIllIlIIIIlII;
        return llllllllllllllllIlllIllIlIIIIlII.isAir(llllllllllllllllIlllIllIlIIIIlII.getBlockAtPos(llllllllllllllllIlllIllIlIIIIlIl));
    }

    public int getDirection() {
        PathFinder llllllllllllllllIlllIllIlIlIIllI;
        if (llllllllllllllllIlllIllIlIlIIllI.target == null || llllllllllllllllIlllIllIlIlIIllI.mc.player == null) {
            return 0;
        }
        Vec3d llllllllllllllllIlllIllIlIlIIlIl = llllllllllllllllIlllIllIlIlIIllI.target.getPos();
        Vec3d llllllllllllllllIlllIllIlIlIIlII = llllllllllllllllIlllIllIlIlIIllI.mc.player.getPos();
        if (llllllllllllllllIlllIllIlIlIIlIl.x == llllllllllllllllIlllIllIlIlIIlII.x && llllllllllllllllIlllIllIlIlIIlIl.z > llllllllllllllllIlllIllIlIlIIlII.z) {
            return 0;
        }
        if (llllllllllllllllIlllIllIlIlIIlIl.x == llllllllllllllllIlllIllIlIlIIlII.x && llllllllllllllllIlllIllIlIlIIlIl.z < llllllllllllllllIlllIllIlIlIIlII.z) {
            return 180;
        }
        if (llllllllllllllllIlllIllIlIlIIlIl.x < llllllllllllllllIlllIllIlIlIIlII.x) {
            return 1;
        }
        if (llllllllllllllllIlllIllIlIlIIlIl.x > llllllllllllllllIlllIllIlIlIIlII.x) {
            return 2;
        }
        return 0;
    }

    public PathBlock getNextPathBlock() {
        PathFinder llllllllllllllllIlllIllIllIlIllI;
        PathBlock llllllllllllllllIlllIllIllIlIlll = llllllllllllllllIlllIllIllIlIllI.new PathBlock(new BlockPos(llllllllllllllllIlllIllIllIlIllI.getNextStraightPos()));
        if (llllllllllllllllIlllIllIllIlIllI.isSolidFloor(llllllllllllllllIlllIllIllIlIlll.blockPos) && llllllllllllllllIlllIllIllIlIllI.isAirAbove(llllllllllllllllIlllIllIllIlIlll.blockPos)) {
            return llllllllllllllllIlllIllIllIlIlll;
        }
        if (!llllllllllllllllIlllIllIllIlIllI.isSolidFloor(llllllllllllllllIlllIllIllIlIlll.blockPos) && llllllllllllllllIlllIllIllIlIllI.isAirAbove(llllllllllllllllIlllIllIllIlIlll.blockPos)) {
            int llllllllllllllllIlllIllIllIllIIl = llllllllllllllllIlllIllIllIlIllI.getDrop(llllllllllllllllIlllIllIllIlIlll.blockPos);
            if (llllllllllllllllIlllIllIllIlIllI.getDrop(llllllllllllllllIlllIllIllIlIlll.blockPos) < 3) {
                llllllllllllllllIlllIllIllIlIlll = llllllllllllllllIlllIllIllIlIllI.new PathBlock(new BlockPos(llllllllllllllllIlllIllIllIlIlll.blockPos.getX(), llllllllllllllllIlllIllIllIlIlll.blockPos.getY() - llllllllllllllllIlllIllIllIllIIl, llllllllllllllllIlllIllIllIlIlll.blockPos.getZ()));
            }
        }
        return llllllllllllllllIlllIllIllIlIlll;
    }

    public void initiate(Entity llllllllllllllllIlllIllIIllIllIl) {
        PathFinder llllllllllllllllIlllIllIIllIlllI;
        llllllllllllllllIlllIllIIllIlllI.target = llllllllllllllllIlllIllIIllIllIl;
        if (llllllllllllllllIlllIllIIllIlllI.target != null) {
            llllllllllllllllIlllIllIIllIlllI.currentPathBlock = llllllllllllllllIlllIllIIllIlllI.getNextPathBlock();
        }
        MeteorClient.EVENT_BUS.subscribe(llllllllllllllllIlllIllIIllIlllI);
    }

    public Block getBlockAtPos(BlockPos llllllllllllllllIlllIllIlIIIlIIl) {
        PathFinder llllllllllllllllIlllIllIlIIIlIlI;
        if (llllllllllllllllIlllIllIlIIIlIlI.mc.world != null) {
            return llllllllllllllllIlllIllIlIIIlIlI.mc.world.getBlockState(llllllllllllllllIlllIllIlIIIlIIl).getBlock();
        }
        return null;
    }

    public boolean isAirAbove(BlockPos llllllllllllllllIlllIllIllIIIlIl) {
        PathFinder llllllllllllllllIlllIllIllIIIllI;
        if (!llllllllllllllllIlllIllIllIIIllI.getBlockStateAtPos(llllllllllllllllIlllIllIllIIIlIl.getX(), llllllllllllllllIlllIllIllIIIlIl.getY(), llllllllllllllllIlllIllIllIIIlIl.getZ()).isAir()) {
            return false;
        }
        return llllllllllllllllIlllIllIllIIIllI.getBlockStateAtPos(llllllllllllllllIlllIllIllIIIlIl.getX(), llllllllllllllllIlllIllIllIIIlIl.getY() + 1, llllllllllllllllIlllIllIllIIIlIl.getZ()).isAir();
    }

    public boolean isAir(Block llllllllllllllllIlllIllIIlllllll) {
        return llllllllllllllllIlllIllIIlllllll.is(Blocks.AIR);
    }

    public void disable() {
        PathFinder llllllllllllllllIlllIllIIllIlIII;
        llllllllllllllllIlllIllIIllIlIII.target = null;
        llllllllllllllllIlllIllIIllIlIII.path.clear();
        if (llllllllllllllllIlllIllIIllIlIII.mc.options.keyForward.isPressed()) {
            llllllllllllllllIlllIllIIllIlIII.mc.options.keyForward.setPressed(false);
        }
        MeteorClient.EVENT_BUS.unsubscribe(llllllllllllllllIlllIllIIllIlIII);
    }

    public int getDrop(BlockPos llllllllllllllllIlllIllIllIIllII) {
        PathFinder llllllllllllllllIlllIllIllIlIIII;
        int llllllllllllllllIlllIllIllIIlllI;
        for (llllllllllllllllIlllIllIllIIlllI = 0; !llllllllllllllllIlllIllIllIlIIII.isSolidFloor(llllllllllllllllIlllIllIllIIllII) && llllllllllllllllIlllIllIllIIlllI < 3; ++llllllllllllllllIlllIllIllIIlllI) {
            llllllllllllllllIlllIllIllIIllII = new BlockPos(llllllllllllllllIlllIllIllIIllII.getX(), llllllllllllllllIlllIllIllIIllII.getY() - 1, llllllllllllllllIlllIllIllIIllII.getZ());
        }
        return llllllllllllllllIlllIllIllIIlllI;
    }

    public class PathBlock {
        public /* synthetic */ double yaw;
        public final /* synthetic */ Block block;
        public final /* synthetic */ BlockPos blockPos;
        public final /* synthetic */ BlockState blockState;

        public PathBlock(Block lllllllllllllllllIlllIlIlIllllIl, BlockPos lllllllllllllllllIlllIlIlIllllII, BlockState lllllllllllllllllIlllIlIlIllIllI) {
            PathBlock lllllllllllllllllIlllIlIlIllllll;
            lllllllllllllllllIlllIlIlIllllll.block = lllllllllllllllllIlllIlIlIllllIl;
            lllllllllllllllllIlllIlIlIllllll.blockPos = lllllllllllllllllIlllIlIlIllllII;
            lllllllllllllllllIlllIlIlIllllll.blockState = lllllllllllllllllIlllIlIlIllIllI;
        }

        public PathBlock(Block lllllllllllllllllIlllIlIlIlIllll, BlockPos lllllllllllllllllIlllIlIlIlIlIlI) {
            PathBlock lllllllllllllllllIlllIlIlIlIllIl;
            lllllllllllllllllIlllIlIlIlIllIl.block = lllllllllllllllllIlllIlIlIlIllll;
            lllllllllllllllllIlllIlIlIlIllIl.blockPos = lllllllllllllllllIlllIlIlIlIlIlI;
            lllllllllllllllllIlllIlIlIlIllIl.blockState = lllllllllllllllllIlllIlIlIlIllIl.PathFinder.this.getBlockStateAtPos(lllllllllllllllllIlllIlIlIlIllIl.blockPos);
        }

        public PathBlock(BlockPos lllllllllllllllllIlllIlIlIlIIIIl) {
            PathBlock lllllllllllllllllIlllIlIlIlIIllI;
            lllllllllllllllllIlllIlIlIlIIllI.blockPos = lllllllllllllllllIlllIlIlIlIIIIl;
            lllllllllllllllllIlllIlIlIlIIllI.block = lllllllllllllllllIlllIlIlIlIIllI.PathFinder.this.getBlockAtPos(lllllllllllllllllIlllIlIlIlIIIIl);
            lllllllllllllllllIlllIlIlIlIIllI.blockState = lllllllllllllllllIlllIlIlIlIIllI.PathFinder.this.getBlockStateAtPos(lllllllllllllllllIlllIlIlIlIIllI.blockPos);
        }
    }
}

