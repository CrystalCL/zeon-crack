/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Exclusive.Ezz;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import org.apache.commons.io.FileUtils;

public class ExtraSurround
extends Module {
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Boolean> disableOnJump;
    private final Setting<Boolean> onlyOnGround;
    private final Setting<Integer> tickDelay;
    private final SettingGroup sgGeneral;
    private int ticks;
    private final Setting<Boolean> rotate;
    private final Setting<ecenter> center;
    private final Setting<antcry> anti;
    class_2338 pos;
    private final Setting<SurrMode> mode;

    private boolean e(int n, int n2, int n3) {
        return Ezz.BlockPlace(Ezz.SetRelative(n, n2, n3), InvUtils.findItemInHotbar(class_1802.field_8466), this.rotate.get());
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.ticks >= this.tickDelay.get()) {
            this.ticks = 0;
            if (this.center.get() == ecenter.legit) {
                double d = 0.0;
                double d2 = 0.0;
                class_243 class_2432 = this.mc.field_1724.method_19538();
                if (class_2432.field_1352 > 0.0 && this.gp(class_2432.field_1352) < 3L) {
                    d = 0.185;
                }
                if (class_2432.field_1352 > 0.0 && this.gp(class_2432.field_1352) > 6L) {
                    d = -0.185;
                }
                if (class_2432.field_1352 < 0.0 && this.gp(class_2432.field_1352) < 3L) {
                    d = -0.185;
                }
                if (class_2432.field_1352 < 0.0 && this.gp(class_2432.field_1352) > 6L) {
                    d = 0.185;
                }
                if (class_2432.field_1350 > 0.0 && this.gp(class_2432.field_1350) < 3L) {
                    d2 = 0.185;
                }
                if (class_2432.field_1350 > 0.0 && this.gp(class_2432.field_1350) > 6L) {
                    d2 = -0.185;
                }
                if (class_2432.field_1350 < 0.0 && this.gp(class_2432.field_1350) < 3L) {
                    d2 = -0.185;
                }
                if (class_2432.field_1350 < 0.0 && this.gp(class_2432.field_1350) > 6L) {
                    d2 = 0.185;
                }
                if (d != 0.0 || d2 != 0.0) {
                    double d3 = this.mc.field_1724.method_23317() + d;
                    double d4 = this.mc.field_1724.method_23321() + d2;
                    this.mc.field_1724.method_5814(d3, this.mc.field_1724.method_23318(), d4);
                    this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318(), this.mc.field_1724.method_23321(), this.mc.field_1724.method_24828()));
                    return;
                }
            }
            if (this.disableOnJump.get().booleanValue() && this.mc.field_1690.field_1903.method_1434()) {
                this.toggle();
                return;
            }
            if (this.onlyOnGround.get().booleanValue() && !this.mc.field_1724.method_24828()) {
                return;
            }
            if (this.mode.get() == SurrMode.Normal) {
                if (this.p(1, 0, 0)) {
                    return;
                }
                if (this.p(-1, 0, 0)) {
                    return;
                }
                if (this.p(0, 0, 1)) {
                    return;
                }
                if (this.p(0, 0, -1)) {
                    return;
                }
                if (this.anti.get() == antcry.Yes) {
                    if (this.e(1, -1, 0)) {
                        return;
                    }
                    if (this.e(-1, -1, 0)) {
                        return;
                    }
                    if (this.e(0, -1, 1)) {
                        return;
                    }
                    if (this.e(0, -1, -1)) {
                        return;
                    }
                } else if (this.anti.get() == antcry.No) {
                    if (this.p(1, -1, 0)) {
                        return;
                    }
                    if (this.p(-1, -1, 0)) {
                        return;
                    }
                    if (this.p(0, -1, 1)) {
                        return;
                    }
                    if (this.p(0, -1, -1)) {
                        return;
                    }
                }
            }
            if (this.mode.get() == SurrMode.AntiFall) {
                if (this.p(0, -1, 0)) {
                    return;
                }
                if (this.p(1, 0, 0)) {
                    return;
                }
                if (this.p(-1, 0, 0)) {
                    return;
                }
                if (this.p(0, 0, 1)) {
                    return;
                }
                if (this.p(0, 0, -1)) {
                    return;
                }
                if (this.anti.get() == antcry.Yes) {
                    if (this.e(1, -1, 0)) {
                        return;
                    }
                    if (this.e(-1, -1, 0)) {
                        return;
                    }
                    if (this.e(0, -1, 1)) {
                        return;
                    }
                    if (this.e(0, -1, -1)) {
                        return;
                    }
                } else if (this.anti.get() == antcry.No) {
                    if (this.p(1, -1, 0)) {
                        return;
                    }
                    if (this.p(-1, -1, 0)) {
                        return;
                    }
                    if (this.p(0, -1, 1)) {
                        return;
                    }
                    if (this.p(0, -1, -1)) {
                        return;
                    }
                }
            }
            if (this.mode.get() == SurrMode.Full) {
                if (this.p(0, -1, 0)) {
                    return;
                }
                if (this.p(1, 0, 0)) {
                    return;
                }
                if (this.p(-1, 0, 0)) {
                    return;
                }
                if (this.p(0, 0, 1)) {
                    return;
                }
                if (this.p(0, 0, -1)) {
                    return;
                }
                if (this.p(0, -1, 0)) {
                    return;
                }
                if (this.p(0, -2, 0)) {
                    return;
                }
                if (this.p(1, 0, 1)) {
                    return;
                }
                if (this.p(-1, 0, -1)) {
                    return;
                }
                if (this.p(-1, 0, 1)) {
                    return;
                }
                if (this.p(1, 0, -1)) {
                    return;
                }
                if (this.p(2, 0, 0)) {
                    return;
                }
                if (this.p(-2, 0, 0)) {
                    return;
                }
                if (this.p(0, 0, 2)) {
                    return;
                }
                if (this.p(0, 0, -2)) {
                    return;
                }
                if (this.p(1, 1, 0)) {
                    return;
                }
                if (this.p(-1, 1, 0)) {
                    return;
                }
                if (this.p(0, 1, 1)) {
                    return;
                }
                if (this.p(0, 1, -1)) {
                    return;
                }
                if (this.p(1, 2, 0)) {
                    return;
                }
                if (this.p(0, 2, 1)) {
                    return;
                }
                if (this.p(-1, 2, 0)) {
                    return;
                }
                if (this.p(0, 2, -1)) {
                    return;
                }
                if (this.p(0, 3, 0)) {
                    return;
                }
                if (this.p(1, 2, 0)) {
                    return;
                }
                if (this.p(0, 2, 0)) {
                    return;
                }
                if (this.anti.get() == antcry.Yes) {
                    if (this.e(1, -1, 0)) {
                        return;
                    }
                    if (this.e(-1, -1, 0)) {
                        return;
                    }
                    if (this.e(0, -1, 1)) {
                        return;
                    }
                    if (this.e(0, -1, -1)) {
                        return;
                    }
                } else if (this.anti.get() == antcry.No) {
                    if (this.p(1, -1, 0)) {
                        return;
                    }
                    if (this.p(-1, -1, 0)) {
                        return;
                    }
                    if (this.p(0, -1, 1)) {
                        return;
                    }
                    if (this.p(0, -1, -1)) {
                        return;
                    }
                }
                if (this.mode.get() == SurrMode.Double) {
                    if (this.p(0, -1, 0)) {
                        return;
                    }
                    if (this.p(1, 0, 0)) {
                        return;
                    }
                    if (this.p(-1, 0, 0)) {
                        return;
                    }
                    if (this.p(0, 0, 1)) {
                        return;
                    }
                    if (this.p(0, 0, -1)) {
                        return;
                    }
                    if (this.p(1, 1, 0)) {
                        return;
                    }
                    if (this.p(-1, 1, 0)) {
                        return;
                    }
                    if (this.p(0, 1, 1)) {
                        return;
                    }
                    if (this.p(0, 1, -1)) {
                        return;
                    }
                    if (this.anti.get() == antcry.Yes) {
                        if (this.e(1, -1, 0)) {
                            return;
                        }
                        if (this.e(-1, -1, 0)) {
                            return;
                        }
                        if (this.e(0, -1, 1)) {
                            return;
                        }
                        if (this.e(0, -1, -1)) {
                            return;
                        }
                    } else if (this.anti.get() == antcry.No) {
                        if (this.p(1, -1, 0)) {
                            return;
                        }
                        if (this.p(-1, -1, 0)) {
                            return;
                        }
                        if (this.p(0, -1, 1)) {
                            return;
                        }
                        if (this.p(0, -1, -1)) {
                            return;
                        }
                    }
                }
            }
        } else {
            ++this.ticks;
        }
    }

    public ExtraSurround() {
        super(Categories.Exclusive, "surround+", "Surround+");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.tickDelay = this.sgGeneral.add(new IntSetting.Builder().name("Delay").description("Delay per ticks.").defaultValue(1).min(0).max(20).sliderMin(0).sliderMax(20).build());
        this.center = this.sgGeneral.add(new EnumSetting.Builder().name("centerTP").description("Teleport to center block.").defaultValue(ecenter.legit).build());
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("Mode of the surround.").defaultValue(SurrMode.Normal).build());
        this.anti = this.sgGeneral.add(new EnumSetting.Builder().name("anti-crystal-aura").description("Anti Break your surround(place ender-chests).").defaultValue(antcry.Yes).build());
        this.onlyOnGround = this.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Works only when you standing on blocks.").defaultValue(false).build());
        this.disableOnJump = this.sgGeneral.add(new BoolSetting.Builder().name("disable-on-jump").description("Automatically disables when you jump.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the obsidian being placed.").defaultValue(false).build());
        this.pos = null;
    }

    private long gp(double d) {
        BigDecimal bigDecimal = BigDecimal.valueOf(d);
        BigDecimal bigDecimal2 = bigDecimal.remainder(BigDecimal.ONE);
        return Byte.valueOf(String.valueOf(String.valueOf(bigDecimal2).replace("0.", "").replace("-", "").charAt(0))).byteValue();
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
            if (false <= false) continue;
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
        this.ticks = 0;
        if (this.center.get() == ecenter.fast) {
            double d = 0.0;
            double d2 = 0.0;
            class_243 class_2432 = this.mc.field_1724.method_19538();
            if (class_2432.field_1352 > 0.0 && this.gp(class_2432.field_1352) < 3L) {
                d = 0.3;
            }
            if (class_2432.field_1352 > 0.0 && this.gp(class_2432.field_1352) > 6L) {
                d = -0.3;
            }
            if (class_2432.field_1352 < 0.0 && this.gp(class_2432.field_1352) < 3L) {
                d = -0.3;
            }
            if (class_2432.field_1352 < 0.0 && this.gp(class_2432.field_1352) > 6L) {
                d = 0.3;
            }
            if (class_2432.field_1350 > 0.0 && this.gp(class_2432.field_1350) < 3L) {
                d2 = 0.3;
            }
            if (class_2432.field_1350 > 0.0 && this.gp(class_2432.field_1350) > 6L) {
                d2 = -0.3;
            }
            if (class_2432.field_1350 < 0.0 && this.gp(class_2432.field_1350) < 3L) {
                d2 = -0.3;
            }
            if (class_2432.field_1350 < 0.0 && this.gp(class_2432.field_1350) > 6L) {
                d2 = 0.3;
            }
            if (d != 0.0 || d2 != 0.0) {
                double d3 = this.mc.field_1724.method_23317() + d;
                double d4 = this.mc.field_1724.method_23321() + d2;
                this.mc.field_1724.method_5814(d3, this.mc.field_1724.method_23318(), d4);
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318(), this.mc.field_1724.method_23321(), this.mc.field_1724.method_24828()));
            }
        }
    }

    private boolean p(int n, int n2, int n3) {
        return Ezz.BlockPlace(Ezz.SetRelative(n, n2, n3), InvUtils.findItemInHotbar(class_1802.field_8281), this.rotate.get());
    }

    public static final class SurrMode
    extends Enum<SurrMode> {
        public static final /* enum */ SurrMode Normal = new SurrMode();
        public static final /* enum */ SurrMode AntiFall;
        public static final /* enum */ SurrMode Double;
        private static final SurrMode[] $VALUES;
        public static final /* enum */ SurrMode Full;

        public static SurrMode[] values() {
            return (SurrMode[])$VALUES.clone();
        }

        private static SurrMode[] $values() {
            return new SurrMode[]{Normal, Double, AntiFall, Full};
        }

        static {
            Double = new SurrMode();
            AntiFall = new SurrMode();
            Full = new SurrMode();
            $VALUES = SurrMode.$values();
        }

        public static SurrMode valueOf(String string) {
            return Enum.valueOf(SurrMode.class, string);
        }
    }

    public static final class ecenter
    extends Enum<ecenter> {
        public static final /* enum */ ecenter legit;
        private static final ecenter[] $VALUES;
        public static final /* enum */ ecenter NoTP;
        public static final /* enum */ ecenter fast;

        public static ecenter valueOf(String string) {
            return Enum.valueOf(ecenter.class, string);
        }

        private static ecenter[] $values() {
            return new ecenter[]{fast, legit, NoTP};
        }

        public static ecenter[] values() {
            return (ecenter[])$VALUES.clone();
        }

        static {
            fast = new ecenter();
            legit = new ecenter();
            NoTP = new ecenter();
            $VALUES = ecenter.$values();
        }
    }

    public static final class antcry
    extends Enum<antcry> {
        public static final /* enum */ antcry Yes = new antcry();
        public static final /* enum */ antcry No = new antcry();
        private static final antcry[] $VALUES = antcry.$values();

        private static antcry[] $values() {
            return new antcry[]{Yes, No};
        }

        public static antcry[] values() {
            return (antcry[])$VALUES.clone();
        }

        public static antcry valueOf(String string) {
            return Enum.valueOf(antcry.class, string);
        }
    }
}

