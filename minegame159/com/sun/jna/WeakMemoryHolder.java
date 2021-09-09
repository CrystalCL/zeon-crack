/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Memory;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.IdentityHashMap;

public class WeakMemoryHolder {
    /* synthetic */ IdentityHashMap<Reference<Object>, Memory> backingMap;
    /* synthetic */ ReferenceQueue<Object> referenceQueue;

    public WeakMemoryHolder() {
        WeakMemoryHolder lllIlIIIlIIlIll;
        lllIlIIIlIIlIll.referenceQueue = new ReferenceQueue();
        lllIlIIIlIIlIll.backingMap = new IdentityHashMap();
    }

    public synchronized void put(Object lllIlIIIlIIIIIl, Memory lllIlIIIlIIIlII) {
        WeakMemoryHolder lllIlIIIlIIIllI;
        lllIlIIIlIIIllI.clean();
        WeakReference<Object> lllIlIIIlIIIIll = new WeakReference<Object>(lllIlIIIlIIIIIl, lllIlIIIlIIIllI.referenceQueue);
        lllIlIIIlIIIllI.backingMap.put(lllIlIIIlIIIIll, lllIlIIIlIIIlII);
    }

    public synchronized void clean() {
        WeakMemoryHolder lllIlIIIIlllIlI;
        Reference<Object> lllIlIIIIllllII = lllIlIIIIlllIlI.referenceQueue.poll();
        while (lllIlIIIIllllII != null) {
            lllIlIIIIlllIlI.backingMap.remove(lllIlIIIIllllII);
            lllIlIIIIllllII = lllIlIIIIlllIlI.referenceQueue.poll();
        }
    }
}

