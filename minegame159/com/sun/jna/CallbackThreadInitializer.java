/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Callback;

public class CallbackThreadInitializer {
    private /* synthetic */ ThreadGroup group;
    private /* synthetic */ boolean detach;
    private /* synthetic */ String name;
    private /* synthetic */ boolean daemon;

    public CallbackThreadInitializer(boolean llllllllllllllllllIlllIllllIlllI, boolean llllllllllllllllllIlllIlllllIIIl, String llllllllllllllllllIlllIllllIllII) {
        llllllllllllllllllIlllIlllllIIll(llllllllllllllllllIlllIllllIlllI, llllllllllllllllllIlllIlllllIIIl, llllllllllllllllllIlllIllllIllII, null);
        CallbackThreadInitializer llllllllllllllllllIlllIlllllIIll;
    }

    public String getName(Callback llllllllllllllllllIlllIlllIllIlI) {
        CallbackThreadInitializer llllllllllllllllllIlllIlllIllIIl;
        return llllllllllllllllllIlllIlllIllIIl.name;
    }

    public ThreadGroup getThreadGroup(Callback llllllllllllllllllIlllIlllIlIllI) {
        CallbackThreadInitializer llllllllllllllllllIlllIlllIlIlIl;
        return llllllllllllllllllIlllIlllIlIlIl.group;
    }

    public CallbackThreadInitializer() {
        llllllllllllllllllIllllIIIIIlIII(true);
        CallbackThreadInitializer llllllllllllllllllIllllIIIIIlIII;
    }

    public CallbackThreadInitializer(boolean llllllllllllllllllIllllIIIIIIIll) {
        llllllllllllllllllIllllIIIIIIlII(llllllllllllllllllIllllIIIIIIIll, false);
        CallbackThreadInitializer llllllllllllllllllIllllIIIIIIlII;
    }

    public CallbackThreadInitializer(boolean llllllllllllllllllIlllIllllllIIl, boolean llllllllllllllllllIlllIllllllIII) {
        llllllllllllllllllIlllIllllllIlI(llllllllllllllllllIlllIllllllIIl, llllllllllllllllllIlllIllllllIII, null);
        CallbackThreadInitializer llllllllllllllllllIlllIllllllIlI;
    }

    public boolean isDaemon(Callback llllllllllllllllllIlllIlllIlIIlI) {
        CallbackThreadInitializer llllllllllllllllllIlllIlllIlIIll;
        return llllllllllllllllllIlllIlllIlIIll.daemon;
    }

    public CallbackThreadInitializer(boolean llllllllllllllllllIlllIllllIIlIl, boolean llllllllllllllllllIlllIlllIlllll, String llllllllllllllllllIlllIllllIIIll, ThreadGroup llllllllllllllllllIlllIllllIIIlI) {
        CallbackThreadInitializer llllllllllllllllllIlllIllllIIllI;
        llllllllllllllllllIlllIllllIIllI.daemon = llllllllllllllllllIlllIllllIIlIl;
        llllllllllllllllllIlllIllllIIllI.detach = llllllllllllllllllIlllIlllIlllll;
        llllllllllllllllllIlllIllllIIllI.name = llllllllllllllllllIlllIllllIIIll;
        llllllllllllllllllIlllIllllIIllI.group = llllllllllllllllllIlllIllllIIIlI;
    }

    public boolean detach(Callback llllllllllllllllllIlllIlllIIlllI) {
        CallbackThreadInitializer llllllllllllllllllIlllIlllIIllll;
        return llllllllllllllllllIlllIlllIIllll.detach;
    }
}

