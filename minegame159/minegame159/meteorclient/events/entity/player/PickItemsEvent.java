/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.class_1799;

public class PickItemsEvent {
    private static final PickItemsEvent INSTANCE = new PickItemsEvent();
    public class_1799 itemStack;
    public int count;

    public static PickItemsEvent get(class_1799 class_17992, int n) {
        PickItemsEvent.INSTANCE.itemStack = class_17992;
        PickItemsEvent.INSTANCE.count = n;
        return INSTANCE;
    }
}

