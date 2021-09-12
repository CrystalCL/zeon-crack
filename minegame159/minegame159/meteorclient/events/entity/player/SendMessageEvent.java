/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import minegame159.meteorclient.events.Cancellable;

public class SendMessageEvent
extends Cancellable {
    public String msg;
    private static final SendMessageEvent INSTANCE = new SendMessageEvent();

    public static SendMessageEvent get(String string) {
        INSTANCE.setCancelled(false);
        SendMessageEvent.INSTANCE.msg = string;
        return INSTANCE;
    }
}

