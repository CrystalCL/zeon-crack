/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class FloatByReference
extends ByReference {
    public FloatByReference(float lllllIlIllllIll) {
        super(4);
        FloatByReference lllllIlIlllllII;
        lllllIlIlllllII.setValue(lllllIlIllllIll);
    }

    public FloatByReference() {
        lllllIllIIIIIIl(0.0f);
        FloatByReference lllllIllIIIIIIl;
    }

    public void setValue(float lllllIlIlllIlll) {
        FloatByReference lllllIlIllllIII;
        lllllIlIllllIII.getPointer().setFloat(0L, lllllIlIlllIlll);
    }

    public float getValue() {
        FloatByReference lllllIlIlllIIll;
        return lllllIlIlllIIll.getPointer().getFloat(0L);
    }
}

