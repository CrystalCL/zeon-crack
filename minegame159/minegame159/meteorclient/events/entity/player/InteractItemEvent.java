/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;

public class InteractItemEvent {
    public Hand hand;
    private static final InteractItemEvent INSTANCE = new InteractItemEvent();
    public ActionResult toReturn;

    public static InteractItemEvent get(Hand Hand2) {
        InteractItemEvent.INSTANCE.hand = Hand2;
        InteractItemEvent.INSTANCE.toReturn = null;
        return INSTANCE;
    }
}

