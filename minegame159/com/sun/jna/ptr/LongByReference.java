/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class LongByReference
extends ByReference {
    public void setValue(long llllllllllllllllIlllIIllllIlllII) {
        LongByReference llllllllllllllllIlllIIllllIlllIl;
        llllllllllllllllIlllIIllllIlllIl.getPointer().setLong(0L, llllllllllllllllIlllIIllllIlllII);
    }

    public LongByReference(long llllllllllllllllIlllIIlllllIIIII) {
        super(8);
        LongByReference llllllllllllllllIlllIIlllllIIIll;
        llllllllllllllllIlllIIlllllIIIll.setValue(llllllllllllllllIlllIIlllllIIIII);
    }

    public long getValue() {
        LongByReference llllllllllllllllIlllIIllllIllIII;
        return llllllllllllllllIlllIIllllIllIII.getPointer().getLong(0L);
    }

    public LongByReference() {
        llllllllllllllllIlllIIlllllIIllI(0L);
        LongByReference llllllllllllllllIlllIIlllllIIllI;
    }
}

