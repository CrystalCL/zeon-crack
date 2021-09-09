/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.input;

import java.util.Locale;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.input.WSlider;
import minegame159.meteorclient.gui.widgets.input.WTextBox;

public class WDoubleEdit
extends WHorizontalList {
    public /* synthetic */ int decimalPlaces;
    public /* synthetic */ Runnable actionOnRelease;
    public /* synthetic */ Runnable action;
    public /* synthetic */ boolean small;
    public /* synthetic */ boolean noSlider;
    public /* synthetic */ Double min;
    private final /* synthetic */ double sliderMax;
    private /* synthetic */ WTextBox textBox;
    public /* synthetic */ Double max;
    private /* synthetic */ WSlider slider;
    private /* synthetic */ double value;
    private final /* synthetic */ double sliderMin;

    public void set(double llllllllllllllllIlIllIlllllIlIll) {
        WDoubleEdit llllllllllllllllIlIllIlllllIllII;
        llllllllllllllllIlIllIlllllIllII.value = llllllllllllllllIlIllIlllllIlIll;
        llllllllllllllllIlIllIlllllIllII.textBox.set(llllllllllllllllIlIllIlllllIllII.valueString());
        if (llllllllllllllllIlIllIlllllIllII.slider != null) {
            llllllllllllllllIlIllIlllllIllII.slider.set(llllllllllllllllIlIllIlllllIlIll);
        }
    }

    @Override
    public void init() {
        WDoubleEdit llllllllllllllllIlIlllIIIIIIIllI;
        llllllllllllllllIlIlllIIIIIIIllI.textBox = llllllllllllllllIlIlllIIIIIIIllI.add(llllllllllllllllIlIlllIIIIIIIllI.theme.textBox(llllllllllllllllIlIlllIIIIIIIllI.valueString(), (arg_0, arg_1) -> llllllllllllllllIlIlllIIIIIIIllI.filter(arg_0, arg_1))).minWidth(75.0).widget();
        if (!llllllllllllllllIlIlllIIIIIIIllI.noSlider) {
            llllllllllllllllIlIlllIIIIIIIllI.slider = llllllllllllllllIlIlllIIIIIIIllI.add(llllllllllllllllIlIlllIIIIIIIllI.theme.slider(llllllllllllllllIlIlllIIIIIIIllI.value, llllllllllllllllIlIlllIIIIIIIllI.sliderMin, llllllllllllllllIlIlllIIIIIIIllI.sliderMax)).minWidth(llllllllllllllllIlIlllIIIIIIIllI.small ? 125.0 - llllllllllllllllIlIlllIIIIIIIllI.spacing : 200.0).centerY().expandX().widget();
        }
        llllllllllllllllIlIlllIIIIIIIllI.textBox.actionOnUnfocused = () -> {
            WDoubleEdit llllllllllllllllIlIllIllllIllIII;
            double llllllllllllllllIlIllIllllIllIlI = llllllllllllllllIlIllIllllIllIII.value;
            llllllllllllllllIlIllIllllIllIII.value = llllllllllllllllIlIllIllllIllIII.textBox.get().isEmpty() ? 0.0 : (llllllllllllllllIlIllIllllIllIII.textBox.get().equals("-") ? 0.0 : (llllllllllllllllIlIllIllllIllIII.textBox.get().equals(".") ? 0.0 : (llllllllllllllllIlIllIllllIllIII.textBox.get().equals("-.") ? 0.0 : Double.parseDouble(llllllllllllllllIlIllIllllIllIII.textBox.get()))));
            double llllllllllllllllIlIllIllllIllIIl = llllllllllllllllIlIllIllllIllIII.value;
            if (llllllllllllllllIlIllIllllIllIII.min != null && llllllllllllllllIlIllIllllIllIII.value < llllllllllllllllIlIllIllllIllIII.min) {
                llllllllllllllllIlIllIllllIllIII.value = llllllllllllllllIlIllIllllIllIII.min;
            } else if (llllllllllllllllIlIllIllllIllIII.max != null && llllllllllllllllIlIllIllllIllIII.value > llllllllllllllllIlIllIllllIllIII.max) {
                llllllllllllllllIlIllIllllIllIII.value = llllllllllllllllIlIllIllllIllIII.max;
            }
            if (llllllllllllllllIlIllIllllIllIII.value != llllllllllllllllIlIllIllllIllIIl) {
                llllllllllllllllIlIllIllllIllIII.textBox.set(llllllllllllllllIlIllIllllIllIII.valueString());
            }
            if (llllllllllllllllIlIllIllllIllIII.slider != null) {
                llllllllllllllllIlIllIllllIllIII.slider.set(llllllllllllllllIlIllIllllIllIII.value);
            }
            if (llllllllllllllllIlIllIllllIllIII.value != llllllllllllllllIlIllIllllIllIlI) {
                if (llllllllllllllllIlIllIllllIllIII.action != null) {
                    llllllllllllllllIlIllIllllIllIII.action.run();
                }
                if (llllllllllllllllIlIllIllllIllIII.actionOnRelease != null) {
                    llllllllllllllllIlIllIllllIllIII.actionOnRelease.run();
                }
            }
        };
        if (llllllllllllllllIlIlllIIIIIIIllI.slider != null) {
            llllllllllllllllIlIlllIIIIIIIllI.slider.action = () -> {
                WDoubleEdit llllllllllllllllIlIllIlllllIIIlI;
                double llllllllllllllllIlIllIlllllIIIIl = llllllllllllllllIlIllIlllllIIIlI.value;
                llllllllllllllllIlIllIlllllIIIlI.value = llllllllllllllllIlIllIlllllIIIlI.slider.get();
                llllllllllllllllIlIllIlllllIIIlI.textBox.set(llllllllllllllllIlIllIlllllIIIlI.valueString());
                if (llllllllllllllllIlIllIlllllIIIlI.action != null && llllllllllllllllIlIllIlllllIIIlI.value != llllllllllllllllIlIllIlllllIIIIl) {
                    llllllllllllllllIlIllIlllllIIIlI.action.run();
                }
            };
            llllllllllllllllIlIlllIIIIIIIllI.slider.actionOnRelease = () -> {
                WDoubleEdit llllllllllllllllIlIllIlllllIIllI;
                if (llllllllllllllllIlIllIlllllIIllI.actionOnRelease != null) {
                    llllllllllllllllIlIllIlllllIIllI.actionOnRelease.run();
                }
            };
        }
    }

    public double get() {
        WDoubleEdit llllllllllllllllIlIllIllllllIIIl;
        return llllllllllllllllIlIllIllllllIIIl.value;
    }

    private String valueString() {
        WDoubleEdit llllllllllllllllIlIllIlllllIlIII;
        return String.format(Locale.US, String.valueOf(new StringBuilder().append("%.").append(llllllllllllllllIlIllIlllllIlIII.decimalPlaces).append("f")), llllllllllllllllIlIllIlllllIlIII.value);
    }

    private boolean filter(String llllllllllllllllIlIllIllllllllII, char llllllllllllllllIlIllIlllllllIll) {
        boolean llllllllllllllllIlIllIlllllllIlI;
        boolean llllllllllllllllIlIllIlllllllIIl = true;
        if (llllllllllllllllIlIllIlllllllIll == '-' && llllllllllllllllIlIllIllllllllII.isEmpty()) {
            boolean llllllllllllllllIlIlllIIIIIIIIII = true;
            llllllllllllllllIlIllIlllllllIIl = false;
        } else if (llllllllllllllllIlIllIlllllllIll == '.' && !llllllllllllllllIlIllIllllllllII.contains(".")) {
            boolean llllllllllllllllIlIllIllllllllll = true;
            if (llllllllllllllllIlIllIllllllllII.isEmpty()) {
                llllllllllllllllIlIllIlllllllIIl = false;
            }
        } else {
            llllllllllllllllIlIllIlllllllIlI = Character.isDigit(llllllllllllllllIlIllIlllllllIll);
        }
        if (llllllllllllllllIlIllIlllllllIlI && llllllllllllllllIlIllIlllllllIIl) {
            try {
                Double.parseDouble(String.valueOf(new StringBuilder().append(llllllllllllllllIlIllIllllllllII).append(llllllllllllllllIlIllIlllllllIll)));
            }
            catch (NumberFormatException llllllllllllllllIlIllIlllllllllI) {
                llllllllllllllllIlIllIlllllllIlI = false;
            }
        }
        return llllllllllllllllIlIllIlllllllIlI;
    }

    public WDoubleEdit(double llllllllllllllllIlIlllIIIIIIlIll, double llllllllllllllllIlIlllIIIIIIlllI, double llllllllllllllllIlIlllIIIIIIlIIl) {
        WDoubleEdit llllllllllllllllIlIlllIIIIIIllII;
        llllllllllllllllIlIlllIIIIIIllII.decimalPlaces = 3;
        llllllllllllllllIlIlllIIIIIIllII.noSlider = false;
        llllllllllllllllIlIlllIIIIIIllII.value = llllllllllllllllIlIlllIIIIIIlIll;
        llllllllllllllllIlIlllIIIIIIllII.sliderMin = llllllllllllllllIlIlllIIIIIIlllI;
        llllllllllllllllIlIlllIIIIIIllII.sliderMax = llllllllllllllllIlIlllIIIIIIlIIl;
    }
}

