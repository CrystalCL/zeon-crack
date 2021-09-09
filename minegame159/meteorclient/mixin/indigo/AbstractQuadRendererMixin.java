/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl
 *  net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractQuadRenderer
 *  net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo
 *  net.minecraft.util.math.Matrix4f
 *  net.minecraft.util.math.Vec3f
 *  net.minecraft.client.render.RenderLayer
 *  net.minecraft.util.math.Matrix3f
 *  net.minecraft.client.render.VertexConsumer
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin.indigo;

import java.util.function.Function;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.WallHack;
import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractQuadRenderer;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={AbstractQuadRenderer.class}, remap=false)
public abstract class AbstractQuadRendererMixin {
    @Final
    @Shadow
    protected BlockRenderInfo blockInfo;
    @Final
    @Shadow
    protected Function<RenderLayer, VertexConsumer> bufferFunc;
    @Final
    @Shadow
    protected Vec3f normalVec;

    @Shadow
    public static void bufferQuad(VertexConsumer buff, MutableQuadViewImpl quad, Matrix4f matrix, int overlay, Matrix3f normalMatrix, Vec3f normalVec) {
    }

    @Shadow
    protected abstract Matrix4f matrix();

    @Shadow
    protected abstract int overlay();

    @Shadow
    protected abstract Matrix3f normalMatrix();

    @Inject(method={"bufferQuad(Lnet/fabricmc/fabric/impl/client/indigo/renderer/mesh/MutableQuadViewImpl;Lnet/minecraft/RenderLayer;)V"}, at={@At(value="HEAD")}, cancellable=true, remap=false)
    private void onBufferQuad(MutableQuadViewImpl quad, RenderLayer renderLayer, CallbackInfo ci) {
        WallHack wallHack = Modules.get().get(WallHack.class);
        if (wallHack.isActive()) {
            if (wallHack.blocks.get().contains((Object)this.blockInfo.blockState.getBlock())) {
                AbstractQuadRendererMixin.whBufferQuad(this.bufferFunc.apply(renderLayer), quad, this.matrix(), this.overlay(), this.normalMatrix(), this.normalVec, wallHack);
            } else {
                AbstractQuadRendererMixin.bufferQuad(this.bufferFunc.apply(renderLayer), quad, this.matrix(), this.overlay(), this.normalMatrix(), this.normalVec);
            }
        } else {
            AbstractQuadRendererMixin.bufferQuad(this.bufferFunc.apply(renderLayer), quad, this.matrix(), this.overlay(), this.normalMatrix(), this.normalVec);
        }
        ci.cancel();
    }

    private static void whBufferQuad(VertexConsumer buff, MutableQuadViewImpl quad, Matrix4f matrix, int overlay, Matrix3f normalMatrix, Vec3f normalVec, WallHack wallHack) {
        boolean useNormals = quad.hasVertexNormals();
        if (useNormals) {
            quad.populateMissingNormals();
        } else {
            Vec3f faceNormal = quad.faceNormal();
            normalVec.set(faceNormal.getX(), faceNormal.getY(), faceNormal.getZ());
            normalVec.transform(normalMatrix);
        }
        for (int i = 0; i < 4; ++i) {
            buff.vertex(matrix, quad.x(i), quad.y(i), quad.z(i));
            int color = quad.spriteColor(i, 0);
            buff.color(color & 0xFF, color >> 8 & 0xFF, color >> 16 & 0xFF, wallHack.opacity.get().intValue());
            buff.texture(quad.spriteU(i, 0), quad.spriteV(i, 0));
            buff.overlay(overlay);
            buff.light(quad.lightmap(i));
            if (useNormals) {
                normalVec.set(quad.normalX(i), quad.normalY(i), quad.normalZ(i));
                normalVec.transform(normalMatrix);
            }
            buff.normal(normalVec.getX(), normalVec.getY(), normalVec.getZ());
            buff.next();
        }
    }
}

