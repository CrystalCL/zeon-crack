/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.packets;

import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;

public class ContainerSlotUpdateEvent {
    public ScreenHandlerSlotUpdateS2CPacket packet;
    private static final ContainerSlotUpdateEvent INSTANCE = new ContainerSlotUpdateEvent();

    public static ContainerSlotUpdateEvent get(ScreenHandlerSlotUpdateS2CPacket ScreenHandlerSlotUpdateS2CPacket2) {
        ContainerSlotUpdateEvent.INSTANCE.packet = ScreenHandlerSlotUpdateS2CPacket2;
        return INSTANCE;
    }
}

