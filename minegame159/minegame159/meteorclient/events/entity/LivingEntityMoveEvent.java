/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class LivingEntityMoveEvent {
    public LivingEntity entity;
    private static final LivingEntityMoveEvent INSTANCE = new LivingEntityMoveEvent();
    public Vec3d movement;

    public static LivingEntityMoveEvent get(LivingEntity LivingEntity2, Vec3d Vec3d2) {
        LivingEntityMoveEvent.INSTANCE.entity = LivingEntity2;
        LivingEntityMoveEvent.INSTANCE.movement = Vec3d2;
        return INSTANCE;
    }
}

