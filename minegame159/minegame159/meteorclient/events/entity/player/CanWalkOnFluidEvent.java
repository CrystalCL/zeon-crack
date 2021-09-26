/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;

public class CanWalkOnFluidEvent {
    public boolean walkOnFluid;
    public LivingEntity entity;
    public Fluid fluid;
    private static final CanWalkOnFluidEvent INSTANCE = new CanWalkOnFluidEvent();

    public static CanWalkOnFluidEvent get(LivingEntity LivingEntity2, Fluid Fluid2) {
        CanWalkOnFluidEvent.INSTANCE.entity = LivingEntity2;
        CanWalkOnFluidEvent.INSTANCE.fluid = Fluid2;
        CanWalkOnFluidEvent.INSTANCE.walkOnFluid = false;
        return INSTANCE;
    }
}

