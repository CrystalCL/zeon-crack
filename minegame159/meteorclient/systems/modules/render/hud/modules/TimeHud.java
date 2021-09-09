/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class TimeHud
extends DoubleTextHudElement {
    private final /* synthetic */ Setting<TimeType> timeType;
    private final /* synthetic */ SettingGroup sgGeneral;

    public TimeHud(HUD lllllllllllllllllIlIIIlllllIlIll) {
        super(lllllllllllllllllIlIIIlllllIlIll, "time", "Displays the world time.", "Time: ");
        TimeHud lllllllllllllllllIlIIIlllllIllII;
        lllllllllllllllllIlIIIlllllIllII.sgGeneral = lllllllllllllllllIlIIIlllllIllII.settings.getDefaultGroup();
        lllllllllllllllllIlIIIlllllIllII.timeType = lllllllllllllllllIlIIIlllllIllII.sgGeneral.add(new EnumSetting.Builder().name("use-time").description("Which time to use.").defaultValue(TimeType.World).build());
    }

    @Override
    protected String getRight() {
        TimeHud lllllllllllllllllIlIIIlllllIIIlI;
        String lllllllllllllllllIlIIIlllllIIIll = "00:00";
        switch (lllllllllllllllllIlIIIlllllIIIlI.timeType.get()) {
            case World: {
                if (lllllllllllllllllIlIIIlllllIIIlI.isInEditor()) {
                    return lllllllllllllllllIlIIIlllllIIIll;
                }
                int lllllllllllllllllIlIIIlllllIIlIl = (int)(lllllllllllllllllIlIIIlllllIIIlI.mc.world.getTimeOfDay() % 24000L);
                if ((lllllllllllllllllIlIIIlllllIIlIl += 6000) > 24000) {
                    lllllllllllllllllIlIIIlllllIIlIl -= 24000;
                }
                lllllllllllllllllIlIIIlllllIIIll = String.format("%02d:%02d", lllllllllllllllllIlIIIlllllIIlIl / 1000, (int)((double)(lllllllllllllllllIlIIIlllllIIlIl % 1000) / 1000.0 * 60.0));
                break;
            }
            case Local: {
                lllllllllllllllllIlIIIlllllIIIll = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            }
        }
        return lllllllllllllllllIlIIIlllllIIIll;
    }

    public static final class TimeType
    extends Enum<TimeType> {
        public static final /* synthetic */ /* enum */ TimeType Local;
        private static final /* synthetic */ TimeType[] $VALUES;
        public static final /* synthetic */ /* enum */ TimeType World;

        public static TimeType[] values() {
            return (TimeType[])$VALUES.clone();
        }

        private TimeType() {
            TimeType lllllllllllllllllllIllllIllIlIll;
        }

        static {
            World = new TimeType();
            Local = new TimeType();
            $VALUES = TimeType.$values();
        }

        public static TimeType valueOf(String lllllllllllllllllllIllllIlllIIII) {
            return Enum.valueOf(TimeType.class, lllllllllllllllllllIllllIlllIIII);
        }

        private static /* synthetic */ TimeType[] $values() {
            return new TimeType[]{World, Local};
        }
    }
}

