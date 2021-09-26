/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.Lazy;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

public abstract class Setting<T>
implements Lazy<T>,
ISerializable<T> {
    protected final T defaultValue;
    public final Consumer<Setting<T>> onModuleActivated;
    public final String description;
    public Module module;
    public final String title;
    protected T value;
    private final Consumer<T> onChanged;
    public final String name;
    private static final List<String> NO_SUGGESTIONS = new ArrayList<String>(0);

    public void reset() {
        this.reset(true);
    }

    protected abstract boolean isValueValid(T var1);

    public boolean parse(String string) {
        T t = this.parseImpl(string);
        if (t != null && this.isValueValid(t)) {
            this.value = t;
            this.changed();
        }
        return t != null;
    }

    public void changed() {
        if (this.onChanged != null) {
            this.onChanged.accept(this.value);
        }
    }

    public void onActivated() {
        if (this.onModuleActivated != null) {
            this.onModuleActivated.accept(this);
        }
    }

    public boolean set(T t) {
        if (!this.isValueValid(t)) {
            return false;
        }
        this.value = t;
        this.changed();
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Setting setting = (Setting)object;
        return Objects.equals(this.name, setting.name);
    }

    public void reset(boolean bl) {
        this.value = this.defaultValue;
        if (bl) {
            this.changed();
        }
    }

    public Iterable<Identifier> getIdentifierSuggestions() {
        return null;
    }

    @Override
    public T get() {
        return this.value;
    }

    protected abstract T parseImpl(String var1);

    public Setting(String string, String string2, T t, Consumer<T> consumer, Consumer<Setting<T>> consumer2) {
        this.name = string;
        this.title = Arrays.stream(string.split("-")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
        this.description = string2;
        this.defaultValue = t;
        this.reset(false);
        this.onChanged = consumer;
        this.onModuleActivated = consumer2;
    }

    public static <T> T parseId(Registry<T> Registry2, String string) {
        Identifier Identifier2 = (string = string.trim()).contains(":") ? new Identifier(string) : new Identifier("minecraft", string);
        if (Registry2.containsId(Identifier2)) {
            return (T)Registry2.get(Identifier2);
        }
        return null;
    }

    protected NbtCompound saveGeneral() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        return NbtCompound2;
    }

    public List<String> getSuggestions() {
        return NO_SUGGESTIONS;
    }

    public String toString() {
        return this.value.toString();
    }
}

