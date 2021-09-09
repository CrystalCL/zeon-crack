/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.AltCallingConvention;
import com.sun.jna.Function;
import com.sun.jna.InvocationMapper;
import com.sun.jna.NativeLibrary;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public interface Library {
    public static final /* synthetic */ String OPTION_ALLOW_OBJECTS;
    public static final /* synthetic */ String OPTION_FUNCTION_MAPPER;
    public static final /* synthetic */ String OPTION_STRING_ENCODING;
    public static final /* synthetic */ String OPTION_INVOCATION_MAPPER;
    public static final /* synthetic */ String OPTION_CALLING_CONVENTION;
    public static final /* synthetic */ String OPTION_CLASSLOADER;
    public static final /* synthetic */ String OPTION_TYPE_MAPPER;
    public static final /* synthetic */ String OPTION_STRUCTURE_ALIGNMENT;
    public static final /* synthetic */ String OPTION_OPEN_FLAGS;

    static {
        OPTION_FUNCTION_MAPPER = "function-mapper";
        OPTION_ALLOW_OBJECTS = "allow-objects";
        OPTION_CLASSLOADER = "classloader";
        OPTION_TYPE_MAPPER = "type-mapper";
        OPTION_INVOCATION_MAPPER = "invocation-mapper";
        OPTION_CALLING_CONVENTION = "calling-convention";
        OPTION_STRUCTURE_ALIGNMENT = "structure-alignment";
        OPTION_OPEN_FLAGS = "open-flags";
        OPTION_STRING_ENCODING = "string-encoding";
    }

    public static class Handler
    implements InvocationHandler {
        private final /* synthetic */ NativeLibrary nativeLibrary;
        private final /* synthetic */ Map<Method, FunctionInfo> functions;
        private final /* synthetic */ InvocationMapper invocationMapper;
        private final /* synthetic */ Class<?> interfaceClass;
        static final /* synthetic */ Method OBJECT_EQUALS;
        static final /* synthetic */ Method OBJECT_HASHCODE;
        private final /* synthetic */ Map<String, Object> options;
        static final /* synthetic */ Method OBJECT_TOSTRING;

        public Handler(String lllllllllllllllllIIIllIIlIlIIlIl, Class<?> lllllllllllllllllIIIllIIlIlIIlII, Map<String, ?> lllllllllllllllllIIIllIIlIlIlIII) {
            int lllllllllllllllllIIIllIIlIlIIlll;
            Handler lllllllllllllllllIIIllIIlIlIlIll;
            lllllllllllllllllIIIllIIlIlIlIll.functions = new WeakHashMap<Method, FunctionInfo>();
            if (lllllllllllllllllIIIllIIlIlIIlIl != null && "".equals(lllllllllllllllllIIIllIIlIlIIlIl.trim())) {
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Invalid library name \"").append(lllllllllllllllllIIIllIIlIlIIlIl).append("\"")));
            }
            if (!lllllllllllllllllIIIllIIlIlIIlII.isInterface()) {
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(lllllllllllllllllIIIllIIlIlIIlIl).append(" does not implement an interface: ").append(lllllllllllllllllIIIllIIlIlIIlII.getName())));
            }
            lllllllllllllllllIIIllIIlIlIlIll.interfaceClass = lllllllllllllllllIIIllIIlIlIIlII;
            lllllllllllllllllIIIllIIlIlIlIll.options = new HashMap(lllllllllllllllllIIIllIIlIlIlIII);
            int n = lllllllllllllllllIIIllIIlIlIIlll = AltCallingConvention.class.isAssignableFrom(lllllllllllllllllIIIllIIlIlIIlII) ? 63 : 0;
            if (lllllllllllllllllIIIllIIlIlIlIll.options.get("calling-convention") == null) {
                lllllllllllllllllIIIllIIlIlIlIll.options.put("calling-convention", lllllllllllllllllIIIllIIlIlIIlll);
            }
            if (lllllllllllllllllIIIllIIlIlIlIll.options.get("classloader") == null) {
                lllllllllllllllllIIIllIIlIlIlIll.options.put("classloader", lllllllllllllllllIIIllIIlIlIIlII.getClassLoader());
            }
            lllllllllllllllllIIIllIIlIlIlIll.nativeLibrary = NativeLibrary.getInstance(lllllllllllllllllIIIllIIlIlIIlIl, lllllllllllllllllIIIllIIlIlIlIll.options);
            lllllllllllllllllIIIllIIlIlIlIll.invocationMapper = (InvocationMapper)lllllllllllllllllIIIllIIlIlIlIll.options.get("invocation-mapper");
        }

        public Class<?> getInterfaceClass() {
            Handler lllllllllllllllllIIIllIIlIIllIlI;
            return lllllllllllllllllIIIllIIlIIllIlI.interfaceClass;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object invoke(Object lllllllllllllllllIIIllIIlIIIIIII, Method lllllllllllllllllIIIllIIlIIIIlII, Object[] lllllllllllllllllIIIllIIlIIIIIll) throws Throwable {
            Handler lllllllllllllllllIIIllIIlIIIIIIl;
            if (OBJECT_TOSTRING.equals(lllllllllllllllllIIIllIIlIIIIlII)) {
                return String.valueOf(new StringBuilder().append("Proxy interface to ").append(lllllllllllllllllIIIllIIlIIIIIIl.nativeLibrary));
            }
            if (OBJECT_HASHCODE.equals(lllllllllllllllllIIIllIIlIIIIlII)) {
                return lllllllllllllllllIIIllIIlIIIIIIl.hashCode();
            }
            if (OBJECT_EQUALS.equals(lllllllllllllllllIIIllIIlIIIIlII)) {
                Object lllllllllllllllllIIIllIIlIIIllII = lllllllllllllllllIIIllIIlIIIIIll[0];
                if (lllllllllllllllllIIIllIIlIIIllII != null && Proxy.isProxyClass(lllllllllllllllllIIIllIIlIIIllII.getClass())) {
                    return Function.valueOf(Proxy.getInvocationHandler(lllllllllllllllllIIIllIIlIIIllII) == lllllllllllllllllIIIllIIlIIIIIIl);
                }
                return Boolean.FALSE;
            }
            FunctionInfo lllllllllllllllllIIIllIIlIIIIIlI = lllllllllllllllllIIIllIIlIIIIIIl.functions.get(lllllllllllllllllIIIllIIlIIIIlII);
            if (lllllllllllllllllIIIllIIlIIIIIlI == null) {
                Map<Method, FunctionInfo> lllllllllllllllllIIIllIIIlllllII = lllllllllllllllllIIIllIIlIIIIIIl.functions;
                synchronized (lllllllllllllllllIIIllIIIlllllII) {
                    lllllllllllllllllIIIllIIlIIIIIlI = lllllllllllllllllIIIllIIlIIIIIIl.functions.get(lllllllllllllllllIIIllIIlIIIIlII);
                    if (lllllllllllllllllIIIllIIlIIIIIlI == null) {
                        boolean lllllllllllllllllIIIllIIlIIIlIll = Function.isVarArgs(lllllllllllllllllIIIllIIlIIIIlII);
                        InvocationHandler lllllllllllllllllIIIllIIlIIIlIlI = null;
                        if (lllllllllllllllllIIIllIIlIIIIIIl.invocationMapper != null) {
                            lllllllllllllllllIIIllIIlIIIlIlI = lllllllllllllllllIIIllIIlIIIIIIl.invocationMapper.getInvocationHandler(lllllllllllllllllIIIllIIlIIIIIIl.nativeLibrary, lllllllllllllllllIIIllIIlIIIIlII);
                        }
                        Function lllllllllllllllllIIIllIIlIIIlIIl = null;
                        Class<?>[] lllllllllllllllllIIIllIIlIIIlIII = null;
                        HashMap<String, Object> lllllllllllllllllIIIllIIlIIIIlll = null;
                        if (lllllllllllllllllIIIllIIlIIIlIlI == null) {
                            lllllllllllllllllIIIllIIlIIIlIIl = lllllllllllllllllIIIllIIlIIIIIIl.nativeLibrary.getFunction(lllllllllllllllllIIIllIIlIIIIlII.getName(), lllllllllllllllllIIIllIIlIIIIlII);
                            lllllllllllllllllIIIllIIlIIIlIII = lllllllllllllllllIIIllIIlIIIIlII.getParameterTypes();
                            lllllllllllllllllIIIllIIlIIIIlll = new HashMap<String, Object>(lllllllllllllllllIIIllIIlIIIIIIl.options);
                            lllllllllllllllllIIIllIIlIIIIlll.put("invoking-method", lllllllllllllllllIIIllIIlIIIIlII);
                        }
                        lllllllllllllllllIIIllIIlIIIIIlI = new FunctionInfo(lllllllllllllllllIIIllIIlIIIlIlI, lllllllllllllllllIIIllIIlIIIlIIl, lllllllllllllllllIIIllIIlIIIlIII, lllllllllllllllllIIIllIIlIIIlIll, lllllllllllllllllIIIllIIlIIIIlll);
                        lllllllllllllllllIIIllIIlIIIIIIl.functions.put(lllllllllllllllllIIIllIIlIIIIlII, lllllllllllllllllIIIllIIlIIIIIlI);
                    }
                }
            }
            if (lllllllllllllllllIIIllIIlIIIIIlI.isVarArgs) {
                lllllllllllllllllIIIllIIlIIIIIll = Function.concatenateVarArgs(lllllllllllllllllIIIllIIlIIIIIll);
            }
            if (lllllllllllllllllIIIllIIlIIIIIlI.handler != null) {
                return lllllllllllllllllIIIllIIlIIIIIlI.handler.invoke(lllllllllllllllllIIIllIIlIIIIIII, lllllllllllllllllIIIllIIlIIIIlII, lllllllllllllllllIIIllIIlIIIIIll);
            }
            return lllllllllllllllllIIIllIIlIIIIIlI.function.invoke(lllllllllllllllllIIIllIIlIIIIlII, lllllllllllllllllIIIllIIlIIIIIlI.parameterTypes, lllllllllllllllllIIIllIIlIIIIlII.getReturnType(), lllllllllllllllllIIIllIIlIIIIIll, lllllllllllllllllIIIllIIlIIIIIlI.options);
        }

        public NativeLibrary getNativeLibrary() {
            Handler lllllllllllllllllIIIllIIlIlIIIII;
            return lllllllllllllllllIIIllIIlIlIIIII.nativeLibrary;
        }

        static {
            try {
                OBJECT_TOSTRING = Object.class.getMethod("toString", new Class[0]);
                OBJECT_HASHCODE = Object.class.getMethod("hashCode", new Class[0]);
                OBJECT_EQUALS = Object.class.getMethod("equals", Object.class);
            }
            catch (Exception lllllllllllllllllIIIllIIIlllIlII) {
                throw new Error("Error retrieving Object.toString() method");
            }
        }

        public String getLibraryName() {
            Handler lllllllllllllllllIIIllIIlIIlllII;
            return lllllllllllllllllIIIllIIlIIlllII.nativeLibrary.getName();
        }

        private static final class FunctionInfo {
            final /* synthetic */ Function function;
            final /* synthetic */ InvocationHandler handler;
            final /* synthetic */ boolean isVarArgs;
            final /* synthetic */ Class<?>[] parameterTypes;
            final /* synthetic */ Map<String, ?> options;

            FunctionInfo(InvocationHandler lllIllIIlIIlIIl, Function lllIllIIlIIlIII, Class<?>[] lllIllIIlIIIlll, boolean lllIllIIlIIIIII, Map<String, ?> lllIllIIlIIIlIl) {
                FunctionInfo lllIllIIlIIIlII;
                lllIllIIlIIIlII.handler = lllIllIIlIIlIIl;
                lllIllIIlIIIlII.function = lllIllIIlIIlIII;
                lllIllIIlIIIlII.isVarArgs = lllIllIIlIIIIII;
                lllIllIIlIIIlII.options = lllIllIIlIIIlIl;
                lllIllIIlIIIlII.parameterTypes = lllIllIIlIIIlll;
            }
        }
    }
}

