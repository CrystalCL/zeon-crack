/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.item.ItemStack;

public class DropItemsEvent {
    private static final DropItemsEvent INSTANCE = new DropItemsEvent();
    public ItemStack itemStack;

    public static DropItemsEvent get(ItemStack ItemStack2) {
        DropItemsEvent.INSTANCE.itemStack = ItemStack2;
        return INSTANCE;
    }
}

