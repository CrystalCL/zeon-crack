/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Function;

public class FunctionResultContext
extends FromNativeContext {
    private /* synthetic */ Object[] args;
    private /* synthetic */ Function function;

    public Object[] getArguments() {
        FunctionResultContext llllllllllllllllIlIllllllIIllIII;
        return llllllllllllllllIlIllllllIIllIII.args;
    }

    FunctionResultContext(Class<?> llllllllllllllllIlIllllllIlIIIII, Function llllllllllllllllIlIllllllIlIIIll, Object[] llllllllllllllllIlIllllllIIllllI) {
        super(llllllllllllllllIlIllllllIlIIIII);
        FunctionResultContext llllllllllllllllIlIllllllIlIIIIl;
        llllllllllllllllIlIllllllIlIIIIl.function = llllllllllllllllIlIllllllIlIIIll;
        llllllllllllllllIlIllllllIlIIIIl.args = llllllllllllllllIlIllllllIIllllI;
    }

    public Function getFunction() {
        FunctionResultContext llllllllllllllllIlIllllllIIlllII;
        return llllllllllllllllIlIllllllIIlllII.function;
    }
}

