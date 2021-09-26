/*
 * Decompiled with CFR 0.151.
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
    private static final int SOUTH;
    private static final int QUAD_1;
    private static final int PATH_AHEAD;
    private final MinecraftClient mc;
    private final ArrayList<PathBlock> path = new ArrayList(3);
    private static final int QUAD_2;
    private static final int NORTH;
    private PathBlock currentPathBlock;
    private Entity target;

    public boolean isSolidFloor(BlockPos BlockPos2) {
        return this.isAir(this.getBlockAtPos(BlockPos2));
    }

    static {
        QUAD_1 = 1;
        SOUTH = 0;
        PATH_AHEAD = 3;
        QUAD_2 = 2;
        NORTH = 180;
    }

    public void lookAtDestination(PathBlock pathBlock) {
        if (this.mc.player != null) {
            this.mc.player.lookAt(EntityAnchor.EYES, new Vec3d((double)pathBlock.blockPos.getX(), (double)((float)pathBlock.blockPos.getY() + this.mc.player.getStandingEyeHeight()), (double)pathBlock.blockPos.getZ()));
        }
    }

    public boolean isAirAbove(BlockPos BlockPos2) {
        if (!this.getBlockStateAtPos(BlockPos2.getX(), BlockPos2.getY(), BlockPos2.getZ()).isAir()) {
            return false;
        }
        return this.getBlockStateAtPos(BlockPos2.getX(), BlockPos2.getY() + 1, BlockPos2.getZ()).isAir();
    }

    public boolean isWater(Block Block2) {
        return Block2.is(Blocks.WATER);
    }

    public PathFinder() {
        this.mc = MinecraftClient.getInstance();
    }

    @EventHandler
    private void moveEventListener(PlayerMoveEvent playerMoveEvent) {
        if (this.target != null && this.mc.player != null) {
            if (this.mc.player.distanceTo(this.target) > 3.0f) {
                if (this.currentPathBlock == null) {
                    this.currentPathBlock = this.getNextPathBlock();
                }
                Vec3d Vec3d2 = new Vec3d((double)this.currentPathBlock.blockPos.getX(), (double)this.currentPathBlock.blockPos.getY(), (double)this.currentPathBlock.blockPos.getZ());
                if (this.mc.player.getPos().distanceTo(Vec3d2) < 0.1) {
                    this.currentPathBlock = this.getNextPathBlock();
                }
                this.lookAtDestination(this.currentPathBlock);
                if (!this.mc.options.keyForward.isPressed()) {
                    this.mc.options.keyForward.setPressed(true);
                }
            } else {
                if (this.mc.options.keyForward.isPressed()) {
                    this.mc.options.keyForward.setPressed(false);
                }
                this.path.clear();
                this.currentPathBlock = null;
            }
        }
    }

    public int getDrop(BlockPos BlockPos2) {
        int n;
        for (n = 0; !this.isSolidFloor(BlockPos2) && n < 3; ++n) {
            BlockPos2 = new BlockPos(BlockPos2.getX(), BlockPos2.getY() - 1, BlockPos2.getZ());
        }
        return n;
    }

    public BlockState getBlockStateAtPos(BlockPos BlockPos2) {
        if (this.mc.world != null) {
            return this.mc.world.getBlockState(BlockPos2);
        }
        return null;
    }

    public BlockState getBlockStateAtPos(int n, int n2, int n3) {
        if (this.mc.world != null) {
            return this.mc.world.getBlockState(new BlockPos(n, n2, n3));
        }
        return null;
    }

    public void disable() {
        this.target = null;
        this.path.clear();
        if (this.mc.options.keyForward.isPressed()) {
            this.mc.options.keyForward.setPressed(false);
        }
        MeteorClient.EVENT_BUS.unsubscribe(this);
    }

    public int getDirection() {
        if (this.target == null || this.mc.player == null) {
            return 0;
        }
        Vec3d Vec3d2 = this.target.getPos();
        Vec3d Vec3d3 = this.mc.player.getPos();
        if (Vec3d2.x == Vec3d3.x && Vec3d2.z > Vec3d3.z) {
            return 0;
        }
        if (Vec3d2.x == Vec3d3.x && Vec3d2.z < Vec3d3.z) {
            return 180;
        }
        if (Vec3d2.x < Vec3d3.x) {
            return 1;
        }
        if (Vec3d2.x > Vec3d3.x) {
            return 2;
        }
        return 0;
    }

    public boolean isAir(Block Block2) {
        return Block2.is(Blocks.AIR);
    }

    public Vec3d getNextStraightPos() {
        Vec3d Vec3d2 = new Vec3d(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ());
        double d = 1.0;
        while (Vec3d2 == this.mc.player.getPos()) {
            Vec3d2 = new Vec3d((double)((int)(this.mc.player.getX() + d * Math.cos(Math.toRadians(this.mc.player.yaw)))), (double)((int)this.mc.player.getY()), (double)((int)(this.mc.player.getZ() + d * Math.sin(Math.toRadians(this.mc.player.yaw)))));
            d += 0.1;
        }
        return Vec3d2;
    }

    public Block getBlockAtPos(BlockPos BlockPos2) {
        if (this.mc.world != null) {
            return this.mc.world.getBlockState(BlockPos2).getBlock();
        }
        return null;
    }

    public PathBlock getNextPathBlock() {
        PathBlock pathBlock = new PathBlock(this, new BlockPos(this.getNextStraightPos()));
        if (this.isSolidFloor(pathBlock.blockPos) && this.isAirAbove(pathBlock.blockPos)) {
            return pathBlock;
        }
        if (!this.isSolidFloor(pathBlock.blockPos) && this.isAirAbove(pathBlock.blockPos)) {
            int n = this.getDrop(pathBlock.blockPos);
            if (this.getDrop(pathBlock.blockPos) < 3) {
                pathBlock = new PathBlock(this, new BlockPos(pathBlock.blockPos.getX(), pathBlock.blockPos.getY() - n, pathBlock.blockPos.getZ()));
            }
        }
        return pathBlock;
    }

    public void initiate(Entity Entity2) {
        this.target = Entity2;
        if (this.target != null) {
            this.currentPathBlock = this.getNextPathBlock();
        }
        MeteorClient.EVENT_BUS.subscribe(this);
    }

    public int getYawToTarget() {
        if (this.target == null || this.mc.player == null) {
            return Integer.MAX_VALUE;
        }
        Vec3d Vec3d2 = this.target.getPos();
        Vec3d Vec3d3 = this.mc.player.getPos();
        int n = 0;
        int n2 = this.getDirection();
        double d = (Vec3d2.z - Vec3d3.z) / (Vec3d2.x - Vec3d3.x);
        if (n2 == 1) {
            n = (int)(1.5707963267948966 - Math.atan(d));
        } else if (n2 == 2) {
            n = (int)(-1.5707963267948966 - Math.atan(d));
        } else {
            return n2;
        }
        return n;
    }

    public class PathBlock {
        public final BlockState blockState;
        public double yaw;
        public final Block block;
        final PathFinder this$0;
        public final BlockPos blockPos;

        public PathBlock(PathFinder pathFinder, BlockPos BlockPos2) {
            this.this$0 = pathFinder;
            this.blockPos = BlockPos2;
            this.block = pathFinder.getBlockAtPos(BlockPos2);
            this.blockState = pathFinder.getBlockStateAtPos(this.blockPos);
        }

        public PathBlock(PathFinder pathFinder, Block Block2, BlockPos BlockPos2, BlockState BlockState2) {
            this.this$0 = pathFinder;
            this.block = Block2;
            this.blockPos = BlockPos2;
            this.blockState = BlockState2;
        }

        public PathBlock(PathFinder pathFinder, Block Block2, BlockPos BlockPos2) {
            this.this$0 = pathFinder;
            this.block = Block2;
            this.blockPos = BlockPos2;
            this.blockState = pathFinder.getBlockStateAtPos(this.blockPos);
        }
    }
}

