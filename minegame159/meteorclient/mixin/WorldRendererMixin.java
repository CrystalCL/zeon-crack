/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.util.math.Matrix4f
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.gl.Framebuffer
 *  net.minecraft.client.render.BufferBuilder
 *  net.minecraft.client.render.Tessellator
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.render.BlockBreakingInfo
 *  net.minecraft.client.render.Camera
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.Frustum
 *  net.minecraft.client.render.GameRenderer
 *  net.minecraft.client.render.WorldRenderer
 *  net.minecraft.client.render.LightmapTextureManager
 *  net.minecraft.client.render.entity.EntityRenderDispatcher
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyArg
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

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
    private void onLoadEntityOutlineShader(CallbackInfo info) {
        Outlines.load();
    }

    @Inject(method={"checkEmpty"}, at={@At(value="HEAD")}, cancellable=true)
    private void onCheckEmpty(MatrixStack matrixStack, CallbackInfo info) {
        info.cancel();
    }

    @Inject(method={"renderWeather"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderWeather(LightmapTextureManager manager, float f, double d, double e, double g, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noWeather()) {
            info.cancel();
        }
    }

    @Inject(method={"drawBlockOutline"}, at={@At(value="HEAD")}, cancellable=true)
    private void onDrawHighlightedBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo info) {
        if (Modules.get().isActive(BlockSelection.class)) {
            info.cancel();
        }
    }

    @ModifyArg(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZIZ)V"), index=4)
    private boolean renderSetupTerrainModifyArg(boolean spectator) {
        return Modules.get().isActive(Freecam.class) || spectator;
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRenderTail(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo info) {
        Blur.render();
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void onRenderHead(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo info) {
        Outlines.beginRender();
    }

    @Inject(method={"renderEntity"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo info) {
        if (vertexConsumers == Outlines.vertexConsumerProvider) {
            return;
        }
        ESP esp = Modules.get().get(ESP.class);
        if (!esp.isActive() || !esp.isOutline()) {
            return;
        }
        Color color = esp.getOutlineColor(entity);
        if (color != null) {
            Framebuffer fbo = this.entityOutlinesFramebuffer;
            this.entityOutlinesFramebuffer = Outlines.outlinesFbo;
            Outlines.vertexConsumerProvider.setColor(color.r, color.g, color.b, color.a);
            this.renderEntity(entity, cameraX, cameraY, cameraZ, tickDelta, matrices, (VertexConsumerProvider)Outlines.vertexConsumerProvider);
            this.entityOutlinesFramebuffer = fbo;
        }
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/OutlineVertexConsumerProvider;draw()V")})
    private void onRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo info) {
        Outlines.endRender(tickDelta);
    }

    @Inject(method={"drawEntityOutlinesFramebuffer"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gl/Framebuffer;draw(IIZ)V")})
    private void onDrawEntityOutlinesFramebuffer(CallbackInfo info) {
        Outlines.renderFbo();
    }

    @Inject(method={"onResized"}, at={@At(value="HEAD")})
    private void onResized(int i, int j, CallbackInfo info) {
        Outlines.onResized(i, j);
    }

    @Inject(method={"setBlockBreakingInfo"}, at={@At(value="HEAD")}, cancellable=true)
    private void onBlockBreakingInfo(int entityId, BlockPos pos, int stage, CallbackInfo ci) {
        BreakIndicators bi = Modules.get().get(BreakIndicators.class);
        if (!bi.isActive()) {
            return;
        }
        if (!bi.multiple.get().booleanValue() && entityId != this.client.player.getEntityId()) {
            return;
        }
        if (0 <= stage && stage <= 8) {
            BlockBreakingInfo info = new BlockBreakingInfo(entityId, pos);
            info.setStage(stage);
            bi.blocks.put(entityId, info);
            if (bi.hideVanillaIndicators.get().booleanValue()) {
                ci.cancel();
            }
        } else {
            bi.blocks.remove(entityId);
        }
    }

    @Inject(method={"removeBlockBreakingInfo"}, at={@At(value="TAIL")})
    private void onBlockBreakingInfoRemoval(BlockBreakingInfo info, CallbackInfo ci) {
        BreakIndicators bi = Modules.get().get(BreakIndicators.class);
        if (!bi.isActive()) {
            return;
        }
        bi.blocks.values().removeIf(((BlockBreakingInfo)info)::equals);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/EntityRenderDispatcher;shouldRender(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/Frustum;DDD)Z"))
    private <E extends Entity> boolean shouldRenderRedirect(EntityRenderDispatcher entityRenderDispatcher, E entity, Frustum frustum, double x, double y, double z) {
        return Modules.get().isActive(Chams.class) || entityRenderDispatcher.shouldRender(entity, frustum, x, y, z);
    }

    @Inject(method={"renderEndSky"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/Tessellator;draw()V")})
    private void onRenderEndSkyDraw(MatrixStack matrices, CallbackInfo info) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.enderCustomSkyColor.get().booleanValue()) {
            Color customEndSkyColor = ambience.endSkyColor.get();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            Matrix4f matrix4f = matrices.peek().getModel();
            bufferBuilder.clear();
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0f, 0.0f).color(customEndSkyColor.r, customEndSkyColor.g, customEndSkyColor.b, 255).next();
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0f, 16.0f).color(customEndSkyColor.r, customEndSkyColor.g, customEndSkyColor.b, 255).next();
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(16.0f, 16.0f).color(customEndSkyColor.r, customEndSkyColor.g, customEndSkyColor.b, 255).next();
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(16.0f, 0.0f).color(customEndSkyColor.r, customEndSkyColor.g, customEndSkyColor.b, 255).next();
        }
    }
}

