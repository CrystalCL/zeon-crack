/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.item.ItemStack;

public class PickItemsEvent {
    public /* synthetic */ ItemStack itemStack;
    public /* synthetic */ int count;
    private static final /* synthetic */ PickItemsEvent INSTANCE;

    public static PickItemsEvent get(ItemStack lllllllllllllllllIlIllIllllllIIl, int lllllllllllllllllIlIllIlllllIlII) {
        PickItemsEvent.INSTANCE.itemStack = lllllllllllllllllIlIllIllllllIIl;
        PickItemsEvent.INSTANCE.count = lllllllllllllllllIlIllIlllllIlII;
        return INSTANCE;
    }

    public PickItemsEvent() {
        PickItemsEvent lllllllllllllllllIlIlllIIIIIIlII;
    }

    static {
        INSTANCE = new PickItemsEvent();
    }
}

