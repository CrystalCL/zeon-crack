/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
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
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1747;
import net.minecraft.class_2338;
import net.minecraft.class_3965;

public class AirPlace
extends Module {
    private final Setting<ShapeMode> shapeMode;
    private final Setting<SettingColor> lineColor;
    private final SettingGroup sgGeneral;
    private class_2338 target;
    private final Setting<Place> placeWhen;
    private final SettingGroup sgRender;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> sideColor;

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!(this.mc.field_1765 instanceof class_3965 && this.mc.field_1687.method_8320(this.target).method_26215() && this.mc.field_1724.method_6047().method_7909() instanceof class_1747 && this.render.get().booleanValue())) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.target, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    public AirPlace() {
        super(Categories.Player, "air-place", "Places a block where your crosshair is pointing at.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.placeWhen = this.sgGeneral.add(new EnumSetting.Builder().name("place-when").description("Decides when it should place.").defaultValue(Place.OnClick).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!(this.mc.field_1765 instanceof class_3965) || !(this.mc.field_1724.method_6047().method_7909() instanceof class_1747)) {
            return;
        }
        this.target = ((class_3965)this.mc.field_1765).method_17777();
        if (!this.mc.field_1687.method_8320(this.target).method_26215()) {
            return;
        }
        if (this.placeWhen.get() == Place.Always || this.placeWhen.get() == Place.OnClick && (this.mc.field_1690.field_1904.method_1436() || this.mc.field_1690.field_1904.method_1434())) {
            BlockUtils.place(this.target, class_1268.field_5808, 0, false, 0, true, true, false, false);
        }
    }

    @Override
    public void onActivate() {
        this.target = this.mc.field_1724.method_24515().method_10069(4, 2, 0);
    }

    public static final class Place
    extends Enum<Place> {
        private static final Place[] $VALUES;
        public static final /* enum */ Place Always;
        public static final /* enum */ Place OnClick;

        private static Place[] $values() {
            return new Place[]{OnClick, Always};
        }

        static {
            OnClick = new Place();
            Always = new Place();
            $VALUES = Place.$values();
        }

        public static Place[] values() {
            return (Place[])$VALUES.clone();
        }

        public static Place valueOf(String string) {
            return Enum.valueOf(Place.class, string);
        }
    }
}

