/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.FunctionParameterContext;
import java.lang.reflect.Method;

public class MethodParameterContext
extends FunctionParameterContext {
    private /* synthetic */ Method method;

    public Method getMethod() {
        MethodParameterContext llllllllllllllllllIlIlIIlIIIIIIl;
        return llllllllllllllllllIlIlIIlIIIIIIl.method;
    }

    MethodParameterContext(Function llllllllllllllllllIlIlIIlIIIIllI, Object[] llllllllllllllllllIlIlIIlIIIIlIl, int llllllllllllllllllIlIlIIlIIIlIIl, Method llllllllllllllllllIlIlIIlIIIIIll) {
        super(llllllllllllllllllIlIlIIlIIIIllI, llllllllllllllllllIlIlIIlIIIIlIl, llllllllllllllllllIlIlIIlIIIlIIl);
        MethodParameterContext llllllllllllllllllIlIlIIlIIIllII;
        llllllllllllllllllIlIlIIlIIIllII.method = llllllllllllllllllIlIlIIlIIIIIll;
    }
}

