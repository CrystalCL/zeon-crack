/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.client.render.VertexFormats
 */
package minegame159.meteorclient.rendering;

import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.render.VertexFormats;

public class Renderer {
    public static final /* synthetic */ MeshBuilder NORMAL;
    public static final /* synthetic */ MeshBuilder LINES;
    private static /* synthetic */ boolean building;

    static {
        NORMAL = new MeshBuilder();
        LINES = new MeshBuilder();
    }

    public Renderer() {
        Renderer llIlllllIlllIlI;
    }

    public static void boxWithLines(MeshBuilder llIlllllIIlllII, MeshBuilder llIlllllIIllIlI, double llIlllllIlIIlll, double llIlllllIIllIII, double llIlllllIlIIlIl, double llIlllllIlIIlII, double llIlllllIlIIIll, double llIlllllIlIIIlI, Color llIlllllIlIIIIl, Color llIlllllIIlIIlI, ShapeMode llIlllllIIlllll, int llIlllllIIllIll) {
        if (llIlllllIIlllll == ShapeMode.Sides || llIlllllIIlllll == ShapeMode.Both) {
            llIlllllIIlllII.boxSides(llIlllllIlIIlll, llIlllllIIllIII, llIlllllIlIIlIl, llIlllllIlIIlII, llIlllllIlIIIll, llIlllllIlIIIlI, llIlllllIlIIIIl, llIlllllIIllIll);
        }
        if (llIlllllIIlllll == ShapeMode.Lines || llIlllllIIlllll == ShapeMode.Both) {
            llIlllllIIllIlI.boxEdges(llIlllllIlIIlll, llIlllllIIllIII, llIlllllIlIIlIl, llIlllllIlIIlII, llIlllllIlIIIll, llIlllllIlIIIlI, llIlllllIIlIIlI, llIlllllIIllIll);
        }
    }

    public static void quadWithLines(MeshBuilder llIllllIlIIllIl, MeshBuilder llIllllIIlllIll, double llIllllIlIIlIll, double llIllllIIlllIIl, double llIllllIIlllIII, double llIllllIlIIlIII, double llIllllIIllIllI, double llIllllIIllIlIl, double llIllllIlIIIlIl, double llIllllIlIIIlII, double llIllllIIllIIlI, double llIllllIlIIIIlI, double llIllllIIllIIII, double llIllllIlIIIIII, Color llIllllIIlIlllI, Color llIllllIIlllllI, ShapeMode llIllllIIllllIl) {
        if (llIllllIIllllIl == ShapeMode.Sides || llIllllIIllllIl == ShapeMode.Both) {
            llIllllIlIIllIl.quad(llIllllIlIIlIll, llIllllIIlllIIl, llIllllIIlllIII, llIllllIlIIlIII, llIllllIIllIllI, llIllllIIllIlIl, llIllllIlIIIlIl, llIllllIlIIIlII, llIllllIIllIIlI, llIllllIlIIIIlI, llIllllIIllIIII, llIllllIlIIIIII, llIllllIIlIlllI);
        }
        if (llIllllIIllllIl == ShapeMode.Lines || llIllllIIllllIl == ShapeMode.Both) {
            llIllllIIlllIll.line(llIllllIlIIlIll, llIllllIIlllIIl, llIllllIIlllIII, llIllllIlIIlIII, llIllllIIllIllI, llIllllIIllIlIl, llIllllIIlllllI);
            llIllllIIlllIll.line(llIllllIlIIlIII, llIllllIIllIllI, llIllllIIllIlIl, llIllllIlIIIlIl, llIllllIlIIIlII, llIllllIIllIIlI, llIllllIIlllllI);
            llIllllIIlllIll.line(llIllllIlIIIlIl, llIllllIlIIIlII, llIllllIIllIIlI, llIllllIlIIIIlI, llIllllIIllIIII, llIllllIlIIIIII, llIllllIIlllllI);
            llIllllIIlllIll.line(llIllllIlIIIIlI, llIllllIIllIIII, llIllllIlIIIIII, llIllllIlIIlIll, llIllllIIlllIIl, llIllllIIlllIII, llIllllIIlllllI);
        }
    }

