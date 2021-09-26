/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.Entity;

public class EntityRemovedEvent {
    private static final EntityRemovedEvent INSTANCE = new EntityRemovedEvent();
    public Entity entity;

    public static EntityRemovedEvent get(Entity Entity2) {
        EntityRemovedEvent.INSTANCE.entity = Entity2;
        return INSTANCE;
    }
}

