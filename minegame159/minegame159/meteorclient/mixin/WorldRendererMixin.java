/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.Collection;
import java.util.Objects;
import javax.annotation.Nullable;
import minegame159.meteorclient.rendering.Blur;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.BlockSelection;
import minegame159.meteorclient.systems.modules.render.BreakIndicators;
import minegame159.meteorclient.systems.modules.render.Chams;
import minegame159.meteorclient.systems.modules.render.ESP;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.systems.modules.render.NoRender;
import minegame159.meteorclient.systems.modules.world.Ambience;
import minegame159.meteorclient.utils.render.Outlines;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={WorldRenderer.class})
public abstract class WorldRendererMixin {
    @Shadow
    @Nullable
    private Framebuffer entityOutlinesFramebuffer;
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract void renderEntity(Entity var1, double var2, double var4, double var6, float var8, MatrixStack var9, VertexConsumerProvider var10);

    @Inject(method={"loadEntityOutlineShader"}, at={@At(value="TAIL")})
    private void onLoadEntityOutlineShader(CallbackInfo callbackInfo) {
        Outlines.load();
    }

    @Inject(method={"checkEmpty"}, at={@At(value="HEAD")}, cancellable=true)
    private void onCheckEmpty(MatrixStack MatrixStack2, CallbackInfo callbackInfo) {
        callbackInfo.cancel();
    }

