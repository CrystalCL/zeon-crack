/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.render;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.class_2586;

public class RenderBlockEntityEvent
extends Cancellable {
    public class_2586 blockEntity;
    private static final RenderBlockEntityEvent INSTANCE = new RenderBlockEntityEvent();

    public static RenderBlockEntityEvent get(class_2586 class_25862) {
        INSTANCE.setCancelled(false);
        RenderBlockEntityEvent.INSTANCE.blockEntity = class_25862;
        return INSTANCE;
    }
}

