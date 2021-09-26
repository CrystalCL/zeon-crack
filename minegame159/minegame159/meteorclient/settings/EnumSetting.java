/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.nbt.NbtCompound;

public class EnumSetting<T extends Enum<?>>
extends Setting<T> {
    private WDropdown<T> widget;
    private final List<String> suggestions;
    private T[] values;

    @Override
    public T fromTag(NbtCompound NbtCompound2) {
        this.parse(NbtCompound2.getString("value"));
        return (T)((Enum)this.get());
    }

    @Override
    protected boolean isValueValid(T t) {
        return true;
    }

    public EnumSetting(String string, String string2, T t, Consumer<T> consumer, Consumer<Setting<T>> consumer2) {
        super(string, string2, t, consumer, consumer2);
        try {
            this.values = (Enum[])t.getClass().getMethod("values", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
        this.suggestions = new ArrayList<String>(this.values.length);
        for (T t2 : this.values) {
            this.suggestions.add(((Enum)t2).toString());
            if (null == null) continue;
            throw null;
        }
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtCompound2.putString("value", ((Enum)this.get()).toString());
        return NbtCompound2;
    }

    @Override
    public List<String> getSuggestions() {
        return this.suggestions;
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((T)((Enum)object));
    }

    @Override
    protected T parseImpl(String string) {
        for (T t : this.values) {
            if (!string.equalsIgnoreCase(((Enum)t).toString())) continue;
            return t;
        }
        return null;
    }

    public static class Builder<T extends Enum<?>> {
        protected Consumer<Setting<T>> onModuleActivated;
        protected T defaultValue;
        protected String name = "undefined";
        protected Consumer<T> onChanged;
        protected String description = "";

        public Builder<T> onChanged(Consumer<T> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder<T> defaultValue(T t) {
            this.defaultValue = t;
            return this;
        }

        public EnumSetting<T> build() {
            return new EnumSetting<T>(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder<T> name(String string) {
            this.name = string;
            return this;
        }

        public Builder<T> onModuleActivated(Consumer<Setting<T>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder<T> description(String string) {
            this.description = string;
            return this;
        }
    }
}

