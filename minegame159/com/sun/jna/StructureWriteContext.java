/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Structure;
import com.sun.jna.ToNativeContext;
import java.lang.reflect.Field;

public class StructureWriteContext
extends ToNativeContext {
    private /* synthetic */ Structure struct;
    private /* synthetic */ Field field;

    public Field getField() {
        StructureWriteContext llllllllllllllllIllIIIlIIIlIlllI;
        return llllllllllllllllIllIIIlIIIlIlllI.field;
    }

    public Structure getStructure() {
        StructureWriteContext llllllllllllllllIllIIIlIIIllIIIl;
        return llllllllllllllllIllIIIlIIIllIIIl.struct;
    }

    StructureWriteContext(Structure llllllllllllllllIllIIIlIIIllIlII, Field llllllllllllllllIllIIIlIIIllIIll) {
        StructureWriteContext llllllllllllllllIllIIIlIIIlllIII;
        llllllllllllllllIllIIIlIIIlllIII.struct = llllllllllllllllIllIIIlIIIllIlII;
        llllllllllllllllIllIIIlIIIlllIII.field = llllllllllllllllIllIIIlIIIllIIll;
    }
}

