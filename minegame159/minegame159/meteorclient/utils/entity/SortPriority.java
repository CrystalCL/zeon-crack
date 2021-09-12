/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.entity;

public final class SortPriority
extends Enum<SortPriority> {
    public static final /* enum */ SortPriority HighestDistance;
    public static final /* enum */ SortPriority LowestDistance;
    public static final /* enum */ SortPriority LowestHealth;
    public static final /* enum */ SortPriority HighestHealth;
    private static final SortPriority[] $VALUES;

    private static SortPriority[] $values() {
        return new SortPriority[]{LowestDistance, HighestDistance, LowestHealth, HighestHealth};
    }

    public static SortPriority valueOf(String string) {
        return Enum.valueOf(SortPriority.class, string);
    }

    public static SortPriority[] values() {
        return (SortPriority[])$VALUES.clone();
    }

    static {
        LowestDistance = new SortPriority();
        HighestDistance = new SortPriority();
        LowestHealth = new SortPriority();
        HighestHealth = new SortPriority();
        $VALUES = SortPriority.$values();
    }
}

