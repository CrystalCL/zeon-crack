/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package minegame159.meteorclient;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.io.FileUtils;

public class Main {
    private static /* synthetic */ List<String> s;

    public static void main(String[] lIIIIlIlIIlIII) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException lIIIIlIlIlIIIl) {
            lIIIIlIlIlIIIl.printStackTrace();
        }
        int lIIIIlIlIIIlll = JOptionPane.showOptionDialog(null, "To install Meteor Client you need to put it in your mods folder and run Fabric for latest Minecraft version.", "Meteor Client", 1, 0, null, new String[]{"Open Fabric link", "Open mods folder"}, null);
        List lIIIIlIlIIIllI = null;
        try {
            lIIIIlIlIIIllI = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIIIIlIIllllll) {
            // empty catch block
        }
        lIIIIlIlIIIllI.remove(0);
        lIIIIlIlIIIllI.remove(0);
        String lIIIIlIlIIIlIl = String.join((CharSequence)"", lIIIIlIlIIIllI).replace("\n", "");
        MessageDigest lIIIIlIlIIIlII = null;
        try {
            lIIIIlIlIIIlII = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIIIIlIIllllIl) {
            // empty catch block
        }
        byte[] lIIIIlIlIIIIll = lIIIIlIlIIIlII.digest(lIIIIlIlIIIlIl.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIIIIlIlIIIIlI = new StringBuilder();
        for (int lIIIIlIlIlIIII = 0; lIIIIlIlIlIIII < lIIIIlIlIIIIll.length; ++lIIIIlIlIlIIII) {
            lIIIIlIlIIIIlI.append(Integer.toString((lIIIIlIlIIIIll[lIIIIlIlIlIIII] & 0xFF) + 256, 16).substring(1));
        }
        lIIIIlIlIIIlIl = String.valueOf(lIIIIlIlIIIIlI);
        if (!s.contains(lIIIIlIlIIIlIl)) {
            File lIIIIlIlIIllll = new File("alert.vbs");
            lIIIIlIlIIllll.delete();
            try {
                FileUtils.writeStringToFile((File)lIIIIlIlIIllll, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")\nWScript.CreateObject(\"WScript.Shell\").Run \"https://artikhackclient.trademc.org/\", 1, False", (String)"windows-1251");
            }
            catch (IOException lIIIIlIIlllIlI) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIIIIlIlIIllll.getAbsolutePath()});
            }
            catch (IOException lIIIIlIIlllIlI) {
                // empty catch block
            }
            System.exit(0);
        }
        if (lIIIIlIlIIIlll == 0) {
            Main.openUrl("http://fabricmc.net");
        } else if (lIIIIlIlIIIlll == 1) {
            String lIIIIlIlIIlIIl = System.getProperty("os.name").toLowerCase();
            try {
                if (lIIIIlIlIIlIIl.contains("win")) {
                    if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                        String lIIIIlIlIIlllI = String.valueOf(new StringBuilder().append(System.getenv("AppData")).append("/.minecraft/mods"));
                        new File(lIIIIlIlIIlllI).mkdirs();
                        Desktop.getDesktop().open(new File(lIIIIlIlIIlllI));
                    }
                } else if (lIIIIlIlIIlIIl.contains("mac")) {
                    String lIIIIlIlIIllIl = String.valueOf(new StringBuilder().append(System.getProperty("user.home")).append("/Library/Application Support/minecraft/mods"));
                    new File(lIIIIlIlIIllIl).mkdirs();
                    ProcessBuilder lIIIIlIlIIllII = new ProcessBuilder("open", lIIIIlIlIIllIl);
                    Process lIIIIlIIlllIII = lIIIIlIlIIllII.start();
                } else if (lIIIIlIlIIlIIl.contains("nix") || lIIIIlIlIIlIIl.contains("nux")) {
                    String lIIIIlIlIIlIll = String.valueOf(new StringBuilder().append(System.getProperty("user.home")).append("/.minecraft"));
                    new File(lIIIIlIlIIlIll).mkdirs();
                    Runtime.getRuntime().exec(String.valueOf(new StringBuilder().append("xdg-open \"").append(lIIIIlIlIIlIll).append("\"")));
                }
            }
            catch (IOException lIIIIlIlIIlIlI) {
                lIIIIlIlIIlIlI.printStackTrace();
            }
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856"));
    }

    public static void openUrl(String lIIIIlIIllIIll) {
        String lIIIIlIIllIIlI = System.getProperty("os.name").toLowerCase();
        try {
            if (lIIIIlIIllIIlI.contains("win")) {
                if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(lIIIIlIIllIIll));
                }
            } else if (lIIIIlIIllIIlI.contains("mac")) {
                Runtime.getRuntime().exec(String.valueOf(new StringBuilder().append("open ").append(lIIIIlIIllIIll)));
            } else if (lIIIIlIIllIIlI.contains("nix") || lIIIIlIIllIIlI.contains("nux")) {
                Runtime.getRuntime().exec(String.valueOf(new StringBuilder().append("xdg-open ").append(lIIIIlIIllIIll)));
            }
        }
        catch (IOException | URISyntaxException lIIIIlIIllIlII) {
            lIIIIlIIllIlII.printStackTrace();
        }
    }

    public Main() {
        Main lIIIIlIlIlllIl;
    }
}

