/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.BlockView
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Box
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.block.BlockState
 *  net.minecraft.util.hit.BlockHitResult
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;

public class BlockSelection
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> advanced;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Boolean> oneSide;

    private void render(BlockPos llllllllllllllllllIlllIIIIIlllIl, Box llllllllllllllllllIlllIIIIIlllII) {
        BlockSelection llllllllllllllllllIlllIIIIIllllI;
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (double)llllllllllllllllllIlllIIIIIlllIl.getX() + llllllllllllllllllIlllIIIIIlllII.minX, (double)llllllllllllllllllIlllIIIIIlllIl.getY() + llllllllllllllllllIlllIIIIIlllII.minY, (double)llllllllllllllllllIlllIIIIIlllIl.getZ() + llllllllllllllllllIlllIIIIIlllII.minZ, (double)llllllllllllllllllIlllIIIIIlllIl.getX() + llllllllllllllllllIlllIIIIIlllII.maxX, (double)llllllllllllllllllIlllIIIIIlllIl.getY() + llllllllllllllllllIlllIIIIIlllII.maxY, (double)llllllllllllllllllIlllIIIIIlllIl.getZ() + llllllllllllllllllIlllIIIIIlllII.maxZ, llllllllllllllllllIlllIIIIIllllI.sideColor.get(), llllllllllllllllllIlllIIIIIllllI.lineColor.get(), llllllllllllllllllIlllIIIIIllllI.shapeMode.get(), 0);
    }

    @EventHandler
    private void onRender(RenderEvent llllllllllllllllllIlllIIIIllIlII) {
        BlockSelection llllllllllllllllllIlllIIIIlIllIl;
        if (llllllllllllllllllIlllIIIIlIllIl.mc.crosshairTarget == null || !(llllllllllllllllllIlllIIIIlIllIl.mc.crosshairTarget instanceof BlockHitResult)) {
            return;
        }
        BlockHitResult llllllllllllllllllIlllIIIIllIIll = (BlockHitResult)llllllllllllllllllIlllIIIIlIllIl.mc.crosshairTarget;
        BlockPos llllllllllllllllllIlllIIIIllIIlI = llllllllllllllllllIlllIIIIllIIll.getBlockPos();
        Direction llllllllllllllllllIlllIIIIllIIIl = llllllllllllllllllIlllIIIIllIIll.getSide();
        BlockState llllllllllllllllllIlllIIIIllIIII = llllllllllllllllllIlllIIIIlIllIl.mc.world.getBlockState(llllllllllllllllllIlllIIIIllIIlI);
        VoxelShape llllllllllllllllllIlllIIIIlIllll = llllllllllllllllllIlllIIIIllIIII.getOutlineShape((BlockView)llllllllllllllllllIlllIIIIlIllIl.mc.world, llllllllllllllllllIlllIIIIllIIlI);
        if (llllllllllllllllllIlllIIIIlIllll.isEmpty()) {
            return;
        }
        Box llllllllllllllllllIlllIIIIlIlllI = llllllllllllllllllIlllIIIIlIllll.getBoundingBox();
        if (llllllllllllllllllIlllIIIIlIllIl.oneSide.get().booleanValue()) {
            if (llllllllllllllllllIlllIIIIllIIIl == Direction.UP || llllllllllllllllllIlllIIIIllIIIl == Direction.DOWN) {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, (double)llllllllllllllllllIlllIIIIllIIlI.getX() + llllllllllllllllllIlllIIIIlIlllI.minX, (double)llllllllllllllllllIlllIIIIllIIlI.getY() + (llllllllllllllllllIlllIIIIllIIIl == Direction.DOWN ? llllllllllllllllllIlllIIIIlIlllI.minY : llllllllllllllllllIlllIIIIlIlllI.maxY), (double)llllllllllllllllllIlllIIIIllIIlI.getZ() + llllllllllllllllllIlllIIIIlIlllI.minZ, (double)llllllllllllllllllIlllIIIIllIIlI.getX() + llllllllllllllllllIlllIIIIlIlllI.maxX, (double)llllllllllllllllllIlllIIIIllIIlI.getZ() + llllllllllllllllllIlllIIIIlIlllI.maxZ, llllllllllllllllllIlllIIIIlIllIl.sideColor.get(), llllllllllllllllllIlllIIIIlIllIl.lineColor.get(), llllllllllllllllllIlllIIIIlIllIl.shapeMode.get());
            } else if (llllllllllllllllllIlllIIIIllIIIl == Direction.SOUTH || llllllllllllllllllIlllIIIIllIIIl == Direction.NORTH) {
                double llllllllllllllllllIlllIIIIlllIII = llllllllllllllllllIlllIIIIllIIIl == Direction.NORTH ? llllllllllllllllllIlllIIIIlIlllI.minZ : llllllllllllllllllIlllIIIIlIlllI.maxZ;
                Renderer.quadWithLinesVertical(Renderer.NORMAL, Renderer.LINES, (double)llllllllllllllllllIlllIIIIllIIlI.getX() + llllllllllllllllllIlllIIIIlIlllI.minX, (double)llllllllllllllllllIlllIIIIllIIlI.getY() + llllllllllllllllllIlllIIIIlIlllI.minY, (double)llllllllllllllllllIlllIIIIllIIlI.getZ() + llllllllllllllllllIlllIIIIlllIII, (double)llllllllllllllllllIlllIIIIllIIlI.getX() + llllllllllllllllllIlllIIIIlIlllI.maxX, (double)llllllllllllllllllIlllIIIIllIIlI.getY() + llllllllllllllllllIlllIIIIlIlllI.maxY, (double)llllllllllllllllllIlllIIIIllIIlI.getZ() + llllllllllllllllllIlllIIIIlllIII, llllllllllllllllllIlllIIIIlIllIl.sideColor.get(), llllllllllllllllllIlllIIIIlIllIl.lineColor.get(), llllllllllllllllllIlllIIIIlIllIl.shapeMode.get());
            } else {
                double llllllllllllllllllIlllIIIIllIlll = llllllllllllllllllIlllIIIIllIIIl == Direction.WEST ? llllllllllllllllllIlllIIIIlIlllI.minX : llllllllllllllllllIlllIIIIlIlllI.maxX;
                Renderer.quadWithLinesVertical(Renderer.NORMAL, Renderer.LINES, (double)llllllllllllllllllIlllIIIIllIIlI.getX() + llllllllllllllllllIlllIIIIllIlll, (double)llllllllllllllllllIlllIIIIllIIlI.getY() + llllllllllllllllllIlllIIIIlIlllI.minY, (double)llllllllllllllllllIlllIIIIllIIlI.getZ() + llllllllllllllllllIlllIIIIlIlllI.minZ, (double)llllllllllllllllllIlllIIIIllIIlI.getX() + llllllllllllllllllIlllIIIIllIlll, (double)llllllllllllllllllIlllIIIIllIIlI.getY() + llllllllllllllllllIlllIIIIlIlllI.maxY, (double)llllllllllllllllllIlllIIIIllIIlI.getZ() + llllllllllllllllllIlllIIIIlIlllI.maxZ, llllllllllllllllllIlllIIIIlIllIl.sideColor.get(), llllllllllllllllllIlllIIIIlIllIl.lineColor.get(), llllllllllllllllllIlllIIIIlIllIl.shapeMode.get());
            }
        } else if (llllllllllllllllllIlllIIIIlIllIl.advanced.get().booleanValue()) {
            for (Box llllllllllllllllllIlllIIIIllIllI : llllllllllllllllllIlllIIIIlIllll.getBoundingBoxes()) {
                llllllllllllllllllIlllIIIIlIllIl.render(llllllllllllllllllIlllIIIIllIIlI, llllllllllllllllllIlllIIIIllIllI);
            }
        } else {
            llllllllllllllllllIlllIIIIlIllIl.render(llllllllllllllllllIlllIIIIllIIlI, llllllllllllllllllIlllIIIIlIlllI);
        }
    }

    public BlockSelection() {
        super(Categories.Render, "block-selection", "Modifies how your block selection is rendered.");
        BlockSelection llllllllllllllllllIlllIIIlIIIIlI;
        llllllllllllllllllIlllIIIlIIIIlI.sgGeneral = llllllllllllllllllIlllIIIlIIIIlI.settings.getDefaultGroup();
        llllllllllllllllllIlllIIIlIIIIlI.advanced = llllllllllllllllllIlllIIIlIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("advanced").description("Shows a more advanced outline on different types of shape blocks.").defaultValue(true).build());
        llllllllllllllllllIlllIIIlIIIIlI.oneSide = llllllllllllllllllIlllIIIlIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("one-side").description("Renders only the side you are looking at.").defaultValue(false).build());
        llllllllllllllllllIlllIIIlIIIIlI.shapeMode = llllllllllllllllllIlllIIIlIIIIlI.sgGeneral.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
        llllllllllllllllllIlllIIIlIIIIlI.sideColor = llllllllllllllllllIlllIIIlIIIIlI.sgGeneral.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 255, 255, 50)).build());
        llllllllllllllllllIlllIIIlIIIIlI.lineColor = llllllllllllllllllIlllIIIlIIIIlI.sgGeneral.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
    }
}

