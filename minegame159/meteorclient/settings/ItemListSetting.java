/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.util.Identifier
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
    public final /* synthetic */ Predicate<Item> filter;

    @Override
    protected List<Item> parseImpl(String llllllllllllllllllIIIlIlIlIIIIll) {
        String[] llllllllllllllllllIIIlIlIlIIIIlI = llllllllllllllllllIIIlIlIlIIIIll.split(",");
        ArrayList<Item> llllllllllllllllllIIIlIlIlIIIIIl = new ArrayList<Item>(llllllllllllllllllIIIlIlIlIIIIlI.length);
        try {
            for (String llllllllllllllllllIIIlIlIlIIIlIl : llllllllllllllllllIIIlIlIlIIIIlI) {
                ItemListSetting llllllllllllllllllIIIlIlIlIIIIII;
                Item llllllllllllllllllIIIlIlIlIIIllI = (Item)ItemListSetting.parseId(Registry.ITEM, llllllllllllllllllIIIlIlIlIIIlIl);
                if (llllllllllllllllllIIIlIlIlIIIllI == null || llllllllllllllllllIIIlIlIlIIIIII.filter != null && !llllllllllllllllllIIIlIlIlIIIIII.filter.test(llllllllllllllllllIIIlIlIlIIIllI)) continue;
                llllllllllllllllllIIIlIlIlIIIIIl.add(llllllllllllllllllIIIlIlIlIIIllI);
            }
        }
        catch (Exception llllllllllllllllllIIIlIlIIllllII) {
            // empty catch block
        }
        return llllllllllllllllllIIIlIlIlIIIIIl;
    }

    @Override
    protected boolean isValueValid(List<Item> llllllllllllllllllIIIlIlIIllIIII) {
        return true;
    }

    @Override
    public List<Item> fromTag(NbtCompound llllllllllllllllllIIIlIlIIIlIlII) {
        ItemListSetting llllllllllllllllllIIIlIlIIIllIII;
        ((List)llllllllllllllllllIIIlIlIIIllIII.get()).clear();
        NbtList llllllllllllllllllIIIlIlIIIlIllI = llllllllllllllllllIIIlIlIIIlIlII.getList("value", 8);
        for (NbtElement llllllllllllllllllIIIlIlIIIllIIl : llllllllllllllllllIIIlIlIIIlIllI) {
            Item llllllllllllllllllIIIlIlIIIllIlI = (Item)Registry.ITEM.get(new Identifier(llllllllllllllllllIIIlIlIIIllIIl.asString()));
            if (llllllllllllllllllIIIlIlIIIllIII.filter != null && !llllllllllllllllllIIIlIlIIIllIII.filter.test(llllllllllllllllllIIIlIlIIIllIlI)) continue;
            ((List)llllllllllllllllllIIIlIlIIIllIII.get()).add(llllllllllllllllllIIIlIlIIIllIlI);
        }
        llllllllllllllllllIIIlIlIIIllIII.changed();
        return (List)llllllllllllllllllIIIlIlIIIllIII.get();
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.ITEM.getIds();
    }

    @Override
    public NbtCompound toTag() {
        ItemListSetting llllllllllllllllllIIIlIlIIlIIlIl;
        NbtCompound llllllllllllllllllIIIlIlIIlIIlll = llllllllllllllllllIIIlIlIIlIIlIl.saveGeneral();
        NbtList llllllllllllllllllIIIlIlIIlIIllI = new NbtList();
        for (Item llllllllllllllllllIIIlIlIIlIlIIl : (List)llllllllllllllllllIIIlIlIIlIIlIl.get()) {
            llllllllllllllllllIIIlIlIIlIIllI.add((Object)NbtString.of((String)Registry.ITEM.getId((Object)llllllllllllllllllIIIlIlIIlIlIIl).toString()));
        }
        llllllllllllllllllIIIlIlIIlIIlll.put("value", (NbtElement)llllllllllllllllllIIIlIlIIlIIllI);
        return llllllllllllllllllIIIlIlIIlIIlll;
    }

    public ItemListSetting(String llllllllllllllllllIIIlIlIlIlIlIl, String llllllllllllllllllIIIlIlIlIllIll, List<Item> llllllllllllllllllIIIlIlIlIllIlI, Consumer<List<Item>> llllllllllllllllllIIIlIlIlIlIIlI, Consumer<Setting<List<Item>>> llllllllllllllllllIIIlIlIlIlIIIl, Predicate<Item> llllllllllllllllllIIIlIlIlIlIlll) {
        super(llllllllllllllllllIIIlIlIlIlIlIl, llllllllllllllllllIIIlIlIlIllIll, llllllllllllllllllIIIlIlIlIllIlI, llllllllllllllllllIIIlIlIlIlIIlI, llllllllllllllllllIIIlIlIlIlIIIl);
        ItemListSetting llllllllllllllllllIIIlIlIlIlIllI;
        llllllllllllllllllIIIlIlIlIlIllI.value = new ArrayList<Item>(llllllllllllllllllIIIlIlIlIllIlI);
        llllllllllllllllllIIIlIlIlIlIllI.filter = llllllllllllllllllIIIlIlIlIlIlll;
    }

    @Override
    public void reset(boolean llllllllllllllllllIIIlIlIIllIlII) {
        ItemListSetting llllllllllllllllllIIIlIlIIllIlIl;
        llllllllllllllllllIIIlIlIIllIlIl.value = new ArrayList((Collection)llllllllllllllllllIIIlIlIIllIlIl.defaultValue);
        if (llllllllllllllllllIIIlIlIIllIlII) {
            llllllllllllllllllIIIlIlIIllIlIl.changed();
        }
    }

    public static class Builder {
        private /* synthetic */ String description;
        private /* synthetic */ Predicate<Item> filter;
        private /* synthetic */ Consumer<List<Item>> onChanged;
        private /* synthetic */ String name;
        private /* synthetic */ Consumer<Setting<List<Item>>> onModuleActivated;
        private /* synthetic */ List<Item> defaultValue;

        public Builder description(String lIllIlIllIIIIlI) {
            Builder lIllIlIllIIIIll;
            lIllIlIllIIIIll.description = lIllIlIllIIIIlI;
            return lIllIlIllIIIIll;
        }

        public ItemListSetting build() {
            Builder lIllIlIlIlIIllI;
            return new ItemListSetting(lIllIlIlIlIIllI.name, lIllIlIlIlIIllI.description, lIllIlIlIlIIllI.defaultValue, lIllIlIlIlIIllI.onChanged, lIllIlIlIlIIllI.onModuleActivated, lIllIlIlIlIIllI.filter);
        }

        public Builder onChanged(Consumer<List<Item>> lIllIlIlIllIllI) {
            Builder lIllIlIlIllIlIl;
            lIllIlIlIllIlIl.onChanged = lIllIlIlIllIllI;
            return lIllIlIlIllIlIl;
        }

        public Builder onModuleActivated(Consumer<Setting<List<Item>>> lIllIlIlIllIIII) {
            Builder lIllIlIlIllIIIl;
            lIllIlIlIllIIIl.onModuleActivated = lIllIlIlIllIIII;
            return lIllIlIlIllIIIl;
        }

        public Builder defaultValue(List<Item> lIllIlIlIllllII) {
            Builder lIllIlIlIlllIll;
            lIllIlIlIlllIll.defaultValue = lIllIlIlIllllII;
            return lIllIlIlIlllIll;
        }

        public Builder filter(Predicate<Item> lIllIlIlIlIlIII) {
            Builder lIllIlIlIlIlIIl;
            lIllIlIlIlIlIIl.filter = lIllIlIlIlIlIII;
            return lIllIlIlIlIlIIl;
        }

        public Builder name(String lIllIlIllIIlIII) {
            Builder lIllIlIllIIIlll;
            lIllIlIllIIIlll.name = lIllIlIllIIlIII;
            return lIllIlIllIIIlll;
        }

        public Builder() {
            Builder lIllIlIllIIllII;
            lIllIlIllIIllII.name = "undefined";
            lIllIlIllIIllII.description = "";
        }
    }
}

