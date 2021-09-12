/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render;

public final class AlignmentX
extends Enum<AlignmentX> {
    public static final /* enum */ AlignmentX Center;
    private static final AlignmentX[] $VALUES;
    public static final /* enum */ AlignmentX Right;
    public static final /* enum */ AlignmentX Left;

    private static AlignmentX[] $values() {
        return new AlignmentX[]{Left, Center, Right};
    }

    public static AlignmentX[] values() {
        return (AlignmentX[])$VALUES.clone();
    }

    public static AlignmentX valueOf(String string) {
        return Enum.valueOf(AlignmentX.class, string);
    }

    static {
        Left = new AlignmentX();
        Center = new AlignmentX();
        Right = new AlignmentX();
        $VALUES = AlignmentX.$values();
    }
}

