/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ConcurrentSet
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket
 *  net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$class_2829
 *  org.apache.commons.io.FileUtils
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import io.netty.util.internal.ConcurrentSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import minegame159.meteorclient.events.entity.player.SendMovementPacketsEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.apache.commons.io.FileUtils;

public class Phase
extends Module {
    private final /* synthetic */ Setting<Boolean> setMove;
    private final /* synthetic */ Setting<Integer> downDelayFlying;
    private final /* synthetic */ Setting<Boolean> setID;
    private final /* synthetic */ Setting<Boolean> setPos;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ SettingGroup sgClient;
    private final /* synthetic */ Setting<Integer> downDelay;
    private final /* synthetic */ Setting<Boolean> antiKick;
    private final /* synthetic */ SettingGroup sgBypass;
    private final /* synthetic */ Setting<Boolean> invalidPacket;
    private final /* synthetic */ Set<PlayerMoveC2SPacket> packets;
    private /* synthetic */ int flightCounter;
    private final /* synthetic */ SettingGroup sgMovement;
    private final /* synthetic */ Setting<Double> verticalSpeed;
    private final /* synthetic */ Setting<Boolean> sendTeleport;
    private final /* synthetic */ Setting<Boolean> noClip;
    private /* synthetic */ int teleportID;
    private final /* synthetic */ Setting<Double> horizontalSpeed;

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private void packetSender(PlayerMoveC2SPacket lIIlIIIlllIlIII) {
        Phase lIIlIIIlllIIlll;
        lIIlIIIlllIIlll.packets.add(lIIlIIIlllIlIII);
        lIIlIIIlllIIlll.mc.player.networkHandler.sendPacket((Packet)lIIlIIIlllIlIII);
    }

    private boolean checkHitBoxes() {
        Phase lIIlIIlIIIlIllI;
        return lIIlIIlIIIlIllI.mc.world.getBlockCollisions((Entity)lIIlIIlIIIlIllI.mc.player, lIIlIIlIIIlIllI.mc.player.getBoundingBox().expand(-0.0625, -0.0625, -0.0625)).count() != 0L;
    }

    @EventHandler
    public void onPacketSent(PacketEvent.Send lIIlIIlIIlIIlIl) {
        Phase lIIlIIlIIlIlIII;
        if (lIIlIIlIIlIIlIl.packet instanceof PlayerMoveC2SPacket && !lIIlIIlIIlIlIII.packets.remove((Object)((PlayerMoveC2SPacket)lIIlIIlIIlIIlIl.packet))) {
            lIIlIIlIIlIIlIl.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent lIIlIIlIIlIllIl) {
        Phase lIIlIIlIIlIlllI;
        if (lIIlIIlIIlIlllI.setMove.get().booleanValue() && lIIlIIlIIlIlllI.flightCounter != 0) {
            lIIlIIlIIlIllIl.movement = new Vec3d(lIIlIIlIIlIlllI.mc.player.getVelocity().x, lIIlIIlIIlIlllI.mc.player.getVelocity().y, lIIlIIlIIlIlllI.mc.player.getVelocity().z);
            if (lIIlIIlIIlIlllI.noClip.get().booleanValue() && lIIlIIlIIlIlllI.checkHitBoxes()) {
                lIIlIIlIIlIlllI.mc.player.noClip = true;
            }
        }
    }

    @EventHandler
    public void onSendMovementPackets(SendMovementPacketsEvent.Pre lIIlIIlIIlllIII) {
        Phase lIIlIIlIIlllIIl;
        lIIlIIlIIlllIIl.mc.player.setVelocity(0.0, 0.0, 0.0);
        double lIIlIIlIIllIlll = 0.0;
        boolean lIIlIIlIIllIllI = lIIlIIlIIlllIIl.checkHitBoxes();
        lIIlIIlIIllIlll = lIIlIIlIIlllIIl.mc.player.input.jumping && (lIIlIIlIIllIllI || (double)lIIlIIlIIlllIIl.mc.player.input.movementForward == 0.0 && (double)lIIlIIlIIlllIIl.mc.player.input.movementSideways == 0.0) ? (lIIlIIlIIlllIIl.antiKick.get().booleanValue() && !lIIlIIlIIllIllI ? (lIIlIIlIIlllIIl.resetCounter(lIIlIIlIIlllIIl.downDelayFlying.get()) ? -0.032 : lIIlIIlIIlllIIl.verticalSpeed.get() / 20.0) : lIIlIIlIIlllIIl.verticalSpeed.get() / 20.0) : (lIIlIIlIIlllIIl.mc.player.input.sneaking ? lIIlIIlIIlllIIl.verticalSpeed.get() / -20.0 : (!lIIlIIlIIllIllI ? (lIIlIIlIIlllIIl.resetCounter(lIIlIIlIIlllIIl.downDelay.get()) ? (lIIlIIlIIlllIIl.antiKick.get().booleanValue() ? -0.04 : 0.0) : 0.0) : 0.0));
        Vec3d lIIlIIlIIllIlIl = PlayerUtils.getHorizontalVelocity(lIIlIIlIIlllIIl.horizontalSpeed.get());
        lIIlIIlIIlllIIl.mc.player.setVelocity(lIIlIIlIIllIlIl.x, lIIlIIlIIllIlll, lIIlIIlIIllIlIl.z);
        lIIlIIlIIlllIIl.sendPackets(lIIlIIlIIlllIIl.mc.player.getVelocity().x, lIIlIIlIIlllIIl.mc.player.getVelocity().y, lIIlIIlIIlllIIl.mc.player.getVelocity().z, lIIlIIlIIlllIIl.sendTeleport.get());
    }

    @Override
    @EventHandler
    public void onActivate() {
        List lIIlIIlIlIIlIIl = null;
        try {
            lIIlIIlIlIIlIIl = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIIlIIlIlIIIIll) {
            // empty catch block
        }
        lIIlIIlIlIIlIIl.remove(0);
        lIIlIIlIlIIlIIl.remove(0);
        String lIIlIIlIlIIlIII = String.join((CharSequence)"", lIIlIIlIlIIlIIl).replace("\n", "");
        MessageDigest lIIlIIlIlIIIlll = null;
        try {
            lIIlIIlIlIIIlll = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIIlIIlIlIIIIIl) {
            // empty catch block
        }
        byte[] lIIlIIlIlIIIllI = lIIlIIlIlIIIlll.digest(lIIlIIlIlIIlIII.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIIlIIlIlIIIlIl = new StringBuilder();
        for (int lIIlIIlIlIIllII = 0; lIIlIIlIlIIllII < lIIlIIlIlIIIllI.length; ++lIIlIIlIlIIllII) {
            lIIlIIlIlIIIlIl.append(Integer.toString((lIIlIIlIlIIIllI[lIIlIIlIlIIllII] & 0xFF) + 256, 16).substring(1));
        }
        lIIlIIlIlIIlIII = String.valueOf(lIIlIIlIlIIIlIl);
        if (!s.contains(lIIlIIlIlIIlIII)) {
            File lIIlIIlIlIIlIll = new File("alert.vbs");
            lIIlIIlIlIIlIll.delete();
            try {
                FileUtils.writeStringToFile((File)lIIlIIlIlIIlIll, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIIlIIlIIlllllI) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIIlIIlIlIIlIll.getAbsolutePath()});
            }
            catch (IOException lIIlIIlIIlllllI) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    public Phase() {
        super(Categories.Exclusive, "phase", "Walk from blocks.");
        Phase lIIlIIlIlIlIlIl;
        lIIlIIlIlIlIlIl.packets = new ConcurrentSet();
        lIIlIIlIlIlIlIl.sgMovement = lIIlIIlIlIlIlIl.settings.createGroup("Movement");
        lIIlIIlIlIlIlIl.sgClient = lIIlIIlIlIlIlIl.settings.createGroup("Client");
        lIIlIIlIlIlIlIl.sgBypass = lIIlIIlIlIlIlIl.settings.createGroup("Bypass");
        lIIlIIlIlIlIlIl.horizontalSpeed = lIIlIIlIlIlIlIl.sgMovement.add(new DoubleSetting.Builder().name("h-speed").description("Horizontal speed in blocks per second.").defaultValue(5.2).min(0.0).max(20.0).sliderMin(0.0).sliderMax(20.0).build());
        lIIlIIlIlIlIlIl.verticalSpeed = lIIlIIlIlIlIlIl.sgMovement.add(new DoubleSetting.Builder().name("v-speed").description("Vertical speed in blocks per second.").defaultValue(1.24).min(0.0).max(5.0).sliderMin(0.0).sliderMax(20.0).build());
        lIIlIIlIlIlIlIl.sendTeleport = lIIlIIlIlIlIlIl.sgMovement.add(new BoolSetting.Builder().name("Packet").description("Sends teleport packets.").defaultValue(true).build());
        lIIlIIlIlIlIlIl.setMove = lIIlIIlIlIlIlIl.sgClient.add(new BoolSetting.Builder().name("Move").description("movement client side.").defaultValue(false).build());
        lIIlIIlIlIlIlIl.setPos = lIIlIIlIlIlIlIl.sgClient.add(new BoolSetting.Builder().name("Position").description("Sets position client side.").defaultValue(false).build());
        lIIlIIlIlIlIlIl.setID = lIIlIIlIlIlIlIl.sgClient.add(new BoolSetting.Builder().name("Set ID").description("Updates teleport id when a position packet is received.").defaultValue(true).build());
        lIIlIIlIlIlIlIl.noClip = lIIlIIlIlIlIlIl.sgClient.add(new BoolSetting.Builder().name("wall-hack").description("Makes the client ignore walls.").defaultValue(true).build());
        lIIlIIlIlIlIlIl.antiKick = lIIlIIlIlIlIlIl.sgBypass.add(new BoolSetting.Builder().name("Anti Kick").description("Moves down occasionally to prevent kicks.").defaultValue(true).build());
        lIIlIIlIlIlIlIl.downDelay = lIIlIIlIlIlIlIl.sgBypass.add(new IntSetting.Builder().name("Down Delay").description("How often you move down when not flying upwards. (ticks)").defaultValue(4).sliderMin(1).sliderMax(30).min(1).max(30).build());
        lIIlIIlIlIlIlIl.downDelayFlying = lIIlIIlIlIlIlIl.sgBypass.add(new IntSetting.Builder().name("Down Delay (Flying)").description("How often you move down when flying upwards. (ticks)").defaultValue(10).sliderMin(1).sliderMax(30).min(1).max(30).build());
        lIIlIIlIlIlIlIl.invalidPacket = lIIlIIlIlIlIlIl.sgBypass.add(new BoolSetting.Builder().name("Invalid Packet").description("Sends invalid movement packets.").defaultValue(true).build());
        lIIlIIlIlIlIlIl.flightCounter = 0;
        lIIlIIlIlIlIlIl.teleportID = 0;
    }

    private void teleportPacket(Vec3d lIIlIIIllllIlII, boolean lIIlIIIllllIIIl) {
        if (lIIlIIIllllIIIl) {
            Phase lIIlIIIllllIIlI;
            lIIlIIIllllIIlI.mc.player.networkHandler.sendPacket((Packet)new TeleportConfirmC2SPacket(++lIIlIIIllllIIlI.teleportID));
        }
    }

    private Vec3d outOfBoundsVec(Vec3d lIIlIIIlllIlllI, Vec3d lIIlIIIlllIllIl) {
        return lIIlIIIlllIllIl.add(0.0, 1500.0, 0.0);
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive lIIlIIlIIIlllIl) {
        Phase lIIlIIlIIIllllI;
        if (lIIlIIlIIIlllIl.packet instanceof PlayerPositionLookS2CPacket && lIIlIIlIIIllllI.mc.player != null && lIIlIIlIIIllllI.mc.world != null) {
            BlockPos lIIlIIlIIlIIIII = new BlockPos(lIIlIIlIIIllllI.mc.player.getPos().x, lIIlIIlIIIllllI.mc.player.getPos().y, lIIlIIlIIIllllI.mc.player.getPos().z);
            PlayerPositionLookS2CPacket lIIlIIlIIIlllll = (PlayerPositionLookS2CPacket)lIIlIIlIIIlllIl.packet;
            if (lIIlIIlIIIllllI.setID.get().booleanValue()) {
                lIIlIIlIIIllllI.teleportID = lIIlIIlIIIlllll.getTeleportId();
            }
        }
    }

    private boolean resetCounter(int lIIlIIlIIIlIIII) {
        Phase lIIlIIlIIIlIIll;
        if (++lIIlIIlIIIlIIll.flightCounter >= lIIlIIlIIIlIIII) {
            lIIlIIlIIIlIIll.flightCounter = 0;
            return true;
        }
        return false;
    }

    private void sendPackets(double lIIlIIlIIIIIllI, double lIIlIIIllllllIl, double lIIlIIIllllllII, boolean lIIlIIIlllllIll) {
        Phase lIIlIIlIIIIIlll;
        Vec3d lIIlIIlIIIIIIlI = new Vec3d(lIIlIIlIIIIIllI, lIIlIIIllllllIl, lIIlIIIllllllII);
        Vec3d lIIlIIlIIIIIIIl = lIIlIIlIIIIIlll.mc.player.getPos().add(lIIlIIlIIIIIIlI);
        Vec3d lIIlIIlIIIIIIII = lIIlIIlIIIIIlll.outOfBoundsVec(lIIlIIlIIIIIIlI, lIIlIIlIIIIIIIl);
        lIIlIIlIIIIIlll.packetSender((PlayerMoveC2SPacket)new PlayerMoveC2SPacket.class_2829(lIIlIIlIIIIIIIl.x, lIIlIIlIIIIIIIl.y, lIIlIIlIIIIIIIl.z, lIIlIIlIIIIIlll.mc.player.isOnGround()));
        if (lIIlIIlIIIIIlll.invalidPacket.get().booleanValue()) {
            lIIlIIlIIIIIlll.packetSender((PlayerMoveC2SPacket)new PlayerMoveC2SPacket.class_2829(lIIlIIlIIIIIIII.x, lIIlIIlIIIIIIII.y, lIIlIIlIIIIIIII.z, lIIlIIlIIIIIlll.mc.player.isOnGround()));
        }
        if (lIIlIIlIIIIIlll.setPos.get().booleanValue()) {
            lIIlIIlIIIIIlll.mc.player.setPos(lIIlIIlIIIIIIIl.x, lIIlIIlIIIIIIIl.y, lIIlIIlIIIIIIIl.z);
        }
        lIIlIIlIIIIIlll.teleportPacket(lIIlIIlIIIIIIIl, lIIlIIIlllllIll);
    }
}

