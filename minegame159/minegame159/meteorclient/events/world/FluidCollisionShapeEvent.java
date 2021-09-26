/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.world;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;

public class FluidCollisionShapeEvent {
    public BlockState state;
    public VoxelShape shape;
    private static final FluidCollisionShapeEvent INSTANCE = new FluidCollisionShapeEvent();

    public static FluidCollisionShapeEvent get(BlockState BlockState2) {
        FluidCollisionShapeEvent.INSTANCE.state = BlockState2;
        FluidCollisionShapeEvent.INSTANCE.shape = null;
        return INSTANCE;
    }
}

