/*
 * Decompiled with CFR 0.151.
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
    private final Setting<ShapeMode> shapeMode;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> advanced;
    private final Setting<SettingColor> sideColor;
    private final Setting<SettingColor> lineColor;
    private final Setting<Boolean> oneSide;

    private void render(BlockPos BlockPos2, Box Box3) {
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (double)BlockPos2.getX() + Box3.minX, (double)BlockPos2.getY() + Box3.minY, (double)BlockPos2.getZ() + Box3.minZ, (double)BlockPos2.getX() + Box3.maxX, (double)BlockPos2.getY() + Box3.maxY, (double)BlockPos2.getZ() + Box3.maxZ, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    public BlockSelection() {
        super(Categories.Render, "block-selection", "Modifies how your block selection is rendered.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.advanced = this.sgGeneral.add(new BoolSetting.Builder().name("advanced").description("Shows a more advanced outline on different types of shape blocks.").defaultValue(true).build());
        this.oneSide = this.sgGeneral.add(new BoolSetting.Builder().name("one-side").description("Renders only the side you are looking at.").defaultValue(false).build());
        this.shapeMode = this.sgGeneral.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
        this.sideColor = this.sgGeneral.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 255, 255, 50)).build());
        this.lineColor = this.sgGeneral.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.mc.crosshairTarget == null || !(this.mc.crosshairTarget instanceof BlockHitResult)) {
            return;
        }
        BlockHitResult BlockHitResult2 = (BlockHitResult)this.mc.crosshairTarget;
        BlockPos BlockPos2 = BlockHitResult2.getBlockPos();
        Direction Direction2 = BlockHitResult2.getSide();
        BlockState BlockState2 = this.mc.world.getBlockState(BlockPos2);
        VoxelShape VoxelShape2 = BlockState2.getOutlineShape((BlockView)this.mc.world, BlockPos2);
        if (VoxelShape2.isEmpty()) {
            return;
        }
        Box Box3 = VoxelShape2.getBoundingBox();
        if (this.oneSide.get().booleanValue()) {
            if (Direction2 == Direction.UP || Direction2 == Direction.DOWN) {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, (double)BlockPos2.getX() + Box3.minX, (double)BlockPos2.getY() + (Direction2 == Direction.DOWN ? Box3.minY : Box3.maxY), (double)BlockPos2.getZ() + Box3.minZ, (double)BlockPos2.getX() + Box3.maxX, (double)BlockPos2.getZ() + Box3.maxZ, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get());
            } else if (Direction2 == Direction.SOUTH || Direction2 == Direction.NORTH) {
                double d = Direction2 == Direction.NORTH ? Box3.minZ : Box3.maxZ;
                Renderer.quadWithLinesVertical(Renderer.NORMAL, Renderer.LINES, (double)BlockPos2.getX() + Box3.minX, (double)BlockPos2.getY() + Box3.minY, (double)BlockPos2.getZ() + d, (double)BlockPos2.getX() + Box3.maxX, (double)BlockPos2.getY() + Box3.maxY, (double)BlockPos2.getZ() + d, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get());
            } else {
                double d = Direction2 == Direction.WEST ? Box3.minX : Box3.maxX;
                Renderer.quadWithLinesVertical(Renderer.NORMAL, Renderer.LINES, (double)BlockPos2.getX() + d, (double)BlockPos2.getY() + Box3.minY, (double)BlockPos2.getZ() + Box3.minZ, (double)BlockPos2.getX() + d, (double)BlockPos2.getY() + Box3.maxY, (double)BlockPos2.getZ() + Box3.maxZ, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get());
            }
        } else if (this.advanced.get().booleanValue()) {
            for (Box Box4 : VoxelShape2.getBoundingBoxes()) {
                this.render(BlockPos2, Box4);
            }
        } else {
            this.render(BlockPos2, Box3);
        }
    }
}

