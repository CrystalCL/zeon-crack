/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

class ELFAnalyser {
    private static final /* synthetic */ int EF_ARM_ABI_FLOAT_SOFT;
    private /* synthetic */ boolean _64Bit;
    private /* synthetic */ boolean armHardFloat;
    private static final /* synthetic */ int EI_CLASS_64BIT;
    private static final /* synthetic */ int EF_ARM_ABI_FLOAT_HARD;
    private final /* synthetic */ String filename;
    private static final /* synthetic */ int E_MACHINE_ARM;
    private /* synthetic */ boolean bigEndian;
    private /* synthetic */ boolean armSoftFloat;
    private /* synthetic */ boolean ELF;
    private static final /* synthetic */ int EI_DATA_BIG_ENDIAN;
    private /* synthetic */ boolean arm;
    private static final /* synthetic */ byte[] ELF_MAGIC;

    private void runDetection() throws IOException {
        ELFAnalyser llllllllllllllllllIIlIIIIlIlllIl;
        RandomAccessFile llllllllllllllllllIIlIIIIllIIIII = new RandomAccessFile(llllllllllllllllllIIlIIIIlIlllIl.filename, "r");
        if (llllllllllllllllllIIlIIIIllIIIII.length() > 4L) {
            byte[] llllllllllllllllllIIlIIIIllIIIll = new byte[4];
            llllllllllllllllllIIlIIIIllIIIII.seek(0L);
            llllllllllllllllllIIlIIIIllIIIII.read(llllllllllllllllllIIlIIIIllIIIll);
            if (Arrays.equals(llllllllllllllllllIIlIIIIllIIIll, ELF_MAGIC)) {
                llllllllllllllllllIIlIIIIlIlllIl.ELF = true;
            }
        }
        if (!llllllllllllllllllIIlIIIIlIlllIl.ELF) {
            return;
        }
        llllllllllllllllllIIlIIIIllIIIII.seek(4L);
        byte llllllllllllllllllIIlIIIIlIlllll = llllllllllllllllllIIlIIIIllIIIII.readByte();
        llllllllllllllllllIIlIIIIlIlllIl._64Bit = llllllllllllllllllIIlIIIIlIlllll == 2;
        llllllllllllllllllIIlIIIIllIIIII.seek(0L);
        ByteBuffer llllllllllllllllllIIlIIIIlIllllI = ByteBuffer.allocate(llllllllllllllllllIIlIIIIlIlllIl._64Bit ? 64 : 52);
        llllllllllllllllllIIlIIIIllIIIII.getChannel().read(llllllllllllllllllIIlIIIIlIllllI, 0L);
        llllllllllllllllllIIlIIIIlIlllIl.bigEndian = llllllllllllllllllIIlIIIIlIllllI.get(5) == 2;
        llllllllllllllllllIIlIIIIlIllllI.order(llllllllllllllllllIIlIIIIlIlllIl.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        boolean bl = llllllllllllllllllIIlIIIIlIlllIl.arm = llllllllllllllllllIIlIIIIlIllllI.get(18) == 40;
        if (llllllllllllllllllIIlIIIIlIlllIl.arm) {
            int llllllllllllllllllIIlIIIIllIIIlI = llllllllllllllllllIIlIIIIlIllllI.getInt(llllllllllllllllllIIlIIIIlIlllIl._64Bit ? 48 : 36);
            llllllllllllllllllIIlIIIIlIlllIl.armSoftFloat = (llllllllllllllllllIIlIIIIllIIIlI & 0x200) == 512;
            llllllllllllllllllIIlIIIIlIlllIl.armHardFloat = (llllllllllllllllllIIlIIIIllIIIlI & 0x400) == 1024;
        }
    }

    public boolean isArm() {
        ELFAnalyser llllllllllllllllllIIlIIIIlllIIII;
        return llllllllllllllllllIIlIIIIlllIIII.arm;
    }

    public boolean isBigEndian() {
        ELFAnalyser llllllllllllllllllIIlIIIIllllIll;
        return llllllllllllllllllIIlIIIIllllIll.bigEndian;
    }

    public boolean isELF() {
        ELFAnalyser llllllllllllllllllIIlIIIlIIIIIlI;
        return llllllllllllllllllIIlIIIlIIIIIlI.ELF;
    }

    public boolean isArmSoftFloat() {
        ELFAnalyser llllllllllllllllllIIlIIIIlllIIlI;
        return llllllllllllllllllIIlIIIIlllIIlI.armSoftFloat;
    }

    public boolean is64Bit() {
        ELFAnalyser llllllllllllllllllIIlIIIIlllllll;
        return llllllllllllllllllIIlIIIIlllllll._64Bit;
    }

    private ELFAnalyser(String llllllllllllllllllIIlIIIIllIlIIl) {
        ELFAnalyser llllllllllllllllllIIlIIIIllIlIlI;
        llllllllllllllllllIIlIIIIllIlIlI.ELF = false;
        llllllllllllllllllIIlIIIIllIlIlI._64Bit = false;
        llllllllllllllllllIIlIIIIllIlIlI.bigEndian = false;
        llllllllllllllllllIIlIIIIllIlIlI.armHardFloat = false;
        llllllllllllllllllIIlIIIIllIlIlI.armSoftFloat = false;
        llllllllllllllllllIIlIIIIllIlIlI.arm = false;
        llllllllllllllllllIIlIIIIllIlIlI.filename = llllllllllllllllllIIlIIIIllIlIIl;
    }

    public String getFilename() {
        ELFAnalyser llllllllllllllllllIIlIIIIllllIII;
        return llllllllllllllllllIIlIIIIllllIII.filename;
    }

    static {
        E_MACHINE_ARM = 40;
        EF_ARM_ABI_FLOAT_HARD = 1024;
        EI_CLASS_64BIT = 2;
        EF_ARM_ABI_FLOAT_SOFT = 512;
        EI_DATA_BIG_ENDIAN = 2;
        ELF_MAGIC = new byte[]{127, 69, 76, 70};
    }

    public static ELFAnalyser analyse(String llllllllllllllllllIIlIIIlIIIIlIl) throws IOException {
        ELFAnalyser llllllllllllllllllIIlIIIlIIIIllI = new ELFAnalyser(llllllllllllllllllIIlIIIlIIIIlIl);
        llllllllllllllllllIIlIIIlIIIIllI.runDetection();
        return llllllllllllllllllIIlIIIlIIIIllI;
    }

    public boolean isArmHardFloat() {
        ELFAnalyser llllllllllllllllllIIlIIIIlllIllI;
        return llllllllllllllllllIIlIIIIlllIllI.armHardFloat;
    }
}

