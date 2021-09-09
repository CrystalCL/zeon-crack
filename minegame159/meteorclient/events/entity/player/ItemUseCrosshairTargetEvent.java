/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.hit.HitResult
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.util.hit.HitResult;

public class ItemUseCrosshairTargetEvent {
    private static final /* synthetic */ ItemUseCrosshairTargetEvent INSTANCE;
    public /* synthetic */ HitResult target;

    public ItemUseCrosshairTargetEvent() {
        ItemUseCrosshairTargetEvent lIlllllllIlllIl;
    }

    static {
        INSTANCE = new ItemUseCrosshairTargetEvent();
    }

    public static ItemUseCrosshairTargetEvent get(HitResult lIlllllllIllIlI) {
        ItemUseCrosshairTargetEvent.INSTANCE.target = lIlllllllIllIlI;
        return INSTANCE;
    }
}

