/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

public final class Safety
extends Enum<Safety> {
    public static final /* enum */ Safety Suicide;
    private static final Safety[] $VALUES;
    public static final /* enum */ Safety Safe;

    private static Safety[] $values() {
        return new Safety[]{Safe, Suicide};
    }

    static {
        Safe = new Safety();
        Suicide = new Safety();
        $VALUES = Safety.$values();
    }

    public static Safety valueOf(String string) {
        return Enum.valueOf(Safety.class, string);
    }

    public static Safety[] values() {
        return (Safety[])$VALUES.clone();
    }
}

