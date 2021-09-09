/*
 * Decompiled with CFR 0.150.
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
    private /* synthetic */ int timer;
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ Setting<Button> button;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Mode> mode;

    @Override
    public void onActivate() {
        AutoClicker lllIlIllIlIlIll;
        lllIlIllIlIlIll.timer = 0;
        lllIlIllIlIlIll.mc.options.keyAttack.setPressed(false);
        lllIlIllIlIlIll.mc.options.keyUse.setPressed(false);
    }

    @EventHandler
    private void onTick(TickEvent.Post lllIlIllIlIIlII) {
        AutoClicker lllIlIllIlIIlIl;
        block0 : switch (lllIlIllIlIIlIl.mode.get()) {
            case Hold: {
                switch (lllIlIllIlIIlIl.button.get()) {
                    case Left: {
                        lllIlIllIlIIlIl.mc.options.keyAttack.setPressed(true);
                        break block0;
                    }
                    case Right: {
                        lllIlIllIlIIlIl.mc.options.keyUse.setPressed(true);
                    }
                }
                break;
            }
            case Press: {
                ++lllIlIllIlIIlIl.timer;
                if (lllIlIllIlIIlIl.delay.get() > lllIlIllIlIIlIl.timer) break;
                switch (lllIlIllIlIIlIl.button.get()) {
                    case Left: {
                        Utils.leftClick();
                        break;
                    }
                    case Right: {
                        Utils.rightClick();
                    }
                }
                lllIlIllIlIIlIl.timer = 0;
            }
        }
    }

    public AutoClicker() {
        super(Categories.Player, "auto-clicker", "Automatically clicks.");
        AutoClicker lllIlIllIlIllIl;
        lllIlIllIlIllIl.sgGeneral = lllIlIllIlIllIl.settings.getDefaultGroup();
        lllIlIllIlIllIl.mode = lllIlIllIlIllIl.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The method of clicking.").defaultValue(Mode.Press).build());
        lllIlIllIlIllIl.button = lllIlIllIlIllIl.sgGeneral.add(new EnumSetting.Builder().name("button").description("Which button to press.").defaultValue(Button.Right).build());
        lllIlIllIlIllIl.delay = lllIlIllIlIllIl.sgGeneral.add(new IntSetting.Builder().name("click-delay").description("The amount of delay between clicks in ticks.").defaultValue(2).min(0).sliderMax(60).build());
    }

    @Override
    public void onDeactivate() {
        AutoClicker lllIlIllIlIIlll;
        lllIlIllIlIIlll.mc.options.keyAttack.setPressed(false);
        lllIlIllIlIIlll.mc.options.keyUse.setPressed(false);
    }

    public static final class Button
    extends Enum<Button> {
        private static final /* synthetic */ Button[] $VALUES;
        public static final /* synthetic */ /* enum */ Button Left;
        public static final /* synthetic */ /* enum */ Button Right;

        private Button() {
            Button llllllllllllllllllIllIlIlIIIlIlI;
        }

        public static Button[] values() {
            return (Button[])$VALUES.clone();
        }

        private static /* synthetic */ Button[] $values() {
            return new Button[]{Right, Left};
        }

        public static Button valueOf(String llllllllllllllllllIllIlIlIIIllll) {
            return Enum.valueOf(Button.class, llllllllllllllllllIllIlIlIIIllll);
        }

        static {
            Right = new Button();
            Left = new Button();
            $VALUES = Button.$values();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Press;
        public static final /* synthetic */ /* enum */ Mode Hold;
        private static final /* synthetic */ Mode[] $VALUES;

        static {
            Hold = new Mode();
            Press = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String lllllllllllllllllllllIlIlIlIlIII) {
            return Enum.valueOf(Mode.class, lllllllllllllllllllllIlIlIlIlIII);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Hold, Press};
        }

        private Mode() {
            Mode lllllllllllllllllllllIlIlIlIIlII;
        }
    }
}

