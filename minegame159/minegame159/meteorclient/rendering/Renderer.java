/*
 * Decompiled with CFR 0.151.
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
    public static final MeshBuilder NORMAL = new MeshBuilder();
    public static final MeshBuilder LINES = new MeshBuilder();
    private static boolean building;

    public static void boxWithLines(MeshBuilder meshBuilder, MeshBuilder meshBuilder2, double d, double d2, double d3, double d4, Color color, Color color2, ShapeMode shapeMode, int n) {
        Renderer.boxWithLines(meshBuilder, meshBuilder2, d, d2, d3, d + d4, d2 + d4, d3 + d4, color, color2, shapeMode, n);
    }

    public static void boxWithLines(MeshBuilder meshBuilder, MeshBuilder meshBuilder2, BlockPos BlockPos2, Color color, Color color2, ShapeMode shapeMode, int n) {
        Renderer.boxWithLines(meshBuilder, meshBuilder2, BlockPos2.getX(), BlockPos2.getY(), BlockPos2.getZ(), BlockPos2.getX() + 1, BlockPos2.getY() + 1, BlockPos2.getZ() + 1, color, color2, shapeMode, n);
    }

    public static void quadWithLinesHorizontal(MeshBuilder meshBuilder, MeshBuilder meshBuilder2, double d, double d2, double d3, double d4, double d5, Color color, Color color2, ShapeMode shapeMode) {
        Renderer.quadWithLines(meshBuilder, meshBuilder2, d, d2, d3, d, d2, d5, d4, d2, d5, d4, d2, d3, color, color2, shapeMode);
    }

    public static void end() {
        if (building) {
            NORMAL.end();
            LINES.end();
            building = false;
        }
    }

    public static void boxWithLines(MeshBuilder meshBuilder, MeshBuilder meshBuilder2, double d, double d2, double d3, double d4, double d5, double d6, Color color, Color color2, ShapeMode shapeMode, int n) {
        if (shapeMode == ShapeMode.Sides || shapeMode == ShapeMode.Both) {
            meshBuilder.boxSides(d, d2, d3, d4, d5, d6, color, n);
        }
        if (shapeMode == ShapeMode.Lines || shapeMode == ShapeMode.Both) {
            meshBuilder2.boxEdges(d, d2, d3, d4, d5, d6, color2, n);
        }
    }

    public static void quadWithLinesHorizontal(MeshBuilder meshBuilder, MeshBuilder meshBuilder2, double d, double d2, double d3, double d4, Color color, Color color2, ShapeMode shapeMode) {
        Renderer.quadWithLines(meshBuilder, meshBuilder2, d, d2, d3, d, d2, d3 + d4, d + d4, d2, d3 + d4, d + d4, d2, d3, color, color2, shapeMode);
    }

    public static void quadWithLines(MeshBuilder meshBuilder, MeshBuilder meshBuilder2, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, Color color, Color color2, ShapeMode shapeMode) {
        if (shapeMode == ShapeMode.Sides || shapeMode == ShapeMode.Both) {
            meshBuilder.quad(d, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, color);
        }
        if (shapeMode == ShapeMode.Lines || shapeMode == ShapeMode.Both) {
            meshBuilder2.line(d, d2, d3, d4, d5, d6, color2);
            meshBuilder2.line(d4, d5, d6, d7, d8, d9, color2);
            meshBuilder2.line(d7, d8, d9, d10, d11, d12, color2);
            meshBuilder2.line(d10, d11, d12, d, d2, d3, color2);
        }
    }

    public static void quadWithLinesVertical(MeshBuilder meshBuilder, MeshBuilder meshBuilder2, double d, double d2, double d3, double d4, double d5, double d6, Color color, Color color2, ShapeMode shapeMode) {
        Renderer.quadWithLines(meshBuilder, meshBuilder2, d, d2, d3, d, d5, d3, d4, d5, d6, d4, d2, d6, color, color2, shapeMode);
    }

    public static void begin(RenderEvent renderEvent) {
        if (!building) {
            NORMAL.begin(renderEvent, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
            LINES.begin(renderEvent, DrawMode.Lines, VertexFormats.POSITION_COLOR);
            building = true;
        }
    }
}

