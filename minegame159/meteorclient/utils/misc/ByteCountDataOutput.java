/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.misc;

import java.io.DataOutput;
import java.io.IOException;

public class ByteCountDataOutput
implements DataOutput {
    public static final /* synthetic */ ByteCountDataOutput INSTANCE;
    private /* synthetic */ int count;

    public ByteCountDataOutput() {
        ByteCountDataOutput llIIIIIIlIIlll;
    }

    @Override
    public void writeByte(int llIIIIIIIIIlll) throws IOException {
        ByteCountDataOutput llIIIIIIIIIllI;
        ++llIIIIIIIIIllI.count;
    }

    public int getCount() {
        ByteCountDataOutput llIIIIIIlIIlII;
        return llIIIIIIlIIlII.count;
    }

    @Override
    public void writeUTF(String lIllllllIllllI) throws IOException {
        ByteCountDataOutput lIllllllIlllIl;
        lIllllllIlllIl.count = (int)((long)lIllllllIlllIl.count + (2L + lIllllllIlllIl.getUTFLength(lIllllllIllllI)));
    }

    @Override
    public void writeBoolean(boolean llIIIIIIIIlIll) throws IOException {
        ByteCountDataOutput llIIIIIIIIlIlI;
        ++llIIIIIIIIlIlI.count;
    }

    @Override
    public void writeLong(long lIllllllllIlll) throws IOException {
        lIllllllllIllI.count += 8;
    }

    long getUTFLength(String lIllllllIlIIlI) {
        long lIllllllIlIIll = 0L;
        for (int lIllllllIlIllI = 0; lIllllllIlIllI < lIllllllIlIIlI.length(); ++lIllllllIlIllI) {
            char lIllllllIlIlll = lIllllllIlIIlI.charAt(lIllllllIlIllI);
            if (lIllllllIlIlll >= '\u0001' && lIllllllIlIlll <= '\u007f') {
                ++lIllllllIlIIll;
                continue;
            }
            if (lIllllllIlIlll > '\u07ff') {
                lIllllllIlIIll += 3L;
                continue;
            }
            lIllllllIlIIll += 2L;
        }
        return lIllllllIlIIll;
    }

    @Override
    public void writeFloat(float lIllllllllIIll) throws IOException {
        lIllllllllIIlI.count += 4;
    }

    @Override
    public void writeInt(int lIlllllllllIll) throws IOException {
        lIlllllllllIlI.count += 4;
    }

    @Override
    public void write(int llIIIIIIIlllIl) throws IOException {
        ByteCountDataOutput llIIIIIIIlllII;
        ++llIIIIIIIlllII.count;
    }

    @Override
    public void write(byte[] llIIIIIIIlIIlI, int llIIIIIIIlIIIl, int llIIIIIIIIlllI) throws IOException {
        llIIIIIIIlIIll.count += llIIIIIIIIlllI;
    }

    @Override
    public void writeChars(String lIlllllllIIIlI) throws IOException {
        lIlllllllIIlIl.count += lIlllllllIIIlI.length() * 2;
    }

    @Override
    public void writeShort(int llIIIIIIIIIIll) throws IOException {
        llIIIIIIIIIIlI.count += 2;
    }

    static {
        INSTANCE = new ByteCountDataOutput();
    }

    public void reset() {
        llIIIIIIlIIIII.count = 0;
    }

    @Override
    public void write(byte[] llIIIIIIIllIII) throws IOException {
        llIIIIIIIlIlll.count += llIIIIIIIllIII.length;
    }

    @Override
    public void writeBytes(String lIlllllllIlIlI) throws IOException {
        lIlllllllIlIIl.count += lIlllllllIlIlI.length();
    }

    @Override
    public void writeDouble(double lIlllllllIllll) throws IOException {
        lIlllllllIlllI.count += 8;
    }

    @Override
    public void writeChar(int lIllllllllllll) throws IOException {
        llIIIIIIIIIIII.count += 2;
    }
}

