/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.jellysquid.mods.sodium.client.model.light.data.QuadLightData
 *  me.jellysquid.mods.sodium.client.model.quad.ModelQuad
 *  me.jellysquid.mods.sodium.client.model.quad.ModelQuadView
 *  me.jellysquid.mods.sodium.client.model.quad.ModelQuadViewMutable
 *  me.jellysquid.mods.sodium.client.model.quad.blender.BiomeColorBlender
 *  me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadFacing
 *  me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadOrientation
 *  me.jellysquid.mods.sodium.client.model.quad.sink.ModelQuadSinkDelegate
 *  me.jellysquid.mods.sodium.client.render.pipeline.BlockRenderer
 *  me.jellysquid.mods.sodium.client.util.ModelQuadUtil
 *  me.jellysquid.mods.sodium.client.util.color.ColorABGR
 *  net.minecraft.client.render.model.BakedModel
 *  net.minecraft.world.BlockRenderView
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.color.block.BlockColorProvider
 *  net.minecraft.client.render.model.BakedQuad
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package minegame159.meteorclient.mixin.sodium;

import me.jellysquid.mods.sodium.client.model.light.data.QuadLightData;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuad;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadViewMutable;
import me.jellysquid.mods.sodium.client.model.quad.blender.BiomeColorBlender;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadOrientation;
import me.jellysquid.mods.sodium.client.model.quad.sink.ModelQuadSinkDelegate;
import me.jellysquid.mods.sodium.client.render.pipeline.BlockRenderer;
import me.jellysquid.mods.sodium.client.util.ModelQuadUtil;
import me.jellysquid.mods.sodium.client.util.color.ColorABGR;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.WallHack;
import minegame159.meteorclient.systems.modules.render.Xray;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.world.BlockRenderView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.render.model.BakedQuad;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockRenderer.class}, remap=false)
public class SodiumBlockRendererMixin {
    @Shadow
    @Final
    private ModelQuad cachedQuad;
    @Shadow
    @Final
    private BiomeColorBlender biomeColorBlender;

    @Inject(method={"renderQuad"}, at={@At(value="INVOKE", target="Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;setColor(II)V", shift=At.Shift.AFTER)}, cancellable=true)
    private void onRenderQuad(BlockRenderView world, BlockState state, BlockPos pos, ModelQuadSinkDelegate consumer, Vec3d offset, BlockColorProvider colorProvider, BakedQuad bakedQuad, QuadLightData light, ModelQuadFacing facing, CallbackInfo ci) {
        WallHack wallHack = Modules.get().get(WallHack.class);
        if (wallHack.isActive() && wallHack.blocks.get().contains((Object)state.getBlock())) {
            this.whRenderQuad(world, state, pos, consumer, offset, colorProvider, bakedQuad, light, facing, wallHack);
            ci.cancel();
        }
    }

    @Inject(method={"renderModel"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderModel(BlockRenderView world, BlockState state, BlockPos pos, BakedModel model, ModelQuadSinkDelegate builder, boolean cull, long seed, CallbackInfoReturnable<Boolean> cir) {
        Xray xray = Modules.get().get(Xray.class);
        if (xray.isActive() && xray.isBlocked(state.getBlock())) {
            cir.setReturnValue((Object)false);
        }
    }

    private void whRenderQuad(BlockRenderView world, BlockState state, BlockPos pos, ModelQuadSinkDelegate consumer, Vec3d offset, BlockColorProvider colorProvider, BakedQuad bakedQuad, QuadLightData light, ModelQuadFacing facing, WallHack wallHack) {
        ModelQuadView src = (ModelQuadView)bakedQuad;
        ModelQuadOrientation order = ModelQuadOrientation.orient((float[])light.br);
        ModelQuad copy = this.cachedQuad;
        int norm = ModelQuadUtil.getFacingNormal((Direction)bakedQuad.getFace());
        int[] colors = null;
        if (bakedQuad.hasColor()) {
            colors = this.biomeColorBlender.getColors(colorProvider, world, state, pos, src);
        }
        for (int dstIndex = 0; dstIndex < 4; ++dstIndex) {
            int srcIndex = order.getVertexIndex(dstIndex);
            copy.setX(dstIndex, src.getX(srcIndex) + (float)offset.getX());
            copy.setY(dstIndex, src.getY(srcIndex) + (float)offset.getY());
            copy.setZ(dstIndex, src.getZ(srcIndex) + (float)offset.getZ());
            int newColor = ColorABGR.mul((int)(colors != null ? colors[srcIndex] : -1), (float)light.br[srcIndex]);
            int alpha = wallHack.opacity.get();
            int blue = ColorABGR.unpackBlue((int)newColor);
            int green = ColorABGR.unpackGreen((int)newColor);
            int red = ColorABGR.unpackRed((int)newColor);
            copy.setColor(dstIndex, ColorABGR.pack((int)red, (int)green, (int)blue, (int)alpha));
            copy.setTexU(dstIndex, src.getTexU(srcIndex));
            copy.setTexV(dstIndex, src.getTexV(srcIndex));
            copy.setLight(dstIndex, light.lm[srcIndex]);
            copy.setNormal(dstIndex, norm);
            copy.setSprite(src.getSprite());
        }
        consumer.get(facing).write((ModelQuadViewMutable)copy);
    }
}

