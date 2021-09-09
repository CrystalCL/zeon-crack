/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.entity;

public final class SortPriority
extends Enum<SortPriority> {
    public static final /* synthetic */ /* enum */ SortPriority HighestHealth;
    public static final /* synthetic */ /* enum */ SortPriority HighestDistance;
    public static final /* synthetic */ /* enum */ SortPriority LowestHealth;
    private static final /* synthetic */ SortPriority[] $VALUES;
    public static final /* synthetic */ /* enum */ SortPriority LowestDistance;

    public static SortPriority[] values() {
        return (SortPriority[])$VALUES.clone();
    }

    public static SortPriority valueOf(String lllllllllllllllllIIlIIIIllIlIIll) {
        return Enum.valueOf(SortPriority.class, lllllllllllllllllIIlIIIIllIlIIll);
    }

    private SortPriority() {
        SortPriority lllllllllllllllllIIlIIIIllIIlllI;
    }

    private static /* synthetic */ SortPriority[] $values() {
        return new SortPriority[]{LowestDistance, HighestDistance, LowestHealth, HighestHealth};
    }

    static {
        LowestDistance = new SortPriority();
        HighestDistance = new SortPriority();
        LowestHealth = new SortPriority();
        HighestHealth = new SortPriority();
        $VALUES = SortPriority.$values();
    }
}

