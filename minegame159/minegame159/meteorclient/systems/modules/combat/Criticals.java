/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixininterface.IPlayerMoveC2SPacket;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.KillAura;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.world.World;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;

public class Criticals
extends Module {
    private int sendTimer;
    private final Setting<Mode> mode;
    private final SettingGroup sgGeneral;
    private PlayerInteractEntityC2SPacket attackPacket;
    private HandSwingC2SPacket swingPacket;
    private final Setting<Boolean> crystals;
    private boolean sendPackets;
    private final Setting<Boolean> ka;

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (send.packet instanceof PlayerInteractEntityC2SPacket && ((PlayerInteractEntityC2SPacket)send.packet).getType() == PlayerInteractEntityC2SPacket.class_2825.ATTACK) {
            if (((PlayerInteractEntityC2SPacket)send.packet).getEntity((World)this.mc.world) != Modules.get().get(KillAura.class).getTarget() && this.ka.get().booleanValue()) {
                return;
            }
            if (((PlayerInteractEntityC2SPacket)send.packet).getEntity((World)this.mc.world) instanceof EndCrystalEntity && !this.crystals.get().booleanValue()) {
                return;
            }
            if (this.skipCrit()) {
                return;
            }
            if (this.mode.get() == Mode.Packet) {
                this.doPacketMode();
            } else {
                this.doJumpMode(send);
            }
        } else if (send.packet instanceof HandSwingC2SPacket && this.mode.get() != Mode.Packet) {
            if (this.skipCrit()) {
                return;
            }
            this.doJumpModeSwing(send);
        }
    }

    private void doPacketMode() {
        double d = this.mc.player.getX();
        double d2 = this.mc.player.getY();
        double d3 = this.mc.player.getZ();
        PlayerMoveC2SPacket.class_2829 class_28292 = new PlayerMoveC2SPacket.class_2829(d, d2 + 0.0625, d3, false);
        PlayerMoveC2SPacket.class_2829 class_28293 = new PlayerMoveC2SPacket.class_2829(d, d2, d3, false);
        ((IPlayerMoveC2SPacket)class_28292).setTag(1337);
        ((IPlayerMoveC2SPacket)class_28293).setTag(1337);
        this.mc.player.networkHandler.sendPacket((Packet)class_28292);
        this.mc.player.networkHandler.sendPacket((Packet)class_28293);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.sendPackets) {
            if (this.sendTimer <= 0) {
                this.sendPackets = false;
                if (this.attackPacket == null || this.swingPacket == null) {
                    return;
                }
                this.mc.getNetworkHandler().sendPacket((Packet)this.attackPacket);
                this.mc.getNetworkHandler().sendPacket((Packet)this.swingPacket);
                this.attackPacket = null;
                this.swingPacket = null;
            } else {
                --this.sendTimer;
            }
        }
    }

    @Override
    public void onActivate() {
        this.attackPacket = null;
        this.swingPacket = null;
        this.sendPackets = false;
        this.sendTimer = 0;
    }

    public Criticals() {
        super(Categories.Combat, "criticals", "Performs critical attacks when you hit your target.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode on how Criticals will function.").defaultValue(Mode.Packet).build());
        this.ka = this.sgGeneral.add(new BoolSetting.Builder().name("only-killaura").description("Only performs crits when using killaura.").defaultValue(false).build());
        this.crystals = this.sgGeneral.add(new BoolSetting.Builder().name("crystals").description("Wether to crit crystals or not.").defaultValue(false).build());
    }

    private void doJumpModeSwing(PacketEvent.Send send) {
        if (this.sendPackets && this.swingPacket == null) {
            this.swingPacket = (HandSwingC2SPacket)send.packet;
            send.cancel();
        }
    }

    private boolean skipCrit() {
        boolean bl;
        boolean bl2 = bl = !this.mc.player.isSubmergedInWater() && !this.mc.player.isInLava() && !this.mc.player.isClimbing();
        if (!this.mc.player.isOnGround()) {
            return true;
        }
        return !bl;
    }

    @Override
    public String getInfoString() {
        return this.mode.get().name();
    }

    private void doJumpMode(PacketEvent.Send send) {
        if (!this.sendPackets) {
            this.sendPackets = true;
            this.sendTimer = this.mode.get() == Mode.Jump ? 6 : 4;
            this.attackPacket = (PlayerInteractEntityC2SPacket)send.packet;
            if (this.mode.get() == Mode.Jump) {
                this.mc.player.jump();
            } else {
                ((IVec3d)this.mc.player.getVelocity()).setY(0.25);
            }
            send.cancel();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Jump;
        public static final /* enum */ Mode MiniJump;
        public static final /* enum */ Mode Packet;

        static {
            Packet = new Mode();
            Jump = new Mode();
            MiniJump = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        private static Mode[] $values() {
            return new Mode[]{Packet, Jump, MiniJump};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

