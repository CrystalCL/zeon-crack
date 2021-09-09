/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Callback;
import com.sun.jna.CallbackReference;
import com.sun.jna.FromNativeContext;
import com.sun.jna.Function;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.ToNativeContext;
import com.sun.jna.WString;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class Pointer {
    protected /* synthetic */ long peer;
    public static final /* synthetic */ int SIZE;
    public static final /* synthetic */ Pointer NULL;

    public short[] getShortArray(long llllllllllllllllllIIIIIlIlIIIIIl, int llllllllllllllllllIIIIIlIlIIIIII) {
        Pointer llllllllllllllllllIIIIIlIlIIIIlI;
        short[] llllllllllllllllllIIIIIlIIllllll = new short[llllllllllllllllllIIIIIlIlIIIIII];
        llllllllllllllllllIIIIIlIlIIIIlI.read(llllllllllllllllllIIIIIlIlIIIIIl, llllllllllllllllllIIIIIlIIllllll, 0, llllllllllllllllllIIIIIlIlIIIIII);
        return llllllllllllllllllIIIIIlIIllllll;
    }

    public void setString(long lllllllllllllllllIllllllllIIIlll, String lllllllllllllllllIllllllllIIIllI, String lllllllllllllllllIllllllllIIIlIl) {
        Pointer lllllllllllllllllIllllllllIIlIII;
        byte[] lllllllllllllllllIllllllllIIlIIl = Native.getBytes(lllllllllllllllllIllllllllIIIllI, lllllllllllllllllIllllllllIIIlIl);
        lllllllllllllllllIllllllllIIlIII.write(lllllllllllllllllIllllllllIIIlll, lllllllllllllllllIllllllllIIlIIl, 0, lllllllllllllllllIllllllllIIlIIl.length);
        lllllllllllllllllIllllllllIIlIII.setByte(lllllllllllllllllIllllllllIIIlll + (long)lllllllllllllllllIllllllllIIlIIl.length, (byte)0);
    }

    Pointer() {
        Pointer llllllllllllllllllIIIIllIIlllIIl;
    }

    public String[] getStringArray(long llllllllllllllllllIIIIIIllIllIIl, int llllllllllllllllllIIIIIIllIllIll) {
        Pointer llllllllllllllllllIIIIIIllIlllIl;
        return llllllllllllllllllIIIIIIllIlllIl.getStringArray(llllllllllllllllllIIIIIIllIllIIl, llllllllllllllllllIIIIIIllIllIll, Native.getDefaultStringEncoding());
    }

    public String[] getStringArray(long llllllllllllllllllIIIIIIlIlIIlIl, int llllllllllllllllllIIIIIIlIlIIlII, String llllllllllllllllllIIIIIIlIIlllII) {
        Pointer llllllllllllllllllIIIIIIlIlIIllI;
        ArrayList<String> llllllllllllllllllIIIIIIlIlIIIlI = new ArrayList<String>();
        int llllllllllllllllllIIIIIIlIlIIIII = 0;
        if (llllllllllllllllllIIIIIIlIlIIlII != -1) {
            Pointer llllllllllllllllllIIIIIIlIlIlIII = llllllllllllllllllIIIIIIlIlIIllI.getPointer(llllllllllllllllllIIIIIIlIlIIlIl + (long)llllllllllllllllllIIIIIIlIlIIIII);
            int llllllllllllllllllIIIIIIlIlIlIIl = 0;
            while (llllllllllllllllllIIIIIIlIlIlIIl++ < llllllllllllllllllIIIIIIlIlIIlII) {
                String llllllllllllllllllIIIIIIlIlIlIlI = llllllllllllllllllIIIIIIlIlIlIII == null ? null : ("--WIDE-STRING--".equals(llllllllllllllllllIIIIIIlIIlllII) ? llllllllllllllllllIIIIIIlIlIlIII.getWideString(0L) : llllllllllllllllllIIIIIIlIlIlIII.getString(0L, llllllllllllllllllIIIIIIlIIlllII));
                llllllllllllllllllIIIIIIlIlIIIlI.add(llllllllllllllllllIIIIIIlIlIlIlI);
                if (llllllllllllllllllIIIIIIlIlIlIIl >= llllllllllllllllllIIIIIIlIlIIlII) continue;
                llllllllllllllllllIIIIIIlIlIlIII = llllllllllllllllllIIIIIIlIlIIllI.getPointer(llllllllllllllllllIIIIIIlIlIIlIl + (long)(llllllllllllllllllIIIIIIlIlIIIII += SIZE));
            }
        } else {
            Pointer llllllllllllllllllIIIIIIlIlIIIIl;
            while ((llllllllllllllllllIIIIIIlIlIIIIl = llllllllllllllllllIIIIIIlIlIIllI.getPointer(llllllllllllllllllIIIIIIlIlIIlIl + (long)llllllllllllllllllIIIIIIlIlIIIII)) != null) {
                String llllllllllllllllllIIIIIIlIlIIlll = llllllllllllllllllIIIIIIlIlIIIIl == null ? null : ("--WIDE-STRING--".equals(llllllllllllllllllIIIIIIlIIlllII) ? llllllllllllllllllIIIIIIlIlIIIIl.getWideString(0L) : llllllllllllllllllIIIIIIlIlIIIIl.getString(0L, llllllllllllllllllIIIIIIlIIlllII));
                llllllllllllllllllIIIIIIlIlIIIlI.add(llllllllllllllllllIIIIIIlIlIIlll);
                llllllllllllllllllIIIIIIlIlIIIII += SIZE;
            }
        }
        return llllllllllllllllllIIIIIIlIlIIIlI.toArray(new String[llllllllllllllllllIIIIIIlIlIIIlI.size()]);
    }

    private void readArray(long llllllllllllllllllIIIIIlllIIIlIl, Object llllllllllllllllllIIIIIlllIIlIlI, Class<?> llllllllllllllllllIIIIIlllIIlIIl) {
        Pointer llllllllllllllllllIIIIIlllIIllII;
        int llllllllllllllllllIIIIIlllIIlIII = 0;
        llllllllllllllllllIIIIIlllIIlIII = Array.getLength(llllllllllllllllllIIIIIlllIIlIlI);
        Object llllllllllllllllllIIIIIlllIIIlll = llllllllllllllllllIIIIIlllIIlIlI;
        if (llllllllllllllllllIIIIIlllIIlIIl == Byte.TYPE) {
            llllllllllllllllllIIIIIlllIIllII.read(llllllllllllllllllIIIIIlllIIIlIl, (byte[])llllllllllllllllllIIIIIlllIIIlll, 0, llllllllllllllllllIIIIIlllIIlIII);
        } else if (llllllllllllllllllIIIIIlllIIlIIl == Short.TYPE) {
            llllllllllllllllllIIIIIlllIIllII.read(llllllllllllllllllIIIIIlllIIIlIl, (short[])llllllllllllllllllIIIIIlllIIIlll, 0, llllllllllllllllllIIIIIlllIIlIII);
        } else if (llllllllllllllllllIIIIIlllIIlIIl == Character.TYPE) {
            llllllllllllllllllIIIIIlllIIllII.read(llllllllllllllllllIIIIIlllIIIlIl, (char[])llllllllllllllllllIIIIIlllIIIlll, 0, llllllllllllllllllIIIIIlllIIlIII);
        } else if (llllllllllllllllllIIIIIlllIIlIIl == Integer.TYPE) {
            llllllllllllllllllIIIIIlllIIllII.read(llllllllllllllllllIIIIIlllIIIlIl, (int[])llllllllllllllllllIIIIIlllIIIlll, 0, llllllllllllllllllIIIIIlllIIlIII);
        } else if (llllllllllllllllllIIIIIlllIIlIIl == Long.TYPE) {
            llllllllllllllllllIIIIIlllIIllII.read(llllllllllllllllllIIIIIlllIIIlIl, (long[])llllllllllllllllllIIIIIlllIIIlll, 0, llllllllllllllllllIIIIIlllIIlIII);
        } else if (llllllllllllllllllIIIIIlllIIlIIl == Float.TYPE) {
            llllllllllllllllllIIIIIlllIIllII.read(llllllllllllllllllIIIIIlllIIIlIl, (float[])llllllllllllllllllIIIIIlllIIIlll, 0, llllllllllllllllllIIIIIlllIIlIII);
        } else if (llllllllllllllllllIIIIIlllIIlIIl == Double.TYPE) {
            llllllllllllllllllIIIIIlllIIllII.read(llllllllllllllllllIIIIIlllIIIlIl, (double[])llllllllllllllllllIIIIIlllIIIlll, 0, llllllllllllllllllIIIIIlllIIlIII);
        } else if (Pointer.class.isAssignableFrom(llllllllllllllllllIIIIIlllIIlIIl)) {
            llllllllllllllllllIIIIIlllIIllII.read(llllllllllllllllllIIIIIlllIIIlIl, (Pointer[])llllllllllllllllllIIIIIlllIIIlll, 0, llllllllllllllllllIIIIIlllIIlIII);
        } else if (Structure.class.isAssignableFrom(llllllllllllllllllIIIIIlllIIlIIl)) {
            Structure[] llllllllllllllllllIIIIIlllIlIIlI = (Structure[])llllllllllllllllllIIIIIlllIIIlll;
            if (Structure.ByReference.class.isAssignableFrom(llllllllllllllllllIIIIIlllIIlIIl)) {
                Pointer[] llllllllllllllllllIIIIIlllIlIllI = llllllllllllllllllIIIIIlllIIllII.getPointerArray(llllllllllllllllllIIIIIlllIIIlIl, llllllllllllllllllIIIIIlllIlIIlI.length);
                for (int llllllllllllllllllIIIIIlllIlIlll = 0; llllllllllllllllllIIIIIlllIlIlll < llllllllllllllllllIIIIIlllIlIIlI.length; ++llllllllllllllllllIIIIIlllIlIlll) {
                    llllllllllllllllllIIIIIlllIlIIlI[llllllllllllllllllIIIIIlllIlIlll] = Structure.updateStructureByReference(llllllllllllllllllIIIIIlllIIlIIl, llllllllllllllllllIIIIIlllIlIIlI[llllllllllllllllllIIIIIlllIlIlll], llllllllllllllllllIIIIIlllIlIllI[llllllllllllllllllIIIIIlllIlIlll]);
                }
            } else {
                Structure llllllllllllllllllIIIIIlllIlIlII = llllllllllllllllllIIIIIlllIlIIlI[0];
                if (llllllllllllllllllIIIIIlllIlIlII == null) {
                    llllllllllllllllllIIIIIlllIlIlII = Structure.newInstance(llllllllllllllllllIIIIIlllIIlIIl, llllllllllllllllllIIIIIlllIIllII.share(llllllllllllllllllIIIIIlllIIIlIl));
                    llllllllllllllllllIIIIIlllIlIlII.conditionalAutoRead();
                    llllllllllllllllllIIIIIlllIlIIlI[0] = llllllllllllllllllIIIIIlllIlIlII;
                } else {
                    llllllllllllllllllIIIIIlllIlIlII.useMemory(llllllllllllllllllIIIIIlllIIllII, (int)llllllllllllllllllIIIIIlllIIIlIl, true);
                    llllllllllllllllllIIIIIlllIlIlII.read();
                }
                Structure[] llllllllllllllllllIIIIIlllIlIIll = llllllllllllllllllIIIIIlllIlIlII.toArray(llllllllllllllllllIIIIIlllIlIIlI.length);
                for (int llllllllllllllllllIIIIIlllIlIlIl = 1; llllllllllllllllllIIIIIlllIlIlIl < llllllllllllllllllIIIIIlllIlIIlI.length; ++llllllllllllllllllIIIIIlllIlIlIl) {
                    if (llllllllllllllllllIIIIIlllIlIIlI[llllllllllllllllllIIIIIlllIlIlIl] == null) {
                        llllllllllllllllllIIIIIlllIlIIlI[llllllllllllllllllIIIIIlllIlIlIl] = llllllllllllllllllIIIIIlllIlIIll[llllllllllllllllllIIIIIlllIlIlIl];
                        continue;
                    }
                    llllllllllllllllllIIIIIlllIlIIlI[llllllllllllllllllIIIIIlllIlIlIl].useMemory(llllllllllllllllllIIIIIlllIIllII, (int)(llllllllllllllllllIIIIIlllIIIlIl + (long)(llllllllllllllllllIIIIIlllIlIlIl * llllllllllllllllllIIIIIlllIlIIlI[llllllllllllllllllIIIIIlllIlIlIl].size())), true);
                    llllllllllllllllllIIIIIlllIlIIlI[llllllllllllllllllIIIIIlllIlIlIl].read();
                }
            }
        } else if (NativeMapped.class.isAssignableFrom(llllllllllllllllllIIIIIlllIIlIIl)) {
            NativeMapped[] llllllllllllllllllIIIIIlllIIllll = (NativeMapped[])llllllllllllllllllIIIIIlllIIIlll;
            NativeMappedConverter llllllllllllllllllIIIIIlllIIlllI = NativeMappedConverter.getInstance(llllllllllllllllllIIIIIlllIIlIIl);
            int llllllllllllllllllIIIIIlllIIllIl = Native.getNativeSize(llllllllllllllllllIIIIIlllIIIlll.getClass(), llllllllllllllllllIIIIIlllIIIlll) / llllllllllllllllllIIIIIlllIIllll.length;
            for (int llllllllllllllllllIIIIIlllIlIIII = 0; llllllllllllllllllIIIIIlllIlIIII < llllllllllllllllllIIIIIlllIIllll.length; ++llllllllllllllllllIIIIIlllIlIIII) {
                Object llllllllllllllllllIIIIIlllIlIIIl = llllllllllllllllllIIIIIlllIIllII.getValue(llllllllllllllllllIIIIIlllIIIlIl + (long)(llllllllllllllllllIIIIIlllIIllIl * llllllllllllllllllIIIIIlllIlIIII), llllllllllllllllllIIIIIlllIIlllI.nativeType(), llllllllllllllllllIIIIIlllIIllll[llllllllllllllllllIIIIIlllIlIIII]);
                llllllllllllllllllIIIIIlllIIllll[llllllllllllllllllIIIIIlllIlIIII] = (NativeMapped)llllllllllllllllllIIIIIlllIIlllI.fromNative(llllllllllllllllllIIIIIlllIlIIIl, new FromNativeContext(llllllllllllllllllIIIIIlllIIlIIl));
            }
        } else {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Reading array of ").append(llllllllllllllllllIIIIIlllIIlIIl).append(" from memory not supported")));
        }
    }

    public void setString(long lllllllllllllllllIllllllllIlIlll, String lllllllllllllllllIllllllllIlIllI) {
        Pointer lllllllllllllllllIllllllllIlIlIl;
        lllllllllllllllllIllllllllIlIlIl.setString(lllllllllllllllllIllllllllIlIlll, lllllllllllllllllIllllllllIlIllI, Native.getDefaultStringEncoding());
    }

    public void write(long llllllllllllllllllIIIIlIIIIIllll, Pointer[] llllllllllllllllllIIIIlIIIIIlllI, int llllllllllllllllllIIIIlIIIIIlIII, int llllllllllllllllllIIIIlIIIIIllII) {
        for (int llllllllllllllllllIIIIlIIIIlIIIl = 0; llllllllllllllllllIIIIlIIIIlIIIl < llllllllllllllllllIIIIlIIIIIllII; ++llllllllllllllllllIIIIlIIIIlIIIl) {
            Pointer llllllllllllllllllIIIIlIIIIlIIII;
            llllllllllllllllllIIIIlIIIIlIIII.setPointer(llllllllllllllllllIIIIlIIIIIllll + (long)(llllllllllllllllllIIIIlIIIIlIIIl * SIZE), llllllllllllllllllIIIIlIIIIIlllI[llllllllllllllllllIIIIlIIIIIlIII + llllllllllllllllllIIIIlIIIIlIIIl]);
        }
    }

    public static long nativeValue(Pointer lllllllllllllllllIlllllllIlIIlII) {
        return lllllllllllllllllIlllllllIlIIlII == null ? 0L : lllllllllllllllllIlllllllIlIIlII.peer;
    }

    @Deprecated
    public void setString(long lllllllllllllllllIllllllllllIIII, String lllllllllllllllllIlllllllllIllll, boolean lllllllllllllllllIllllllllllIIlI) {
        Pointer lllllllllllllllllIllllllllllIIIl;
        if (lllllllllllllllllIllllllllllIIlI) {
            lllllllllllllllllIllllllllllIIIl.setWideString(lllllllllllllllllIllllllllllIIII, lllllllllllllllllIlllllllllIllll);
        } else {
            lllllllllllllllllIllllllllllIIIl.setString(lllllllllllllllllIllllllllllIIII, lllllllllllllllllIlllllllllIllll);
        }
    }

    public void setDouble(long llllllllllllllllllIIIIIIIIIIIlll, double llllllllllllllllllIIIIIIIIIIIllI) {
        Pointer llllllllllllllllllIIIIIIIIIIIlIl;
        Native.setDouble(llllllllllllllllllIIIIIIIIIIIlIl, llllllllllllllllllIIIIIIIIIIIlIl.peer, llllllllllllllllllIIIIIIIIIIIlll, llllllllllllllllllIIIIIIIIIIIllI);
    }

    public Pointer(long llllllllllllllllllIIIIllIIlIlllI) {
        Pointer llllllllllllllllllIIIIllIIllIIll;
        llllllllllllllllllIIIIllIIllIIll.peer = llllllllllllllllllIIIIllIIlIlllI;
    }

    public String dump(long lllllllllllllllllIlllllllIllIlll, int lllllllllllllllllIlllllllIllIllI) {
        int lllllllllllllllllIlllllllIllIlIl = 4;
        String lllllllllllllllllIlllllllIllIlII = "memory dump";
        StringWriter lllllllllllllllllIlllllllIllIIll = new StringWriter("memory dump".length() + 2 + lllllllllllllllllIlllllllIllIllI * 2 + lllllllllllllllllIlllllllIllIllI / 4 * 4);
        PrintWriter lllllllllllllllllIlllllllIllIIlI = new PrintWriter(lllllllllllllllllIlllllllIllIIll);
        lllllllllllllllllIlllllllIllIIlI.println("memory dump");
        for (int lllllllllllllllllIlllllllIlllIIl = 0; lllllllllllllllllIlllllllIlllIIl < lllllllllllllllllIlllllllIllIllI; ++lllllllllllllllllIlllllllIlllIIl) {
            Pointer lllllllllllllllllIlllllllIlllIII;
            byte lllllllllllllllllIlllllllIlllIlI = lllllllllllllllllIlllllllIlllIII.getByte(lllllllllllllllllIlllllllIllIlll + (long)lllllllllllllllllIlllllllIlllIIl);
            if (lllllllllllllllllIlllllllIlllIIl % 4 == 0) {
                lllllllllllllllllIlllllllIllIIlI.print("[");
            }
            if (lllllllllllllllllIlllllllIlllIlI >= 0 && lllllllllllllllllIlllllllIlllIlI < 16) {
                lllllllllllllllllIlllllllIllIIlI.print("0");
            }
            lllllllllllllllllIlllllllIllIIlI.print(Integer.toHexString(lllllllllllllllllIlllllllIlllIlI & 0xFF));
            if (lllllllllllllllllIlllllllIlllIIl % 4 != 3 || lllllllllllllllllIlllllllIlllIIl >= lllllllllllllllllIlllllllIllIllI - 1) continue;
            lllllllllllllllllIlllllllIllIIlI.println("]");
        }
        if (lllllllllllllllllIlllllllIllIIll.getBuffer().charAt(lllllllllllllllllIlllllllIllIIll.getBuffer().length() - 2) != ']') {
            lllllllllllllllllIlllllllIllIIlI.println("]");
        }
        return lllllllllllllllllIlllllllIllIIll.toString();
    }

    public void setMemory(long llllllllllllllllllIIIIIIIlIlIIIl, long llllllllllllllllllIIIIIIIlIlIIII, byte llllllllllllllllllIIIIIIIlIIlIll) {
        Pointer llllllllllllllllllIIIIIIIlIlIIlI;
        Native.setMemory(llllllllllllllllllIIIIIIIlIlIIlI, llllllllllllllllllIIIIIIIlIlIIlI.peer, llllllllllllllllllIIIIIIIlIlIIIl, llllllllllllllllllIIIIIIIlIlIIII, llllllllllllllllllIIIIIIIlIIlIll);
    }

    public void setChar(long llllllllllllllllllIIIIIIIIllIIIl, char llllllllllllllllllIIIIIIIIllIIll) {
        Pointer llllllllllllllllllIIIIIIIIllIIlI;
        Native.setChar(llllllllllllllllllIIIIIIIIllIIlI, llllllllllllllllllIIIIIIIIllIIlI.peer, llllllllllllllllllIIIIIIIIllIIIl, llllllllllllllllllIIIIIIIIllIIll);
    }

    public void setInt(long llllllllllllllllllIIIIIIIIlIlIll, int llllllllllllllllllIIIIIIIIlIIlll) {
        Pointer llllllllllllllllllIIIIIIIIlIllII;
        Native.setInt(llllllllllllllllllIIIIIIIIlIllII, llllllllllllllllllIIIIIIIIlIllII.peer, llllllllllllllllllIIIIIIIIlIlIll, llllllllllllllllllIIIIIIIIlIIlll);
    }

    public void read(long llllllllllllllllllIIIIlIlIIlllII, double[] llllllllllllllllllIIIIlIlIIllIll, int llllllllllllllllllIIIIlIlIIlllll, int llllllllllllllllllIIIIlIlIIllllI) {
        Pointer llllllllllllllllllIIIIlIlIIlllIl;
        Native.read(llllllllllllllllllIIIIlIlIIlllIl, llllllllllllllllllIIIIlIlIIlllIl.peer, llllllllllllllllllIIIIlIlIIlllII, llllllllllllllllllIIIIlIlIIllIll, llllllllllllllllllIIIIlIlIIlllll, llllllllllllllllllIIIIlIlIIllllI);
    }

    public void read(long llllllllllllllllllIIIIlIlllIIlll, short[] llllllllllllllllllIIIIlIlllIlIll, int llllllllllllllllllIIIIlIlllIIlIl, int llllllllllllllllllIIIIlIlllIlIIl) {
        Pointer llllllllllllllllllIIIIlIlllIllIl;
        Native.read(llllllllllllllllllIIIIlIlllIllIl, llllllllllllllllllIIIIlIlllIllIl.peer, llllllllllllllllllIIIIlIlllIIlll, llllllllllllllllllIIIIlIlllIlIll, llllllllllllllllllIIIIlIlllIIlIl, llllllllllllllllllIIIIlIlllIlIIl);
    }

    public String[] getWideStringArray(long llllllllllllllllllIIIIIIllIIIlII, int llllllllllllllllllIIIIIIllIIIIII) {
        Pointer llllllllllllllllllIIIIIIllIIIlIl;
        return llllllllllllllllllIIIIIIllIIIlIl.getStringArray(llllllllllllllllllIIIIIIllIIIlII, llllllllllllllllllIIIIIIllIIIIII, "--WIDE-STRING--");
    }

    public String getString(long llllllllllllllllllIIIIIlIllIlIlI) {
        Pointer llllllllllllllllllIIIIIlIllIlIIl;
        return llllllllllllllllllIIIIIlIllIlIIl.getString(llllllllllllllllllIIIIIlIllIlIlI, Native.getDefaultStringEncoding());
    }

    public String[] getStringArray(long llllllllllllllllllIIIIIIlllIlIlI) {
        Pointer llllllllllllllllllIIIIIIlllIllIl;
        return llllllllllllllllllIIIIIIlllIllIl.getStringArray(llllllllllllllllllIIIIIIlllIlIlI, -1, Native.getDefaultStringEncoding());
    }

    public void clear(long llllllllllllllllllIIIIllIIIlIllI) {
        Pointer llllllllllllllllllIIIIllIIIlIlll;
        llllllllllllllllllIIIIllIIIlIlll.setMemory(0L, llllllllllllllllllIIIIllIIIlIllI, (byte)0);
    }

    public void read(long llllllllllllllllllIIIIlIllIIlIIl, int[] llllllllllllllllllIIIIlIllIIllIl, int llllllllllllllllllIIIIlIllIIllII, int llllllllllllllllllIIIIlIllIIlIll) {
        Pointer llllllllllllllllllIIIIlIllIIllll;
        Native.read(llllllllllllllllllIIIIlIllIIllll, llllllllllllllllllIIIIlIllIIllll.peer, llllllllllllllllllIIIIlIllIIlIIl, llllllllllllllllllIIIIlIllIIllIl, llllllllllllllllllIIIIlIllIIllII, llllllllllllllllllIIIIlIllIIlIll);
    }

    public double[] getDoubleArray(long llllllllllllllllllIIIIIlIIIIllIl, int llllllllllllllllllIIIIIlIIIlIIII) {
        Pointer llllllllllllllllllIIIIIlIIIlIIlI;
        double[] llllllllllllllllllIIIIIlIIIIllll = new double[llllllllllllllllllIIIIIlIIIlIIII];
        llllllllllllllllllIIIIIlIIIlIIlI.read(llllllllllllllllllIIIIIlIIIIllIl, llllllllllllllllllIIIIIlIIIIllll, 0, llllllllllllllllllIIIIIlIIIlIIII);
        return llllllllllllllllllIIIIIlIIIIllll;
    }

    public int hashCode() {
        Pointer llllllllllllllllllIIIIllIIIIllII;
        return (int)((llllllllllllllllllIIIIllIIIIllII.peer >>> 32) + (llllllllllllllllllIIIIllIIIIllII.peer & 0xFFFFFFFFFFFFFFFFL));
    }

    public void setFloat(long llllllllllllllllllIIIIIIIIIIllIl, float llllllllllllllllllIIIIIIIIIIllII) {
        Pointer llllllllllllllllllIIIIIIIIIIlllI;
        Native.setFloat(llllllllllllllllllIIIIIIIIIIlllI, llllllllllllllllllIIIIIIIIIIlllI.peer, llllllllllllllllllIIIIIIIIIIllIl, llllllllllllllllllIIIIIIIIIIllII);
    }

    public void read(long llllllllllllllllllIIIIlIlIIIllII, Pointer[] llllllllllllllllllIIIIlIlIIIIllI, int llllllllllllllllllIIIIlIlIIIIlIl, int llllllllllllllllllIIIIlIlIIIIlII) {
        for (int llllllllllllllllllIIIIlIlIIIlllI = 0; llllllllllllllllllIIIIlIlIIIlllI < llllllllllllllllllIIIIlIlIIIIlII; ++llllllllllllllllllIIIIlIlIIIlllI) {
            Pointer llllllllllllllllllIIIIlIlIIIllIl;
            Pointer llllllllllllllllllIIIIlIlIIlIIII = llllllllllllllllllIIIIlIlIIIllIl.getPointer(llllllllllllllllllIIIIlIlIIIllII + (long)(llllllllllllllllllIIIIlIlIIIlllI * SIZE));
            Pointer llllllllllllllllllIIIIlIlIIIllll = llllllllllllllllllIIIIlIlIIIIllI[llllllllllllllllllIIIIlIlIIIlllI + llllllllllllllllllIIIIlIlIIIIlIl];
            if (llllllllllllllllllIIIIlIlIIIllll != null && llllllllllllllllllIIIIlIlIIlIIII != null && llllllllllllllllllIIIIlIlIIlIIII.peer == llllllllllllllllllIIIIlIlIIIllll.peer) continue;
            llllllllllllllllllIIIIlIlIIIIllI[llllllllllllllllllIIIIlIlIIIlllI + llllllllllllllllllIIIIlIlIIIIlIl] = llllllllllllllllllIIIIlIlIIlIIII;
        }
    }

    public void read(long llllllllllllllllllIIIIlIlIllIIII, float[] llllllllllllllllllIIIIlIlIlIllll, int llllllllllllllllllIIIIlIlIlIlIIl, int llllllllllllllllllIIIIlIlIlIlIII) {
        Pointer llllllllllllllllllIIIIlIlIllIIIl;
        Native.read(llllllllllllllllllIIIIlIlIllIIIl, llllllllllllllllllIIIIlIlIllIIIl.peer, llllllllllllllllllIIIIlIlIllIIII, llllllllllllllllllIIIIlIlIlIllll, llllllllllllllllllIIIIlIlIlIlIIl, llllllllllllllllllIIIIlIlIlIlIII);
    }

    public int getInt(long llllllllllllllllllIIIIIllIlIIllI) {
        Pointer llllllllllllllllllIIIIIllIlIIlIl;
        return Native.getInt(llllllllllllllllllIIIIIllIlIIlIl, llllllllllllllllllIIIIIllIlIIlIl.peer, llllllllllllllllllIIIIIllIlIIllI);
    }

    public String getWideString(long llllllllllllllllllIIIIIlIlllIIII) {
        Pointer llllllllllllllllllIIIIIlIllIllll;
        return Native.getWideString(llllllllllllllllllIIIIIlIllIllll, llllllllllllllllllIIIIIlIllIllll.peer, llllllllllllllllllIIIIIlIlllIIII);
    }

    public ByteBuffer getByteBuffer(long llllllllllllllllllIIIIIlIllllllI, long llllllllllllllllllIIIIIllIIIIIII) {
        Pointer llllllllllllllllllIIIIIlIlllllll;
        return Native.getDirectByteBuffer(llllllllllllllllllIIIIIlIlllllll, llllllllllllllllllIIIIIlIlllllll.peer, llllllllllllllllllIIIIIlIllllllI, llllllllllllllllllIIIIIllIIIIIII).order(ByteOrder.nativeOrder());
    }

    Object getValue(long llllllllllllllllllIIIIIllllIlIIl, Class<?> llllllllllllllllllIIIIIllllIlIII, Object llllllllllllllllllIIIIIllllIllII) {
        Pointer llllllllllllllllllIIIIIllllIllll;
        Object llllllllllllllllllIIIIIllllIlIll = null;
        if (Structure.class.isAssignableFrom(llllllllllllllllllIIIIIllllIlIII)) {
            Structure llllllllllllllllllIIIIIlllllllIl = (Structure)llllllllllllllllllIIIIIllllIllII;
            if (Structure.ByReference.class.isAssignableFrom(llllllllllllllllllIIIIIllllIlIII)) {
                llllllllllllllllllIIIIIlllllllIl = Structure.updateStructureByReference(llllllllllllllllllIIIIIllllIlIII, llllllllllllllllllIIIIIlllllllIl, llllllllllllllllllIIIIIllllIllll.getPointer(llllllllllllllllllIIIIIllllIlIIl));
            } else {
                llllllllllllllllllIIIIIlllllllIl.useMemory(llllllllllllllllllIIIIIllllIllll, (int)llllllllllllllllllIIIIIllllIlIIl, true);
                llllllllllllllllllIIIIIlllllllIl.read();
            }
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIlllllllIl;
        } else if (llllllllllllllllllIIIIIllllIlIII == Boolean.TYPE || llllllllllllllllllIIIIIllllIlIII == Boolean.class) {
            llllllllllllllllllIIIIIllllIlIll = Function.valueOf(llllllllllllllllllIIIIIllllIllll.getInt(llllllllllllllllllIIIIIllllIlIIl) != 0);
        } else if (llllllllllllllllllIIIIIllllIlIII == Byte.TYPE || llllllllllllllllllIIIIIllllIlIII == Byte.class) {
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllIllll.getByte(llllllllllllllllllIIIIIllllIlIIl);
        } else if (llllllllllllllllllIIIIIllllIlIII == Short.TYPE || llllllllllllllllllIIIIIllllIlIII == Short.class) {
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllIllll.getShort(llllllllllllllllllIIIIIllllIlIIl);
        } else if (llllllllllllllllllIIIIIllllIlIII == Character.TYPE || llllllllllllllllllIIIIIllllIlIII == Character.class) {
            llllllllllllllllllIIIIIllllIlIll = Character.valueOf(llllllllllllllllllIIIIIllllIllll.getChar(llllllllllllllllllIIIIIllllIlIIl));
        } else if (llllllllllllllllllIIIIIllllIlIII == Integer.TYPE || llllllllllllllllllIIIIIllllIlIII == Integer.class) {
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllIllll.getInt(llllllllllllllllllIIIIIllllIlIIl);
        } else if (llllllllllllllllllIIIIIllllIlIII == Long.TYPE || llllllllllllllllllIIIIIllllIlIII == Long.class) {
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllIllll.getLong(llllllllllllllllllIIIIIllllIlIIl);
        } else if (llllllllllllllllllIIIIIllllIlIII == Float.TYPE || llllllllllllllllllIIIIIllllIlIII == Float.class) {
            llllllllllllllllllIIIIIllllIlIll = Float.valueOf(llllllllllllllllllIIIIIllllIllll.getFloat(llllllllllllllllllIIIIIllllIlIIl));
        } else if (llllllllllllllllllIIIIIllllIlIII == Double.TYPE || llllllllllllllllllIIIIIllllIlIII == Double.class) {
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllIllll.getDouble(llllllllllllllllllIIIIIllllIlIIl);
        } else if (Pointer.class.isAssignableFrom(llllllllllllllllllIIIIIllllIlIII)) {
            Pointer llllllllllllllllllIIIIIllllllIll = llllllllllllllllllIIIIIllllIllll.getPointer(llllllllllllllllllIIIIIllllIlIIl);
            if (llllllllllllllllllIIIIIllllllIll != null) {
                Pointer llllllllllllllllllIIIIIlllllllII;
                Pointer pointer = llllllllllllllllllIIIIIlllllllII = llllllllllllllllllIIIIIllllIllII instanceof Pointer ? (Pointer)llllllllllllllllllIIIIIllllIllII : null;
                llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIlllllllII == null || llllllllllllllllllIIIIIllllllIll.peer != llllllllllllllllllIIIIIlllllllII.peer ? llllllllllllllllllIIIIIllllllIll : llllllllllllllllllIIIIIlllllllII;
            }
        } else if (llllllllllllllllllIIIIIllllIlIII == String.class) {
            Pointer llllllllllllllllllIIIIIllllllIlI = llllllllllllllllllIIIIIllllIllll.getPointer(llllllllllllllllllIIIIIllllIlIIl);
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllllIlI != null ? llllllllllllllllllIIIIIllllllIlI.getString(0L) : null;
        } else if (llllllllllllllllllIIIIIllllIlIII == WString.class) {
            Pointer llllllllllllllllllIIIIIllllllIIl = llllllllllllllllllIIIIIllllIllll.getPointer(llllllllllllllllllIIIIIllllIlIIl);
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllllIIl != null ? new WString(llllllllllllllllllIIIIIllllllIIl.getWideString(0L)) : null;
        } else if (Callback.class.isAssignableFrom(llllllllllllllllllIIIIIllllIlIII)) {
            Pointer llllllllllllllllllIIIIIlllllIllI = llllllllllllllllllIIIIIllllIllll.getPointer(llllllllllllllllllIIIIIllllIlIIl);
            if (llllllllllllllllllIIIIIlllllIllI == null) {
                llllllllllllllllllIIIIIllllIlIll = null;
            } else {
                Callback llllllllllllllllllIIIIIllllllIII = (Callback)llllllllllllllllllIIIIIllllIllII;
                Pointer llllllllllllllllllIIIIIlllllIlll = CallbackReference.getFunctionPointer(llllllllllllllllllIIIIIllllllIII);
                if (!llllllllllllllllllIIIIIlllllIllI.equals(llllllllllllllllllIIIIIlllllIlll)) {
                    llllllllllllllllllIIIIIllllllIII = CallbackReference.getCallback(llllllllllllllllllIIIIIllllIlIII, llllllllllllllllllIIIIIlllllIllI);
                }
                llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllllIII;
            }
        } else if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(llllllllllllllllllIIIIIllllIlIII)) {
            Pointer llllllllllllllllllIIIIIlllllIlII = llllllllllllllllllIIIIIllllIllll.getPointer(llllllllllllllllllIIIIIllllIlIIl);
            if (llllllllllllllllllIIIIIlllllIlII == null) {
                llllllllllllllllllIIIIIllllIlIll = null;
            } else {
                Pointer llllllllllllllllllIIIIIlllllIlIl;
                Pointer pointer = llllllllllllllllllIIIIIlllllIlIl = llllllllllllllllllIIIIIllllIllII == null ? null : Native.getDirectBufferPointer((Buffer)llllllllllllllllllIIIIIllllIllII);
                if (llllllllllllllllllIIIIIlllllIlIl == null || !llllllllllllllllllIIIIIlllllIlIl.equals(llllllllllllllllllIIIIIlllllIlII)) {
                    throw new IllegalStateException("Can't autogenerate a direct buffer on memory read");
                }
                llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllIllII;
            }
        } else if (NativeMapped.class.isAssignableFrom(llllllllllllllllllIIIIIllllIlIII)) {
            NativeMapped llllllllllllllllllIIIIIlllllIIII = (NativeMapped)llllllllllllllllllIIIIIllllIllII;
            if (llllllllllllllllllIIIIIlllllIIII != null) {
                Object llllllllllllllllllIIIIIlllllIIll = llllllllllllllllllIIIIIllllIllll.getValue(llllllllllllllllllIIIIIllllIlIIl, llllllllllllllllllIIIIIlllllIIII.nativeType(), null);
                llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIlllllIIII.fromNative(llllllllllllllllllIIIIIlllllIIll, new FromNativeContext(llllllllllllllllllIIIIIllllIlIII));
                if (llllllllllllllllllIIIIIlllllIIII.equals(llllllllllllllllllIIIIIllllIlIll)) {
                    llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIlllllIIII;
                }
            } else {
                NativeMappedConverter llllllllllllllllllIIIIIlllllIIlI = NativeMappedConverter.getInstance(llllllllllllllllllIIIIIllllIlIII);
                Object llllllllllllllllllIIIIIlllllIIIl = llllllllllllllllllIIIIIllllIllll.getValue(llllllllllllllllllIIIIIllllIlIIl, llllllllllllllllllIIIIIlllllIIlI.nativeType(), null);
                llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIlllllIIlI.fromNative(llllllllllllllllllIIIIIlllllIIIl, new FromNativeContext(llllllllllllllllllIIIIIllllIlIII));
            }
        } else if (llllllllllllllllllIIIIIllllIlIII.isArray()) {
            llllllllllllllllllIIIIIllllIlIll = llllllllllllllllllIIIIIllllIllII;
            if (llllllllllllllllllIIIIIllllIlIll == null) {
                throw new IllegalStateException("Need an initialized array");
            }
            llllllllllllllllllIIIIIllllIllll.readArray(llllllllllllllllllIIIIIllllIlIIl, llllllllllllllllllIIIIIllllIlIll, llllllllllllllllllIIIIIllllIlIII.getComponentType());
        } else {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Reading \"").append(llllllllllllllllllIIIIIllllIlIII).append("\" from memory is not supported")));
        }
        return llllllllllllllllllIIIIIllllIlIll;
    }

    public void write(long llllllllllllllllllIIIIlIIlllIlIl, byte[] llllllllllllllllllIIIIlIIllllIIl, int llllllllllllllllllIIIIlIIllllIII, int llllllllllllllllllIIIIlIIlllIIlI) {
        Pointer llllllllllllllllllIIIIlIIlllIllI;
        Native.write(llllllllllllllllllIIIIlIIlllIllI, llllllllllllllllllIIIIlIIlllIllI.peer, llllllllllllllllllIIIIlIIlllIlIl, llllllllllllllllllIIIIlIIllllIIl, llllllllllllllllllIIIIlIIllllIII, llllllllllllllllllIIIIlIIlllIIlI);
    }

    public float getFloat(long llllllllllllllllllIIIIIllIIlIIlI) {
        Pointer llllllllllllllllllIIIIIllIIlIIll;
        return Native.getFloat(llllllllllllllllllIIIIIllIIlIIll, llllllllllllllllllIIIIIllIIlIIll.peer, llllllllllllllllllIIIIIllIIlIIlI);
    }

    public long indexOf(long llllllllllllllllllIIIIllIIIIIIll, byte llllllllllllllllllIIIIllIIIIIIlI) {
        Pointer llllllllllllllllllIIIIllIIIIIlII;
        return Native.indexOf(llllllllllllllllllIIIIllIIIIIlII, llllllllllllllllllIIIIllIIIIIlII.peer, llllllllllllllllllIIIIllIIIIIIll, llllllllllllllllllIIIIllIIIIIIlI);
    }

    public byte[] getByteArray(long llllllllllllllllllIIIIIlIlIllIIl, int llllllllllllllllllIIIIIlIlIlIlII) {
        Pointer llllllllllllllllllIIIIIlIlIllIlI;
        byte[] llllllllllllllllllIIIIIlIlIlIlll = new byte[llllllllllllllllllIIIIIlIlIlIlII];
        llllllllllllllllllIIIIIlIlIllIlI.read(llllllllllllllllllIIIIIlIlIllIIl, llllllllllllllllllIIIIIlIlIlIlll, 0, llllllllllllllllllIIIIIlIlIlIlII);
        return llllllllllllllllllIIIIIlIlIlIlll;
    }

    public void write(long llllllllllllllllllIIIIlIIllIlIll, short[] llllllllllllllllllIIIIlIIllIIlIl, int llllllllllllllllllIIIIlIIllIlIIl, int llllllllllllllllllIIIIlIIllIIIll) {
        Pointer llllllllllllllllllIIIIlIIllIIlll;
        Native.write(llllllllllllllllllIIIIlIIllIIlll, llllllllllllllllllIIIIlIIllIIlll.peer, llllllllllllllllllIIIIlIIllIlIll, llllllllllllllllllIIIIlIIllIIlIl, llllllllllllllllllIIIIlIIllIlIIl, llllllllllllllllllIIIIlIIllIIIll);
    }

    public void write(long llllllllllllllllllIIIIlIIIlIIIII, double[] llllllllllllllllllIIIIlIIIIllIlI, int llllllllllllllllllIIIIlIIIIllIIl, int llllllllllllllllllIIIIlIIIIllIII) {
        Pointer llllllllllllllllllIIIIlIIIlIIIIl;
        Native.write(llllllllllllllllllIIIIlIIIlIIIIl, llllllllllllllllllIIIIlIIIlIIIIl.peer, llllllllllllllllllIIIIlIIIlIIIII, llllllllllllllllllIIIIlIIIIllIlI, llllllllllllllllllIIIIlIIIIllIIl, llllllllllllllllllIIIIlIIIIllIII);
    }

    public void setPointer(long lllllllllllllllllIlllllllllllIll, Pointer lllllllllllllllllIllllllllllllIl) {
        Pointer lllllllllllllllllIllllllllllllII;
        Native.setPointer(lllllllllllllllllIllllllllllllII, lllllllllllllllllIllllllllllllII.peer, lllllllllllllllllIlllllllllllIll, lllllllllllllllllIllllllllllllIl != null ? lllllllllllllllllIllllllllllllIl.peer : 0L);
    }

    public Pointer share(long llllllllllllllllllIIIIllIIIllIlI, long llllllllllllllllllIIIIllIIIlllII) {
        Pointer llllllllllllllllllIIIIllIIIllllI;
        if (llllllllllllllllllIIIIllIIIllIlI == 0L) {
            return llllllllllllllllllIIIIllIIIllllI;
        }
        return new Pointer(llllllllllllllllllIIIIllIIIllllI.peer + llllllllllllllllllIIIIllIIIllIlI);
    }

    public void write(long llllllllllllllllllIIIIlIIIlllIIl, long[] llllllllllllllllllIIIIlIIIllllIl, int llllllllllllllllllIIIIlIIIllIlll, int llllllllllllllllllIIIIlIIIllIllI) {
        Pointer llllllllllllllllllIIIIlIIIlllIlI;
        Native.write(llllllllllllllllllIIIIlIIIlllIlI, llllllllllllllllllIIIIlIIIlllIlI.peer, llllllllllllllllllIIIIlIIIlllIIl, llllllllllllllllllIIIIlIIIllllIl, llllllllllllllllllIIIIlIIIllIlll, llllllllllllllllllIIIIlIIIllIllI);
    }

    public float[] getFloatArray(long llllllllllllllllllIIIIIlIIIlllIl, int llllllllllllllllllIIIIIlIIIlllII) {
        Pointer llllllllllllllllllIIIIIlIIIllIlI;
        float[] llllllllllllllllllIIIIIlIIIllIll = new float[llllllllllllllllllIIIIIlIIIlllII];
        llllllllllllllllllIIIIIlIIIllIlI.read(llllllllllllllllllIIIIIlIIIlllIl, llllllllllllllllllIIIIIlIIIllIll, 0, llllllllllllllllllIIIIIlIIIlllII);
        return llllllllllllllllllIIIIIlIIIllIll;
    }

    public int[] getIntArray(long llllllllllllllllllIIIIIlIIllIIIl, int llllllllllllllllllIIIIIlIIllIlII) {
        Pointer llllllllllllllllllIIIIIlIIllIllI;
        int[] llllllllllllllllllIIIIIlIIllIIll = new int[llllllllllllllllllIIIIIlIIllIlII];
        llllllllllllllllllIIIIIlIIllIllI.read(llllllllllllllllllIIIIIlIIllIIIl, llllllllllllllllllIIIIIlIIllIIll, 0, llllllllllllllllllIIIIIlIIllIlII);
        return llllllllllllllllllIIIIIlIIllIIll;
    }

    public void setString(long lllllllllllllllllIlllllllllIIIII, WString lllllllllllllllllIllllllllIlllII) {
        Pointer lllllllllllllllllIlllllllllIIIIl;
        lllllllllllllllllIlllllllllIIIIl.setWideString(lllllllllllllllllIlllllllllIIIII, lllllllllllllllllIllllllllIlllII == null ? null : lllllllllllllllllIllllllllIlllII.toString());
    }

    public void read(long llllllllllllllllllIIIIlIlIllllll, long[] llllllllllllllllllIIIIlIlIlllIIl, int llllllllllllllllllIIIIlIlIllllIl, int llllllllllllllllllIIIIlIlIllIlll) {
        Pointer llllllllllllllllllIIIIlIlIlllIll;
        Native.read(llllllllllllllllllIIIIlIlIlllIll, llllllllllllllllllIIIIlIlIlllIll.peer, llllllllllllllllllIIIIlIlIllllll, llllllllllllllllllIIIIlIlIlllIIl, llllllllllllllllllIIIIlIlIllllIl, llllllllllllllllllIIIIlIlIllIlll);
    }

    public void setLong(long llllllllllllllllllIIIIIIIIIlllll, long llllllllllllllllllIIIIIIIIIllllI) {
        Pointer llllllllllllllllllIIIIIIIIlIIIll;
        Native.setLong(llllllllllllllllllIIIIIIIIlIIIll, llllllllllllllllllIIIIIIIIlIIIll.peer, llllllllllllllllllIIIIIIIIIlllll, llllllllllllllllllIIIIIIIIIllllI);
    }

    public boolean equals(Object llllllllllllllllllIIIIllIIIIlllI) {
        Pointer llllllllllllllllllIIIIllIIIlIIIl;
        if (llllllllllllllllllIIIIllIIIIlllI == llllllllllllllllllIIIIllIIIlIIIl) {
            return true;
        }
        if (llllllllllllllllllIIIIllIIIIlllI == null) {
            return false;
        }
        return llllllllllllllllllIIIIllIIIIlllI instanceof Pointer && ((Pointer)llllllllllllllllllIIIIllIIIIlllI).peer == llllllllllllllllllIIIIllIIIlIIIl.peer;
    }

    public char[] getCharArray(long llllllllllllllllllIIIIIlIlIIlIIl, int llllllllllllllllllIIIIIlIlIIllII) {
        Pointer llllllllllllllllllIIIIIlIlIIlIlI;
        char[] llllllllllllllllllIIIIIlIlIIlIll = new char[llllllllllllllllllIIIIIlIlIIllII];
        llllllllllllllllllIIIIIlIlIIlIlI.read(llllllllllllllllllIIIIIlIlIIlIIl, llllllllllllllllllIIIIIlIlIIlIll, 0, llllllllllllllllllIIIIIlIlIIllII);
        return llllllllllllllllllIIIIIlIlIIlIll;
    }

    private void writeArray(long llllllllllllllllllIIIIIIIllIIIll, Object llllllllllllllllllIIIIIIIllIIIlI, Class<?> llllllllllllllllllIIIIIIIllIIIIl) {
        Pointer llllllllllllllllllIIIIIIIllIIlII;
        if (llllllllllllllllllIIIIIIIllIIIIl == Byte.TYPE) {
            byte[] llllllllllllllllllIIIIIIIllllIII = (byte[])llllllllllllllllllIIIIIIIllIIIlI;
            llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIllllIII, 0, llllllllllllllllllIIIIIIIllllIII.length);
        } else if (llllllllllllllllllIIIIIIIllIIIIl == Short.TYPE) {
            short[] llllllllllllllllllIIIIIIIlllIlll = (short[])llllllllllllllllllIIIIIIIllIIIlI;
            llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIlllIlll, 0, llllllllllllllllllIIIIIIIlllIlll.length);
        } else if (llllllllllllllllllIIIIIIIllIIIIl == Character.TYPE) {
            char[] llllllllllllllllllIIIIIIIlllIllI = (char[])llllllllllllllllllIIIIIIIllIIIlI;
            llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIlllIllI, 0, llllllllllllllllllIIIIIIIlllIllI.length);
        } else if (llllllllllllllllllIIIIIIIllIIIIl == Integer.TYPE) {
            int[] llllllllllllllllllIIIIIIIlllIlIl = (int[])llllllllllllllllllIIIIIIIllIIIlI;
            llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIlllIlIl, 0, llllllllllllllllllIIIIIIIlllIlIl.length);
        } else if (llllllllllllllllllIIIIIIIllIIIIl == Long.TYPE) {
            long[] llllllllllllllllllIIIIIIIlllIlII = (long[])llllllllllllllllllIIIIIIIllIIIlI;
            llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIlllIlII, 0, llllllllllllllllllIIIIIIIlllIlII.length);
        } else if (llllllllllllllllllIIIIIIIllIIIIl == Float.TYPE) {
            float[] llllllllllllllllllIIIIIIIlllIIll = (float[])llllllllllllllllllIIIIIIIllIIIlI;
            llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIlllIIll, 0, llllllllllllllllllIIIIIIIlllIIll.length);
        } else if (llllllllllllllllllIIIIIIIllIIIIl == Double.TYPE) {
            double[] llllllllllllllllllIIIIIIIlllIIlI = (double[])llllllllllllllllllIIIIIIIllIIIlI;
            llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIlllIIlI, 0, llllllllllllllllllIIIIIIIlllIIlI.length);
        } else if (Pointer.class.isAssignableFrom(llllllllllllllllllIIIIIIIllIIIIl)) {
            Pointer[] llllllllllllllllllIIIIIIIlllIIIl = (Pointer[])llllllllllllllllllIIIIIIIllIIIlI;
            llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIlllIIIl, 0, llllllllllllllllllIIIIIIIlllIIIl.length);
        } else if (Structure.class.isAssignableFrom(llllllllllllllllllIIIIIIIllIIIIl)) {
            Structure[] llllllllllllllllllIIIIIIIllIlIll = (Structure[])llllllllllllllllllIIIIIIIllIIIlI;
            if (Structure.ByReference.class.isAssignableFrom(llllllllllllllllllIIIIIIIllIIIIl)) {
                Pointer[] llllllllllllllllllIIIIIIIllIllll = new Pointer[llllllllllllllllllIIIIIIIllIlIll.length];
                for (int llllllllllllllllllIIIIIIIlllIIII = 0; llllllllllllllllllIIIIIIIlllIIII < llllllllllllllllllIIIIIIIllIlIll.length; ++llllllllllllllllllIIIIIIIlllIIII) {
                    if (llllllllllllllllllIIIIIIIllIlIll[llllllllllllllllllIIIIIIIlllIIII] == null) {
                        llllllllllllllllllIIIIIIIllIllll[llllllllllllllllllIIIIIIIlllIIII] = null;
                        continue;
                    }
                    llllllllllllllllllIIIIIIIllIllll[llllllllllllllllllIIIIIIIlllIIII] = llllllllllllllllllIIIIIIIllIlIll[llllllllllllllllllIIIIIIIlllIIII].getPointer();
                    llllllllllllllllllIIIIIIIllIlIll[llllllllllllllllllIIIIIIIlllIIII].write();
                }
                llllllllllllllllllIIIIIIIllIIlII.write(llllllllllllllllllIIIIIIIllIIIll, llllllllllllllllllIIIIIIIllIllll, 0, llllllllllllllllllIIIIIIIllIllll.length);
            } else {
                Structure llllllllllllllllllIIIIIIIllIllIl = llllllllllllllllllIIIIIIIllIlIll[0];
                if (llllllllllllllllllIIIIIIIllIllIl == null) {
                    llllllllllllllllllIIIIIIIllIlIll[0] = llllllllllllllllllIIIIIIIllIllIl = Structure.newInstance(llllllllllllllllllIIIIIIIllIIIIl, llllllllllllllllllIIIIIIIllIIlII.share(llllllllllllllllllIIIIIIIllIIIll));
                } else {
                    llllllllllllllllllIIIIIIIllIllIl.useMemory(llllllllllllllllllIIIIIIIllIIlII, (int)llllllllllllllllllIIIIIIIllIIIll, true);
                }
                llllllllllllllllllIIIIIIIllIllIl.write();
                Structure[] llllllllllllllllllIIIIIIIllIllII = llllllllllllllllllIIIIIIIllIllIl.toArray(llllllllllllllllllIIIIIIIllIlIll.length);
                for (int llllllllllllllllllIIIIIIIllIlllI = 1; llllllllllllllllllIIIIIIIllIlllI < llllllllllllllllllIIIIIIIllIlIll.length; ++llllllllllllllllllIIIIIIIllIlllI) {
                    if (llllllllllllllllllIIIIIIIllIlIll[llllllllllllllllllIIIIIIIllIlllI] == null) {
                        llllllllllllllllllIIIIIIIllIlIll[llllllllllllllllllIIIIIIIllIlllI] = llllllllllllllllllIIIIIIIllIllII[llllllllllllllllllIIIIIIIllIlllI];
                    } else {
                        llllllllllllllllllIIIIIIIllIlIll[llllllllllllllllllIIIIIIIllIlllI].useMemory(llllllllllllllllllIIIIIIIllIIlII, (int)(llllllllllllllllllIIIIIIIllIIIll + (long)(llllllllllllllllllIIIIIIIllIlllI * llllllllllllllllllIIIIIIIllIlIll[llllllllllllllllllIIIIIIIllIlllI].size())), true);
                    }
                    llllllllllllllllllIIIIIIIllIlIll[llllllllllllllllllIIIIIIIllIlllI].write();
                }
            }
        } else if (NativeMapped.class.isAssignableFrom(llllllllllllllllllIIIIIIIllIIIIl)) {
            NativeMapped[] llllllllllllllllllIIIIIIIllIlIII = (NativeMapped[])llllllllllllllllllIIIIIIIllIIIlI;
            NativeMappedConverter llllllllllllllllllIIIIIIIllIIlll = NativeMappedConverter.getInstance(llllllllllllllllllIIIIIIIllIIIIl);
            Class<?> llllllllllllllllllIIIIIIIllIIllI = llllllllllllllllllIIIIIIIllIIlll.nativeType();
            int llllllllllllllllllIIIIIIIllIIlIl = Native.getNativeSize(llllllllllllllllllIIIIIIIllIIIlI.getClass(), llllllllllllllllllIIIIIIIllIIIlI) / llllllllllllllllllIIIIIIIllIlIII.length;
            for (int llllllllllllllllllIIIIIIIllIlIIl = 0; llllllllllllllllllIIIIIIIllIlIIl < llllllllllllllllllIIIIIIIllIlIII.length; ++llllllllllllllllllIIIIIIIllIlIIl) {
                Object llllllllllllllllllIIIIIIIllIlIlI = llllllllllllllllllIIIIIIIllIIlll.toNative(llllllllllllllllllIIIIIIIllIlIII[llllllllllllllllllIIIIIIIllIlIIl], new ToNativeContext());
                llllllllllllllllllIIIIIIIllIIlII.setValue(llllllllllllllllllIIIIIIIllIIIll + (long)(llllllllllllllllllIIIIIIIllIlIIl * llllllllllllllllllIIIIIIIllIIlIl), llllllllllllllllllIIIIIIIllIlIlI, llllllllllllllllllIIIIIIIllIIllI);
            }
        } else {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Writing array of ").append(llllllllllllllllllIIIIIIIllIIIIl).append(" to memory not supported")));
        }
    }

    public String toString() {
        Pointer lllllllllllllllllIlllllllIlIIllI;
        return String.valueOf(new StringBuilder().append("native@0x").append(Long.toHexString(lllllllllllllllllIlllllllIlIIllI.peer)));
    }

    public void setNativeLong(long llllllllllllllllllIIIIIIIIIlIllI, NativeLong llllllllllllllllllIIIIIIIIIlIlIl) {
        Pointer llllllllllllllllllIIIIIIIIIlIlll;
        if (NativeLong.SIZE == 8) {
            llllllllllllllllllIIIIIIIIIlIlll.setLong(llllllllllllllllllIIIIIIIIIlIllI, llllllllllllllllllIIIIIIIIIlIlIl.longValue());
        } else {
            llllllllllllllllllIIIIIIIIIlIlll.setInt(llllllllllllllllllIIIIIIIIIlIllI, llllllllllllllllllIIIIIIIIIlIlIl.intValue());
        }
    }

    static {
        SIZE = Native.POINTER_SIZE;
        if (SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        NULL = null;
    }

    public static final Pointer createConstant(int llllllllllllllllllIIIIllIIllllll) {
        return new Opaque((long)llllllllllllllllllIIIIllIIllllll & 0xFFFFFFFFFFFFFFFFL);
    }

    public byte getByte(long llllllllllllllllllIIIIIllIllIllI) {
        Pointer llllllllllllllllllIIIIIllIllIlll;
        return Native.getByte(llllllllllllllllllIIIIIllIllIlll, llllllllllllllllllIIIIIllIllIlll.peer, llllllllllllllllllIIIIIllIllIllI);
    }

    public void setByte(long llllllllllllllllllIIIIIIIlIIIIll, byte llllllllllllllllllIIIIIIIlIIIIlI) {
        Pointer llllllllllllllllllIIIIIIIlIIIlll;
        Native.setByte(llllllllllllllllllIIIIIIIlIIIlll, llllllllllllllllllIIIIIIIlIIIlll.peer, llllllllllllllllllIIIIIIIlIIIIll, llllllllllllllllllIIIIIIIlIIIIlI);
    }

    public String[] getWideStringArray(long llllllllllllllllllIIIIIIllIIlIll) {
        Pointer llllllllllllllllllIIIIIIllIIllII;
        return llllllllllllllllllIIIIIIllIIllII.getWideStringArray(llllllllllllllllllIIIIIIllIIlIll, -1);
    }

    public void setShort(long llllllllllllllllllIIIIIIIIlllIlI, short llllllllllllllllllIIIIIIIIlllIIl) {
        Pointer llllllllllllllllllIIIIIIIIlllllI;
        Native.setShort(llllllllllllllllllIIIIIIIIlllllI, llllllllllllllllllIIIIIIIIlllllI.peer, llllllllllllllllllIIIIIIIIlllIlI, llllllllllllllllllIIIIIIIIlllIIl);
    }

    public Pointer share(long llllllllllllllllllIIIIllIIlIIlll) {
        Pointer llllllllllllllllllIIIIllIIlIIllI;
        return llllllllllllllllllIIIIllIIlIIllI.share(llllllllllllllllllIIIIllIIlIIlll, 0L);
    }

    public void write(long llllllllllllllllllIIIIlIIlIIlIII, int[] llllllllllllllllllIIIIlIIlIIllII, int llllllllllllllllllIIIIlIIlIIIllI, int llllllllllllllllllIIIIlIIlIIIlIl) {
        Pointer llllllllllllllllllIIIIlIIlIIlllI;
        Native.write(llllllllllllllllllIIIIlIIlIIlllI, llllllllllllllllllIIIIlIIlIIlllI.peer, llllllllllllllllllIIIIlIIlIIlIII, llllllllllllllllllIIIIlIIlIIllII, llllllllllllllllllIIIIlIIlIIIllI, llllllllllllllllllIIIIlIIlIIIlIl);
    }

    @Deprecated
    public String[] getStringArray(long llllllllllllllllllIIIIIIllIlIIII, boolean llllllllllllllllllIIIIIIllIlIIlI) {
        Pointer llllllllllllllllllIIIIIIllIlIIIl;
        return llllllllllllllllllIIIIIIllIlIIIl.getStringArray(llllllllllllllllllIIIIIIllIlIIII, -1, llllllllllllllllllIIIIIIllIlIIlI);
    }

    public static void nativeValue(Pointer lllllllllllllllllIlllllllIIllllI, long lllllllllllllllllIlllllllIIlllll) {
        lllllllllllllllllIlllllllIIllllI.peer = lllllllllllllllllIlllllllIIlllll;
    }

    public String getString(long llllllllllllllllllIIIIIlIllIIIII, String llllllllllllllllllIIIIIlIllIIIlI) {
        Pointer llllllllllllllllllIIIIIlIllIIIIl;
        return Native.getString(llllllllllllllllllIIIIIlIllIIIIl, llllllllllllllllllIIIIIlIllIIIII, llllllllllllllllllIIIIIlIllIIIlI);
    }

    public Pointer[] getPointerArray(long llllllllllllllllllIIIIIlIIIIIlII) {
        Pointer llllllllllllllllllIIIIIlIIIIIlIl;
        ArrayList<Pointer> llllllllllllllllllIIIIIlIIIIIIll = new ArrayList<Pointer>();
        int llllllllllllllllllIIIIIlIIIIIIlI = 0;
        Pointer llllllllllllllllllIIIIIlIIIIIIIl = llllllllllllllllllIIIIIlIIIIIlIl.getPointer(llllllllllllllllllIIIIIlIIIIIlII);
        while (llllllllllllllllllIIIIIlIIIIIIIl != null) {
            llllllllllllllllllIIIIIlIIIIIIll.add(llllllllllllllllllIIIIIlIIIIIIIl);
            llllllllllllllllllIIIIIlIIIIIIIl = llllllllllllllllllIIIIIlIIIIIlIl.getPointer(llllllllllllllllllIIIIIlIIIIIlII + (long)(llllllllllllllllllIIIIIlIIIIIIlI += SIZE));
        }
        return llllllllllllllllllIIIIIlIIIIIIll.toArray(new Pointer[llllllllllllllllllIIIIIlIIIIIIll.size()]);
    }

    public String[] getStringArray(long llllllllllllllllllIIIIIIlllIIlIl, String llllllllllllllllllIIIIIIlllIIlII) {
        Pointer llllllllllllllllllIIIIIIlllIIllI;
        return llllllllllllllllllIIIIIIlllIIllI.getStringArray(llllllllllllllllllIIIIIIlllIIlIl, -1, llllllllllllllllllIIIIIIlllIIlII);
    }

    public static final Pointer createConstant(long llllllllllllllllllIIIIllIlIIIllI) {
        return new Opaque(llllllllllllllllllIIIIllIlIIIllI);
    }

    public long[] getLongArray(long llllllllllllllllllIIIIIlIIlIIlIl, int llllllllllllllllllIIIIIlIIlIlIII) {
        Pointer llllllllllllllllllIIIIIlIIlIlIlI;
        long[] llllllllllllllllllIIIIIlIIlIIlll = new long[llllllllllllllllllIIIIIlIIlIlIII];
        llllllllllllllllllIIIIIlIIlIlIlI.read(llllllllllllllllllIIIIIlIIlIIlIl, llllllllllllllllllIIIIIlIIlIIlll, 0, llllllllllllllllllIIIIIlIIlIlIII);
        return llllllllllllllllllIIIIIlIIlIIlll;
    }

    public void setWideString(long lllllllllllllllllIlllllllllIlIIl, String lllllllllllllllllIlllllllllIlIII) {
        Pointer lllllllllllllllllIlllllllllIIlll;
        Native.setWideString(lllllllllllllllllIlllllllllIIlll, lllllllllllllllllIlllllllllIIlll.peer, lllllllllllllllllIlllllllllIlIIl, lllllllllllllllllIlllllllllIlIII);
    }

    public void write(long llllllllllllllllllIIIIlIIlIlIlll, char[] llllllllllllllllllIIIIlIIlIlIllI, int llllllllllllllllllIIIIlIIlIllIlI, int llllllllllllllllllIIIIlIIlIllIIl) {
        Pointer llllllllllllllllllIIIIlIIlIllIII;
        Native.write(llllllllllllllllllIIIIlIIlIllIII, llllllllllllllllllIIIIlIIlIllIII.peer, llllllllllllllllllIIIIlIIlIlIlll, llllllllllllllllllIIIIlIIlIlIllI, llllllllllllllllllIIIIlIIlIllIlI, llllllllllllllllllIIIIlIIlIllIIl);
    }

    public long getLong(long llllllllllllllllllIIIIIllIIllllI) {
        Pointer llllllllllllllllllIIIIIllIIlllll;
        return Native.getLong(llllllllllllllllllIIIIIllIIlllll, llllllllllllllllllIIIIIllIIlllll.peer, llllllllllllllllllIIIIIllIIllllI);
    }

    public void read(long llllllllllllllllllIIIIlIllllIllI, byte[] llllllllllllllllllIIIIlIlllllIlI, int llllllllllllllllllIIIIlIllllIlII, int llllllllllllllllllIIIIlIllllIIll) {
        Pointer llllllllllllllllllIIIIlIllllllII;
        Native.read(llllllllllllllllllIIIIlIllllllII, llllllllllllllllllIIIIlIllllllII.peer, llllllllllllllllllIIIIlIllllIllI, llllllllllllllllllIIIIlIlllllIlI, llllllllllllllllllIIIIlIllllIlII, llllllllllllllllllIIIIlIllllIIll);
    }

    public void write(long llllllllllllllllllIIIIlIIIlIlIlI, float[] llllllllllllllllllIIIIlIIIlIlIIl, int llllllllllllllllllIIIIlIIIlIllIl, int llllllllllllllllllIIIIlIIIlIllII) {
        Pointer llllllllllllllllllIIIIlIIIllIIII;
        Native.write(llllllllllllllllllIIIIlIIIllIIII, llllllllllllllllllIIIIlIIIllIIII.peer, llllllllllllllllllIIIIlIIIlIlIlI, llllllllllllllllllIIIIlIIIlIlIIl, llllllllllllllllllIIIIlIIIlIllIl, llllllllllllllllllIIIIlIIIlIllII);
    }

    public char getChar(long llllllllllllllllllIIIIIllIllIIlI) {
        Pointer llllllllllllllllllIIIIIllIllIIIl;
        return Native.getChar(llllllllllllllllllIIIIIllIllIIIl, llllllllllllllllllIIIIIllIllIIIl.peer, llllllllllllllllllIIIIIllIllIIlI);
    }

    public short getShort(long llllllllllllllllllIIIIIllIlIlIlI) {
        Pointer llllllllllllllllllIIIIIllIlIllIl;
        return Native.getShort(llllllllllllllllllIIIIIllIlIllIl, llllllllllllllllllIIIIIllIlIllIl.peer, llllllllllllllllllIIIIIllIlIlIlI);
    }

    void setValue(long llllllllllllllllllIIIIIIlIIIlIll, Object llllllllllllllllllIIIIIIlIIIlIlI, Class<?> llllllllllllllllllIIIIIIlIIIIlIl) {
        Pointer llllllllllllllllllIIIIIIlIIIllII;
        if (llllllllllllllllllIIIIIIlIIIIlIl == Boolean.TYPE || llllllllllllllllllIIIIIIlIIIIlIl == Boolean.class) {
            llllllllllllllllllIIIIIIlIIIllII.setInt(llllllllllllllllllIIIIIIlIIIlIll, Boolean.TRUE.equals(llllllllllllllllllIIIIIIlIIIlIlI) ? -1 : 0);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == Byte.TYPE || llllllllllllllllllIIIIIIlIIIIlIl == Byte.class) {
            llllllllllllllllllIIIIIIlIIIllII.setByte(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlIlI == null ? (byte)0 : (Byte)llllllllllllllllllIIIIIIlIIIlIlI);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == Short.TYPE || llllllllllllllllllIIIIIIlIIIIlIl == Short.class) {
            llllllllllllllllllIIIIIIlIIIllII.setShort(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlIlI == null ? (short)0 : (Short)llllllllllllllllllIIIIIIlIIIlIlI);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == Character.TYPE || llllllllllllllllllIIIIIIlIIIIlIl == Character.class) {
            llllllllllllllllllIIIIIIlIIIllII.setChar(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlIlI == null ? (char)'\u0000' : ((Character)llllllllllllllllllIIIIIIlIIIlIlI).charValue());
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == Integer.TYPE || llllllllllllllllllIIIIIIlIIIIlIl == Integer.class) {
            llllllllllllllllllIIIIIIlIIIllII.setInt(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlIlI == null ? 0 : (Integer)llllllllllllllllllIIIIIIlIIIlIlI);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == Long.TYPE || llllllllllllllllllIIIIIIlIIIIlIl == Long.class) {
            llllllllllllllllllIIIIIIlIIIllII.setLong(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlIlI == null ? 0L : (Long)llllllllllllllllllIIIIIIlIIIlIlI);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == Float.TYPE || llllllllllllllllllIIIIIIlIIIIlIl == Float.class) {
            llllllllllllllllllIIIIIIlIIIllII.setFloat(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlIlI == null ? 0.0f : ((Float)llllllllllllllllllIIIIIIlIIIlIlI).floatValue());
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == Double.TYPE || llllllllllllllllllIIIIIIlIIIIlIl == Double.class) {
            llllllllllllllllllIIIIIIlIIIllII.setDouble(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlIlI == null ? 0.0 : (Double)llllllllllllllllllIIIIIIlIIIlIlI);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == Pointer.class) {
            llllllllllllllllllIIIIIIlIIIllII.setPointer(llllllllllllllllllIIIIIIlIIIlIll, (Pointer)llllllllllllllllllIIIIIIlIIIlIlI);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == String.class) {
            llllllllllllllllllIIIIIIlIIIllII.setPointer(llllllllllllllllllIIIIIIlIIIlIll, (Pointer)llllllllllllllllllIIIIIIlIIIlIlI);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl == WString.class) {
            llllllllllllllllllIIIIIIlIIIllII.setPointer(llllllllllllllllllIIIIIIlIIIlIll, (Pointer)llllllllllllllllllIIIIIIlIIIlIlI);
        } else if (Structure.class.isAssignableFrom(llllllllllllllllllIIIIIIlIIIIlIl)) {
            Structure llllllllllllllllllIIIIIIlIIlIIII = (Structure)llllllllllllllllllIIIIIIlIIIlIlI;
            if (Structure.ByReference.class.isAssignableFrom(llllllllllllllllllIIIIIIlIIIIlIl)) {
                llllllllllllllllllIIIIIIlIIIllII.setPointer(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIlIIII == null ? null : llllllllllllllllllIIIIIIlIIlIIII.getPointer());
                if (llllllllllllllllllIIIIIIlIIlIIII != null) {
                    llllllllllllllllllIIIIIIlIIlIIII.autoWrite();
                }
            } else {
                llllllllllllllllllIIIIIIlIIlIIII.useMemory(llllllllllllllllllIIIIIIlIIIllII, (int)llllllllllllllllllIIIIIIlIIIlIll, true);
                llllllllllllllllllIIIIIIlIIlIIII.write();
            }
        } else if (Callback.class.isAssignableFrom(llllllllllllllllllIIIIIIlIIIIlIl)) {
            llllllllllllllllllIIIIIIlIIIllII.setPointer(llllllllllllllllllIIIIIIlIIIlIll, CallbackReference.getFunctionPointer((Callback)llllllllllllllllllIIIIIIlIIIlIlI));
        } else if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(llllllllllllllllllIIIIIIlIIIIlIl)) {
            Pointer llllllllllllllllllIIIIIIlIIIllll = llllllllllllllllllIIIIIIlIIIlIlI == null ? null : Native.getDirectBufferPointer((Buffer)llllllllllllllllllIIIIIIlIIIlIlI);
            llllllllllllllllllIIIIIIlIIIllII.setPointer(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIllll);
        } else if (NativeMapped.class.isAssignableFrom(llllllllllllllllllIIIIIIlIIIIlIl)) {
            NativeMappedConverter llllllllllllllllllIIIIIIlIIIlllI = NativeMappedConverter.getInstance(llllllllllllllllllIIIIIIlIIIIlIl);
            Class<?> llllllllllllllllllIIIIIIlIIIllIl = llllllllllllllllllIIIIIIlIIIlllI.nativeType();
            llllllllllllllllllIIIIIIlIIIllII.setValue(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlllI.toNative(llllllllllllllllllIIIIIIlIIIlIlI, new ToNativeContext()), llllllllllllllllllIIIIIIlIIIllIl);
        } else if (llllllllllllllllllIIIIIIlIIIIlIl.isArray()) {
            llllllllllllllllllIIIIIIlIIIllII.writeArray(llllllllllllllllllIIIIIIlIIIlIll, llllllllllllllllllIIIIIIlIIIlIlI, llllllllllllllllllIIIIIIlIIIIlIl.getComponentType());
        } else {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Writing ").append(llllllllllllllllllIIIIIIlIIIIlIl).append(" to memory is not supported")));
        }
    }

    public NativeLong getNativeLong(long llllllllllllllllllIIIIIllIIllIII) {
        Pointer llllllllllllllllllIIIIIllIIllIIl;
        return new NativeLong(NativeLong.SIZE == 8 ? llllllllllllllllllIIIIIllIIllIIl.getLong(llllllllllllllllllIIIIIllIIllIII) : (long)llllllllllllllllllIIIIIllIIllIIl.getInt(llllllllllllllllllIIIIIllIIllIII));
    }

    public Pointer[] getPointerArray(long llllllllllllllllllIIIIIIllllIIlI, int llllllllllllllllllIIIIIIllllIlIl) {
        Pointer llllllllllllllllllIIIIIIllllIIll;
        Pointer[] llllllllllllllllllIIIIIIllllIlII = new Pointer[llllllllllllllllllIIIIIIllllIlIl];
        llllllllllllllllllIIIIIIllllIIll.read(llllllllllllllllllIIIIIIllllIIlI, llllllllllllllllllIIIIIIllllIlII, 0, llllllllllllllllllIIIIIIllllIlIl);
        return llllllllllllllllllIIIIIIllllIlII;
    }

    public void read(long llllllllllllllllllIIIIlIllIllIII, char[] llllllllllllllllllIIIIlIllIlIlll, int llllllllllllllllllIIIIlIllIlIllI, int llllllllllllllllllIIIIlIllIlIlIl) {
        Pointer llllllllllllllllllIIIIlIllIllIIl;
        Native.read(llllllllllllllllllIIIIlIllIllIIl, llllllllllllllllllIIIIlIllIllIIl.peer, llllllllllllllllllIIIIlIllIllIII, llllllllllllllllllIIIIlIllIlIlll, llllllllllllllllllIIIIlIllIlIllI, llllllllllllllllllIIIIlIllIlIlIl);
    }

    public Pointer getPointer(long llllllllllllllllllIIIIIllIIIIllI) {
        Pointer llllllllllllllllllIIIIIllIIIIlll;
        return Native.getPointer(llllllllllllllllllIIIIIllIIIIlll.peer + llllllllllllllllllIIIIIllIIIIllI);
    }

    @Deprecated
    public String[] getStringArray(long llllllllllllllllllIIIIIIlIlllIlI, int llllllllllllllllllIIIIIIlIlllIIl, boolean llllllllllllllllllIIIIIIlIlllIII) {
        Pointer llllllllllllllllllIIIIIIlIlllIll;
        return llllllllllllllllllIIIIIIlIlllIll.getStringArray(llllllllllllllllllIIIIIIlIlllIlI, llllllllllllllllllIIIIIIlIlllIIl, llllllllllllllllllIIIIIIlIlllIII ? "--WIDE-STRING--" : Native.getDefaultStringEncoding());
    }

    @Deprecated
    public String getString(long llllllllllllllllllIIIIIlIllllIII, boolean llllllllllllllllllIIIIIlIlllIlll) {
        Pointer llllllllllllllllllIIIIIlIlllIllI;
        return llllllllllllllllllIIIIIlIlllIlll ? llllllllllllllllllIIIIIlIlllIllI.getWideString(llllllllllllllllllIIIIIlIllllIII) : llllllllllllllllllIIIIIlIlllIllI.getString(llllllllllllllllllIIIIIlIllllIII);
    }

    public double getDouble(long llllllllllllllllllIIIIIllIIIllII) {
        Pointer llllllllllllllllllIIIIIllIIIllll;
        return Native.getDouble(llllllllllllllllllIIIIIllIIIllll, llllllllllllllllllIIIIIllIIIllll.peer, llllllllllllllllllIIIIIllIIIllII);
    }

    private static class Opaque
    extends Pointer {
        private final /* synthetic */ String MSG;

        @Override
        public void read(long lIIIlllllIllIlI, short[] lIIIlllllIllIIl, int lIIIlllllIllIII, int lIIIlllllIlIlll) {
            Opaque lIIIlllllIllIll;
            throw new UnsupportedOperationException(lIIIlllllIllIll.MSG);
        }

        @Override
        public void write(long lIIIllllIIlIlII, long[] lIIIllllIIlIIll, int lIIIllllIIlIIlI, int lIIIllllIIlIIIl) {
            Opaque lIIIllllIIlIIII;
            throw new UnsupportedOperationException(lIIIllllIIlIIII.MSG);
        }

        @Override
        public void setLong(long lIIIlllIIllIllI, long lIIIlllIIllIlIl) {
            Opaque lIIIlllIIllIlll;
            throw new UnsupportedOperationException(lIIIlllIIllIlll.MSG);
        }

        @Override
        public void write(long lIIIllllIIIllIl, float[] lIIIllllIIIllII, int lIIIllllIIIlIll, int lIIIllllIIIlIlI) {
            Opaque lIIIllllIIIlllI;
            throw new UnsupportedOperationException(lIIIllllIIIlllI.MSG);
        }

        @Override
        public void setChar(long lIIIlllIlIIIlIl, char lIIIlllIlIIIlII) {
            Opaque lIIIlllIlIIIIll;
            throw new UnsupportedOperationException(lIIIlllIlIIIIll.MSG);
        }

        @Override
        public int getInt(long lIIIlllIllIIlll) {
            Opaque lIIIlllIllIlIII;
            throw new UnsupportedOperationException(lIIIlllIllIlIII.MSG);
        }

        @Override
        public Pointer share(long lIIIlllllllIllI, long lIIIlllllllIlIl) {
            Opaque lIIIlllllllIlll;
            throw new UnsupportedOperationException(lIIIlllllllIlll.MSG);
        }

        @Override
        public void setWideString(long lIIIlllIIIlllII, String lIIIlllIIIllIll) {
            Opaque lIIIlllIIIlllIl;
            throw new UnsupportedOperationException(lIIIlllIIIlllIl.MSG);
        }

        @Override
        public short getShort(long lIIIlllIllIlIll) {
            Opaque lIIIlllIllIllII;
            throw new UnsupportedOperationException(lIIIlllIllIllII.MSG);
        }

        @Override
        public void read(long lIIIlllllIIIlIl, float[] lIIIlllllIIIlII, int lIIIlllllIIIIll, int lIIIlllllIIIIlI) {
            Opaque lIIIlllllIIIIIl;
            throw new UnsupportedOperationException(lIIIlllllIIIIIl.MSG);
        }

        @Override
        public void write(long lIIIlllIlllllll, Pointer[] lIIIlllIllllllI, int lIIIlllIlllllIl, int lIIIlllIlllllII) {
            Opaque lIIIllllIIIIIII;
            throw new UnsupportedOperationException(lIIIllllIIIIIII.MSG);
        }

        @Override
        public void read(long lIIIllllIllIlll, Pointer[] lIIIllllIllIllI, int lIIIllllIllIlIl, int lIIIllllIllIlII) {
            Opaque lIIIllllIllIIll;
            throw new UnsupportedOperationException(lIIIllllIllIIll.MSG);
        }

        @Override
        public void read(long lIIIllllllIIIIl, char[] lIIIllllllIIIII, int lIIIlllllIlllll, int lIIIlllllIllllI) {
            Opaque lIIIlllllIlllIl;
            throw new UnsupportedOperationException(lIIIlllllIlllIl.MSG);
        }

        @Override
        public void read(long lIIIllllIlllllI, double[] lIIIllllIllllIl, int lIIIllllIllllII, int lIIIllllIlllIll) {
            Opaque lIIIllllIlllIlI;
            throw new UnsupportedOperationException(lIIIllllIlllIlI.MSG);
        }

        @Override
        public long getLong(long lIIIlllIllIIIll) {
            Opaque lIIIlllIllIIlII;
            throw new UnsupportedOperationException(lIIIlllIllIIlII.MSG);
        }

        @Override
        public float getFloat(long lIIIlllIlIlllll) {
            Opaque lIIIlllIllIIIII;
            throw new UnsupportedOperationException(lIIIlllIllIIIII.MSG);
        }

        @Override
        public void setPointer(long lIIIlllIIlIIlll, Pointer lIIIlllIIlIIllI) {
            Opaque lIIIlllIIlIIlIl;
            throw new UnsupportedOperationException(lIIIlllIIlIIlIl.MSG);
        }

        @Override
        public String toString() {
            Opaque lIIIlllIIIIllIl;
            return String.valueOf(new StringBuilder().append("const@0x").append(Long.toHexString(lIIIlllIIIIllIl.peer)));
        }

        @Override
        public void setShort(long lIIIlllIlIIIIII, short lIIIlllIIllllll) {
            Opaque lIIIlllIIlllllI;
            throw new UnsupportedOperationException(lIIIlllIIlllllI.MSG);
        }

        private Opaque(long lIIIllllllllIll) {
            super(lIIIllllllllIll);
            Opaque lIIIllllllllIlI;
            lIIIllllllllIlI.MSG = String.valueOf(new StringBuilder().append("This pointer is opaque: ").append(lIIIllllllllIlI));
        }

        @Override
        public char getChar(long lIIIlllIllIllll) {
            Opaque lIIIlllIlllIIII;
            throw new UnsupportedOperationException(lIIIlllIlllIIII.MSG);
        }

        @Override
        public void setInt(long lIIIlllIIlllIll, int lIIIlllIIlllIlI) {
            Opaque lIIIlllIIllllII;
            throw new UnsupportedOperationException(lIIIlllIIllllII.MSG);
        }

        @Override
        public void write(long lIIIllllIIIIllI, double[] lIIIllllIIIIlIl, int lIIIllllIIIIlII, int lIIIllllIIIIIll) {
            Opaque lIIIllllIIIIIlI;
            throw new UnsupportedOperationException(lIIIllllIIIIIlI.MSG);
        }

        @Override
        public void read(long lIIIllllllIlIII, byte[] lIIIllllllIIlll, int lIIIllllllIIllI, int lIIIllllllIIlIl) {
            Opaque lIIIllllllIIlII;
            throw new UnsupportedOperationException(lIIIllllllIIlII.MSG);
        }

        @Override
        public void write(long lIIIllllIlIlIIl, char[] lIIIllllIlIlIII, int lIIIllllIlIIlll, int lIIIllllIlIIllI) {
            Opaque lIIIllllIlIIlIl;
            throw new UnsupportedOperationException(lIIIllllIlIIlIl.MSG);
        }

        @Override
        public String getWideString(long lIIIlllIlIIlllI) {
            Opaque lIIIlllIlIIllIl;
            throw new UnsupportedOperationException(lIIIlllIlIIllIl.MSG);
        }

        @Override
        public long indexOf(long lIIIllllllIllIl, byte lIIIllllllIllII) {
            Opaque lIIIllllllIlllI;
            throw new UnsupportedOperationException(lIIIllllllIlllI.MSG);
        }

        @Override
        public void read(long lIIIlllllIIllII, long[] lIIIlllllIIlIll, int lIIIlllllIIlIlI, int lIIIlllllIIlIIl) {
            Opaque lIIIlllllIIlIII;
            throw new UnsupportedOperationException(lIIIlllllIIlIII.MSG);
        }

        @Override
        public void setByte(long lIIIlllIlIIlIlI, byte lIIIlllIlIIlIIl) {
            Opaque lIIIlllIlIIlIll;
            throw new UnsupportedOperationException(lIIIlllIlIIlIll.MSG);
        }

        @Override
        public void setFloat(long lIIIlllIIllIIIl, float lIIIlllIIllIIII) {
            Opaque lIIIlllIIlIllll;
            throw new UnsupportedOperationException(lIIIlllIIlIllll.MSG);
        }

        @Override
        public Pointer getPointer(long lIIIlllIlIlIlll) {
            Opaque lIIIlllIlIllIII;
            throw new UnsupportedOperationException(lIIIlllIlIllIII.MSG);
        }

        @Override
        public void write(long lIIIllllIllIIII, byte[] lIIIllllIlIllll, int lIIIllllIlIlllI, int lIIIllllIlIllIl) {
            Opaque lIIIllllIlIllII;
            throw new UnsupportedOperationException(lIIIllllIlIllII.MSG);
        }

        @Override
        public String dump(long lIIIlllIIIlIIIl, int lIIIlllIIIlIIII) {
            Opaque lIIIlllIIIlIIlI;
            throw new UnsupportedOperationException(lIIIlllIIIlIIlI.MSG);
        }

        @Override
        public ByteBuffer getByteBuffer(long lIIIlllIllllIII, long lIIIlllIlllIlll) {
            Opaque lIIIlllIllllIIl;
            throw new UnsupportedOperationException(lIIIlllIllllIIl.MSG);
        }

        @Override
        public void setDouble(long lIIIlllIIlIllII, double lIIIlllIIlIlIll) {
            Opaque lIIIlllIIlIllIl;
            throw new UnsupportedOperationException(lIIIlllIIlIllIl.MSG);
        }

        @Override
        public void read(long lIIIlllllIlIIll, int[] lIIIlllllIlIIlI, int lIIIlllllIlIIIl, int lIIIlllllIlIIII) {
            Opaque lIIIlllllIIllll;
            throw new UnsupportedOperationException(lIIIlllllIIllll.MSG);
        }

        @Override
        public String getString(long lIIIlllIlIlIIll, String lIIIlllIlIlIIlI) {
            Opaque lIIIlllIlIlIlII;
            throw new UnsupportedOperationException(lIIIlllIlIlIlII.MSG);
        }

        @Override
        public void write(long lIIIllllIlIIIlI, short[] lIIIllllIlIIIIl, int lIIIllllIlIIIII, int lIIIllllIIlllll) {
            Opaque lIIIllllIlIIIll;
            throw new UnsupportedOperationException(lIIIllllIlIIIll.MSG);
        }

        @Override
        public void clear(long lIIIlllllllIIIl) {
            Opaque lIIIlllllllIIII;
            throw new UnsupportedOperationException(lIIIlllllllIIII.MSG);
        }

        @Override
        public double getDouble(long lIIIlllIlIllIll) {
            Opaque lIIIlllIlIlllII;
            throw new UnsupportedOperationException(lIIIlllIlIlllII.MSG);
        }

        @Override
        public void write(long lIIIllllIIllIll, int[] lIIIllllIIllIlI, int lIIIllllIIllIIl, int lIIIllllIIllIII) {
            Opaque lIIIllllIIlIlll;
            throw new UnsupportedOperationException(lIIIllllIIlIlll.MSG);
        }

        @Override
        public byte getByte(long lIIIlllIlllIIll) {
            Opaque lIIIlllIlllIIlI;
            throw new UnsupportedOperationException(lIIIlllIlllIIlI.MSG);
        }

        @Override
        public void setString(long lIIIlllIIlIIIlI, String lIIIlllIIlIIIIl, String lIIIlllIIlIIIII) {
            Opaque lIIIlllIIIlllll;
            throw new UnsupportedOperationException(lIIIlllIIIlllll.MSG);
        }

        @Override
        public void setMemory(long lIIIlllIIIlIlll, long lIIIlllIIIlIllI, byte lIIIlllIIIlIlIl) {
            Opaque lIIIlllIIIlIlII;
            throw new UnsupportedOperationException(lIIIlllIIIlIlII.MSG);
        }
    }
}

