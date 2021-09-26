/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Xray;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={TerrainRenderContext.class}, remap=false)
public class TerrainRenderContextMixin {
    @Inject(method={"tesselateBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void onTesselateBlock(BlockState BlockState2, BlockPos BlockPos2, BakedModel BakedModel2, MatrixStack MatrixStack2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        Xray xray = Modules.get().get(Xray.class);
        if (xray.isActive() && xray.isBlocked(BlockState2.getBlock())) {
            callbackInfoReturnable.cancel();
        }
    }
}

