/*
 * Decompiled with CFR 0.151.
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Structure;
import java.lang.reflect.Field;

public class StructureReadContext
extends FromNativeContext {
    private Field field;
    private Structure structure;

    public Structure getStructure() {
        return this.structure;
    }

    public Field getField() {
        return this.field;
    }

    StructureReadContext(Structure structure, Field field) {
        super(field.getType());
        this.structure = structure;
        this.field = field;
    }
}

