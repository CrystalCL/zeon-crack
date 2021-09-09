/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
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
    private final /* synthetic */ Setting<Integer> horizontalRadius;
    private final /* synthetic */ List<BlockPos> voidHoles;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<Boolean> airOnly;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Integer> holeHeight;

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIIlIIlIlllIllll) {
        VoidESP lllllllllllllllllIIlIIlIlllIlllI;
        for (BlockPos lllllllllllllllllIIlIIlIllllIIIl : lllllllllllllllllIIlIIlIlllIlllI.voidHoles) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllIIlIIlIllllIIIl, lllllllllllllllllIIlIIlIlllIlllI.sideColor.get(), lllllllllllllllllIIlIIlIlllIlllI.lineColor.get(), lllllllllllllllllIIlIIlIlllIlllI.shapeMode.get(), 0);
        }
    }

    public VoidESP() {
        super(Categories.Render, "void-esp", "Renders holes in bedrock layers that lead to the void.");
        VoidESP lllllllllllllllllIIlIIllIIlIIlIl;
        lllllllllllllllllIIlIIllIIlIIlIl.sgGeneral = lllllllllllllllllIIlIIllIIlIIlIl.settings.getDefaultGroup();
        lllllllllllllllllIIlIIllIIlIIlIl.sgRender = lllllllllllllllllIIlIIllIIlIIlIl.settings.createGroup("Render");
        lllllllllllllllllIIlIIllIIlIIlIl.airOnly = lllllllllllllllllIIlIIllIIlIIlIl.sgGeneral.add(new BoolSetting.Builder().name("air-only").description("Checks bedrock only for air blocks.").defaultValue(false).build());
        lllllllllllllllllIIlIIllIIlIIlIl.horizontalRadius = lllllllllllllllllIIlIIllIIlIIlIl.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for holes.").defaultValue(64).min(0).sliderMax(256).build());
        lllllllllllllllllIIlIIllIIlIIlIl.holeHeight = lllllllllllllllllIIlIIllIIlIIlIl.sgGeneral.add(new IntSetting.Builder().name("hole-height").description("The minimum hole height to be rendered.").defaultValue(1).min(1).sliderMax(5).build());
        lllllllllllllllllIIlIIllIIlIIlIl.shapeMode = lllllllllllllllllIIlIIllIIlIIlIl.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllIIlIIllIIlIIlIl.sideColor = lllllllllllllllllIIlIIllIIlIIlIl.sgRender.add(new ColorSetting.Builder().name("fill-color").description("The color that fills holes in the void.").defaultValue(new SettingColor(225, 25, 25)).build());
        lllllllllllllllllIIlIIllIIlIIlIl.lineColor = lllllllllllllllllIIlIIllIIlIIlIl.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color to draw lines of holes to the void.").defaultValue(new SettingColor(225, 25, 255)).build());
        lllllllllllllllllIIlIIllIIlIIlIl.voidHoles = new ArrayList<BlockPos>();
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIIlIIlIllllIllI) {
        VoidESP lllllllllllllllllIIlIIlIllllIlIl;
        lllllllllllllllllIIlIIlIllllIlIl.getHoles(lllllllllllllllllIIlIIlIllllIlIl.horizontalRadius.get(), lllllllllllllllllIIlIIlIllllIlIl.holeHeight.get());
    }

    private void getHoles(int lllllllllllllllllIIlIIllIIIIlIIl, int lllllllllllllllllIIlIIllIIIIllIl) {
        VoidESP lllllllllllllllllIIlIIllIIIIllll;
        lllllllllllllllllIIlIIllIIIIllll.voidHoles.clear();
        if (Utils.getDimension() == Dimension.End) {
            return;
        }
        BlockPos lllllllllllllllllIIlIIllIIIIllII = lllllllllllllllllIIlIIllIIIIllll.mc.player.getBlockPos();
        int lllllllllllllllllIIlIIllIIIIlIll = lllllllllllllllllIIlIIllIIIIllII.getY();
        for (int lllllllllllllllllIIlIIllIIIlIIII = -lllllllllllllllllIIlIIllIIIIlIIl; lllllllllllllllllIIlIIllIIIlIIII < lllllllllllllllllIIlIIllIIIIlIIl; ++lllllllllllllllllIIlIIllIIIlIIII) {
            for (int lllllllllllllllllIIlIIllIIIlIIIl = -lllllllllllllllllIIlIIllIIIIlIIl; lllllllllllllllllIIlIIllIIIlIIIl < lllllllllllllllllIIlIIllIIIIlIIl; ++lllllllllllllllllIIlIIllIIIlIIIl) {
                BlockPos lllllllllllllllllIIlIIllIIIlIIll = lllllllllllllllllIIlIIllIIIIllII.add(lllllllllllllllllIIlIIllIIIlIIII, -lllllllllllllllllIIlIIllIIIIlIll, lllllllllllllllllIIlIIllIIIlIIIl);
                int lllllllllllllllllIIlIIllIIIlIIlI = 0;
                for (int lllllllllllllllllIIlIIllIIIlIlll = 0; lllllllllllllllllIIlIIllIIIlIlll < lllllllllllllllllIIlIIllIIIIllIl; ++lllllllllllllllllIIlIIllIIIlIlll) {
                    if (!lllllllllllllllllIIlIIllIIIIllll.isBlockMatching(lllllllllllllllllIIlIIllIIIIllll.mc.world.getBlockState(lllllllllllllllllIIlIIllIIIlIIll.add(0, lllllllllllllllllIIlIIllIIIlIlll, 0)).getBlock())) continue;
                    ++lllllllllllllllllIIlIIllIIIlIIlI;
                }
                if (lllllllllllllllllIIlIIllIIIlIIlI >= lllllllllllllllllIIlIIllIIIIllIl) {
                    lllllllllllllllllIIlIIllIIIIllll.voidHoles.add(lllllllllllllllllIIlIIllIIIlIIll);
                }
                if (Utils.getDimension() != Dimension.Nether) continue;
                BlockPos lllllllllllllllllIIlIIllIIIlIlIl = lllllllllllllllllIIlIIllIIIIllII.add(lllllllllllllllllIIlIIllIIIlIIII, 127 - lllllllllllllllllIIlIIllIIIIlIll, lllllllllllllllllIIlIIllIIIlIIIl);
                int lllllllllllllllllIIlIIllIIIlIlII = 0;
                for (int lllllllllllllllllIIlIIllIIIlIllI = 0; lllllllllllllllllIIlIIllIIIlIllI < lllllllllllllllllIIlIIllIIIIllIl; ++lllllllllllllllllIIlIIllIIIlIllI) {
                    if (!lllllllllllllllllIIlIIllIIIIllll.isBlockMatching(lllllllllllllllllIIlIIllIIIIllll.mc.world.getBlockState(lllllllllllllllllIIlIIllIIIlIIll.add(0, 127 - lllllllllllllllllIIlIIllIIIlIllI, 0)).getBlock())) continue;
                    ++lllllllllllllllllIIlIIllIIIlIlII;
                }
                if (lllllllllllllllllIIlIIllIIIlIlII < lllllllllllllllllIIlIIllIIIIllIl) continue;
                lllllllllllllllllIIlIIllIIIIllll.voidHoles.add(lllllllllllllllllIIlIIllIIIlIlIl);
            }
        }
    }

    private boolean isBlockMatching(Block lllllllllllllllllIIlIIlIlllllIll) {
        VoidESP lllllllllllllllllIIlIIlIllllllII;
        if (lllllllllllllllllIIlIIlIllllllII.airOnly.get().booleanValue()) {
            return lllllllllllllllllIIlIIlIlllllIll == Blocks.AIR;
        }
        return lllllllllllllllllIIlIIlIlllllIll != Blocks.BEDROCK;
    }
}

