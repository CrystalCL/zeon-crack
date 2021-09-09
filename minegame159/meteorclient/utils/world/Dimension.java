/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.world;

public final class Dimension
extends Enum<Dimension> {
    public static final /* synthetic */ /* enum */ Dimension Nether;
    public static final /* synthetic */ /* enum */ Dimension End;
    private static final /* synthetic */ Dimension[] $VALUES;
    public static final /* synthetic */ /* enum */ Dimension Overworld;

    private static /* synthetic */ Dimension[] $values() {
        return new Dimension[]{Overworld, Nether, End};
    }

    public static Dimension[] values() {
        return (Dimension[])$VALUES.clone();
    }

    static {
        Overworld = new Dimension();
        Nether = new Dimension();
        End = new Dimension();
        $VALUES = Dimension.$values();
    }

    private Dimension() {
        Dimension lIllIIIIlIlII;
    }

    public static Dimension valueOf(String lIllIIIIllIIl) {
        return Enum.valueOf(Dimension.class, lIllIIIIllIIl);
    }
}

