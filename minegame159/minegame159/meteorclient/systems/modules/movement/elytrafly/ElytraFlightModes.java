/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement.elytrafly;

public final class ElytraFlightModes
extends Enum<ElytraFlightModes> {
    private static final ElytraFlightModes[] $VALUES;
    public static final /* enum */ ElytraFlightModes Vanilla;
    public static final /* enum */ ElytraFlightModes Packet;

    private static ElytraFlightModes[] $values() {
        return new ElytraFlightModes[]{Vanilla, Packet};
    }

    public static ElytraFlightModes valueOf(String string) {
        return Enum.valueOf(ElytraFlightModes.class, string);
    }

    public static ElytraFlightModes[] values() {
        return (ElytraFlightModes[])$VALUES.clone();
    }

    static {
        Vanilla = new ElytraFlightModes();
        Packet = new ElytraFlightModes();
        $VALUES = ElytraFlightModes.$values();
    }
}

