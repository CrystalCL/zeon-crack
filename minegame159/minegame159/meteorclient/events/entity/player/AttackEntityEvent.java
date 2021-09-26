/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.entity.Entity;

public class AttackEntityEvent
extends Cancellable {
    public Entity entity;
    private static final AttackEntityEvent INSTANCE = new AttackEntityEvent();

    public static AttackEntityEvent get(Entity Entity2) {
        INSTANCE.setCancelled(false);
        AttackEntityEvent.INSTANCE.entity = Entity2;
        return INSTANCE;
    }
}

