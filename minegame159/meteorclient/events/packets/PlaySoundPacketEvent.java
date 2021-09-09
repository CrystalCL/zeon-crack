/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket
 */
package minegame159.meteorclient.events.packets;

import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;

public class PlaySoundPacketEvent {
    private static final /* synthetic */ PlaySoundPacketEvent INSTANCE;
    public /* synthetic */ PlaySoundS2CPacket packet;

    public static PlaySoundPacketEvent get(PlaySoundS2CPacket lIllIlllllllI) {
        PlaySoundPacketEvent.INSTANCE.packet = lIllIlllllllI;
        return INSTANCE;
    }

    public PlaySoundPacketEvent() {
        PlaySoundPacketEvent lIlllIIIIIIIl;
    }

    static {
        INSTANCE = new PlaySoundPacketEvent();
    }
}

