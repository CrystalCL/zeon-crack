/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.class_2338;

public class CityESP
extends Module {
    private final Setting<SettingColor> sideColor;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Double> range;
    private final Setting<SettingColor> lineColor;
    private final SettingGroup sgRender;
    private final SettingGroup sgGeneral;

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        class_2338 class_23382 = CityUtils.getTargetBlock(CityUtils.getPlayerTarget(this.range.get()));
        if (class_23382 == null) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, class_23382, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    public CityESP() {
        super(Categories.Render, "city-esp", "Displays blocks that can be broken in order to city another player.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum range a city-able block will be found.").defaultValue(5.0).min(0.0).sliderMax(20.0).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("fill-color").description("The fill color the city block will render as.").defaultValue(new SettingColor(225, 0, 0, 75)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("outline-color").description("The line color the city block will render as.").defaultValue(new SettingColor(225, 0, 0, 255)).build());
    }
}

