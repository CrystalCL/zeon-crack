/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.item.ItemStack;

public class PickItemsEvent {
    private static final PickItemsEvent INSTANCE = new PickItemsEvent();
    public ItemStack itemStack;
    public int count;

    public static PickItemsEvent get(ItemStack ItemStack2, int n) {
        PickItemsEvent.INSTANCE.itemStack = ItemStack2;
        PickItemsEvent.INSTANCE.count = n;
        return INSTANCE;
    }
}

