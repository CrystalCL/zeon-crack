/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import java.util.ArrayList;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import net.minecraft.class_1297;
import net.minecraft.class_2183;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_310;

public class PathFinder {
    private static final int SOUTH;
    private static final int QUAD_1;
    private static final int PATH_AHEAD;
    private final class_310 mc;
    private final ArrayList<PathBlock> path = new ArrayList(3);
    private static final int QUAD_2;
    private static final int NORTH;
    private PathBlock currentPathBlock;
    private class_1297 target;

    public boolean isSolidFloor(class_2338 class_23382) {
        return this.isAir(this.getBlockAtPos(class_23382));
    }

    static {
        QUAD_1 = 1;
        SOUTH = 0;
        PATH_AHEAD = 3;
        QUAD_2 = 2;
        NORTH = 180;
    }

    public void lookAtDestination(PathBlock pathBlock) {
        if (this.mc.field_1724 != null) {
            this.mc.field_1724.method_5702(class_2183.class_2184.field_9851, new class_243((double)pathBlock.blockPos.method_10263(), (double)((float)pathBlock.blockPos.method_10264() + this.mc.field_1724.method_5751()), (double)pathBlock.blockPos.method_10260()));
        }
    }

    public boolean isAirAbove(class_2338 class_23382) {
        if (!this.getBlockStateAtPos(class_23382.method_10263(), class_23382.method_10264(), class_23382.method_10260()).method_26215()) {
            return false;
        }
        return this.getBlockStateAtPos(class_23382.method_10263(), class_23382.method_10264() + 1, class_23382.method_10260()).method_26215();
    }

    public boolean isWater(class_2248 class_22482) {
        return class_22482.method_27839(class_2246.field_10382);
    }

    public PathFinder() {
        this.mc = class_310.method_1551();
    }

    @EventHandler
    private void moveEventListener(PlayerMoveEvent playerMoveEvent) {
        if (this.target != null && this.mc.field_1724 != null) {
            if (this.mc.field_1724.method_5739(this.target) > 3.0f) {
                if (this.currentPathBlock == null) {
                    this.currentPathBlock = this.getNextPathBlock();
                }
                class_243 class_2432 = new class_243((double)this.currentPathBlock.blockPos.method_10263(), (double)this.currentPathBlock.blockPos.method_10264(), (double)this.currentPathBlock.blockPos.method_10260());
                if (this.mc.field_1724.method_19538().method_1022(class_2432) < 0.1) {
                    this.currentPathBlock = this.getNextPathBlock();
                }
                this.lookAtDestination(this.currentPathBlock);
                if (!this.mc.field_1690.field_1894.method_1434()) {
                    this.mc.field_1690.field_1894.method_23481(true);
                }
            } else {
                if (this.mc.field_1690.field_1894.method_1434()) {
                    this.mc.field_1690.field_1894.method_23481(false);
                }
                this.path.clear();
                this.currentPathBlock = null;
            }
        }
    }

    public int getDrop(class_2338 class_23382) {
        int n;
        for (n = 0; !this.isSolidFloor(class_23382) && n < 3; ++n) {
            class_23382 = new class_2338(class_23382.method_10263(), class_23382.method_10264() - 1, class_23382.method_10260());
        }
        return n;
    }

    public class_2680 getBlockStateAtPos(class_2338 class_23382) {
        if (this.mc.field_1687 != null) {
            return this.mc.field_1687.method_8320(class_23382);
        }
        return null;
    }

    public class_2680 getBlockStateAtPos(int n, int n2, int n3) {
        if (this.mc.field_1687 != null) {
            return this.mc.field_1687.method_8320(new class_2338(n, n2, n3));
        }
        return null;
    }

    public void disable() {
        this.target = null;
        this.path.clear();
        if (this.mc.field_1690.field_1894.method_1434()) {
            this.mc.field_1690.field_1894.method_23481(false);
        }
        MeteorClient.EVENT_BUS.unsubscribe(this);
    }

