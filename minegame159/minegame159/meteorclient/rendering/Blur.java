/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.rendering;

import minegame159.meteorclient.gui.GuiThemes;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.rendering.gl.PostProcessRenderer;
import minegame159.meteorclient.rendering.gl.Shader;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.GlStateManager;

public class Blur {
    private static Shader shaderDown;
    private static Shader shaderUp;
    private static final int OFFSET;
    private static final int ITERATIONS;
    private static final MinecraftClient mc;

    public static void render() {
        int n;
        if (!GuiThemes.get().blur() || !(Blur.mc.currentScreen instanceof WidgetScreen)) {
            return;
        }
        Framebuffer Framebuffer2 = mc.getFramebuffer();
        GlStateManager.activeTexture((int)33984);
        GlStateManager.bindTexture((int)Framebuffer2.getColorAttachment());
        PostProcessRenderer.begin();
        shaderDown.bind();
        shaderDown.set("u_Texture", 0);
        shaderDown.set("u_Size", Framebuffer2.textureWidth, Framebuffer2.textureHeight);
        shaderDown.set("u_Offset", 4.0f, 4.0f);
        shaderDown.set("u_HalfPixel", 0.5f / (float)Framebuffer2.textureWidth, 0.5f / (float)Framebuffer2.textureHeight);
        for (n = 0; n < 4; ++n) {
            PostProcessRenderer.renderMesh();
            if (null == null) continue;
            return;
        }
        shaderUp.bind();
        shaderUp.set("u_Texture", 0);
        shaderUp.set("u_Size", Framebuffer2.textureWidth, Framebuffer2.textureHeight);
        shaderUp.set("u_Offset", 4.0f, 4.0f);
        shaderUp.set("u_HalfPixel", 0.5f / (float)Framebuffer2.textureWidth, 0.5f / (float)Framebuffer2.textureHeight);
        shaderUp.set("u_Alpha", 1.0f);
        for (n = 0; n < 4; ++n) {
            if (n == 3) {
                shaderUp.set("u_Alpha", (float)((WidgetScreen)Blur.mc.currentScreen).animProgress);
            }
            PostProcessRenderer.renderMesh();
            if (2 > 0) continue;
            return;
        }
        shaderUp.unbind();
        GlStateManager.bindTexture((int)0);
        PostProcessRenderer.end();
    }

    public static void init() {
        shaderDown = new Shader("shaders/simple.vert", "shaders/kawase_down.frag");
        shaderUp = new Shader("shaders/simple.vert", "shaders/kawase_up.frag");
    }

    static {
        OFFSET = 4;
        ITERATIONS = 4;
        mc = MinecraftClient.getInstance();
    }
}

