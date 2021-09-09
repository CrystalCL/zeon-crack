/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket
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
    private /* synthetic */ boolean invOpened;

    @Override
    public void onDeactivate() {
        XCarry llllllllllllllllllIIlIllIlIlIIlI;
        if (llllllllllllllllllIIlIllIlIlIIlI.invOpened) {
            llllllllllllllllllIIlIllIlIlIIlI.mc.player.networkHandler.sendPacket((Packet)new CloseHandledScreenC2SPacket(llllllllllllllllllIIlIllIlIlIIlI.mc.player.playerScreenHandler.syncId));
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send llllllllllllllllllIIlIllIlIIlllI) {
        XCarry llllllllllllllllllIIlIllIlIIllIl;
        if (!(llllllllllllllllllIIlIllIlIIlllI.packet instanceof CloseHandledScreenC2SPacket)) {
            return;
        }
        if (((CloseHandledScreenC2SPacketAccessor)llllllllllllllllllIIlIllIlIIlllI.packet).getSyncId() == llllllllllllllllllIIlIllIlIIllIl.mc.player.playerScreenHandler.syncId) {
            llllllllllllllllllIIlIllIlIIllIl.invOpened = true;
            llllllllllllllllllIIlIllIlIIlllI.cancel();
        }
    }

    public XCarry() {
        super(Categories.Player, "XCarry", "Allows you to store four extra items in your crafting grid.");
        XCarry llllllllllllllllllIIlIllIlIllIII;
    }

    @Override
    public void onActivate() {
        llllllllllllllllllIIlIllIlIlIlIl.invOpened = false;
    }
}

