/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket
 */
package minegame159.meteorclient.events.packets;

import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;

public class ContainerSlotUpdateEvent {
    public /* synthetic */ ScreenHandlerSlotUpdateS2CPacket packet;
    private static final /* synthetic */ ContainerSlotUpdateEvent INSTANCE;

    public ContainerSlotUpdateEvent() {
        ContainerSlotUpdateEvent lllllllllllllllllIIIIlIIIlllllIl;
    }

    public static ContainerSlotUpdateEvent get(ScreenHandlerSlotUpdateS2CPacket lllllllllllllllllIIIIlIIIllllIIl) {
        ContainerSlotUpdateEvent.INSTANCE.packet = lllllllllllllllllIIIIlIIIllllIIl;
        return INSTANCE;
    }

    static {
        INSTANCE = new ContainerSlotUpdateEvent();
    }
}

