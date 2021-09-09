/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Callback;
import com.sun.jna.CallbackReference;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.FunctionParameterContext;
import com.sun.jna.FunctionResultContext;
import com.sun.jna.Memory;
import com.sun.jna.MethodParameterContext;
import com.sun.jna.MethodResultContext;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.NativeString;
import com.sun.jna.Pointer;
import com.sun.jna.StringArray;
import com.sun.jna.Structure;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeMapper;
import com.sun.jna.VarArgsChecker;
import com.sun.jna.WString;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class Function
extends Pointer {
    public static final /* synthetic */ int MAX_NARGS;
    final /* synthetic */ String encoding;
    private static final /* synthetic */ VarArgsChecker IS_VARARGS;
    private /* synthetic */ NativeLibrary library;
    public static final /* synthetic */ int USE_VARARGS;
    public static final /* synthetic */ int ALT_CONVENTION;
    static final /* synthetic */ Integer INTEGER_TRUE;
    static final /* synthetic */ Integer INTEGER_FALSE;
    private static final /* synthetic */ int MASK_CC;
    public static final /* synthetic */ int THROW_LAST_ERROR;
    public static final /* synthetic */ int C_CONVENTION;
    private final /* synthetic */ String functionName;
    final /* synthetic */ Map<String, ?> options;
    static final /* synthetic */ String OPTION_INVOKING_METHOD;
    final /* synthetic */ int callFlags;

    static {
        OPTION_INVOKING_METHOD = "invoking-method";
        USE_VARARGS = 384;
        MASK_CC = 63;
        THROW_LAST_ERROR = 64;
        C_CONVENTION = 0;
        MAX_NARGS = 256;
        ALT_CONVENTION = 63;
        INTEGER_TRUE = -1;
        INTEGER_FALSE = 0;
        IS_VARARGS = VarArgsChecker.create();
    }

    public long invokeLong(Object[] lIIlIIllllIlIII) {
        Function lIIlIIllllIIlll;
        return (Long)lIIlIIllllIIlll.invoke(Long.class, lIIlIIllllIlIII);
    }

    @Override
    public boolean equals(Object lIIlIIlllIIlllI) {
        Function lIIlIIlllIIllIl;
        if (lIIlIIlllIIlllI == lIIlIIlllIIllIl) {
            return true;
        }
        if (lIIlIIlllIIlllI == null) {
            return false;
        }
        if (lIIlIIlllIIlllI.getClass() == lIIlIIlllIIllIl.getClass()) {
            Function lIIlIIlllIlIIII = (Function)lIIlIIlllIIlllI;
            return lIIlIIlllIlIIII.callFlags == lIIlIIlllIIllIl.callFlags && lIIlIIlllIlIIII.options.equals(lIIlIIlllIIllIl.options) && lIIlIIlllIlIIII.peer == lIIlIIlllIIllIl.peer;
        }
        return false;
    }

    @Override
    public String toString() {
        Function lIIlIlIIIIIlIlI;
        if (lIIlIlIIIIIlIlI.library != null) {
            return String.valueOf(new StringBuilder().append("native function ").append(lIIlIlIIIIIlIlI.functionName).append("(").append(lIIlIlIIIIIlIlI.library.getName()).append(")@0x").append(Long.toHexString(lIIlIlIIIIIlIlI.peer)));
        }
        return String.valueOf(new StringBuilder().append("native function@0x").append(Long.toHexString(lIIlIlIIIIIlIlI.peer)));
    }

    private void checkCallingConvention(int lIIlIllIIIIIIII) throws IllegalArgumentException {
        if ((lIIlIllIIIIIIII & 0x3F) != lIIlIllIIIIIIII) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Unrecognized calling convention: ").append(lIIlIllIIIIIIII)));
        }
    }

    Object invoke(Object[] lIIlIlIIlllIIIl, Class<?> lIIlIlIIlllIIII, boolean lIIlIlIIllIllll, int lIIlIlIIlllIlIl) {
        Function lIIlIlIIllllIIl;
        Object lIIlIlIIlllIlII = null;
        int lIIlIlIIlllIIll = lIIlIlIIllllIIl.callFlags | (lIIlIlIIlllIlIl & 3) << 7;
        if (lIIlIlIIlllIIII == null || lIIlIlIIlllIIII == Void.TYPE || lIIlIlIIlllIIII == Void.class) {
            Native.invokeVoid(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
            lIIlIlIIlllIlII = null;
        } else if (lIIlIlIIlllIIII == Boolean.TYPE || lIIlIlIIlllIIII == Boolean.class) {
            lIIlIlIIlllIlII = Function.valueOf(Native.invokeInt(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl) != 0);
        } else if (lIIlIlIIlllIIII == Byte.TYPE || lIIlIlIIlllIIII == Byte.class) {
            lIIlIlIIlllIlII = (byte)Native.invokeInt(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
        } else if (lIIlIlIIlllIIII == Short.TYPE || lIIlIlIIlllIIII == Short.class) {
            lIIlIlIIlllIlII = (short)Native.invokeInt(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
        } else if (lIIlIlIIlllIIII == Character.TYPE || lIIlIlIIlllIIII == Character.class) {
            lIIlIlIIlllIlII = Character.valueOf((char)Native.invokeInt(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl));
        } else if (lIIlIlIIlllIIII == Integer.TYPE || lIIlIlIIlllIIII == Integer.class) {
            lIIlIlIIlllIlII = Native.invokeInt(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
        } else if (lIIlIlIIlllIIII == Long.TYPE || lIIlIlIIlllIIII == Long.class) {
            lIIlIlIIlllIlII = Native.invokeLong(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
        } else if (lIIlIlIIlllIIII == Float.TYPE || lIIlIlIIlllIIII == Float.class) {
            lIIlIlIIlllIlII = Float.valueOf(Native.invokeFloat(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl));
        } else if (lIIlIlIIlllIIII == Double.TYPE || lIIlIlIIlllIIII == Double.class) {
            lIIlIlIIlllIlII = Native.invokeDouble(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
        } else if (lIIlIlIIlllIIII == String.class) {
            lIIlIlIIlllIlII = lIIlIlIIllllIIl.invokeString(lIIlIlIIlllIIll, lIIlIlIIlllIIIl, false);
        } else if (lIIlIlIIlllIIII == WString.class) {
            String lIIlIlIlIIIIIlI = lIIlIlIIllllIIl.invokeString(lIIlIlIIlllIIll, lIIlIlIIlllIIIl, true);
            if (lIIlIlIlIIIIIlI != null) {
                lIIlIlIIlllIlII = new WString(lIIlIlIlIIIIIlI);
            }
        } else {
            if (Pointer.class.isAssignableFrom(lIIlIlIIlllIIII)) {
                return lIIlIlIIllllIIl.invokePointer(lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
            }
            if (Structure.class.isAssignableFrom(lIIlIlIIlllIIII)) {
                if (Structure.ByValue.class.isAssignableFrom(lIIlIlIIlllIIII)) {
                    Structure lIIlIlIlIIIIIIl = Native.invokeStructure(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl, Structure.newInstance(lIIlIlIIlllIIII));
                    lIIlIlIlIIIIIIl.autoRead();
                    lIIlIlIIlllIlII = lIIlIlIlIIIIIIl;
                } else {
                    lIIlIlIIlllIlII = lIIlIlIIllllIIl.invokePointer(lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
                    if (lIIlIlIIlllIlII != null) {
                        Structure lIIlIlIlIIIIIII = Structure.newInstance(lIIlIlIIlllIIII, (Pointer)lIIlIlIIlllIlII);
                        lIIlIlIlIIIIIII.conditionalAutoRead();
                        lIIlIlIIlllIlII = lIIlIlIlIIIIIII;
                    }
                }
            } else if (Callback.class.isAssignableFrom(lIIlIlIIlllIIII)) {
                lIIlIlIIlllIlII = lIIlIlIIllllIIl.invokePointer(lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
                if (lIIlIlIIlllIlII != null) {
                    lIIlIlIIlllIlII = CallbackReference.getCallback(lIIlIlIIlllIIII, (Pointer)lIIlIlIIlllIlII);
                }
            } else if (lIIlIlIIlllIIII == String[].class) {
                Pointer lIIlIlIIlllllll = lIIlIlIIllllIIl.invokePointer(lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
                if (lIIlIlIIlllllll != null) {
                    lIIlIlIIlllIlII = lIIlIlIIlllllll.getStringArray(0L, lIIlIlIIllllIIl.encoding);
                }
            } else if (lIIlIlIIlllIIII == WString[].class) {
                Pointer lIIlIlIIllllIll = lIIlIlIIllllIIl.invokePointer(lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
                if (lIIlIlIIllllIll != null) {
                    String[] lIIlIlIIlllllIl = lIIlIlIIllllIll.getWideStringArray(0L);
                    WString[] lIIlIlIIlllllII = new WString[lIIlIlIIlllllIl.length];
                    for (int lIIlIlIIllllllI = 0; lIIlIlIIllllllI < lIIlIlIIlllllIl.length; ++lIIlIlIIllllllI) {
                        lIIlIlIIlllllII[lIIlIlIIllllllI] = new WString(lIIlIlIIlllllIl[lIIlIlIIllllllI]);
                    }
                    lIIlIlIIlllIlII = lIIlIlIIlllllII;
                }
            } else if (lIIlIlIIlllIIII == Pointer[].class) {
                Pointer lIIlIlIIllllIlI = lIIlIlIIllllIIl.invokePointer(lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
                if (lIIlIlIIllllIlI != null) {
                    lIIlIlIIlllIlII = lIIlIlIIllllIlI.getPointerArray(0L);
                }
            } else if (lIIlIlIIllIllll) {
                lIIlIlIIlllIlII = Native.invokeObject(lIIlIlIIllllIIl, lIIlIlIIllllIIl.peer, lIIlIlIIlllIIll, lIIlIlIIlllIIIl);
                if (lIIlIlIIlllIlII != null && !lIIlIlIIlllIIII.isAssignableFrom(lIIlIlIIlllIlII.getClass())) {
                    throw new ClassCastException(String.valueOf(new StringBuilder().append("Return type ").append(lIIlIlIIlllIIII).append(" does not match result ").append(lIIlIlIIlllIlII.getClass())));
                }
            } else {
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Unsupported return type ").append(lIIlIlIIlllIIII).append(" in function ").append(lIIlIlIIllllIIl.getName())));
            }
        }
        return lIIlIlIIlllIlII;
    }

    public double invokeDouble(Object[] lIIlIIlllIlllII) {
        Function lIIlIIlllIllIll;
        return (Double)lIIlIIlllIllIll.invoke(Double.class, lIIlIIlllIlllII);
    }

    static Object[] concatenateVarArgs(Object[] lIIlIIllIllllII) {
        if (lIIlIIllIllllII != null && lIIlIIllIllllII.length > 0) {
            Class<?> lIIlIIllIlllllI;
            Object lIIlIIllIllllll = lIIlIIllIllllII[lIIlIIllIllllII.length - 1];
            Class<?> class_ = lIIlIIllIlllllI = lIIlIIllIllllll != null ? lIIlIIllIllllll.getClass() : null;
            if (lIIlIIllIlllllI != null && lIIlIIllIlllllI.isArray()) {
                Object[] lIIlIIlllIIIIIl = (Object[])lIIlIIllIllllll;
                for (int lIIlIIlllIIIIlI = 0; lIIlIIlllIIIIlI < lIIlIIlllIIIIIl.length; ++lIIlIIlllIIIIlI) {
                    if (!(lIIlIIlllIIIIIl[lIIlIIlllIIIIlI] instanceof Float)) continue;
                    lIIlIIlllIIIIIl[lIIlIIlllIIIIlI] = (double)((Float)lIIlIIlllIIIIIl[lIIlIIlllIIIIlI]).floatValue();
                }
                Object[] lIIlIIlllIIIIII = new Object[lIIlIIllIllllII.length + lIIlIIlllIIIIIl.length];
                System.arraycopy(lIIlIIllIllllII, 0, lIIlIIlllIIIIII, 0, lIIlIIllIllllII.length - 1);
                System.arraycopy(lIIlIIlllIIIIIl, 0, lIIlIIlllIIIIII, lIIlIIllIllllII.length - 1, lIIlIIlllIIIIIl.length);
                lIIlIIlllIIIIII[lIIlIIlllIIIIII.length - 1] = null;
                lIIlIIllIllllII = lIIlIIlllIIIIII;
            }
        }
        return lIIlIIllIllllII;
    }

    public String invokeString(Object[] lIIlIIlllllIlII, boolean lIIlIIlllllIIll) {
        Function lIIlIIllllllIIl;
        Object lIIlIIlllllIllI = lIIlIIllllllIIl.invoke(lIIlIIlllllIIll ? WString.class : String.class, lIIlIIlllllIlII);
        return lIIlIIlllllIllI != null ? lIIlIIlllllIllI.toString() : null;
    }

    public Object invoke(Class<?> lIIlIlIlllIIIlI, Object[] lIIlIlIlllIIIIl, Map<String, ?> lIIlIlIlllIIllI) {
        Function lIIlIlIlllIIIll;
        Method lIIlIlIlllIIlIl = (Method)lIIlIlIlllIIllI.get("invoking-method");
        Class<?>[] lIIlIlIlllIIlII = lIIlIlIlllIIlIl != null ? lIIlIlIlllIIlIl.getParameterTypes() : null;
        return lIIlIlIlllIIIll.invoke(lIIlIlIlllIIlIl, lIIlIlIlllIIlII, lIIlIlIlllIIIlI, lIIlIlIlllIIIIl, lIIlIlIlllIIllI);
    }

    public void invokeVoid(Object[] lIIlIIlllIlIllI) {
        Function lIIlIIlllIlIlIl;
        lIIlIIlllIlIlIl.invoke(Void.class, lIIlIIlllIlIllI);
    }

    Object invoke(Object[] lIIlIlIlIIlIIII, Class<?> lIIlIlIlIIlIIll, boolean lIIlIlIlIIlIIlI) {
        Function lIIlIlIlIIlIlIl;
        return lIIlIlIlIIlIlIl.invoke(lIIlIlIlIIlIIII, lIIlIlIlIIlIIll, lIIlIlIlIIlIIlI, 0);
    }

    private String invokeString(int lIIlIlIIIIlIlll, Object[] lIIlIlIIIIlIllI, boolean lIIlIlIIIIIllll) {
        Function lIIlIlIIIIlIIlI;
        Pointer lIIlIlIIIIlIlII = lIIlIlIIIIlIIlI.invokePointer(lIIlIlIIIIlIlll, lIIlIlIIIIlIllI);
        String lIIlIlIIIIlIIll = null;
        if (lIIlIlIIIIlIlII != null) {
            lIIlIlIIIIlIIll = lIIlIlIIIIIllll ? lIIlIlIIIIlIlII.getWideString(0L) : lIIlIlIIIIlIlII.getString(0L, lIIlIlIIIIlIIlI.encoding);
        }
        return lIIlIlIIIIlIIll;
    }

    public String getName() {
        Function lIIlIlIllllllII;
        return lIIlIlIllllllII.functionName;
    }

    public float invokeFloat(Object[] lIIlIIllllIIIII) {
        Function lIIlIIllllIIIll;
        return ((Float)lIIlIIllllIIIll.invoke(Float.class, lIIlIIllllIIIII)).floatValue();
    }

    static int fixedArgs(Method lIIlIIllIllIIlI) {
        return IS_VARARGS.fixedArgs(lIIlIIllIllIIlI);
    }

    public static Function getFunction(Pointer lIIlIllIIllIIII) {
        return Function.getFunction(lIIlIllIIllIIII, 0, null);
    }

    public static Function getFunction(String lIIlIllIlIIlIll, String lIIlIllIlIIlIlI) {
        return NativeLibrary.getInstance(lIIlIllIlIIlIll).getFunction(lIIlIllIlIIlIlI);
    }

    public static Function getFunction(Pointer lIIlIllIIlIIIll, int lIIlIllIIlIIIlI, String lIIlIllIIlIIIIl) {
        return new Function(lIIlIllIIlIIIll, lIIlIllIIlIIIlI, lIIlIllIIlIIIIl);
    }

    public Object invoke(Class<?> lIIlIlIllllIIIl, Object[] lIIlIlIllllIIll) {
        Function lIIlIlIllllIIlI;
        return lIIlIlIllllIIlI.invoke(lIIlIlIllllIIIl, lIIlIlIllllIIll, lIIlIlIllllIIlI.options);
    }

    public int getCallingConvention() {
        Function lIIlIlIlllllIlI;
        return lIIlIlIlllllIlI.callFlags & 0x3F;
    }

    @Override
    public int hashCode() {
        Function lIIlIIlllIIlIIl;
        return lIIlIIlllIIlIIl.callFlags + lIIlIIlllIIlIIl.options.hashCode() + super.hashCode();
    }

    public Pointer invokePointer(Object[] lIIlIlIIIIIIIII) {
        Function lIIlIlIIIIIIIIl;
        return (Pointer)lIIlIlIIIIIIIIl.invoke(Pointer.class, lIIlIlIIIIIIIII);
    }

    public void invoke(Object[] lIIlIlIIIIlllll) {
        Function lIIlIlIIIlIIIII;
        lIIlIlIIIlIIIII.invoke(Void.class, lIIlIlIIIIlllll);
    }

    static Boolean valueOf(boolean lIIlIIllIlIllll) {
        return lIIlIIllIlIllll ? Boolean.TRUE : Boolean.FALSE;
    }

    static boolean isVarArgs(Method lIIlIIllIllIllI) {
        return IS_VARARGS.isVarArgs(lIIlIIllIllIllI);
    }

    Object invoke(Method lIIlIlIlIlIllIl, Class<?>[] lIIlIlIlIlllIlI, Class<?> lIIlIlIlIlllIIl, Object[] lIIlIlIlIlIlIlI, Map<String, ?> lIIlIlIlIlIlIIl) {
        Function lIIlIlIlIllllII;
        Object[] lIIlIlIlIllIllI = new Object[]{};
        if (lIIlIlIlIlIlIlI != null) {
            if (lIIlIlIlIlIlIlI.length > 256) {
                throw new UnsupportedOperationException("Maximum argument count is 256");
            }
            lIIlIlIlIllIllI = new Object[lIIlIlIlIlIlIlI.length];
            System.arraycopy(lIIlIlIlIlIlIlI, 0, lIIlIlIlIllIllI, 0, lIIlIlIlIllIllI.length);
        }
        TypeMapper lIIlIlIlIllIlIl = (TypeMapper)lIIlIlIlIlIlIIl.get("type-mapper");
        boolean lIIlIlIlIllIlII = Boolean.TRUE.equals(lIIlIlIlIlIlIIl.get("allow-objects"));
        boolean lIIlIlIlIllIIll = lIIlIlIlIllIllI.length > 0 && lIIlIlIlIlIllIl != null ? Function.isVarArgs(lIIlIlIlIlIllIl) : false;
        int lIIlIlIlIllIIlI = lIIlIlIlIllIllI.length > 0 && lIIlIlIlIlIllIl != null ? Function.fixedArgs(lIIlIlIlIlIllIl) : 0;
        for (int lIIlIlIllIIIlll = 0; lIIlIlIllIIIlll < lIIlIlIlIllIllI.length; ++lIIlIlIllIIIlll) {
            Class<?> lIIlIlIllIIlIII = lIIlIlIlIlIllIl != null ? (lIIlIlIlIllIIll && lIIlIlIllIIIlll >= lIIlIlIlIlllIlI.length - 1 ? lIIlIlIlIlllIlI[lIIlIlIlIlllIlI.length - 1].getComponentType() : lIIlIlIlIlllIlI[lIIlIlIllIIIlll]) : null;
            lIIlIlIlIllIllI[lIIlIlIllIIIlll] = lIIlIlIlIllllII.convertArgument(lIIlIlIlIllIllI, lIIlIlIllIIIlll, lIIlIlIlIlIllIl, lIIlIlIlIllIlIl, lIIlIlIlIllIlII, lIIlIlIllIIlIII);
        }
        Class<?> lIIlIlIlIllIIIl = lIIlIlIlIlllIIl;
        FromNativeConverter lIIlIlIlIllIIII = null;
        if (NativeMapped.class.isAssignableFrom(lIIlIlIlIlllIIl)) {
            NativeMappedConverter lIIlIlIllIIIllI = NativeMappedConverter.getInstance(lIIlIlIlIlllIIl);
            lIIlIlIlIllIIII = lIIlIlIllIIIllI;
            lIIlIlIlIllIIIl = lIIlIlIllIIIllI.nativeType();
        } else if (lIIlIlIlIllIlIl != null && (lIIlIlIlIllIIII = lIIlIlIlIllIlIl.getFromNativeConverter(lIIlIlIlIlllIIl)) != null) {
            lIIlIlIlIllIIIl = lIIlIlIlIllIIII.nativeType();
        }
        Object lIIlIlIlIlIllll = lIIlIlIlIllllII.invoke(lIIlIlIlIllIllI, lIIlIlIlIllIIIl, lIIlIlIlIllIlII, lIIlIlIlIllIIlI);
        if (lIIlIlIlIllIIII != null) {
            FunctionResultContext lIIlIlIllIIIlII;
            if (lIIlIlIlIlIllIl != null) {
                MethodResultContext lIIlIlIllIIIlIl = new MethodResultContext(lIIlIlIlIlllIIl, lIIlIlIlIllllII, lIIlIlIlIlIlIlI, lIIlIlIlIlIllIl);
            } else {
                lIIlIlIllIIIlII = new FunctionResultContext(lIIlIlIlIlllIIl, lIIlIlIlIllllII, lIIlIlIlIlIlIlI);
            }
            lIIlIlIlIlIllll = lIIlIlIlIllIIII.fromNative(lIIlIlIlIlIllll, lIIlIlIllIIIlII);
        }
        if (lIIlIlIlIlIlIlI != null) {
            for (int lIIlIlIlIllllIl = 0; lIIlIlIlIllllIl < lIIlIlIlIlIlIlI.length; ++lIIlIlIlIllllIl) {
                Object lIIlIlIlIlllllI = lIIlIlIlIlIlIlI[lIIlIlIlIllllIl];
                if (lIIlIlIlIlllllI == null) continue;
                if (lIIlIlIlIlllllI instanceof Structure) {
                    if (lIIlIlIlIlllllI instanceof Structure.ByValue) continue;
                    ((Structure)lIIlIlIlIlllllI).autoRead();
                    continue;
                }
                if (lIIlIlIlIllIllI[lIIlIlIlIllllIl] instanceof PostCallRead) {
                    ((PostCallRead)lIIlIlIlIllIllI[lIIlIlIlIllllIl]).read();
                    if (!(lIIlIlIlIllIllI[lIIlIlIlIllllIl] instanceof PointerArray)) continue;
                    PointerArray lIIlIlIlIllllll = (PointerArray)lIIlIlIlIllIllI[lIIlIlIlIllllIl];
                    if (!Structure.ByReference[].class.isAssignableFrom(lIIlIlIlIlllllI.getClass())) continue;
                    Class<?> lIIlIlIllIIIIIl = lIIlIlIlIlllllI.getClass().getComponentType();
                    Structure[] lIIlIlIllIIIIII = (Structure[])lIIlIlIlIlllllI;
                    for (int lIIlIlIllIIIIlI = 0; lIIlIlIllIIIIlI < lIIlIlIllIIIIII.length; ++lIIlIlIllIIIIlI) {
                        Pointer lIIlIlIllIIIIll = lIIlIlIlIllllll.getPointer(Pointer.SIZE * lIIlIlIllIIIIlI);
                        lIIlIlIllIIIIII[lIIlIlIllIIIIlI] = Structure.updateStructureByReference(lIIlIlIllIIIIIl, lIIlIlIllIIIIII[lIIlIlIllIIIIlI], lIIlIlIllIIIIll);
                    }
                    continue;
                }
                if (!Structure[].class.isAssignableFrom(lIIlIlIlIlllllI.getClass())) continue;
                Structure.autoRead((Structure[])lIIlIlIlIlllllI);
            }
        }
        return lIIlIlIlIlIllll;
    }

    private boolean isPrimitiveArray(Class<?> lIIlIlIIIlIIlIl) {
        return lIIlIlIIIlIIlIl.isArray() && lIIlIlIIIlIIlIl.getComponentType().isPrimitive();
    }

    private Pointer invokePointer(int lIIlIlIIllIIIlI, Object[] lIIlIlIIllIIIIl) {
        Function lIIlIlIIllIIIll;
        long lIIlIlIIllIIIII = Native.invokePointer(lIIlIlIIllIIIll, lIIlIlIIllIIIll.peer, lIIlIlIIllIIIlI, lIIlIlIIllIIIIl);
        return lIIlIlIIllIIIII == 0L ? null : new Pointer(lIIlIlIIllIIIII);
    }

    public Object invokeObject(Object[] lIIlIlIIIIIIllI) {
        Function lIIlIlIIIIIIlll;
        return lIIlIlIIIIIIlll.invoke(Object.class, lIIlIlIIIIIIllI);
    }

    private Object convertArgument(Object[] lIIlIlIIIllIlIl, int lIIlIlIIIllllIl, Method lIIlIlIIIllIIll, TypeMapper lIIlIlIIIlllIll, boolean lIIlIlIIIlllIlI, Class<?> lIIlIlIIIllIIII) {
        Function lIIlIlIIIllllll;
        Object lIIlIlIIIlllIII = lIIlIlIIIllIlIl[lIIlIlIIIllllIl];
        if (lIIlIlIIIlllIII != null) {
            Class<?> lIIlIlIIlIIlIll = lIIlIlIIIlllIII.getClass();
            ToNativeConverter lIIlIlIIlIIlIlI = null;
            if (NativeMapped.class.isAssignableFrom(lIIlIlIIlIIlIll)) {
                lIIlIlIIlIIlIlI = NativeMappedConverter.getInstance(lIIlIlIIlIIlIll);
            } else if (lIIlIlIIIlllIll != null) {
                lIIlIlIIlIIlIlI = lIIlIlIIIlllIll.getToNativeConverter(lIIlIlIIlIIlIll);
            }
            if (lIIlIlIIlIIlIlI != null) {
                FunctionParameterContext lIIlIlIIlIIllII;
                if (lIIlIlIIIllIIll != null) {
                    MethodParameterContext lIIlIlIIlIIllIl = new MethodParameterContext(lIIlIlIIIllllll, lIIlIlIIIllIlIl, lIIlIlIIIllllIl, lIIlIlIIIllIIll);
                } else {
                    lIIlIlIIlIIllII = new FunctionParameterContext(lIIlIlIIIllllll, lIIlIlIIIllIlIl, lIIlIlIIIllllIl);
                }
                lIIlIlIIIlllIII = lIIlIlIIlIIlIlI.toNative(lIIlIlIIIlllIII, lIIlIlIIlIIllII);
            }
        }
        if (lIIlIlIIIlllIII == null || lIIlIlIIIllllll.isPrimitiveArray(lIIlIlIIIlllIII.getClass())) {
            return lIIlIlIIIlllIII;
        }
        Class<?> lIIlIlIIIllIlll = lIIlIlIIIlllIII.getClass();
        if (lIIlIlIIIlllIII instanceof Structure) {
            Structure lIIlIlIIlIIIllI = (Structure)lIIlIlIIIlllIII;
            lIIlIlIIlIIIllI.autoWrite();
            if (lIIlIlIIlIIIllI instanceof Structure.ByValue) {
                Class<?> lIIlIlIIlIIIlll = lIIlIlIIlIIIllI.getClass();
                if (lIIlIlIIIllIIll != null) {
                    Class<?>[] lIIlIlIIlIIlIII = lIIlIlIIIllIIll.getParameterTypes();
                    if (IS_VARARGS.isVarArgs(lIIlIlIIIllIIll)) {
                        if (lIIlIlIIIllllIl < lIIlIlIIlIIlIII.length - 1) {
                            lIIlIlIIlIIIlll = lIIlIlIIlIIlIII[lIIlIlIIIllllIl];
                        } else {
                            Class<?> lIIlIlIIlIIlIIl = lIIlIlIIlIIlIII[lIIlIlIIlIIlIII.length - 1].getComponentType();
                            if (lIIlIlIIlIIlIIl != Object.class) {
                                lIIlIlIIlIIIlll = lIIlIlIIlIIlIIl;
                            }
                        }
                    } else {
                        lIIlIlIIlIIIlll = lIIlIlIIlIIlIII[lIIlIlIIIllllIl];
                    }
                }
                if (Structure.ByValue.class.isAssignableFrom(lIIlIlIIlIIIlll)) {
                    return lIIlIlIIlIIIllI;
                }
            }
            return lIIlIlIIlIIIllI.getPointer();
        }
        if (lIIlIlIIIlllIII instanceof Callback) {
            return CallbackReference.getFunctionPointer((Callback)lIIlIlIIIlllIII);
        }
        if (lIIlIlIIIlllIII instanceof String) {
            return new NativeString((String)lIIlIlIIIlllIII, false).getPointer();
        }
        if (lIIlIlIIIlllIII instanceof WString) {
            return new NativeString(lIIlIlIIIlllIII.toString(), true).getPointer();
        }
        if (lIIlIlIIIlllIII instanceof Boolean) {
            return Boolean.TRUE.equals(lIIlIlIIIlllIII) ? INTEGER_TRUE : INTEGER_FALSE;
        }
        if (String[].class == lIIlIlIIIllIlll) {
            return new StringArray((String[])lIIlIlIIIlllIII, lIIlIlIIIllllll.encoding);
        }
        if (WString[].class == lIIlIlIIIllIlll) {
            return new StringArray((WString[])lIIlIlIIIlllIII);
        }
        if (Pointer[].class == lIIlIlIIIllIlll) {
            return new PointerArray((Pointer[])lIIlIlIIIlllIII);
        }
        if (NativeMapped[].class.isAssignableFrom(lIIlIlIIIllIlll)) {
            return new NativeMappedArray((NativeMapped[])lIIlIlIIIlllIII);
        }
        if (Structure[].class.isAssignableFrom(lIIlIlIIIllIlll)) {
            Structure[] lIIlIlIIlIIIIlI = (Structure[])lIIlIlIIIlllIII;
            Class<?> lIIlIlIIlIIIIIl = lIIlIlIIIllIlll.getComponentType();
            boolean lIIlIlIIlIIIIII = Structure.ByReference.class.isAssignableFrom(lIIlIlIIlIIIIIl);
            if (lIIlIlIIIllIIII != null && !Structure.ByReference[].class.isAssignableFrom(lIIlIlIIIllIIII)) {
                if (lIIlIlIIlIIIIII) {
                    throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Function ").append(lIIlIlIIIllllll.getName()).append(" declared Structure[] at parameter ").append(lIIlIlIIIllllIl).append(" but array of ").append(lIIlIlIIlIIIIIl).append(" was passed")));
                }
                for (int lIIlIlIIlIIIlIl = 0; lIIlIlIIlIIIlIl < lIIlIlIIlIIIIlI.length; ++lIIlIlIIlIIIlIl) {
                    if (!(lIIlIlIIlIIIIlI[lIIlIlIIlIIIlIl] instanceof Structure.ByReference)) continue;
                    throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Function ").append(lIIlIlIIIllllll.getName()).append(" declared Structure[] at parameter ").append(lIIlIlIIIllllIl).append(" but element ").append(lIIlIlIIlIIIlIl).append(" is of Structure.ByReference type")));
                }
            }
            if (lIIlIlIIlIIIIII) {
                Structure.autoWrite(lIIlIlIIlIIIIlI);
                Pointer[] lIIlIlIIlIIIIll = new Pointer[lIIlIlIIlIIIIlI.length + 1];
                for (int lIIlIlIIlIIIlII = 0; lIIlIlIIlIIIlII < lIIlIlIIlIIIIlI.length; ++lIIlIlIIlIIIlII) {
                    lIIlIlIIlIIIIll[lIIlIlIIlIIIlII] = lIIlIlIIlIIIIlI[lIIlIlIIlIIIlII] != null ? lIIlIlIIlIIIIlI[lIIlIlIIlIIIlII].getPointer() : null;
                }
                return new PointerArray(lIIlIlIIlIIIIll);
            }
            if (lIIlIlIIlIIIIlI.length == 0) {
                throw new IllegalArgumentException("Structure array must have non-zero length");
            }
            if (lIIlIlIIlIIIIlI[0] == null) {
                Structure.newInstance(lIIlIlIIlIIIIIl).toArray(lIIlIlIIlIIIIlI);
                return lIIlIlIIlIIIIlI[0].getPointer();
            }
            Structure.autoWrite(lIIlIlIIlIIIIlI);
            return lIIlIlIIlIIIIlI[0].getPointer();
        }
        if (lIIlIlIIIllIlll.isArray()) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Unsupported array argument type: ").append(lIIlIlIIIllIlll.getComponentType())));
        }
        if (lIIlIlIIIlllIlI) {
            return lIIlIlIIIlllIII;
        }
        if (!Native.isSupportedNativeType(lIIlIlIIIlllIII.getClass())) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Unsupported argument type ").append(lIIlIlIIIlllIII.getClass().getName()).append(" at parameter ").append(lIIlIlIIIllllIl).append(" of function ").append(lIIlIlIIIllllll.getName())));
        }
        return lIIlIlIIIlllIII;
    }

    public static Function getFunction(Pointer lIIlIllIIlIllIl, int lIIlIllIIlIlIlI) {
        return Function.getFunction(lIIlIllIIlIllIl, lIIlIllIIlIlIlI, null);
    }

    public static Function getFunction(String lIIlIllIIllIllI, String lIIlIllIIllIlIl, int lIIlIllIIllIlII, String lIIlIllIIllIlll) {
        return NativeLibrary.getInstance(lIIlIllIIllIllI).getFunction(lIIlIllIIllIlIl, lIIlIllIIllIlII, lIIlIllIIllIlll);
    }

    public int invokeInt(Object[] lIIlIIllllIlllI) {
        Function lIIlIIllllIllll;
        return (Integer)lIIlIIllllIllll.invoke(Integer.class, lIIlIIllllIlllI);
    }

    public static Function getFunction(String lIIlIllIlIIIlII, String lIIlIllIlIIIIll, int lIIlIllIIllllll) {
        return NativeLibrary.getInstance(lIIlIllIlIIIlII).getFunction(lIIlIllIlIIIIll, lIIlIllIIllllll, null);
    }

    Function(Pointer lIIlIllIIIIlIIl, int lIIlIllIIIIlIII, String lIIlIllIIIIIlll) {
        Function lIIlIllIIIIIllI;
        lIIlIllIIIIIllI.checkCallingConvention(lIIlIllIIIIlIII & 0x3F);
        if (lIIlIllIIIIlIIl == null || lIIlIllIIIIlIIl.peer == 0L) {
            throw new NullPointerException("Function address may not be null");
        }
        lIIlIllIIIIIllI.functionName = lIIlIllIIIIlIIl.toString();
        lIIlIllIIIIIllI.callFlags = lIIlIllIIIIlIII;
        lIIlIllIIIIIllI.peer = lIIlIllIIIIlIIl.peer;
        lIIlIllIIIIIllI.options = Collections.EMPTY_MAP;
        lIIlIllIIIIIllI.encoding = lIIlIllIIIIIlll != null ? lIIlIllIIIIIlll : Native.getDefaultStringEncoding();
    }

    Function(NativeLibrary lIIlIllIIIlIIll, String lIIlIllIIIlIIlI, int lIIlIllIIIlIIIl, String lIIlIllIIIlIIII) {
        Function lIIlIllIIIllIIl;
        lIIlIllIIIllIIl.checkCallingConvention(lIIlIllIIIlIIIl & 0x3F);
        if (lIIlIllIIIlIIlI == null) {
            throw new NullPointerException("Function name must not be null");
        }
        lIIlIllIIIllIIl.library = lIIlIllIIIlIIll;
        lIIlIllIIIllIIl.functionName = lIIlIllIIIlIIlI;
        lIIlIllIIIllIIl.callFlags = lIIlIllIIIlIIIl;
        lIIlIllIIIllIIl.options = lIIlIllIIIlIIll.options;
        lIIlIllIIIllIIl.encoding = lIIlIllIIIlIIII != null ? lIIlIllIIIlIIII : Native.getDefaultStringEncoding();
        try {
            lIIlIllIIIllIIl.peer = lIIlIllIIIlIIll.getSymbolAddress(lIIlIllIIIlIIlI);
        }
        catch (UnsatisfiedLinkError lIIlIllIIIllIlI) {
            throw new UnsatisfiedLinkError(String.valueOf(new StringBuilder().append("Error looking up function '").append(lIIlIllIIIlIIlI).append("': ").append(lIIlIllIIIllIlI.getMessage())));
        }
    }

    private static class PointerArray
    extends Memory
    implements PostCallRead {
        private final /* synthetic */ Pointer[] original;

        public PointerArray(Pointer[] lllllllllllllllllIIIIlIllIlIIlII) {
            super(Pointer.SIZE * (lllllllllllllllllIIIIlIllIlIIlII.length + 1));
            PointerArray lllllllllllllllllIIIIlIllIlIIlIl;
            lllllllllllllllllIIIIlIllIlIIlIl.original = lllllllllllllllllIIIIlIllIlIIlII;
            for (int lllllllllllllllllIIIIlIllIlIlIII = 0; lllllllllllllllllIIIIlIllIlIlIII < lllllllllllllllllIIIIlIllIlIIlII.length; ++lllllllllllllllllIIIIlIllIlIlIII) {
                lllllllllllllllllIIIIlIllIlIIlIl.setPointer(lllllllllllllllllIIIIlIllIlIlIII * Pointer.SIZE, lllllllllllllllllIIIIlIllIlIIlII[lllllllllllllllllIIIIlIllIlIlIII]);
            }
            lllllllllllllllllIIIIlIllIlIIlIl.setPointer(Pointer.SIZE * lllllllllllllllllIIIIlIllIlIIlII.length, null);
        }

        @Override
        public void read() {
            PointerArray lllllllllllllllllIIIIlIllIlIIIIl;
            lllllllllllllllllIIIIlIllIlIIIIl.read(0L, lllllllllllllllllIIIIlIllIlIIIIl.original, 0, lllllllllllllllllIIIIlIllIlIIIIl.original.length);
        }
    }

    public static interface PostCallRead {
        public void read();
    }

    private static class NativeMappedArray
    extends Memory
    implements PostCallRead {
        private final /* synthetic */ NativeMapped[] original;

        @Override
        public void read() {
            NativeMappedArray lllllllllllllllllIIllllIlIlIlllI;
            lllllllllllllllllIIllllIlIlIlllI.getValue(0L, lllllllllllllllllIIllllIlIlIlllI.original.getClass(), lllllllllllllllllIIllllIlIlIlllI.original);
        }

        public NativeMappedArray(NativeMapped[] lllllllllllllllllIIllllIlIllIIIl) {
            super(Native.getNativeSize(lllllllllllllllllIIllllIlIllIIIl.getClass(), lllllllllllllllllIIllllIlIllIIIl));
            NativeMappedArray lllllllllllllllllIIllllIlIllIlII;
            lllllllllllllllllIIllllIlIllIlII.original = lllllllllllllllllIIllllIlIllIIIl;
            lllllllllllllllllIIllllIlIllIlII.setValue(0L, lllllllllllllllllIIllllIlIllIlII.original, lllllllllllllllllIIllllIlIllIlII.original.getClass());
        }
    }
}

