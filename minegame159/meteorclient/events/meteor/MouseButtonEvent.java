/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.meteor;

import minegame159.meteorclient.events.Cancellable;
import minegame159.meteorclient.utils.misc.input.KeyAction;

public class MouseButtonEvent
extends Cancellable {
    public /* synthetic */ int button;
    private static final /* synthetic */ MouseButtonEvent INSTANCE;
    public /* synthetic */ KeyAction action;

    static {
        INSTANCE = new MouseButtonEvent();
    }

    public MouseButtonEvent() {
        MouseButtonEvent lllllIIllIlIl;
    }

    public static MouseButtonEvent get(int lllllIIllIIlI, KeyAction lllllIIllIIIl) {
        INSTANCE.setCancelled(false);
        MouseButtonEvent.INSTANCE.button = lllllIIllIIlI;
        MouseButtonEvent.INSTANCE.action = lllllIIllIIIl;
        return INSTANCE;
    }
}

