/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.ToNativeContext;

public class FunctionParameterContext
extends ToNativeContext {
    private /* synthetic */ Function function;
    private /* synthetic */ int index;
    private /* synthetic */ Object[] args;

    public Object[] getParameters() {
        FunctionParameterContext lIllIIIIIIlIlI;
        return lIllIIIIIIlIlI.args;
    }

    FunctionParameterContext(Function lIllIIIIIlIIlI, Object[] lIllIIIIIlIIIl, int lIllIIIIIlIIII) {
        FunctionParameterContext lIllIIIIIlIIll;
        lIllIIIIIlIIll.function = lIllIIIIIlIIlI;
        lIllIIIIIlIIll.args = lIllIIIIIlIIIl;
        lIllIIIIIlIIll.index = lIllIIIIIlIIII;
    }

    public int getParameterIndex() {
        FunctionParameterContext lIllIIIIIIlIII;
        return lIllIIIIIIlIII.index;
    }

    public Function getFunction() {
        FunctionParameterContext lIllIIIIIIllIl;
        return lIllIIIIIIllIl.function;
    }
}

