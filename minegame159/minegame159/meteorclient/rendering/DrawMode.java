/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.rendering;

public final class DrawMode
extends Enum<DrawMode> {
    public static final /* enum */ DrawMode Triangles = new DrawMode();
    public static final /* enum */ DrawMode Quads;
    private static final DrawMode[] $VALUES;
    public static final /* enum */ DrawMode Lines;

    public int toOpenGl() {
        if (this == Triangles) {
            return 4;
        }
        if (this == Quads) {
            return 7;
        }
        return 1;
    }

    public static DrawMode[] values() {
        return (DrawMode[])$VALUES.clone();
    }

    public static DrawMode valueOf(String string) {
        return Enum.valueOf(DrawMode.class, string);
    }

    private static DrawMode[] $values() {
        return new DrawMode[]{Triangles, Lines, Quads};
    }

    static {
        Lines = new DrawMode();
        Quads = new DrawMode();
        $VALUES = DrawMode.$values();
    }
}

