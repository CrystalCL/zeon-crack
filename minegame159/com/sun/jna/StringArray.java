/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeString;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringArray
extends Memory
implements Function.PostCallRead {
    private /* synthetic */ Object[] original;
    private /* synthetic */ String encoding;
    private /* synthetic */ List<NativeString> natives;

    public StringArray(String[] llllllllllllllllllIIllllIlllIlII, boolean llllllllllllllllllIIllllIlllIllI) {
        llllllllllllllllllIIllllIlllIlIl((Object[])llllllllllllllllllIIllllIlllIlII, llllllllllllllllllIIllllIlllIllI ? "--WIDE-STRING--" : Native.getDefaultStringEncoding());
        StringArray llllllllllllllllllIIllllIlllIlIl;
    }

    public StringArray(String[] llllllllllllllllllIIllllIllIlIll, String llllllllllllllllllIIllllIllIlIlI) {
        llllllllllllllllllIIllllIllIllII((Object[])llllllllllllllllllIIllllIllIlIll, llllllllllllllllllIIllllIllIlIlI);
        StringArray llllllllllllllllllIIllllIllIllII;
    }

    public StringArray(String[] llllllllllllllllllIIllllIlllllII) {
        llllllllllllllllllIIllllIlllllIl(llllllllllllllllllIIllllIlllllII, false);
        StringArray llllllllllllllllllIIllllIlllllIl;
    }

    private StringArray(Object[] llllllllllllllllllIIllllIlIlIllI, String llllllllllllllllllIIllllIlIllIII) {
        super((llllllllllllllllllIIllllIlIlIllI.length + 1) * Pointer.SIZE);
        StringArray llllllllllllllllllIIllllIlIlIlll;
        llllllllllllllllllIIllllIlIlIlll.natives = new ArrayList<NativeString>();
        llllllllllllllllllIIllllIlIlIlll.original = llllllllllllllllllIIllllIlIlIllI;
        llllllllllllllllllIIllllIlIlIlll.encoding = llllllllllllllllllIIllllIlIllIII;
        for (int llllllllllllllllllIIllllIlIllIll = 0; llllllllllllllllllIIllllIlIllIll < llllllllllllllllllIIllllIlIlIllI.length; ++llllllllllllllllllIIllllIlIllIll) {
            Pointer llllllllllllllllllIIllllIlIlllII = null;
            if (llllllllllllllllllIIllllIlIlIllI[llllllllllllllllllIIllllIlIllIll] != null) {
                NativeString llllllllllllllllllIIllllIlIlllIl = new NativeString(llllllllllllllllllIIllllIlIlIllI[llllllllllllllllllIIllllIlIllIll].toString(), llllllllllllllllllIIllllIlIllIII);
                llllllllllllllllllIIllllIlIlIlll.natives.add(llllllllllllllllllIIllllIlIlllIl);
                llllllllllllllllllIIllllIlIlllII = llllllllllllllllllIIllllIlIlllIl.getPointer();
            }
            llllllllllllllllllIIllllIlIlIlll.setPointer(Pointer.SIZE * llllllllllllllllllIIllllIlIllIll, llllllllllllllllllIIllllIlIlllII);
        }
        llllllllllllllllllIIllllIlIlIlll.setPointer(Pointer.SIZE * llllllllllllllllllIIllllIlIlIllI.length, null);
    }

    @Override
    public void read() {
        StringArray llllllllllllllllllIIllllIlIIlIII;
        boolean llllllllllllllllllIIllllIlIIIlll = llllllllllllllllllIIllllIlIIlIII.original instanceof WString[];
        boolean llllllllllllllllllIIllllIlIIIllI = "--WIDE-STRING--".equals(llllllllllllllllllIIllllIlIIlIII.encoding);
        for (int llllllllllllllllllIIllllIlIIlIIl = 0; llllllllllllllllllIIllllIlIIlIIl < llllllllllllllllllIIllllIlIIlIII.original.length; ++llllllllllllllllllIIllllIlIIlIIl) {
            Pointer llllllllllllllllllIIllllIlIIlIll = llllllllllllllllllIIllllIlIIlIII.getPointer(llllllllllllllllllIIllllIlIIlIIl * Pointer.SIZE);
            CharSequence llllllllllllllllllIIllllIlIIlIlI = null;
            if (llllllllllllllllllIIllllIlIIlIll != null) {
                String string = llllllllllllllllllIIllllIlIIlIlI = llllllllllllllllllIIllllIlIIIllI ? llllllllllllllllllIIllllIlIIlIll.getWideString(0L) : llllllllllllllllllIIllllIlIIlIll.getString(0L, llllllllllllllllllIIllllIlIIlIII.encoding);
                if (llllllllllllllllllIIllllIlIIIlll) {
                    llllllllllllllllllIIllllIlIIlIlI = new WString((String)llllllllllllllllllIIllllIlIIlIlI);
                }
            }
            llllllllllllllllllIIllllIlIIlIII.original[llllllllllllllllllIIllllIlIIlIIl] = llllllllllllllllllIIllllIlIIlIlI;
        }
    }

    public StringArray(WString[] llllllllllllllllllIIllllIllIIlII) {
        llllllllllllllllllIIllllIllIIlIl(llllllllllllllllllIIllllIllIIlII, "--WIDE-STRING--");
        StringArray llllllllllllllllllIIllllIllIIlIl;
    }

    @Override
    public String toString() {
        StringArray llllllllllllllllllIIllllIIlllIIl;
        boolean llllllllllllllllllIIllllIIlllIll = "--WIDE-STRING--".equals(llllllllllllllllllIIllllIIlllIIl.encoding);
        String llllllllllllllllllIIllllIIlllIlI = llllllllllllllllllIIllllIIlllIll ? "const wchar_t*[]" : "const char*[]";
        llllllllllllllllllIIllllIIlllIlI = String.valueOf(new StringBuilder().append(llllllllllllllllllIIllllIIlllIlI).append(Arrays.asList(llllllllllllllllllIIllllIIlllIIl.original)));
        return llllllllllllllllllIIllllIIlllIlI;
    }
}

