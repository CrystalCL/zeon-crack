/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockIterator;
import net.minecraft.class_1922;
import net.minecraft.class_1944;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_259;
import net.minecraft.class_2680;
import net.minecraft.class_290;

public class LightOverlay
extends Module {
    private final Setting<SettingColor> color;
    private final Pool<Cross> crossPool;
    private final MeshBuilder mb;
    private final Setting<Boolean> seeThroughBlocks;
    private final SettingGroup sgColors;
    private final List<Cross> crosses;
    private final class_2338.class_2339 bp;
    private final Setting<SettingColor> potentialColor;
    private final SettingGroup sgGeneral;
    private final Setting<Integer> verticalRange;
    private final Setting<Integer> horizontalRange;

    static Setting access$000(LightOverlay lightOverlay) {
        return lightOverlay.potentialColor;
    }

    private void lambda$onTick$1(class_2338 class_23382, class_2680 class_26802) {
        if (!class_26802.method_26220((class_1922)this.mc.field_1687, class_23382).method_1110()) {
            return;
        }
        this.bp.method_10101((class_2382)class_23382).method_10100(0, -1, 0);
        if (this.mc.field_1687.method_8320((class_2338)this.bp).method_26220((class_1922)this.mc.field_1687, (class_2338)this.bp) != class_259.method_1077()) {
            return;
        }
        if (this.mc.field_1687.method_22346(class_23382, 0) <= 7) {
            this.crosses.add(this.crossPool.get().set(class_23382, false));
        } else if (this.mc.field_1687.method_8314(class_1944.field_9282, class_23382) <= 7) {
            this.crosses.add(this.crossPool.get().set(class_23382, true));
        }
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.crosses.isEmpty()) {
            return;
        }
        this.mb.depthTest = this.seeThroughBlocks.get() == false;
        this.mb.begin(renderEvent, DrawMode.Lines, class_290.field_1576);
        for (Cross cross : this.crosses) {
            cross.render();
        }
        this.mb.end();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        for (Cross cross : this.crosses) {
            this.crossPool.free(cross);
        }
        this.crosses.clear();
        BlockIterator.register(this.horizontalRange.get(), this.verticalRange.get(), this::lambda$onTick$1);
    }

    static MeshBuilder access$200(LightOverlay lightOverlay) {
        return lightOverlay.mb;
    }

    private Cross lambda$new$0() {
        return new Cross(this, null);
    }

    static Setting access$100(LightOverlay lightOverlay) {
        return lightOverlay.color;
    }

    public LightOverlay() {
        super(Categories.Render, "light-overlay", "Shows blocks where mobs can spawn.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgColors = this.settings.createGroup("Colors");
        this.horizontalRange = this.sgGeneral.add(new IntSetting.Builder().name("horizontal-range").description("Horizontal range in blocks.").defaultValue(8).min(0).build());
        this.verticalRange = this.sgGeneral.add(new IntSetting.Builder().name("vertical-range").description("Vertical range in blocks.").defaultValue(4).min(0).build());
        this.seeThroughBlocks = this.sgGeneral.add(new BoolSetting.Builder().name("see-through-blocks").description("Allows you to see the lines through blocks.").defaultValue(false).build());
        this.color = this.sgColors.add(new ColorSetting.Builder().name("color").description("Color of places where mobs can currently spawn.").defaultValue(new SettingColor(225, 25, 25)).build());
        this.potentialColor = this.sgColors.add(new ColorSetting.Builder().name("potential-color").description("Color of places where mobs can potentially spawn (eg at night).").defaultValue(new SettingColor(225, 225, 25)).build());
        this.crossPool = new Pool<Cross>(this::lambda$new$0);
        this.crosses = new ArrayList<Cross>();
        this.bp = new class_2338.class_2339();
        this.mb = new MeshBuilder();
    }

    private class Cross {
        private double y;
        private boolean potential;
        final LightOverlay this$0;
        private double z;
        private double x;

        public Cross set(class_2338 class_23382, boolean bl) {
            this.x = class_23382.method_10263();
            this.y = (double)class_23382.method_10264() + 0.0075;
            this.z = class_23382.method_10260();
            this.potential = bl;
            return this;
        }

        private Cross(LightOverlay lightOverlay) {
            this.this$0 = lightOverlay;
        }

        public void render() {
            Color color = this.potential ? (Color)LightOverlay.access$000(this.this$0).get() : (Color)LightOverlay.access$100(this.this$0).get();
            LightOverlay.access$200(this.this$0).line(this.x, this.y, this.z, this.x + 1.0, this.y, this.z + 1.0, color);
            LightOverlay.access$200(this.this$0).line(this.x + 1.0, this.y, this.z, this.x, this.y, this.z + 1.0, color);
        }

        Cross(LightOverlay lightOverlay, 1 var2_2) {
            this(lightOverlay);
        }
    }
}

