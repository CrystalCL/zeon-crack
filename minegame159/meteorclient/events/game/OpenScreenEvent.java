/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.events.game;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.client.gui.screen.Screen;

public class OpenScreenEvent
extends Cancellable {
    private static final /* synthetic */ OpenScreenEvent INSTANCE;
    public /* synthetic */ Screen screen;

    public OpenScreenEvent() {
        OpenScreenEvent lIlllllIlIIIlII;
    }

    static {
        INSTANCE = new OpenScreenEvent();
    }

    public static OpenScreenEvent get(Screen lIlllllIlIIIIIl) {
        INSTANCE.setCancelled(false);
        OpenScreenEvent.INSTANCE.screen = lIlllllIlIIIIIl;
        return INSTANCE;
    }
}

