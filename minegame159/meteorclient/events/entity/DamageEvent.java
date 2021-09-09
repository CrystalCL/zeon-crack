/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.damage.DamageSource
 *  net.minecraft.entity.LivingEntity
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.LivingEntity;

public class DamageEvent {
    public /* synthetic */ LivingEntity entity;
    public /* synthetic */ DamageSource source;
    private static final /* synthetic */ DamageEvent INSTANCE;

    public DamageEvent() {
        DamageEvent lIIlIllllIIlIll;
    }

    public static DamageEvent get(LivingEntity lIIlIllllIIIlIl, DamageSource lIIlIllllIIIlII) {
        DamageEvent.INSTANCE.entity = lIIlIllllIIIlIl;
        DamageEvent.INSTANCE.source = lIIlIllllIIIlII;
        return INSTANCE;
    }

    static {
        INSTANCE = new DamageEvent();
    }
}

