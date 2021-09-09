/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.Entity;

public class EntityRemovedEvent {
    public /* synthetic */ Entity entity;
    private static final /* synthetic */ EntityRemovedEvent INSTANCE;

    public EntityRemovedEvent() {
        EntityRemovedEvent llllIIlIIllllII;
    }

    static {
        INSTANCE = new EntityRemovedEvent();
    }

    public static EntityRemovedEvent get(Entity llllIIlIIlllIIl) {
        EntityRemovedEvent.INSTANCE.entity = llllIIlIIlllIIl;
        return INSTANCE;
    }
}

