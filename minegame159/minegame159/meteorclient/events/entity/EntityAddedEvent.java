/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.Entity;

public class EntityAddedEvent {
    private static final EntityAddedEvent INSTANCE = new EntityAddedEvent();
    public Entity entity;

    public static EntityAddedEvent get(Entity Entity2) {
        EntityAddedEvent.INSTANCE.entity = Entity2;
        return INSTANCE;
    }
}

