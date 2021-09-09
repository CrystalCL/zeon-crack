/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;
import com.sun.jna.ToNativeContext;
import com.sun.jna.TypeConverter;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.WeakHashMap;

public class NativeMappedConverter
implements TypeConverter {
    private final /* synthetic */ NativeMapped instance;
    private final /* synthetic */ Class<?> nativeType;
    private final /* synthetic */ Class<?> type;
    private static final /* synthetic */ Map<Class<?>, Reference<NativeMappedConverter>> converters;

    public NativeMappedConverter(Class<?> lllllllllllllllllIIlIIlIlIIlIlll) {
        NativeMappedConverter lllllllllllllllllIIlIIlIlIIlIllI;
        if (!NativeMapped.class.isAssignableFrom(lllllllllllllllllIIlIIlIlIIlIlll)) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Type must derive from ").append(NativeMapped.class)));
        }
        lllllllllllllllllIIlIIlIlIIlIllI.type = lllllllllllllllllIIlIIlIlIIlIlll;
        lllllllllllllllllIIlIIlIlIIlIllI.instance = lllllllllllllllllIIlIIlIlIIlIllI.defaultValue();
        lllllllllllllllllIIlIIlIlIIlIllI.nativeType = lllllllllllllllllIIlIIlIlIIlIllI.instance.nativeType();
    }

    static {
        converters = new WeakHashMap();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NativeMappedConverter getInstance(Class<?> lllllllllllllllllIIlIIlIlIIlllll) {
        Map<Class<?>, Reference<NativeMappedConverter>> lllllllllllllllllIIlIIlIlIIllllI = converters;
        synchronized (lllllllllllllllllIIlIIlIlIIllllI) {
            NativeMappedConverter lllllllllllllllllIIlIIlIlIlIIIIl;
            Reference<NativeMappedConverter> lllllllllllllllllIIlIIlIlIlIIIlI = converters.get(lllllllllllllllllIIlIIlIlIIlllll);
            NativeMappedConverter nativeMappedConverter = lllllllllllllllllIIlIIlIlIlIIIIl = lllllllllllllllllIIlIIlIlIlIIIlI != null ? lllllllllllllllllIIlIIlIlIlIIIlI.get() : null;
            if (lllllllllllllllllIIlIIlIlIlIIIIl == null) {
                lllllllllllllllllIIlIIlIlIlIIIIl = new NativeMappedConverter(lllllllllllllllllIIlIIlIlIIlllll);
                converters.put(lllllllllllllllllIIlIIlIlIIlllll, new SoftReference<NativeMappedConverter>(lllllllllllllllllIIlIIlIlIlIIIIl));
            }
            return lllllllllllllllllIIlIIlIlIlIIIIl;
        }
    }

    @Override
    public Object fromNative(Object lllllllllllllllllIIlIIlIlIIIIIlI, FromNativeContext lllllllllllllllllIIlIIlIlIIIIlII) {
        NativeMappedConverter lllllllllllllllllIIlIIlIlIIIIllI;
        return lllllllllllllllllIIlIIlIlIIIIllI.instance.fromNative(lllllllllllllllllIIlIIlIlIIIIIlI, lllllllllllllllllIIlIIlIlIIIIlII);
    }

    @Override
    public Object toNative(Object lllllllllllllllllIIlIIlIIllllIlI, ToNativeContext lllllllllllllllllIIlIIlIIllllIIl) {
        if (lllllllllllllllllIIlIIlIIllllIlI == null) {
            NativeMappedConverter lllllllllllllllllIIlIIlIIllllIll;
            if (Pointer.class.isAssignableFrom(lllllllllllllllllIIlIIlIIllllIll.nativeType)) {
                return null;
            }
            lllllllllllllllllIIlIIlIIllllIlI = lllllllllllllllllIIlIIlIIllllIll.defaultValue();
        }
        return ((NativeMapped)lllllllllllllllllIIlIIlIIllllIlI).toNative();
    }

    @Override
    public Class<?> nativeType() {
        NativeMappedConverter lllllllllllllllllIIlIIlIIlllllll;
        return lllllllllllllllllIIlIIlIIlllllll.nativeType;
    }

    public NativeMapped defaultValue() {
        NativeMappedConverter lllllllllllllllllIIlIIlIlIIIllIl;
        try {
            return (NativeMapped)lllllllllllllllllIIlIIlIlIIIllIl.type.newInstance();
        }
        catch (InstantiationException lllllllllllllllllIIlIIlIlIIlIIII) {
            String lllllllllllllllllIIlIIlIlIIlIIIl = String.valueOf(new StringBuilder().append("Can't create an instance of ").append(lllllllllllllllllIIlIIlIlIIIllIl.type).append(", requires a no-arg constructor: ").append(lllllllllllllllllIIlIIlIlIIlIIII));
            throw new IllegalArgumentException(lllllllllllllllllIIlIIlIlIIlIIIl);
        }
        catch (IllegalAccessException lllllllllllllllllIIlIIlIlIIIlllI) {
            String lllllllllllllllllIIlIIlIlIIIllll = String.valueOf(new StringBuilder().append("Not allowed to create an instance of ").append(lllllllllllllllllIIlIIlIlIIIllIl.type).append(", requires a public, no-arg constructor: ").append(lllllllllllllllllIIlIIlIlIIIlllI));
            throw new IllegalArgumentException(lllllllllllllllllIIlIIlIlIIIllll);
        }
    }
}

