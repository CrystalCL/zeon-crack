/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.passive.AbstractDonkeyEntity
 *  net.minecraft.world.World
 *  net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket$class_2825
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
    private /* synthetic */ boolean dontCancel;

    @EventHandler
    public void onSendPacket(PacketEvent.Send lIlIllllllIIl) {
        MountBypass lIlIlllllllIl;
        if (Modules.get().isActive(AutoMountBypassDupe.class)) {
            return;
        }
        if (lIlIlllllllIl.dontCancel) {
            lIlIlllllllIl.dontCancel = false;
            return;
        }
        if (!(lIlIllllllIIl.packet instanceof PlayerInteractEntityC2SPacket)) {
            return;
        }
        PlayerInteractEntityC2SPacket lIlIllllllIll = (PlayerInteractEntityC2SPacket)lIlIllllllIIl.packet;
        if (lIlIllllllIll.getType() == PlayerInteractEntityC2SPacket.class_2825.INTERACT_AT && lIlIllllllIll.getEntity((World)lIlIlllllllIl.mc.world) instanceof AbstractDonkeyEntity) {
            lIlIllllllIIl.cancel();
        }
    }

    public MountBypass() {
        super(Categories.World, "mount-bypass", "Allows you to bypass the IllegalStacks plugin and put chests on entities.");
        MountBypass lIllIIIIIIIIl;
    }
}

