/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.rendering;

public final class DrawMode
extends Enum<DrawMode> {
    public static final /* synthetic */ /* enum */ DrawMode Triangles;
    public static final /* synthetic */ /* enum */ DrawMode Quads;
    public static final /* synthetic */ /* enum */ DrawMode Lines;
    private static final /* synthetic */ DrawMode[] $VALUES;

    public static DrawMode[] values() {
        return (DrawMode[])$VALUES.clone();
    }

    public int toOpenGl() {
        DrawMode lllllllllllllllllIIlllllIIIlllII;
        if (lllllllllllllllllIIlllllIIIlllII == Triangles) {
            return 4;
        }
        if (lllllllllllllllllIIlllllIIIlllII == Quads) {
            return 7;
        }
        return 1;
    }

    public static DrawMode valueOf(String lllllllllllllllllIIlllllIIlIIllI) {
        return Enum.valueOf(DrawMode.class, lllllllllllllllllIIlllllIIlIIllI);
    }

    static {
        Triangles = new DrawMode();
        Lines = new DrawMode();
        Quads = new DrawMode();
        $VALUES = DrawMode.$values();
    }

    private DrawMode() {
        DrawMode lllllllllllllllllIIlllllIIlIIIIl;
    }

    private static /* synthetic */ DrawMode[] $values() {
        return new DrawMode[]{Triangles, Lines, Quads};
    }
}

