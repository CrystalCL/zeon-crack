/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.pathing.goals.Goal
 *  net.minecraft.client.option.KeyBinding
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
    private final /* synthetic */ Setting<Mode> mode;
    private /* synthetic */ GoalDirection goal;
    private /* synthetic */ int timer;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Direction> direction;

    @Override
    public void onActivate() {
        AutoWalk llllIIlIIIIIlll;
        if (llllIIlIIIIIlll.mode.get() == Mode.Smart) {
            llllIIlIIIIIlll.createGoal();
        }
    }

    private void createGoal() {
        AutoWalk llllIIIllllIIll;
        llllIIIllllIIll.timer = 0;
        llllIIIllllIIll.goal = new GoalDirection(llllIIIllllIIll.mc.player.getPos(), llllIIIllllIIll.mc.player.yaw);
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)llllIIIllllIIll.goal);
    }

    @EventHandler(priority=100)
    private void onTick(TickEvent.Pre llllIIlIIIIIIIl) {
        AutoWalk llllIIlIIIIIIlI;
        if (llllIIlIIIIIIlI.mode.get() == Mode.Simple) {
            switch (llllIIlIIIIIIlI.direction.get()) {
                case Forwards: {
                    llllIIlIIIIIIlI.setPressed(llllIIlIIIIIIlI.mc.options.keyForward, true);
                    break;
                }
                case Backwards: {
                    llllIIlIIIIIIlI.setPressed(llllIIlIIIIIIlI.mc.options.keyBack, true);
                    break;
                }
                case Left: {
                    llllIIlIIIIIIlI.setPressed(llllIIlIIIIIIlI.mc.options.keyLeft, true);
                    break;
                }
                case Right: {
                    llllIIlIIIIIIlI.setPressed(llllIIlIIIIIIlI.mc.options.keyRight, true);
                }
            }
        } else {
            if (llllIIlIIIIIIlI.timer > 20) {
                llllIIlIIIIIIlI.timer = 0;
                llllIIlIIIIIIlI.goal.recalculate(llllIIlIIIIIIlI.mc.player.getPos());
            }
            ++llllIIlIIIIIIlI.timer;
        }
    }

    private void unpress() {
        AutoWalk llllIIIlllllllI;
        llllIIIlllllllI.setPressed(llllIIIlllllllI.mc.options.keyForward, false);
        llllIIIlllllllI.setPressed(llllIIIlllllllI.mc.options.keyBack, false);
        llllIIIlllllllI.setPressed(llllIIIlllllllI.mc.options.keyLeft, false);
        llllIIIlllllllI.setPressed(llllIIIlllllllI.mc.options.keyRight, false);
    }

    private void setPressed(KeyBinding llllIIIlllllIIl, boolean llllIIIlllllIII) {
        llllIIIlllllIIl.setPressed(llllIIIlllllIII);
        Input.setKeyState(llllIIIlllllIIl, llllIIIlllllIII);
    }

    public AutoWalk() {
        super(Categories.Movement, "auto-walk", "Automatically walks forward.");
        AutoWalk llllIIlIIIIlIlI;
        llllIIlIIIIlIlI.sgGeneral = llllIIlIIIIlIlI.settings.getDefaultGroup();
        llllIIlIIIIlIlI.mode = llllIIlIIIIlIlI.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Walking mode.").defaultValue(Mode.Smart).onChanged(llllIIIlllIlIIl -> {
            AutoWalk llllIIIlllIllII;
            if (llllIIIlllIllII.isActive()) {
                if (llllIIIlllIlIIl == Mode.Simple) {
                    BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
                    llllIIIlllIllII.goal = null;
                } else {
                    llllIIIlllIllII.timer = 0;
                    llllIIIlllIllII.createGoal();
                }
                llllIIIlllIllII.unpress();
            }
        }).build());
        llllIIlIIIIlIlI.direction = llllIIlIIIIlIlI.sgGeneral.add(new EnumSetting.Builder().name("simple-direction").description("The direction to walk in simple mode.").defaultValue(Direction.Forwards).onChanged(llllIIIllllIIII -> {
            AutoWalk llllIIIllllIIIl;
            if (llllIIIllllIIIl.isActive()) {
                llllIIIllllIIIl.unpress();
            }
        }).build());
        llllIIlIIIIlIlI.timer = 0;
    }

    @Override
    public void onDeactivate() {
        AutoWalk llllIIlIIIIIlII;
        if (llllIIlIIIIIlII.mode.get() == Mode.Simple) {
            llllIIlIIIIIlII.unpress();
        } else {
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        }
        llllIIlIIIIIlII.goal = null;
    }

    public static final class Direction
    extends Enum<Direction> {
        private static final /* synthetic */ Direction[] $VALUES;
        public static final /* synthetic */ /* enum */ Direction Forwards;
        public static final /* synthetic */ /* enum */ Direction Backwards;
        public static final /* synthetic */ /* enum */ Direction Left;
        public static final /* synthetic */ /* enum */ Direction Right;

        public static Direction valueOf(String lIIllIIlllIIll) {
            return Enum.valueOf(Direction.class, lIIllIIlllIIll);
        }

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }

        private Direction() {
            Direction lIIllIIllIlllI;
        }

        static {
            Forwards = new Direction();
            Backwards = new Direction();
            Left = new Direction();
            Right = new Direction();
            $VALUES = Direction.$values();
        }

        private static /* synthetic */ Direction[] $values() {
            return new Direction[]{Forwards, Backwards, Left, Right};
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Simple;
        public static final /* synthetic */ /* enum */ Mode Smart;

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Simple, Smart};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Simple = new Mode();
            Smart = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String llllllllllllllllllllIllIlllIIIll) {
            return Enum.valueOf(Mode.class, llllllllllllllllllllIllIlllIIIll);
        }

        private Mode() {
            Mode llllllllllllllllllllIllIllIlllIl;
        }
    }
}

