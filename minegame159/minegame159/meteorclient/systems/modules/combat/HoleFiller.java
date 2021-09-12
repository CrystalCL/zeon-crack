/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.world.BlockIterator;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1747;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1922;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_2680;

public class HoleFiller
extends Module {
    private final Setting<Integer> verticalRadius;
    private final Setting<Integer> horizontalRadius;
    private final Setting<Boolean> rotate;
    private final class_2338.class_2339 blockPos;
    private final Setting<Integer> placeDelay;
    private final Setting<PlaceMode> mode;
    private final SettingGroup sgGeneral;
    private int tickDelayLeft;

    @Override
    public void onActivate() {
        this.tickDelayLeft = 0;
    }

    private void lambda$onTick$0(int n, class_2338 class_23382, class_2680 class_26802) {
        if (!class_26802.method_26207().method_15800()) {
            return;
        }
        this.blockPos.method_10101((class_2382)class_23382);
        class_2248 class_22482 = this.mc.field_1687.method_8320((class_2338)this.add(0, -1, 0)).method_26204();
        if (class_22482 != class_2246.field_9987 && class_22482 != class_2246.field_10540) {
            return;
        }
        class_2248 class_22483 = this.mc.field_1687.method_8320((class_2338)this.add(0, 1, 1)).method_26204();
        if (class_22483 != class_2246.field_9987 && class_22483 != class_2246.field_10540) {
            return;
        }
        class_2248 class_22484 = this.mc.field_1687.method_8320((class_2338)this.add(0, 0, -2)).method_26204();
        if (class_22484 != class_2246.field_9987 && class_22484 != class_2246.field_10540) {
            return;
        }
        class_2248 class_22485 = this.mc.field_1687.method_8320((class_2338)this.add(1, 0, 1)).method_26204();
        if (class_22485 != class_2246.field_9987 && class_22485 != class_2246.field_10540) {
            return;
        }
        class_2248 class_22486 = this.mc.field_1687.method_8320((class_2338)this.add(-2, 0, 0)).method_26204();
        if (class_22486 != class_2246.field_9987 && class_22486 != class_2246.field_10540) {
            return;
        }
        this.add(1, 0, 0);
        if (BlockUtils.place((class_2338)this.blockPos, class_1268.field_5808, n, this.rotate.get(), 0, true)) {
            BlockIterator.disableCurrent();
        }
    }

    public HoleFiller() {
        super(Categories.Combat, "hole-filler", "Fills holes with specified blocks.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.horizontalRadius = this.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for holes.").defaultValue(4).min(0).sliderMax(6).build());
        this.verticalRadius = this.sgGeneral.add(new IntSetting.Builder().name("vertical-radius").description("Vertical radius in which to search for holes.").defaultValue(4).min(0).sliderMax(6).build());
        this.placeDelay = this.sgGeneral.add(new IntSetting.Builder().name("place-delay").description("The delay in ticks in between placement.").defaultValue(1).min(0).sliderMax(10).build());
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("block").description("The blocks you use to fill holes with.").defaultValue(PlaceMode.Obsidian).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the holes being filled.").defaultValue(true).build());
        this.blockPos = new class_2338.class_2339();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = this.findSlot();
        if (n != -1 && this.tickDelayLeft <= 0) {
            this.tickDelayLeft = this.placeDelay.get();
            BlockIterator.register(this.horizontalRadius.get(), this.verticalRadius.get(), (arg_0, arg_1) -> this.lambda$onTick$0(n, arg_0, arg_1));
        }
        --this.tickDelayLeft;
    }

    private class_2338.class_2339 add(int n, int n2, int n3) {
        this.blockPos.method_20787(this.blockPos.method_10263() + n);
        this.blockPos.method_10099(this.blockPos.method_10264() + n2);
        this.blockPos.method_20788(this.blockPos.method_10260() + n3);
        return this.blockPos;
    }

    private int findSlot() {
        block6: for (int i = 0; i < 9; ++i) {
            class_1799 class_17992 = this.mc.field_1724.field_7514.method_5438(i);
            switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$HoleFiller$PlaceMode[this.mode.get().ordinal()]) {
                case 1: {
                    if (class_17992.method_7909() != class_1802.field_8281 && class_17992.method_7909() != class_1802.field_22421) continue block6;
                    return i;
                }
                case 2: {
                    if (class_17992.method_7909() != class_1802.field_8786) continue block6;
                    return i;
                }
                case 3: {
                    if (class_17992.method_7909() != class_1802.field_8786 && class_17992.method_7909() != class_1802.field_8281 && class_17992.method_7909() != class_1802.field_22421) continue block6;
                    return i;
                }
                case 4: {
                    if (!(class_17992.method_7909() instanceof class_1747) || !((class_1747)class_17992.method_7909()).method_7711().method_9564().method_26234((class_1922)this.mc.field_1687, (class_2338)this.blockPos)) continue block6;
                    return i;
                }
            }
            if (!false) continue;
            return 0;
        }
        return -1;
    }

    public static final class PlaceMode
    extends Enum<PlaceMode> {
        public static final /* enum */ PlaceMode Cobweb;
        public static final /* enum */ PlaceMode Any;
        private static final PlaceMode[] $VALUES;
        public static final /* enum */ PlaceMode Both;
        public static final /* enum */ PlaceMode Obsidian;

        public static PlaceMode[] values() {
            return (PlaceMode[])$VALUES.clone();
        }

        private static PlaceMode[] $values() {
            return new PlaceMode[]{Obsidian, Cobweb, Both, Any};
        }

        static {
            Obsidian = new PlaceMode();
            Cobweb = new PlaceMode();
            Both = new PlaceMode();
            Any = new PlaceMode();
            $VALUES = PlaceMode.$values();
        }

        public static PlaceMode valueOf(String string) {
            return Enum.valueOf(PlaceMode.class, string);
        }
    }
}

