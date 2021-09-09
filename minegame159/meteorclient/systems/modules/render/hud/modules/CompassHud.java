/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
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
import net.minecraft.util.math.MathHelper;

public class CompassHud
extends HudElement {
    private final /* synthetic */ Setting<SettingColor> northColor;
    private final /* synthetic */ Setting<Double> scale;
    private final /* synthetic */ Setting<Mode> mod;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> otherColor;

    private double getY(double llllllllllllllllllIlllIIlIIIlllI) {
        CompassHud llllllllllllllllllIlllIIlIIIllll;
        double llllllllllllllllllIlllIIlIIIllIl = 0.0;
        if (!llllllllllllllllllIlllIIlIIIllll.isInEditor()) {
            llllllllllllllllllIlllIIlIIIllIl = llllllllllllllllllIlllIIlIIIllll.mc.player.pitch;
        }
        return Math.cos(llllllllllllllllllIlllIIlIIIlllI) * Math.sin(Math.toRadians(MathHelper.clamp((double)(llllllllllllllllllIlllIIlIIIllIl + 30.0), (double)-90.0, (double)90.0))) * (llllllllllllllllllIlllIIlIIIllll.scale.get() * 40.0);
    }

    public CompassHud(HUD llllllllllllllllllIlllIIlIllIlIl) {
        super(llllllllllllllllllIlllIIlIllIlIl, "compass", "Displays your rotation as a 3D compass.");
        CompassHud llllllllllllllllllIlllIIlIllIllI;
        llllllllllllllllllIlllIIlIllIllI.sgGeneral = llllllllllllllllllIlllIIlIllIllI.settings.getDefaultGroup();
        llllllllllllllllllIlllIIlIllIllI.mod = llllllllllllllllllIlllIIlIllIllI.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode of the compass.").defaultValue(Mode.Pole).build());
        llllllllllllllllllIlllIIlIllIllI.scale = llllllllllllllllllIlllIIlIllIllI.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale of compass.").defaultValue(1.0).sliderMin(1.0).sliderMax(5.0).build());
        llllllllllllllllllIlllIIlIllIllI.northColor = llllllllllllllllllIlllIIlIllIllI.sgGeneral.add(new ColorSetting.Builder().name("north-color").description("The color of north axis.").defaultValue(new SettingColor(225, 45, 45)).build());
        llllllllllllllllllIlllIIlIllIllI.otherColor = llllllllllllllllllIlllIIlIllIllI.sgGeneral.add(new ColorSetting.Builder().name("other-color").description("The color of other axis.").defaultValue(new SettingColor(225, 225, 255)).build());
    }

    private double getPosOnCompass(Direction llllllllllllllllllIlllIIlIIIIlIl) {
        CompassHud llllllllllllllllllIlllIIlIIIIllI;
        double llllllllllllllllllIlllIIlIIIIlII = 0.0;
        if (!llllllllllllllllllIlllIIlIIIIllI.isInEditor()) {
            llllllllllllllllllIlllIIlIIIIlII = llllllllllllllllllIlllIIlIIIIllI.mc.player.yaw;
        }
        return Math.toRadians(MathHelper.wrapDegrees((double)llllllllllllllllllIlllIIlIIIIlII)) + (double)llllllllllllllllllIlllIIlIIIIlIl.ordinal() * 1.5707963267948966;
    }

    private double getX(double llllllllllllllllllIlllIIlIIlIIll) {
        CompassHud llllllllllllllllllIlllIIlIIlIllI;
        return Math.sin(llllllllllllllllllIlllIIlIIlIIll) * (llllllllllllllllllIlllIIlIIlIllI.scale.get() * 40.0);
    }

    @Override
    public void update(HudRenderer llllllllllllllllllIlllIIlIllIIlI) {
        CompassHud llllllllllllllllllIlllIIlIllIIIl;
        llllllllllllllllllIlllIIlIllIIIl.box.setSize(100.0 * llllllllllllllllllIlllIIlIllIIIl.scale.get(), 100.0 * llllllllllllllllllIlllIIlIllIIIl.scale.get());
    }

    @Override
    public void render(HudRenderer llllllllllllllllllIlllIIlIlIIlII) {
        CompassHud llllllllllllllllllIlllIIlIlIIIIl;
        double llllllllllllllllllIlllIIlIlIIIll = llllllllllllllllllIlllIIlIlIIIIl.box.getX();
        double llllllllllllllllllIlllIIlIlIIIlI = llllllllllllllllllIlllIIlIlIIIIl.box.getY();
        for (Direction llllllllllllllllllIlllIIlIlIIllI : Direction.values()) {
            double llllllllllllllllllIlllIIlIlIIlll = llllllllllllllllllIlllIIlIlIIIIl.getPosOnCompass(llllllllllllllllllIlllIIlIlIIllI);
            llllllllllllllllllIlllIIlIlIIlII.text(llllllllllllllllllIlllIIlIlIIIIl.mod.get() == Mode.Axis ? llllllllllllllllllIlllIIlIlIIllI.getAlternate() : llllllllllllllllllIlllIIlIlIIllI.name(), llllllllllllllllllIlllIIlIlIIIll + llllllllllllllllllIlllIIlIlIIIIl.box.width / 2.0 + llllllllllllllllllIlllIIlIlIIIIl.getX(llllllllllllllllllIlllIIlIlIIlll), llllllllllllllllllIlllIIlIlIIIlI + llllllllllllllllllIlllIIlIlIIIIl.box.height / 2.0 + llllllllllllllllllIlllIIlIlIIIIl.getY(llllllllllllllllllIlllIIlIlIIlll), llllllllllllllllllIlllIIlIlIIllI == Direction.N ? (Color)llllllllllllllllllIlllIIlIlIIIIl.northColor.get() : (Color)llllllllllllllllllIlllIIlIlIIIIl.otherColor.get());
        }
    }

    private static final class Direction
    extends Enum<Direction> {
        public static final /* synthetic */ /* enum */ Direction S;
        public static final /* synthetic */ /* enum */ Direction W;
        public static final /* synthetic */ /* enum */ Direction N;
        private static final /* synthetic */ Direction[] $VALUES;
        public static final /* synthetic */ /* enum */ Direction E;
        /* synthetic */ String alternate;

        private static /* synthetic */ Direction[] $values() {
            return new Direction[]{N, W, S, E};
        }

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }

        public String getAlternate() {
            Direction lllIl;
            return lllIl.alternate;
        }

        public static Direction valueOf(String lIlIlI) {
            return Enum.valueOf(Direction.class, lIlIlI);
        }

        static {
            N = new Direction("Z-");
            W = new Direction("X-");
            S = new Direction("Z+");
            E = new Direction("X+");
            $VALUES = Direction.$values();
        }

        private Direction(String lIIIII) {
            Direction lIIIll;
            lIIIll.alternate = lIIIII;
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Axis;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Pole;

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Axis, Pole};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String lIlIIlIlll) {
            return Enum.valueOf(Mode.class, lIlIIlIlll);
        }

        private Mode() {
            Mode lIlIIlIIlI;
        }

        static {
            Axis = new Mode();
            Pole = new Mode();
            $VALUES = Mode.$values();
        }
    }
}

