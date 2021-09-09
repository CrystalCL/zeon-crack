/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.entity.player;

import minegame159.meteorclient.events.Cancellable;

public class SendMessageEvent
extends Cancellable {
    private static final /* synthetic */ SendMessageEvent INSTANCE;
    public /* synthetic */ String msg;

    public SendMessageEvent() {
        SendMessageEvent lllIIIlIlIIlll;
    }

    public static SendMessageEvent get(String lllIIIlIlIIIll) {
        INSTANCE.setCancelled(false);
        SendMessageEvent.INSTANCE.msg = lllIIIlIlIIIll;
        return INSTANCE;
    }

    static {
        INSTANCE = new SendMessageEvent();
    }
}

