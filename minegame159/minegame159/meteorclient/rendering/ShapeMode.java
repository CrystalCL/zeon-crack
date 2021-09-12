/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.rendering;

public final class ShapeMode
extends Enum<ShapeMode> {
    private static final ShapeMode[] $VALUES;
    public static final /* enum */ ShapeMode Sides;
    public static final /* enum */ ShapeMode Lines;
    public static final /* enum */ ShapeMode Both;

    public static ShapeMode[] values() {
        return (ShapeMode[])$VALUES.clone();
    }

    private static ShapeMode[] $values() {
        return new ShapeMode[]{Lines, Sides, Both};
    }

    public static ShapeMode valueOf(String string) {
        return Enum.valueOf(ShapeMode.class, string);
    }

    static {
        Lines = new ShapeMode();
        Sides = new ShapeMode();
        Both = new ShapeMode();
        $VALUES = ShapeMode.$values();
    }
}

