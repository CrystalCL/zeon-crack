/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.ToNativeContext;
import java.lang.reflect.Method;

public class CallbackResultContext
extends ToNativeContext {
    private /* synthetic */ Method method;

    CallbackResultContext(Method lIllllllllIIlII) {
        CallbackResultContext lIllllllllIIIll;
        lIllllllllIIIll.method = lIllllllllIIlII;
    }

    public Method getMethod() {
        CallbackResultContext lIlllllllIlllll;
        return lIlllllllIlllll.method;
    }
}

