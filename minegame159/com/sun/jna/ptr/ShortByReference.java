/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class ShortByReference
extends ByReference {
    public ShortByReference(short lIllIIlIIlllllI) {
        super(2);
        ShortByReference lIllIIlIIllllIl;
        lIllIIlIIllllIl.setValue(lIllIIlIIlllllI);
    }

    public ShortByReference() {
        lIllIIlIlIIIIll(0);
        ShortByReference lIllIIlIlIIIIll;
    }

    public short getValue() {
        ShortByReference lIllIIlIIllIlII;
        return lIllIIlIIllIlII.getPointer().getShort(0L);
    }

    public void setValue(short lIllIIlIIllIllI) {
        ShortByReference lIllIIlIIlllIIl;
        lIllIIlIIlllIIl.getPointer().setShort(0L, lIllIIlIIllIllI);
    }
}

