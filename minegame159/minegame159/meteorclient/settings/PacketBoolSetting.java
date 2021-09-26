/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.network.PacketUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.Packet;

public class PacketBoolSetting
extends Setting<Object2BooleanMap<Class<? extends Packet<?>>>> {
    private static List<String> suggestions;

    @Override
    public void reset(boolean bl) {
        this.value = new Object2BooleanArrayMap((Object2BooleanMap)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    @Override
    public List<String> getSuggestions() {
        if (suggestions == null) {
            suggestions = new ArrayList<String>(PacketUtils.getC2SPackets().size() + PacketUtils.getS2CPackets().size());
            for (Class<? extends Packet<?>> clazz : PacketUtils.getC2SPackets()) {
                suggestions.add(PacketUtils.getName(clazz));
            }
            for (Class<? extends Packet<?>> clazz : PacketUtils.getS2CPackets()) {
                suggestions.add(PacketUtils.getName(clazz));
            }
        }
        return suggestions;
    }

    public PacketBoolSetting(String string, String string2, Object2BooleanMap<Class<? extends Packet<?>>> object2BooleanMap, Consumer<Object2BooleanMap<Class<? extends Packet<?>>>> consumer, Consumer<Setting<Object2BooleanMap<Class<? extends Packet<?>>>>> consumer2) {
        super(string, string2, object2BooleanMap, consumer, consumer2);
        this.value = new Object2BooleanArrayMap(object2BooleanMap);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    protected Object2BooleanMap<Class<? extends Packet<?>>> parseImpl(String string) {
        String[] stringArray = string.split(",");
        Object2BooleanOpenHashMap object2BooleanOpenHashMap = new Object2BooleanOpenHashMap(stringArray.length);
        try {
            for (String string2 : stringArray) {
                Class<? extends Packet<?>> clazz = PacketUtils.getPacket(string2.trim());
                if (clazz == null) continue;
                object2BooleanOpenHashMap.put(clazz, true);
                if (0 != -1) continue;
                return null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return object2BooleanOpenHashMap;
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((Object2BooleanMap)object);
    }

    @Override
    public Object2BooleanMap<Class<? extends Packet<?>>> fromTag(NbtCompound NbtCompound2) {
        ((Object2BooleanMap)this.get()).clear();
        NbtCompound NbtCompound3 = NbtCompound2.getCompound("value");
        for (String string : NbtCompound3.getKeys()) {
            Class<? extends Packet<?>> clazz = PacketUtils.getPacket(string);
            if (clazz == null) continue;
            ((Object2BooleanMap)this.get()).put(clazz, NbtCompound3.getBoolean(string));
        }
        this.changed();
        return (Object2BooleanMap)this.get();
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtCompound NbtCompound3 = new NbtCompound();
        for (Class clazz : ((Object2BooleanMap)this.get()).keySet()) {
            NbtCompound3.putBoolean(PacketUtils.getName(clazz), ((Object2BooleanMap)this.get()).getBoolean((Object)clazz));
        }
        NbtCompound2.put("value", (NbtElement)NbtCompound3);
        return NbtCompound2;
    }

    @Override
    protected boolean isValueValid(Object2BooleanMap<Class<? extends Packet<?>>> object2BooleanMap) {
        return true;
    }

    public static class Builder {
        private Object2BooleanMap<Class<? extends Packet<?>>> defaultValue;
        private String name = "undefined";
        private String description = "";
        private Consumer<Setting<Object2BooleanMap<Class<? extends Packet<?>>>>> onModuleActivated;
        private Consumer<Object2BooleanMap<Class<? extends Packet<?>>>> onChanged;

        public PacketBoolSetting build() {
            return new PacketBoolSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder onChanged(Consumer<Object2BooleanMap<Class<? extends Packet<?>>>> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<Object2BooleanMap<Class<? extends Packet<?>>>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder defaultValue(Object2BooleanMap<Class<? extends Packet<?>>> object2BooleanMap) {
            this.defaultValue = object2BooleanMap;
            return this;
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }
    }
}

