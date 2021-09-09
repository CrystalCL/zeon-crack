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

public class DoubleSetting
extends Setting<Double> {
    public final /* synthetic */ boolean onSliderRelease;
    public final /* synthetic */ Double min;
    public final /* synthetic */ Double max;
    private final /* synthetic */ Double sliderMin;
    public final /* synthetic */ int decimalPlaces;
    private final /* synthetic */ Double sliderMax;

    public double getSliderMax() {
        DoubleSetting lIlIllIIlIIIIlI;
        return lIlIllIIlIIIIlI.sliderMax != null ? lIlIllIIlIIIIlI.sliderMax : 10.0;
    }

    @Override
    public Double fromTag(NbtCompound lIlIllIIIllIllI) {
        DoubleSetting lIlIllIIIllIlll;
        lIlIllIIIllIlll.set(lIlIllIIIllIllI.getDouble("value"));
        return (Double)lIlIllIIIllIlll.get();
    }

    @Override
    protected boolean isValueValid(Double lIlIllIIlIIlIII) {
        DoubleSetting lIlIllIIlIIlIll;
        return (lIlIllIIlIIlIll.min == null || lIlIllIIlIIlIII >= lIlIllIIlIIlIll.min) && (lIlIllIIlIIlIll.max == null || lIlIllIIlIIlIII <= lIlIllIIlIIlIll.max);
    }

    private DoubleSetting(String lIlIllIIlIlllll, String lIlIllIIlIllllI, Double lIlIllIIllIlIIl, Consumer<Double> lIlIllIIllIlIII, Consumer<Setting<Double>> lIlIllIIlIllIll, Double lIlIllIIllIIllI, Double lIlIllIIllIIlIl, Double lIlIllIIllIIlII, Double lIlIllIIlIlIlll, boolean lIlIllIIlIlIllI, int lIlIllIIllIIIIl) {
        super(lIlIllIIlIlllll, lIlIllIIlIllllI, lIlIllIIllIlIIl, lIlIllIIllIlIII, lIlIllIIlIllIll);
        DoubleSetting lIlIllIIllIIIII;
        lIlIllIIllIIIII.min = lIlIllIIllIIllI;
        lIlIllIIllIIIII.max = lIlIllIIllIIlIl;
        lIlIllIIllIIIII.sliderMin = lIlIllIIllIIlII;
        lIlIllIIllIIIII.sliderMax = lIlIllIIlIlIlll;
        lIlIllIIllIIIII.decimalPlaces = lIlIllIIllIIIIl;
        lIlIllIIllIIIII.onSliderRelease = lIlIllIIlIlIllI;
    }

    @Override
    public NbtCompound toTag() {
        DoubleSetting lIlIllIIIllllIl;
        NbtCompound lIlIllIIIlllllI = lIlIllIIIllllIl.saveGeneral();
        lIlIllIIIlllllI.putDouble("value", ((Double)lIlIllIIIllllIl.get()).doubleValue());
        return lIlIllIIIlllllI;
    }

    @Override
    protected Double parseImpl(String lIlIllIIlIIllll) {
        try {
            return Double.parseDouble(lIlIllIIlIIllll.trim());
        }
        catch (NumberFormatException lIlIllIIlIlIIlI) {
            return null;
        }
    }

    public double getSliderMin() {
        DoubleSetting lIlIllIIlIIIllI;
        return lIlIllIIlIIIllI.sliderMin != null ? lIlIllIIlIIIllI.sliderMin : 0.0;
    }

    public static class Builder {
        private /* synthetic */ Double defaultValue;
        private /* synthetic */ String description;
        private /* synthetic */ int decimalPlaces;
        private /* synthetic */ Double sliderMin;
        private /* synthetic */ Double sliderMax;
        private /* synthetic */ boolean onSliderRelease;
        private /* synthetic */ Double min;
        private /* synthetic */ Consumer<Double> onChanged;
        private /* synthetic */ Consumer<Setting<Double>> onModuleActivated;
        private /* synthetic */ String name;
        private /* synthetic */ Double max;

        public Builder onSliderRelease() {
            Builder llllllllllllllllllllIllIIllllIII;
            llllllllllllllllllllIllIIllllIII.onSliderRelease = true;
            return llllllllllllllllllllIllIIllllIII;
        }

        public Builder() {
            Builder llllllllllllllllllllIllIlIllIIIl;
            llllllllllllllllllllIllIlIllIIIl.name = "undefined";
            llllllllllllllllllllIllIlIllIIIl.description = "";
            llllllllllllllllllllIllIlIllIIIl.decimalPlaces = 3;
        }

        public Builder sliderMin(double llllllllllllllllllllIllIlIIIIIII) {
            Builder llllllllllllllllllllIllIlIIIIIll;
            llllllllllllllllllllIllIlIIIIIll.sliderMin = llllllllllllllllllllIllIlIIIIIII;
            return llllllllllllllllllllIllIlIIIIIll;
        }

        public Builder description(String llllllllllllllllllllIllIlIlIIlII) {
            Builder llllllllllllllllllllIllIlIlIIlIl;
            llllllllllllllllllllIllIlIlIIlIl.description = llllllllllllllllllllIllIlIlIIlII;
            return llllllllllllllllllllIllIlIlIIlIl;
        }

        public Builder onModuleActivated(Consumer<Setting<Double>> llllllllllllllllllllIllIlIIlIlII) {
            Builder llllllllllllllllllllIllIlIIlIIll;
            llllllllllllllllllllIllIlIIlIIll.onModuleActivated = llllllllllllllllllllIllIlIIlIlII;
            return llllllllllllllllllllIllIlIIlIIll;
        }

        public Builder sliderMax(double llllllllllllllllllllIllIIlllllII) {
            Builder llllllllllllllllllllIllIIllllIll;
            llllllllllllllllllllIllIIllllIll.sliderMax = llllllllllllllllllllIllIIlllllII;
            return llllllllllllllllllllIllIIllllIll;
        }

        public Builder onChanged(Consumer<Double> llllllllllllllllllllIllIlIIllIII) {
            Builder llllllllllllllllllllIllIlIIllIll;
            llllllllllllllllllllIllIlIIllIll.onChanged = llllllllllllllllllllIllIlIIllIII;
            return llllllllllllllllllllIllIlIIllIll;
        }

        public Builder min(double llllllllllllllllllllIllIlIIIlllI) {
            Builder llllllllllllllllllllIllIlIIIllIl;
            llllllllllllllllllllIllIlIIIllIl.min = llllllllllllllllllllIllIlIIIlllI;
            return llllllllllllllllllllIllIlIIIllIl;
        }

        public DoubleSetting build() {
            Builder llllllllllllllllllllIllIIllIllll;
            return new DoubleSetting(llllllllllllllllllllIllIIllIllll.name, llllllllllllllllllllIllIIllIllll.description, llllllllllllllllllllIllIIllIllll.defaultValue, llllllllllllllllllllIllIIllIllll.onChanged, llllllllllllllllllllIllIIllIllll.onModuleActivated, llllllllllllllllllllIllIIllIllll.min, llllllllllllllllllllIllIIllIllll.max, llllllllllllllllllllIllIIllIllll.sliderMin, llllllllllllllllllllIllIIllIllll.sliderMax, llllllllllllllllllllIllIIllIllll.onSliderRelease, llllllllllllllllllllIllIIllIllll.decimalPlaces);
        }

        public Builder defaultValue(double llllllllllllllllllllIllIlIIllllI) {
            Builder llllllllllllllllllllIllIlIlIIIIl;
            llllllllllllllllllllIllIlIlIIIIl.defaultValue = llllllllllllllllllllIllIlIIllllI;
            return llllllllllllllllllllIllIlIlIIIIl;
        }

        public Builder decimalPlaces(int llllllllllllllllllllIllIIlllIIll) {
            Builder llllllllllllllllllllIllIIlllIlII;
            llllllllllllllllllllIllIIlllIlII.decimalPlaces = llllllllllllllllllllIllIIlllIIll;
            return llllllllllllllllllllIllIIlllIlII;
        }

        public Builder name(String llllllllllllllllllllIllIlIlIllII) {
            Builder llllllllllllllllllllIllIlIlIllIl;
            llllllllllllllllllllIllIlIlIllIl.name = llllllllllllllllllllIllIlIlIllII;
            return llllllllllllllllllllIllIlIlIllIl;
        }

        public Builder max(double llllllllllllllllllllIllIlIIIlIII) {
            Builder llllllllllllllllllllIllIlIIIlIIl;
            llllllllllllllllllllIllIlIIIlIIl.max = llllllllllllllllllllIllIlIIIlIII;
            return llllllllllllllllllllIllIlIIIlIIl;
        }
    }
}

