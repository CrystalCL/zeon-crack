/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class AutoJump
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ Setting<JumpWhen> jumpIf;
    private final /* synthetic */ Setting<Double> velocityHeight;

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIlIIIIIIllIlIII) {
        AutoJump lllllllllllllllllIlIIIIIIllIlIIl;
        if (!lllllllllllllllllIlIIIIIIllIlIIl.mc.player.isOnGround() || lllllllllllllllllIlIIIIIIllIlIIl.mc.player.isSneaking() || !lllllllllllllllllIlIIIIIIllIlIIl.jump()) {
            return;
        }
        if (lllllllllllllllllIlIIIIIIllIlIIl.mode.get() == Mode.Jump) {
            lllllllllllllllllIlIIIIIIllIlIIl.mc.player.jump();
        } else {
            ((IVec3d)lllllllllllllllllIlIIIIIIllIlIIl.mc.player.getVelocity()).setY(lllllllllllllllllIlIIIIIIllIlIIl.velocityHeight.get());
        }
    }

    private boolean jump() {
        AutoJump lllllllllllllllllIlIIIIIIllIllII;
        switch (lllllllllllllllllIlIIIIIIllIllII.jumpIf.get()) {
            case Sprinting: {
                return lllllllllllllllllIlIIIIIIllIllII.mc.player.isSprinting() && (lllllllllllllllllIlIIIIIIllIllII.mc.player.forwardSpeed != 0.0f || lllllllllllllllllIlIIIIIIllIllII.mc.player.sidewaysSpeed != 0.0f);
            }
            case Walking: {
                return lllllllllllllllllIlIIIIIIllIllII.mc.player.forwardSpeed != 0.0f || lllllllllllllllllIlIIIIIIllIllII.mc.player.sidewaysSpeed != 0.0f;
            }
            case Always: {
                return true;
            }
        }
        return false;
    }

    public AutoJump() {
        super(Categories.Movement, "auto-jump", "Automatically jumps.");
        AutoJump lllllllllllllllllIlIIIIIIllIllll;
        lllllllllllllllllIlIIIIIIllIllll.sgGeneral = lllllllllllllllllIlIIIIIIllIllll.settings.getDefaultGroup();
        lllllllllllllllllIlIIIIIIllIllll.mode = lllllllllllllllllIlIIIIIIllIllll.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The method of jumping.").defaultValue(Mode.Jump).build());
        lllllllllllllllllIlIIIIIIllIllll.jumpIf = lllllllllllllllllIlIIIIIIllIllll.sgGeneral.add(new EnumSetting.Builder().name("jump-if").description("Jump if.").defaultValue(JumpWhen.Always).build());
        lllllllllllllllllIlIIIIIIllIllll.velocityHeight = lllllllllllllllllIlIIIIIIllIllll.sgGeneral.add(new DoubleSetting.Builder().name("velocity-height").description("The distance that velocity mode moves you.").defaultValue(0.25).min(0.0).sliderMax(2.0).build());
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode LowHop;
        public static final /* synthetic */ /* enum */ Mode Jump;

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Jump, LowHop};
        }

        public static Mode valueOf(String lIIIIIIlllIII) {
            return Enum.valueOf(Mode.class, lIIIIIIlllIII);
        }

        static {
            Jump = new Mode();
            LowHop = new Mode();
            $VALUES = Mode.$values();
        }

        private Mode() {
            Mode lIIIIIIllIIlI;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }

    public static final class JumpWhen
    extends Enum<JumpWhen> {
        public static final /* synthetic */ /* enum */ JumpWhen Walking;
        public static final /* synthetic */ /* enum */ JumpWhen Always;
        private static final /* synthetic */ JumpWhen[] $VALUES;
        public static final /* synthetic */ /* enum */ JumpWhen Sprinting;

        static {
            Sprinting = new JumpWhen();
            Walking = new JumpWhen();
            Always = new JumpWhen();
            $VALUES = JumpWhen.$values();
        }

        public static JumpWhen[] values() {
            return (JumpWhen[])$VALUES.clone();
        }

        private static /* synthetic */ JumpWhen[] $values() {
            return new JumpWhen[]{Sprinting, Walking, Always};
        }

        private JumpWhen() {
            JumpWhen llllllllllllllllIlIlllIlIlIlIIlI;
        }

        public static JumpWhen valueOf(String llllllllllllllllIlIlllIlIlIlIlll) {
            return Enum.valueOf(JumpWhen.class, llllllllllllllllIlIlllIlIlIlIlll);
        }
    }
}

