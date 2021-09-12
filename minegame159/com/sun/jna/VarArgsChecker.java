/*
 * Decompiled with CFR 0.151.
 */
package com.sun.jna;

import java.lang.reflect.Method;

abstract class VarArgsChecker {
    abstract boolean isVarArgs(Method var1);

    abstract int fixedArgs(Method var1);

    static VarArgsChecker create() {
        block5: {
            try {
                Method method = Method.class.getMethod("isVarArgs", new Class[0]);
                if (method == null) break block5;
                return new RealVarArgsChecker(null);
            }
            catch (NoSuchMethodException | SecurityException exception) {
                return new NoVarArgsChecker(null);
            }
        }
        try {
            return new NoVarArgsChecker(null);
        }
        catch (SecurityException securityException) {
            return new NoVarArgsChecker(null);
        }
    }

    VarArgsChecker(1 var1_1) {
        this();
    }

    private VarArgsChecker() {
    }

    private static final class RealVarArgsChecker
    extends VarArgsChecker {
        @Override
        int fixedArgs(Method method) {
            return method.isVarArgs() ? method.getParameterTypes().length - 1 : 0;
        }

        private RealVarArgsChecker() {
            super(null);
        }

        @Override
        boolean isVarArgs(Method method) {
            return method.isVarArgs();
        }

        RealVarArgsChecker(1 var1_1) {
            this();
        }
    }

    private static final class NoVarArgsChecker
    extends VarArgsChecker {
        NoVarArgsChecker(1 var1_1) {
            this();
        }

        private NoVarArgsChecker() {
            super(null);
        }

        @Override
        boolean isVarArgs(Method method) {
            return false;
        }

        @Override
        int fixedArgs(Method method) {
            return 0;
        }
    }
}

