/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.Exclusive;

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
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.class_2338;
import net.minecraft.class_2620;
import org.apache.commons.io.FileUtils;

public class BreakInfo
extends Module {
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Boolean> legs;
    private final Setting<Boolean> face;
    private String m;
    private final SettingGroup q;
    private final Setting<Boolean> head;

    @Override
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
            if (null == null) continue;
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

    public BreakInfo() {
        super(Categories.Exclusive, "break-info", "surround break info.");
        this.q = this.settings.getDefaultGroup();
        this.head = this.q.add(new BoolSetting.Builder().name("head").defaultValue(true).build());
        this.face = this.q.add(new BoolSetting.Builder().name("face").defaultValue(true).build());
        this.legs = this.q.add(new BoolSetting.Builder().name("legs").defaultValue(true).build());
        this.m = " starting break ";
    }

    @EventHandler
    private void a(PacketEvent.Receive receive) {
        if (receive.packet instanceof class_2620) {
            class_2620 class_26202 = (class_2620)receive.packet;
            if (class_26202.method_11278() != 0) {
                return;
            }
            String string = this.mc.field_1687.method_8469(class_26202.method_11280()).method_5477().method_10851();
            class_2338 class_23382 = this.mc.field_1724.method_24515();
            class_2338 class_23383 = class_26202.method_11277();
            if (this.legs.get().booleanValue()) {
                for (class_2338 class_23384 : new class_2338[]{class_23382.method_10078(), class_23382.method_10067(), class_23382.method_10072(), class_23382.method_10095()}) {
                    if (class_23384.equals((Object)class_23383)) {
                        ChatUtils.moduleInfo(this, String.valueOf(new StringBuilder().append(string).append(this.m).append("legs")), new Object[0]);
                    }
                }
            }
            if (this.face.get().booleanValue()) {
                for (class_2338 class_23384 : new class_2338[]{class_23382.method_10084().method_10078(), class_23382.method_10084().method_10067(), class_23382.method_10084().method_10072(), class_23382.method_10084().method_10095()}) {
                    if (!class_23384.equals((Object)class_23383)) continue;
                    ChatUtils.moduleInfo(this, String.valueOf(new StringBuilder().append(string).append(this.m).append("face")), new Object[0]);
                    if (!false) continue;
                    return;
                }
            }
            if (this.head.get().booleanValue() && class_23382.method_10086(2).equals((Object)class_23383)) {
                ChatUtils.moduleInfo(this, String.valueOf(new StringBuilder().append(string).append(this.m).append("head")), new Object[0]);
            }
        }
    }
}

