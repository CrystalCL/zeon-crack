/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
    public static void copy(InputStream llllllllllllllllllIlIllIllllIIll, OutputStream llllllllllllllllllIlIllIlllIllll) {
        byte[] llllllllllllllllllIlIllIllllIIIl = new byte[512];
        try {
            int llllllllllllllllllIlIllIllllIlIl;
            while ((llllllllllllllllllIlIllIllllIlIl = llllllllllllllllllIlIllIllllIIll.read(llllllllllllllllllIlIllIllllIIIl)) != -1) {
                llllllllllllllllllIlIllIlllIllll.write(llllllllllllllllllIlIllIllllIIIl, 0, llllllllllllllllllIlIllIllllIlIl);
            }
        }
        catch (IOException llllllllllllllllllIlIllIllllIlII) {
            llllllllllllllllllIlIllIllllIlII.printStackTrace();
        }
    }

    public StreamUtils() {
        StreamUtils llllllllllllllllllIlIlllIIIIlIIl;
    }

    public static void copy(File llllllllllllllllllIlIlllIIIIIIII, File llllllllllllllllllIlIllIllllllll) {
        try {
            FileInputStream llllllllllllllllllIlIlllIIIIIIll = new FileInputStream(llllllllllllllllllIlIlllIIIIIIII);
            FileOutputStream llllllllllllllllllIlIlllIIIIIIlI = new FileOutputStream(llllllllllllllllllIlIllIllllllll);
            StreamUtils.copy(llllllllllllllllllIlIlllIIIIIIll, llllllllllllllllllIlIlllIIIIIIlI);
            ((InputStream)llllllllllllllllllIlIlllIIIIIIll).close();
            ((OutputStream)llllllllllllllllllIlIlllIIIIIIlI).close();
        }
        catch (IOException llllllllllllllllllIlIlllIIIIIIIl) {
            llllllllllllllllllIlIlllIIIIIIIl.printStackTrace();
        }
    }
}

