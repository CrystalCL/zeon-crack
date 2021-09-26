/*
 * Decompiled with CFR 0.151.
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
    private String[] text;

    @Override
    public void onDeactivate() {
        this.text = null;
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent openScreenEvent) {
        if (!(openScreenEvent.screen instanceof SignEditScreen) || this.text == null) {
            return;
        }
        SignBlockEntity SignBlockEntity2 = ((SignEditScreenAccessor)openScreenEvent.screen).getSign();
        this.mc.player.networkHandler.sendPacket((Packet)new UpdateSignC2SPacket(SignBlockEntity2.getPos(), this.text[0], this.text[1], this.text[2], this.text[3]));
        openScreenEvent.cancel();
    }

    public AutoSign() {
        super(Categories.World, "auto-sign", "Automatically writes signs. The first sign's text will be used.");
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (!(send.packet instanceof UpdateSignC2SPacket)) {
            return;
        }
        this.text = ((UpdateSignC2SPacket)send.packet).getText();
    }
}

