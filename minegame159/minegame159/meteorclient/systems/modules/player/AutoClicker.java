/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;

public class AutoClicker
extends Module {
    private int timer;
    private final SettingGroup sgGeneral;
    private final Setting<Button> button;
    private final Setting<Integer> delay;
    private final Setting<Mode> mode;

    public AutoClicker() {
        super(Categories.Player, "auto-clicker", "Automatically clicks.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The method of clicking.").defaultValue(Mode.Press).build());
        this.button = this.sgGeneral.add(new EnumSetting.Builder().name("button").description("Which button to press.").defaultValue(Button.Right).build());
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("click-delay").description("The amount of delay between clicks in ticks.").defaultValue(2).min(0).sliderMax(60).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        switch (this.mode.get()) {
            case Hold: {
                switch (this.button.get()) {
                    case Left: {
                        this.mc.options.keyAttack.setPressed(true);
                        break;
                    }
                    case Right: {
                        this.mc.options.keyUse.setPressed(true);
                    }
                }
                break;
            }
            case Press: {
                ++this.timer;
                if (this.delay.get() > this.timer) break;
                switch (this.button.get()) {
                    case Left: {
                        Utils.leftClick();
                        break;
                    }
                    case Right: {
                        Utils.rightClick();
                    }
                }
                this.timer = 0;
            }
        }
    }

    @Override
    public void onDeactivate() {
        this.mc.options.keyAttack.setPressed(false);
        this.mc.options.keyUse.setPressed(false);
    }

    @Override
    public void onActivate() {
        this.timer = 0;
        this.mc.options.keyAttack.setPressed(false);
        this.mc.options.keyUse.setPressed(false);
    }

    public static final class Button
    extends Enum<Button> {
        private static final Button[] $VALUES;
        public static final /* enum */ Button Right;
        public static final /* enum */ Button Left;

        private static Button[] $values() {
            return new Button[]{Right, Left};
        }

        static {
            Right = new Button();
            Left = new Button();
            $VALUES = Button.$values();
        }

        public static Button[] values() {
            return (Button[])$VALUES.clone();
        }

        public static Button valueOf(String string) {
            return Enum.valueOf(Button.class, string);
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Hold = new Mode();
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Press;

        static {
            Press = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        private static Mode[] $values() {
            return new Mode[]{Hold, Press};
        }
    }
}

