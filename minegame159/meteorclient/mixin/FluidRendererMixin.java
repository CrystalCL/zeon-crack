/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.BlockRenderView
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.tag.FluidTags
 *  net.minecraft.tag.Tag
 *  net.minecraft.fluid.FluidState
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.block.FluidRenderer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.Ambience;
import net.minecraft.world.BlockRenderView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.fluid.FluidState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={FluidRenderer.class})
public class FluidRendererMixin {
    @ModifyVariable(method={"render"}, at=@At(value="STORE", ordinal=0), index=8)
    private int modifyColorIfLava(int color, BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, FluidState state) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeLavaColor.get().booleanValue() && state.isIn((Tag)FluidTags.LAVA)) {
            return ambience.lavaColor.get().getPacked();
        }
        return color;
    }
}

