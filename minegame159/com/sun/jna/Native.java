/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Callback;
import com.sun.jna.CallbackReference;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.FromNativeContext;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.Function;
import com.sun.jna.IntegerType;
import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.MethodResultContext;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.ToNativeContext;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeMapper;
import com.sun.jna.Version;
import com.sun.jna.WString;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;

public final class Native
implements Version {
    private static /* synthetic */ Callback.UncaughtExceptionHandler callbackExceptionHandler;
    public static final /* synthetic */ int SIZE_T_SIZE;
    public static /* synthetic */ boolean DEBUG_LOAD;
    private static final /* synthetic */ int CVT_NATIVE_MAPPED;
    private static final /* synthetic */ int CVT_FLOAT;
    private static final /* synthetic */ int CVT_ARRAY_INT;
    private static final /* synthetic */ Map<Class<?>, Reference<?>> libraries;
    static final /* synthetic */ int CB_OPTION_DIRECT;
    private static final /* synthetic */ int CVT_NATIVE_MAPPED_STRING;
    private static final /* synthetic */ int TYPE_VOIDP;
    private static final /* synthetic */ int CVT_STRUCTURE_BYVAL;
    static final /* synthetic */ String JNA_TMPLIB_PREFIX;
    private static final /* synthetic */ Map<Thread, Pointer> nativeThreads;
    private static final /* synthetic */ int CVT_ARRAY_BYTE;
    private static final /* synthetic */ int CVT_STRING;
    static final /* synthetic */ int CB_OPTION_IN_DLL;
    public static final /* synthetic */ int POINTER_SIZE;
    private static final /* synthetic */ Map<Class<?>, Map<String, Object>> typeOptions;
    private static final /* synthetic */ int CVT_NATIVE_MAPPED_WSTRING;
    private static final /* synthetic */ String _OPTION_ENCLOSING_LIBRARY;
    private static final /* synthetic */ int CVT_ARRAY_BOOLEAN;
    private static final /* synthetic */ int CVT_BOOLEAN;
    private static final /* synthetic */ int CVT_DEFAULT;
    static final /* synthetic */ int MAX_PADDING;
    private static /* synthetic */ Map<Class<?>, long[]> registeredClasses;
    static /* synthetic */ String jnidispatchPath;
    private static final /* synthetic */ int CVT_WSTRING;
    private static final /* synthetic */ int CVT_ARRAY_SHORT;
    public static final /* synthetic */ int BOOL_SIZE;
    private static final /* synthetic */ int CVT_TYPE_MAPPER_WSTRING;
    private static final /* synthetic */ Callback.UncaughtExceptionHandler DEFAULT_HANDLER;
    private static final /* synthetic */ int TYPE_LONG;
    static final /* synthetic */ int CB_HAS_INITIALIZER;
    private static final /* synthetic */ Object finalizer;
    private static final /* synthetic */ int CVT_STRUCTURE;
    private static final /* synthetic */ int CVT_ARRAY_FLOAT;
    private static final /* synthetic */ int CVT_TYPE_MAPPER_STRING;
    public static final /* synthetic */ int WCHAR_SIZE;
    private static final /* synthetic */ int CVT_UNSUPPORTED;
    private static final /* synthetic */ int CVT_ARRAY_DOUBLE;
    private static final /* synthetic */ int CVT_ARRAY_LONG;
    private static final /* synthetic */ int CVT_TYPE_MAPPER;
    public static /* synthetic */ boolean DEBUG_JNA_LOAD;
    private static final /* synthetic */ int CVT_BUFFER;
    private static final /* synthetic */ int TYPE_WCHAR_T;
    public static final /* synthetic */ String DEFAULT_ENCODING;
    private static final /* synthetic */ int CVT_CALLBACK;
    private static final /* synthetic */ ThreadLocal<Memory> nativeThreadTerminationFlag;
    private static final /* synthetic */ int TYPE_SIZE_T;
    private static final /* synthetic */ int TYPE_BOOL;
    private static final /* synthetic */ int CVT_POINTER;
    private static /* synthetic */ Map<Class<?>, NativeLibrary> registeredLibraries;
    private static final /* synthetic */ int CVT_POINTER_TYPE;
    private static final /* synthetic */ int CVT_ARRAY_CHAR;
    static final /* synthetic */ int MAX_ALIGNMENT;
    public static final /* synthetic */ int LONG_SIZE;
    private static final /* synthetic */ int CVT_INTEGER_TYPE;

    public static File extractFromResourcePath(String llllllllllllllllIllIIlIlllIlllIl) throws IOException {
        return Native.extractFromResourcePath(llllllllllllllllIllIIlIlllIlllIl, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void loadLibraryInstance(Class<?> llllllllllllllllIllIIllIIlllIlll) {
        Map<Class<?>, Reference<?>> llllllllllllllllIllIIllIIlllIlIl = libraries;
        synchronized (llllllllllllllllIllIIllIIlllIlIl) {
            if (llllllllllllllllIllIIllIIlllIlll != null && !libraries.containsKey(llllllllllllllllIllIIllIIlllIlll)) {
                try {
                    Field[] llllllllllllllllIllIIllIIllllIIl = llllllllllllllllIllIIllIIlllIlll.getFields();
                    for (int llllllllllllllllIllIIllIIllllIlI = 0; llllllllllllllllIllIIllIIllllIlI < llllllllllllllllIllIIllIIllllIIl.length; ++llllllllllllllllIllIIllIIllllIlI) {
                        Field llllllllllllllllIllIIllIIllllIll = llllllllllllllllIllIIllIIllllIIl[llllllllllllllllIllIIllIIllllIlI];
                        if (llllllllllllllllIllIIllIIllllIll.getType() != llllllllllllllllIllIIllIIlllIlll || !Modifier.isStatic(llllllllllllllllIllIIllIIllllIll.getModifiers())) continue;
                        libraries.put(llllllllllllllllIllIIllIIlllIlll, new WeakReference<Object>(llllllllllllllllIllIIllIIllllIll.get(null)));
                        break;
                    }
                }
                catch (Exception llllllllllllllllIllIIllIIllllIII) {
                    throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Could not access instance of ").append(llllllllllllllllIllIIllIIlllIlll).append(" (").append(llllllllllllllllIllIIllIIllllIII).append(")")));
                }
            }
        }
    }

    static String getSignature(Class<?> llllllllllllllllIllIIlIlIIIllIll) {
        if (llllllllllllllllIllIIlIlIIIllIll.isArray()) {
            return String.valueOf(new StringBuilder().append("[").append(Native.getSignature(llllllllllllllllIllIIlIlIIIllIll.getComponentType())));
        }
        if (llllllllllllllllIllIIlIlIIIllIll.isPrimitive()) {
            if (llllllllllllllllIllIIlIlIIIllIll == Void.TYPE) {
                return "V";
            }
            if (llllllllllllllllIllIIlIlIIIllIll == Boolean.TYPE) {
                return "Z";
            }
            if (llllllllllllllllIllIIlIlIIIllIll == Byte.TYPE) {
                return "B";
            }
            if (llllllllllllllllIllIIlIlIIIllIll == Short.TYPE) {
                return "S";
            }
            if (llllllllllllllllIllIIlIlIIIllIll == Character.TYPE) {
                return "C";
            }
            if (llllllllllllllllIllIIlIlIIIllIll == Integer.TYPE) {
                return "I";
            }
            if (llllllllllllllllIllIIlIlIIIllIll == Long.TYPE) {
                return "J";
            }
            if (llllllllllllllllIllIIlIlIIIllIll == Float.TYPE) {
                return "F";
            }
            if (llllllllllllllllIllIIlIlIIIllIll == Double.TYPE) {
                return "D";
            }
        }
        return String.valueOf(new StringBuilder().append("L").append(Native.replace(".", "/", llllllllllllllllIllIIlIlIIIllIll.getName())).append(";"));
    }

    static /* bridge */ native /* synthetic */ void setWideString(Pointer var0, long var1, long var3, String var5);

    static /* bridge */ native /* synthetic */ void invokeVoid(Function var0, long var1, int var3, Object[] var4);

    static /* bridge */ native /* synthetic */ float invokeFloat(Function var0, long var1, int var3, Object[] var4);

    static /* bridge */ native /* synthetic */ void read(Pointer var0, long var1, long var3, char[] var5, int var6, int var7);

    public static Pointer getDirectBufferPointer(Buffer llllllllllllllllIllIIllIllIllIll) {
        long llllllllllllllllIllIIllIllIllIlI = Native._getDirectBufferPointer(llllllllllllllllIllIIllIllIllIll);
        return llllllllllllllllIllIIllIllIllIlI == 0L ? null : new Pointer(llllllllllllllllIllIIllIllIllIlI);
    }

    static /* bridge */ native /* synthetic */ double getDouble(Pointer var0, long var1, long var3);

    public static void register(Class<?> llllllllllllllllIllIIlIIllllIIll, String llllllllllllllllIllIIlIIllllIlIl) {
        NativeLibrary llllllllllllllllIllIIlIIllllIlII = NativeLibrary.getInstance(llllllllllllllllIllIIlIIllllIlIl, Collections.singletonMap("classloader", llllllllllllllllIllIIlIIllllIIll.getClassLoader()));
        Native.register(llllllllllllllllIllIIlIIllllIIll, llllllllllllllllIllIIlIIllllIlII);
    }

    static Class<?> findDirectMappedClass(Class<?> llllllllllllllllIllIIlIlIlIIlIII) {
        Method[] llllllllllllllllIllIIlIlIlIIIlll;
        for (Method llllllllllllllllIllIIlIlIlIIlIlI : llllllllllllllllIllIIlIlIlIIIlll = llllllllllllllllIllIIlIlIlIIlIII.getDeclaredMethods()) {
            if ((llllllllllllllllIllIIlIlIlIIlIlI.getModifiers() & 0x100) == 0) continue;
            return llllllllllllllllIllIIlIlIlIIlIII;
        }
        int llllllllllllllllIllIIlIlIlIIIllI = llllllllllllllllIllIIlIlIlIIlIII.getName().lastIndexOf("$");
        if (llllllllllllllllIllIIlIlIlIIIllI != -1) {
            String llllllllllllllllIllIIlIlIlIIlIIl = llllllllllllllllIllIIlIlIlIIlIII.getName().substring(0, llllllllllllllllIllIIlIlIlIIIllI);
            try {
                return Native.findDirectMappedClass(Class.forName(llllllllllllllllIllIIlIlIlIIlIIl, true, llllllllllllllllIllIIlIlIlIIlIII.getClassLoader()));
            }
            catch (ClassNotFoundException llllllllllllllllIllIIlIlIlIIIIIl) {
                // empty catch block
            }
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Can't determine class with native methods from the current context (").append(llllllllllllllllIllIIlIlIlIIlIII).append(")")));
    }

    public static Callback.UncaughtExceptionHandler getCallbackExceptionHandler() {
        return callbackExceptionHandler;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void register(Class<?> llllllllllllllllIllIIlIIlIlllIIl, NativeLibrary llllllllllllllllIllIIlIIlIlllIII) {
        Method[] llllllllllllllllIllIIlIIlIllIlll = llllllllllllllllIllIIlIIlIlllIIl.getDeclaredMethods();
        ArrayList<Method> llllllllllllllllIllIIlIIlIllIllI = new ArrayList<Method>();
        Map<String, Object> llllllllllllllllIllIIlIIlIllIlIl = llllllllllllllllIllIIlIIlIlllIII.getOptions();
        TypeMapper llllllllllllllllIllIIlIIlIllIlII = (TypeMapper)llllllllllllllllIllIIlIIlIllIlIl.get("type-mapper");
        llllllllllllllllIllIIlIIlIllIlIl = Native.cacheOptions(llllllllllllllllIllIIlIIlIlllIIl, llllllllllllllllIllIIlIIlIllIlIl, null);
        for (Method llllllllllllllllIllIIlIIllIlIlll : llllllllllllllllIllIIlIIlIllIlll) {
            if ((llllllllllllllllIllIIlIIllIlIlll.getModifiers() & 0x100) == 0) continue;
            llllllllllllllllIllIIlIIlIllIllI.add(llllllllllllllllIllIIlIIllIlIlll);
        }
        long[] llllllllllllllllIllIIlIIlIllIIll = new long[llllllllllllllllIllIIlIIlIllIllI.size()];
        for (int llllllllllllllllIllIIlIIlIlllIlI = 0; llllllllllllllllIllIIlIIlIlllIlI < llllllllllllllllIllIIlIIlIllIIll.length; ++llllllllllllllllIllIIlIIlIlllIlI) {
            long llllllllllllllllIllIIlIIllIIIlIl;
            long llllllllllllllllIllIIlIIllIIIllI;
            Method llllllllllllllllIllIIlIIllIIlIIl = (Method)llllllllllllllllIllIIlIIlIllIllI.get(llllllllllllllllIllIIlIIlIlllIlI);
            String llllllllllllllllIllIIlIIllIIlIII = "(";
            Class<?> llllllllllllllllIllIIlIIllIIIlll = llllllllllllllllIllIIlIIllIIlIIl.getReturnType();
            Class<?>[] llllllllllllllllIllIIlIIllIIIlII = llllllllllllllllIllIIlIIllIIlIIl.getParameterTypes();
            long[] llllllllllllllllIllIIlIIllIIIIll = new long[llllllllllllllllIllIIlIIllIIIlII.length];
            long[] llllllllllllllllIllIIlIIllIIIIlI = new long[llllllllllllllllIllIIlIIllIIIlII.length];
            int[] llllllllllllllllIllIIlIIllIIIIIl = new int[llllllllllllllllIllIIlIIllIIIlII.length];
            ToNativeConverter[] llllllllllllllllIllIIlIIllIIIIII = new ToNativeConverter[llllllllllllllllIllIIlIIllIIIlII.length];
            FromNativeConverter llllllllllllllllIllIIlIIlIllllll = null;
            int llllllllllllllllIllIIlIIlIlllllI = Native.getConversion(llllllllllllllllIllIIlIIllIIIlll, llllllllllllllllIllIIlIIlIllIlII);
            boolean llllllllllllllllIllIIlIIlIllllIl = false;
            switch (llllllllllllllllIllIIlIIlIlllllI) {
                case -1: {
                    throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIIllIIIlll).append(" is not a supported return type (in method ").append(llllllllllllllllIllIIlIIllIIlIIl.getName()).append(" in ").append(llllllllllllllllIllIIlIIlIlllIIl).append(")")));
                }
                case 23: 
                case 24: 
                case 25: {
                    llllllllllllllllIllIIlIIlIllllll = llllllllllllllllIllIIlIIlIllIlII.getFromNativeConverter(llllllllllllllllIllIIlIIllIIIlll);
                    long llllllllllllllllIllIIlIIllIlIlIl = Structure.FFIType.get(llllllllllllllllIllIIlIIllIIIlll.isPrimitive() ? llllllllllllllllIllIIlIIllIIIlll : Pointer.class).peer;
                    long llllllllllllllllIllIIlIIllIlIllI = Structure.FFIType.get(llllllllllllllllIllIIlIIlIllllll.nativeType()).peer;
                    break;
                }
                case 17: 
                case 18: 
                case 19: 
                case 21: 
                case 22: {
                    long llllllllllllllllIllIIlIIllIlIIll = Structure.FFIType.get(Pointer.class).peer;
                    long llllllllllllllllIllIIlIIllIlIlII = Structure.FFIType.get(NativeMappedConverter.getInstance(llllllllllllllllIllIIlIIllIIIlll).nativeType()).peer;
                    break;
                }
                case 3: {
                    long llllllllllllllllIllIIlIIllIlIIlI;
                    long llllllllllllllllIllIIlIIllIlIIIl = llllllllllllllllIllIIlIIllIlIIlI = Structure.FFIType.get(Pointer.class).peer;
                    break;
                }
                case 4: {
                    long llllllllllllllllIllIIlIIllIIllll = Structure.FFIType.get(Pointer.class).peer;
                    long llllllllllllllllIllIIlIIllIlIIII = Structure.FFIType.get(llllllllllllllllIllIIlIIllIIIlll).peer;
                    break;
                }
                default: {
                    llllllllllllllllIllIIlIIllIIIlIl = llllllllllllllllIllIIlIIllIIIllI = Structure.FFIType.get(llllllllllllllllIllIIlIIllIIIlll).peer;
                }
            }
            block19: for (int llllllllllllllllIllIIlIIllIIllII = 0; llllllllllllllllIllIIlIIllIIllII < llllllllllllllllIllIIlIIllIIIlII.length; ++llllllllllllllllIllIIlIIllIIllII) {
                int llllllllllllllllIllIIlIIllIIllIl;
                Class<?> llllllllllllllllIllIIlIIllIIlllI = llllllllllllllllIllIIlIIllIIIlII[llllllllllllllllIllIIlIIllIIllII];
                llllllllllllllllIllIIlIIllIIlIII = String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIIllIIlIII).append(Native.getSignature(llllllllllllllllIllIIlIIllIIlllI)));
                llllllllllllllllIllIIlIIllIIIIIl[llllllllllllllllIllIIlIIllIIllII] = llllllllllllllllIllIIlIIllIIllIl = Native.getConversion(llllllllllllllllIllIIlIIllIIlllI, llllllllllllllllIllIIlIIlIllIlII);
                if (llllllllllllllllIllIIlIIllIIllIl == -1) {
                    throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIIllIIlllI).append(" is not a supported argument type (in method ").append(llllllllllllllllIllIIlIIllIIlIIl.getName()).append(" in ").append(llllllllllllllllIllIIlIIlIlllIIl).append(")")));
                }
                if (llllllllllllllllIllIIlIIllIIllIl == 17 || llllllllllllllllIllIIlIIllIIllIl == 18 || llllllllllllllllIllIIlIIllIIllIl == 19 || llllllllllllllllIllIIlIIllIIllIl == 21) {
                    llllllllllllllllIllIIlIIllIIlllI = NativeMappedConverter.getInstance(llllllllllllllllIllIIlIIllIIlllI).nativeType();
                } else if (llllllllllllllllIllIIlIIllIIllIl == 23 || llllllllllllllllIllIIlIIllIIllIl == 24 || llllllllllllllllIllIIlIIllIIllIl == 25) {
                    llllllllllllllllIllIIlIIllIIIIII[llllllllllllllllIllIIlIIllIIllII] = llllllllllllllllIllIIlIIlIllIlII.getToNativeConverter(llllllllllllllllIllIIlIIllIIlllI);
                }
                switch (llllllllllllllllIllIIlIIllIIllIl) {
                    case 4: 
                    case 17: 
                    case 18: 
                    case 19: 
                    case 21: 
                    case 22: {
                        llllllllllllllllIllIIlIIllIIIIll[llllllllllllllllIllIIlIIllIIllII] = Structure.FFIType.get(llllllllllllllllIllIIlIIllIIlllI).peer;
                        llllllllllllllllIllIIlIIllIIIIlI[llllllllllllllllIllIIlIIllIIllII] = Structure.FFIType.get(Pointer.class).peer;
                        continue block19;
                    }
                    case 23: 
                    case 24: 
                    case 25: {
                        llllllllllllllllIllIIlIIllIIIIlI[llllllllllllllllIllIIlIIllIIllII] = Structure.FFIType.get(llllllllllllllllIllIIlIIllIIlllI.isPrimitive() ? llllllllllllllllIllIIlIIllIIlllI : Pointer.class).peer;
                        llllllllllllllllIllIIlIIllIIIIll[llllllllllllllllIllIIlIIllIIllII] = Structure.FFIType.get(llllllllllllllllIllIIlIIllIIIIII[llllllllllllllllIllIIlIIllIIllII].nativeType()).peer;
                        continue block19;
                    }
                    case 0: {
                        llllllllllllllllIllIIlIIllIIIIlI[llllllllllllllllIllIIlIIllIIllII] = llllllllllllllllIllIIlIIllIIIIll[llllllllllllllllIllIIlIIllIIllII] = Structure.FFIType.get(llllllllllllllllIllIIlIIllIIlllI).peer;
                        continue block19;
                    }
                    default: {
                        llllllllllllllllIllIIlIIllIIIIlI[llllllllllllllllIllIIlIIllIIllII] = llllllllllllllllIllIIlIIllIIIIll[llllllllllllllllIllIIlIIllIIllII] = Structure.FFIType.get(Pointer.class).peer;
                    }
                }
            }
            llllllllllllllllIllIIlIIllIIlIII = String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIIllIIlIII).append(")"));
            llllllllllllllllIllIIlIIllIIlIII = String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIIllIIlIII).append(Native.getSignature(llllllllllllllllIllIIlIIllIIIlll)));
            Class<?>[] llllllllllllllllIllIIlIIlIllllII = llllllllllllllllIllIIlIIllIIlIIl.getExceptionTypes();
            for (int llllllllllllllllIllIIlIIllIIlIll = 0; llllllllllllllllIllIIlIIllIIlIll < llllllllllllllllIllIIlIIlIllllII.length; ++llllllllllllllllIllIIlIIllIIlIll) {
                if (!LastErrorException.class.isAssignableFrom(llllllllllllllllIllIIlIIlIllllII[llllllllllllllllIllIIlIIllIIlIll])) continue;
                llllllllllllllllIllIIlIIlIllllIl = true;
                break;
            }
            Function llllllllllllllllIllIIlIIlIlllIll = llllllllllllllllIllIIlIIlIlllIII.getFunction(llllllllllllllllIllIIlIIllIIlIIl.getName(), llllllllllllllllIllIIlIIllIIlIIl);
            try {
                llllllllllllllllIllIIlIIlIllIIll[llllllllllllllllIllIIlIIlIlllIlI] = Native.registerMethod(llllllllllllllllIllIIlIIlIlllIIl, llllllllllllllllIllIIlIIllIIlIIl.getName(), llllllllllllllllIllIIlIIllIIlIII, llllllllllllllllIllIIlIIllIIIIIl, llllllllllllllllIllIIlIIllIIIIlI, llllllllllllllllIllIIlIIllIIIIll, llllllllllllllllIllIIlIIlIlllllI, llllllllllllllllIllIIlIIllIIIlIl, llllllllllllllllIllIIlIIllIIIllI, llllllllllllllllIllIIlIIllIIlIIl, llllllllllllllllIllIIlIIlIlllIll.peer, llllllllllllllllIllIIlIIlIlllIll.getCallingConvention(), llllllllllllllllIllIIlIIlIllllIl, llllllllllllllllIllIIlIIllIIIIII, llllllllllllllllIllIIlIIlIllllll, llllllllllllllllIllIIlIIlIlllIll.encoding);
                continue;
            }
            catch (NoSuchMethodError llllllllllllllllIllIIlIIllIIlIlI) {
                throw new UnsatisfiedLinkError(String.valueOf(new StringBuilder().append("No method ").append(llllllllllllllllIllIIlIIllIIlIIl.getName()).append(" with signature ").append(llllllllllllllllIllIIlIIllIIlIII).append(" in ").append(llllllllllllllllIllIIlIIlIlllIIl)));
            }
        }
        Map<Class<?>, long[]> map = registeredClasses;
        synchronized (map) {
            registeredClasses.put(llllllllllllllllIllIIlIIlIlllIIl, llllllllllllllllIllIIlIIlIllIIll);
            registeredLibraries.put(llllllllllllllllIllIIlIIlIlllIIl, llllllllllllllllIllIIlIIlIlllIII);
        }
    }

    public static /* bridge */ native /* synthetic */ void ffi_free_closure(long var0);

    static /* bridge */ native /* synthetic */ byte[] getStringBytes(Pointer var0, long var1, long var3);

    static boolean isUnpacked(File llllllllllllllllIllIIlIllllIIIII) {
        return llllllllllllllllIllIIlIllllIIIII.getName().startsWith("jna");
    }

    @Deprecated
    public static boolean getPreserveLastError() {
        return true;
    }

    static String getString(Pointer llllllllllllllllIllIIlIIIIlIllll, long llllllllllllllllIllIIlIIIIlIlllI) {
        return Native.getString(llllllllllllllllIllIIlIIIIlIllll, llllllllllllllllIllIIlIIIIlIlllI, Native.getDefaultStringEncoding());
    }

    static /* bridge */ native /* synthetic */ void setInt(Pointer var0, long var1, long var3, int var5);

    static Pointer getPointer(long llllllllllllllllIllIIlIIIIllIlIl) {
        long llllllllllllllllIllIIlIIIIllIlII = Native._getPointer(llllllllllllllllIllIIlIIIIllIlIl);
        return llllllllllllllllIllIIlIIIIllIlII == 0L ? null : new Pointer(llllllllllllllllIllIIlIIIIllIlII);
    }

    static /* bridge */ native /* synthetic */ void setMemory(Pointer var0, long var1, long var3, long var5, byte var7);

    public static char[] toCharArray(String llllllllllllllllIllIIllIIIIIllIl) {
        char[] llllllllllllllllIllIIllIIIIIllII = llllllllllllllllIllIIllIIIIIllIl.toCharArray();
        char[] llllllllllllllllIllIIllIIIIIlIll = new char[llllllllllllllllIllIIllIIIIIllII.length + 1];
        System.arraycopy(llllllllllllllllIllIIllIIIIIllII, 0, llllllllllllllllIllIIllIIIIIlIll, 0, llllllllllllllllIllIIllIIIIIllII.length);
        return llllllllllllllllIllIIllIIIIIlIll;
    }

    public static /* bridge */ native /* synthetic */ long ffi_prep_cif(int var0, int var1, long var2, long var4);

    static /* bridge */ native /* synthetic */ short getShort(Pointer var0, long var1, long var3);

    private static /* bridge */ native /* synthetic */ long registerMethod(Class<?> var0, String var1, String var2, int[] var3, long[] var4, long[] var5, int var6, long var7, long var9, Method var11, long var12, int var14, boolean var15, ToNativeConverter[] var16, FromNativeConverter var17, String var18);

    static String getString(Pointer llllllllllllllllIllIIlIIIIlIIIlI, long llllllllllllllllIllIIlIIIIlIIIIl, String llllllllllllllllIllIIlIIIIlIIIII) {
        byte[] llllllllllllllllIllIIlIIIIlIIIll = Native.getStringBytes(llllllllllllllllIllIIlIIIIlIIIlI, llllllllllllllllIllIIlIIIIlIIIlI.peer, llllllllllllllllIllIIlIIIIlIIIIl);
        if (llllllllllllllllIllIIlIIIIlIIIII != null) {
            try {
                return new String(llllllllllllllllIllIIlIIIIlIIIll, llllllllllllllllIllIIlIIIIlIIIII);
            }
            catch (UnsupportedEncodingException llllllllllllllllIllIIlIIIIIllllI) {
                // empty catch block
            }
        }
        return new String(llllllllllllllllIllIIlIIIIlIIIll);
    }

    static /* bridge */ native /* synthetic */ long indexOf(Pointer var0, long var1, long var3, byte var5);

    public static /* bridge */ native /* synthetic */ long ffi_prep_closure(long var0, ffi_callback var2);

    private static /* bridge */ native /* synthetic */ void setDetachState(boolean var0, long var1);

    public static int getNativeSize(Class<?> llllllllllllllllIllIIlIlIllIIllI, Object llllllllllllllllIllIIlIlIllIIlll) {
        if (llllllllllllllllIllIIlIlIllIIllI.isArray()) {
            int llllllllllllllllIllIIlIlIllIlIlI = Array.getLength(llllllllllllllllIllIIlIlIllIIlll);
            if (llllllllllllllllIllIIlIlIllIlIlI > 0) {
                Object llllllllllllllllIllIIlIlIllIlIll = Array.get(llllllllllllllllIllIIlIlIllIIlll, 0);
                return llllllllllllllllIllIIlIlIllIlIlI * Native.getNativeSize(llllllllllllllllIllIIlIlIllIIllI.getComponentType(), llllllllllllllllIllIIlIlIllIlIll);
            }
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Arrays of length zero not allowed: ").append(llllllllllllllllIllIIlIlIllIIllI)));
        }
        if (Structure.class.isAssignableFrom(llllllllllllllllIllIIlIlIllIIllI) && !Structure.ByReference.class.isAssignableFrom(llllllllllllllllIllIIlIlIllIIllI)) {
            return Structure.size(llllllllllllllllIllIIlIlIllIIllI, (Structure)llllllllllllllllIllIIlIlIllIIlll);
        }
        try {
            return Native.getNativeSize(llllllllllllllllIllIIlIlIllIIllI);
        }
        catch (IllegalArgumentException llllllllllllllllIllIIlIlIllIlIIl) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("The type \"").append(llllllllllllllllIllIIlIlIllIIllI.getName()).append("\" is not supported: ").append(llllllllllllllllIllIIlIlIllIlIIl.getMessage())));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Map<String, Object> getLibraryOptions(Class<?> llllllllllllllllIllIIllIIlIlIlll) {
        Map<Class<?>, Reference<?>> llllllllllllllllIllIIllIIlIlIIlI = libraries;
        synchronized (llllllllllllllllIllIIllIIlIlIIlI) {
            Map<String, Object> llllllllllllllllIllIIllIIlIllIll = typeOptions.get(llllllllllllllllIllIIllIIlIlIlll);
            if (llllllllllllllllIllIIllIIlIllIll != null) {
                return llllllllllllllllIllIIllIIlIllIll;
            }
        }
        Class<?> llllllllllllllllIllIIllIIlIlIlIl = Native.findEnclosingLibraryClass(llllllllllllllllIllIIllIIlIlIlll);
        if (llllllllllllllllIllIIllIIlIlIlIl != null) {
            Native.loadLibraryInstance(llllllllllllllllIllIIllIIlIlIlIl);
        } else {
            llllllllllllllllIllIIllIIlIlIlIl = llllllllllllllllIllIIllIIlIlIlll;
        }
        Map<Class<?>, Reference<?>> llllllllllllllllIllIIllIIlIlIIIl = libraries;
        synchronized (llllllllllllllllIllIIllIIlIlIIIl) {
            Map<String, Object> llllllllllllllllIllIIllIIlIlIllI = typeOptions.get(llllllllllllllllIllIIllIIlIlIlIl);
            if (llllllllllllllllIllIIllIIlIlIllI != null) {
                typeOptions.put(llllllllllllllllIllIIllIIlIlIlll, llllllllllllllllIllIIllIIlIlIllI);
                return llllllllllllllllIllIIllIIlIlIllI;
            }
            try {
                Field llllllllllllllllIllIIllIIlIllIlI = llllllllllllllllIllIIllIIlIlIlIl.getField("OPTIONS");
                llllllllllllllllIllIIllIIlIllIlI.setAccessible(true);
                llllllllllllllllIllIIllIIlIlIllI = (Map<String, Object>)llllllllllllllllIllIIllIIlIllIlI.get(null);
                if (llllllllllllllllIllIIllIIlIlIllI == null) {
                    throw new IllegalStateException("Null options field");
                }
            }
            catch (NoSuchFieldException llllllllllllllllIllIIllIIlIllIIl) {
                llllllllllllllllIllIIllIIlIlIllI = Collections.emptyMap();
            }
            catch (Exception llllllllllllllllIllIIllIIlIllIII) {
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("OPTIONS must be a public field of type java.util.Map (").append(llllllllllllllllIllIIllIIlIllIII).append("): ").append(llllllllllllllllIllIIllIIlIlIlIl)));
            }
            llllllllllllllllIllIIllIIlIlIllI = new HashMap<String, Object>(llllllllllllllllIllIIllIIlIlIllI);
            if (!llllllllllllllllIllIIllIIlIlIllI.containsKey("type-mapper")) {
                llllllllllllllllIllIIllIIlIlIllI.put("type-mapper", Native.lookupField(llllllllllllllllIllIIllIIlIlIlIl, "TYPE_MAPPER", TypeMapper.class));
            }
            if (!llllllllllllllllIllIIllIIlIlIllI.containsKey("structure-alignment")) {
                llllllllllllllllIllIIllIIlIlIllI.put("structure-alignment", Native.lookupField(llllllllllllllllIllIIllIIlIlIlIl, "STRUCTURE_ALIGNMENT", Integer.class));
            }
            if (!llllllllllllllllIllIIllIIlIlIllI.containsKey("string-encoding")) {
                llllllllllllllllIllIIllIIlIlIllI.put("string-encoding", Native.lookupField(llllllllllllllllIllIIllIIlIlIlIl, "STRING_ENCODING", String.class));
            }
            llllllllllllllllIllIIllIIlIlIllI = Native.cacheOptions(llllllllllllllllIllIIllIIlIlIlIl, llllllllllllllllIllIIllIIlIlIllI, null);
            if (llllllllllllllllIllIIllIIlIlIlll != llllllllllllllllIllIIllIIlIlIlIl) {
                typeOptions.put(llllllllllllllllIllIIllIIlIlIlll, llllllllllllllllIllIIllIIlIlIllI);
            }
            return llllllllllllllllIllIIllIIlIlIllI;
        }
    }

    private static /* bridge */ native /* synthetic */ void unregister(Class<?> var0, long[] var1);

    private static void dispose() {
        CallbackReference.disposeAll();
        Memory.disposeAll();
        NativeLibrary.disposeAll();
        Native.unregisterAll();
        jnidispatchPath = null;
        System.setProperty("jna.loaded", "false");
    }

    public static byte[] toByteArray(String llllllllllllllllIllIIllIIIIllIII, String llllllllllllllllIllIIllIIIIlIlll) {
        byte[] llllllllllllllllIllIIllIIIIlIllI = Native.getBytes(llllllllllllllllIllIIllIIIIllIII, llllllllllllllllIllIIllIIIIlIlll);
        byte[] llllllllllllllllIllIIllIIIIlIlIl = new byte[llllllllllllllllIllIIllIIIIlIllI.length + 1];
        System.arraycopy(llllllllllllllllIllIIllIIIIlIllI, 0, llllllllllllllllIllIIllIIIIlIlIl, 0, llllllllllllllllIllIIllIIIIlIllI.length);
        return llllllllllllllllIllIIllIIIIlIlIl;
    }

    static /* bridge */ native /* synthetic */ void write(Pointer var0, long var1, long var3, byte[] var5, int var6, int var7);

    static /* bridge */ native /* synthetic */ long invokeLong(Function var0, long var1, int var3, Object[] var4);

    static long open(String llllllllllllllllIllIIlIIIIlllIIl) {
        return Native.open(llllllllllllllllIllIIlIIIIlllIIl, -1);
    }

    static byte[] getBytes(String llllllllllllllllIllIIllIIIlIlIlI) {
        return Native.getBytes(llllllllllllllllIllIIllIIIlIlIlI, Native.getDefaultStringEncoding());
    }

    static /* bridge */ native /* synthetic */ void write(Pointer var0, long var1, long var3, double[] var5, int var6, int var7);

    private Native() {
        Native llllllllllllllllIllIIllIlllIllII;
    }

    public static Pointer getWindowPointer(Window llllllllllllllllIllIIllIlllIIIlI) throws HeadlessException {
        return new Pointer(AWT.getWindowID(llllllllllllllllIllIIllIlllIIIlI));
    }

    public static void register(String llllllllllllllllIllIIlIlIlIlIlII) {
        Native.register(Native.findDirectMappedClass(Native.getCallingClass()), llllllllllllllllIllIIlIlIlIlIlII);
    }

    static /* bridge */ native /* synthetic */ void read(Pointer var0, long var1, long var3, int[] var5, int var6, int var7);

    static byte[] getBytes(String llllllllllllllllIllIIllIIIlIIlII, String llllllllllllllllIllIIllIIIlIIIIl) {
        if (llllllllllllllllIllIIllIIIlIIIIl != null) {
            try {
                return llllllllllllllllIllIIllIIIlIIlII.getBytes(llllllllllllllllIllIIllIIIlIIIIl);
            }
            catch (UnsupportedEncodingException llllllllllllllllIllIIllIIIlIIlIl) {
                System.err.println(String.valueOf(new StringBuilder().append("JNA Warning: Encoding '").append(llllllllllllllllIllIIllIIIlIIIIl).append("' is unsupported")));
            }
        }
        System.err.println(String.valueOf(new StringBuilder().append("JNA Warning: Encoding with fallback ").append(System.getProperty("file.encoding"))));
        return llllllllllllllllIllIIllIIIlIIlII.getBytes();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void unregister(Class<?> llllllllllllllllIllIIlIlIIlIlIII) {
        Map<Class<?>, long[]> llllllllllllllllIllIIlIlIIlIIllI = registeredClasses;
        synchronized (llllllllllllllllIllIIlIlIIlIIllI) {
            long[] llllllllllllllllIllIIlIlIIlIlIIl = registeredClasses.get(llllllllllllllllIllIIlIlIIlIlIII);
            if (llllllllllllllllIllIIlIlIIlIlIIl != null) {
                Native.unregister(llllllllllllllllIllIIlIlIIlIlIII, llllllllllllllllIllIIlIlIIlIlIIl);
                registeredClasses.remove(llllllllllllllllIllIIlIlIIlIlIII);
                registeredLibraries.remove(llllllllllllllllIllIIlIlIIlIlIII);
            }
        }
    }

    static /* bridge */ native /* synthetic */ void setPointer(Pointer var0, long var1, long var3, long var5);

    static /* bridge */ native /* synthetic */ void setShort(Pointer var0, long var1, long var3, short var5);

    private static Object fromNative(FromNativeConverter llllllllllllllllIllIIlIIIlIlllll, Object llllllllllllllllIllIIlIIIlIllllI, Method llllllllllllllllIllIIlIIIlIlllIl) {
        return llllllllllllllllIllIIlIIIlIlllll.fromNative(llllllllllllllllIllIIlIIIlIllllI, new MethodResultContext(llllllllllllllllIllIIlIIIlIlllIl.getReturnType(), null, null, llllllllllllllllIllIIlIIIlIlllIl));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void unregisterAll() {
        Map<Class<?>, long[]> llllllllllllllllIllIIlIlIIllIIIl = registeredClasses;
        synchronized (llllllllllllllllIllIIlIlIIllIIIl) {
            for (Map.Entry<Class<?>, long[]> llllllllllllllllIllIIlIlIIllIIlI : registeredClasses.entrySet()) {
                Native.unregister(llllllllllllllllIllIIlIlIIllIIlI.getKey(), llllllllllllllllIllIIlIlIIllIIlI.getValue());
            }
            registeredClasses.clear();
        }
    }

    static /* bridge */ native /* synthetic */ void read(Pointer var0, long var1, long var3, short[] var5, int var6, int var7);

    private static /* bridge */ native /* synthetic */ long _getDirectBufferPointer(Buffer var0);

    public static List<String> toStringList(char[] llllllllllllllllIllIIllIlIllIIII, int llllllllllllllllIllIIllIlIlIlIIl, int llllllllllllllllIllIIllIlIlIlllI) {
        ArrayList<String> llllllllllllllllIllIIllIlIlIllIl = new ArrayList<String>();
        int llllllllllllllllIllIIllIlIlIllII = llllllllllllllllIllIIllIlIlIlIIl;
        int llllllllllllllllIllIIllIlIlIlIll = llllllllllllllllIllIIllIlIlIlIIl + llllllllllllllllIllIIllIlIlIlllI;
        for (int llllllllllllllllIllIIllIlIllIIlI = llllllllllllllllIllIIllIlIlIlIIl; llllllllllllllllIllIIllIlIllIIlI < llllllllllllllllIllIIllIlIlIlIll; ++llllllllllllllllIllIIllIlIllIIlI) {
            if (llllllllllllllllIllIIllIlIllIIII[llllllllllllllllIllIIllIlIllIIlI] != '\u0000') continue;
            if (llllllllllllllllIllIIllIlIlIllII == llllllllllllllllIllIIllIlIllIIlI) {
                return llllllllllllllllIllIIllIlIlIllIl;
            }
            String llllllllllllllllIllIIllIlIllIIll = new String(llllllllllllllllIllIIllIlIllIIII, llllllllllllllllIllIIllIlIlIllII, llllllllllllllllIllIIllIlIllIIlI - llllllllllllllllIllIIllIlIlIllII);
            llllllllllllllllIllIIllIlIlIllIl.add(llllllllllllllllIllIIllIlIllIIll);
            llllllllllllllllIllIIllIlIlIllII = llllllllllllllllIllIIllIlIllIIlI + 1;
        }
        if (llllllllllllllllIllIIllIlIlIllII < llllllllllllllllIllIIllIlIlIlIll) {
            String llllllllllllllllIllIIllIlIllIIIl = new String(llllllllllllllllIllIIllIlIllIIII, llllllllllllllllIllIIllIlIlIllII, llllllllllllllllIllIIllIlIlIlIll - llllllllllllllllIllIIllIlIlIllII);
            llllllllllllllllIllIIllIlIlIllIl.add(llllllllllllllllIllIIllIlIllIIIl);
        }
        return llllllllllllllllIllIIllIlIlIllIl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Map<String, Object> cacheOptions(Class<?> llllllllllllllllIllIIlIIlIIIlIII, Map<String, ?> llllllllllllllllIllIIlIIlIIIIlll, Object llllllllllllllllIllIIlIIlIIIIllI) {
        HashMap<String, Object> llllllllllllllllIllIIlIIlIIIlIIl = new HashMap<String, Object>(llllllllllllllllIllIIlIIlIIIIlll);
        llllllllllllllllIllIIlIIlIIIlIIl.put("enclosing-library", llllllllllllllllIllIIlIIlIIIlIII);
        Map<Class<?>, Reference<?>> llllllllllllllllIllIIlIIlIIIIlII = libraries;
        synchronized (llllllllllllllllIllIIlIIlIIIIlII) {
            typeOptions.put(llllllllllllllllIllIIlIIlIIIlIII, llllllllllllllllIllIIlIIlIIIlIIl);
            if (llllllllllllllllIllIIlIIlIIIIllI != null) {
                libraries.put(llllllllllllllllIllIIlIIlIIIlIII, new WeakReference<Object>(llllllllllllllllIllIIlIIlIIIIllI));
            }
            if (!llllllllllllllllIllIIlIIlIIIlIII.isInterface() && Library.class.isAssignableFrom(llllllllllllllllIllIIlIIlIIIlIII)) {
                Class<?>[] llllllllllllllllIllIIlIIlIIIllIl;
                for (Class<?> llllllllllllllllIllIIlIIlIIIlllI : llllllllllllllllIllIIlIIlIIIllIl = llllllllllllllllIllIIlIIlIIIlIII.getInterfaces()) {
                    if (!Library.class.isAssignableFrom(llllllllllllllllIllIIlIIlIIIlllI)) continue;
                    Native.cacheOptions(llllllllllllllllIllIIlIIlIIIlllI, llllllllllllllllIllIIlIIlIIIlIIl, llllllllllllllllIllIIlIIlIIIIllI);
                    break;
                }
            }
        }
        return llllllllllllllllIllIIlIIlIIIlIIl;
    }

    public static <T> T loadLibrary(String llllllllllllllllIllIIllIlIIlIlll, Class<T> llllllllllllllllIllIIllIlIIlIllI) {
        return Native.loadLibrary(llllllllllllllllIllIIllIlIIlIlll, llllllllllllllllIllIIllIlIIlIllI, Collections.emptyMap());
    }

    static Structure invokeStructure(Function llllllllllllllllIllIIlIIIIllllll, long llllllllllllllllIllIIlIIIIlllllI, int llllllllllllllllIllIIlIIIIllllIl, Object[] llllllllllllllllIllIIlIIIlIIIIIl, Structure llllllllllllllllIllIIlIIIIlllIll) {
        Native.invokeStructure(llllllllllllllllIllIIlIIIIllllll, llllllllllllllllIllIIlIIIIlllllI, llllllllllllllllIllIIlIIIIllllIl, llllllllllllllllIllIIlIIIlIIIIIl, llllllllllllllllIllIIlIIIIlllIll.getPointer().peer, llllllllllllllllIllIIlIIIIlllIll.getTypeInfo().peer);
        return llllllllllllllllIllIIlIIIIlllIll;
    }

    private static /* bridge */ native /* synthetic */ void initIDs();

    static /* bridge */ native /* synthetic */ double invokeDouble(Function var0, long var1, int var3, Object[] var4);

    public static void setCallbackExceptionHandler(Callback.UncaughtExceptionHandler llllllllllllllllIllIIlIlIlIlIlll) {
        callbackExceptionHandler = llllllllllllllllIllIIlIlIlIlIlll == null ? DEFAULT_HANDLER : llllllllllllllllIllIIlIlIlIlIlll;
    }

    public static File extractFromResourcePath(String llllllllllllllllIllIIlIllIllllll, ClassLoader llllllllllllllllIllIIlIllIlllllI) throws IOException {
        URL llllllllllllllllIllIIlIlllIIIIIl;
        String llllllllllllllllIllIIlIlllIIIIlI;
        boolean llllllllllllllllIllIIlIlllIIIlII;
        boolean bl = llllllllllllllllIllIIlIlllIIIlII = DEBUG_LOAD || DEBUG_JNA_LOAD && llllllllllllllllIllIIlIllIllllll.indexOf("jnidispatch") != -1;
        if (llllllllllllllllIllIIlIllIlllllI == null && (llllllllllllllllIllIIlIllIlllllI = Thread.currentThread().getContextClassLoader()) == null) {
            llllllllllllllllIllIIlIllIlllllI = Native.class.getClassLoader();
        }
        if (llllllllllllllllIllIIlIlllIIIlII) {
            System.out.println(String.valueOf(new StringBuilder().append("Looking in classpath from ").append(llllllllllllllllIllIIlIllIlllllI).append(" for ").append(llllllllllllllllIllIIlIllIllllll)));
        }
        String llllllllllllllllIllIIlIlllIIIIll = llllllllllllllllIllIIlIllIllllll.startsWith("/") ? llllllllllllllllIllIIlIllIllllll : NativeLibrary.mapSharedLibraryName(llllllllllllllllIllIIlIllIllllll);
        String string = llllllllllllllllIllIIlIlllIIIIlI = llllllllllllllllIllIIlIllIllllll.startsWith("/") ? llllllllllllllllIllIIlIllIllllll : String.valueOf(new StringBuilder().append(Platform.RESOURCE_PREFIX).append("/").append(llllllllllllllllIllIIlIlllIIIIll));
        if (llllllllllllllllIllIIlIlllIIIIlI.startsWith("/")) {
            llllllllllllllllIllIIlIlllIIIIlI = llllllllllllllllIllIIlIlllIIIIlI.substring(1);
        }
        if ((llllllllllllllllIllIIlIlllIIIIIl = llllllllllllllllIllIIlIllIlllllI.getResource(llllllllllllllllIllIIlIlllIIIIlI)) == null && llllllllllllllllIllIIlIlllIIIIlI.startsWith(Platform.RESOURCE_PREFIX)) {
            llllllllllllllllIllIIlIlllIIIIIl = llllllllllllllllIllIIlIllIlllllI.getResource(llllllllllllllllIllIIlIlllIIIIll);
        }
        if (llllllllllllllllIllIIlIlllIIIIIl == null) {
            String llllllllllllllllIllIIlIlllIIlllI = System.getProperty("java.class.path");
            if (llllllllllllllllIllIIlIllIlllllI instanceof URLClassLoader) {
                llllllllllllllllIllIIlIlllIIlllI = Arrays.asList(((URLClassLoader)llllllllllllllllIllIIlIllIlllllI).getURLs()).toString();
            }
            throw new IOException(String.valueOf(new StringBuilder().append("Native library (").append(llllllllllllllllIllIIlIlllIIIIlI).append(") not found in resource path (").append(llllllllllllllllIllIIlIlllIIlllI).append(")")));
        }
        if (llllllllllllllllIllIIlIlllIIIlII) {
            System.out.println(String.valueOf(new StringBuilder().append("Found library resource at ").append(llllllllllllllllIllIIlIlllIIIIIl)));
        }
        File llllllllllllllllIllIIlIlllIIIIII = null;
        if (llllllllllllllllIllIIlIlllIIIIIl.getProtocol().toLowerCase().equals("file")) {
            try {
                llllllllllllllllIllIIlIlllIIIIII = new File(new URI(llllllllllllllllIllIIlIlllIIIIIl.toString()));
            }
            catch (URISyntaxException llllllllllllllllIllIIlIlllIIllIl) {
                llllllllllllllllIllIIlIlllIIIIII = new File(llllllllllllllllIllIIlIlllIIIIIl.getPath());
            }
            if (llllllllllllllllIllIIlIlllIIIlII) {
                System.out.println(String.valueOf(new StringBuilder().append("Looking in ").append(llllllllllllllllIllIIlIlllIIIIII.getAbsolutePath())));
            }
            if (!llllllllllllllllIllIIlIlllIIIIII.exists()) {
                throw new IOException(String.valueOf(new StringBuilder().append("File URL ").append(llllllllllllllllIllIIlIlllIIIIIl).append(" could not be properly decoded")));
            }
        } else if (!Boolean.getBoolean("jna.nounpack")) {
            InputStream llllllllllllllllIllIIlIlllIIlIII = llllllllllllllllIllIIlIllIlllllI.getResourceAsStream(llllllllllllllllIllIIlIlllIIIIlI);
            if (llllllllllllllllIllIIlIlllIIlIII == null) {
                throw new IOException(String.valueOf(new StringBuilder().append("Can't obtain InputStream for ").append(llllllllllllllllIllIIlIlllIIIIlI)));
            }
            FileOutputStream llllllllllllllllIllIIlIlllIIIlll = null;
            try {
                int llllllllllllllllIllIIlIlllIIlIll;
                File llllllllllllllllIllIIlIlllIIllII = Native.getTempDir();
                llllllllllllllllIllIIlIlllIIIIII = File.createTempFile("jna", Platform.isWindows() ? ".dll" : null, llllllllllllllllIllIIlIlllIIllII);
                if (!Boolean.getBoolean("jnidispatch.preserve")) {
                    llllllllllllllllIllIIlIlllIIIIII.deleteOnExit();
                }
                llllllllllllllllIllIIlIlllIIIlll = new FileOutputStream(llllllllllllllllIllIIlIlllIIIIII);
                byte[] llllllllllllllllIllIIlIlllIIlIlI = new byte[1024];
                while ((llllllllllllllllIllIIlIlllIIlIll = llllllllllllllllIllIIlIlllIIlIII.read(llllllllllllllllIllIIlIlllIIlIlI, 0, llllllllllllllllIllIIlIlllIIlIlI.length)) > 0) {
                    llllllllllllllllIllIIlIlllIIIlll.write(llllllllllllllllIllIIlIlllIIlIlI, 0, llllllllllllllllIllIIlIlllIIlIll);
                }
            }
            catch (IOException llllllllllllllllIllIIlIlllIIlIIl) {
                throw new IOException(String.valueOf(new StringBuilder().append("Failed to create temporary file for ").append(llllllllllllllllIllIIlIllIllllll).append(" library: ").append(llllllllllllllllIllIIlIlllIIlIIl.getMessage())));
            }
            finally {
                try {
                    llllllllllllllllIllIIlIlllIIlIII.close();
                }
                catch (IOException llllllllllllllllIllIIlIllIllIIlI) {}
                if (llllllllllllllllIllIIlIlllIIIlll != null) {
                    try {
                        llllllllllllllllIllIIlIlllIIIlll.close();
                    }
                    catch (IOException llllllllllllllllIllIIlIllIllIIlI) {}
                }
            }
        }
        return llllllllllllllllIllIIlIlllIIIIII;
    }

    static /* bridge */ native /* synthetic */ void write(Pointer var0, long var1, long var3, short[] var5, int var6, int var7);

    static File getTempDir() throws IOException {
        File llllllllllllllllIllIIlIllIIIlIIl;
        String llllllllllllllllIllIIlIllIIIlIII = System.getProperty("jna.tmpdir");
        if (llllllllllllllllIllIIlIllIIIlIII != null) {
            File llllllllllllllllIllIIlIllIIIlIll = new File(llllllllllllllllIllIIlIllIIIlIII);
            llllllllllllllllIllIIlIllIIIlIll.mkdirs();
        } else {
            File llllllllllllllllIllIIlIllIIIlIlI = new File(System.getProperty("java.io.tmpdir"));
            llllllllllllllllIllIIlIllIIIlIIl = new File(llllllllllllllllIllIIlIllIIIlIlI, String.valueOf(new StringBuilder().append("jna-").append(System.getProperty("user.name").hashCode())));
            llllllllllllllllIllIIlIllIIIlIIl.mkdirs();
            if (!llllllllllllllllIllIIlIllIIIlIIl.exists() || !llllllllllllllllIllIIlIllIIIlIIl.canWrite()) {
                llllllllllllllllIllIIlIllIIIlIIl = llllllllllllllllIllIIlIllIIIlIlI;
            }
        }
        if (!llllllllllllllllIllIIlIllIIIlIIl.exists()) {
            throw new IOException(String.valueOf(new StringBuilder().append("JNA temporary directory '").append(llllllllllllllllIllIIlIllIIIlIIl).append("' does not exist")));
        }
        if (!llllllllllllllllIllIIlIllIIIlIIl.canWrite()) {
            throw new IOException(String.valueOf(new StringBuilder().append("JNA temporary directory '").append(llllllllllllllllIllIIlIllIIIlIIl).append("' is not writable")));
        }
        return llllllllllllllllIllIIlIllIIIlIIl;
    }

    static /* bridge */ native /* synthetic */ int getInt(Pointer var0, long var1, long var3);

    static /* bridge */ native /* synthetic */ long getWindowHandle0(Component var0);

    private static /* bridge */ native /* synthetic */ String getNativeVersion();

    static Class<?> getCallingClass() {
        Class<?>[] llllllllllllllllIllIIlIlIIlllllI = new SecurityManager(){
            {
                6 llIIllIIllIlIlI;
            }

            @Override
            public Class<?>[] getClassContext() {
                6 llIIllIIllIlIII;
                return super.getClassContext();
            }
        }.getClassContext();
        if (llllllllllllllllIllIIlIlIIlllllI == null) {
            throw new IllegalStateException("The SecurityManager implementation on this platform is broken; you must explicitly provide the class to register");
        }
        if (llllllllllllllllIllIIlIlIIlllllI.length < 4) {
            throw new IllegalStateException("This method must be called from the static initializer of a class");
        }
        return llllllllllllllllIllIIlIlIIlllllI[3];
    }

    public static String getWebStartLibraryPath(String llllllllllllllllIllIIlIllIIllIIl) {
        if (System.getProperty("javawebstart.version") == null) {
            return null;
        }
        try {
            ClassLoader llllllllllllllllIllIIlIllIIllllI = Native.class.getClassLoader();
            Method llllllllllllllllIllIIlIllIIlllIl = AccessController.doPrivileged(new PrivilegedAction<Method>(){

                @Override
                public Method run() {
                    try {
                        Method lllllllllllllllllllIIIlIIIIlIlll = ClassLoader.class.getDeclaredMethod("findLibrary", String.class);
                        lllllllllllllllllllIIIlIIIIlIlll.setAccessible(true);
                        return lllllllllllllllllllIIIlIIIIlIlll;
                    }
                    catch (Exception lllllllllllllllllllIIIlIIIIlIllI) {
                        return null;
                    }
                }
                {
                    4 lllllllllllllllllllIIIlIIIIllIIl;
                }
            });
            String llllllllllllllllIllIIlIllIIlllII = (String)llllllllllllllllIllIIlIllIIlllIl.invoke(llllllllllllllllIllIIlIllIIllllI, llllllllllllllllIllIIlIllIIllIIl);
            if (llllllllllllllllIllIIlIllIIlllII != null) {
                return new File(llllllllllllllllIllIIlIllIIlllII).getParent();
            }
            return null;
        }
        catch (Exception llllllllllllllllIllIIlIllIIllIll) {
            return null;
        }
    }

    public static void unregister() {
        Native.unregister(Native.findDirectMappedClass(Native.getCallingClass()));
    }

    public static /* bridge */ native /* synthetic */ void free(long var0);

    @Deprecated
    public static void setPreserveLastError(boolean llllllllllllllllIllIIllIlllIlIlI) {
    }

    public static boolean isSupportedNativeType(Class<?> llllllllllllllllIllIIlIlIlIllIll) {
        if (Structure.class.isAssignableFrom(llllllllllllllllIllIIlIlIlIllIll)) {
            return true;
        }
        try {
            return Native.getNativeSize(llllllllllllllllIllIIlIlIlIllIll) != 0;
        }
        catch (IllegalArgumentException llllllllllllllllIllIIlIlIlIlllIl) {
            return false;
        }
    }

    static /* bridge */ native /* synthetic */ ByteBuffer getDirectByteBuffer(Pointer var0, long var1, long var3, long var5);

    public static String toString(char[] llllllllllllllllIllIIllIllIIIIll) {
        int llllllllllllllllIllIIllIllIIIIlI = llllllllllllllllIllIIllIllIIIIll.length;
        for (int llllllllllllllllIllIIllIllIIIlII = 0; llllllllllllllllIllIIllIllIIIlII < llllllllllllllllIllIIllIllIIIIlI; ++llllllllllllllllIllIIllIllIIIlII) {
            if (llllllllllllllllIllIIllIllIIIIll[llllllllllllllllIllIIllIllIIIlII] != '\u0000') continue;
            llllllllllllllllIllIIllIllIIIIlI = llllllllllllllllIllIIllIllIIIlII;
            break;
        }
        if (llllllllllllllllIllIIllIllIIIIlI == 0) {
            return "";
        }
        return new String(llllllllllllllllIllIIllIllIIIIll, 0, llllllllllllllllIllIIllIllIIIIlI);
    }

    static /* bridge */ native /* synthetic */ void setLong(Pointer var0, long var1, long var3, long var5);

    static /* bridge */ native /* synthetic */ void setFloat(Pointer var0, long var1, long var3, float var5);

    static void markTemporaryFile(File llllllllllllllllIllIIlIllIIlIIIl) {
        try {
            File llllllllllllllllIllIIlIllIIlIIll = new File(llllllllllllllllIllIIlIllIIlIIIl.getParentFile(), String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIllIIlIIIl.getName()).append(".x")));
            llllllllllllllllIllIIlIllIIlIIll.createNewFile();
        }
        catch (IOException llllllllllllllllIllIIlIllIIlIIlI) {
            llllllllllllllllIllIIlIllIIlIIlI.printStackTrace();
        }
    }

    static synchronized /* bridge */ native /* synthetic */ void freeNativeCallback(long var0);

    static {
        TYPE_VOIDP = 0;
        CVT_ARRAY_DOUBLE = 12;
        CB_HAS_INITIALIZER = 1;
        TYPE_LONG = 1;
        JNA_TMPLIB_PREFIX = "jna";
        CVT_UNSUPPORTED = -1;
        CVT_ARRAY_LONG = 10;
        CVT_ARRAY_FLOAT = 11;
        CVT_CALLBACK = 15;
        CVT_ARRAY_SHORT = 7;
        CVT_TYPE_MAPPER_WSTRING = 25;
        CVT_STRING = 2;
        CVT_NATIVE_MAPPED_STRING = 18;
        CVT_DEFAULT = 0;
        TYPE_SIZE_T = 3;
        CVT_STRUCTURE_BYVAL = 4;
        _OPTION_ENCLOSING_LIBRARY = "enclosing-library";
        CVT_TYPE_MAPPER_STRING = 24;
        CVT_TYPE_MAPPER = 23;
        CVT_FLOAT = 16;
        CVT_NATIVE_MAPPED_WSTRING = 19;
        TYPE_BOOL = 4;
        CVT_POINTER = 1;
        CVT_POINTER_TYPE = 22;
        CVT_BOOLEAN = 14;
        CVT_WSTRING = 20;
        CB_OPTION_DIRECT = 1;
        CVT_ARRAY_BYTE = 6;
        CB_OPTION_IN_DLL = 2;
        CVT_BUFFER = 5;
        CVT_ARRAY_BOOLEAN = 13;
        TYPE_WCHAR_T = 2;
        CVT_NATIVE_MAPPED = 17;
        CVT_ARRAY_INT = 9;
        CVT_ARRAY_CHAR = 8;
        CVT_STRUCTURE = 3;
        CVT_INTEGER_TYPE = 21;
        DEFAULT_ENCODING = Charset.defaultCharset().name();
        DEBUG_LOAD = Boolean.getBoolean("jna.debug_load");
        DEBUG_JNA_LOAD = Boolean.getBoolean("jna.debug_load.jna");
        jnidispatchPath = null;
        typeOptions = new WeakHashMap();
        libraries = new WeakHashMap();
        callbackExceptionHandler = DEFAULT_HANDLER = new Callback.UncaughtExceptionHandler(){

            @Override
            public void uncaughtException(Callback lIIllIIIlllllII, Throwable lIIllIIIllllIll) {
                System.err.println(String.valueOf(new StringBuilder().append("JNA: Callback ").append(lIIllIIIlllllII).append(" threw the following exception:")));
                lIIllIIIllllIll.printStackTrace();
            }
            {
                1 lIIllIIlIIIIIlI;
            }
        };
        Native.loadNativeDispatchLibrary();
        if (!Native.isCompatibleVersion("5.1.0", Native.getNativeVersion())) {
            String llllllllllllllllIllIIlIIIIIIllll = System.getProperty("line.separator");
            throw new Error(String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIIIIIIllll).append(llllllllllllllllIllIIlIIIIIIllll).append("There is an incompatible JNA native library installed on this system").append(llllllllllllllllIllIIlIIIIIIllll).append("Expected: ").append("5.1.0").append(llllllllllllllllIllIIlIIIIIIllll).append("Found:    ").append(Native.getNativeVersion()).append(llllllllllllllllIllIIlIIIIIIllll).append(jnidispatchPath != null ? String.valueOf(new StringBuilder().append("(at ").append(jnidispatchPath).append(")")) : System.getProperty("java.library.path")).append(".").append(llllllllllllllllIllIIlIIIIIIllll).append("To resolve this issue you may do one of the following:").append(llllllllllllllllIllIIlIIIIIIllll).append(" - remove or uninstall the offending library").append(llllllllllllllllIllIIlIIIIIIllll).append(" - set the system property jna.nosys=true").append(llllllllllllllllIllIIlIIIIIIllll).append(" - set jna.boot.library.path to include the path to the version of the ").append(llllllllllllllllIllIIlIIIIIIllll).append("   jnidispatch library included with the JNA jar file you are using").append(llllllllllllllllIllIIlIIIIIIllll)));
        }
        POINTER_SIZE = Native.sizeof(0);
        LONG_SIZE = Native.sizeof(1);
        WCHAR_SIZE = Native.sizeof(2);
        SIZE_T_SIZE = Native.sizeof(3);
        BOOL_SIZE = Native.sizeof(4);
        Native.initIDs();
        if (Boolean.getBoolean("jna.protected")) {
            Native.setProtected(true);
        }
        MAX_ALIGNMENT = Platform.isSPARC() || Platform.isWindows() || Platform.isLinux() && (Platform.isARM() || Platform.isPPC()) || Platform.isAIX() || Platform.isAndroid() ? 8 : LONG_SIZE;
        MAX_PADDING = Platform.isMac() && Platform.isPPC() ? 8 : MAX_ALIGNMENT;
        System.setProperty("jna.loaded", "true");
        finalizer = new Object(){
            {
                2 lIIlllllllIlllI;
            }

            protected void finalize() {
                Native.dispose();
            }
        };
        registeredClasses = new WeakHashMap();
        registeredLibraries = new WeakHashMap();
        nativeThreadTerminationFlag = new ThreadLocal<Memory>(){

            @Override
            protected Memory initialValue() {
                Memory llllllllllllllllIlllIlllIIIlllll = new Memory(4L);
                llllllllllllllllIlllIlllIIIlllll.clear();
                return llllllllllllllllIlllIlllIIIlllll;
            }
            {
                7 llllllllllllllllIlllIlllIIlIIIll;
            }
        };
        nativeThreads = Collections.synchronizedMap(new WeakHashMap());
    }

    public static TypeMapper getTypeMapper(Class<?> llllllllllllllllIllIIllIIIlllllI) {
        Map<String, Object> llllllllllllllllIllIIllIIIllllIl = Native.getLibraryOptions(llllllllllllllllIllIIllIIIlllllI);
        return (TypeMapper)llllllllllllllllIllIIllIIIllllIl.get("type-mapper");
    }

    public static /* bridge */ native /* synthetic */ int getLastError();

    static /* bridge */ native /* synthetic */ long getLong(Pointer var0, long var1, long var3);

    public static /* bridge */ native /* synthetic */ void setLastError(int var0);

    public static int getNativeSize(Class<?> llllllllllllllllIllIIlIlIllIIIII) {
        if (NativeMapped.class.isAssignableFrom(llllllllllllllllIllIIlIlIllIIIII)) {
            llllllllllllllllIllIIlIlIllIIIII = NativeMappedConverter.getInstance(llllllllllllllllIllIIlIlIllIIIII).nativeType();
        }
        if (llllllllllllllllIllIIlIlIllIIIII == Boolean.TYPE || llllllllllllllllIllIIlIlIllIIIII == Boolean.class) {
            return 4;
        }
        if (llllllllllllllllIllIIlIlIllIIIII == Byte.TYPE || llllllllllllllllIllIIlIlIllIIIII == Byte.class) {
            return 1;
        }
        if (llllllllllllllllIllIIlIlIllIIIII == Short.TYPE || llllllllllllllllIllIIlIlIllIIIII == Short.class) {
            return 2;
        }
        if (llllllllllllllllIllIIlIlIllIIIII == Character.TYPE || llllllllllllllllIllIIlIlIllIIIII == Character.class) {
            return WCHAR_SIZE;
        }
        if (llllllllllllllllIllIIlIlIllIIIII == Integer.TYPE || llllllllllllllllIllIIlIlIllIIIII == Integer.class) {
            return 4;
        }
        if (llllllllllllllllIllIIlIlIllIIIII == Long.TYPE || llllllllllllllllIllIIlIlIllIIIII == Long.class) {
            return 8;
        }
        if (llllllllllllllllIllIIlIlIllIIIII == Float.TYPE || llllllllllllllllIllIIlIlIllIIIII == Float.class) {
            return 4;
        }
        if (llllllllllllllllIllIIlIlIllIIIII == Double.TYPE || llllllllllllllllIllIIlIlIllIIIII == Double.class) {
            return 8;
        }
        if (Structure.class.isAssignableFrom(llllllllllllllllIllIIlIlIllIIIII)) {
            if (Structure.ByValue.class.isAssignableFrom(llllllllllllllllIllIIlIlIllIIIII)) {
                return Structure.size(llllllllllllllllIllIIlIlIllIIIII);
            }
            return POINTER_SIZE;
        }
        if (Pointer.class.isAssignableFrom(llllllllllllllllIllIIlIlIllIIIII) || Platform.HAS_BUFFERS && Buffers.isBuffer(llllllllllllllllIllIIlIlIllIIIII) || Callback.class.isAssignableFrom(llllllllllllllllIllIIlIlIllIIIII) || String.class == llllllllllllllllIllIIlIlIllIIIII || WString.class == llllllllllllllllIllIIlIlIllIIIII) {
            return POINTER_SIZE;
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Native size for type \"").append(llllllllllllllllIllIIlIlIllIIIII.getName()).append("\" is unknown")));
    }

    public static String getStringEncoding(Class<?> llllllllllllllllIllIIllIIIllIlII) {
        Map<String, Object> llllllllllllllllIllIIllIIIllIllI = Native.getLibraryOptions(llllllllllllllllIllIIllIIIllIlII);
        String llllllllllllllllIllIIllIIIllIlIl = (String)llllllllllllllllIllIIllIIIllIllI.get("string-encoding");
        return llllllllllllllllIllIIllIIIllIlIl != null ? llllllllllllllllIllIIllIIIllIlIl : Native.getDefaultStringEncoding();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Class<?> findEnclosingLibraryClass(Class<?> llllllllllllllllIllIIllIIllIlIIl) {
        Class<?> llllllllllllllllIllIIllIIllIlIII;
        Class<?> llllllllllllllllIllIIllIIllIIlll;
        if (llllllllllllllllIllIIllIIllIlIIl == null) {
            return null;
        }
        Map<Class<?>, Reference<?>> llllllllllllllllIllIIllIIllIIlIl = libraries;
        synchronized (llllllllllllllllIllIIllIIllIIlIl) {
            if (typeOptions.containsKey(llllllllllllllllIllIIllIIllIlIIl)) {
                Map<String, Object> llllllllllllllllIllIIllIIllIlIll = typeOptions.get(llllllllllllllllIllIIllIIllIlIIl);
                Class llllllllllllllllIllIIllIIllIlIlI = (Class)llllllllllllllllIllIIllIIllIlIll.get("enclosing-library");
                if (llllllllllllllllIllIIllIIllIlIlI != null) {
                    return llllllllllllllllIllIIllIIllIlIlI;
                }
                return llllllllllllllllIllIIllIIllIlIIl;
            }
        }
        if (Library.class.isAssignableFrom(llllllllllllllllIllIIllIIllIlIIl)) {
            return llllllllllllllllIllIIllIIllIlIIl;
        }
        if (Callback.class.isAssignableFrom(llllllllllllllllIllIIllIIllIlIIl)) {
            llllllllllllllllIllIIllIIllIlIIl = CallbackReference.findCallbackClass(llllllllllllllllIllIIllIIllIlIIl);
        }
        if ((llllllllllllllllIllIIllIIllIIlll = Native.findEnclosingLibraryClass(llllllllllllllllIllIIllIIllIlIII = llllllllllllllllIllIIllIIllIlIIl.getDeclaringClass())) != null) {
            return llllllllllllllllIllIIllIIllIIlll;
        }
        return Native.findEnclosingLibraryClass(llllllllllllllllIllIIllIIllIlIIl.getSuperclass());
    }

    @Deprecated
    public static float parseVersion(String llllllllllllllllIllIIlllIIIIlIlI) {
        return Float.parseFloat(llllllllllllllllIllIIlllIIIIlIlI.substring(0, llllllllllllllllIllIIlllIIIIlIlI.lastIndexOf(".")));
    }

    static /* bridge */ native /* synthetic */ void write(Pointer var0, long var1, long var3, float[] var5, int var6, int var7);

    @Deprecated
    public static /* bridge */ native /* synthetic */ ByteBuffer getDirectByteBuffer(long var0, long var2);

    private static int getConversion(Class<?> llllllllllllllllIllIIlIlIIIIIIII, TypeMapper llllllllllllllllIllIIlIIllllllIl) {
        if (llllllllllllllllIllIIlIlIIIIIIII == Boolean.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Boolean.TYPE;
        } else if (llllllllllllllllIllIIlIlIIIIIIII == Byte.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Byte.TYPE;
        } else if (llllllllllllllllIllIIlIlIIIIIIII == Short.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Short.TYPE;
        } else if (llllllllllllllllIllIIlIlIIIIIIII == Character.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Character.TYPE;
        } else if (llllllllllllllllIllIIlIlIIIIIIII == Integer.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Integer.TYPE;
        } else if (llllllllllllllllIllIIlIlIIIIIIII == Long.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Long.TYPE;
        } else if (llllllllllllllllIllIIlIlIIIIIIII == Float.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Float.TYPE;
        } else if (llllllllllllllllIllIIlIlIIIIIIII == Double.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Double.TYPE;
        } else if (llllllllllllllllIllIIlIlIIIIIIII == Void.class) {
            llllllllllllllllIllIIlIlIIIIIIII = Void.TYPE;
        }
        if (llllllllllllllllIllIIlIIllllllIl != null) {
            FromNativeConverter llllllllllllllllIllIIlIlIIIIIIll = llllllllllllllllIllIIlIIllllllIl.getFromNativeConverter(llllllllllllllllIllIIlIlIIIIIIII);
            ToNativeConverter llllllllllllllllIllIIlIlIIIIIIlI = llllllllllllllllIllIIlIIllllllIl.getToNativeConverter(llllllllllllllllIllIIlIlIIIIIIII);
            if (llllllllllllllllIllIIlIlIIIIIIll != null) {
                Class<?> llllllllllllllllIllIIlIlIIIIIlIl = llllllllllllllllIllIIlIlIIIIIIll.nativeType();
                if (llllllllllllllllIllIIlIlIIIIIlIl == String.class) {
                    return 24;
                }
                if (llllllllllllllllIllIIlIlIIIIIlIl == WString.class) {
                    return 25;
                }
                return 23;
            }
            if (llllllllllllllllIllIIlIlIIIIIIlI != null) {
                Class<?> llllllllllllllllIllIIlIlIIIIIlII = llllllllllllllllIllIIlIlIIIIIIlI.nativeType();
                if (llllllllllllllllIllIIlIlIIIIIlII == String.class) {
                    return 24;
                }
                if (llllllllllllllllIllIIlIlIIIIIlII == WString.class) {
                    return 25;
                }
                return 23;
            }
        }
        if (Pointer.class.isAssignableFrom(llllllllllllllllIllIIlIlIIIIIIII)) {
            return 1;
        }
        if (String.class == llllllllllllllllIllIIlIlIIIIIIII) {
            return 2;
        }
        if (WString.class.isAssignableFrom(llllllllllllllllIllIIlIlIIIIIIII)) {
            return 20;
        }
        if (Platform.HAS_BUFFERS && Buffers.isBuffer(llllllllllllllllIllIIlIlIIIIIIII)) {
            return 5;
        }
        if (Structure.class.isAssignableFrom(llllllllllllllllIllIIlIlIIIIIIII)) {
            if (Structure.ByValue.class.isAssignableFrom(llllllllllllllllIllIIlIlIIIIIIII)) {
                return 4;
            }
            return 3;
        }
        if (llllllllllllllllIllIIlIlIIIIIIII.isArray()) {
            switch (llllllllllllllllIllIIlIlIIIIIIII.getName().charAt(1)) {
                case 'Z': {
                    return 13;
                }
                case 'B': {
                    return 6;
                }
                case 'S': {
                    return 7;
                }
                case 'C': {
                    return 8;
                }
                case 'I': {
                    return 9;
                }
                case 'J': {
                    return 10;
                }
                case 'F': {
                    return 11;
                }
                case 'D': {
                    return 12;
                }
            }
        }
        if (llllllllllllllllIllIIlIlIIIIIIII.isPrimitive()) {
            return llllllllllllllllIllIIlIlIIIIIIII == Boolean.TYPE ? 14 : 0;
        }
        if (Callback.class.isAssignableFrom(llllllllllllllllIllIIlIlIIIIIIII)) {
            return 15;
        }
        if (IntegerType.class.isAssignableFrom(llllllllllllllllIllIIlIlIIIIIIII)) {
            return 21;
        }
        if (PointerType.class.isAssignableFrom(llllllllllllllllIllIIlIlIIIIIIII)) {
            return 22;
        }
        if (NativeMapped.class.isAssignableFrom(llllllllllllllllIllIIlIlIIIIIIII)) {
            Class<?> llllllllllllllllIllIIlIlIIIIIIIl = NativeMappedConverter.getInstance(llllllllllllllllIllIIlIlIIIIIIII).nativeType();
            if (llllllllllllllllIllIIlIlIIIIIIIl == String.class) {
                return 18;
            }
            if (llllllllllllllllIllIIlIlIIIIIIIl == WString.class) {
                return 19;
            }
            return 17;
        }
        return -1;
    }

    public static long getComponentID(Component llllllllllllllllIllIIllIlllIIlIl) throws HeadlessException {
        return AWT.getComponentID(llllllllllllllllIllIIllIlllIIlIl);
    }

    static /* bridge */ native /* synthetic */ int invokeInt(Function var0, long var1, int var3, Object[] var4);

    static /* bridge */ native /* synthetic */ void write(Pointer var0, long var1, long var3, int[] var5, int var6, int var7);

    static /* bridge */ native /* synthetic */ long invokePointer(Function var0, long var1, int var3, Object[] var4);

    static /* bridge */ native /* synthetic */ void read(Pointer var0, long var1, long var3, double[] var5, int var6, int var7);

    public static byte[] toByteArray(String llllllllllllllllIllIIllIIIIllllI) {
        return Native.toByteArray(llllllllllllllllIllIIllIIIIllllI, Native.getDefaultStringEncoding());
    }

    public static long getWindowID(Window llllllllllllllllIllIIllIlllIlIII) throws HeadlessException {
        return AWT.getWindowID(llllllllllllllllIllIIllIlllIlIII);
    }

    static /* bridge */ native /* synthetic */ void setDouble(Pointer var0, long var1, long var3, double var5);

    static /* bridge */ native /* synthetic */ void close(long var0);

    public static String toString(byte[] llllllllllllllllIllIIllIllIIlIll, String llllllllllllllllIllIIllIllIIlIlI) {
        int llllllllllllllllIllIIllIllIIllII = llllllllllllllllIllIIllIllIIlIll.length;
        for (int llllllllllllllllIllIIllIllIlIIII = 0; llllllllllllllllIllIIllIllIlIIII < llllllllllllllllIllIIllIllIIllII; ++llllllllllllllllIllIIllIllIlIIII) {
            if (llllllllllllllllIllIIllIllIIlIll[llllllllllllllllIllIIllIllIlIIII] != 0) continue;
            llllllllllllllllIllIIllIllIIllII = llllllllllllllllIllIIllIllIlIIII;
            break;
        }
        if (llllllllllllllllIllIIllIllIIllII == 0) {
            return "";
        }
        if (llllllllllllllllIllIIllIllIIlIlI != null) {
            try {
                return new String(llllllllllllllllIllIIllIllIIlIll, 0, llllllllllllllllIllIIllIllIIllII, llllllllllllllllIllIIllIllIIlIlI);
            }
            catch (UnsupportedEncodingException llllllllllllllllIllIIllIllIIllll) {
                System.err.println(String.valueOf(new StringBuilder().append("JNA Warning: Encoding '").append(llllllllllllllllIllIIllIllIIlIlI).append("' is unsupported")));
            }
        }
        System.err.println(String.valueOf(new StringBuilder().append("JNA Warning: Decoding with fallback ").append(System.getProperty("file.encoding"))));
        return new String(llllllllllllllllIllIIllIllIIlIll, 0, llllllllllllllllIllIIllIllIIllII);
    }

    private static void loadNativeDispatchLibrary() {
        if (!Boolean.getBoolean("jna.nounpack")) {
            try {
                Native.removeTemporaryFiles();
            }
            catch (IOException llllllllllllllllIllIIlIllllllllI) {
                System.err.println(String.valueOf(new StringBuilder().append("JNA Warning: IOException removing temporary files: ").append(llllllllllllllllIllIIlIllllllllI.getMessage())));
            }
        }
        String llllllllllllllllIllIIlIlllllIlII = System.getProperty("jna.boot.library.name", "jnidispatch");
        String llllllllllllllllIllIIlIlllllIIll = System.getProperty("jna.boot.library.path");
        if (llllllllllllllllIllIIlIlllllIIll != null) {
            StringTokenizer llllllllllllllllIllIIlIlllllIlIl = new StringTokenizer(llllllllllllllllIllIIlIlllllIIll, File.pathSeparator);
            while (llllllllllllllllIllIIlIlllllIlIl.hasMoreTokens()) {
                String llllllllllllllllIllIIlIllllllIIl;
                String llllllllllllllllIllIIlIllllllIlI;
                String llllllllllllllllIllIIlIllllllIII = llllllllllllllllIllIIlIlllllIlIl.nextToken();
                File llllllllllllllllIllIIlIlllllIlll = new File(new File(llllllllllllllllIllIIlIllllllIII), System.mapLibraryName(llllllllllllllllIllIIlIlllllIlII).replace(".dylib", ".jnilib"));
                String llllllllllllllllIllIIlIlllllIllI = llllllllllllllllIllIIlIlllllIlll.getAbsolutePath();
                if (DEBUG_JNA_LOAD) {
                    System.out.println(String.valueOf(new StringBuilder().append("Looking in ").append(llllllllllllllllIllIIlIlllllIllI)));
                }
                if (llllllllllllllllIllIIlIlllllIlll.exists()) {
                    try {
                        if (DEBUG_JNA_LOAD) {
                            System.out.println(String.valueOf(new StringBuilder().append("Trying ").append(llllllllllllllllIllIIlIlllllIllI)));
                        }
                        System.setProperty("jnidispatch.path", llllllllllllllllIllIIlIlllllIllI);
                        System.load(llllllllllllllllIllIIlIlllllIllI);
                        jnidispatchPath = llllllllllllllllIllIIlIlllllIllI;
                        if (DEBUG_JNA_LOAD) {
                            System.out.println(String.valueOf(new StringBuilder().append("Found jnidispatch at ").append(llllllllllllllllIllIIlIlllllIllI)));
                        }
                        return;
                    }
                    catch (UnsatisfiedLinkError llllllllllllllllIllIIlIllllIllII) {
                        // empty catch block
                    }
                }
                if (!Platform.isMac()) continue;
                if (llllllllllllllllIllIIlIlllllIllI.endsWith("dylib")) {
                    String llllllllllllllllIllIIlIlllllllIl = "dylib";
                    String llllllllllllllllIllIIlIlllllllII = "jnilib";
                } else {
                    llllllllllllllllIllIIlIllllllIlI = "jnilib";
                    llllllllllllllllIllIIlIllllllIIl = "dylib";
                }
                llllllllllllllllIllIIlIlllllIllI = String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIlllllIllI.substring(0, llllllllllllllllIllIIlIlllllIllI.lastIndexOf(llllllllllllllllIllIIlIllllllIlI))).append(llllllllllllllllIllIIlIllllllIIl));
                if (DEBUG_JNA_LOAD) {
                    System.out.println(String.valueOf(new StringBuilder().append("Looking in ").append(llllllllllllllllIllIIlIlllllIllI)));
                }
                if (!new File(llllllllllllllllIllIIlIlllllIllI).exists()) continue;
                try {
                    if (DEBUG_JNA_LOAD) {
                        System.out.println(String.valueOf(new StringBuilder().append("Trying ").append(llllllllllllllllIllIIlIlllllIllI)));
                    }
                    System.setProperty("jnidispatch.path", llllllllllllllllIllIIlIlllllIllI);
                    System.load(llllllllllllllllIllIIlIlllllIllI);
                    jnidispatchPath = llllllllllllllllIllIIlIlllllIllI;
                    if (DEBUG_JNA_LOAD) {
                        System.out.println(String.valueOf(new StringBuilder().append("Found jnidispatch at ").append(llllllllllllllllIllIIlIlllllIllI)));
                    }
                    return;
                }
                catch (UnsatisfiedLinkError llllllllllllllllIllIIlIllllllIll) {
                    System.err.println(String.valueOf(new StringBuilder().append("File found at ").append(llllllllllllllllIllIIlIlllllIllI).append(" but not loadable: ").append(llllllllllllllllIllIIlIllllllIll.getMessage())));
                }
            }
        }
        if (!Boolean.getBoolean("jna.nosys")) {
            try {
                if (DEBUG_JNA_LOAD) {
                    System.out.println(String.valueOf(new StringBuilder().append("Trying (via loadLibrary) ").append(llllllllllllllllIllIIlIlllllIlII)));
                }
                System.loadLibrary(llllllllllllllllIllIIlIlllllIlII);
                if (DEBUG_JNA_LOAD) {
                    System.out.println("Found jnidispatch on system path");
                }
                return;
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                // empty catch block
            }
        }
        if (Boolean.getBoolean("jna.noclasspath")) {
            throw new UnsatisfiedLinkError("Unable to locate JNA native support library");
        }
        Native.loadNativeDispatchLibraryFromClasspath();
    }

    public static synchronized /* bridge */ native /* synthetic */ void setProtected(boolean var0);

    static boolean isCompatibleVersion(String llllllllllllllllIllIIllIlllllIII, String llllllllllllllllIllIIllIllllllll) {
        String[] llllllllllllllllIllIIllIlllllllI = llllllllllllllllIllIIllIlllllIII.split("\\.");
        String[] llllllllllllllllIllIIllIllllllIl = llllllllllllllllIllIIllIllllllll.split("\\.");
        if (llllllllllllllllIllIIllIlllllllI.length < 3 || llllllllllllllllIllIIllIllllllIl.length < 3) {
            return false;
        }
        int llllllllllllllllIllIIllIllllllII = Integer.parseInt(llllllllllllllllIllIIllIlllllllI[0]);
        int llllllllllllllllIllIIllIlllllIll = Integer.parseInt(llllllllllllllllIllIIllIllllllIl[0]);
        int llllllllllllllllIllIIllIlllllIlI = Integer.parseInt(llllllllllllllllIllIIllIlllllllI[1]);
        int llllllllllllllllIllIIllIlllllIIl = Integer.parseInt(llllllllllllllllIllIIllIllllllIl[1]);
        if (llllllllllllllllIllIIllIllllllII != llllllllllllllllIllIIllIlllllIll) {
            return false;
        }
        return llllllllllllllllIllIIllIlllllIlI <= llllllllllllllllIllIIllIlllllIIl;
    }

    private static /* bridge */ native /* synthetic */ String getAPIChecksum();

    public static synchronized /* bridge */ native /* synthetic */ boolean isProtected();

    static /* bridge */ native /* synthetic */ void read(Pointer var0, long var1, long var3, long[] var5, int var6, int var7);

    public static Library synchronizedLibrary(final Library llllllllllllllllIllIIlIllIlIllII) {
        Class<?> llllllllllllllllIllIIlIllIlIlIll = llllllllllllllllIllIIlIllIlIllII.getClass();
        if (!Proxy.isProxyClass(llllllllllllllllIllIIlIllIlIlIll)) {
            throw new IllegalArgumentException("Library must be a proxy class");
        }
        InvocationHandler llllllllllllllllIllIIlIllIlIlIlI = Proxy.getInvocationHandler(llllllllllllllllIllIIlIllIlIllII);
        if (!(llllllllllllllllIllIIlIllIlIlIlI instanceof Library.Handler)) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Unrecognized proxy handler: ").append(llllllllllllllllIllIIlIllIlIlIlI)));
        }
        final Library.Handler llllllllllllllllIllIIlIllIlIlIIl = (Library.Handler)llllllllllllllllIllIIlIllIlIlIlI;
        InvocationHandler llllllllllllllllIllIIlIllIlIlIII = new InvocationHandler(){
            {
                3 lllllllllllllllllIIIIlIIIlllIlIl;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public Object invoke(Object lllllllllllllllllIIIIlIIIllIlIll, Method lllllllllllllllllIIIIlIIIllIlIlI, Object[] lllllllllllllllllIIIIlIIIllIlIIl) throws Throwable {
                3 lllllllllllllllllIIIIlIIIllIllII;
                NativeLibrary lllllllllllllllllIIIIlIIIllIIlIl = lllllllllllllllllIIIIlIIIllIllII.llllllllllllllllIllIIlIllIlIlIIl.getNativeLibrary();
                synchronized (lllllllllllllllllIIIIlIIIllIIlIl) {
                    return lllllllllllllllllIIIIlIIIllIllII.llllllllllllllllIllIIlIllIlIlIIl.invoke(lllllllllllllllllIIIIlIIIllIllII.llllllllllllllllIllIIlIllIlIllII, lllllllllllllllllIIIIlIIIllIlIlI, lllllllllllllllllIIIIlIIIllIlIIl);
                }
            }
        };
        return (Library)Proxy.newProxyInstance(llllllllllllllllIllIIlIllIlIlIll.getClassLoader(), llllllllllllllllIllIIlIllIlIlIll.getInterfaces(), llllllllllllllllIllIIlIllIlIlIII);
    }

    static /* bridge */ native /* synthetic */ byte getByte(Pointer var0, long var1, long var3);

    public static List<String> toStringList(char[] llllllllllllllllIllIIllIlIllllII) {
        return Native.toStringList(llllllllllllllllIllIIllIlIllllII, 0, llllllllllllllllIllIIllIlIllllII.length);
    }

    static /* bridge */ native /* synthetic */ void write(Pointer var0, long var1, long var3, char[] var5, int var6, int var7);

    static /* bridge */ native /* synthetic */ void setByte(Pointer var0, long var1, long var3, byte var5);

    static synchronized /* bridge */ native /* synthetic */ long createNativeCallback(Callback var0, Method var1, Class<?>[] var2, Class<?> var3, int var4, int var5, String var6);

    public static void register(NativeLibrary llllllllllllllllIllIIlIlIlIlIIIl) {
        Native.register(Native.findDirectMappedClass(Native.getCallingClass()), llllllllllllllllIllIIlIlIlIlIIIl);
    }

    static /* bridge */ native /* synthetic */ float getFloat(Pointer var0, long var1, long var3);

    private static Object lookupField(Class<?> llllllllllllllllIllIIllIIlIIIlll, String llllllllllllllllIllIIllIIlIIIIll, Class<?> llllllllllllllllIllIIllIIlIIIlIl) {
        try {
            Field llllllllllllllllIllIIllIIlIIlIlI = llllllllllllllllIllIIllIIlIIIlll.getField(llllllllllllllllIllIIllIIlIIIIll);
            llllllllllllllllIllIIllIIlIIlIlI.setAccessible(true);
            return llllllllllllllllIllIIllIIlIIlIlI.get(null);
        }
        catch (NoSuchFieldException llllllllllllllllIllIIllIIlIIlIIl) {
            return null;
        }
        catch (Exception llllllllllllllllIllIIllIIlIIlIII) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(llllllllllllllllIllIIllIIlIIIIll).append(" must be a public field of type ").append(llllllllllllllllIllIIllIIlIIIlIl.getName()).append(" (").append(llllllllllllllllIllIIllIIlIIlIII).append("): ").append(llllllllllllllllIllIIllIIlIIIlll)));
        }
    }

    public static String getDefaultStringEncoding() {
        return System.getProperty("jna.encoding", DEFAULT_ENCODING);
    }

    static /* bridge */ native /* synthetic */ long findSymbol(long var0, String var2);

    static /* bridge */ native /* synthetic */ void setChar(Pointer var0, long var1, long var3, char var5);

    public static void setCallbackThreadInitializer(Callback llllllllllllllllIllIIlIlIIlllIII, CallbackThreadInitializer llllllllllllllllIllIIlIlIIllIlll) {
        CallbackReference.setCallbackThreadInitializer(llllllllllllllllIllIIlIlIIlllIII, llllllllllllllllIllIIlIlIIllIlll);
    }

    public static String toString(byte[] llllllllllllllllIllIIllIllIlIllI) {
        return Native.toString(llllllllllllllllIllIIllIllIlIllI, Native.getDefaultStringEncoding());
    }

    private static Object toNative(ToNativeConverter llllllllllllllllIllIIlIIIllIlIIl, Object llllllllllllllllIllIIlIIIllIIllI) {
        return llllllllllllllllIllIIlIIIllIlIIl.toNative(llllllllllllllllIllIIlIIIllIIllI, new ToNativeContext());
    }

    public static /* bridge */ native /* synthetic */ void ffi_call(long var0, long var2, long var4, long var6);

    public static <T> T loadLibrary(Class<T> llllllllllllllllIllIIllIlIlIIIIl) {
        return Native.loadLibrary(null, llllllllllllllllIllIIllIlIlIIIIl);
    }

    static /* bridge */ native /* synthetic */ char getChar(Pointer var0, long var1, long var3);

    private static /* bridge */ native /* synthetic */ void invokeStructure(Function var0, long var1, int var3, Object[] var4, long var5, long var7);

    static void removeTemporaryFiles() throws IOException {
        File llllllllllllllllIllIIlIlIllllIIl = Native.getTempDir();
        FilenameFilter llllllllllllllllIllIIlIlIllllIII = new FilenameFilter(){
            {
                5 llllllllllllllllIllllIlllIllIIIl;
            }

            @Override
            public boolean accept(File llllllllllllllllIllllIlllIlIlllI, String llllllllllllllllIllllIlllIlIllII) {
                return llllllllllllllllIllllIlllIlIllII.endsWith(".x") && llllllllllllllllIllllIlllIlIllII.startsWith("jna");
            }
        };
        File[] llllllllllllllllIllIIlIlIlllIlll = llllllllllllllllIllIIlIlIllllIIl.listFiles(llllllllllllllllIllIIlIlIllllIII);
        for (int llllllllllllllllIllIIlIlIllllIlI = 0; llllllllllllllllIllIIlIlIlllIlll != null && llllllllllllllllIllIIlIlIllllIlI < llllllllllllllllIllIIlIlIlllIlll.length; ++llllllllllllllllIllIIlIlIllllIlI) {
            File llllllllllllllllIllIIlIlIlllllIl = llllllllllllllllIllIIlIlIlllIlll[llllllllllllllllIllIIlIlIllllIlI];
            String llllllllllllllllIllIIlIlIlllllII = llllllllllllllllIllIIlIlIlllllIl.getName();
            llllllllllllllllIllIIlIlIlllllII = llllllllllllllllIllIIlIlIlllllII.substring(0, llllllllllllllllIllIIlIlIlllllII.length() - 2);
            File llllllllllllllllIllIIlIlIllllIll = new File(llllllllllllllllIllIIlIlIlllllIl.getParentFile(), llllllllllllllllIllIIlIlIlllllII);
            if (llllllllllllllllIllIIlIlIllllIll.exists() && !llllllllllllllllIllIIlIlIllllIll.delete()) continue;
            llllllllllllllllIllIIlIlIlllllIl.delete();
        }
    }

    static /* bridge */ native /* synthetic */ Object invokeObject(Function var0, long var1, int var3, Object[] var4);

    public static void detach(boolean llllllllllllllllIllIIlIIIIIllIII) {
        Thread llllllllllllllllIllIIlIIIIIlIlll = Thread.currentThread();
        if (llllllllllllllllIllIIlIIIIIllIII) {
            nativeThreads.remove(llllllllllllllllIllIIlIIIIIlIlll);
            Pointer llllllllllllllllIllIIlIIIIIllIlI = nativeThreadTerminationFlag.get();
            Native.setDetachState(true, 0L);
        } else if (!nativeThreads.containsKey(llllllllllllllllIllIIlIIIIIlIlll)) {
            Pointer llllllllllllllllIllIIlIIIIIllIIl = nativeThreadTerminationFlag.get();
            nativeThreads.put(llllllllllllllllIllIIlIIIIIlIlll, llllllllllllllllIllIIlIIIIIllIIl);
            Native.setDetachState(false, llllllllllllllllIllIIlIIIIIllIIl.peer);
        }
    }

    static /* bridge */ native /* synthetic */ int initialize_ffi_type(long var0);

    public static /* bridge */ native /* synthetic */ long malloc(long var0);

    public static Pointer getComponentPointer(Component llllllllllllllllIllIIllIllIlllll) throws HeadlessException {
        return new Pointer(AWT.getComponentID(llllllllllllllllIllIIllIllIlllll));
    }

    static /* bridge */ native /* synthetic */ void read(Pointer var0, long var1, long var3, byte[] var5, int var6, int var7);

    public static int getStructureAlignment(Class<?> llllllllllllllllIllIIllIIIlIllIl) {
        Integer llllllllllllllllIllIIllIIIlIlllI = (Integer)Native.getLibraryOptions(llllllllllllllllIllIIllIIIlIllIl).get("structure-alignment");
        return llllllllllllllllIllIIllIIIlIlllI == null ? 0 : llllllllllllllllIllIIllIIIlIlllI;
    }

    static /* bridge */ native /* synthetic */ long open(String var0, int var1);

    private static /* bridge */ native /* synthetic */ int sizeof(int var0);

    public static <T> T loadLibrary(String llllllllllllllllIllIIllIlIIIIlll, Class<T> llllllllllllllllIllIIllIlIIIIllI, Map<String, ?> llllllllllllllllIllIIllIlIIIIlIl) {
        if (!Library.class.isAssignableFrom(llllllllllllllllIllIIllIlIIIIllI)) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Interface (").append(llllllllllllllllIllIIllIlIIIIllI.getSimpleName()).append(") of library=").append(llllllllllllllllIllIIllIlIIIIlll).append(" does not extend ").append(Library.class.getSimpleName())));
        }
        Library.Handler llllllllllllllllIllIIllIlIIIlIlI = new Library.Handler(llllllllllllllllIllIIllIlIIIIlll, llllllllllllllllIllIIllIlIIIIllI, llllllllllllllllIllIIllIlIIIIlIl);
        ClassLoader llllllllllllllllIllIIllIlIIIlIIl = llllllllllllllllIllIIllIlIIIIllI.getClassLoader();
        Object llllllllllllllllIllIIllIlIIIlIII = Proxy.newProxyInstance(llllllllllllllllIllIIllIlIIIlIIl, new Class[]{llllllllllllllllIllIIllIlIIIIllI}, (InvocationHandler)llllllllllllllllIllIIllIlIIIlIlI);
        Native.cacheOptions(llllllllllllllllIllIIllIlIIIIllI, llllllllllllllllIllIIllIlIIIIlIl, llllllllllllllllIllIIllIlIIIlIII);
        return llllllllllllllllIllIIllIlIIIIllI.cast(llllllllllllllllIllIIllIlIIIlIII);
    }

    private static NativeMapped fromNative(Method llllllllllllllllIllIIlIIIlllIlII, Object llllllllllllllllIllIIlIIIlllIIII) {
        Class<?> llllllllllllllllIllIIlIIIlllIIlI = llllllllllllllllIllIIlIIIlllIlII.getReturnType();
        return (NativeMapped)NativeMappedConverter.getInstance(llllllllllllllllIllIIlIIIlllIIlI).fromNative(llllllllllllllllIllIIlIIIlllIIII, new MethodResultContext(llllllllllllllllIllIIlIIIlllIIlI, null, null, llllllllllllllllIllIIlIIIlllIlII));
    }

    static String replace(String llllllllllllllllIllIIlIlIIIlIIll, String llllllllllllllllIllIIlIlIIIlIIlI, String llllllllllllllllIllIIlIlIIIIllIl) {
        StringBuilder llllllllllllllllIllIIlIlIIIlIIII = new StringBuilder();
        while (true) {
            int llllllllllllllllIllIIlIlIIIlIlII;
            if ((llllllllllllllllIllIIlIlIIIlIlII = llllllllllllllllIllIIlIlIIIIllIl.indexOf(llllllllllllllllIllIIlIlIIIlIIll)) == -1) break;
            llllllllllllllllIllIIlIlIIIlIIII.append(llllllllllllllllIllIIlIlIIIIllIl.substring(0, llllllllllllllllIllIIlIlIIIlIlII));
            llllllllllllllllIllIIlIlIIIlIIII.append(llllllllllllllllIllIIlIlIIIlIIlI);
            llllllllllllllllIllIIlIlIIIIllIl = llllllllllllllllIllIIlIlIIIIllIl.substring(llllllllllllllllIllIIlIlIIIlIlII + llllllllllllllllIllIIlIlIIIlIIll.length());
        }
        llllllllllllllllIllIIlIlIIIlIIII.append(llllllllllllllllIllIIlIlIIIIllIl);
        return String.valueOf(llllllllllllllllIllIIlIlIIIlIIII);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean registered(Class<?> llllllllllllllllIllIIlIlIIIlllll) {
        Map<Class<?>, long[]> llllllllllllllllIllIIlIlIIIllllI = registeredClasses;
        synchronized (llllllllllllllllIllIIlIlIIIllllI) {
            return registeredClasses.containsKey(llllllllllllllllIllIIlIlIIIlllll);
        }
    }

    public static <T> T loadLibrary(Class<T> llllllllllllllllIllIIllIlIIllIll, Map<String, ?> llllllllllllllllIllIIllIlIIlllII) {
        return Native.loadLibrary(null, llllllllllllllllIllIIllIlIIllIll, llllllllllllllllIllIIllIlIIlllII);
    }

    private static void loadNativeDispatchLibraryFromClasspath() {
        try {
            String llllllllllllllllIllIIlIllllIIlll = String.valueOf(new StringBuilder().append("/com/sun/jna/").append(Platform.RESOURCE_PREFIX).append("/").append(System.mapLibraryName("jnidispatch").replace(".dylib", ".jnilib")));
            File llllllllllllllllIllIIlIllllIIllI = Native.extractFromResourcePath(llllllllllllllllIllIIlIllllIIlll, Native.class.getClassLoader());
            if (llllllllllllllllIllIIlIllllIIllI == null && llllllllllllllllIllIIlIllllIIllI == null) {
                throw new UnsatisfiedLinkError("Could not find JNA native support");
            }
            if (DEBUG_JNA_LOAD) {
                System.out.println(String.valueOf(new StringBuilder().append("Trying ").append(llllllllllllllllIllIIlIllllIIllI.getAbsolutePath())));
            }
            System.setProperty("jnidispatch.path", llllllllllllllllIllIIlIllllIIllI.getAbsolutePath());
            System.load(llllllllllllllllIllIIlIllllIIllI.getAbsolutePath());
            jnidispatchPath = llllllllllllllllIllIIlIllllIIllI.getAbsolutePath();
            if (DEBUG_JNA_LOAD) {
                System.out.println(String.valueOf(new StringBuilder().append("Found jnidispatch at ").append(jnidispatchPath)));
            }
            if (Native.isUnpacked(llllllllllllllllIllIIlIllllIIllI) && !Boolean.getBoolean("jnidispatch.preserve")) {
                Native.deleteLibrary(llllllllllllllllIllIIlIllllIIllI);
            }
        }
        catch (IOException llllllllllllllllIllIIlIllllIIlIl) {
            throw new UnsatisfiedLinkError(llllllllllllllllIllIIlIllllIIlIl.getMessage());
        }
    }

    private static /* bridge */ native /* synthetic */ long _getPointer(long var0);

    static /* bridge */ native /* synthetic */ String getWideString(Pointer var0, long var1, long var3);

    static /* bridge */ native /* synthetic */ void read(Pointer var0, long var1, long var3, float[] var5, int var6, int var7);

    static /* bridge */ native /* synthetic */ void write(Pointer var0, long var1, long var3, long[] var5, int var6, int var7);

    public static void main(String[] llllllllllllllllIllIIlIIIlIlIllI) {
        String llllllllllllllllIllIIlIIIlIlIIII;
        String llllllllllllllllIllIIlIIIlIlIIIl;
        String llllllllllllllllIllIIlIIIlIlIlIl = "Java Native Access (JNA)";
        String llllllllllllllllIllIIlIIIlIlIlII = "4.4.0";
        String llllllllllllllllIllIIlIIIlIlIIll = "4.4.0 (package information missing)";
        Package llllllllllllllllIllIIlIIIlIlIIlI = Native.class.getPackage();
        String string = llllllllllllllllIllIIlIIIlIlIIIl = llllllllllllllllIllIIlIIIlIlIIlI != null ? llllllllllllllllIllIIlIIIlIlIIlI.getSpecificationTitle() : "Java Native Access (JNA)";
        if (llllllllllllllllIllIIlIIIlIlIIIl == null) {
            llllllllllllllllIllIIlIIIlIlIIIl = "Java Native Access (JNA)";
        }
        String string2 = llllllllllllllllIllIIlIIIlIlIIII = llllllllllllllllIllIIlIIIlIlIIlI != null ? llllllllllllllllIllIIlIIIlIlIIlI.getSpecificationVersion() : "4.4.0";
        if (llllllllllllllllIllIIlIIIlIlIIII == null) {
            llllllllllllllllIllIIlIIIlIlIIII = "4.4.0";
        }
        llllllllllllllllIllIIlIIIlIlIIIl = String.valueOf(new StringBuilder().append(llllllllllllllllIllIIlIIIlIlIIIl).append(" API Version ").append(llllllllllllllllIllIIlIIIlIlIIII));
        System.out.println(llllllllllllllllIllIIlIIIlIlIIIl);
        String string3 = llllllllllllllllIllIIlIIIlIlIIII = llllllllllllllllIllIIlIIIlIlIIlI != null ? llllllllllllllllIllIIlIIIlIlIIlI.getImplementationVersion() : "4.4.0 (package information missing)";
        if (llllllllllllllllIllIIlIIIlIlIIII == null) {
            llllllllllllllllIllIIlIIIlIlIIII = "4.4.0 (package information missing)";
        }
        System.out.println(String.valueOf(new StringBuilder().append("Version: ").append(llllllllllllllllIllIIlIIIlIlIIII)));
        System.out.println(String.valueOf(new StringBuilder().append(" Native: ").append(Native.getNativeVersion()).append(" (").append(Native.getAPIChecksum()).append(")")));
        System.out.println(String.valueOf(new StringBuilder().append(" Prefix: ").append(Platform.RESOURCE_PREFIX)));
    }

    private static NativeMapped fromNative(Class<?> llllllllllllllllIllIIlIIIllllIll, Object llllllllllllllllIllIIlIIIllllIlI) {
        return (NativeMapped)NativeMappedConverter.getInstance(llllllllllllllllIllIIlIIIllllIll).fromNative(llllllllllllllllIllIIlIIIllllIlI, new FromNativeContext(llllllllllllllllIllIIlIIIllllIll));
    }

    static boolean deleteLibrary(File llllllllllllllllIllIIllIlllIlllI) {
        if (llllllllllllllllIllIIllIlllIlllI.delete()) {
            return true;
        }
        Native.markTemporaryFile(llllllllllllllllIllIIllIlllIlllI);
        return false;
    }

    private static Class<?> nativeType(Class<?> llllllllllllllllIllIIlIIIllIllII) {
        return NativeMappedConverter.getInstance(llllllllllllllllIllIIlIIIllIllII).nativeType();
    }

    static Pointer getTerminationFlag(Thread llllllllllllllllIllIIlIIIIIlIIIl) {
        return nativeThreads.get(llllllllllllllllIllIIlIIIIIlIIIl);
    }

    private static class Buffers {
        static boolean isBuffer(Class<?> llllllllllllllllllIlllllIllIIIlI) {
            return Buffer.class.isAssignableFrom(llllllllllllllllllIlllllIllIIIlI);
        }

        private Buffers() {
            Buffers llllllllllllllllllIlllllIllIIllI;
        }
    }

    private static class AWT {
        private AWT() {
            AWT llllllllllllllllIlIllIIlIIIIllll;
        }

        static long getWindowID(Window llllllllllllllllIlIllIIlIIIIllII) throws HeadlessException {
            return AWT.getComponentID(llllllllllllllllIlIllIIlIIIIllII);
        }

        static long getComponentID(Object llllllllllllllllIlIllIIlIIIIIlll) throws HeadlessException {
            if (GraphicsEnvironment.isHeadless()) {
                throw new HeadlessException("No native windows when headless");
            }
            Component llllllllllllllllIlIllIIlIIIIlIII = (Component)llllllllllllllllIlIllIIlIIIIIlll;
            if (llllllllllllllllIlIllIIlIIIIlIII.isLightweight()) {
                throw new IllegalArgumentException("Component must be heavyweight");
            }
            if (!llllllllllllllllIlIllIIlIIIIlIII.isDisplayable()) {
                throw new IllegalStateException("Component must be displayable");
            }
            if (Platform.isX11() && System.getProperty("java.version").startsWith("1.4") && !llllllllllllllllIlIllIIlIIIIlIII.isVisible()) {
                throw new IllegalStateException("Component must be visible");
            }
            return Native.getWindowHandle0(llllllllllllllllIlIllIIlIIIIlIII);
        }
    }

    public static interface ffi_callback {
        public void invoke(long var1, long var3, long var5);
    }
}

