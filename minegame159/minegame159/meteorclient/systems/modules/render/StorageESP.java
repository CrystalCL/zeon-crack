/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.Arrays;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StorageBlockListSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.render.RenderUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.Dir;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.ChestType;
import net.minecraft.state.property.Property;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;

public class StorageESP
extends Module {
    private final Setting<SettingColor> chest;
    private final Setting<Boolean> tracers;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<SettingColor> enderChest;
    private final Setting<List<BlockEntityType<?>>> storageBlocks;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> other;
    private final Setting<SettingColor> shulker;
    private final Setting<SettingColor> barrel;
    private final Color lineColor;
    private boolean render;
    private final Color sideColor;
    private final Setting<SettingColor> trappedChest;
    private int count;
    private final Setting<Double> fadeDistance;

    private void getTileEntityColor(BlockEntity BlockEntity2) {
        this.render = false;
        if (!this.storageBlocks.get().contains(BlockEntity2.getType())) {
            return;
        }
        if (BlockEntity2 instanceof TrappedChestBlockEntity) {
            this.lineColor.set(this.trappedChest.get());
        } else if (BlockEntity2 instanceof ChestBlockEntity) {
            this.lineColor.set(this.chest.get());
        } else if (BlockEntity2 instanceof BarrelBlockEntity) {
            this.lineColor.set(this.barrel.get());
        } else if (BlockEntity2 instanceof ShulkerBoxBlockEntity) {
            this.lineColor.set(this.shulker.get());
        } else if (BlockEntity2 instanceof EnderChestBlockEntity) {
            this.lineColor.set(this.enderChest.get());
        } else if (BlockEntity2 instanceof FurnaceBlockEntity || BlockEntity2 instanceof DispenserBlockEntity || BlockEntity2 instanceof HopperBlockEntity) {
            this.lineColor.set(this.other.get());
        } else {
            return;
        }
        this.render = true;
        if (this.shapeMode.get() == ShapeMode.Sides || this.shapeMode.get() == ShapeMode.Both) {
            this.sideColor.set(this.lineColor);
            this.sideColor.a -= 225;
            if (this.sideColor.a < 0) {
                this.sideColor.a = 0;
            }
        }
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        this.count = 0;
        for (BlockEntity BlockEntity2 : this.mc.world.blockEntities) {
            BlockState BlockState2;
            if (BlockEntity2.isRemoved() || !EntityUtils.isInRenderDistance(BlockEntity2)) continue;
            this.getTileEntityColor(BlockEntity2);
            if (!this.render) continue;
            double d = BlockEntity2.getPos().getX();
            double d2 = BlockEntity2.getPos().getY();
            double d3 = BlockEntity2.getPos().getZ();
            double d4 = BlockEntity2.getPos().getX() + 1;
            double d5 = BlockEntity2.getPos().getY() + 1;
            double d6 = BlockEntity2.getPos().getZ() + 1;
            int n = 0;
            if (BlockEntity2 instanceof ChestBlockEntity && ((BlockState2 = this.mc.world.getBlockState(BlockEntity2.getPos())).getBlock() == Blocks.CHEST || BlockState2.getBlock() == Blocks.TRAPPED_CHEST) && BlockState2.get((Property)ChestBlock.CHEST_TYPE) != ChestType.SINGLE) {
                n = Dir.get(ChestBlock.getFacing((BlockState)BlockState2));
            }
            if (BlockEntity2 instanceof ChestBlockEntity || BlockEntity2 instanceof EnderChestBlockEntity) {
                double d7 = 0.0625;
                if (Dir.is(n, (byte)32)) {
                    d += d7;
                }
                if (Dir.is(n, (byte)8)) {
                    d3 += d7;
                }
                if (Dir.is(n, (byte)64)) {
                    d4 -= d7;
                }
                d5 -= d7 * 2.0;
                if (Dir.is(n, (byte)16)) {
                    d6 -= d7;
                }
            }
            double d8 = this.mc.player.squaredDistanceTo((double)BlockEntity2.getPos().getX() + 0.5, (double)BlockEntity2.getPos().getY() + 0.5, (double)BlockEntity2.getPos().getZ() + 0.5);
            double d9 = 1.0;
            if (d8 <= this.fadeDistance.get() * this.fadeDistance.get()) {
                d9 = d8 / (this.fadeDistance.get() * this.fadeDistance.get());
            }
            int n2 = this.lineColor.a;
            int n3 = this.sideColor.a;
            this.lineColor.a = (int)((double)this.lineColor.a * d9);
            this.sideColor.a = (int)((double)this.sideColor.a * d9);
            if (d9 >= 0.075) {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d, d2, d3, d4, d5, d6, this.sideColor, this.lineColor, this.shapeMode.get(), n);
            }
            if (this.tracers.get().booleanValue()) {
                RenderUtils.drawTracerToBlockEntity(BlockEntity2, this.lineColor, renderEvent);
            }
            this.lineColor.a = n2;
            this.sideColor.a = n3;
            ++this.count;
        }
    }

    public StorageESP() {
        super(Categories.Render, "storage-esp", "Renders all specified storage blocks.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.storageBlocks = this.sgGeneral.add(new StorageBlockListSetting.Builder().name("storage-blocks").description("Select the storage blocks to display.").defaultValue(Arrays.asList(StorageBlockListSetting.STORAGE_BLOCKS)).build());
        this.tracers = this.sgGeneral.add(new BoolSetting.Builder().name("tracers").description("Draws tracers to storage blocks.").defaultValue(false).build());
        this.shapeMode = this.sgGeneral.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.chest = this.sgGeneral.add(new ColorSetting.Builder().name("chest").description("The color of chests.").defaultValue(new SettingColor(255, 160, 0, 255)).build());
        this.trappedChest = this.sgGeneral.add(new ColorSetting.Builder().name("trapped-chest").description("The color of trapped chests.").defaultValue(new SettingColor(255, 0, 0, 255)).build());
        this.barrel = this.sgGeneral.add(new ColorSetting.Builder().name("barrel").description("The color of barrels.").defaultValue(new SettingColor(255, 160, 0, 255)).build());
        this.shulker = this.sgGeneral.add(new ColorSetting.Builder().name("shulker").description("The color of Shulker Boxes.").defaultValue(new SettingColor(255, 160, 0, 255)).build());
        this.enderChest = this.sgGeneral.add(new ColorSetting.Builder().name("ender-chest").description("The color of Ender Chests.").defaultValue(new SettingColor(120, 0, 255, 255)).build());
        this.other = this.sgGeneral.add(new ColorSetting.Builder().name("other").description("The color of furnaces, dispenders, droppers and hoppers.").defaultValue(new SettingColor(140, 140, 140, 255)).build());
        this.fadeDistance = this.sgGeneral.add(new DoubleSetting.Builder().name("fade-distance").description("The distance at which the color will fade.").defaultValue(6.0).min(0.0).sliderMax(12.0).build());
        this.lineColor = new Color(0, 0, 0, 0);
        this.sideColor = new Color(0, 0, 0, 0);
    }

    @Override
    public String getInfoString() {
        return Integer.toString(this.count);
    }
}

