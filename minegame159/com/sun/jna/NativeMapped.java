/*
 * Decompiled with CFR 0.151.
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;

public interface NativeMapped {
    public Object toNative();

    public Class<?> nativeType();

    public Object fromNative(Object var1, FromNativeContext var2);
}

