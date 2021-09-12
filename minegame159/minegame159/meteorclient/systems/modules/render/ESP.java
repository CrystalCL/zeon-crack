/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_238;

public class ESP
extends Module {
    private final Setting<SettingColor> waterAnimalsColor;
    private final Setting<Object2BooleanMap<class_1299<?>>> entities;
    private int count;
    private final Color outlineColor;
    private final Setting<SettingColor> monstersColor;
    private final SettingGroup sgColors;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Double> fadeDistance;
    private final Setting<SettingColor> playersColor;
    private final Color sideColor;
    private final Setting<SettingColor> ambientColor;
    private final SettingGroup sgGeneral;
    public final Setting<Boolean> showInvis;
    private final Setting<SettingColor> miscColor;
    private static final MeshBuilder MB = new MeshBuilder(128);
    private final Setting<SettingColor> animalsColor;
    public final Setting<Boolean> useNameColor;
    private final Setting<Mode> mode;

    public ESP() {
        super(Categories.Render, "esp", "Renders entities through walls.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgColors = this.settings.createGroup("Colors");
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Rendering mode.").defaultValue(Mode.Outline).build());
        this.shapeMode = this.sgGeneral.add(new EnumSetting.Builder().name("box-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entites").description("Select specific entities.").defaultValue((Object2BooleanMap<class_1299<?>>)Utils.asObject2BooleanOpenHashMap(class_1299.field_6097)).build());
        this.showInvis = this.sgGeneral.add(new BoolSetting.Builder().name("show-invisible").description("Shows invisibile entities.").defaultValue(true).build());
        this.useNameColor = this.sgColors.add(new BoolSetting.Builder().name("use-name-color").description("Uses players displayname color for the ESP color (good for minigames).").defaultValue(false).build());
        this.playersColor = this.sgColors.add(new ColorSetting.Builder().name("players-color").description("The other player's color.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.animalsColor = this.sgColors.add(new ColorSetting.Builder().name("animals-color").description("The animal's color.").defaultValue(new SettingColor(25, 255, 25, 255)).build());
        this.waterAnimalsColor = this.sgColors.add(new ColorSetting.Builder().name("water-animals-color").description("The water animal's color.").defaultValue(new SettingColor(25, 25, 255, 255)).build());
        this.monstersColor = this.sgColors.add(new ColorSetting.Builder().name("monsters-color").description("The monster's color.").defaultValue(new SettingColor(255, 25, 25, 255)).build());
        this.ambientColor = this.sgColors.add(new ColorSetting.Builder().name("ambient-color").description("The ambient's color.").defaultValue(new SettingColor(25, 25, 25, 255)).build());
        this.miscColor = this.sgColors.add(new ColorSetting.Builder().name("misc-color").description("The misc color.").defaultValue(new SettingColor(175, 175, 175, 255)).build());
        this.fadeDistance = this.sgGeneral.add(new DoubleSetting.Builder().name("fade-distance").description("The distance where the color fades.").defaultValue(6.0).min(0.0).sliderMax(12.0).build());
        this.sideColor = new Color();
        this.outlineColor = new Color();
        ESP.MB.texture = true;
    }

    private void setSideColor(Color color) {
        this.sideColor.set(color);
        this.sideColor.a = 25;
    }

    private void render(RenderEvent renderEvent, class_1297 class_12972, Color color) {
        this.setSideColor(color);
        double d = this.mc.field_1719.method_5649(class_12972.method_23317() + (double)(class_12972.method_17681() / 2.0f), class_12972.method_23318() + (double)(class_12972.method_17682() / 2.0f), class_12972.method_23321() + (double)(class_12972.method_17681() / 2.0f));
        double d2 = 1.0;
        if (d <= this.fadeDistance.get() * this.fadeDistance.get()) {
            d2 = d / (this.fadeDistance.get() * this.fadeDistance.get());
        }
        int n = color.a;
        int n2 = this.sideColor.a;
        color.a = (int)((double)color.a * d2);
        this.sideColor.a = (int)((double)this.sideColor.a * d2);
        if (d2 >= 0.075) {
            double d3 = (class_12972.method_23317() - class_12972.field_6014) * (double)renderEvent.tickDelta;
            double d4 = (class_12972.method_23318() - class_12972.field_6036) * (double)renderEvent.tickDelta;
            double d5 = (class_12972.method_23321() - class_12972.field_5969) * (double)renderEvent.tickDelta;
            class_238 class_2383 = class_12972.method_5829();
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d3 + class_2383.field_1323, d4 + class_2383.field_1322, d5 + class_2383.field_1321, d3 + class_2383.field_1320, d4 + class_2383.field_1325, d5 + class_2383.field_1324, this.sideColor, color, this.shapeMode.get(), 0);
        }
        color.a = n;
        this.sideColor.a = n2;
    }

    @Override
    public String getInfoString() {
        return Integer.toString(this.count);
    }

    public Color getOutlineColor(class_1297 class_12972) {
        if (!this.entities.get().getBoolean((Object)class_12972.method_5864())) {
            return null;
        }
        Color color = this.getColor(class_12972);
        double d = this.mc.field_1719.method_5649(class_12972.method_23317() + (double)(class_12972.method_17681() / 2.0f), class_12972.method_23318() + (double)(class_12972.method_17682() / 2.0f), class_12972.method_23321() + (double)(class_12972.method_17681() / 2.0f));
        double d2 = 1.0;
        if (d <= this.fadeDistance.get() * this.fadeDistance.get()) {
            d2 = d / (this.fadeDistance.get() * this.fadeDistance.get());
        }
        if (d2 >= 0.075) {
            this.outlineColor.set(color);
            this.outlineColor.a = (int)((double)this.outlineColor.a * d2);
            return this.outlineColor;
        }
        return null;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        this.count = 0;
        for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
            if (!Modules.get().isActive(Freecam.class) && class_12972 == this.mc.field_1724 || !this.entities.get().getBoolean((Object)class_12972.method_5864()) || !EntityUtils.isInRenderDistance(class_12972)) continue;
            if (this.mode.get() == Mode.Box) {
                this.render(renderEvent, class_12972, this.getColor(class_12972));
            }
            ++this.count;
        }
    }

    public Color getColor(class_1297 class_12972) {
        return EntityUtils.getEntityColor(class_12972, this.playersColor.get(), this.animalsColor.get(), this.waterAnimalsColor.get(), this.monstersColor.get(), this.ambientColor.get(), this.miscColor.get(), this.useNameColor.get());
    }

    public boolean isOutline() {
        return this.mode.get() == Mode.Outline;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Outline;
        public static final /* enum */ Mode Box;
        private static final Mode[] $VALUES;

        private static Mode[] $values() {
            return new Mode[]{Box, Outline};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Box = new Mode();
            Outline = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }
    }
}

