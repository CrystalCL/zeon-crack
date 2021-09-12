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
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;

public class VoidESP
extends Module {
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Integer> holeHeight;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> airOnly;
    private final Setting<Integer> horizontalRadius;
    private final List<class_2338> voidHoles;
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
        this.voidHoles = new ArrayList<class_2338>();
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        for (class_2338 class_23382 : this.voidHoles) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, class_23382, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
        }
    }

    private void getHoles(int n, int n2) {
        this.voidHoles.clear();
        if (Utils.getDimension() == Dimension.End) {
            return;
        }
        class_2338 class_23382 = this.mc.field_1724.method_24515();
        int n3 = class_23382.method_10264();
        for (int i = -n; i < n; ++i) {
            for (int j = -n; j < n; ++j) {
                class_2338 class_23383 = class_23382.method_10069(i, -n3, j);
                int n4 = 0;
                for (int k = 0; k < n2; ++k) {
                    if (!this.isBlockMatching(this.mc.field_1687.method_8320(class_23383.method_10069(0, k, 0)).method_26204())) continue;
                    ++n4;
                    if (!false) continue;
                    return;
                }
                if (n4 >= n2) {
                    this.voidHoles.add(class_23383);
                }
                if (Utils.getDimension() != Dimension.Nether) continue;
                class_2338 class_23384 = class_23382.method_10069(i, 127 - n3, j);
                int n5 = 0;
                for (int k = 0; k < n2; ++k) {
                    if (!this.isBlockMatching(this.mc.field_1687.method_8320(class_23383.method_10069(0, 127 - k, 0)).method_26204())) continue;
                    ++n5;
                    if (null == null) continue;
                    return;
                }
                if (n5 < n2) continue;
                this.voidHoles.add(class_23384);
            }
            if (-1 <= 0) continue;
            return;
        }
    }

    private boolean isBlockMatching(class_2248 class_22482) {
        if (this.airOnly.get().booleanValue()) {
            return class_22482 == class_2246.field_10124;
        }
        return class_22482 != class_2246.field_9987;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.getHoles(this.horizontalRadius.get(), this.holeHeight.get());
    }
}

