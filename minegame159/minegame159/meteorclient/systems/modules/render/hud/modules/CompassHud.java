/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_3532;

public class CompassHud
extends HudElement {
    private final Setting<Double> scale;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> northColor;
    private final Setting<SettingColor> otherColor;
    private final Setting<Mode> mod;

    private double getPosOnCompass(Direction direction) {
        double d = 0.0;
        if (!this.isInEditor()) {
            d = this.mc.field_1724.field_6031;
        }
        return Math.toRadians(class_3532.method_15338((double)d)) + (double)direction.ordinal() * 1.5707963267948966;
    }

    public CompassHud(HUD hUD) {
        super(hUD, "compass", "Displays your rotation as a 3D compass.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mod = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode of the compass.").defaultValue(Mode.Pole).build());
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale of compass.").defaultValue(1.0).sliderMin(1.0).sliderMax(5.0).build());
        this.northColor = this.sgGeneral.add(new ColorSetting.Builder().name("north-color").description("The color of north axis.").defaultValue(new SettingColor(225, 45, 45)).build());
        this.otherColor = this.sgGeneral.add(new ColorSetting.Builder().name("other-color").description("The color of other axis.").defaultValue(new SettingColor(225, 225, 255)).build());
    }

    private double getY(double d) {
        double d2 = 0.0;
        if (!this.isInEditor()) {
            d2 = this.mc.field_1724.field_5965;
        }
        return Math.cos(d) * Math.sin(Math.toRadians(class_3532.method_15350((double)(d2 + 30.0), (double)-90.0, (double)90.0))) * (this.scale.get() * 40.0);
    }

    private double getX(double d) {
        return Math.sin(d) * (this.scale.get() * 40.0);
    }

    @Override
    public void render(HudRenderer hudRenderer) {
        double d = this.box.getX();
        double d2 = this.box.getY();
        for (Direction direction : Direction.values()) {
            double d3 = this.getPosOnCompass(direction);
            hudRenderer.text(this.mod.get() == Mode.Axis ? direction.getAlternate() : direction.name(), d + this.box.width / 2.0 + this.getX(d3), d2 + this.box.height / 2.0 + this.getY(d3), direction == Direction.N ? (Color)this.northColor.get() : (Color)this.otherColor.get());
            if (3 != 0) continue;
            return;
        }
    }

    @Override
    public void update(HudRenderer hudRenderer) {
        this.box.setSize(100.0 * this.scale.get(), 100.0 * this.scale.get());
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Pole;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Axis;

        private static Mode[] $values() {
            return new Mode[]{Axis, Pole};
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            Axis = new Mode();
            Pole = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }

    private static final class Direction
    extends Enum<Direction> {
        public static final /* enum */ Direction W;
        private static final Direction[] $VALUES;
        String alternate;
        public static final /* enum */ Direction S;
        public static final /* enum */ Direction N;
        public static final /* enum */ Direction E;

        private Direction(String string2) {
            this.alternate = string2;
        }

        static {
            N = new Direction("Z-");
            W = new Direction("X-");
            S = new Direction("Z+");
            E = new Direction("X+");
            $VALUES = Direction.$values();
        }

        private static Direction[] $values() {
            return new Direction[]{N, W, S, E};
        }

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }

        public String getAlternate() {
            return this.alternate;
        }

        public static Direction valueOf(String string) {
            return Enum.valueOf(Direction.class, string);
        }
    }
}

