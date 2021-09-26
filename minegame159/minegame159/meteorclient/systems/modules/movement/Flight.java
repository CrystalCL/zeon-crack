/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Flight
extends Module {
    private boolean flip;
    private final SettingGroup sgAntiKick;
    private final Setting<Integer> offTime;
    private long lastModifiedTime;
    private final Setting<Double> speed;
    private final Setting<Integer> delay;
    private int delayLeft;
    private float lastYaw;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> verticalSpeedMatch;
    private double lastY;
    private final Setting<AntiKickMode> antiKickMode;
    private final Setting<Mode> mode;
    private int offLeft;

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (!(send.packet instanceof PlayerMoveC2SPacket) || this.antiKickMode.get() != AntiKickMode.Packet) {
            return;
        }
        PlayerMoveC2SPacket PlayerMoveC2SPacket2 = (PlayerMoveC2SPacket)send.packet;
        long l = System.currentTimeMillis();
        double d = PlayerMoveC2SPacket2.getY(Double.MAX_VALUE);
        if (d != Double.MAX_VALUE) {
            if (l - this.lastModifiedTime > 1000L && this.lastY != Double.MAX_VALUE && this.mc.world.getBlockState(this.mc.player.getBlockPos().down()).isAir()) {
                ((PlayerMoveC2SPacketAccessor)PlayerMoveC2SPacket2).setY(this.lastY - 0.0313);
                this.lastModifiedTime = l;
            } else {
                this.lastY = d;
            }
        }
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre pre) {
        float f = this.mc.player.yaw;
        if (this.mc.player.fallDistance >= 3.0f && f == this.lastYaw && this.mc.player.getVelocity().length() < 0.003) {
            this.mc.player.yaw += this.flip ? 1.0f : -1.0f;
            this.flip = !this.flip;
        }
        this.lastYaw = f;
    }

    @Override
    public void onActivate() {
        if (this.mode.get() == Mode.Abilities && !this.mc.player.isSpectator()) {
            this.mc.player.abilities.flying = true;
            if (this.mc.player.abilities.creativeMode) {
                return;
            }
            this.mc.player.abilities.allowFlying = true;
        }
    }

    public Flight() {
        super(Categories.Movement, "flight", "FLYYYY! No Fall is recommended with this module.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgAntiKick = this.settings.createGroup("Anti Kick");
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode for Flight.").defaultValue(Mode.Abilities).build());
        this.speed = this.sgGeneral.add(new DoubleSetting.Builder().name("speed").description("Your speed when flying.").defaultValue(0.1).min(0.0).build());
        this.verticalSpeedMatch = this.sgGeneral.add(new BoolSetting.Builder().name("vertical-speed-match").description("Matches your vertical speed to your horizontal speed, otherwise uses vanilla ratio.").defaultValue(false).build());
        this.antiKickMode = this.sgAntiKick.add(new EnumSetting.Builder().name("mode").description("The mode for anti kick.").defaultValue(AntiKickMode.Packet).build());
        this.delay = this.sgAntiKick.add(new IntSetting.Builder().name("delay").description("The amount of delay, in ticks, between toggles in normal mode.").defaultValue(80).min(1).max(5000).sliderMax(200).build());
        this.offTime = this.sgAntiKick.add(new IntSetting.Builder().name("off-time").description("The amount of delay, in ticks, that Flight is toggled off for in normal mode.").defaultValue(5).min(1).max(20).sliderMax(10).build());
        this.delayLeft = this.delay.get();
        this.offLeft = this.offTime.get();
        this.lastModifiedTime = 0L;
        this.lastY = Double.MAX_VALUE;
    }

    @EventHandler
    private void onPostTick(TickEvent.Post post) {
        if (this.antiKickMode.get() == AntiKickMode.Normal && this.delayLeft > 0) {
            --this.delayLeft;
        } else {
            if (this.antiKickMode.get() == AntiKickMode.Normal && this.delayLeft <= 0 && this.offLeft > 0) {
                --this.offLeft;
                if (this.mode.get() == Mode.Abilities) {
                    this.mc.player.abilities.flying = false;
                    this.mc.player.abilities.setFlySpeed(0.05f);
                    if (this.mc.player.abilities.creativeMode) {
                        return;
                    }
                    this.mc.player.abilities.allowFlying = false;
                }
                return;
            }
            if (this.antiKickMode.get() == AntiKickMode.Normal && this.delayLeft <= 0 && this.offLeft <= 0) {
                this.delayLeft = this.delay.get();
                this.offLeft = this.offTime.get();
            }
        }
        if (this.mc.player.yaw != this.lastYaw) {
            this.mc.player.yaw = this.lastYaw;
        }
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$movement$Flight$Mode[this.mode.get().ordinal()]) {
            case 1: {
                this.mc.player.abilities.flying = false;
                this.mc.player.flyingSpeed = this.speed.get().floatValue() * (this.mc.player.isSprinting() ? 15.0f : 10.0f);
                this.mc.player.setVelocity(0.0, 0.0, 0.0);
                Vec3d Vec3d2 = this.mc.player.getVelocity();
                if (this.mc.options.keyJump.isPressed()) {
                    this.mc.player.setVelocity(Vec3d2.add(0.0, this.speed.get() * (double)(this.verticalSpeedMatch.get() != false ? 10.0f : 5.0f), 0.0));
                }
                if (!this.mc.options.keySneak.isPressed()) break;
                this.mc.player.setVelocity(Vec3d2.subtract(0.0, this.speed.get() * (double)(this.verticalSpeedMatch.get() != false ? 10.0f : 5.0f), 0.0));
                break;
            }
            case 2: {
                if (this.mc.player.isSpectator()) {
                    return;
                }
                this.mc.player.abilities.setFlySpeed(this.speed.get().floatValue());
                this.mc.player.abilities.flying = true;
                if (this.mc.player.abilities.creativeMode) {
                    return;
                }
                this.mc.player.abilities.allowFlying = true;
            }
        }
    }

    @Override
    public void onDeactivate() {
        if (this.mode.get() == Mode.Abilities && !this.mc.player.isSpectator()) {
            this.mc.player.abilities.flying = false;
            this.mc.player.abilities.setFlySpeed(0.05f);
            if (this.mc.player.abilities.creativeMode) {
                return;
            }
            this.mc.player.abilities.allowFlying = false;
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Abilities = new Mode();
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Velocity;

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            Velocity = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static Mode[] $values() {
            return new Mode[]{Abilities, Velocity};
        }
    }

    public static final class AntiKickMode
    extends Enum<AntiKickMode> {
        public static final /* enum */ AntiKickMode Normal = new AntiKickMode();
        public static final /* enum */ AntiKickMode Packet = new AntiKickMode();
        public static final /* enum */ AntiKickMode None = new AntiKickMode();
        private static final AntiKickMode[] $VALUES = AntiKickMode.$values();

        private static AntiKickMode[] $values() {
            return new AntiKickMode[]{Normal, Packet, None};
        }

        public static AntiKickMode[] values() {
            return (AntiKickMode[])$VALUES.clone();
        }

        public static AntiKickMode valueOf(String string) {
            return Enum.valueOf(AntiKickMode.class, string);
        }
    }
}

