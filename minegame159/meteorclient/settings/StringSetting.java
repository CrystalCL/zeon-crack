/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.settings;

import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.nbt.NbtCompound;

public class StringSetting
extends Setting<String> {
    @Override
    public NbtCompound toTag() {
        StringSetting lllllIlIlllll;
        NbtCompound lllllIlIllllI = lllllIlIlllll.saveGeneral();
        lllllIlIllllI.putString("value", (String)lllllIlIlllll.get());
        return lllllIlIllllI;
    }

    @Override
    public void reset(boolean lllllIllIIlII) {
        StringSetting lllllIllIIlIl;
        lllllIllIIlIl.value = lllllIllIIlIl.defaultValue;
        if (lllllIllIIlII) {
            lllllIllIIlIl.changed();
        }
    }

    @Override
    protected String parseImpl(String lllllIllIlIlI) {
        return lllllIllIlIlI;
    }

    @Override
    public String fromTag(NbtCompound lllllIlIlIllI) {
        StringSetting lllllIlIllIIl;
        lllllIlIllIIl.set(lllllIlIlIllI.getString("value"));
        return (String)lllllIlIllIIl.get();
    }

    @Override
    protected boolean isValueValid(String lllllIllIIIlI) {
        return true;
    }

    public StringSetting(String lllllIlllIIlI, String lllllIlllIlll, String lllllIlllIllI, Consumer<String> lllllIlllIlIl, Consumer<Setting<String>> lllllIlllIlII) {
        super(lllllIlllIIlI, lllllIlllIlll, lllllIlllIllI, lllllIlllIlIl, lllllIlllIlII);
        StringSetting lllllIllllIIl;
        lllllIllllIIl.value = lllllIlllIllI;
    }

    public static class Builder {
        private /* synthetic */ String defaultValue;
        private /* synthetic */ Consumer<Setting<String>> onModuleActivated;
        private /* synthetic */ Consumer<String> onChanged;
        private /* synthetic */ String name;
        private /* synthetic */ String description;

        public Builder onChanged(Consumer<String> lIIlIIIIIllllIl) {
            Builder lIIlIIIIIlllllI;
            lIIlIIIIIlllllI.onChanged = lIIlIIIIIllllIl;
            return lIIlIIIIIlllllI;
        }

        public Builder defaultValue(String lIIlIIIIlIIIIIl) {
            Builder lIIlIIIIlIIIIlI;
            lIIlIIIIlIIIIlI.defaultValue = lIIlIIIIlIIIIIl;
            return lIIlIIIIlIIIIlI;
        }

        public Builder description(String lIIlIIIIlIIIlll) {
            Builder lIIlIIIIlIIlIII;
            lIIlIIIIlIIlIII.description = lIIlIIIIlIIIlll;
            return lIIlIIIIlIIlIII;
        }

        public Builder() {
            Builder lIIlIIIIlIlIIll;
            lIIlIIIIlIlIIll.name = "undefined";
            lIIlIIIIlIlIIll.description = "";
        }

        public Builder name(String lIIlIIIIlIIllIl) {
            Builder lIIlIIIIlIIlllI;
            lIIlIIIIlIIlllI.name = lIIlIIIIlIIllIl;
            return lIIlIIIIlIIlllI;
        }

        public Builder onModuleActivated(Consumer<Setting<String>> lIIlIIIIIllIlll) {
            Builder lIIlIIIIIlllIII;
            lIIlIIIIIlllIII.onModuleActivated = lIIlIIIIIllIlll;
            return lIIlIIIIIlllIII;
        }

        public StringSetting build() {
            Builder lIIlIIIIIllIIll;
            return new StringSetting(lIIlIIIIIllIIll.name, lIIlIIIIIllIIll.description, lIIlIIIIIllIIll.defaultValue, lIIlIIIIIllIIll.onChanged, lIIlIIIIIllIIll.onModuleActivated);
        }
    }
}

