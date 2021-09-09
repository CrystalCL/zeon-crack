/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.render.color.SettingColor;

public class ActiveModulesHud
extends HudElement {
    private final /* synthetic */ Setting<ColorMode> colorMode;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> activeInfo;
    private /* synthetic */ double rainbowHue2;
    private final /* synthetic */ Setting<SettingColor> flatColor;
    private final /* synthetic */ minegame159.meteorclient.utils.render.color.Color rainbow;
    private final /* synthetic */ Setting<Sort> sort;
    private final /* synthetic */ List<Module> modules;
    private final /* synthetic */ Setting<Double> rainbowSpread;
    private /* synthetic */ double rainbowHue1;
    private final /* synthetic */ Setting<Double> rainbowSpeed;

    public ActiveModulesHud(HUD lllllllllllllllllIIllIIlIIIIIIll) {
        super(lllllllllllllllllIIllIIlIIIIIIll, "active-modules", "Displays your active modules.");
        ActiveModulesHud lllllllllllllllllIIllIIlIIIIIlII;
        lllllllllllllllllIIllIIlIIIIIlII.sgGeneral = lllllllllllllllllIIllIIlIIIIIlII.settings.getDefaultGroup();
        lllllllllllllllllIIllIIlIIIIIlII.sort = lllllllllllllllllIIllIIlIIIIIlII.sgGeneral.add(new EnumSetting.Builder().name("sort").description("How to sort active modules.").defaultValue(Sort.Biggest).build());
        lllllllllllllllllIIllIIlIIIIIlII.activeInfo = lllllllllllllllllIIllIIlIIIIIlII.sgGeneral.add(new BoolSetting.Builder().name("additional-info").description("Shows additional info from the module next to the name in the active modules list.").defaultValue(true).build());
        lllllllllllllllllIIllIIlIIIIIlII.colorMode = lllllllllllllllllIIllIIlIIIIIlII.sgGeneral.add(new EnumSetting.Builder().name("color-mode").description("What color to use for active modules.").defaultValue(ColorMode.Rainbow).build());
        lllllllllllllllllIIllIIlIIIIIlII.flatColor = lllllllllllllllllIIllIIlIIIIIlII.sgGeneral.add(new ColorSetting.Builder().name("flat-color").description("Color for flat color mode.").defaultValue(new SettingColor(225, 25, 25)).build());
        lllllllllllllllllIIllIIlIIIIIlII.rainbowSpeed = lllllllllllllllllIIllIIlIIIIIlII.sgGeneral.add(new DoubleSetting.Builder().name("rainbow-speed").description("Rainbow speed of rainbow color mode.").defaultValue(0.025).sliderMax(0.1).decimalPlaces(4).build());
        lllllllllllllllllIIllIIlIIIIIlII.rainbowSpread = lllllllllllllllllIIllIIlIIIIIlII.sgGeneral.add(new DoubleSetting.Builder().name("rainbow-spread").description("Rainbow spread of rainbow color mode.").defaultValue(0.025).sliderMax(0.05).decimalPlaces(4).build());
        lllllllllllllllllIIllIIlIIIIIlII.modules = new ArrayList<Module>();
        lllllllllllllllllIIllIIlIIIIIlII.rainbow = new minegame159.meteorclient.utils.render.color.Color(255, 255, 255);
    }

    @Override
    public void update(HudRenderer lllllllllllllllllIIllIIIllllIlll) {
        ActiveModulesHud lllllllllllllllllIIllIIIlllllIII;
        if (Modules.get() == null) {
            lllllllllllllllllIIllIIIlllllIII.box.setSize(lllllllllllllllllIIllIIIllllIlll.textWidth("Active Modules"), lllllllllllllllllIIllIIIllllIlll.textHeight());
            return;
        }
        lllllllllllllllllIIllIIIlllllIII.modules.clear();
        for (Module lllllllllllllllllIIllIIIlllllIll : Modules.get().getActive()) {
            if (!lllllllllllllllllIIllIIIlllllIll.isVisible()) continue;
            lllllllllllllllllIIllIIIlllllIII.modules.add(lllllllllllllllllIIllIIIlllllIll);
        }
        lllllllllllllllllIIllIIIlllllIII.modules.sort((lllllllllllllllllIIllIIIlIlIIIll, lllllllllllllllllIIllIIIlIlIIIlI) -> {
            int lllllllllllllllllIIllIIIlIlIIllI;
            ActiveModulesHud lllllllllllllllllIIllIIIlIlIIlIl;
            double lllllllllllllllllIIllIIIlIlIlIII = lllllllllllllllllIIllIIIlIlIIlIl.getModuleWidth(lllllllllllllllllIIllIIIllllIlll, (Module)lllllllllllllllllIIllIIIlIlIIIll);
            double lllllllllllllllllIIllIIIlIlIIlll = lllllllllllllllllIIllIIIlIlIIlIl.getModuleWidth(lllllllllllllllllIIllIIIllllIlll, (Module)lllllllllllllllllIIllIIIlIlIIIlI);
            if (lllllllllllllllllIIllIIIlIlIIlIl.sort.get() == Sort.Smallest) {
                double lllllllllllllllllIIllIIIlIlIllIl = lllllllllllllllllIIllIIIlIlIlIII;
                lllllllllllllllllIIllIIIlIlIlIII = lllllllllllllllllIIllIIIlIlIIlll;
                lllllllllllllllllIIllIIIlIlIIlll = lllllllllllllllllIIllIIIlIlIllIl;
            }
            if ((lllllllllllllllllIIllIIIlIlIIllI = Double.compare(lllllllllllllllllIIllIIIlIlIlIII, lllllllllllllllllIIllIIIlIlIIlll)) == 0) {
                return 0;
            }
            return lllllllllllllllllIIllIIIlIlIIllI < 0 ? 1 : -1;
        });
        double lllllllllllllllllIIllIIIllllIllI = 0.0;
        double lllllllllllllllllIIllIIIllllIlIl = 0.0;
        for (int lllllllllllllllllIIllIIIlllllIIl = 0; lllllllllllllllllIIllIIIlllllIIl < lllllllllllllllllIIllIIIlllllIII.modules.size(); ++lllllllllllllllllIIllIIIlllllIIl) {
            Module lllllllllllllllllIIllIIIlllllIlI = lllllllllllllllllIIllIIIlllllIII.modules.get(lllllllllllllllllIIllIIIlllllIIl);
            lllllllllllllllllIIllIIIllllIllI = Math.max(lllllllllllllllllIIllIIIllllIllI, lllllllllllllllllIIllIIIlllllIII.getModuleWidth(lllllllllllllllllIIllIIIllllIlll, lllllllllllllllllIIllIIIlllllIlI));
            lllllllllllllllllIIllIIIllllIlIl += lllllllllllllllllIIllIIIllllIlll.textHeight();
            if (lllllllllllllllllIIllIIIlllllIIl <= 0) continue;
            lllllllllllllllllIIllIIIllllIlIl += 2.0;
        }
        lllllllllllllllllIIllIIIlllllIII.box.setSize(lllllllllllllllllIIllIIIllllIllI, lllllllllllllllllIIllIIIllllIlIl);
    }

    private double getModuleWidth(HudRenderer lllllllllllllllllIIllIIIlIlllIII, Module lllllllllllllllllIIllIIIlIllIlll) {
        String lllllllllllllllllIIllIIIlIlllllI;
        ActiveModulesHud lllllllllllllllllIIllIIIlIlllIIl;
        double lllllllllllllllllIIllIIIlIlllIlI = lllllllllllllllllIIllIIIlIlllIII.textWidth(lllllllllllllllllIIllIIIlIllIlll.title);
        if (lllllllllllllllllIIllIIIlIlllIIl.activeInfo.get().booleanValue() && (lllllllllllllllllIIllIIIlIlllllI = lllllllllllllllllIIllIIIlIllIlll.getInfoString()) != null) {
            lllllllllllllllllIIllIIIlIlllIlI += lllllllllllllllllIIllIIIlIlllIII.textWidth(" ") + lllllllllllllllllIIllIIIlIlllIII.textWidth(lllllllllllllllllIIllIIIlIlllllI);
        }
        return lllllllllllllllllIIllIIIlIlllIlI;
    }

    @Override
    public void render(HudRenderer lllllllllllllllllIIllIIIlllIIlIl) {
        ActiveModulesHud lllllllllllllllllIIllIIIlllIIllI;
        double lllllllllllllllllIIllIIIlllIIlII = lllllllllllllllllIIllIIIlllIIllI.box.getX();
        double lllllllllllllllllIIllIIIlllIIIll = lllllllllllllllllIIllIIIlllIIllI.box.getY();
        if (Modules.get() == null) {
            lllllllllllllllllIIllIIIlllIIlIl.text("Active Modules", lllllllllllllllllIIllIIIlllIIlII, lllllllllllllllllIIllIIIlllIIIll, lllllllllllllllllIIllIIIlllIIllI.hud.color);
            return;
        }
        lllllllllllllllllIIllIIIlllIIllI.rainbowHue1 += lllllllllllllllllIIllIIIlllIIllI.rainbowSpeed.get() * lllllllllllllllllIIllIIIlllIIlIl.delta;
        if (lllllllllllllllllIIllIIIlllIIllI.rainbowHue1 > 1.0) {
            lllllllllllllllllIIllIIIlllIIllI.rainbowHue1 -= 1.0;
        } else if (lllllllllllllllllIIllIIIlllIIllI.rainbowHue1 < -1.0) {
            lllllllllllllllllIIllIIIlllIIllI.rainbowHue1 += 1.0;
        }
        lllllllllllllllllIIllIIIlllIIllI.rainbowHue2 = lllllllllllllllllIIllIIIlllIIllI.rainbowHue1;
        for (Module lllllllllllllllllIIllIIIlllIIlll : lllllllllllllllllIIllIIIlllIIllI.modules) {
            lllllllllllllllllIIllIIIlllIIllI.renderModule(lllllllllllllllllIIllIIIlllIIlIl, lllllllllllllllllIIllIIIlllIIlll, lllllllllllllllllIIllIIIlllIIlII + lllllllllllllllllIIllIIIlllIIllI.box.alignX(lllllllllllllllllIIllIIIlllIIllI.getModuleWidth(lllllllllllllllllIIllIIIlllIIlIl, lllllllllllllllllIIllIIIlllIIlll)), lllllllllllllllllIIllIIIlllIIIll);
            lllllllllllllllllIIllIIIlllIIIll += 2.0 + lllllllllllllllllIIllIIIlllIIlIl.textHeight();
        }
    }

    private void renderModule(HudRenderer lllllllllllllllllIIllIIIllIlIIIl, Module lllllllllllllllllIIllIIIllIlIIII, double lllllllllllllllllIIllIIIllIIlIII, double lllllllllllllllllIIllIIIllIIIlll) {
        String lllllllllllllllllIIllIIIllIlIIll;
        ActiveModulesHud lllllllllllllllllIIllIIIllIlIIlI;
        minegame159.meteorclient.utils.render.color.Color lllllllllllllllllIIllIIIllIIllIl = lllllllllllllllllIIllIIIllIlIIlI.flatColor.get();
        ColorMode lllllllllllllllllIIllIIIllIIllII = lllllllllllllllllIIllIIIllIlIIlI.colorMode.get();
        if (lllllllllllllllllIIllIIIllIIllII == ColorMode.Random) {
            lllllllllllllllllIIllIIIllIIllIl = lllllllllllllllllIIllIIIllIlIIII.color;
        } else if (lllllllllllllllllIIllIIIllIIllII == ColorMode.Rainbow) {
            lllllllllllllllllIIllIIIllIlIIlI.rainbowHue2 += lllllllllllllllllIIllIIIllIlIIlI.rainbowSpread.get().doubleValue();
            int lllllllllllllllllIIllIIIllIlIlII = Color.HSBtoRGB((float)lllllllllllllllllIIllIIIllIlIIlI.rainbowHue2, 1.0f, 1.0f);
            lllllllllllllllllIIllIIIllIlIIlI.rainbow.r = minegame159.meteorclient.utils.render.color.Color.toRGBAR(lllllllllllllllllIIllIIIllIlIlII);
            lllllllllllllllllIIllIIIllIlIIlI.rainbow.g = minegame159.meteorclient.utils.render.color.Color.toRGBAG(lllllllllllllllllIIllIIIllIlIlII);
            lllllllllllllllllIIllIIIllIlIIlI.rainbow.b = minegame159.meteorclient.utils.render.color.Color.toRGBAB(lllllllllllllllllIIllIIIllIlIlII);
            lllllllllllllllllIIllIIIllIIllIl = lllllllllllllllllIIllIIIllIlIIlI.rainbow;
        }
        lllllllllllllllllIIllIIIllIlIIIl.text(lllllllllllllllllIIllIIIllIlIIII.title, lllllllllllllllllIIllIIIllIIlIII, lllllllllllllllllIIllIIIllIIIlll, lllllllllllllllllIIllIIIllIIllIl);
        if (lllllllllllllllllIIllIIIllIlIIlI.activeInfo.get().booleanValue() && (lllllllllllllllllIIllIIIllIlIIll = lllllllllllllllllIIllIIIllIlIIII.getInfoString()) != null) {
            lllllllllllllllllIIllIIIllIlIIIl.text(lllllllllllllllllIIllIIIllIlIIll, lllllllllllllllllIIllIIIllIIlIII + lllllllllllllllllIIllIIIllIlIIIl.textWidth(lllllllllllllllllIIllIIIllIlIIII.title) + lllllllllllllllllIIllIIIllIlIIIl.textWidth(" "), lllllllllllllllllIIllIIIllIIIlll, lllllllllllllllllIIllIIIllIlIIlI.hud.secondaryColor.get());
        }
    }

    public static final class ColorMode
    extends Enum<ColorMode> {
        public static final /* synthetic */ /* enum */ ColorMode Rainbow;
        public static final /* synthetic */ /* enum */ ColorMode Flat;
        private static final /* synthetic */ ColorMode[] $VALUES;
        public static final /* synthetic */ /* enum */ ColorMode Random;

        public static ColorMode valueOf(String lllllllllllllllllllIlIIIIlIllIIl) {
            return Enum.valueOf(ColorMode.class, lllllllllllllllllllIlIIIIlIllIIl);
        }

        private ColorMode() {
            ColorMode lllllllllllllllllllIlIIIIlIlIIII;
        }

        static {
            Flat = new ColorMode();
            Random = new ColorMode();
            Rainbow = new ColorMode();
            $VALUES = ColorMode.$values();
        }

        private static /* synthetic */ ColorMode[] $values() {
            return new ColorMode[]{Flat, Random, Rainbow};
        }

        public static ColorMode[] values() {
            return (ColorMode[])$VALUES.clone();
        }
    }

    public static final class Sort
    extends Enum<Sort> {
        public static final /* synthetic */ /* enum */ Sort Biggest;
        private static final /* synthetic */ Sort[] $VALUES;
        public static final /* synthetic */ /* enum */ Sort Smallest;

        private static /* synthetic */ Sort[] $values() {
            return new Sort[]{Biggest, Smallest};
        }

        public static Sort[] values() {
            return (Sort[])$VALUES.clone();
        }

        private Sort() {
            Sort lIllIllllIIII;
        }

        static {
            Biggest = new Sort();
            Smallest = new Sort();
            $VALUES = Sort.$values();
        }

        public static Sort valueOf(String lIllIllllIllI) {
            return Enum.valueOf(Sort.class, lIllIllllIllI);
        }
    }
}

