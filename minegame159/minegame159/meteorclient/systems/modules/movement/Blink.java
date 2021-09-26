/*
 * Decompiled with CFR 0.151.
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
    private final List<PlayerMoveC2SPacket> packets = new ArrayList<PlayerMoveC2SPacket>();
    private int timer = 0;

    @Override
    public String getInfoString() {
        return String.format("%.1f", Float.valueOf((float)this.timer / 20.0f));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onDeactivate() {
        List<PlayerMoveC2SPacket> list = this.packets;
        synchronized (list) {
            this.packets.forEach(this::lambda$onDeactivate$0);
            this.packets.clear();
            this.timer = 0;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (!(send.packet instanceof PlayerMoveC2SPacket)) {
            return;
        }
        send.cancel();
        List<PlayerMoveC2SPacket> list = this.packets;
        synchronized (list) {
            PlayerMoveC2SPacket PlayerMoveC2SPacket2;
            PlayerMoveC2SPacket PlayerMoveC2SPacket3 = (PlayerMoveC2SPacket)send.packet;
            PlayerMoveC2SPacket PlayerMoveC2SPacket4 = PlayerMoveC2SPacket2 = this.packets.size() == 0 ? null : this.packets.get(this.packets.size() - 1);
            if (PlayerMoveC2SPacket2 != null && PlayerMoveC2SPacket3.isOnGround() == PlayerMoveC2SPacket2.isOnGround() && PlayerMoveC2SPacket3.getYaw(-1.0f) == PlayerMoveC2SPacket2.getYaw(-1.0f) && PlayerMoveC2SPacket3.getPitch(-1.0f) == PlayerMoveC2SPacket2.getPitch(-1.0f) && PlayerMoveC2SPacket3.getX(-1.0) == PlayerMoveC2SPacket2.getX(-1.0) && PlayerMoveC2SPacket3.getY(-1.0) == PlayerMoveC2SPacket2.getY(-1.0) && PlayerMoveC2SPacket3.getZ(-1.0) == PlayerMoveC2SPacket2.getZ(-1.0)) {
                return;
            }
            this.packets.add(PlayerMoveC2SPacket3);
            return;
        }
    }

    public Blink() {
        super(Categories.Movement, "blink", "Allows you to essentially teleport while suspending motion updates.");
    }

    private void lambda$onDeactivate$0(PlayerMoveC2SPacket PlayerMoveC2SPacket2) {
        this.mc.player.networkHandler.sendPacket((Packet)PlayerMoveC2SPacket2);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        ++this.timer;
    }
}

