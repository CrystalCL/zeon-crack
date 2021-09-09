/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

public final class WString
implements Comparable,
CharSequence {
    private /* synthetic */ String string;

    @Override
    public int length() {
        WString llIlllIIllllll;
        return llIlllIIllllll.toString().length();
    }

    @Override
    public CharSequence subSequence(int llIlllIIllIIIl, int llIlllIIllIIll) {
        WString llIlllIIllIIlI;
        return llIlllIIllIIlI.toString().subSequence(llIlllIIllIIIl, llIlllIIllIIll);
    }

    @Override
    public String toString() {
        WString llIlllIlIlIIlI;
        return llIlllIlIlIIlI.string;
    }

    public int hashCode() {
        WString llIlllIlIIlIIl;
        return llIlllIlIIlIIl.toString().hashCode();
    }

    public WString(String llIlllIlIlIllI) {
        WString llIlllIlIlIlIl;
        if (llIlllIlIlIllI == null) {
            throw new NullPointerException("String initializer must be non-null");
        }
        llIlllIlIlIlIl.string = llIlllIlIlIllI;
    }

    @Override
    public char charAt(int llIlllIIlllIIl) {
        WString llIlllIIllllII;
        return llIlllIIllllII.toString().charAt(llIlllIIlllIIl);
    }

    public int compareTo(Object llIlllIlIIIlII) {
        WString llIlllIlIIIIll;
        return llIlllIlIIIIll.toString().compareTo(llIlllIlIIIlII.toString());
    }

    public boolean equals(Object llIlllIlIIlIll) {
        WString llIlllIlIIlllI;
        return llIlllIlIIlIll instanceof WString && llIlllIlIIlllI.toString().equals(llIlllIlIIlIll.toString());
    }
}

