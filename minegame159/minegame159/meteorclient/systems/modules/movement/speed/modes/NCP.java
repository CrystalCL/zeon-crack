/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement.speed.modes;

import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.Anchor;
import minegame159.meteorclient.systems.modules.movement.speed.SpeedMode;
import minegame159.meteorclient.systems.modules.movement.speed.SpeedModes;
import minegame159.meteorclient.utils.misc.Vector2;
import minegame159.meteorclient.utils.player.PlayerUtils;

public class NCP
extends SpeedMode {
    private long timer = 0L;

    @Override
    public void onTick() {
        this.distance = Math.sqrt((this.mc.field_1724.method_23317() - this.mc.field_1724.field_6014) * (this.mc.field_1724.method_23317() - this.mc.field_1724.field_6014) + (this.mc.field_1724.method_23321() - this.mc.field_1724.field_5969) * (this.mc.field_1724.method_23321() - this.mc.field_1724.field_5969));
    }

    public NCP() {
        super(SpeedModes.NCP);
    }

    @Override
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        switch (this.stage) {
            case 0: {
                if (PlayerUtils.isMoving()) {
                    ++this.stage;
                    this.speed = (double)1.18f * this.getDefaultSpeed() - 0.01;
                }
            }
            case 1: {
                if (!PlayerUtils.isMoving() || !this.mc.field_1724.method_24828()) break;
                ((IVec3d)playerMoveEvent.movement).setY(this.getHop(0.40123128));
                this.speed *= this.settings.ncpSpeed.get().doubleValue();
                ++this.stage;
                break;
            }
            case 2: {
                this.speed = this.distance - 0.76 * (this.distance - this.getDefaultSpeed());
                ++this.stage;
                break;
            }
            case 3: {
                if (!this.mc.field_1687.method_18026(this.mc.field_1724.method_5829().method_989(0.0, this.mc.field_1724.method_18798().field_1351, 0.0)) || this.mc.field_1724.field_5992 && this.stage > 0) {
                    this.stage = 0;
                }
                this.speed = this.distance - this.distance / 159.0;
            }
        }
        this.speed = Math.max(this.speed, this.getDefaultSpeed());
        if (this.settings.ncpSpeedLimit.get().booleanValue()) {
            if (System.currentTimeMillis() - this.timer > 2500L) {
                this.timer = System.currentTimeMillis();
            }
            this.speed = Math.min(this.speed, System.currentTimeMillis() - this.timer > 1250L ? 0.44 : 0.43);
        }
        Vector2 vector2 = PlayerUtils.transformStrafe(this.speed);
        double d = vector2.x;
        double d2 = vector2.y;
        Anchor anchor = Modules.get().get(Anchor.class);
        if (anchor.isActive() && anchor.controlMovement) {
            d = anchor.deltaX;
            d2 = anchor.deltaZ;
        }
        ((IVec3d)playerMoveEvent.movement).setXZ(d, d2);
    }
}