    @Inject(method={"renderWeather"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderWeather(LightmapTextureManager LightmapTextureManager2, float f, double d, double d2, double d3, CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noWeather()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"drawBlockOutline"}, at={@At(value="HEAD")}, cancellable=true)
    private void onDrawHighlightedBlockOutline(MatrixStack MatrixStack2, VertexConsumer VertexConsumer2, Entity Entity2, double d, double d2, double d3, BlockPos BlockPos2, BlockState BlockState2, CallbackInfo callbackInfo) {
        if (Modules.get().isActive(BlockSelection.class)) {
            callbackInfo.cancel();
        }
    }

    @ModifyArg(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZIZ)V"), index=4)
    private boolean renderSetupTerrainModifyArg(boolean bl) {
        return Modules.get().isActive(Freecam.class) || bl;
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRenderTail(MatrixStack MatrixStack2, float f, long l, boolean bl, Camera Camera2, GameRenderer GameRenderer2, LightmapTextureManager LightmapTextureManager2, Matrix4f Matrix4f2, CallbackInfo callbackInfo) {
        Blur.render();
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void onRenderHead(MatrixStack MatrixStack2, float f, long l, boolean bl, Camera Camera2, GameRenderer GameRenderer2, LightmapTextureManager LightmapTextureManager2, Matrix4f Matrix4f2, CallbackInfo callbackInfo) {
        Outlines.beginRender();
    }

    @Inject(method={"renderEntity"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderEntity(Entity Entity2, double d, double d2, double d3, float f, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, CallbackInfo callbackInfo) {
        if (VertexConsumerProvider2 == Outlines.vertexConsumerProvider) {
            return;
        }
        ESP eSP = Modules.get().get(ESP.class);
        if (!eSP.isActive() || !eSP.isOutline()) {
            return;
        }
        Color color = eSP.getOutlineColor(Entity2);
        if (color != null) {
            Framebuffer Framebuffer3 = this.entityOutlinesFramebuffer;
            this.entityOutlinesFramebuffer = Outlines.outlinesFbo;
            Outlines.vertexConsumerProvider.setColor(color.r, color.g, color.b, color.a);
            this.renderEntity(Entity2, d, d2, d3, f, MatrixStack2, (VertexConsumerProvider)Outlines.vertexConsumerProvider);
            this.entityOutlinesFramebuffer = Framebuffer3;
        }
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/OutlineVertexConsumerProvider;draw()V")})
    private void onRender(MatrixStack MatrixStack2, float f, long l, boolean bl, Camera Camera2, GameRenderer GameRenderer2, LightmapTextureManager LightmapTextureManager2, Matrix4f Matrix4f2, CallbackInfo callbackInfo) {
        Outlines.endRender(f);
    }

    @Inject(method={"drawEntityOutlinesFramebuffer"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gl/Framebuffer;draw(IIZ)V")})
    private void onDrawEntityOutlinesFramebuffer(CallbackInfo callbackInfo) {
        Outlines.renderFbo();
    }

    @Inject(method={"onResized"}, at={@At(value="HEAD")})
    private void onResized(int n, int n2, CallbackInfo callbackInfo) {
        Outlines.onResized(n, n2);
    }

    @Inject(method={"setBlockBreakingInfo"}, at={@At(value="HEAD")}, cancellable=true)
    private void onBlockBreakingInfo(int n, BlockPos BlockPos2, int n2, CallbackInfo callbackInfo) {
        BreakIndicators breakIndicators = Modules.get().get(BreakIndicators.class);
        if (!breakIndicators.isActive()) {
            return;
        }
        if (!breakIndicators.multiple.get().booleanValue() && n != this.client.player.getEntityId()) {
            return;
        }
        if (0 <= n2 && n2 <= 8) {
            BlockBreakingInfo BlockBreakingInfo2 = new BlockBreakingInfo(n, BlockPos2);
            BlockBreakingInfo2.setStage(n2);
            breakIndicators.blocks.put(n, BlockBreakingInfo2);
            if (breakIndicators.hideVanillaIndicators.get().booleanValue()) {
                callbackInfo.cancel();
            }
        } else {
            breakIndicators.blocks.remove(n);
        }
    }

    @Inject(method={"removeBlockBreakingInfo"}, at={@At(value="TAIL")})
    private void onBlockBreakingInfoRemoval(BlockBreakingInfo BlockBreakingInfo2, CallbackInfo callbackInfo) {
        BreakIndicators breakIndicators = Modules.get().get(BreakIndicators.class);
        if (!breakIndicators.isActive()) {
            return;
        }
        Collection<BlockBreakingInfo> collection = breakIndicators.blocks.values();
        Objects.requireNonNull(BlockBreakingInfo2);
        collection.removeIf(arg_0 -> ((BlockBreakingInfo)BlockBreakingInfo2).equals(arg_0));
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/EntityRenderDispatcher;shouldRender(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/Frustum;DDD)Z"))
    private <E extends Entity> boolean shouldRenderRedirect(EntityRenderDispatcher EntityRenderDispatcher2, E e, Frustum Frustum2, double d, double d2, double d3) {
        return Modules.get().isActive(Chams.class) || EntityRenderDispatcher2.shouldRender(e, Frustum2, d, d2, d3);
    }

    @Inject(method={"renderEndSky"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/Tessellator;draw()V")})
    private void onRenderEndSkyDraw(MatrixStack MatrixStack2, CallbackInfo callbackInfo) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.enderCustomSkyColor.get().booleanValue()) {
            Color color = ambience.endSkyColor.get();
            Tessellator Tessellator2 = Tessellator.getInstance();
            BufferBuilder BufferBuilder2 = Tessellator2.getBuffer();
            Matrix4f Matrix4f2 = MatrixStack2.peek().getModel();
            BufferBuilder2.clear();
            BufferBuilder2.vertex(Matrix4f2, -100.0f, -100.0f, -100.0f).texture(0.0f, 0.0f).color(color.r, color.g, color.b, 255).next();
            BufferBuilder2.vertex(Matrix4f2, -100.0f, -100.0f, 100.0f).texture(0.0f, 16.0f).color(color.r, color.g, color.b, 255).next();
            BufferBuilder2.vertex(Matrix4f2, 100.0f, -100.0f, 100.0f).texture(16.0f, 16.0f).color(color.r, color.g, color.b, 255).next();
            BufferBuilder2.vertex(Matrix4f2, 100.0f, -100.0f, -100.0f).texture(16.0f, 0.0f).color(color.r, color.g, color.b, 255).next();
        }
    }
}

