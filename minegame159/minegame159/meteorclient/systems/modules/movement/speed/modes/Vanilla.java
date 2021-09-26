/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement.speed.modes;

import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.Anchor;
import minegame159.meteorclient.systems.modules.movement.AutoJump;
import minegame159.meteorclient.systems.modules.movement.speed.SpeedMode;
import minegame159.meteorclient.systems.modules.movement.speed.SpeedModes;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;

public class Vanilla
extends SpeedMode {
    public Vanilla() {
        super(SpeedModes.Vanilla);
    }

    @Override
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        Anchor anchor;
        Vec3d Vec3d2 = PlayerUtils.getHorizontalVelocity(this.settings.speed.get());
        double d = Vec3d2.getX();
        double d2 = Vec3d2.getZ();
        if (this.settings.applySpeedPotions.get().booleanValue() && this.mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            double d3 = (double)(this.mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1) * 0.205;
            d += d * d3;
            d2 += d2 * d3;
        }
        if ((anchor = Modules.get().get(Anchor.class)).isActive() && anchor.controlMovement) {
            d = anchor.deltaX;
            d2 = anchor.deltaZ;
        }
        ((IVec3d)playerMoveEvent.movement).set(d, playerMoveEvent.movement.y, d2);
    }

    @Override
    public void onTick() {
        if (this.settings.jump.get().booleanValue()) {
            if (!this.mc.player.isOnGround() || this.mc.player.isSneaking() || !this.jump()) {
                return;
            }
            if (this.settings.jumpMode.get() == AutoJump.Mode.Jump) {
                this.mc.player.jump();
            } else {
                ((IVec3d)this.mc.player.getVelocity()).setY(this.settings.hopHeight.get());
            }
        }
    }

    private boolean jump() {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$movement$AutoJump$JumpWhen[this.settings.jumpIf.get().ordinal()]) {
            case 1: {
                return PlayerUtils.isSprinting();
            }
            case 2: {
                return PlayerUtils.isMoving();
            }
            case 3: {
                return true;
            }
        }
        return false;
    }
}

