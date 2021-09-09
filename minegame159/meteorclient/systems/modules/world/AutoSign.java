/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.block.entity.SignBlockEntity
 *  net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket
 *  net.minecraft.client.gui.screen.ingame.SignEditScreen
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.mixin.SignEditScreenAccessor;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;

public class AutoSign
extends Module {
    private /* synthetic */ String[] text;

    @Override
    public void onDeactivate() {
        llllllllllllllllllIllIIlIIlIIllI.text = null;
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send llllllllllllllllllIllIIlIIIlllll) {
        if (!(llllllllllllllllllIllIIlIIIlllll.packet instanceof UpdateSignC2SPacket)) {
            return;
        }
        llllllllllllllllllIllIIlIIlIIIII.text = ((UpdateSignC2SPacket)llllllllllllllllllIllIIlIIIlllll.packet).getText();
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent llllllllllllllllllIllIIlIIIlIlll) {
        AutoSign llllllllllllllllllIllIIlIIIllIII;
        if (!(llllllllllllllllllIllIIlIIIlIlll.screen instanceof SignEditScreen) || llllllllllllllllllIllIIlIIIllIII.text == null) {
            return;
        }
        SignBlockEntity llllllllllllllllllIllIIlIIIllIIl = ((SignEditScreenAccessor)llllllllllllllllllIllIIlIIIlIlll.screen).getSign();
        llllllllllllllllllIllIIlIIIllIII.mc.player.networkHandler.sendPacket((Packet)new UpdateSignC2SPacket(llllllllllllllllllIllIIlIIIllIIl.getPos(), llllllllllllllllllIllIIlIIIllIII.text[0], llllllllllllllllllIllIIlIIIllIII.text[1], llllllllllllllllllIllIIlIIIllIII.text[2], llllllllllllllllllIllIIlIIIllIII.text[3]));
        llllllllllllllllllIllIIlIIIlIlll.cancel();
    }

    public AutoSign() {
        super(Categories.World, "auto-sign", "Automatically writes signs. The first sign's text will be used.");
        AutoSign llllllllllllllllllIllIIlIIlIlIIl;
    }
}

