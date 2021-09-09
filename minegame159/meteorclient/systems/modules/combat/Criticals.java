/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.world.World
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket$class_2825
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
 *  net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
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
    private final /* synthetic */ Setting<Boolean> crystals;
    private /* synthetic */ int sendTimer;
    private /* synthetic */ PlayerInteractEntityC2SPacket attackPacket;
    private final /* synthetic */ Setting<Boolean> ka;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Mode> mode;
    private /* synthetic */ HandSwingC2SPacket swingPacket;
    private /* synthetic */ boolean sendPackets;

    @EventHandler
    private void onSendPacket(PacketEvent.Send lllllllllllllllllIllIlIlIIllIlIl) {
        Criticals lllllllllllllllllIllIlIlIIllIlII;
        if (lllllllllllllllllIllIlIlIIllIlIl.packet instanceof PlayerInteractEntityC2SPacket && ((PlayerInteractEntityC2SPacket)lllllllllllllllllIllIlIlIIllIlIl.packet).getType() == PlayerInteractEntityC2SPacket.class_2825.ATTACK) {
            if (((PlayerInteractEntityC2SPacket)lllllllllllllllllIllIlIlIIllIlIl.packet).getEntity((World)lllllllllllllllllIllIlIlIIllIlII.mc.world) != Modules.get().get(KillAura.class).getTarget() && lllllllllllllllllIllIlIlIIllIlII.ka.get().booleanValue()) {
                return;
            }
            if (((PlayerInteractEntityC2SPacket)lllllllllllllllllIllIlIlIIllIlIl.packet).getEntity((World)lllllllllllllllllIllIlIlIIllIlII.mc.world) instanceof EndCrystalEntity && !lllllllllllllllllIllIlIlIIllIlII.crystals.get().booleanValue()) {
                return;
            }
            if (lllllllllllllllllIllIlIlIIllIlII.skipCrit()) {
                return;
            }
            if (lllllllllllllllllIllIlIlIIllIlII.mode.get() == Mode.Packet) {
                lllllllllllllllllIllIlIlIIllIlII.doPacketMode();
            } else {
                lllllllllllllllllIllIlIlIIllIlII.doJumpMode(lllllllllllllllllIllIlIlIIllIlIl);
            }
        } else if (lllllllllllllllllIllIlIlIIllIlIl.packet instanceof HandSwingC2SPacket && lllllllllllllllllIllIlIlIIllIlII.mode.get() != Mode.Packet) {
            if (lllllllllllllllllIllIlIlIIllIlII.skipCrit()) {
                return;
            }
            lllllllllllllllllIllIlIlIIllIlII.doJumpModeSwing(lllllllllllllllllIllIlIlIIllIlIl);
        }
    }

    @Override
    public void onActivate() {
        lllllllllllllllllIllIlIlIIlllIIl.attackPacket = null;
        lllllllllllllllllIllIlIlIIlllIIl.swingPacket = null;
        lllllllllllllllllIllIlIlIIlllIIl.sendPackets = false;
        lllllllllllllllllIllIlIlIIlllIIl.sendTimer = 0;
    }

    private void doJumpModeSwing(PacketEvent.Send lllllllllllllllllIllIlIlIIIlIIll) {
        Criticals lllllllllllllllllIllIlIlIIIlIIlI;
        if (lllllllllllllllllIllIlIlIIIlIIlI.sendPackets && lllllllllllllllllIllIlIlIIIlIIlI.swingPacket == null) {
            lllllllllllllllllIllIlIlIIIlIIlI.swingPacket = (HandSwingC2SPacket)lllllllllllllllllIllIlIlIIIlIIll.packet;
            lllllllllllllllllIllIlIlIIIlIIll.cancel();
        }
    }

    @Override
    public String getInfoString() {
        Criticals lllllllllllllllllIllIlIlIIIIlIII;
        return lllllllllllllllllIllIlIlIIIIlIII.mode.get().name();
    }

    private void doPacketMode() {
        Criticals lllllllllllllllllIllIlIlIIlIIIlI;
        double lllllllllllllllllIllIlIlIIlIIlll = lllllllllllllllllIllIlIlIIlIIIlI.mc.player.getX();
        double lllllllllllllllllIllIlIlIIlIIllI = lllllllllllllllllIllIlIlIIlIIIlI.mc.player.getY();
        double lllllllllllllllllIllIlIlIIlIIlIl = lllllllllllllllllIllIlIlIIlIIIlI.mc.player.getZ();
        PositionOnly lllllllllllllllllIllIlIlIIlIIlII = new PositionOnly(lllllllllllllllllIllIlIlIIlIIlll, lllllllllllllllllIllIlIlIIlIIllI + 0.0625, lllllllllllllllllIllIlIlIIlIIlIl, false);
        PositionOnly lllllllllllllllllIllIlIlIIlIIIll = new PositionOnly(lllllllllllllllllIllIlIlIIlIIlll, lllllllllllllllllIllIlIlIIlIIllI, lllllllllllllllllIllIlIlIIlIIlIl, false);
        ((IPlayerMoveC2SPacket)lllllllllllllllllIllIlIlIIlIIlII).setTag(1337);
        ((IPlayerMoveC2SPacket)lllllllllllllllllIllIlIlIIlIIIll).setTag(1337);
        lllllllllllllllllIllIlIlIIlIIIlI.mc.player.networkHandler.sendPacket((Packet)lllllllllllllllllIllIlIlIIlIIlII);
        lllllllllllllllllIllIlIlIIlIIIlI.mc.player.networkHandler.sendPacket((Packet)lllllllllllllllllIllIlIlIIlIIIll);
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIllIlIlIIllIIII) {
        Criticals lllllllllllllllllIllIlIlIIllIIIl;
        if (lllllllllllllllllIllIlIlIIllIIIl.sendPackets) {
            if (lllllllllllllllllIllIlIlIIllIIIl.sendTimer <= 0) {
                lllllllllllllllllIllIlIlIIllIIIl.sendPackets = false;
                if (lllllllllllllllllIllIlIlIIllIIIl.attackPacket == null || lllllllllllllllllIllIlIlIIllIIIl.swingPacket == null) {
                    return;
                }
                lllllllllllllllllIllIlIlIIllIIIl.mc.getNetworkHandler().sendPacket((Packet)lllllllllllllllllIllIlIlIIllIIIl.attackPacket);
                lllllllllllllllllIllIlIlIIllIIIl.mc.getNetworkHandler().sendPacket((Packet)lllllllllllllllllIllIlIlIIllIIIl.swingPacket);
                lllllllllllllllllIllIlIlIIllIIIl.attackPacket = null;
                lllllllllllllllllIllIlIlIIllIIIl.swingPacket = null;
            } else {
                --lllllllllllllllllIllIlIlIIllIIIl.sendTimer;
            }
        }
    }

    private boolean skipCrit() {
        Criticals lllllllllllllllllIllIlIlIIIIllII;
        boolean lllllllllllllllllIllIlIlIIIIllIl;
        boolean bl = lllllllllllllllllIllIlIlIIIIllIl = !lllllllllllllllllIllIlIlIIIIllII.mc.player.isSubmergedInWater() && !lllllllllllllllllIllIlIlIIIIllII.mc.player.isInLava() && !lllllllllllllllllIllIlIlIIIIllII.mc.player.isClimbing();
        if (!lllllllllllllllllIllIlIlIIIIllII.mc.player.isOnGround()) {
            return true;
        }
        return !lllllllllllllllllIllIlIlIIIIllIl;
    }

    private void doJumpMode(PacketEvent.Send lllllllllllllllllIllIlIlIIIlIlll) {
        Criticals lllllllllllllllllIllIlIlIIIllIII;
        if (!lllllllllllllllllIllIlIlIIIllIII.sendPackets) {
            lllllllllllllllllIllIlIlIIIllIII.sendPackets = true;
            lllllllllllllllllIllIlIlIIIllIII.sendTimer = lllllllllllllllllIllIlIlIIIllIII.mode.get() == Mode.Jump ? 6 : 4;
            lllllllllllllllllIllIlIlIIIllIII.attackPacket = (PlayerInteractEntityC2SPacket)lllllllllllllllllIllIlIlIIIlIlll.packet;
            if (lllllllllllllllllIllIlIlIIIllIII.mode.get() == Mode.Jump) {
                lllllllllllllllllIllIlIlIIIllIII.mc.player.jump();
            } else {
                ((IVec3d)lllllllllllllllllIllIlIlIIIllIII.mc.player.getVelocity()).setY(0.25);
            }
            lllllllllllllllllIllIlIlIIIlIlll.cancel();
        }
    }

    public Criticals() {
        super(Categories.Combat, "criticals", "Performs critical attacks when you hit your target.");
        Criticals lllllllllllllllllIllIlIlIIllllII;
        lllllllllllllllllIllIlIlIIllllII.sgGeneral = lllllllllllllllllIllIlIlIIllllII.settings.getDefaultGroup();
        lllllllllllllllllIllIlIlIIllllII.mode = lllllllllllllllllIllIlIlIIllllII.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode on how Criticals will function.").defaultValue(Mode.Packet).build());
        lllllllllllllllllIllIlIlIIllllII.ka = lllllllllllllllllIllIlIlIIllllII.sgGeneral.add(new BoolSetting.Builder().name("only-killaura").description("Only performs crits when using killaura.").defaultValue(false).build());
        lllllllllllllllllIllIlIlIIllllII.crystals = lllllllllllllllllIllIlIlIIllllII.sgGeneral.add(new BoolSetting.Builder().name("crystals").description("Wether to crit crystals or not.").defaultValue(false).build());
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode MiniJump;
        public static final /* synthetic */ /* enum */ Mode Packet;
        public static final /* synthetic */ /* enum */ Mode Jump;
        private static final /* synthetic */ Mode[] $VALUES;

        private Mode() {
            Mode llllIIlllIIIIII;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Packet = new Mode();
            Jump = new Mode();
            MiniJump = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String llllIIlllIIIllI) {
            return Enum.valueOf(Mode.class, llllIIlllIIIllI);
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Packet, Jump, MiniJump};
        }
    }
}

