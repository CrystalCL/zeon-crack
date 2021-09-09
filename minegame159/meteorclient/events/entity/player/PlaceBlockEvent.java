/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class PlaceBlockEvent {
    public /* synthetic */ BlockPos blockPos;
    public /* synthetic */ Block block;
    private static final /* synthetic */ PlaceBlockEvent INSTANCE;

    public static PlaceBlockEvent get(BlockPos lllllIIlIIlll, Block lllllIIlIlIII) {
        PlaceBlockEvent.INSTANCE.blockPos = lllllIIlIIlll;
        PlaceBlockEvent.INSTANCE.block = lllllIIlIlIII;
        return INSTANCE;
    }

    public PlaceBlockEvent() {
        PlaceBlockEvent lllllIIlIllII;
    }

    static {
        INSTANCE = new PlaceBlockEvent();
    }
}

