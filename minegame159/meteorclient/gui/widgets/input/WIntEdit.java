/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.input;

import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.input.WSlider;
import minegame159.meteorclient.gui.widgets.input.WTextBox;

public class WIntEdit
extends WHorizontalList {
    private /* synthetic */ int value;
    private /* synthetic */ WSlider slider;
    private final /* synthetic */ int sliderMax;
    private /* synthetic */ WTextBox textBox;
    public /* synthetic */ boolean hasSlider;
    public /* synthetic */ Runnable actionOnRelease;
    public /* synthetic */ boolean small;
    public /* synthetic */ Integer min;
    public /* synthetic */ Integer max;
    public /* synthetic */ Runnable action;
    private final /* synthetic */ int sliderMin;

    public int get() {
        WIntEdit llllllllllllllllllllIlIIllIlIIlI;
        return llllllllllllllllllllIlIIllIlIIlI.value;
    }

    private boolean filter(String llllllllllllllllllllIlIIllIllIII, char llllllllllllllllllllIlIIllIlIlll) {
        boolean llllllllllllllllllllIlIIllIllIlI;
        boolean llllllllllllllllllllIlIIllIllIIl = true;
        if (llllllllllllllllllllIlIIllIlIlll == '-' && llllllllllllllllllllIlIIllIllIII.isEmpty()) {
            boolean llllllllllllllllllllIlIIllIlllll = true;
            llllllllllllllllllllIlIIllIllIIl = false;
        } else {
            llllllllllllllllllllIlIIllIllIlI = Character.isDigit(llllllllllllllllllllIlIIllIlIlll);
        }
        if (llllllllllllllllllllIlIIllIllIlI && llllllllllllllllllllIlIIllIllIIl) {
            try {
                Integer.parseInt(String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIIllIllIII).append(llllllllllllllllllllIlIIllIlIlll)));
            }
            catch (NumberFormatException llllllllllllllllllllIlIIllIllllI) {
                llllllllllllllllllllIlIIllIllIlI = false;
            }
        }
        return llllllllllllllllllllIlIIllIllIlI;
    }

    public void set(int llllllllllllllllllllIlIIllIIllIl) {
        WIntEdit llllllllllllllllllllIlIIllIIllII;
        llllllllllllllllllllIlIIllIIllII.value = llllllllllllllllllllIlIIllIIllIl;
        llllllllllllllllllllIlIIllIIllII.textBox.set(Integer.toString(llllllllllllllllllllIlIIllIIllIl));
        if (llllllllllllllllllllIlIIllIIllII.slider != null) {
            llllllllllllllllllllIlIIllIIllII.slider.set(llllllllllllllllllllIlIIllIIllIl);
        }
    }

    public WIntEdit(int llllllllllllllllllllIlIIlllIlIlI, int llllllllllllllllllllIlIIlllIllIl, int llllllllllllllllllllIlIIlllIllII) {
        WIntEdit llllllllllllllllllllIlIIlllIlIll;
        llllllllllllllllllllIlIIlllIlIll.hasSlider = true;
        llllllllllllllllllllIlIIlllIlIll.value = llllllllllllllllllllIlIIlllIlIlI;
        llllllllllllllllllllIlIIlllIlIll.sliderMin = llllllllllllllllllllIlIIlllIllIl;
        llllllllllllllllllllIlIIlllIlIll.sliderMax = llllllllllllllllllllIlIIlllIllII;
    }

    @Override
    public void init() {
        WIntEdit llllllllllllllllllllIlIIlllIIllI;
        llllllllllllllllllllIlIIlllIIllI.textBox = llllllllllllllllllllIlIIlllIIllI.add(llllllllllllllllllllIlIIlllIIllI.theme.textBox(Integer.toString(llllllllllllllllllllIlIIlllIIllI.value), (arg_0, arg_1) -> llllllllllllllllllllIlIIlllIIllI.filter(arg_0, arg_1))).minWidth(75.0).widget();
        if (llllllllllllllllllllIlIIlllIIllI.hasSlider) {
            llllllllllllllllllllIlIIlllIIllI.slider = llllllllllllllllllllIlIIlllIIllI.add(llllllllllllllllllllIlIIlllIIllI.theme.slider(llllllllllllllllllllIlIIlllIIllI.value, llllllllllllllllllllIlIIlllIIllI.sliderMin, llllllllllllllllllllIlIIlllIIllI.sliderMax)).minWidth(llllllllllllllllllllIlIIlllIIllI.small ? 125.0 - llllllllllllllllllllIlIIlllIIllI.spacing : 200.0).centerY().expandX().widget();
        }
        llllllllllllllllllllIlIIlllIIllI.textBox.actionOnUnfocused = () -> {
            WIntEdit llllllllllllllllllllIlIIlIllllll;
            int llllllllllllllllllllIlIIlIlllllI = llllllllllllllllllllIlIIlIllllll.value;
            llllllllllllllllllllIlIIlIllllll.value = llllllllllllllllllllIlIIlIllllll.textBox.get().isEmpty() ? 0 : (llllllllllllllllllllIlIIlIllllll.textBox.get().equals("-") ? 0 : Integer.parseInt(llllllllllllllllllllIlIIlIllllll.textBox.get()));
            if (llllllllllllllllllllIlIIlIllllll.slider != null) {
                llllllllllllllllllllIlIIlIllllll.slider.set(llllllllllllllllllllIlIIlIllllll.value);
            }
            if (llllllllllllllllllllIlIIlIllllll.value != llllllllllllllllllllIlIIlIlllllI) {
                if (llllllllllllllllllllIlIIlIllllll.action != null) {
                    llllllllllllllllllllIlIIlIllllll.action.run();
                }
                if (llllllllllllllllllllIlIIlIllllll.actionOnRelease != null) {
                    llllllllllllllllllllIlIIlIllllll.actionOnRelease.run();
                }
            }
        };
        if (llllllllllllllllllllIlIIlllIIllI.slider != null) {
            llllllllllllllllllllIlIIlllIIllI.slider.action = () -> {
                WIntEdit llllllllllllllllllllIlIIllIIIIll;
                int llllllllllllllllllllIlIIllIIIlII = llllllllllllllllllllIlIIllIIIIll.value;
                llllllllllllllllllllIlIIllIIIIll.value = (int)Math.round(llllllllllllllllllllIlIIllIIIIll.slider.get());
                llllllllllllllllllllIlIIllIIIIll.textBox.set(Integer.toString(llllllllllllllllllllIlIIllIIIIll.value));
                if (llllllllllllllllllllIlIIllIIIIll.action != null && llllllllllllllllllllIlIIllIIIIll.value != llllllllllllllllllllIlIIllIIIlII) {
                    llllllllllllllllllllIlIIllIIIIll.action.run();
                }
            };
            llllllllllllllllllllIlIIlllIIllI.slider.actionOnRelease = () -> {
                WIntEdit llllllllllllllllllllIlIIllIIlIII;
                if (llllllllllllllllllllIlIIllIIlIII.actionOnRelease != null) {
                    llllllllllllllllllllIlIIllIIlIII.actionOnRelease.run();
                }
            };
        }
    }
}

