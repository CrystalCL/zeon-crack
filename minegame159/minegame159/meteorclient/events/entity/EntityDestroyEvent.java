/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.Entity;

public class EntityDestroyEvent {
    public Entity entity;
    private static final EntityDestroyEvent INSTANCE = new EntityDestroyEvent();

    public static EntityDestroyEvent get(Entity Entity2) {
        EntityDestroyEvent.INSTANCE.entity = Entity2;
        return INSTANCE;
    }
}

