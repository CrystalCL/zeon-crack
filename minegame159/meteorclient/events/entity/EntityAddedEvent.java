/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.Entity;

public class EntityAddedEvent {
    public /* synthetic */ Entity entity;
    private static final /* synthetic */ EntityAddedEvent INSTANCE;

    static {
        INSTANCE = new EntityAddedEvent();
    }

    public static EntityAddedEvent get(Entity lIllIIlllllllI) {
        EntityAddedEvent.INSTANCE.entity = lIllIIlllllllI;
        return INSTANCE;
    }

    public EntityAddedEvent() {
        EntityAddedEvent lIllIlIIIIIIII;
    }
}

