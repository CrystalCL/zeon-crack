/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement.elytrafly.modes;

import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.systems.modules.movement.elytrafly.ElytraFlightMode;
import minegame159.meteorclient.systems.modules.movement.elytrafly.ElytraFlightModes;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class Packet
extends ElytraFlightMode {
    private final Vec3d vec3d = new Vec3d(0.0, 0.0, 0.0);

    @Override
    public void onPlayerMove() {
        this.mc.player.abilities.flying = true;
        this.mc.player.abilities.setFlySpeed(this.settings.horizontalSpeed.get().floatValue() / 20.0f);
    }

    public Packet() {
        super(ElytraFlightModes.Packet);
    }

    @Override
    public void onPacketSend(PacketEvent.Send send) {
        if (send.packet instanceof PlayerMoveC2SPacket) {
            this.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.START_FALL_FLYING));
        }
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.mc.player.inventory.getArmorStack(2).getItem() != Items.ELYTRA || (double)this.mc.player.fallDistance <= 0.2 || this.mc.options.keySneak.isPressed()) {
            return;
        }
        if (this.mc.options.keyForward.isPressed()) {
            this.vec3d.add(0.0, 0.0, this.settings.horizontalSpeed.get().doubleValue());
            this.vec3d.rotateY(-((float)Math.toRadians(this.mc.player.yaw)));
        } else if (this.mc.options.keyBack.isPressed()) {
            this.vec3d.add(0.0, 0.0, this.settings.horizontalSpeed.get().doubleValue());
            this.vec3d.rotateY((float)Math.toRadians(this.mc.player.yaw));
        }
        if (this.mc.options.keyJump.isPressed()) {
            this.vec3d.add(0.0, this.settings.verticalSpeed.get().doubleValue(), 0.0);
        } else if (!this.mc.options.keyJump.isPressed()) {
            this.vec3d.add(0.0, -this.settings.verticalSpeed.get().doubleValue(), 0.0);
        }
        this.mc.player.setVelocity(this.vec3d);
        this.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.START_FALL_FLYING));
        this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket(true));
    }

    @Override
    public void onDeactivate() {
        this.mc.player.abilities.flying = false;
        this.mc.player.abilities.allowFlying = false;
    }
}

