/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;

public class ModuleListSetting
extends Setting<List<Module>> {
    private static List<String> suggestions;

    public ModuleListSetting(String string, String string2, List<Module> list, Consumer<List<Module>> consumer, Consumer<Setting<List<Module>>> consumer2) {
        super(string, string2, list, consumer, consumer2);
        this.value = new ArrayList<Module>(list);
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((List)object);
    }

    @Override
    public List<Module> fromTag(NbtCompound NbtCompound2) {
        ((List)this.get()).clear();
        NbtList NbtList2 = NbtCompound2.getList("modules", 8);
        for (NbtElement NbtElement2 : NbtList2) {
            Module module = Modules.get().get(NbtElement2.asString());
            if (module == null) continue;
            ((List)this.get()).add(module);
        }
        this.changed();
        return (List)this.get();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtList NbtList2 = new NbtList();
        for (Module module : (List)this.get()) {
            NbtList2.add((Object)NbtString.of((String)module.name));
        }
        NbtCompound2.put("modules", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    @Override
    protected List<Module> parseImpl(String string) {
        String[] stringArray = string.split(",");
        ArrayList<Module> arrayList = new ArrayList<Module>(stringArray.length);
        try {
            for (String string2 : stringArray) {
                Module module = Modules.get().get(string2.trim());
                if (module == null) continue;
                arrayList.add(module);
                if (null == null) continue;
                return null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return arrayList;
    }

    @Override
    protected boolean isValueValid(List<Module> list) {
        return true;
    }

    @Override
    public void reset(boolean bl) {
        this.value = new ArrayList((Collection)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public List<String> getSuggestions() {
        if (suggestions == null) {
            suggestions = new ArrayList<String>(Modules.get().getAll().size());
            for (Module module : Modules.get().getAll()) {
                suggestions.add(module.name);
            }
        }
        return suggestions;
    }

    public static class Builder {
        private String description = "";
        private Consumer<List<Module>> onChanged;
        private String name = "undefined";
        private Consumer<Setting<List<Module>>> onModuleActivated;
        private List<Module> defaultValue;

        public Builder onChanged(Consumer<List<Module>> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public ModuleListSetting build() {
            return new ModuleListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder defaultValue(List<Module> list) {
            this.defaultValue = list;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<List<Module>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }
    }
}

