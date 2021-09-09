/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.fluid.Fluid
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;

public class CanWalkOnFluidEvent {
    public /* synthetic */ boolean walkOnFluid;
    private static final /* synthetic */ CanWalkOnFluidEvent INSTANCE;
    public /* synthetic */ LivingEntity entity;
    public /* synthetic */ Fluid fluid;

    static {
        INSTANCE = new CanWalkOnFluidEvent();
    }

    public static CanWalkOnFluidEvent get(LivingEntity lIlIIIllIllIlIl, Fluid lIlIIIllIllIllI) {
        CanWalkOnFluidEvent.INSTANCE.entity = lIlIIIllIllIlIl;
        CanWalkOnFluidEvent.INSTANCE.fluid = lIlIIIllIllIllI;
        CanWalkOnFluidEvent.INSTANCE.walkOnFluid = false;
        return INSTANCE;
    }

    public CanWalkOnFluidEvent() {
        CanWalkOnFluidEvent lIlIIIllIlllIll;
    }
}

