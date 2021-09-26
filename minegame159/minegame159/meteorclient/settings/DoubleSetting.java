/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.nbt.NbtCompound;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoubleSetting
extends Setting<Double> {
    private final Double sliderMin;
    public final Double max;
    public final boolean onSliderRelease;
    private final Double sliderMax;
    public final int decimalPlaces;
    public final Double min;

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtCompound2.putDouble("value", ((Double)this.get()).doubleValue());
        return NbtCompound2;
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((Double)object);
    }

    @Override
    protected boolean isValueValid(Double d) {
        return (this.min == null || d >= this.min) && (this.max == null || d <= this.max);
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    public double getSliderMax() {
        return this.sliderMax != null ? this.sliderMax : 10.0;
    }

    @Override
    public Double fromTag(NbtCompound NbtCompound2) {
        this.set(NbtCompound2.getDouble("value"));
        return (Double)this.get();
    }

    @Override
    protected Double parseImpl(String string) {
        try {
            return Double.parseDouble(string.trim());
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    DoubleSetting(String string, String string2, Double d, Consumer consumer, Consumer consumer2, Double d2, Double d3, Double d4, Double d5, boolean bl, int n, 1 var12_12) {
        this(string, string2, d, consumer, consumer2, d2, d3, d4, d5, bl, n);
    }

    private DoubleSetting(String string, String string2, Double d, Consumer<Double> consumer, Consumer<Setting<Double>> consumer2, Double d2, Double d3, Double d4, Double d5, boolean bl, int n) {
        super(string, string2, d, consumer, consumer2);
        this.min = d2;
        this.max = d3;
        this.sliderMin = d4;
        this.sliderMax = d5;
        this.decimalPlaces = n;
        this.onSliderRelease = bl;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public double getSliderMin() {
        return this.sliderMin != null ? this.sliderMin : 0.0;
    }

    public static class Builder {
        private Consumer<Setting<Double>> onModuleActivated;
        private Double sliderMin;
        private Double defaultValue;
        private Double min;
        private Double max;
        private String description = "";
        private boolean onSliderRelease;
        private String name = "undefined";
        private int decimalPlaces = 3;
        private Consumer<Double> onChanged;
        private Double sliderMax;

        public Builder min(double d) {
            this.min = d;
            return this;
        }

        public Builder sliderMin(double d) {
            this.sliderMin = d;
            return this;
        }

        public Builder decimalPlaces(int n) {
            this.decimalPlaces = n;
            return this;
        }

        public Builder max(double d) {
            this.max = d;
            return this;
        }

        public Builder defaultValue(double d) {
            this.defaultValue = d;
            return this;
        }

        public Builder onChanged(Consumer<Double> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder onSliderRelease() {
            this.onSliderRelease = true;
            return this;
        }

        public Builder sliderMax(double d) {
            this.sliderMax = d;
            return this;
        }

        public DoubleSetting build() {
            return new DoubleSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.min, this.max, this.sliderMin, this.sliderMax, this.onSliderRelease, this.decimalPlaces, null);
        }

        public Builder onModuleActivated(Consumer<Setting<Double>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }
    }
}

