/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.Slippy;
import minegame159.meteorclient.systems.modules.render.Xray;
import net.minecraft.world.BlockView;
import net.minecraft.item.ItemConvertible;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Block.class})
public abstract class BlockMixin
extends AbstractBlock
implements ItemConvertible {
    public BlockMixin(Settings class_22512) {
        super(class_22512);
    }

    @Inject(method={"shouldDrawSide"}, at={@At(value="RETURN")}, cancellable=true)
    private static void onShouldDrawSide(BlockState BlockState2, BlockView BlockView2, BlockPos BlockPos2, Direction Direction2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        Xray xray = Modules.get().get(Xray.class);
        if (xray.isActive()) {
            callbackInfoReturnable.setReturnValue((Object)xray.modifyDrawSide(BlockState2, BlockView2, BlockPos2, Direction2, callbackInfoReturnable.getReturnValueZ()));
        }
    }

    @Inject(method={"getSlipperiness"}, at={@At(value="RETURN")}, cancellable=true)
    public void getSlipperiness(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (Modules.get() == null) {
            return;
        }
        Slippy slippy = Modules.get().get(Slippy.class);
        Block Block2 = (Block)this;
        if (slippy.isActive() && !slippy.blocks.get().contains(Block2)) {
            callbackInfoReturnable.setReturnValue((Object)Float.valueOf(slippy.slippness.get().floatValue()));
        }
    }
}

