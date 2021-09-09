/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.block.BlockState
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

public class BreakBlockEvent {
    public /* synthetic */ BlockPos blockPos;
    private static final /* synthetic */ BreakBlockEvent INSTANCE;

    static {
        INSTANCE = new BreakBlockEvent();
    }

    public static BreakBlockEvent get(BlockPos lllllllllllllllllIlIllIIIlllIIlI) {
        BreakBlockEvent.INSTANCE.blockPos = lllllllllllllllllIlIllIIIlllIIlI;
        return INSTANCE;
    }

    public BlockState getBlockState(World lllllllllllllllllIlIllIIIlllIllI) {
        BreakBlockEvent lllllllllllllllllIlIllIIIlllIlll;
        return lllllllllllllllllIlIllIIIlllIllI.getBlockState(lllllllllllllllllIlIllIIIlllIlll.blockPos);
    }

    public BreakBlockEvent() {
        BreakBlockEvent lllllllllllllllllIlIllIIIllllIll;
    }
}

