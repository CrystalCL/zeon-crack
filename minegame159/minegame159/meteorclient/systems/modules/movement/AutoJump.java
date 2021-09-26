/*
 * Decompiled with CFR 0.151.
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
    private final SettingGroup sgGeneral;
    private final Setting<Double> velocityHeight;
    private final Setting<JumpWhen> jumpIf;
    private final Setting<Mode> mode;

    public AutoJump() {
        super(Categories.Movement, "auto-jump", "Automatically jumps.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The method of jumping.").defaultValue(Mode.Jump).build());
        this.jumpIf = this.sgGeneral.add(new EnumSetting.Builder().name("jump-if").description("Jump if.").defaultValue(JumpWhen.Always).build());
        this.velocityHeight = this.sgGeneral.add(new DoubleSetting.Builder().name("velocity-height").description("The distance that velocity mode moves you.").defaultValue(0.25).min(0.0).sliderMax(2.0).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (!this.mc.player.isOnGround() || this.mc.player.isSneaking() || !this.jump()) {
            return;
        }
        if (this.mode.get() == Mode.Jump) {
            this.mc.player.jump();
        } else {
            ((IVec3d)this.mc.player.getVelocity()).setY(this.velocityHeight.get());
        }
    }

    private boolean jump() {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$movement$AutoJump$JumpWhen[this.jumpIf.get().ordinal()]) {
            case 1: {
                return this.mc.player.isSprinting() && (this.mc.player.forwardSpeed != 0.0f || this.mc.player.sidewaysSpeed != 0.0f);
            }
            case 2: {
                return this.mc.player.forwardSpeed != 0.0f || this.mc.player.sidewaysSpeed != 0.0f;
            }
            case 3: {
                return true;
            }
        }
        return false;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode LowHop;
        public static final /* enum */ Mode Jump;
        private static final Mode[] $VALUES;

        private static Mode[] $values() {
            return new Mode[]{Jump, LowHop};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Jump = new Mode();
            LowHop = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }
    }

    public static final class JumpWhen
    extends Enum<JumpWhen> {
        public static final /* enum */ JumpWhen Always;
        private static final JumpWhen[] $VALUES;
        public static final /* enum */ JumpWhen Walking;
        public static final /* enum */ JumpWhen Sprinting;

        private static JumpWhen[] $values() {
            return new JumpWhen[]{Sprinting, Walking, Always};
        }

        static {
            Sprinting = new JumpWhen();
            Walking = new JumpWhen();
            Always = new JumpWhen();
            $VALUES = JumpWhen.$values();
        }

        public static JumpWhen[] values() {
            return (JumpWhen[])$VALUES.clone();
        }

        public static JumpWhen valueOf(String string) {
            return Enum.valueOf(JumpWhen.class, string);
        }
    }
}

