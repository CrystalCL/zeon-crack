/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.Flight;

public class AntiVoid
extends Module {
    private /* synthetic */ boolean wasFlightEnabled;
    private final /* synthetic */ SettingGroup sgDefault;
    private /* synthetic */ boolean hasRun;
    private final /* synthetic */ Setting<Mode> mode;

    @EventHandler
    public void onPreTick(TickEvent.Pre lllllllllllllllllIllIIIllIIlIlll) {
        AntiVoid lllllllllllllllllIllIIIllIIllIII;
        if (lllllllllllllllllIllIIIllIIllIII.mc.player.getY() > 0.0 || lllllllllllllllllIllIIIllIIllIII.mc.player.getY() < -15.0) {
            if (lllllllllllllllllIllIIIllIIllIII.hasRun && lllllllllllllllllIllIIIllIIllIII.mode.get() == Mode.Flight && Modules.get().isActive(Flight.class)) {
                Modules.get().get(Flight.class).toggle();
                lllllllllllllllllIllIIIllIIllIII.hasRun = false;
            }
            return;
        }
        switch (lllllllllllllllllIllIIIllIIllIII.mode.get()) {
            case Flight: {
                if (!Modules.get().isActive(Flight.class)) {
                    Modules.get().get(Flight.class).toggle();
                }
                lllllllllllllllllIllIIIllIIllIII.hasRun = true;
                break;
            }
            case Jump: {
                lllllllllllllllllIllIIIllIIllIII.mc.player.jump();
            }
        }
    }

    @Override
    public void onDeactivate() {
        AntiVoid lllllllllllllllllIllIIIllIIllIlI;
        if (!lllllllllllllllllIllIIIllIIllIlI.wasFlightEnabled && lllllllllllllllllIllIIIllIIllIlI.mode.get() == Mode.Flight) {
            Modules.get().get(Flight.class).toggle();
        }
    }

    @Override
    public void onActivate() {
        AntiVoid lllllllllllllllllIllIIIllIIlllIl;
        if (lllllllllllllllllIllIIIllIIlllIl.mode.get() == Mode.Flight) {
            lllllllllllllllllIllIIIllIIlllIl.wasFlightEnabled = Modules.get().isActive(Flight.class);
        }
    }

    public AntiVoid() {
        super(Categories.Movement, "anti-void", "Attempts to prevent you from falling into the void.");
        AntiVoid lllllllllllllllllIllIIIllIlIIIIl;
        lllllllllllllllllIllIIIllIlIIIIl.sgDefault = lllllllllllllllllIllIIIllIlIIIIl.settings.getDefaultGroup();
        lllllllllllllllllIllIIIllIlIIIIl.mode = lllllllllllllllllIllIIIllIlIIIIl.sgDefault.add(new EnumSetting.Builder().name("mode").description("The method to prevent you from falling into the void.").defaultValue(Mode.Jump).onChanged(lllllllllllllllllIllIIIllIIlIIll -> {
            AntiVoid lllllllllllllllllIllIIIllIIlIIlI;
            lllllllllllllllllIllIIIllIIlIIlI.onActivate();
        }).build());
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Jump;
        public static final /* synthetic */ /* enum */ Mode Flight;
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Flight, Jump};
        }

        private Mode() {
            Mode llIllllIllIll;
        }

        static {
            Flight = new Mode();
            Jump = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String llIllllIlllll) {
            return Enum.valueOf(Mode.class, llIllllIlllll);
        }
    }
}

