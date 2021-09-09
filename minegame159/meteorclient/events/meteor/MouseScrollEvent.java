/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.meteor;

import minegame159.meteorclient.events.Cancellable;

public class MouseScrollEvent
extends Cancellable {
    public /* synthetic */ double value;
    private static final /* synthetic */ MouseScrollEvent INSTANCE;

    static {
        INSTANCE = new MouseScrollEvent();
    }

    public MouseScrollEvent() {
        MouseScrollEvent lIlIIllIlIlllII;
    }

    public static MouseScrollEvent get(double lIlIIllIlIllIII) {
        INSTANCE.setCancelled(false);
        MouseScrollEvent.INSTANCE.value = lIlIIllIlIllIII;
        return INSTANCE;
    }
}

