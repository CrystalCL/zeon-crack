/*
 * Decompiled with CFR 0.151.
 */
package com.sun.jna.win32;

import com.sun.jna.FunctionMapper;
import com.sun.jna.NativeLibrary;
import java.lang.reflect.Method;

public class W32APIFunctionMapper
implements FunctionMapper {
    public static final FunctionMapper UNICODE = new W32APIFunctionMapper(true);
    private final String suffix;
    public static final FunctionMapper ASCII = new W32APIFunctionMapper(false);

    protected W32APIFunctionMapper(boolean bl) {
        this.suffix = bl ? "W" : "A";
    }

    @Override
    public String getFunctionName(NativeLibrary nativeLibrary, Method method) {
        String string = method.getName();
        if (!string.endsWith("W") && !string.endsWith("A")) {
            try {
                string = nativeLibrary.getFunction(String.valueOf(new StringBuilder().append(string).append(this.suffix)), 63).getName();
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                // empty catch block
            }
        }
        return string;
    }
}

