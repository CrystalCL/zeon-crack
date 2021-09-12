/*
 * Decompiled with CFR 0.151.
 */
package com.sun.jna;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

class ELFAnalyser {
    private static final int EF_ARM_ABI_FLOAT_HARD;
    private static final int EF_ARM_ABI_FLOAT_SOFT;
    private boolean armHardFloat = false;
    private boolean ELF = false;
    private static final int EI_CLASS_64BIT;
    private boolean armSoftFloat = false;
    private boolean _64Bit = false;
    private static final byte[] ELF_MAGIC;
    private final String filename;
    private static final int EI_DATA_BIG_ENDIAN;
    private boolean bigEndian = false;
    private boolean arm = false;
    private static final int E_MACHINE_ARM;

    public boolean isELF() {
        return this.ELF;
    }

    public String getFilename() {
        return this.filename;
    }

    public static ELFAnalyser analyse(String string) throws IOException {
        ELFAnalyser eLFAnalyser = new ELFAnalyser(string);
        eLFAnalyser.runDetection();
        return eLFAnalyser;
    }

    public boolean isArmSoftFloat() {
        return this.armSoftFloat;
    }

    public boolean isArmHardFloat() {
        return this.armHardFloat;
    }

    private ELFAnalyser(String string) {
        this.filename = string;
    }

    public boolean isArm() {
        return this.arm;
    }

    private void runDetection() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(this.filename, "r");
        if (randomAccessFile.length() > 4L) {
            byte[] byArray = new byte[4];
            randomAccessFile.seek(0L);
            randomAccessFile.read(byArray);
            if (Arrays.equals(byArray, ELF_MAGIC)) {
                this.ELF = true;
            }
        }
        if (!this.ELF) {
            return;
        }
        randomAccessFile.seek(4L);
        byte by = randomAccessFile.readByte();
        this._64Bit = by == 2;
        randomAccessFile.seek(0L);
        ByteBuffer byteBuffer = ByteBuffer.allocate(this._64Bit ? 64 : 52);
        randomAccessFile.getChannel().read(byteBuffer, 0L);
        this.bigEndian = byteBuffer.get(5) == 2;
        byteBuffer.order(this.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        boolean bl = this.arm = byteBuffer.get(18) == 40;
        if (this.arm) {
            int n = byteBuffer.getInt(this._64Bit ? 48 : 36);
            this.armSoftFloat = (n & 0x200) == 512;
            this.armHardFloat = (n & 0x400) == 1024;
        }
    }

    public boolean is64Bit() {
        return this._64Bit;
    }

    public boolean isBigEndian() {
        return this.bigEndian;
    }

    static {
        EI_CLASS_64BIT = 2;
        EI_DATA_BIG_ENDIAN = 2;
        EF_ARM_ABI_FLOAT_SOFT = 512;
        EF_ARM_ABI_FLOAT_HARD = 1024;
        E_MACHINE_ARM = 40;
        ELF_MAGIC = new byte[]{127, 69, 76, 70};
    }
}

