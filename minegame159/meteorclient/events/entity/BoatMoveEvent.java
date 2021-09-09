/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.vehicle.BoatEntity
 */
package minegame159.meteorclient.events.entity;

import net.minecraft.entity.vehicle.BoatEntity;

public class BoatMoveEvent {
    public /* synthetic */ BoatEntity boat;
    private static final /* synthetic */ BoatMoveEvent INSTANCE;

    static {
        INSTANCE = new BoatMoveEvent();
    }

    public BoatMoveEvent() {
        BoatMoveEvent lllllllllllllllllIIlIIIlIlllIIIl;
    }

    public static BoatMoveEvent get(BoatEntity lllllllllllllllllIIlIIIlIllIlllI) {
        BoatMoveEvent.INSTANCE.boat = lllllllllllllllllIIlIIIlIllIlllI;
        return INSTANCE;
    }
}

