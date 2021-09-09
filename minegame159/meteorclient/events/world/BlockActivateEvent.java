/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockState
 */
package minegame159.meteorclient.events.world;

import net.minecraft.block.BlockState;

public class BlockActivateEvent {
    private static final /* synthetic */ BlockActivateEvent INSTANCE;
    public /* synthetic */ BlockState blockState;

    static {
        INSTANCE = new BlockActivateEvent();
    }

    public static BlockActivateEvent get(BlockState lIllIlllllIII) {
        BlockActivateEvent.INSTANCE.blockState = lIllIlllllIII;
        return INSTANCE;
    }

    public BlockActivateEvent() {
        BlockActivateEvent lIllIlllllIll;
    }
}

