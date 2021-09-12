/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.meteor;

import minegame159.meteorclient.events.Cancellable;

public class CharTypedEvent
extends Cancellable {
    private static final CharTypedEvent INSTANCE = new CharTypedEvent();
    public char c;

    public static CharTypedEvent get(char c) {
        INSTANCE.setCancelled(false);
        CharTypedEvent.INSTANCE.c = c;
        return INSTANCE;
    }
}

