/*
 * Decompiled with CFR 0.151.
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
    private int modifyColorIfLava(int n, BlockRenderView BlockRenderView2, BlockPos BlockPos2, VertexConsumer VertexConsumer2, FluidState FluidState2) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeLavaColor.get().booleanValue() && FluidState2.isIn((Tag)FluidTags.LAVA)) {
            return ambience.lavaColor.get().getPacked();
        }
        return n;
    }
}

