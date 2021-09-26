/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayDeque;
import java.util.Queue;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.client.MinecraftClient;

public class Breadcrumbs
extends Module {
    private Section section;
    private DimensionType lastDimension;
    private final Pool<Section> sectionPool;
    private final Setting<Integer> maxSections;
    private final Setting<Double> sectionLength;
    private final Setting<SettingColor> color;
    private final SettingGroup sgGeneral;
    private final Queue<Section> sections;

    static MinecraftClient access$500(Breadcrumbs breadcrumbs) {
        return breadcrumbs.mc;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        for (Section section : this.sections) {
            section.render();
        }
    }

    private boolean isFarEnough(double d, double d2, double d3) {
        return Math.abs(this.mc.player.getX() - d) >= this.sectionLength.get() || Math.abs(this.mc.player.getY() - d2) >= this.sectionLength.get() || Math.abs(this.mc.player.getZ() - d3) >= this.sectionLength.get();
    }

    public Breadcrumbs() {
        super(Categories.Render, "breadcrumbs", "Displays a trail behind where you have walked.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.color = this.sgGeneral.add(new ColorSetting.Builder().name("color").description("The color of the Breadcrumbs trail.").defaultValue(new SettingColor(225, 25, 25)).build());
        this.maxSections = this.sgGeneral.add(new IntSetting.Builder().name("max-sections").description("The maximum number of sections.").defaultValue(1000).min(1).sliderMin(1).sliderMax(5000).build());
        this.sectionLength = this.sgGeneral.add(new DoubleSetting.Builder().name("section-length").description("The section length in blocks.").defaultValue(0.5).min(0.0).sliderMin(0.0).sliderMax(1.0).build());
        this.sectionPool = new Pool<Section>(this::lambda$new$0);
        this.sections = new ArrayDeque<Section>();
    }

    static MinecraftClient access$200(Breadcrumbs breadcrumbs) {
        return breadcrumbs.mc;
    }

    static MinecraftClient access$100(Breadcrumbs breadcrumbs) {
        return breadcrumbs.mc;
    }

    static Setting access$600(Breadcrumbs breadcrumbs) {
        return breadcrumbs.color;
    }

    @Override
    public void onActivate() {
        this.section = this.sectionPool.get();
        this.section.set1();
        this.lastDimension = this.mc.world.getDimension();
    }

    static MinecraftClient access$400(Breadcrumbs breadcrumbs) {
        return breadcrumbs.mc;
    }

    private Section lambda$new$0() {
        return new Section(this, null);
    }

    static MinecraftClient access$300(Breadcrumbs breadcrumbs) {
        return breadcrumbs.mc;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        Object object;
        if (this.lastDimension != this.mc.world.getDimension()) {
            object = this.sections.iterator();
            while (object.hasNext()) {
                Section section = (Section)object.next();
                this.sectionPool.free(section);
            }
            this.sections.clear();
        }
        if (this.isFarEnough(this.section.x1, this.section.y1, this.section.z1)) {
            this.section.set2();
            if (this.sections.size() >= this.maxSections.get() && (object = this.sections.poll()) != null) {
                this.sectionPool.free((Section)object);
            }
            this.sections.add(this.section);
            this.section = this.sectionPool.get();
            this.section.set1();
        }
        this.lastDimension = this.mc.world.getDimension();
    }

    @Override
    public void onDeactivate() {
        for (Section section : this.sections) {
            this.sectionPool.free(section);
        }
        this.sections.clear();
    }

    static MinecraftClient access$000(Breadcrumbs breadcrumbs) {
        return breadcrumbs.mc;
    }

    private class Section {
        public float x2;
        public float y1;
        public float z2;
        public float y2;
        public float z1;
        final Breadcrumbs this$0;
        public float x1;

        public void set1() {
            this.x1 = (float)Breadcrumbs.access$000((Breadcrumbs)this.this$0).player.getX();
            this.y1 = (float)Breadcrumbs.access$100((Breadcrumbs)this.this$0).player.getY();
            this.z1 = (float)Breadcrumbs.access$200((Breadcrumbs)this.this$0).player.getZ();
        }

        Section(Breadcrumbs breadcrumbs, 1 var2_2) {
            this(breadcrumbs);
        }

        private Section(Breadcrumbs breadcrumbs) {
            this.this$0 = breadcrumbs;
        }

        public void set2() {
            this.x2 = (float)Breadcrumbs.access$300((Breadcrumbs)this.this$0).player.getX();
            this.y2 = (float)Breadcrumbs.access$400((Breadcrumbs)this.this$0).player.getY();
            this.z2 = (float)Breadcrumbs.access$500((Breadcrumbs)this.this$0).player.getZ();
        }

        public void render() {
            Renderer.LINES.line(this.x1, this.y1, this.z1, this.x2, this.y2, this.z2, (Color)Breadcrumbs.access$600(this.this$0).get());
        }
    }
}

