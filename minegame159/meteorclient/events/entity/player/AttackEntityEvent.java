/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package minegame159.meteorclient.events.entity.player;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.entity.Entity;

public class AttackEntityEvent
extends Cancellable {
    private static final /* synthetic */ AttackEntityEvent INSTANCE;
    public /* synthetic */ Entity entity;

    static {
        INSTANCE = new AttackEntityEvent();
    }

    public AttackEntityEvent() {
        AttackEntityEvent llIlIIllIlllIll;
    }

    public static AttackEntityEvent get(Entity llIlIIllIlllIIl) {
        INSTANCE.setCancelled(false);
        AttackEntityEvent.INSTANCE.entity = llIlIIllIlllIIl;
        return INSTANCE;
    }
}

