/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render;

public final class AlignmentY
extends Enum<AlignmentY> {
    public static final /* enum */ AlignmentY Top = new AlignmentY();
    public static final /* enum */ AlignmentY Bottom;
    private static final AlignmentY[] $VALUES;
    public static final /* enum */ AlignmentY Center;

    static {
        Center = new AlignmentY();
        Bottom = new AlignmentY();
        $VALUES = AlignmentY.$values();
    }

    private static AlignmentY[] $values() {
        return new AlignmentY[]{Top, Center, Bottom};
    }

    public static AlignmentY valueOf(String string) {
        return Enum.valueOf(AlignmentY.class, string);
    }

    public static AlignmentY[] values() {
        return (AlignmentY[])$VALUES.clone();
    }
}

