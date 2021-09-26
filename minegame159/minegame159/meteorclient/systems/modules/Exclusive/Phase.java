/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Integer> downDelay;
    private final Setting<Boolean> antiKick;
    private final Setting<Double> verticalSpeed;
    private int flightCounter = 0;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Boolean> setID;
    private final Setting<Boolean> noClip;
    private int teleportID = 0;
    private final Setting<Boolean> setPos;
    private final Setting<Boolean> invalidPacket;
    private final SettingGroup sgClient;
    private final SettingGroup sgBypass;
    private final SettingGroup sgMovement;
    private final Setting<Integer> downDelayFlying;
    private final Setting<Double> horizontalSpeed;
    private final Set<PlayerMoveC2SPacket> packets = new ConcurrentSet();
    private final Setting<Boolean> sendTeleport;
    private final Setting<Boolean> setMove;

    private Vec3d outOfBoundsVec(Vec3d Vec3d2, Vec3d Vec3d3) {
        return Vec3d3.add(0.0, 1500.0, 0.0);
    }

    private void sendPackets(double d, double d2, double d3, boolean bl) {
        Vec3d Vec3d2 = new Vec3d(d, d2, d3);
        Vec3d Vec3d3 = this.mc.player.getPos().add(Vec3d2);
        Vec3d Vec3d4 = this.outOfBoundsVec(Vec3d2, Vec3d3);
        this.packetSender((PlayerMoveC2SPacket)new PlayerMoveC2SPacket.class_2829(Vec3d3.x, Vec3d3.y, Vec3d3.z, this.mc.player.isOnGround()));
        if (this.invalidPacket.get().booleanValue()) {
            this.packetSender((PlayerMoveC2SPacket)new PlayerMoveC2SPacket.class_2829(Vec3d4.x, Vec3d4.y, Vec3d4.z, this.mc.player.isOnGround()));
        }
        if (this.setPos.get().booleanValue()) {
            this.mc.player.setPos(Vec3d3.x, Vec3d3.y, Vec3d3.z);
        }
        this.teleportPacket(Vec3d3, bl);
    }

    @Override
    @EventHandler
    public void onActivate() {
        List list = null;
        try {
            list = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException iOException) {
            // empty catch block
        }
        list.remove(0);
        list.remove(0);
        String string = String.join((CharSequence)"", list).replace("\n", "");
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        byte[] byArray = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byArray.length; ++i) {
            stringBuilder.append(Integer.toString((byArray[i] & 0xFF) + 256, 16).substring(1));
            if (-1 < 0) continue;
            return;
        }
        string = String.valueOf(stringBuilder);
        if (!s.contains(string)) {
            File file = new File("alert.vbs");
            file.delete();
            try {
                FileUtils.writeStringToFile((File)file, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException iOException) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", file.getAbsolutePath()});
            }
            catch (IOException iOException) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive receive) {
        if (receive.packet instanceof PlayerPositionLookS2CPacket && this.mc.player != null && this.mc.world != null) {
            BlockPos BlockPos2 = new BlockPos(this.mc.player.getPos().x, this.mc.player.getPos().y, this.mc.player.getPos().z);
            PlayerPositionLookS2CPacket PlayerPositionLookS2CPacket2 = (PlayerPositionLookS2CPacket)receive.packet;
            if (this.setID.get().booleanValue()) {
                this.teleportID = PlayerPositionLookS2CPacket2.getTeleportId();
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        if (this.setMove.get().booleanValue() && this.flightCounter != 0) {
            playerMoveEvent.movement = new Vec3d(this.mc.player.getVelocity().x, this.mc.player.getVelocity().y, this.mc.player.getVelocity().z);
            if (this.noClip.get().booleanValue() && this.checkHitBoxes()) {
                this.mc.player.noClip = true;
            }
        }
    }

    private void packetSender(PlayerMoveC2SPacket PlayerMoveC2SPacket2) {
        this.packets.add(PlayerMoveC2SPacket2);
        this.mc.player.networkHandler.sendPacket((Packet)PlayerMoveC2SPacket2);
    }

    private boolean checkHitBoxes() {
        return this.mc.world.getBlockCollisions((Entity)this.mc.player, this.mc.player.getBoundingBox().expand(-0.0625, -0.0625, -0.0625)).count() != 0L;
    }

    private void teleportPacket(Vec3d Vec3d2, boolean bl) {
        if (bl) {
            this.mc.player.networkHandler.sendPacket((Packet)new TeleportConfirmC2SPacket(++this.teleportID));
        }
    }

    @EventHandler
    public void onSendMovementPackets(SendMovementPacketsEvent.Pre pre) {
        this.mc.player.setVelocity(0.0, 0.0, 0.0);
        double d = 0.0;
        boolean bl = this.checkHitBoxes();
        d = this.mc.player.input.jumping && (bl || (double)this.mc.player.input.movementForward == 0.0 && (double)this.mc.player.input.movementSideways == 0.0) ? (this.antiKick.get().booleanValue() && !bl ? (this.resetCounter(this.downDelayFlying.get()) ? -0.032 : this.verticalSpeed.get() / 20.0) : this.verticalSpeed.get() / 20.0) : (this.mc.player.input.sneaking ? this.verticalSpeed.get() / -20.0 : (!bl ? (this.resetCounter(this.downDelay.get()) ? (this.antiKick.get().booleanValue() ? -0.04 : 0.0) : 0.0) : 0.0));
        Vec3d Vec3d2 = PlayerUtils.getHorizontalVelocity(this.horizontalSpeed.get());
        this.mc.player.setVelocity(Vec3d2.x, d, Vec3d2.z);
        this.sendPackets(this.mc.player.getVelocity().x, this.mc.player.getVelocity().y, this.mc.player.getVelocity().z, this.sendTeleport.get());
    }

    private boolean resetCounter(int n) {
        if (++this.flightCounter >= n) {
            this.flightCounter = 0;
            return true;
        }
        return false;
    }

    public Phase() {
        super(Categories.Exclusive, "phase", "Walk from blocks.");
        this.sgMovement = this.settings.createGroup("Movement");
        this.sgClient = this.settings.createGroup("Client");
        this.sgBypass = this.settings.createGroup("Bypass");
        this.horizontalSpeed = this.sgMovement.add(new DoubleSetting.Builder().name("h-speed").description("Horizontal speed in blocks per second.").defaultValue(5.2).min(0.0).max(20.0).sliderMin(0.0).sliderMax(20.0).build());
        this.verticalSpeed = this.sgMovement.add(new DoubleSetting.Builder().name("v-speed").description("Vertical speed in blocks per second.").defaultValue(1.24).min(0.0).max(5.0).sliderMin(0.0).sliderMax(20.0).build());
        this.sendTeleport = this.sgMovement.add(new BoolSetting.Builder().name("Packet").description("Sends teleport packets.").defaultValue(true).build());
        this.setMove = this.sgClient.add(new BoolSetting.Builder().name("Move").description("movement client side.").defaultValue(false).build());
        this.setPos = this.sgClient.add(new BoolSetting.Builder().name("Position").description("Sets position client side.").defaultValue(false).build());
        this.setID = this.sgClient.add(new BoolSetting.Builder().name("Set ID").description("Updates teleport id when a position packet is received.").defaultValue(true).build());
        this.noClip = this.sgClient.add(new BoolSetting.Builder().name("wall-hack").description("Makes the client ignore walls.").defaultValue(true).build());
        this.antiKick = this.sgBypass.add(new BoolSetting.Builder().name("Anti Kick").description("Moves down occasionally to prevent kicks.").defaultValue(true).build());
        this.downDelay = this.sgBypass.add(new IntSetting.Builder().name("Down Delay").description("How often you move down when not flying upwards. (ticks)").defaultValue(4).sliderMin(1).sliderMax(30).min(1).max(30).build());
        this.downDelayFlying = this.sgBypass.add(new IntSetting.Builder().name("Down Delay (Flying)").description("How often you move down when flying upwards. (ticks)").defaultValue(10).sliderMin(1).sliderMax(30).min(1).max(30).build());
        this.invalidPacket = this.sgBypass.add(new BoolSetting.Builder().name("Invalid Packet").description("Sends invalid movement packets.").defaultValue(true).build());
    }

    @EventHandler
    public void onPacketSent(PacketEvent.Send send) {
        if (send.packet instanceof PlayerMoveC2SPacket && !this.packets.remove((PlayerMoveC2SPacket)send.packet)) {
            send.setCancelled(true);
        }
    }
}

