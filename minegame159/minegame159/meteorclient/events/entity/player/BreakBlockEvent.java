/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

public class BreakBlockEvent {
    public BlockPos blockPos;
    private static final BreakBlockEvent INSTANCE = new BreakBlockEvent();

    public static BreakBlockEvent get(BlockPos BlockPos2) {
        BreakBlockEvent.INSTANCE.blockPos = BlockPos2;
        return INSTANCE;
    }

    public BlockState getBlockState(World World2) {
        return World2.getBlockState(this.blockPos);
    }
}

