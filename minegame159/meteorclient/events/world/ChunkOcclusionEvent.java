/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.world;

import minegame159.meteorclient.events.Cancellable;

public class ChunkOcclusionEvent
extends Cancellable {
    private static final /* synthetic */ ChunkOcclusionEvent INSTANCE;

    public static ChunkOcclusionEvent get() {
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }

    public ChunkOcclusionEvent() {
        ChunkOcclusionEvent llllllllllllllllIllIIIllIlIllIlI;
    }

    static {
        INSTANCE = new ChunkOcclusionEvent();
    }
}

