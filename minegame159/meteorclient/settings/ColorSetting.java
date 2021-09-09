/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 */
package minegame159.meteorclient.settings;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class ColorSetting
extends Setting<SettingColor> {
    private static final /* synthetic */ List<String> SUGGESTIONS;

    @Override
    protected SettingColor parseImpl(String lIllIlIIlIlIIlI) {
        try {
            String[] lIllIlIIlIlIlIl = lIllIlIIlIlIIlI.split(" ");
            return new SettingColor(Integer.parseInt(lIllIlIIlIlIlIl[0]), Integer.parseInt(lIllIlIIlIlIlIl[1]), Integer.parseInt(lIllIlIIlIlIlIl[2]), Integer.parseInt(lIllIlIIlIlIlIl[3]));
        }
        catch (IndexOutOfBoundsException | NumberFormatException lIllIlIIlIlIlII) {
            return null;
        }
    }

    @Override
    protected boolean isValueValid(SettingColor lIllIlIIlIIIlll) {
        lIllIlIIlIIIlll.validate();
        return true;
    }

    @Override
    public NbtCompound toTag() {
        ColorSetting lIllIlIIlIIIIII;
        NbtCompound lIllIlIIlIIIIIl = lIllIlIIlIIIIII.saveGeneral();
        lIllIlIIlIIIIIl.put("value", (NbtElement)((SettingColor)lIllIlIIlIIIIII.get()).toTag());
        return lIllIlIIlIIIIIl;
    }

    @Override
    public SettingColor fromTag(NbtCompound lIllIlIIIlllIll) {
        ColorSetting lIllIlIIIlllIlI;
        ((SettingColor)lIllIlIIIlllIlI.get()).fromTag(lIllIlIIIlllIll.getCompound("value"));
        lIllIlIIIlllIlI.changed();
        return (SettingColor)lIllIlIIIlllIlI.get();
    }

    static {
        SUGGESTIONS = ImmutableList.of((Object)"0 0 0 255", (Object)"225 25 25 255", (Object)"25 225 25 255", (Object)"25 25 225 255", (Object)"255 255 255 255");
    }

    public ColorSetting(String lIllIlIIlIlllII, String lIllIlIIllIIIIl, SettingColor lIllIlIIllIIIII, Consumer<SettingColor> lIllIlIIlIllIIl, Consumer<Setting<SettingColor>> lIllIlIIlIllIII) {
        super(lIllIlIIlIlllII, lIllIlIIllIIIIl, lIllIlIIllIIIII, lIllIlIIlIllIIl, lIllIlIIlIllIII);
        ColorSetting lIllIlIIllIIIll;
    }

    @Override
    public List<String> getSuggestions() {
        return SUGGESTIONS;
    }

    @Override
    public void reset(boolean lIllIlIIlIIllII) {
        ColorSetting lIllIlIIlIIlIll;
        if (lIllIlIIlIIlIll.value == null) {
            lIllIlIIlIIlIll.value = new SettingColor((SettingColor)lIllIlIIlIIlIll.defaultValue);
        } else {
            ((SettingColor)lIllIlIIlIIlIll.value).set((Color)lIllIlIIlIIlIll.defaultValue);
        }
        if (lIllIlIIlIIllII) {
            lIllIlIIlIIlIll.changed();
        }
    }

    public static class Builder {
        private /* synthetic */ Consumer<Setting<SettingColor>> onModuleActivated;
        private /* synthetic */ Consumer<SettingColor> onChanged;
        private /* synthetic */ SettingColor defaultValue;
        private /* synthetic */ String name;
        private /* synthetic */ String description;

        public Builder() {
            Builder lllllllllllllllllIllllllIIIIIIIl;
            lllllllllllllllllIllllllIIIIIIIl.name = "undefined";
            lllllllllllllllllIllllllIIIIIIIl.description = "";
        }

        public Builder description(String lllllllllllllllllIlllllIllllIllI) {
            Builder lllllllllllllllllIlllllIllllIlIl;
            lllllllllllllllllIlllllIllllIlIl.description = lllllllllllllllllIlllllIllllIllI;
            return lllllllllllllllllIlllllIllllIlIl;
        }

        public Builder onChanged(Consumer<SettingColor> lllllllllllllllllIlllllIlllIlIII) {
            Builder lllllllllllllllllIlllllIlllIlIIl;
            lllllllllllllllllIlllllIlllIlIIl.onChanged = lllllllllllllllllIlllllIlllIlIII;
            return lllllllllllllllllIlllllIlllIlIIl;
        }

        public Builder onModuleActivated(Consumer<Setting<SettingColor>> lllllllllllllllllIlllllIlllIIlII) {
            Builder lllllllllllllllllIlllllIlllIIIll;
            lllllllllllllllllIlllllIlllIIIll.onModuleActivated = lllllllllllllllllIlllllIlllIIlII;
            return lllllllllllllllllIlllllIlllIIIll;
        }

        public ColorSetting build() {
            Builder lllllllllllllllllIlllllIlllIIIII;
            return new ColorSetting(lllllllllllllllllIlllllIlllIIIII.name, lllllllllllllllllIlllllIlllIIIII.description, lllllllllllllllllIlllllIlllIIIII.defaultValue, lllllllllllllllllIlllllIlllIIIII.onChanged, lllllllllllllllllIlllllIlllIIIII.onModuleActivated);
        }

        public Builder name(String lllllllllllllllllIlllllIllllllII) {
            Builder lllllllllllllllllIlllllIllllllIl;
            lllllllllllllllllIlllllIllllllIl.name = lllllllllllllllllIlllllIllllllII;
            return lllllllllllllllllIlllllIllllllIl;
        }

        public Builder defaultValue(SettingColor lllllllllllllllllIlllllIlllIlllI) {
            Builder lllllllllllllllllIlllllIllllIIIl;
            lllllllllllllllllIlllllIllllIIIl.defaultValue = lllllllllllllllllIlllllIlllIlllI;
            return lllllllllllllllllIlllllIllllIIIl;
        }
    }
}

