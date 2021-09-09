/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.BlockView
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.ShapeContext
 *  net.minecraft.block.AbstractBlock
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.AmbientOcclusionEvent;
import minegame159.meteorclient.events.world.FluidCollisionShapeEvent;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractBlock.class})
public class AbstractBlockMixin {
    @Inject(method={"getAmbientOcclusionLightLevel"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        AmbientOcclusionEvent event = MeteorClient.EVENT_BUS.post(AmbientOcclusionEvent.get());
        if (event.lightLevel != -1.0f) {
            info.setReturnValue((Object)Float.valueOf(event.lightLevel));
        }
    }

    @Inject(method={"getCollisionShape"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> info) {
        if (!state.getFluidState().isEmpty()) {
            FluidCollisionShapeEvent event = MeteorClient.EVENT_BUS.post(FluidCollisionShapeEvent.get(state.getFluidState().getBlockState()));
            if (event.shape != null) {
                info.setReturnValue((Object)event.shape);
            }
        }
    }
}

