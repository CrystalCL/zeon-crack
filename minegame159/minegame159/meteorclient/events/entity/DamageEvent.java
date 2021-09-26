/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.LivingEntity;

public class DamageEvent {
    public DamageSource source;
    public LivingEntity entity;
    private static final DamageEvent INSTANCE = new DamageEvent();

    public static DamageEvent get(LivingEntity LivingEntity2, DamageSource DamageSource2) {
        DamageEvent.INSTANCE.entity = LivingEntity2;
        DamageEvent.INSTANCE.source = DamageSource2;
        return INSTANCE;
    }
}

