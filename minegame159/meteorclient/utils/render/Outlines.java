/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gl.Framebuffer
 *  net.minecraft.client.gl.ShaderEffect
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.render.OutlineVertexConsumerProvider
 *  net.minecraft.client.render.WorldRenderer
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
    public static /* synthetic */ boolean loadingOutlineShader;
    private static /* synthetic */ ShaderEffect outlinesShader;
    public static /* synthetic */ boolean renderingOutlines;
    public static /* synthetic */ OutlineVertexConsumerProvider vertexConsumerProvider;
    public static /* synthetic */ Framebuffer outlinesFbo;

    public static void endRender(float llllllllllllllllllllllIIlIIIlIlI) {
        WorldRenderer llllllllllllllllllllllIIlIIIllIl = MinecraftClient.getInstance().worldRenderer;
        WorldRendererAccessor llllllllllllllllllllllIIlIIIllII = (WorldRendererAccessor)llllllllllllllllllllllIIlIIIllIl;
        Framebuffer llllllllllllllllllllllIIlIIIlIll = llllllllllllllllllllllIIlIIIllIl.getEntityOutlinesFramebuffer();
        llllllllllllllllllllllIIlIIIllII.setEntityOutlinesFramebuffer(outlinesFbo);
        vertexConsumerProvider.draw();
        llllllllllllllllllllllIIlIIIllII.setEntityOutlinesFramebuffer(llllllllllllllllllllllIIlIIIlIll);
        outlinesShader.render(llllllllllllllllllllllIIlIIIlIlI);
        MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
    }

    public static void load() {
        try {
            MinecraftClient llllllllllllllllllllllIIlIIlIlIl = MinecraftClient.getInstance();
            if (outlinesShader != null) {
                outlinesShader.close();
            }
            loadingOutlineShader = true;
            outlinesShader = new ShaderEffect(llllllllllllllllllllllIIlIIlIlIl.getTextureManager(), llllllllllllllllllllllIIlIIlIlIl.getResourceManager(), llllllllllllllllllllllIIlIIlIlIl.getFramebuffer(), new Identifier("meteor-client", "shaders/post/my_entity_outline.json"));
            outlinesShader.setupDimensions(llllllllllllllllllllllIIlIIlIlIl.getWindow().getFramebufferWidth(), llllllllllllllllllllllIIlIIlIlIl.getWindow().getFramebufferHeight());
            outlinesFbo = outlinesShader.getSecondaryTarget("final");
            vertexConsumerProvider = new OutlineVertexConsumerProvider(llllllllllllllllllllllIIlIIlIlIl.getBufferBuilders().getEntityVertexConsumers());
            loadingOutlineShader = false;
        }
        catch (IOException llllllllllllllllllllllIIlIIlIlII) {
            llllllllllllllllllllllIIlIIlIlII.printStackTrace();
        }
    }

    public static void beginRender() {
        outlinesFbo.clear(MinecraftClient.IS_SYSTEM_MAC);
        MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
    }

    public static void renderFbo() {
        MinecraftClient llllllllllllllllllllllIIlIIIIlIl = MinecraftClient.getInstance();
        outlinesFbo.draw(llllllllllllllllllllllIIlIIIIlIl.getWindow().getFramebufferWidth(), llllllllllllllllllllllIIlIIIIlIl.getWindow().getFramebufferHeight(), false);
    }

    public Outlines() {
        Outlines llllllllllllllllllllllIIlIIllIII;
    }

    public static void onResized(int llllllllllllllllllllllIIIlllllll, int llllllllllllllllllllllIIlIIIIIII) {
        if (outlinesShader != null) {
            outlinesShader.setupDimensions(llllllllllllllllllllllIIIlllllll, llllllllllllllllllllllIIlIIIIIII);
        }
    }
}

