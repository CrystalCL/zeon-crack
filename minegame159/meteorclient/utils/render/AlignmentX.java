/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.render;

public final class AlignmentX
extends Enum<AlignmentX> {
    public static final /* synthetic */ /* enum */ AlignmentX Center;
    private static final /* synthetic */ AlignmentX[] $VALUES;
    public static final /* synthetic */ /* enum */ AlignmentX Left;
    public static final /* synthetic */ /* enum */ AlignmentX Right;

    public static AlignmentX[] values() {
        return (AlignmentX[])$VALUES.clone();
    }

    static {
        Left = new AlignmentX();
        Center = new AlignmentX();
        Right = new AlignmentX();
        $VALUES = AlignmentX.$values();
    }

    private static /* synthetic */ AlignmentX[] $values() {
        return new AlignmentX[]{Left, Center, Right};
    }

    private AlignmentX() {
        AlignmentX llllllllllllllllllllllIIIIIIlIII;
    }

    public static AlignmentX valueOf(String llllllllllllllllllllllIIIIIIllII) {
        return Enum.valueOf(AlignmentX.class, llllllllllllllllllllllIIIIIIllII);
    }
}