    public int getDirection() {
        if (this.target == null || this.mc.field_1724 == null) {
            return 0;
        }
        class_243 class_2432 = this.target.method_19538();
        class_243 class_2433 = this.mc.field_1724.method_19538();
        if (class_2432.field_1352 == class_2433.field_1352 && class_2432.field_1350 > class_2433.field_1350) {
            return 0;
        }
        if (class_2432.field_1352 == class_2433.field_1352 && class_2432.field_1350 < class_2433.field_1350) {
            return 180;
        }
        if (class_2432.field_1352 < class_2433.field_1352) {
            return 1;
        }
        if (class_2432.field_1352 > class_2433.field_1352) {
            return 2;
        }
        return 0;
    }

    public boolean isAir(class_2248 class_22482) {
        return class_22482.method_27839(class_2246.field_10124);
    }

    public class_243 getNextStraightPos() {
        class_243 class_2432 = new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318(), this.mc.field_1724.method_23321());
        double d = 1.0;
        while (class_2432 == this.mc.field_1724.method_19538()) {
            class_2432 = new class_243((double)((int)(this.mc.field_1724.method_23317() + d * Math.cos(Math.toRadians(this.mc.field_1724.field_6031)))), (double)((int)this.mc.field_1724.method_23318()), (double)((int)(this.mc.field_1724.method_23321() + d * Math.sin(Math.toRadians(this.mc.field_1724.field_6031)))));
            d += 0.1;
        }
        return class_2432;
    }

    public class_2248 getBlockAtPos(class_2338 class_23382) {
        if (this.mc.field_1687 != null) {
            return this.mc.field_1687.method_8320(class_23382).method_26204();
        }
        return null;
    }

    public PathBlock getNextPathBlock() {
        PathBlock pathBlock = new PathBlock(this, new class_2338(this.getNextStraightPos()));
        if (this.isSolidFloor(pathBlock.blockPos) && this.isAirAbove(pathBlock.blockPos)) {
            return pathBlock;
        }
        if (!this.isSolidFloor(pathBlock.blockPos) && this.isAirAbove(pathBlock.blockPos)) {
            int n = this.getDrop(pathBlock.blockPos);
            if (this.getDrop(pathBlock.blockPos) < 3) {
                pathBlock = new PathBlock(this, new class_2338(pathBlock.blockPos.method_10263(), pathBlock.blockPos.method_10264() - n, pathBlock.blockPos.method_10260()));
            }
        }
        return pathBlock;
    }

    public void initiate(class_1297 class_12972) {
        this.target = class_12972;
        if (this.target != null) {
            this.currentPathBlock = this.getNextPathBlock();
        }
        MeteorClient.EVENT_BUS.subscribe(this);
    }

    public int getYawToTarget() {
        if (this.target == null || this.mc.field_1724 == null) {
            return Integer.MAX_VALUE;
        }
        class_243 class_2432 = this.target.method_19538();
        class_243 class_2433 = this.mc.field_1724.method_19538();
        int n = 0;
        int n2 = this.getDirection();
        double d = (class_2432.field_1350 - class_2433.field_1350) / (class_2432.field_1352 - class_2433.field_1352);
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
        public final class_2680 blockState;
        public double yaw;
        public final class_2248 block;
        final PathFinder this$0;
        public final class_2338 blockPos;

        public PathBlock(PathFinder pathFinder, class_2338 class_23382) {
            this.this$0 = pathFinder;
            this.blockPos = class_23382;
            this.block = pathFinder.getBlockAtPos(class_23382);
            this.blockState = pathFinder.getBlockStateAtPos(this.blockPos);
        }

        public PathBlock(PathFinder pathFinder, class_2248 class_22482, class_2338 class_23382, class_2680 class_26802) {
            this.this$0 = pathFinder;
            this.block = class_22482;
            this.blockPos = class_23382;
            this.blockState = class_26802;
        }

        public PathBlock(PathFinder pathFinder, class_2248 class_22482, class_2338 class_23382) {
            this.this$0 = pathFinder;
            this.block = class_22482;
            this.blockPos = class_23382;
            this.blockState = pathFinder.getBlockStateAtPos(this.blockPos);
        }
    }
}

