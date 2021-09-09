/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class IntByReference
extends ByReference {
    public IntByReference() {
        llllllllllllllllIlIllllllllIllII(0);
        IntByReference llllllllllllllllIlIllllllllIllII;
    }

    public IntByReference(int llllllllllllllllIlIllllllllIIllI) {
        super(4);
        IntByReference llllllllllllllllIlIllllllllIlIIl;
        llllllllllllllllIlIllllllllIlIIl.setValue(llllllllllllllllIlIllllllllIIllI);
    }

    public void setValue(int llllllllllllllllIlIllllllllIIIlI) {
        IntByReference llllllllllllllllIlIllllllllIIIIl;
        llllllllllllllllIlIllllllllIIIIl.getPointer().setInt(0L, llllllllllllllllIlIllllllllIIIlI);
    }

    public int getValue() {
        IntByReference llllllllllllllllIlIlllllllIlllIl;
        return llllllllllllllllIlIlllllllIlllIl.getPointer().getInt(0L);
    }
}

