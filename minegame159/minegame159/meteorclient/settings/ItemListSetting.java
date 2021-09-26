/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class ItemListSetting
extends Setting<List<Item>> {
    public final Predicate<Item> filter;

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtList NbtList2 = new NbtList();
        for (Item Item2 : (List)this.get()) {
            NbtList2.add((Object)NbtString.of((String)Registry.ITEM.getId((Object)Item2).toString()));
        }
        NbtCompound2.put("value", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    @Override
    protected boolean isValueValid(List<Item> list) {
        return true;
    }

    @Override
    public List<Item> fromTag(NbtCompound NbtCompound2) {
        ((List)this.get()).clear();
        NbtList NbtList2 = NbtCompound2.getList("value", 8);
        for (NbtElement NbtElement2 : NbtList2) {
            Item Item2 = (Item)Registry.ITEM.get(new Identifier(NbtElement2.asString()));
            if (this.filter != null && !this.filter.test(Item2)) continue;
            ((List)this.get()).add(Item2);
        }
        this.changed();
        return (List)this.get();
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.ITEM.getIds();
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    protected List<Item> parseImpl(String string) {
        String[] stringArray = string.split(",");
        ArrayList<Item> arrayList = new ArrayList<Item>(stringArray.length);
        try {
            for (String string2 : stringArray) {
                Item Item2 = (Item)ItemListSetting.parseId(Registry.ITEM, string2);
                if (Item2 == null || this.filter != null && !this.filter.test(Item2)) continue;
                arrayList.add(Item2);
                if (3 > 0) continue;
                return null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return arrayList;
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((List)object);
    }

    public ItemListSetting(String string, String string2, List<Item> list, Consumer<List<Item>> consumer, Consumer<Setting<List<Item>>> consumer2, Predicate<Item> predicate) {
        super(string, string2, list, consumer, consumer2);
        this.value = new ArrayList<Item>(list);
        this.filter = predicate;
    }

    @Override
    public void reset(boolean bl) {
        this.value = new ArrayList((Collection)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    public static class Builder {
        private Consumer<List<Item>> onChanged;
        private Consumer<Setting<List<Item>>> onModuleActivated;
        private Predicate<Item> filter;
        private String name = "undefined";
        private String description = "";
        private List<Item> defaultValue;

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder defaultValue(List<Item> list) {
            this.defaultValue = list;
            return this;
        }

        public Builder onChanged(Consumer<List<Item>> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<List<Item>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public ItemListSetting build() {
            return new ItemListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.filter);
        }

        public Builder filter(Predicate<Item> predicate) {
            this.filter = predicate;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }
    }
}

