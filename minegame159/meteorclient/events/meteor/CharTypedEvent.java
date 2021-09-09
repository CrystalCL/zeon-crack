/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.meteor;

import minegame159.meteorclient.events.Cancellable;

public class CharTypedEvent
extends Cancellable {
    private static final /* synthetic */ CharTypedEvent INSTANCE;
    public /* synthetic */ char c;

    public CharTypedEvent() {
        CharTypedEvent lllllllllllllllllIIllllIIIIIlllI;
    }

    static {
        INSTANCE = new CharTypedEvent();
    }

    public static CharTypedEvent get(char lllllllllllllllllIIllllIIIIIlIll) {
        INSTANCE.setCancelled(false);
        CharTypedEvent.INSTANCE.c = lllllllllllllllllIIllllIIIIIlIll;
        return INSTANCE;
    }
}

