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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_1657;
import net.minecraft.class_1922;
import net.minecraft.class_2199;
import net.minecraft.class_2554;
import net.minecraft.class_2561;
import net.minecraft.class_2585;
import net.minecraft.class_2588;
import net.minecraft.class_5251;
import org.apache.commons.io.FileUtils;

public class BurrowDetect
extends Module {
    private final Setting<ShapeMode> shapeMode;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final SettingGroup r;
    Set<class_1657> players;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> lineColor;
    private final Setting<SettingColor> sideColor;

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
        this.players.clear();
    }

    @EventHandler
    private void RenderEvent(RenderEvent renderEvent) {
        if (this.render.get().booleanValue()) {
            for (class_1657 class_16572 : this.players) {
                if (!this.mc.field_1687.method_18456().contains(class_16572)) continue;
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, class_16572.method_24515(), this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
            }
        }
    }

    public BurrowDetect() {
        super(Categories.Exclusive, "burrow-detect", "Burrow detect.");
        this.r = this.settings.createGroup("Render");
        this.render = this.r.add(new BoolSetting.Builder().name("render-burrow").defaultValue(true).build());
        this.shapeMode = this.r.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.r.add(new ColorSetting.Builder().name("side-color").description("The side color of the target block rendering.").defaultValue(new SettingColor(197, 137, 232, 10)).build());
        this.lineColor = this.r.add(new ColorSetting.Builder().name("line-color").description("The line color of the target block rendering.").defaultValue(new SettingColor(197, 137, 232)).build());
        this.players = new HashSet<class_1657>();
    }

    @EventHandler
    private void TickEventPos(TickEvent.Post post) {
        if (this.mc.field_1687 == null || this.mc.field_1724 == null) {
            return;
        }
        for (class_1657 class_16572 : new ArrayList<class_1657>(this.players)) {
            if (this.mc.field_1687.method_18456().contains(class_16572) && this.inBlock(class_16572)) continue;
            this.players.remove(class_16572);
        }
        for (class_1657 class_16572 : this.mc.field_1687.method_18456()) {
            if (class_16572.equals((Object)this.mc.field_1724) || this.players.contains(class_16572) || !class_16572.method_24828() || !Friends.get().attack(class_16572) || !this.inBlock(class_16572)) continue;
            class_2585 class_25852 = new class_2585("Player ");
            class_2554 class_25542 = (class_2554)class_16572.method_5477();
            class_25542.method_10862(class_25542.method_10866().method_27703(class_5251.method_27717((int)0xFF0000)));
            class_25852.method_10852((class_2561)class_25542);
            class_25852.method_27693(" burrowed in ");
            class_25852.method_10852((class_2561)new class_2588(class_16572.method_16212().method_26204().method_9539()));
            ChatUtils.moduleInfo(this, (class_2561)class_25852);
            this.players.add(class_16572);
        }
    }

    private boolean inBlock(class_1657 class_16572) {
        return class_16572.method_16212().method_26234((class_1922)this.mc.field_1687, class_16572.method_24515()) || class_16572.method_16212().method_26204().method_26161() && class_16572.method_16212().method_26214((class_1922)this.mc.field_1687, class_16572.method_24515()) > 10.0f || class_16572.method_16212().method_26204() instanceof class_2199;
    }
}

