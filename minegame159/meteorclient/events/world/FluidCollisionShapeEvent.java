/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.block.BlockState
 */
package minegame159.meteorclient.events.world;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;

public class FluidCollisionShapeEvent {
    public /* synthetic */ BlockState state;
    public /* synthetic */ VoxelShape shape;
    private static final /* synthetic */ FluidCollisionShapeEvent INSTANCE;

    public static FluidCollisionShapeEvent get(BlockState llIIIIIlIIIlII) {
        FluidCollisionShapeEvent.INSTANCE.state = llIIIIIlIIIlII;
        FluidCollisionShapeEvent.INSTANCE.shape = null;
        return INSTANCE;
    }

    public FluidCollisionShapeEvent() {
        FluidCollisionShapeEvent llIIIIIlIIlIII;
    }

    static {
        INSTANCE = new FluidCollisionShapeEvent();
    }
}

