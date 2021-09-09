/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.notebot.nbs;

import java.io.File;
import java.util.HashMap;
import minegame159.meteorclient.utils.notebot.nbs.Layer;

public class Song {
    private /* synthetic */ float delay;
    private /* synthetic */ float speed;
    private /* synthetic */ File path;
    private /* synthetic */ HashMap<Integer, Layer> layerHashMap;
    private /* synthetic */ String title;
    private /* synthetic */ short songHeight;
    private /* synthetic */ String description;
    private /* synthetic */ short length;
    private /* synthetic */ String author;

    public HashMap<Integer, Layer> getLayerHashMap() {
        Song llllllllllllllllIllIllIIIIllIlII;
        return llllllllllllllllIllIllIIIIllIlII.layerHashMap;
    }

    public short getSongHeight() {
        Song llllllllllllllllIllIllIIIIllIIII;
        return llllllllllllllllIllIllIIIIllIIII.songHeight;
    }

    public String getTitle() {
        Song llllllllllllllllIllIllIIIIlIlIll;
        return llllllllllllllllIllIllIIIIlIlIll.title;
    }

    public short getLength() {
        Song llllllllllllllllIllIllIIIIlIlllI;
        return llllllllllllllllIllIllIIIIlIlllI.length;
    }

    public String getDescription() {
        Song llllllllllllllllIllIllIIIIlIIIIl;
        return llllllllllllllllIllIllIIIIlIIIIl.description;
    }

    public Song(float llllllllllllllllIllIllIIIIllllIl, HashMap<Integer, Layer> llllllllllllllllIllIllIIIIllllII, short llllllllllllllllIllIllIIIlIIIlII, short llllllllllllllllIllIllIIIlIIIIll, String llllllllllllllllIllIllIIIIlllIIl, String llllllllllllllllIllIllIIIIlllIII, String llllllllllllllllIllIllIIIIllIlll, File llllllllllllllllIllIllIIIIllllll) {
        Song llllllllllllllllIllIllIIIIlllllI;
        llllllllllllllllIllIllIIIIlllllI.layerHashMap = new HashMap();
        llllllllllllllllIllIllIIIIlllllI.speed = llllllllllllllllIllIllIIIIllllIl;
        llllllllllllllllIllIllIIIIlllllI.delay = 20.0f / llllllllllllllllIllIllIIIIllllIl;
        llllllllllllllllIllIllIIIIlllllI.layerHashMap = llllllllllllllllIllIllIIIIllllII;
        llllllllllllllllIllIllIIIIlllllI.songHeight = llllllllllllllllIllIllIIIlIIIlII;
        llllllllllllllllIllIllIIIIlllllI.length = llllllllllllllllIllIllIIIlIIIIll;
        llllllllllllllllIllIllIIIIlllllI.title = llllllllllllllllIllIllIIIIlllIIl;
        llllllllllllllllIllIllIIIIlllllI.author = llllllllllllllllIllIllIIIIlllIII;
        llllllllllllllllIllIllIIIIlllllI.description = llllllllllllllllIllIllIIIIllIlll;
        llllllllllllllllIllIllIIIIlllllI.path = llllllllllllllllIllIllIIIIllllll;
    }

    public float getDelay() {
        Song llllllllllllllllIllIllIIIIIlllII;
        return llllllllllllllllIllIllIIIIIlllII.delay;
    }

    public Song(Song llllllllllllllllIllIllIIIlIlIIIl) {
        Song llllllllllllllllIllIllIIIlIlIlII;
        llllllllllllllllIllIllIIIlIlIlII.layerHashMap = new HashMap();
        llllllllllllllllIllIllIIIlIlIlII.speed = llllllllllllllllIllIllIIIlIlIIIl.getSpeed();
        llllllllllllllllIllIllIIIlIlIlII.delay = 20.0f / llllllllllllllllIllIllIIIlIlIlII.speed;
        llllllllllllllllIllIllIIIlIlIlII.layerHashMap = llllllllllllllllIllIllIIIlIlIIIl.getLayerHashMap();
        llllllllllllllllIllIllIIIlIlIlII.songHeight = llllllllllllllllIllIllIIIlIlIIIl.getSongHeight();
        llllllllllllllllIllIllIIIlIlIlII.length = llllllllllllllllIllIllIIIlIlIIIl.getLength();
        llllllllllllllllIllIllIIIlIlIlII.title = llllllllllllllllIllIllIIIlIlIIIl.getTitle();
        llllllllllllllllIllIllIIIlIlIlII.author = llllllllllllllllIllIllIIIlIlIIIl.getAuthor();
        llllllllllllllllIllIllIIIlIlIlII.description = llllllllllllllllIllIllIIIlIlIIIl.getDescription();
        llllllllllllllllIllIllIIIlIlIlII.path = llllllllllllllllIllIllIIIlIlIIIl.getPath();
    }

    public String getAuthor() {
        Song llllllllllllllllIllIllIIIIlIIlll;
        return llllllllllllllllIllIllIIIIlIIlll.author;
    }

    public File getPath() {
        Song llllllllllllllllIllIllIIIIlIIlIl;
        return llllllllllllllllIllIllIIIIlIIlIl.path;
    }

    public float getSpeed() {
        Song llllllllllllllllIllIllIIIIIllllI;
        return llllllllllllllllIllIllIIIIIllllI.speed;
    }
}

