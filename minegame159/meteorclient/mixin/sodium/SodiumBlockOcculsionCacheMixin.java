/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache
 *  net.minecraft.world.BlockView
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.block.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
    private void shouldDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> info) {
        Xray xray = Modules.get().get(Xray.class);
        if (xray.isActive()) {
            info.setReturnValue((Object)xray.modifyDrawSide(state, view, pos, facing, info.getReturnValueZ()));
        }
    }
}

