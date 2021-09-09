/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.TypeMapper;
import com.sun.jna.WString;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Union
extends Structure {
    private /* synthetic */ Structure.StructField activeField;

    protected Union(Pointer llllllllllllllllllIllllIllIlIIIl, int llllllllllllllllllIllllIllIlIlII, TypeMapper llllllllllllllllllIllllIllIlIIll) {
        super(llllllllllllllllllIllllIllIlIIIl, llllllllllllllllllIllllIllIlIlII, llllllllllllllllllIllllIllIlIIll);
        Union llllllllllllllllllIllllIllIlIllI;
    }

    @Override
    protected List<String> getFieldOrder() {
        Union llllllllllllllllllIllllIllIIIlIl;
        List<Field> llllllllllllllllllIllllIllIIIlll = llllllllllllllllllIllllIllIIIlIl.getFieldList();
        ArrayList<String> llllllllllllllllllIllllIllIIIllI = new ArrayList<String>(llllllllllllllllllIllllIllIIIlll.size());
        for (Field llllllllllllllllllIllllIllIIlIIl : llllllllllllllllllIllllIllIIIlll) {
            llllllllllllllllllIllllIllIIIllI.add(llllllllllllllllllIllllIllIIlIIl.getName());
        }
        return llllllllllllllllllIllllIllIIIllI;
    }

    public Object setTypedValue(Object llllllllllllllllllIllllIlIIIlIII) {
        Union llllllllllllllllllIllllIlIIIIllI;
        Structure.StructField llllllllllllllllllIllllIlIIIIlll = llllllllllllllllllIllllIlIIIIllI.findField(llllllllllllllllllIllllIlIIIlIII.getClass());
        if (llllllllllllllllllIllllIlIIIIlll != null) {
            llllllllllllllllllIllllIlIIIIllI.activeField = llllllllllllllllllIllllIlIIIIlll;
            llllllllllllllllllIllllIlIIIIllI.setFieldValue(llllllllllllllllllIllllIlIIIIlll.field, llllllllllllllllllIllllIlIIIlIII);
            return llllllllllllllllllIllllIlIIIIllI;
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No field of type ").append(llllllllllllllllllIllllIlIIIlIII.getClass()).append(" in ").append(llllllllllllllllllIllllIlIIIIllI)));
    }

    private Structure.StructField findField(Class<?> llllllllllllllllllIllllIIllllIll) {
        Union llllllllllllllllllIllllIIllllllI;
        llllllllllllllllllIllllIIllllllI.ensureAllocated();
        for (Structure.StructField llllllllllllllllllIllllIIlllllll : llllllllllllllllllIllllIIllllllI.fields().values()) {
            if (!llllllllllllllllllIllllIIlllllll.type.isAssignableFrom(llllllllllllllllllIllllIIllllIll)) continue;
            return llllllllllllllllllIllllIIlllllll;
        }
        return null;
    }

    public Object getTypedValue(Class<?> llllllllllllllllllIllllIlIIIllll) {
        Union llllllllllllllllllIllllIlIIlIIII;
        llllllllllllllllllIllllIlIIlIIII.ensureAllocated();
        for (Structure.StructField llllllllllllllllllIllllIlIIlIIll : llllllllllllllllllIllllIlIIlIIII.fields().values()) {
            if (llllllllllllllllllIllllIlIIlIIll.type != llllllllllllllllllIllllIlIIIllll) continue;
            llllllllllllllllllIllllIlIIlIIII.activeField = llllllllllllllllllIllllIlIIlIIll;
            llllllllllllllllllIllllIlIIlIIII.read();
            return llllllllllllllllllIllllIlIIlIIII.getFieldValue(llllllllllllllllllIllllIlIIlIIII.activeField.field);
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No field of type ").append(llllllllllllllllllIllllIlIIIllll).append(" in ").append(llllllllllllllllllIllllIlIIlIIII)));
    }

    @Override
    public void writeField(String llllllllllllllllllIllllIlIlIIIll) {
        Union llllllllllllllllllIllllIlIlIIlII;
        llllllllllllllllllIllllIlIlIIlII.ensureAllocated();
        llllllllllllllllllIllllIlIlIIlII.setType(llllllllllllllllllIllllIlIlIIIll);
        super.writeField(llllllllllllllllllIllllIlIlIIIll);
    }

    protected Union(TypeMapper llllllllllllllllllIllllIllIlllIl) {
        super(llllllllllllllllllIllllIllIlllIl);
        Union llllllllllllllllllIllllIllIllllI;
    }

    public void setType(Class<?> llllllllllllllllllIllllIlIlllIII) {
        Union llllllllllllllllllIllllIlIlllIIl;
        llllllllllllllllllIllllIlIlllIIl.ensureAllocated();
        for (Structure.StructField llllllllllllllllllIllllIlIllllII : llllllllllllllllllIllllIlIlllIIl.fields().values()) {
            if (llllllllllllllllllIllllIlIllllII.type != llllllllllllllllllIllllIlIlllIII) continue;
            llllllllllllllllllIllllIlIlllIIl.activeField = llllllllllllllllllIllllIlIllllII;
            return;
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No field of type ").append(llllllllllllllllllIllllIlIlllIII).append(" in ").append(llllllllllllllllllIllllIlIlllIIl)));
    }

    protected Union() {
        Union llllllllllllllllllIllllIllllIIII;
    }

    protected Union(Pointer llllllllllllllllllIllllIlllIIIlI, int llllllllllllllllllIllllIlllIIIIl) {
        super(llllllllllllllllllIllllIlllIIIlI, llllllllllllllllllIllllIlllIIIIl);
        Union llllllllllllllllllIllllIlllIIIll;
    }

    @Override
    protected Object readField(Structure.StructField llllllllllllllllllIllllIIllIlIlI) {
        Union llllllllllllllllllIllllIIllIllIl;
        if (llllllllllllllllllIllllIIllIlIlI == llllllllllllllllllIllllIIllIllIl.activeField || !Structure.class.isAssignableFrom(llllllllllllllllllIllllIIllIlIlI.type) && !String.class.isAssignableFrom(llllllllllllllllllIllllIIllIlIlI.type) && !WString.class.isAssignableFrom(llllllllllllllllllIllllIIllIlIlI.type)) {
            return super.readField(llllllllllllllllllIllllIIllIlIlI);
        }
        return null;
    }

    @Override
    public void writeField(String llllllllllllllllllIllllIlIIlllII, Object llllllllllllllllllIllllIlIIllIII) {
        Union llllllllllllllllllIllllIlIIllIlI;
        llllllllllllllllllIllllIlIIllIlI.ensureAllocated();
        llllllllllllllllllIllllIlIIllIlI.setType(llllllllllllllllllIllllIlIIlllII);
        super.writeField(llllllllllllllllllIllllIlIIlllII, llllllllllllllllllIllllIlIIllIII);
    }

    @Override
    protected int getNativeAlignment(Class<?> llllllllllllllllllIllllIIlIlllII, Object llllllllllllllllllIllllIIlIllIll, boolean llllllllllllllllllIllllIIlIlllll) {
        Union llllllllllllllllllIllllIIllIIIll;
        return super.getNativeAlignment(llllllllllllllllllIllllIIlIlllII, llllllllllllllllllIllllIIlIllIll, true);
    }

    protected Union(Pointer llllllllllllllllllIllllIlllIlIlI) {
        super(llllllllllllllllllIllllIlllIlIlI);
        Union llllllllllllllllllIllllIlllIlIll;
    }

    public void setType(String llllllllllllllllllIllllIlIlIlllI) {
        Union llllllllllllllllllIllllIlIlIllll;
        llllllllllllllllllIllllIlIlIllll.ensureAllocated();
        Structure.StructField llllllllllllllllllIllllIlIllIIII = llllllllllllllllllIllllIlIlIllll.fields().get(llllllllllllllllllIllllIlIlIlllI);
        if (llllllllllllllllllIllllIlIllIIII == null) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No field named ").append(llllllllllllllllllIllllIlIlIlllI).append(" in ").append(llllllllllllllllllIllllIlIlIllll)));
        }
        llllllllllllllllllIllllIlIlIllll.activeField = llllllllllllllllllIllllIlIllIIII;
    }

    @Override
    protected void writeField(Structure.StructField llllllllllllllllllIllllIIlllIlIl) {
        Union llllllllllllllllllIllllIIlllIllI;
        if (llllllllllllllllllIllllIIlllIlIl == llllllllllllllllllIllllIIlllIllI.activeField) {
            super.writeField(llllllllllllllllllIllllIIlllIlIl);
        }
    }

    @Override
    public Object readField(String llllllllllllllllllIllllIlIlIIlll) {
        Union llllllllllllllllllIllllIlIlIlIlI;
        llllllllllllllllllIllllIlIlIlIlI.ensureAllocated();
        llllllllllllllllllIllllIlIlIlIlI.setType(llllllllllllllllllIllllIlIlIIlll);
        return super.readField(llllllllllllllllllIllllIlIlIIlll);
    }
}

