/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.ChestBlock
 *  net.minecraft.block.entity.BlockEntity
 *  net.minecraft.block.entity.BlockEntityType
 *  net.minecraft.block.entity.ChestBlockEntity
 *  net.minecraft.block.entity.DispenserBlockEntity
 *  net.minecraft.block.entity.EnderChestBlockEntity
 *  net.minecraft.block.entity.HopperBlockEntity
 *  net.minecraft.block.entity.ShulkerBoxBlockEntity
 *  net.minecraft.block.entity.TrappedChestBlockEntity
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.enums.ChestType
 *  net.minecraft.state.property.Property
 *  net.minecraft.block.entity.BarrelBlockEntity
 *  net.minecraft.block.entity.FurnaceBlockEntity
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
    private /* synthetic */ boolean render;
    private final /* synthetic */ Setting<Boolean> tracers;
    private /* synthetic */ int count;
    private final /* synthetic */ Setting<List<BlockEntityType<?>>> storageBlocks;
    private final /* synthetic */ Setting<SettingColor> shulker;
    private final /* synthetic */ Setting<SettingColor> barrel;
    private final /* synthetic */ Color sideColor;
    private final /* synthetic */ Color lineColor;
    private final /* synthetic */ Setting<SettingColor> trappedChest;
    private final /* synthetic */ Setting<SettingColor> chest;
    private final /* synthetic */ Setting<SettingColor> enderChest;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> other;
    private final /* synthetic */ Setting<Double> fadeDistance;

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllllllllllIllllIl) {
        StorageESP llllllllllllllllllllllllllIIIIII;
        llllllllllllllllllllllllllIIIIII.count = 0;
        for (BlockEntity llllllllllllllllllllllllllIIIIIl : llllllllllllllllllllllllllIIIIII.mc.world.blockEntities) {
            BlockState llllllllllllllllllllllllllIIlllI;
            if (llllllllllllllllllllllllllIIIIIl.isRemoved() || !EntityUtils.isInRenderDistance(llllllllllllllllllllllllllIIIIIl)) continue;
            llllllllllllllllllllllllllIIIIII.getTileEntityColor(llllllllllllllllllllllllllIIIIIl);
            if (!llllllllllllllllllllllllllIIIIII.render) continue;
            double llllllllllllllllllllllllllIIllII = llllllllllllllllllllllllllIIIIIl.getPos().getX();
            double llllllllllllllllllllllllllIIlIll = llllllllllllllllllllllllllIIIIIl.getPos().getY();
            double llllllllllllllllllllllllllIIlIlI = llllllllllllllllllllllllllIIIIIl.getPos().getZ();
            double llllllllllllllllllllllllllIIlIIl = llllllllllllllllllllllllllIIIIIl.getPos().getX() + 1;
            double llllllllllllllllllllllllllIIlIII = llllllllllllllllllllllllllIIIIIl.getPos().getY() + 1;
            double llllllllllllllllllllllllllIIIlll = llllllllllllllllllllllllllIIIIIl.getPos().getZ() + 1;
            int llllllllllllllllllllllllllIIIllI = 0;
            if (llllllllllllllllllllllllllIIIIIl instanceof ChestBlockEntity && ((llllllllllllllllllllllllllIIlllI = llllllllllllllllllllllllllIIIIII.mc.world.getBlockState(llllllllllllllllllllllllllIIIIIl.getPos())).getBlock() == Blocks.CHEST || llllllllllllllllllllllllllIIlllI.getBlock() == Blocks.TRAPPED_CHEST) && llllllllllllllllllllllllllIIlllI.get((Property)ChestBlock.CHEST_TYPE) != ChestType.SINGLE) {
                llllllllllllllllllllllllllIIIllI = Dir.get(ChestBlock.getFacing((BlockState)llllllllllllllllllllllllllIIlllI));
            }
            if (llllllllllllllllllllllllllIIIIIl instanceof ChestBlockEntity || llllllllllllllllllllllllllIIIIIl instanceof EnderChestBlockEntity) {
                double llllllllllllllllllllllllllIIllIl = 0.0625;
                if (Dir.is(llllllllllllllllllllllllllIIIllI, (byte)32)) {
                    llllllllllllllllllllllllllIIllII += llllllllllllllllllllllllllIIllIl;
                }
                if (Dir.is(llllllllllllllllllllllllllIIIllI, (byte)8)) {
                    llllllllllllllllllllllllllIIlIlI += llllllllllllllllllllllllllIIllIl;
                }
                if (Dir.is(llllllllllllllllllllllllllIIIllI, (byte)64)) {
                    llllllllllllllllllllllllllIIlIIl -= llllllllllllllllllllllllllIIllIl;
                }
                llllllllllllllllllllllllllIIlIII -= llllllllllllllllllllllllllIIllIl * 2.0;
                if (Dir.is(llllllllllllllllllllllllllIIIllI, (byte)16)) {
                    llllllllllllllllllllllllllIIIlll -= llllllllllllllllllllllllllIIllIl;
                }
            }
            double llllllllllllllllllllllllllIIIlIl = llllllllllllllllllllllllllIIIIII.mc.player.squaredDistanceTo((double)llllllllllllllllllllllllllIIIIIl.getPos().getX() + 0.5, (double)llllllllllllllllllllllllllIIIIIl.getPos().getY() + 0.5, (double)llllllllllllllllllllllllllIIIIIl.getPos().getZ() + 0.5);
            double llllllllllllllllllllllllllIIIlII = 1.0;
            if (llllllllllllllllllllllllllIIIlIl <= llllllllllllllllllllllllllIIIIII.fadeDistance.get() * llllllllllllllllllllllllllIIIIII.fadeDistance.get()) {
                llllllllllllllllllllllllllIIIlII = llllllllllllllllllllllllllIIIlIl / (llllllllllllllllllllllllllIIIIII.fadeDistance.get() * llllllllllllllllllllllllllIIIIII.fadeDistance.get());
            }
            int llllllllllllllllllllllllllIIIIll = llllllllllllllllllllllllllIIIIII.lineColor.a;
            int llllllllllllllllllllllllllIIIIlI = llllllllllllllllllllllllllIIIIII.sideColor.a;
            llllllllllllllllllllllllllIIIIII.lineColor.a = (int)((double)llllllllllllllllllllllllllIIIIII.lineColor.a * llllllllllllllllllllllllllIIIlII);
            llllllllllllllllllllllllllIIIIII.sideColor.a = (int)((double)llllllllllllllllllllllllllIIIIII.sideColor.a * llllllllllllllllllllllllllIIIlII);
            if (llllllllllllllllllllllllllIIIlII >= 0.075) {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllllllllllllllllllllllllIIllII, llllllllllllllllllllllllllIIlIll, llllllllllllllllllllllllllIIlIlI, llllllllllllllllllllllllllIIlIIl, llllllllllllllllllllllllllIIlIII, llllllllllllllllllllllllllIIIlll, llllllllllllllllllllllllllIIIIII.sideColor, llllllllllllllllllllllllllIIIIII.lineColor, llllllllllllllllllllllllllIIIIII.shapeMode.get(), llllllllllllllllllllllllllIIIllI);
            }
            if (llllllllllllllllllllllllllIIIIII.tracers.get().booleanValue()) {
                RenderUtils.drawTracerToBlockEntity(llllllllllllllllllllllllllIIIIIl, llllllllllllllllllllllllllIIIIII.lineColor, lllllllllllllllllllllllllIllllIl);
            }
            llllllllllllllllllllllllllIIIIII.lineColor.a = llllllllllllllllllllllllllIIIIll;
            llllllllllllllllllllllllllIIIIII.sideColor.a = llllllllllllllllllllllllllIIIIlI;
            ++llllllllllllllllllllllllllIIIIII.count;
        }
    }

    public StorageESP() {
        super(Categories.Render, "storage-esp", "Renders all specified storage blocks.");
        StorageESP lllllllllllllllllllllllllllIIlIl;
        lllllllllllllllllllllllllllIIlIl.sgGeneral = lllllllllllllllllllllllllllIIlIl.settings.getDefaultGroup();
        lllllllllllllllllllllllllllIIlIl.storageBlocks = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new StorageBlockListSetting.Builder().name("storage-blocks").description("Select the storage blocks to display.").defaultValue(Arrays.asList(StorageBlockListSetting.STORAGE_BLOCKS)).build());
        lllllllllllllllllllllllllllIIlIl.tracers = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new BoolSetting.Builder().name("tracers").description("Draws tracers to storage blocks.").defaultValue(false).build());
        lllllllllllllllllllllllllllIIlIl.shapeMode = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllllllllllllIIlIl.chest = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new ColorSetting.Builder().name("chest").description("The color of chests.").defaultValue(new SettingColor(255, 160, 0, 255)).build());
        lllllllllllllllllllllllllllIIlIl.trappedChest = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new ColorSetting.Builder().name("trapped-chest").description("The color of trapped chests.").defaultValue(new SettingColor(255, 0, 0, 255)).build());
        lllllllllllllllllllllllllllIIlIl.barrel = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new ColorSetting.Builder().name("barrel").description("The color of barrels.").defaultValue(new SettingColor(255, 160, 0, 255)).build());
        lllllllllllllllllllllllllllIIlIl.shulker = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new ColorSetting.Builder().name("shulker").description("The color of Shulker Boxes.").defaultValue(new SettingColor(255, 160, 0, 255)).build());
        lllllllllllllllllllllllllllIIlIl.enderChest = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new ColorSetting.Builder().name("ender-chest").description("The color of Ender Chests.").defaultValue(new SettingColor(120, 0, 255, 255)).build());
        lllllllllllllllllllllllllllIIlIl.other = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new ColorSetting.Builder().name("other").description("The color of furnaces, dispenders, droppers and hoppers.").defaultValue(new SettingColor(140, 140, 140, 255)).build());
        lllllllllllllllllllllllllllIIlIl.fadeDistance = lllllllllllllllllllllllllllIIlIl.sgGeneral.add(new DoubleSetting.Builder().name("fade-distance").description("The distance at which the color will fade.").defaultValue(6.0).min(0.0).sliderMax(12.0).build());
        lllllllllllllllllllllllllllIIlIl.lineColor = new Color(0, 0, 0, 0);
        lllllllllllllllllllllllllllIIlIl.sideColor = new Color(0, 0, 0, 0);
    }

    @Override
    public String getInfoString() {
        StorageESP lllllllllllllllllllllllllIlIlllI;
        return Integer.toString(lllllllllllllllllllllllllIlIlllI.count);
    }

    private void getTileEntityColor(BlockEntity lllllllllllllllllllllllllllIIIII) {
        StorageESP lllllllllllllllllllllllllllIIIIl;
        lllllllllllllllllllllllllllIIIIl.render = false;
        if (!lllllllllllllllllllllllllllIIIIl.storageBlocks.get().contains((Object)lllllllllllllllllllllllllllIIIII.getType())) {
            return;
        }
        if (lllllllllllllllllllllllllllIIIII instanceof TrappedChestBlockEntity) {
            lllllllllllllllllllllllllllIIIIl.lineColor.set(lllllllllllllllllllllllllllIIIIl.trappedChest.get());
        } else if (lllllllllllllllllllllllllllIIIII instanceof ChestBlockEntity) {
            lllllllllllllllllllllllllllIIIIl.lineColor.set(lllllllllllllllllllllllllllIIIIl.chest.get());
        } else if (lllllllllllllllllllllllllllIIIII instanceof BarrelBlockEntity) {
            lllllllllllllllllllllllllllIIIIl.lineColor.set(lllllllllllllllllllllllllllIIIIl.barrel.get());
        } else if (lllllllllllllllllllllllllllIIIII instanceof ShulkerBoxBlockEntity) {
            lllllllllllllllllllllllllllIIIIl.lineColor.set(lllllllllllllllllllllllllllIIIIl.shulker.get());
        } else if (lllllllllllllllllllllllllllIIIII instanceof EnderChestBlockEntity) {
            lllllllllllllllllllllllllllIIIIl.lineColor.set(lllllllllllllllllllllllllllIIIIl.enderChest.get());
        } else if (lllllllllllllllllllllllllllIIIII instanceof FurnaceBlockEntity || lllllllllllllllllllllllllllIIIII instanceof DispenserBlockEntity || lllllllllllllllllllllllllllIIIII instanceof HopperBlockEntity) {
            lllllllllllllllllllllllllllIIIIl.lineColor.set(lllllllllllllllllllllllllllIIIIl.other.get());
        } else {
            return;
        }
        lllllllllllllllllllllllllllIIIIl.render = true;
        if (lllllllllllllllllllllllllllIIIIl.shapeMode.get() == ShapeMode.Sides || lllllllllllllllllllllllllllIIIIl.shapeMode.get() == ShapeMode.Both) {
            lllllllllllllllllllllllllllIIIIl.sideColor.set(lllllllllllllllllllllllllllIIIIl.lineColor);
            lllllllllllllllllllllllllllIIIIl.sideColor.a -= 225;
            if (lllllllllllllllllllllllllllIIIIl.sideColor.a < 0) {
                lllllllllllllllllllllllllllIIIIl.sideColor.a = 0;
            }
        }
    }
}

