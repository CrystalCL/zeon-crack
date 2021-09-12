/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.notebot.nbs;

import java.util.HashMap;
import minegame159.meteorclient.utils.notebot.nbs.Note;

public class Layer {
    private String name = "";
    private byte volume;
    private HashMap<Integer, Note> hashMap = new HashMap();

    public Layer() {
        this.volume = (byte)100;
    }

    public void setNote(int n, Note note) {
        this.hashMap.put(n, note);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public HashMap<Integer, Note> getHashMap() {
        return this.hashMap;
    }

    public Note getNote(int n) {
        return this.hashMap.get(n);
    }

    public void setHashMap(HashMap<Integer, Note> hashMap) {
        this.hashMap = hashMap;
    }

    public void setVolume(byte by) {
        this.volume = by;
    }

    public byte getVolume() {
        return this.volume;
    }
}

