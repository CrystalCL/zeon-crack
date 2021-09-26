/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class StatusEffectSetting
extends Setting<Object2IntMap<StatusEffect>> {
    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtCompound NbtCompound3 = new NbtCompound();
        for (StatusEffect StatusEffect2 : ((Object2IntMap)this.get()).keySet()) {
            Identifier Identifier2 = Registry.STATUS_EFFECT.getId((Object)StatusEffect2);
            if (Identifier2 == null) continue;
            NbtCompound3.putInt(Identifier2.toString(), ((Object2IntMap)this.get()).getInt((Object)StatusEffect2));
        }
        NbtCompound2.put("value", (NbtElement)NbtCompound3);
        return NbtCompound2;
    }

    @Override
    protected Object2IntMap<StatusEffect> parseImpl(String string) {
        String[] stringArray = string.split(",");
        Object2IntMap<StatusEffect> object2IntMap = Utils.createStatusEffectMap();
        try {
            for (String string2 : stringArray) {
                String[] stringArray2 = string2.split(" ");
                StatusEffect StatusEffect2 = (StatusEffect)StatusEffectSetting.parseId(Registry.STATUS_EFFECT, stringArray2[0]);
                int n = Integer.parseInt(stringArray2[1]);
                object2IntMap.put((Object)StatusEffect2, n);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return object2IntMap;
    }

    public StatusEffectSetting(String string, String string2, Object2IntMap<StatusEffect> object2IntMap, Consumer<Object2IntMap<StatusEffect>> consumer, Consumer<Setting<Object2IntMap<StatusEffect>>> consumer2) {
        super(string, string2, object2IntMap, consumer, consumer2);
    }

    @Override
    public Object2IntMap<StatusEffect> fromTag(NbtCompound NbtCompound2) {
        ((Object2IntMap)this.get()).clear();
        NbtCompound NbtCompound3 = NbtCompound2.getCompound("value");
        for (String string : NbtCompound3.getKeys()) {
            StatusEffect StatusEffect2 = (StatusEffect)Registry.STATUS_EFFECT.get(new Identifier(string));
            if (StatusEffect2 == null) continue;
            ((Object2IntMap)this.get()).put((Object)StatusEffect2, NbtCompound3.getInt(string));
        }
        this.changed();
        return (Object2IntMap)this.get();
    }

    @Override
    public void reset(boolean bl) {
        this.value = new Object2IntArrayMap((Object2IntMap)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((Object2IntMap<StatusEffect>)((Object2IntMap)object));
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
    protected boolean isValueValid(Object2IntMap<StatusEffect> object2IntMap) {
        return true;
    }

    public static class Builder {
        private Consumer<Object2IntMap<StatusEffect>> onChanged;
        private Consumer<Setting<Object2IntMap<StatusEffect>>> onModuleActivated;
        private String name = "undefined";
        private String description = "";
        private Object2IntMap<StatusEffect> defaultValue;

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<Object2IntMap<StatusEffect>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder defaultValue(Object2IntMap<StatusEffect> object2IntMap) {
            this.defaultValue = object2IntMap;
            return this;
        }

        public StatusEffectSetting build() {
            return new StatusEffectSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder onChanged(Consumer<Object2IntMap<StatusEffect>> consumer) {
            this.onChanged = consumer;
            return this;
        }
    }
}

