/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.util.Identifier
 *  org.lwjgl.opengl.GL11
 */
package minegame159.meteorclient.gui.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.renderer.GuiRenderOperation;
import minegame159.meteorclient.gui.renderer.Scissor;
import minegame159.meteorclient.gui.renderer.operations.TextOperation;
import minegame159.meteorclient.gui.renderer.packer.GuiTexture;
import minegame159.meteorclient.gui.renderer.packer.TexturePacker;
import minegame159.meteorclient.gui.renderer.packer.TextureRegion;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.render.ByteTexture;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class GuiRenderer {
    public static /* synthetic */ GuiTexture EDIT;
    public static /* synthetic */ GuiTexture TRIANGLE;
    public /* synthetic */ GuiTheme theme;
    private final /* synthetic */ Stack<Scissor> scissorStack;
    public /* synthetic */ String lastTooltip;
    private final /* synthetic */ List<TextOperation> texts;
    private final /* synthetic */ MeshBuilder mb;
    public static /* synthetic */ GuiTexture RESET;
    private /* synthetic */ double tooltipAnimProgress;
    private final /* synthetic */ List<Runnable> postTasks;
    private final /* synthetic */ Pool<Scissor> scissorPool;
    private final /* synthetic */ Pool<TextOperation> textPool;
    private final /* synthetic */ MeshBuilder mbTex;
    private static final /* synthetic */ TexturePacker TEXTURE_PACKER;
    private static /* synthetic */ ByteTexture TEXTURE;
    public /* synthetic */ WWidget tooltipWidget;
    private static final /* synthetic */ Color WHITE;
    public static /* synthetic */ GuiTexture CIRCLE;
    public /* synthetic */ String tooltip;

    public void text(String llIIIIllIIIlllI, double llIIIIllIIlIlIl, double llIIIIllIIlIIll, Color llIIIIllIIIlIll, boolean llIIIIllIIlIIII) {
        GuiRenderer llIIIIllIIIllll;
        llIIIIllIIIllll.texts.add(llIIIIllIIIllll.getOp(llIIIIllIIIllll.textPool, llIIIIllIIlIlIl, llIIIIllIIlIIll, llIIIIllIIIlIll).set(llIIIIllIIIlllI, llIIIIllIIIllll.theme.textRenderer(), llIIIIllIIlIIII));
    }

    public void absolutePost(Runnable llIIIIlIlIllIIl) {
        GuiRenderer llIIIIlIlIllIII;
        llIIIIlIlIllIII.postTasks.add(llIIIIlIlIllIIl);
    }

    private <T extends GuiRenderOperation<T>> T getOp(Pool<T> llIIIIlIlIIIlIl, double llIIIIlIlIIlIIl, double llIIIIlIlIIlIII, Color llIIIIlIlIIIIIl) {
        GuiRenderOperation llIIIIlIlIIIllI = (GuiRenderOperation)llIIIIlIlIIIlIl.get();
        llIIIIlIlIIIllI.set(llIIIIlIlIIlIIl, llIIIIlIlIIlIII, llIIIIlIlIIIIIl);
        return (T)llIIIIlIlIIIllI;
    }

    public void quad(double llIIIlIIlIIIIll, double llIIIlIIlIIIIlI, double llIIIlIIlIIIIIl, double llIIIlIIlIIIIII, Color llIIIlIIIllllll, Color llIIIlIIIllIlll) {
        GuiRenderer llIIIlIIIllllIl;
        llIIIlIIIllllIl.quad(llIIIlIIlIIIIll, llIIIlIIlIIIIlI, llIIIlIIlIIIIIl, llIIIlIIlIIIIII, llIIIlIIIllllll, llIIIlIIIllllll, llIIIlIIIllIlll, llIIIlIIIllllll);
    }

    public void tooltip(String llIIIlIIllIlIIl) {
        llIIIlIIllIlIII.tooltip = llIIIlIIllIlIIl;
    }

    public static GuiTexture addTexture(Identifier llIIIlIlIllIllI) {
        return TEXTURE_PACKER.add(llIIIlIlIllIllI);
    }

    private void endRender() {
        GuiRenderer llIIIlIlIlIIIIl;
        llIIIlIlIlIIIIl.mb.end();
        TEXTURE.bindTexture();
        llIIIlIlIlIIIIl.mbTex.texture = true;
        llIIIlIlIlIIIIl.mbTex.end();
        llIIIlIlIlIIIIl.theme.textRenderer().begin(llIIIlIlIlIIIIl.theme.scale(1.0));
        for (TextOperation llIIIlIlIlIIlII : llIIIlIlIlIIIIl.texts) {
            if (llIIIlIlIlIIlII.title) continue;
            llIIIlIlIlIIlII.run(llIIIlIlIlIIIIl.textPool);
        }
        llIIIlIlIlIIIIl.theme.textRenderer().end();
        llIIIlIlIlIIIIl.theme.textRenderer().begin(llIIIlIlIlIIIIl.theme.scale(1.25));
        for (TextOperation llIIIlIlIlIIIll : llIIIlIlIlIIIIl.texts) {
            if (!llIIIlIlIlIIIll.title) continue;
            llIIIlIlIlIIIll.run(llIIIlIlIlIIIIl.textPool);
        }
        llIIIlIlIlIIIIl.theme.textRenderer().end();
        llIIIlIlIlIIIIl.texts.clear();
    }

    public void quad(WWidget llIIIlIIIlIIIII, Color llIIIlIIIIlllII) {
        GuiRenderer llIIIlIIIlIIIIl;
        llIIIlIIIlIIIIl.quad(llIIIlIIIlIIIII.x, llIIIlIIIlIIIII.y, llIIIlIIIlIIIII.width, llIIIlIIIlIIIII.height, llIIIlIIIIlllII);
    }

    public void quad(double llIIIlIIIIlIIll, double llIIIlIIIIIlIll, double llIIIlIIIIIlIlI, double llIIIlIIIIIlIIl, GuiTexture llIIIlIIIIIllll, Color llIIIlIIIIIIlll) {
        GuiRenderer llIIIlIIIIlIlII;
        llIIIlIIIIlIlII.mbTex.texQuad(llIIIlIIIIlIIll, llIIIlIIIIIlIll, llIIIlIIIIIlIlI, llIIIlIIIIIlIIl, llIIIlIIIIIllll.get(llIIIlIIIIIlIlI, llIIIlIIIIIlIIl), llIIIlIIIIIIlll);
    }

    public void scissorEnd() {
        GuiRenderer llIIIlIlIIIIlll;
        Scissor llIIIlIlIIIIllI = llIIIlIlIIIIlll.scissorStack.pop();
        llIIIlIlIIIIllI.apply();
        llIIIlIlIIIIlll.endRender();
        for (Runnable llIIIlIlIIIlIII : llIIIlIlIIIIllI.postTasks) {
            llIIIlIlIIIlIII.run();
        }
        if (!llIIIlIlIIIIlll.scissorStack.isEmpty()) {
            llIIIlIlIIIIlll.beginRender();
        }
        llIIIlIlIIIIlll.scissorPool.free(llIIIlIlIIIIllI);
    }

    public GuiRenderer() {
        GuiRenderer llIIIlIlIlllIlI;
        llIIIlIlIlllIlI.mb = new MeshBuilder();
        llIIIlIlIlllIlI.mbTex = new MeshBuilder();
        llIIIlIlIlllIlI.scissorPool = new Pool<Scissor>(Scissor::new);
        llIIIlIlIlllIlI.scissorStack = new Stack();
        llIIIlIlIlllIlI.textPool = new Pool<TextOperation>(TextOperation::new);
        llIIIlIlIlllIlI.texts = new ArrayList<TextOperation>();
        llIIIlIlIlllIlI.postTasks = new ArrayList<Runnable>();
    }

    public void post(Runnable llIIIIlIllIlIll) {
        GuiRenderer llIIIIlIlllIIII;
        llIIIIlIlllIIII.scissorStack.peek().postTasks.add(llIIIIlIllIlIll);
    }

    public void setAlpha(double llIIIlIIllIllll) {
        GuiRenderer llIIIlIIlllIIII;
        llIIIlIIlllIIII.mb.alpha = llIIIlIIllIllll;
        llIIIlIIlllIIII.mbTex.alpha = llIIIlIIllIllll;
        llIIIlIIlllIIII.theme.textRenderer().setAlpha(llIIIlIIllIllll);
    }

    public void texture(double llIIIIlIllllIlI, double llIIIIlIllllIIl, double llIIIIlIllllIII, double llIIIIlIllllllI, double llIIIIlIlllIllI, AbstractTexture llIIIIlIlllIlIl) {
        GuiRenderer llIIIIllIIIIIlI;
        llIIIIllIIIIIlI.post(() -> {
            GuiRenderer llIIIIlIIlIIllI;
            llIIIIlIIlIIllI.mbTex.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR_TEXTURE);
            llIIIIlIIlIIllI.mbTex.pos(llIIIIlIllllIlI, llIIIIlIllllIIl, 0.0).color(WHITE).texture(0.0, 0.0).endVertex();
            llIIIIlIIlIIllI.mbTex.pos(llIIIIlIllllIlI + llIIIIlIllllIII, llIIIIlIllllIIl, 0.0).color(WHITE).texture(1.0, 0.0).endVertex();
            llIIIIlIIlIIllI.mbTex.pos(llIIIIlIllllIlI + llIIIIlIllllIII, llIIIIlIllllIIl + llIIIIlIllllllI, 0.0).color(WHITE).texture(1.0, 1.0).endVertex();
            llIIIIlIIlIIllI.mbTex.pos(llIIIIlIllllIlI, llIIIIlIllllIIl, 0.0).color(WHITE).texture(0.0, 0.0).endVertex();
            llIIIIlIIlIIllI.mbTex.pos(llIIIIlIllllIlI + llIIIIlIllllIII, llIIIIlIllllIIl + llIIIIlIllllllI, 0.0).color(WHITE).texture(1.0, 1.0).endVertex();
            llIIIIlIIlIIllI.mbTex.pos(llIIIIlIllllIlI, llIIIIlIllllIIl + llIIIIlIllllllI, 0.0).color(WHITE).texture(0.0, 1.0).endVertex();
            llIIIIlIlllIlIl.bindTexture();
            GL11.glPushMatrix();
            GL11.glTranslated((double)(llIIIIlIllllIlI + llIIIIlIllllIII / 2.0), (double)(llIIIIlIllllIIl + llIIIIlIllllllI / 2.0), (double)0.0);
            GL11.glRotated((double)llIIIIlIlllIllI, (double)0.0, (double)0.0, (double)1.0);
            GL11.glTranslated((double)(-llIIIIlIllllIlI - llIIIIlIllllIII / 2.0), (double)(-llIIIIlIllllIIl - llIIIIlIllllllI / 2.0), (double)0.0);
            llIIIIlIIlIIllI.mbTex.end();
            GL11.glPopMatrix();
        });
    }

    public void rotatedQuad(double llIIIIlllIIIIIl, double llIIIIllIlllllI, double llIIIIllIllllIl, double llIIIIlllIlIlIl, double llIIIIlllIlIlII, GuiTexture llIIIIllIlllIII, Color llIIIIllIllIllI) {
        GuiRenderer llIIIIlllIlllII;
        TextureRegion llIIIIlllIlIIIl = llIIIIllIlllIII.get(llIIIIllIllllIl, llIIIIlllIlIlIl);
        double llIIIIlllIlIIII = Math.toRadians(llIIIIlllIlIlII);
        double llIIIIlllIIllll = Math.cos(llIIIIlllIlIIII);
        double llIIIIlllIIlllI = Math.sin(llIIIIlllIlIIII);
        double llIIIIlllIIllIl = llIIIIlllIIIIIl + llIIIIllIllllIl / 2.0;
        double llIIIIlllIIllII = llIIIIllIlllllI + llIIIIlllIlIlIl / 2.0;
        double llIIIIlllIIlIll = (llIIIIlllIIIIIl - llIIIIlllIIllIl) * llIIIIlllIIllll - (llIIIIllIlllllI - llIIIIlllIIllII) * llIIIIlllIIlllI + llIIIIlllIIllIl;
        double llIIIIlllIIlIlI = (llIIIIllIlllllI - llIIIIlllIIllII) * llIIIIlllIIllll + (llIIIIlllIIIIIl - llIIIIlllIIllIl) * llIIIIlllIIlllI + llIIIIlllIIllII;
        llIIIIlllIlllII.mbTex.pos(llIIIIlllIIlIll, llIIIIlllIIlIlI, 0.0).color(llIIIIllIllIllI).texture(llIIIIlllIlIIIl.x1, llIIIIlllIlIIIl.y1).endVertex();
        double llIIIIlllIIlIIl = (llIIIIlllIIIIIl + llIIIIllIllllIl - llIIIIlllIIllIl) * llIIIIlllIIllll - (llIIIIllIlllllI - llIIIIlllIIllII) * llIIIIlllIIlllI + llIIIIlllIIllIl;
        double llIIIIlllIIlIII = (llIIIIllIlllllI - llIIIIlllIIllII) * llIIIIlllIIllll + (llIIIIlllIIIIIl + llIIIIllIllllIl - llIIIIlllIIllIl) * llIIIIlllIIlllI + llIIIIlllIIllII;
        llIIIIlllIlllII.mbTex.pos(llIIIIlllIIlIIl, llIIIIlllIIlIII, 0.0).color(llIIIIllIllIllI).texture(llIIIIlllIlIIIl.x2, llIIIIlllIlIIIl.y1).endVertex();
        double llIIIIlllIIIlll = (llIIIIlllIIIIIl + llIIIIllIllllIl - llIIIIlllIIllIl) * llIIIIlllIIllll - (llIIIIllIlllllI + llIIIIlllIlIlIl - llIIIIlllIIllII) * llIIIIlllIIlllI + llIIIIlllIIllIl;
        double llIIIIlllIIIlIl = (llIIIIllIlllllI + llIIIIlllIlIlIl - llIIIIlllIIllII) * llIIIIlllIIllll + (llIIIIlllIIIIIl + llIIIIllIllllIl - llIIIIlllIIllIl) * llIIIIlllIIlllI + llIIIIlllIIllII;
        llIIIIlllIlllII.mbTex.pos(llIIIIlllIIIlll, llIIIIlllIIIlIl, 0.0).color(llIIIIllIllIllI).texture(llIIIIlllIlIIIl.x2, llIIIIlllIlIIIl.y2).endVertex();
        llIIIIlllIlllII.mbTex.pos(llIIIIlllIIlIll, llIIIIlllIIlIlI, 0.0).color(llIIIIllIllIllI).texture(llIIIIlllIlIIIl.x1, llIIIIlllIlIIIl.y1).endVertex();
        llIIIIlllIlllII.mbTex.pos(llIIIIlllIIIlll, llIIIIlllIIIlIl, 0.0).color(llIIIIllIllIllI).texture(llIIIIlllIlIIIl.x2, llIIIIlllIlIIIl.y2).endVertex();
        llIIIIlllIIlIIl = (llIIIIlllIIIIIl - llIIIIlllIIllIl) * llIIIIlllIIllll - (llIIIIllIlllllI + llIIIIlllIlIlIl - llIIIIlllIIllII) * llIIIIlllIIlllI + llIIIIlllIIllIl;
        llIIIIlllIIlIII = (llIIIIllIlllllI + llIIIIlllIlIlIl - llIIIIlllIIllII) * llIIIIlllIIllll + (llIIIIlllIIIIIl - llIIIIlllIIllIl) * llIIIIlllIIlllI + llIIIIlllIIllII;
        llIIIIlllIlllII.mbTex.pos(llIIIIlllIIlIIl, llIIIIlllIIlIII, 0.0).color(llIIIIllIllIllI).texture(llIIIIlllIlIIIl.x1, llIIIIlllIlIIIl.y2).endVertex();
    }

    public void quad(double llIIIlIIIlIlIIl, double llIIIlIIIlIlIII, double llIIIlIIIlIIlll, double llIIIlIIIlIIllI, Color llIIIlIIIlIlIll) {
        GuiRenderer llIIIlIIIlIlIlI;
        llIIIlIIIlIlIlI.quad(llIIIlIIIlIlIIl, llIIIlIIIlIlIII, llIIIlIIIlIIlll, llIIIlIIIlIIllI, llIIIlIIIlIlIll, llIIIlIIIlIlIll);
    }

    public void end() {
        GuiRenderer llIIIlIlIlIlllI;
        llIIIlIlIlIlllI.scissorEnd();
        for (Runnable llIIIlIlIlIllll : llIIIlIlIlIlllI.postTasks) {
            llIIIlIlIlIllll.run();
        }
        llIIIlIlIlIlllI.postTasks.clear();
        GL11.glDisable((int)3089);
    }

    public void scissorStart(double llIIIlIlIIlIllI, double llIIIlIlIIlIlIl, double llIIIlIlIIlIlII, double llIIIlIlIIIlllI) {
        GuiRenderer llIIIlIlIIlIlll;
        if (!llIIIlIlIIlIlll.scissorStack.isEmpty()) {
            Scissor llIIIlIlIIllIII = llIIIlIlIIlIlll.scissorStack.peek();
            if (llIIIlIlIIlIllI < (double)llIIIlIlIIllIII.x) {
                llIIIlIlIIlIllI = llIIIlIlIIllIII.x;
            } else if (llIIIlIlIIlIllI + llIIIlIlIIlIlII > (double)(llIIIlIlIIllIII.x + llIIIlIlIIllIII.width)) {
                llIIIlIlIIlIlII -= llIIIlIlIIlIllI + llIIIlIlIIlIlII - (double)(llIIIlIlIIllIII.x + llIIIlIlIIllIII.width);
            }
            if (llIIIlIlIIlIlIl < (double)llIIIlIlIIllIII.y) {
                llIIIlIlIIlIlIl = llIIIlIlIIllIII.y;
            } else if (llIIIlIlIIlIlIl + llIIIlIlIIIlllI > (double)(llIIIlIlIIllIII.y + llIIIlIlIIllIII.height)) {
                llIIIlIlIIIlllI -= llIIIlIlIIlIlIl + llIIIlIlIIIlllI - (double)(llIIIlIlIIllIII.y + llIIIlIlIIllIII.height);
            }
            llIIIlIlIIllIII.apply();
            llIIIlIlIIlIlll.endRender();
        }
        llIIIlIlIIlIlll.scissorStack.push(llIIIlIlIIlIlll.scissorPool.get().set(llIIIlIlIIlIllI, llIIIlIlIIlIlIl, llIIIlIlIIlIlII, llIIIlIlIIIlllI));
        llIIIlIlIIlIlll.beginRender();
    }

    static {
        WHITE = new Color(255, 255, 255);
        TEXTURE_PACKER = new TexturePacker();
    }

    public boolean renderTooltip(double llIIIlIIllllIll, double llIIIlIIllllIlI, double llIIIlIIllllIIl) {
        GuiRenderer llIIIlIIlllllII;
        llIIIlIIlllllII.tooltipAnimProgress += (double)(llIIIlIIlllllII.tooltip != null ? 1 : -1) * llIIIlIIllllIIl * 14.0;
        llIIIlIIlllllII.tooltipAnimProgress = Utils.clamp(llIIIlIIlllllII.tooltipAnimProgress, 0.0, 1.0);
        boolean llIIIlIIllllIII = false;
        if (llIIIlIIlllllII.tooltipAnimProgress > 0.0) {
            if (llIIIlIIlllllII.tooltip != null && !llIIIlIIlllllII.tooltip.equals(llIIIlIIlllllII.lastTooltip)) {
                llIIIlIIlllllII.tooltipWidget = llIIIlIIlllllII.theme.tooltip(llIIIlIIlllllII.tooltip);
                llIIIlIIlllllII.tooltipWidget.init();
            }
            llIIIlIIlllllII.tooltipWidget.move(-llIIIlIIlllllII.tooltipWidget.x + llIIIlIIllllIll + 12.0, -llIIIlIIlllllII.tooltipWidget.y + llIIIlIIllllIlI + 12.0);
            llIIIlIIlllllII.setAlpha(llIIIlIIlllllII.tooltipAnimProgress);
            llIIIlIIlllllII.begin();
            llIIIlIIlllllII.tooltipWidget.render(llIIIlIIlllllII, llIIIlIIllllIll, llIIIlIIllllIlI, llIIIlIIllllIIl);
            llIIIlIIlllllII.end();
            llIIIlIIlllllII.setAlpha(1.0);
            llIIIlIIlllllII.lastTooltip = llIIIlIIlllllII.tooltip;
            llIIIlIIllllIII = true;
        }
        llIIIlIIlllllII.tooltip = null;
        return llIIIlIIllllIII;
    }

    public void quad(double llIIIlIIlIlllII, double llIIIlIIlIllIll, double llIIIlIIlIlIIIl, double llIIIlIIlIlIIII, Color llIIIlIIlIllIII, Color llIIIlIIlIIlllI, Color llIIIlIIlIlIllI, Color llIIIlIIlIIllII) {
        GuiRenderer llIIIlIIlIlIlII;
        llIIIlIIlIlIlII.mb.quad(llIIIlIIlIlllII, llIIIlIIlIllIll, llIIIlIIlIlIIIl, llIIIlIIlIlIIII, llIIIlIIlIllIII, llIIIlIIlIIlllI, llIIIlIIlIlIllI, llIIIlIIlIIllII);
    }

    private void beginRender() {
        GuiRenderer llIIIlIlIlIlIIl;
        llIIIlIlIlIlIIl.mb.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
        llIIIlIlIlIlIIl.mbTex.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR_TEXTURE);
    }

    public void begin() {
        GuiRenderer llIIIlIlIllIlII;
        GL11.glEnable((int)3089);
        llIIIlIlIllIlII.scissorStart(0.0, 0.0, Utils.getWindowWidth(), Utils.getWindowHeight());
    }

    public static void init() {
        CIRCLE = GuiRenderer.addTexture(new Identifier("meteor-client", "gui/circle.png"));
        TRIANGLE = GuiRenderer.addTexture(new Identifier("meteor-client", "gui/triangle.png"));
        EDIT = GuiRenderer.addTexture(new Identifier("meteor-client", "gui/edit.png"));
        RESET = GuiRenderer.addTexture(new Identifier("meteor-client", "gui/reset.png"));
        TEXTURE = TEXTURE_PACKER.pack();
    }
}

