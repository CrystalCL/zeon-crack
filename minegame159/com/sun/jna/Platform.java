/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.ELFAnalyser;
import com.sun.jna.Native;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Platform {
    public static final /* synthetic */ int WINDOWS;
    public static final /* synthetic */ String MATH_LIBRARY_NAME;
    public static final /* synthetic */ int ANDROID;
    public static final /* synthetic */ int WINDOWSCE;
    public static final /* synthetic */ String C_LIBRARY_NAME;
    public static final /* synthetic */ boolean HAS_DLL_CALLBACKS;
    public static final /* synthetic */ int MAC;
    public static final /* synthetic */ boolean HAS_JAWT;
    public static final /* synthetic */ boolean HAS_BUFFERS;
    public static final /* synthetic */ int LINUX;
    public static final /* synthetic */ int GNU;
    private static final /* synthetic */ int osType;
    public static final /* synthetic */ int KFREEBSD;
    public static final /* synthetic */ String ARCH;
    public static final /* synthetic */ String RESOURCE_PREFIX;
    public static final /* synthetic */ boolean RO_FIELDS;
    public static final /* synthetic */ int NETBSD;
    public static final /* synthetic */ int AIX;
    public static final /* synthetic */ int SOLARIS;
    public static final /* synthetic */ int OPENBSD;
    public static final /* synthetic */ boolean HAS_AWT;
    public static final /* synthetic */ int UNSPECIFIED;
    public static final /* synthetic */ int FREEBSD;

    public static final boolean isAndroid() {
        return osType == 8;
    }

    public static final boolean isARM() {
        return ARCH.startsWith("arm");
    }

    public static final boolean isX11() {
        return !Platform.isWindows() && !Platform.isMac();
    }

    private static boolean isSoftFloat() {
        try {
            File lllllllllllllllllIIIlIlllllllIll = new File("/proc/self/exe");
            ELFAnalyser lllllllllllllllllIIIlIlllllllIlI = ELFAnalyser.analyse(lllllllllllllllllIIIlIlllllllIll.getCanonicalPath());
            return lllllllllllllllllIIIlIlllllllIlI.isArmSoftFloat();
        }
        catch (IOException lllllllllllllllllIIIlIlllllllIIl) {
            Logger.getLogger(Platform.class.getName()).log(Level.FINE, null, lllllllllllllllllIIIlIlllllllIIl);
            return false;
        }
    }

    public static final boolean isSPARC() {
        return ARCH.startsWith("sparc");
    }

    public static final boolean isNetBSD() {
        return osType == 11;
    }

    public static final boolean isAix() {
        return Platform.isAIX();
    }

    public static final boolean isOpenBSD() {
        return osType == 5;
    }

    public static final boolean isFreeBSD() {
        return osType == 4;
    }

    public static final boolean isGNU() {
        return osType == 9;
    }

    static String getNativeLibraryResourcePrefix(int lllllllllllllllllIIIlIlllllIllIl, String lllllllllllllllllIIIlIlllllIllll, String lllllllllllllllllIIIlIlllllIlllI) {
        return Platform.getNativeLibraryResourcePrefix(lllllllllllllllllIIIlIlllllIllIl, lllllllllllllllllIIIlIlllllIllll, lllllllllllllllllIIIlIlllllIlllI, Platform.isSoftFloat());
    }

    public static final boolean isWindows() {
        return osType == 2 || osType == 6;
    }

    static String getNativeLibraryResourcePrefix() {
        String lllllllllllllllllIIIlIllllllIlIl = System.getProperty("jna.prefix");
        if (lllllllllllllllllIIIlIllllllIlIl != null) {
            return lllllllllllllllllIIIlIllllllIlIl;
        }
        return Platform.getNativeLibraryResourcePrefix(Platform.getOSType(), System.getProperty("os.arch"), System.getProperty("os.name"));
    }

    public static final boolean isAIX() {
        return osType == 7;
    }

    static String getNativeLibraryResourcePrefix(int lllllllllllllllllIIIlIllllIlIlII, String lllllllllllllllllIIIlIllllIllIII, String lllllllllllllllllIIIlIllllIlIIlI, boolean lllllllllllllllllIIIlIllllIlIllI) {
        String lllllllllllllllllIIIlIllllIlIlIl;
        lllllllllllllllllIIIlIllllIllIII = Platform.getCanonicalArchitecture(lllllllllllllllllIIIlIllllIllIII, lllllllllllllllllIIIlIllllIlIllI);
        switch (lllllllllllllllllIIIlIllllIlIlII) {
            case 8: {
                if (lllllllllllllllllIIIlIllllIllIII.startsWith("arm")) {
                    lllllllllllllllllIIIlIllllIllIII = "arm";
                }
                String lllllllllllllllllIIIlIlllllIIlII = String.valueOf(new StringBuilder().append("android-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            case 2: {
                String lllllllllllllllllIIIlIlllllIIIll = String.valueOf(new StringBuilder().append("win32-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            case 6: {
                String lllllllllllllllllIIIlIlllllIIIlI = String.valueOf(new StringBuilder().append("w32ce-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            case 0: {
                String lllllllllllllllllIIIlIlllllIIIIl = "darwin";
                break;
            }
            case 1: {
                String lllllllllllllllllIIIlIlllllIIIII = String.valueOf(new StringBuilder().append("linux-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            case 3: {
                String lllllllllllllllllIIIlIllllIlllll = String.valueOf(new StringBuilder().append("sunos-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            case 4: {
                String lllllllllllllllllIIIlIllllIllllI = String.valueOf(new StringBuilder().append("freebsd-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            case 5: {
                String lllllllllllllllllIIIlIllllIlllIl = String.valueOf(new StringBuilder().append("openbsd-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            case 11: {
                String lllllllllllllllllIIIlIllllIlllII = String.valueOf(new StringBuilder().append("netbsd-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            case 10: {
                String lllllllllllllllllIIIlIllllIllIll = String.valueOf(new StringBuilder().append("kfreebsd-").append(lllllllllllllllllIIIlIllllIllIII));
                break;
            }
            default: {
                lllllllllllllllllIIIlIllllIlIlIl = lllllllllllllllllIIIlIllllIlIIlI.toLowerCase();
                int lllllllllllllllllIIIlIllllIllIlI = lllllllllllllllllIIIlIllllIlIlIl.indexOf(" ");
                if (lllllllllllllllllIIIlIllllIllIlI != -1) {
                    lllllllllllllllllIIIlIllllIlIlIl = lllllllllllllllllIIIlIllllIlIlIl.substring(0, lllllllllllllllllIIIlIllllIllIlI);
                }
                lllllllllllllllllIIIlIllllIlIlIl = String.valueOf(new StringBuilder().append(lllllllllllllllllIIIlIllllIlIlIl).append("-").append(lllllllllllllllllIIIlIllllIllIII));
            }
        }
        return lllllllllllllllllIIIlIllllIlIlIl;
    }

    public static final boolean isIntel() {
        return ARCH.startsWith("x86");
    }

    static String getCanonicalArchitecture(String lllllllllllllllllIIIllIIIIIIIIIl, boolean lllllllllllllllllIIIllIIIIIIIIII) {
        if ("powerpc".equals(lllllllllllllllllIIIllIIIIIIIIIl = lllllllllllllllllIIIllIIIIIIIIIl.toLowerCase().trim())) {
            lllllllllllllllllIIIllIIIIIIIIIl = "ppc";
        } else if ("powerpc64".equals(lllllllllllllllllIIIllIIIIIIIIIl)) {
            lllllllllllllllllIIIllIIIIIIIIIl = "ppc64";
        } else if ("i386".equals(lllllllllllllllllIIIllIIIIIIIIIl) || "i686".equals(lllllllllllllllllIIIllIIIIIIIIIl)) {
            lllllllllllllllllIIIllIIIIIIIIIl = "x86";
        } else if ("x86_64".equals(lllllllllllllllllIIIllIIIIIIIIIl) || "amd64".equals(lllllllllllllllllIIIllIIIIIIIIIl)) {
            lllllllllllllllllIIIllIIIIIIIIIl = "x86-64";
        }
        if ("ppc64".equals(lllllllllllllllllIIIllIIIIIIIIIl) && "little".equals(System.getProperty("sun.cpu.endian"))) {
            lllllllllllllllllIIIllIIIIIIIIIl = "ppc64le";
        }
        if ("arm".equals(lllllllllllllllllIIIllIIIIIIIIIl) && lllllllllllllllllIIIllIIIIIIIIII) {
            lllllllllllllllllIIIllIIIIIIIIIl = "armel";
        }
        return lllllllllllllllllIIIllIIIIIIIIIl;
    }

    public static final boolean iskFreeBSD() {
        return osType == 10;
    }

    public static final boolean is64Bit() {
        String lllllllllllllllllIIIllIIIIIIIlIl = System.getProperty("sun.arch.data.model", System.getProperty("com.ibm.vm.bitmode"));
        if (lllllllllllllllllIIIllIIIIIIIlIl != null) {
            return "64".equals(lllllllllllllllllIIIllIIIIIIIlIl);
        }
        if ("x86-64".equals(ARCH) || "ia64".equals(ARCH) || "ppc64".equals(ARCH) || "ppc64le".equals(ARCH) || "sparcv9".equals(ARCH) || "amd64".equals(ARCH)) {
            return true;
        }
        return Native.POINTER_SIZE == 8;
    }

    public static final int getOSType() {
        return osType;
    }

    public static final boolean isLinux() {
        return osType == 1;
    }

    private Platform() {
        Platform lllllllllllllllllIIIllIIIIIIIlll;
    }

    public static final boolean isWindowsCE() {
        return osType == 6;
    }

    static {
        GNU = 9;
        ANDROID = 8;
        SOLARIS = 3;
        MAC = 0;
        KFREEBSD = 10;
        WINDOWS = 2;
        WINDOWSCE = 6;
        FREEBSD = 4;
        NETBSD = 11;
        OPENBSD = 5;
        LINUX = 1;
        AIX = 7;
        UNSPECIFIED = -1;
        String lllllllllllllllllIIIlIllllIIlIll = System.getProperty("os.name");
        if (lllllllllllllllllIIIlIllllIIlIll.startsWith("Linux")) {
            if ("dalvik".equals(System.getProperty("java.vm.name").toLowerCase())) {
                osType = 8;
                System.setProperty("jna.nounpack", "true");
            } else {
                osType = 1;
            }
        } else {
            osType = lllllllllllllllllIIIlIllllIIlIll.startsWith("AIX") ? 7 : (lllllllllllllllllIIIlIllllIIlIll.startsWith("Mac") || lllllllllllllllllIIIlIllllIIlIll.startsWith("Darwin") ? 0 : (lllllllllllllllllIIIlIllllIIlIll.startsWith("Windows CE") ? 6 : (lllllllllllllllllIIIlIllllIIlIll.startsWith("Windows") ? 2 : (lllllllllllllllllIIIlIllllIIlIll.startsWith("Solaris") || lllllllllllllllllIIIlIllllIIlIll.startsWith("SunOS") ? 3 : (lllllllllllllllllIIIlIllllIIlIll.startsWith("FreeBSD") ? 4 : (lllllllllllllllllIIIlIllllIIlIll.startsWith("OpenBSD") ? 5 : (lllllllllllllllllIIIlIllllIIlIll.equalsIgnoreCase("gnu") ? 9 : (lllllllllllllllllIIIlIllllIIlIll.equalsIgnoreCase("gnu/kfreebsd") ? 10 : (lllllllllllllllllIIIlIllllIIlIll.equalsIgnoreCase("netbsd") ? 11 : -1)))))))));
        }
        boolean lllllllllllllllllIIIlIllllIIlIlI = false;
        try {
            Class.forName("java.nio.Buffer");
            lllllllllllllllllIIIlIllllIIlIlI = true;
        }
        catch (ClassNotFoundException lllllllllllllllllIIIlIllllIIIlll) {
            // empty catch block
        }
        HAS_AWT = osType != 6 && osType != 8 && osType != 7;
        HAS_JAWT = HAS_AWT && osType != 0;
        HAS_BUFFERS = lllllllllllllllllIIIlIllllIIlIlI;
        boolean bl = RO_FIELDS = osType != 6;
        String string = osType == 2 ? "msvcrt" : (C_LIBRARY_NAME = osType == 6 ? "coredll" : "c");
        MATH_LIBRARY_NAME = osType == 2 ? "msvcrt" : (osType == 6 ? "coredll" : "m");
        HAS_DLL_CALLBACKS = osType == 2;
        ARCH = Platform.getCanonicalArchitecture(System.getProperty("os.arch"), Platform.isSoftFloat());
        RESOURCE_PREFIX = Platform.getNativeLibraryResourcePrefix();
    }

    public static final boolean isSolaris() {
        return osType == 3;
    }

    public static final boolean isPPC() {
        return ARCH.startsWith("ppc");
    }

    public static final boolean hasRuntimeExec() {
        return !Platform.isWindowsCE() || !"J9".equals(System.getProperty("java.vm.name"));
    }

    public static final boolean isMac() {
        return osType == 0;
    }
}

