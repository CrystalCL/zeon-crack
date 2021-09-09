/*
 * Decompiled with CFR 0.150.
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
    private /* synthetic */ long timer;

    @Override
    public void onTick() {
        NCP lIIlIIIIllllIlI;
        lIIlIIIIllllIlI.distance = Math.sqrt((lIIlIIIIllllIlI.mc.player.getX() - lIIlIIIIllllIlI.mc.player.prevX) * (lIIlIIIIllllIlI.mc.player.getX() - lIIlIIIIllllIlI.mc.player.prevX) + (lIIlIIIIllllIlI.mc.player.getZ() - lIIlIIIIllllIlI.mc.player.prevZ) * (lIIlIIIIllllIlI.mc.player.getZ() - lIIlIIIIllllIlI.mc.player.prevZ));
    }

    @Override
    public void onMove(PlayerMoveEvent lIIlIIIlIIIIIII) {
        NCP lIIlIIIlIIIIlll;
        switch (lIIlIIIlIIIIlll.stage) {
            case 0: {
                if (PlayerUtils.isMoving()) {
                    ++lIIlIIIlIIIIlll.stage;
                    lIIlIIIlIIIIlll.speed = (double)1.18f * lIIlIIIlIIIIlll.getDefaultSpeed() - 0.01;
                }
            }
            case 1: {
                if (!PlayerUtils.isMoving() || !lIIlIIIlIIIIlll.mc.player.isOnGround()) break;
                ((IVec3d)lIIlIIIlIIIIIII.movement).setY(lIIlIIIlIIIIlll.getHop(0.40123128));
                lIIlIIIlIIIIlll.speed *= lIIlIIIlIIIIlll.settings.ncpSpeed.get().doubleValue();
                ++lIIlIIIlIIIIlll.stage;
                break;
            }
            case 2: {
                lIIlIIIlIIIIlll.speed = lIIlIIIlIIIIlll.distance - 0.76 * (lIIlIIIlIIIIlll.distance - lIIlIIIlIIIIlll.getDefaultSpeed());
                ++lIIlIIIlIIIIlll.stage;
                break;
            }
            case 3: {
                if (!lIIlIIIlIIIIlll.mc.world.isSpaceEmpty(lIIlIIIlIIIIlll.mc.player.getBoundingBox().offset(0.0, lIIlIIIlIIIIlll.mc.player.getVelocity().y, 0.0)) || lIIlIIIlIIIIlll.mc.player.verticalCollision && lIIlIIIlIIIIlll.stage > 0) {
                    lIIlIIIlIIIIlll.stage = 0;
                }
                lIIlIIIlIIIIlll.speed = lIIlIIIlIIIIlll.distance - lIIlIIIlIIIIlll.distance / 159.0;
            }
        }
        lIIlIIIlIIIIlll.speed = Math.max(lIIlIIIlIIIIlll.speed, lIIlIIIlIIIIlll.getDefaultSpeed());
        if (lIIlIIIlIIIIlll.settings.ncpSpeedLimit.get().booleanValue()) {
            if (System.currentTimeMillis() - lIIlIIIlIIIIlll.timer > 2500L) {
                lIIlIIIlIIIIlll.timer = System.currentTimeMillis();
            }
            lIIlIIIlIIIIlll.speed = Math.min(lIIlIIIlIIIIlll.speed, System.currentTimeMillis() - lIIlIIIlIIIIlll.timer > 1250L ? 0.44 : 0.43);
        }
        Vector2 lIIlIIIlIIIIlIl = PlayerUtils.transformStrafe(lIIlIIIlIIIIlll.speed);
        double lIIlIIIlIIIIlII = lIIlIIIlIIIIlIl.x;
        double lIIlIIIlIIIIIll = lIIlIIIlIIIIlIl.y;
        Anchor lIIlIIIlIIIIIlI = Modules.get().get(Anchor.class);
        if (lIIlIIIlIIIIIlI.isActive() && lIIlIIIlIIIIIlI.controlMovement) {
            lIIlIIIlIIIIlII = lIIlIIIlIIIIIlI.deltaX;
            lIIlIIIlIIIIIll = lIIlIIIlIIIIIlI.deltaZ;
        }
        ((IVec3d)lIIlIIIlIIIIIII.movement).setXZ(lIIlIIIlIIIIlII, lIIlIIIlIIIIIll);
    }

    public NCP() {
        super(SpeedModes.NCP);
        NCP lIIlIIIlIIIllll;
        lIIlIIIlIIIllll.timer = 0L;
    }
}

