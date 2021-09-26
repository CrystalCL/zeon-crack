/*
 * Decompiled with CFR 0.151.
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
    private static List<Identifier> suggestions;

    @Override
    protected List<ParticleType<?>> parseImpl(String string) {
        String[] stringArray = string.split(",");
        ArrayList arrayList = new ArrayList(stringArray.length);
        try {
            for (String string2 : stringArray) {
                ParticleType ParticleType2 = (ParticleType)ParticleTypeListSetting.parseId(Registry.PARTICLE_TYPE, string2);
                if (!(ParticleType2 instanceof ParticleType)) continue;
                arrayList.add(ParticleType2);
                if (0 != 3) continue;
                return null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return arrayList;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        if (suggestions == null) {
            suggestions = new ArrayList<Identifier>(Registry.PARTICLE_TYPE.getIds().size());
            for (Identifier Identifier2 : Registry.PARTICLE_TYPE.getIds()) {
                ParticleType ParticleType2 = (ParticleType)Registry.PARTICLE_TYPE.get(Identifier2);
                if (!(ParticleType2 instanceof ParticleType)) continue;
                suggestions.add(Identifier2);
            }
        }
        return suggestions;
    }

    @Override
    public List<ParticleType<?>> fromTag(NbtCompound NbtCompound2) {
        ((List)this.get()).clear();
        NbtList NbtList2 = NbtCompound2.getList("value", 8);
        for (NbtElement NbtElement2 : NbtList2) {
            ((List)this.get()).add((ParticleType)Registry.PARTICLE_TYPE.get(new Identifier(NbtElement2.asString())));
        }
        this.changed();
        return (List)this.get();
    }

    @Override
    public void reset(boolean bl) {
        this.value = new ArrayList((Collection)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtList NbtList2 = new NbtList();
        for (ParticleType ParticleType2 : (List)this.get()) {
            NbtList2.add((Object)NbtString.of((String)Registry.PARTICLE_TYPE.getId((Object)ParticleType2).toString()));
        }
        NbtCompound2.put("value", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public ParticleTypeListSetting(String string, String string2, List<ParticleType<?>> list, Consumer<List<ParticleType<?>>> consumer, Consumer<Setting<List<ParticleType<?>>>> consumer2) {
        super(string, string2, list, consumer, consumer2);
        this.value = new ArrayList(list);
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((List)object);
    }

    @Override
    protected boolean isValueValid(List<ParticleType<?>> list) {
        return true;
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    public static class Builder {
        private List<ParticleType<?>> defaultValue;
        private Consumer<Setting<List<ParticleType<?>>>> onModuleActivated;
        private String name = "undefined";
        private String description = "";
        private Consumer<List<ParticleType<?>>> onChanged;

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<List<ParticleType<?>>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder defaultValue(List<ParticleType<?>> list) {
            this.defaultValue = list;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public ParticleTypeListSetting build() {
            return new ParticleTypeListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder onChanged(Consumer<List<ParticleType<?>>> consumer) {
            this.onChanged = consumer;
            return this;
        }
    }
}

