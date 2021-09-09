/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.item.ItemStack;

public class StoppedUsingItemEvent {
    private static final /* synthetic */ StoppedUsingItemEvent INSTANCE;
    public /* synthetic */ ItemStack itemStack;

    public StoppedUsingItemEvent() {
        StoppedUsingItemEvent llllllllllllllllllIlIlllIIIIlllI;
    }

    public static StoppedUsingItemEvent get(ItemStack llllllllllllllllllIlIlllIIIIlIll) {
        StoppedUsingItemEvent.INSTANCE.itemStack = llllllllllllllllllIlIlllIIIIlIll;
        return INSTANCE;
    }

    static {
        INSTANCE = new StoppedUsingItemEvent();
    }
}

