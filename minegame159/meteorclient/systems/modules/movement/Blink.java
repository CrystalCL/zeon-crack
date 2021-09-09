/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
 */
package minegame159.meteorclient.systems.modules.movement;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Blink
extends Module {
    private /* synthetic */ int timer;
    private final /* synthetic */ List<PlayerMoveC2SPacket> packets;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private void onSendPacket(PacketEvent.Send lllllllllllllllllllIIIlIIlIllIlI) {
        Blink lllllllllllllllllllIIIlIIlIllIll;
        if (!(lllllllllllllllllllIIIlIIlIllIlI.packet instanceof PlayerMoveC2SPacket)) {
            return;
        }
        lllllllllllllllllllIIIlIIlIllIlI.cancel();
        List<PlayerMoveC2SPacket> lllllllllllllllllllIIIlIIlIllIIl = lllllllllllllllllllIIIlIIlIllIll.packets;
        synchronized (lllllllllllllllllllIIIlIIlIllIIl) {
            PlayerMoveC2SPacket lllllllllllllllllllIIIlIIlIllllI;
            PlayerMoveC2SPacket lllllllllllllllllllIIIlIIlIlllll = (PlayerMoveC2SPacket)lllllllllllllllllllIIIlIIlIllIlI.packet;
            PlayerMoveC2SPacket PlayerMoveC2SPacket2 = lllllllllllllllllllIIIlIIlIllllI = lllllllllllllllllllIIIlIIlIllIll.packets.size() == 0 ? null : lllllllllllllllllllIIIlIIlIllIll.packets.get(lllllllllllllllllllIIIlIIlIllIll.packets.size() - 1);
            if (lllllllllllllllllllIIIlIIlIllllI != null && lllllllllllllllllllIIIlIIlIlllll.isOnGround() == lllllllllllllllllllIIIlIIlIllllI.isOnGround() && lllllllllllllllllllIIIlIIlIlllll.getYaw(-1.0f) == lllllllllllllllllllIIIlIIlIllllI.getYaw(-1.0f) && lllllllllllllllllllIIIlIIlIlllll.getPitch(-1.0f) == lllllllllllllllllllIIIlIIlIllllI.getPitch(-1.0f) && lllllllllllllllllllIIIlIIlIlllll.getX(-1.0) == lllllllllllllllllllIIIlIIlIllllI.getX(-1.0) && lllllllllllllllllllIIIlIIlIlllll.getY(-1.0) == lllllllllllllllllllIIIlIIlIllllI.getY(-1.0) && lllllllllllllllllllIIIlIIlIlllll.getZ(-1.0) == lllllllllllllllllllIIIlIIlIllllI.getZ(-1.0)) {
                return;
            }
            lllllllllllllllllllIIIlIIlIllIll.packets.add(lllllllllllllllllllIIIlIIlIlllll);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onDeactivate() {
        Blink lllllllllllllllllllIIIlIIllIllII;
        List<PlayerMoveC2SPacket> lllllllllllllllllllIIIlIIllIlIll = lllllllllllllllllllIIIlIIllIllII.packets;
        synchronized (lllllllllllllllllllIIIlIIllIlIll) {
            lllllllllllllllllllIIIlIIllIllII.packets.forEach(lllllllllllllllllllIIIlIIlIIllIl -> {
                Blink lllllllllllllllllllIIIlIIlIlIIII;
                lllllllllllllllllllIIIlIIlIlIIII.mc.player.networkHandler.sendPacket((Packet)lllllllllllllllllllIIIlIIlIIllIl);
            });
            lllllllllllllllllllIIIlIIllIllII.packets.clear();
            lllllllllllllllllllIIIlIIllIllII.timer = 0;
        }
    }

    @Override
    public String getInfoString() {
        Blink lllllllllllllllllllIIIlIIlIlIIll;
        return String.format("%.1f", Float.valueOf((float)lllllllllllllllllllIIIlIIlIlIIll.timer / 20.0f));
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllIIIlIIllIIlll) {
        Blink lllllllllllllllllllIIIlIIllIIllI;
        ++lllllllllllllllllllIIIlIIllIIllI.timer;
    }

    public Blink() {
        super(Categories.Movement, "blink", "Allows you to essentially teleport while suspending motion updates.");
        Blink lllllllllllllllllllIIIlIIlllIIIl;
        lllllllllllllllllllIIIlIIlllIIIl.packets = new ArrayList<PlayerMoveC2SPacket>();
        lllllllllllllllllllIIIlIIlllIIIl.timer = 0;
    }
}

