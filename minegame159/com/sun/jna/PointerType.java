/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;

public abstract class PointerType
implements NativeMapped {
    private /* synthetic */ Pointer pointer;

    public String toString() {
        PointerType llllllllllllllllllllIlIlIIllllII;
        return llllllllllllllllllllIlIlIIllllII.pointer == null ? "NULL" : String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIlIIllllII.pointer.toString()).append(" (").append(super.toString()).append(")"));
    }

    @Override
    public Object fromNative(Object llllllllllllllllllllIlIlIlIIllII, FromNativeContext llllllllllllllllllllIlIlIlIIlllI) {
        PointerType llllllllllllllllllllIlIlIlIIllIl;
        if (llllllllllllllllllllIlIlIlIIllII == null) {
            return null;
        }
        try {
            PointerType llllllllllllllllllllIlIlIlIlIIll = (PointerType)llllllllllllllllllllIlIlIlIIllIl.getClass().newInstance();
            llllllllllllllllllllIlIlIlIlIIll.pointer = (Pointer)llllllllllllllllllllIlIlIlIIllII;
            return llllllllllllllllllllIlIlIlIlIIll;
        }
        catch (InstantiationException llllllllllllllllllllIlIlIlIlIIlI) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Can't instantiate ").append(llllllllllllllllllllIlIlIlIIllIl.getClass())));
        }
        catch (IllegalAccessException llllllllllllllllllllIlIlIlIlIIIl) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Not allowed to instantiate ").append(llllllllllllllllllllIlIlIlIIllIl.getClass())));
        }
    }

    public int hashCode() {
        PointerType llllllllllllllllllllIlIlIlIIlIIl;
        return llllllllllllllllllllIlIlIlIIlIIl.pointer != null ? llllllllllllllllllllIlIlIlIIlIIl.pointer.hashCode() : 0;
    }

    public Pointer getPointer() {
        PointerType llllllllllllllllllllIlIlIlIllllI;
        return llllllllllllllllllllIlIlIlIllllI.pointer;
    }

    protected PointerType(Pointer llllllllllllllllllllIlIlIllIIllI) {
        PointerType llllllllllllllllllllIlIlIllIIlIl;
        llllllllllllllllllllIlIlIllIIlIl.pointer = llllllllllllllllllllIlIlIllIIllI;
    }

    @Override
    public Object toNative() {
        PointerType llllllllllllllllllllIlIlIllIIIIl;
        return llllllllllllllllllllIlIlIllIIIIl.getPointer();
    }

    public void setPointer(Pointer llllllllllllllllllllIlIlIlIlIlll) {
        llllllllllllllllllllIlIlIlIllIII.pointer = llllllllllllllllllllIlIlIlIlIlll;
    }

    protected PointerType() {
        PointerType llllllllllllllllllllIlIlIllIlIlI;
        llllllllllllllllllllIlIlIllIlIlI.pointer = Pointer.NULL;
    }

    @Override
    public Class<?> nativeType() {
        return Pointer.class;
    }

    public boolean equals(Object llllllllllllllllllllIlIlIlIIIIII) {
        PointerType llllllllllllllllllllIlIlIlIIIIIl;
        if (llllllllllllllllllllIlIlIlIIIIII == llllllllllllllllllllIlIlIlIIIIIl) {
            return true;
        }
        if (llllllllllllllllllllIlIlIlIIIIII instanceof PointerType) {
            Pointer llllllllllllllllllllIlIlIlIIIlII = ((PointerType)llllllllllllllllllllIlIlIlIIIIII).getPointer();
            if (llllllllllllllllllllIlIlIlIIIIIl.pointer == null) {
                return llllllllllllllllllllIlIlIlIIIlII == null;
            }
            return llllllllllllllllllllIlIlIlIIIIIl.pointer.equals(llllllllllllllllllllIlIlIlIIIlII);
        }
        return false;
    }
}

