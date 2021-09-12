/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.class_1799;

public class DropItemsEvent {
    private static final DropItemsEvent INSTANCE = new DropItemsEvent();
    public class_1799 itemStack;

    public static DropItemsEvent get(class_1799 class_17992) {
        DropItemsEvent.INSTANCE.itemStack = class_17992;
        return INSTANCE;
    }
}

