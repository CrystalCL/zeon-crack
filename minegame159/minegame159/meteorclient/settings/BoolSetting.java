/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.class_2487;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BoolSetting
extends Setting<Boolean> {
    private static final List<String> SUGGESTIONS = ImmutableList.of((Object)"true", (Object)"false", (Object)"toggle");

    @Override
    public class_2487 toTag() {
        class_2487 class_24872 = this.saveGeneral();
        class_24872.method_10556("value", ((Boolean)this.get()).booleanValue());
        return class_24872;
    }

    @Override
    public Boolean fromTag(class_2487 class_24872) {
        this.set(class_24872.method_10577("value"));
        return (Boolean)this.get();
    }

    BoolSetting(String string, String string2, Boolean bl, Consumer consumer, Consumer consumer2, 1 var6_6) {
        this(string, string2, bl, consumer, consumer2);
    }

    @Override
    protected boolean isValueValid(Boolean bl) {
        return true;
    }

    private BoolSetting(String string, String string2, Boolean bl, Consumer<Boolean> consumer, Consumer<Setting<Boolean>> consumer2) {
        super(string, string2, bl, consumer, consumer2);
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((Boolean)object);
    }

    @Override
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }

    @Override
    public List<String> getSuggestions() {
        return SUGGESTIONS;
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    protected Boolean parseImpl(String string) {
        if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("1")) {
            return true;
        }
        if (string.equalsIgnoreCase("false") || string.equalsIgnoreCase("0")) {
            return false;
        }
        if (string.equalsIgnoreCase("toggle")) {
            return (Boolean)this.get() == false;
        }
        return null;
    }

    public static class Builder {
        private String name = "undefined";
        private Boolean defaultValue;
        private Consumer<Boolean> onChanged;
        private String description = "";
        private Consumer<Setting<Boolean>> onModuleActivated;

        public Builder defaultValue(boolean bl) {
            this.defaultValue = bl;
            return this;
        }

        public BoolSetting build() {
            return new BoolSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, null);
        }

        public Builder onChanged(Consumer<Boolean> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<Boolean>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }
    }
}

