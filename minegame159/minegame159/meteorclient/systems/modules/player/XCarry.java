/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.mixin.CloseHandledScreenC2SPacketAccessor;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

public class XCarry
extends Module {
    private boolean invOpened;

    public XCarry() {
        super(Categories.Player, "XCarry", "Allows you to store four extra items in your crafting grid.");
    }

    @Override
    public void onDeactivate() {
        if (this.invOpened) {
            this.mc.player.networkHandler.sendPacket((Packet)new CloseHandledScreenC2SPacket(this.mc.player.playerScreenHandler.syncId));
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (!(send.packet instanceof CloseHandledScreenC2SPacket)) {
            return;
        }
        if (((CloseHandledScreenC2SPacketAccessor)send.packet).getSyncId() == this.mc.player.playerScreenHandler.syncId) {
            this.invOpened = true;
            send.cancel();
        }
    }

    @Override
    public void onActivate() {
        this.invOpened = false;
    }
}

