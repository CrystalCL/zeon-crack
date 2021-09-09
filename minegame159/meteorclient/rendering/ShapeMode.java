/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.rendering;

public final class ShapeMode
extends Enum<ShapeMode> {
    public static final /* synthetic */ /* enum */ ShapeMode Both;
    public static final /* synthetic */ /* enum */ ShapeMode Lines;
    public static final /* synthetic */ /* enum */ ShapeMode Sides;
    private static final /* synthetic */ ShapeMode[] $VALUES;

    private static /* synthetic */ ShapeMode[] $values() {
        return new ShapeMode[]{Lines, Sides, Both};
    }

    private ShapeMode() {
        ShapeMode llIIIllIlIll;
    }

    public static ShapeMode valueOf(String llIIIllIllll) {
        return Enum.valueOf(ShapeMode.class, llIIIllIllll);
    }

    static {
        Lines = new ShapeMode();
        Sides = new ShapeMode();
        Both = new ShapeMode();
        $VALUES = ShapeMode.$values();
    }

    public static ShapeMode[] values() {
        return (ShapeMode[])$VALUES.clone();
    }
}

