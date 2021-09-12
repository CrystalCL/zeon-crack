/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.notebot.nbs;

import java.io.File;
import java.util.HashMap;
import minegame159.meteorclient.utils.notebot.nbs.Layer;

public class Song {
    private short songHeight;
    private short length;
    private String description;
    private String author;
    private float speed;
    private String title;
    private File path;
    private float delay;
    private HashMap<Integer, Layer> layerHashMap = new HashMap();

    public String getDescription() {
        return this.description;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getDelay() {
        return this.delay;
    }

    public String getTitle() {
        return this.title;
    }

    public short getSongHeight() {
        return this.songHeight;
    }

    public String getAuthor() {
        return this.author;
    }

    public Song(float f, HashMap<Integer, Layer> hashMap, short s, short s2, String string, String string2, String string3, File file) {
        this.speed = f;
        this.delay = 20.0f / f;
        this.layerHashMap = hashMap;
        this.songHeight = s;
        this.length = s2;
        this.title = string;
        this.author = string2;
        this.description = string3;
        this.path = file;
    }

    public short getLength() {
        return this.length;
    }

    public File getPath() {
        return this.path;
    }

    public Song(Song song) {
        this.speed = song.getSpeed();
        this.delay = 20.0f / this.speed;
        this.layerHashMap = song.getLayerHashMap();
        this.songHeight = song.getSongHeight();
        this.length = song.getLength();
        this.title = song.getTitle();
        this.author = song.getAuthor();
        this.description = song.getDescription();
        this.path = song.getPath();
    }

    public HashMap<Integer, Layer> getLayerHashMap() {
        return this.layerHashMap;
    }
}

