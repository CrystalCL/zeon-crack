/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.utils;

public final class AlignmentY
extends Enum<AlignmentY> {
    public static final /* synthetic */ /* enum */ AlignmentY Bottom;
    public static final /* synthetic */ /* enum */ AlignmentY Center;
    public static final /* synthetic */ /* enum */ AlignmentY Top;
    private static final /* synthetic */ AlignmentY[] $VALUES;

    public static AlignmentY valueOf(String llllllllllllllllIllllIllIlIIlIIl) {
        return Enum.valueOf(AlignmentY.class, llllllllllllllllIllllIllIlIIlIIl);
    }

    static {
        Top = new AlignmentY();
        Center = new AlignmentY();
        Bottom = new AlignmentY();
        $VALUES = AlignmentY.$values();
    }

    public static AlignmentY[] values() {
        return (AlignmentY[])$VALUES.clone();
    }

    private static /* synthetic */ AlignmentY[] $values() {
        return new AlignmentY[]{Top, Center, Bottom};
    }

    private AlignmentY() {
        AlignmentY llllllllllllllllIllllIllIlIIIlII;
    }
}

