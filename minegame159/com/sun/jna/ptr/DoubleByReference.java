/*
 * Decompiled with CFR 0.151.
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class DoubleByReference
extends ByReference {
    public double getValue() {
        return this.getPointer().getDouble(0L);
    }

    public DoubleByReference(double d) {
        super(8);
        this.setValue(d);
    }

    public void setValue(double d) {
        this.getPointer().setDouble(0L, d);
    }

    public DoubleByReference() {
        this(0.0);
    }
}

