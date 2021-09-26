/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.game;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.client.gui.screen.Screen;

public class OpenScreenEvent
extends Cancellable {
    private static final OpenScreenEvent INSTANCE = new OpenScreenEvent();
    public Screen screen;

    public static OpenScreenEvent get(Screen Screen2) {
        INSTANCE.setCancelled(false);
        OpenScreenEvent.INSTANCE.screen = Screen2;
        return INSTANCE;
    }
}

