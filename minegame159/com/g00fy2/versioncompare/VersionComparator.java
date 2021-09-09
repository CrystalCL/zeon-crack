/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 */
package com.g00fy2.versioncompare;

import java.util.List;
import javax.annotation.Nonnull;

final class VersionComparator {
    private static final /* synthetic */ String SNAPSHOT_STRING;
    static final /* synthetic */ int MINOR;
    private static final /* synthetic */ String ALPHA_STRING;
    private static final /* synthetic */ int UNKNOWN;
    private static final /* synthetic */ String RC_STRING;
    private static final /* synthetic */ int BETA;
    private static final /* synthetic */ String BETA_STRING;
    private static final /* synthetic */ int ALPHA;
    private static final /* synthetic */ int SNAPSHOT;
    private static final /* synthetic */ int RC;
    private static final /* synthetic */ String PRE_STRING;
    static final /* synthetic */ int PATCH;
    private static final /* synthetic */ int PRE_ALPHA;
    static final /* synthetic */ int MAJOR;

    static boolean startsNumeric(@Nonnull String llllllllllllllllIlllIIllIlIIllIl) {
        return (llllllllllllllllIlllIIllIlIIllIl = llllllllllllllllIlllIIllIlIIllIl.trim()).length() > 0 && Character.isDigit(llllllllllllllllIlllIIllIlIIllIl.charAt(0));
    }

