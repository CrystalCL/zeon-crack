/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.win32;

import com.sun.jna.Function;
import com.sun.jna.FunctionMapper;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.Pointer;
import java.lang.reflect.Method;

public class StdCallFunctionMapper
implements FunctionMapper {
    public StdCallFunctionMapper() {
        StdCallFunctionMapper lIlIlIIIIIlIIlI;
    }

    protected int getArgumentNativeStackSize(Class<?> lIlIlIIIIIIllII) {
        if (NativeMapped.class.isAssignableFrom(lIlIlIIIIIIllII)) {
            lIlIlIIIIIIllII = NativeMappedConverter.getInstance(lIlIlIIIIIIllII).nativeType();
        }
        if (lIlIlIIIIIIllII.isArray()) {
            return Pointer.SIZE;
        }
        try {
            return Native.getNativeSize(lIlIlIIIIIIllII);
        }
        catch (IllegalArgumentException lIlIlIIIIIIlllI) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Unknown native stack allocation size for ").append(lIlIlIIIIIIllII)));
        }
    }

    @Override
    public String getFunctionName(NativeLibrary lIlIIlllllllIlI, Method lIlIIllllllIIIl) {
        Class<?>[] lIlIIllllllIllI;
        String lIlIIlllllllIII = lIlIIllllllIIIl.getName();
        int lIlIIllllllIlll = 0;
        for (Class<?> lIlIIllllllllll : lIlIIllllllIllI = lIlIIllllllIIIl.getParameterTypes()) {
            StdCallFunctionMapper lIlIIllllllIIll;
            lIlIIllllllIlll += lIlIIllllllIIll.getArgumentNativeStackSize(lIlIIllllllllll);
        }
        String lIlIIllllllIlIl = String.valueOf(new StringBuilder().append(lIlIIlllllllIII).append("@").append(lIlIIllllllIlll));
        int lIlIIllllllIlII = 63;
        try {
            Function lIlIIlllllllllI = lIlIIlllllllIlI.getFunction(lIlIIllllllIlIl, lIlIIllllllIlII);
            lIlIIlllllllIII = lIlIIlllllllllI.getName();
        }
        catch (UnsatisfiedLinkError lIlIIllllllllII) {
            try {
                Function lIlIIllllllllIl = lIlIIlllllllIlI.getFunction(String.valueOf(new StringBuilder().append("_").append(lIlIIllllllIlIl)), lIlIIllllllIlII);
                lIlIIlllllllIII = lIlIIllllllllIl.getName();
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                // empty catch block
            }
        }
        return lIlIIlllllllIII;
    }
}

