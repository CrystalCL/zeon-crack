/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import java.lang.reflect.Method;

abstract class VarArgsChecker {
    abstract int fixedArgs(Method var1);

    private VarArgsChecker() {
        VarArgsChecker llIIIIIlIIIIlI;
    }

    abstract boolean isVarArgs(Method var1);

    static VarArgsChecker create() {
        try {
            Method llIIIIIIllllll = Method.class.getMethod("isVarArgs", new Class[0]);
            if (llIIIIIIllllll != null) {
                return new RealVarArgsChecker();
            }
            return new NoVarArgsChecker();
        }
        catch (NoSuchMethodException llIIIIIIlllllI) {
            return new NoVarArgsChecker();
        }
        catch (SecurityException llIIIIIIllllIl) {
            return new NoVarArgsChecker();
        }
    }

    private static final class RealVarArgsChecker
    extends VarArgsChecker {
        @Override
        boolean isVarArgs(Method llllllllllllllllIlllIlllIIllIIII) {
            return llllllllllllllllIlllIlllIIllIIII.isVarArgs();
        }

        @Override
        int fixedArgs(Method llllllllllllllllIlllIlllIIlIlIll) {
            return llllllllllllllllIlllIlllIIlIlIll.isVarArgs() ? llllllllllllllllIlllIlllIIlIlIll.getParameterTypes().length - 1 : 0;
        }

        private RealVarArgsChecker() {
            RealVarArgsChecker llllllllllllllllIlllIlllIIllIIll;
        }
    }

    private static final class NoVarArgsChecker
    extends VarArgsChecker {
        @Override
        int fixedArgs(Method llllllllllllllllllllIllIIlIIlIII) {
            return 0;
        }

        private NoVarArgsChecker() {
            NoVarArgsChecker llllllllllllllllllllIllIIlIIllIl;
        }

        @Override
        boolean isVarArgs(Method llllllllllllllllllllIllIIlIIlIlI) {
            return false;
        }
    }
}

