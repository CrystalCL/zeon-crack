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
import minegame159.meteorclient.events.entity.EntityAddedEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_1657;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2530;
import net.minecraft.class_2596;
import net.minecraft.class_2620;
import net.minecraft.class_2626;
import net.minecraft.class_2846;
import net.minecraft.class_2868;
import net.minecraft.class_2885;
import net.minecraft.class_3965;
import org.apache.commons.io.FileUtils;

public class ExplosionProtector
extends Module {
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> tnt;
    private final Setting<Boolean> crystalHead;
    private final SettingGroup sgGeneral;

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
            if (2 >= 0) continue;
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
    private void a(PacketEvent.Receive receive) {
        List<class_2338> list;
        class_2338 class_23382;
        class_2620 class_26202;
        if (!this.online()) {
            return;
        }
        if (this.crystalHead.get().booleanValue() && receive.packet instanceof class_2620) {
            class_26202 = (class_2620)receive.packet;
            class_23382 = this.mc.field_1724.method_24515();
            list = Arrays.asList(class_23382.method_10084(), class_23382.method_10086(2), class_23382.method_10086(3));
            if (list.contains(class_26202.method_11277())) {
                list.forEach(this::lambda$a$0);
                this.mc.field_1687.method_18112().forEach(arg_0 -> this.lambda$a$1(list, arg_0));
            }
        }
        if (this.tnt.get().booleanValue() && receive.packet instanceof class_2626) {
            class_2338 class_23383;
            class_26202 = (class_2626)receive.packet;
            if (this.tnt.get().booleanValue() && class_26202.method_11308().method_26204() instanceof class_2530 && (list = Arrays.asList(class_23382 = this.mc.field_1724.method_24515(), class_23382.method_10074(), class_23382.method_10084(), class_23382.method_10086(2), class_23382.method_10086(3), class_23382.method_10078(), class_23382.method_10067(), class_23382.method_10095(), class_23382.method_10072(), class_23382.method_10084().method_10078(), class_23382.method_10084().method_10067(), class_23382.method_10084().method_10095(), class_23382.method_10084().method_10072(), class_23382.method_10086(2).method_10078(), class_23382.method_10086(2).method_10067(), class_23382.method_10086(2).method_10095(), class_23382.method_10086(2).method_10072())).contains(class_23383 = class_26202.method_11309())) {
                this.look(class_23383);
                this.mc.method_1562().method_2883((class_2596)new class_2846(class_2846.class_2847.field_12968, class_23383, class_2350.field_11036));
                this.mc.method_1562().method_2883((class_2596)new class_2846(class_2846.class_2847.field_12973, class_23383, class_2350.field_11036));
                this.place_obsidian(class_23383);
            }
        }
    }

    private boolean online() {
        return this.mc.field_1687 != null && this.mc.field_1724 != null && this.mc.field_1687.method_18456().size() > 1;
    }

    private void lambda$a$0(class_2338 class_23382) {
        this.place_obsidian(class_23382);
    }

    private void kill(class_1297 class_12972) {
        this.look(class_12972.method_24515());
        this.mc.field_1761.method_2918((class_1657)this.mc.field_1724, class_12972);
        class_12972.method_5650();
    }

    private void place_obsidian(class_2338 class_23382) {
        if (!BlockUtils.canPlace(class_23382)) {
            return;
        }
        if (this.mc.field_1724.method_24515().method_10264() - class_23382.method_10264() > 2) {
            return;
        }
        int n = InvUtils.findItemInHotbar(class_1802.field_8281);
        if (n > -1) {
            this.look(class_23382);
            int n2 = this.mc.field_1724.field_7514.field_7545;
            this.swap(n);
            this.mc.method_1562().method_2883((class_2596)new class_2885(class_1268.field_5808, new class_3965(this.mc.field_1724.method_19538(), class_2350.field_11033, class_23382, true)));
            this.swap(n2);
        }
    }

    private void lambda$a$1(List list, class_1297 class_12972) {
        if (class_12972 instanceof class_1511 && list.contains(class_12972.method_24515())) {
            this.kill(class_12972);
        }
    }

    private void swap(int n) {
        if (n != this.mc.field_1724.field_7514.field_7545) {
            this.mc.method_1562().method_2883((class_2596)new class_2868(n));
            this.mc.field_1724.field_7514.field_7545 = n;
        }
    }

    @EventHandler
    private void a(EntityAddedEvent entityAddedEvent) {
        class_2338 class_23382;
        if (!(this.online() && this.crystalHead.get().booleanValue() && entityAddedEvent.entity instanceof class_1511)) {
            return;
        }
        class_2338 class_23383 = this.mc.field_1724.method_24515();
        List<class_2338> list = Arrays.asList(class_23383.method_10086(2), class_23383.method_10086(3), class_23383.method_10086(4), class_23383.method_10086(2).method_10078(), class_23383.method_10086(2).method_10067(), class_23383.method_10086(2).method_10095(), class_23383.method_10086(2).method_10072());
        if (list.contains(class_23382 = entityAddedEvent.entity.method_24515())) {
            this.place_obsidian(class_23382.method_10074());
            this.kill(entityAddedEvent.entity);
            this.place_obsidian(class_23382);
            this.place_obsidian(class_23382.method_10084());
        }
    }

    public ExplosionProtector() {
        super(Categories.Exclusive, "explosion-protector", "Explosion protection.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.tnt = this.sgGeneral.add(new BoolSetting.Builder().name("anti-tnt-aura").description("Break near tnt blocks").defaultValue(true).build());
        this.crystalHead = this.sgGeneral.add(new BoolSetting.Builder().name("anti-crystal-head").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Look at block or crystal.").defaultValue(false).build());
    }

    private void look(class_2338 class_23382) {
        if (!this.rotate.get().booleanValue()) {
            return;
        }
        class_243 class_2432 = new class_243(0.0, 0.0, 0.0);
        ((IVec3d)class_2432).set((double)class_23382.method_10263() + 0.5, (double)class_23382.method_10264() + 0.5, (double)class_23382.method_10260() + 0.5);
        Rotations.rotate(Rotations.getYaw(class_2432), Rotations.getPitch(class_2432));
    }
}

