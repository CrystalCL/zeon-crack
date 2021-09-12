/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.entity;

public final class Target
extends Enum<Target> {
    public static final /* enum */ Target Feet;
    public static final /* enum */ Target Head;
    public static final /* enum */ Target Body;
    private static final Target[] $VALUES;

    private static Target[] $values() {
        return new Target[]{Head, Body, Feet};
    }

    public static Target[] values() {
        return (Target[])$VALUES.clone();
    }

    public static Target valueOf(String string) {
        return Enum.valueOf(Target.class, string);
    }

    static {
        Head = new Target();
        Body = new Target();
        Feet = new Target();
        $VALUES = Target.$values();
    }
}

