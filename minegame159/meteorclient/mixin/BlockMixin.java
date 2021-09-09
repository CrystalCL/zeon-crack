/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.BlockView
 *  net.minecraft.item.ItemConvertible
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.AbstractBlock
 *  net.minecraft.block.AbstractBlock.Settings
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method={"shouldDrawSide"}, at={@At(value="RETURN")}, cancellable=true)
    private static void onShouldDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> info) {
        Xray xray = Modules.get().get(Xray.class);
        if (xray.isActive()) {
            info.setReturnValue((Object)xray.modifyDrawSide(state, view, pos, facing, info.getReturnValueZ()));
        }
    }

    @Inject(method={"getSlipperiness"}, at={@At(value="RETURN")}, cancellable=true)
    public void getSlipperiness(CallbackInfoReturnable<Float> info) {
        if (Modules.get() == null) {
            return;
        }
        Slippy slippy = Modules.get().get(Slippy.class);
        Block block = (Block)this;
        if (slippy.isActive() && !slippy.blocks.get().contains((Object)block)) {
            info.setReturnValue((Object)Float.valueOf(slippy.slippness.get().floatValue()));
        }
    }
}

