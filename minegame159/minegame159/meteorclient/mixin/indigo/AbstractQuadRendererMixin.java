/*
 * Decompiled with CFR 0.151.
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
    public static void bufferQuad(VertexConsumer VertexConsumer2, MutableQuadViewImpl mutableQuadViewImpl, Matrix4f Matrix4f2, int n, Matrix3f Matrix3f2, Vec3f Vec3f2) {
    }

    @Shadow
    protected abstract Matrix4f matrix();

    @Shadow
    protected abstract int overlay();

    @Shadow
    protected abstract Matrix3f normalMatrix();

    @Inject(method={"bufferQuad(Lnet/fabricmc/fabric/impl/client/indigo/renderer/mesh/MutableQuadViewImpl;Lnet/minecraft/RenderLayer;)V"}, at={@At(value="HEAD")}, cancellable=true, remap=false)
    private void onBufferQuad(MutableQuadViewImpl mutableQuadViewImpl, RenderLayer RenderLayer2, CallbackInfo callbackInfo) {
        WallHack wallHack = Modules.get().get(WallHack.class);
        if (wallHack.isActive()) {
            if (wallHack.blocks.get().contains(this.blockInfo.blockState.getBlock())) {
                AbstractQuadRendererMixin.whBufferQuad(this.bufferFunc.apply(RenderLayer2), mutableQuadViewImpl, this.matrix(), this.overlay(), this.normalMatrix(), this.normalVec, wallHack);
            } else {
                AbstractQuadRendererMixin.bufferQuad(this.bufferFunc.apply(RenderLayer2), mutableQuadViewImpl, this.matrix(), this.overlay(), this.normalMatrix(), this.normalVec);
            }
        } else {
            AbstractQuadRendererMixin.bufferQuad(this.bufferFunc.apply(RenderLayer2), mutableQuadViewImpl, this.matrix(), this.overlay(), this.normalMatrix(), this.normalVec);
        }
        callbackInfo.cancel();
    }

    private static void whBufferQuad(VertexConsumer VertexConsumer2, MutableQuadViewImpl mutableQuadViewImpl, Matrix4f Matrix4f2, int n, Matrix3f Matrix3f2, Vec3f Vec3f2, WallHack wallHack) {
        boolean bl = mutableQuadViewImpl.hasVertexNormals();
        if (bl) {
            mutableQuadViewImpl.populateMissingNormals();
        } else {
            Vec3f Vec3f3 = mutableQuadViewImpl.faceNormal();
            Vec3f2.set(Vec3f3.getX(), Vec3f3.getY(), Vec3f3.getZ());
            Vec3f2.transform(Matrix3f2);
        }
        for (int i = 0; i < 4; ++i) {
            VertexConsumer2.vertex(Matrix4f2, mutableQuadViewImpl.x(i), mutableQuadViewImpl.y(i), mutableQuadViewImpl.z(i));
            int n2 = mutableQuadViewImpl.spriteColor(i, 0);
            VertexConsumer2.color(n2 & 0xFF, n2 >> 8 & 0xFF, n2 >> 16 & 0xFF, wallHack.opacity.get().intValue());
            VertexConsumer2.texture(mutableQuadViewImpl.spriteU(i, 0), mutableQuadViewImpl.spriteV(i, 0));
            VertexConsumer2.overlay(n);
            VertexConsumer2.light(mutableQuadViewImpl.lightmap(i));
            if (bl) {
                Vec3f2.set(mutableQuadViewImpl.normalX(i), mutableQuadViewImpl.normalY(i), mutableQuadViewImpl.normalZ(i));
                Vec3f2.transform(Matrix3f2);
            }
            VertexConsumer2.normal(Vec3f2.getX(), Vec3f2.getY(), Vec3f2.getZ());
            VertexConsumer2.next();
        }
    }
}

