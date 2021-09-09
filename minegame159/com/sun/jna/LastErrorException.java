/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Platform;

public class LastErrorException
extends RuntimeException {
    private static final /* synthetic */ long serialVersionUID = 1L;
    private /* synthetic */ int errorCode;

    private static String parseMessage(String lIIllIlIlllIllI) {
        try {
            return LastErrorException.formatMessage(Integer.parseInt(lIIllIlIlllIllI));
        }
        catch (NumberFormatException lIIllIlIlllIlll) {
            return lIIllIlIlllIllI;
        }
    }

    public LastErrorException(String lIIllIlIllIlIIl) {
        super(LastErrorException.parseMessage(lIIllIlIllIlIIl.trim()));
        LastErrorException lIIllIlIllIllII;
        try {
            if (lIIllIlIllIlIIl.startsWith("[")) {
                lIIllIlIllIlIIl = lIIllIlIllIlIIl.substring(1, lIIllIlIllIlIIl.indexOf("]"));
            }
            lIIllIlIllIllII.errorCode = Integer.parseInt(lIIllIlIllIlIIl);
        }
        catch (NumberFormatException lIIllIlIllIllIl) {
            lIIllIlIllIllII.errorCode = -1;
        }
    }

    private static String formatMessage(int lIIllIlIllllIlI) {
        return Platform.isWindows() ? String.valueOf(new StringBuilder().append("GetLastError() returned ").append(lIIllIlIllllIlI)) : String.valueOf(new StringBuilder().append("errno was ").append(lIIllIlIllllIlI));
    }

    public LastErrorException(int lIIllIlIllIIlII) {
        lIIllIlIllIIIll(lIIllIlIllIIlII, LastErrorException.formatMessage(lIIllIlIllIIlII));
        LastErrorException lIIllIlIllIIIll;
    }

    public int getErrorCode() {
        LastErrorException lIIllIlIlllIIIl;
        return lIIllIlIlllIIIl.errorCode;
    }

    protected LastErrorException(int lIIllIlIlIllIlI, String lIIllIlIlIllIIl) {
        super(lIIllIlIlIllIIl);
        LastErrorException lIIllIlIlIllIll;
        lIIllIlIlIllIll.errorCode = lIIllIlIlIllIlI;
    }
}

