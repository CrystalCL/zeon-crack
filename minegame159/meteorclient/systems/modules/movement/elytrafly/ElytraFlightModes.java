/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.movement.elytrafly;

public final class ElytraFlightModes
extends Enum<ElytraFlightModes> {
    private static final /* synthetic */ ElytraFlightModes[] $VALUES;
    public static final /* synthetic */ /* enum */ ElytraFlightModes Vanilla;
    public static final /* synthetic */ /* enum */ ElytraFlightModes Packet;

    static {
        Vanilla = new ElytraFlightModes();
        Packet = new ElytraFlightModes();
        $VALUES = ElytraFlightModes.$values();
    }

    public static ElytraFlightModes[] values() {
        return (ElytraFlightModes[])$VALUES.clone();
    }

    private static /* synthetic */ ElytraFlightModes[] $values() {
        return new ElytraFlightModes[]{Vanilla, Packet};
    }

    public static ElytraFlightModes valueOf(String llllIIllllIlIlI) {
        return Enum.valueOf(ElytraFlightModes.class, llllIIllllIlIlI);
    }

    private ElytraFlightModes() {
        ElytraFlightModes llllIIllllIIlII;
    }
}

