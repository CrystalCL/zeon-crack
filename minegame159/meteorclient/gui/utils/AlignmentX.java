/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.utils;

public final class AlignmentX
extends Enum<AlignmentX> {
    public static final /* synthetic */ /* enum */ AlignmentX Center;
    public static final /* synthetic */ /* enum */ AlignmentX Right;
    public static final /* synthetic */ /* enum */ AlignmentX Left;
    private static final /* synthetic */ AlignmentX[] $VALUES;

    public static AlignmentX[] values() {
        return (AlignmentX[])$VALUES.clone();
    }

    public static AlignmentX valueOf(String lllllllllllllllllllIIIIIlllIllll) {
        return Enum.valueOf(AlignmentX.class, lllllllllllllllllllIIIIIlllIllll);
    }

    private static /* synthetic */ AlignmentX[] $values() {
        return new AlignmentX[]{Left, Center, Right};
    }

    private AlignmentX() {
        AlignmentX lllllllllllllllllllIIIIIlllIlIll;
    }

    static {
        Left = new AlignmentX();
        Center = new AlignmentX();
        Right = new AlignmentX();
        $VALUES = AlignmentX.$values();
    }
}

