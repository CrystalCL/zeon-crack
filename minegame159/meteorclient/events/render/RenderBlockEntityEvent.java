/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.entity.BlockEntity
 */
package minegame159.meteorclient.events.render;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.block.entity.BlockEntity;

public class RenderBlockEntityEvent
extends Cancellable {
    public /* synthetic */ BlockEntity blockEntity;
    private static final /* synthetic */ RenderBlockEntityEvent INSTANCE;

    public static RenderBlockEntityEvent get(BlockEntity llIIlllIIIII) {
        INSTANCE.setCancelled(false);
        RenderBlockEntityEvent.INSTANCE.blockEntity = llIIlllIIIII;
        return INSTANCE;
    }

    public RenderBlockEntityEvent() {
        RenderBlockEntityEvent llIIlllIIIll;
    }

    static {
        INSTANCE = new RenderBlockEntityEvent();
    }
}

