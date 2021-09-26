/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.item.ItemStack;

public class FinishUsingItem {
    private static final FinishUsingItem INSTANCE = new FinishUsingItem();
    public ItemStack itemStack;

    public static FinishUsingItem get(ItemStack ItemStack2) {
        FinishUsingItem.INSTANCE.itemStack = ItemStack2;
        return INSTANCE;
    }
}

