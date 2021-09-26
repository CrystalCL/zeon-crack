/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.WallHack;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={RenderLayers.class})
public class RenderLayersMixin {
    @Inject(method={"getBlockLayer"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onGetBlockLayer(BlockState BlockState2, CallbackInfoReturnable<RenderLayer> callbackInfoReturnable) {
        WallHack wallHack;
        if (Modules.get() != null && (wallHack = Modules.get().get(WallHack.class)).isActive() && wallHack.blocks.get().contains(BlockState2.getBlock())) {
            callbackInfoReturnable.setReturnValue((Object)RenderLayer.getTranslucent());
        }
    }
}

