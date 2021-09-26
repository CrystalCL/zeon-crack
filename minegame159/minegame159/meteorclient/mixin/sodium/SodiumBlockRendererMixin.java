/*
 * Decompiled with CFR 0.151.
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
    private void onRenderQuad(BlockRenderView BlockRenderView2, BlockState BlockState2, BlockPos BlockPos2, ModelQuadSinkDelegate modelQuadSinkDelegate, Vec3d Vec3d2, BlockColorProvider BlockColorProvider2, BakedQuad BakedQuad2, QuadLightData quadLightData, ModelQuadFacing modelQuadFacing, CallbackInfo callbackInfo) {
        WallHack wallHack = Modules.get().get(WallHack.class);
        if (wallHack.isActive() && wallHack.blocks.get().contains(BlockState2.getBlock())) {
            this.whRenderQuad(BlockRenderView2, BlockState2, BlockPos2, modelQuadSinkDelegate, Vec3d2, BlockColorProvider2, BakedQuad2, quadLightData, modelQuadFacing, wallHack);
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderModel"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderModel(BlockRenderView BlockRenderView2, BlockState BlockState2, BlockPos BlockPos2, BakedModel BakedModel2, ModelQuadSinkDelegate modelQuadSinkDelegate, boolean bl, long l, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        Xray xray = Modules.get().get(Xray.class);
        if (xray.isActive() && xray.isBlocked(BlockState2.getBlock())) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }

    private void whRenderQuad(BlockRenderView BlockRenderView2, BlockState BlockState2, BlockPos BlockPos2, ModelQuadSinkDelegate modelQuadSinkDelegate, Vec3d Vec3d2, BlockColorProvider BlockColorProvider2, BakedQuad BakedQuad2, QuadLightData quadLightData, ModelQuadFacing modelQuadFacing, WallHack wallHack) {
        ModelQuadView modelQuadView = (ModelQuadView)BakedQuad2;
        ModelQuadOrientation modelQuadOrientation = ModelQuadOrientation.orient((float[])quadLightData.br);
        ModelQuad modelQuad = this.cachedQuad;
        int n = ModelQuadUtil.getFacingNormal((Direction)BakedQuad2.getFace());
        int[] nArray = null;
        if (BakedQuad2.hasColor()) {
            nArray = this.biomeColorBlender.getColors(BlockColorProvider2, BlockRenderView2, BlockState2, BlockPos2, modelQuadView);
        }
        for (int i = 0; i < 4; ++i) {
            int n2 = modelQuadOrientation.getVertexIndex(i);
            modelQuad.setX(i, modelQuadView.getX(n2) + (float)Vec3d2.getX());
            modelQuad.setY(i, modelQuadView.getY(n2) + (float)Vec3d2.getY());
            modelQuad.setZ(i, modelQuadView.getZ(n2) + (float)Vec3d2.getZ());
            int n3 = ColorABGR.mul((int)(nArray != null ? nArray[n2] : -1), (float)quadLightData.br[n2]);
            int n4 = wallHack.opacity.get();
            int n5 = ColorABGR.unpackBlue((int)n3);
            int n6 = ColorABGR.unpackGreen((int)n3);
            int n7 = ColorABGR.unpackRed((int)n3);
            modelQuad.setColor(i, ColorABGR.pack((int)n7, (int)n6, (int)n5, (int)n4));
            modelQuad.setTexU(i, modelQuadView.getTexU(n2));
            modelQuad.setTexV(i, modelQuadView.getTexV(n2));
            modelQuad.setLight(i, quadLightData.lm[n2]);
            modelQuad.setNormal(i, n);
            modelQuad.setSprite(modelQuadView.getSprite());
        }
        modelQuadSinkDelegate.get(modelQuadFacing).write((ModelQuadViewMutable)modelQuad);
    }
}

