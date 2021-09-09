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

public class TookDamageEvent {
    private static final /* synthetic */ TookDamageEvent INSTANCE;
    public /* synthetic */ DamageSource source;
    public /* synthetic */ LivingEntity entity;

    static {
        INSTANCE = new TookDamageEvent();
    }

    public static TookDamageEvent get(LivingEntity lllllllllllllllllIllIIIIIlllIIIl, DamageSource lllllllllllllllllIllIIIIIllIlllI) {
        TookDamageEvent.INSTANCE.entity = lllllllllllllllllIllIIIIIlllIIIl;
        TookDamageEvent.INSTANCE.source = lllllllllllllllllIllIIIIIllIlllI;
        return INSTANCE;
    }

    public TookDamageEvent() {
        TookDamageEvent lllllllllllllllllIllIIIIIlllIlIl;
    }
}

