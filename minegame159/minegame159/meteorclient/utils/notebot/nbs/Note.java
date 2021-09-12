/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.notebot.nbs;

public class Note {
    private byte instrument;
    private byte key;

    public byte getKey() {
        return this.key;
    }

    public void setKey(byte by) {
        this.key = by;
    }

    public void setInstrument(byte by) {
        this.instrument = by;
    }

    public byte getInstrument() {
        return this.instrument;
    }

    public Note(byte by, byte by2) {
        this.instrument = by;
        this.key = by2;
    }
}

