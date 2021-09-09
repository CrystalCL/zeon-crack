/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.Entity;

public class EntityDestroyEvent {
    private static final /* synthetic */ EntityDestroyEvent INSTANCE;
    public /* synthetic */ Entity entity;

    public static EntityDestroyEvent get(Entity lllllllllllllllllIIIIlIlIIIlIllI) {
        EntityDestroyEvent.INSTANCE.entity = lllllllllllllllllIIIIlIlIIIlIllI;
        return INSTANCE;
    }

    public EntityDestroyEvent() {
        EntityDestroyEvent lllllllllllllllllIIIIlIlIIIllIIl;
    }

    static {
        INSTANCE = new EntityDestroyEvent();
    }
}

