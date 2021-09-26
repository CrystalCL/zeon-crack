/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.AutoMountBypassDupe;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.world.World;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class MountBypass
extends Module {
    private boolean dontCancel;

    public MountBypass() {
        super(Categories.World, "mount-bypass", "Allows you to bypass the IllegalStacks plugin and put chests on entities.");
    }

    @EventHandler
    public void onSendPacket(PacketEvent.Send send) {
        if (Modules.get().isActive(AutoMountBypassDupe.class)) {
            return;
        }
        if (this.dontCancel) {
            this.dontCancel = false;
            return;
        }
        if (!(send.packet instanceof PlayerInteractEntityC2SPacket)) {
            return;
        }
        PlayerInteractEntityC2SPacket PlayerInteractEntityC2SPacket2 = (PlayerInteractEntityC2SPacket)send.packet;
        if (PlayerInteractEntityC2SPacket2.getType() == PlayerInteractEntityC2SPacket.class_2825.INTERACT_AT && PlayerInteractEntityC2SPacket2.getEntity((World)this.mc.world) instanceof AbstractDonkeyEntity) {
            send.cancel();
        }
    }
}

