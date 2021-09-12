/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.world;

public final class Dimension
extends Enum<Dimension> {
    public static final /* enum */ Dimension Nether;
    private static final Dimension[] $VALUES;
    public static final /* enum */ Dimension End;
    public static final /* enum */ Dimension Overworld;

    private static Dimension[] $values() {
        return new Dimension[]{Overworld, Nether, End};
    }

    public static Dimension[] values() {
        return (Dimension[])$VALUES.clone();
    }

    public static Dimension valueOf(String string) {
        return Enum.valueOf(Dimension.class, string);
    }

    static {
        Overworld = new Dimension();
        Nether = new Dimension();
        End = new Dimension();
        $VALUES = Dimension.$values();
    }
}

