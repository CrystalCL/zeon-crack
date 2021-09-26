/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.mixin.BlockHitResultAccessor;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.util.math.Direction;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;

public class BuildHeight
extends Module {
    public BuildHeight() {
        super(Categories.World, "build-height", "Allows you to interact with objects at the build limit.");
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (!(send.packet instanceof PlayerInteractBlockC2SPacket)) {
            return;
        }
        PlayerInteractBlockC2SPacket PlayerInteractBlockC2SPacket2 = (PlayerInteractBlockC2SPacket)send.packet;
        if (PlayerInteractBlockC2SPacket2.getBlockHitResult().getPos().y >= 255.0 && PlayerInteractBlockC2SPacket2.getBlockHitResult().getSide() == Direction.UP) {
            ((BlockHitResultAccessor)PlayerInteractBlockC2SPacket2.getBlockHitResult()).setSide(Direction.DOWN);
        }
    }
}

