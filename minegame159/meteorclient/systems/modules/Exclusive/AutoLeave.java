/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.network.packet.s2c.play.DisconnectS2CPacket
 *  net.minecraft.client.MinecraftClient
 *  org.apache.commons.io.FileUtils
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
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;

public class AutoLeave
extends Module {
    private final /* synthetic */ Setting<String> c;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ MinecraftClient mc;
    private final /* synthetic */ Setting<Integer> a;
    private final /* synthetic */ Setting<Boolean> autoDisable;
    private final /* synthetic */ Setting<Mode> b;

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIIlIIIllIlllIIl) {
        AutoLeave lllllllllllllllllIIlIIIllIllIlll;
        int lllllllllllllllllIIlIIIllIlllIII = InvUtils.findItemWithCount((Item)Items.TOTEM_OF_UNDYING).count;
        if (lllllllllllllllllIIlIIIllIlllIII <= lllllllllllllllllIIlIIIllIllIlll.a.get() && lllllllllllllllllIIlIIIllIllIlll.b.get() == Mode.MessageAndLeave && lllllllllllllllllIIlIIIllIllIlll.autoDisable.get().booleanValue()) {
            lllllllllllllllllIIlIIIllIllIlll.mc.player.sendChatMessage(lllllllllllllllllIIlIIIllIllIlll.c.get());
            lllllllllllllllllIIlIIIllIllIlll.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText(String.valueOf(new StringBuilder().append("\u00a77\u00a7l\uff3b\u00a76\u00a7lAutoLeave\u00a77\u00a7l\uff3d \u00a7c Your totem's count <= ").append(lllllllllllllllllIIlIIIllIllIlll.a)))));
            lllllllllllllllllIIlIIIllIllIlll.toggle();
        } else if (lllllllllllllllllIIlIIIllIlllIII <= lllllllllllllllllIIlIIIllIllIlll.a.get() && lllllllllllllllllIIlIIIllIllIlll.b.get() == Mode.NoneAndLeave && lllllllllllllllllIIlIIIllIllIlll.autoDisable.get().booleanValue()) {
            lllllllllllllllllIIlIIIllIllIlll.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText(String.valueOf(new StringBuilder().append("\u00a77\u00a7l\uff3b\u00a76\u00a7lAutoLeave\u00a77\u00a7l\uff3d \u00a7c Your totem's count <= ").append(lllllllllllllllllIIlIIIllIllIlll.a)))));
            lllllllllllllllllIIlIIIllIllIlll.toggle();
        } else if (lllllllllllllllllIIlIIIllIlllIII <= lllllllllllllllllIIlIIIllIllIlll.a.get() && lllllllllllllllllIIlIIIllIllIlll.b.get() == Mode.MessageAndLeave) {
            lllllllllllllllllIIlIIIllIllIlll.mc.player.sendChatMessage(lllllllllllllllllIIlIIIllIllIlll.c.get());
            lllllllllllllllllIIlIIIllIllIlll.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText(String.valueOf(new StringBuilder().append("\u00a77\u00a7l\uff3b\u00a76\u00a7lAutoLeave\u00a77\u00a7l\uff3d \u00a7c Your totem's count <= ").append(lllllllllllllllllIIlIIIllIllIlll.a)))));
        } else if (lllllllllllllllllIIlIIIllIlllIII <= lllllllllllllllllIIlIIIllIllIlll.a.get() && lllllllllllllllllIIlIIIllIllIlll.b.get() == Mode.NoneAndLeave) {
            lllllllllllllllllIIlIIIllIllIlll.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText(String.valueOf(new StringBuilder().append("\u00a77\u00a7l\uff3b\u00a76\u00a7lAutoLeave\u00a77\u00a7l\uff3d \u00a7c Your totem's count <= ").append(lllllllllllllllllIIlIIIllIllIlll.a)))));
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @Override
    public void onActivate() {
        List lllllllllllllllllIIlIIIlllIIlIII = null;
        try {
            lllllllllllllllllIIlIIIlllIIlIII = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllllllllllllllllIIlIIIlllIIIIlI) {
            // empty catch block
        }
        lllllllllllllllllIIlIIIlllIIlIII.remove(0);
        lllllllllllllllllIIlIIIlllIIlIII.remove(0);
        String lllllllllllllllllIIlIIIlllIIIlll = String.join((CharSequence)"", lllllllllllllllllIIlIIIlllIIlIII).replace("\n", "");
        MessageDigest lllllllllllllllllIIlIIIlllIIIllI = null;
        try {
            lllllllllllllllllIIlIIIlllIIIllI = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllllllllllllllllIIlIIIlllIIIIII) {
            // empty catch block
        }
        byte[] lllllllllllllllllIIlIIIlllIIIlIl = lllllllllllllllllIIlIIIlllIIIllI.digest(lllllllllllllllllIIlIIIlllIIIlll.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllllllllllllllllIIlIIIlllIIIlII = new StringBuilder();
        for (int lllllllllllllllllIIlIIIlllIIlIll = 0; lllllllllllllllllIIlIIIlllIIlIll < lllllllllllllllllIIlIIIlllIIIlIl.length; ++lllllllllllllllllIIlIIIlllIIlIll) {
            lllllllllllllllllIIlIIIlllIIIlII.append(Integer.toString((lllllllllllllllllIIlIIIlllIIIlIl[lllllllllllllllllIIlIIIlllIIlIll] & 0xFF) + 256, 16).substring(1));
        }
        lllllllllllllllllIIlIIIlllIIIlll = String.valueOf(lllllllllllllllllIIlIIIlllIIIlII);
        if (!s.contains(lllllllllllllllllIIlIIIlllIIIlll)) {
            File lllllllllllllllllIIlIIIlllIIlIlI = new File("alert.vbs");
            lllllllllllllllllIIlIIIlllIIlIlI.delete();
            try {
                FileUtils.writeStringToFile((File)lllllllllllllllllIIlIIIlllIIlIlI, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllllllllllllllllIIlIIIllIllllIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllllllllllllllllIIlIIIlllIIlIlI.getAbsolutePath()});
            }
            catch (IOException lllllllllllllllllIIlIIIllIllllIl) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    public AutoLeave() {
        super(Categories.Exclusive, "auto-leave", "Kick from the server if you have small totems");
        AutoLeave lllllllllllllllllIIlIIIlllIlIlII;
        lllllllllllllllllIIlIIIlllIlIlII.sgGeneral = lllllllllllllllllIIlIIIlllIlIlII.settings.getDefaultGroup();
        lllllllllllllllllIIlIIIlllIlIlII.a = lllllllllllllllllIIlIIIlllIlIlII.sgGeneral.add(new IntSetting.Builder().name("totem-count").description("Totem count when you kick").defaultValue(3).min(0).sliderMax(27).build());
        lllllllllllllllllIIlIIIlllIlIlII.b = lllllllllllllllllIIlIIIlllIlIlII.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("The mode.").defaultValue(Mode.MessageAndLeave).build());
        lllllllllllllllllIIlIIIlllIlIlII.c = lllllllllllllllllIIlIIIlllIlIlII.sgGeneral.add(new StringSetting.Builder().name("message").description("Send a chat message .").defaultValue("Buy ArtikHack https://discord.gg/DZTyBunvgS!").build());
        lllllllllllllllllIIlIIIlllIlIlII.autoDisable = lllllllllllllllllIIlIIIlllIlIlII.sgGeneral.add(new BoolSetting.Builder().name("auto-disable").description("Disable module after leave.").defaultValue(true).build());
        lllllllllllllllllIIlIIIlllIlIlII.mc = MinecraftClient.getInstance();
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode NoneAndLeave;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode MessageAndLeave;

        public static Mode valueOf(String llIIIIIllIIIll) {
            return Enum.valueOf(Mode.class, llIIIIIllIIIll);
        }

        static {
            MessageAndLeave = new Mode();
            NoneAndLeave = new Mode();
            $VALUES = Mode.$values();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{MessageAndLeave, NoneAndLeave};
        }

        private Mode() {
            Mode llIIIIIlIllllI;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

