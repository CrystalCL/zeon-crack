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
import net.minecraft.class_2378;
import net.minecraft.class_2487;
import net.minecraft.class_2960;
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

    public Iterable<class_2960> getIdentifierSuggestions() {
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

    public static <T> T parseId(class_2378<T> class_23782, String string) {
        class_2960 class_29602 = (string = string.trim()).contains(":") ? new class_2960(string) : new class_2960("minecraft", string);
        if (class_23782.method_10250(class_29602)) {
            return (T)class_23782.method_10223(class_29602);
        }
        return null;
    }

    protected class_2487 saveGeneral() {
        class_2487 class_24872 = new class_2487();
        class_24872.method_10582("name", this.name);
        return class_24872;
    }

    public List<String> getSuggestions() {
        return NO_SUGGESTIONS;
    }

    public String toString() {
        return this.value.toString();
    }
}

