/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import java.nio.CharBuffer;

class NativeString
implements Comparable,
CharSequence {
    static final /* synthetic */ String WIDE_STRING;
    private /* synthetic */ String encoding;
    private /* synthetic */ Pointer pointer;

    @Override
    public CharSequence subSequence(int llllllllllllllllllIlIllIIIllllIl, int llllllllllllllllllIlIllIIIlllIIl) {
        NativeString llllllllllllllllllIlIllIIIlllllI;
        return CharBuffer.wrap(llllllllllllllllllIlIllIIIlllllI.toString()).subSequence(llllllllllllllllllIlIllIIIllllIl, llllllllllllllllllIlIllIIIlllIIl);
    }

    public int hashCode() {
        NativeString llllllllllllllllllIlIllIIlIlllIl;
        return llllllllllllllllllIlIllIIlIlllIl.toString().hashCode();
    }

    public int compareTo(Object llllllllllllllllllIlIllIIIllIIll) {
        NativeString llllllllllllllllllIlIllIIIllIllI;
        if (llllllllllllllllllIlIllIIIllIIll == null) {
            return 1;
        }
        return llllllllllllllllllIlIllIIIllIllI.toString().compareTo(llllllllllllllllllIlIllIIIllIIll.toString());
    }

    public boolean equals(Object llllllllllllllllllIlIllIIlIlIlll) {
        if (llllllllllllllllllIlIllIIlIlIlll instanceof CharSequence) {
            NativeString llllllllllllllllllIlIllIIlIllIII;
            return llllllllllllllllllIlIllIIlIllIII.compareTo(llllllllllllllllllIlIllIIlIlIlll) == 0;
        }
        return false;
    }

    @Override
    public char charAt(int llllllllllllllllllIlIllIIlIIIlIl) {
        NativeString llllllllllllllllllIlIllIIlIIIllI;
        return llllllllllllllllllIlIllIIlIIIllI.toString().charAt(llllllllllllllllllIlIllIIlIIIlIl);
    }

    public NativeString(String llllllllllllllllllIlIllIIlllIlll, boolean llllllllllllllllllIlIllIIlllIIll) {
        llllllllllllllllllIlIllIIlllIlIl(llllllllllllllllllIlIllIIlllIlll, llllllllllllllllllIlIllIIlllIIll ? "--WIDE-STRING--" : Native.getDefaultStringEncoding());
        NativeString llllllllllllllllllIlIllIIlllIlIl;
    }

    public NativeString(WString llllllllllllllllllIlIllIIllIllll) {
        llllllllllllllllllIlIllIIllIlllI(llllllllllllllllllIlIllIIllIllll.toString(), "--WIDE-STRING--");
        NativeString llllllllllllllllllIlIllIIllIlllI;
    }

    public Pointer getPointer() {
        NativeString llllllllllllllllllIlIllIIlIIlIll;
        return llllllllllllllllllIlIllIIlIIlIll.pointer;
    }

    @Override
    public int length() {
        NativeString llllllllllllllllllIlIllIIlIIIIll;
        return llllllllllllllllllIlIllIIlIIIIll.toString().length();
    }

    public NativeString(String llllllllllllllllllIlIllIIllIIlIl, String llllllllllllllllllIlIllIIllIIlII) {
        NativeString llllllllllllllllllIlIllIIllIIIll;
        if (llllllllllllllllllIlIllIIllIIlIl == null) {
            throw new NullPointerException("String must not be null");
        }
        llllllllllllllllllIlIllIIllIIIll.encoding = llllllllllllllllllIlIllIIllIIlII;
        if ("--WIDE-STRING--".equals(llllllllllllllllllIlIllIIllIIIll.encoding)) {
            int llllllllllllllllllIlIllIIllIlIII = (llllllllllllllllllIlIllIIllIIlIl.length() + 1) * Native.WCHAR_SIZE;
            llllllllllllllllllIlIllIIllIIIll.pointer = llllllllllllllllllIlIllIIllIIIll.new StringMemory(llllllllllllllllllIlIllIIllIlIII);
            llllllllllllllllllIlIllIIllIIIll.pointer.setWideString(0L, llllllllllllllllllIlIllIIllIIlIl);
        } else {
            byte[] llllllllllllllllllIlIllIIllIIlll = Native.getBytes(llllllllllllllllllIlIllIIllIIlIl, llllllllllllllllllIlIllIIllIIlII);
            llllllllllllllllllIlIllIIllIIIll.pointer = llllllllllllllllllIlIllIIllIIIll.new StringMemory(llllllllllllllllllIlIllIIllIIlll.length + 1);
            llllllllllllllllllIlIllIIllIIIll.pointer.write(0L, llllllllllllllllllIlIllIIllIIlll, 0, llllllllllllllllllIlIllIIllIIlll.length);
            llllllllllllllllllIlIllIIllIIIll.pointer.setByte(llllllllllllllllllIlIllIIllIIlll.length, (byte)0);
        }
    }

    @Override
    public String toString() {
        NativeString llllllllllllllllllIlIllIIlIlIIll;
        boolean llllllllllllllllllIlIllIIlIlIIlI = "--WIDE-STRING--".equals(llllllllllllllllllIlIllIIlIlIIll.encoding);
        String llllllllllllllllllIlIllIIlIlIIIl = llllllllllllllllllIlIllIIlIlIIlI ? "const wchar_t*" : "const char*";
        llllllllllllllllllIlIllIIlIlIIIl = String.valueOf(new StringBuilder().append(llllllllllllllllllIlIllIIlIlIIIl).append("(").append(llllllllllllllllllIlIllIIlIlIIlI ? llllllllllllllllllIlIllIIlIlIIll.pointer.getWideString(0L) : llllllllllllllllllIlIllIIlIlIIll.pointer.getString(0L, llllllllllllllllllIlIllIIlIlIIll.encoding)).append(")"));
        return llllllllllllllllllIlIllIIlIlIIIl;
    }

    static {
        WIDE_STRING = "--WIDE-STRING--";
    }

    public NativeString(String llllllllllllllllllIlIllIIllllllI) {
        llllllllllllllllllIlIllIIlllllIl(llllllllllllllllllIlIllIIllllllI, Native.getDefaultStringEncoding());
        NativeString llllllllllllllllllIlIllIIlllllIl;
    }

    private class StringMemory
    extends Memory {
        public StringMemory(long lIIIllIllllIIII) {
            StringMemory lIIIllIllllIlII;
            super(lIIIllIllllIIII);
        }

        @Override
        public String toString() {
            StringMemory lIIIllIlllIlllI;
            return lIIIllIlllIlllI.NativeString.this.toString();
        }
    }
}

