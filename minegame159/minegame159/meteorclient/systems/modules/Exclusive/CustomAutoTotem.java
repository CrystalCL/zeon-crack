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
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Exclusive.Ezz;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AutoTotem;
import minegame159.meteorclient.systems.modules.combat.OffhandExtra;
import minegame159.meteorclient.systems.modules.misc.OffhandCrash;
import minegame159.meteorclient.systems.modules.player.AutoMend;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1706;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1714;
import net.minecraft.class_1733;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2663;
import net.minecraft.class_437;
import net.minecraft.class_471;
import net.minecraft.class_476;
import net.minecraft.class_479;
import net.minecraft.class_495;
import org.apache.commons.io.FileUtils;

public class CustomAutoTotem
extends Module {
    private final SettingGroup sgDelay = this.settings.createGroup("Delay");
    private int ticks;
    private final Setting<Integer> tickDelay;
    private String totemCountString = "0";
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Mode> takeTotemMode = this.sgDelay.add(new EnumSetting.Builder().name("take-mode").description("The take totem in your hand.").defaultValue(Mode.NoAntiCheat).build());

    private void InvTotem() {
        if (this.ticks >= this.tickDelay.get()) {
            Object object;
            this.ticks = 0;
            if (this.takeTotemMode.get() == Mode.ForAntiCheat) {
                object = this.mc.field_1755;
                InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(class_1802.field_8288);
                if (findItemResult.count > 0) {
                    int n = Ezz.invIndexToSlotId(findItemResult.slot);
                    this.MOVE_TOTEM(n);
                }
            }
            if (this.takeTotemMode.get() == Mode.NoAntiCheat) {
                object = InvUtils.findItemWithCount(class_1802.field_8288);
                if (object.count > 0) {
                    int n = Ezz.invIndexToSlotId(object.slot);
                    this.MOVE_TOTEM(n);
                }
            }
        } else {
            ++this.ticks;
        }
    }

    private void SET_TOTEM_COUNT() {
        InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(class_1802.field_8288);
        this.totemCountString = Integer.toString(findItemResult.count);
    }

    @EventHandler(priority=0x7FFFFFFE)
    private void onTick(TickEvent.Pre pre) {
        class_437 class_4372 = this.mc.field_1755;
        if (this.mc.field_1724 == null || this.mc.field_1687 == null) {
            return;
        }
        this.SET_TOTEM_COUNT();
        if (this.mc.field_1724.method_6079().method_7909() == class_1802.field_8288) {
            return;
        }
        if (class_4372 instanceof class_476) {
            this.CHTotem();
            return;
        }
        if (class_4372 instanceof class_495) {
            this.SHTotem();
            return;
        }
        if (class_4372 instanceof class_479) {
            this.CRTotem();
            return;
        }
        this.InvTotem();
    }

    @Override
    public String getInfoString() {
        return this.totemCountString;
    }

    public CustomAutoTotem() {
        super(Categories.Exclusive, "custom-auto-totem", "Custom auto totem.");
        this.tickDelay = this.sgDelay.add(new IntSetting.Builder().name("take-delay").description("Totem's take delay.").defaultValue(1).min(0).sliderMax(20).build());
    }

    private void ATotem() {
        class_1706 class_17062 = (class_1706)((class_471)this.mc.field_1755).method_17577();
        if (class_17062 == null) {
            return;
        }
        int n = -1;
        for (int i = 0; i < class_17062.field_7761.size(); ++i) {
            if (((class_1735)class_17062.field_7761.get(i)).method_7677().method_7909() != class_1802.field_8288) continue;
            n = i;
            break;
        }
        if (n > -1) {
            this.MOVE_TOTEM(n);
            ((class_1735)class_17062.field_7761.get(n)).method_7673(new class_1799((class_1935)class_1802.field_8162));
        }
    }

    @Override
    public void onActivate() {
        this.ticks = 0;
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
            if (0 <= 0) continue;
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
        if (Modules.get().get(AutoMend.class).isActive()) {
            Modules.get().get(AutoMend.class).toggle();
        }
        if (Modules.get().get(OffhandExtra.class).isActive()) {
            Modules.get().get(OffhandExtra.class).toggle();
        }
        if (Modules.get().get(AutoTotem.class).isActive()) {
            Modules.get().get(AutoTotem.class).toggle();
        }
        if (Modules.get().get(OffhandCrash.class).isActive()) {
            Modules.get().get(OffhandCrash.class).toggle();
        }
    }

    private void CHTotem() {
        class_1707 class_17072 = (class_1707)((class_476)this.mc.field_1755).method_17577();
        if (class_17072 == null) {
            return;
        }
        int n = -1;
        for (int i = 0; i < class_17072.field_7761.size(); ++i) {
            if (((class_1735)class_17072.field_7761.get(i)).method_7677().method_7909() != class_1802.field_8288) continue;
            n = i;
            break;
        }
        if (n > -1) {
            this.MOVE_TOTEM(n);
            ((class_1735)class_17072.field_7761.get(n)).method_7673(new class_1799((class_1935)class_1802.field_8162));
        }
    }

    private void MOVE_TOTEM(int n) {
        this.mc.field_1761.method_2906(this.mc.field_1724.field_7512.field_7763, n, 40, class_1713.field_7791, (class_1657)this.mc.field_1724);
    }

    private void CRTotem() {
        class_1714 class_17142 = (class_1714)((class_479)this.mc.field_1755).method_17577();
        if (class_17142 == null) {
            return;
        }
        int n = -1;
        for (int i = 0; i < class_17142.field_7761.size(); ++i) {
            if (((class_1735)class_17142.field_7761.get(i)).method_7677().method_7909() != class_1802.field_8288) continue;
            n = i;
            break;
        }
        if (n > -1) {
            this.MOVE_TOTEM(n);
            ((class_1735)class_17142.field_7761.get(n)).method_7673(new class_1799((class_1935)class_1802.field_8162));
        }
    }

    @EventHandler(priority=0x7FFFFFFF)
    private void POPS(PacketEvent.Receive receive) {
        if (receive.packet instanceof class_2663) {
            class_2663 class_26632 = (class_2663)receive.packet;
            if (class_26632.method_11470() != 35) {
                return;
            }
            class_1297 class_12972 = class_26632.method_11469((class_1937)this.mc.field_1687);
            if (class_12972 == null || !class_12972.equals((Object)this.mc.field_1724)) {
                return;
            }
            if (this.mc.field_1755 instanceof class_476) {
                this.CHTotem();
            }
            if (this.mc.field_1755 instanceof class_495) {
                this.SHTotem();
            }
            if (this.mc.field_1755 instanceof class_479) {
                this.CRTotem();
            }
            if (this.mc.field_1755 instanceof class_471) {
                this.ATotem();
            }
        }
    }

    private void SHTotem() {
        class_1733 class_17332 = (class_1733)((class_495)this.mc.field_1755).method_17577();
        if (class_17332 == null) {
            return;
        }
        int n = -1;
        for (int i = 0; i < class_17332.field_7761.size(); ++i) {
            if (((class_1735)class_17332.field_7761.get(i)).method_7677().method_7909() != class_1802.field_8288) continue;
            n = i;
            break;
        }
        if (n > -1) {
            this.MOVE_TOTEM(n);
            ((class_1735)class_17332.field_7761.get(n)).method_7673(new class_1799((class_1935)class_1802.field_8162));
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode NoAntiCheat;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode ForAntiCheat;

        static {
            ForAntiCheat = new Mode();
            NoAntiCheat = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        private static Mode[] $values() {
            return new Mode[]{ForAntiCheat, NoAntiCheat};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

