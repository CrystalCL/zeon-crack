/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.ptr;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.ByReference;

public class NativeLongByReference
extends ByReference {
    public void setValue(NativeLong lIIlllIllIlIIll) {
        NativeLongByReference lIIlllIllIlIIlI;
        lIIlllIllIlIIlI.getPointer().setNativeLong(0L, lIIlllIllIlIIll);
    }

    public NativeLong getValue() {
        NativeLongByReference lIIlllIllIIlllI;
        return lIIlllIllIIlllI.getPointer().getNativeLong(0L);
    }

    public NativeLongByReference() {
        lIIlllIllIlllIl(new NativeLong(0L));
        NativeLongByReference lIIlllIllIlllIl;
    }

    public NativeLongByReference(NativeLong lIIlllIllIlIlll) {
        super(NativeLong.SIZE);
        NativeLongByReference lIIlllIllIllIlI;
        lIIlllIllIllIlI.setValue(lIIlllIllIlIlll);
    }
}

