/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.settings;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.nbt.NbtCompound;

public class EnumSetting<T extends Enum<?>>
extends Setting<T> {
    private /* synthetic */ WDropdown<T> widget;
    private /* synthetic */ T[] values;
    private final /* synthetic */ List<String> suggestions;

    public EnumSetting(String llllIlllllIllII, String llllIlllllIlIll, T llllIlllllIlIlI, Consumer<T> llllIlllllIllll, Consumer<Setting<T>> llllIlllllIlIII) {
        super(llllIlllllIllII, llllIlllllIlIll, llllIlllllIlIlI, llllIlllllIllll, llllIlllllIlIII);
        EnumSetting llllIllllllIIll;
        try {
            llllIllllllIIll.values = (Enum[])llllIlllllIlIlI.getClass().getMethod("values", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException llllIllllllIlIl) {
            llllIllllllIlIl.printStackTrace();
        }
        llllIllllllIIll.suggestions = new ArrayList<String>(llllIllllllIIll.values.length);
        for (T llllIllllllIlII : llllIllllllIIll.values) {
            llllIllllllIIll.suggestions.add(((Enum)llllIllllllIlII).toString());
        }
    }

    @Override
    protected boolean isValueValid(T llllIllllIlIIll) {
        return true;
    }

    @Override
    protected T parseImpl(String llllIllllIllIll) {
        EnumSetting llllIllllIlllII;
        for (T llllIllllIlllIl : llllIllllIlllII.values) {
            if (!llllIllllIllIll.equalsIgnoreCase(((Enum)llllIllllIlllIl).toString())) continue;
            return llllIllllIlllIl;
        }
        return null;
    }

    @Override
    public T fromTag(NbtCompound llllIllllIIIlII) {
        EnumSetting llllIllllIIIlIl;
        llllIllllIIIlIl.parse(llllIllllIIIlII.getString("value"));
        return (T)((Enum)llllIllllIIIlIl.get());
    }

    @Override
    public NbtCompound toTag() {
        EnumSetting llllIllllIIllIl;
        NbtCompound llllIllllIIllII = llllIllllIIllIl.saveGeneral();
        llllIllllIIllII.putString("value", ((Enum)llllIllllIIllIl.get()).toString());
        return llllIllllIIllII;
    }

    @Override
    public List<String> getSuggestions() {
        EnumSetting llllIllllIlIIIl;
        return llllIllllIlIIIl.suggestions;
    }

    public static class Builder<T extends Enum<?>> {
        protected /* synthetic */ Consumer<Setting<T>> onModuleActivated;
        protected /* synthetic */ String name;
        protected /* synthetic */ String description;
        protected /* synthetic */ T defaultValue;
        protected /* synthetic */ Consumer<T> onChanged;

        public Builder() {
            Builder lllllllllllllllllIIIlIIIllIllIll;
            lllllllllllllllllIIIlIIIllIllIll.name = "undefined";
            lllllllllllllllllIIIlIIIllIllIll.description = "";
        }

        public Builder<T> description(String lllllllllllllllllIIIlIIIllIIlllI) {
            Builder lllllllllllllllllIIIlIIIllIIllll;
            lllllllllllllllllIIIlIIIllIIllll.description = lllllllllllllllllIIIlIIIllIIlllI;
            return lllllllllllllllllIIIlIIIllIIllll;
        }

        public Builder<T> onChanged(Consumer<T> lllllllllllllllllIIIlIIIllIIIIlI) {
            Builder lllllllllllllllllIIIlIIIllIIIIll;
            lllllllllllllllllIIIlIIIllIIIIll.onChanged = lllllllllllllllllIIIlIIIllIIIIlI;
            return lllllllllllllllllIIIlIIIllIIIIll;
        }

        public Builder<T> name(String lllllllllllllllllIIIlIIIllIlIllI) {
            Builder lllllllllllllllllIIIlIIIllIlIlIl;
            lllllllllllllllllIIIlIIIllIlIlIl.name = lllllllllllllllllIIIlIIIllIlIllI;
            return lllllllllllllllllIIIlIIIllIlIlIl;
        }

        public Builder<T> defaultValue(T lllllllllllllllllIIIlIIIllIIlIlI) {
            Builder lllllllllllllllllIIIlIIIllIIlIll;
            lllllllllllllllllIIIlIIIllIIlIll.defaultValue = lllllllllllllllllIIIlIIIllIIlIlI;
            return lllllllllllllllllIIIlIIIllIIlIll;
        }

        public EnumSetting<T> build() {
            Builder lllllllllllllllllIIIlIIIlIlllIIl;
            return new EnumSetting<T>(lllllllllllllllllIIIlIIIlIlllIIl.name, lllllllllllllllllIIIlIIIlIlllIIl.description, lllllllllllllllllIIIlIIIlIlllIIl.defaultValue, lllllllllllllllllIIIlIIIlIlllIIl.onChanged, lllllllllllllllllIIIlIIIlIlllIIl.onModuleActivated);
        }

        public Builder<T> onModuleActivated(Consumer<Setting<T>> lllllllllllllllllIIIlIIIlIlllllI) {
            Builder lllllllllllllllllIIIlIIIlIllllll;
            lllllllllllllllllIIIlIIIlIllllll.onModuleActivated = lllllllllllllllllIIIlIIIlIlllllI;
            return lllllllllllllllllIIIlIIIlIllllll;
        }
    }
}

