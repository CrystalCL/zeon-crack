/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 */
package minegame159.meteorclient.events.entity.player;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class StartBreakingBlockEvent
extends Cancellable {
    private static final /* synthetic */ StartBreakingBlockEvent INSTANCE;
    public /* synthetic */ Direction direction;
    public /* synthetic */ BlockPos blockPos;

    public static StartBreakingBlockEvent get(BlockPos llllllllllllllllllllllIIlIIlllIl, Direction llllllllllllllllllllllIIlIIllIlI) {
        INSTANCE.setCancelled(false);
        StartBreakingBlockEvent.INSTANCE.blockPos = llllllllllllllllllllllIIlIIlllIl;
        StartBreakingBlockEvent.INSTANCE.direction = llllllllllllllllllllllIIlIIllIlI;
        return INSTANCE;
    }

    static {
        INSTANCE = new StartBreakingBlockEvent();
    }

    public StartBreakingBlockEvent() {
        StartBreakingBlockEvent llllllllllllllllllllllIIlIlIIIIl;
    }
}

