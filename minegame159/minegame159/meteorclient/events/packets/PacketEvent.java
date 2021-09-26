/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.packets;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.network.Packet;

public class PacketEvent
extends Cancellable {
    public Packet<?> packet;

    public static class Receive
    extends PacketEvent {
        private static final Receive INSTANCE = new Receive();

        public static Receive get(Packet<?> Packet2) {
            INSTANCE.setCancelled(false);
            Receive.INSTANCE.packet = Packet2;
            return INSTANCE;
        }
    }

    public static class Sent
    extends PacketEvent {
        private static final Sent INSTANCE = new Sent();

        public static Sent get(Packet<?> Packet2) {
            Sent.INSTANCE.packet = Packet2;
            return INSTANCE;
        }
    }

    public static class Send
    extends PacketEvent {
        private static final Send INSTANCE = new Send();

        public static Send get(Packet<?> Packet2) {
            INSTANCE.setCancelled(false);
            Send.INSTANCE.packet = Packet2;
            return INSTANCE;
        }
    }
}

