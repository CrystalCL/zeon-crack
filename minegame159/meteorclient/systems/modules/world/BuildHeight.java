/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Direction
 *  net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
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
    @EventHandler
    private void onSendPacket(PacketEvent.Send llIlllIIlIlIIIl) {
        if (!(llIlllIIlIlIIIl.packet instanceof PlayerInteractBlockC2SPacket)) {
            return;
        }
        PlayerInteractBlockC2SPacket llIlllIIlIlIIlI = (PlayerInteractBlockC2SPacket)llIlllIIlIlIIIl.packet;
        if (llIlllIIlIlIIlI.getBlockHitResult().getPos().y >= 255.0 && llIlllIIlIlIIlI.getBlockHitResult().getSide() == Direction.UP) {
            ((BlockHitResultAccessor)llIlllIIlIlIIlI.getBlockHitResult()).setSide(Direction.DOWN);
        }
    }

    public BuildHeight() {
        super(Categories.World, "build-height", "Allows you to interact with objects at the build limit.");
        BuildHeight llIlllIIlIlIlll;
    }
}

