/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.rendering;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.rendering.text.CustomTextRenderer;

public class Fonts {
    public static void reset() {
        File[] lIlIllIllIlIIlI;
        File[] arrfile = lIlIllIllIlIIlI = MeteorClient.FOLDER.exists() ? MeteorClient.FOLDER.listFiles() : new File[0];
        if (lIlIllIllIlIIlI != null) {
            for (File lIlIllIllIlIIll : lIlIllIllIlIIlI) {
                if (!lIlIllIllIlIIll.getName().endsWith(".ttf") && !lIlIllIllIlIIll.getName().endsWith(".TTF")) continue;
                lIlIllIllIlIIll.delete();
            }
        }
    }

    public static void init() {
        File[] lIlIllIllIIIIII = MeteorClient.FOLDER.exists() ? MeteorClient.FOLDER.listFiles() : new File[0];
        File lIlIllIlIllllll = null;
        if (lIlIllIllIIIIII != null) {
            for (File lIlIllIllIIIllI : lIlIllIllIIIIII) {
                if (!lIlIllIllIIIllI.getName().endsWith(".ttf") && !lIlIllIllIIIllI.getName().endsWith(".TTF")) continue;
                lIlIllIlIllllll = lIlIllIllIIIllI;
                break;
            }
        }
        if (lIlIllIlIllllll == null) {
            try {
                int lIlIllIllIIIIlI;
                lIlIllIlIllllll = new File(MeteorClient.FOLDER, "JetBrainsMono-Regular.ttf");
                lIlIllIlIllllll.getParentFile().mkdirs();
                InputStream lIlIllIllIIIlIl = MeteorClient.class.getResourceAsStream("/assets/meteor-client/JetBrainsMono-Regular.ttf");
                FileOutputStream lIlIllIllIIIlII = new FileOutputStream(lIlIllIlIllllll);
                byte[] lIlIllIllIIIIll = new byte[255];
                while ((lIlIllIllIIIIlI = lIlIllIllIIIlIl.read(lIlIllIllIIIIll)) > 0) {
                    ((OutputStream)lIlIllIllIIIlII).write(lIlIllIllIIIIll, 0, lIlIllIllIIIIlI);
                }
                lIlIllIllIIIlIl.close();
                ((OutputStream)lIlIllIllIIIlII).close();
            }
            catch (IOException lIlIllIllIIIIIl) {
                lIlIllIllIIIIIl.printStackTrace();
            }
        }
        MeteorClient.FONT = new CustomTextRenderer(lIlIllIlIllllll);
    }

    public Fonts() {
        Fonts lIlIllIllIllIlI;
    }
}

