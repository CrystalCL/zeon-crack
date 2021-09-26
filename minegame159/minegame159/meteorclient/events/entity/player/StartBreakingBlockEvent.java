/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class StartBreakingBlockEvent
extends Cancellable {
    public Direction direction;
    private static final StartBreakingBlockEvent INSTANCE = new StartBreakingBlockEvent();
    public BlockPos blockPos;

    public static StartBreakingBlockEvent get(BlockPos BlockPos2, Direction Direction2) {
        INSTANCE.setCancelled(false);
        StartBreakingBlockEvent.INSTANCE.blockPos = BlockPos2;
        StartBreakingBlockEvent.INSTANCE.direction = Direction2;
        return INSTANCE;
    }
}

