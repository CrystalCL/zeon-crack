/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.network.Packet
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
    private static /* synthetic */ List<String> suggestions;

    @Override
    protected Object2BooleanMap<Class<? extends Packet<?>>> parseImpl(String llllllllllllllllllIlIIlIllIllIlI) {
        String[] llllllllllllllllllIlIIlIllIllIIl = llllllllllllllllllIlIIlIllIllIlI.split(",");
        Object2BooleanOpenHashMap llllllllllllllllllIlIIlIllIllIII = new Object2BooleanOpenHashMap(llllllllllllllllllIlIIlIllIllIIl.length);
        try {
            for (String llllllllllllllllllIlIIlIllIlllII : llllllllllllllllllIlIIlIllIllIIl) {
                Class<? extends Packet<?>> llllllllllllllllllIlIIlIllIlllIl = PacketUtils.getPacket(llllllllllllllllllIlIIlIllIlllII.trim());
                if (llllllllllllllllllIlIIlIllIlllIl == null) continue;
                llllllllllllllllllIlIIlIllIllIII.put(llllllllllllllllllIlIIlIllIlllIl, true);
            }
        }
        catch (Exception llllllllllllllllllIlIIlIllIlIlII) {
            // empty catch block
        }
        return llllllllllllllllllIlIIlIllIllIII;
    }

    @Override
    public List<String> getSuggestions() {
        if (suggestions == null) {
            suggestions = new ArrayList<String>(PacketUtils.getC2SPackets().size() + PacketUtils.getS2CPackets().size());
            for (Class<? extends Packet<?>> class_ : PacketUtils.getC2SPackets()) {
                suggestions.add(PacketUtils.getName(class_));
            }
            for (Class<? extends Packet<?>> class_ : PacketUtils.getS2CPackets()) {
                suggestions.add(PacketUtils.getName(class_));
            }
        }
        return suggestions;
    }

    @Override
    protected boolean isValueValid(Object2BooleanMap<Class<? extends Packet<?>>> llllllllllllllllllIlIIlIllIIlllI) {
        return true;
    }

    public PacketBoolSetting(String llllllllllllllllllIlIIlIllllIIII, String llllllllllllllllllIlIIlIlllIllll, Object2BooleanMap<Class<? extends Packet<?>>> llllllllllllllllllIlIIlIlllIlllI, Consumer<Object2BooleanMap<Class<? extends Packet<?>>>> llllllllllllllllllIlIIlIlllIllIl, Consumer<Setting<Object2BooleanMap<Class<? extends Packet<?>>>>> llllllllllllllllllIlIIlIlllIllII) {
        super(llllllllllllllllllIlIIlIllllIIII, llllllllllllllllllIlIIlIlllIllll, llllllllllllllllllIlIIlIlllIlllI, llllllllllllllllllIlIIlIlllIllIl, llllllllllllllllllIlIIlIlllIllII);
        PacketBoolSetting llllllllllllllllllIlIIlIllllIlll;
        llllllllllllllllllIlIIlIllllIlll.value = new Object2BooleanArrayMap(llllllllllllllllllIlIIlIlllIlllI);
    }

    @Override
    public NbtCompound toTag() {
        PacketBoolSetting llllllllllllllllllIlIIlIlIllllIl;
        NbtCompound llllllllllllllllllIlIIlIlIllllll = llllllllllllllllllIlIIlIlIllllIl.saveGeneral();
        NbtCompound llllllllllllllllllIlIIlIlIlllllI = new NbtCompound();
        for (Class llllllllllllllllllIlIIlIllIIIIIl : ((Object2BooleanMap)llllllllllllllllllIlIIlIlIllllIl.get()).keySet()) {
            llllllllllllllllllIlIIlIlIlllllI.putBoolean(PacketUtils.getName(llllllllllllllllllIlIIlIllIIIIIl), ((Object2BooleanMap)llllllllllllllllllIlIIlIlIllllIl.get()).getBoolean((Object)llllllllllllllllllIlIIlIllIIIIIl));
        }
        llllllllllllllllllIlIIlIlIllllll.put("value", (NbtElement)llllllllllllllllllIlIIlIlIlllllI);
        return llllllllllllllllllIlIIlIlIllllll;
    }

    @Override
    public Object2BooleanMap<Class<? extends Packet<?>>> fromTag(NbtCompound llllllllllllllllllIlIIlIlIlIllll) {
        PacketBoolSetting llllllllllllllllllIlIIlIlIllIIII;
        ((Object2BooleanMap)llllllllllllllllllIlIIlIlIllIIII.get()).clear();
        NbtCompound llllllllllllllllllIlIIlIlIlIlllI = llllllllllllllllllIlIIlIlIlIllll.getCompound("value");
        for (String llllllllllllllllllIlIIlIlIllIIIl : llllllllllllllllllIlIIlIlIlIlllI.getKeys()) {
            Class<? extends Packet<?>> llllllllllllllllllIlIIlIlIllIIlI = PacketUtils.getPacket(llllllllllllllllllIlIIlIlIllIIIl);
            if (llllllllllllllllllIlIIlIlIllIIlI == null) continue;
            ((Object2BooleanMap)llllllllllllllllllIlIIlIlIllIIII.get()).put(llllllllllllllllllIlIIlIlIllIIlI, llllllllllllllllllIlIIlIlIlIlllI.getBoolean(llllllllllllllllllIlIIlIlIllIIIl));
        }
        llllllllllllllllllIlIIlIlIllIIII.changed();
        return (Object2BooleanMap)llllllllllllllllllIlIIlIlIllIIII.get();
    }

    @Override
    public void reset(boolean llllllllllllllllllIlIIlIlllIlIII) {
        PacketBoolSetting llllllllllllllllllIlIIlIlllIlIIl;
        llllllllllllllllllIlIIlIlllIlIIl.value = new Object2BooleanArrayMap((Object2BooleanMap)llllllllllllllllllIlIIlIlllIlIIl.defaultValue);
        if (llllllllllllllllllIlIIlIlllIlIII) {
            llllllllllllllllllIlIIlIlllIlIIl.changed();
        }
    }

    public static class Builder {
        private /* synthetic */ String name;
        private /* synthetic */ String description;
        private /* synthetic */ Consumer<Object2BooleanMap<Class<? extends Packet<?>>>> onChanged;
        private /* synthetic */ Object2BooleanMap<Class<? extends Packet<?>>> defaultValue;
        private /* synthetic */ Consumer<Setting<Object2BooleanMap<Class<? extends Packet<?>>>>> onModuleActivated;

        public Builder name(String lIlIIIIIlllIlll) {
            Builder lIlIIIIIllllIlI;
            lIlIIIIIllllIlI.name = lIlIIIIIlllIlll;
            return lIlIIIIIllllIlI;
        }

        public PacketBoolSetting build() {
            Builder lIlIIIIIlIlllIl;
            return new PacketBoolSetting(lIlIIIIIlIlllIl.name, lIlIIIIIlIlllIl.description, lIlIIIIIlIlllIl.defaultValue, lIlIIIIIlIlllIl.onChanged, lIlIIIIIlIlllIl.onModuleActivated);
        }

        public Builder onChanged(Consumer<Object2BooleanMap<Class<? extends Packet<?>>>> lIlIIIIIllIIlIl) {
            Builder lIlIIIIIllIIllI;
            lIlIIIIIllIIllI.onChanged = lIlIIIIIllIIlIl;
            return lIlIIIIIllIIllI;
        }

        public Builder() {
            Builder lIlIIIIIlllllIl;
            lIlIIIIIlllllIl.name = "undefined";
            lIlIIIIIlllllIl.description = "";
        }

        public Builder defaultValue(Object2BooleanMap<Class<? extends Packet<?>>> lIlIIIIIllIllIl) {
            Builder lIlIIIIIllIllII;
            lIlIIIIIllIllII.defaultValue = lIlIIIIIllIllIl;
            return lIlIIIIIllIllII;
        }

        public Builder description(String lIlIIIIIlllIIll) {
            Builder lIlIIIIIlllIlII;
            lIlIIIIIlllIlII.description = lIlIIIIIlllIIll;
            return lIlIIIIIlllIlII;
        }

        public Builder onModuleActivated(Consumer<Setting<Object2BooleanMap<Class<? extends Packet<?>>>>> lIlIIIIIllIIIIl) {
            Builder lIlIIIIIllIIIlI;
            lIlIIIIIllIIIlI.onModuleActivated = lIlIIIIIllIIIIl;
            return lIlIIIIIllIIIlI;
        }
    }
}

