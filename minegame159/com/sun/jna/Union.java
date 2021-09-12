/*
 * Decompiled with CFR 0.151.
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
    private Structure.StructField activeField;

    public void setType(String string) {
        this.ensureAllocated();
        Structure.StructField structField = this.fields().get(string);
        if (structField == null) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No field named ").append(string).append(" in ").append(this)));
        }
        this.activeField = structField;
    }

    protected Union(Pointer pointer) {
        super(pointer);
    }

    protected Union(Pointer pointer, int n) {
        super(pointer, n);
    }

    @Override
    protected int getNativeAlignment(Class<?> clazz, Object object, boolean bl) {
        return super.getNativeAlignment(clazz, object, true);
    }

    private Structure.StructField findField(Class<?> clazz) {
        this.ensureAllocated();
        for (Structure.StructField structField : this.fields().values()) {
            if (!structField.type.isAssignableFrom(clazz)) continue;
            return structField;
        }
        return null;
    }

    protected Union(Pointer pointer, int n, TypeMapper typeMapper) {
        super(pointer, n, typeMapper);
    }

    @Override
    public void writeField(String string, Object object) {
        this.ensureAllocated();
        this.setType(string);
        super.writeField(string, object);
    }

    protected Union() {
    }

    @Override
    public void writeField(String string) {
        this.ensureAllocated();
        this.setType(string);
        super.writeField(string);
    }

    public Object getTypedValue(Class<?> clazz) {
        this.ensureAllocated();
        for (Structure.StructField structField : this.fields().values()) {
            if (structField.type != clazz) continue;
            this.activeField = structField;
            this.read();
            return this.getFieldValue(this.activeField.field);
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No field of type ").append(clazz).append(" in ").append(this)));
    }

    @Override
    protected List<String> getFieldOrder() {
        List<Field> list = this.getFieldList();
        ArrayList<String> arrayList = new ArrayList<String>(list.size());
        for (Field field : list) {
            arrayList.add(field.getName());
        }
        return arrayList;
    }

    @Override
    protected void writeField(Structure.StructField structField) {
        if (structField == this.activeField) {
            super.writeField(structField);
        }
    }

    public void setType(Class<?> clazz) {
        this.ensureAllocated();
        for (Structure.StructField structField : this.fields().values()) {
            if (structField.type != clazz) continue;
            this.activeField = structField;
            return;
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No field of type ").append(clazz).append(" in ").append(this)));
    }

    @Override
    protected Object readField(Structure.StructField structField) {
        if (structField == this.activeField || !Structure.class.isAssignableFrom(structField.type) && !String.class.isAssignableFrom(structField.type) && !WString.class.isAssignableFrom(structField.type)) {
            return super.readField(structField);
        }
        return null;
    }

    protected Union(TypeMapper typeMapper) {
        super(typeMapper);
    }

    public Object setTypedValue(Object object) {
        Structure.StructField structField = this.findField(object.getClass());
        if (structField != null) {
            this.activeField = structField;
            this.setFieldValue(structField.field, object);
            return this;
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No field of type ").append(object.getClass()).append(" in ").append(this)));
    }

    @Override
    public Object readField(String string) {
        this.ensureAllocated();
        this.setType(string);
        return super.readField(string);
    }
}

