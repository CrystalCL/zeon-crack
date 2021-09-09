/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.util.math.Vec3d
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
    private boolean jump() {
        Vanilla lllIlIllIlllIII;
        switch (lllIlIllIlllIII.settings.jumpIf.get()) {
            case Sprinting: {
                return PlayerUtils.isSprinting();
            }
            case Walking: {
                return PlayerUtils.isMoving();
            }
            case Always: {
                return true;
            }
        }
        return false;
    }

    public Vanilla() {
        super(SpeedModes.Vanilla);
        Vanilla lllIlIlllIlIIIl;
    }

    @Override
    public void onMove(PlayerMoveEvent lllIlIlllIIlIII) {
        Anchor lllIlIlllIIIlII;
        Vanilla lllIlIlllIIlIIl;
        Vec3d lllIlIlllIIIlll = PlayerUtils.getHorizontalVelocity(lllIlIlllIIlIIl.settings.speed.get());
        double lllIlIlllIIIllI = lllIlIlllIIIlll.getX();
        double lllIlIlllIIIlIl = lllIlIlllIIIlll.getZ();
        if (lllIlIlllIIlIIl.settings.applySpeedPotions.get().booleanValue() && lllIlIlllIIlIIl.mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            double lllIlIlllIIlIlI = (double)(lllIlIlllIIlIIl.mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1) * 0.205;
            lllIlIlllIIIllI += lllIlIlllIIIllI * lllIlIlllIIlIlI;
            lllIlIlllIIIlIl += lllIlIlllIIIlIl * lllIlIlllIIlIlI;
        }
        if ((lllIlIlllIIIlII = Modules.get().get(Anchor.class)).isActive() && lllIlIlllIIIlII.controlMovement) {
            lllIlIlllIIIllI = lllIlIlllIIIlII.deltaX;
            lllIlIlllIIIlIl = lllIlIlllIIIlII.deltaZ;
        }
        ((IVec3d)lllIlIlllIIlIII.movement).set(lllIlIlllIIIllI, lllIlIlllIIlIII.movement.y, lllIlIlllIIIlIl);
    }

    @Override
    public void onTick() {
        Vanilla lllIlIllIllllII;
        if (lllIlIllIllllII.settings.jump.get().booleanValue()) {
            if (!lllIlIllIllllII.mc.player.isOnGround() || lllIlIllIllllII.mc.player.isSneaking() || !lllIlIllIllllII.jump()) {
                return;
            }
            if (lllIlIllIllllII.settings.jumpMode.get() == AutoJump.Mode.Jump) {
                lllIlIllIllllII.mc.player.jump();
            } else {
                ((IVec3d)lllIlIllIllllII.mc.player.getVelocity()).setY(lllIlIllIllllII.settings.hopHeight.get());
            }
        }
    }
}

