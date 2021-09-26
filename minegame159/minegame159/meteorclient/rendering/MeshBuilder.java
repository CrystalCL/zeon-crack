/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.gui.renderer.packer.TextureRegion;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Matrices;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.world.Dir;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;

public class MeshBuilder {
    private double offsetY;
    public boolean depthTest = false;
    private double offsetX;
    public boolean texture = false;
    public double alpha = 1.0;
    private double offsetZ;
    private final BufferBuilder buffer;
    private int count;

    public void gradientQuad(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, Color color, Color color2) {
        this.pos(d, d2, d3).color(color).endVertex();
        this.pos(d4, d5, d6).color(color2).endVertex();
        this.pos(d7, d8, d9).color(color2).endVertex();
        this.pos(d, d2, d3).color(color).endVertex();
        this.pos(d7, d8, d9).color(color2).endVertex();
        this.pos(d10, d11, d12).color(color).endVertex();
    }

    public MeshBuilder color(Color color) {
        this.buffer.color((float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, (float)color.a / 255.0f * (float)this.alpha);
        return this;
    }

    public void boxEdges(double d, double d2, double d3, double d4, double d5, double d6, Color color, int n) {
        if (Dir.is(n, (byte)32) && Dir.is(n, (byte)8)) {
            this.line(d, d2, d3, d, d5, d3, color);
        }
        if (Dir.is(n, (byte)32) && Dir.is(n, (byte)16)) {
            this.line(d, d2, d6, d, d5, d6, color);
        }
        if (Dir.is(n, (byte)64) && Dir.is(n, (byte)8)) {
            this.line(d4, d2, d3, d4, d5, d3, color);
        }
        if (Dir.is(n, (byte)64) && Dir.is(n, (byte)16)) {
            this.line(d4, d2, d6, d4, d5, d6, color);
        }
        if (Dir.is(n, (byte)8)) {
            this.line(d, d2, d3, d4, d2, d3, color);
        }
        if (Dir.is(n, (byte)8)) {
            this.line(d, d5, d3, d4, d5, d3, color);
        }
        if (Dir.is(n, (byte)16)) {
            this.line(d, d2, d6, d4, d2, d6, color);
        }
        if (Dir.is(n, (byte)16)) {
            this.line(d, d5, d6, d4, d5, d6, color);
        }
        if (Dir.is(n, (byte)32)) {
            this.line(d, d2, d3, d, d2, d6, color);
        }
        if (Dir.is(n, (byte)32)) {
            this.line(d, d5, d3, d, d5, d6, color);
        }
        if (Dir.is(n, (byte)64)) {
            this.line(d4, d2, d3, d4, d2, d6, color);
        }
        if (Dir.is(n, (byte)64)) {
            this.line(d4, d5, d3, d4, d5, d6, color);
        }
    }

    public void endVertex() {
        this.buffer.next();
        ++this.count;
    }

    public void gradientQuad(double d, double d2, double d3, double d4, Color color, Color color2) {
        this.gradientQuad(d, d2, 0.0, d + d3, d2, 0.0, d + d3, d2 + d4, 0.0, d, d2 + d4, 0.0, color, color2);
    }

    public MeshBuilder color(int n) {
        this.buffer.color((float)Color.toRGBAR(n) / 255.0f, (float)Color.toRGBAG(n) / 255.0f, (float)Color.toRGBAB(n) / 255.0f, (float)Color.toRGBAA(n) / 255.0f * (float)this.alpha);
        return this;
    }

    public void quad(double d, double d2, double d3, double d4, Color color, Color color2, Color color3, Color color4) {
        this.pos(d, d2, 0.0).color(color).endVertex();
        this.pos(d + d3, d2, 0.0).color(color2).endVertex();
        this.pos(d + d3, d2 + d4, 0.0).color(color3).endVertex();
        this.pos(d, d2, 0.0).color(color).endVertex();
        this.pos(d + d3, d2 + d4, 0.0).color(color3).endVertex();
        this.pos(d, d2 + d4, 0.0).color(color4).endVertex();
    }

    public void gradientLine(double d, double d2, double d3, double d4, double d5, double d6, Color color, Color color2) {
        this.pos(d, d2, d3).color(color).endVertex();
        this.pos(d4, d5, d6).color(color2).endVertex();
    }

    public MeshBuilder() {
        this.buffer = new BufferBuilder(0x200000);
    }

    public void gradientVerticalBox(double d, double d2, double d3, double d4, double d5, double d6, Color color, Color color2, boolean bl) {
        this.gradientLine(d, d2, d3, d, d2 + d6, d3, bl ? color2 : color, bl ? color : color2);
        this.gradientLine(d4, d2, d3, d4, d2 + d6, d3, bl ? color2 : color, bl ? color : color2);
        this.gradientLine(d, d2, d5, d, d2 + d6, d5, bl ? color2 : color, bl ? color : color2);
        this.gradientLine(d4, d2, d5, d4, d2 + d6, d5, bl ? color2 : color, bl ? color : color2);
    }

    public void quad(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, Color color) {
        this.pos(d, d2, d3).color(color).endVertex();
        this.pos(d4, d5, d6).color(color).endVertex();
        this.pos(d7, d8, d9).color(color).endVertex();
        this.pos(d, d2, d3).color(color).endVertex();
        this.pos(d7, d8, d9).color(color).endVertex();
        this.pos(d10, d11, d12).color(color).endVertex();
    }

    public void line(double d, double d2, double d3, double d4, double d5, double d6, Color color) {
        this.pos(d, d2, d3).color(color).endVertex();
        this.pos(d4, d5, d6).color(color).endVertex();
    }

    public void quad(double d, double d2, double d3, double d4, Color color) {
        this.quad(d, d2, 0.0, d + d3, d2, 0.0, d + d3, d2 + d4, 0.0, d, d2 + d4, 0.0, color);
    }

    public MeshBuilder pos(double d, double d2, double d3) {
        this.buffer.vertex(d + this.offsetX, d2 + this.offsetY, d3 + this.offsetZ);
        return this;
    }

    public void begin(RenderEvent renderEvent, DrawMode drawMode, VertexFormat VertexFormat2) {
        if (renderEvent != null) {
            this.offsetX = -renderEvent.offsetX;
            this.offsetY = -renderEvent.offsetY;
            this.offsetZ = -renderEvent.offsetZ;
        } else {
            this.offsetX = 0.0;
            this.offsetY = 0.0;
            this.offsetZ = 0.0;
        }
        this.buffer.begin(drawMode.toOpenGl(), VertexFormat2);
        this.count = 0;
    }

    public void end() {
        this.buffer.end();
        GL11.glPushMatrix();
        RenderSystem.multMatrix((Matrix4f)Matrices.getTop());
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
        if (this.depthTest) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.disableAlphaTest();
        if (this.texture) {
            RenderSystem.enableTexture();
        } else {
            RenderSystem.disableTexture();
        }
        RenderSystem.disableLighting();
        RenderSystem.disableCull();
        GL11.glEnable((int)2848);
        RenderSystem.lineWidth((float)1.0f);
        RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.shadeModel((int)7425);
        BufferRenderer.draw((BufferBuilder)this.buffer);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableDepthTest();
        RenderSystem.enableTexture();
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public void boxEdges(double d, double d2, double d3, double d4, Color color) {
        this.boxEdges(d, d2, 0.0, d + d3, d2 + d4, 0.0, color, 0);
    }

    public MeshBuilder texture(double d, double d2) {
        this.buffer.texture((float)d, (float)d2);
        return this;
    }

    public void gradientBoxSides(double d, double d2, double d3, double d4, double d5, double d6, Color color, Color color2, boolean bl) {
        this.gradientQuad(d, d2, d3, d, d2 + d6, d3, d4, d2 + d6, d3, d4, d2, d3, bl ? color2 : color, bl ? color : color2);
        this.gradientQuad(d, d2, d5, d, d2 + d6, d5, d4, d2 + d6, d5, d4, d2, d5, bl ? color2 : color, bl ? color : color2);
        this.gradientQuad(d, d2, d3, d, d2 + d6, d3, d, d2 + d6, d5, d, d2, d5, bl ? color2 : color, bl ? color : color2);
        this.gradientQuad(d4, d2, d3, d4, d2 + d6, d3, d4, d2 + d6, d5, d4, d2, d5, bl ? color2 : color, bl ? color : color2);
    }

    public boolean isBuilding() {
        return this.buffer.isBuilding();
    }

    public MeshBuilder(int n) {
        this.buffer = new BufferBuilder(n);
    }

    public void texQuad(double d, double d2, double d3, double d4, TextureRegion textureRegion, Color color) {
        this.pos(d, d2, 0.0).color(color).texture(textureRegion.x1, textureRegion.y1).endVertex();
        this.pos(d + d3, d2, 0.0).color(color).texture(textureRegion.x2, textureRegion.y1).endVertex();
        this.pos(d + d3, d2 + d4, 0.0).color(color).texture(textureRegion.x2, textureRegion.y2).endVertex();
        this.pos(d, d2, 0.0).color(color).texture(textureRegion.x1, textureRegion.y1).endVertex();
        this.pos(d + d3, d2 + d4, 0.0).color(color).texture(textureRegion.x2, textureRegion.y2).endVertex();
        this.pos(d, d2 + d4, 0.0).color(color).texture(textureRegion.x1, textureRegion.y2).endVertex();
    }

    public void boxSides(double d, double d2, double d3, double d4, double d5, double d6, Color color, int n) {
        if (Dir.is(n, (byte)4)) {
            this.quad(d, d2, d3, d, d2, d6, d4, d2, d6, d4, d2, d3, color);
        }
        if (Dir.is(n, (byte)2)) {
            this.quad(d, d5, d3, d, d5, d6, d4, d5, d6, d4, d5, d3, color);
        }
        if (Dir.is(n, (byte)8)) {
            this.quad(d, d2, d3, d, d5, d3, d4, d5, d3, d4, d2, d3, color);
        }
        if (Dir.is(n, (byte)16)) {
            this.quad(d, d2, d6, d, d5, d6, d4, d5, d6, d4, d2, d6, color);
        }
        if (Dir.is(n, (byte)32)) {
            this.quad(d, d2, d3, d, d5, d3, d, d5, d6, d, d2, d6, color);
        }
        if (Dir.is(n, (byte)64)) {
            this.quad(d4, d2, d3, d4, d5, d3, d4, d5, d6, d4, d2, d6, color);
        }
    }
}

