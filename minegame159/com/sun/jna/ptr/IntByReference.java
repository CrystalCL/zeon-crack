/*
 * Decompiled with CFR 0.151.
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class IntByReference
extends ByReference {
    public IntByReference(int n) {
        super(4);
        this.setValue(n);
    }

    public int getValue() {
        return this.getPointer().getInt(0L);
    }

    public IntByReference() {
        this(0);
    }

    public void setValue(int n) {
        this.getPointer().setInt(0L, n);
    }
}

