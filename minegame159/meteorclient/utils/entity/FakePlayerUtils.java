/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.utils.entity;

import java.util.HashMap;
import java.util.Map;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.FakePlayer;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import net.minecraft.client.MinecraftClient;

public class FakePlayerUtils {
    private static final /* synthetic */ MinecraftClient mc;
    private static final /* synthetic */ Map<FakePlayerEntity, Integer> players;
    public static /* synthetic */ int ID;

    static {
        mc = MinecraftClient.getInstance();
        players = new HashMap<FakePlayerEntity, Integer>();
    }

    public static void clearFakePlayers() {
        for (Map.Entry<FakePlayerEntity, Integer> llllllllllllllllllIIllIllIlIIllI : players.entrySet()) {
            llllllllllllllllllIIllIllIlIIllI.getKey().despawn();
        }
        players.clear();
    }

    public static void spawnFakePlayer() {
        FakePlayer llllllllllllllllllIIllIllIllIIll = Modules.get().get(FakePlayer.class);
        if (llllllllllllllllllIIllIllIllIIll.isActive()) {
            FakePlayerEntity llllllllllllllllllIIllIllIllIlII = new FakePlayerEntity(llllllllllllllllllIIllIllIllIIll.name.get(), llllllllllllllllllIIllIllIllIIll.copyInv.get(), llllllllllllllllllIIllIllIllIIll.glowing.get(), llllllllllllllllllIIllIllIllIIll.health.get().intValue());
            players.put(llllllllllllllllllIIllIllIllIlII, ID);
            ++ID;
        }
    }

    public static Map<FakePlayerEntity, Integer> getPlayers() {
        return players;
    }

    public static int getID(FakePlayerEntity llllllllllllllllllIIllIllIlIIIIl) {
        return players.getOrDefault((Object)llllllllllllllllllIIllIllIlIIIIl, 0);
    }

    public FakePlayerUtils() {
        FakePlayerUtils llllllllllllllllllIIllIllIlllIII;
    }

    public static void removeFakePlayer(int llllllllllllllllllIIllIllIlIllII) {
        if (Modules.get().isActive(FakePlayer.class)) {
            if (players.isEmpty()) {
                return;
            }
            for (Map.Entry<FakePlayerEntity, Integer> llllllllllllllllllIIllIllIlIllIl : players.entrySet()) {
                if (llllllllllllllllllIIllIllIlIllIl.getValue() != llllllllllllllllllIIllIllIlIllII) continue;
                llllllllllllllllllIIllIllIlIllIl.getKey().despawn();
            }
        }
    }
}

