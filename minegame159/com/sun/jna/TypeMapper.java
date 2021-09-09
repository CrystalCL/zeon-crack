/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.FromNativeConverter;
import com.sun.jna.ToNativeConverter;

public interface TypeMapper {
    public ToNativeConverter getToNativeConverter(Class<?> var1);

    public FromNativeConverter getFromNativeConverter(Class<?> var1);
}

