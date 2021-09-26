/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.item.ItemStack;

public class StoppedUsingItemEvent {
    private static final StoppedUsingItemEvent INSTANCE = new StoppedUsingItemEvent();
    public ItemStack itemStack;

    public static StoppedUsingItemEvent get(ItemStack ItemStack2) {
        StoppedUsingItemEvent.INSTANCE.itemStack = ItemStack2;
        return INSTANCE;
    }
}

