/*
 * Decompiled with CFR 0.150.
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.FunctionMapper;
import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class NativeLibrary {
    private final /* synthetic */ String libraryPath;
    private final /* synthetic */ Map<String, Function> functions;
    private /* synthetic */ String encoding;
    private /* synthetic */ long handle;
    private final /* synthetic */ String libraryName;
    final /* synthetic */ int callFlags;
    private static final /* synthetic */ int DEFAULT_OPEN_OPTIONS;
    private static final /* synthetic */ List<String> librarySearchPath;
    final /* synthetic */ Map<String, ?> options;
    private static final /* synthetic */ Map<String, Reference<NativeLibrary>> libraries;
    private static final /* synthetic */ Map<String, List<String>> searchPaths;

    public static final synchronized NativeLibrary getProcess(Map<String, ?> llllllllllllllllIllIlIlllIIllIIl) {
        return NativeLibrary.getInstance(null, llllllllllllllllIllIlIlllIIllIIl);
    }

    public Function getFunction(String llllllllllllllllIllIlIlllIIIIllI) {
        NativeLibrary llllllllllllllllIllIlIlllIIIlIIl;
        return llllllllllllllllIllIlIlllIIIlIIl.getFunction(llllllllllllllllIllIlIlllIIIIllI, llllllllllllllllIllIlIlllIIIlIIl.callFlags);
    }

    private static int openFlags(Map<String, ?> llllllllllllllllIllIllIIIIIIllIl) {
        Object llllllllllllllllIllIllIIIIIIlllI = llllllllllllllllIllIllIIIIIIllIl.get("open-flags");
        if (llllllllllllllllIllIllIIIIIIlllI instanceof Number) {
            return ((Number)llllllllllllllllIllIllIIIIIIlllI).intValue();
        }
        return -1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final NativeLibrary getInstance(String llllllllllllllllIllIlIlllIlIIIll, Map<String, ?> llllllllllllllllIllIlIlllIlIIlIl) {
        HashMap llllllllllllllllIllIlIlllIlIIlII = new HashMap(llllllllllllllllIllIlIlllIlIIlIl);
        if (llllllllllllllllIllIlIlllIlIIlII.get("calling-convention") == null) {
            llllllllllllllllIllIlIlllIlIIlII.put("calling-convention", 0);
        }
        if ((Platform.isLinux() || Platform.isFreeBSD() || Platform.isAIX()) && Platform.C_LIBRARY_NAME.equals(llllllllllllllllIllIlIlllIlIIIll)) {
            llllllllllllllllIllIlIlllIlIIIll = null;
        }
        Map<String, Reference<NativeLibrary>> llllllllllllllllIllIlIlllIlIIIII = libraries;
        synchronized (llllllllllllllllIllIlIlllIlIIIII) {
            NativeLibrary llllllllllllllllIllIlIlllIlIIlll;
            Reference<NativeLibrary> llllllllllllllllIllIlIlllIlIlIII = libraries.get(String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIlllIlIIIll).append(llllllllllllllllIllIlIlllIlIIlII)));
            NativeLibrary nativeLibrary = llllllllllllllllIllIlIlllIlIIlll = llllllllllllllllIllIlIlllIlIlIII != null ? llllllllllllllllIllIlIlllIlIlIII.get() : null;
            if (llllllllllllllllIllIlIlllIlIIlll == null) {
                llllllllllllllllIllIlIlllIlIIlll = llllllllllllllllIllIlIlllIlIIIll == null ? new NativeLibrary("<process>", null, Native.open(null, NativeLibrary.openFlags(llllllllllllllllIllIlIlllIlIIlII)), llllllllllllllllIllIlIlllIlIIlII) : NativeLibrary.loadLibrary(llllllllllllllllIllIlIlllIlIIIll, llllllllllllllllIllIlIlllIlIIlII);
                llllllllllllllllIllIlIlllIlIlIII = new WeakReference<NativeLibrary>(llllllllllllllllIllIlIlllIlIIlll);
                libraries.put(String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIlllIlIIlll.getName()).append(llllllllllllllllIllIlIlllIlIIlII)), llllllllllllllllIllIlIlllIlIlIII);
                File llllllllllllllllIllIlIlllIlIlIIl = llllllllllllllllIllIlIlllIlIIlll.getFile();
                if (llllllllllllllllIllIlIlllIlIlIIl != null) {
                    libraries.put(String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIlllIlIlIIl.getAbsolutePath()).append(llllllllllllllllIllIlIlllIlIIlII)), llllllllllllllllIllIlIlllIlIlIII);
                    libraries.put(String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIlllIlIlIIl.getName()).append(llllllllllllllllIllIlIlllIlIIlII)), llllllllllllllllIllIlIlllIlIlIII);
                }
            }
            return llllllllllllllllIllIlIlllIlIIlll;
        }
    }

    public String getName() {
        NativeLibrary llllllllllllllllIllIlIllIIllIlll;
        return llllllllllllllllIllIlIllIIllIlll.libraryName;
    }

    private static List<String> initPaths(String llllllllllllllllIllIlIllIIIIlIIl) {
        String llllllllllllllllIllIlIllIIIIlIII = System.getProperty(llllllllllllllllIllIlIllIIIIlIIl, "");
        if ("".equals(llllllllllllllllIllIlIllIIIIlIII)) {
            return Collections.emptyList();
        }
        StringTokenizer llllllllllllllllIllIlIllIIIIIlll = new StringTokenizer(llllllllllllllllIllIlIllIIIIlIII, File.pathSeparator);
        ArrayList<String> llllllllllllllllIllIlIllIIIIIllI = new ArrayList<String>();
        while (llllllllllllllllIllIlIllIIIIIlll.hasMoreTokens()) {
            String llllllllllllllllIllIlIllIIIIlIlI = llllllllllllllllIllIlIllIIIIIlll.nextToken();
            if ("".equals(llllllllllllllllIllIlIllIIIIlIlI)) continue;
            llllllllllllllllIllIlIllIIIIIllI.add(llllllllllllllllIllIlIllIIIIlIlI);
        }
        return llllllllllllllllIllIlIllIIIIIllI;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    static void disposeAll() {
        void llllllllllllllllIllIlIllIIlIlIIl;
        Map<String, Reference<NativeLibrary>> llllllllllllllllIllIlIllIIlIIlll = libraries;
        synchronized (llllllllllllllllIllIlIllIIlIIlll) {
            LinkedHashSet<Reference<NativeLibrary>> llllllllllllllllIllIlIllIIlIllII = new LinkedHashSet<Reference<NativeLibrary>>(libraries.values());
        }
        for (Reference llllllllllllllllIllIlIllIIlIlIlI : llllllllllllllllIllIlIllIIlIlIIl) {
            NativeLibrary llllllllllllllllIllIlIllIIlIlIll = (NativeLibrary)llllllllllllllllIllIlIllIIlIlIlI.get();
            if (llllllllllllllllIllIlIllIIlIlIll == null) continue;
            llllllllllllllllIllIlIllIIlIlIll.dispose();
        }
    }

    private static String findLibraryPath(String llllllllllllllllIllIlIlIllllIlIl, List<String> llllllllllllllllIllIlIlIllllIlll) {
        if (new File(llllllllllllllllIllIlIlIllllIlIl).isAbsolute()) {
            return llllllllllllllllIllIlIlIllllIlIl;
        }
        String llllllllllllllllIllIlIlIllllIllI = NativeLibrary.mapSharedLibraryName(llllllllllllllllIllIlIlIllllIlIl);
        for (String llllllllllllllllIllIlIlIlllllIIl : llllllllllllllllIllIlIlIllllIlll) {
            File llllllllllllllllIllIlIlIlllllIlI = new File(llllllllllllllllIllIlIlIlllllIIl, llllllllllllllllIllIlIlIllllIllI);
            if (llllllllllllllllIllIlIlIlllllIlI.exists()) {
                return llllllllllllllllIllIlIlIlllllIlI.getAbsolutePath();
            }
            if (!Platform.isMac() || !llllllllllllllllIllIlIlIllllIllI.endsWith(".dylib") || !(llllllllllllllllIllIlIlIlllllIlI = new File(llllllllllllllllIllIlIlIlllllIIl, String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIlIllllIllI.substring(0, llllllllllllllllIllIlIlIllllIllI.lastIndexOf(".dylib"))).append(".jnilib")))).exists()) continue;
            return llllllllllllllllIllIlIlIlllllIlI.getAbsolutePath();
        }
        return llllllllllllllllIllIlIlIllllIllI;
    }

    static double parseVersion(String llllllllllllllllIllIlIlIlIlIlIIl) {
        double llllllllllllllllIllIlIlIlIlIllII = 0.0;
        double llllllllllllllllIllIlIlIlIlIlIll = 1.0;
        int llllllllllllllllIllIlIlIlIlIlIlI = llllllllllllllllIllIlIlIlIlIlIIl.indexOf(".");
        while (llllllllllllllllIllIlIlIlIlIlIIl != null) {
            String llllllllllllllllIllIlIlIlIlIlllI;
            if (llllllllllllllllIllIlIlIlIlIlIlI != -1) {
                String llllllllllllllllIllIlIlIlIllIIII = llllllllllllllllIllIlIlIlIlIlIIl.substring(0, llllllllllllllllIllIlIlIlIlIlIlI);
                llllllllllllllllIllIlIlIlIlIlIIl = llllllllllllllllIllIlIlIlIlIlIIl.substring(llllllllllllllllIllIlIlIlIlIlIlI + 1);
                llllllllllllllllIllIlIlIlIlIlIlI = llllllllllllllllIllIlIlIlIlIlIIl.indexOf(".");
            } else {
                llllllllllllllllIllIlIlIlIlIlllI = llllllllllllllllIllIlIlIlIlIlIIl;
                llllllllllllllllIllIlIlIlIlIlIIl = null;
            }
            try {
                llllllllllllllllIllIlIlIlIlIllII += (double)Integer.parseInt(llllllllllllllllIllIlIlIlIlIlllI) / llllllllllllllllIllIlIlIlIlIlIll;
            }
            catch (NumberFormatException llllllllllllllllIllIlIlIlIlIllll) {
                return 0.0;
            }
            llllllllllllllllIllIlIlIlIlIlIll *= 100.0;
        }
        return llllllllllllllllIllIlIlIlIlIllII;
    }

    private static String functionKey(String llllllllllllllllIllIllIIlIlllIll, int llllllllllllllllIllIllIIlIlllIlI, String llllllllllllllllIllIllIIlIllIllI) {
        return String.valueOf(new StringBuilder().append(llllllllllllllllIllIllIIlIlllIll).append("|").append(llllllllllllllllIllIllIIlIlllIlI).append("|").append(llllllllllllllllIllIllIIlIllIllI));
    }

    static String mapSharedLibraryName(String llllllllllllllllIllIlIlIlllIllII) {
        if (Platform.isMac()) {
            if (llllllllllllllllIllIlIlIlllIllII.startsWith("lib") && (llllllllllllllllIllIlIlIlllIllII.endsWith(".dylib") || llllllllllllllllIllIlIlIlllIllII.endsWith(".jnilib"))) {
                return llllllllllllllllIllIlIlIlllIllII;
            }
            String llllllllllllllllIllIlIlIlllIllIl = System.mapLibraryName(llllllllllllllllIllIlIlIlllIllII);
            if (llllllllllllllllIllIlIlIlllIllIl.endsWith(".jnilib")) {
                return String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIlIlllIllIl.substring(0, llllllllllllllllIllIlIlIlllIllIl.lastIndexOf(".jnilib"))).append(".dylib"));
            }
            return llllllllllllllllIllIlIlIlllIllIl;
        }
        if (Platform.isLinux() || Platform.isFreeBSD() ? NativeLibrary.isVersionedName(llllllllllllllllIllIlIlIlllIllII) || llllllllllllllllIllIlIlIlllIllII.endsWith(".so") : (Platform.isAIX() ? llllllllllllllllIllIlIlIlllIllII.startsWith("lib") : Platform.isWindows() && (llllllllllllllllIllIlIlIlllIllII.endsWith(".drv") || llllllllllllllllIllIlIlIlllIllII.endsWith(".dll")))) {
            return llllllllllllllllIllIlIlIlllIllII;
        }
        return System.mapLibraryName(llllllllllllllllIllIlIlIlllIllII);
    }

    static {
        DEFAULT_OPEN_OPTIONS = -1;
        libraries = new HashMap<String, Reference<NativeLibrary>>();
        searchPaths = Collections.synchronizedMap(new HashMap());
        librarySearchPath = new ArrayList<String>();
        if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        String llllllllllllllllIllIlIlIIlllIIII = Native.getWebStartLibraryPath("jnidispatch");
        if (llllllllllllllllIllIlIlIIlllIIII != null) {
            librarySearchPath.add(llllllllllllllllIllIlIlIIlllIIII);
        }
        if (System.getProperty("jna.platform.library.path") == null && !Platform.isWindows()) {
            String llllllllllllllllIllIlIlIIlllIlII = "";
            String llllllllllllllllIllIlIlIIlllIIll = "";
            String llllllllllllllllIllIlIlIIlllIIlI = "";
            if (Platform.isLinux() || Platform.isSolaris() || Platform.isFreeBSD() || Platform.iskFreeBSD()) {
                llllllllllllllllIllIlIlIIlllIIlI = String.valueOf(new StringBuilder().append(Platform.isSolaris() ? "/" : "").append(Pointer.SIZE * 8));
            }
            String[] llllllllllllllllIllIlIlIIlllIIIl = new String[]{String.valueOf(new StringBuilder().append("/usr/lib").append(llllllllllllllllIllIlIlIIlllIIlI)), String.valueOf(new StringBuilder().append("/lib").append(llllllllllllllllIllIlIlIIlllIIlI)), "/usr/lib", "/lib"};
            if (Platform.isLinux() || Platform.iskFreeBSD() || Platform.isGNU()) {
                String llllllllllllllllIllIlIlIIllllIlI = NativeLibrary.getMultiArchPath();
                llllllllllllllllIllIlIlIIlllIIIl = new String[]{String.valueOf(new StringBuilder().append("/usr/lib/").append(llllllllllllllllIllIlIlIIllllIlI)), String.valueOf(new StringBuilder().append("/lib/").append(llllllllllllllllIllIlIlIIllllIlI)), String.valueOf(new StringBuilder().append("/usr/lib").append(llllllllllllllllIllIlIlIIlllIIlI)), String.valueOf(new StringBuilder().append("/lib").append(llllllllllllllllIllIlIlIIlllIIlI)), "/usr/lib", "/lib"};
            }
            if (Platform.isLinux()) {
                ArrayList<String> llllllllllllllllIllIlIlIIlllIlll = NativeLibrary.getLinuxLdPaths();
                for (int llllllllllllllllIllIlIlIIllllIII = llllllllllllllllIllIlIlIIlllIIIl.length - 1; 0 <= llllllllllllllllIllIlIlIIllllIII; --llllllllllllllllIllIlIlIIllllIII) {
                    int llllllllllllllllIllIlIlIIllllIIl = llllllllllllllllIllIlIlIIlllIlll.indexOf(llllllllllllllllIllIlIlIIlllIIIl[llllllllllllllllIllIlIlIIllllIII]);
                    if (llllllllllllllllIllIlIlIIllllIIl != -1) {
                        llllllllllllllllIllIlIlIIlllIlll.remove(llllllllllllllllIllIlIlIIllllIIl);
                    }
                    llllllllllllllllIllIlIlIIlllIlll.add(0, llllllllllllllllIllIlIlIIlllIIIl[llllllllllllllllIllIlIlIIllllIII]);
                }
                llllllllllllllllIllIlIlIIlllIIIl = llllllllllllllllIllIlIlIIlllIlll.toArray(new String[llllllllllllllllIllIlIlIIlllIlll.size()]);
            }
            for (int llllllllllllllllIllIlIlIIlllIlIl = 0; llllllllllllllllIllIlIlIIlllIlIl < llllllllllllllllIllIlIlIIlllIIIl.length; ++llllllllllllllllIllIlIlIIlllIlIl) {
                File llllllllllllllllIllIlIlIIlllIllI = new File(llllllllllllllllIllIlIlIIlllIIIl[llllllllllllllllIllIlIlIIlllIlIl]);
                if (!llllllllllllllllIllIlIlIIlllIllI.exists() || !llllllllllllllllIllIlIlIIlllIllI.isDirectory()) continue;
                llllllllllllllllIllIlIlIIlllIlII = String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIlIIlllIlII).append(llllllllllllllllIllIlIlIIlllIIll).append(llllllllllllllllIllIlIlIIlllIIIl[llllllllllllllllIllIlIlIIlllIlIl]));
                llllllllllllllllIllIlIlIIlllIIll = File.pathSeparator;
            }
            if (!"".equals(llllllllllllllllIllIlIlIIlllIlII)) {
                System.setProperty("jna.platform.library.path", llllllllllllllllIllIlIlIIlllIlII);
            }
        }
        librarySearchPath.addAll(NativeLibrary.initPaths("jna.platform.library.path"));
    }

    public Function getFunction(String llllllllllllllllIllIlIllIllIlIIl, int llllllllllllllllIllIlIllIllIIlIl) {
        NativeLibrary llllllllllllllllIllIlIllIllIlIlI;
        return llllllllllllllllIllIlIllIllIlIlI.getFunction(llllllllllllllllIllIlIllIllIlIIl, llllllllllllllllIllIlIllIllIIlIl, llllllllllllllllIllIlIllIllIlIlI.encoding);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void addSearchPath(String llllllllllllllllIllIlIlllIIlIIII, String llllllllllllllllIllIlIlllIIlIIIl) {
        Map<String, List<String>> llllllllllllllllIllIlIlllIIIlllI = searchPaths;
        synchronized (llllllllllllllllIllIlIlllIIIlllI) {
            List<String> llllllllllllllllIllIlIlllIIlIIll = searchPaths.get(llllllllllllllllIllIlIlllIIlIIII);
            if (llllllllllllllllIllIlIlllIIlIIll == null) {
                llllllllllllllllIllIlIlllIIlIIll = Collections.synchronizedList(new ArrayList());
                searchPaths.put(llllllllllllllllIllIlIlllIIlIIII, llllllllllllllllIllIlIlllIIlIIll);
            }
            llllllllllllllllIllIlIlllIIlIIll.add(llllllllllllllllIllIlIlllIIlIIIl);
        }
    }

    public File getFile() {
        NativeLibrary llllllllllllllllIllIlIllIIllIlII;
        if (llllllllllllllllIllIlIllIIllIlII.libraryPath == null) {
            return null;
        }
        return new File(llllllllllllllllIllIlIllIIllIlII.libraryPath);
    }

    Function getFunction(String llllllllllllllllIllIlIllIlllIlII, Method llllllllllllllllIllIlIllIlllIIll) {
        String llllllllllllllllIllIlIllIllllIII;
        NativeLibrary llllllllllllllllIllIlIllIlllllII;
        FunctionMapper llllllllllllllllIllIlIllIllllIIl = (FunctionMapper)llllllllllllllllIllIlIllIlllllII.options.get("function-mapper");
        if (llllllllllllllllIllIlIllIllllIIl != null) {
            llllllllllllllllIllIlIllIlllIlII = llllllllllllllllIllIlIllIllllIIl.getFunctionName(llllllllllllllllIllIlIllIlllllII, llllllllllllllllIllIlIllIlllIIll);
        }
        if (llllllllllllllllIllIlIllIlllIlII.startsWith(llllllllllllllllIllIlIllIllllIII = System.getProperty("jna.profiler.prefix", "$$YJP$$"))) {
            llllllllllllllllIllIlIllIlllIlII = llllllllllllllllIllIlIllIlllIlII.substring(llllllllllllllllIllIlIllIllllIII.length());
        }
        int llllllllllllllllIllIlIllIlllIlll = llllllllllllllllIllIlIllIlllllII.callFlags;
        Class<?>[] llllllllllllllllIllIlIllIlllIllI = llllllllllllllllIllIlIllIlllIIll.getExceptionTypes();
        for (int llllllllllllllllIllIlIllIlllllIl = 0; llllllllllllllllIllIlIllIlllllIl < llllllllllllllllIllIlIllIlllIllI.length; ++llllllllllllllllIllIlIllIlllllIl) {
            if (!LastErrorException.class.isAssignableFrom(llllllllllllllllIllIlIllIlllIllI[llllllllllllllllIllIlIllIlllllIl])) continue;
            llllllllllllllllIllIlIllIlllIlll |= 0x40;
        }
        return llllllllllllllllIllIlIllIlllllII.getFunction(llllllllllllllllIllIlIllIlllIlII, llllllllllllllllIllIlIllIlllIlll);
    }

    public static final synchronized NativeLibrary getProcess() {
        return NativeLibrary.getInstance(null);
    }

    private static boolean isVersionedName(String llllllllllllllllIllIlIlIlllIIIlI) {
        int llllllllllllllllIllIlIlIlllIIIll;
        if (llllllllllllllllIllIlIlIlllIIIlI.startsWith("lib") && (llllllllllllllllIllIlIlIlllIIIll = llllllllllllllllIllIlIlIlllIIIlI.lastIndexOf(".so.")) != -1 && llllllllllllllllIllIlIlIlllIIIll + 4 < llllllllllllllllIllIlIlIlllIIIlI.length()) {
            for (int llllllllllllllllIllIlIlIlllIIlII = llllllllllllllllIllIlIlIlllIIIll + 4; llllllllllllllllIllIlIlIlllIIlII < llllllllllllllllIllIlIlIlllIIIlI.length(); ++llllllllllllllllIllIlIlIlllIIlII) {
                char llllllllllllllllIllIlIlIlllIIlIl = llllllllllllllllIllIlIlIlllIIIlI.charAt(llllllllllllllllIllIlIlIlllIIlII);
                if (Character.isDigit(llllllllllllllllIllIlIlIlllIIlIl) || llllllllllllllllIllIlIlIlllIIlIl == '.') continue;
                return false;
            }
            return true;
        }
        return false;
    }

    long getSymbolAddress(String llllllllllllllllIllIlIllIIllllll) {
        NativeLibrary llllllllllllllllIllIlIllIIlllllI;
        if (llllllllllllllllIllIlIllIIlllllI.handle == 0L) {
            throw new UnsatisfiedLinkError("Library has been unloaded");
        }
        return Native.findSymbol(llllllllllllllllIllIlIllIIlllllI.handle, llllllllllllllllIllIlIllIIllllll);
    }

    private String getLibraryName(String llllllllllllllllIllIlIllllIIlIII) {
        String llllllllllllllllIllIlIllllIIIIll;
        int llllllllllllllllIllIlIllllIIIIlI;
        String llllllllllllllllIllIlIllllIIIlll = llllllllllllllllIllIlIllllIIlIII;
        String llllllllllllllllIllIlIllllIIIllI = "---";
        String llllllllllllllllIllIlIllllIIIlIl = NativeLibrary.mapSharedLibraryName("---");
        int llllllllllllllllIllIlIllllIIIlII = llllllllllllllllIllIlIllllIIIlIl.indexOf("---");
        if (llllllllllllllllIllIlIllllIIIlII > 0 && llllllllllllllllIllIlIllllIIIlll.startsWith(llllllllllllllllIllIlIllllIIIlIl.substring(0, llllllllllllllllIllIlIllllIIIlII))) {
            llllllllllllllllIllIlIllllIIIlll = llllllllllllllllIllIlIllllIIIlll.substring(llllllllllllllllIllIlIllllIIIlII);
        }
        if ((llllllllllllllllIllIlIllllIIIIlI = llllllllllllllllIllIlIllllIIIlll.indexOf(llllllllllllllllIllIlIllllIIIIll = llllllllllllllllIllIlIllllIIIlIl.substring(llllllllllllllllIllIlIllllIIIlII + "---".length()))) != -1) {
            llllllllllllllllIllIlIllllIIIlll = llllllllllllllllIllIlIllllIIIlll.substring(0, llllllllllllllllIllIlIllllIIIIlI);
        }
        return llllllllllllllllIllIlIllllIIIlll;
    }

    static String matchLibrary(final String llllllllllllllllIllIlIlIllIIIIll, List<String> llllllllllllllllIllIlIlIllIIIIlI) {
        File llllllllllllllllIllIlIlIllIIlIII = new File(llllllllllllllllIllIlIlIllIIIIll);
        if (llllllllllllllllIllIlIlIllIIlIII.isAbsolute()) {
            llllllllllllllllIllIlIlIllIIIIlI = Arrays.asList(llllllllllllllllIllIlIlIllIIlIII.getParent());
        }
        FilenameFilter llllllllllllllllIllIlIlIllIIIlll = new FilenameFilter(){
            {
                2 lllIlIIIIlIlIIl;
            }

            @Override
            public boolean accept(File lllIlIIIIlIIlII, String lllIlIIIIlIIIll) {
                2 lllIlIIIIlIIIlI;
                return (lllIlIIIIlIIIll.startsWith(String.valueOf(new StringBuilder().append("lib").append(lllIlIIIIlIIIlI.llllllllllllllllIllIlIlIllIIIIll).append(".so"))) || lllIlIIIIlIIIll.startsWith(String.valueOf(new StringBuilder().append(lllIlIIIIlIIIlI.llllllllllllllllIllIlIlIllIIIIll).append(".so"))) && lllIlIIIIlIIIlI.llllllllllllllllIllIlIlIllIIIIll.startsWith("lib")) && NativeLibrary.isVersionedName(lllIlIIIIlIIIll);
            }
        };
        LinkedList<File> llllllllllllllllIllIlIlIllIIIllI = new LinkedList<File>();
        for (String llllllllllllllllIllIlIlIllIIllll : llllllllllllllllIllIlIlIllIIIIlI) {
            File[] llllllllllllllllIllIlIlIllIlIIII = new File(llllllllllllllllIllIlIlIllIIllll).listFiles(llllllllllllllllIllIlIlIllIIIlll);
            if (llllllllllllllllIllIlIlIllIlIIII == null || llllllllllllllllIllIlIlIllIlIIII.length <= 0) continue;
            llllllllllllllllIllIlIlIllIIIllI.addAll(Arrays.asList(llllllllllllllllIllIlIlIllIlIIII));
        }
        double llllllllllllllllIllIlIlIllIIIlIl = -1.0;
        String llllllllllllllllIllIlIlIllIIIlII = null;
        for (File llllllllllllllllIllIlIlIllIIlIll : llllllllllllllllIllIlIlIllIIIllI) {
            String llllllllllllllllIllIlIlIllIIlllI = llllllllllllllllIllIlIlIllIIlIll.getAbsolutePath();
            String llllllllllllllllIllIlIlIllIIllIl = llllllllllllllllIllIlIlIllIIlllI.substring(llllllllllllllllIllIlIlIllIIlllI.lastIndexOf(".so.") + 4);
            double llllllllllllllllIllIlIlIllIIllII = NativeLibrary.parseVersion(llllllllllllllllIllIlIlIllIIllIl);
            if (!(llllllllllllllllIllIlIlIllIIllII > llllllllllllllllIllIlIlIllIIIlIl)) continue;
            llllllllllllllllIllIlIlIllIIIlIl = llllllllllllllllIllIlIlIllIIllII;
            llllllllllllllllIllIlIlIllIIIlII = llllllllllllllllIllIlIlIllIIlllI;
        }
        return llllllllllllllllIllIlIlIllIIIlII;
    }

    public Pointer getGlobalVariableAddress(String llllllllllllllllIllIlIllIlIIIlII) {
        try {
            NativeLibrary llllllllllllllllIllIlIllIlIIIlIl;
            return new Pointer(llllllllllllllllIllIlIllIlIIIlIl.getSymbolAddress(llllllllllllllllIllIlIllIlIIIlII));
        }
        catch (UnsatisfiedLinkError llllllllllllllllIllIlIllIlIIlIII) {
            throw new UnsatisfiedLinkError(String.valueOf(new StringBuilder().append("Error looking up '").append(llllllllllllllllIllIlIllIlIIIlII).append("': ").append(llllllllllllllllIllIlIllIlIIlIII.getMessage())));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dispose() {
        NativeLibrary llllllllllllllllIllIlIllIIIlIlll;
        HashSet<String> llllllllllllllllIllIlIllIIIllIII = new HashSet<String>();
        Object llllllllllllllllIllIlIllIIIlIlIl = libraries;
        synchronized (llllllllllllllllIllIlIllIIIlIlIl) {
            for (Map.Entry<String, Reference<NativeLibrary>> llllllllllllllllIllIlIllIIIllIll : libraries.entrySet()) {
                Reference<NativeLibrary> llllllllllllllllIllIlIllIIIlllII = llllllllllllllllIllIlIllIIIllIll.getValue();
                if (llllllllllllllllIllIlIllIIIlllII.get() != llllllllllllllllIllIlIllIIIlIlll) continue;
                llllllllllllllllIllIlIllIIIllIII.add(llllllllllllllllIllIlIllIIIllIll.getKey());
            }
            for (String llllllllllllllllIllIlIllIIIllIlI : llllllllllllllllIllIlIllIIIllIII) {
                libraries.remove(llllllllllllllllIllIlIllIIIllIlI);
            }
        }
        llllllllllllllllIllIlIllIIIlIlIl = llllllllllllllllIllIlIllIIIlIlll;
        synchronized (llllllllllllllllIllIlIllIIIlIlIl) {
            if (llllllllllllllllIllIlIllIIIlIlll.handle != 0L) {
                Native.close(llllllllllllllllIllIlIllIIIlIlll.handle);
                llllllllllllllllIllIlIllIIIlIlll.handle = 0L;
            }
        }
    }

    protected void finalize() {
        NativeLibrary llllllllllllllllIllIlIllIIllIIIl;
        llllllllllllllllIllIlIllIIllIIIl.dispose();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static NativeLibrary loadLibrary(String llllllllllllllllIllIlIlllllIlllI, Map<String, ?> llllllllllllllllIllIlIlllllIllIl) {
        long llllllllllllllllIllIlIlllllIllll;
        String llllllllllllllllIllIlIllllllIIII;
        block51: {
            List<String> llllllllllllllllIllIlIllllllIIIl;
            if (Native.DEBUG_LOAD) {
                System.out.println(String.valueOf(new StringBuilder().append("Looking for library '").append(llllllllllllllllIllIlIlllllIlllI).append("'")));
            }
            boolean llllllllllllllllIllIlIllllllIlIl = new File(llllllllllllllllIllIlIlllllIlllI).isAbsolute();
            ArrayList<String> llllllllllllllllIllIlIllllllIlII = new ArrayList<String>();
            int llllllllllllllllIllIlIllllllIIll = NativeLibrary.openFlags(llllllllllllllllIllIlIlllllIllIl);
            String llllllllllllllllIllIlIllllllIIlI = Native.getWebStartLibraryPath(llllllllllllllllIllIlIlllllIlllI);
            if (llllllllllllllllIllIlIllllllIIlI != null) {
                if (Native.DEBUG_LOAD) {
                    System.out.println(String.valueOf(new StringBuilder().append("Adding web start path ").append(llllllllllllllllIllIlIllllllIIlI)));
                }
                llllllllllllllllIllIlIllllllIlII.add(llllllllllllllllIllIlIllllllIIlI);
            }
            if ((llllllllllllllllIllIlIllllllIIIl = searchPaths.get(llllllllllllllllIllIlIlllllIlllI)) != null) {
                List<String> llllllllllllllllIllIlIlllllIIlll = llllllllllllllllIllIlIllllllIIIl;
                synchronized (llllllllllllllllIllIlIlllllIIlll) {
                    llllllllllllllllIllIlIllllllIlII.addAll(0, llllllllllllllllIllIlIllllllIIIl);
                }
            }
            if (Native.DEBUG_LOAD) {
                System.out.println(String.valueOf(new StringBuilder().append("Adding paths from jna.library.path: ").append(System.getProperty("jna.library.path"))));
            }
            llllllllllllllllIllIlIllllllIlII.addAll(NativeLibrary.initPaths("jna.library.path"));
            llllllllllllllllIllIlIllllllIIII = NativeLibrary.findLibraryPath(llllllllllllllllIllIlIlllllIlllI, llllllllllllllllIllIlIllllllIlII);
            llllllllllllllllIllIlIlllllIllll = 0L;
            try {
                if (Native.DEBUG_LOAD) {
                    System.out.println(String.valueOf(new StringBuilder().append("Trying ").append(llllllllllllllllIllIlIllllllIIII)));
                }
                llllllllllllllllIllIlIlllllIllll = Native.open(llllllllllllllllIllIlIllllllIIII, llllllllllllllllIllIlIllllllIIll);
            }
            catch (UnsatisfiedLinkError llllllllllllllllIllIlIllllllllll) {
                if (Native.DEBUG_LOAD) {
                    System.out.println(String.valueOf(new StringBuilder().append("Adding system paths: ").append(librarySearchPath)));
                }
                llllllllllllllllIllIlIllllllIlII.addAll(librarySearchPath);
            }
            try {
                if (llllllllllllllllIllIlIlllllIllll == 0L) {
                    llllllllllllllllIllIlIllllllIIII = NativeLibrary.findLibraryPath(llllllllllllllllIllIlIlllllIlllI, llllllllllllllllIllIlIllllllIlII);
                    if (Native.DEBUG_LOAD) {
                        System.out.println(String.valueOf(new StringBuilder().append("Trying ").append(llllllllllllllllIllIlIllllllIIII)));
                    }
                    if ((llllllllllllllllIllIlIlllllIllll = Native.open(llllllllllllllllIllIlIllllllIIII, llllllllllllllllIllIlIllllllIIll)) == 0L) {
                        throw new UnsatisfiedLinkError(String.valueOf(new StringBuilder().append("Failed to load library '").append(llllllllllllllllIllIlIlllllIlllI).append("'")));
                    }
                }
            }
            catch (UnsatisfiedLinkError llllllllllllllllIllIlIlllllllIII) {
                if (Platform.isAndroid()) {
                    try {
                        if (Native.DEBUG_LOAD) {
                            System.out.println(String.valueOf(new StringBuilder().append("Preload (via System.loadLibrary) ").append(llllllllllllllllIllIlIlllllIlllI)));
                        }
                        System.loadLibrary(llllllllllllllllIllIlIlllllIlllI);
                        llllllllllllllllIllIlIlllllIllll = Native.open(llllllllllllllllIllIlIllllllIIII, llllllllllllllllIllIlIllllllIIll);
                    }
                    catch (UnsatisfiedLinkError llllllllllllllllIllIlIlllllllllI) {
                        llllllllllllllllIllIlIlllllllIII = llllllllllllllllIllIlIlllllllllI;
                    }
                } else if (Platform.isLinux() || Platform.isFreeBSD()) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Looking for version variants");
                    }
                    if ((llllllllllllllllIllIlIllllllIIII = NativeLibrary.matchLibrary(llllllllllllllllIllIlIlllllIlllI, llllllllllllllllIllIlIllllllIlII)) != null) {
                        if (Native.DEBUG_LOAD) {
                            System.out.println(String.valueOf(new StringBuilder().append("Trying ").append(llllllllllllllllIllIlIllllllIIII)));
                        }
                        try {
                            llllllllllllllllIllIlIlllllIllll = Native.open(llllllllllllllllIllIlIllllllIIII, llllllllllllllllIllIlIllllllIIll);
                        }
                        catch (UnsatisfiedLinkError llllllllllllllllIllIlIllllllllIl) {
                            llllllllllllllllIllIlIlllllllIII = llllllllllllllllIllIlIllllllllIl;
                        }
                    }
                } else if (Platform.isMac() && !llllllllllllllllIllIlIlllllIlllI.endsWith(".dylib")) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Looking for matching frameworks");
                    }
                    if ((llllllllllllllllIllIlIllllllIIII = NativeLibrary.matchFramework(llllllllllllllllIllIlIlllllIlllI)) != null) {
                        try {
                            if (Native.DEBUG_LOAD) {
                                System.out.println(String.valueOf(new StringBuilder().append("Trying ").append(llllllllllllllllIllIlIllllllIIII)));
                            }
                            llllllllllllllllIllIlIlllllIllll = Native.open(llllllllllllllllIllIlIllllllIIII, llllllllllllllllIllIlIllllllIIll);
                        }
                        catch (UnsatisfiedLinkError llllllllllllllllIllIlIllllllllII) {
                            llllllllllllllllIllIlIlllllllIII = llllllllllllllllIllIlIllllllllII;
                        }
                    }
                } else if (Platform.isWindows() && !llllllllllllllllIllIlIllllllIlIl) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Looking for lib- prefix");
                    }
                    if ((llllllllllllllllIllIlIllllllIIII = NativeLibrary.findLibraryPath(String.valueOf(new StringBuilder().append("lib").append(llllllllllllllllIllIlIlllllIlllI)), llllllllllllllllIllIlIllllllIlII)) != null) {
                        if (Native.DEBUG_LOAD) {
                            System.out.println(String.valueOf(new StringBuilder().append("Trying ").append(llllllllllllllllIllIlIllllllIIII)));
                        }
                        try {
                            llllllllllllllllIllIlIlllllIllll = Native.open(llllllllllllllllIllIlIllllllIIII, llllllllllllllllIllIlIllllllIIll);
                        }
                        catch (UnsatisfiedLinkError llllllllllllllllIllIlIlllllllIll) {
                            llllllllllllllllIllIlIlllllllIII = llllllllllllllllIllIlIlllllllIll;
                        }
                    }
                }
                if (llllllllllllllllIllIlIlllllIllll == 0L) {
                    try {
                        File llllllllllllllllIllIlIlllllllIlI = Native.extractFromResourcePath(llllllllllllllllIllIlIlllllIlllI, (ClassLoader)llllllllllllllllIllIlIlllllIllIl.get("classloader"));
                        try {
                            llllllllllllllllIllIlIlllllIllll = Native.open(llllllllllllllllIllIlIlllllllIlI.getAbsolutePath(), llllllllllllllllIllIlIllllllIIll);
                            llllllllllllllllIllIlIllllllIIII = llllllllllllllllIllIlIlllllllIlI.getAbsolutePath();
                        }
                        finally {
                            if (Native.isUnpacked(llllllllllllllllIllIlIlllllllIlI)) {
                                Native.deleteLibrary(llllllllllllllllIllIlIlllllllIlI);
                            }
                        }
                    }
                    catch (IOException llllllllllllllllIllIlIlllllllIIl) {
                        llllllllllllllllIllIlIlllllllIII = new UnsatisfiedLinkError(llllllllllllllllIllIlIlllllllIIl.getMessage());
                    }
                }
                if (llllllllllllllllIllIlIlllllIllll != 0L) break block51;
                throw new UnsatisfiedLinkError(String.valueOf(new StringBuilder().append("Unable to load library '").append(llllllllllllllllIllIlIlllllIlllI).append("': ").append(llllllllllllllllIllIlIlllllllIII.getMessage())));
            }
        }
        if (Native.DEBUG_LOAD) {
            System.out.println(String.valueOf(new StringBuilder().append("Found library '").append(llllllllllllllllIllIlIlllllIlllI).append("' at ").append(llllllllllllllllIllIlIllllllIIII)));
        }
        return new NativeLibrary(llllllllllllllllIllIlIlllllIlllI, llllllllllllllllIllIlIllllllIIII, llllllllllllllllIllIlIlllllIllll, llllllllllllllllIllIlIlllllIllIl);
    }

    public static final NativeLibrary getInstance(String llllllllllllllllIllIlIlllIllIIll, ClassLoader llllllllllllllllIllIlIlllIllIIlI) {
        return NativeLibrary.getInstance(llllllllllllllllIllIlIlllIllIIll, Collections.singletonMap("classloader", llllllllllllllllIllIlIlllIllIIlI));
    }

    private static ArrayList<String> getLinuxLdPaths() {
        ArrayList<String> llllllllllllllllIllIlIlIlIIIllIl = new ArrayList<String>();
        try {
            Process llllllllllllllllIllIlIlIlIIlIIII = Runtime.getRuntime().exec("/sbin/ldconfig -p");
            BufferedReader llllllllllllllllIllIlIlIlIIIllll = new BufferedReader(new InputStreamReader(llllllllllllllllIllIlIlIlIIlIIII.getInputStream()));
            String llllllllllllllllIllIlIlIlIIIlllI = "";
            while ((llllllllllllllllIllIlIlIlIIIlllI = llllllllllllllllIllIlIlIlIIIllll.readLine()) != null) {
                String llllllllllllllllIllIlIlIlIIlIIll;
                int llllllllllllllllIllIlIlIlIIlIIlI = llllllllllllllllIllIlIlIlIIIlllI.indexOf(" => ");
                int llllllllllllllllIllIlIlIlIIlIIIl = llllllllllllllllIllIlIlIlIIIlllI.lastIndexOf(47);
                if (llllllllllllllllIllIlIlIlIIlIIlI == -1 || llllllllllllllllIllIlIlIlIIlIIIl == -1 || llllllllllllllllIllIlIlIlIIlIIlI >= llllllllllllllllIllIlIlIlIIlIIIl || llllllllllllllllIllIlIlIlIIIllIl.contains(llllllllllllllllIllIlIlIlIIlIIll = llllllllllllllllIllIlIlIlIIIlllI.substring(llllllllllllllllIllIlIlIlIIlIIlI + 4, llllllllllllllllIllIlIlIlIIlIIIl))) continue;
                llllllllllllllllIllIlIlIlIIIllIl.add(llllllllllllllllIllIlIlIlIIlIIll);
            }
            llllllllllllllllIllIlIlIlIIIllll.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return llllllllllllllllIllIlIlIlIIIllIl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private NativeLibrary(String llllllllllllllllIllIllIIIIIllIlI, String llllllllllllllllIllIllIIlIlIlIII, long llllllllllllllllIllIllIIIIIllIII, Map<String, ?> llllllllllllllllIllIllIIlIlIIllI) {
        int llllllllllllllllIllIllIIlIlIIlII;
        NativeLibrary llllllllllllllllIllIllIIlIlIlIlI;
        llllllllllllllllIllIllIIlIlIlIlI.functions = new HashMap<String, Function>();
        llllllllllllllllIllIllIIlIlIlIlI.libraryName = llllllllllllllllIllIllIIlIlIlIlI.getLibraryName(llllllllllllllllIllIllIIIIIllIlI);
        llllllllllllllllIllIllIIlIlIlIlI.libraryPath = llllllllllllllllIllIllIIlIlIlIII;
        llllllllllllllllIllIllIIlIlIlIlI.handle = llllllllllllllllIllIllIIIIIllIII;
        Object llllllllllllllllIllIllIIlIlIIlIl = llllllllllllllllIllIllIIlIlIIllI.get("calling-convention");
        llllllllllllllllIllIllIIlIlIlIlI.callFlags = llllllllllllllllIllIllIIlIlIIlII = llllllllllllllllIllIllIIlIlIIlIl instanceof Number ? ((Number)llllllllllllllllIllIllIIlIlIIlIl).intValue() : 0;
        llllllllllllllllIllIllIIlIlIlIlI.options = llllllllllllllllIllIllIIlIlIIllI;
        llllllllllllllllIllIllIIlIlIlIlI.encoding = (String)llllllllllllllllIllIllIIlIlIIllI.get("string-encoding");
        if (llllllllllllllllIllIllIIlIlIlIlI.encoding == null) {
            llllllllllllllllIllIllIIlIlIlIlI.encoding = Native.getDefaultStringEncoding();
        }
        if (Platform.isWindows() && "kernel32".equals(llllllllllllllllIllIllIIlIlIlIlI.libraryName.toLowerCase())) {
            Map<String, Function> llllllllllllllllIllIllIIIIIlIlII = llllllllllllllllIllIllIIlIlIlIlI.functions;
            synchronized (llllllllllllllllIllIllIIIIIlIlII) {
                Function llllllllllllllllIllIllIIlIlIlIll = new Function(llllllllllllllllIllIllIIlIlIlIlI, "GetLastError", 63, llllllllllllllllIllIllIIlIlIlIlI.encoding){

                    @Override
                    Object invoke(Object[] llllllllllllllllllllllIIlIlIllII, Class<?> llllllllllllllllllllllIIlIlIlIll, boolean llllllllllllllllllllllIIlIlIlIlI, int llllllllllllllllllllllIIlIlIlIIl) {
                        return Native.getLastError();
                    }
                    {
                        1 llllllllllllllllllllllIIlIllIIll;
                        super(llllllllllllllllllllllIIlIllIIIl, llllllllllllllllllllllIIlIllIIII, llllllllllllllllllllllIIlIlIllll, llllllllllllllllllllllIIlIlIlllI);
                    }

                    @Override
                    Object invoke(Method llllllllllllllllllllllIIlIlIIlll, Class<?>[] llllllllllllllllllllllIIlIlIIllI, Class<?> llllllllllllllllllllllIIlIlIIlIl, Object[] llllllllllllllllllllllIIlIlIIlII, Map<String, ?> llllllllllllllllllllllIIlIlIIIll) {
                        return Native.getLastError();
                    }
                };
                llllllllllllllllIllIllIIlIlIlIlI.functions.put(NativeLibrary.functionKey("GetLastError", llllllllllllllllIllIllIIlIlIlIlI.callFlags, llllllllllllllllIllIllIIlIlIlIlI.encoding), llllllllllllllllIllIllIIlIlIlIll);
            }
        }
    }

    private static String getMultiArchPath() {
        String llllllllllllllllIllIlIlIlIlIIIII = Platform.ARCH;
        String llllllllllllllllIllIlIlIlIIlllll = Platform.iskFreeBSD() ? "-kfreebsd" : (Platform.isGNU() ? "" : "-linux");
        String llllllllllllllllIllIlIlIlIIllllI = "-gnu";
        if (Platform.isIntel()) {
            llllllllllllllllIllIlIlIlIlIIIII = Platform.is64Bit() ? "x86_64" : "i386";
        } else if (Platform.isPPC()) {
            llllllllllllllllIllIlIlIlIlIIIII = Platform.is64Bit() ? "powerpc64" : "powerpc";
        } else if (Platform.isARM()) {
            llllllllllllllllIllIlIlIlIlIIIII = "arm";
            llllllllllllllllIllIlIlIlIIllllI = "-gnueabi";
        }
        return String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIlIlIlIIIII).append(llllllllllllllllIllIlIlIlIIlllll).append(llllllllllllllllIllIlIlIlIIllllI));
    }

    static String matchFramework(String llllllllllllllllIllIlIllllIllIII) {
        File llllllllllllllllIllIlIllllIlIlll = new File(llllllllllllllllIllIlIllllIllIII);
        if (llllllllllllllllIllIlIllllIlIlll.isAbsolute()) {
            if (llllllllllllllllIllIlIllllIllIII.indexOf(".framework") != -1 && llllllllllllllllIllIlIllllIlIlll.exists()) {
                return llllllllllllllllIllIlIllllIlIlll.getAbsolutePath();
            }
            if ((llllllllllllllllIllIlIllllIlIlll = new File(new File(llllllllllllllllIllIlIllllIlIlll.getParentFile(), String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIllllIlIlll.getName()).append(".framework"))), llllllllllllllllIllIlIllllIlIlll.getName())).exists()) {
                return llllllllllllllllIllIlIllllIlIlll.getAbsolutePath();
            }
        } else {
            String[] llllllllllllllllIllIlIllllIllIlI = new String[]{System.getProperty("user.home"), "", "/System"};
            String llllllllllllllllIllIlIllllIllIIl = llllllllllllllllIllIlIllllIllIII.indexOf(".framework") == -1 ? String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIllllIllIII).append(".framework/").append(llllllllllllllllIllIlIllllIllIII)) : llllllllllllllllIllIlIllllIllIII;
            for (int llllllllllllllllIllIlIllllIllIll = 0; llllllllllllllllIllIlIllllIllIll < llllllllllllllllIllIlIllllIllIlI.length; ++llllllllllllllllIllIlIllllIllIll) {
                String llllllllllllllllIllIlIllllIlllII = String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIllllIllIlI[llllllllllllllllIllIlIllllIllIll]).append("/Library/Frameworks/").append(llllllllllllllllIllIlIllllIllIIl));
                if (!new File(llllllllllllllllIllIlIllllIlllII).exists()) continue;
                return llllllllllllllllIllIlIllllIlllII;
            }
        }
        return null;
    }

    public String toString() {
        NativeLibrary llllllllllllllllIllIlIllIIlllIll;
        return String.valueOf(new StringBuilder().append("Native Library <").append(llllllllllllllllIllIlIllIIlllIll.libraryPath).append("@").append(llllllllllllllllIllIlIllIIlllIll.handle).append(">"));
    }

    public Map<String, ?> getOptions() {
        NativeLibrary llllllllllllllllIllIlIllIlIIllIl;
        return llllllllllllllllIllIlIllIlIIllIl.options;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Function getFunction(String llllllllllllllllIllIlIllIlIlIlIl, int llllllllllllllllIllIlIllIlIlIlII, String llllllllllllllllIllIlIllIlIlIIll) {
        NativeLibrary llllllllllllllllIllIlIllIlIlIllI;
        if (llllllllllllllllIllIlIllIlIlIlIl == null) {
            throw new NullPointerException("Function name may not be null");
        }
        Map<String, Function> llllllllllllllllIllIlIllIlIlIIlI = llllllllllllllllIllIlIllIlIlIllI.functions;
        synchronized (llllllllllllllllIllIlIllIlIlIIlI) {
            String llllllllllllllllIllIlIllIlIlllII = NativeLibrary.functionKey(llllllllllllllllIllIlIllIlIlIlIl, llllllllllllllllIllIlIllIlIlIlII, llllllllllllllllIllIlIllIlIlIIll);
            Function llllllllllllllllIllIlIllIlIllIll = llllllllllllllllIllIlIllIlIlIllI.functions.get(llllllllllllllllIllIlIllIlIlllII);
            if (llllllllllllllllIllIlIllIlIllIll == null) {
                llllllllllllllllIllIlIllIlIllIll = new Function(llllllllllllllllIllIlIllIlIlIllI, llllllllllllllllIllIlIllIlIlIlIl, llllllllllllllllIllIlIllIlIlIlII, llllllllllllllllIllIlIllIlIlIIll);
                llllllllllllllllIllIlIllIlIlIllI.functions.put(llllllllllllllllIllIlIllIlIlllII, llllllllllllllllIllIlIllIlIllIll);
            }
            return llllllllllllllllIllIlIllIlIllIll;
        }
    }

    public static final NativeLibrary getInstance(String llllllllllllllllIllIlIlllIlllIIl) {
        return NativeLibrary.getInstance(llllllllllllllllIllIlIlllIlllIIl, Collections.emptyMap());
    }
}

