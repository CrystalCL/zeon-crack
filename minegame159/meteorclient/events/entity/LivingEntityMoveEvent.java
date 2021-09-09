/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.util.math.Vec3d
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class LivingEntityMoveEvent {
    public /* synthetic */ LivingEntity entity;
    private static final /* synthetic */ LivingEntityMoveEvent INSTANCE;
    public /* synthetic */ Vec3d movement;

    static {
        INSTANCE = new LivingEntityMoveEvent();
    }

    public LivingEntityMoveEvent() {
        LivingEntityMoveEvent lllllllllllllllllllIIlllIIllllIl;
    }

    public static LivingEntityMoveEvent get(LivingEntity lllllllllllllllllllIIlllIIlllIIl, Vec3d lllllllllllllllllllIIlllIIlllIII) {
        LivingEntityMoveEvent.INSTANCE.entity = lllllllllllllllllllIIlllIIlllIIl;
        LivingEntityMoveEvent.INSTANCE.movement = lllllllllllllllllllIIlllIIlllIII;
        return INSTANCE;
    }
}