    public static void quadWithLinesHorizontal(MeshBuilder llIlllIllllllII, MeshBuilder llIllllIIIIIlIl, double llIlllIlllllIlI, double llIllllIIIIIIll, double llIllllIIIIIIlI, double llIllllIIIIIIIl, double llIllllIIIIIIII, Color llIlllIllllllll, Color llIlllIllllIlII, ShapeMode llIlllIllllllIl) {
        Renderer.quadWithLines(llIlllIllllllII, llIllllIIIIIlIl, llIlllIlllllIlI, llIllllIIIIIIll, llIllllIIIIIIlI, llIlllIlllllIlI, llIllllIIIIIIll, llIllllIIIIIIII, llIllllIIIIIIIl, llIllllIIIIIIll, llIllllIIIIIIII, llIllllIIIIIIIl, llIllllIIIIIIll, llIllllIIIIIIlI, llIlllIllllllll, llIlllIllllIlII, llIlllIllllllIl);
    }

    public static void quadWithLinesHorizontal(MeshBuilder llIllllIIlIIIlI, MeshBuilder llIllllIIlIIIIl, double llIllllIIlIIIII, double llIllllIIIlIllI, double llIllllIIIllllI, double llIllllIIIlIlII, Color llIllllIIIlIIll, Color llIllllIIIllIll, ShapeMode llIllllIIIllIlI) {
        Renderer.quadWithLines(llIllllIIlIIIlI, llIllllIIlIIIIl, llIllllIIlIIIII, llIllllIIIlIllI, llIllllIIIllllI, llIllllIIlIIIII, llIllllIIIlIllI, llIllllIIIllllI + llIllllIIIlIlII, llIllllIIlIIIII + llIllllIIIlIlII, llIllllIIIlIllI, llIllllIIIllllI + llIllllIIIlIlII, llIllllIIlIIIII + llIllllIIIlIlII, llIllllIIIlIllI, llIllllIIIllllI, llIllllIIIlIIll, llIllllIIIllIll, llIllllIIIllIlI);
    }

    public static void boxWithLines(MeshBuilder llIllllIllIllII, MeshBuilder llIllllIllIIlII, BlockPos llIllllIllIIIll, Color llIllllIllIlIIl, Color llIllllIllIIIIl, ShapeMode llIllllIllIIlll, int llIllllIllIIllI) {
        Renderer.boxWithLines(llIllllIllIllII, llIllllIllIIlII, llIllllIllIIIll.getX(), llIllllIllIIIll.getY(), llIllllIllIIIll.getZ(), llIllllIllIIIll.getX() + 1, llIllllIllIIIll.getY() + 1, llIllllIllIIIll.getZ() + 1, llIllllIllIlIIl, llIllllIllIIIIl, llIllllIllIIlll, llIllllIllIIllI);
    }

    public static void boxWithLines(MeshBuilder llIllllIlllllIl, MeshBuilder llIlllllIIIIllI, double llIllllIllllIll, double llIlllllIIIIlII, double llIlllllIIIIIll, double llIlllllIIIIIlI, Color llIllllIlllIlll, Color llIlllllIIIIIII, ShapeMode llIllllIlllllll, int llIllllIlllIlII) {
        Renderer.boxWithLines(llIllllIlllllIl, llIlllllIIIIllI, llIllllIllllIll, llIlllllIIIIlII, llIlllllIIIIIll, llIllllIllllIll + llIlllllIIIIIlI, llIlllllIIIIlII + llIlllllIIIIIlI, llIlllllIIIIIll + llIlllllIIIIIlI, llIllllIlllIlll, llIlllllIIIIIII, llIllllIlllllll, llIllllIlllIlII);
    }

    public static void begin(RenderEvent llIlllllIllIllI) {
        if (!building) {
            NORMAL.begin(llIlllllIllIllI, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
            LINES.begin(llIlllllIllIllI, DrawMode.Lines, VertexFormats.POSITION_COLOR);
            building = true;
        }
    }

    public static void quadWithLinesVertical(MeshBuilder llIlllIllIlllII, MeshBuilder llIlllIlllIIllI, double llIlllIlllIIlIl, double llIlllIllIllIII, double llIlllIlllIIIll, double llIlllIlllIIIlI, double llIlllIllIlIlIl, double llIlllIlllIIIII, Color llIlllIllIlIIll, Color llIlllIllIlIIlI, ShapeMode llIlllIllIllIll) {
        Renderer.quadWithLines(llIlllIllIlllII, llIlllIlllIIllI, llIlllIlllIIlIl, llIlllIllIllIII, llIlllIlllIIIll, llIlllIlllIIlIl, llIlllIllIlIlIl, llIlllIlllIIIll, llIlllIlllIIIlI, llIlllIllIlIlIl, llIlllIlllIIIII, llIlllIlllIIIlI, llIlllIllIllIII, llIlllIlllIIIII, llIlllIllIlIIll, llIlllIllIlIIlI, llIlllIllIllIll);
    }

    public static void end() {
        if (building) {
            NORMAL.end();
            LINES.end();
            building = false;
        }
    }
}

