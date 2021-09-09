/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.entity.player;

public class JumpVelocityMultiplierEvent {
    private static final /* synthetic */ JumpVelocityMultiplierEvent INSTANCE;
    public /* synthetic */ float multiplier;

    static {
        INSTANCE = new JumpVelocityMultiplierEvent();
    }

    public JumpVelocityMultiplierEvent() {
        JumpVelocityMultiplierEvent lIIllIIIlIIllI;
        lIIllIIIlIIllI.multiplier = 1.0f;
    }

    public static JumpVelocityMultiplierEvent get() {
        JumpVelocityMultiplierEvent.INSTANCE.multiplier = 1.0f;
        return INSTANCE;
    }
}

