/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.effect.StatusEffectInstance
 *  net.minecraft.entity.effect.StatusEffects
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class SpeedMine
extends Module {
    public final /* synthetic */ Setting<Double> modifier;
    private final /* synthetic */ SettingGroup sgGeneral;
    public final /* synthetic */ Setting<Mode> mode;

    public SpeedMine() {
        super(Categories.Player, "speed-mine", "Allows you to quickly mine blocks.");
        SpeedMine lIlIllllllIIllI;
        lIlIllllllIIllI.sgGeneral = lIlIllllllIIllI.settings.getDefaultGroup();
        lIlIllllllIIllI.mode = lIlIllllllIIllI.sgGeneral.add(new EnumSetting.Builder().name("mode").defaultValue(Mode.Normal).build());
        lIlIllllllIIllI.modifier = lIlIllllllIIllI.sgGeneral.add(new DoubleSetting.Builder().name("modifier").description("Mining speed modifier. An additional value of 0.2 is equivalent to one haste level (1.2 = haste 1).").defaultValue(1.4).min(0.0).sliderMin(1.0).sliderMax(10.0).build());
    }

    @EventHandler
    public void onTick(TickEvent.Post lIlIlllllIlllIl) {
        SpeedMine lIlIlllllIllIll;
        Mode lIlIlllllIlllII = lIlIlllllIllIll.mode.get();
        if (lIlIlllllIlllII == Mode.Haste1 || lIlIlllllIlllII == Mode.Haste2) {
            int lIlIlllllIlllll;
            int n = lIlIlllllIlllll = lIlIlllllIlllII == Mode.Haste2 ? 1 : 0;
            if (lIlIlllllIllIll.mc.player.hasStatusEffect(StatusEffects.HASTE)) {
                StatusEffectInstance lIlIllllllIIIII = lIlIlllllIllIll.mc.player.getStatusEffect(StatusEffects.HASTE);
                ((StatusEffectInstanceAccessor)lIlIllllllIIIII).setAmplifier(lIlIlllllIlllll);
                if (lIlIllllllIIIII.getDuration() < 20) {
                    ((StatusEffectInstanceAccessor)lIlIllllllIIIII).setDuration(20);
                }
            } else {
                lIlIlllllIllIll.mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20, lIlIlllllIlllll, false, false, false));
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Haste2;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Normal;
        public static final /* synthetic */ /* enum */ Mode Haste1;

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Normal, Haste1, Haste2};
        }

        public static Mode valueOf(String lIIlIllllllllIl) {
            return Enum.valueOf(Mode.class, lIIlIllllllllIl);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private Mode() {
            Mode lIIlIlllllllIII;
        }

        static {
            Normal = new Mode();
            Haste1 = new Mode();
            Haste2 = new Mode();
            $VALUES = Mode.$values();
        }
    }
}

