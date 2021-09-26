/*
 * Decompiled with CFR 0.151.
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
    private void onGetAmbientOcclusionLightLevel(BlockState BlockState2, BlockView BlockView2, BlockPos BlockPos2, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        AmbientOcclusionEvent ambientOcclusionEvent = MeteorClient.EVENT_BUS.post(AmbientOcclusionEvent.get());
        if (ambientOcclusionEvent.lightLevel != -1.0f) {
            callbackInfoReturnable.setReturnValue((Object)Float.valueOf(ambientOcclusionEvent.lightLevel));
        }
    }

    @Inject(method={"getCollisionShape"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetCollisionShape(BlockState BlockState2, BlockView BlockView2, BlockPos BlockPos2, ShapeContext ShapeContext2, CallbackInfoReturnable<VoxelShape> callbackInfoReturnable) {
        if (!BlockState2.getFluidState().isEmpty()) {
            FluidCollisionShapeEvent fluidCollisionShapeEvent = MeteorClient.EVENT_BUS.post(FluidCollisionShapeEvent.get(BlockState2.getFluidState().getBlockState()));
            if (fluidCollisionShapeEvent.shape != null) {
                callbackInfoReturnable.setReturnValue((Object)fluidCollisionShapeEvent.shape);
            }
        }
    }
}

