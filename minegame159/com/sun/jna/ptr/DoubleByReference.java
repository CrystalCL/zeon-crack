/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class DoubleByReference
extends ByReference {
    public double getValue() {
        DoubleByReference llllllllllllllllllIIlIIlllllllII;
        return llllllllllllllllllIIlIIlllllllII.getPointer().getDouble(0L);
    }

    public DoubleByReference() {
        llllllllllllllllllIIlIlIIIIIllII(0.0);
        DoubleByReference llllllllllllllllllIIlIlIIIIIllII;
    }

    public void setValue(double llllllllllllllllllIIlIlIIIIIIIIl) {
        DoubleByReference llllllllllllllllllIIlIlIIIIIIIlI;
        llllllllllllllllllIIlIlIIIIIIIlI.getPointer().setDouble(0L, llllllllllllllllllIIlIlIIIIIIIIl);
    }

    public DoubleByReference(double llllllllllllllllllIIlIlIIIIIIlIl) {
        super(8);
        DoubleByReference llllllllllllllllllIIlIlIIIIIIllI;
        llllllllllllllllllIIlIlIIIIIIllI.setValue(llllllllllllllllllIIlIlIIIIIIlIl);
    }
}

