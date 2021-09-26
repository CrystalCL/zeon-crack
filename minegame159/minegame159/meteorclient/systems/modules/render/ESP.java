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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Box;

public class ESP
extends Module {
    private final Setting<SettingColor> waterAnimalsColor;
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
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
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entites").description("Select specific entities.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(EntityType.PLAYER)).build());
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

    private void render(RenderEvent renderEvent, Entity Entity2, Color color) {
        this.setSideColor(color);
        double d = this.mc.cameraEntity.squaredDistanceTo(Entity2.getX() + (double)(Entity2.getWidth() / 2.0f), Entity2.getY() + (double)(Entity2.getHeight() / 2.0f), Entity2.getZ() + (double)(Entity2.getWidth() / 2.0f));
        double d2 = 1.0;
        if (d <= this.fadeDistance.get() * this.fadeDistance.get()) {
            d2 = d / (this.fadeDistance.get() * this.fadeDistance.get());
        }
        int n = color.a;
        int n2 = this.sideColor.a;
        color.a = (int)((double)color.a * d2);
        this.sideColor.a = (int)((double)this.sideColor.a * d2);
        if (d2 >= 0.075) {
            double d3 = (Entity2.getX() - Entity2.prevX) * (double)renderEvent.tickDelta;
            double d4 = (Entity2.getY() - Entity2.prevY) * (double)renderEvent.tickDelta;
            double d5 = (Entity2.getZ() - Entity2.prevZ) * (double)renderEvent.tickDelta;
            Box Box3 = Entity2.getBoundingBox();
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d3 + Box3.minX, d4 + Box3.minY, d5 + Box3.minZ, d3 + Box3.maxX, d4 + Box3.maxY, d5 + Box3.maxZ, this.sideColor, color, this.shapeMode.get(), 0);
        }
        color.a = n;
        this.sideColor.a = n2;
    }

    @Override
    public String getInfoString() {
        return Integer.toString(this.count);
    }

    public Color getOutlineColor(Entity Entity2) {
        if (!this.entities.get().getBoolean((Object)Entity2.getType())) {
            return null;
        }
        Color color = this.getColor(Entity2);
        double d = this.mc.cameraEntity.squaredDistanceTo(Entity2.getX() + (double)(Entity2.getWidth() / 2.0f), Entity2.getY() + (double)(Entity2.getHeight() / 2.0f), Entity2.getZ() + (double)(Entity2.getWidth() / 2.0f));
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
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!Modules.get().isActive(Freecam.class) && Entity2 == this.mc.player || !this.entities.get().getBoolean((Object)Entity2.getType()) || !EntityUtils.isInRenderDistance(Entity2)) continue;
            if (this.mode.get() == Mode.Box) {
                this.render(renderEvent, Entity2, this.getColor(Entity2));
            }
            ++this.count;
        }
    }

    public Color getColor(Entity Entity2) {
        return EntityUtils.getEntityColor(Entity2, this.playersColor.get(), this.animalsColor.get(), this.waterAnimalsColor.get(), this.monstersColor.get(), this.ambientColor.get(), this.miscColor.get(), this.useNameColor.get());
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

