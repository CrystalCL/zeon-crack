/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.world;

import minegame159.meteorclient.utils.misc.Pool;
import net.minecraft.world.chunk.WorldChunk;

public class ChunkDataEvent {
    public WorldChunk chunk;
    private static final Pool<ChunkDataEvent> INSTANCE = new Pool<ChunkDataEvent>(ChunkDataEvent::new);

    public static void returnChunkDataEvent(ChunkDataEvent chunkDataEvent) {
        INSTANCE.free(chunkDataEvent);
    }

    public static ChunkDataEvent get(WorldChunk WorldChunk2) {
        ChunkDataEvent chunkDataEvent = INSTANCE.get();
        chunkDataEvent.chunk = WorldChunk2;
        return chunkDataEvent;
    }
}

