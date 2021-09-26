/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.util.hit.HitResult;

public class ItemUseCrosshairTargetEvent {
    private static final ItemUseCrosshairTargetEvent INSTANCE = new ItemUseCrosshairTargetEvent();
    public HitResult target;

    public static ItemUseCrosshairTargetEvent get(HitResult HitResult2) {
        ItemUseCrosshairTargetEvent.INSTANCE.target = HitResult2;
        return INSTANCE;
    }
}

