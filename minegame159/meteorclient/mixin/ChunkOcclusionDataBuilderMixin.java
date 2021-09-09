/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.ChunkOcclusionEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChunkOcclusionDataBuilder.class})
public class ChunkOcclusionDataBuilderMixin {
    @Inject(method={"markClosed"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMarkClosed(BlockPos pos, CallbackInfo info) {
        ChunkOcclusionEvent event = MeteorClient.EVENT_BUS.post(ChunkOcclusionEvent.get());
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}

