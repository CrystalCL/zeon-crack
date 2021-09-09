/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.color.world.BiomeColors
 *  net.minecraft.world.BlockRenderView
 *  net.minecraft.util.math.BlockPos
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
    private static void onGetWaterColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> info) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeWaterColor.get().booleanValue()) {
            info.setReturnValue((Object)ambience.waterColor.get().getPacked());
        }
    }

    @Inject(method={"getFoliageColor"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onGetFoliageColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> info) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeFoliageColor.get().booleanValue()) {
            info.setReturnValue((Object)ambience.foliageColor.get().getPacked());
        }
    }

    @Inject(method={"getGrassColor"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onGetGrassColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> info) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeGrassColor.get().booleanValue()) {
            info.setReturnValue((Object)ambience.grassColor.get().getPacked());
        }
    }
}

