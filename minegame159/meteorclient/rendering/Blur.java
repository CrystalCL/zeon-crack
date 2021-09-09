/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gl.Framebuffer
 *  net.minecraft.client.MinecraftClient
 *  com.mojang.blaze3d.platform.GlStateManager
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
    private static final /* synthetic */ int ITERATIONS;
    private static final /* synthetic */ MinecraftClient mc;
    private static /* synthetic */ Shader shaderUp;
    private static /* synthetic */ Shader shaderDown;
    private static final /* synthetic */ int OFFSET;

    public static void render() {
        if (!GuiThemes.get().blur() || !(Blur.mc.currentScreen instanceof WidgetScreen)) {
            return;
        }
        Framebuffer lllllllllllllllllIIIllllIllIIllI = mc.getFramebuffer();
        GlStateManager.activeTexture((int)33984);
        GlStateManager.bindTexture((int)lllllllllllllllllIIIllllIllIIllI.getColorAttachment());
        PostProcessRenderer.begin();
        shaderDown.bind();
        shaderDown.set("u_Texture", 0);
        shaderDown.set("u_Size", lllllllllllllllllIIIllllIllIIllI.textureWidth, lllllllllllllllllIIIllllIllIIllI.textureHeight);
        shaderDown.set("u_Offset", 4.0f, 4.0f);
        shaderDown.set("u_HalfPixel", 0.5f / (float)lllllllllllllllllIIIllllIllIIllI.textureWidth, 0.5f / (float)lllllllllllllllllIIIllllIllIIllI.textureHeight);
        for (int lllllllllllllllllIIIllllIllIlIII = 0; lllllllllllllllllIIIllllIllIlIII < 4; ++lllllllllllllllllIIIllllIllIlIII) {
            PostProcessRenderer.renderMesh();
        }
        shaderUp.bind();
        shaderUp.set("u_Texture", 0);
        shaderUp.set("u_Size", lllllllllllllllllIIIllllIllIIllI.textureWidth, lllllllllllllllllIIIllllIllIIllI.textureHeight);
        shaderUp.set("u_Offset", 4.0f, 4.0f);
        shaderUp.set("u_HalfPixel", 0.5f / (float)lllllllllllllllllIIIllllIllIIllI.textureWidth, 0.5f / (float)lllllllllllllllllIIIllllIllIIllI.textureHeight);
        shaderUp.set("u_Alpha", 1.0f);
        for (int lllllllllllllllllIIIllllIllIIlll = 0; lllllllllllllllllIIIllllIllIIlll < 4; ++lllllllllllllllllIIIllllIllIIlll) {
            if (lllllllllllllllllIIIllllIllIIlll == 3) {
                shaderUp.set("u_Alpha", (float)((WidgetScreen)Blur.mc.currentScreen).animProgress);
            }
            PostProcessRenderer.renderMesh();
        }
        shaderUp.unbind();
        GlStateManager.bindTexture((int)0);
        PostProcessRenderer.end();
    }

    public Blur() {
        Blur lllllllllllllllllIIIllllIllIllII;
    }

    static {
        OFFSET = 4;
        ITERATIONS = 4;
        mc = MinecraftClient.getInstance();
    }

    public static void init() {
        shaderDown = new Shader("shaders/simple.vert", "shaders/kawase_down.frag");
        shaderUp = new Shader("shaders/simple.vert", "shaders/kawase_up.frag");
    }
}

