/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package minegame159.meteorclient.events.packets;

import minegame159.meteorclient.events.Cancellable;
import net.minecraft.network.Packet;

public class PacketEvent
extends Cancellable {
    public /* synthetic */ Packet<?> packet;

    public PacketEvent() {
        PacketEvent llllllllllllllllllllIllIIlIlIIII;
    }

    public static class Sent
    extends PacketEvent {
        private static final /* synthetic */ Sent INSTANCE;

        static {
            INSTANCE = new Sent();
        }

        public static Sent get(Packet<?> llllllllllllllllllIllIlIlIllIllI) {
            Sent.INSTANCE.packet = llllllllllllllllllIllIlIlIllIllI;
            return INSTANCE;
        }

        public Sent() {
            Sent llllllllllllllllllIllIlIlIlllIII;
        }
    }

    public static class Receive
    extends PacketEvent {
        private static final /* synthetic */ Receive INSTANCE;

        public Receive() {
            Receive llllllllllllllllIllllllllIlIIllI;
        }

        public static Receive get(Packet<?> llllllllllllllllIllllllllIlIIIll) {
            INSTANCE.setCancelled(false);
            Receive.INSTANCE.packet = llllllllllllllllIllllllllIlIIIll;
            return INSTANCE;
        }

        static {
            INSTANCE = new Receive();
        }
    }

    public static class Send
    extends PacketEvent {
        private static final /* synthetic */ Send INSTANCE;

        public Send() {
            Send lllllllllllllllllllIIlIIllllllII;
        }

        static {
            INSTANCE = new Send();
        }

        public static Send get(Packet<?> lllllllllllllllllllIIlIIlllllIIl) {
            INSTANCE.setCancelled(false);
            Send.INSTANCE.packet = lllllllllllllllllllIIlIIlllllIIl;
            return INSTANCE;
        }
    }
}

