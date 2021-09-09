/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.render;

public final class AlignmentY
extends Enum<AlignmentY> {
    public static final /* synthetic */ /* enum */ AlignmentY Center;
    private static final /* synthetic */ AlignmentY[] $VALUES;
    public static final /* synthetic */ /* enum */ AlignmentY Top;
    public static final /* synthetic */ /* enum */ AlignmentY Bottom;

    public static AlignmentY[] values() {
        return (AlignmentY[])$VALUES.clone();
    }

    private AlignmentY() {
        AlignmentY lllllllllllllllllllIllllIlIIlIII;
    }

    private static /* synthetic */ AlignmentY[] $values() {
        return new AlignmentY[]{Top, Center, Bottom};
    }

    public static AlignmentY valueOf(String lllllllllllllllllllIllllIlIIllIl) {
        return Enum.valueOf(AlignmentY.class, lllllllllllllllllllIllllIlIIllIl);
    }

    static {
        Top = new AlignmentY();
        Center = new AlignmentY();
        Bottom = new AlignmentY();
        $VALUES = AlignmentY.$values();
    }
}

