/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class PlaceBlockEvent {
    public Block block;
    private static final PlaceBlockEvent INSTANCE = new PlaceBlockEvent();
    public BlockPos blockPos;

    public static PlaceBlockEvent get(BlockPos BlockPos2, Block Block2) {
        PlaceBlockEvent.INSTANCE.blockPos = BlockPos2;
        PlaceBlockEvent.INSTANCE.block = Block2;
        return INSTANCE;
    }
}

