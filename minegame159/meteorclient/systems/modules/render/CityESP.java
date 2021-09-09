/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.CityUtils;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.math.BlockPos;

public class CityESP
extends Module {
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Double> range;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> sideColor;

    public CityESP() {
        super(Categories.Render, "city-esp", "Displays blocks that can be broken in order to city another player.");
        CityESP lllllllllllllllllIIlIlIlIIlIllll;
        lllllllllllllllllIIlIlIlIIlIllll.sgGeneral = lllllllllllllllllIIlIlIlIIlIllll.settings.getDefaultGroup();
        lllllllllllllllllIIlIlIlIIlIllll.sgRender = lllllllllllllllllIIlIlIlIIlIllll.settings.createGroup("Render");
        lllllllllllllllllIIlIlIlIIlIllll.range = lllllllllllllllllIIlIlIlIIlIllll.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum range a city-able block will be found.").defaultValue(5.0).min(0.0).sliderMax(20.0).build());
        lllllllllllllllllIIlIlIlIIlIllll.shapeMode = lllllllllllllllllIIlIlIlIIlIllll.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllIIlIlIlIIlIllll.sideColor = lllllllllllllllllIIlIlIlIIlIllll.sgRender.add(new ColorSetting.Builder().name("fill-color").description("The fill color the city block will render as.").defaultValue(new SettingColor(225, 0, 0, 75)).build());
        lllllllllllllllllIIlIlIlIIlIllll.lineColor = lllllllllllllllllIIlIlIlIIlIllll.sgRender.add(new ColorSetting.Builder().name("outline-color").description("The line color the city block will render as.").defaultValue(new SettingColor(225, 0, 0, 255)).build());
    }

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIIlIlIlIIlIlIll) {
        CityESP lllllllllllllllllIIlIlIlIIlIlIIl;
        BlockPos lllllllllllllllllIIlIlIlIIlIlIlI = CityUtils.getTargetBlock(CityUtils.getPlayerTarget(lllllllllllllllllIIlIlIlIIlIlIIl.range.get()));
        if (lllllllllllllllllIIlIlIlIIlIlIlI == null) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllIIlIlIlIIlIlIlI, lllllllllllllllllIIlIlIlIIlIlIIl.sideColor.get(), lllllllllllllllllIIlIlIlIIlIlIIl.lineColor.get(), lllllllllllllllllIIlIlIlIIlIlIIl.shapeMode.get(), 0);
    }
}

