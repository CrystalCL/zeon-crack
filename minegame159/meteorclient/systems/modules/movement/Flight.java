/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
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
    private /* synthetic */ long lastModifiedTime;
    private /* synthetic */ double lastY;
    private /* synthetic */ int offLeft;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ Setting<Mode> mode;
    private /* synthetic */ int delayLeft;
    private final /* synthetic */ Setting<Integer> offTime;
    private final /* synthetic */ Setting<Boolean> verticalSpeedMatch;
    private final /* synthetic */ Setting<Double> speed;
    private /* synthetic */ boolean flip;
    private final /* synthetic */ SettingGroup sgAntiKick;
    private /* synthetic */ float lastYaw;
    private final /* synthetic */ Setting<AntiKickMode> antiKickMode;

    @EventHandler
    private void onPreTick(TickEvent.Pre llllllllllllllllllllIIIllIlIIIll) {
        Flight llllllllllllllllllllIIIllIlIIIIl;
        float llllllllllllllllllllIIIllIlIIIlI = llllllllllllllllllllIIIllIlIIIIl.mc.player.yaw;
        if (llllllllllllllllllllIIIllIlIIIIl.mc.player.fallDistance >= 3.0f && llllllllllllllllllllIIIllIlIIIlI == llllllllllllllllllllIIIllIlIIIIl.lastYaw && llllllllllllllllllllIIIllIlIIIIl.mc.player.getVelocity().length() < 0.003) {
            llllllllllllllllllllIIIllIlIIIIl.mc.player.yaw += llllllllllllllllllllIIIllIlIIIIl.flip ? 1.0f : -1.0f;
            llllllllllllllllllllIIIllIlIIIIl.flip = !llllllllllllllllllllIIIllIlIIIIl.flip;
        }
        llllllllllllllllllllIIIllIlIIIIl.lastYaw = llllllllllllllllllllIIIllIlIIIlI;
    }

    @EventHandler
    private void onPostTick(TickEvent.Post llllllllllllllllllllIIIllIIllIll) {
        Flight llllllllllllllllllllIIIllIIllIlI;
        if (llllllllllllllllllllIIIllIIllIlI.antiKickMode.get() == AntiKickMode.Normal && llllllllllllllllllllIIIllIIllIlI.delayLeft > 0) {
            --llllllllllllllllllllIIIllIIllIlI.delayLeft;
        } else {
            if (llllllllllllllllllllIIIllIIllIlI.antiKickMode.get() == AntiKickMode.Normal && llllllllllllllllllllIIIllIIllIlI.delayLeft <= 0 && llllllllllllllllllllIIIllIIllIlI.offLeft > 0) {
                --llllllllllllllllllllIIIllIIllIlI.offLeft;
                if (llllllllllllllllllllIIIllIIllIlI.mode.get() == Mode.Abilities) {
                    llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.flying = false;
                    llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.setFlySpeed(0.05f);
                    if (llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.creativeMode) {
                        return;
                    }
                    llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.allowFlying = false;
                }
                return;
            }
            if (llllllllllllllllllllIIIllIIllIlI.antiKickMode.get() == AntiKickMode.Normal && llllllllllllllllllllIIIllIIllIlI.delayLeft <= 0 && llllllllllllllllllllIIIllIIllIlI.offLeft <= 0) {
                llllllllllllllllllllIIIllIIllIlI.delayLeft = llllllllllllllllllllIIIllIIllIlI.delay.get();
                llllllllllllllllllllIIIllIIllIlI.offLeft = llllllllllllllllllllIIIllIIllIlI.offTime.get();
            }
        }
        if (llllllllllllllllllllIIIllIIllIlI.mc.player.yaw != llllllllllllllllllllIIIllIIllIlI.lastYaw) {
            llllllllllllllllllllIIIllIIllIlI.mc.player.yaw = llllllllllllllllllllIIIllIIllIlI.lastYaw;
        }
        switch (llllllllllllllllllllIIIllIIllIlI.mode.get()) {
            case Velocity: {
                llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.flying = false;
                llllllllllllllllllllIIIllIIllIlI.mc.player.flyingSpeed = llllllllllllllllllllIIIllIIllIlI.speed.get().floatValue() * (llllllllllllllllllllIIIllIIllIlI.mc.player.isSprinting() ? 15.0f : 10.0f);
                llllllllllllllllllllIIIllIIllIlI.mc.player.setVelocity(0.0, 0.0, 0.0);
                Vec3d llllllllllllllllllllIIIllIIlllIl = llllllllllllllllllllIIIllIIllIlI.mc.player.getVelocity();
                if (llllllllllllllllllllIIIllIIllIlI.mc.options.keyJump.isPressed()) {
                    llllllllllllllllllllIIIllIIllIlI.mc.player.setVelocity(llllllllllllllllllllIIIllIIlllIl.add(0.0, llllllllllllllllllllIIIllIIllIlI.speed.get() * (double)(llllllllllllllllllllIIIllIIllIlI.verticalSpeedMatch.get() != false ? 10.0f : 5.0f), 0.0));
                }
                if (!llllllllllllllllllllIIIllIIllIlI.mc.options.keySneak.isPressed()) break;
                llllllllllllllllllllIIIllIIllIlI.mc.player.setVelocity(llllllllllllllllllllIIIllIIlllIl.subtract(0.0, llllllllllllllllllllIIIllIIllIlI.speed.get() * (double)(llllllllllllllllllllIIIllIIllIlI.verticalSpeedMatch.get() != false ? 10.0f : 5.0f), 0.0));
                break;
            }
            case Abilities: {
                if (llllllllllllllllllllIIIllIIllIlI.mc.player.isSpectator()) {
                    return;
                }
                llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.setFlySpeed(llllllllllllllllllllIIIllIIllIlI.speed.get().floatValue());
                llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.flying = true;
                if (llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.creativeMode) {
                    return;
                }
                llllllllllllllllllllIIIllIIllIlI.mc.player.abilities.allowFlying = true;
            }
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send llllllllllllllllllllIIIllIIlIIlI) {
        Flight llllllllllllllllllllIIIllIIIlllI;
        if (!(llllllllllllllllllllIIIllIIlIIlI.packet instanceof PlayerMoveC2SPacket) || llllllllllllllllllllIIIllIIIlllI.antiKickMode.get() != AntiKickMode.Packet) {
            return;
        }
        PlayerMoveC2SPacket llllllllllllllllllllIIIllIIlIIIl = (PlayerMoveC2SPacket)llllllllllllllllllllIIIllIIlIIlI.packet;
        long llllllllllllllllllllIIIllIIlIIII = System.currentTimeMillis();
        double llllllllllllllllllllIIIllIIIllll = llllllllllllllllllllIIIllIIlIIIl.getY(Double.MAX_VALUE);
        if (llllllllllllllllllllIIIllIIIllll != Double.MAX_VALUE) {
            if (llllllllllllllllllllIIIllIIlIIII - llllllllllllllllllllIIIllIIIlllI.lastModifiedTime > 1000L && llllllllllllllllllllIIIllIIIlllI.lastY != Double.MAX_VALUE && llllllllllllllllllllIIIllIIIlllI.mc.world.getBlockState(llllllllllllllllllllIIIllIIIlllI.mc.player.getBlockPos().down()).isAir()) {
                ((PlayerMoveC2SPacketAccessor)llllllllllllllllllllIIIllIIlIIIl).setY(llllllllllllllllllllIIIllIIIlllI.lastY - 0.0313);
                llllllllllllllllllllIIIllIIIlllI.lastModifiedTime = llllllllllllllllllllIIIllIIlIIII;
            } else {
                llllllllllllllllllllIIIllIIIlllI.lastY = llllllllllllllllllllIIIllIIIllll;
            }
        }
    }

    public Flight() {
        super(Categories.Movement, "flight", "FLYYYY! No Fall is recommended with this module.");
        Flight llllllllllllllllllllIIIllIlIllIl;
        llllllllllllllllllllIIIllIlIllIl.sgGeneral = llllllllllllllllllllIIIllIlIllIl.settings.getDefaultGroup();
        llllllllllllllllllllIIIllIlIllIl.sgAntiKick = llllllllllllllllllllIIIllIlIllIl.settings.createGroup("Anti Kick");
        llllllllllllllllllllIIIllIlIllIl.mode = llllllllllllllllllllIIIllIlIllIl.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode for Flight.").defaultValue(Mode.Abilities).build());
        llllllllllllllllllllIIIllIlIllIl.speed = llllllllllllllllllllIIIllIlIllIl.sgGeneral.add(new DoubleSetting.Builder().name("speed").description("Your speed when flying.").defaultValue(0.1).min(0.0).build());
        llllllllllllllllllllIIIllIlIllIl.verticalSpeedMatch = llllllllllllllllllllIIIllIlIllIl.sgGeneral.add(new BoolSetting.Builder().name("vertical-speed-match").description("Matches your vertical speed to your horizontal speed, otherwise uses vanilla ratio.").defaultValue(false).build());
        llllllllllllllllllllIIIllIlIllIl.antiKickMode = llllllllllllllllllllIIIllIlIllIl.sgAntiKick.add(new EnumSetting.Builder().name("mode").description("The mode for anti kick.").defaultValue(AntiKickMode.Packet).build());
        llllllllllllllllllllIIIllIlIllIl.delay = llllllllllllllllllllIIIllIlIllIl.sgAntiKick.add(new IntSetting.Builder().name("delay").description("The amount of delay, in ticks, between toggles in normal mode.").defaultValue(80).min(1).max(5000).sliderMax(200).build());
        llllllllllllllllllllIIIllIlIllIl.offTime = llllllllllllllllllllIIIllIlIllIl.sgAntiKick.add(new IntSetting.Builder().name("off-time").description("The amount of delay, in ticks, that Flight is toggled off for in normal mode.").defaultValue(5).min(1).max(20).sliderMax(10).build());
        llllllllllllllllllllIIIllIlIllIl.delayLeft = llllllllllllllllllllIIIllIlIllIl.delay.get();
        llllllllllllllllllllIIIllIlIllIl.offLeft = llllllllllllllllllllIIIllIlIllIl.offTime.get();
        llllllllllllllllllllIIIllIlIllIl.lastModifiedTime = 0L;
        llllllllllllllllllllIIIllIlIllIl.lastY = Double.MAX_VALUE;
    }

    @Override
    public void onDeactivate() {
        Flight llllllllllllllllllllIIIllIlIlIII;
        if (llllllllllllllllllllIIIllIlIlIII.mode.get() == Mode.Abilities && !llllllllllllllllllllIIIllIlIlIII.mc.player.isSpectator()) {
            llllllllllllllllllllIIIllIlIlIII.mc.player.abilities.flying = false;
            llllllllllllllllllllIIIllIlIlIII.mc.player.abilities.setFlySpeed(0.05f);
            if (llllllllllllllllllllIIIllIlIlIII.mc.player.abilities.creativeMode) {
                return;
            }
            llllllllllllllllllllIIIllIlIlIII.mc.player.abilities.allowFlying = false;
        }
    }

    @Override
    public void onActivate() {
        Flight llllllllllllllllllllIIIllIlIlIll;
        if (llllllllllllllllllllIIIllIlIlIll.mode.get() == Mode.Abilities && !llllllllllllllllllllIIIllIlIlIll.mc.player.isSpectator()) {
            llllllllllllllllllllIIIllIlIlIll.mc.player.abilities.flying = true;
            if (llllllllllllllllllllIIIllIlIlIll.mc.player.abilities.creativeMode) {
                return;
            }
            llllllllllllllllllllIIIllIlIlIll.mc.player.abilities.allowFlying = true;
        }
    }

    public static final class AntiKickMode
    extends Enum<AntiKickMode> {
        private static final /* synthetic */ AntiKickMode[] $VALUES;
        public static final /* synthetic */ /* enum */ AntiKickMode Packet;
        public static final /* synthetic */ /* enum */ AntiKickMode Normal;
        public static final /* synthetic */ /* enum */ AntiKickMode None;

        private static /* synthetic */ AntiKickMode[] $values() {
            return new AntiKickMode[]{Normal, Packet, None};
        }

        public static AntiKickMode[] values() {
            return (AntiKickMode[])$VALUES.clone();
        }

        private AntiKickMode() {
            AntiKickMode llIIlllIlIlllI;
        }

        static {
            Normal = new AntiKickMode();
            Packet = new AntiKickMode();
            None = new AntiKickMode();
            $VALUES = AntiKickMode.$values();
        }

        public static AntiKickMode valueOf(String llIIlllIllIlII) {
            return Enum.valueOf(AntiKickMode.class, llIIlllIllIlII);
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Abilities;
        public static final /* synthetic */ /* enum */ Mode Velocity;

        public static Mode valueOf(String llllllllllllllllllllIlIIIIlIIIII) {
            return Enum.valueOf(Mode.class, llllllllllllllllllllIlIIIIlIIIII);
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Abilities, Velocity};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private Mode() {
            Mode llllllllllllllllllllIlIIIIIlllII;
        }

        static {
            Abilities = new Mode();
            Velocity = new Mode();
            $VALUES = Mode.$values();
        }
    }
}

