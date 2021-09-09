/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.AltCallingConvention;
import com.sun.jna.Callback;
import com.sun.jna.CallbackParameterContext;
import com.sun.jna.CallbackProxy;
import com.sun.jna.CallbackResultContext;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.Function;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.NativeString;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.StringArray;
import com.sun.jna.Structure;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeMapper;
import com.sun.jna.WString;
import com.sun.jna.win32.DLLCallback;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class CallbackReference
extends WeakReference<Callback> {
    static final /* synthetic */ Map<Callback, CallbackReference> callbackMap;
    static final /* synthetic */ Map<Pointer, Reference<Callback>> pointerCallbackMap;
    static final /* synthetic */ Map<Callback, CallbackReference> directCallbackMap;
    private static final /* synthetic */ Map<CallbackReference, Reference<CallbackReference>> allocatedMemory;
    /* synthetic */ Pointer trampoline;
    /* synthetic */ Pointer cbstruct;
    private static final /* synthetic */ Method PROXY_CALLBACK_METHOD;
    private static final /* synthetic */ Map<Callback, CallbackThreadInitializer> initializers;
    static final /* synthetic */ Map<Object, Object> allocations;
    /* synthetic */ CallbackProxy proxy;
    /* synthetic */ int callingConvention;
    /* synthetic */ Method method;

    private Class<?> getNativeType(Class<?> lllllllllllllllllIlIlIIIlIllIIlI) {
        if (Structure.class.isAssignableFrom(lllllllllllllllllIlIlIIIlIllIIlI)) {
            Structure.validate(lllllllllllllllllIlIlIIIlIllIIlI);
            if (!Structure.ByValue.class.isAssignableFrom(lllllllllllllllllIlIlIIIlIllIIlI)) {
                return Pointer.class;
            }
        } else {
            if (NativeMapped.class.isAssignableFrom(lllllllllllllllllIlIlIIIlIllIIlI)) {
                return NativeMappedConverter.getInstance(lllllllllllllllllIlIlIIIlIllIIlI).nativeType();
            }
            if (lllllllllllllllllIlIlIIIlIllIIlI == String.class || lllllllllllllllllIlIlIIIlIllIIlI == WString.class || lllllllllllllllllIlIlIIIlIllIIlI == String[].class || lllllllllllllllllIlIlIIIlIllIIlI == WString[].class || Callback.class.isAssignableFrom(lllllllllllllllllIlIlIIIlIllIIlI)) {
                return Pointer.class;
            }
        }
        return lllllllllllllllllIlIlIIIlIllIIlI;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static CallbackThreadInitializer setCallbackThreadInitializer(Callback lllllllllllllllllIlIlIIlIIIlllll, CallbackThreadInitializer lllllllllllllllllIlIlIIlIIIlllII) {
        Map<Callback, CallbackThreadInitializer> lllllllllllllllllIlIlIIlIIIllIll = initializers;
        synchronized (lllllllllllllllllIlIlIIlIIIllIll) {
            if (lllllllllllllllllIlIlIIlIIIlllII != null) {
                return initializers.put(lllllllllllllllllIlIlIIlIIIlllll, lllllllllllllllllIlIlIIlIIIlllII);
            }
            return initializers.remove(lllllllllllllllllIlIlIIlIIIlllll);
        }
    }

    private static Method getCallbackMethod(Callback lllllllllllllllllIlIlIIIlIIlllIl) {
        return CallbackReference.getCallbackMethod(CallbackReference.findCallbackClass(lllllllllllllllllIlIlIIIlIIlllIl.getClass()));
    }

    private static Pointer getNativeFunctionPointer(Callback lllllllllllllllllIlIlIIIIllIIlIl) {
        InvocationHandler lllllllllllllllllIlIlIIIIllIIllI;
        if (Proxy.isProxyClass(lllllllllllllllllIlIlIIIIllIIlIl.getClass()) && (lllllllllllllllllIlIlIIIIllIIllI = Proxy.getInvocationHandler(lllllllllllllllllIlIlIIIIllIIlIl)) instanceof NativeFunctionHandler) {
            return ((NativeFunctionHandler)lllllllllllllllllIlIlIIIIllIIllI).getPointer();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Pointer getFunctionPointer(Callback lllllllllllllllllIlIlIIIIlIlIlIl, boolean lllllllllllllllllIlIlIIIIlIlIlII) {
        Pointer lllllllllllllllllIlIlIIIIlIlIIll = null;
        if (lllllllllllllllllIlIlIIIIlIlIlIl == null) {
            return null;
        }
        lllllllllllllllllIlIlIIIIlIlIIll = CallbackReference.getNativeFunctionPointer(lllllllllllllllllIlIlIIIIlIlIlIl);
        if (lllllllllllllllllIlIlIIIIlIlIIll != null) {
            return lllllllllllllllllIlIlIIIIlIlIIll;
        }
        Map<String, Object> lllllllllllllllllIlIlIIIIlIlIIlI = Native.getLibraryOptions(lllllllllllllllllIlIlIIIIlIlIlIl.getClass());
        int lllllllllllllllllIlIlIIIIlIlIIIl = lllllllllllllllllIlIlIIIIlIlIlIl instanceof AltCallingConvention ? 63 : (lllllllllllllllllIlIlIIIIlIlIIlI != null && lllllllllllllllllIlIlIIIIlIlIIlI.containsKey("calling-convention") ? (Integer)lllllllllllllllllIlIlIIIIlIlIIlI.get("calling-convention") : 0);
        Map<Callback, CallbackReference> lllllllllllllllllIlIlIIIIlIlIIII = lllllllllllllllllIlIlIIIIlIlIlII ? directCallbackMap : callbackMap;
        Map<Pointer, Reference<Callback>> lllllllllllllllllIlIlIIIIlIIlIIl = pointerCallbackMap;
        synchronized (lllllllllllllllllIlIlIIIIlIIlIIl) {
            CallbackReference lllllllllllllllllIlIlIIIIlIlIllI = lllllllllllllllllIlIlIIIIlIlIIII.get(lllllllllllllllllIlIlIIIIlIlIlIl);
            if (lllllllllllllllllIlIlIIIIlIlIllI == null) {
                lllllllllllllllllIlIlIIIIlIlIllI = new CallbackReference(lllllllllllllllllIlIlIIIIlIlIlIl, lllllllllllllllllIlIlIIIIlIlIIIl, lllllllllllllllllIlIlIIIIlIlIlII);
                lllllllllllllllllIlIlIIIIlIlIIII.put(lllllllllllllllllIlIlIIIIlIlIlIl, lllllllllllllllllIlIlIIIIlIlIllI);
                pointerCallbackMap.put(lllllllllllllllllIlIlIIIIlIlIllI.getTrampoline(), new WeakReference<Callback>(lllllllllllllllllIlIlIIIIlIlIlIl));
                if (initializers.containsKey(lllllllllllllllllIlIlIIIIlIlIlIl)) {
                    lllllllllllllllllIlIlIIIIlIlIllI.setCallbackOptions(1);
                }
            }
            return lllllllllllllllllIlIlIIIIlIlIllI.getTrampoline();
        }
    }

    protected synchronized void dispose() {
        CallbackReference lllllllllllllllllIlIlIIIIlllIllI;
        if (lllllllllllllllllIlIlIIIIlllIllI.cbstruct != null) {
            try {
                Native.freeNativeCallback(lllllllllllllllllIlIlIIIIlllIllI.cbstruct.peer);
            }
            finally {
                lllllllllllllllllIlIlIIIIlllIllI.cbstruct.peer = 0L;
                lllllllllllllllllIlIlIIIIlllIllI.cbstruct = null;
                allocatedMemory.remove(lllllllllllllllllIlIlIIIIlllIllI);
            }
        }
    }

    public static Pointer getFunctionPointer(Callback lllllllllllllllllIlIlIIIIllIIIIl) {
        return CallbackReference.getFunctionPointer(lllllllllllllllllIlIlIIIIllIIIIl, false);
    }

    private static Method checkMethod(Method lllllllllllllllllIlIlIIIlIlIllIl) {
        if (lllllllllllllllllIlIlIIIlIlIllIl.getParameterTypes().length > 256) {
            String lllllllllllllllllIlIlIIIlIlIllll = String.valueOf(new StringBuilder().append("Method signature exceeds the maximum parameter count: ").append(lllllllllllllllllIlIlIIIlIlIllIl));
            throw new UnsupportedOperationException(lllllllllllllllllIlIlIIIlIlIllll);
        }
        return lllllllllllllllllIlIlIIIlIlIllIl;
    }

    public static Callback getCallback(Class<?> lllllllllllllllllIlIlIIlIIIIlIIl, Pointer lllllllllllllllllIlIlIIlIIIIIllI) {
        return CallbackReference.getCallback(lllllllllllllllllIlIlIIlIIIIlIIl, lllllllllllllllllIlIlIIlIIIIIllI, false);
    }

    private static Method getCallbackMethod(Class<?> lllllllllllllllllIlIlIIIlIIlIIIl) {
        Method[] lllllllllllllllllIlIlIIIlIIlIIII = lllllllllllllllllIlIlIIIlIIlIIIl.getDeclaredMethods();
        Method[] lllllllllllllllllIlIlIIIlIIIllll = lllllllllllllllllIlIlIIIlIIlIIIl.getMethods();
        HashSet<Method> lllllllllllllllllIlIlIIIlIIIlllI = new HashSet<Method>(Arrays.asList(lllllllllllllllllIlIlIIIlIIlIIII));
        lllllllllllllllllIlIlIIIlIIIlllI.retainAll(Arrays.asList(lllllllllllllllllIlIlIIIlIIIllll));
        Iterator lllllllllllllllllIlIlIIIlIIlIlII = lllllllllllllllllIlIlIIIlIIIlllI.iterator();
        while (lllllllllllllllllIlIlIIIlIIlIlII.hasNext()) {
            Method lllllllllllllllllIlIlIIIlIIlIlIl = (Method)lllllllllllllllllIlIlIIIlIIlIlII.next();
            if (!Callback.FORBIDDEN_NAMES.contains(lllllllllllllllllIlIlIIIlIIlIlIl.getName())) continue;
            lllllllllllllllllIlIlIIIlIIlIlII.remove();
        }
        Method[] lllllllllllllllllIlIlIIIlIIIllIl = lllllllllllllllllIlIlIIIlIIIlllI.toArray(new Method[lllllllllllllllllIlIlIIIlIIIlllI.size()]);
        if (lllllllllllllllllIlIlIIIlIIIllIl.length == 1) {
            return CallbackReference.checkMethod(lllllllllllllllllIlIlIIIlIIIllIl[0]);
        }
        for (int lllllllllllllllllIlIlIIIlIIlIIlI = 0; lllllllllllllllllIlIlIIIlIIlIIlI < lllllllllllllllllIlIlIIIlIIIllIl.length; ++lllllllllllllllllIlIlIIIlIIlIIlI) {
            Method lllllllllllllllllIlIlIIIlIIlIIll = lllllllllllllllllIlIlIIIlIIIllIl[lllllllllllllllllIlIlIIIlIIlIIlI];
            if (!"callback".equals(lllllllllllllllllIlIlIIIlIIlIIll.getName())) continue;
            return CallbackReference.checkMethod(lllllllllllllllllIlIlIIIlIIlIIll);
        }
        String lllllllllllllllllIlIlIIIlIIIllII = "Callback must implement a single public method, or one public method named 'callback'";
        throw new IllegalArgumentException(lllllllllllllllllIlIlIIIlIIIllII);
    }

    private static Pointer getNativeString(Object lllllllllllllllllIlIlIIIIIllllIl, boolean lllllllllllllllllIlIlIIIIIlllllI) {
        if (lllllllllllllllllIlIlIIIIIllllIl != null) {
            NativeString lllllllllllllllllIlIlIIIIlIIIIII = new NativeString(lllllllllllllllllIlIlIIIIIllllIl.toString(), lllllllllllllllllIlIlIIIIIlllllI);
            allocations.put(lllllllllllllllllIlIlIIIIIllllIl, lllllllllllllllllIlIlIIIIlIIIIII);
            return lllllllllllllllllIlIlIIIIlIIIIII.getPointer();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ThreadGroup initializeThread(Callback lllllllllllllllllIlIlIIlIIIlIIII, AttachOptions lllllllllllllllllIlIlIIlIIIlIIll) {
        CallbackThreadInitializer lllllllllllllllllIlIlIIlIIIlIIlI = null;
        if (lllllllllllllllllIlIlIIlIIIlIIII instanceof DefaultCallbackProxy) {
            lllllllllllllllllIlIlIIlIIIlIIII = ((DefaultCallbackProxy)lllllllllllllllllIlIlIIlIIIlIIII).getCallback();
        }
        Map<Callback, CallbackThreadInitializer> lllllllllllllllllIlIlIIlIIIIllIl = initializers;
        synchronized (lllllllllllllllllIlIlIIlIIIIllIl) {
            lllllllllllllllllIlIlIIlIIIlIIlI = initializers.get(lllllllllllllllllIlIlIIlIIIlIIII);
        }
        ThreadGroup lllllllllllllllllIlIlIIlIIIlIIIl = null;
        if (lllllllllllllllllIlIlIIlIIIlIIlI != null) {
            lllllllllllllllllIlIlIIlIIIlIIIl = lllllllllllllllllIlIlIIlIIIlIIlI.getThreadGroup(lllllllllllllllllIlIlIIlIIIlIIII);
            lllllllllllllllllIlIlIIlIIIlIIll.name = lllllllllllllllllIlIlIIlIIIlIIlI.getName(lllllllllllllllllIlIlIIlIIIlIIII);
            lllllllllllllllllIlIlIIlIIIlIIll.daemon = lllllllllllllllllIlIlIIlIIIlIIlI.isDaemon(lllllllllllllllllIlIlIIlIIIlIIII);
            lllllllllllllllllIlIlIIlIIIlIIll.detach = lllllllllllllllllIlIlIIlIIIlIIlI.detach(lllllllllllllllllIlIlIIlIIIlIIII);
            lllllllllllllllllIlIlIIlIIIlIIll.write();
        }
        return lllllllllllllllllIlIlIIlIIIlIIIl;
    }

    static Class<?> findCallbackClass(Class<?> lllllllllllllllllIlIlIIIlIlIIlIl) {
        if (!Callback.class.isAssignableFrom(lllllllllllllllllIlIlIIIlIlIIlIl)) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(lllllllllllllllllIlIlIIIlIlIIlIl.getName()).append(" is not derived from com.sun.jna.Callback")));
        }
        if (lllllllllllllllllIlIlIIIlIlIIlIl.isInterface()) {
            return lllllllllllllllllIlIlIIIlIlIIlIl;
        }
        Class<?>[] lllllllllllllllllIlIlIIIlIlIIlII = lllllllllllllllllIlIlIIIlIlIIlIl.getInterfaces();
        for (int lllllllllllllllllIlIlIIIlIlIIllI = 0; lllllllllllllllllIlIlIIIlIlIIllI < lllllllllllllllllIlIlIIIlIlIIlII.length; ++lllllllllllllllllIlIlIIIlIlIIllI) {
            if (!Callback.class.isAssignableFrom(lllllllllllllllllIlIlIIIlIlIIlII[lllllllllllllllllIlIlIIIlIlIIllI])) continue;
            try {
                CallbackReference.getCallbackMethod(lllllllllllllllllIlIlIIIlIlIIlII[lllllllllllllllllIlIlIIIlIlIIllI]);
                return lllllllllllllllllIlIlIIIlIlIIlII[lllllllllllllllllIlIlIIIlIlIIllI];
            }
            catch (IllegalArgumentException lllllllllllllllllIlIlIIIlIlIIlll) {
                break;
            }
        }
        if (Callback.class.isAssignableFrom(lllllllllllllllllIlIlIIIlIlIIlIl.getSuperclass())) {
            return CallbackReference.findCallbackClass(lllllllllllllllllIlIlIIIlIlIIlIl.getSuperclass());
        }
        return lllllllllllllllllIlIlIIIlIlIIlIl;
    }

    protected void finalize() {
        CallbackReference lllllllllllllllllIlIlIIIIllllIIl;
        lllllllllllllllllIlIlIIIIllllIIl.dispose();
    }

    private void setCallbackOptions(int lllllllllllllllllIlIlIIIlIIIIIIl) {
        CallbackReference lllllllllllllllllIlIlIIIlIIIIIlI;
        lllllllllllllllllIlIlIIIlIIIIIlI.cbstruct.setInt(Pointer.SIZE, lllllllllllllllllIlIlIIIlIIIIIIl);
    }

    static {
        callbackMap = new WeakHashMap<Callback, CallbackReference>();
        directCallbackMap = new WeakHashMap<Callback, CallbackReference>();
        pointerCallbackMap = new WeakHashMap<Pointer, Reference<Callback>>();
        allocations = new WeakHashMap<Object, Object>();
        allocatedMemory = Collections.synchronizedMap(new WeakHashMap());
        try {
            PROXY_CALLBACK_METHOD = CallbackProxy.class.getMethod("callback", Object[].class);
        }
        catch (Exception lllllllllllllllllIlIlIIIIIllIIII) {
            throw new Error("Error looking up CallbackProxy.callback() method");
        }
        initializers = new WeakHashMap<Callback, CallbackThreadInitializer>();
    }

    private Callback getCallback() {
        CallbackReference lllllllllllllllllIlIlIIIIllIlIIl;
        return (Callback)lllllllllllllllllIlIlIIIIllIlIIl.get();
    }

    private CallbackReference(Callback lllllllllllllllllIlIlIIIllIIIIIl, int lllllllllllllllllIlIlIIIllIIlIlI, boolean lllllllllllllllllIlIlIIIlIllllll) {
        super(lllllllllllllllllIlIlIIIllIIIIIl);
        CallbackReference lllllllllllllllllIlIlIIIllIIllII;
        TypeMapper lllllllllllllllllIlIlIIIllIIlIII = Native.getTypeMapper(lllllllllllllllllIlIlIIIllIIIIIl.getClass());
        lllllllllllllllllIlIlIIIllIIllII.callingConvention = lllllllllllllllllIlIlIIIllIIlIlI;
        boolean lllllllllllllllllIlIlIIIllIIIlIl = Platform.isPPC();
        if (lllllllllllllllllIlIlIIIlIllllll) {
            Method lllllllllllllllllIlIlIIIllIllIII = CallbackReference.getCallbackMethod(lllllllllllllllllIlIlIIIllIIIIIl);
            Class<?>[] lllllllllllllllllIlIlIIIllIlIlll = lllllllllllllllllIlIlIIIllIllIII.getParameterTypes();
            for (int lllllllllllllllllIlIlIIIllIllIIl = 0; lllllllllllllllllIlIlIIIllIllIIl < lllllllllllllllllIlIlIIIllIlIlll.length; ++lllllllllllllllllIlIlIIIllIllIIl) {
                if (lllllllllllllllllIlIlIIIllIIIlIl && (lllllllllllllllllIlIlIIIllIlIlll[lllllllllllllllllIlIlIIIllIllIIl] == Float.TYPE || lllllllllllllllllIlIlIIIllIlIlll[lllllllllllllllllIlIlIIIllIllIIl] == Double.TYPE)) {
                    lllllllllllllllllIlIlIIIlIllllll = false;
                    break;
                }
                if (lllllllllllllllllIlIlIIIllIIlIII == null || lllllllllllllllllIlIlIIIllIIlIII.getFromNativeConverter(lllllllllllllllllIlIlIIIllIlIlll[lllllllllllllllllIlIlIIIllIllIIl]) == null) continue;
                lllllllllllllllllIlIlIIIlIllllll = false;
                break;
            }
            if (lllllllllllllllllIlIlIIIllIIlIII != null && lllllllllllllllllIlIlIIIllIIlIII.getToNativeConverter(lllllllllllllllllIlIlIIIllIllIII.getReturnType()) != null) {
                lllllllllllllllllIlIlIIIlIllllll = false;
            }
        }
        String lllllllllllllllllIlIlIIIllIIIlII = Native.getStringEncoding(lllllllllllllllllIlIlIIIllIIIIIl.getClass());
        long lllllllllllllllllIlIlIIIllIIIIll = 0L;
        if (lllllllllllllllllIlIlIIIlIllllll) {
            lllllllllllllllllIlIlIIIllIIllII.method = CallbackReference.getCallbackMethod(lllllllllllllllllIlIlIIIllIIIIIl);
            Class<?>[] lllllllllllllllllIlIlIIIllIlIlIl = lllllllllllllllllIlIlIIIllIIllII.method.getParameterTypes();
            Class<?> lllllllllllllllllIlIlIIIllIlIlII = lllllllllllllllllIlIlIIIllIIllII.method.getReturnType();
            int lllllllllllllllllIlIlIIIllIlIllI = 1;
            if (lllllllllllllllllIlIlIIIllIIIIIl instanceof DLLCallback) {
                lllllllllllllllllIlIlIIIllIlIllI |= 2;
            }
            lllllllllllllllllIlIlIIIllIIIIll = Native.createNativeCallback(lllllllllllllllllIlIlIIIllIIIIIl, lllllllllllllllllIlIlIIIllIIllII.method, lllllllllllllllllIlIlIIIllIlIlIl, lllllllllllllllllIlIlIIIllIlIlII, lllllllllllllllllIlIlIIIllIIlIlI, lllllllllllllllllIlIlIIIllIlIllI, lllllllllllllllllIlIlIIIllIIIlII);
        } else {
            lllllllllllllllllIlIlIIIllIIllII.proxy = lllllllllllllllllIlIlIIIllIIIIIl instanceof CallbackProxy ? (CallbackProxy)lllllllllllllllllIlIlIIIllIIIIIl : lllllllllllllllllIlIlIIIllIIllII.new DefaultCallbackProxy(CallbackReference.getCallbackMethod(lllllllllllllllllIlIlIIIllIIIIIl), lllllllllllllllllIlIlIIIllIIlIII, lllllllllllllllllIlIlIIIllIIIlII);
            Class<?>[] lllllllllllllllllIlIlIIIllIIIlll = lllllllllllllllllIlIlIIIllIIllII.proxy.getParameterTypes();
            Class<?> lllllllllllllllllIlIlIIIllIIIllI = lllllllllllllllllIlIlIIIllIIllII.proxy.getReturnType();
            if (lllllllllllllllllIlIlIIIllIIlIII != null) {
                for (int lllllllllllllllllIlIlIIIllIlIIlI = 0; lllllllllllllllllIlIlIIIllIlIIlI < lllllllllllllllllIlIlIIIllIIIlll.length; ++lllllllllllllllllIlIlIIIllIlIIlI) {
                    FromNativeConverter lllllllllllllllllIlIlIIIllIlIIll = lllllllllllllllllIlIlIIIllIIlIII.getFromNativeConverter(lllllllllllllllllIlIlIIIllIIIlll[lllllllllllllllllIlIlIIIllIlIIlI]);
                    if (lllllllllllllllllIlIlIIIllIlIIll == null) continue;
                    lllllllllllllllllIlIlIIIllIIIlll[lllllllllllllllllIlIlIIIllIlIIlI] = lllllllllllllllllIlIlIIIllIlIIll.nativeType();
                }
                ToNativeConverter lllllllllllllllllIlIlIIIllIlIIIl = lllllllllllllllllIlIlIIIllIIlIII.getToNativeConverter(lllllllllllllllllIlIlIIIllIIIllI);
                if (lllllllllllllllllIlIlIIIllIlIIIl != null) {
                    lllllllllllllllllIlIlIIIllIIIllI = lllllllllllllllllIlIlIIIllIlIIIl.nativeType();
                }
            }
            for (int lllllllllllllllllIlIlIIIllIIllll = 0; lllllllllllllllllIlIlIIIllIIllll < lllllllllllllllllIlIlIIIllIIIlll.length; ++lllllllllllllllllIlIlIIIllIIllll) {
                lllllllllllllllllIlIlIIIllIIIlll[lllllllllllllllllIlIlIIIllIIllll] = lllllllllllllllllIlIlIIIllIIllII.getNativeType(lllllllllllllllllIlIlIIIllIIIlll[lllllllllllllllllIlIlIIIllIIllll]);
                if (CallbackReference.isAllowableNativeType(lllllllllllllllllIlIlIIIllIIIlll[lllllllllllllllllIlIlIIIllIIllll])) continue;
                String lllllllllllllllllIlIlIIIllIlIIII = String.valueOf(new StringBuilder().append("Callback argument ").append(lllllllllllllllllIlIlIIIllIIIlll[lllllllllllllllllIlIlIIIllIIllll]).append(" requires custom type conversion"));
                throw new IllegalArgumentException(lllllllllllllllllIlIlIIIllIlIIII);
            }
            if (!CallbackReference.isAllowableNativeType(lllllllllllllllllIlIlIIIllIIIllI = lllllllllllllllllIlIlIIIllIIllII.getNativeType(lllllllllllllllllIlIlIIIllIIIllI))) {
                String lllllllllllllllllIlIlIIIllIIlllI = String.valueOf(new StringBuilder().append("Callback return type ").append(lllllllllllllllllIlIlIIIllIIIllI).append(" requires custom type conversion"));
                throw new IllegalArgumentException(lllllllllllllllllIlIlIIIllIIlllI);
            }
            int lllllllllllllllllIlIlIIIllIIllIl = lllllllllllllllllIlIlIIIllIIIIIl instanceof DLLCallback ? 2 : 0;
            lllllllllllllllllIlIlIIIllIIIIll = Native.createNativeCallback(lllllllllllllllllIlIlIIIllIIllII.proxy, PROXY_CALLBACK_METHOD, lllllllllllllllllIlIlIIIllIIIlll, lllllllllllllllllIlIlIIIllIIIllI, lllllllllllllllllIlIlIIIllIIlIlI, lllllllllllllllllIlIlIIIllIIllIl, lllllllllllllllllIlIlIIIllIIIlII);
        }
        lllllllllllllllllIlIlIIIllIIllII.cbstruct = lllllllllllllllllIlIlIIIllIIIIll != 0L ? new Pointer(lllllllllllllllllIlIlIIIllIIIIll) : null;
        allocatedMemory.put(lllllllllllllllllIlIlIIIllIIllII, new WeakReference<CallbackReference>(lllllllllllllllllIlIlIIIllIIllII));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Callback getCallback(Class<?> lllllllllllllllllIlIlIIIllllIlIl, Pointer lllllllllllllllllIlIlIIIllllIIII, boolean lllllllllllllllllIlIlIIIllllIIll) {
        if (lllllllllllllllllIlIlIIIllllIIII == null) {
            return null;
        }
        if (!lllllllllllllllllIlIlIIIllllIlIl.isInterface()) {
            throw new IllegalArgumentException("Callback type must be an interface");
        }
        Map<Callback, CallbackReference> lllllllllllllllllIlIlIIIllllIIlI = lllllllllllllllllIlIlIIIllllIIll ? directCallbackMap : callbackMap;
        Map<Pointer, Reference<Callback>> lllllllllllllllllIlIlIIIlllIllIl = pointerCallbackMap;
        synchronized (lllllllllllllllllIlIlIIIlllIllIl) {
            Callback lllllllllllllllllIlIlIIIlllllIlI = null;
            Reference<Callback> lllllllllllllllllIlIlIIIlllllIIl = pointerCallbackMap.get(lllllllllllllllllIlIlIIIllllIIII);
            if (lllllllllllllllllIlIlIIIlllllIIl != null) {
                lllllllllllllllllIlIlIIIlllllIlI = lllllllllllllllllIlIlIIIlllllIIl.get();
                if (lllllllllllllllllIlIlIIIlllllIlI != null && !lllllllllllllllllIlIlIIIllllIlIl.isAssignableFrom(lllllllllllllllllIlIlIIIlllllIlI.getClass())) {
                    throw new IllegalStateException(String.valueOf(new StringBuilder().append("Pointer ").append(lllllllllllllllllIlIlIIIllllIIII).append(" already mapped to ").append(lllllllllllllllllIlIlIIIlllllIlI).append(".\nNative code may be re-using a default function pointer, in which case you may need to use a common Callback class wherever the function pointer is reused.")));
                }
                return lllllllllllllllllIlIlIIIlllllIlI;
            }
            int lllllllllllllllllIlIlIIIlllllIII = AltCallingConvention.class.isAssignableFrom(lllllllllllllllllIlIlIIIllllIlIl) ? 63 : 0;
            HashMap<String, Object> lllllllllllllllllIlIlIIIllllIlll = new HashMap<String, Object>(Native.getLibraryOptions(lllllllllllllllllIlIlIIIllllIlIl));
            lllllllllllllllllIlIlIIIllllIlll.put("invoking-method", CallbackReference.getCallbackMethod(lllllllllllllllllIlIlIIIllllIlIl));
            NativeFunctionHandler lllllllllllllllllIlIlIIIllllIllI = new NativeFunctionHandler(lllllllllllllllllIlIlIIIllllIIII, lllllllllllllllllIlIlIIIlllllIII, lllllllllllllllllIlIlIIIllllIlll);
            lllllllllllllllllIlIlIIIlllllIlI = (Callback)Proxy.newProxyInstance(lllllllllllllllllIlIlIIIllllIlIl.getClassLoader(), new Class[]{lllllllllllllllllIlIlIIIllllIlIl}, (InvocationHandler)lllllllllllllllllIlIlIIIllllIllI);
            lllllllllllllllllIlIlIIIllllIIlI.remove(lllllllllllllllllIlIlIIIlllllIlI);
            pointerCallbackMap.put(lllllllllllllllllIlIlIIIllllIIII, new WeakReference<Callback>(lllllllllllllllllIlIlIIIlllllIlI));
            return lllllllllllllllllIlIlIIIlllllIlI;
        }
    }

    public Pointer getTrampoline() {
        CallbackReference lllllllllllllllllIlIlIIIIlllllII;
        if (lllllllllllllllllIlIlIIIIlllllII.trampoline == null) {
            lllllllllllllllllIlIlIIIIlllllII.trampoline = lllllllllllllllllIlIlIIIIlllllII.cbstruct.getPointer(0L);
        }
        return lllllllllllllllllIlIlIIIIlllllII.trampoline;
    }

    static void disposeAll() {
        LinkedList<CallbackReference> lllllllllllllllllIlIlIIIIllIllll = new LinkedList<CallbackReference>(allocatedMemory.keySet());
        for (CallbackReference lllllllllllllllllIlIlIIIIlllIIII : lllllllllllllllllIlIlIIIIllIllll) {
            lllllllllllllllllIlIlIIIIlllIIII.dispose();
        }
    }

    private static boolean isAllowableNativeType(Class<?> lllllllllllllllllIlIlIIIIlIIIlII) {
        return lllllllllllllllllIlIlIIIIlIIIlII == Void.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Void.class || lllllllllllllllllIlIlIIIIlIIIlII == Boolean.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Boolean.class || lllllllllllllllllIlIlIIIIlIIIlII == Byte.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Byte.class || lllllllllllllllllIlIlIIIIlIIIlII == Short.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Short.class || lllllllllllllllllIlIlIIIIlIIIlII == Character.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Character.class || lllllllllllllllllIlIlIIIIlIIIlII == Integer.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Integer.class || lllllllllllllllllIlIlIIIIlIIIlII == Long.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Long.class || lllllllllllllllllIlIlIIIIlIIIlII == Float.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Float.class || lllllllllllllllllIlIlIIIIlIIIlII == Double.TYPE || lllllllllllllllllIlIlIIIIlIIIlII == Double.class || Structure.ByValue.class.isAssignableFrom(lllllllllllllllllIlIlIIIIlIIIlII) && Structure.class.isAssignableFrom(lllllllllllllllllIlIlIIIIlIIIlII) || Pointer.class.isAssignableFrom(lllllllllllllllllIlIlIIIIlIIIlII);
    }

    private static class NativeFunctionHandler
    implements InvocationHandler {
        private final /* synthetic */ Map<String, ?> options;
        private final /* synthetic */ Function function;

        @Override
        public Object invoke(Object llllllllllllllllllllllllIlIIllIl, Method llllllllllllllllllllllllIlIIllII, Object[] llllllllllllllllllllllllIlIIlIll) throws Throwable {
            NativeFunctionHandler llllllllllllllllllllllllIlIIlllI;
            if (Library.Handler.OBJECT_TOSTRING.equals(llllllllllllllllllllllllIlIIllII)) {
                String llllllllllllllllllllllllIlIlIIlI = String.valueOf(new StringBuilder().append("Proxy interface to ").append(llllllllllllllllllllllllIlIIlllI.function));
                Method llllllllllllllllllllllllIlIlIIIl = (Method)llllllllllllllllllllllllIlIIlllI.options.get("invoking-method");
                Class<?> llllllllllllllllllllllllIlIlIIII = CallbackReference.findCallbackClass(llllllllllllllllllllllllIlIlIIIl.getDeclaringClass());
                llllllllllllllllllllllllIlIlIIlI = String.valueOf(new StringBuilder().append(llllllllllllllllllllllllIlIlIIlI).append(" (").append(llllllllllllllllllllllllIlIlIIII.getName()).append(")"));
                return llllllllllllllllllllllllIlIlIIlI;
            }
            if (Library.Handler.OBJECT_HASHCODE.equals(llllllllllllllllllllllllIlIIllII)) {
                return llllllllllllllllllllllllIlIIlllI.hashCode();
            }
            if (Library.Handler.OBJECT_EQUALS.equals(llllllllllllllllllllllllIlIIllII)) {
                Object llllllllllllllllllllllllIlIIllll = llllllllllllllllllllllllIlIIlIll[0];
                if (llllllllllllllllllllllllIlIIllll != null && Proxy.isProxyClass(llllllllllllllllllllllllIlIIllll.getClass())) {
                    return Function.valueOf(Proxy.getInvocationHandler(llllllllllllllllllllllllIlIIllll) == llllllllllllllllllllllllIlIIlllI);
                }
                return Boolean.FALSE;
            }
            if (Function.isVarArgs(llllllllllllllllllllllllIlIIllII)) {
                llllllllllllllllllllllllIlIIlIll = Function.concatenateVarArgs(llllllllllllllllllllllllIlIIlIll);
            }
            return llllllllllllllllllllllllIlIIlllI.function.invoke(llllllllllllllllllllllllIlIIllII.getReturnType(), llllllllllllllllllllllllIlIIlIll, llllllllllllllllllllllllIlIIlllI.options);
        }

        public Pointer getPointer() {
            NativeFunctionHandler llllllllllllllllllllllllIlIIIIlI;
            return llllllllllllllllllllllllIlIIIIlI.function;
        }

        public NativeFunctionHandler(Pointer llllllllllllllllllllllllIlIlllll, int llllllllllllllllllllllllIlIllIlI, Map<String, ?> llllllllllllllllllllllllIlIlllIl) {
            NativeFunctionHandler llllllllllllllllllllllllIllIIIII;
            llllllllllllllllllllllllIllIIIII.options = llllllllllllllllllllllllIlIlllIl;
            llllllllllllllllllllllllIllIIIII.function = new Function(llllllllllllllllllllllllIlIlllll, llllllllllllllllllllllllIlIllIlI, (String)llllllllllllllllllllllllIlIlllIl.get("string-encoding"));
        }
    }

    static class AttachOptions
    extends Structure {
        public /* synthetic */ boolean detach;
        public static final /* synthetic */ List<String> FIELDS;
        public /* synthetic */ boolean daemon;
        public /* synthetic */ String name;

        AttachOptions() {
            AttachOptions lIlIllIllIllI;
            lIlIllIllIllI.setStringEncoding("utf8");
        }

        @Override
        protected List<String> getFieldOrder() {
            return FIELDS;
        }

        static {
            FIELDS = AttachOptions.createFieldsOrder("daemon", "detach", "name");
        }
    }

    private class DefaultCallbackProxy
    implements CallbackProxy {
        private final /* synthetic */ Method callbackMethod;
        private /* synthetic */ ToNativeConverter toNative;
        private final /* synthetic */ String encoding;
        private final /* synthetic */ FromNativeConverter[] fromNative;

        @Override
        public Object callback(Object[] lllllllllllllllllIlIlllIllIlllll) {
            DefaultCallbackProxy lllllllllllllllllIlIlllIllIllllI;
            try {
                return lllllllllllllllllIlIlllIllIllllI.invokeCallback(lllllllllllllllllIlIlllIllIlllll);
            }
            catch (Throwable lllllllllllllllllIlIlllIlllIIIIl) {
                Native.getCallbackExceptionHandler().uncaughtException(lllllllllllllllllIlIlllIllIllllI.getCallback(), lllllllllllllllllIlIlllIlllIIIIl);
                return null;
            }
        }

        private Object invokeCallback(Object[] lllllllllllllllllIlIlllIlllIlIll) {
            DefaultCallbackProxy lllllllllllllllllIlIlllIlllIllII;
            Class<?>[] lllllllllllllllllIlIlllIllllIIII = lllllllllllllllllIlIlllIlllIllII.callbackMethod.getParameterTypes();
            Object[] lllllllllllllllllIlIlllIlllIllll = new Object[lllllllllllllllllIlIlllIlllIlIll.length];
            for (int lllllllllllllllllIlIlllIllllIlll = 0; lllllllllllllllllIlIlllIllllIlll < lllllllllllllllllIlIlllIlllIlIll.length; ++lllllllllllllllllIlIlllIllllIlll) {
                Class<?> lllllllllllllllllIlIlllIlllllIIl = lllllllllllllllllIlIlllIllllIIII[lllllllllllllllllIlIlllIllllIlll];
                Object lllllllllllllllllIlIlllIlllllIII = lllllllllllllllllIlIlllIlllIlIll[lllllllllllllllllIlIlllIllllIlll];
                if (lllllllllllllllllIlIlllIlllIllII.fromNative[lllllllllllllllllIlIlllIllllIlll] != null) {
                    CallbackParameterContext lllllllllllllllllIlIlllIlllllIlI = new CallbackParameterContext(lllllllllllllllllIlIlllIlllllIIl, lllllllllllllllllIlIlllIlllIllII.callbackMethod, lllllllllllllllllIlIlllIlllIlIll, lllllllllllllllllIlIlllIllllIlll);
                    lllllllllllllllllIlIlllIlllIllll[lllllllllllllllllIlIlllIllllIlll] = lllllllllllllllllIlIlllIlllIllII.fromNative[lllllllllllllllllIlIlllIllllIlll].fromNative(lllllllllllllllllIlIlllIlllllIII, lllllllllllllllllIlIlllIlllllIlI);
                    continue;
                }
                lllllllllllllllllIlIlllIlllIllll[lllllllllllllllllIlIlllIllllIlll] = lllllllllllllllllIlIlllIlllIllII.convertArgument(lllllllllllllllllIlIlllIlllllIII, lllllllllllllllllIlIlllIlllllIIl);
            }
            Object lllllllllllllllllIlIlllIlllIlllI = null;
            Callback lllllllllllllllllIlIlllIlllIllIl = lllllllllllllllllIlIlllIlllIllII.getCallback();
            if (lllllllllllllllllIlIlllIlllIllIl != null) {
                try {
                    lllllllllllllllllIlIlllIlllIlllI = lllllllllllllllllIlIlllIlllIllII.convertResult(lllllllllllllllllIlIlllIlllIllII.callbackMethod.invoke(lllllllllllllllllIlIlllIlllIllIl, lllllllllllllllllIlIlllIlllIllll));
                }
                catch (IllegalArgumentException lllllllllllllllllIlIlllIllllIllI) {
                    Native.getCallbackExceptionHandler().uncaughtException(lllllllllllllllllIlIlllIlllIllIl, lllllllllllllllllIlIlllIllllIllI);
                }
                catch (IllegalAccessException lllllllllllllllllIlIlllIllllIlIl) {
                    Native.getCallbackExceptionHandler().uncaughtException(lllllllllllllllllIlIlllIlllIllIl, lllllllllllllllllIlIlllIllllIlIl);
                }
                catch (InvocationTargetException lllllllllllllllllIlIlllIllllIlII) {
                    Native.getCallbackExceptionHandler().uncaughtException(lllllllllllllllllIlIlllIlllIllIl, lllllllllllllllllIlIlllIllllIlII.getTargetException());
                }
            }
            for (int lllllllllllllllllIlIlllIllllIIll = 0; lllllllllllllllllIlIlllIllllIIll < lllllllllllllllllIlIlllIlllIllll.length; ++lllllllllllllllllIlIlllIllllIIll) {
                if (!(lllllllllllllllllIlIlllIlllIllll[lllllllllllllllllIlIlllIllllIIll] instanceof Structure) || lllllllllllllllllIlIlllIlllIllll[lllllllllllllllllIlIlllIllllIIll] instanceof Structure.ByValue) continue;
                ((Structure)lllllllllllllllllIlIlllIlllIllll[lllllllllllllllllIlIlllIllllIIll]).autoWrite();
            }
            return lllllllllllllllllIlIlllIlllIlllI;
        }

        private Object convertResult(Object lllllllllllllllllIlIlllIllIIIlIl) {
            DefaultCallbackProxy lllllllllllllllllIlIlllIllIIIllI;
            if (lllllllllllllllllIlIlllIllIIIllI.toNative != null) {
                lllllllllllllllllIlIlllIllIIIlIl = lllllllllllllllllIlIlllIllIIIllI.toNative.toNative(lllllllllllllllllIlIlllIllIIIlIl, new CallbackResultContext(lllllllllllllllllIlIlllIllIIIllI.callbackMethod));
            }
            if (lllllllllllllllllIlIlllIllIIIlIl == null) {
                return null;
            }
            Class<?> lllllllllllllllllIlIlllIllIIIlII = lllllllllllllllllIlIlllIllIIIlIl.getClass();
            if (Structure.class.isAssignableFrom(lllllllllllllllllIlIlllIllIIIlII)) {
                if (Structure.ByValue.class.isAssignableFrom(lllllllllllllllllIlIlllIllIIIlII)) {
                    return lllllllllllllllllIlIlllIllIIIlIl;
                }
                return ((Structure)lllllllllllllllllIlIlllIllIIIlIl).getPointer();
            }
            if (lllllllllllllllllIlIlllIllIIIlII == Boolean.TYPE || lllllllllllllllllIlIlllIllIIIlII == Boolean.class) {
                return Boolean.TRUE.equals(lllllllllllllllllIlIlllIllIIIlIl) ? Function.INTEGER_TRUE : Function.INTEGER_FALSE;
            }
            if (lllllllllllllllllIlIlllIllIIIlII == String.class || lllllllllllllllllIlIlllIllIIIlII == WString.class) {
                return CallbackReference.getNativeString(lllllllllllllllllIlIlllIllIIIlIl, lllllllllllllllllIlIlllIllIIIlII == WString.class);
            }
            if (lllllllllllllllllIlIlllIllIIIlII == String[].class || lllllllllllllllllIlIlllIllIIIlII == WString.class) {
                StringArray lllllllllllllllllIlIlllIllIIIlll = lllllllllllllllllIlIlllIllIIIlII == String[].class ? new StringArray((String[])lllllllllllllllllIlIlllIllIIIlIl, lllllllllllllllllIlIlllIllIIIllI.encoding) : new StringArray((WString[])lllllllllllllllllIlIlllIllIIIlIl);
                allocations.put(lllllllllllllllllIlIlllIllIIIlIl, lllllllllllllllllIlIlllIllIIIlll);
                return lllllllllllllllllIlIlllIllIIIlll;
            }
            if (Callback.class.isAssignableFrom(lllllllllllllllllIlIlllIllIIIlII)) {
                return CallbackReference.getFunctionPointer((Callback)lllllllllllllllllIlIlllIllIIIlIl);
            }
            return lllllllllllllllllIlIlllIllIIIlIl;
        }

        private Object convertArgument(Object lllllllllllllllllIlIlllIllIlIIlI, Class<?> lllllllllllllllllIlIlllIllIIlllI) {
            if (lllllllllllllllllIlIlllIllIlIIlI instanceof Pointer) {
                DefaultCallbackProxy lllllllllllllllllIlIlllIllIlIIII;
                if (lllllllllllllllllIlIlllIllIIlllI == String.class) {
                    lllllllllllllllllIlIlllIllIlIIlI = ((Pointer)lllllllllllllllllIlIlllIllIlIIlI).getString(0L, lllllllllllllllllIlIlllIllIlIIII.encoding);
                } else if (lllllllllllllllllIlIlllIllIIlllI == WString.class) {
                    lllllllllllllllllIlIlllIllIlIIlI = new WString(((Pointer)lllllllllllllllllIlIlllIllIlIIlI).getWideString(0L));
                } else if (lllllllllllllllllIlIlllIllIIlllI == String[].class) {
                    lllllllllllllllllIlIlllIllIlIIlI = ((Pointer)lllllllllllllllllIlIlllIllIlIIlI).getStringArray(0L, lllllllllllllllllIlIlllIllIlIIII.encoding);
                } else if (lllllllllllllllllIlIlllIllIIlllI == WString[].class) {
                    lllllllllllllllllIlIlllIllIlIIlI = ((Pointer)lllllllllllllllllIlIlllIllIlIIlI).getWideStringArray(0L);
                } else if (Callback.class.isAssignableFrom(lllllllllllllllllIlIlllIllIIlllI)) {
                    lllllllllllllllllIlIlllIllIlIIlI = CallbackReference.getCallback(lllllllllllllllllIlIlllIllIIlllI, (Pointer)lllllllllllllllllIlIlllIllIlIIlI);
                } else if (Structure.class.isAssignableFrom(lllllllllllllllllIlIlllIllIIlllI)) {
                    if (Structure.ByValue.class.isAssignableFrom(lllllllllllllllllIlIlllIllIIlllI)) {
                        Structure lllllllllllllllllIlIlllIllIlIllI = Structure.newInstance(lllllllllllllllllIlIlllIllIIlllI);
                        byte[] lllllllllllllllllIlIlllIllIlIlIl = new byte[lllllllllllllllllIlIlllIllIlIllI.size()];
                        ((Pointer)lllllllllllllllllIlIlllIllIlIIlI).read(0L, lllllllllllllllllIlIlllIllIlIlIl, 0, lllllllllllllllllIlIlllIllIlIlIl.length);
                        lllllllllllllllllIlIlllIllIlIllI.getPointer().write(0L, lllllllllllllllllIlIlllIllIlIlIl, 0, lllllllllllllllllIlIlllIllIlIlIl.length);
                        lllllllllllllllllIlIlllIllIlIllI.read();
                        lllllllllllllllllIlIlllIllIlIIlI = lllllllllllllllllIlIlllIllIlIllI;
                    } else {
                        Structure lllllllllllllllllIlIlllIllIlIlII = Structure.newInstance(lllllllllllllllllIlIlllIllIIlllI, (Pointer)lllllllllllllllllIlIlllIllIlIIlI);
                        lllllllllllllllllIlIlllIllIlIlII.conditionalAutoRead();
                        lllllllllllllllllIlIlllIllIlIIlI = lllllllllllllllllIlIlllIllIlIlII;
                    }
                }
            } else if ((Boolean.TYPE == lllllllllllllllllIlIlllIllIIlllI || Boolean.class == lllllllllllllllllIlIlllIllIIlllI) && lllllllllllllllllIlIlllIllIlIIlI instanceof Number) {
                lllllllllllllllllIlIlllIllIlIIlI = Function.valueOf(((Number)lllllllllllllllllIlIlllIllIlIIlI).intValue() != 0);
            }
            return lllllllllllllllllIlIlllIllIlIIlI;
        }

        @Override
        public Class<?>[] getParameterTypes() {
            DefaultCallbackProxy lllllllllllllllllIlIlllIlIlllllI;
            return lllllllllllllllllIlIlllIlIlllllI.callbackMethod.getParameterTypes();
        }

        @Override
        public Class<?> getReturnType() {
            DefaultCallbackProxy lllllllllllllllllIlIlllIlIlllIll;
            return lllllllllllllllllIlIlllIlIlllIll.callbackMethod.getReturnType();
        }

        public Callback getCallback() {
            DefaultCallbackProxy lllllllllllllllllIlIllllIIIIIIll;
            return lllllllllllllllllIlIllllIIIIIIll.CallbackReference.this.getCallback();
        }

        public DefaultCallbackProxy(Method lllllllllllllllllIlIllllIIIIlIll, TypeMapper lllllllllllllllllIlIllllIIIlIIIl, String lllllllllllllllllIlIllllIIIIlIIl) {
            DefaultCallbackProxy lllllllllllllllllIlIllllIIIIllIl;
            lllllllllllllllllIlIllllIIIIllIl.callbackMethod = lllllllllllllllllIlIllllIIIIlIll;
            lllllllllllllllllIlIllllIIIIllIl.encoding = lllllllllllllllllIlIllllIIIIlIIl;
            Class<?>[] lllllllllllllllllIlIllllIIIIllll = lllllllllllllllllIlIllllIIIIlIll.getParameterTypes();
            Class<?> lllllllllllllllllIlIllllIIIIlllI = lllllllllllllllllIlIllllIIIIlIll.getReturnType();
            lllllllllllllllllIlIllllIIIIllIl.fromNative = new FromNativeConverter[lllllllllllllllllIlIllllIIIIllll.length];
            if (NativeMapped.class.isAssignableFrom(lllllllllllllllllIlIllllIIIIlllI)) {
                lllllllllllllllllIlIllllIIIIllIl.toNative = NativeMappedConverter.getInstance(lllllllllllllllllIlIllllIIIIlllI);
            } else if (lllllllllllllllllIlIllllIIIlIIIl != null) {
                lllllllllllllllllIlIllllIIIIllIl.toNative = lllllllllllllllllIlIllllIIIlIIIl.getToNativeConverter(lllllllllllllllllIlIllllIIIIlllI);
            }
            for (int lllllllllllllllllIlIllllIIIlIlIl = 0; lllllllllllllllllIlIllllIIIlIlIl < lllllllllllllllllIlIllllIIIIllIl.fromNative.length; ++lllllllllllllllllIlIllllIIIlIlIl) {
                if (NativeMapped.class.isAssignableFrom(lllllllllllllllllIlIllllIIIIllll[lllllllllllllllllIlIllllIIIlIlIl])) {
                    lllllllllllllllllIlIllllIIIIllIl.fromNative[lllllllllllllllllIlIllllIIIlIlIl] = new NativeMappedConverter(lllllllllllllllllIlIllllIIIIllll[lllllllllllllllllIlIllllIIIlIlIl]);
                    continue;
                }
                if (lllllllllllllllllIlIllllIIIlIIIl == null) continue;
                lllllllllllllllllIlIllllIIIIllIl.fromNative[lllllllllllllllllIlIllllIIIlIlIl] = lllllllllllllllllIlIllllIIIlIIIl.getFromNativeConverter(lllllllllllllllllIlIllllIIIIllll[lllllllllllllllllIlIllllIIIlIlIl]);
            }
            if (!lllllllllllllllllIlIllllIIIIlIll.isAccessible()) {
                try {
                    lllllllllllllllllIlIllllIIIIlIll.setAccessible(true);
                }
                catch (SecurityException lllllllllllllllllIlIllllIIIlIlII) {
                    throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Callback method is inaccessible, make sure the interface is public: ").append(lllllllllllllllllIlIllllIIIIlIll)));
                }
            }
        }
    }
}

