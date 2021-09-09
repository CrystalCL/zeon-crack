/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna.win32;

import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.FromNativeContext;
import com.sun.jna.StringArray;
import com.sun.jna.ToNativeContext;
import com.sun.jna.TypeConverter;
import com.sun.jna.TypeMapper;
import com.sun.jna.WString;

public class W32APITypeMapper
extends DefaultTypeMapper {
    public static final /* synthetic */ TypeMapper ASCII;
    public static final /* synthetic */ TypeMapper DEFAULT;
    public static final /* synthetic */ TypeMapper UNICODE;

    protected W32APITypeMapper(boolean lllIIllllIllllI) {
        W32APITypeMapper lllIIlllllIIIlI;
        if (lllIIllllIllllI) {
            TypeConverter lllIIlllllIIIll = new TypeConverter(){
                {
                    1 lllllllllllllllllIlIlIIlIIllIlII;
                }

                @Override
                public Object toNative(Object lllllllllllllllllIlIlIIlIIlIlllI, ToNativeContext lllllllllllllllllIlIlIIlIIlIllll) {
                    if (lllllllllllllllllIlIlIIlIIlIlllI == null) {
                        return null;
                    }
                    if (lllllllllllllllllIlIlIIlIIlIlllI instanceof String[]) {
                        return new StringArray((String[])lllllllllllllllllIlIlIIlIIlIlllI, true);
                    }
                    return new WString(lllllllllllllllllIlIlIIlIIlIlllI.toString());
                }

                @Override
                public Object fromNative(Object lllllllllllllllllIlIlIIlIIlIlIIl, FromNativeContext lllllllllllllllllIlIlIIlIIlIlIlI) {
                    if (lllllllllllllllllIlIlIIlIIlIlIIl == null) {
                        return null;
                    }
                    return lllllllllllllllllIlIlIIlIIlIlIIl.toString();
                }

                @Override
                public Class<?> nativeType() {
                    return WString.class;
                }
            };
            lllIIlllllIIIlI.addTypeConverter(String.class, lllIIlllllIIIll);
            lllIIlllllIIIlI.addToNativeConverter(String[].class, lllIIlllllIIIll);
        }
        TypeConverter lllIIlllllIIIII = new TypeConverter(){
            {
                2 lIIllIIlIIlIlIl;
            }

            @Override
            public Class<?> nativeType() {
                return Integer.class;
            }

            @Override
            public Object fromNative(Object lIIllIIlIIIlIlI, FromNativeContext lIIllIIlIIIlIIl) {
                return (Integer)lIIllIIlIIIlIlI != 0 ? Boolean.TRUE : Boolean.FALSE;
            }

            @Override
            public Object toNative(Object lIIllIIlIIIllIl, ToNativeContext lIIllIIlIIIlllI) {
                return Boolean.TRUE.equals(lIIllIIlIIIllIl) ? 1 : 0;
            }
        };
        lllIIlllllIIIlI.addTypeConverter(Boolean.class, lllIIlllllIIIII);
    }

    static {
        UNICODE = new W32APITypeMapper(true);
        ASCII = new W32APITypeMapper(false);
        DEFAULT = Boolean.getBoolean("w32.ascii") ? ASCII : UNICODE;
    }
}

