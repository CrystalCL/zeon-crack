/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.FluidCollisionShapeEvent;
import net.minecraft.world.BlockView;
import net.minecraft.block.Block;
import net.minecraft.block.FluidDrainable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.FluidBlock;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={FluidBlock.class})
public abstract class FluidBlockMixin
extends Block
implements FluidDrainable {
    public FluidBlockMixin(Settings class_22512) {
        super(class_22512);
    }

    @Inject(method={"getCollisionShape"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetCollisionShape(BlockState BlockState2, BlockView BlockView2, BlockPos BlockPos2, ShapeContext ShapeContext2, CallbackInfoReturnable<VoxelShape> callbackInfoReturnable) {
        FluidCollisionShapeEvent fluidCollisionShapeEvent = MeteorClient.EVENT_BUS.post(FluidCollisionShapeEvent.get(BlockState2));
        if (fluidCollisionShapeEvent.shape != null) {
            callbackInfoReturnable.setReturnValue((Object)fluidCollisionShapeEvent.shape);
        }
    }
}

