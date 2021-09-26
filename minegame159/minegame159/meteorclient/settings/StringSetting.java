/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.nbt.NbtCompound;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringSetting
extends Setting<String> {
    @Override
    protected String parseImpl(String string) {
        return string;
    }

    public StringSetting(String string, String string2, String string3, Consumer<String> consumer, Consumer<Setting<String>> consumer2) {
        super(string, string2, string3, consumer, consumer2);
        this.value = string3;
    }

    @Override
    public String fromTag(NbtCompound NbtCompound2) {
        this.set(NbtCompound2.getString("value"));
        return (String)this.get();
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtCompound2.putString("value", (String)this.get());
        return NbtCompound2;
    }

    @Override
    public void reset(boolean bl) {
        this.value = this.defaultValue;
        if (bl) {
            this.changed();
        }
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((String)object);
    }

    @Override
    protected boolean isValueValid(String string) {
        return true;
    }

    public static class Builder {
        private Consumer<String> onChanged;
        private String name = "undefined";
        private Consumer<Setting<String>> onModuleActivated;
        private String defaultValue;
        private String description = "";

        public Builder onModuleActivated(Consumer<Setting<String>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder defaultValue(String string) {
            this.defaultValue = string;
            return this;
        }

        public StringSetting build() {
            return new StringSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder onChanged(Consumer<String> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }
    }
}

