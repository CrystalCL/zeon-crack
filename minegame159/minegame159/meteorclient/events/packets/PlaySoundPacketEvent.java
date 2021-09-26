/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.packets;

import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;

public class PlaySoundPacketEvent {
    private static final PlaySoundPacketEvent INSTANCE = new PlaySoundPacketEvent();
    public PlaySoundS2CPacket packet;

    public static PlaySoundPacketEvent get(PlaySoundS2CPacket PlaySoundS2CPacket2) {
        PlaySoundPacketEvent.INSTANCE.packet = PlaySoundS2CPacket2;
        return INSTANCE;
    }
}

