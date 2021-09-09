/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Structure;
import java.lang.reflect.Field;

public class StructureReadContext
extends FromNativeContext {
    private /* synthetic */ Field field;
    private /* synthetic */ Structure structure;

    public Field getField() {
        StructureReadContext lllllllllllllllllllIIIlllllIlIIl;
        return lllllllllllllllllllIIIlllllIlIIl.field;
    }

    StructureReadContext(Structure lllllllllllllllllllIIIlllllIllll, Field lllllllllllllllllllIIIllllllIIIl) {
        super(lllllllllllllllllllIIIllllllIIIl.getType());
        StructureReadContext lllllllllllllllllllIIIllllllIIll;
        lllllllllllllllllllIIIllllllIIll.structure = lllllllllllllllllllIIIlllllIllll;
        lllllllllllllllllllIIIllllllIIll.field = lllllllllllllllllllIIIllllllIIIl;
    }

    public Structure getStructure() {
        StructureReadContext lllllllllllllllllllIIIlllllIlIll;
        return lllllllllllllllllllIIIlllllIlIll.structure;
    }
}

