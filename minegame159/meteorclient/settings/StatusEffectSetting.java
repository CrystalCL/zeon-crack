/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  net.minecraft.entity.effect.StatusEffect
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.util.Identifier
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
    protected boolean isValueValid(Object2IntMap<StatusEffect> lIIllIlIIIIIIll) {
        return true;
    }

    @Override
    public void reset(boolean lIIllIlIIlIIIll) {
        StatusEffectSetting lIIllIlIIlIIlII;
        lIIllIlIIlIIlII.value = new Object2IntArrayMap((Object2IntMap)lIIllIlIIlIIlII.defaultValue);
        if (lIIllIlIIlIIIll) {
            lIIllIlIIlIIlII.changed();
        }
    }

    @Override
    protected Object2IntMap<StatusEffect> parseImpl(String lIIllIlIIIIlllI) {
        String[] lIIllIlIIIlIIII = lIIllIlIIIIlllI.split(",");
        Object2IntMap<StatusEffect> lIIllIlIIIIllll = Utils.createStatusEffectMap();
        try {
            for (String lIIllIlIIIlIIll : lIIllIlIIIlIIII) {
                String[] lIIllIlIIIlIllI = lIIllIlIIIlIIll.split(" ");
                StatusEffect lIIllIlIIIlIlIl = (StatusEffect)StatusEffectSetting.parseId(Registry.STATUS_EFFECT, lIIllIlIIIlIllI[0]);
                int lIIllIlIIIlIlII = Integer.parseInt(lIIllIlIIIlIllI[1]);
                lIIllIlIIIIllll.put((Object)lIIllIlIIIlIlIl, lIIllIlIIIlIlII);
            }
        }
        catch (Exception lIIllIlIIIIlIll) {
            // empty catch block
        }
        return lIIllIlIIIIllll;
    }

    public StatusEffectSetting(String lIIllIlIIlIlIll, String lIIllIlIIlIlIlI, Object2IntMap<StatusEffect> lIIllIlIIlIllll, Consumer<Object2IntMap<StatusEffect>> lIIllIlIIlIlIII, Consumer<Setting<Object2IntMap<StatusEffect>>> lIIllIlIIlIIlll) {
        super(lIIllIlIIlIlIll, lIIllIlIIlIlIlI, lIIllIlIIlIllll, lIIllIlIIlIlIII, lIIllIlIIlIIlll);
        StatusEffectSetting lIIllIlIIlIllII;
    }

    @Override
    public NbtCompound toTag() {
        StatusEffectSetting lIIllIIlllllIlI;
        NbtCompound lIIllIIlllllIIl = lIIllIIlllllIlI.saveGeneral();
        NbtCompound lIIllIIlllllIII = new NbtCompound();
        for (StatusEffect lIIllIIlllllIll : ((Object2IntMap)lIIllIIlllllIlI.get()).keySet()) {
            Identifier lIIllIIllllllII = Registry.STATUS_EFFECT.getId((Object)lIIllIIlllllIll);
            if (lIIllIIllllllII == null) continue;
            lIIllIIlllllIII.putInt(lIIllIIllllllII.toString(), ((Object2IntMap)lIIllIIlllllIlI.get()).getInt((Object)lIIllIIlllllIll));
        }
        lIIllIIlllllIIl.put("value", (NbtElement)lIIllIIlllllIII);
        return lIIllIIlllllIIl;
    }

    @Override
    public Object2IntMap<StatusEffect> fromTag(NbtCompound lIIllIIlllIlIII) {
        StatusEffectSetting lIIllIIlllIIllI;
        ((Object2IntMap)lIIllIIlllIIllI.get()).clear();
        NbtCompound lIIllIIlllIIlll = lIIllIIlllIlIII.getCompound("value");
        for (String lIIllIIlllIlIlI : lIIllIIlllIIlll.getKeys()) {
            StatusEffect lIIllIIlllIlIll = (StatusEffect)Registry.STATUS_EFFECT.get(new Identifier(lIIllIIlllIlIlI));
            if (lIIllIIlllIlIll == null) continue;
            ((Object2IntMap)lIIllIIlllIIllI.get()).put((Object)lIIllIIlllIlIll, lIIllIIlllIIlll.getInt(lIIllIIlllIlIlI));
        }
        lIIllIIlllIIllI.changed();
        return (Object2IntMap)lIIllIIlllIIllI.get();
    }

    public static class Builder {
        private /* synthetic */ Object2IntMap<StatusEffect> defaultValue;
        private /* synthetic */ String description;
        private /* synthetic */ String name;
        private /* synthetic */ Consumer<Object2IntMap<StatusEffect>> onChanged;
        private /* synthetic */ Consumer<Setting<Object2IntMap<StatusEffect>>> onModuleActivated;

        public Builder onModuleActivated(Consumer<Setting<Object2IntMap<StatusEffect>>> llIIIIlIlII) {
            Builder llIIIIlIlll;
            llIIIIlIlll.onModuleActivated = llIIIIlIlII;
            return llIIIIlIlll;
        }

        public Builder description(String llIIIlIlIII) {
            Builder llIIIlIlIIl;
            llIIIlIlIIl.description = llIIIlIlIII;
            return llIIIlIlIIl;
        }

        public Builder() {
            Builder llIIIllIIlI;
            llIIIllIIlI.name = "undefined";
            llIIIllIIlI.description = "";
        }

        public Builder name(String llIIIlIlllI) {
            Builder llIIIlIllll;
            llIIIlIllll.name = llIIIlIlllI;
            return llIIIlIllll;
        }

        public Builder onChanged(Consumer<Object2IntMap<StatusEffect>> llIIIIlllII) {
            Builder llIIIIlllIl;
            llIIIIlllIl.onChanged = llIIIIlllII;
            return llIIIIlllIl;
        }

        public Builder defaultValue(Object2IntMap<StatusEffect> llIIIlIIIII) {
            Builder llIIIlIIIll;
            llIIIlIIIll.defaultValue = llIIIlIIIII;
            return llIIIlIIIll;
        }

        public StatusEffectSetting build() {
            Builder llIIIIlIIlI;
            return new StatusEffectSetting(llIIIIlIIlI.name, llIIIIlIIlI.description, llIIIIlIIlI.defaultValue, llIIIIlIIlI.onChanged, llIIIIlIIlI.onModuleActivated);
        }
    }
}

