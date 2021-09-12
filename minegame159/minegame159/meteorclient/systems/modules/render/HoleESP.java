/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.AbstractBlockAccessor;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockIterator;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_2680;

public class HoleESP
extends Module {
    private final SettingGroup sgColors;
    private final Setting<SettingColor> singleMixed;
    private final Setting<SettingColor> singleBedrock;
    private final Setting<SettingColor> doubleMixed;
    private final Setting<SettingColor> doubleObsidian;
    private final Setting<Boolean> webs;
    private final Setting<Double> glowHeight;
    private final Setting<Integer> verticalRadius;
    private final Pool<Hole> holePool;
    private final Setting<Boolean> ignoreOwn;
    private final Setting<Mode> renderMode;
    private final Setting<Boolean> drawOpposite;
    private final Setting<Boolean> doubles;
    private final Setting<Integer> holeHeight;
    private final class_2338.class_2339 blockPos;
    private final Setting<SettingColor> singleObsidian;
    private final Setting<Integer> horizontalRadius;
    private final SettingGroup sgGlow;
    private final Setting<ShapeMode> shapeMode;
    private final List<Hole> holes;
    private final Color transparent;
    private final Setting<SettingColor> doubleBedrock;
    private final SettingGroup sgGeneral;

    private void drawFlat(Hole hole) {
        int n = hole.blockPos.method_10263();
        int n2 = hole.blockPos.method_10264();
        int n3 = hole.blockPos.method_10260();
        switch (hole.direction) {
            case field_11036: {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, n, n2, n3, 1.0, hole.colorSides, hole.colorLines, this.shapeMode.get());
                break;
            }
            case field_11043: {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, n, n2, n3 + 1, n + 1, n3 - 1, hole.colorSides, hole.colorLines, this.shapeMode.get());
                break;
            }
            case field_11035: {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, n, n2, n3, n + 1, n3 + 2, hole.colorSides, hole.colorLines, this.shapeMode.get());
                break;
            }
            case field_11034: {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, n, n2, n3, n + 2, n3 + 1, hole.colorSides, hole.colorLines, this.shapeMode.get());
                break;
            }
            case field_11039: {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, n + 1, n2, n3, n - 1, n3 + 1, hole.colorSides, hole.colorLines, this.shapeMode.get());
            }
        }
    }

    private void drawGlowSimple(double d, double d2, double d3, double d4, double d5, Color color, Color color2, boolean bl) {
        if (this.shapeMode.get() != ShapeMode.Lines) {
            Renderer.NORMAL.gradientBoxSides(d, d2, d3, d4, d5, this.glowHeight.get(), color2, this.transparent, bl);
        }
        if (this.shapeMode.get() != ShapeMode.Sides) {
            if (this.drawOpposite.get().booleanValue()) {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, d, bl ? d2 : d2 + this.glowHeight.get(), d3, d4, d5, color, color2, ShapeMode.Lines);
            }
            Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, d, bl ? d2 + this.glowHeight.get() : d2, d3, d4, d5, color, color2, ShapeMode.Lines);
            Renderer.LINES.gradientVerticalBox(d, d2, d3, d4, d5, this.glowHeight.get(), color2, this.transparent, bl);
        }
    }

    private void lambda$onTick$1(class_2338 class_23382, class_2680 class_26802) {
        this.blockPos.method_10101((class_2382)class_23382);
        if (this.ignoreOwn.get().booleanValue() && this.mc.field_1724.method_24515().equals((Object)this.blockPos) || this.isBlocked((class_2338)this.blockPos)) {
            return;
        }
        if (!this.webs.get().booleanValue() && this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26204().method_27839(class_2246.field_10343)) {
            return;
        }
        class_2350 class_23502 = class_2350.field_11036;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        class_2680 class_26803 = this.mc.field_1687.method_8320(this.blockPos.method_10074());
        if (class_26803.method_26204() == class_2246.field_9987) {
            ++n;
        } else if (class_26803.method_26204() == class_2246.field_10540) {
            ++n2;
        } else if (class_26803.method_26215()) {
            return;
        }
        class_2680 class_26804 = this.mc.field_1687.method_8320(this.blockPos.method_10095());
        if (class_26804.method_26204() == class_2246.field_9987) {
            ++n;
        } else if (class_26804.method_26204() == class_2246.field_10540) {
            ++n2;
        } else if (class_26804.method_26215()) {
            class_23502 = class_2350.field_11043;
            ++n3;
        }
        class_2680 class_26805 = this.mc.field_1687.method_8320(this.blockPos.method_10072());
        if (class_26805.method_26204() == class_2246.field_9987) {
            ++n;
        } else if (class_26805.method_26204() == class_2246.field_10540) {
            ++n2;
        } else if (class_26805.method_26215()) {
            class_23502 = class_2350.field_11035;
            ++n3;
        }
        class_2680 class_26806 = this.mc.field_1687.method_8320(this.blockPos.method_10078());
        if (class_26806.method_26204() == class_2246.field_9987) {
            ++n;
        } else if (class_26806.method_26204() == class_2246.field_10540) {
            ++n2;
        } else if (class_26806.method_26215()) {
            class_23502 = class_2350.field_11034;
            ++n3;
        }
        class_2680 class_26807 = this.mc.field_1687.method_8320(this.blockPos.method_10067());
        if (class_26807.method_26204() == class_2246.field_9987) {
            ++n;
        } else if (class_26807.method_26204() == class_2246.field_10540) {
            ++n2;
        } else if (class_26807.method_26215()) {
            class_23502 = class_2350.field_11039;
            ++n3;
        }
        if (n3 > 1) {
            return;
        }
        if (n2 + n == 5) {
            if (n == 5) {
                this.holes.add(this.holePool.get().set((class_2338)this.blockPos, this.singleBedrock.get(), class_2350.field_11036));
            } else if (n2 == 5) {
                this.holes.add(this.holePool.get().set((class_2338)this.blockPos, this.singleObsidian.get(), class_2350.field_11036));
            } else {
                this.holes.add(this.holePool.get().set((class_2338)this.blockPos, this.singleMixed.get(), class_2350.field_11036));
            }
        } else if (n2 + n == 4 && n3 == 1 && this.doubles.get().booleanValue()) {
            int[] nArray = this.checkArround(this.blockPos.method_10093(class_23502), class_23502.method_10153());
            if (nArray[0] == 4 && n == 4) {
                this.holes.add(this.holePool.get().set((class_2338)this.blockPos, this.doubleBedrock.get(), class_23502));
            } else if (nArray[1] == 4 && n2 == 4) {
                this.holes.add(this.holePool.get().set((class_2338)this.blockPos, this.doubleObsidian.get(), class_23502));
            } else if (nArray[0] + nArray[1] == 4) {
                this.holes.add(this.holePool.get().set((class_2338)this.blockPos, this.doubleMixed.get(), class_23502));
            }
        }
    }

    public HoleESP() {
        super(Categories.Render, "hole-esp", "Displays Safe holes that you will take less damage in.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgGlow = this.settings.createGroup("Glow");
        this.sgColors = this.settings.createGroup("Colors");
        this.renderMode = this.sgGeneral.add(new EnumSetting.Builder().name("render-mode").description("The rendering mode.").defaultValue(Mode.Glow).build());
        this.shapeMode = this.sgGeneral.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
        this.horizontalRadius = this.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for holes.").defaultValue(10).min(0).sliderMax(32).build());
        this.verticalRadius = this.sgGeneral.add(new IntSetting.Builder().name("vertical-radius").description("Vertical radius in which to search for holes.").defaultValue(10).min(0).sliderMax(32).build());
        this.holeHeight = this.sgGeneral.add(new IntSetting.Builder().name("hole-height").description("Minimum hole height required to be rendered.").defaultValue(3).min(1).build());
        this.doubles = this.sgGeneral.add(new BoolSetting.Builder().name("doubles").description("Highlights double holes that can be stood across.").defaultValue(false).build());
        this.ignoreOwn = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-own").description("Ignores rendering the hole you are currently standing in.").defaultValue(true).build());
        this.webs = this.sgGeneral.add(new BoolSetting.Builder().name("webs").description("Whether to show holes that have webs inside of them.").defaultValue(false).build());
        this.glowHeight = this.sgGlow.add(new DoubleSetting.Builder().name("glow-height").description("The height of the glow when Glow mode is active").defaultValue(1.0).min(0.0).build());
        this.drawOpposite = this.sgGlow.add(new BoolSetting.Builder().name("draw-opposite").description("Draws a quad at the opposite end of the glow.").defaultValue(false).build());
        this.singleBedrock = this.sgColors.add(new ColorSetting.Builder().name("single-bedrock").description("The color for single holes that are completely bedrock.").defaultValue(new SettingColor(25, 225, 25, 100)).build());
        this.singleObsidian = this.sgColors.add(new ColorSetting.Builder().name("single-obsidian").description("The color for single holes that are completely obsidian.").defaultValue(new SettingColor(225, 25, 25, 100)).build());
        this.singleMixed = this.sgColors.add(new ColorSetting.Builder().name("single-mixed").description("The color for single holes that have mixed bedrock and obsidian.").defaultValue(new SettingColor(225, 145, 25, 100)).build());
        this.doubleBedrock = this.sgColors.add(new ColorSetting.Builder().name("double-bedrock").description("The color for double holes that are completely bedrock.").defaultValue(new SettingColor(25, 225, 25, 100)).build());
        this.doubleObsidian = this.sgColors.add(new ColorSetting.Builder().name("double-obsidian").description("The color for double holes that are completely obsidian.").defaultValue(new SettingColor(225, 25, 25, 100)).build());
        this.doubleMixed = this.sgColors.add(new ColorSetting.Builder().name("double-mixed").description("The color for double holes that have mixed bedrock and obsidian.").defaultValue(new SettingColor(225, 145, 25, 100)).build());
        this.holePool = new Pool<Hole>(HoleESP::lambda$new$0);
        this.blockPos = new class_2338.class_2339();
        this.holes = new ArrayList<Hole>();
        this.transparent = new Color(0, 0, 0, 0);
    }

    private static Hole lambda$new$0() {
        return new Hole(null);
    }

    private int[] checkArround(class_2338 class_23382, class_2350 class_23502) {
        class_2248 class_22482;
        int n = 0;
        int n2 = 0;
        if (this.isBlocked(class_23382)) {
            return new int[]{n, n2};
        }
        class_2248 class_22483 = this.mc.field_1687.method_8320(class_23382.method_10074()).method_26204();
        if (class_22483 == class_2246.field_9987) {
            ++n;
        } else if (class_22483 == class_2246.field_10540) {
            ++n2;
        }
        class_2248 class_22484 = this.mc.field_1687.method_8320(class_23382.method_10095()).method_26204();
        if (class_23502 != class_2350.field_11043) {
            if (class_22484 == class_2246.field_9987) {
                ++n;
            } else if (class_22484 == class_2246.field_10540) {
                ++n2;
            }
        }
        if ((class_22482 = this.mc.field_1687.method_8320(class_23382.method_10072()).method_26204()) == class_2246.field_9987) {
            ++n;
        } else if (class_22482 == class_2246.field_10540) {
            ++n2;
        }
        class_2248 class_22485 = this.mc.field_1687.method_8320(class_23382.method_10078()).method_26204();
        if (class_22485 == class_2246.field_9987) {
            ++n;
        } else if (class_22485 == class_2246.field_10540) {
            ++n2;
        }
        class_2248 class_22486 = this.mc.field_1687.method_8320(class_23382.method_10067()).method_26204();
        if (class_22486 == class_2246.field_9987 || class_22486 == class_2246.field_10540) {
            // empty if block
        }
        return new int[]{++n, ++n2};
    }

    private boolean isBlocked(class_2338 class_23382) {
        if (((AbstractBlockAccessor)this.mc.field_1687.method_8320(class_23382).method_26204()).isCollidable()) {
            return true;
        }
        for (int i = 0; i < this.holeHeight.get(); ++i) {
            if (!((AbstractBlockAccessor)this.mc.field_1687.method_8320(class_23382.method_10086(i)).method_26204()).isCollidable()) continue;
            return true;
        }
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        for (Hole hole : this.holes) {
            this.holePool.free(hole);
        }
        this.holes.clear();
        BlockIterator.register(this.horizontalRadius.get(), this.verticalRadius.get(), this::lambda$onTick$1);
    }

    private void drawBox(Hole hole, boolean bl) {
        int n = hole.blockPos.method_10263();
        int n2 = bl ? hole.blockPos.method_10264() - 1 : hole.blockPos.method_10264();
        int n3 = hole.blockPos.method_10260();
        switch (hole.direction) {
            case field_11036: {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (class_2338)(bl ? hole.blockPos.method_10074() : hole.blockPos), hole.colorSides, hole.colorLines, this.shapeMode.get(), 0);
                break;
            }
            case field_11043: {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3 + 1, n + 1, n2 + 1, n3 - 1, hole.colorSides, hole.colorLines, this.shapeMode.get(), 0);
                break;
            }
            case field_11035: {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3, n + 1, n2 + 1, n3 + 2, hole.colorSides, hole.colorLines, this.shapeMode.get(), 0);
                break;
            }
            case field_11034: {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3, n + 2, n2 + 1, n3 + 1, hole.colorSides, hole.colorLines, this.shapeMode.get(), 0);
                break;
            }
            case field_11039: {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n + 1, n2, n3, n - 1, n2 + 1, n3 + 1, hole.colorSides, hole.colorLines, this.shapeMode.get(), 0);
            }
        }
    }

    private void drawBoxGlowDirection(Hole hole, boolean bl) {
        int n = hole.blockPos.method_10263();
        int n2 = hole.blockPos.method_10264();
        int n3 = hole.blockPos.method_10260();
        switch (hole.direction) {
            case field_11036: {
                this.drawGlowSimple(n, n2, n3, n + 1, n3 + 1, hole.colorSides, hole.colorLines, bl);
                break;
            }
            case field_11043: {
                this.drawGlowSimple(n, n2, n3 + 1, n + 1, n3 - 1, hole.colorSides, hole.colorLines, bl);
                break;
            }
            case field_11035: {
                this.drawGlowSimple(n, n2, n3, n + 1, n3 + 2, hole.colorSides, hole.colorLines, bl);
                break;
            }
            case field_11034: {
                this.drawGlowSimple(n, n2, n3, n + 2, n3 + 1, hole.colorSides, hole.colorLines, bl);
                break;
            }
            case field_11039: {
                this.drawGlowSimple(n + 1, n2, n3, n - 1, n3 + 1, hole.colorSides, hole.colorLines, bl);
            }
        }
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        for (Hole hole : this.holes) {
            switch (this.renderMode.get()) {
                case Flat: {
                    this.drawFlat(hole);
                    break;
                }
                case Box: {
                    this.drawBox(hole, false);
                    break;
                }
                case BoxBelow: {
                    this.drawBox(hole, true);
                    break;
                }
                case ReverseGlow: 
                case Glow: {
                    this.drawBoxGlowDirection(hole, this.renderMode.get() == Mode.ReverseGlow);
                }
            }
        }
    }

    private static class Hole {
        public Color colorLines;
        public class_2350 direction;
        public Color colorSides;
        public class_2338.class_2339 blockPos = new class_2338.class_2339();

        Hole(1 var1_1) {
            this();
        }

        private Hole() {
            this.colorSides = new Color();
            this.colorLines = new Color();
        }

        public Hole set(class_2338 class_23382, Color color, class_2350 class_23502) {
            this.blockPos.method_10101((class_2382)class_23382);
            this.direction = class_23502;
            this.colorLines.set(color);
            this.colorSides.set(color);
            this.colorSides.a = (int)((double)this.colorSides.a * 0.5);
            this.colorSides.validate();
            return this;
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode BoxBelow;
        public static final /* enum */ Mode Box;
        public static final /* enum */ Mode Glow;
        public static final /* enum */ Mode Flat;
        public static final /* enum */ Mode ReverseGlow;

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            Flat = new Mode();
            Box = new Mode();
            BoxBelow = new Mode();
            Glow = new Mode();
            ReverseGlow = new Mode();
            $VALUES = Mode.$values();
        }

        private static Mode[] $values() {
            return new Mode[]{Flat, Box, BoxBelow, Glow, ReverseGlow};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

