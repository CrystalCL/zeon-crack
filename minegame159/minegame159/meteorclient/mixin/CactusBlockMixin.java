/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.AntiCactus;
import net.minecraft.world.BlockView;
import net.minecraft.block.CactusBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={CactusBlock.class})
public class CactusBlockMixin {
    @Inject(method={"getCollisionShape"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetCollisionShape(BlockState BlockState2, BlockView BlockView2, BlockPos BlockPos2, ShapeContext ShapeContext2, CallbackInfoReturnable<VoxelShape> callbackInfoReturnable) {
        if (Modules.get().isActive(AntiCactus.class)) {
            callbackInfoReturnable.setReturnValue((Object)VoxelShapes.fullCube());
        }
    }
}

