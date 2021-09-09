/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.MovementType
 *  net.minecraft.util.math.Vec3d
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

public class PlayerMoveEvent {
    public /* synthetic */ MovementType type;
    private static final /* synthetic */ PlayerMoveEvent INSTANCE;
    public /* synthetic */ Vec3d movement;

    public PlayerMoveEvent() {
        PlayerMoveEvent llllllllllllllllllllIllIlllIllII;
    }

    public static PlayerMoveEvent get(MovementType llllllllllllllllllllIllIlllIlIII, Vec3d llllllllllllllllllllIllIlllIIlIl) {
        PlayerMoveEvent.INSTANCE.type = llllllllllllllllllllIllIlllIlIII;
        PlayerMoveEvent.INSTANCE.movement = llllllllllllllllllllIllIlllIIlIl;
        return INSTANCE;
    }

    static {
        INSTANCE = new PlayerMoveEvent();
    }
}

