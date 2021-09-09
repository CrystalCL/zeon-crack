/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.util.ActionResult
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;

public class InteractItemEvent {
    private static final /* synthetic */ InteractItemEvent INSTANCE;
    public /* synthetic */ Hand hand;
    public /* synthetic */ ActionResult toReturn;

    public InteractItemEvent() {
        InteractItemEvent llllllllllllllllllllIIlIIlllllll;
    }

    public static InteractItemEvent get(Hand llllllllllllllllllllIIlIIlllllII) {
        InteractItemEvent.INSTANCE.hand = llllllllllllllllllllIIlIIlllllII;
        InteractItemEvent.INSTANCE.toReturn = null;
        return INSTANCE;
    }

    static {
        INSTANCE = new InteractItemEvent();
    }
}

