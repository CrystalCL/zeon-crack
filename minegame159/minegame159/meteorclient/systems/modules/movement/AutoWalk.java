/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.input.Input;
import minegame159.meteorclient.utils.world.GoalDirection;
import net.minecraft.client.option.KeyBinding;

public class AutoWalk
extends Module {
    private GoalDirection goal;
    private int timer;
    private final Setting<Mode> mode;
    private final SettingGroup sgGeneral;
    private final Setting<Direction> direction;

    @Override
    public void onActivate() {
        if (this.mode.get() == Mode.Smart) {
            this.createGoal();
        }
    }

    private void createGoal() {
        this.timer = 0;
        this.goal = new GoalDirection(this.mc.player.getPos(), this.mc.player.yaw);
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)this.goal);
    }

    private void lambda$new$1(Direction direction) {
        if (this.isActive()) {
            this.unpress();
        }
    }

    @EventHandler(priority=100)
    private void onTick(TickEvent.Pre pre) {
        if (this.mode.get() == Mode.Simple) {
            switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$movement$AutoWalk$Direction[this.direction.get().ordinal()]) {
                case 1: {
                    this.setPressed(this.mc.options.keyForward, true);
                    break;
                }
                case 2: {
                    this.setPressed(this.mc.options.keyBack, true);
                    break;
                }
                case 3: {
                    this.setPressed(this.mc.options.keyLeft, true);
                    break;
                }
                case 4: {
                    this.setPressed(this.mc.options.keyRight, true);
                }
            }
        } else {
            if (this.timer > 20) {
                this.timer = 0;
                this.goal.recalculate(this.mc.player.getPos());
            }
            ++this.timer;
        }
    }

    private void unpress() {
        this.setPressed(this.mc.options.keyForward, false);
        this.setPressed(this.mc.options.keyBack, false);
        this.setPressed(this.mc.options.keyLeft, false);
        this.setPressed(this.mc.options.keyRight, false);
    }

    public AutoWalk() {
        super(Categories.Movement, "auto-walk", "Automatically walks forward.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Walking mode.").defaultValue(Mode.Smart).onChanged(this::lambda$new$0).build());
        this.direction = this.sgGeneral.add(new EnumSetting.Builder().name("simple-direction").description("The direction to walk in simple mode.").defaultValue(Direction.Forwards).onChanged(this::lambda$new$1).build());
        this.timer = 0;
    }

    private void lambda$new$0(Mode mode) {
        if (this.isActive()) {
            if (mode == Mode.Simple) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
                this.goal = null;
            } else {
                this.timer = 0;
                this.createGoal();
            }
            this.unpress();
        }
    }

    @Override
    public void onDeactivate() {
        if (this.mode.get() == Mode.Simple) {
            this.unpress();
        } else {
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        }
        this.goal = null;
    }

    private void setPressed(KeyBinding KeyBinding2, boolean bl) {
        KeyBinding2.setPressed(bl);
        Input.setKeyState(KeyBinding2, bl);
    }

    public static final class Direction
    extends Enum<Direction> {
        public static final /* enum */ Direction Forwards = new Direction();
        private static final Direction[] $VALUES;
        public static final /* enum */ Direction Right;
        public static final /* enum */ Direction Backwards;
        public static final /* enum */ Direction Left;

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }

        public static Direction valueOf(String string) {
            return Enum.valueOf(Direction.class, string);
        }

        private static Direction[] $values() {
            return new Direction[]{Forwards, Backwards, Left, Right};
        }

        static {
            Backwards = new Direction();
            Left = new Direction();
            Right = new Direction();
            $VALUES = Direction.$values();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Simple = new Mode();
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Smart;

        static {
            Smart = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static Mode[] $values() {
            return new Mode[]{Simple, Smart};
        }
    }
}

