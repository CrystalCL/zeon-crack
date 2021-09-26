/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class EnchListSetting
extends Setting<List<Enchantment>> {
    public EnchListSetting(String string, String string2, List<Enchantment> list, Consumer<List<Enchantment>> consumer, Consumer<Setting<List<Enchantment>>> consumer2) {
        super(string, string2, list, consumer, consumer2);
        this.value = new ArrayList<Enchantment>(list);
    }

    @Override
    protected boolean isValueValid(List<Enchantment> list) {
        return true;
    }

    @Override
    protected List<Enchantment> parseImpl(String string) {
        String[] stringArray = string.split(",");
        ArrayList<Enchantment> arrayList = new ArrayList<Enchantment>(stringArray.length);
        try {
            for (String string2 : stringArray) {
                Enchantment Enchantment2 = (Enchantment)EnchListSetting.parseId(Registry.ENCHANTMENT, string2);
                if (Enchantment2 == null) continue;
                arrayList.add(Enchantment2);
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
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtList NbtList2 = new NbtList();
        for (Enchantment Enchantment2 : (List)this.get()) {
            try {
                NbtList2.add((Object)NbtString.of((String)Registry.ENCHANTMENT.getId((Object)Enchantment2).toString()));
            }
            catch (NullPointerException nullPointerException) {}
        }
        NbtCompound2.put("value", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((List)object);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public void reset(boolean bl) {
        this.value = new ArrayList((Collection)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    @Override
    public List<Enchantment> fromTag(NbtCompound NbtCompound2) {
        ((List)this.get()).clear();
        NbtList NbtList2 = NbtCompound2.getList("value", 8);
        for (NbtElement NbtElement2 : NbtList2) {
            ((List)this.get()).add((Enchantment)Registry.ENCHANTMENT.get(new Identifier(NbtElement2.asString())));
        }
        this.changed();
        return (List)this.get();
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.ENCHANTMENT.getIds();
    }

    public static class Builder {
        private String name = "undefined";
        private String description = "";
        private Consumer<List<Enchantment>> onChanged;
        private Consumer<Setting<List<Enchantment>>> onModuleActivated;
        private List<Enchantment> defaultValue;

        public EnchListSetting build() {
            return new EnchListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder defaultValue(List<Enchantment> list) {
            this.defaultValue = list;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<List<Enchantment>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder onChanged(Consumer<List<Enchantment>> consumer) {
            this.onChanged = consumer;
            return this;
        }
    }
}

