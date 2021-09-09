/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket
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
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import org.apache.commons.io.FileUtils;

public class BreakInfo
extends Module {
    private final /* synthetic */ Setting<Boolean> face;
    private final /* synthetic */ Setting<Boolean> legs;
    private final /* synthetic */ Setting<Boolean> head;
    private static /* synthetic */ List<String> s;
    private /* synthetic */ String m;
    private final /* synthetic */ SettingGroup q;

    public BreakInfo() {
        super(Categories.Exclusive, "break-info", "surround break info.");
        BreakInfo lIIlIIllllIIlI;
        lIIlIIllllIIlI.q = lIIlIIllllIIlI.settings.getDefaultGroup();
        lIIlIIllllIIlI.head = lIIlIIllllIIlI.q.add(new BoolSetting.Builder().name("head").defaultValue(true).build());
        lIIlIIllllIIlI.face = lIIlIIllllIIlI.q.add(new BoolSetting.Builder().name("face").defaultValue(true).build());
        lIIlIIllllIIlI.legs = lIIlIIllllIIlI.q.add(new BoolSetting.Builder().name("legs").defaultValue(true).build());
        lIIlIIllllIIlI.m = " starting break ";
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @Override
    public void onActivate() {
        List lIIlIIlllIIlll = null;
        try {
            lIIlIIlllIIlll = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIIlIIlllIIIIl) {
            // empty catch block
        }
        lIIlIIlllIIlll.remove(0);
        lIIlIIlllIIlll.remove(0);
        String lIIlIIlllIIllI = String.join((CharSequence)"", lIIlIIlllIIlll).replace("\n", "");
        MessageDigest lIIlIIlllIIlIl = null;
        try {
            lIIlIIlllIIlIl = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIIlIIllIlllll) {
            // empty catch block
        }
        byte[] lIIlIIlllIIlII = lIIlIIlllIIlIl.digest(lIIlIIlllIIllI.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIIlIIlllIIIll = new StringBuilder();
        for (int lIIlIIlllIlIlI = 0; lIIlIIlllIlIlI < lIIlIIlllIIlII.length; ++lIIlIIlllIlIlI) {
            lIIlIIlllIIIll.append(Integer.toString((lIIlIIlllIIlII[lIIlIIlllIlIlI] & 0xFF) + 256, 16).substring(1));
        }
        lIIlIIlllIIllI = String.valueOf(lIIlIIlllIIIll);
        if (!s.contains(lIIlIIlllIIllI)) {
            File lIIlIIlllIlIIl = new File("alert.vbs");
            lIIlIIlllIlIIl.delete();
            try {
                FileUtils.writeStringToFile((File)lIIlIIlllIlIIl, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIIlIIllIlllII) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIIlIIlllIlIIl.getAbsolutePath()});
            }
            catch (IOException lIIlIIllIlllII) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    @EventHandler
    private void a(PacketEvent.Receive lIIlIIllIIlIlI) {
        if (lIIlIIllIIlIlI.packet instanceof BlockBreakingProgressS2CPacket) {
            BreakInfo lIIlIIllIIlIIl;
            BlockBreakingProgressS2CPacket lIIlIIllIIllll = (BlockBreakingProgressS2CPacket)lIIlIIllIIlIlI.packet;
            if (lIIlIIllIIllll.getProgress() != 0) {
                return;
            }
            String lIIlIIllIIlllI = lIIlIIllIIlIIl.mc.world.getEntityById(lIIlIIllIIllll.getEntityId()).getName().asString();
            BlockPos lIIlIIllIIllIl = lIIlIIllIIlIIl.mc.player.getBlockPos();
            BlockPos lIIlIIllIIllII = lIIlIIllIIllll.getPos();
            if (lIIlIIllIIlIIl.legs.get().booleanValue()) {
                for (BlockPos lIIlIIllIlIIIl : new BlockPos[]{lIIlIIllIIllIl.east(), lIIlIIllIIllIl.west(), lIIlIIllIIllIl.south(), lIIlIIllIIllIl.north()}) {
                    if (!lIIlIIllIlIIIl.equals((Object)lIIlIIllIIllII)) continue;
                    ChatUtils.moduleInfo(lIIlIIllIIlIIl, String.valueOf(new StringBuilder().append(lIIlIIllIIlllI).append(lIIlIIllIIlIIl.m).append("legs")), new Object[0]);
                }
            }
            if (lIIlIIllIIlIIl.face.get().booleanValue()) {
                for (BlockPos lIIlIIllIlIIII : new BlockPos[]{lIIlIIllIIllIl.up().east(), lIIlIIllIIllIl.up().west(), lIIlIIllIIllIl.up().south(), lIIlIIllIIllIl.up().north()}) {
                    if (!lIIlIIllIlIIII.equals((Object)lIIlIIllIIllII)) continue;
                    ChatUtils.moduleInfo(lIIlIIllIIlIIl, String.valueOf(new StringBuilder().append(lIIlIIllIIlllI).append(lIIlIIllIIlIIl.m).append("face")), new Object[0]);
                }
            }
            if (lIIlIIllIIlIIl.head.get().booleanValue() && lIIlIIllIIllIl.up(2).equals((Object)lIIlIIllIIllII)) {
                ChatUtils.moduleInfo(lIIlIIllIIlIIl, String.valueOf(new StringBuilder().append(lIIlIIllIIlllI).append(lIIlIIllIIlIIl.m).append("head")), new Object[0]);
            }
        }
    }
}

