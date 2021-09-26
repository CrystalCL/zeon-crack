/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.render;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.block.entity.BlockEntity;

public class RenderBlockEntityEvent
extends Cancellable {
    public BlockEntity blockEntity;
    private static final RenderBlockEntityEvent INSTANCE = new RenderBlockEntityEvent();

    public static RenderBlockEntityEvent get(BlockEntity BlockEntity2) {
        INSTANCE.setCancelled(false);
        RenderBlockEntityEvent.INSTANCE.blockEntity = BlockEntity2;
        return INSTANCE;
    }
}

