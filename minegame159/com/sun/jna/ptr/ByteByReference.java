/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class ByteByReference
extends ByReference {
    public byte getValue() {
        ByteByReference lllllllllllllllllIlIllllIlIlIIlI;
        return lllllllllllllllllIlIllllIlIlIIlI.getPointer().getByte(0L);
    }

    public ByteByReference() {
        lllllllllllllllllIlIllllIllIIIII(0);
        ByteByReference lllllllllllllllllIlIllllIllIIIII;
    }

    public void setValue(byte lllllllllllllllllIlIllllIlIlIlII) {
        ByteByReference lllllllllllllllllIlIllllIlIlIlIl;
        lllllllllllllllllIlIllllIlIlIlIl.getPointer().setByte(0L, lllllllllllllllllIlIllllIlIlIlII);
    }

    public ByteByReference(byte lllllllllllllllllIlIllllIlIllIlI) {
        super(1);
        ByteByReference lllllllllllllllllIlIllllIlIllIll;
        lllllllllllllllllIlIllllIlIllIll.setValue(lllllllllllllllllIlIllllIlIllIlI);
    }
}

