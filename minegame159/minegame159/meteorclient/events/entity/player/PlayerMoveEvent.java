/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity.player;

import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

public class PlayerMoveEvent {
    private static final PlayerMoveEvent INSTANCE = new PlayerMoveEvent();
    public Vec3d movement;
    public MovementType type;

    public static PlayerMoveEvent get(MovementType MovementType2, Vec3d Vec3d2) {
        PlayerMoveEvent.INSTANCE.type = MovementType2;
        PlayerMoveEvent.INSTANCE.movement = Vec3d2;
        return INSTANCE;
    }
}

