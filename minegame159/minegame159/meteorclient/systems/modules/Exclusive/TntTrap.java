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
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.CityUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1802;
import net.minecraft.class_1812;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_3726;
import net.minecraft.class_3965;
import org.apache.commons.io.FileUtils;

public class TntTrap
extends Module {
    private final Setting<ShapeMode> shapeMode;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> sideColor;
    private final Setting<Boolean> pauseOnEat;
    private final SettingGroup sgRender;
    private List<class_2338> placePositions;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> render;
    private final Setting<Boolean> pauseOnDrink;
    private final Setting<SettingColor> lineColor;
    private final Setting<Boolean> head2;
    private final Setting<Integer> range;
    private boolean placed;
    private final Setting<Boolean> pauseOnMine;
    private final SettingGroup sgPause;
    private class_1657 target;

    public TntTrap() {
        super(Categories.Exclusive, "TNT-trap", "Anti Surround.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.sgPause = this.settings.createGroup("Pause");
        this.range = this.sgGeneral.add(new IntSetting.Builder().name("range").description("The radius players can be in to be targeted.").defaultValue(5).sliderMin(0).sliderMax(10).build());
        this.head2 = this.sgGeneral.add(new BoolSetting.Builder().name("head +1").description("Tnt head place.").defaultValue(false).build());
        this.pauseOnEat = this.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
        this.pauseOnDrink = this.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
        this.pauseOnMine = this.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(false).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        this.target = null;
        this.placePositions = new ArrayList<class_2338>();
    }

    private void add(class_2338 class_23382) {
        if (!this.placePositions.contains(class_23382) && this.mc.field_1687.method_8320(class_23382).method_26207().method_15800() && this.mc.field_1687.method_8628(class_2246.field_10375.method_9564(), class_23382, class_3726.method_16194())) {
            this.placePositions.add(class_23382);
        }
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
            if (-2 < 0) continue;
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
    private void onRender(RenderEvent renderEvent) {
        if (!this.render.get().booleanValue() || this.placePositions.isEmpty()) {
            return;
        }
        for (class_2338 class_23382 : this.placePositions) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, class_23382, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
        }
    }

    private void findPlacePos(class_1657 class_16572) {
        this.placePositions.clear();
        class_2338 class_23382 = class_16572.method_24515();
        this.add(class_23382.method_10069(0, 2, 0));
        if (this.head2.get().booleanValue()) {
            this.add(class_23382.method_10069(0, 3, 0));
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        this.target = CityUtils.getPlayerTarget(this.range.get().intValue());
        if (this.target == null || this.mc.field_1724.method_5739((class_1297)this.target) > (float)this.range.get().intValue()) {
            return;
        }
        this.placed = false;
        this.placePositions.clear();
        int n = -1;
        int n2 = -1;
        n = InvUtils.findItemInHotbar(class_1802.field_8626);
        n2 = InvUtils.findItemInHotbar(class_1802.field_8884);
        if (n2 == -1) {
            n2 = InvUtils.findItemInHotbar(class_1802.field_8814);
        }
        if (n == -1 || n2 == -1) {
            return;
        }
        if (this.mc.field_1724.method_6115() && (this.mc.field_1724.method_6047().method_7909().method_19263() || this.mc.field_1724.method_6079().method_7909().method_19263()) && this.pauseOnEat.get().booleanValue() || this.mc.field_1761.method_2923() && this.pauseOnMine.get().booleanValue() || this.mc.field_1724.method_6115() && (this.mc.field_1724.method_6047().method_7909() instanceof class_1812 || this.mc.field_1724.method_6079().method_7909() instanceof class_1812) && this.pauseOnDrink.get().booleanValue()) {
            return;
        }
        this.findPlacePos(this.target);
        for (int i = 0; i < this.placePositions.size(); ++i) {
            class_2338 class_23382 = this.placePositions.get(this.placePositions.size() - 1);
            if (BlockUtils.place(class_23382, class_1268.field_5808, n, this.rotate.get(), 50, true)) {
                this.placePositions.remove(class_23382);
                this.placed = true;
            }
            if (!this.placed || n2 == -1) continue;
            int n3 = this.mc.field_1724.field_7514.field_7545;
            this.mc.field_1724.field_7514.field_7545 = n2;
            this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, class_1268.field_5808, new class_3965(this.mc.field_1724.method_19538(), class_2350.field_11036, class_23382, true));
            this.mc.field_1724.field_7514.field_7545 = n3;
        }
        this.target = null;
    }
}

