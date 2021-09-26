/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.utils.json.UUIDSerializer;
import minegame159.meteorclient.utils.network.HttpUtils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import net.minecraft.entity.player.PlayerEntity;

public class MeteorPlayers {
    private static final Object2BooleanMap<UUID> players;
    private static int checkTimer;
    private static final Type uuidBooleanMapType;
    private static final Gson gson;
    private static final List<UUID> toCheck;

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(MeteorPlayers.class);
    }

    static {
        uuidBooleanMapType = new TypeToken<Map<UUID, Boolean>>(){}.getType();
        players = new Object2BooleanOpenHashMap();
        toCheck = new ArrayList<UUID>();
        gson = new GsonBuilder().registerTypeAdapter(UUID.class, (Object)new UUIDSerializer()).create();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @EventHandler
    private static void onGameLeft(GameLeftEvent gameLeftEvent) {
        Object object = players;
        synchronized (object) {
            players.clear();
        }
        object = toCheck;
        synchronized (object) {
            toCheck.clear();
            return;
        }
    }

    @EventHandler
    private static void onTick(TickEvent.Post post) {
        if (toCheck.isEmpty()) {
            return;
        }
        if (checkTimer >= 10) {
            checkTimer = 0;
            MeteorExecutor.execute(MeteorPlayers::check);
        } else {
            ++checkTimer;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void check() {
        String string;
        Object object = toCheck;
        synchronized (object) {
            string = gson.toJson(toCheck);
            toCheck.clear();
        }
        object = HttpUtils.post("http://meteorclient.com/api/online/usingMeteor", string);
        Map map = (Map)gson.fromJson((Reader)new InputStreamReader((InputStream)object, StandardCharsets.UTF_8), uuidBooleanMapType);
        Object2BooleanMap<UUID> object2BooleanMap = players;
        synchronized (object2BooleanMap) {
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                UUID uUID = (UUID)iterator.next();
                players.put((Object)uUID, (Boolean)map.get(uUID));
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean get(PlayerEntity PlayerEntity2) {
        UUID uUID = PlayerEntity2.getUuid();
        if (players.containsKey((Object)uUID)) {
            return players.getBoolean((Object)uUID);
        }
        List<UUID> list = toCheck;
        synchronized (list) {
            toCheck.add(uUID);
        }
        list = players;
        synchronized (list) {
            players.put((Object)uUID, false);
            return false;
        }
    }
}

