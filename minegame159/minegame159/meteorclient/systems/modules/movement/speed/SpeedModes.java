/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement.speed;

public final class SpeedModes
extends Enum<SpeedModes> {
    public static final /* enum */ SpeedModes NCP = new SpeedModes();
    private static final SpeedModes[] $VALUES;
    public static final /* enum */ SpeedModes Vanilla;

    private static SpeedModes[] $values() {
        return new SpeedModes[]{NCP, Vanilla};
    }

    static {
        Vanilla = new SpeedModes();
        $VALUES = SpeedModes.$values();
    }

    public static SpeedModes valueOf(String string) {
        return Enum.valueOf(SpeedModes.class, string);
    }

    public static SpeedModes[] values() {
        return (SpeedModes[])$VALUES.clone();
    }
}

