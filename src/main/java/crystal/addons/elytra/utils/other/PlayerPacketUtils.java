package crystal.addons.elytra.utils.other;

import meteordevelopment.meteorclient.events.Cancellable;
import net.minecraft.network.Packet;

public class PlayerPacketUtils extends Cancellable {
    public Packet packet;

    public void PacketEvent(Packet packet) {
        packet = packet;
    }
}
