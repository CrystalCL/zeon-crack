/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.utils;

public final class AlignmentX
extends Enum<AlignmentX> {
    public static final /* enum */ AlignmentX Center;
    public static final /* enum */ AlignmentX Left;
    private static final AlignmentX[] $VALUES;
    public static final /* enum */ AlignmentX Right;

    static {
        Left = new AlignmentX();
        Center = new AlignmentX();
        Right = new AlignmentX();
        $VALUES = AlignmentX.$values();
    }

    public static AlignmentX valueOf(String string) {
        return Enum.valueOf(AlignmentX.class, string);
    }

    private static AlignmentX[] $values() {
        return new AlignmentX[]{Left, Center, Right};
    }

    public static AlignmentX[] values() {
        return (AlignmentX[])$VALUES.clone();
    }
}

