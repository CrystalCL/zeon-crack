/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WeakMemoryHolder;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;

public class Memory
extends Pointer {
    protected /* synthetic */ long size;
    private static final /* synthetic */ Map<Memory, Reference<Memory>> allocatedMemory;
    private static final /* synthetic */ WeakMemoryHolder buffers;

    @Override
    public void read(long llllllllllllllllIlllllIllIIIlIll, byte[] llllllllllllllllIlllllIllIIIlIlI, int llllllllllllllllIlllllIllIIIlIIl, int llllllllllllllllIlllllIllIIIlIII) {
        Memory llllllllllllllllIlllllIllIIIIlll;
        llllllllllllllllIlllllIllIIIIlll.boundsCheck(llllllllllllllllIlllllIllIIIlIll, (long)llllllllllllllllIlllllIllIIIlIII * 1L);
        super.read(llllllllllllllllIlllllIllIIIlIll, llllllllllllllllIlllllIllIIIlIlI, llllllllllllllllIlllllIllIIIlIIl, llllllllllllllllIlllllIllIIIlIII);
    }

    protected void finalize() {
        Memory llllllllllllllllIlllllIllIlIllII;
        llllllllllllllllIlllllIllIlIllII.dispose();
    }

    public Memory align(int llllllllllllllllIlllllIllIllIIll) {
        if (llllllllllllllllIlllllIllIllIIll <= 0) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append("Byte boundary must be positive: ").append(llllllllllllllllIlllllIllIllIIll)));
        }
        for (int llllllllllllllllIlllllIllIllIlll = 0; llllllllllllllllIlllllIllIllIlll < 32; ++llllllllllllllllIlllllIllIllIlll) {
            Memory llllllllllllllllIlllllIllIllIlII;
            if (llllllllllllllllIlllllIllIllIIll != 1 << llllllllllllllllIlllllIllIllIlll) continue;
            long llllllllllllllllIlllllIllIlllIII = (long)llllllllllllllllIlllllIllIllIIll - 1L ^ 0xFFFFFFFFFFFFFFFFL;
            if ((llllllllllllllllIlllllIllIllIlII.peer & llllllllllllllllIlllllIllIlllIII) != llllllllllllllllIlllllIllIllIlII.peer) {
                long llllllllllllllllIlllllIllIlllIlI = llllllllllllllllIlllllIllIllIlII.peer + (long)llllllllllllllllIlllllIllIllIIll - 1L & llllllllllllllllIlllllIllIlllIII;
                long llllllllllllllllIlllllIllIlllIIl = llllllllllllllllIlllllIllIllIlII.peer + llllllllllllllllIlllllIllIllIlII.size - llllllllllllllllIlllllIllIlllIlI;
                if (llllllllllllllllIlllllIllIlllIIl <= 0L) {
                    throw new IllegalArgumentException("Insufficient memory to align to the requested boundary");
                }
                return (Memory)llllllllllllllllIlllllIllIllIlII.share(llllllllllllllllIlllllIllIlllIlI - llllllllllllllllIlllllIllIllIlII.peer, llllllllllllllllIlllllIllIlllIIl);
            }
            return llllllllllllllllIlllllIllIllIlII;
        }
        throw new IllegalArgumentException("Byte boundary must be a power of two");
    }

    public void clear() {
        Memory llllllllllllllllIlllllIllIlIIlIl;
        llllllllllllllllIlllllIllIlIIlIl.clear(llllllllllllllllIlllllIllIlIIlIl.size);
    }

    @Override
    public void read(long llllllllllllllllIlllllIlIIllIIIl, double[] llllllllllllllllIlllllIlIIllIIII, int llllllllllllllllIlllllIlIIlIlIlI, int llllllllllllllllIlllllIlIIlIlllI) {
        Memory llllllllllllllllIlllllIlIIlIllIl;
        llllllllllllllllIlllllIlIIlIllIl.boundsCheck(llllllllllllllllIlllllIlIIllIIIl, (long)llllllllllllllllIlllllIlIIlIlllI * 8L);
        super.read(llllllllllllllllIlllllIlIIllIIIl, llllllllllllllllIlllllIlIIllIIII, llllllllllllllllIlllllIlIIlIlIlI, llllllllllllllllIlllllIlIIlIlllI);
    }

    static {
        allocatedMemory = Collections.synchronizedMap(new WeakHashMap());
        buffers = new WeakMemoryHolder();
    }

    protected void boundsCheck(long llllllllllllllllIlllllIllIIlIlll, long llllllllllllllllIlllllIllIIlIllI) {
        Memory llllllllllllllllIlllllIllIIlIlIl;
        if (llllllllllllllllIlllllIllIIlIlll < 0L) {
            throw new IndexOutOfBoundsException(String.valueOf(new StringBuilder().append("Invalid offset: ").append(llllllllllllllllIlllllIllIIlIlll)));
        }
        if (llllllllllllllllIlllllIllIIlIlll + llllllllllllllllIlllllIllIIlIllI > llllllllllllllllIlllllIllIIlIlIl.size) {
            String llllllllllllllllIlllllIllIIllIIl = String.valueOf(new StringBuilder().append("Bounds exceeds available space : size=").append(llllllllllllllllIlllllIllIIlIlIl.size).append(", offset=").append(llllllllllllllllIlllllIllIIlIlll + llllllllllllllllIlllllIllIIlIllI));
            throw new IndexOutOfBoundsException(llllllllllllllllIlllllIllIIllIIl);
        }
    }

    @Override
    public void setDouble(long llllllllllllllllIlllllIIIIllIlll, double llllllllllllllllIlllllIIIIllIllI) {
        Memory llllllllllllllllIlllllIIIIlllIll;
        llllllllllllllllIlllllIIIIlllIll.boundsCheck(llllllllllllllllIlllllIIIIllIlll, 8L);
        super.setDouble(llllllllllllllllIlllllIIIIllIlll, llllllllllllllllIlllllIIIIllIllI);
    }

    @Override
    public String getString(long llllllllllllllllIlllllIIIlllllII, String llllllllllllllllIlllllIIIllllIll) {
        Memory llllllllllllllllIlllllIIlIIIIIII;
        llllllllllllllllIlllllIIlIIIIIII.boundsCheck(llllllllllllllllIlllllIIIlllllII, 0L);
        return super.getString(llllllllllllllllIlllllIIIlllllII, llllllllllllllllIlllllIIIllllIll);
    }

    public Memory(long llllllllllllllllIlllllIlllIlIlIl) {
        Memory llllllllllllllllIlllllIlllIlIlII;
        llllllllllllllllIlllllIlllIlIlII.size = llllllllllllllllIlllllIlllIlIlIl;
        if (llllllllllllllllIlllllIlllIlIlIl <= 0L) {
            throw new IllegalArgumentException("Allocation size must be greater than zero");
        }
        llllllllllllllllIlllllIlllIlIlII.peer = Memory.malloc(llllllllllllllllIlllllIlllIlIlIl);
        if (llllllllllllllllIlllllIlllIlIlII.peer == 0L) {
            throw new OutOfMemoryError(String.valueOf(new StringBuilder().append("Cannot allocate ").append(llllllllllllllllIlllllIlllIlIlIl).append(" bytes")));
        }
        allocatedMemory.put(llllllllllllllllIlllllIlllIlIlII, new WeakReference<Memory>(llllllllllllllllIlllllIlllIlIlII));
    }

    @Override
    public ByteBuffer getByteBuffer(long llllllllllllllllIlllllIIlIIIIllI, long llllllllllllllllIlllllIIlIIIlIIl) {
        Memory llllllllllllllllIlllllIIlIIIlIll;
        llllllllllllllllIlllllIIlIIIlIll.boundsCheck(llllllllllllllllIlllllIIlIIIIllI, llllllllllllllllIlllllIIlIIIlIIl);
        ByteBuffer llllllllllllllllIlllllIIlIIIlIII = super.getByteBuffer(llllllllllllllllIlllllIIlIIIIllI, llllllllllllllllIlllllIIlIIIlIIl);
        buffers.put(llllllllllllllllIlllllIIlIIIlIII, llllllllllllllllIlllllIIlIIIlIll);
        return llllllllllllllllIlllllIIlIIIlIII;
    }

    @Override
    public String toString() {
        Memory llllllllllllllllIlllllIIIIIlIllI;
        return String.valueOf(new StringBuilder().append("allocated@0x").append(Long.toHexString(llllllllllllllllIlllllIIIIIlIllI.peer)).append(" (").append(llllllllllllllllIlllllIIIIIlIllI.size).append(" bytes)"));
    }

    @Override
    public void write(long llllllllllllllllIlllllIlIIIIIlII, char[] llllllllllllllllIlllllIlIIIIIIll, int llllllllllllllllIlllllIlIIIIIIlI, int llllllllllllllllIlllllIlIIIIIIIl) {
        Memory llllllllllllllllIlllllIlIIIIIIII;
        llllllllllllllllIlllllIlIIIIIIII.boundsCheck(llllllllllllllllIlllllIlIIIIIlII, (long)llllllllllllllllIlllllIlIIIIIIIl * 2L);
        super.write(llllllllllllllllIlllllIlIIIIIlII, llllllllllllllllIlllllIlIIIIIIll, llllllllllllllllIlllllIlIIIIIIlI, llllllllllllllllIlllllIlIIIIIIIl);
    }

    @Override
    public short getShort(long llllllllllllllllIlllllIIlIlIlllI) {
        Memory llllllllllllllllIlllllIIlIllIIIl;
        llllllllllllllllIlllllIIlIllIIIl.boundsCheck(llllllllllllllllIlllllIIlIlIlllI, 2L);
        return super.getShort(llllllllllllllllIlllllIIlIlIlllI);
    }

    @Override
    public void setShort(long llllllllllllllllIlllllIIIlIllIll, short llllllllllllllllIlllllIIIlIllIlI) {
        Memory llllllllllllllllIlllllIIIlIlllII;
        llllllllllllllllIlllllIIIlIlllII.boundsCheck(llllllllllllllllIlllllIIIlIllIll, 2L);
        super.setShort(llllllllllllllllIlllllIIIlIllIll, llllllllllllllllIlllllIIIlIllIlI);
    }

    protected static void free(long llllllllllllllllIlllllIIIIIlIIll) {
        if (llllllllllllllllIlllllIIIIIlIIll != 0L) {
            Native.free(llllllllllllllllIlllllIIIIIlIIll);
        }
    }

    public static void purge() {
        buffers.clean();
    }

    @Override
    public void setPointer(long llllllllllllllllIlllllIIIIllIIIl, Pointer llllllllllllllllIlllllIIIIllIIII) {
        Memory llllllllllllllllIlllllIIIIlIllll;
        llllllllllllllllIlllllIIIIlIllll.boundsCheck(llllllllllllllllIlllllIIIIllIIIl, Pointer.SIZE);
        super.setPointer(llllllllllllllllIlllllIIIIllIIIl, llllllllllllllllIlllllIIIIllIIII);
    }

    @Override
    public void write(long llllllllllllllllIlllllIIllIIIIll, double[] llllllllllllllllIlllllIIllIIIIlI, int llllllllllllllllIlllllIIllIIIllI, int llllllllllllllllIlllllIIllIIIIII) {
        Memory llllllllllllllllIlllllIIllIIlIIl;
        llllllllllllllllIlllllIIllIIlIIl.boundsCheck(llllllllllllllllIlllllIIllIIIIll, (long)llllllllllllllllIlllllIIllIIIIII * 8L);
        super.write(llllllllllllllllIlllllIIllIIIIll, llllllllllllllllIlllllIIllIIIIlI, llllllllllllllllIlllllIIllIIIllI, llllllllllllllllIlllllIIllIIIIII);
    }

    @Override
    public void read(long llllllllllllllllIlllllIlIlIllIIl, int[] llllllllllllllllIlllllIlIlIlllIl, int llllllllllllllllIlllllIlIlIlllII, int llllllllllllllllIlllllIlIlIlIllI) {
        Memory llllllllllllllllIlllllIlIlIlllll;
        llllllllllllllllIlllllIlIlIlllll.boundsCheck(llllllllllllllllIlllllIlIlIllIIl, (long)llllllllllllllllIlllllIlIlIlIllI * 4L);
        super.read(llllllllllllllllIlllllIlIlIllIIl, llllllllllllllllIlllllIlIlIlllIl, llllllllllllllllIlllllIlIlIlllII, llllllllllllllllIlllllIlIlIlIllI);
    }

    public boolean valid() {
        Memory llllllllllllllllIlllllIllIlIIIlI;
        return llllllllllllllllIlllllIllIlIIIlI.peer != 0L;
    }

    public String dump() {
        Memory llllllllllllllllIlllllIIIIIIllII;
        return llllllllllllllllIlllllIIIIIIllII.dump(0L, (int)llllllllllllllllIlllllIIIIIIllII.size());
    }

    protected static long malloc(long llllllllllllllllIlllllIIIIIIllll) {
        return Native.malloc(llllllllllllllllIlllllIIIIIIllll);
    }

    @Override
    public long getLong(long llllllllllllllllIlllllIIlIlIIlII) {
        Memory llllllllllllllllIlllllIIlIlIIlIl;
        llllllllllllllllIlllllIIlIlIIlIl.boundsCheck(llllllllllllllllIlllllIIlIlIIlII, 8L);
        return super.getLong(llllllllllllllllIlllllIIlIlIIlII);
    }

    @Override
    public void read(long llllllllllllllllIlllllIlIlIIllll, long[] llllllllllllllllIlllllIlIlIIlIIl, int llllllllllllllllIlllllIlIlIIlIII, int llllllllllllllllIlllllIlIlIIIlll) {
        Memory llllllllllllllllIlllllIlIlIIlIll;
        llllllllllllllllIlllllIlIlIIlIll.boundsCheck(llllllllllllllllIlllllIlIlIIllll, (long)llllllllllllllllIlllllIlIlIIIlll * 8L);
        super.read(llllllllllllllllIlllllIlIlIIllll, llllllllllllllllIlllllIlIlIIlIIl, llllllllllllllllIlllllIlIlIIlIII, llllllllllllllllIlllllIlIlIIIlll);
    }

    @Override
    public void read(long llllllllllllllllIlllllIlIlllllII, short[] llllllllllllllllIlllllIlIlllIllI, int llllllllllllllllIlllllIlIllllIlI, int llllllllllllllllIlllllIlIllllIIl) {
        Memory llllllllllllllllIlllllIlIlllllIl;
        llllllllllllllllIlllllIlIlllllIl.boundsCheck(llllllllllllllllIlllllIlIlllllII, (long)llllllllllllllllIlllllIlIllllIIl * 2L);
        super.read(llllllllllllllllIlllllIlIlllllII, llllllllllllllllIlllllIlIlllIllI, llllllllllllllllIlllllIlIllllIlI, llllllllllllllllIlllllIlIllllIIl);
    }

    @Override
    public char getChar(long llllllllllllllllIlllllIIlIllIlII) {
        Memory llllllllllllllllIlllllIIlIllIlIl;
        llllllllllllllllIlllllIIlIllIlIl.boundsCheck(llllllllllllllllIlllllIIlIllIlII, 1L);
        return super.getChar(llllllllllllllllIlllllIIlIllIlII);
    }

    @Override
    public void write(long llllllllllllllllIlllllIIllIlIlll, float[] llllllllllllllllIlllllIIllIlIllI, int llllllllllllllllIlllllIIllIlIlIl, int llllllllllllllllIlllllIIllIlIlII) {
        Memory llllllllllllllllIlllllIIllIlIIll;
        llllllllllllllllIlllllIIllIlIIll.boundsCheck(llllllllllllllllIlllllIIllIlIlll, (long)llllllllllllllllIlllllIIllIlIlII * 4L);
        super.write(llllllllllllllllIlllllIIllIlIlll, llllllllllllllllIlllllIIllIlIllI, llllllllllllllllIlllllIIllIlIlIl, llllllllllllllllIlllllIIllIlIlII);
    }

    @Override
    public float getFloat(long llllllllllllllllIlllllIIlIIlllII) {
        Memory llllllllllllllllIlllllIIlIIlllIl;
        llllllllllllllllIlllllIIlIIlllIl.boundsCheck(llllllllllllllllIlllllIIlIIlllII, 4L);
        return super.getFloat(llllllllllllllllIlllllIIlIIlllII);
    }

    @Override
    public byte getByte(long llllllllllllllllIlllllIIlIllllII) {
        Memory llllllllllllllllIlllllIIlIlllIll;
        llllllllllllllllIlllllIIlIlllIll.boundsCheck(llllllllllllllllIlllllIIlIllllII, 1L);
        return super.getByte(llllllllllllllllIlllllIIlIllllII);
    }

    @Override
    public Pointer share(long llllllllllllllllIlllllIlllIIllII) {
        Memory llllllllllllllllIlllllIlllIIlIll;
        return llllllllllllllllIlllllIlllIIlIll.share(llllllllllllllllIlllllIlllIIllII, llllllllllllllllIlllllIlllIIlIll.size() - llllllllllllllllIlllllIlllIIllII);
    }

    @Override
    public void setChar(long llllllllllllllllIlllllIIIllIIlII, char llllllllllllllllIlllllIIIllIIllI) {
        Memory llllllllllllllllIlllllIIIllIIlIl;
        llllllllllllllllIlllllIIIllIIlIl.boundsCheck(llllllllllllllllIlllllIIIllIIlII, Native.WCHAR_SIZE);
        super.setChar(llllllllllllllllIlllllIIIllIIlII, llllllllllllllllIlllllIIIllIIllI);
    }

    public static void disposeAll() {
        LinkedList<Memory> llllllllllllllllIlllllIlllIlllII = new LinkedList<Memory>(allocatedMemory.keySet());
        for (Memory llllllllllllllllIlllllIlllIlllIl : llllllllllllllllIlllllIlllIlllII) {
            llllllllllllllllIlllllIlllIlllIl.dispose();
        }
    }

    @Override
    public Pointer getPointer(long llllllllllllllllIlllllIIlIIlIIlI) {
        Memory llllllllllllllllIlllllIIlIIlIIIl;
        llllllllllllllllIlllllIIlIIlIIIl.boundsCheck(llllllllllllllllIlllllIIlIIlIIlI, Pointer.SIZE);
        return super.getPointer(llllllllllllllllIlllllIIlIIlIIlI);
    }

    @Override
    public void read(long llllllllllllllllIlllllIlIllIllIl, char[] llllllllllllllllIlllllIlIllIIlll, int llllllllllllllllIlllllIlIllIlIll, int llllllllllllllllIlllllIlIllIlIlI) {
        Memory llllllllllllllllIlllllIlIllIlIIl;
        llllllllllllllllIlllllIlIllIlIIl.boundsCheck(llllllllllllllllIlllllIlIllIllIl, (long)llllllllllllllllIlllllIlIllIlIlI * 2L);
        super.read(llllllllllllllllIlllllIlIllIllIl, llllllllllllllllIlllllIlIllIIlll, llllllllllllllllIlllllIlIllIlIll, llllllllllllllllIlllllIlIllIlIlI);
    }

    @Override
    public void write(long llllllllllllllllIlllllIlIIIIlllI, short[] llllllllllllllllIlllllIlIIIlIIlI, int llllllllllllllllIlllllIlIIIIllII, int llllllllllllllllIlllllIlIIIIlIll) {
        Memory llllllllllllllllIlllllIlIIIlIlII;
        llllllllllllllllIlllllIlIIIlIlII.boundsCheck(llllllllllllllllIlllllIlIIIIlllI, (long)llllllllllllllllIlllllIlIIIIlIll * 2L);
        super.write(llllllllllllllllIlllllIlIIIIlllI, llllllllllllllllIlllllIlIIIlIIlI, llllllllllllllllIlllllIlIIIIllII, llllllllllllllllIlllllIlIIIIlIll);
    }

    @Override
    public void setWideString(long llllllllllllllllIlllllIIIIIllIIl, String llllllllllllllllIlllllIIIIIllIII) {
        Memory llllllllllllllllIlllllIIIIIlllIl;
        llllllllllllllllIlllllIIIIIlllIl.boundsCheck(llllllllllllllllIlllllIIIIIllIIl, ((long)llllllllllllllllIlllllIIIIIllIII.length() + 1L) * (long)Native.WCHAR_SIZE);
        super.setWideString(llllllllllllllllIlllllIIIIIllIIl, llllllllllllllllIlllllIIIIIllIII);
    }

    @Override
    public void setByte(long llllllllllllllllIlllllIIIlllIIII, byte llllllllllllllllIlllllIIIllIllll) {
        Memory llllllllllllllllIlllllIIIllIlllI;
        llllllllllllllllIlllllIIIllIlllI.boundsCheck(llllllllllllllllIlllllIIIlllIIII, 1L);
        super.setByte(llllllllllllllllIlllllIIIlllIIII, llllllllllllllllIlllllIIIllIllll);
    }

    @Override
    public double getDouble(long llllllllllllllllIlllllIIlIIlIllI) {
        Memory llllllllllllllllIlllllIIlIIlIlll;
        llllllllllllllllIlllllIIlIIlIlll.boundsCheck(llllllllllllllllIlllllIIlIIlIllI, 8L);
        return super.getDouble(llllllllllllllllIlllllIIlIIlIllI);
    }

    protected synchronized void dispose() {
        Memory llllllllllllllllIlllllIllIlIlIII;
        try {
            Memory.free(llllllllllllllllIlllllIllIlIlIII.peer);
        }
        finally {
            allocatedMemory.remove(llllllllllllllllIlllllIllIlIlIII);
            llllllllllllllllIlllllIllIlIlIII.peer = 0L;
        }
    }

    @Override
    public void setString(long llllllllllllllllIlllllIIIIlIIlll, String llllllllllllllllIlllllIIIIlIIllI, String llllllllllllllllIlllllIIIIlIIlIl) {
        Memory llllllllllllllllIlllllIIIIlIIlII;
        llllllllllllllllIlllllIIIIlIIlII.boundsCheck(llllllllllllllllIlllllIIIIlIIlll, (long)Native.getBytes(llllllllllllllllIlllllIIIIlIIllI, llllllllllllllllIlllllIIIIlIIlIl).length + 1L);
        super.setString(llllllllllllllllIlllllIIIIlIIlll, llllllllllllllllIlllllIIIIlIIllI, llllllllllllllllIlllllIIIIlIIlIl);
    }

    @Override
    public void write(long llllllllllllllllIlllllIlIIIlllIl, byte[] llllllllllllllllIlllllIlIIIlllII, int llllllllllllllllIlllllIlIIIllIll, int llllllllllllllllIlllllIlIIIllIlI) {
        Memory llllllllllllllllIlllllIlIIIllllI;
        llllllllllllllllIlllllIlIIIllllI.boundsCheck(llllllllllllllllIlllllIlIIIlllIl, (long)llllllllllllllllIlllllIlIIIllIlI * 1L);
        super.write(llllllllllllllllIlllllIlIIIlllIl, llllllllllllllllIlllllIlIIIlllII, llllllllllllllllIlllllIlIIIllIll, llllllllllllllllIlllllIlIIIllIlI);
    }

    @Override
    public void setFloat(long llllllllllllllllIlllllIIIlIIIIll, float llllllllllllllllIlllllIIIlIIIIlI) {
        Memory llllllllllllllllIlllllIIIlIIIIIl;
        llllllllllllllllIlllllIIIlIIIIIl.boundsCheck(llllllllllllllllIlllllIIIlIIIIll, 4L);
        super.setFloat(llllllllllllllllIlllllIIIlIIIIll, llllllllllllllllIlllllIIIlIIIIlI);
    }

    @Override
    public void read(long llllllllllllllllIlllllIlIlIIIIII, float[] llllllllllllllllIlllllIlIIllllll, int llllllllllllllllIlllllIlIIlllIIl, int llllllllllllllllIlllllIlIIllllIl) {
        Memory llllllllllllllllIlllllIlIIllllII;
        llllllllllllllllIlllllIlIIllllII.boundsCheck(llllllllllllllllIlllllIlIlIIIIII, (long)llllllllllllllllIlllllIlIIllllIl * 4L);
        super.read(llllllllllllllllIlllllIlIlIIIIII, llllllllllllllllIlllllIlIIllllll, llllllllllllllllIlllllIlIIlllIIl, llllllllllllllllIlllllIlIIllllIl);
    }

    @Override
    public void setLong(long llllllllllllllllIlllllIIIlIIlIIl, long llllllllllllllllIlllllIIIlIIlIII) {
        Memory llllllllllllllllIlllllIIIlIIllIl;
        llllllllllllllllIlllllIIIlIIllIl.boundsCheck(llllllllllllllllIlllllIIIlIIlIIl, 8L);
        super.setLong(llllllllllllllllIlllllIIIlIIlIIl, llllllllllllllllIlllllIIIlIIlIII);
    }

    protected Memory() {
        Memory llllllllllllllllIlllllIlllIlIIIl;
    }

    @Override
    public void write(long llllllllllllllllIlllllIIllllIlIl, int[] llllllllllllllllIlllllIIllllIlII, int llllllllllllllllIlllllIIlllIlllI, int llllllllllllllllIlllllIIllllIIlI) {
        Memory llllllllllllllllIlllllIIllllIllI;
        llllllllllllllllIlllllIIllllIllI.boundsCheck(llllllllllllllllIlllllIIllllIlIl, (long)llllllllllllllllIlllllIIllllIIlI * 4L);
        super.write(llllllllllllllllIlllllIIllllIlIl, llllllllllllllllIlllllIIllllIlII, llllllllllllllllIlllllIIlllIlllI, llllllllllllllllIlllllIIllllIIlI);
    }

    @Override
    public void write(long llllllllllllllllIlllllIIlllIIIIl, long[] llllllllllllllllIlllllIIlllIIIII, int llllllllllllllllIlllllIIlllIIlII, int llllllllllllllllIlllllIIlllIIIll) {
        Memory llllllllllllllllIlllllIIlllIIIlI;
        llllllllllllllllIlllllIIlllIIIlI.boundsCheck(llllllllllllllllIlllllIIlllIIIIl, (long)llllllllllllllllIlllllIIlllIIIll * 8L);
        super.write(llllllllllllllllIlllllIIlllIIIIl, llllllllllllllllIlllllIIlllIIIII, llllllllllllllllIlllllIIlllIIlII, llllllllllllllllIlllllIIlllIIIll);
    }

    @Override
    public Pointer share(long llllllllllllllllIlllllIlllIIIlIl, long llllllllllllllllIlllllIlllIIIIIl) {
        Memory llllllllllllllllIlllllIlllIIIllI;
        llllllllllllllllIlllllIlllIIIllI.boundsCheck(llllllllllllllllIlllllIlllIIIlIl, llllllllllllllllIlllllIlllIIIIIl);
        return llllllllllllllllIlllllIlllIIIllI.new SharedMemory(llllllllllllllllIlllllIlllIIIlIl, llllllllllllllllIlllllIlllIIIIIl);
    }

    @Override
    public String getWideString(long llllllllllllllllIlllllIIIlllIlIl) {
        Memory llllllllllllllllIlllllIIIllllIII;
        llllllllllllllllIlllllIIIllllIII.boundsCheck(llllllllllllllllIlllllIIIlllIlIl, 0L);
        return super.getWideString(llllllllllllllllIlllllIIIlllIlIl);
    }

    @Override
    public int getInt(long llllllllllllllllIlllllIIlIlIlIII) {
        Memory llllllllllllllllIlllllIIlIlIlIll;
        llllllllllllllllIlllllIIlIlIlIll.boundsCheck(llllllllllllllllIlllllIIlIlIlIII, 4L);
        return super.getInt(llllllllllllllllIlllllIIlIlIlIII);
    }

    public long size() {
        Memory llllllllllllllllIlllllIllIIllllI;
        return llllllllllllllllIlllllIllIIllllI.size;
    }

    @Override
    public void setInt(long llllllllllllllllIlllllIIIlIlIlIl, int llllllllllllllllIlllllIIIlIlIIIl) {
        Memory llllllllllllllllIlllllIIIlIlIIll;
        llllllllllllllllIlllllIIIlIlIIll.boundsCheck(llllllllllllllllIlllllIIIlIlIlIl, 4L);
        super.setInt(llllllllllllllllIlllllIIIlIlIlIl, llllllllllllllllIlllllIIIlIlIIIl);
    }

    private class SharedMemory
    extends Memory {
        @Override
        protected void dispose() {
            lllllllIIlIIlIl.peer = 0L;
        }

        @Override
        public String toString() {
            SharedMemory lllllllIIIllIII;
            return String.valueOf(new StringBuilder().append(super.toString()).append(" (shared from ").append(lllllllIIIllIII.Memory.this.toString()).append(")"));
        }

        @Override
        protected void boundsCheck(long lllllllIIIlllII, long lllllllIIIllIll) {
            SharedMemory lllllllIIIlllIl;
            lllllllIIIlllIl.Memory.this.boundsCheck(lllllllIIIlllIl.peer - lllllllIIIlllIl.Memory.this.peer + lllllllIIIlllII, lllllllIIIllIll);
        }

        public SharedMemory(long lllllllIIlIllII, long lllllllIIlIIlll) {
            SharedMemory lllllllIIlIllIl;
            lllllllIIlIllIl.size = lllllllIIlIIlll;
            lllllllIIlIllIl.peer = lllllllIIlIllIl.Memory.this.peer + lllllllIIlIllII;
        }
    }
}

