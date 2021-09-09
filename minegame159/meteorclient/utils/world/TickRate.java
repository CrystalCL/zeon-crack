/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket
 */
package minegame159.meteorclient.utils.world;

import java.util.Arrays;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.GameJoinedEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

public class TickRate {
    private /* synthetic */ long timeLastTimeUpdate;
    public static /* synthetic */ TickRate INSTANCE;
    private final /* synthetic */ float[] tickRates;
    private /* synthetic */ int nextIndex;
    private /* synthetic */ long timeGameJoined;

    private TickRate() {
        TickRate llllllllllllllllllIlllIIlllIlIlI;
        llllllllllllllllllIlllIIlllIlIlI.tickRates = new float[20];
        llllllllllllllllllIlllIIlllIlIlI.nextIndex = 0;
        llllllllllllllllllIlllIIlllIlIlI.timeLastTimeUpdate = -1L;
        MeteorClient.EVENT_BUS.subscribe(llllllllllllllllllIlllIIlllIlIlI);
    }

    static {
        INSTANCE = new TickRate();
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive llllllllllllllllllIlllIIlllIIIIl) {
        if (llllllllllllllllllIlllIIlllIIIIl.packet instanceof WorldTimeUpdateS2CPacket) {
            TickRate llllllllllllllllllIlllIIlllIIlII;
            if (llllllllllllllllllIlllIIlllIIlII.timeLastTimeUpdate != -1L) {
                float llllllllllllllllllIlllIIlllIIlIl = (float)(System.currentTimeMillis() - llllllllllllllllllIlllIIlllIIlII.timeLastTimeUpdate) / 1000.0f;
                llllllllllllllllllIlllIIlllIIlII.tickRates[llllllllllllllllllIlllIIlllIIlII.nextIndex % llllllllllllllllllIlllIIlllIIlII.tickRates.length] = Utils.clamp(20.0f / llllllllllllllllllIlllIIlllIIlIl, 0.0f, 20.0f);
                ++llllllllllllllllllIlllIIlllIIlII.nextIndex;
            }
            llllllllllllllllllIlllIIlllIIlII.timeLastTimeUpdate = System.currentTimeMillis();
        }
    }

    public float getTickRate() {
        TickRate llllllllllllllllllIlllIIllIlIIII;
        if (!Utils.canUpdate()) {
            return 0.0f;
        }
        if (System.currentTimeMillis() - llllllllllllllllllIlllIIllIlIIII.timeGameJoined < 4000L) {
            return 20.0f;
        }
        float llllllllllllllllllIlllIIllIlIIlI = 0.0f;
        float llllllllllllllllllIlllIIllIlIIIl = 0.0f;
        for (float llllllllllllllllllIlllIIllIlIlII : llllllllllllllllllIlllIIllIlIIII.tickRates) {
            if (!(llllllllllllllllllIlllIIllIlIlII > 0.0f)) continue;
            llllllllllllllllllIlllIIllIlIIIl += llllllllllllllllllIlllIIllIlIlII;
            llllllllllllllllllIlllIIllIlIIlI += 1.0f;
        }
        return Utils.clamp(llllllllllllllllllIlllIIllIlIIIl / llllllllllllllllllIlllIIllIlIIlI, 0.0f, 20.0f);
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent llllllllllllllllllIlllIIllIlllIl) {
        TickRate llllllllllllllllllIlllIIllIllllI;
        Arrays.fill(llllllllllllllllllIlllIIllIllllI.tickRates, 0.0f);
        llllllllllllllllllIlllIIllIllllI.nextIndex = 0;
        llllllllllllllllllIlllIIllIllllI.timeLastTimeUpdate = -1L;
        llllllllllllllllllIlllIIllIllllI.timeGameJoined = System.currentTimeMillis();
    }

    public float getTimeSinceLastTick() {
        TickRate llllllllllllllllllIlllIIllIIlIII;
        if (System.currentTimeMillis() - llllllllllllllllllIlllIIllIIlIII.timeGameJoined < 4000L) {
            return 0.0f;
        }
        return (float)(System.currentTimeMillis() - llllllllllllllllllIlllIIllIIlIII.timeLastTimeUpdate) / 1000.0f;
    }
}

