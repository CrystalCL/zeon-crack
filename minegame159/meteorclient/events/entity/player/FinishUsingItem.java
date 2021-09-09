/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.item.ItemStack;

public class FinishUsingItem {
    public /* synthetic */ ItemStack itemStack;
    private static final /* synthetic */ FinishUsingItem INSTANCE;

    static {
        INSTANCE = new FinishUsingItem();
    }

    public static FinishUsingItem get(ItemStack llllllllllllllllIllllIllIllllIll) {
        FinishUsingItem.INSTANCE.itemStack = llllllllllllllllIllllIllIllllIll;
        return INSTANCE;
    }

    public FinishUsingItem() {
        FinishUsingItem llllllllllllllllIllllIllIllllllI;
    }
}

