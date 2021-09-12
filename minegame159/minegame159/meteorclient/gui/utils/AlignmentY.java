/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.utils;

public final class AlignmentY
extends Enum<AlignmentY> {
    public static final /* enum */ AlignmentY Top = new AlignmentY();
    private static final AlignmentY[] $VALUES;
    public static final /* enum */ AlignmentY Bottom;
    public static final /* enum */ AlignmentY Center;

    private static AlignmentY[] $values() {
        return new AlignmentY[]{Top, Center, Bottom};
    }

    static {
        Center = new AlignmentY();
        Bottom = new AlignmentY();
        $VALUES = AlignmentY.$values();
    }

    public static AlignmentY valueOf(String string) {
        return Enum.valueOf(AlignmentY.class, string);
    }

    public static AlignmentY[] values() {
        return (AlignmentY[])$VALUES.clone();
    }
}

