/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.Ambience;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.world.BlockRenderView;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BiomeColors.class})
public class BiomeColorsMixin {
    @Inject(method={"getWaterColor"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onGetWaterColor(BlockRenderView BlockRenderView2, BlockPos BlockPos2, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeWaterColor.get().booleanValue()) {
            callbackInfoReturnable.setReturnValue((Object)ambience.waterColor.get().getPacked());
        }
    }

    @Inject(method={"getFoliageColor"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onGetFoliageColor(BlockRenderView BlockRenderView2, BlockPos BlockPos2, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeFoliageColor.get().booleanValue()) {
            callbackInfoReturnable.setReturnValue((Object)ambience.foliageColor.get().getPacked());
        }
    }

    @Inject(method={"getGrassColor"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onGetGrassColor(BlockRenderView BlockRenderView2, BlockPos BlockPos2, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeGrassColor.get().booleanValue()) {
            callbackInfoReturnable.setReturnValue((Object)ambience.grassColor.get().getPacked());
        }
    }
}

