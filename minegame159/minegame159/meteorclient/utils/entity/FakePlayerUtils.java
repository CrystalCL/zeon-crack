/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.entity;

import java.util.HashMap;
import java.util.Map;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.FakePlayer;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import net.minecraft.class_310;

public class FakePlayerUtils {
    private static final class_310 mc = class_310.method_1551();
    public static int ID;
    private static final Map<FakePlayerEntity, Integer> players;

    static {
        players = new HashMap<FakePlayerEntity, Integer>();
    }

    public static int getID(FakePlayerEntity fakePlayerEntity) {
        return players.getOrDefault((Object)fakePlayerEntity, 0);
    }

    public static Map<FakePlayerEntity, Integer> getPlayers() {
        return players;
    }

    public static void clearFakePlayers() {
        for (Map.Entry<FakePlayerEntity, Integer> entry : players.entrySet()) {
            entry.getKey().despawn();
        }
        players.clear();
    }

    public static void spawnFakePlayer() {
        FakePlayer fakePlayer = Modules.get().get(FakePlayer.class);
        if (fakePlayer.isActive()) {
            FakePlayerEntity fakePlayerEntity = new FakePlayerEntity(fakePlayer.name.get(), fakePlayer.copyInv.get(), fakePlayer.glowing.get(), fakePlayer.health.get().intValue());
            players.put(fakePlayerEntity, ID);
            ++ID;
        }
    }

    public static void removeFakePlayer(int n) {
        if (Modules.get().isActive(FakePlayer.class)) {
            if (players.isEmpty()) {
                return;
            }
            for (Map.Entry<FakePlayerEntity, Integer> entry : players.entrySet()) {
                if (entry.getValue() != n) continue;
                entry.getKey().despawn();
            }
        }
    }
}

