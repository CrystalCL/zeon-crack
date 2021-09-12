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
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2708;
import net.minecraft.class_2793;
import net.minecraft.class_2828;
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
    private final Set<class_2828> packets = new ConcurrentSet();
    private final Setting<Boolean> sendTeleport;
    private final Setting<Boolean> setMove;

    private class_243 outOfBoundsVec(class_243 class_2432, class_243 class_2433) {
        return class_2433.method_1031(0.0, 1500.0, 0.0);
    }

    private void sendPackets(double d, double d2, double d3, boolean bl) {
        class_243 class_2432 = new class_243(d, d2, d3);
        class_243 class_2433 = this.mc.field_1724.method_19538().method_1019(class_2432);
        class_243 class_2434 = this.outOfBoundsVec(class_2432, class_2433);
        this.packetSender((class_2828)new class_2828.class_2829(class_2433.field_1352, class_2433.field_1351, class_2433.field_1350, this.mc.field_1724.method_24828()));
        if (this.invalidPacket.get().booleanValue()) {
            this.packetSender((class_2828)new class_2828.class_2829(class_2434.field_1352, class_2434.field_1351, class_2434.field_1350, this.mc.field_1724.method_24828()));
        }
        if (this.setPos.get().booleanValue()) {
            this.mc.field_1724.method_23327(class_2433.field_1352, class_2433.field_1351, class_2433.field_1350);
        }
        this.teleportPacket(class_2433, bl);
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
        if (receive.packet instanceof class_2708 && this.mc.field_1724 != null && this.mc.field_1687 != null) {
            class_2338 class_23382 = new class_2338(this.mc.field_1724.method_19538().field_1352, this.mc.field_1724.method_19538().field_1351, this.mc.field_1724.method_19538().field_1350);
            class_2708 class_27082 = (class_2708)receive.packet;
            if (this.setID.get().booleanValue()) {
                this.teleportID = class_27082.method_11737();
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        if (this.setMove.get().booleanValue() && this.flightCounter != 0) {
            playerMoveEvent.movement = new class_243(this.mc.field_1724.method_18798().field_1352, this.mc.field_1724.method_18798().field_1351, this.mc.field_1724.method_18798().field_1350);
            if (this.noClip.get().booleanValue() && this.checkHitBoxes()) {
                this.mc.field_1724.field_5960 = true;
            }
        }
    }

    private void packetSender(class_2828 class_28282) {
        this.packets.add(class_28282);
        this.mc.field_1724.field_3944.method_2883((class_2596)class_28282);
    }

    private boolean checkHitBoxes() {
        return this.mc.field_1687.method_20812((class_1297)this.mc.field_1724, this.mc.field_1724.method_5829().method_1009(-0.0625, -0.0625, -0.0625)).count() != 0L;
    }

    private void teleportPacket(class_243 class_2432, boolean bl) {
        if (bl) {
            this.mc.field_1724.field_3944.method_2883((class_2596)new class_2793(++this.teleportID));
        }
    }

    @EventHandler
    public void onSendMovementPackets(SendMovementPacketsEvent.Pre pre) {
        this.mc.field_1724.method_18800(0.0, 0.0, 0.0);
        double d = 0.0;
        boolean bl = this.checkHitBoxes();
        d = this.mc.field_1724.field_3913.field_3904 && (bl || (double)this.mc.field_1724.field_3913.field_3905 == 0.0 && (double)this.mc.field_1724.field_3913.field_3907 == 0.0) ? (this.antiKick.get().booleanValue() && !bl ? (this.resetCounter(this.downDelayFlying.get()) ? -0.032 : this.verticalSpeed.get() / 20.0) : this.verticalSpeed.get() / 20.0) : (this.mc.field_1724.field_3913.field_3903 ? this.verticalSpeed.get() / -20.0 : (!bl ? (this.resetCounter(this.downDelay.get()) ? (this.antiKick.get().booleanValue() ? -0.04 : 0.0) : 0.0) : 0.0));
        class_243 class_2432 = PlayerUtils.getHorizontalVelocity(this.horizontalSpeed.get());
        this.mc.field_1724.method_18800(class_2432.field_1352, d, class_2432.field_1350);
        this.sendPackets(this.mc.field_1724.method_18798().field_1352, this.mc.field_1724.method_18798().field_1351, this.mc.field_1724.method_18798().field_1350, this.sendTeleport.get());
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
        if (send.packet instanceof class_2828 && !this.packets.remove((class_2828)send.packet)) {
            send.setCancelled(true);
        }
    }
}

