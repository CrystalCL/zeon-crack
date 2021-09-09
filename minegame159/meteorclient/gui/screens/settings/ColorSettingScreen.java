/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.screens.settings;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.widgets.WQuad;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WDoubleEdit;
import minegame159.meteorclient.gui.widgets.input.WIntEdit;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;

public class ColorSettingScreen
extends WindowScreen {
    private final /* synthetic */ WBrightnessQuad brightnessQuad;
    private static final /* synthetic */ Color WHITE;
    private final /* synthetic */ WIntEdit rItb;
    private static final /* synthetic */ Color[] HUE_COLORS;
    private final /* synthetic */ WIntEdit bItb;
    private final /* synthetic */ WQuad displayQuad;
    private final /* synthetic */ WIntEdit aItb;
    private final /* synthetic */ WDoubleEdit rainbowSpeed;
    private final /* synthetic */ Setting<SettingColor> setting;
    private final /* synthetic */ WIntEdit gItb;
    private final /* synthetic */ WHueQuad hueQuad;
    private static final /* synthetic */ Color BLACK;
    public /* synthetic */ Runnable action;

    static {
        HUE_COLORS = new Color[]{new Color(255, 0, 0), new Color(255, 255, 0), new Color(0, 255, 0), new Color(0, 255, 255), new Color(0, 0, 255), new Color(255, 0, 255), new Color(255, 0, 0)};
        WHITE = new Color(255, 255, 255);
        BLACK = new Color(0, 0, 0);
    }

    public void tick() {
        ColorSettingScreen llllllllllllllllllIllIlIIlIIlIlI;
        super.tick();
        if (llllllllllllllllllIllIlIIlIIlIlI.setting.get().rainbowSpeed > 0.0) {
            llllllllllllllllllIllIlIIlIIlIlI.setFromSetting();
        }
    }

    private void callAction() {
        ColorSettingScreen llllllllllllllllllIllIlIIlIIllIl;
        if (llllllllllllllllllIllIlIIlIIllIl.action != null) {
            llllllllllllllllllIllIlIIlIIllIl.action.run();
        }
    }

    private void hsvChanged() {
        ColorSettingScreen llllllllllllllllllIllIlIIIllIIII;
        double llllllllllllllllllIllIlIIIlIllll = 0.0;
        double llllllllllllllllllIllIlIIIlIlllI = 0.0;
        double llllllllllllllllllIllIlIIIlIllIl = 0.0;
        boolean llllllllllllllllllIllIlIIIlIllII = false;
        if (llllllllllllllllllIllIlIIIllIIII.brightnessQuad.saturation <= 0.0) {
            llllllllllllllllllIllIlIIIlIllll = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
            llllllllllllllllllIllIlIIIlIlllI = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
            llllllllllllllllllIllIlIIIlIllIl = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
            llllllllllllllllllIllIlIIIlIllII = true;
        }
        if (!llllllllllllllllllIllIlIIIlIllII) {
            double llllllllllllllllllIllIlIIIllIllI = llllllllllllllllllIllIlIIIllIIII.hueQuad.hueAngle;
            if (llllllllllllllllllIllIlIIIllIllI >= 360.0) {
                llllllllllllllllllIllIlIIIllIllI = 0.0;
            }
            int llllllllllllllllllIllIlIIIllIIIl = (int)(llllllllllllllllllIllIlIIIllIllI /= 60.0);
            double llllllllllllllllllIllIlIIIllIIlI = llllllllllllllllllIllIlIIIllIllI - (double)llllllllllllllllllIllIlIIIllIIIl;
            double llllllllllllllllllIllIlIIIllIlIl = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value * (1.0 - llllllllllllllllllIllIlIIIllIIII.brightnessQuad.saturation);
            double llllllllllllllllllIllIlIIIllIlII = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value * (1.0 - llllllllllllllllllIllIlIIIllIIII.brightnessQuad.saturation * llllllllllllllllllIllIlIIIllIIlI);
            double llllllllllllllllllIllIlIIIllIIll = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value * (1.0 - llllllllllllllllllIllIlIIIllIIII.brightnessQuad.saturation * (1.0 - llllllllllllllllllIllIlIIIllIIlI));
            switch (llllllllllllllllllIllIlIIIllIIIl) {
                case 0: {
                    llllllllllllllllllIllIlIIIlIllll = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
                    llllllllllllllllllIllIlIIIlIlllI = llllllllllllllllllIllIlIIIllIIll;
                    llllllllllllllllllIllIlIIIlIllIl = llllllllllllllllllIllIlIIIllIlIl;
                    break;
                }
                case 1: {
                    llllllllllllllllllIllIlIIIlIllll = llllllllllllllllllIllIlIIIllIlII;
                    llllllllllllllllllIllIlIIIlIlllI = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
                    llllllllllllllllllIllIlIIIlIllIl = llllllllllllllllllIllIlIIIllIlIl;
                    break;
                }
                case 2: {
                    llllllllllllllllllIllIlIIIlIllll = llllllllllllllllllIllIlIIIllIlIl;
                    llllllllllllllllllIllIlIIIlIlllI = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
                    llllllllllllllllllIllIlIIIlIllIl = llllllllllllllllllIllIlIIIllIIll;
                    break;
                }
                case 3: {
                    llllllllllllllllllIllIlIIIlIllll = llllllllllllllllllIllIlIIIllIlIl;
                    llllllllllllllllllIllIlIIIlIlllI = llllllllllllllllllIllIlIIIllIlII;
                    llllllllllllllllllIllIlIIIlIllIl = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
                    break;
                }
                case 4: {
                    llllllllllllllllllIllIlIIIlIllll = llllllllllllllllllIllIlIIIllIIll;
                    llllllllllllllllllIllIlIIIlIlllI = llllllllllllllllllIllIlIIIllIlIl;
                    llllllllllllllllllIllIlIIIlIllIl = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
                    break;
                }
                default: {
                    llllllllllllllllllIllIlIIIlIllll = llllllllllllllllllIllIlIIIllIIII.brightnessQuad.value;
                    llllllllllllllllllIllIlIIIlIlllI = llllllllllllllllllIllIlIIIllIlIl;
                    llllllllllllllllllIllIlIIIlIllIl = llllllllllllllllllIllIlIIIllIlII;
                }
            }
        }
        Color llllllllllllllllllIllIlIIIlIlIll = llllllllllllllllllIllIlIIIllIIII.setting.get();
        llllllllllllllllllIllIlIIIlIlIll.r = (int)(llllllllllllllllllIllIlIIIlIllll * 255.0);
        llllllllllllllllllIllIlIIIlIlIll.g = (int)(llllllllllllllllllIllIlIIIlIlllI * 255.0);
        llllllllllllllllllIllIlIIIlIlIll.b = (int)(llllllllllllllllllIllIlIIIlIllIl * 255.0);
        llllllllllllllllllIllIlIIIlIlIll.validate();
        llllllllllllllllllIllIlIIIllIIII.rItb.set(llllllllllllllllllIllIlIIIlIlIll.r);
        llllllllllllllllllIllIlIIIllIIII.gItb.set(llllllllllllllllllIllIlIIIlIlIll.g);
        llllllllllllllllllIllIlIIIllIIII.bItb.set(llllllllllllllllllIllIlIIIlIlIll.b);
        llllllllllllllllllIllIlIIIllIIII.displayQuad.color.set(llllllllllllllllllIllIlIIIlIlIll);
        llllllllllllllllllIllIlIIIllIIII.setting.changed();
        llllllllllllllllllIllIlIIIllIIII.callAction();
    }

    private void setFromSetting() {
        ColorSettingScreen llllllllllllllllllIllIlIIlIlIIlI;
        SettingColor llllllllllllllllllIllIlIIlIlIIIl = llllllllllllllllllIllIlIIlIlIIlI.setting.get();
        if (llllllllllllllllllIllIlIIlIlIIIl.r != llllllllllllllllllIllIlIIlIlIIlI.rItb.get()) {
            llllllllllllllllllIllIlIIlIlIIlI.rItb.set(llllllllllllllllllIllIlIIlIlIIIl.r);
        }
        if (llllllllllllllllllIllIlIIlIlIIIl.g != llllllllllllllllllIllIlIIlIlIIlI.gItb.get()) {
            llllllllllllllllllIllIlIIlIlIIlI.gItb.set(llllllllllllllllllIllIlIIlIlIIIl.g);
        }
        if (llllllllllllllllllIllIlIIlIlIIIl.b != llllllllllllllllllIllIlIIlIlIIlI.bItb.get()) {
            llllllllllllllllllIllIlIIlIlIIlI.bItb.set(llllllllllllllllllIllIlIIlIlIIIl.b);
        }
        if (llllllllllllllllllIllIlIIlIlIIIl.a != llllllllllllllllllIllIlIIlIlIIlI.aItb.get()) {
            llllllllllllllllllIllIlIIlIlIIlI.aItb.set(llllllllllllllllllIllIlIIlIlIIIl.a);
        }
        if (llllllllllllllllllIllIlIIlIlIIIl.rainbowSpeed != llllllllllllllllllIllIlIIlIlIIlI.rainbowSpeed.get()) {
            llllllllllllllllllIllIlIIlIlIIlI.rainbowSpeed.set(llllllllllllllllllIllIlIIlIlIIIl.rainbowSpeed);
        }
        llllllllllllllllllIllIlIIlIlIIlI.displayQuad.color.set(llllllllllllllllllIllIlIIlIlIIlI.setting.get());
        llllllllllllllllllIllIlIIlIlIIlI.hueQuad.calculateFromSetting(true);
        llllllllllllllllllIllIlIIlIlIIlI.brightnessQuad.calculateFromColor(llllllllllllllllllIllIlIIlIlIIlI.setting.get(), true);
    }

    private void rgbaChanged() {
        ColorSettingScreen llllllllllllllllllIllIlIIlIIIlII;
        Color llllllllllllllllllIllIlIIlIIIlIl = llllllllllllllllllIllIlIIlIIIlII.setting.get();
        llllllllllllllllllIllIlIIlIIIlIl.r = llllllllllllllllllIllIlIIlIIIlII.rItb.get();
        llllllllllllllllllIllIlIIlIIIlIl.g = llllllllllllllllllIllIlIIlIIIlII.gItb.get();
        llllllllllllllllllIllIlIIlIIIlIl.b = llllllllllllllllllIllIlIIlIIIlII.bItb.get();
        llllllllllllllllllIllIlIIlIIIlIl.a = llllllllllllllllllIllIlIIlIIIlII.aItb.get();
        llllllllllllllllllIllIlIIlIIIlIl.validate();
        if (llllllllllllllllllIllIlIIlIIIlIl.r != llllllllllllllllllIllIlIIlIIIlII.rItb.get()) {
            llllllllllllllllllIllIlIIlIIIlII.rItb.set(llllllllllllllllllIllIlIIlIIIlIl.r);
        }
        if (llllllllllllllllllIllIlIIlIIIlIl.g != llllllllllllllllllIllIlIIlIIIlII.gItb.get()) {
            llllllllllllllllllIllIlIIlIIIlII.gItb.set(llllllllllllllllllIllIlIIlIIIlIl.g);
        }
        if (llllllllllllllllllIllIlIIlIIIlIl.b != llllllllllllllllllIllIlIIlIIIlII.bItb.get()) {
            llllllllllllllllllIllIlIIlIIIlII.bItb.set(llllllllllllllllllIllIlIIlIIIlIl.b);
        }
        if (llllllllllllllllllIllIlIIlIIIlIl.a != llllllllllllllllllIllIlIIlIIIlII.aItb.get()) {
            llllllllllllllllllIllIlIIlIIIlII.aItb.set(llllllllllllllllllIllIlIIlIIIlIl.a);
        }
        llllllllllllllllllIllIlIIlIIIlII.displayQuad.color.set(llllllllllllllllllIllIlIIlIIIlIl);
        llllllllllllllllllIllIlIIlIIIlII.hueQuad.calculateFromSetting(true);
        llllllllllllllllllIllIlIIlIIIlII.brightnessQuad.calculateFromColor(llllllllllllllllllIllIlIIlIIIlII.setting.get(), true);
        llllllllllllllllllIllIlIIlIIIlII.setting.changed();
        llllllllllllllllllIllIlIIlIIIlII.callAction();
    }

    public ColorSettingScreen(GuiTheme llllllllllllllllllIllIlIIllIIIll, Setting<SettingColor> llllllllllllllllllIllIlIIlIllIlI) {
        super(llllllllllllllllllIllIlIIllIIIll, "Select Color");
        ColorSettingScreen llllllllllllllllllIllIlIIlIlllII;
        llllllllllllllllllIllIlIIlIlllII.setting = llllllllllllllllllIllIlIIlIllIlI;
        llllllllllllllllllIllIlIIlIlllII.displayQuad = llllllllllllllllllIllIlIIlIlllII.add(llllllllllllllllllIllIlIIllIIIll.quad(llllllllllllllllllIllIlIIlIllIlI.get())).expandX().widget();
        llllllllllllllllllIllIlIIlIlllII.brightnessQuad = llllllllllllllllllIllIlIIlIlllII.add(llllllllllllllllllIllIlIIlIlllII.new WBrightnessQuad()).expandX().widget();
        llllllllllllllllllIllIlIIlIlllII.hueQuad = llllllllllllllllllIllIlIIlIlllII.add(llllllllllllllllllIllIlIIlIlllII.new WHueQuad()).expandX().widget();
        WTable llllllllllllllllllIllIlIIllIIIIl = llllllllllllllllllIllIlIIlIlllII.add(llllllllllllllllllIllIlIIllIIIll.table()).expandX().widget();
        llllllllllllllllllIllIlIIllIIIIl.add(llllllllllllllllllIllIlIIllIIIll.label("R:"));
        llllllllllllllllllIllIlIIlIlllII.rItb = llllllllllllllllllIllIlIIllIIIIl.add(llllllllllllllllllIllIlIIllIIIll.intEdit(llllllllllllllllllIllIlIIlIllIlI.get().r, 0, 255)).expandX().widget();
        llllllllllllllllllIllIlIIlIlllII.rItb.min = 0;
        llllllllllllllllllIllIlIIlIlllII.rItb.max = 255;
        llllllllllllllllllIllIlIIlIlllII.rItb.action = llllllllllllllllllIllIlIIlIlllII::rgbaChanged;
        llllllllllllllllllIllIlIIllIIIIl.row();
        llllllllllllllllllIllIlIIllIIIIl.add(llllllllllllllllllIllIlIIllIIIll.label("G:"));
        llllllllllllllllllIllIlIIlIlllII.gItb = llllllllllllllllllIllIlIIllIIIIl.add(llllllllllllllllllIllIlIIllIIIll.intEdit(llllllllllllllllllIllIlIIlIllIlI.get().g, 0, 255)).expandX().widget();
        llllllllllllllllllIllIlIIlIlllII.gItb.min = 0;
        llllllllllllllllllIllIlIIlIlllII.gItb.max = 255;
        llllllllllllllllllIllIlIIlIlllII.gItb.action = llllllllllllllllllIllIlIIlIlllII::rgbaChanged;
        llllllllllllllllllIllIlIIllIIIIl.row();
        llllllllllllllllllIllIlIIllIIIIl.add(llllllllllllllllllIllIlIIllIIIll.label("B:"));
        llllllllllllllllllIllIlIIlIlllII.bItb = llllllllllllllllllIllIlIIllIIIIl.add(llllllllllllllllllIllIlIIllIIIll.intEdit(llllllllllllllllllIllIlIIlIllIlI.get().b, 0, 255)).expandX().widget();
        llllllllllllllllllIllIlIIlIlllII.bItb.min = 0;
        llllllllllllllllllIllIlIIlIlllII.bItb.max = 255;
        llllllllllllllllllIllIlIIlIlllII.bItb.action = llllllllllllllllllIllIlIIlIlllII::rgbaChanged;
        llllllllllllllllllIllIlIIllIIIIl.row();
        llllllllllllllllllIllIlIIllIIIIl.add(llllllllllllllllllIllIlIIllIIIll.label("A:"));
        llllllllllllllllllIllIlIIlIlllII.aItb = llllllllllllllllllIllIlIIllIIIIl.add(llllllllllllllllllIllIlIIllIIIll.intEdit(llllllllllllllllllIllIlIIlIllIlI.get().a, 0, 255)).expandX().widget();
        llllllllllllllllllIllIlIIlIlllII.aItb.min = 0;
        llllllllllllllllllIllIlIIlIlllII.aItb.max = 255;
        llllllllllllllllllIllIlIIlIlllII.aItb.action = llllllllllllllllllIllIlIIlIlllII::rgbaChanged;
        WHorizontalList llllllllllllllllllIllIlIIllIIIII = llllllllllllllllllIllIlIIlIlllII.add(llllllllllllllllllIllIlIIllIIIll.horizontalList()).expandX().widget();
        llllllllllllllllllIllIlIIllIIIII.add(llllllllllllllllllIllIlIIllIIIll.label("Rainbow: "));
        llllllllllllllllllIllIlIIlIlllII.rainbowSpeed = llllllllllllllllllIllIlIIllIIIll.doubleEdit(llllllllllllllllllIllIlIIlIllIlI.get().rainbowSpeed, 0.0, 0.025);
        llllllllllllllllllIllIlIIlIlllII.rainbowSpeed.min = 0.0;
        llllllllllllllllllIllIlIIlIlllII.rainbowSpeed.action = () -> {
            ColorSettingScreen llllllllllllllllllIllIlIIIIlIllI;
            ((SettingColor)llllllllllllllllllIllIlIIIIlIlIl.get()).rainbowSpeed = llllllllllllllllllIllIlIIIIlIllI.rainbowSpeed.get();
            llllllllllllllllllIllIlIIlIllIlI.changed();
        };
        llllllllllllllllllIllIlIIlIlllII.rainbowSpeed.small = true;
        llllllllllllllllllIllIlIIllIIIII.add(llllllllllllllllllIllIlIIlIlllII.rainbowSpeed).expandX();
        WHorizontalList llllllllllllllllllIllIlIIlIlllll = llllllllllllllllllIllIlIIlIlllII.add(llllllllllllllllllIllIlIIllIIIll.horizontalList()).expandX().widget();
        WButton llllllllllllllllllIllIlIIlIllllI = llllllllllllllllllIllIlIIlIlllll.add(llllllllllllllllllIllIlIIllIIIll.button("Back")).expandX().widget();
        llllllllllllllllllIllIlIIlIllllI.action = llllllllllllllllllIllIlIIlIlllII::onClose;
        WButton llllllllllllllllllIllIlIIlIlllIl = llllllllllllllllllIllIlIIlIlllll.add(llllllllllllllllllIllIlIIllIIIll.button(GuiRenderer.RESET)).widget();
        llllllllllllllllllIllIlIIlIlllIl.action = () -> {
            ColorSettingScreen llllllllllllllllllIllIlIIIIllIlI;
            llllllllllllllllllIllIlIIlIllIlI.reset();
            llllllllllllllllllIllIlIIIIllIlI.setFromSetting();
            llllllllllllllllllIllIlIIIIllIlI.callAction();
        };
        llllllllllllllllllIllIlIIlIlllII.hueQuad.calculateFromSetting(false);
        llllllllllllllllllIllIlIIlIlllII.brightnessQuad.calculateFromColor(llllllllllllllllllIllIlIIlIllIlI.get(), false);
    }

    private class WHueQuad
    extends WWidget {
        private /* synthetic */ boolean calculateHandleXOnLayout;
        private /* synthetic */ boolean dragging;
        private /* synthetic */ double lastMouseX;
        private /* synthetic */ double handleX;
        private /* synthetic */ double hueAngle;
        private final /* synthetic */ Color color;

        @Override
        public boolean onMouseClicked(double lIIIllIIlIlIII, double lIIIllIIlIIlll, int lIIIllIIlIIllI, boolean lIIIllIIlIIIll) {
            WHueQuad lIIIllIIlIIlII;
            if (lIIIllIIlIIIll) {
                return false;
            }
            if (lIIIllIIlIIlII.mouseOver) {
                lIIIllIIlIIlII.dragging = true;
                lIIIllIIlIIlII.handleX = lIIIllIIlIIlII.lastMouseX - lIIIllIIlIIlII.x;
                lIIIllIIlIIlII.calculateHueAngleFromHandleX();
                lIIIllIIlIIlII.ColorSettingScreen.this.hsvChanged();
                return true;
            }
            return false;
        }

        void calculateFromSetting(boolean lIIIllIllIllII) {
            WHueQuad lIIIllIllIIllI;
            Color lIIIllIllIlIll = (Color)lIIIllIllIIllI.ColorSettingScreen.this.setting.get();
            boolean lIIIllIllIlIlI = false;
            double lIIIllIllIlIIl = Math.min(lIIIllIllIlIll.r, lIIIllIllIlIll.g);
            lIIIllIllIlIIl = lIIIllIllIlIIl < (double)lIIIllIllIlIll.b ? lIIIllIllIlIIl : (double)lIIIllIllIlIll.b;
            double lIIIllIllIlIII = Math.max(lIIIllIllIlIll.r, lIIIllIllIlIll.g);
            double lIIIllIllIIlll = (lIIIllIllIlIII = lIIIllIllIlIII > (double)lIIIllIllIlIll.b ? lIIIllIllIlIII : (double)lIIIllIllIlIll.b) - lIIIllIllIlIIl;
            if (lIIIllIllIIlll < 1.0E-5) {
                lIIIllIllIIllI.hueAngle = 0.0;
                lIIIllIllIlIlI = true;
            }
            if (!lIIIllIllIlIlI) {
                if (lIIIllIllIlIII <= 0.0) {
                    lIIIllIllIIllI.hueAngle = 0.0;
                    lIIIllIllIlIlI = true;
                }
                if (!lIIIllIllIlIlI) {
                    lIIIllIllIIllI.hueAngle = (double)lIIIllIllIlIll.r >= lIIIllIllIlIII ? (double)(lIIIllIllIlIll.g - lIIIllIllIlIll.b) / lIIIllIllIIlll : ((double)lIIIllIllIlIll.g >= lIIIllIllIlIII ? 2.0 + (double)(lIIIllIllIlIll.b - lIIIllIllIlIll.r) / lIIIllIllIIlll : 4.0 + (double)(lIIIllIllIlIll.r - lIIIllIllIlIll.g) / lIIIllIllIIlll);
                    lIIIllIllIIllI.hueAngle *= 60.0;
                    if (lIIIllIllIIllI.hueAngle < 0.0) {
                        lIIIllIllIIllI.hueAngle += 360.0;
                    }
                }
            }
            if (lIIIllIllIllII) {
                double lIIIllIllIlllI = lIIIllIllIIllI.hueAngle / 360.0;
                lIIIllIllIIllI.handleX = lIIIllIllIlllI * lIIIllIllIIllI.width;
            } else {
                lIIIllIllIIllI.calculateHandleXOnLayout = true;
            }
        }

        @Override
        protected void onRender(GuiRenderer lIIIlIllllllII, double lIIIllIIIIIIll, double lIIIllIIIIIIlI, double lIIIllIIIIIIIl) {
            WHueQuad lIIIlIllllllIl;
            double lIIIllIIIIIIII = lIIIlIllllllIl.width / (double)(HUE_COLORS.length - 1);
            double lIIIlIllllllll = lIIIlIllllllIl.x;
            for (int lIIIllIIIIIllI = 0; lIIIllIIIIIllI < HUE_COLORS.length - 1; ++lIIIllIIIIIllI) {
                lIIIlIllllllII.quad(lIIIlIllllllll, lIIIlIllllllIl.y, lIIIllIIIIIIII, lIIIlIllllllIl.height, HUE_COLORS[lIIIllIIIIIllI], HUE_COLORS[lIIIllIIIIIllI + 1], HUE_COLORS[lIIIllIIIIIllI + 1], HUE_COLORS[lIIIllIIIIIllI]);
                lIIIlIllllllll += lIIIllIIIIIIII;
            }
            double lIIIlIlllllllI = lIIIlIllllllIl.theme.scale(2.0);
            lIIIlIllllllII.quad(lIIIlIllllllIl.x + lIIIlIllllllIl.handleX - lIIIlIlllllllI / 2.0, lIIIlIllllllIl.y, lIIIlIlllllllI, lIIIlIllllllIl.height, WHITE);
        }

        void calculateColor() {
            double lIIIllIIllIllI;
            double lIIIllIIllIlll;
            double lIIIllIIlllIII;
            WHueQuad lIIIllIIllllll;
            double lIIIllIIlllllI = lIIIllIIllllll.hueAngle;
            if (lIIIllIIlllllI >= 360.0) {
                lIIIllIIlllllI = 0.0;
            }
            int lIIIllIIlllIIl = (int)(lIIIllIIlllllI /= 60.0);
            double lIIIllIIlllIlI = lIIIllIIlllllI - (double)lIIIllIIlllIIl;
            double lIIIllIIllllIl = 0.0;
            double lIIIllIIllllII = 1.0 * (1.0 - 1.0 * lIIIllIIlllIlI);
            double lIIIllIIlllIll = 1.0 * (1.0 - 1.0 * (1.0 - lIIIllIIlllIlI));
            switch (lIIIllIIlllIIl) {
                case 0: {
                    double lIIIllIlIIlllI = 1.0;
                    double lIIIllIlIIllIl = lIIIllIIlllIll;
                    double lIIIllIlIIllII = lIIIllIIllllIl;
                    break;
                }
                case 1: {
                    double lIIIllIlIIlIll = lIIIllIIllllII;
                    double lIIIllIlIIlIlI = 1.0;
                    double lIIIllIlIIlIIl = lIIIllIIllllIl;
                    break;
                }
                case 2: {
                    double lIIIllIlIIlIII = lIIIllIIllllIl;
                    double lIIIllIlIIIlll = 1.0;
                    double lIIIllIlIIIllI = lIIIllIIlllIll;
                    break;
                }
                case 3: {
                    double lIIIllIlIIIlIl = lIIIllIIllllIl;
                    double lIIIllIlIIIlII = lIIIllIIllllII;
                    double lIIIllIlIIIIll = 1.0;
                    break;
                }
                case 4: {
                    double lIIIllIlIIIIlI = lIIIllIIlllIll;
                    double lIIIllIlIIIIIl = lIIIllIIllllIl;
                    double lIIIllIlIIIIII = 1.0;
                    break;
                }
                default: {
                    lIIIllIIlllIII = 1.0;
                    lIIIllIIllIlll = lIIIllIIllllIl;
                    lIIIllIIllIllI = lIIIllIIllllII;
                }
            }
            lIIIllIIllllll.color.r = (int)(lIIIllIIlllIII * 255.0);
            lIIIllIIllllll.color.g = (int)(lIIIllIIllIlll * 255.0);
            lIIIllIIllllll.color.b = (int)(lIIIllIIllIllI * 255.0);
            lIIIllIIllllll.color.validate();
        }

        void calculateHueAngleFromHandleX() {
            WHueQuad lIIIllIIIIllll;
            double lIIIllIIIIlllI = lIIIllIIIIllll.handleX / (lIIIllIIIIllll.width - 4.0);
            lIIIllIIIIllll.hueAngle = lIIIllIIIIlllI * 360.0;
        }

        @Override
        protected void onCalculateWidgetPositions() {
            WHueQuad lIIIllIlIllIll;
            if (lIIIllIlIllIll.calculateHandleXOnLayout) {
                double lIIIllIlIlllII = lIIIllIlIllIll.hueAngle / 360.0;
                lIIIllIlIllIll.handleX = lIIIllIlIlllII * lIIIllIlIllIll.width;
                lIIIllIlIllIll.calculateHandleXOnLayout = false;
            }
            super.onCalculateWidgetPositions();
        }

        @Override
        public void onMouseMoved(double lIIIllIIIllIII, double lIIIllIIIlIlll, double lIIIllIIIlIllI, double lIIIllIIIlIlIl) {
            WHueQuad lIIIllIIIllIIl;
            if (lIIIllIIIllIIl.dragging) {
                if (lIIIllIIIllIII >= lIIIllIIIllIIl.x && lIIIllIIIllIII <= lIIIllIIIllIIl.x + lIIIllIIIllIIl.width) {
                    lIIIllIIIllIIl.handleX += lIIIllIIIllIII - lIIIllIIIlIllI;
                    lIIIllIIIllIIl.handleX = Utils.clamp(lIIIllIIIllIIl.handleX, 0.0, lIIIllIIIllIIl.width);
                } else if (lIIIllIIIllIIl.handleX > 0.0 && lIIIllIIIllIII < lIIIllIIIllIIl.x) {
                    lIIIllIIIllIIl.handleX = 0.0;
                } else if (lIIIllIIIllIIl.handleX < lIIIllIIIllIIl.width && lIIIllIIIllIII > lIIIllIIIllIIl.x + lIIIllIIIllIIl.width) {
                    lIIIllIIIllIIl.handleX = lIIIllIIIllIIl.width;
                }
                lIIIllIIIllIIl.calculateHueAngleFromHandleX();
                lIIIllIIIllIIl.ColorSettingScreen.this.hsvChanged();
            }
            lIIIllIIIllIIl.lastMouseX = lIIIllIIIllIII;
        }

        @Override
        protected void onCalculateSize() {
            WHueQuad lIIIllIlllIlll;
            lIIIllIlllIlll.width = lIIIllIlllIlll.theme.scale(75.0);
            lIIIllIlllIlll.height = lIIIllIlllIlll.theme.scale(10.0);
        }

        private WHueQuad() {
            WHueQuad lIIIllIllllIll;
            lIIIllIllllIll.color = new Color();
        }

        @Override
        public boolean onMouseReleased(double lIIIllIIlIIIII, double lIIIllIIIlllll, int lIIIllIIIllllI) {
            WHueQuad lIIIllIIlIIIIl;
            if (lIIIllIIlIIIIl.dragging) {
                lIIIllIIlIIIIl.dragging = false;
            }
            return lIIIllIIlIIIIl.mouseOver;
        }
    }

    private class WBrightnessQuad
    extends WWidget {
        /* synthetic */ double lastMouseY;
        /* synthetic */ double value;
        /* synthetic */ boolean dragging;
        /* synthetic */ double handleY;
        /* synthetic */ double handleX;
        /* synthetic */ double fixedHeight;
        /* synthetic */ boolean calculateHandlePosOnLayout;
        /* synthetic */ double lastMouseX;
        /* synthetic */ double saturation;

        @Override
        protected void onCalculateWidgetPositions() {
            WBrightnessQuad llllIIllIIIIlIl;
            if (llllIIllIIIIlIl.calculateHandlePosOnLayout) {
                llllIIllIIIIlIl.handleX = llllIIllIIIIlIl.saturation * llllIIllIIIIlIl.width;
                llllIIllIIIIlIl.handleY = (1.0 - llllIIllIIIIlIl.value) * llllIIllIIIIlIl.height;
                llllIIllIIIIlIl.calculateHandlePosOnLayout = false;
            }
            super.onCalculateWidgetPositions();
        }

        void calculateFromColor(Color llllIIllIIlIIIl, boolean llllIIllIIlIIII) {
            double llllIIllIIIllll = Math.min(Math.min(llllIIllIIlIIIl.r, llllIIllIIlIIIl.g), llllIIllIIlIIIl.b);
            double llllIIllIIIlllI = Math.max(Math.max(llllIIllIIlIIIl.r, llllIIllIIlIIIl.g), llllIIllIIlIIIl.b);
            double llllIIllIIIllIl = llllIIllIIIlllI - llllIIllIIIllll;
            llllIIllIIIllII.value = llllIIllIIIlllI / 255.0;
            llllIIllIIIllII.saturation = llllIIllIIIllIl == 0.0 ? 0.0 : llllIIllIIIllIl / llllIIllIIIlllI;
            if (llllIIllIIlIIII) {
                WBrightnessQuad llllIIllIIIllII;
                llllIIllIIIllII.handleX = llllIIllIIIllII.saturation * llllIIllIIIllII.width;
                llllIIllIIIllII.handleY = (1.0 - llllIIllIIIllII.value) * llllIIllIIIllII.height;
            } else {
                llllIIllIIIllII.calculateHandlePosOnLayout = true;
            }
        }

        private WBrightnessQuad() {
            WBrightnessQuad llllIIllIlIIIII;
            llllIIllIlIIIII.fixedHeight = -1.0;
        }

        @Override
        protected void onCalculateSize() {
            WBrightnessQuad llllIIllIIlllII;
            double llllIIllIIllIll;
            llllIIllIIlllII.width = llllIIllIIllIll = llllIIllIIlllII.theme.scale(75.0);
            llllIIllIIlllII.height = llllIIllIIllIll;
            if (llllIIllIIlllII.fixedHeight != -1.0) {
                llllIIllIIlllII.height = llllIIllIIlllII.fixedHeight;
                llllIIllIIlllII.fixedHeight = -1.0;
            }
        }

        @Override
        protected void onRender(GuiRenderer llllIIlIlIlIIlI, double llllIIlIlIlIlll, double llllIIlIlIlIllI, double llllIIlIlIlIlIl) {
            WBrightnessQuad llllIIlIlIlIIll;
            if (llllIIlIlIlIIll.height != llllIIlIlIlIIll.width) {
                llllIIlIlIlIIll.fixedHeight = llllIIlIlIlIIll.width;
                llllIIlIlIlIIll.invalidate();
            }
            llllIIlIlIlIIll.ColorSettingScreen.this.hueQuad.calculateColor();
            llllIIlIlIlIIlI.quad(llllIIlIlIlIIll.x, llllIIlIlIlIIll.y, llllIIlIlIlIIll.width, llllIIlIlIlIIll.height, WHITE, llllIIlIlIlIIll.ColorSettingScreen.this.hueQuad.color, BLACK, BLACK);
            double llllIIlIlIlIlII = llllIIlIlIlIIll.theme.scale(2.0);
            llllIIlIlIlIIlI.quad(llllIIlIlIlIIll.x + llllIIlIlIlIIll.handleX - llllIIlIlIlIlII / 2.0, llllIIlIlIlIIll.y + llllIIlIlIlIIll.handleY - llllIIlIlIlIlII / 2.0, llllIIlIlIlIlII, llllIIlIlIlIlII, WHITE);
        }

        @Override
        public boolean onMouseClicked(double llllIIllIIIIIII, double llllIIlIlllllll, int llllIIlIllllllI, boolean llllIIlIllllIll) {
            WBrightnessQuad llllIIlIlllllII;
            if (llllIIlIllllIll) {
                return false;
            }
            if (llllIIlIlllllII.mouseOver) {
                llllIIlIlllllII.dragging = true;
                llllIIlIlllllII.handleX = llllIIlIlllllII.lastMouseX - llllIIlIlllllII.x;
                llllIIlIlllllII.handleY = llllIIlIlllllII.lastMouseY - llllIIlIlllllII.y;
                llllIIlIlllllII.handleMoved();
                return true;
            }
            return false;
        }

        @Override
        public void onMouseMoved(double llllIIlIllIlllI, double llllIIlIllIlIII, double llllIIlIllIIlll, double llllIIlIllIlIll) {
            WBrightnessQuad llllIIlIllIlIlI;
            if (llllIIlIllIlIlI.dragging) {
                if (llllIIlIllIlllI >= llllIIlIllIlIlI.x && llllIIlIllIlllI <= llllIIlIllIlIlI.x + llllIIlIllIlIlI.width) {
                    llllIIlIllIlIlI.handleX += llllIIlIllIlllI - llllIIlIllIIlll;
                } else if (llllIIlIllIlIlI.handleX > 0.0 && llllIIlIllIlllI < llllIIlIllIlIlI.x) {
                    llllIIlIllIlIlI.handleX = 0.0;
                } else if (llllIIlIllIlIlI.handleX < llllIIlIllIlIlI.width && llllIIlIllIlllI > llllIIlIllIlIlI.x + llllIIlIllIlIlI.width) {
                    llllIIlIllIlIlI.handleX = llllIIlIllIlIlI.width;
                }
                if (llllIIlIllIlIII >= llllIIlIllIlIlI.y && llllIIlIllIlIII <= llllIIlIllIlIlI.y + llllIIlIllIlIlI.height) {
                    llllIIlIllIlIlI.handleY += llllIIlIllIlIII - llllIIlIllIlIll;
                } else if (llllIIlIllIlIlI.handleY > 0.0 && llllIIlIllIlIII < llllIIlIllIlIlI.y) {
                    llllIIlIllIlIlI.handleY = 0.0;
                } else if (llllIIlIllIlIlI.handleY < llllIIlIllIlIlI.height && llllIIlIllIlIII > llllIIlIllIlIlI.y + llllIIlIllIlIlI.height) {
                    llllIIlIllIlIlI.handleY = llllIIlIllIlIlI.height;
                }
                llllIIlIllIlIlI.handleMoved();
            }
            llllIIlIllIlIlI.lastMouseX = llllIIlIllIlllI;
            llllIIlIllIlIlI.lastMouseY = llllIIlIllIlIII;
        }

        @Override
        public boolean onMouseReleased(double llllIIlIllllIII, double llllIIlIlllIlll, int llllIIlIlllIllI) {
            WBrightnessQuad llllIIlIlllIlIl;
            if (llllIIlIlllIlIl.dragging) {
                llllIIlIlllIlIl.dragging = false;
            }
            return false;
        }

        void handleMoved() {
            WBrightnessQuad llllIIlIllIIIlI;
            double llllIIlIllIIIIl = llllIIlIllIIIlI.handleX / llllIIlIllIIIlI.width;
            double llllIIlIllIIIII = llllIIlIllIIIlI.handleY / llllIIlIllIIIlI.height;
            llllIIlIllIIIlI.saturation = llllIIlIllIIIIl;
            llllIIlIllIIIlI.value = 1.0 - llllIIlIllIIIII;
            llllIIlIllIIIlI.ColorSettingScreen.this.hsvChanged();
        }
    }
}

