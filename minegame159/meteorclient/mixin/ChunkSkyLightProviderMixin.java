/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.chunk.light.ChunkSkyLightProvider
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
    private void recalculateLevel(long long_1, long long_2, int int_1, CallbackInfoReturnable<Integer> ci) {
        if (Modules.get().get(NoRender.class).noSkylightUpdates()) {
            ci.setReturnValue((Object)15);
            ci.cancel();
        }
    }
}

