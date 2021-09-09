/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.player;

public final class Safety
extends Enum<Safety> {
    public static final /* synthetic */ /* enum */ Safety Suicide;
    public static final /* synthetic */ /* enum */ Safety Safe;
    private static final /* synthetic */ Safety[] $VALUES;

    public static Safety valueOf(String llllllllllllllllllIIllIIllIllIIl) {
        return Enum.valueOf(Safety.class, llllllllllllllllllIIllIIllIllIIl);
    }

    private Safety() {
        Safety llllllllllllllllllIIllIIllIlIIll;
    }

    static {
        Safe = new Safety();
        Suicide = new Safety();
        $VALUES = Safety.$values();
    }

    public static Safety[] values() {
        return (Safety[])$VALUES.clone();
    }

    private static /* synthetic */ Safety[] $values() {
        return new Safety[]{Safe, Suicide};
    }
}

