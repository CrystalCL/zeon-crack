/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.utils.misc.Keybind;

public class WKeybind
extends WHorizontalList {
    private /* synthetic */ boolean listening;
    private /* synthetic */ WLabel label;
    public /* synthetic */ Runnable actionOnSet;
    public /* synthetic */ Runnable action;
    private final /* synthetic */ Keybind keybind;
    private final /* synthetic */ boolean addBindText;

    private void refreshLabel() {
        WKeybind llllllIlIlIIII;
        llllllIlIlIIII.label.set(llllllIlIlIIII.appendBindText(llllllIlIlIIII.keybind.toString()));
    }

    private String appendBindText(String llllllIlIIllII) {
        WKeybind llllllIlIIlIll;
        return llllllIlIIlIll.addBindText ? String.valueOf(new StringBuilder().append("Bind: ").append(llllllIlIIllII)) : llllllIlIIllII;
    }

    public void reset() {
        WKeybind llllllIlIlIlII;
        llllllIlIlIlII.listening = false;
        llllllIlIlIlII.refreshLabel();
    }

    public WKeybind(Keybind llllllIllIlIIl, boolean llllllIllIlIII) {
        WKeybind llllllIllIllIl;
        llllllIllIllIl.keybind = llllllIllIlIIl;
        llllllIllIllIl.addBindText = llllllIllIlIII;
    }

    public boolean onAction(boolean llllllIlIlIlll, int llllllIlIllIIl) {
        WKeybind llllllIlIllIII;
        if (llllllIlIllIII.listening && llllllIlIllIII.keybind.canBindTo(llllllIlIlIlll, llllllIlIllIIl)) {
            llllllIlIllIII.keybind.set(llllllIlIlIlll, llllllIlIllIIl);
            llllllIlIllIII.reset();
            if (llllllIlIllIII.action != null) {
                llllllIlIllIII.action.run();
            }
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        WKeybind llllllIllIIIIl;
        llllllIllIIIIl.label = llllllIllIIIIl.add(llllllIllIIIIl.theme.label("")).widget();
        WButton llllllIllIIIll = llllllIllIIIIl.add(llllllIllIIIIl.theme.button("Set")).widget();
        llllllIllIIIll.action = () -> {
            WKeybind llllllIlIIIlII;
            llllllIlIIIlII.listening = true;
            llllllIlIIIlII.label.set(llllllIlIIIlII.appendBindText("Press any key or mouse button"));
            if (llllllIlIIIlII.actionOnSet != null) {
                llllllIlIIIlII.actionOnSet.run();
            }
        };
        WButton llllllIllIIIlI = llllllIllIIIIl.add(llllllIllIIIIl.theme.button("Reset")).widget();
        llllllIllIIIlI.action = () -> {
            WKeybind llllllIlIIIlll;
            llllllIlIIIlll.keybind.set(true, -1);
            llllllIlIIIlll.reset();
            if (llllllIlIIIlll.action != null) {
                llllllIlIIIlll.action.run();
            }
        };
        llllllIllIIIIl.refreshLabel();
    }
}

