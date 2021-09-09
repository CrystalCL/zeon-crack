/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.item.ItemStack;

public class DropItemsEvent {
    public /* synthetic */ ItemStack itemStack;
    private static final /* synthetic */ DropItemsEvent INSTANCE;

    public DropItemsEvent() {
        DropItemsEvent llllIlIlllllllI;
    }

    public static DropItemsEvent get(ItemStack llllIlIlllllIlI) {
        DropItemsEvent.INSTANCE.itemStack = llllIlIlllllIlI;
        return INSTANCE;
    }

    static {
        INSTANCE = new DropItemsEvent();
    }
}

