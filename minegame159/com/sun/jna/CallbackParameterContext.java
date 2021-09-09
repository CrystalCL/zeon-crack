/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import java.lang.reflect.Method;

public class CallbackParameterContext
extends FromNativeContext {
    private /* synthetic */ Object[] args;
    private /* synthetic */ Method method;
    private /* synthetic */ int index;

    public Method getMethod() {
        CallbackParameterContext lIllIIlllIlIll;
        return lIllIIlllIlIll.method;
    }

    public Object[] getArguments() {
        CallbackParameterContext lIllIIlllIlIII;
        return lIllIIlllIlIII.args;
    }

    public int getIndex() {
        CallbackParameterContext lIllIIlllIIlIl;
        return lIllIIlllIIlIl.index;
    }

    CallbackParameterContext(Class<?> lIllIIllllIllI, Method lIllIIllllIlIl, Object[] lIllIIllllIlII, int lIllIIllllIIll) {
        super(lIllIIllllIllI);
        CallbackParameterContext lIllIIllllIIlI;
        lIllIIllllIIlI.method = lIllIIllllIlIl;
        lIllIIllllIIlI.args = lIllIIllllIlII;
        lIllIIllllIIlI.index = lIllIIllllIIll;
    }
}

