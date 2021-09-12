/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.class_2487;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntSetting
extends Setting<Integer> {
    public final Integer min;
    public final Integer max;
    private final Integer sliderMin;
    private final Integer sliderMax;

    @Override
    protected boolean isValueValid(Integer n) {
        return !(this.min != null && n < this.min || this.max != null && n > this.max);
    }

    @Override
    protected Integer parseImpl(String string) {
        try {
            return Integer.parseInt(string.trim());
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }

    public int getSliderMax() {
        return this.sliderMax != null ? this.sliderMax : 10;
    }

    IntSetting(String string, String string2, Integer n, Consumer consumer, Consumer consumer2, Integer n2, Integer n3, Integer n4, Integer n5, 1 var10_10) {
        this(string, string2, n, consumer, consumer2, n2, n3, n4, n5);
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((Integer)object);
    }

    @Override
    public class_2487 toTag() {
        class_2487 class_24872 = this.saveGeneral();
        class_24872.method_10569("value", ((Integer)this.get()).intValue());
        return class_24872;
    }

    public int getSliderMin() {
        return this.sliderMin != null ? this.sliderMin : 0;
    }

    @Override
    public Integer fromTag(class_2487 class_24872) {
        this.set(class_24872.method_10550("value"));
        return (Integer)this.get();
    }

    private IntSetting(String string, String string2, Integer n, Consumer<Integer> consumer, Consumer<Setting<Integer>> consumer2, Integer n2, Integer n3, Integer n4, Integer n5) {
        super(string, string2, n, consumer, consumer2);
        this.min = n2;
        this.max = n3;
        this.sliderMin = n4;
        this.sliderMax = n5;
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    public static class Builder {
        private Integer min;
        private String description = "";
        private String name = "undefined";
        private Consumer<Integer> onChanged;
        private Integer max;
        private Integer sliderMin;
        private Integer defaultValue;
        private Consumer<Setting<Integer>> onModuleActivated;
        private Integer sliderMax;

        public Builder onChanged(Consumer<Integer> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<Integer>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder max(int n) {
            this.max = n;
            return this;
        }

        public Builder sliderMin(int n) {
            this.sliderMin = n;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public IntSetting build() {
            return new IntSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.min, this.max, this.sliderMin, this.sliderMax, null);
        }

        public Builder sliderMax(int n) {
            this.sliderMax = n;
            return this;
        }

        public Builder defaultValue(int n) {
            this.defaultValue = n;
            return this;
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder min(int n) {
            this.min = n;
            return this;
        }
    }
}

