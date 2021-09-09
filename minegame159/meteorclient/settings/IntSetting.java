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

public class IntSetting
extends Setting<Integer> {
    private final /* synthetic */ Integer sliderMax;
    public final /* synthetic */ Integer min;
    public final /* synthetic */ Integer max;
    private final /* synthetic */ Integer sliderMin;

    @Override
    protected boolean isValueValid(Integer llllllllllllllllllIIlIIlIIIIlIll) {
        IntSetting llllllllllllllllllIIlIIlIIIIlllI;
        return !(llllllllllllllllllIIlIIlIIIIlllI.min != null && llllllllllllllllllIIlIIlIIIIlIll < llllllllllllllllllIIlIIlIIIIlllI.min || llllllllllllllllllIIlIIlIIIIlllI.max != null && llllllllllllllllllIIlIIlIIIIlIll > llllllllllllllllllIIlIIlIIIIlllI.max);
    }

    @Override
    public Integer fromTag(NbtCompound llllllllllllllllllIIlIIIlllllIIl) {
        IntSetting llllllllllllllllllIIlIIIlllllIlI;
        llllllllllllllllllIIlIIIlllllIlI.set(llllllllllllllllllIIlIIIlllllIIl.getInt("value"));
        return (Integer)llllllllllllllllllIIlIIIlllllIlI.get();
    }

    public int getSliderMin() {
        IntSetting llllllllllllllllllIIlIIlIIIIlIII;
        return llllllllllllllllllIIlIIlIIIIlIII.sliderMin != null ? llllllllllllllllllIIlIIlIIIIlIII.sliderMin : 0;
    }

    private IntSetting(String llllllllllllllllllIIlIIlIIlIIIII, String llllllllllllllllllIIlIIlIIlIlIIl, Integer llllllllllllllllllIIlIIlIIIllllI, Consumer<Integer> llllllllllllllllllIIlIIlIIIlllIl, Consumer<Setting<Integer>> llllllllllllllllllIIlIIlIIlIIllI, Integer llllllllllllllllllIIlIIlIIIllIll, Integer llllllllllllllllllIIlIIlIIlIIlII, Integer llllllllllllllllllIIlIIlIIIllIIl, Integer llllllllllllllllllIIlIIlIIIllIII) {
        super(llllllllllllllllllIIlIIlIIlIIIII, llllllllllllllllllIIlIIlIIlIlIIl, llllllllllllllllllIIlIIlIIIllllI, llllllllllllllllllIIlIIlIIIlllIl, llllllllllllllllllIIlIIlIIlIIllI);
        IntSetting llllllllllllllllllIIlIIlIIlIlIll;
        llllllllllllllllllIIlIIlIIlIlIll.min = llllllllllllllllllIIlIIlIIIllIll;
        llllllllllllllllllIIlIIlIIlIlIll.max = llllllllllllllllllIIlIIlIIlIIlII;
        llllllllllllllllllIIlIIlIIlIlIll.sliderMin = llllllllllllllllllIIlIIlIIIllIIl;
        llllllllllllllllllIIlIIlIIlIlIll.sliderMax = llllllllllllllllllIIlIIlIIIllIII;
    }

    @Override
    public NbtCompound toTag() {
        IntSetting llllllllllllllllllIIlIIlIIIIIIII;
        NbtCompound llllllllllllllllllIIlIIlIIIIIIIl = llllllllllllllllllIIlIIlIIIIIIII.saveGeneral();
        llllllllllllllllllIIlIIlIIIIIIIl.putInt("value", ((Integer)llllllllllllllllllIIlIIlIIIIIIII.get()).intValue());
        return llllllllllllllllllIIlIIlIIIIIIIl;
    }

    @Override
    protected Integer parseImpl(String llllllllllllllllllIIlIIlIIIlIIlI) {
        try {
            return Integer.parseInt(llllllllllllllllllIIlIIlIIIlIIlI.trim());
        }
        catch (NumberFormatException llllllllllllllllllIIlIIlIIIlIlIl) {
            return null;
        }
    }

    public int getSliderMax() {
        IntSetting llllllllllllllllllIIlIIlIIIIIllI;
        return llllllllllllllllllIIlIIlIIIIIllI.sliderMax != null ? llllllllllllllllllIIlIIlIIIIIllI.sliderMax : 10;
    }

    public static class Builder {
        private /* synthetic */ Integer min;
        private /* synthetic */ Consumer<Integer> onChanged;
        private /* synthetic */ Integer sliderMin;
        private /* synthetic */ Integer sliderMax;
        private /* synthetic */ Integer max;
        private /* synthetic */ Consumer<Setting<Integer>> onModuleActivated;
        private /* synthetic */ String description;
        private /* synthetic */ Integer defaultValue;
        private /* synthetic */ String name;

        public Builder onModuleActivated(Consumer<Setting<Integer>> lllllllllllllllllllllIlllIlllIll) {
            Builder lllllllllllllllllllllIlllIlllIlI;
            lllllllllllllllllllllIlllIlllIlI.onModuleActivated = lllllllllllllllllllllIlllIlllIll;
            return lllllllllllllllllllllIlllIlllIlI;
        }

        public Builder max(int lllllllllllllllllllllIlllIlIllIl) {
            Builder lllllllllllllllllllllIlllIlIlllI;
            lllllllllllllllllllllIlllIlIlllI.max = lllllllllllllllllllllIlllIlIllIl;
            return lllllllllllllllllllllIlllIlIlllI;
        }

        public Builder() {
            Builder lllllllllllllllllllllIllllIlIlll;
            lllllllllllllllllllllIllllIlIlll.name = "undefined";
            lllllllllllllllllllllIllllIlIlll.description = "";
        }

        public Builder description(String lllllllllllllllllllllIllllIIllIl) {
            Builder lllllllllllllllllllllIllllIIllII;
            lllllllllllllllllllllIllllIIllII.description = lllllllllllllllllllllIllllIIllIl;
            return lllllllllllllllllllllIllllIIllII;
        }

        public Builder defaultValue(int lllllllllllllllllllllIllllIIIlIl) {
            Builder lllllllllllllllllllllIllllIIIllI;
            lllllllllllllllllllllIllllIIIllI.defaultValue = lllllllllllllllllllllIllllIIIlIl;
            return lllllllllllllllllllllIllllIIIllI;
        }

        public Builder name(String lllllllllllllllllllllIllllIlIIIl) {
            Builder lllllllllllllllllllllIllllIlIlII;
            lllllllllllllllllllllIllllIlIlII.name = lllllllllllllllllllllIllllIlIIIl;
            return lllllllllllllllllllllIllllIlIlII;
        }

        public Builder onChanged(Consumer<Integer> lllllllllllllllllllllIlllIllllll) {
            Builder lllllllllllllllllllllIllllIIIIII;
            lllllllllllllllllllllIllllIIIIII.onChanged = lllllllllllllllllllllIlllIllllll;
            return lllllllllllllllllllllIllllIIIIII;
        }

        public Builder min(int lllllllllllllllllllllIlllIllIIll) {
            Builder lllllllllllllllllllllIlllIllIllI;
            lllllllllllllllllllllIlllIllIllI.min = lllllllllllllllllllllIlllIllIIll;
            return lllllllllllllllllllllIlllIllIllI;
        }

        public Builder sliderMax(int lllllllllllllllllllllIlllIlIIIIl) {
            Builder lllllllllllllllllllllIlllIlIIIlI;
            lllllllllllllllllllllIlllIlIIIlI.sliderMax = lllllllllllllllllllllIlllIlIIIIl;
            return lllllllllllllllllllllIlllIlIIIlI;
        }

        public IntSetting build() {
            Builder lllllllllllllllllllllIlllIIllllI;
            return new IntSetting(lllllllllllllllllllllIlllIIllllI.name, lllllllllllllllllllllIlllIIllllI.description, lllllllllllllllllllllIlllIIllllI.defaultValue, lllllllllllllllllllllIlllIIllllI.onChanged, lllllllllllllllllllllIlllIIllllI.onModuleActivated, lllllllllllllllllllllIlllIIllllI.min, lllllllllllllllllllllIlllIIllllI.max, lllllllllllllllllllllIlllIIllllI.sliderMin, lllllllllllllllllllllIlllIIllllI.sliderMax);
        }

        public Builder sliderMin(int lllllllllllllllllllllIlllIlIlIIl) {
            Builder lllllllllllllllllllllIlllIlIlIlI;
            lllllllllllllllllllllIlllIlIlIlI.sliderMin = lllllllllllllllllllllIlllIlIlIIl;
            return lllllllllllllllllllllIlllIlIlIlI;
        }
    }
}

