/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
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
    private static /* synthetic */ List<String> suggestions;

    @Override
    public NbtCompound toTag() {
        ModuleListSetting lIllllllIIlllII;
        NbtCompound lIllllllIIllIll = lIllllllIIlllII.saveGeneral();
        NbtList lIllllllIIllIlI = new NbtList();
        for (Module lIllllllIIlllIl : (List)lIllllllIIlllII.get()) {
            lIllllllIIllIlI.add((Object)NbtString.of((String)lIllllllIIlllIl.name));
        }
        lIllllllIIllIll.put("modules", (NbtElement)lIllllllIIllIlI);
        return lIllllllIIllIll;
    }

    public ModuleListSetting(String lIlllllllIIlIll, String lIlllllllIIlIlI, List<Module> lIlllllllIIlIIl, Consumer<List<Module>> lIlllllllIIlIII, Consumer<Setting<List<Module>>> lIlllllllIIllIl) {
        super(lIlllllllIIlIll, lIlllllllIIlIlI, lIlllllllIIlIIl, lIlllllllIIlIII, lIlllllllIIllIl);
        ModuleListSetting lIlllllllIIllII;
        lIlllllllIIllII.value = new ArrayList<Module>(lIlllllllIIlIIl);
    }

    @Override
    public void reset(boolean lIlllllllIIIIll) {
        ModuleListSetting lIlllllllIIIlII;
        lIlllllllIIIlII.value = new ArrayList((Collection)lIlllllllIIIlII.defaultValue);
        if (lIlllllllIIIIll) {
            lIlllllllIIIlII.changed();
        }
    }

    @Override
    protected boolean isValueValid(List<Module> lIllllllIlIlIIl) {
        return true;
    }

    @Override
    protected List<Module> parseImpl(String lIllllllIllIIlI) {
        String[] lIllllllIllIlII = lIllllllIllIIlI.split(",");
        ArrayList<Module> lIllllllIllIIll = new ArrayList<Module>(lIllllllIllIlII.length);
        try {
            for (String lIllllllIllIlll : lIllllllIllIlII) {
                Module lIllllllIlllIII = Modules.get().get(lIllllllIllIlll.trim());
                if (lIllllllIlllIII == null) continue;
                lIllllllIllIIll.add(lIllllllIlllIII);
            }
        }
        catch (Exception lIllllllIlIllll) {
            // empty catch block
        }
        return lIllllllIllIIll;
    }

    @Override
    public List<Module> fromTag(NbtCompound lIllllllIIIlIll) {
        ModuleListSetting lIllllllIIIllII;
        ((List)lIllllllIIIllII.get()).clear();
        NbtList lIllllllIIIlIlI = lIllllllIIIlIll.getList("modules", 8);
        for (NbtElement lIllllllIIIllIl : lIllllllIIIlIlI) {
            Module lIllllllIIIlllI = Modules.get().get(lIllllllIIIllIl.asString());
            if (lIllllllIIIlllI == null) continue;
            ((List)lIllllllIIIllII.get()).add(lIllllllIIIlllI);
        }
        lIllllllIIIllII.changed();
        return (List)lIllllllIIIllII.get();
    }

    @Override
    public List<String> getSuggestions() {
        if (suggestions == null) {
            suggestions = new ArrayList<String>(Modules.get().getAll().size());
            for (Module lIllllllIlIIllI : Modules.get().getAll()) {
                suggestions.add(lIllllllIlIIllI.name);
            }
        }
        return suggestions;
    }

    public static class Builder {
        private /* synthetic */ Consumer<List<Module>> onChanged;
        private /* synthetic */ String description;
        private /* synthetic */ List<Module> defaultValue;
        private /* synthetic */ Consumer<Setting<List<Module>>> onModuleActivated;
        private /* synthetic */ String name;

        public Builder onModuleActivated(Consumer<Setting<List<Module>>> lllIIlIIIIIIIIl) {
            Builder lllIIlIIIIIIIlI;
            lllIIlIIIIIIIlI.onModuleActivated = lllIIlIIIIIIIIl;
            return lllIIlIIIIIIIlI;
        }

        public Builder description(String lllIIlIIIIlIIll) {
            Builder lllIIlIIIIlIIlI;
            lllIIlIIIIlIIlI.description = lllIIlIIIIlIIll;
            return lllIIlIIIIlIIlI;
        }

        public ModuleListSetting build() {
            Builder lllIIIlllllllII;
            return new ModuleListSetting(lllIIIlllllllII.name, lllIIIlllllllII.description, lllIIIlllllllII.defaultValue, lllIIIlllllllII.onChanged, lllIIIlllllllII.onModuleActivated);
        }

        public Builder defaultValue(List<Module> lllIIlIIIIIlIll) {
            Builder lllIIlIIIIIlllI;
            lllIIlIIIIIlllI.defaultValue = lllIIlIIIIIlIll;
            return lllIIlIIIIIlllI;
        }

        public Builder onChanged(Consumer<List<Module>> lllIIlIIIIIIlIl) {
            Builder lllIIlIIIIIlIII;
            lllIIlIIIIIlIII.onChanged = lllIIlIIIIIIlIl;
            return lllIIlIIIIIlIII;
        }

        public Builder() {
            Builder lllIIlIIIIllllI;
            lllIIlIIIIllllI.name = "undefined";
            lllIIlIIIIllllI.description = "";
        }

        public Builder name(String lllIIlIIIIlIlll) {
            Builder lllIIlIIIIllIII;
            lllIIlIIIIllIII.name = lllIIlIIIIlIlll;
            return lllIIlIIIIllIII;
        }
    }
}

