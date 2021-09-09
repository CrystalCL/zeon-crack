/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.render.VertexFormats
 */
package minegame159.meteorclient.gui.renderer;

import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WContainer;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.client.render.VertexFormats;

public class GuiDebugRenderer {
    private final /* synthetic */ MeshBuilder mb;
    private static final /* synthetic */ Color CELL_COLOR;
    private static final /* synthetic */ Color WIDGET_COLOR;

    static {
        CELL_COLOR = new Color(25, 225, 25);
        WIDGET_COLOR = new Color(25, 25, 225);
    }

    public GuiDebugRenderer() {
        GuiDebugRenderer lIIIllllI;
        lIIIllllI.mb = new MeshBuilder();
    }

    private void renderWidget(WWidget lIIIIlllI) {
        GuiDebugRenderer lIIIlIIIl;
        lIIIlIIIl.lineBox(lIIIIlllI.x, lIIIIlllI.y, lIIIIlllI.width, lIIIIlllI.height, WIDGET_COLOR);
        if (lIIIIlllI instanceof WContainer) {
            for (Cell<?> lIIIlIIlI : ((WContainer)lIIIIlllI).cells) {
                lIIIlIIIl.lineBox(lIIIlIIlI.x, lIIIlIIlI.y, lIIIlIIlI.width, lIIIlIIlI.height, CELL_COLOR);
                lIIIlIIIl.renderWidget((WWidget)lIIIlIIlI.widget());
            }
        }
    }

    private void lineBox(double lIIIIIlII, double lIIIIIIll, double lIIIIIIlI, double lIIIIIIIl, Color lllllIlI) {
        GuiDebugRenderer llllllll;
        llllllll.line(lIIIIIlII, lIIIIIIll, lIIIIIlII + lIIIIIIlI, lIIIIIIll, lllllIlI);
        llllllll.line(lIIIIIlII + lIIIIIIlI, lIIIIIIll, lIIIIIlII + lIIIIIIlI, lIIIIIIll + lIIIIIIIl, lllllIlI);
        llllllll.line(lIIIIIlII, lIIIIIIll, lIIIIIlII, lIIIIIIll + lIIIIIIIl, lllllIlI);
        llllllll.line(lIIIIIlII, lIIIIIIll + lIIIIIIIl, lIIIIIlII + lIIIIIIlI, lIIIIIIll + lIIIIIIIl, lllllIlI);
    }

    public void render(WWidget lIIIlIlll) {
        GuiDebugRenderer lIIIllIII;
        if (lIIIlIlll == null) {
            return;
        }
        lIIIllIII.mb.begin(null, DrawMode.Lines, VertexFormats.POSITION_COLOR);
        lIIIllIII.renderWidget(lIIIlIlll);
        lIIIllIII.mb.end();
    }

    private void line(double llllIIlI, double lllIlIll, double llllIIII, double lllIlIIl, Color lllIlllI) {
        GuiDebugRenderer lllIllIl;
        lllIllIl.mb.pos(llllIIlI, lllIlIll, 0.0).color(lllIlllI).endVertex();
        lllIllIl.mb.pos(llllIIII, lllIlIIl, 0.0).color(lllIlllI).endVertex();
    }
}

