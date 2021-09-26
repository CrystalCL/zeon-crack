/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ColorSetting
extends Setting<SettingColor> {
    private static final List<String> SUGGESTIONS = ImmutableList.of((Object)"0 0 0 255", (Object)"225 25 25 255", (Object)"25 225 25 255", (Object)"25 25 225 255", (Object)"255 255 255 255");

    @Override
    public void reset(boolean bl) {
        if (this.value == null) {
            this.value = new SettingColor((SettingColor)this.defaultValue);
        } else {
            ((SettingColor)this.value).set((Color)this.defaultValue);
        }
        if (bl) {
            this.changed();
        }
    }

    @Override
    public SettingColor fromTag(NbtCompound NbtCompound2) {
        ((SettingColor)this.get()).fromTag(NbtCompound2.getCompound("value"));
        this.changed();
        return (SettingColor)this.get();
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtCompound2.put("value", (NbtElement)((SettingColor)this.get()).toTag());
        return NbtCompound2;
    }

    public ColorSetting(String string, String string2, SettingColor settingColor, Consumer<SettingColor> consumer, Consumer<Setting<SettingColor>> consumer2) {
        super(string, string2, settingColor, consumer, consumer2);
    }

    @Override
    protected boolean isValueValid(SettingColor settingColor) {
        settingColor.validate();
        return true;
    }

    @Override
    protected SettingColor parseImpl(String string) {
        try {
            String[] stringArray = string.split(" ");
            return new SettingColor(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]), Integer.parseInt(stringArray[3]));
        }
        catch (IndexOutOfBoundsException | NumberFormatException runtimeException) {
            return null;
        }
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((SettingColor)object);
    }

    @Override
    public List<String> getSuggestions() {
        return SUGGESTIONS;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public static class Builder {
        private Consumer<SettingColor> onChanged;
        private String name = "undefined";
        private String description = "";
        private Consumer<Setting<SettingColor>> onModuleActivated;
        private SettingColor defaultValue;

        public Builder onModuleActivated(Consumer<Setting<SettingColor>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public ColorSetting build() {
            return new ColorSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder onChanged(Consumer<SettingColor> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder defaultValue(SettingColor settingColor) {
            this.defaultValue = settingColor;
            return this;
        }
    }
}

