/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.util.math.Matrix4f
 *  net.minecraft.client.render.BufferRenderer
 *  net.minecraft.client.render.BufferBuilder
 *  net.minecraft.client.render.VertexFormat
 *  com.mojang.blaze3d.platform.GlStateManager
 *  com.mojang.blaze3d.platform.GlStateManager$class_4534
 *  com.mojang.blaze3d.platform.GlStateManager$class_4535
 *  org.lwjgl.opengl.GL11
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
    private final /* synthetic */ BufferBuilder buffer;
    private /* synthetic */ int count;
    private /* synthetic */ double offsetZ;
    private /* synthetic */ double offsetY;
    private /* synthetic */ double offsetX;
    public /* synthetic */ boolean depthTest;
    public /* synthetic */ double alpha;
    public /* synthetic */ boolean texture;

    public void boxEdges(double lIIIlIlIIIIlllI, double lIIIlIlIIIIIlll, double lIIIlIlIIIIIllI, double lIIIlIlIIIIlIll, Color lIIIlIlIIIIIlII) {
        MeshBuilder lIIIlIlIIIIlIIl;
        lIIIlIlIIIIlIIl.boxEdges(lIIIlIlIIIIlllI, lIIIlIlIIIIIlll, 0.0, lIIIlIlIIIIlllI + lIIIlIlIIIIIllI, lIIIlIlIIIIIlll + lIIIlIlIIIIlIll, 0.0, lIIIlIlIIIIIlII, 0);
    }

    public void quad(double lIIIlIlllIIIIlI, double lIIIlIllIlllIII, double lIIIlIllIllIlll, double lIIIlIllIllIllI, Color lIIIlIllIllIlIl, Color lIIIlIllIllllIl, Color lIIIlIllIllllII, Color lIIIlIllIllIIlI) {
        MeshBuilder lIIIlIllIlllIlI;
        lIIIlIllIlllIlI.pos(lIIIlIlllIIIIlI, lIIIlIllIlllIII, 0.0).color(lIIIlIllIllIlIl).endVertex();
        lIIIlIllIlllIlI.pos(lIIIlIlllIIIIlI + lIIIlIllIllIlll, lIIIlIllIlllIII, 0.0).color(lIIIlIllIllllIl).endVertex();
        lIIIlIllIlllIlI.pos(lIIIlIlllIIIIlI + lIIIlIllIllIlll, lIIIlIllIlllIII + lIIIlIllIllIllI, 0.0).color(lIIIlIllIllllII).endVertex();
        lIIIlIllIlllIlI.pos(lIIIlIlllIIIIlI, lIIIlIllIlllIII, 0.0).color(lIIIlIllIllIlIl).endVertex();
        lIIIlIllIlllIlI.pos(lIIIlIlllIIIIlI + lIIIlIllIllIlll, lIIIlIllIlllIII + lIIIlIllIllIllI, 0.0).color(lIIIlIllIllllII).endVertex();
        lIIIlIllIlllIlI.pos(lIIIlIlllIIIIlI, lIIIlIllIlllIII + lIIIlIllIllIllI, 0.0).color(lIIIlIllIllIIlI).endVertex();
    }

    public void endVertex() {
        MeshBuilder lIIIllIIlIIllII;
        lIIIllIIlIIllII.buffer.next();
        ++lIIIllIIlIIllII.count;
    }

    public MeshBuilder pos(double lIIIllIIllIlIIl, double lIIIllIIllIlIII, double lIIIllIIllIIIll) {
        MeshBuilder lIIIllIIllIIllI;
        lIIIllIIllIIllI.buffer.vertex(lIIIllIIllIlIIl + lIIIllIIllIIllI.offsetX, lIIIllIIllIlIII + lIIIllIIllIIllI.offsetY, lIIIllIIllIIIll + lIIIllIIllIIllI.offsetZ);
        return lIIIllIIllIIllI;
    }

    public void gradientBoxSides(double lIIIlIlIllIllII, double lIIIlIlIlllIlIl, double lIIIlIlIlllIlII, double lIIIlIlIllIlIIl, double lIIIlIlIllIlIII, double lIIIlIlIllIIlll, Color lIIIlIlIlllIIII, Color lIIIlIlIllIIlIl, boolean lIIIlIlIllIlllI) {
        MeshBuilder lIIIlIlIllIllIl;
        lIIIlIlIllIllIl.gradientQuad(lIIIlIlIllIllII, lIIIlIlIlllIlIl, lIIIlIlIlllIlII, lIIIlIlIllIllII, lIIIlIlIlllIlIl + lIIIlIlIllIIlll, lIIIlIlIlllIlII, lIIIlIlIllIlIIl, lIIIlIlIlllIlIl + lIIIlIlIllIIlll, lIIIlIlIlllIlII, lIIIlIlIllIlIIl, lIIIlIlIlllIlIl, lIIIlIlIlllIlII, lIIIlIlIllIlllI ? lIIIlIlIllIIlIl : lIIIlIlIlllIIII, lIIIlIlIllIlllI ? lIIIlIlIlllIIII : lIIIlIlIllIIlIl);
        lIIIlIlIllIllIl.gradientQuad(lIIIlIlIllIllII, lIIIlIlIlllIlIl, lIIIlIlIllIlIII, lIIIlIlIllIllII, lIIIlIlIlllIlIl + lIIIlIlIllIIlll, lIIIlIlIllIlIII, lIIIlIlIllIlIIl, lIIIlIlIlllIlIl + lIIIlIlIllIIlll, lIIIlIlIllIlIII, lIIIlIlIllIlIIl, lIIIlIlIlllIlIl, lIIIlIlIllIlIII, lIIIlIlIllIlllI ? lIIIlIlIllIIlIl : lIIIlIlIlllIIII, lIIIlIlIllIlllI ? lIIIlIlIlllIIII : lIIIlIlIllIIlIl);
        lIIIlIlIllIllIl.gradientQuad(lIIIlIlIllIllII, lIIIlIlIlllIlIl, lIIIlIlIlllIlII, lIIIlIlIllIllII, lIIIlIlIlllIlIl + lIIIlIlIllIIlll, lIIIlIlIlllIlII, lIIIlIlIllIllII, lIIIlIlIlllIlIl + lIIIlIlIllIIlll, lIIIlIlIllIlIII, lIIIlIlIllIllII, lIIIlIlIlllIlIl, lIIIlIlIllIlIII, lIIIlIlIllIlllI ? lIIIlIlIllIIlIl : lIIIlIlIlllIIII, lIIIlIlIllIlllI ? lIIIlIlIlllIIII : lIIIlIlIllIIlIl);
        lIIIlIlIllIllIl.gradientQuad(lIIIlIlIllIlIIl, lIIIlIlIlllIlIl, lIIIlIlIlllIlII, lIIIlIlIllIlIIl, lIIIlIlIlllIlIl + lIIIlIlIllIIlll, lIIIlIlIlllIlII, lIIIlIlIllIlIIl, lIIIlIlIlllIlIl + lIIIlIlIllIIlll, lIIIlIlIllIlIII, lIIIlIlIllIlIIl, lIIIlIlIlllIlIl, lIIIlIlIllIlIII, lIIIlIlIllIlllI ? lIIIlIlIllIIlIl : lIIIlIlIlllIIII, lIIIlIlIllIlllI ? lIIIlIlIlllIIII : lIIIlIlIllIIlIl);
    }

    public void gradientQuad(double lIIIlIlllIllIIl, double lIIIlIlllIllIII, double lIIIlIlllIlIIII, double lIIIlIlllIlIllI, Color lIIIlIlllIlIlIl, Color lIIIlIlllIIllIl) {
        MeshBuilder lIIIlIlllIlIIll;
        lIIIlIlllIlIIll.gradientQuad(lIIIlIlllIllIIl, lIIIlIlllIllIII, 0.0, lIIIlIlllIllIIl + lIIIlIlllIlIIII, lIIIlIlllIllIII, 0.0, lIIIlIlllIllIIl + lIIIlIlllIlIIII, lIIIlIlllIllIII + lIIIlIlllIlIllI, 0.0, lIIIlIlllIllIIl, lIIIlIlllIllIII + lIIIlIlllIlIllI, 0.0, lIIIlIlllIlIlIl, lIIIlIlllIIllIl);
    }

    public MeshBuilder texture(double lIIIllIIlIllllI, double lIIIllIIlIlllIl) {
        MeshBuilder lIIIllIIlIlllll;
        lIIIllIIlIlllll.buffer.texture((float)lIIIllIIlIllllI, (float)lIIIllIIlIlllIl);
        return lIIIllIIlIlllll;
    }

    public void texQuad(double lIIIlIllIlIlIIl, double lIIIlIllIlIlIII, double lIIIlIllIlIIlll, double lIIIlIllIlIIllI, TextureRegion lIIIlIllIIllllI, Color lIIIlIllIlIIlII) {
        MeshBuilder lIIIlIllIlIIIll;
        lIIIlIllIlIIIll.pos(lIIIlIllIlIlIIl, lIIIlIllIlIlIII, 0.0).color(lIIIlIllIlIIlII).texture(lIIIlIllIIllllI.x1, lIIIlIllIIllllI.y1).endVertex();
        lIIIlIllIlIIIll.pos(lIIIlIllIlIlIIl + lIIIlIllIlIIlll, lIIIlIllIlIlIII, 0.0).color(lIIIlIllIlIIlII).texture(lIIIlIllIIllllI.x2, lIIIlIllIIllllI.y1).endVertex();
        lIIIlIllIlIIIll.pos(lIIIlIllIlIlIIl + lIIIlIllIlIIlll, lIIIlIllIlIlIII + lIIIlIllIlIIllI, 0.0).color(lIIIlIllIlIIlII).texture(lIIIlIllIIllllI.x2, lIIIlIllIIllllI.y2).endVertex();
        lIIIlIllIlIIIll.pos(lIIIlIllIlIlIIl, lIIIlIllIlIlIII, 0.0).color(lIIIlIllIlIIlII).texture(lIIIlIllIIllllI.x1, lIIIlIllIIllllI.y1).endVertex();
        lIIIlIllIlIIIll.pos(lIIIlIllIlIlIIl + lIIIlIllIlIIlll, lIIIlIllIlIlIII + lIIIlIllIlIIllI, 0.0).color(lIIIlIllIlIIlII).texture(lIIIlIllIIllllI.x2, lIIIlIllIIllllI.y2).endVertex();
        lIIIlIllIlIIIll.pos(lIIIlIllIlIlIIl, lIIIlIllIlIlIII + lIIIlIllIlIIllI, 0.0).color(lIIIlIllIlIIlII).texture(lIIIlIllIIllllI.x1, lIIIlIllIIllllI.y2).endVertex();
    }

    public void gradientVerticalBox(double lIIIlIIlllIlllI, double lIIIlIIllllIlll, double lIIIlIIlllIllII, double lIIIlIIlllIlIll, double lIIIlIIlllIlIlI, double lIIIlIIllllIIll, Color lIIIlIIlllIlIII, Color lIIIlIIlllIIlll, boolean lIIIlIIllllIIII) {
        MeshBuilder lIIIlIIlllllIIl;
        lIIIlIIlllllIIl.gradientLine(lIIIlIIlllIlllI, lIIIlIIllllIlll, lIIIlIIlllIllII, lIIIlIIlllIlllI, lIIIlIIllllIlll + lIIIlIIllllIIll, lIIIlIIlllIllII, lIIIlIIllllIIII ? lIIIlIIlllIIlll : lIIIlIIlllIlIII, lIIIlIIllllIIII ? lIIIlIIlllIlIII : lIIIlIIlllIIlll);
        lIIIlIIlllllIIl.gradientLine(lIIIlIIlllIlIll, lIIIlIIllllIlll, lIIIlIIlllIllII, lIIIlIIlllIlIll, lIIIlIIllllIlll + lIIIlIIllllIIll, lIIIlIIlllIllII, lIIIlIIllllIIII ? lIIIlIIlllIIlll : lIIIlIIlllIlIII, lIIIlIIllllIIII ? lIIIlIIlllIlIII : lIIIlIIlllIIlll);
        lIIIlIIlllllIIl.gradientLine(lIIIlIIlllIlllI, lIIIlIIllllIlll, lIIIlIIlllIlIlI, lIIIlIIlllIlllI, lIIIlIIllllIlll + lIIIlIIllllIIll, lIIIlIIlllIlIlI, lIIIlIIllllIIII ? lIIIlIIlllIIlll : lIIIlIIlllIlIII, lIIIlIIllllIIII ? lIIIlIIlllIlIII : lIIIlIIlllIIlll);
        lIIIlIIlllllIIl.gradientLine(lIIIlIIlllIlIll, lIIIlIIllllIlll, lIIIlIIlllIlIlI, lIIIlIIlllIlIll, lIIIlIIllllIlll + lIIIlIIllllIIll, lIIIlIIlllIlIlI, lIIIlIIllllIIII ? lIIIlIIlllIIlll : lIIIlIIlllIlIII, lIIIlIIllllIIII ? lIIIlIIlllIlIII : lIIIlIIlllIIlll);
    }

    public void quad(double lIIIllIIIIllIIl, double lIIIllIIIIlIIlI, double lIIIllIIIIlIIIl, double lIIIllIIIIlIIII, Color lIIIllIIIIIllll) {
        MeshBuilder lIIIllIIIIllIlI;
        lIIIllIIIIllIlI.quad(lIIIllIIIIllIIl, lIIIllIIIIlIIlI, 0.0, lIIIllIIIIllIIl + lIIIllIIIIlIIIl, lIIIllIIIIlIIlI, 0.0, lIIIllIIIIllIIl + lIIIllIIIIlIIIl, lIIIllIIIIlIIlI + lIIIllIIIIlIIII, 0.0, lIIIllIIIIllIIl, lIIIllIIIIlIIlI + lIIIllIIIIlIIII, 0.0, lIIIllIIIIIllll);
    }

    public void gradientQuad(double lIIIlIllllIllll, double lIIIlIllllIlllI, double lIIIlIllllIllIl, double lIIIlIllllIllII, double lIIIlIllllllIlI, double lIIIlIllllIlIlI, double lIIIlIllllIlIIl, double lIIIlIlllllIlll, double lIIIlIlllllIllI, double lIIIlIlllllIlIl, double lIIIlIllllIIlIl, double lIIIlIllllIIlII, Color lIIIlIlllllIIlI, Color lIIIlIlllllIIIl) {
        MeshBuilder lIIIlIlllllIIII;
        lIIIlIlllllIIII.pos(lIIIlIllllIllll, lIIIlIllllIlllI, lIIIlIllllIllIl).color(lIIIlIlllllIIlI).endVertex();
        lIIIlIlllllIIII.pos(lIIIlIllllIllII, lIIIlIllllllIlI, lIIIlIllllIlIlI).color(lIIIlIlllllIIIl).endVertex();
        lIIIlIlllllIIII.pos(lIIIlIllllIlIIl, lIIIlIlllllIlll, lIIIlIlllllIllI).color(lIIIlIlllllIIIl).endVertex();
        lIIIlIlllllIIII.pos(lIIIlIllllIllll, lIIIlIllllIlllI, lIIIlIllllIllIl).color(lIIIlIlllllIIlI).endVertex();
        lIIIlIlllllIIII.pos(lIIIlIllllIlIIl, lIIIlIlllllIlll, lIIIlIlllllIllI).color(lIIIlIlllllIIIl).endVertex();
        lIIIlIlllllIIII.pos(lIIIlIlllllIlIl, lIIIlIllllIIlIl, lIIIlIllllIIlII).color(lIIIlIlllllIIlI).endVertex();
    }

    public MeshBuilder color(Color lIIIllIIlIlIlII) {
        MeshBuilder lIIIllIIlIlIlIl;
        lIIIllIIlIlIlIl.buffer.color((float)lIIIllIIlIlIlII.r / 255.0f, (float)lIIIllIIlIlIlII.g / 255.0f, (float)lIIIllIIlIlIlII.b / 255.0f, (float)lIIIllIIlIlIlII.a / 255.0f * (float)lIIIllIIlIlIlIl.alpha);
        return lIIIllIIlIlIlIl;
    }

    public void boxSides(double lIIIlIllIIlIIlI, double lIIIlIllIIIlIII, double lIIIlIllIIlIIII, double lIIIlIllIIIIllI, double lIIIlIllIIIIlIl, double lIIIlIllIIIIlII, Color lIIIlIllIIIIIll, int lIIIlIllIIIlIll) {
        MeshBuilder lIIIlIllIIIlIlI;
        if (Dir.is(lIIIlIllIIIlIll, (byte)4)) {
            lIIIlIllIIIlIlI.quad(lIIIlIllIIlIIlI, lIIIlIllIIIlIII, lIIIlIllIIlIIII, lIIIlIllIIlIIlI, lIIIlIllIIIlIII, lIIIlIllIIIIlII, lIIIlIllIIIIllI, lIIIlIllIIIlIII, lIIIlIllIIIIlII, lIIIlIllIIIIllI, lIIIlIllIIIlIII, lIIIlIllIIlIIII, lIIIlIllIIIIIll);
        }
        if (Dir.is(lIIIlIllIIIlIll, (byte)2)) {
            lIIIlIllIIIlIlI.quad(lIIIlIllIIlIIlI, lIIIlIllIIIIlIl, lIIIlIllIIlIIII, lIIIlIllIIlIIlI, lIIIlIllIIIIlIl, lIIIlIllIIIIlII, lIIIlIllIIIIllI, lIIIlIllIIIIlIl, lIIIlIllIIIIlII, lIIIlIllIIIIllI, lIIIlIllIIIIlIl, lIIIlIllIIlIIII, lIIIlIllIIIIIll);
        }
        if (Dir.is(lIIIlIllIIIlIll, (byte)8)) {
            lIIIlIllIIIlIlI.quad(lIIIlIllIIlIIlI, lIIIlIllIIIlIII, lIIIlIllIIlIIII, lIIIlIllIIlIIlI, lIIIlIllIIIIlIl, lIIIlIllIIlIIII, lIIIlIllIIIIllI, lIIIlIllIIIIlIl, lIIIlIllIIlIIII, lIIIlIllIIIIllI, lIIIlIllIIIlIII, lIIIlIllIIlIIII, lIIIlIllIIIIIll);
        }
        if (Dir.is(lIIIlIllIIIlIll, (byte)16)) {
            lIIIlIllIIIlIlI.quad(lIIIlIllIIlIIlI, lIIIlIllIIIlIII, lIIIlIllIIIIlII, lIIIlIllIIlIIlI, lIIIlIllIIIIlIl, lIIIlIllIIIIlII, lIIIlIllIIIIllI, lIIIlIllIIIIlIl, lIIIlIllIIIIlII, lIIIlIllIIIIllI, lIIIlIllIIIlIII, lIIIlIllIIIIlII, lIIIlIllIIIIIll);
        }
        if (Dir.is(lIIIlIllIIIlIll, (byte)32)) {
            lIIIlIllIIIlIlI.quad(lIIIlIllIIlIIlI, lIIIlIllIIIlIII, lIIIlIllIIlIIII, lIIIlIllIIlIIlI, lIIIlIllIIIIlIl, lIIIlIllIIlIIII, lIIIlIllIIlIIlI, lIIIlIllIIIIlIl, lIIIlIllIIIIlII, lIIIlIllIIlIIlI, lIIIlIllIIIlIII, lIIIlIllIIIIlII, lIIIlIllIIIIIll);
        }
        if (Dir.is(lIIIlIllIIIlIll, (byte)64)) {
            lIIIlIllIIIlIlI.quad(lIIIlIllIIIIllI, lIIIlIllIIIlIII, lIIIlIllIIlIIII, lIIIlIllIIIIllI, lIIIlIllIIIIlIl, lIIIlIllIIlIIII, lIIIlIllIIIIllI, lIIIlIllIIIIlIl, lIIIlIllIIIIlII, lIIIlIllIIIIllI, lIIIlIllIIIlIII, lIIIlIllIIIIlII, lIIIlIllIIIIIll);
        }
    }

    public void end() {
        MeshBuilder lIIIllIIlllIIlI;
        lIIIllIIlllIIlI.buffer.end();
        GL11.glPushMatrix();
        RenderSystem.multMatrix((Matrix4f)Matrices.getTop());
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
        if (lIIIllIIlllIIlI.depthTest) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.disableAlphaTest();
        if (lIIIllIIlllIIlI.texture) {
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
        BufferRenderer.draw((BufferBuilder)lIIIllIIlllIIlI.buffer);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableDepthTest();
        RenderSystem.enableTexture();
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public void boxEdges(double lIIIlIlIIlIIllI, double lIIIlIlIIIlllII, double lIIIlIlIIlIIlII, double lIIIlIlIIlIIIll, double lIIIlIlIIlIIIlI, double lIIIlIlIIIllIII, Color lIIIlIlIIIlIlll, int lIIIlIlIIIlIllI) {
        MeshBuilder lIIIlIlIIIllllI;
        if (Dir.is(lIIIlIlIIIlIllI, (byte)32) && Dir.is(lIIIlIlIIIlIllI, (byte)8)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIllI, lIIIlIlIIIlllII, lIIIlIlIIlIIlII, lIIIlIlIIlIIllI, lIIIlIlIIlIIIlI, lIIIlIlIIlIIlII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)32) && Dir.is(lIIIlIlIIIlIllI, (byte)16)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIllI, lIIIlIlIIIlllII, lIIIlIlIIIllIII, lIIIlIlIIlIIllI, lIIIlIlIIlIIIlI, lIIIlIlIIIllIII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)64) && Dir.is(lIIIlIlIIIlIllI, (byte)8)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIIll, lIIIlIlIIIlllII, lIIIlIlIIlIIlII, lIIIlIlIIlIIIll, lIIIlIlIIlIIIlI, lIIIlIlIIlIIlII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)64) && Dir.is(lIIIlIlIIIlIllI, (byte)16)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIIll, lIIIlIlIIIlllII, lIIIlIlIIIllIII, lIIIlIlIIlIIIll, lIIIlIlIIlIIIlI, lIIIlIlIIIllIII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)8)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIllI, lIIIlIlIIIlllII, lIIIlIlIIlIIlII, lIIIlIlIIlIIIll, lIIIlIlIIIlllII, lIIIlIlIIlIIlII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)8)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIllI, lIIIlIlIIlIIIlI, lIIIlIlIIlIIlII, lIIIlIlIIlIIIll, lIIIlIlIIlIIIlI, lIIIlIlIIlIIlII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)16)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIllI, lIIIlIlIIIlllII, lIIIlIlIIIllIII, lIIIlIlIIlIIIll, lIIIlIlIIIlllII, lIIIlIlIIIllIII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)16)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIllI, lIIIlIlIIlIIIlI, lIIIlIlIIIllIII, lIIIlIlIIlIIIll, lIIIlIlIIlIIIlI, lIIIlIlIIIllIII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)32)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIllI, lIIIlIlIIIlllII, lIIIlIlIIlIIlII, lIIIlIlIIlIIllI, lIIIlIlIIIlllII, lIIIlIlIIIllIII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)32)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIllI, lIIIlIlIIlIIIlI, lIIIlIlIIlIIlII, lIIIlIlIIlIIllI, lIIIlIlIIlIIIlI, lIIIlIlIIIllIII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)64)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIIll, lIIIlIlIIIlllII, lIIIlIlIIlIIlII, lIIIlIlIIlIIIll, lIIIlIlIIIlllII, lIIIlIlIIIllIII, lIIIlIlIIIlIlll);
        }
        if (Dir.is(lIIIlIlIIIlIllI, (byte)64)) {
            lIIIlIlIIIllllI.line(lIIIlIlIIlIIIll, lIIIlIlIIlIIIlI, lIIIlIlIIlIIlII, lIIIlIlIIlIIIll, lIIIlIlIIlIIIlI, lIIIlIlIIIllIII, lIIIlIlIIIlIlll);
        }
    }

    public MeshBuilder color(int lIIIllIIlIlIIII) {
        MeshBuilder lIIIllIIlIlIIIl;
        lIIIllIIlIlIIIl.buffer.color((float)Color.toRGBAR(lIIIllIIlIlIIII) / 255.0f, (float)Color.toRGBAG(lIIIllIIlIlIIII) / 255.0f, (float)Color.toRGBAB(lIIIllIIlIlIIII) / 255.0f, (float)Color.toRGBAA(lIIIllIIlIlIIII) / 255.0f * (float)lIIIllIIlIlIIIl.alpha);
        return lIIIllIIlIlIIIl;
    }

    public void gradientLine(double lIIIlIlIIlllIII, double lIIIlIlIlIIIIII, double lIIIlIlIIllllll, double lIIIlIlIIlllllI, double lIIIlIlIIllIlII, double lIIIlIlIIllllII, Color lIIIlIlIIlllIll, Color lIIIlIlIIllIIIl) {
        MeshBuilder lIIIlIlIlIIIIlI;
        lIIIlIlIlIIIIlI.pos(lIIIlIlIIlllIII, lIIIlIlIlIIIIII, lIIIlIlIIllllll).color(lIIIlIlIIlllIll).endVertex();
        lIIIlIlIlIIIIlI.pos(lIIIlIlIIlllllI, lIIIlIlIIllIlII, lIIIlIlIIllllII).color(lIIIlIlIIllIIIl).endVertex();
    }

    public MeshBuilder() {
        MeshBuilder lIIIllIlIIIIIlI;
        lIIIllIlIIIIIlI.alpha = 1.0;
        lIIIllIlIIIIIlI.depthTest = false;
        lIIIllIlIIIIIlI.texture = false;
        lIIIllIlIIIIIlI.buffer = new BufferBuilder(0x200000);
    }

    public void begin(RenderEvent lIIIllIIlllIlll, DrawMode lIIIllIIlllIllI, VertexFormat lIIIllIIlllIlIl) {
        MeshBuilder lIIIllIIllllIII;
        if (lIIIllIIlllIlll != null) {
            lIIIllIIllllIII.offsetX = -lIIIllIIlllIlll.offsetX;
            lIIIllIIllllIII.offsetY = -lIIIllIIlllIlll.offsetY;
            lIIIllIIllllIII.offsetZ = -lIIIllIIlllIlll.offsetZ;
        } else {
            lIIIllIIllllIII.offsetX = 0.0;
            lIIIllIIllllIII.offsetY = 0.0;
            lIIIllIIllllIII.offsetZ = 0.0;
        }
        lIIIllIIllllIII.buffer.begin(lIIIllIIlllIllI.toOpenGl(), lIIIllIIlllIlIl);
        lIIIllIIllllIII.count = 0;
    }

    public MeshBuilder(int lIIIllIlIIIIllI) {
        MeshBuilder lIIIllIlIIIIlIl;
        lIIIllIlIIIIlIl.alpha = 1.0;
        lIIIllIlIIIIlIl.depthTest = false;
        lIIIllIlIIIIlIl.texture = false;
        lIIIllIlIIIIlIl.buffer = new BufferBuilder(lIIIllIlIIIIllI);
    }

    public void quad(double lIIIllIIIlllIll, double lIIIllIIIlIllII, double lIIIllIIIlllIIl, double lIIIllIIIlllIII, double lIIIllIIIllIlll, double lIIIllIIIlIlIII, double lIIIllIIIlIIlll, double lIIIllIIIllIlII, double lIIIllIIIllIIll, double lIIIllIIIlIIlII, double lIIIllIIIlIIIll, double lIIIllIIIllIIII, Color lIIIllIIIlIIIIl) {
        MeshBuilder lIIIllIIIllllII;
        lIIIllIIIllllII.pos(lIIIllIIIlllIll, lIIIllIIIlIllII, lIIIllIIIlllIIl).color(lIIIllIIIlIIIIl).endVertex();
        lIIIllIIIllllII.pos(lIIIllIIIlllIII, lIIIllIIIllIlll, lIIIllIIIlIlIII).color(lIIIllIIIlIIIIl).endVertex();
        lIIIllIIIllllII.pos(lIIIllIIIlIIlll, lIIIllIIIllIlII, lIIIllIIIllIIll).color(lIIIllIIIlIIIIl).endVertex();
        lIIIllIIIllllII.pos(lIIIllIIIlllIll, lIIIllIIIlIllII, lIIIllIIIlllIIl).color(lIIIllIIIlIIIIl).endVertex();
        lIIIllIIIllllII.pos(lIIIllIIIlIIlll, lIIIllIIIllIlII, lIIIllIIIllIIll).color(lIIIllIIIlIIIIl).endVertex();
        lIIIllIIIllllII.pos(lIIIllIIIlIIlII, lIIIllIIIlIIIll, lIIIllIIIllIIII).color(lIIIllIIIlIIIIl).endVertex();
    }

    public boolean isBuilding() {
        MeshBuilder lIIIllIIllIllll;
        return lIIIllIIllIllll.buffer.isBuilding();
    }

    public void line(double lIIIlIlIlIllIlI, double lIIIlIlIlIlIIIl, double lIIIlIlIlIlIIII, double lIIIlIlIlIlIlll, double lIIIlIlIlIlIllI, double lIIIlIlIlIlIlIl, Color lIIIlIlIlIIllII) {
        MeshBuilder lIIIlIlIlIllIll;
        lIIIlIlIlIllIll.pos(lIIIlIlIlIllIlI, lIIIlIlIlIlIIIl, lIIIlIlIlIlIIII).color(lIIIlIlIlIIllII).endVertex();
        lIIIlIlIlIllIll.pos(lIIIlIlIlIlIlll, lIIIlIlIlIlIllI, lIIIlIlIlIlIlIl).color(lIIIlIlIlIIllII).endVertex();
    }
}

