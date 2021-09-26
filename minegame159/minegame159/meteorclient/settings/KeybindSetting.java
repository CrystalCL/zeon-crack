/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class KeybindSetting
extends Setting<Keybind> {
    private final Runnable action;
    public WKeybind widget;

    @EventHandler(priority=200)
    private void onKeyBinding(KeyEvent keyEvent) {
        if (this.widget != null && this.widget.onAction(true, keyEvent.key)) {
            keyEvent.cancel();
        }
    }

    @EventHandler(priority=100)
    private void onMouseButton(MouseButtonEvent mouseButtonEvent) {
        if (mouseButtonEvent.action == KeyAction.Release && ((Keybind)this.get()).matches(false, mouseButtonEvent.button) && this.module.isActive() && this.action != null) {
            this.action.run();
        }
    }

    @Override
    public Keybind fromTag(NbtCompound NbtCompound2) {
        ((Keybind)this.get()).fromTag(NbtCompound2);
        return (Keybind)this.get();
    }

    @Override
    protected Keybind parseImpl(String string) {
        try {
            return Keybind.fromKey(Integer.parseInt(string.trim()));
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @EventHandler(priority=100)
    private void onKey(KeyEvent keyEvent) {
        if (keyEvent.action == KeyAction.Release && ((Keybind)this.get()).matches(true, keyEvent.key) && this.module.isActive() && this.action != null) {
            this.action.run();
        }
    }

    public KeybindSetting(String string, String string2, Keybind keybind, Consumer<Keybind> consumer, Consumer<Setting<Keybind>> consumer2, Runnable runnable) {
        super(string, string2, keybind, consumer, consumer2);
        this.action = runnable;
        MeteorClient.EVENT_BUS.subscribe(this);
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((Keybind)object);
    }

    @EventHandler(priority=200)
    private void onMouseButtonBinding(MouseButtonEvent mouseButtonEvent) {
        if (this.widget != null && this.widget.onAction(false, mouseButtonEvent.button)) {
            mouseButtonEvent.cancel();
        }
    }

    @Override
    public NbtCompound toTag() {
        return ((Keybind)this.get()).toTag();
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    protected boolean isValueValid(Keybind keybind) {
        return true;
    }

    public static class Builder {
        private Consumer<Setting<Keybind>> onModuleActivated;
        private Keybind defaultValue = Keybind.fromKey(-1);
        private String description = "";
        private Consumer<Keybind> onChanged;
        private String name = "undefined";
        private Runnable action;

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<Keybind>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder defaultValue(Keybind keybind) {
            this.defaultValue = keybind;
            return this;
        }

        public KeybindSetting build() {
            return new KeybindSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.action);
        }

        public Builder onChanged(Consumer<Keybind> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder action(Runnable runnable) {
            this.action = runnable;
            return this;
        }
    }
}

