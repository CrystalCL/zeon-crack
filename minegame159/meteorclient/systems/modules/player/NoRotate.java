/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.mixin.PlayerPositionLookS2CPacketAccessor;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class NoRotate
extends Module {
    @EventHandler
    private void onReceivePacket(PacketEvent.Receive lllllllllllllllllIIllllIIIlIIIlI) {
        if (lllllllllllllllllIIllllIIIlIIIlI.packet instanceof PlayerPositionLookS2CPacket) {
            NoRotate lllllllllllllllllIIllllIIIlIIIll;
            ((PlayerPositionLookS2CPacketAccessor)lllllllllllllllllIIllllIIIlIIIlI.packet).setPitch(lllllllllllllllllIIllllIIIlIIIll.mc.player.pitch);
            ((PlayerPositionLookS2CPacketAccessor)lllllllllllllllllIIllllIIIlIIIlI.packet).setYaw(lllllllllllllllllIIllllIIIlIIIll.mc.player.yaw);
        }
    }

    public NoRotate() {
        super(Categories.Player, "no-rotate", "Attempts to block rotations sent from server to client.");
        NoRotate lllllllllllllllllIIllllIIIlIIllI;
    }
}

