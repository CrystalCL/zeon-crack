/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render;

import java.io.IOException;
import minegame159.meteorclient.mixin.WorldRendererAccessor;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;

public class Outlines {
    public static OutlineVertexConsumerProvider vertexConsumerProvider;
    public static boolean loadingOutlineShader;
    public static Framebuffer outlinesFbo;
    public static boolean renderingOutlines;
    private static ShaderEffect outlinesShader;

    public static void beginRender() {
        outlinesFbo.clear(MinecraftClient.IS_SYSTEM_MAC);
        MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
    }

    public static void onResized(int n, int n2) {
        if (outlinesShader != null) {
            outlinesShader.setupDimensions(n, n2);
        }
    }

    public static void endRender(float f) {
        WorldRenderer WorldRenderer2 = MinecraftClient.getInstance().worldRenderer;
        WorldRendererAccessor worldRendererAccessor = (WorldRendererAccessor)WorldRenderer2;
        Framebuffer Framebuffer3 = WorldRenderer2.getEntityOutlinesFramebuffer();
        worldRendererAccessor.setEntityOutlinesFramebuffer(outlinesFbo);
        vertexConsumerProvider.draw();
        worldRendererAccessor.setEntityOutlinesFramebuffer(Framebuffer3);
        outlinesShader.render(f);
        MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
    }

    public static void renderFbo() {
        MinecraftClient MinecraftClient2 = MinecraftClient.getInstance();
        outlinesFbo.draw(MinecraftClient2.getWindow().getFramebufferWidth(), MinecraftClient2.getWindow().getFramebufferHeight(), false);
    }

    public static void load() {
        try {
            MinecraftClient MinecraftClient2 = MinecraftClient.getInstance();
            if (outlinesShader != null) {
                outlinesShader.close();
            }
            loadingOutlineShader = true;
            outlinesShader = new ShaderEffect(MinecraftClient2.getTextureManager(), MinecraftClient2.getResourceManager(), MinecraftClient2.getFramebuffer(), new Identifier("meteor-client", "shaders/post/my_entity_outline.json"));
            outlinesShader.setupDimensions(MinecraftClient2.getWindow().getFramebufferWidth(), MinecraftClient2.getWindow().getFramebufferHeight());
            outlinesFbo = outlinesShader.getSecondaryTarget("final");
            vertexConsumerProvider = new OutlineVertexConsumerProvider(MinecraftClient2.getBufferBuilders().getEntityVertexConsumers());
            loadingOutlineShader = false;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

