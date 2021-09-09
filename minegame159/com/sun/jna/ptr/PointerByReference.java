/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.ptr;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByReference;

public class PointerByReference
extends ByReference {
    public PointerByReference() {
        lllllllllllllllllIllIllIllIIllIl(null);
        PointerByReference lllllllllllllllllIllIllIllIIllIl;
    }

    public Pointer getValue() {
        PointerByReference lllllllllllllllllIllIllIlIlllllI;
        return lllllllllllllllllIllIllIlIlllllI.getPointer().getPointer(0L);
    }

    public PointerByReference(Pointer lllllllllllllllllIllIllIllIIlIIl) {
        super(Pointer.SIZE);
        PointerByReference lllllllllllllllllIllIllIllIIlIlI;
        lllllllllllllllllIllIllIllIIlIlI.setValue(lllllllllllllllllIllIllIllIIlIIl);
    }

    public void setValue(Pointer lllllllllllllllllIllIllIllIIIIIl) {
        PointerByReference lllllllllllllllllIllIllIllIIIlII;
        lllllllllllllllllIllIllIllIIIlII.getPointer().setPointer(0L, lllllllllllllllllIllIllIllIIIIIl);
    }
}

