/*
 * Decompiled with CFR 0.151.
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
    private final float[] tickRates = new float[20];
    private long timeGameJoined;
    public static TickRate INSTANCE = new TickRate();
    private int nextIndex = 0;
    private long timeLastTimeUpdate = -1L;

    public float getTickRate() {
        if (!Utils.canUpdate()) {
            return 0.0f;
        }
        if (System.currentTimeMillis() - this.timeGameJoined < 4000L) {
            return 20.0f;
        }
        float f = 0.0f;
        float f2 = 0.0f;
        for (float f3 : this.tickRates) {
            if (!(f3 > 0.0f)) continue;
            f2 += f3;
            f += 1.0f;
            if (2 != 0) continue;
            return 0.0f;
        }
        return Utils.clamp(f2 / f, 0.0f, 20.0f);
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive receive) {
        if (receive.packet instanceof WorldTimeUpdateS2CPacket) {
            if (this.timeLastTimeUpdate != -1L) {
                float f = (float)(System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
                this.tickRates[this.nextIndex % this.tickRates.length] = Utils.clamp(20.0f / f, 0.0f, 20.0f);
                ++this.nextIndex;
            }
            this.timeLastTimeUpdate = System.currentTimeMillis();
        }
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent gameJoinedEvent) {
        Arrays.fill(this.tickRates, 0.0f);
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        this.timeGameJoined = System.currentTimeMillis();
    }

    public float getTimeSinceLastTick() {
        if (System.currentTimeMillis() - this.timeGameJoined < 4000L) {
            return 0.0f;
        }
        return (float)(System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
    }

    private TickRate() {
        MeteorClient.EVENT_BUS.subscribe(this);
    }
}

