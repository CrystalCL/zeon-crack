/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.win32;

import com.sun.jna.FunctionMapper;
import com.sun.jna.NativeLibrary;
import java.lang.reflect.Method;

public class W32APIFunctionMapper
implements FunctionMapper {
    private final /* synthetic */ String suffix;
    public static final /* synthetic */ FunctionMapper ASCII;
    public static final /* synthetic */ FunctionMapper UNICODE;

    protected W32APIFunctionMapper(boolean llllllllllllllllIlIllIlllIIIllII) {
        W32APIFunctionMapper llllllllllllllllIlIllIlllIIIllll;
        llllllllllllllllIlIllIlllIIIllll.suffix = llllllllllllllllIlIllIlllIIIllII ? "W" : "A";
    }

    static {
        UNICODE = new W32APIFunctionMapper(true);
        ASCII = new W32APIFunctionMapper(false);
    }

    @Override
    public String getFunctionName(NativeLibrary llllllllllllllllIlIllIlllIIIIIIl, Method llllllllllllllllIlIllIlllIIIIlII) {
        String llllllllllllllllIlIllIlllIIIIIll = llllllllllllllllIlIllIlllIIIIlII.getName();
        if (!llllllllllllllllIlIllIlllIIIIIll.endsWith("W") && !llllllllllllllllIlIllIlllIIIIIll.endsWith("A")) {
            try {
                W32APIFunctionMapper llllllllllllllllIlIllIlllIIIIIlI;
                llllllllllllllllIlIllIlllIIIIIll = llllllllllllllllIlIllIlllIIIIIIl.getFunction(String.valueOf(new StringBuilder().append(llllllllllllllllIlIllIlllIIIIIll).append(llllllllllllllllIlIllIlllIIIIIlI.suffix)), 63).getName();
            }
            catch (UnsatisfiedLinkError llllllllllllllllIlIllIllIllllllI) {
                // empty catch block
            }
        }
        return llllllllllllllllIlIllIlllIIIIIll;
    }
}

