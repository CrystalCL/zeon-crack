/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.particle.ParticleType
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
import net.minecraft.util.registry.Registry;
import net.minecraft.particle.ParticleType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class ParticleTypeListSetting
extends Setting<List<ParticleType<?>>> {
    private static /* synthetic */ List<Identifier> suggestions;

    @Override
    public NbtCompound toTag() {
        ParticleTypeListSetting lllIIllIIllIlll;
        NbtCompound lllIIllIIlllIIl = lllIIllIIllIlll.saveGeneral();
        NbtList lllIIllIIlllIII = new NbtList();
        for (ParticleType lllIIllIIlllIll : (List)lllIIllIIllIlll.get()) {
            lllIIllIIlllIII.add((Object)NbtString.of((String)Registry.PARTICLE_TYPE.getId((Object)lllIIllIIlllIll).toString()));
        }
        lllIIllIIlllIIl.put("value", (NbtElement)lllIIllIIlllIII);
        return lllIIllIIlllIIl;
    }

    public ParticleTypeListSetting(String lllIIllIlllIIlI, String lllIIllIlllIIIl, List<ParticleType<?>> lllIIllIlllIIII, Consumer<List<ParticleType<?>>> lllIIllIllIlIIl, Consumer<Setting<List<ParticleType<?>>>> lllIIllIllIlllI) {
        super(lllIIllIlllIIlI, lllIIllIlllIIIl, lllIIllIlllIIII, lllIIllIllIlIIl, lllIIllIllIlllI);
        ParticleTypeListSetting lllIIllIllIllIl;
        lllIIllIllIllIl.value = new ArrayList(lllIIllIlllIIII);
    }

    @Override
    public void reset(boolean lllIIllIllIIlII) {
        ParticleTypeListSetting lllIIllIllIIIll;
        lllIIllIllIIIll.value = new ArrayList((Collection)lllIIllIllIIIll.defaultValue);
        if (lllIIllIllIIlII) {
            lllIIllIllIIIll.changed();
        }
    }

    @Override
    public List<ParticleType<?>> fromTag(NbtCompound lllIIllIIlIlIII) {
        ParticleTypeListSetting lllIIllIIlIlIIl;
        ((List)lllIIllIIlIlIIl.get()).clear();
        NbtList lllIIllIIlIlIlI = lllIIllIIlIlIII.getList("value", 8);
        for (NbtElement lllIIllIIlIllIl : lllIIllIIlIlIlI) {
            ((List)lllIIllIIlIlIIl.get()).add((ParticleType)Registry.PARTICLE_TYPE.get(new Identifier(lllIIllIIlIllIl.asString())));
        }
        lllIIllIIlIlIIl.changed();
        return (List)lllIIllIIlIlIIl.get();
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        if (suggestions == null) {
            suggestions = new ArrayList<Identifier>(Registry.PARTICLE_TYPE.getIds().size());
            for (Identifier lllIIllIlIIIlIl : Registry.PARTICLE_TYPE.getIds()) {
                ParticleType lllIIllIlIIIllI = (ParticleType)Registry.PARTICLE_TYPE.get(lllIIllIlIIIlIl);
                if (!(lllIIllIlIIIllI instanceof ParticleType)) continue;
                suggestions.add(lllIIllIlIIIlIl);
            }
        }
        return suggestions;
    }

    @Override
    protected List<ParticleType<?>> parseImpl(String lllIIllIlIlIIll) {
        String[] lllIIllIlIlIlIl = lllIIllIlIlIIll.split(",");
        ArrayList lllIIllIlIlIlII = new ArrayList(lllIIllIlIlIlIl.length);
        try {
            for (String lllIIllIlIllIII : lllIIllIlIlIlIl) {
                ParticleType lllIIllIlIllIIl = (ParticleType)ParticleTypeListSetting.parseId(Registry.PARTICLE_TYPE, lllIIllIlIllIII);
                if (!(lllIIllIlIllIIl instanceof ParticleType)) continue;
                lllIIllIlIlIlII.add(lllIIllIlIllIIl);
            }
        }
        catch (Exception lllIIllIlIlIIII) {
            // empty catch block
        }
        return lllIIllIlIlIlII;
    }

    @Override
    protected boolean isValueValid(List<ParticleType<?>> lllIIllIlIIlIlI) {
        return true;
    }

    public static class Builder {
        private /* synthetic */ Consumer<List<ParticleType<?>>> onChanged;
        private /* synthetic */ List<ParticleType<?>> defaultValue;
        private /* synthetic */ String description;
        private /* synthetic */ Consumer<Setting<List<ParticleType<?>>>> onModuleActivated;
        private /* synthetic */ String name;

        public Builder description(String lllllllllllllllllIIIIllIIlIllIII) {
            Builder lllllllllllllllllIIIIllIIlIllIll;
            lllllllllllllllllIIIIllIIlIllIll.description = lllllllllllllllllIIIIllIIlIllIII;
            return lllllllllllllllllIIIIllIIlIllIll;
        }

        public Builder name(String lllllllllllllllllIIIIllIIllIIIII) {
            Builder lllllllllllllllllIIIIllIIllIIIIl;
            lllllllllllllllllIIIIllIIllIIIIl.name = lllllllllllllllllIIIIllIIllIIIII;
            return lllllllllllllllllIIIIllIIllIIIIl;
        }

        public Builder onChanged(Consumer<List<ParticleType<?>>> lllllllllllllllllIIIIllIIlIIllII) {
            Builder lllllllllllllllllIIIIllIIlIIllIl;
            lllllllllllllllllIIIIllIIlIIllIl.onChanged = lllllllllllllllllIIIIllIIlIIllII;
            return lllllllllllllllllIIIIllIIlIIllIl;
        }

        public Builder() {
            Builder lllllllllllllllllIIIIllIIllIIlIl;
            lllllllllllllllllIIIIllIIllIIlIl.name = "undefined";
            lllllllllllllllllIIIIllIIllIIlIl.description = "";
        }

        public ParticleTypeListSetting build() {
            Builder lllllllllllllllllIIIIllIIlIIIlII;
            return new ParticleTypeListSetting(lllllllllllllllllIIIIllIIlIIIlII.name, lllllllllllllllllIIIIllIIlIIIlII.description, lllllllllllllllllIIIIllIIlIIIlII.defaultValue, lllllllllllllllllIIIIllIIlIIIlII.onChanged, lllllllllllllllllIIIIllIIlIIIlII.onModuleActivated);
        }

        public Builder defaultValue(List<ParticleType<?>> lllllllllllllllllIIIIllIIlIlIIlI) {
            Builder lllllllllllllllllIIIIllIIlIlIlIl;
            lllllllllllllllllIIIIllIIlIlIlIl.defaultValue = lllllllllllllllllIIIIllIIlIlIIlI;
            return lllllllllllllllllIIIIllIIlIlIlIl;
        }

        public Builder onModuleActivated(Consumer<Setting<List<ParticleType<?>>>> lllllllllllllllllIIIIllIIlIIlIII) {
            Builder lllllllllllllllllIIIIllIIlIIlIIl;
            lllllllllllllllllIIIIllIIlIIlIIl.onModuleActivated = lllllllllllllllllIIIIllIIlIIlIII;
            return lllllllllllllllllIIIIllIIlIIlIIl;
        }
    }
}

