/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.notebot.nbs;

import java.util.HashMap;
import minegame159.meteorclient.utils.notebot.nbs.Note;

public class Layer {
    private /* synthetic */ byte volume;
    private /* synthetic */ HashMap<Integer, Note> hashMap;
    private /* synthetic */ String name;

    public Note getNote(int lIllIIlIIlIIl) {
        Layer lIllIIlIIllII;
        return lIllIIlIIllII.hashMap.get(lIllIIlIIlIIl);
    }

    public void setVolume(byte lIllIIIllIlll) {
        lIllIIIlllIII.volume = lIllIIIllIlll;
    }

    public void setHashMap(HashMap<Integer, Note> lIllIIlIllIlI) {
        lIllIIlIllIll.hashMap = lIllIIlIllIlI;
    }

    public String getName() {
        Layer lIllIIlIlIllI;
        return lIllIIlIlIllI.name;
    }

    public HashMap<Integer, Note> getHashMap() {
        Layer lIllIIlIlllll;
        return lIllIIlIlllll.hashMap;
    }

    public void setNote(int lIllIIlIIIIIl, Note lIllIIlIIIIII) {
        Layer lIllIIlIIIlIl;
        lIllIIlIIIlIl.hashMap.put(lIllIIlIIIIIl, lIllIIlIIIIII);
    }

    public Layer() {
        Layer lIllIIllIIIIl;
        lIllIIllIIIIl.hashMap = new HashMap();
        lIllIIllIIIIl.volume = (byte)100;
        lIllIIllIIIIl.name = "";
    }

    public void setName(String lIllIIlIIllll) {
        lIllIIlIlIIlI.name = lIllIIlIIllll;
    }

    public byte getVolume() {
        Layer lIllIIIllllIl;
        return lIllIIIllllIl.volume;
    }
}

