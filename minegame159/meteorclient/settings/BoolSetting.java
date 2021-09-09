/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.settings;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.nbt.NbtCompound;

public class BoolSetting
extends Setting<Boolean> {
    private static final /* synthetic */ List<String> SUGGESTIONS;

    @Override
    protected boolean isValueValid(Boolean lllllllllllllllllIIIllIlIIIllIlI) {
        return true;
    }

    @Override
    public List<String> getSuggestions() {
        return SUGGESTIONS;
    }

    @Override
    protected Boolean parseImpl(String lllllllllllllllllIIIllIlIIIllllI) {
        if (lllllllllllllllllIIIllIlIIIllllI.equalsIgnoreCase("true") || lllllllllllllllllIIIllIlIIIllllI.equalsIgnoreCase("1")) {
            return true;
        }
        if (lllllllllllllllllIIIllIlIIIllllI.equalsIgnoreCase("false") || lllllllllllllllllIIIllIlIIIllllI.equalsIgnoreCase("0")) {
            return false;
        }
        if (lllllllllllllllllIIIllIlIIIllllI.equalsIgnoreCase("toggle")) {
            BoolSetting lllllllllllllllllIIIllIlIIIlllll;
            return (Boolean)lllllllllllllllllIIIllIlIIIlllll.get() == false;
        }
        return null;
    }

    @Override
    public Boolean fromTag(NbtCompound lllllllllllllllllIIIllIlIIIIllll) {
        BoolSetting lllllllllllllllllIIIllIlIIIlIIII;
        lllllllllllllllllIIIllIlIIIlIIII.set(lllllllllllllllllIIIllIlIIIIllll.getBoolean("value"));
        return (Boolean)lllllllllllllllllIIIllIlIIIlIIII.get();
    }

    private BoolSetting(String lllllllllllllllllIIIllIlIIlIllII, String lllllllllllllllllIIIllIlIIlIlIll, Boolean lllllllllllllllllIIIllIlIIlIIlII, Consumer<Boolean> lllllllllllllllllIIIllIlIIlIlIIl, Consumer<Setting<Boolean>> lllllllllllllllllIIIllIlIIlIlIII) {
        super(lllllllllllllllllIIIllIlIIlIllII, lllllllllllllllllIIIllIlIIlIlIll, lllllllllllllllllIIIllIlIIlIIlII, lllllllllllllllllIIIllIlIIlIlIIl, lllllllllllllllllIIIllIlIIlIlIII);
        BoolSetting lllllllllllllllllIIIllIlIIlIllIl;
    }

    @Override
    public NbtCompound toTag() {
        BoolSetting lllllllllllllllllIIIllIlIIIlIlII;
        NbtCompound lllllllllllllllllIIIllIlIIIlIlIl = lllllllllllllllllIIIllIlIIIlIlII.saveGeneral();
        lllllllllllllllllIIIllIlIIIlIlIl.putBoolean("value", ((Boolean)lllllllllllllllllIIIllIlIIIlIlII.get()).booleanValue());
        return lllllllllllllllllIIIllIlIIIlIlIl;
    }

    static {
        SUGGESTIONS = ImmutableList.of((Object)"true", (Object)"false", (Object)"toggle");
    }

    public static class Builder {
        private /* synthetic */ Consumer<Setting<Boolean>> onModuleActivated;
        private /* synthetic */ Consumer<Boolean> onChanged;
        private /* synthetic */ Boolean defaultValue;
        private /* synthetic */ String name;
        private /* synthetic */ String description;

        public Builder defaultValue(boolean llllllllllllllllIlIlllllIlllIIIl) {
            Builder llllllllllllllllIlIlllllIlllIlII;
            llllllllllllllllIlIlllllIlllIlII.defaultValue = llllllllllllllllIlIlllllIlllIIIl;
            return llllllllllllllllIlIlllllIlllIlII;
        }

        public BoolSetting build() {
            Builder llllllllllllllllIlIlllllIllIIIlI;
            return new BoolSetting(llllllllllllllllIlIlllllIllIIIlI.name, llllllllllllllllIlIlllllIllIIIlI.description, llllllllllllllllIlIlllllIllIIIlI.defaultValue, llllllllllllllllIlIlllllIllIIIlI.onChanged, llllllllllllllllIlIlllllIllIIIlI.onModuleActivated);
        }

        public Builder description(String llllllllllllllllIlIlllllIllllIIl) {
            Builder llllllllllllllllIlIlllllIllllIII;
            llllllllllllllllIlIlllllIllllIII.description = llllllllllllllllIlIlllllIllllIIl;
            return llllllllllllllllIlIlllllIllllIII;
        }

        public Builder() {
            Builder llllllllllllllllIlIllllllIIIIIll;
            llllllllllllllllIlIllllllIIIIIll.name = "undefined";
            llllllllllllllllIlIllllllIIIIIll.description = "";
        }

        public Builder onChanged(Consumer<Boolean> llllllllllllllllIlIlllllIllIlIll) {
            Builder llllllllllllllllIlIlllllIllIlllI;
            llllllllllllllllIlIlllllIllIlllI.onChanged = llllllllllllllllIlIlllllIllIlIll;
            return llllllllllllllllIlIlllllIllIlllI;
        }

        public Builder name(String llllllllllllllllIlIlllllIlllllIl) {
            Builder llllllllllllllllIlIlllllIllllllI;
            llllllllllllllllIlIlllllIllllllI.name = llllllllllllllllIlIlllllIlllllIl;
            return llllllllllllllllIlIlllllIllllllI;
        }

        public Builder onModuleActivated(Consumer<Setting<Boolean>> llllllllllllllllIlIlllllIllIIlll) {
            Builder llllllllllllllllIlIlllllIllIIllI;
            llllllllllllllllIlIlllllIllIIllI.onModuleActivated = llllllllllllllllIlIlllllIllIIlll;
            return llllllllllllllllIlIlllllIllIIllI;
        }
    }
}

