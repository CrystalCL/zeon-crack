/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.dimension.DimensionType
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

public class Breadcrumbs
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ Section section;
    private final /* synthetic */ Setting<Integer> maxSections;
    private /* synthetic */ DimensionType lastDimension;
    private final /* synthetic */ Setting<SettingColor> color;
    private final /* synthetic */ Queue<Section> sections;
    private final /* synthetic */ Setting<Double> sectionLength;
    private final /* synthetic */ Pool<Section> sectionPool;

    @Override
    public void onDeactivate() {
        Breadcrumbs lllllllllllllllllIIlIlllIllIIIII;
        for (Section lllllllllllllllllIIlIlllIllIIIIl : lllllllllllllllllIIlIlllIllIIIII.sections) {
            lllllllllllllllllIIlIlllIllIIIII.sectionPool.free(lllllllllllllllllIIlIlllIllIIIIl);
        }
        lllllllllllllllllIIlIlllIllIIIII.sections.clear();
    }

    @Override
    public void onActivate() {
        Breadcrumbs lllllllllllllllllIIlIlllIllIIlIl;
        lllllllllllllllllIIlIlllIllIIlIl.section = lllllllllllllllllIIlIlllIllIIlIl.sectionPool.get();
        lllllllllllllllllIIlIlllIllIIlIl.section.set1();
        lllllllllllllllllIIlIlllIllIIlIl.lastDimension = lllllllllllllllllIIlIlllIllIIlIl.mc.world.getDimension();
    }

    public Breadcrumbs() {
        super(Categories.Render, "breadcrumbs", "Displays a trail behind where you have walked.");
        Breadcrumbs lllllllllllllllllIIlIlllIllIllIl;
        lllllllllllllllllIIlIlllIllIllIl.sgGeneral = lllllllllllllllllIIlIlllIllIllIl.settings.getDefaultGroup();
        lllllllllllllllllIIlIlllIllIllIl.color = lllllllllllllllllIIlIlllIllIllIl.sgGeneral.add(new ColorSetting.Builder().name("color").description("The color of the Breadcrumbs trail.").defaultValue(new SettingColor(225, 25, 25)).build());
        lllllllllllllllllIIlIlllIllIllIl.maxSections = lllllllllllllllllIIlIlllIllIllIl.sgGeneral.add(new IntSetting.Builder().name("max-sections").description("The maximum number of sections.").defaultValue(1000).min(1).sliderMin(1).sliderMax(5000).build());
        lllllllllllllllllIIlIlllIllIllIl.sectionLength = lllllllllllllllllIIlIlllIllIllIl.sgGeneral.add(new DoubleSetting.Builder().name("section-length").description("The section length in blocks.").defaultValue(0.5).min(0.0).sliderMin(0.0).sliderMax(1.0).build());
        lllllllllllllllllIIlIlllIllIllIl.sectionPool = new Pool<Section>(() -> {
            Breadcrumbs lllllllllllllllllIIlIlllIIlllIll;
            return lllllllllllllllllIIlIlllIIlllIll.new Section();
        });
        lllllllllllllllllIIlIlllIllIllIl.sections = new ArrayDeque<Section>();
    }

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIIlIlllIlIIllIl) {
        Breadcrumbs lllllllllllllllllIIlIlllIlIIllII;
        for (Section lllllllllllllllllIIlIlllIlIIllll : lllllllllllllllllIIlIlllIlIIllII.sections) {
            lllllllllllllllllIIlIlllIlIIllll.render();
        }
    }

    private boolean isFarEnough(double lllllllllllllllllIIlIlllIlIIIIII, double lllllllllllllllllIIlIlllIlIIIIll, double lllllllllllllllllIIlIlllIIlllllI) {
        Breadcrumbs lllllllllllllllllIIlIlllIlIIIIIl;
        return Math.abs(lllllllllllllllllIIlIlllIlIIIIIl.mc.player.getX() - lllllllllllllllllIIlIlllIlIIIIII) >= lllllllllllllllllIIlIlllIlIIIIIl.sectionLength.get() || Math.abs(lllllllllllllllllIIlIlllIlIIIIIl.mc.player.getY() - lllllllllllllllllIIlIlllIlIIIIll) >= lllllllllllllllllIIlIlllIlIIIIIl.sectionLength.get() || Math.abs(lllllllllllllllllIIlIlllIlIIIIIl.mc.player.getZ() - lllllllllllllllllIIlIlllIIlllllI) >= lllllllllllllllllIIlIlllIlIIIIIl.sectionLength.get();
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIIlIlllIlIlIllI) {
        Breadcrumbs lllllllllllllllllIIlIlllIlIlIlIl;
        if (lllllllllllllllllIIlIlllIlIlIlIl.lastDimension != lllllllllllllllllIIlIlllIlIlIlIl.mc.world.getDimension()) {
            for (Section lllllllllllllllllIIlIlllIlIllIIl : lllllllllllllllllIIlIlllIlIlIlIl.sections) {
                lllllllllllllllllIIlIlllIlIlIlIl.sectionPool.free(lllllllllllllllllIIlIlllIlIllIIl);
            }
            lllllllllllllllllIIlIlllIlIlIlIl.sections.clear();
        }
        if (lllllllllllllllllIIlIlllIlIlIlIl.isFarEnough(lllllllllllllllllIIlIlllIlIlIlIl.section.x1, lllllllllllllllllIIlIlllIlIlIlIl.section.y1, lllllllllllllllllIIlIlllIlIlIlIl.section.z1)) {
            Section lllllllllllllllllIIlIlllIlIllIII;
            lllllllllllllllllIIlIlllIlIlIlIl.section.set2();
            if (lllllllllllllllllIIlIlllIlIlIlIl.sections.size() >= lllllllllllllllllIIlIlllIlIlIlIl.maxSections.get() && (lllllllllllllllllIIlIlllIlIllIII = lllllllllllllllllIIlIlllIlIlIlIl.sections.poll()) != null) {
                lllllllllllllllllIIlIlllIlIlIlIl.sectionPool.free(lllllllllllllllllIIlIlllIlIllIII);
            }
            lllllllllllllllllIIlIlllIlIlIlIl.sections.add(lllllllllllllllllIIlIlllIlIlIlIl.section);
            lllllllllllllllllIIlIlllIlIlIlIl.section = lllllllllllllllllIIlIlllIlIlIlIl.sectionPool.get();
            lllllllllllllllllIIlIlllIlIlIlIl.section.set1();
        }
        lllllllllllllllllIIlIlllIlIlIlIl.lastDimension = lllllllllllllllllIIlIlllIlIlIlIl.mc.world.getDimension();
    }

    private class Section {
        public /* synthetic */ float y1;
        public /* synthetic */ float z1;
        public /* synthetic */ float x1;
        public /* synthetic */ float y2;
        public /* synthetic */ float z2;
        public /* synthetic */ float x2;

        public void set2() {
            Section llllllllllllllllIlllIIllllllIIll;
            llllllllllllllllIlllIIllllllIIll.x2 = (float)((Breadcrumbs)llllllllllllllllIlllIIllllllIIll.Breadcrumbs.this).mc.player.getX();
            llllllllllllllllIlllIIllllllIIll.y2 = (float)((Breadcrumbs)llllllllllllllllIlllIIllllllIIll.Breadcrumbs.this).mc.player.getY();
            llllllllllllllllIlllIIllllllIIll.z2 = (float)((Breadcrumbs)llllllllllllllllIlllIIllllllIIll.Breadcrumbs.this).mc.player.getZ();
        }

        private Section() {
            Section llllllllllllllllIlllIIlllllllIlI;
        }

        public void set1() {
            Section llllllllllllllllIlllIIllllllIlll;
            llllllllllllllllIlllIIllllllIlll.x1 = (float)((Breadcrumbs)llllllllllllllllIlllIIllllllIlll.Breadcrumbs.this).mc.player.getX();
            llllllllllllllllIlllIIllllllIlll.y1 = (float)((Breadcrumbs)llllllllllllllllIlllIIllllllIlll.Breadcrumbs.this).mc.player.getY();
            llllllllllllllllIlllIIllllllIlll.z1 = (float)((Breadcrumbs)llllllllllllllllIlllIIllllllIlll.Breadcrumbs.this).mc.player.getZ();
        }

        public void render() {
            Section llllllllllllllllIlllIIllllllIIIl;
            Renderer.LINES.line(llllllllllllllllIlllIIllllllIIIl.x1, llllllllllllllllIlllIIllllllIIIl.y1, llllllllllllllllIlllIIllllllIIIl.z1, llllllllllllllllIlllIIllllllIIIl.x2, llllllllllllllllIlllIIllllllIIIl.y2, llllllllllllllllIlllIIllllllIIIl.z2, (Color)llllllllllllllllIlllIIllllllIIIl.Breadcrumbs.this.color.get());
        }
    }
}

