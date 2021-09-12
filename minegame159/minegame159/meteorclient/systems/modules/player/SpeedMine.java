/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.StatusEffectInstanceAccessor;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.class_1293;
import net.minecraft.class_1294;

public class SpeedMine
extends Module {
    private final SettingGroup sgGeneral;
    public final Setting<Mode> mode;
    public final Setting<Double> modifier;

    @EventHandler
    public void onTick(TickEvent.Post post) {
        Mode mode = this.mode.get();
        if (mode == Mode.Haste1 || mode == Mode.Haste2) {
            int n;
            int n2 = n = mode == Mode.Haste2 ? 1 : 0;
            if (this.mc.field_1724.method_6059(class_1294.field_5917)) {
                class_1293 class_12932 = this.mc.field_1724.method_6112(class_1294.field_5917);
                ((StatusEffectInstanceAccessor)class_12932).setAmplifier(n);
                if (class_12932.method_5584() < 20) {
                    ((StatusEffectInstanceAccessor)class_12932).setDuration(20);
                }
            } else {
                this.mc.field_1724.method_6092(new class_1293(class_1294.field_5917, 20, n, false, false, false));
            }
        }
    }

    public SpeedMine() {
        super(Categories.Player, "speed-mine", "Allows you to quickly mine blocks.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").defaultValue(Mode.Normal).build());
        this.modifier = this.sgGeneral.add(new DoubleSetting.Builder().name("modifier").description("Mining speed modifier. An additional value of 0.2 is equivalent to one haste level (1.2 = haste 1).").defaultValue(1.4).min(0.0).sliderMin(1.0).sliderMax(10.0).build());
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Haste1;
        public static final /* enum */ Mode Haste2;
        public static final /* enum */ Mode Normal;

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Normal = new Mode();
            Haste1 = new Mode();
            Haste2 = new Mode();
            $VALUES = Mode.$values();
        }

        private static Mode[] $values() {
            return new Mode[]{Normal, Haste1, Haste2};
        }
    }
}

