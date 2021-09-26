/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.vehicle.BoatEntity;

public class BoatMoveEvent {
    public BoatEntity boat;
    private static final BoatMoveEvent INSTANCE = new BoatMoveEvent();

    public static BoatMoveEvent get(BoatEntity BoatEntity2) {
        BoatMoveEvent.INSTANCE.boat = BoatEntity2;
        return INSTANCE;
    }
}

