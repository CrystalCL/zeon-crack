/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.chunk.WorldChunk
 */
package minegame159.meteorclient.events.world;

import minegame159.meteorclient.utils.misc.Pool;
import net.minecraft.world.chunk.WorldChunk;

public class ChunkDataEvent {
    public /* synthetic */ WorldChunk chunk;
    private static final /* synthetic */ Pool<ChunkDataEvent> INSTANCE;

    public static void returnChunkDataEvent(ChunkDataEvent llIIlIlIIlIllI) {
        INSTANCE.free(llIIlIlIIlIllI);
    }

    static {
        INSTANCE = new Pool<ChunkDataEvent>(ChunkDataEvent::new);
    }

    public static ChunkDataEvent get(WorldChunk llIIlIlIIllIlI) {
        ChunkDataEvent llIIlIlIIllIll = INSTANCE.get();
        llIIlIlIIllIll.chunk = llIIlIlIIllIlI;
        return llIIlIlIIllIll;
    }

    public ChunkDataEvent() {
        ChunkDataEvent llIIlIlIlIIIII;
    }
}

