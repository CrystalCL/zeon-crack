/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.platform.GlStateManager.DstFactor
 *  com.mojang.blaze3d.platform.GlStateManager.SrcFactor
 *  org.lwjgl.opengl.GL11
 */
package minegame159.meteorclient.rendering.gl;

import com.mojang.blaze3d.systems.RenderSystem;
import minegame159.meteorclient.rendering.gl.Mesh;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;

public class PostProcessRenderer {
    private static /* synthetic */ Mesh mesh;

    public static void init() {
        float[] lIllIIlIIlIllIl = new float[]{-1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f};
        int[] lIllIIlIIlIllII = new int[]{0, 1, 2, 2, 3, 0};
        mesh = new Mesh(lIllIIlIIlIllIl, lIllIIlIIlIllII, 2);
    }

    public static void end() {
        mesh.unbind();
        GL11.glPopMatrix();
        GL11.glMatrixMode((int)5888);
        GL11.glPopMatrix();
        RenderSystem.enableDepthTest();
    }

    public static void renderMesh() {
        mesh.renderMesh();
    }

    public static void render() {
        PostProcessRenderer.begin();
        PostProcessRenderer.renderMesh();
        PostProcessRenderer.end();
    }

    public static void begin() {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((SrcFactor)SrcFactor.SRC_ALPHA, (DstFactor)DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.disableLighting();
        RenderSystem.disableCull();
        RenderSystem.disableAlphaTest();
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        mesh.bind();
    }

    public PostProcessRenderer() {
        PostProcessRenderer lIllIIlIIllIIII;
    }
}

