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
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2585;
import net.minecraft.class_2661;
import net.minecraft.class_310;
import org.apache.commons.io.FileUtils;

public class AutoLeave
extends Module {
    private final SettingGroup sgGeneral;
    private final class_310 mc;
    private final Setting<Boolean> autoDisable;
    private final Setting<Mode> b;
    private final Setting<String> c;
    private final Setting<Integer> a;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));

    public AutoLeave() {
        super(Categories.Exclusive, "auto-leave", "Kick from the server if you have small totems");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.a = this.sgGeneral.add(new IntSetting.Builder().name("totem-count").description("Totem count when you kick").defaultValue(3).min(0).sliderMax(27).build());
        this.b = this.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("The mode.").defaultValue(Mode.MessageAndLeave).build());
        this.c = this.sgGeneral.add(new StringSetting.Builder().name("message").description("Send a chat message .").defaultValue("Buy ArtikHack https://discord.gg/DZTyBunvgS!").build());
        this.autoDisable = this.sgGeneral.add(new BoolSetting.Builder().name("auto-disable").description("Disable module after leave.").defaultValue(true).build());
        this.mc = class_310.method_1551();
    }

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
            if (3 >= 0) continue;
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
    private void onTick(TickEvent.Post post) {
        int n = InvUtils.findItemWithCount((class_1792)class_1802.field_8288).count;
        if (n <= this.a.get() && this.b.get() == Mode.MessageAndLeave && this.autoDisable.get().booleanValue()) {
            this.mc.field_1724.method_3142(this.c.get());
            this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585(String.valueOf(new StringBuilder().append("\u00a77\u00a7l\uff3b\u00a76\u00a7lAutoLeave\u00a77\u00a7l\uff3d \u00a7c Your totem's count <= ").append(this.a)))));
            this.toggle();
        } else if (n <= this.a.get() && this.b.get() == Mode.NoneAndLeave && this.autoDisable.get().booleanValue()) {
            this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585(String.valueOf(new StringBuilder().append("\u00a77\u00a7l\uff3b\u00a76\u00a7lAutoLeave\u00a77\u00a7l\uff3d \u00a7c Your totem's count <= ").append(this.a)))));
            this.toggle();
        } else if (n <= this.a.get() && this.b.get() == Mode.MessageAndLeave) {
            this.mc.field_1724.method_3142(this.c.get());
            this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585(String.valueOf(new StringBuilder().append("\u00a77\u00a7l\uff3b\u00a76\u00a7lAutoLeave\u00a77\u00a7l\uff3d \u00a7c Your totem's count <= ").append(this.a)))));
        } else if (n <= this.a.get() && this.b.get() == Mode.NoneAndLeave) {
            this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585(String.valueOf(new StringBuilder().append("\u00a77\u00a7l\uff3b\u00a76\u00a7lAutoLeave\u00a77\u00a7l\uff3d \u00a7c Your totem's count <= ").append(this.a)))));
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode MessageAndLeave = new Mode();
        public static final /* enum */ Mode NoneAndLeave = new Mode();
        private static final Mode[] $VALUES = Mode.$values();

        private static Mode[] $values() {
            return new Mode[]{MessageAndLeave, NoneAndLeave};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }
    }
}

