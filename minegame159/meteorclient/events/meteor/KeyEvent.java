/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.meteor;

import minegame159.meteorclient.events.Cancellable;
import minegame159.meteorclient.utils.misc.input.KeyAction;

public class KeyEvent
extends Cancellable {
    public /* synthetic */ int key;
    public /* synthetic */ KeyAction action;
    private static final /* synthetic */ KeyEvent INSTANCE;

    public static KeyEvent get(int llllllllllllllllllIlIIllIllIIIIl, KeyAction llllllllllllllllllIlIIllIlIllllI) {
        INSTANCE.setCancelled(false);
        KeyEvent.INSTANCE.key = llllllllllllllllllIlIIllIllIIIIl;
        KeyEvent.INSTANCE.action = llllllllllllllllllIlIIllIlIllllI;
        return INSTANCE;
    }

    static {
        INSTANCE = new KeyEvent();
    }

    public KeyEvent() {
        KeyEvent llllllllllllllllllIlIIllIllIIlIl;
    }
}

