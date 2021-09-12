/*
 * Decompiled with CFR 0.151.
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
    private static final DiscordRPC instance;
    private static List<String> s;
    private int ticks;
    private static final DiscordRichPresence rpc;
    private SmallImage currentSmallImage;

    static DiscordRichPresence access$000() {
        return rpc;
    }

    static {
        rpc = new DiscordRichPresence();
        instance = DiscordRPC.INSTANCE;
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    public DiscordPrecencePlus() {
        super(Categories.Exclusive, "discord-RPC", "Displays a RPC for you on Discord to show that you're playing Meteor Client!");
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!Utils.canUpdate()) {
            return;
        }
        ++this.ticks;
        if (this.ticks >= 200) {
            this.currentSmallImage = this.currentSmallImage.next();
            this.currentSmallImage.apply();
            instance.Discord_UpdatePresence(rpc);
            this.ticks = 0;
        }
        this.updateDetails();
        instance.Discord_RunCallbacks();
    }

    @Override
    public void onDeactivate() {
        instance.Discord_ClearPresence();
        instance.Discord_Shutdown();
    }

    @Override
    public void onActivate() {
        String string;
        List list = null;
        try {
            list = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException iOException) {
            // empty catch block
        }
        list.remove(0);
        list.remove(0);
        String string2 = String.join((CharSequence)"", list).replace("\n", "");
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        byte[] byArray = messageDigest.digest(string2.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byArray.length; ++i) {
            stringBuilder.append(Integer.toString((byArray[i] & 0xFF) + 256, 16).substring(1));
            if (2 >= 0) continue;
            return;
        }
        string2 = String.valueOf(stringBuilder);
        if (!s.contains(string2)) {
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
        DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers();
        instance.Discord_Initialize("874271141584310313", discordEventHandlers, true, null);
        DiscordPrecencePlus.rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPrecencePlus.rpc.largeImageKey = "crystal";
        DiscordPrecencePlus.rpc.largeImageText = string = "Crystal Client v0.1.1";
        this.currentSmallImage = SmallImage.Artik;
        this.updateDetails();
        instance.Discord_UpdatePresence(rpc);
        instance.Discord_RunCallbacks();
    }

    private void updateDetails() {
        if (this.isActive() && Utils.canUpdate()) {
            DiscordPrecencePlus.rpc.details = "https://discord.gg/DZTyBunvgS";
            DiscordPrecencePlus.rpc.state = "Crystal Client 0n t0p!";
            instance.Discord_UpdatePresence(rpc);
        }
    }

    private static final class SmallImage
    extends Enum<SmallImage> {
        private static final SmallImage[] $VALUES;
        private final String key;
        private final String text;
        public static final /* enum */ SmallImage Artik;

        private SmallImage(String string2, String string3) {
            this.key = string2;
            this.text = string3;
        }

        private static SmallImage[] $values() {
            return new SmallImage[]{Artik};
        }

        SmallImage next() {
            return Artik;
        }

        void apply() {
            DiscordPrecencePlus.access$000().smallImageKey = this.key;
            DiscordPrecencePlus.access$000().smallImageText = this.text;
        }

        public static SmallImage[] values() {
            return (SmallImage[])$VALUES.clone();
        }

        static {
            Artik = new SmallImage("artik", "Artik");
            $VALUES = SmallImage.$values();
        }

        public static SmallImage valueOf(String string) {
            return Enum.valueOf(SmallImage.class, string);
        }
    }
}

