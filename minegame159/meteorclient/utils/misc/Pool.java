/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.misc;

import java.util.ArrayDeque;
import java.util.Queue;
import minegame159.meteorclient.utils.misc.Producer;

public class Pool<T> {
    private final /* synthetic */ Queue<T> items;
    private final /* synthetic */ Producer<T> producer;

    public Pool(Producer<T> lllllllllllllllllIIlIIllIIllIlII) {
        Pool lllllllllllllllllIIlIIllIIllIlIl;
        lllllllllllllllllIIlIIllIIllIlIl.items = new ArrayDeque<T>();
        lllllllllllllllllIIlIIllIIllIlIl.producer = lllllllllllllllllIIlIIllIIllIlII;
    }

    public synchronized void free(T lllllllllllllllllIIlIIllIIlIlIll) {
        Pool lllllllllllllllllIIlIIllIIlIlIlI;
        lllllllllllllllllIIlIIllIIlIlIlI.items.offer(lllllllllllllllllIIlIIllIIlIlIll);
    }

    public synchronized T get() {
        Pool lllllllllllllllllIIlIIllIIllIIII;
        if (lllllllllllllllllIIlIIllIIllIIII.items.size() > 0) {
            return lllllllllllllllllIIlIIllIIllIIII.items.poll();
        }
        return lllllllllllllllllIIlIIllIIllIIII.producer.create();
    }
}

