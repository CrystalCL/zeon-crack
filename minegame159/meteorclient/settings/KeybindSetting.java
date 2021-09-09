/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.settings;

import java.util.function.Consumer;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.meteor.MouseButtonEvent;
import minegame159.meteorclient.gui.widgets.WKeybind;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.misc.Keybind;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import net.minecraft.nbt.NbtCompound;

public class KeybindSetting
extends Setting<Keybind> {
    public /* synthetic */ WKeybind widget;
    private final /* synthetic */ Runnable action;

    @EventHandler(priority=100)
    private void onMouseButton(MouseButtonEvent lllllllllllllllllIIllllIIlIlIIlI) {
        KeybindSetting lllllllllllllllllIIllllIIlIlIIll;
        if (lllllllllllllllllIIllllIIlIlIIlI.action == KeyAction.Release && ((Keybind)lllllllllllllllllIIllllIIlIlIIll.get()).matches(false, lllllllllllllllllIIllllIIlIlIIlI.button) && lllllllllllllllllIIllllIIlIlIIll.module.isActive() && lllllllllllllllllIIllllIIlIlIIll.action != null) {
            lllllllllllllllllIIllllIIlIlIIll.action.run();
        }
    }

    @EventHandler(priority=200)
    private void onKeyBinding(KeyEvent lllllllllllllllllIIllllIIllIlllI) {
        KeybindSetting lllllllllllllllllIIllllIIlllIIII;
        if (lllllllllllllllllIIllllIIlllIIII.widget != null && lllllllllllllllllIIllllIIlllIIII.widget.onAction(true, lllllllllllllllllIIllllIIllIlllI.key)) {
            lllllllllllllllllIIllllIIllIlllI.cancel();
        }
    }

    @EventHandler(priority=200)
    private void onMouseButtonBinding(MouseButtonEvent lllllllllllllllllIIllllIIllIIlll) {
        KeybindSetting lllllllllllllllllIIllllIIllIIllI;
        if (lllllllllllllllllIIllllIIllIIllI.widget != null && lllllllllllllllllIIllllIIllIIllI.widget.onAction(false, lllllllllllllllllIIllllIIllIIlll.button)) {
            lllllllllllllllllIIllllIIllIIlll.cancel();
        }
    }

    @Override
    protected Keybind parseImpl(String lllllllllllllllllIIllllIIlIIllII) {
        try {
            return Keybind.fromKey(Integer.parseInt(lllllllllllllllllIIllllIIlIIllII.trim()));
        }
        catch (NumberFormatException lllllllllllllllllIIllllIIlIIllll) {
            return null;
        }
    }

    @EventHandler(priority=100)
    private void onKey(KeyEvent lllllllllllllllllIIllllIIlIlllIl) {
        KeybindSetting lllllllllllllllllIIllllIIlIllIll;
        if (lllllllllllllllllIIllllIIlIlllIl.action == KeyAction.Release && ((Keybind)lllllllllllllllllIIllllIIlIllIll.get()).matches(true, lllllllllllllllllIIllllIIlIlllIl.key) && lllllllllllllllllIIllllIIlIllIll.module.isActive() && lllllllllllllllllIIllllIIlIllIll.action != null) {
            lllllllllllllllllIIllllIIlIllIll.action.run();
        }
    }

    @Override
    public Keybind fromTag(NbtCompound lllllllllllllllllIIllllIIlIIIIII) {
        KeybindSetting lllllllllllllllllIIllllIIlIIIIll;
        ((Keybind)lllllllllllllllllIIllllIIlIIIIll.get()).fromTag(lllllllllllllllllIIllllIIlIIIIII);
        return (Keybind)lllllllllllllllllIIllllIIlIIIIll.get();
    }

    @Override
    public NbtCompound toTag() {
        KeybindSetting lllllllllllllllllIIllllIIlIIIllI;
        return ((Keybind)lllllllllllllllllIIllllIIlIIIllI.get()).toTag();
    }

    @Override
    protected boolean isValueValid(Keybind lllllllllllllllllIIllllIIlIIlIIl) {
        return true;
    }

    public KeybindSetting(String lllllllllllllllllIIllllIlIIIlIIl, String lllllllllllllllllIIllllIlIIIlIII, Keybind lllllllllllllllllIIllllIlIIIIIII, Consumer<Keybind> lllllllllllllllllIIllllIIlllllll, Consumer<Setting<Keybind>> lllllllllllllllllIIllllIlIIIIlIl, Runnable lllllllllllllllllIIllllIlIIIIlII) {
        super(lllllllllllllllllIIllllIlIIIlIIl, lllllllllllllllllIIllllIlIIIlIII, lllllllllllllllllIIllllIlIIIIIII, lllllllllllllllllIIllllIIlllllll, lllllllllllllllllIIllllIlIIIIlIl);
        KeybindSetting lllllllllllllllllIIllllIlIIIIIll;
        lllllllllllllllllIIllllIlIIIIIll.action = lllllllllllllllllIIllllIlIIIIlII;
        MeteorClient.EVENT_BUS.subscribe(lllllllllllllllllIIllllIlIIIIIll);
    }

    public static class Builder {
        private /* synthetic */ Consumer<Keybind> onChanged;
        private /* synthetic */ String name;
        private /* synthetic */ Consumer<Setting<Keybind>> onModuleActivated;
        private /* synthetic */ Keybind defaultValue;
        private /* synthetic */ Runnable action;
        private /* synthetic */ String description;

        public Builder name(String lllIllIIIlIIIII) {
            Builder lllIllIIIIlllll;
            lllIllIIIIlllll.name = lllIllIIIlIIIII;
            return lllIllIIIIlllll;
        }

        public Builder defaultValue(Keybind lllIllIIIIlIIlI) {
            Builder lllIllIIIIlIIll;
            lllIllIIIIlIIll.defaultValue = lllIllIIIIlIIlI;
            return lllIllIIIIlIIll;
        }

        public KeybindSetting build() {
            Builder lllIlIlllllllIl;
            return new KeybindSetting(lllIlIlllllllIl.name, lllIlIlllllllIl.description, lllIlIlllllllIl.defaultValue, lllIlIlllllllIl.onChanged, lllIlIlllllllIl.onModuleActivated, lllIlIlllllllIl.action);
        }

        public Builder action(Runnable lllIllIIIIIIIII) {
            Builder lllIllIIIIIIIIl;
            lllIllIIIIIIIIl.action = lllIllIIIIIIIII;
            return lllIllIIIIIIIIl;
        }

        public Builder onModuleActivated(Consumer<Setting<Keybind>> lllIllIIIIIlIII) {
            Builder lllIllIIIIIIlll;
            lllIllIIIIIIlll.onModuleActivated = lllIllIIIIIlIII;
            return lllIllIIIIIIlll;
        }

        public Builder() {
            Builder lllIllIIIlIIlII;
            lllIllIIIlIIlII.name = "undefined";
            lllIllIIIlIIlII.description = "";
            lllIllIIIlIIlII.defaultValue = Keybind.fromKey(-1);
        }

        public Builder onChanged(Consumer<Keybind> lllIllIIIIIllII) {
            Builder lllIllIIIIIllll;
            lllIllIIIIIllll.onChanged = lllIllIIIIIllII;
            return lllIllIIIIIllll;
        }

        public Builder description(String lllIllIIIIllIlI) {
            Builder lllIllIIIIllIIl;
            lllIllIIIIllIIl.description = lllIllIIIIllIlI;
            return lllIllIIIIllIIl;
        }
    }
}

