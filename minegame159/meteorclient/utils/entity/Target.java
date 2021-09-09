/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.entity;

public final class Target
extends Enum<Target> {
    private static final /* synthetic */ Target[] $VALUES;
    public static final /* synthetic */ /* enum */ Target Body;
    public static final /* synthetic */ /* enum */ Target Feet;
    public static final /* synthetic */ /* enum */ Target Head;

    public static Target[] values() {
        return (Target[])$VALUES.clone();
    }

    private static /* synthetic */ Target[] $values() {
        return new Target[]{Head, Body, Feet};
    }

    public static Target valueOf(String llllllllllllllllIlIllIIlllllllll) {
        return Enum.valueOf(Target.class, llllllllllllllllIlIllIIlllllllll);
    }

    static {
        Head = new Target();
        Body = new Target();
        Feet = new Target();
        $VALUES = Target.$values();
    }

    private Target() {
        Target llllllllllllllllIlIllIIllllllIIl;
    }
}

