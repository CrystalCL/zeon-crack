/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.NoRender;
import net.minecraft.world.chunk.light.ChunkSkyLightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ChunkSkyLightProvider.class})
public class ChunkSkyLightProviderMixin {
    @Inject(at={@At(value="HEAD")}, method={"recalculateLevel"}, cancellable=true)
    private void recalculateLevel(long l, long l2, int n, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (Modules.get().get(NoRender.class).noSkylightUpdates()) {
            callbackInfoReturnable.setReturnValue((Object)15);
            callbackInfoReturnable.cancel();
        }
    }
}

