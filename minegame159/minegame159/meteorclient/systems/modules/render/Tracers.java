/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.Target;
import minegame159.meteorclient.utils.render.RenderUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1657;

public class Tracers
extends Module {
    public final Setting<Boolean> distance;
    private final Setting<Object2BooleanMap<class_1299<?>>> entities;
    private final Setting<Integer> maxDist;
    public final Setting<Boolean> showInvis;
    private final Setting<SettingColor> waterAnimalsColor;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> miscColor;
    private final Setting<Target> target;
    private final Setting<SettingColor> playersColor;
    private final Setting<SettingColor> animalsColor;
    private final Setting<SettingColor> monstersColor;
    private final Setting<Boolean> stem;
    private final SettingGroup sgAppearance;
    private final SettingGroup sgColors;
    private final Color distanceColor;
    private final Setting<SettingColor> ambientColor;
    private int count;
    public final Setting<Boolean> useNameColor;

    @Override
    public String getInfoString() {
        return Integer.toString(this.count);
    }

    public Tracers() {
        super(Categories.Render, "tracers", "Displays tracer lines to specified entities.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgAppearance = this.settings.createGroup("Appearance");
        this.sgColors = this.settings.createGroup("Colors");
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entites").description("Select specific entities.").defaultValue((Object2BooleanMap<class_1299<?>>)Utils.asObject2BooleanOpenHashMap(class_1299.field_6097)).build());
        this.target = this.sgAppearance.add(new EnumSetting.Builder().name("target").description("What part of the entity to target.").defaultValue(Target.Body).build());
        this.stem = this.sgAppearance.add(new BoolSetting.Builder().name("stem").description("Draw a line through the center of the tracer target.").defaultValue(true).build());
        this.maxDist = this.sgAppearance.add(new IntSetting.Builder().name("max-distance").description("Maximum distance for tracers to show.").defaultValue(256).min(0).sliderMax(256).build());
        this.showInvis = this.sgGeneral.add(new BoolSetting.Builder().name("show-invisible").description("Shows invisibile entities.").defaultValue(true).build());
        this.distance = this.sgColors.add(new BoolSetting.Builder().name("distance-colors").description("Changes the color of tracers depending on distance.").defaultValue(false).build());
        this.useNameColor = this.sgColors.add(new BoolSetting.Builder().name("use-name-color").description("Uses players displayname color for the tracer color (good for minigames).").defaultValue(false).build());
        this.playersColor = this.sgColors.add(new ColorSetting.Builder().name("players-colors").description("The player's color.").defaultValue(new SettingColor(205, 205, 205, 127)).build());
        this.animalsColor = this.sgColors.add(new ColorSetting.Builder().name("animals-color").description("The animal's color.").defaultValue(new SettingColor(145, 255, 145, 127)).build());
        this.waterAnimalsColor = this.sgColors.add(new ColorSetting.Builder().name("water-animals-color").description("The water animal's color.").defaultValue(new SettingColor(145, 145, 255, 127)).build());
        this.monstersColor = this.sgColors.add(new ColorSetting.Builder().name("monsters-color").description("The monster's color.").defaultValue(new SettingColor(255, 145, 145, 127)).build());
        this.ambientColor = this.sgColors.add(new ColorSetting.Builder().name("ambient-color").description("The ambient color.").defaultValue(new SettingColor(75, 75, 75, 127)).build());
        this.miscColor = this.sgColors.add(new ColorSetting.Builder().name("misc-color").description("The misc color.").defaultValue(new SettingColor(145, 145, 145, 127)).build());
        this.distanceColor = new Color(255, 255, 255);
    }

    private Color getColorFromDistance(class_1657 class_16572) {
        int n;
        int n2;
        double d = this.mc.field_1724.method_5739((class_1297)class_16572);
        double d2 = d / 60.0;
        if (d2 < 0.0 || d2 > 1.0) {
            this.distanceColor.set(0, 255, 0, 255);
            return this.distanceColor;
        }
        if (d2 < 0.5) {
            n2 = 255;
            n = (int)(255.0 * d2 / 0.5);
        } else {
            n = 255;
            n2 = 255 - (int)(255.0 * (d2 - 0.5) / 0.5);
        }
        this.distanceColor.set(n2, n, 0, 255);
        return this.distanceColor;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        this.count = 0;
        for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
            if (this.mc.field_1724.method_5739(class_12972) > (float)this.maxDist.get().intValue() || class_12972 instanceof class_1657 && Friends.get().get((class_1657)class_12972) != null && !Friends.get().show((class_1657)class_12972) || !Modules.get().isActive(Freecam.class) && class_12972 == this.mc.field_1724 || !this.entities.get().getBoolean((Object)class_12972.method_5864()) || !this.showInvis.get().booleanValue() && class_12972.method_5767() || !EntityUtils.isInRenderDistance(class_12972)) continue;
            Color color = this.distance.get() == false || !(class_12972 instanceof class_1657) || Friends.get().contains(Friends.get().get((class_1657)class_12972)) ? EntityUtils.getEntityColor(class_12972, this.playersColor.get(), this.animalsColor.get(), this.waterAnimalsColor.get(), this.monstersColor.get(), this.ambientColor.get(), this.miscColor.get(), this.useNameColor.get()) : this.getColorFromDistance((class_1657)class_12972);
            RenderUtils.drawTracerToEntity(renderEvent, class_12972, color, this.target.get(), this.stem.get());
            ++this.count;
        }
    }
}

