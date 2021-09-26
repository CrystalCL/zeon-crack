/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.Dimension;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class VoidESP
extends Module {
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Integer> holeHeight;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> airOnly;
    private final Setting<Integer> horizontalRadius;
    private final List<BlockPos> voidHoles;
    private final Setting<SettingColor> lineColor;
    private final Setting<SettingColor> sideColor;
    private final SettingGroup sgRender;

    public VoidESP() {
        super(Categories.Render, "void-esp", "Renders holes in bedrock layers that lead to the void.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.airOnly = this.sgGeneral.add(new BoolSetting.Builder().name("air-only").description("Checks bedrock only for air blocks.").defaultValue(false).build());
        this.horizontalRadius = this.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for holes.").defaultValue(64).min(0).sliderMax(256).build());
        this.holeHeight = this.sgGeneral.add(new IntSetting.Builder().name("hole-height").description("The minimum hole height to be rendered.").defaultValue(1).min(1).sliderMax(5).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("fill-color").description("The color that fills holes in the void.").defaultValue(new SettingColor(225, 25, 25)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color to draw lines of holes to the void.").defaultValue(new SettingColor(225, 25, 255)).build());
        this.voidHoles = new ArrayList<BlockPos>();
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        for (BlockPos BlockPos2 : this.voidHoles) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, BlockPos2, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
        }
    }

    private void getHoles(int n, int n2) {
        this.voidHoles.clear();
        if (Utils.getDimension() == Dimension.End) {
            return;
        }
        BlockPos BlockPos2 = this.mc.player.getBlockPos();
        int n3 = BlockPos2.getY();
        for (int i = -n; i < n; ++i) {
            for (int j = -n; j < n; ++j) {
                BlockPos BlockPos3 = BlockPos2.add(i, -n3, j);
                int n4 = 0;
                for (int k = 0; k < n2; ++k) {
                    if (!this.isBlockMatching(this.mc.world.getBlockState(BlockPos3.add(0, k, 0)).getBlock())) continue;
                    ++n4;
                    if (!false) continue;
                    return;
                }
                if (n4 >= n2) {
                    this.voidHoles.add(BlockPos3);
                }
                if (Utils.getDimension() != Dimension.Nether) continue;
                BlockPos BlockPos4 = BlockPos2.add(i, 127 - n3, j);
                int n5 = 0;
                for (int k = 0; k < n2; ++k) {
                    if (!this.isBlockMatching(this.mc.world.getBlockState(BlockPos3.add(0, 127 - k, 0)).getBlock())) continue;
                    ++n5;
                    if (null == null) continue;
                    return;
                }
                if (n5 < n2) continue;
                this.voidHoles.add(BlockPos4);
            }
            if (-1 <= 0) continue;
            return;
        }
    }

    private boolean isBlockMatching(Block Block2) {
        if (this.airOnly.get().booleanValue()) {
            return Block2 == Blocks.AIR;
        }
        return Block2 != Blocks.BEDROCK;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.getHoles(this.horizontalRadius.get(), this.holeHeight.get());
    }
}