    static boolean isNumeric(@Nonnull CharSequence llllllllllllllllIlllIIllIlIIIlII) {
        int llllllllllllllllIlllIIllIlIIIIll = llllllllllllllllIlllIIllIlIIIlII.length();
        if (llllllllllllllllIlllIIllIlIIIIll > 0) {
            for (int llllllllllllllllIlllIIllIlIIIlIl = 0; llllllllllllllllIlllIIllIlIIIlIl < llllllllllllllllIlllIIllIlIIIIll; ++llllllllllllllllIlllIIllIlIIIlIl) {
                if (Character.isDigit(llllllllllllllllIlllIIllIlIIIlII.charAt(llllllllllllllllIlllIIllIlIIIlIl))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean containsNumeric(@Nonnull CharSequence llllllllllllllllIlllIIllIIlllIIl) {
        int llllllllllllllllIlllIIllIIlllIlI = llllllllllllllllIlllIIllIIlllIIl.length();
        if (llllllllllllllllIlllIIllIIlllIlI > 0) {
            for (int llllllllllllllllIlllIIllIIllllII = 0; llllllllllllllllIlllIIllIIllllII < llllllllllllllllIlllIIllIIlllIlI; ++llllllllllllllllIlllIIllIIllllII) {
                if (!Character.isDigit(llllllllllllllllIlllIIllIIlllIIl.charAt(llllllllllllllllIlllIIllIIllllII))) continue;
                return true;
            }
        }
        return false;
    }

    static int preReleaseVersion(@Nonnull String llllllllllllllllIlllIIllIlIllIll, int llllllllllllllllIlllIIllIlIlllIl) {
        int llllllllllllllllIlllIIllIlIlllll;
        int llllllllllllllllIlllIIllIlIlllII = VersionComparator.indexOfQualifier(llllllllllllllllIlllIIllIlIllIll, llllllllllllllllIlllIIllIlIlllIl);
        if (llllllllllllllllIlllIIllIlIlllII < llllllllllllllllIlllIIllIlIllIll.length() && VersionComparator.containsNumeric(llllllllllllllllIlllIIllIlIllIll.substring(llllllllllllllllIlllIIllIlIlllII, llllllllllllllllIlllIIllIlIlllll = Math.min(llllllllllllllllIlllIIllIlIlllII + 2, llllllllllllllllIlllIIllIlIllIll.length())))) {
            StringBuilder llllllllllllllllIlllIIllIllIIIII = new StringBuilder();
            for (int llllllllllllllllIlllIIllIllIIIIl = llllllllllllllllIlllIIllIlIlllII; llllllllllllllllIlllIIllIllIIIIl < llllllllllllllllIlllIIllIlIllIll.length(); ++llllllllllllllllIlllIIllIllIIIIl) {
                char llllllllllllllllIlllIIllIllIIIlI = llllllllllllllllIlllIIllIlIllIll.charAt(llllllllllllllllIlllIIllIllIIIIl);
                if (Character.isDigit(llllllllllllllllIlllIIllIllIIIlI)) {
                    llllllllllllllllIlllIIllIllIIIII.append(llllllllllllllllIlllIIllIllIIIlI);
                    continue;
                }
                if (llllllllllllllllIlllIIllIllIIIIl != llllllllllllllllIlllIIllIlIlllII) break;
            }
            return VersionComparator.safeParseInt(String.valueOf(llllllllllllllllIlllIIllIllIIIII));
        }
        return 0;
    }

    static int compareSuffix(@Nonnull String llllllllllllllllIlllIIllIlllIlII, @Nonnull String llllllllllllllllIlllIIllIlllIIll) {
        if (llllllllllllllllIlllIIllIlllIlII.length() > 0 || llllllllllllllllIlllIIllIlllIIll.length() > 0) {
            int llllllllllllllllIlllIIllIlllIlIl;
            int llllllllllllllllIlllIIllIlllIllI = VersionComparator.qualifierToNumber(llllllllllllllllIlllIIllIlllIlII);
            if (llllllllllllllllIlllIIllIlllIllI > (llllllllllllllllIlllIIllIlllIlIl = VersionComparator.qualifierToNumber(llllllllllllllllIlllIIllIlllIIll))) {
                return 1;
            }
            if (llllllllllllllllIlllIIllIlllIllI < llllllllllllllllIlllIIllIlllIlIl) {
                return -1;
            }
            if (llllllllllllllllIlllIIllIlllIllI != 5 && llllllllllllllllIlllIIllIlllIllI != 0) {
                int llllllllllllllllIlllIIllIlllIlll;
                int llllllllllllllllIlllIIllIllllIII = VersionComparator.preReleaseVersion(llllllllllllllllIlllIIllIlllIlII, llllllllllllllllIlllIIllIlllIllI);
                if (llllllllllllllllIlllIIllIllllIII > (llllllllllllllllIlllIIllIlllIlll = VersionComparator.preReleaseVersion(llllllllllllllllIlllIIllIlllIIll, llllllllllllllllIlllIIllIlllIlIl))) {
                    return 1;
                }
                if (llllllllllllllllIlllIIllIllllIII < llllllllllllllllIlllIIllIlllIlll) {
                    return -1;
                }
            }
        }
        return 0;
    }

    private static int indexOfQualifier(@Nonnull String llllllllllllllllIlllIIllIlIlIIlI, int llllllllllllllllIlllIIllIlIIllll) {
        if (llllllllllllllllIlllIIllIlIIllll == 4) {
            return llllllllllllllllIlllIIllIlIlIIlI.indexOf("rc") + "rc".length();
        }
        if (llllllllllllllllIlllIIllIlIIllll == 3) {
            return llllllllllllllllIlllIIllIlIlIIlI.indexOf("beta") + "beta".length();
        }
        if (llllllllllllllllIlllIIllIlIIllll == 2 || llllllllllllllllIlllIIllIlIIllll == 1) {
            return llllllllllllllllIlllIIllIlIlIIlI.indexOf("alpha") + "alpha".length();
        }
        return 0;
    }

    VersionComparator() {
        VersionComparator llllllllllllllllIlllIIlllIIlIIIl;
    }

    static int safeParseInt(@Nonnull String llllllllllllllllIlllIIllIlIIlIIl) {
        if (llllllllllllllllIlllIIllIlIIlIIl.length() > 9) {
            llllllllllllllllIlllIIllIlIIlIIl = llllllllllllllllIlllIIllIlIIlIIl.substring(0, 9);
        }
        return Integer.parseInt(llllllllllllllllIlllIIllIlIIlIIl);
    }

    static int compareSubversionNumbers(@Nonnull List<Integer> llllllllllllllllIlllIIlllIIIlIIl, @Nonnull List<Integer> llllllllllllllllIlllIIlllIIIlIII) {
        int llllllllllllllllIlllIIlllIIIIlll = llllllllllllllllIlllIIlllIIIlIIl.size();
        int llllllllllllllllIlllIIlllIIIIllI = llllllllllllllllIlllIIlllIIIlIII.size();
        int llllllllllllllllIlllIIlllIIIIlIl = Math.max(llllllllllllllllIlllIIlllIIIIlll, llllllllllllllllIlllIIlllIIIIllI);
        for (int llllllllllllllllIlllIIlllIIIlIlI = 0; llllllllllllllllIlllIIlllIIIlIlI < llllllllllllllllIlllIIlllIIIIlIl; ++llllllllllllllllIlllIIlllIIIlIlI) {
            if ((llllllllllllllllIlllIIlllIIIlIlI < llllllllllllllllIlllIIlllIIIIlll ? llllllllllllllllIlllIIlllIIIlIIl.get(llllllllllllllllIlllIIlllIIIlIlI) : 0) > (llllllllllllllllIlllIIlllIIIlIlI < llllllllllllllllIlllIIlllIIIIllI ? llllllllllllllllIlllIIlllIIIlIII.get(llllllllllllllllIlllIIlllIIIlIlI) : 0)) {
                return 1;
            }
            if ((llllllllllllllllIlllIIlllIIIlIlI < llllllllllllllllIlllIIlllIIIIlll ? llllllllllllllllIlllIIlllIIIlIIl.get(llllllllllllllllIlllIIlllIIIlIlI) : 0) >= (llllllllllllllllIlllIIlllIIIlIlI < llllllllllllllllIlllIIlllIIIIllI ? llllllllllllllllIlllIIlllIIIlIII.get(llllllllllllllllIlllIIlllIIIlIlI) : 0)) continue;
            return -1;
        }
        return 0;
    }

    static int qualifierToNumber(@Nonnull String llllllllllllllllIlllIIllIllIlIll) {
        if (llllllllllllllllIlllIIllIllIlIll.length() > 0) {
            if ((llllllllllllllllIlllIIllIllIlIll = llllllllllllllllIlllIIllIllIlIll.toLowerCase()).contains("rc")) {
                return 4;
            }
            if (llllllllllllllllIlllIIllIllIlIll.contains("beta")) {
                return 3;
            }
            if (llllllllllllllllIlllIIllIllIlIll.contains("alpha")) {
                if (llllllllllllllllIlllIIllIllIlIll.substring(0, llllllllllllllllIlllIIllIllIlIll.indexOf("alpha")).contains("pre")) {
                    return 1;
                }
                return 2;
            }
            if (llllllllllllllllIlllIIllIllIlIll.contains("snapshot")) {
                return 0;
            }
        }
        return 5;
    }

    static {
        UNKNOWN = 5;
        MAJOR = 0;
        BETA = 3;
        SNAPSHOT_STRING = "snapshot";
        ALPHA_STRING = "alpha";
        ALPHA = 2;
        PRE_STRING = "pre";
        MINOR = 1;
        RC = 4;
        SNAPSHOT = 0;
        RC_STRING = "rc";
        PATCH = 2;
        PRE_ALPHA = 1;
        BETA_STRING = "beta";
    }
}

