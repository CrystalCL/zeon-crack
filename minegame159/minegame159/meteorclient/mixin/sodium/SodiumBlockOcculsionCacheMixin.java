/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin.sodium;

import me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Xray;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockOcclusionCache.class}, remap=false)
public class SodiumBlockOcculsionCacheMixin {
    @Inject(method={"shouldDrawSide"}, at={@At(value="RETURN")}, cancellable=true)
    private void shouldDrawSide(BlockState BlockState2, BlockView BlockView2, BlockPos BlockPos2, Direction Direction2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        Xray xray = Modules.get().get(Xray.class);
        if (xray.isActive()) {
            callbackInfoReturnable.setReturnValue((Object)xray.modifyDrawSide(BlockState2, BlockView2, BlockPos2, Direction2, callbackInfoReturnable.getReturnValueZ()));
        }
    }
}

