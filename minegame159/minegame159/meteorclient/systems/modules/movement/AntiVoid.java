/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Mode> mode;
    private boolean wasFlightEnabled;
    private final SettingGroup sgDefault;
    private boolean hasRun;

    private void lambda$new$0(Mode mode) {
        this.onActivate();
    }

    @Override
    public void onDeactivate() {
        if (!this.wasFlightEnabled && this.mode.get() == Mode.Flight) {
            Modules.get().get(Flight.class).toggle();
        }
    }

    public AntiVoid() {
        super(Categories.Movement, "anti-void", "Attempts to prevent you from falling into the void.");
        this.sgDefault = this.settings.getDefaultGroup();
        this.mode = this.sgDefault.add(new EnumSetting.Builder().name("mode").description("The method to prevent you from falling into the void.").defaultValue(Mode.Jump).onChanged(this::lambda$new$0).build());
    }

    @Override
    public void onActivate() {
        if (this.mode.get() == Mode.Flight) {
            this.wasFlightEnabled = Modules.get().isActive(Flight.class);
        }
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre pre) {
        if (this.mc.player.getY() > 0.0 || this.mc.player.getY() < -15.0) {
            if (this.hasRun && this.mode.get() == Mode.Flight && Modules.get().isActive(Flight.class)) {
                Modules.get().get(Flight.class).toggle();
                this.hasRun = false;
            }
            return;
        }
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$movement$AntiVoid$Mode[this.mode.get().ordinal()]) {
            case 1: {
                if (!Modules.get().isActive(Flight.class)) {
                    Modules.get().get(Flight.class).toggle();
                }
                this.hasRun = true;
                break;
            }
            case 2: {
                this.mc.player.jump();
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Flight = new Mode();
        public static final /* enum */ Mode Jump = new Mode();
        private static final Mode[] $VALUES = Mode.$values();

        private static Mode[] $values() {
            return new Mode[]{Flight, Jump};
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

