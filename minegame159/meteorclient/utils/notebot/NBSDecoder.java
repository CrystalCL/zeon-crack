/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package minegame159.meteorclient.utils.notebot;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import minegame159.meteorclient.utils.notebot.nbs.Layer;
import minegame159.meteorclient.utils.notebot.nbs.Note;
import minegame159.meteorclient.utils.notebot.nbs.Song;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBSDecoder {
    public static final /* synthetic */ Logger LOG;

    public static Song parse(File lllIIllllIllll) {
        try {
            return NBSDecoder.parse(new FileInputStream(lllIIllllIllll), lllIIllllIllll);
        }
        catch (FileNotFoundException lllIIlllllIIIl) {
            lllIIlllllIIIl.printStackTrace();
            return null;
        }
    }

    private static Song parseClassic(DataInputStream lllIIlllIIIIII, File lllIIllIllllll, short lllIIllIlllllI) throws IOException {
        short lllIIlllIIlllI;
        HashMap<Integer, Layer> lllIIlllIIIlll = new HashMap<Integer, Layer>();
        int lllIIlllIIIllI = NBSDecoder.readShort(lllIIlllIIIIII);
        String lllIIlllIIIlIl = NBSDecoder.readString(lllIIlllIIIIII);
        String lllIIlllIIIlII = NBSDecoder.readString(lllIIlllIIIIII);
        NBSDecoder.readString(lllIIlllIIIIII);
        String lllIIlllIIIIll = NBSDecoder.readString(lllIIlllIIIIII);
        float lllIIlllIIIIlI = (float)NBSDecoder.readShort(lllIIlllIIIIII) / 100.0f;
        lllIIlllIIIIII.readBoolean();
        lllIIlllIIIIII.readByte();
        lllIIlllIIIIII.readByte();
        NBSDecoder.readInt(lllIIlllIIIIII);
        NBSDecoder.readInt(lllIIlllIIIIII);
        NBSDecoder.readInt(lllIIlllIIIIII);
        NBSDecoder.readInt(lllIIlllIIIIII);
        NBSDecoder.readInt(lllIIlllIIIIII);
        NBSDecoder.readString(lllIIlllIIIIII);
        int lllIIlllIIIIIl = -1;
        while ((lllIIlllIIlllI = NBSDecoder.readShort(lllIIlllIIIIII)) != 0) {
            short lllIIlllIIllll;
            lllIIlllIIIIIl = (short)(lllIIlllIIIIIl + lllIIlllIIlllI);
            int lllIIlllIIllIl = -1;
            while ((lllIIlllIIllll = NBSDecoder.readShort(lllIIlllIIIIII)) != 0) {
                lllIIlllIIllIl = (short)(lllIIlllIIllIl + lllIIlllIIllll);
                NBSDecoder.setNote(lllIIlllIIllIl, lllIIlllIIIIIl, lllIIlllIIIIII.readByte(), lllIIlllIIIIII.readByte(), lllIIlllIIIlll);
            }
        }
        for (int lllIIlllIIlIll = 0; lllIIlllIIlIll < lllIIlllIIIllI; ++lllIIlllIIlIll) {
            Layer lllIIlllIIllII = (Layer)lllIIlllIIIlll.get(lllIIlllIIlIll);
            if (lllIIlllIIllII == null) continue;
            lllIIlllIIllII.setName(NBSDecoder.readString(lllIIlllIIIIII));
            lllIIlllIIllII.setVolume(lllIIlllIIIIII.readByte());
        }
        return new Song(lllIIlllIIIIlI, lllIIlllIIIlll, (short)lllIIlllIIIllI, lllIIllIlllllI, lllIIlllIIIlIl, lllIIlllIIIlII, lllIIlllIIIIll, lllIIllIllllll);
    }

    private static Song parse(InputStream lllIIllllIIIII, File lllIIllllIIIIl) {
        try {
            DataInputStream lllIIllllIIllI = new DataInputStream(lllIIllllIIIII);
            short lllIIllllIIlIl = NBSDecoder.readShort(lllIIllllIIllI);
            if (lllIIllllIIlIl != 0) {
                return NBSDecoder.parseClassic(lllIIllllIIllI, lllIIllllIIIIl, lllIIllllIIlIl);
            }
            return NBSDecoder.parseOpenNBS(lllIIllllIIllI, lllIIllllIIIIl);
        }
        catch (FileNotFoundException lllIIllllIIlII) {
            LOG.error((Object)lllIIllllIIlII.getStackTrace());
        }
        catch (IOException lllIIllllIIIll) {
            LOG.error((Object)lllIIllllIIIll.getStackTrace());
        }
        return null;
    }

    static {
        LOG = LogManager.getLogger();
    }

    public static Song parse(InputStream lllIIllllIllII) {
        return NBSDecoder.parse(lllIIllllIllII, null);
    }

    private static void setNote(int lllIIllIIIIIIl, int lllIIllIIIIIII, byte lllIIlIlllllll, byte lllIIlIllllIII, HashMap<Integer, Layer> lllIIlIlllIlll) {
        Layer lllIIlIlllllII = lllIIlIlllIlll.get(lllIIllIIIIIIl);
        if (lllIIlIlllllII == null) {
            lllIIlIlllllII = new Layer();
            lllIIlIlllIlll.put(lllIIllIIIIIIl, lllIIlIlllllII);
        }
        lllIIlIlllllII.setNote(lllIIllIIIIIII, new Note(lllIIlIlllllll, lllIIlIllllIII));
    }

    private static int readInt(DataInputStream lllIIlIllIIIlI) throws IOException {
        int lllIIlIllIIllI = lllIIlIllIIIlI.readUnsignedByte();
        int lllIIlIllIIlIl = lllIIlIllIIIlI.readUnsignedByte();
        int lllIIlIllIIlII = lllIIlIllIIIlI.readUnsignedByte();
        int lllIIlIllIIIll = lllIIlIllIIIlI.readUnsignedByte();
        return lllIIlIllIIllI + (lllIIlIllIIlIl << 8) + (lllIIlIllIIlII << 16) + (lllIIlIllIIIll << 24);
    }

    private static Song parseOpenNBS(DataInputStream lllIIllIIlIlIl, File lllIIllIIlIlII) throws IOException {
        short lllIIllIlIIlII;
        HashMap<Integer, Layer> lllIIllIIllllI = new HashMap<Integer, Layer>();
        byte lllIIllIIlllIl = lllIIllIIlIlIl.readByte();
        if (lllIIllIIlllIl != 5) {
            return null;
        }
        lllIIllIIlIlIl.readByte();
        short lllIIllIIlllII = NBSDecoder.readShort(lllIIllIIlIlIl);
        int lllIIllIIllIll = NBSDecoder.readShort(lllIIllIIlIlIl);
        String lllIIllIIllIlI = NBSDecoder.readString(lllIIllIIlIlIl);
        String lllIIllIIllIIl = NBSDecoder.readString(lllIIllIIlIlIl);
        NBSDecoder.readString(lllIIllIIlIlIl);
        String lllIIllIIllIII = NBSDecoder.readString(lllIIllIIlIlIl);
        float lllIIllIIlIlll = (float)NBSDecoder.readShort(lllIIllIIlIlIl) / 100.0f;
        lllIIllIIlIlIl.readByte();
        lllIIllIIlIlIl.readByte();
        lllIIllIIlIlIl.readByte();
        NBSDecoder.readInt(lllIIllIIlIlIl);
        NBSDecoder.readInt(lllIIllIIlIlIl);
        NBSDecoder.readInt(lllIIllIIlIlIl);
        NBSDecoder.readInt(lllIIllIIlIlIl);
        NBSDecoder.readInt(lllIIllIIlIlIl);
        NBSDecoder.readString(lllIIllIIlIlIl);
        lllIIllIIlIlIl.readByte();
        lllIIllIIlIlIl.readByte();
        NBSDecoder.readShort(lllIIllIIlIlIl);
        int lllIIllIIlIllI = -1;
        while ((lllIIllIlIIlII = NBSDecoder.readShort(lllIIllIIlIlIl)) != 0) {
            short lllIIllIlIIlIl;
            lllIIllIIlIllI = (short)(lllIIllIIlIllI + lllIIllIlIIlII);
            int lllIIllIlIIIll = -1;
            while ((lllIIllIlIIlIl = NBSDecoder.readShort(lllIIllIIlIlIl)) != 0) {
                lllIIllIlIIIll = (short)(lllIIllIlIIIll + lllIIllIlIIlIl);
                NBSDecoder.setNote(lllIIllIlIIIll, lllIIllIIlIllI, lllIIllIIlIlIl.readByte(), lllIIllIIlIlIl.readByte(), lllIIllIIllllI);
                lllIIllIIlIlIl.readByte();
                lllIIllIIlIlIl.readByte();
                NBSDecoder.readShort(lllIIllIIlIlIl);
            }
        }
        for (int lllIIllIlIIIIl = 0; lllIIllIlIIIIl < lllIIllIIllIll; ++lllIIllIlIIIIl) {
            Layer lllIIllIlIIIlI = (Layer)lllIIllIIllllI.get(lllIIllIlIIIIl);
            if (lllIIllIlIIIlI == null) continue;
            lllIIllIlIIIlI.setName(NBSDecoder.readString(lllIIllIIlIlIl));
            lllIIllIIlIlIl.readByte();
            lllIIllIlIIIlI.setVolume(lllIIllIIlIlIl.readByte());
            lllIIllIIlIlIl.readByte();
        }
        return new Song(lllIIllIIlIlll, lllIIllIIllllI, (short)lllIIllIIllIll, lllIIllIIlllII, lllIIllIIllIlI, lllIIllIIllIIl, lllIIllIIllIII, lllIIllIIlIlII);
    }

    private static String readString(DataInputStream lllIIlIlIllIII) throws IOException {
        int lllIIlIlIlIlll;
        StringBuilder lllIIlIlIlIllI = new StringBuilder(lllIIlIlIlIlll);
        for (lllIIlIlIlIlll = NBSDecoder.readInt(lllIIlIlIllIII); lllIIlIlIlIlll > 0; --lllIIlIlIlIlll) {
            char lllIIlIlIllIIl = (char)lllIIlIlIllIII.readByte();
            if (lllIIlIlIllIIl == '\r') {
                lllIIlIlIllIIl = ' ';
            }
            lllIIlIlIlIllI.append(lllIIlIlIllIIl);
        }
        return String.valueOf(lllIIlIlIlIllI);
    }

    public NBSDecoder() {
        NBSDecoder lllIIlllllIlIl;
    }

    private static short readShort(DataInputStream lllIIlIllIllll) throws IOException {
        int lllIIlIlllIIIl = lllIIlIllIllll.readUnsignedByte();
        int lllIIlIlllIIII = lllIIlIllIllll.readUnsignedByte();
        return (short)(lllIIlIlllIIIl + (lllIIlIlllIIII << 8));
    }
}

