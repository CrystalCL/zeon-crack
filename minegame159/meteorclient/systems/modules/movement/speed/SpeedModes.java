/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.movement.speed;

public final class SpeedModes
extends Enum<SpeedModes> {
    public static final /* synthetic */ /* enum */ SpeedModes Vanilla;
    private static final /* synthetic */ SpeedModes[] $VALUES;
    public static final /* synthetic */ /* enum */ SpeedModes NCP;

    static {
        NCP = new SpeedModes();
        Vanilla = new SpeedModes();
        $VALUES = SpeedModes.$values();
    }

    private static /* synthetic */ SpeedModes[] $values() {
        return new SpeedModes[]{NCP, Vanilla};
    }

    private SpeedModes() {
        SpeedModes lllIlllIIIIllI;
    }

    public static SpeedModes[] values() {
        return (SpeedModes[])$VALUES.clone();
    }

    public static SpeedModes valueOf(String lllIlllIIIllII) {
        return Enum.valueOf(SpeedModes.class, lllIlllIIIllII);
    }
}

