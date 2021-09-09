/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;

public abstract class IntegerType
extends Number
implements NativeMapped {
    private /* synthetic */ boolean unsigned;
    private /* synthetic */ Number number;
    private static final /* synthetic */ long serialVersionUID = 1L;
    private /* synthetic */ long value;
    private /* synthetic */ int size;

    public static <T extends IntegerType> int compare(T llIIIlllIllIll, T llIIIlllIllIII) {
        if (llIIIlllIllIll == llIIIlllIllIII) {
            return 0;
        }
        if (llIIIlllIllIll == null) {
            return 1;
        }
        if (llIIIlllIllIII == null) {
            return -1;
        }
        return IntegerType.compare(llIIIlllIllIll.longValue(), llIIIlllIllIII.longValue());
    }

    public IntegerType(int llIIlIIIllIlll) {
        llIIlIIIlllIII(llIIlIIIllIlll, 0L, false);
        IntegerType llIIlIIIlllIII;
    }

    public void setValue(long llIIlIIIIlIIII) {
        IntegerType llIIlIIIIIlllI;
        long llIIlIIIIIllll = llIIlIIIIlIIII;
        llIIlIIIIIlllI.value = llIIlIIIIlIIII;
        switch (llIIlIIIIIlllI.size) {
            case 1: {
                if (llIIlIIIIIlllI.unsigned) {
                    llIIlIIIIIlllI.value = llIIlIIIIlIIII & 0xFFL;
                }
                llIIlIIIIIllll = (byte)llIIlIIIIlIIII;
                llIIlIIIIIlllI.number = (byte)llIIlIIIIlIIII;
                break;
            }
            case 2: {
                if (llIIlIIIIIlllI.unsigned) {
                    llIIlIIIIIlllI.value = llIIlIIIIlIIII & 0xFFFFL;
                }
                llIIlIIIIIllll = (short)llIIlIIIIlIIII;
                llIIlIIIIIlllI.number = (short)llIIlIIIIlIIII;
                break;
            }
            case 4: {
                if (llIIlIIIIIlllI.unsigned) {
                    llIIlIIIIIlllI.value = llIIlIIIIlIIII & 0xFFFFFFFFL;
                }
                llIIlIIIIIllll = (int)llIIlIIIIlIIII;
                llIIlIIIIIlllI.number = (int)llIIlIIIIlIIII;
                break;
            }
            case 8: {
                llIIlIIIIIlllI.number = llIIlIIIIlIIII;
                break;
            }
            default: {
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Unsupported size: ").append(llIIlIIIIIlllI.size)));
            }
        }
        if (llIIlIIIIIlllI.size < 8) {
            long llIIlIIIIlIIlI = (1L << llIIlIIIIIlllI.size * 8) - 1L ^ 0xFFFFFFFFFFFFFFFFL;
            if (llIIlIIIIlIIII < 0L && llIIlIIIIIllll != llIIlIIIIlIIII || llIIlIIIIlIIII >= 0L && (llIIlIIIIlIIlI & llIIlIIIIlIIII) != 0L) {
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Argument value 0x").append(Long.toHexString(llIIlIIIIlIIII)).append(" exceeds native capacity (").append(llIIlIIIIIlllI.size).append(" bytes) mask=0x").append(Long.toHexString(llIIlIIIIlIIlI))));
            }
        }
    }

    public static final int compare(long llIIIlllIIllll, long llIIIlllIIllII) {
        if (llIIIlllIIllll == llIIIlllIIllII) {
            return 0;
        }
        if (llIIIlllIIllll < llIIIlllIIllII) {
            return -1;
        }
        return 1;
    }

    public String toString() {
        IntegerType llIIIllllIIIIl;
        return llIIIllllIIIIl.number.toString();
    }

    @Override
    public long longValue() {
        IntegerType llIIIlllllIIIl;
        return llIIIlllllIIIl.value;
    }

    public IntegerType(int llIIlIIIlIIlII, long llIIlIIIlIIllI) {
        llIIlIIIlIIlIl(llIIlIIIlIIlII, llIIlIIIlIIllI, false);
        IntegerType llIIlIIIlIIlIl;
    }

    public int hashCode() {
        IntegerType llIIIlllIlllll;
        return llIIIlllIlllll.number.hashCode();
    }

    @Override
    public Class<?> nativeType() {
        IntegerType llIIIlllllIllI;
        return llIIIlllllIllI.number.getClass();
    }

    @Override
    public float floatValue() {
        IntegerType llIIIllllIllIl;
        return llIIIllllIllIl.number.floatValue();
    }

    @Override
    public Object fromNative(Object llIIIlllllllll, FromNativeContext llIIIllllllllI) {
        IntegerType llIIlIIIIIIIII;
        long llIIIlllllllIl = llIIIlllllllll == null ? 0L : ((Number)llIIIlllllllll).longValue();
        try {
            IntegerType llIIlIIIIIIIll = (IntegerType)llIIlIIIIIIIII.getClass().newInstance();
            llIIlIIIIIIIll.setValue(llIIIlllllllIl);
            return llIIlIIIIIIIll;
        }
        catch (InstantiationException llIIlIIIIIIIlI) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Can't instantiate ").append(llIIlIIIIIIIII.getClass())));
        }
        catch (IllegalAccessException llIIlIIIIIIIIl) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Not allowed to instantiate ").append(llIIlIIIIIIIII.getClass())));
        }
    }

    public IntegerType(int llIIlIIIllIIII, boolean llIIlIIIlIllII) {
        llIIlIIIlIlllI(llIIlIIIllIIII, 0L, llIIlIIIlIllII);
        IntegerType llIIlIIIlIlllI;
    }

    @Override
    public double doubleValue() {
        IntegerType llIIIllllIlIlI;
        return llIIIllllIlIlI.number.doubleValue();
    }

    @Override
    public int intValue() {
        IntegerType llIIIlllllIlII;
        return (int)llIIIlllllIlII.value;
    }

    @Override
    public Object toNative() {
        IntegerType llIIlIIIIIlIII;
        return llIIlIIIIIlIII.number;
    }

    public boolean equals(Object llIIIllllIIllI) {
        IntegerType llIIIllllIIlIl;
        return llIIIllllIIllI instanceof IntegerType && llIIIllllIIlIl.number.equals(((IntegerType)llIIIllllIIllI).number);
    }

    public IntegerType(int llIIlIIIIlllIl, long llIIlIIIIlllII, boolean llIIlIIIIllIll) {
        IntegerType llIIlIIIIllIlI;
        llIIlIIIIllIlI.size = llIIlIIIIlllIl;
        llIIlIIIIllIlI.unsigned = llIIlIIIIllIll;
        llIIlIIIIllIlI.setValue(llIIlIIIIlllII);
    }

    public static int compare(IntegerType llIIIlllIlIlIl, long llIIIlllIlIIlI) {
        if (llIIIlllIlIlIl == null) {
            return 1;
        }
        return IntegerType.compare(llIIIlllIlIlIl.longValue(), llIIIlllIlIIlI);
    }
}

