/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Callback;
import com.sun.jna.FromNativeContext;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.Function;
import com.sun.jna.IntegerType;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.NativeString;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.StructureReadContext;
import com.sun.jna.StructureWriteContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeMapper;
import com.sun.jna.Union;
import com.sun.jna.WString;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class Structure {
    private /* synthetic */ int size;
    static final /* synthetic */ Map<Class<?>, LayoutInfo> layoutInfo;
    private /* synthetic */ boolean autoRead;
    private final /* synthetic */ Map<String, Object> nativeStrings;
    public static final /* synthetic */ int ALIGN_DEFAULT;
    private static final /* synthetic */ ThreadLocal<Map<Pointer, Structure>> reads;
    public static final /* synthetic */ int ALIGN_NONE;
    private /* synthetic */ Pointer memory;
    private static final /* synthetic */ Pointer PLACEHOLDER_MEMORY;
    private /* synthetic */ boolean autoWrite;
    protected static final /* synthetic */ int CALCULATE_SIZE;
    private static final /* synthetic */ ThreadLocal<Set<Structure>> busy;
    private /* synthetic */ int actualAlignType;
    private /* synthetic */ boolean readCalled;
    private /* synthetic */ Map<String, StructField> structFields;
    static final /* synthetic */ Map<Class<?>, List<String>> fieldOrder;
    private /* synthetic */ Structure[] array;
    public static final /* synthetic */ int ALIGN_GNUC;
    public static final /* synthetic */ int ALIGN_MSVC;
    private /* synthetic */ long typeInfo;
    private /* synthetic */ int alignType;
    private /* synthetic */ TypeMapper typeMapper;
    private /* synthetic */ int structAlignment;
    private /* synthetic */ String encoding;

    protected void setStringEncoding(String lIlIlIlIIIIll) {
        lIlIlIlIIIllI.encoding = lIlIlIlIIIIll;
    }

    protected Memory autoAllocate(int lIlIlIIllIllI) {
        return new AutoAllocated(lIlIlIIllIllI);
    }

    Map<String, StructField> fields() {
        Structure lIlIlIlIlIllI;
        return lIlIlIlIlIllI.structFields;
    }

    static Set<Structure> busy() {
        return busy.get();
    }

    private void layoutChanged() {
        Structure lIlIlIlIIlIIl;
        if (lIlIlIlIIlIIl.size != -1) {
            lIlIlIlIIlIIl.size = -1;
            if (lIlIlIlIIlIIl.memory instanceof AutoAllocated) {
                lIlIlIlIIlIIl.memory = null;
            }
            lIlIlIlIIlIIl.ensureAllocated();
        }
    }

    public int size() {
        Structure lIlIIlllllIIl;
        lIlIIlllllIIl.ensureAllocated();
        return lIlIIlllllIIl.size;
    }

    protected abstract List<String> getFieldOrder();

    public String toString(boolean lIIlIlIIIlllI) {
        Structure lIIlIlIIIllIl;
        return lIIlIlIIIllIl.toString(0, true, lIIlIlIIIlllI);
    }

    public int hashCode() {
        Structure lIIlIIIIlllll;
        Pointer lIIlIIIIllllI = lIIlIIIIlllll.getPointer();
        if (lIIlIIIIllllI != null) {
            return lIIlIIIIlllll.getPointer().hashCode();
        }
        return lIIlIIIIlllll.getClass().hashCode();
    }

    public static Structure newInstance(Class<?> lIIIlllIIllIl, Pointer lIIIlllIIllII) throws IllegalArgumentException {
        try {
            Constructor<?> lIIIlllIlIlll = lIIIlllIIllIl.getConstructor(Pointer.class);
            return (Structure)lIIIlllIlIlll.newInstance(lIIIlllIIllII);
        }
        catch (NoSuchMethodException lIIIlllIlIlll) {
        }
        catch (SecurityException lIIIlllIlIlll) {
        }
        catch (InstantiationException lIIIlllIlIlIl) {
            String lIIIlllIlIllI = String.valueOf(new StringBuilder().append("Can't instantiate ").append(lIIIlllIIllIl));
            throw new IllegalArgumentException(lIIIlllIlIllI, lIIIlllIlIlIl);
        }
        catch (IllegalAccessException lIIIlllIlIIll) {
            String lIIIlllIlIlII = String.valueOf(new StringBuilder().append("Instantiation of ").append(lIIIlllIIllIl).append(" (Pointer) not allowed, is it public?"));
            throw new IllegalArgumentException(lIIIlllIlIlII, lIIIlllIlIIll);
        }
        catch (InvocationTargetException lIIIlllIlIIIl) {
            String lIIIlllIlIIlI = String.valueOf(new StringBuilder().append("Exception thrown while instantiating an instance of ").append(lIIIlllIIllIl));
            lIIIlllIlIIIl.printStackTrace();
            throw new IllegalArgumentException(lIIIlllIlIIlI, lIIIlllIlIIIl);
        }
        Structure lIIIlllIIlllI = Structure.newInstance(lIIIlllIIllIl);
        if (lIIIlllIIllII != PLACEHOLDER_MEMORY) {
            lIIIlllIIlllI.useMemory(lIIIlllIIllII);
        }
        return lIIIlllIIlllI;
    }

    public void write() {
        Structure lIlIIlIIIIlIl;
        if (lIlIIlIIIIlIl.memory == PLACEHOLDER_MEMORY) {
            return;
        }
        lIlIIlIIIIlIl.ensureAllocated();
        if (lIlIIlIIIIlIl instanceof ByValue) {
            lIlIIlIIIIlIl.getTypeInfo();
        }
        if (Structure.busy().contains(lIlIIlIIIIlIl)) {
            return;
        }
        Structure.busy().add(lIlIIlIIIIlIl);
        try {
            for (StructField lIlIIlIIIIllI : lIlIIlIIIIlIl.fields().values()) {
                if (lIlIIlIIIIllI.isVolatile) continue;
                lIlIIlIIIIlIl.writeField(lIlIIlIIIIllI);
            }
        }
        finally {
            Structure.busy().remove(lIlIIlIIIIlIl);
        }
    }

    public void autoWrite() {
        Structure lIIIllIIIlllI;
        if (lIIIllIIIlllI.getAutoWrite()) {
            lIIIllIIIlllI.write();
            if (lIIIllIIIlllI.array != null) {
                for (int lIIIllIIIllll = 1; lIIIllIIIllll < lIIIllIIIlllI.array.length; ++lIIIllIIIllll) {
                    lIIIllIIIlllI.array[lIIIllIIIllll].autoWrite();
                }
            }
        }
    }

    public boolean dataEquals(Structure lIIlIIIlllIlI) {
        Structure lIIlIIIllllIl;
        return lIIlIIIllllIl.dataEquals(lIIlIIIlllIlI, false);
    }

    private static void structureArrayCheck(Structure[] lIIIllIlIlIII) {
        if (ByReference[].class.isAssignableFrom(lIIIllIlIlIII.getClass())) {
            return;
        }
        Pointer lIIIllIlIlIlI = lIIIllIlIlIII[0].getPointer();
        int lIIIllIlIlIIl = lIIIllIlIlIII[0].size();
        for (int lIIIllIlIllII = 1; lIIIllIlIllII < lIIIllIlIlIII.length; ++lIIIllIlIllII) {
            if (lIIIllIlIlIII[lIIIllIlIllII].getPointer().peer == lIIIllIlIlIlI.peer + (long)(lIIIllIlIlIIl * lIIIllIlIllII)) continue;
            String lIIIllIlIllIl = String.valueOf(new StringBuilder().append("Structure array elements must use contiguous memory (bad backing address at Structure array index ").append(lIIIllIlIllII).append(")"));
            throw new IllegalArgumentException(lIIIllIlIllIl);
        }
    }

    protected List<Field> getFieldList() {
        Structure lIIllllIIIllI;
        ArrayList<Field> lIIllllIIlIII = new ArrayList<Field>();
        Class<?> lIIllllIIllII = lIIllllIIIllI.getClass();
        while (!lIIllllIIllII.equals(Structure.class)) {
            ArrayList<Field> lIIllllIlIIII = new ArrayList<Field>();
            Field[] lIIllllIIlllI = lIIllllIIllII.getDeclaredFields();
            for (int lIIllllIlIIlI = 0; lIIllllIlIIlI < lIIllllIIlllI.length; ++lIIllllIlIIlI) {
                int lIIllllIlIlII = lIIllllIIlllI[lIIllllIlIIlI].getModifiers();
                if (Modifier.isStatic(lIIllllIlIlII) || !Modifier.isPublic(lIIllllIlIlII)) continue;
                lIIllllIlIIII.add(lIIllllIIlllI[lIIllllIlIIlI]);
            }
            lIIllllIIlIII.addAll(0, lIIllllIlIIII);
            lIIllllIIllII = lIIllllIIllII.getSuperclass();
        }
        return lIIllllIIlIII;
    }

    public void autoRead() {
        Structure lIIIllIIllIlI;
        if (lIIIllIIllIlI.getAutoRead()) {
            lIIIllIIllIlI.read();
            if (lIIIllIIllIlI.array != null) {
                for (int lIIIllIIllIll = 1; lIIIllIIllIll < lIIIllIIllIlI.array.length; ++lIIIllIIllIll) {
                    lIIIllIIllIlI.array[lIIIllIIllIll].autoRead();
                }
            }
        }
    }

    private String toString(int lIIlIIllIIIlI, boolean lIIlIIllIIIIl, boolean lIIlIIllIIIII) {
        Structure lIIlIIllIIIll;
        lIIlIIllIIIll.ensureAllocated();
        String lIIlIIllIIlll = System.getProperty("line.separator");
        String lIIlIIllIIllI = String.valueOf(new StringBuilder().append(lIIlIIllIIIll.format(lIIlIIllIIIll.getClass())).append("(").append(lIIlIIllIIIll.getPointer()).append(")"));
        if (!(lIIlIIllIIIll.getPointer() instanceof Memory)) {
            lIIlIIllIIllI = String.valueOf(new StringBuilder().append(lIIlIIllIIllI).append(" (").append(lIIlIIllIIIll.size()).append(" bytes)"));
        }
        String lIIlIIllIIlIl = "";
        for (int lIIlIIlllIlII = 0; lIIlIIlllIlII < lIIlIIllIIIlI; ++lIIlIIlllIlII) {
            lIIlIIllIIlIl = String.valueOf(new StringBuilder().append(lIIlIIllIIlIl).append("  "));
        }
        String lIIlIIllIIlII = lIIlIIllIIlll;
        if (!lIIlIIllIIIIl) {
            lIIlIIllIIlII = "...}";
        } else {
            Iterator<StructField> lIIlIIllIllll = lIIlIIllIIIll.fields().values().iterator();
            while (lIIlIIllIllll.hasNext()) {
                StructField lIIlIIlllIIll = lIIlIIllIllll.next();
                Object lIIlIIlllIIlI = lIIlIIllIIIll.getFieldValue(lIIlIIlllIIll.field);
                String lIIlIIlllIIIl = lIIlIIllIIIll.format(lIIlIIlllIIll.type);
                String lIIlIIlllIIII = "";
                lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(lIIlIIllIIlIl));
                if (lIIlIIlllIIll.type.isArray() && lIIlIIlllIIlI != null) {
                    lIIlIIlllIIIl = lIIlIIllIIIll.format(lIIlIIlllIIll.type.getComponentType());
                    lIIlIIlllIIII = String.valueOf(new StringBuilder().append("[").append(Array.getLength(lIIlIIlllIIlI)).append("]"));
                }
                lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append("  ").append(lIIlIIlllIIIl).append(" ").append(lIIlIIlllIIll.name).append(lIIlIIlllIIII).append("@").append(Integer.toHexString(lIIlIIlllIIll.offset)));
                if (lIIlIIlllIIlI instanceof Structure) {
                    lIIlIIlllIIlI = ((Structure)lIIlIIlllIIlI).toString(lIIlIIllIIIlI + 1, !(lIIlIIlllIIlI instanceof ByReference), lIIlIIllIIIII);
                }
                lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append("="));
                lIIlIIllIIlII = lIIlIIlllIIlI instanceof Long ? String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(Long.toHexString((Long)lIIlIIlllIIlI))) : (lIIlIIlllIIlI instanceof Integer ? String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(Integer.toHexString((Integer)lIIlIIlllIIlI))) : (lIIlIIlllIIlI instanceof Short ? String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(Integer.toHexString(((Short)lIIlIIlllIIlI).shortValue()))) : (lIIlIIlllIIlI instanceof Byte ? String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(Integer.toHexString(((Byte)lIIlIIlllIIlI).byteValue()))) : String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(String.valueOf(lIIlIIlllIIlI).trim())))));
                lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(lIIlIIllIIlll));
                if (lIIlIIllIllll.hasNext()) continue;
                lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(lIIlIIllIIlIl).append("}"));
            }
        }
        if (lIIlIIllIIIlI == 0 && lIIlIIllIIIII) {
            int lIIlIIllIllIl = 4;
            lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(lIIlIIllIIlll).append("memory dump").append(lIIlIIllIIlll));
            byte[] lIIlIIllIllII = lIIlIIllIIIll.getPointer().getByteArray(0L, lIIlIIllIIIll.size());
            for (int lIIlIIllIlllI = 0; lIIlIIllIlllI < lIIlIIllIllII.length; ++lIIlIIllIlllI) {
                if (lIIlIIllIlllI % 4 == 0) {
                    lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append("["));
                }
                if (lIIlIIllIllII[lIIlIIllIlllI] >= 0 && lIIlIIllIllII[lIIlIIllIlllI] < 16) {
                    lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append("0"));
                }
                lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append(Integer.toHexString(lIIlIIllIllII[lIIlIIllIlllI] & 0xFF)));
                if (lIIlIIllIlllI % 4 != 3 || lIIlIIllIlllI >= lIIlIIllIllII.length - 1) continue;
                lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append("]").append(lIIlIIllIIlll));
            }
            lIIlIIllIIlII = String.valueOf(new StringBuilder().append(lIIlIIllIIlII).append("]"));
        }
        return String.valueOf(new StringBuilder().append(lIIlIIllIIllI).append(" {").append(lIIlIIllIIlII));
    }

    Object getFieldValue(Field lIlIIllIIllII) {
        Structure lIlIIllIIllIl;
        try {
            return lIlIIllIIllII.get(lIlIIllIIllIl);
        }
        catch (Exception lIlIIllIlIIII) {
            throw new Error(String.valueOf(new StringBuilder().append("Exception reading field '").append(lIlIIllIIllII.getName()).append("' in ").append(lIlIIllIIllIl.getClass())), lIlIIllIlIIII);
        }
    }

    static Structure updateStructureByReference(Class<?> lIlIIlIlIlIlI, Structure lIlIIlIlIIllI, Pointer lIlIIlIlIIlIl) {
        if (lIlIIlIlIIlIl == null) {
            lIlIIlIlIIllI = null;
        } else if (lIlIIlIlIIllI == null || !lIlIIlIlIIlIl.equals(lIlIIlIlIIllI.getPointer())) {
            Structure lIlIIlIlIlIll = Structure.reading().get(lIlIIlIlIIlIl);
            if (lIlIIlIlIlIll != null && lIlIIlIlIlIlI.equals(lIlIIlIlIlIll.getClass())) {
                lIlIIlIlIIllI = lIlIIlIlIlIll;
                lIlIIlIlIIllI.autoRead();
            } else {
                lIlIIlIlIIllI = Structure.newInstance(lIlIIlIlIlIlI, lIlIIlIlIIlIl);
                lIlIIlIlIIllI.conditionalAutoRead();
            }
        } else {
            lIlIIlIlIIllI.autoRead();
        }
        return lIlIIlIlIIllI;
    }

    private void validateField(String lIIllIIlIIlIl, Class<?> lIIllIIlIIlII) {
        ToNativeConverter lIIllIIlIllII;
        Structure lIIllIIlIIllI;
        if (lIIllIIlIIllI.typeMapper != null && (lIIllIIlIllII = lIIllIIlIIllI.typeMapper.getToNativeConverter(lIIllIIlIIlII)) != null) {
            lIIllIIlIIllI.validateField(lIIllIIlIIlIl, lIIllIIlIllII.nativeType());
            return;
        }
        if (lIIllIIlIIlII.isArray()) {
            lIIllIIlIIllI.validateField(lIIllIIlIIlIl, lIIllIIlIIlII.getComponentType());
        } else {
            try {
                lIIllIIlIIllI.getNativeSize(lIIllIIlIIlII);
            }
            catch (IllegalArgumentException lIIllIIlIlIlI) {
                String lIIllIIlIlIll = String.valueOf(new StringBuilder().append("Invalid Structure field in ").append(lIIllIIlIIllI.getClass()).append(", field name '").append(lIIllIIlIIlIl).append("' (").append(lIIllIIlIIlII).append("): ").append(lIIllIIlIlIlI.getMessage()));
                throw new IllegalArgumentException(lIIllIIlIlIll, lIIllIIlIlIlI);
            }
        }
    }

    public void writeField(String lIlIIIlIllIll, Object lIlIIIlIlllll) {
        Structure lIlIIIlIlllII;
        lIlIIIlIlllII.ensureAllocated();
        StructField lIlIIIlIllllI = lIlIIIlIlllII.fields().get(lIlIIIlIllIll);
        if (lIlIIIlIllllI == null) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No such field: ").append(lIlIIIlIllIll)));
        }
        lIlIIIlIlllII.setFieldValue(lIlIIIlIllllI.field, lIlIIIlIlllll);
        lIlIIIlIlllII.writeField(lIlIIIlIllllI);
    }

    private Object initializeField(Field lIIlIllIIIIlI, Class<?> lIIlIlIllllIl) {
        Structure lIIlIlIllllll;
        Object lIIlIllIIIIII = null;
        if (Structure.class.isAssignableFrom(lIIlIlIllllIl) && !ByReference.class.isAssignableFrom(lIIlIlIllllIl)) {
            try {
                lIIlIllIIIIII = Structure.newInstance(lIIlIlIllllIl, PLACEHOLDER_MEMORY);
                lIIlIlIllllll.setFieldValue(lIIlIllIIIIlI, lIIlIllIIIIII);
            }
            catch (IllegalArgumentException lIIlIllIIIlIl) {
                String lIIlIllIIIllI = "Can't determine size of nested structure";
                throw new IllegalArgumentException(lIIlIllIIIllI, lIIlIllIIIlIl);
            }
        } else if (NativeMapped.class.isAssignableFrom(lIIlIlIllllIl)) {
            NativeMappedConverter lIIlIllIIIlII = NativeMappedConverter.getInstance(lIIlIlIllllIl);
            lIIlIllIIIIII = lIIlIllIIIlII.defaultValue();
            lIIlIlIllllll.setFieldValue(lIIlIllIIIIlI, lIIlIllIIIIII);
        }
        return lIIlIllIIIIII;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<String> fieldOrder() {
        Structure lIIlllIlIlIII;
        Class<?> lIIlllIlIlIlI = lIIlllIlIlIII.getClass();
        Map<Class<?>, List<String>> lIIlllIlIIlIl = fieldOrder;
        synchronized (lIIlllIlIIlIl) {
            List<String> lIIlllIlIlllI = fieldOrder.get(lIIlllIlIlIlI);
            if (lIIlllIlIlllI == null) {
                lIIlllIlIlllI = lIIlllIlIlIII.getFieldOrder();
                fieldOrder.put(lIIlllIlIlIlI, lIIlllIlIlllI);
            }
            return lIIlllIlIlllI;
        }
    }

    private void initializeTypeMapper(TypeMapper lIlIlIlIIlllI) {
        Structure lIlIlIlIIllIl;
        if (lIlIlIlIIlllI == null) {
            lIlIlIlIIlllI = Native.getTypeMapper(lIlIlIlIIllIl.getClass());
        }
        lIlIlIlIIllIl.typeMapper = lIlIlIlIIlllI;
        lIlIlIlIIllIl.layoutChanged();
    }

    private LayoutInfo deriveLayout(boolean lIIlIllllIIll, boolean lIIlIlllIlIll) {
        Structure lIIlIllllIlII;
        int lIIlIllllIIIl = 0;
        List<Field> lIIlIllllIIII = lIIlIllllIlII.getFields(lIIlIllllIIll);
        if (lIIlIllllIIII == null) {
            return null;
        }
        LayoutInfo lIIlIlllIllll = new LayoutInfo();
        lIIlIlllIllll.alignType = lIIlIllllIlII.alignType;
        lIIlIlllIllll.typeMapper = lIIlIllllIlII.typeMapper;
        boolean lIIlIlllIlllI = true;
        for (Field lIIlIllllllIl : lIIlIllllIIII) {
            int lIIlIllllllII = lIIlIllllllIl.getModifiers();
            Class<?> lIIlIlllllIll = lIIlIllllllIl.getType();
            if (lIIlIlllllIll.isArray()) {
                lIIlIlllIllll.variable = true;
            }
            StructField lIIlIlllllIlI = new StructField();
            lIIlIlllllIlI.isVolatile = Modifier.isVolatile(lIIlIllllllII);
            lIIlIlllllIlI.isReadOnly = Modifier.isFinal(lIIlIllllllII);
            if (lIIlIlllllIlI.isReadOnly) {
                if (!Platform.RO_FIELDS) {
                    throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("This VM does not support read-only fields (field '").append(lIIlIllllllIl.getName()).append("' within ").append(lIIlIllllIlII.getClass()).append(")")));
                }
                lIIlIllllllIl.setAccessible(true);
            }
            lIIlIlllllIlI.field = lIIlIllllllIl;
            lIIlIlllllIlI.name = lIIlIllllllIl.getName();
            lIIlIlllllIlI.type = lIIlIlllllIll;
            if (Callback.class.isAssignableFrom(lIIlIlllllIll) && !lIIlIlllllIll.isInterface()) {
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Structure Callback field '").append(lIIlIllllllIl.getName()).append("' must be an interface")));
            }
            if (lIIlIlllllIll.isArray() && Structure.class.equals(lIIlIlllllIll.getComponentType())) {
                String lIIllIIIIIlII = "Nested Structure arrays must use a derived Structure type so that the size of the elements can be determined";
                throw new IllegalArgumentException(lIIllIIIIIlII);
            }
            int lIIlIlllllIIl = 1;
            if (Modifier.isPublic(lIIlIllllllIl.getModifiers())) {
                Object lIIlIlllllIII = lIIlIllllIlII.getFieldValue(lIIlIlllllIlI.field);
                if (lIIlIlllllIII == null && lIIlIlllllIll.isArray()) {
                    if (lIIlIllllIIll) {
                        throw new IllegalStateException("Array fields must be initialized");
                    }
                    return null;
                }
                Class<Object> lIIlIllllIlll = lIIlIlllllIll;
                if (NativeMapped.class.isAssignableFrom(lIIlIlllllIll)) {
                    NativeMappedConverter lIIllIIIIIIll = NativeMappedConverter.getInstance(lIIlIlllllIll);
                    lIIlIllllIlll = lIIllIIIIIIll.nativeType();
                    lIIlIlllllIlI.writeConverter = lIIllIIIIIIll;
                    lIIlIlllllIlI.readConverter = lIIllIIIIIIll;
                    lIIlIlllllIlI.context = new StructureReadContext(lIIlIllllIlII, lIIlIllllllIl);
                } else if (lIIlIllllIlII.typeMapper != null) {
                    ToNativeConverter lIIllIIIIIIIl = lIIlIllllIlII.typeMapper.getToNativeConverter(lIIlIlllllIll);
                    FromNativeConverter lIIllIIIIIIII = lIIlIllllIlII.typeMapper.getFromNativeConverter(lIIlIlllllIll);
                    if (lIIllIIIIIIIl != null && lIIllIIIIIIII != null) {
                        lIIlIllllIlll = (lIIlIlllllIII = lIIllIIIIIIIl.toNative(lIIlIlllllIII, new StructureWriteContext(lIIlIllllIlII, lIIlIlllllIlI.field))) != null ? lIIlIlllllIII.getClass() : Pointer.class;
                        lIIlIlllllIlI.writeConverter = lIIllIIIIIIIl;
                        lIIlIlllllIlI.readConverter = lIIllIIIIIIII;
                        lIIlIlllllIlI.context = new StructureReadContext(lIIlIllllIlII, lIIlIllllllIl);
                    } else if (lIIllIIIIIIIl != null || lIIllIIIIIIII != null) {
                        String lIIllIIIIIIlI = String.valueOf(new StringBuilder().append("Structures require bidirectional type conversion for ").append(lIIlIlllllIll));
                        throw new IllegalArgumentException(lIIllIIIIIIlI);
                    }
                }
                if (lIIlIlllllIII == null) {
                    lIIlIlllllIII = lIIlIllllIlII.initializeField(lIIlIlllllIlI.field, lIIlIlllllIll);
                }
                try {
                    lIIlIlllllIlI.size = lIIlIllllIlII.getNativeSize(lIIlIllllIlll, lIIlIlllllIII);
                    lIIlIlllllIIl = lIIlIllllIlII.getNativeAlignment(lIIlIllllIlll, lIIlIlllllIII, lIIlIlllIlllI);
                }
                catch (IllegalArgumentException lIIlIlllllllI) {
                    if (!lIIlIllllIIll && lIIlIllllIlII.typeMapper == null) {
                        return null;
                    }
                    String lIIlIllllllll = String.valueOf(new StringBuilder().append("Invalid Structure field in ").append(lIIlIllllIlII.getClass()).append(", field name '").append(lIIlIlllllIlI.name).append("' (").append(lIIlIlllllIlI.type).append("): ").append(lIIlIlllllllI.getMessage()));
                    throw new IllegalArgumentException(lIIlIllllllll, lIIlIlllllllI);
                }
                if (lIIlIlllllIIl == 0) {
                    throw new Error(String.valueOf(new StringBuilder().append("Field alignment is zero for field '").append(lIIlIlllllIlI.name).append("' within ").append(lIIlIllllIlII.getClass())));
                }
                lIIlIlllIllll.alignment = Math.max(lIIlIlllIllll.alignment, lIIlIlllllIIl);
                if (lIIlIllllIIIl % lIIlIlllllIIl != 0) {
                    lIIlIllllIIIl += lIIlIlllllIIl - lIIlIllllIIIl % lIIlIlllllIIl;
                }
                if (lIIlIllllIlII instanceof Union) {
                    lIIlIlllllIlI.offset = 0;
                    lIIlIllllIIIl = Math.max(lIIlIllllIIIl, lIIlIlllllIlI.size);
                } else {
                    lIIlIlllllIlI.offset = lIIlIllllIIIl;
                    lIIlIllllIIIl += lIIlIlllllIlI.size;
                }
                lIIlIlllIllll.fields.put(lIIlIlllllIlI.name, lIIlIlllllIlI);
                if (lIIlIlllIllll.typeInfoField == null || ((LayoutInfo)lIIlIlllIllll).typeInfoField.size < lIIlIlllllIlI.size || ((LayoutInfo)lIIlIlllIllll).typeInfoField.size == lIIlIlllllIlI.size && Structure.class.isAssignableFrom(lIIlIlllllIlI.type)) {
                    lIIlIlllIllll.typeInfoField = lIIlIlllllIlI;
                }
            }
            lIIlIlllIlllI = false;
        }
        if (lIIlIllllIIIl > 0) {
            int lIIlIllllIlIl = lIIlIllllIlII.addPadding(lIIlIllllIIIl, lIIlIlllIllll.alignment);
            if (lIIlIllllIlII instanceof ByValue && !lIIlIlllIlIll) {
                lIIlIllllIlII.getTypeInfo();
            }
            lIIlIlllIllll.size = lIIlIllllIlIl;
            return lIIlIlllIllll;
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Structure ").append(lIIlIllllIlII.getClass()).append(" has unknown or zero size (ensure all fields are public)")));
    }

    public static Structure newInstance(Class<?> lIIIlllIIIIIl) throws IllegalArgumentException {
        try {
            Structure lIIIlllIIIllI = (Structure)lIIIlllIIIIIl.newInstance();
            if (lIIIlllIIIllI instanceof ByValue) {
                lIIIlllIIIllI.allocateMemory();
            }
            return lIIIlllIIIllI;
        }
        catch (InstantiationException lIIIlllIIIlII) {
            String lIIIlllIIIlIl = String.valueOf(new StringBuilder().append("Can't instantiate ").append(lIIIlllIIIIIl));
            throw new IllegalArgumentException(lIIIlllIIIlIl, lIIIlllIIIlII);
        }
        catch (IllegalAccessException lIIIlllIIIIlI) {
            String lIIIlllIIIIll = String.valueOf(new StringBuilder().append("Instantiation of ").append(lIIIlllIIIIIl).append(" not allowed, is it public?"));
            throw new IllegalArgumentException(lIIIlllIIIIll, lIIIlllIIIIlI);
        }
    }

    public static void autoWrite(Structure[] lIIIllIIlIlII) {
        Structure.structureArrayCheck(lIIIllIIlIlII);
        if (lIIIllIIlIlII[0].array == lIIIllIIlIlII) {
            lIIIllIIlIlII[0].autoWrite();
        } else {
            for (int lIIIllIIlIlIl = 0; lIIIllIIlIlIl < lIIIllIIlIlII.length; ++lIIIllIIlIlIl) {
                if (lIIIllIIlIlII[lIIIllIIlIlIl] == null) continue;
                lIIIllIIlIlII[lIIIllIIlIlIl].autoWrite();
            }
        }
    }

    public void setAutoWrite(boolean lIIIllllIlllI) {
        lIIIllllIllll.autoWrite = lIIIllllIlllI;
    }

    private int addPadding(int lIIlIlIlIllII, int lIIlIlIlIlIll) {
        Structure lIIlIlIlIllIl;
        if (lIIlIlIlIllIl.actualAlignType != 1 && lIIlIlIlIllII % lIIlIlIlIlIll != 0) {
            lIIlIlIlIllII += lIIlIlIlIlIll - lIIlIlIlIllII % lIIlIlIlIlIll;
        }
        return lIIlIlIlIllII;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    int calculateSize(boolean lIIllIIlllIIl, boolean lIIllIIlllllI) {
        LayoutInfo lIIllIIlllIll;
        Structure lIIllIlIIIIII;
        int lIIllIIllllIl = -1;
        Class<?> lIIllIIllllII = lIIllIlIIIIII.getClass();
        Map<Class<?>, LayoutInfo> lIIllIIllIlII = layoutInfo;
        synchronized (lIIllIIllIlII) {
            LayoutInfo lIIllIlIIIIIl = layoutInfo.get(lIIllIIllllII);
        }
        if (lIIllIIlllIll == null || lIIllIlIIIIII.alignType != lIIllIIlllIll.alignType || lIIllIlIIIIII.typeMapper != lIIllIIlllIll.typeMapper) {
            lIIllIIlllIll = lIIllIlIIIIII.deriveLayout(lIIllIIlllIIl, lIIllIIlllllI);
        }
        if (lIIllIIlllIll != null) {
            lIIllIlIIIIII.structAlignment = lIIllIIlllIll.alignment;
            lIIllIlIIIIII.structFields = lIIllIIlllIll.fields;
            if (!lIIllIIlllIll.variable) {
                lIIllIIllIlII = layoutInfo;
                synchronized (lIIllIIllIlII) {
                    if (!layoutInfo.containsKey(lIIllIIllllII) || lIIllIlIIIIII.alignType != 0 || lIIllIlIIIIII.typeMapper != null) {
                        layoutInfo.put(lIIllIIllllII, lIIllIIlllIll);
                    }
                }
            }
            lIIllIIllllIl = lIIllIIlllIll.size;
        }
        return lIIllIIllllIl;
    }

    public boolean dataEquals(Structure lIIlIIIlIllII, boolean lIIlIIIllIIII) {
        byte[] lIIlIIIlIlllI;
        byte[] lIIlIIIlIllll;
        Structure lIIlIIIlIllIl;
        if (lIIlIIIllIIII) {
            lIIlIIIlIllII.getPointer().clear(lIIlIIIlIllII.size());
            lIIlIIIlIllII.write();
            lIIlIIIlIllIl.getPointer().clear(lIIlIIIlIllIl.size());
            lIIlIIIlIllIl.write();
        }
        if ((lIIlIIIlIllll = lIIlIIIlIllII.getPointer().getByteArray(0L, lIIlIIIlIllII.size())).length == (lIIlIIIlIlllI = lIIlIIIlIllIl.getPointer().getByteArray(0L, lIIlIIIlIllIl.size())).length) {
            for (int lIIlIIIllIIll = 0; lIIlIIIllIIll < lIIlIIIlIllll.length; ++lIIlIIIllIIll) {
                if (lIIlIIIlIllll[lIIlIIIllIIll] == lIIlIIIlIlllI[lIIlIIIllIIll]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private void allocateMemory(boolean lIlIlIIIIIIlI) {
        Structure lIlIlIIIIIlIl;
        lIlIlIIIIIlIl.allocateMemory(lIlIlIIIIIlIl.calculateSize(true, lIlIlIIIIIIlI));
    }

    public void setAutoSynch(boolean lIIIlllllllIl) {
        Structure lIIIlllllllII;
        lIIIlllllllII.setAutoRead(lIIIlllllllIl);
        lIIIlllllllII.setAutoWrite(lIIIlllllllIl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    StructField typeInfoField() {
        void lIIIllIllIlll;
        Map<Class<?>, LayoutInfo> lIIIllIllIlII = layoutInfo;
        synchronized (lIIIllIllIlII) {
            Structure lIIIllIllIllI;
            LayoutInfo lIIIllIlllIIl = layoutInfo.get(lIIIllIllIllI.getClass());
        }
        if (lIIIllIllIlll != null) {
            return ((LayoutInfo)lIIIllIllIlll).typeInfoField;
        }
        return null;
    }

    public Object readField(String lIlIIllIllIII) {
        Structure lIlIIllIlIllI;
        lIlIIllIlIllI.ensureAllocated();
        StructField lIlIIllIlIlll = lIlIIllIlIllI.fields().get(lIlIIllIllIII);
        if (lIlIIllIlIlll == null) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No such field: ").append(lIlIIllIllIII)));
        }
        return lIlIIllIlIllI.readField(lIlIIllIlIlll);
    }

    protected Structure(Pointer lIlIlIlIllIlI, int lIlIlIlIllIIl, TypeMapper lIlIlIlIllIII) {
        Structure lIlIlIlIllIll;
        lIlIlIlIllIll.size = -1;
        lIlIlIlIllIll.nativeStrings = new HashMap<String, Object>();
        lIlIlIlIllIll.autoRead = true;
        lIlIlIlIllIll.autoWrite = true;
        lIlIlIlIllIll.setAlignType(lIlIlIlIllIIl);
        lIlIlIlIllIll.setStringEncoding(Native.getStringEncoding(lIlIlIlIllIll.getClass()));
        lIlIlIlIllIll.initializeTypeMapper(lIlIlIlIllIII);
        lIlIlIlIllIll.validateFields();
        if (lIlIlIlIllIlI != null) {
            lIlIlIlIllIll.useMemory(lIlIlIlIllIlI, 0, true);
        } else {
            lIlIlIlIllIll.allocateMemory(-1);
        }
        lIlIlIlIllIll.initializeFields();
    }

    protected int getStructAlignment() {
        Structure lIIlIlIlIlIII;
        if (lIIlIlIlIlIII.size == -1) {
            lIIlIlIlIlIII.calculateSize(true);
        }
        return lIIlIlIlIlIII.structAlignment;
    }

    Pointer getTypeInfo() {
        Structure lIIlIIIIIIIlI;
        Pointer lIIlIIIIIIIll = Structure.getTypeInfo(lIIlIIIIIIIlI);
        lIIlIIIIIIIlI.cacheTypeInfo(lIIlIIIIIIIll);
        return lIIlIIIIIIIll;
    }

    public boolean getAutoWrite() {
        Structure lIIIllllIlIIl;
        return lIIIllllIlIIl.autoWrite;
    }

    protected void sortFields(List<Field> lIIllllllIIIl, List<String> lIIlllllIlllI) {
        block0: for (int lIIllllllIIll = 0; lIIllllllIIll < lIIlllllIlllI.size(); ++lIIllllllIIll) {
            String lIIllllllIlII = lIIlllllIlllI.get(lIIllllllIIll);
            for (int lIIllllllIllI = 0; lIIllllllIllI < lIIllllllIIIl.size(); ++lIIllllllIllI) {
                Field lIIlllllllIII = lIIllllllIIIl.get(lIIllllllIllI);
                if (!lIIllllllIlII.equals(lIIlllllllIII.getName())) continue;
                Collections.swap(lIIllllllIIIl, lIIllllllIIll, lIIllllllIllI);
                continue block0;
            }
        }
    }

    protected Structure() {
        lIlIllIIIlIII(0);
        Structure lIlIllIIIlIII;
    }

    protected int getNativeSize(Class<?> lIIIllIIIIIlI, Object lIIIlIlllllll) {
        return Native.getNativeSize(lIIIllIIIIIlI, lIIIlIlllllll);
    }

    static {
        ALIGN_DEFAULT = 0;
        CALCULATE_SIZE = -1;
        ALIGN_MSVC = 3;
        ALIGN_NONE = 1;
        ALIGN_GNUC = 2;
        layoutInfo = new WeakHashMap();
        fieldOrder = new WeakHashMap();
        reads = new ThreadLocal<Map<Pointer, Structure>>(){
            {
                1 lllllllllllllllllIIIlllIlllIllIl;
            }

            @Override
            protected synchronized Map<Pointer, Structure> initialValue() {
                return new HashMap<Pointer, Structure>();
            }
        };
        busy = new ThreadLocal<Set<Structure>>(){
            {
                2 llllllllllllllllllIlllIIllllIIIl;
            }

            @Override
            protected synchronized Set<Structure> initialValue() {
                return new StructureSet();
            }
        };
        PLACEHOLDER_MEMORY = new Pointer(0L){

            @Override
            public Pointer share(long llllllllllllllllIlIllllIIIIllIII, long llllllllllllllllIlIllllIIIIlIlll) {
                3 llllllllllllllllIlIllllIIIIlIllI;
                return llllllllllllllllIlIllllIIIIlIllI;
            }
            {
                3 llllllllllllllllIlIllllIIIIllllI;
            }
        };
    }

    protected void allocateMemory(int lIlIIllllllII) {
        Structure lIlIIllllllIl;
        if (lIlIIllllllII == -1) {
            lIlIIllllllII = lIlIIllllllIl.calculateSize(false);
        } else if (lIlIIllllllII <= 0) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Structure size must be greater than zero: ").append(lIlIIllllllII)));
        }
        if (lIlIIllllllII != -1) {
            if (lIlIIllllllIl.memory == null || lIlIIllllllIl.memory instanceof AutoAllocated) {
                lIlIIllllllIl.memory = lIlIIllllllIl.autoAllocate(lIlIIllllllII);
            }
            lIlIIllllllIl.size = lIlIIllllllII;
        }
    }

    public static List<String> createFieldsOrder(List<String> lIIlllIlIIIII, String ... lIIlllIIlllIl) {
        return Structure.createFieldsOrder(lIIlllIlIIIII, Arrays.asList(lIIlllIIlllIl));
    }

    protected int calculateSize(boolean lIIllIlIlllIl) {
        Structure lIIllIllIIIII;
        return lIIllIllIIIII.calculateSize(lIIllIlIlllIl, false);
    }

    protected void writeField(StructField lIlIIIIIlllII) {
        Structure lIlIIIIlIIlll;
        if (lIlIIIIIlllII.isReadOnly) {
            return;
        }
        int lIlIIIIlIIIll = lIlIIIIIlllII.offset;
        Object lIlIIIIlIIIIl = lIlIIIIlIIlll.getFieldValue(lIlIIIIIlllII.field);
        Class<?> lIlIIIIIlllll = lIlIIIIIlllII.type;
        ToNativeConverter lIlIIIIIllllI = lIlIIIIIlllII.writeConverter;
        if (lIlIIIIIllllI != null) {
            lIlIIIIlIIIIl = lIlIIIIIllllI.toNative(lIlIIIIlIIIIl, new StructureWriteContext(lIlIIIIlIIlll, lIlIIIIIlllII.field));
            lIlIIIIIlllll = lIlIIIIIllllI.nativeType();
        }
        if (String.class == lIlIIIIIlllll || WString.class == lIlIIIIIlllll) {
            boolean lIlIIIIlIllIl;
            boolean bl = lIlIIIIlIllIl = lIlIIIIIlllll == WString.class;
            if (lIlIIIIlIIIIl != null) {
                if (lIlIIIIlIIlll.nativeStrings.containsKey(String.valueOf(new StringBuilder().append(lIlIIIIIlllII.name).append(".ptr"))) && lIlIIIIlIIIIl.equals(lIlIIIIlIIlll.nativeStrings.get(String.valueOf(new StringBuilder().append(lIlIIIIIlllII.name).append(".val"))))) {
                    return;
                }
                NativeString lIlIIIIlIlllI = lIlIIIIlIllIl ? new NativeString(lIlIIIIlIIIIl.toString(), true) : new NativeString(lIlIIIIlIIIIl.toString(), lIlIIIIlIIlll.encoding);
                lIlIIIIlIIlll.nativeStrings.put(lIlIIIIIlllII.name, lIlIIIIlIlllI);
                lIlIIIIlIIIIl = lIlIIIIlIlllI.getPointer();
            } else {
                lIlIIIIlIIlll.nativeStrings.remove(lIlIIIIIlllII.name);
            }
            lIlIIIIlIIlll.nativeStrings.remove(String.valueOf(new StringBuilder().append(lIlIIIIIlllII.name).append(".ptr")));
            lIlIIIIlIIlll.nativeStrings.remove(String.valueOf(new StringBuilder().append(lIlIIIIIlllII.name).append(".val")));
        }
        try {
            lIlIIIIlIIlll.memory.setValue(lIlIIIIlIIIll, lIlIIIIlIIIIl, lIlIIIIIlllll);
        }
        catch (IllegalArgumentException lIlIIIIlIlIIl) {
            String lIlIIIIlIlIll = String.valueOf(new StringBuilder().append("Structure field \"").append(lIlIIIIIlllII.name).append("\" was declared as ").append(lIlIIIIIlllII.type).append(lIlIIIIIlllII.type == lIlIIIIIlllll ? "" : String.valueOf(new StringBuilder().append(" (native type ").append(lIlIIIIIlllll).append(")"))).append(", which is not supported within a Structure"));
            throw new IllegalArgumentException(lIlIIIIlIlIll, lIlIIIIlIlIIl);
        }
    }

    public void read() {
        Structure lIlIIlllIlIIl;
        if (lIlIIlllIlIIl.memory == PLACEHOLDER_MEMORY) {
            return;
        }
        lIlIIlllIlIIl.readCalled = true;
        lIlIIlllIlIIl.ensureAllocated();
        if (Structure.busy().contains(lIlIIlllIlIIl)) {
            return;
        }
        Structure.busy().add(lIlIIlllIlIIl);
        if (lIlIIlllIlIIl instanceof ByReference) {
            Structure.reading().put(lIlIIlllIlIIl.getPointer(), lIlIIlllIlIIl);
        }
        try {
            for (StructField lIlIIlllIlIll : lIlIIlllIlIIl.fields().values()) {
                lIlIIlllIlIIl.readField(lIlIIlllIlIll);
            }
        }
        finally {
            Structure.busy().remove(lIlIIlllIlIIl);
            if (Structure.reading().get(lIlIIlllIlIIl.getPointer()) == lIlIIlllIlIIl) {
                Structure.reading().remove(lIlIIlllIlIIl.getPointer());
            }
        }
    }

    public void clear() {
        Structure lIlIIllllIlll;
        lIlIIllllIlll.ensureAllocated();
        lIlIIllllIlll.memory.clear(lIlIIllllIlll.size());
    }

    public Structure[] toArray(int lIIlIIlIIIlIl) {
        Structure lIIlIIlIIIlII;
        return lIIlIIlIIIlII.toArray((Structure[])Array.newInstance(lIIlIIlIIIlII.getClass(), lIIlIIlIIIlIl));
    }

    TypeMapper getTypeMapper() {
        Structure lIlIlIlIlIIlI;
        return lIlIlIlIlIIlI.typeMapper;
    }

    protected void useMemory(Pointer lIlIlIIlIlIII, int lIlIlIIlIlIlI) {
        Structure lIlIlIIlIllII;
        lIlIlIIlIllII.useMemory(lIlIlIIlIlIII, lIlIlIIlIlIlI, false);
    }

    protected List<Field> getFields(boolean lIIllIllIIlll) {
        Structure lIIllIllIlllI;
        List<Field> lIIllIllIllII = lIIllIllIlllI.getFieldList();
        HashSet<String> lIIllIllIlIll = new HashSet<String>();
        for (Field lIIllIllIllll : lIIllIllIllII) {
            lIIllIllIlIll.add(lIIllIllIllll.getName());
        }
        List<String> lIIllIllIlIlI = lIIllIllIlllI.fieldOrder();
        if (lIIllIllIlIlI.size() != lIIllIllIllII.size() && lIIllIllIllII.size() > 1) {
            if (lIIllIllIIlll) {
                throw new Error(String.valueOf(new StringBuilder().append("Structure.getFieldOrder() on ").append(lIIllIllIlllI.getClass()).append(" does not provide enough names [").append(lIIllIllIlIlI.size()).append("] (").append(Structure.sort(lIIllIllIlIlI)).append(") to match declared fields [").append(lIIllIllIllII.size()).append("] (").append(Structure.sort(lIIllIllIlIll)).append(")")));
            }
            return null;
        }
        HashSet<String> lIIllIllIlIIl = new HashSet<String>(lIIllIllIlIlI);
        if (!lIIllIllIlIIl.equals(lIIllIllIlIll)) {
            throw new Error(String.valueOf(new StringBuilder().append("Structure.getFieldOrder() on ").append(lIIllIllIlllI.getClass()).append(" returns names (").append(Structure.sort(lIIllIllIlIlI)).append(") which do not match declared field names (").append(Structure.sort(lIIllIllIlIll)).append(")")));
        }
        lIIllIllIlllI.sortFields(lIIllIllIllII, lIIllIllIlIlI);
        return lIIllIllIllII;
    }

    void useMemory(Pointer lIlIlIIIllIlI, int lIlIlIIIllIIl, boolean lIlIlIIIllIII) {
        try {
            Structure lIlIlIIIllIll;
            lIlIlIIIllIll.nativeStrings.clear();
            if (lIlIlIIIllIll instanceof ByValue && !lIlIlIIIllIII) {
                byte[] lIlIlIIlIIIIl = new byte[lIlIlIIIllIll.size()];
                lIlIlIIIllIlI.read(0L, lIlIlIIlIIIIl, 0, lIlIlIIlIIIIl.length);
                lIlIlIIIllIll.memory.write(0L, lIlIlIIlIIIIl, 0, lIlIlIIlIIIIl.length);
            } else {
                lIlIlIIIllIll.memory = lIlIlIIIllIlI.share(lIlIlIIIllIIl);
                if (lIlIlIIIllIll.size == -1) {
                    lIlIlIIIllIll.size = lIlIlIIIllIll.calculateSize(false);
                }
                if (lIlIlIIIllIll.size != -1) {
                    lIlIlIIIllIll.memory = lIlIlIIIllIlI.share(lIlIlIIIllIIl, lIlIlIIIllIll.size);
                }
            }
            lIlIlIIIllIll.array = null;
            lIlIlIIIllIll.readCalled = false;
        }
        catch (IndexOutOfBoundsException lIlIlIIlIIIII) {
            throw new IllegalArgumentException("Structure exceeds provided memory bounds", lIlIlIIlIIIII);
        }
    }

    public boolean getAutoRead() {
        Structure lIIIlllllIIll;
        return lIIIlllllIIll.autoRead;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    static int size(Class<?> lIIllIlIIllll, Structure lIIllIlIlIIlI) {
        void lIIllIlIlIIIl;
        int lIIllIlIlIIII;
        Map<Class<?>, LayoutInfo> lIIllIlIIllII = layoutInfo;
        synchronized (lIIllIlIIllII) {
            LayoutInfo lIIllIlIlIlII = layoutInfo.get(lIIllIlIIllll);
        }
        int n = lIIllIlIlIIII = lIIllIlIlIIIl != null && !((LayoutInfo)lIIllIlIlIIIl).variable ? ((LayoutInfo)lIIllIlIlIIIl).size : -1;
        if (lIIllIlIlIIII == -1) {
            if (lIIllIlIlIIlI == null) {
                lIIllIlIlIIlI = Structure.newInstance(lIIllIlIIllll, PLACEHOLDER_MEMORY);
            }
            lIIllIlIlIIII = lIIllIlIlIIlI.size();
        }
        return lIIllIlIlIIII;
    }

    void conditionalAutoRead() {
        Structure lIlIIllllIIII;
        if (!lIlIIllllIIII.readCalled) {
            lIlIIllllIIII.autoRead();
        }
    }

    protected String getStringEncoding() {
        Structure lIlIlIlIIIIIl;
        return lIlIlIlIIIIIl.encoding;
    }

    protected Structure(int lIlIlIlllIlII, TypeMapper lIlIlIlllIllI) {
        lIlIlIlllIlIl(null, lIlIlIlllIlII, lIlIlIlllIllI);
        Structure lIlIlIlllIlIl;
    }

    public Structure[] toArray(Structure[] lIIlIIlIIlIll) {
        Structure lIIlIIlIIllII;
        lIIlIIlIIllII.ensureAllocated();
        if (lIIlIIlIIllII.memory instanceof AutoAllocated) {
            Memory lIIlIIlIlIIlI = (Memory)lIIlIIlIIllII.memory;
            int lIIlIIlIlIIIl = lIIlIIlIIlIll.length * lIIlIIlIIllII.size();
            if (lIIlIIlIlIIlI.size() < (long)lIIlIIlIlIIIl) {
                lIIlIIlIIllII.useMemory(lIIlIIlIIllII.autoAllocate(lIIlIIlIlIIIl));
            }
        }
        lIIlIIlIIlIll[0] = lIIlIIlIIllII;
        int lIIlIIlIIllIl = lIIlIIlIIllII.size();
        for (int lIIlIIlIlIIII = 1; lIIlIIlIlIIII < lIIlIIlIIlIll.length; ++lIIlIIlIlIIII) {
            lIIlIIlIIlIll[lIIlIIlIlIIII] = Structure.newInstance(lIIlIIlIIllII.getClass(), lIIlIIlIIllII.memory.share(lIIlIIlIlIIII * lIIlIIlIIllIl, lIIlIIlIIllIl));
            lIIlIIlIIlIll[lIIlIIlIlIIII].conditionalAutoRead();
        }
        if (!(lIIlIIlIIllII instanceof ByValue)) {
            lIIlIIlIIllII.array = lIIlIIlIIlIll;
        }
        return lIIlIIlIIlIll;
    }

    protected void allocateMemory() {
        Structure lIlIlIIIIlIIl;
        lIlIlIIIIlIIl.allocateMemory(false);
    }

    static int size(Class<?> lIIllIlIllIll) {
        return Structure.size(lIIllIlIllIll, null);
    }

    protected Structure(Pointer lIlIlIllIIlIl, int lIlIlIllIIlll) {
        lIlIlIllIlIIl(lIlIlIllIIlIl, lIlIlIllIIlll, null);
        Structure lIlIlIllIlIIl;
    }

    private void ensureAllocated(boolean lIlIlIIIIlllI) {
        Structure lIlIlIIIIllll;
        if (lIlIlIIIIllll.memory == null) {
            lIlIlIIIIllll.allocateMemory(lIlIlIIIIlllI);
        } else if (lIlIlIIIIllll.size == -1) {
            lIlIlIIIIllll.size = lIlIlIIIIllll.calculateSize(true, lIlIlIIIIlllI);
            if (!(lIlIlIIIIllll.memory instanceof AutoAllocated)) {
                try {
                    lIlIlIIIIllll.memory = lIlIlIIIIllll.memory.share(0L, lIlIlIIIIllll.size);
                }
                catch (IndexOutOfBoundsException lIlIlIIIlIIII) {
                    throw new IllegalArgumentException("Structure exceeds provided memory bounds", lIlIlIIIlIIII);
                }
            }
        }
    }

    protected int getNativeSize(Class<?> lIIIllIIIlIII) {
        Structure lIIIllIIIIlll;
        return lIIIllIIIIlll.getNativeSize(lIIIllIIIlIII, null);
    }

    public String toString() {
        Structure lIIlIlIIlIIll;
        return lIIlIlIIlIIll.toString(Boolean.getBoolean("jna.dump_memory"));
    }

    public void writeField(String lIlIIIlllIlll) {
        Structure lIlIIIllllIIl;
        lIlIIIllllIIl.ensureAllocated();
        StructField lIlIIIlllIllI = lIlIIIllllIIl.fields().get(lIlIIIlllIlll);
        if (lIlIIIlllIllI == null) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No such field: ").append(lIlIIIlllIlll)));
        }
        lIlIIIllllIIl.writeField(lIlIIIlllIllI);
    }

    protected Structure(TypeMapper lIlIllIIIIIlI) {
        lIlIllIIIIIll(null, 0, lIlIllIIIIIlI);
        Structure lIlIllIIIIIll;
    }

    protected void ensureAllocated() {
        Structure lIlIlIIIlIlIl;
        lIlIlIIIlIlIl.ensureAllocated(false);
    }

    private Class<?> baseClass() {
        Structure lIIlIIlIIIIII;
        if ((lIIlIIlIIIIII instanceof ByReference || lIIlIIlIIIIII instanceof ByValue) && Structure.class.isAssignableFrom(lIIlIIlIIIIII.getClass().getSuperclass())) {
            return lIIlIIlIIIIII.getClass().getSuperclass();
        }
        return lIIlIIlIIIIII.getClass();
    }

    @Deprecated
    protected final void setFieldOrder(String[] lIlIIIIIlIlII) {
        throw new Error("This method is obsolete, use getFieldOrder() instead");
    }

    void setFieldValue(Field lIlIIllIIIIll, Object lIlIIllIIIIlI) {
        Structure lIlIIllIIIlll;
        lIlIIllIIIlll.setFieldValue(lIlIIllIIIIll, lIlIIllIIIIlI, false);
    }

    protected int getNativeAlignment(Class<?> lIIlIlIIlllll, Object lIIlIlIIllIII, boolean lIIlIlIIlIlll) {
        Structure lIIlIlIlIIIII;
        int lIIlIlIIlllII = 1;
        if (NativeMapped.class.isAssignableFrom(lIIlIlIIlllll)) {
            NativeMappedConverter lIIlIlIlIIIIl = NativeMappedConverter.getInstance(lIIlIlIIlllll);
            lIIlIlIIlllll = lIIlIlIlIIIIl.nativeType();
            lIIlIlIIllIII = lIIlIlIlIIIIl.toNative(lIIlIlIIllIII, new ToNativeContext());
        }
        int lIIlIlIIllIll = Native.getNativeSize(lIIlIlIIlllll, lIIlIlIIllIII);
        if (lIIlIlIIlllll.isPrimitive() || Long.class == lIIlIlIIlllll || Integer.class == lIIlIlIIlllll || Short.class == lIIlIlIIlllll || Character.class == lIIlIlIIlllll || Byte.class == lIIlIlIIlllll || Boolean.class == lIIlIlIIlllll || Float.class == lIIlIlIIlllll || Double.class == lIIlIlIIlllll) {
            lIIlIlIIlllII = lIIlIlIIllIll;
        } else if (Pointer.class.isAssignableFrom(lIIlIlIIlllll) && !Function.class.isAssignableFrom(lIIlIlIIlllll) || Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(lIIlIlIIlllll) || Callback.class.isAssignableFrom(lIIlIlIIlllll) || WString.class == lIIlIlIIlllll || String.class == lIIlIlIIlllll) {
            lIIlIlIIlllII = Pointer.SIZE;
        } else if (Structure.class.isAssignableFrom(lIIlIlIIlllll)) {
            if (ByReference.class.isAssignableFrom(lIIlIlIIlllll)) {
                lIIlIlIIlllII = Pointer.SIZE;
            } else {
                if (lIIlIlIIllIII == null) {
                    lIIlIlIIllIII = Structure.newInstance(lIIlIlIIlllll, PLACEHOLDER_MEMORY);
                }
                lIIlIlIIlllII = ((Structure)lIIlIlIIllIII).getStructAlignment();
            }
        } else if (lIIlIlIIlllll.isArray()) {
            lIIlIlIIlllII = lIIlIlIlIIIII.getNativeAlignment(lIIlIlIIlllll.getComponentType(), null, lIIlIlIIlIlll);
        } else {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Type ").append(lIIlIlIIlllll).append(" has unknown native alignment")));
        }
        if (lIIlIlIlIIIII.actualAlignType == 1) {
            lIIlIlIIlllII = 1;
        } else if (lIIlIlIlIIIII.actualAlignType == 3) {
            lIIlIlIIlllII = Math.min(8, lIIlIlIIlllII);
        } else if (lIIlIlIlIIIII.actualAlignType == 2) {
            if (!(lIIlIlIIlIlll && Platform.isMac() && Platform.isPPC())) {
                lIIlIlIIlllII = Math.min(Native.MAX_ALIGNMENT, lIIlIlIIlllII);
            }
            if (!lIIlIlIIlIlll && Platform.isAIX() && (lIIlIlIIlllll == Double.TYPE || lIIlIlIIlllll == Double.class)) {
                lIIlIlIIlllII = 4;
            }
        }
        return lIIlIlIIlllII;
    }

    public static List<String> createFieldsOrder(String lIIlllIIIIlll) {
        return Collections.unmodifiableList(Collections.singletonList(lIIlllIIIIlll));
    }

    protected void setAlignType(int lIlIlIIlllIlI) {
        Structure lIlIlIIllllIl;
        lIlIlIIllllIl.alignType = lIlIlIIlllIlI;
        if (lIlIlIIlllIlI == 0 && (lIlIlIIlllIlI = Native.getStructureAlignment(lIlIlIIllllIl.getClass())) == 0) {
            lIlIlIIlllIlI = Platform.isWindows() ? 3 : 2;
        }
        lIlIlIIllllIl.actualAlignType = lIlIlIIlllIlI;
        lIlIlIIllllIl.layoutChanged();
    }

    static Pointer getTypeInfo(Object lIIIllllIIllI) {
        return FFIType.get(lIIIllllIIllI);
    }

    protected void cacheTypeInfo(Pointer lIIlIIIIllIII) {
        lIIlIIIIllIIl.typeInfo = lIIlIIIIllIII.peer;
    }

    static void validate(Class<?> lIIIlIlllllII) {
        Structure.newInstance(lIIIlIlllllII, PLACEHOLDER_MEMORY);
    }

    private static <T extends Comparable<T>> List<T> sort(Collection<? extends T> lIIllIllllIIl) {
        ArrayList<? extends T> lIIllIllllIlI = new ArrayList<T>(lIIllIllllIIl);
        Collections.sort(lIIllIllllIlI);
        return lIIllIllllIlI;
    }

    public void setAutoRead(boolean lIIIlllllIlIl) {
        lIIIlllllIllI.autoRead = lIIIlllllIlIl;
    }

    private static Structure newInstance(Class<?> lIIIlllIllllI, long lIIIlllIlllll) {
        try {
            Structure lIIIllllIIIlI = Structure.newInstance(lIIIlllIllllI, lIIIlllIlllll == 0L ? PLACEHOLDER_MEMORY : new Pointer(lIIIlllIlllll));
            if (lIIIlllIlllll != 0L) {
                lIIIllllIIIlI.conditionalAutoRead();
            }
            return lIIIllllIIIlI;
        }
        catch (Throwable lIIIllllIIIIl) {
            System.err.println(String.valueOf(new StringBuilder().append("JNA: Error creating structure: ").append(lIIIllllIIIIl)));
            return null;
        }
    }

    private void initializeFields() {
        Structure lIIlIllIlIIIl;
        List<Field> lIIlIllIlIIlI = lIIlIllIlIIIl.getFieldList();
        for (Field lIIlIllIlIlII : lIIlIllIlIIlI) {
            try {
                Object lIIlIllIlIllI = lIIlIllIlIlII.get(lIIlIllIlIIIl);
                if (lIIlIllIlIllI != null) continue;
                lIIlIllIlIIIl.initializeField(lIIlIllIlIlII, lIIlIllIlIlII.getType());
            }
            catch (Exception lIIlIllIlIlIl) {
                throw new Error(String.valueOf(new StringBuilder().append("Exception reading field '").append(lIIlIllIlIlII.getName()).append("' in ").append(lIIlIllIlIIIl.getClass())), lIIlIllIlIlIl);
            }
        }
    }

    protected Structure(Pointer lIlIlIllIllll) {
        lIlIlIlllIIII(lIlIlIllIllll, 0);
        Structure lIlIlIlllIIII;
    }

    public static void autoRead(Structure[] lIIIllIIlllll) {
        Structure.structureArrayCheck(lIIIllIIlllll);
        if (lIIIllIIlllll[0].array == lIIIllIIlllll) {
            lIIIllIIlllll[0].autoRead();
        } else {
            for (int lIIIllIlIIIIl = 0; lIIIllIlIIIIl < lIIIllIIlllll.length; ++lIIIllIlIIIIl) {
                if (lIIIllIIlllll[lIIIllIlIIIIl] == null) continue;
                lIIIllIIlllll[lIIIllIlIIIIl].autoRead();
            }
        }
    }

    protected Object readField(StructField lIlIIlIIlIIIl) {
        Object lIlIIlIIlIIll;
        Structure lIlIIlIIllIIl;
        Object lIlIIlIIlIlII;
        int lIlIIlIIlIlll = lIlIIlIIlIIIl.offset;
        Class<?> lIlIIlIIlIllI = lIlIIlIIlIIIl.type;
        FromNativeConverter lIlIIlIIlIlIl = lIlIIlIIlIIIl.readConverter;
        if (lIlIIlIIlIlIl != null) {
            lIlIIlIIlIllI = lIlIIlIIlIlIl.nativeType();
        }
        Object object = lIlIIlIIlIlII = Structure.class.isAssignableFrom(lIlIIlIIlIllI) || Callback.class.isAssignableFrom(lIlIIlIIlIllI) || Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(lIlIIlIIlIllI) || Pointer.class.isAssignableFrom(lIlIIlIIlIllI) || NativeMapped.class.isAssignableFrom(lIlIIlIIlIllI) || lIlIIlIIlIllI.isArray() ? lIlIIlIIllIIl.getFieldValue(lIlIIlIIlIIIl.field) : null;
        if (lIlIIlIIlIllI == String.class) {
            Pointer lIlIIlIIllIll = lIlIIlIIllIIl.memory.getPointer(lIlIIlIIlIlll);
            String lIlIIlIIllIlI = lIlIIlIIllIll == null ? null : lIlIIlIIllIll.getString(0L, lIlIIlIIllIIl.encoding);
        } else {
            lIlIIlIIlIIll = lIlIIlIIllIIl.memory.getValue(lIlIIlIIlIlll, lIlIIlIIlIllI, lIlIIlIIlIlII);
        }
        if (lIlIIlIIlIlIl != null) {
            lIlIIlIIlIIll = lIlIIlIIlIlIl.fromNative(lIlIIlIIlIIll, lIlIIlIIlIIIl.context);
            if (lIlIIlIIlIlII != null && lIlIIlIIlIlII.equals(lIlIIlIIlIIll)) {
                lIlIIlIIlIIll = lIlIIlIIlIlII;
            }
        }
        if (lIlIIlIIlIllI.equals(String.class) || lIlIIlIIlIllI.equals(WString.class)) {
            lIlIIlIIllIIl.nativeStrings.put(String.valueOf(new StringBuilder().append(lIlIIlIIlIIIl.name).append(".ptr")), lIlIIlIIllIIl.memory.getPointer(lIlIIlIIlIlll));
            lIlIIlIIllIIl.nativeStrings.put(String.valueOf(new StringBuilder().append(lIlIIlIIlIIIl.name).append(".val")), lIlIIlIIlIIll);
        }
        lIlIIlIIllIIl.setFieldValue(lIlIIlIIlIIIl.field, lIlIIlIIlIIll, true);
        return lIlIIlIIlIIll;
    }

    Pointer getFieldTypeInfo(StructField lIIlIIIIIlllI) {
        ToNativeConverter lIIlIIIIlIIII;
        Structure lIIlIIIIIlIll;
        Class<?> lIIlIIIIIllIl = lIIlIIIIIlllI.type;
        Object lIIlIIIIIllII = lIIlIIIIIlIll.getFieldValue(lIIlIIIIIlllI.field);
        if (lIIlIIIIIlIll.typeMapper != null && (lIIlIIIIlIIII = lIIlIIIIIlIll.typeMapper.getToNativeConverter(lIIlIIIIIllIl)) != null) {
            lIIlIIIIIllIl = lIIlIIIIlIIII.nativeType();
            lIIlIIIIIllII = lIIlIIIIlIIII.toNative(lIIlIIIIIllII, new ToNativeContext());
        }
        return FFIType.get(lIIlIIIIIllII, lIIlIIIIIllIl);
    }

    public boolean equals(Object lIIlIIIlIIlII) {
        Structure lIIlIIIlIIlIl;
        return lIIlIIIlIIlII instanceof Structure && lIIlIIIlIIlII.getClass() == lIIlIIIlIIlIl.getClass() && ((Structure)lIIlIIIlIIlII).getPointer().equals(lIIlIIIlIIlIl.getPointer());
    }

    private void setFieldValue(Field lIlIIlIllIlII, Object lIlIIlIllIIll, boolean lIlIIlIllIllI) {
        Structure lIlIIlIlllIIl;
        try {
            lIlIIlIllIlII.set(lIlIIlIlllIIl, lIlIIlIllIIll);
        }
        catch (IllegalAccessException lIlIIlIlllIlI) {
            int lIlIIlIlllIll = lIlIIlIllIlII.getModifiers();
            if (Modifier.isFinal(lIlIIlIlllIll)) {
                if (lIlIIlIllIllI) {
                    throw new UnsupportedOperationException(String.valueOf(new StringBuilder().append("This VM does not support Structures with final fields (field '").append(lIlIIlIllIlII.getName()).append("' within ").append(lIlIIlIlllIIl.getClass()).append(")")), lIlIIlIlllIlI);
                }
                throw new UnsupportedOperationException(String.valueOf(new StringBuilder().append("Attempt to write to read-only field '").append(lIlIIlIllIlII.getName()).append("' within ").append(lIlIIlIlllIIl.getClass())), lIlIIlIlllIlI);
            }
            throw new Error(String.valueOf(new StringBuilder().append("Unexpectedly unable to write to field '").append(lIlIIlIllIlII.getName()).append("' within ").append(lIlIIlIlllIIl.getClass())), lIlIIlIlllIlI);
        }
    }

    static Map<Pointer, Structure> reading() {
        return reads.get();
    }

    public static List<String> createFieldsOrder(List<String> lIIlllIIIlIll, List<String> lIIlllIIIlIlI) {
        ArrayList<String> lIIlllIIIllII = new ArrayList<String>(lIIlllIIIlIll.size() + lIIlllIIIlIlI.size());
        lIIlllIIIllII.addAll(lIIlllIIIlIll);
        lIIlllIIIllII.addAll(lIIlllIIIlIlI);
        return Collections.unmodifiableList(lIIlllIIIllII);
    }

    private void validateFields() {
        Structure lIIllIIIllIlI;
        List<Field> lIIllIIIllIll = lIIllIIIllIlI.getFieldList();
        for (Field lIIllIIIlllIl : lIIllIIIllIll) {
            lIIllIIIllIlI.validateField(lIIllIIIlllIl.getName(), lIIllIIIlllIl.getType());
        }
    }

    private String format(Class<?> lIIlIlIIIIlII) {
        String lIIlIlIIIIllI = lIIlIlIIIIlII.getName();
        int lIIlIlIIIIlIl = lIIlIlIIIIllI.lastIndexOf(".");
        return lIIlIlIIIIllI.substring(lIIlIlIIIIlIl + 1);
    }

    protected Structure(int lIlIlIlllllII) {
        lIlIlIlllllIl(null, lIlIlIlllllII);
        Structure lIlIlIlllllIl;
    }

    protected int fieldOffset(String lIlIIlllIIIIl) {
        Structure lIlIIlllIIIlI;
        lIlIIlllIIIlI.ensureAllocated();
        StructField lIlIIlllIIIII = lIlIIlllIIIlI.fields().get(lIlIIlllIIIIl);
        if (lIlIIlllIIIII == null) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("No such field: ").append(lIlIIlllIIIIl)));
        }
        return lIlIIlllIIIII.offset;
    }

    private int addPadding(int lIIlIlIllIllI) {
        Structure lIIlIlIllIlIl;
        return lIIlIlIllIlIl.addPadding(lIIlIlIllIllI, lIIlIlIllIlIl.structAlignment);
    }

    public static List<String> createFieldsOrder(String ... lIIlllIIIIIIl) {
        return Collections.unmodifiableList(Arrays.asList(lIIlllIIIIIIl));
    }

    public Pointer getPointer() {
        Structure lIlIIllllIlII;
        lIlIIllllIlII.ensureAllocated();
        return lIlIIllllIlII.memory;
    }

    protected void useMemory(Pointer lIlIlIIllIIII) {
        Structure lIlIlIIllIIIl;
        lIlIlIIllIIIl.useMemory(lIlIlIIllIIII, 0);
    }

    public static interface ByValue {
    }

    public static interface ByReference {
    }

    static class StructureSet
    extends AbstractCollection<Structure>
    implements Set<Structure> {
        /* synthetic */ Structure[] elements;
        private /* synthetic */ int count;

        @Override
        public boolean remove(Object llllllllllllllllIllIlllIIIIllIIl) {
            StructureSet llllllllllllllllIllIlllIIIIllIlI;
            int llllllllllllllllIllIlllIIIIllIll = llllllllllllllllIllIlllIIIIllIlI.indexOf((Structure)llllllllllllllllIllIlllIIIIllIIl);
            if (llllllllllllllllIllIlllIIIIllIll != -1) {
                if (--llllllllllllllllIllIlllIIIIllIlI.count >= 0) {
                    llllllllllllllllIllIlllIIIIllIlI.elements[llllllllllllllllIllIlllIIIIllIll] = llllllllllllllllIllIlllIIIIllIlI.elements[llllllllllllllllIllIlllIIIIllIlI.count];
                    llllllllllllllllIllIlllIIIIllIlI.elements[llllllllllllllllIllIlllIIIIllIlI.count] = null;
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean add(Structure llllllllllllllllIllIlllIIIlIllIl) {
            StructureSet llllllllllllllllIllIlllIIIlIlllI;
            if (!llllllllllllllllIllIlllIIIlIlllI.contains(llllllllllllllllIllIlllIIIlIllIl)) {
                llllllllllllllllIllIlllIIIlIlllI.ensureCapacity(llllllllllllllllIllIlllIIIlIlllI.count + 1);
                llllllllllllllllIllIlllIIIlIlllI.elements[llllllllllllllllIllIlllIIIlIlllI.count++] = llllllllllllllllIllIlllIIIlIllIl;
            }
            return true;
        }

        StructureSet() {
            StructureSet llllllllllllllllIllIlllIIlIIlIIl;
        }

        private int indexOf(Structure llllllllllllllllIllIlllIIIlIIlIl) {
            StructureSet llllllllllllllllIllIlllIIIlIIllI;
            for (int llllllllllllllllIllIlllIIIlIIlll = 0; llllllllllllllllIllIlllIIIlIIlll < llllllllllllllllIllIlllIIIlIIllI.count; ++llllllllllllllllIllIlllIIIlIIlll) {
                Structure llllllllllllllllIllIlllIIIlIlIII = llllllllllllllllIllIlllIIIlIIllI.elements[llllllllllllllllIllIlllIIIlIIlll];
                if (llllllllllllllllIllIlllIIIlIIlIl != llllllllllllllllIllIlllIIIlIlIII && (llllllllllllllllIllIlllIIIlIIlIl.getClass() != llllllllllllllllIllIlllIIIlIlIII.getClass() || llllllllllllllllIllIlllIIIlIIlIl.size() != llllllllllllllllIllIlllIIIlIlIII.size() || !llllllllllllllllIllIlllIIIlIIlIl.getPointer().equals(llllllllllllllllIllIlllIIIlIlIII.getPointer()))) continue;
                return llllllllllllllllIllIlllIIIlIIlll;
            }
            return -1;
        }

        private void ensureCapacity(int llllllllllllllllIllIlllIIlIIIIII) {
            StructureSet llllllllllllllllIllIlllIIlIIIIll;
            if (llllllllllllllllIllIlllIIlIIIIll.elements == null) {
                llllllllllllllllIllIlllIIlIIIIll.elements = new Structure[llllllllllllllllIllIlllIIlIIIIII * 3 / 2];
            } else if (llllllllllllllllIllIlllIIlIIIIll.elements.length < llllllllllllllllIllIlllIIlIIIIII) {
                Structure[] llllllllllllllllIllIlllIIlIIIlII = new Structure[llllllllllllllllIllIlllIIlIIIIII * 3 / 2];
                System.arraycopy(llllllllllllllllIllIlllIIlIIIIll.elements, 0, llllllllllllllllIllIlllIIlIIIlII, 0, llllllllllllllllIllIlllIIlIIIIll.elements.length);
                llllllllllllllllIllIlllIIlIIIIll.elements = llllllllllllllllIllIlllIIlIIIlII;
            }
        }

        @Override
        public Iterator<Structure> iterator() {
            StructureSet llllllllllllllllIllIlllIIIIlIlIl;
            Structure[] llllllllllllllllIllIlllIIIIlIlII = new Structure[llllllllllllllllIllIlllIIIIlIlIl.count];
            if (llllllllllllllllIllIlllIIIIlIlIl.count > 0) {
                System.arraycopy(llllllllllllllllIllIlllIIIIlIlIl.elements, 0, llllllllllllllllIllIlllIIIIlIlII, 0, llllllllllllllllIllIlllIIIIlIlIl.count);
            }
            return Arrays.asList(llllllllllllllllIllIlllIIIIlIlII).iterator();
        }

        @Override
        public boolean contains(Object llllllllllllllllIllIlllIIIllIlIl) {
            StructureSet llllllllllllllllIllIlllIIIllIlII;
            return llllllllllllllllIllIlllIIIllIlII.indexOf((Structure)llllllllllllllllIllIlllIIIllIlIl) != -1;
        }

        @Override
        public int size() {
            StructureSet llllllllllllllllIllIlllIIIlllIIl;
            return llllllllllllllllIllIlllIIIlllIIl.count;
        }

        public Structure[] getElements() {
            StructureSet llllllllllllllllIllIlllIIIllllIl;
            return llllllllllllllllIllIlllIIIllllIl.elements;
        }
    }

    protected static class StructField {
        public /* synthetic */ Field field;
        public /* synthetic */ ToNativeConverter writeConverter;
        public /* synthetic */ String name;
        public /* synthetic */ int size;
        public /* synthetic */ boolean isVolatile;
        public /* synthetic */ boolean isReadOnly;
        public /* synthetic */ FromNativeContext context;
        public /* synthetic */ FromNativeConverter readConverter;
        public /* synthetic */ Class<?> type;
        public /* synthetic */ int offset;

        protected StructField() {
            StructField llllllllllllllllIllllIllIllllIIl;
            llllllllllllllllIllllIllIllllIIl.size = -1;
            llllllllllllllllIllllIllIllllIIl.offset = -1;
        }

        public String toString() {
            StructField llllllllllllllllIllllIllIlllIllI;
            return String.valueOf(new StringBuilder().append(llllllllllllllllIllllIllIlllIllI.name).append("@").append(llllllllllllllllIllllIllIlllIllI.offset).append("[").append(llllllllllllllllIllllIllIlllIllI.size).append("] (").append(llllllllllllllllIllllIllIlllIllI.type).append(")"));
        }
    }

    private static class AutoAllocated
    extends Memory {
        @Override
        public String toString() {
            AutoAllocated llIllIlIIIllIII;
            return String.valueOf(new StringBuilder().append("auto-").append(super.toString()));
        }

        public AutoAllocated(int llIllIlIIIlllII) {
            super(llIllIlIIIlllII);
            AutoAllocated llIllIlIIIllIll;
            super.clear();
        }
    }

    static class FFIType
    extends Structure {
        public /* synthetic */ size_t size;
        public /* synthetic */ short alignment;
        public /* synthetic */ Pointer elements;
        private static final /* synthetic */ Map<Object, Object> typeInfoMap;
        private static final /* synthetic */ int FFI_TYPE_STRUCT;
        public /* synthetic */ short type;

        private FFIType(Structure llIIIIlllIlIlI) {
            Pointer[] llIIIIlllIllII;
            FFIType llIIIIlllIlllI;
            llIIIIlllIlllI.type = (short)13;
            llIIIIlllIlIlI.ensureAllocated(true);
            if (llIIIIlllIlIlI instanceof Union) {
                StructField llIIIIllllIIlI = ((Union)llIIIIlllIlIlI).typeInfoField();
                Pointer[] llIIIIllllIIIl = new Pointer[]{FFIType.get(llIIIIlllIlIlI.getFieldValue(llIIIIllllIIlI.field), llIIIIllllIIlI.type), null};
            } else {
                llIIIIlllIllII = new Pointer[llIIIIlllIlIlI.fields().size() + 1];
                int llIIIIlllIllll = 0;
                for (StructField llIIIIllllIIII : llIIIIlllIlIlI.fields().values()) {
                    llIIIIlllIllII[llIIIIlllIllll++] = llIIIIlllIlIlI.getFieldTypeInfo(llIIIIllllIIII);
                }
            }
            llIIIIlllIlllI.init(llIIIIlllIllII);
        }

        private void init(Pointer[] llIIIIllIIllII) {
            FFIType llIIIIllIIllIl;
            llIIIIllIIllIl.elements = new Memory(Pointer.SIZE * llIIIIllIIllII.length);
            llIIIIllIIllIl.elements.write(0L, llIIIIllIIllII, 0, llIIIIllIIllII.length);
            llIIIIllIIllIl.write();
        }

        static {
            FFI_TYPE_STRUCT = 13;
            typeInfoMap = new WeakHashMap<Object, Object>();
            if (Native.POINTER_SIZE == 0) {
                throw new Error("Native library not initialized");
            }
            if (FFITypes.ffi_type_void == null) {
                throw new Error("FFI types not initialized");
            }
            typeInfoMap.put(Void.TYPE, FFITypes.ffi_type_void);
            typeInfoMap.put(Void.class, FFITypes.ffi_type_void);
            typeInfoMap.put(Float.TYPE, FFITypes.ffi_type_float);
            typeInfoMap.put(Float.class, FFITypes.ffi_type_float);
            typeInfoMap.put(Double.TYPE, FFITypes.ffi_type_double);
            typeInfoMap.put(Double.class, FFITypes.ffi_type_double);
            typeInfoMap.put(Long.TYPE, FFITypes.ffi_type_sint64);
            typeInfoMap.put(Long.class, FFITypes.ffi_type_sint64);
            typeInfoMap.put(Integer.TYPE, FFITypes.ffi_type_sint32);
            typeInfoMap.put(Integer.class, FFITypes.ffi_type_sint32);
            typeInfoMap.put(Short.TYPE, FFITypes.ffi_type_sint16);
            typeInfoMap.put(Short.class, FFITypes.ffi_type_sint16);
            Pointer llIIIIlIlIlIIl = Native.WCHAR_SIZE == 2 ? FFITypes.ffi_type_uint16 : FFITypes.ffi_type_uint32;
            typeInfoMap.put(Character.TYPE, llIIIIlIlIlIIl);
            typeInfoMap.put(Character.class, llIIIIlIlIlIIl);
            typeInfoMap.put(Byte.TYPE, FFITypes.ffi_type_sint8);
            typeInfoMap.put(Byte.class, FFITypes.ffi_type_sint8);
            typeInfoMap.put(Pointer.class, FFITypes.ffi_type_pointer);
            typeInfoMap.put(String.class, FFITypes.ffi_type_pointer);
            typeInfoMap.put(WString.class, FFITypes.ffi_type_pointer);
            typeInfoMap.put(Boolean.TYPE, FFITypes.ffi_type_uint32);
            typeInfoMap.put(Boolean.class, FFITypes.ffi_type_uint32);
        }

        private static Pointer get(Object llIIIIlIllIlll, Class<?> llIIIIlIlllIIl) {
            ToNativeConverter llIIIIlIllllll;
            TypeMapper llIIIIlIlllIII = Native.getTypeMapper(llIIIIlIlllIIl);
            if (llIIIIlIlllIII != null && (llIIIIlIllllll = llIIIIlIlllIII.getToNativeConverter(llIIIIlIlllIIl)) != null) {
                llIIIIlIlllIIl = llIIIIlIllllll.nativeType();
            }
            Map<Object, Object> map = typeInfoMap;
            synchronized (map) {
                Object llIIIIlIlllIll = typeInfoMap.get(llIIIIlIlllIIl);
                if (llIIIIlIlllIll instanceof Pointer) {
                    return (Pointer)llIIIIlIlllIll;
                }
                if (llIIIIlIlllIll instanceof FFIType) {
                    return ((FFIType)llIIIIlIlllIll).getPointer();
                }
                if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(llIIIIlIlllIIl) || Callback.class.isAssignableFrom(llIIIIlIlllIIl)) {
                    typeInfoMap.put(llIIIIlIlllIIl, FFITypes.ffi_type_pointer);
                    return FFITypes.ffi_type_pointer;
                }
                if (Structure.class.isAssignableFrom(llIIIIlIlllIIl)) {
                    if (llIIIIlIllIlll == null) {
                        llIIIIlIllIlll = FFIType.newInstance(llIIIIlIlllIIl, PLACEHOLDER_MEMORY);
                    }
                    if (ByReference.class.isAssignableFrom(llIIIIlIlllIIl)) {
                        typeInfoMap.put(llIIIIlIlllIIl, FFITypes.ffi_type_pointer);
                        return FFITypes.ffi_type_pointer;
                    }
                    FFIType llIIIIlIlllllI = new FFIType((Structure)llIIIIlIllIlll);
                    typeInfoMap.put(llIIIIlIlllIIl, llIIIIlIlllllI);
                    return llIIIIlIlllllI.getPointer();
                }
                if (NativeMapped.class.isAssignableFrom(llIIIIlIlllIIl)) {
                    NativeMappedConverter llIIIIlIllllIl = NativeMappedConverter.getInstance(llIIIIlIlllIIl);
                    return FFIType.get(llIIIIlIllllIl.toNative(llIIIIlIllIlll, new ToNativeContext()), llIIIIlIllllIl.nativeType());
                }
                if (llIIIIlIlllIIl.isArray()) {
                    FFIType llIIIIlIllllII = new FFIType(llIIIIlIllIlll, llIIIIlIlllIIl);
                    typeInfoMap.put(llIIIIlIllIlll, llIIIIlIllllII);
                    return llIIIIlIllllII.getPointer();
                }
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Unsupported type ").append(llIIIIlIlllIIl)));
            }
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("size", "alignment", "type", "elements");
        }

        private FFIType(Object llIIIIllIlIllI, Class<?> llIIIIllIllIll) {
            FFIType llIIIIllIlllIl;
            llIIIIllIlllIl.type = (short)13;
            int llIIIIllIllIlI = Array.getLength(llIIIIllIlIllI);
            Pointer[] llIIIIllIllIIl = new Pointer[llIIIIllIllIlI + 1];
            Pointer llIIIIllIllIII = FFIType.get(null, llIIIIllIllIll.getComponentType());
            for (int llIIIIllIllllI = 0; llIIIIllIllllI < llIIIIllIllIlI; ++llIIIIllIllllI) {
                llIIIIllIllIIl[llIIIIllIllllI] = llIIIIllIllIII;
            }
            llIIIIllIlllIl.init(llIIIIllIllIIl);
        }

        static Pointer get(Object llIIIIllIIlIII) {
            if (llIIIIllIIlIII == null) {
                return FFITypes.ffi_type_pointer;
            }
            if (llIIIIllIIlIII instanceof Class) {
                return FFIType.get(null, (Class)llIIIIllIIlIII);
            }
            return FFIType.get(llIIIIllIIlIII, llIIIIllIIlIII.getClass());
        }

        public static class size_t
        extends IntegerType {
            private static final /* synthetic */ long serialVersionUID = 1L;

            public size_t(long lIllIlIllIIlIl) {
                super(Native.SIZE_T_SIZE, lIllIlIllIIlIl);
                size_t lIllIlIllIIlII;
            }

            public size_t() {
                lIllIlIllIlIlI(0L);
                size_t lIllIlIllIlIlI;
            }
        }

        private static class FFITypes {
            private static /* synthetic */ Pointer ffi_type_void;
            private static /* synthetic */ Pointer ffi_type_uint8;
            private static /* synthetic */ Pointer ffi_type_uint32;
            private static /* synthetic */ Pointer ffi_type_uint16;
            private static /* synthetic */ Pointer ffi_type_sint64;
            private static /* synthetic */ Pointer ffi_type_sint16;
            private static /* synthetic */ Pointer ffi_type_uint64;
            private static /* synthetic */ Pointer ffi_type_sint8;
            private static /* synthetic */ Pointer ffi_type_sint32;
            private static /* synthetic */ Pointer ffi_type_longdouble;
            private static /* synthetic */ Pointer ffi_type_float;
            private static /* synthetic */ Pointer ffi_type_double;
            private static /* synthetic */ Pointer ffi_type_pointer;

            private FFITypes() {
                FFITypes llllllllllllllllllIlIllIlIllllll;
            }
        }
    }

    private static class LayoutInfo {
        private /* synthetic */ int size;
        private /* synthetic */ int alignment;
        private /* synthetic */ TypeMapper typeMapper;
        private final /* synthetic */ Map<String, StructField> fields;
        private /* synthetic */ boolean variable;
        private /* synthetic */ StructField typeInfoField;
        private /* synthetic */ int alignType;

        private LayoutInfo() {
            LayoutInfo llllllllllllllllIlIllIIIllIIlIlI;
            llllllllllllllllIlIllIIIllIIlIlI.size = -1;
            llllllllllllllllIlIllIIIllIIlIlI.alignment = 1;
            llllllllllllllllIlIllIIIllIIlIlI.fields = Collections.synchronizedMap(new LinkedHashMap());
            llllllllllllllllIlIllIIIllIIlIlI.alignType = 0;
        }
    }
}

