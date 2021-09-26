/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.LivingEntity;

public class TookDamageEvent {
    public LivingEntity entity;
    public DamageSource source;
    private static final TookDamageEvent INSTANCE = new TookDamageEvent();

    public static TookDamageEvent get(LivingEntity LivingEntity2, DamageSource DamageSource2) {
        TookDamageEvent.INSTANCE.entity = LivingEntity2;
        TookDamageEvent.INSTANCE.source = DamageSource2;
        return INSTANCE;
    }
}

