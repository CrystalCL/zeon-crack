/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 */
package com.g00fy2.versioncompare;

import com.g00fy2.versioncompare.VersionComparator;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Version
implements Comparable<Version> {
    @Nonnull
    private final /* synthetic */ List<Integer> subversionNumbers;
    @Nullable
    private final /* synthetic */ String originalString;
    @Nonnull
    private /* synthetic */ String suffix;
    @Nonnull
    private final /* synthetic */ List<Integer> subversionNumbersWithoutTrailingZeros;

    public boolean isAtLeast(String llllllllllllllllIlIlIlIIIlllIIll, boolean llllllllllllllllIlIlIlIIIlllIIlI) {
        Version llllllllllllllllIlIlIlIIIlllIlll;
        return llllllllllllllllIlIlIlIIIlllIlll.isAtLeast(new Version(llllllllllllllllIlIlIlIIIlllIIll), llllllllllllllllIlIlIlIIIlllIIlI);
    }

    public boolean isLowerThan(String llllllllllllllllIlIlIlIIlIIllllI) {
        Version llllllllllllllllIlIlIlIIlIIlllIl;
        return llllllllllllllllIlIlIlIIlIIlllIl.isLowerThan(new Version(llllllllllllllllIlIlIlIIlIIllllI));
    }

    private void initVersion() {
        Version llllllllllllllllIlIlIlIIIlIlIlll;
        if (llllllllllllllllIlIlIlIIIlIlIlll.originalString != null && VersionComparator.startsNumeric(llllllllllllllllIlIlIlIIIlIlIlll.originalString)) {
            String[] llllllllllllllllIlIlIlIIIlIllIlI = llllllllllllllllIlIlIlIIIlIlIlll.originalString.replaceAll("\\s", "").split("\\.");
            boolean llllllllllllllllIlIlIlIIIlIllIIl = false;
            StringBuilder llllllllllllllllIlIlIlIIIlIllIII = null;
            block0: for (String llllllllllllllllIlIlIlIIIlIllIll : llllllllllllllllIlIlIlIIIlIllIlI) {
                if (llllllllllllllllIlIlIlIIIlIllIIl) {
                    llllllllllllllllIlIlIlIIIlIllIII.append(".");
                    llllllllllllllllIlIlIlIIIlIllIII.append(llllllllllllllllIlIlIlIIIlIllIll);
                    continue;
                }
                if (VersionComparator.isNumeric(llllllllllllllllIlIlIlIIIlIllIll)) {
                    llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbers.add(VersionComparator.safeParseInt(llllllllllllllllIlIlIlIIIlIllIll));
                    continue;
                }
                for (int llllllllllllllllIlIlIlIIIlIlllII = 0; llllllllllllllllIlIlIlIIIlIlllII < llllllllllllllllIlIlIlIIIlIllIll.length(); ++llllllllllllllllIlIlIlIIIlIlllII) {
                    if (Character.isDigit(llllllllllllllllIlIlIlIIIlIllIll.charAt(llllllllllllllllIlIlIlIIIlIlllII))) continue;
                    llllllllllllllllIlIlIlIIIlIllIII = new StringBuilder();
                    if (llllllllllllllllIlIlIlIIIlIlllII > 0) {
                        llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbers.add(VersionComparator.safeParseInt(llllllllllllllllIlIlIlIIIlIllIll.substring(0, llllllllllllllllIlIlIlIIIlIlllII)));
                        llllllllllllllllIlIlIlIIIlIllIII.append(llllllllllllllllIlIlIlIIIlIllIll.substring(llllllllllllllllIlIlIlIIIlIlllII));
                    } else {
                        llllllllllllllllIlIlIlIIIlIllIII.append(llllllllllllllllIlIlIlIIIlIllIll);
                    }
                    llllllllllllllllIlIlIlIIIlIllIIl = true;
                    continue block0;
                }
            }
            llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbersWithoutTrailingZeros.addAll(llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbers);
            while (!llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbersWithoutTrailingZeros.isEmpty() && llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbersWithoutTrailingZeros.lastIndexOf(0) == llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbersWithoutTrailingZeros.size() - 1) {
                llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbersWithoutTrailingZeros.remove(llllllllllllllllIlIlIlIIIlIlIlll.subversionNumbersWithoutTrailingZeros.lastIndexOf(0));
            }
            if (llllllllllllllllIlIlIlIIIlIllIII != null) {
                llllllllllllllllIlIlIlIIIlIlIlll.suffix = String.valueOf(llllllllllllllllIlIlIlIIIlIllIII);
            }
        }
    }

    public int getMinor() {
        Version llllllllllllllllIlIlllIIIIllIlII;
        return llllllllllllllllIlIlllIIIIllIlII.subversionNumbers.size() > 1 ? llllllllllllllllIlIlllIIIIllIlII.subversionNumbers.get(1) : 0;
    }

    public boolean isLowerThan(Version llllllllllllllllIlIlIlIIlIIlIlII) {
        Version llllllllllllllllIlIlIlIIlIIlIlIl;
        int llllllllllllllllIlIlIlIIlIIlIllI = VersionComparator.compareSubversionNumbers(llllllllllllllllIlIlIlIIlIIlIlIl.subversionNumbersWithoutTrailingZeros, llllllllllllllllIlIlIlIIlIIlIlII.subversionNumbersWithoutTrailingZeros);
        if (llllllllllllllllIlIlIlIIlIIlIllI != 0) {
            return llllllllllllllllIlIlIlIIlIIlIllI < 0;
        }
        return VersionComparator.compareSuffix(llllllllllllllllIlIlIlIIlIIlIlIl.suffix, llllllllllllllllIlIlIlIIlIIlIlII.suffix) < 0;
    }

    public boolean isHigherThan(Version llllllllllllllllIlIlllIIIIIllIIl) {
        Version llllllllllllllllIlIlllIIIIIllIlI;
        int llllllllllllllllIlIlllIIIIIllIll = VersionComparator.compareSubversionNumbers(llllllllllllllllIlIlllIIIIIllIlI.subversionNumbersWithoutTrailingZeros, llllllllllllllllIlIlllIIIIIllIIl.subversionNumbersWithoutTrailingZeros);
        if (llllllllllllllllIlIlllIIIIIllIll != 0) {
            return llllllllllllllllIlIlllIIIIIllIll > 0;
        }
        return VersionComparator.compareSuffix(llllllllllllllllIlIlllIIIIIllIlI.suffix, llllllllllllllllIlIlllIIIIIllIIl.suffix) > 0;
    }

    public boolean isAtLeast(Version llllllllllllllllIlIlIlIIIllllIll) {
        Version llllllllllllllllIlIlIlIIIllllllI;
        return llllllllllllllllIlIlIlIIIllllllI.isAtLeast(llllllllllllllllIlIlIlIIIllllIll, false);
    }

    public boolean isEqual(String llllllllllllllllIlIlIlIIlIIIllll) {
        Version llllllllllllllllIlIlIlIIlIIlIIII;
        return llllllllllllllllIlIlIlIIlIIlIIII.isEqual(new Version(llllllllllllllllIlIlIlIIlIIIllll));
    }

    public Version(@Nullable String llllllllllllllllIlIlllIIIlIIIIlI) {
        llllllllllllllllIlIlllIIIlIIIlIl(llllllllllllllllIlIlllIIIlIIIIlI, false);
        Version llllllllllllllllIlIlllIIIlIIIlIl;
    }

    public boolean isAtLeast(String llllllllllllllllIlIlIlIIlIIIIIIl) {
        Version llllllllllllllllIlIlIlIIlIIIIIlI;
        return llllllllllllllllIlIlIlIIlIIIIIlI.isAtLeast(new Version(llllllllllllllllIlIlIlIIlIIIIIIl));
    }

    public final boolean equals(Object llllllllllllllllIlIlIlIIIlIIIIlI) {
        Version llllllllllllllllIlIlIlIIIlIIIIll;
        if (llllllllllllllllIlIlIlIIIlIIIIlI instanceof Version && llllllllllllllllIlIlIlIIIlIIIIll.isEqual((Version)llllllllllllllllIlIlIlIIIlIIIIlI)) {
            return true;
        }
        return super.equals(llllllllllllllllIlIlIlIIIlIIIIlI);
    }

    @Nullable
    public String getOriginalString() {
        Version llllllllllllllllIlIlllIIIIlIIlll;
        return llllllllllllllllIlIlllIIIIlIIlll.originalString;
    }

    public boolean isHigherThan(String llllllllllllllllIlIlllIIIIlIIIll) {
        Version llllllllllllllllIlIlllIIIIlIIlII;
        return llllllllllllllllIlIlllIIIIlIIlII.isHigherThan(new Version(llllllllllllllllIlIlllIIIIlIIIll));
    }

    @Override
    public final int compareTo(@Nonnull Version llllllllllllllllIlIlIlIIIlIIlIII) {
        Version llllllllllllllllIlIlIlIIIlIIlIll;
        if (llllllllllllllllIlIlIlIIIlIIlIll.isEqual(llllllllllllllllIlIlIlIIIlIIlIII)) {
            return 0;
        }
        if (llllllllllllllllIlIlIlIIIlIIlIll.isLowerThan(llllllllllllllllIlIlIlIIIlIIlIII)) {
            return -1;
        }
        return 1;
    }

    public boolean isEqual(Version llllllllllllllllIlIlIlIIlIIIlIIl) {
        Version llllllllllllllllIlIlIlIIlIIIlIII;
        return VersionComparator.compareSubversionNumbers(llllllllllllllllIlIlIlIIlIIIlIII.subversionNumbersWithoutTrailingZeros, llllllllllllllllIlIlIlIIlIIIlIIl.subversionNumbersWithoutTrailingZeros) == 0 && VersionComparator.compareSuffix(llllllllllllllllIlIlIlIIlIIIlIII.suffix, llllllllllllllllIlIlIlIIlIIIlIIl.suffix) == 0;
    }

    public final int hashCode() {
        Version llllllllllllllllIlIlIlIIIIllllII;
        int llllllllllllllllIlIlIlIIIIlllIll = 31;
        int llllllllllllllllIlIlIlIIIIlllIlI = 1;
        llllllllllllllllIlIlIlIIIIlllIlI = 31 * llllllllllllllllIlIlIlIIIIlllIlI + llllllllllllllllIlIlIlIIIIllllII.subversionNumbersWithoutTrailingZeros.hashCode();
        if (llllllllllllllllIlIlIlIIIIllllII.suffix.isEmpty()) {
            return llllllllllllllllIlIlIlIIIIlllIlI;
        }
        int llllllllllllllllIlIlIlIIIIlllIIl = VersionComparator.qualifierToNumber(llllllllllllllllIlIlIlIIIIllllII.suffix);
        int llllllllllllllllIlIlIlIIIIlllIII = VersionComparator.preReleaseVersion(llllllllllllllllIlIlIlIIIIllllII.suffix, llllllllllllllllIlIlIlIIIIlllIIl);
        llllllllllllllllIlIlIlIIIIlllIlI = 31 * llllllllllllllllIlIlIlIIIIlllIlI + llllllllllllllllIlIlIlIIIIlllIIl;
        llllllllllllllllIlIlIlIIIIlllIlI = 31 * llllllllllllllllIlIlIlIIIIlllIlI + llllllllllllllllIlIlIlIIIIlllIII;
        return llllllllllllllllIlIlIlIIIIlllIlI;
    }

    public int getPatch() {
        Version llllllllllllllllIlIlllIIIIllIIIl;
        return llllllllllllllllIlIlllIIIIllIIIl.subversionNumbers.size() > 2 ? llllllllllllllllIlIlllIIIIllIIIl.subversionNumbers.get(2) : 0;
    }

    @Nonnull
    public String getSuffix() {
        Version llllllllllllllllIlIlllIIIIlIlIlI;
        return llllllllllllllllIlIlllIIIIlIlIlI.suffix;
    }

    public Version(@Nullable String llllllllllllllllIlIlllIIIIllllIl, boolean llllllllllllllllIlIlllIIIIllllII) {
        Version llllllllllllllllIlIlllIIIIlllllI;
        llllllllllllllllIlIlllIIIIlllllI.subversionNumbers = new ArrayList<Integer>();
        llllllllllllllllIlIlllIIIIlllllI.subversionNumbersWithoutTrailingZeros = new ArrayList<Integer>();
        llllllllllllllllIlIlllIIIIlllllI.suffix = "";
        if (llllllllllllllllIlIlllIIIIllllII) {
            if (llllllllllllllllIlIlllIIIIllllIl == null) {
                throw new NullPointerException("Argument versionString is null");
            }
            if (!VersionComparator.startsNumeric(llllllllllllllllIlIlllIIIIllllIl)) {
                throw new IllegalArgumentException("Argument versionString is no valid version");
            }
        }
        llllllllllllllllIlIlllIIIIlllllI.originalString = llllllllllllllllIlIlllIIIIllllIl;
        llllllllllllllllIlIlllIIIIlllllI.initVersion();
    }

    public int getMajor() {
        Version llllllllllllllllIlIlllIIIIllIlll;
        return llllllllllllllllIlIlllIIIIllIlll.subversionNumbers.size() > 0 ? llllllllllllllllIlIlllIIIIllIlll.subversionNumbers.get(0) : 0;
    }

    public boolean isAtLeast(Version llllllllllllllllIlIlIlIIIllIllII, boolean llllllllllllllllIlIlIlIIIllIlIll) {
        Version llllllllllllllllIlIlIlIIIllIlIIl;
        int llllllllllllllllIlIlIlIIIllIlIlI = VersionComparator.compareSubversionNumbers(llllllllllllllllIlIlIlIIIllIlIIl.subversionNumbersWithoutTrailingZeros, llllllllllllllllIlIlIlIIIllIllII.subversionNumbersWithoutTrailingZeros);
        if (llllllllllllllllIlIlIlIIIllIlIlI == 0 && !llllllllllllllllIlIlIlIIIllIlIll) {
            return VersionComparator.compareSuffix(llllllllllllllllIlIlIlIIIllIlIIl.suffix, llllllllllllllllIlIlIlIIIllIllII.suffix) >= 0;
        }
        return llllllllllllllllIlIlIlIIIllIlIlI >= 0;
    }

    @Nonnull
    public List<Integer> getSubversionNumbers() {
        Version llllllllllllllllIlIlllIIIIlIllIl;
        return llllllllllllllllIlIlllIIIIlIllIl.subversionNumbers;
    }
}

