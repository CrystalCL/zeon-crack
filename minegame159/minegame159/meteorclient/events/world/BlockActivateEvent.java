/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.world;

import net.minecraft.block.BlockState;

public class BlockActivateEvent {
    public BlockState blockState;
    private static final BlockActivateEvent INSTANCE = new BlockActivateEvent();

    public static BlockActivateEvent get(BlockState BlockState2) {
        BlockActivateEvent.INSTANCE.blockState = BlockState2;
        return INSTANCE;
    }
}

