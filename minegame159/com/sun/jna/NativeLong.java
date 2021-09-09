/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

public class NativeLong
extends IntegerType {
    private static final /* synthetic */ long serialVersionUID = 1L;
    public static final /* synthetic */ int SIZE;

    public NativeLong() {
        llllllllllllllllIllllIllllIIIlII(0L);
        NativeLong llllllllllllllllIllllIllllIIIlII;
    }

    public NativeLong(long llllllllllllllllIllllIlllIllllll) {
        llllllllllllllllIllllIllllIIIIII(llllllllllllllllIllllIlllIllllll, false);
        NativeLong llllllllllllllllIllllIllllIIIIII;
    }

    public NativeLong(long llllllllllllllllIllllIlllIlllIII, boolean llllllllllllllllIllllIlllIllIlII) {
        super(SIZE, llllllllllllllllIllllIlllIlllIII, llllllllllllllllIllllIlllIllIlII);
        NativeLong llllllllllllllllIllllIlllIllIllI;
    }

    static {
        SIZE = Native.LONG_SIZE;
    }
}

