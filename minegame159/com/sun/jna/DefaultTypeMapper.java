/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.FromNativeConverter;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeConverter;
import com.sun.jna.TypeMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultTypeMapper
implements TypeMapper {
    private /* synthetic */ List<Entry> toNativeConverters;
    private /* synthetic */ List<Entry> fromNativeConverters;

    private Class<?> getAltClass(Class<?> lllIIIlIIlllIlI) {
        if (lllIIIlIIlllIlI == Boolean.class) {
            return Boolean.TYPE;
        }
        if (lllIIIlIIlllIlI == Boolean.TYPE) {
            return Boolean.class;
        }
        if (lllIIIlIIlllIlI == Byte.class) {
            return Byte.TYPE;
        }
        if (lllIIIlIIlllIlI == Byte.TYPE) {
            return Byte.class;
        }
        if (lllIIIlIIlllIlI == Character.class) {
            return Character.TYPE;
        }
        if (lllIIIlIIlllIlI == Character.TYPE) {
            return Character.class;
        }
        if (lllIIIlIIlllIlI == Short.class) {
            return Short.TYPE;
        }
        if (lllIIIlIIlllIlI == Short.TYPE) {
            return Short.class;
        }
        if (lllIIIlIIlllIlI == Integer.class) {
            return Integer.TYPE;
        }
        if (lllIIIlIIlllIlI == Integer.TYPE) {
            return Integer.class;
        }
        if (lllIIIlIIlllIlI == Long.class) {
            return Long.TYPE;
        }
        if (lllIIIlIIlllIlI == Long.TYPE) {
            return Long.class;
        }
        if (lllIIIlIIlllIlI == Float.class) {
            return Float.TYPE;
        }
        if (lllIIIlIIlllIlI == Float.TYPE) {
            return Float.class;
        }
        if (lllIIIlIIlllIlI == Double.class) {
            return Double.TYPE;
        }
        if (lllIIIlIIlllIlI == Double.TYPE) {
            return Double.class;
        }
        return null;
    }

    public void addFromNativeConverter(Class<?> lllIIIlIIlIIIll, FromNativeConverter lllIIIlIIlIIllI) {
        DefaultTypeMapper lllIIIlIIlIIlII;
        lllIIIlIIlIIlII.fromNativeConverters.add(new Entry(lllIIIlIIlIIIll, lllIIIlIIlIIllI));
        Class<?> lllIIIlIIlIIlIl = lllIIIlIIlIIlII.getAltClass(lllIIIlIIlIIIll);
        if (lllIIIlIIlIIlIl != null) {
            lllIIIlIIlIIlII.fromNativeConverters.add(new Entry(lllIIIlIIlIIlIl, lllIIIlIIlIIllI));
        }
    }

    @Override
    public FromNativeConverter getFromNativeConverter(Class<?> lllIIIlIIIIlIII) {
        DefaultTypeMapper lllIIIlIIIIIlll;
        return (FromNativeConverter)lllIIIlIIIIIlll.lookupConverter(lllIIIlIIIIlIII, lllIIIlIIIIIlll.fromNativeConverters);
    }

    public void addToNativeConverter(Class<?> lllIIIlIIlIllll, ToNativeConverter lllIIIlIIllIIlI) {
        DefaultTypeMapper lllIIIlIIllIlII;
        lllIIIlIIllIlII.toNativeConverters.add(new Entry(lllIIIlIIlIllll, lllIIIlIIllIIlI));
        Class<?> lllIIIlIIllIIIl = lllIIIlIIllIlII.getAltClass(lllIIIlIIlIllll);
        if (lllIIIlIIllIIIl != null) {
            lllIIIlIIllIlII.toNativeConverters.add(new Entry(lllIIIlIIllIIIl, lllIIIlIIllIIlI));
        }
    }

    @Override
    public ToNativeConverter getToNativeConverter(Class<?> lllIIIlIIIIIIII) {
        DefaultTypeMapper lllIIIlIIIIIIll;
        return (ToNativeConverter)lllIIIlIIIIIIll.lookupConverter(lllIIIlIIIIIIII, lllIIIlIIIIIIll.toNativeConverters);
    }

    public void addTypeConverter(Class<?> lllIIIlIIIllIIl, TypeConverter lllIIIlIIIllIll) {
        DefaultTypeMapper lllIIIlIIIlllIl;
        lllIIIlIIIlllIl.addFromNativeConverter(lllIIIlIIIllIIl, lllIIIlIIIllIll);
        lllIIIlIIIlllIl.addToNativeConverter(lllIIIlIIIllIIl, lllIIIlIIIllIll);
    }

    private Object lookupConverter(Class<?> lllIIIlIIIIllll, Collection<? extends Entry> lllIIIlIIIlIIII) {
        for (Entry entry : lllIIIlIIIlIIII) {
            if (!entry.type.isAssignableFrom(lllIIIlIIIIllll)) continue;
            return entry.converter;
        }
        return null;
    }

    public DefaultTypeMapper() {
        DefaultTypeMapper lllIIIlIIllllIl;
        lllIIIlIIllllIl.toNativeConverters = new ArrayList<Entry>();
        lllIIIlIIllllIl.fromNativeConverters = new ArrayList<Entry>();
    }

    private static class Entry {
        public /* synthetic */ Class<?> type;
        public /* synthetic */ Object converter;

        public Entry(Class<?> llIlIllIIIIIIII, Object llIlIlIllllllll) {
            Entry llIlIlIlllllllI;
            llIlIlIlllllllI.type = llIlIllIIIIIIII;
            llIlIlIlllllllI.converter = llIlIlIllllllll;
        }
    }
}

