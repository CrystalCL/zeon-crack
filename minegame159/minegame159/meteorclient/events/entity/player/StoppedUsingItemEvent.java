/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.class_1799;

public class StoppedUsingItemEvent {
    private static final StoppedUsingItemEvent INSTANCE = new StoppedUsingItemEvent();
    public class_1799 itemStack;

    public static StoppedUsingItemEvent get(class_1799 class_17992) {
        StoppedUsingItemEvent.INSTANCE.itemStack = class_17992;
        return INSTANCE;
    }
}

