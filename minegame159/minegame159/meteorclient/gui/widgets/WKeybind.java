/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.utils.misc.Keybind;

public class WKeybind
extends WHorizontalList {
    private final boolean addBindText;
    public Runnable actionOnSet;
    private WLabel label;
    private final Keybind keybind;
    public Runnable action;
    private boolean listening;

    private void lambda$init$1() {
        this.keybind.set(true, -1);
        this.reset();
        if (this.action != null) {
            this.action.run();
        }
    }

    private void lambda$init$0() {
        this.listening = true;
        this.label.set(this.appendBindText("Press any key or mouse button"));
        if (this.actionOnSet != null) {
            this.actionOnSet.run();
        }
    }

    public WKeybind(Keybind keybind, boolean bl) {
        this.keybind = keybind;
        this.addBindText = bl;
    }

    public boolean onAction(boolean bl, int n) {
        if (this.listening && this.keybind.canBindTo(bl, n)) {
            this.keybind.set(bl, n);
            this.reset();
            if (this.action != null) {
                this.action.run();
            }
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        this.label = this.add(this.theme.label("")).widget();
        WButton wButton = this.add(this.theme.button("Set")).widget();
        wButton.action = this::lambda$init$0;
        WButton wButton2 = this.add(this.theme.button("Reset")).widget();
        wButton2.action = this::lambda$init$1;
        this.refreshLabel();
    }

    private String appendBindText(String string) {
        return this.addBindText ? String.valueOf(new StringBuilder().append("Bind: ").append(string)) : string;
    }

    public void reset() {
        this.listening = false;
        this.refreshLabel();
    }

    private void refreshLabel() {
        this.label.set(this.appendBindText(this.keybind.toString()));
    }
}

