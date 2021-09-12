/*
 * Decompiled with CFR 0.151.
 */
package com.sun.jna;

import com.sun.jna.Structure;
import com.sun.jna.ToNativeContext;
import java.lang.reflect.Field;

public class StructureWriteContext
extends ToNativeContext {
    private Structure struct;
    private Field field;

    StructureWriteContext(Structure structure, Field field) {
        this.struct = structure;
        this.field = field;
    }

    public Field getField() {
        return this.field;
    }

    public Structure getStructure() {
        return this.struct;
    }
}

