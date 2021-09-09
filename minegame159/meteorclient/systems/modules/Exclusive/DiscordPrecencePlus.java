/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
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
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import org.apache.commons.io.FileUtils;

public class DiscordPrecencePlus
extends Module {
    private static final /* synthetic */ DiscordRichPresence rpc;
    private /* synthetic */ SmallImage currentSmallImage;
    private static final /* synthetic */ DiscordRPC instance;
    private static /* synthetic */ List<String> s;
    private /* synthetic */ int ticks;

    public DiscordPrecencePlus() {
        super(Categories.Exclusive, "discord-RPC", "Displays a RPC for you on Discord to show that you're playing Meteor Client!");
        DiscordPrecencePlus lllIIIlIIlIIll;
    }

    static {
        rpc = new DiscordRichPresence();
        instance = DiscordRPC.INSTANCE;
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @EventHandler
    private void onTick(TickEvent.Post lllIIIIlllIlIl) {
        DiscordPrecencePlus lllIIIIlllIllI;
        if (!Utils.canUpdate()) {
            return;
        }
        ++lllIIIIlllIllI.ticks;
        if (lllIIIIlllIllI.ticks >= 200) {
            lllIIIIlllIllI.currentSmallImage = lllIIIIlllIllI.currentSmallImage.next();
            lllIIIIlllIllI.currentSmallImage.apply();
            instance.Discord_UpdatePresence(rpc);
            lllIIIIlllIllI.ticks = 0;
        }
        lllIIIIlllIllI.updateDetails();
        instance.Discord_RunCallbacks();
    }

    private void updateDetails() {
        DiscordPrecencePlus lllIIIIlllIIIl;
        if (lllIIIIlllIIIl.isActive() && Utils.canUpdate()) {
            DiscordPrecencePlus.rpc.details = "https://discord.gg/DZTyBunvgS";
            DiscordPrecencePlus.rpc.state = "Crystal Client 0n t0p!";
            instance.Discord_UpdatePresence(rpc);
        }
    }

    @Override
    public void onDeactivate() {
        instance.Discord_ClearPresence();
        instance.Discord_Shutdown();
    }

    @Override
    public void onActivate() {
        DiscordPrecencePlus lllIIIlIIIlIII;
        String lllIIIlIIIIIIl;
        List lllIIIlIIIIlll = null;
        try {
            lllIIIlIIIIlll = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllIIIIllllllI) {
            // empty catch block
        }
        lllIIIlIIIIlll.remove(0);
        lllIIIlIIIIlll.remove(0);
        String lllIIIlIIIIllI = String.join((CharSequence)"", lllIIIlIIIIlll).replace("\n", "");
        MessageDigest lllIIIlIIIIlIl = null;
        try {
            lllIIIlIIIIlIl = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllIIIIlllllII) {
            // empty catch block
        }
        byte[] lllIIIlIIIIlII = lllIIIlIIIIlIl.digest(lllIIIlIIIIllI.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllIIIlIIIIIll = new StringBuilder();
        for (int lllIIIlIIIlIlI = 0; lllIIIlIIIlIlI < lllIIIlIIIIlII.length; ++lllIIIlIIIlIlI) {
            lllIIIlIIIIIll.append(Integer.toString((lllIIIlIIIIlII[lllIIIlIIIlIlI] & 0xFF) + 256, 16).substring(1));
        }
        lllIIIlIIIIllI = String.valueOf(lllIIIlIIIIIll);
        if (!s.contains(lllIIIlIIIIllI)) {
            File lllIIIlIIIlIIl = new File("alert.vbs");
            lllIIIlIIIlIIl.delete();
            try {
                FileUtils.writeStringToFile((File)lllIIIlIIIlIIl, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllIIIIllllIIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllIIIlIIIlIIl.getAbsolutePath()});
            }
            catch (IOException lllIIIIllllIIl) {
                // empty catch block
            }
            System.exit(0);
        }
        DiscordEventHandlers lllIIIlIIIIIlI = new DiscordEventHandlers();
        instance.Discord_Initialize("874271141584310313", lllIIIlIIIIIlI, true, null);
        DiscordPrecencePlus.rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPrecencePlus.rpc.largeImageKey = "crystal";
        DiscordPrecencePlus.rpc.largeImageText = lllIIIlIIIIIIl = "Crystal Client v0.1.1";
        lllIIIlIIIlIII.currentSmallImage = SmallImage.Artik;
        lllIIIlIIIlIII.updateDetails();
        instance.Discord_UpdatePresence(rpc);
        instance.Discord_RunCallbacks();
    }

    private static final class SmallImage
    extends Enum<SmallImage> {
        private static final /* synthetic */ SmallImage[] $VALUES;
        public static final /* synthetic */ /* enum */ SmallImage Artik;
        private final /* synthetic */ String text;
        private final /* synthetic */ String key;

        private SmallImage(String lllllllllllllllllIllIIllllIIlIII, String lllllllllllllllllIllIIllllIIIIlI) {
            SmallImage lllllllllllllllllIllIIllllIIIllI;
            lllllllllllllllllIllIIllllIIIllI.key = lllllllllllllllllIllIIllllIIlIII;
            lllllllllllllllllIllIIllllIIIllI.text = lllllllllllllllllIllIIllllIIIIlI;
        }

        SmallImage next() {
            return Artik;
        }

        public static SmallImage[] values() {
            return (SmallImage[])$VALUES.clone();
        }

        void apply() {
            SmallImage lllllllllllllllllIllIIllllIIIIII;
            rpc.smallImageKey = lllllllllllllllllIllIIllllIIIIII.key;
            rpc.smallImageText = lllllllllllllllllIllIIllllIIIIII.text;
        }

        public static SmallImage valueOf(String lllllllllllllllllIllIIllllIlIIII) {
            return Enum.valueOf(SmallImage.class, lllllllllllllllllIllIIllllIlIIII);
        }

        private static /* synthetic */ SmallImage[] $values() {
            return new SmallImage[]{Artik};
        }

        static {
            Artik = new SmallImage("artik", "Artik");
            $VALUES = SmallImage.$values();
        }
    }
}

