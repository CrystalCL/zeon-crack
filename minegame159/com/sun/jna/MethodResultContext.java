/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.FunctionResultContext;
import java.lang.reflect.Method;

public class MethodResultContext
extends FunctionResultContext {
    private final /* synthetic */ Method method;

    public Method getMethod() {
        MethodResultContext lIlIIlllIlIIll;
        return lIlIIlllIlIIll.method;
    }

    MethodResultContext(Class<?> lIlIIlllIllIIl, Function lIlIIlllIllIII, Object[] lIlIIlllIlllII, Method lIlIIlllIllIll) {
        super(lIlIIlllIllIIl, lIlIIlllIllIII, lIlIIlllIlllII);
        MethodResultContext lIlIIlllIllIlI;
        lIlIIlllIllIlI.method = lIlIIlllIllIll;
    }
}

