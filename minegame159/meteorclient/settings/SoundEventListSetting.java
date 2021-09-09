/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.util.Identifier
 *  net.minecraft.sound.SoundEvent
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
    protected boolean isValueValid(List<SoundEvent> lllIllIlIllllIl) {
        return true;
    }

    public SoundEventListSetting(String lllIllIllIlllll, String lllIllIlllIIlII, List<SoundEvent> lllIllIllIlllIl, Consumer<List<SoundEvent>> lllIllIlllIIIlI, Consumer<Setting<List<SoundEvent>>> lllIllIlllIIIIl) {
        super(lllIllIllIlllll, lllIllIlllIIlII, lllIllIllIlllIl, lllIllIlllIIIlI, lllIllIlllIIIIl);
        SoundEventListSetting lllIllIlllIIIII;
        lllIllIlllIIIII.value = new ArrayList<SoundEvent>(lllIllIllIlllIl);
    }

    @Override
    public void reset(boolean lllIllIllIlIlIl) {
        SoundEventListSetting lllIllIllIllIII;
        lllIllIllIllIII.value = new ArrayList((Collection)lllIllIllIllIII.defaultValue);
        if (lllIllIllIlIlIl) {
            lllIllIllIllIII.changed();
        }
    }

    @Override
    protected List<SoundEvent> parseImpl(String lllIllIllIIlIIl) {
        String[] lllIllIllIIlIII = lllIllIllIIlIIl.split(",");
        ArrayList<SoundEvent> lllIllIllIIIlll = new ArrayList<SoundEvent>(lllIllIllIIlIII.length);
        try {
            for (String lllIllIllIIlIll : lllIllIllIIlIII) {
                SoundEvent lllIllIllIIllII = (SoundEvent)SoundEventListSetting.parseId(Registry.SOUND_EVENT, lllIllIllIIlIll);
                if (lllIllIllIIllII == null) continue;
                lllIllIllIIIlll.add(lllIllIllIIllII);
            }
        }
        catch (Exception lllIllIllIIIIll) {
            // empty catch block
        }
        return lllIllIllIIIlll;
    }

    @Override
    public NbtCompound toTag() {
        SoundEventListSetting lllIllIlIllIlIl;
        NbtCompound lllIllIlIllIlII = lllIllIlIllIlIl.saveGeneral();
        NbtList lllIllIlIllIIll = new NbtList();
        for (SoundEvent lllIllIlIllIllI : (List)lllIllIlIllIlIl.get()) {
            lllIllIlIllIIll.add((Object)NbtString.of((String)Registry.SOUND_EVENT.getId((Object)lllIllIlIllIllI).toString()));
        }
        lllIllIlIllIlII.put("value", (NbtElement)lllIllIlIllIIll);
        return lllIllIlIllIlII;
    }

    @Override
    public List<SoundEvent> fromTag(NbtCompound lllIllIlIlIIIll) {
        SoundEventListSetting lllIllIlIlIIlll;
        ((List)lllIllIlIlIIlll.get()).clear();
        NbtList lllIllIlIlIIlIl = lllIllIlIlIIIll.getList("value", 8);
        for (NbtElement lllIllIlIlIlIII : lllIllIlIlIIlIl) {
            ((List)lllIllIlIlIIlll.get()).add((SoundEvent)Registry.SOUND_EVENT.get(new Identifier(lllIllIlIlIlIII.asString())));
        }
        lllIllIlIlIIlll.changed();
        return (List)lllIllIlIlIIlll.get();
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.SOUND_EVENT.getIds();
    }

    public static class Builder {
        private /* synthetic */ String name;
        private /* synthetic */ String description;
        private /* synthetic */ Consumer<Setting<List<SoundEvent>>> onModuleActivated;
        private /* synthetic */ Consumer<List<SoundEvent>> onChanged;
        private /* synthetic */ List<SoundEvent> defaultValue;

        public Builder name(String lIIIIIIlIIllII) {
            Builder lIIIIIIlIIllIl;
            lIIIIIIlIIllIl.name = lIIIIIIlIIllII;
            return lIIIIIIlIIllIl;
        }

        public Builder onChanged(Consumer<List<SoundEvent>> lIIIIIIIlllIlI) {
            Builder lIIIIIIIllllIl;
            lIIIIIIIllllIl.onChanged = lIIIIIIIlllIlI;
            return lIIIIIIIllllIl;
        }

        public SoundEventListSetting build() {
            Builder lIIIIIIIllIIlI;
            return new SoundEventListSetting(lIIIIIIIllIIlI.name, lIIIIIIIllIIlI.description, lIIIIIIIllIIlI.defaultValue, lIIIIIIIllIIlI.onChanged, lIIIIIIIllIIlI.onModuleActivated);
        }

        public Builder description(String lIIIIIIlIIIllI) {
            Builder lIIIIIIlIIIlll;
            lIIIIIIlIIIlll.description = lIIIIIIlIIIllI;
            return lIIIIIIlIIIlll;
        }

        public Builder defaultValue(List<SoundEvent> lIIIIIIlIIIIlI) {
            Builder lIIIIIIlIIIIll;
            lIIIIIIlIIIIll.defaultValue = lIIIIIIlIIIIlI;
            return lIIIIIIlIIIIll;
        }

        public Builder onModuleActivated(Consumer<Setting<List<SoundEvent>>> lIIIIIIIllIlII) {
            Builder lIIIIIIIllIlIl;
            lIIIIIIIllIlIl.onModuleActivated = lIIIIIIIllIlII;
            return lIIIIIIIllIlIl;
        }

        public Builder() {
            Builder lIIIIIIlIlIIlI;
            lIIIIIIlIlIIlI.name = "undefined";
            lIIIIIIlIlIIlI.description = "";
        }
    }
}

