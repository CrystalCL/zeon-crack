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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.sound.SoundEvent;

public class SoundEventListSetting
extends Setting<List<SoundEvent>> {
    @Override
    protected boolean isValueValid(List<SoundEvent> list) {
        return true;
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.SOUND_EVENT.getIds();
    }

    @Override
    public void reset(boolean bl) {
        this.value = new ArrayList((Collection)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    @Override
    protected List<SoundEvent> parseImpl(String string) {
        String[] stringArray = string.split(",");
        ArrayList<SoundEvent> arrayList = new ArrayList<SoundEvent>(stringArray.length);
        try {
            for (String string2 : stringArray) {
                SoundEvent SoundEvent2 = (SoundEvent)SoundEventListSetting.parseId(Registry.SOUND_EVENT, string2);
                if (SoundEvent2 == null) continue;
                arrayList.add(SoundEvent2);
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
        for (SoundEvent SoundEvent2 : (List)this.get()) {
            NbtList2.add((Object)NbtString.of((String)Registry.SOUND_EVENT.getId((Object)SoundEvent2).toString()));
        }
        NbtCompound2.put("value", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    @Override
    public List<SoundEvent> fromTag(NbtCompound NbtCompound2) {
        ((List)this.get()).clear();
        NbtList NbtList2 = NbtCompound2.getList("value", 8);
        for (NbtElement NbtElement2 : NbtList2) {
            ((List)this.get()).add((SoundEvent)Registry.SOUND_EVENT.get(new Identifier(NbtElement2.asString())));
        }
        this.changed();
        return (List)this.get();
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((List)object);
    }

    public SoundEventListSetting(String string, String string2, List<SoundEvent> list, Consumer<List<SoundEvent>> consumer, Consumer<Setting<List<SoundEvent>>> consumer2) {
        super(string, string2, list, consumer, consumer2);
        this.value = new ArrayList<SoundEvent>(list);
    }

    public static class Builder {
        private Consumer<List<SoundEvent>> onChanged;
        private List<SoundEvent> defaultValue;
        private Consumer<Setting<List<SoundEvent>>> onModuleActivated;
        private String name = "undefined";
        private String description = "";

        public Builder onModuleActivated(Consumer<Setting<List<SoundEvent>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder defaultValue(List<SoundEvent> list) {
            this.defaultValue = list;
            return this;
        }

        public Builder onChanged(Consumer<List<SoundEvent>> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public SoundEventListSetting build() {
            return new SoundEventListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }
    }
}

