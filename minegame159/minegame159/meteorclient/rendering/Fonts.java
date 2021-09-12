/*
 * Decompiled with CFR 0.151.
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
        File[] fileArray;
        File[] fileArray2 = fileArray = MeteorClient.FOLDER.exists() ? MeteorClient.FOLDER.listFiles() : new File[]{};
        if (fileArray != null) {
            for (File file : fileArray) {
                if (!file.getName().endsWith(".ttf") && !file.getName().endsWith(".TTF")) continue;
                file.delete();
                if (null == null) continue;
                return;
            }
        }
    }

    public static void init() {
        File[] fileArray = MeteorClient.FOLDER.exists() ? MeteorClient.FOLDER.listFiles() : new File[]{};
        File file = null;
        if (fileArray != null) {
            for (File file2 : fileArray) {
                if (!file2.getName().endsWith(".ttf") && !file2.getName().endsWith(".TTF")) continue;
                file = file2;
                break;
            }
        }
        if (file == null) {
            try {
                int n;
                file = new File(MeteorClient.FOLDER, "JetBrainsMono-Regular.ttf");
                file.getParentFile().mkdirs();
                InputStream inputStream = MeteorClient.class.getResourceAsStream("/assets/meteor-client/JetBrainsMono-Regular.ttf");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] byArray = new byte[255];
                while ((n = inputStream.read(byArray)) > 0) {
                    ((OutputStream)fileOutputStream).write(byArray, 0, n);
                }
                inputStream.close();
                ((OutputStream)fileOutputStream).close();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        MeteorClient.FONT = new CustomTextRenderer(file);
    }
}

