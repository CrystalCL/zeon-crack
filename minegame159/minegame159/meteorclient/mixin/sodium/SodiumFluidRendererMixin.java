/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin.sodium;

import java.util.Arrays;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.render.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.client.util.color.ColorARGB;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.Ambience;
import net.minecraft.world.BlockRenderView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={FluidRenderer.class}, remap=false)
public class SodiumFluidRendererMixin {
    @Final
    @Shadow(remap=false)
    private int[] quadColors;

    @Inject(method={"calculateQuadColors"}, at={@At(value="TAIL")}, cancellable=true, remap=false)
    private void onCalculateQuadColors(ModelQuadView modelQuadView, BlockRenderView BlockRenderView2, BlockPos BlockPos2, LightPipeline lightPipeline, Direction Direction2, float f, boolean bl, CallbackInfo callbackInfo) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeLavaColor.get().booleanValue() && !bl) {
            Arrays.fill(this.quadColors, ColorARGB.toABGR((int)ambience.lavaColor.get().getPacked()));
        }
    }
}

