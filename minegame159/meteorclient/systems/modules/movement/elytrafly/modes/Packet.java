/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket$class_2849
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
    private final /* synthetic */ Vec3d vec3d;

    @Override
    public void onPacketSend(PacketEvent.Send lIIIIlIIIIIIIlI) {
        if (lIIIIlIIIIIIIlI.packet instanceof PlayerMoveC2SPacket) {
            Packet lIIIIlIIIIIIIll;
            lIIIIlIIIIIIIll.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)lIIIIlIIIIIIIll.mc.player, ClientCommandC2SPacket.class_2849.START_FALL_FLYING));
        }
    }

    @Override
    public void onTick() {
        Packet lIIIIlIIIIIIllI;
        super.onTick();
        if (lIIIIlIIIIIIllI.mc.player.inventory.getArmorStack(2).getItem() != Items.ELYTRA || (double)lIIIIlIIIIIIllI.mc.player.fallDistance <= 0.2 || lIIIIlIIIIIIllI.mc.options.keySneak.isPressed()) {
            return;
        }
        if (lIIIIlIIIIIIllI.mc.options.keyForward.isPressed()) {
            lIIIIlIIIIIIllI.vec3d.add(0.0, 0.0, lIIIIlIIIIIIllI.settings.horizontalSpeed.get().doubleValue());
            lIIIIlIIIIIIllI.vec3d.rotateY(-((float)Math.toRadians(lIIIIlIIIIIIllI.mc.player.yaw)));
        } else if (lIIIIlIIIIIIllI.mc.options.keyBack.isPressed()) {
            lIIIIlIIIIIIllI.vec3d.add(0.0, 0.0, lIIIIlIIIIIIllI.settings.horizontalSpeed.get().doubleValue());
            lIIIIlIIIIIIllI.vec3d.rotateY((float)Math.toRadians(lIIIIlIIIIIIllI.mc.player.yaw));
        }
        if (lIIIIlIIIIIIllI.mc.options.keyJump.isPressed()) {
            lIIIIlIIIIIIllI.vec3d.add(0.0, lIIIIlIIIIIIllI.settings.verticalSpeed.get().doubleValue(), 0.0);
        } else if (!lIIIIlIIIIIIllI.mc.options.keyJump.isPressed()) {
            lIIIIlIIIIIIllI.vec3d.add(0.0, -lIIIIlIIIIIIllI.settings.verticalSpeed.get().doubleValue(), 0.0);
        }
        lIIIIlIIIIIIllI.mc.player.setVelocity(lIIIIlIIIIIIllI.vec3d);
        lIIIIlIIIIIIllI.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)lIIIIlIIIIIIllI.mc.player, ClientCommandC2SPacket.class_2849.START_FALL_FLYING));
        lIIIIlIIIIIIllI.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket(true));
    }

    public Packet() {
        super(ElytraFlightModes.Packet);
        Packet lIIIIlIIIIIllII;
        lIIIIlIIIIIllII.vec3d = new Vec3d(0.0, 0.0, 0.0);
    }

    @Override
    public void onPlayerMove() {
        Packet lIIIIIlllllllIl;
        lIIIIIlllllllIl.mc.player.abilities.flying = true;
        lIIIIIlllllllIl.mc.player.abilities.setFlySpeed(lIIIIIlllllllIl.settings.horizontalSpeed.get().floatValue() / 20.0f);
    }

    @Override
    public void onDeactivate() {
        lIIIIlIIIIIlIIl.mc.player.abilities.flying = false;
        lIIIIlIIIIIlIIl.mc.player.abilities.allowFlying = false;
    }
}

