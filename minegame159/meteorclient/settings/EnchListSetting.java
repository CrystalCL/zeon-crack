/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.enchantment.Enchantment
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
    @Override
    public List<Enchantment> fromTag(NbtCompound lIlllIIlllII) {
        EnchListSetting lIlllIIllIlI;
        ((List)lIlllIIllIlI.get()).clear();
        NbtList lIlllIIllIll = lIlllIIlllII.getList("value", 8);
        for (NbtElement lIlllIIllllI : lIlllIIllIll) {
            ((List)lIlllIIllIlI.get()).add((Enchantment)Registry.ENCHANTMENT.get(new Identifier(lIlllIIllllI.asString())));
        }
        lIlllIIllIlI.changed();
        return (List)lIlllIIllIlI.get();
    }

    @Override
    public NbtCompound toTag() {
        EnchListSetting lIlllIlIllII;
        NbtCompound lIlllIlIlIll = lIlllIlIllII.saveGeneral();
        NbtList lIlllIlIlIlI = new NbtList();
        for (Enchantment lIlllIlIllIl : (List)lIlllIlIllII.get()) {
            try {
                lIlllIlIlIlI.add((Object)NbtString.of((String)Registry.ENCHANTMENT.getId((Object)lIlllIlIllIl).toString()));
            }
            catch (NullPointerException lIlllIlIIlII) {}
        }
        lIlllIlIlIll.put("value", (NbtElement)lIlllIlIlIlI);
        return lIlllIlIlIll;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.ENCHANTMENT.getIds();
    }

    public EnchListSetting(String lIllllIlllIl, String lIllllIlIllI, List<Enchantment> lIllllIllIll, Consumer<List<Enchantment>> lIllllIlIlII, Consumer<Setting<List<Enchantment>>> lIllllIllIIl) {
        super(lIllllIlllIl, lIllllIlIllI, lIllllIllIll, lIllllIlIlII, lIllllIllIIl);
        EnchListSetting lIllllIllIII;
        lIllllIllIII.value = new ArrayList<Enchantment>(lIllllIllIll);
    }

    @Override
    protected boolean isValueValid(List<Enchantment> lIlllIllIlIl) {
        return true;
    }

    @Override
    protected List<Enchantment> parseImpl(String lIlllIlllllI) {
        String[] lIllllIIIIII = lIlllIlllllI.split(",");
        ArrayList<Enchantment> lIlllIllllll = new ArrayList<Enchantment>(lIllllIIIIII.length);
        try {
            for (String lIllllIIIIll : lIllllIIIIII) {
                Enchantment lIllllIIIlII = (Enchantment)EnchListSetting.parseId(Registry.ENCHANTMENT, lIllllIIIIll);
                if (lIllllIIIlII == null) continue;
                lIlllIllllll.add(lIllllIIIlII);
            }
        }
        catch (Exception lIlllIlllIll) {
            // empty catch block
        }
        return lIlllIllllll;
    }

    @Override
    public void reset(boolean lIllllIIllIl) {
        EnchListSetting lIllllIIlllI;
        lIllllIIlllI.value = new ArrayList((Collection)lIllllIIlllI.defaultValue);
        if (lIllllIIllIl) {
            lIllllIIlllI.changed();
        }
    }

    public static class Builder {
        private /* synthetic */ Consumer<Setting<List<Enchantment>>> onModuleActivated;
        private /* synthetic */ String name;
        private /* synthetic */ String description;
        private /* synthetic */ Consumer<List<Enchantment>> onChanged;
        private /* synthetic */ List<Enchantment> defaultValue;

        public Builder onChanged(Consumer<List<Enchantment>> llllllllllllllllllllIlllllIlIlIl) {
            Builder llllllllllllllllllllIlllllIlIlII;
            llllllllllllllllllllIlllllIlIlII.onChanged = llllllllllllllllllllIlllllIlIlIl;
            return llllllllllllllllllllIlllllIlIlII;
        }

        public Builder name(String llllllllllllllllllllIllllllIIlll) {
            Builder llllllllllllllllllllIllllllIlIII;
            llllllllllllllllllllIllllllIlIII.name = llllllllllllllllllllIllllllIIlll;
            return llllllllllllllllllllIllllllIlIII;
        }

        public Builder defaultValue(List<Enchantment> llllllllllllllllllllIlllllIllIIl) {
            Builder llllllllllllllllllllIlllllIlllII;
            llllllllllllllllllllIlllllIlllII.defaultValue = llllllllllllllllllllIlllllIllIIl;
            return llllllllllllllllllllIlllllIlllII;
        }

        public Builder() {
            Builder llllllllllllllllllllIllllllIlIll;
            llllllllllllllllllllIllllllIlIll.name = "undefined";
            llllllllllllllllllllIllllllIlIll.description = "";
        }

        public Builder onModuleActivated(Consumer<Setting<List<Enchantment>>> llllllllllllllllllllIlllllIIllll) {
            Builder llllllllllllllllllllIlllllIlIIII;
            llllllllllllllllllllIlllllIlIIII.onModuleActivated = llllllllllllllllllllIlllllIIllll;
            return llllllllllllllllllllIlllllIlIIII;
        }

        public EnchListSetting build() {
            Builder llllllllllllllllllllIlllllIIlIll;
            return new EnchListSetting(llllllllllllllllllllIlllllIIlIll.name, llllllllllllllllllllIlllllIIlIll.description, llllllllllllllllllllIlllllIIlIll.defaultValue, llllllllllllllllllllIlllllIIlIll.onChanged, llllllllllllllllllllIlllllIIlIll.onModuleActivated);
        }

        public Builder description(String llllllllllllllllllllIlllllIlllll) {
            Builder llllllllllllllllllllIllllllIIIlI;
            llllllllllllllllllllIllllllIIIlI.description = llllllllllllllllllllIlllllIlllll;
            return llllllllllllllllllllIllllllIIIlI;
        }
    }
}

