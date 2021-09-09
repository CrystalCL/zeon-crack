/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  net.minecraft.entity.player.PlayerEntity
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
    private static final /* synthetic */ List<UUID> toCheck;
    private static final /* synthetic */ Type uuidBooleanMapType;
    private static /* synthetic */ int checkTimer;
    private static final /* synthetic */ Gson gson;
    private static final /* synthetic */ Object2BooleanMap<UUID> players;

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(MeteorPlayers.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private static void check() {
        void lllllllllllllllllIIllIllllllIllI;
        List<UUID> lllllllllllllllllIIllIllllllIIlI = toCheck;
        synchronized (lllllllllllllllllIIllIllllllIIlI) {
            String lllllllllllllllllIIllIlllllllIII = gson.toJson(toCheck);
            toCheck.clear();
        }
        InputStream lllllllllllllllllIIllIllllllIlIl = HttpUtils.post("http://meteorclient.com/api/online/usingMeteor", (String)lllllllllllllllllIIllIllllllIllI);
        Map lllllllllllllllllIIllIllllllIlII = (Map)gson.fromJson((Reader)new InputStreamReader(lllllllllllllllllIIllIllllllIlIl, StandardCharsets.UTF_8), uuidBooleanMapType);
        Object2BooleanMap<UUID> lllllllllllllllllIIllIllllllIIII = players;
        synchronized (lllllllllllllllllIIllIllllllIIII) {
            for (UUID lllllllllllllllllIIllIllllllIlll : lllllllllllllllllIIllIllllllIlII.keySet()) {
                players.put((Object)lllllllllllllllllIIllIllllllIlll, (Boolean)lllllllllllllllllIIllIllllllIlII.get(lllllllllllllllllIIllIllllllIlll));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private static void onGameLeft(GameLeftEvent lllllllllllllllllIIlllIIIIIIIlII) {
        Object lllllllllllllllllIIlllIIIIIIIIll = players;
        synchronized (lllllllllllllllllIIlllIIIIIIIIll) {
            players.clear();
        }
        lllllllllllllllllIIlllIIIIIIIIll = toCheck;
        synchronized (lllllllllllllllllIIlllIIIIIIIIll) {
            toCheck.clear();
        }
    }

    public MeteorPlayers() {
        MeteorPlayers lllllllllllllllllIIlllIIIIIIlIIl;
    }

    @EventHandler
    private static void onTick(TickEvent.Post lllllllllllllllllIIlllIIIIIIIIII) {
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

    static {
        uuidBooleanMapType = new TypeToken<Map<UUID, Boolean>>(){
            {
                1 llllllllllllllllllIIlIlIIlIIIIll;
            }
        }.getType();
        players = new Object2BooleanOpenHashMap();
        toCheck = new ArrayList<UUID>();
        gson = new GsonBuilder().registerTypeAdapter(UUID.class, (Object)new UUIDSerializer()).create();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean get(PlayerEntity lllllllllllllllllIIllIlllllIIlIl) {
        UUID lllllllllllllllllIIllIlllllIIllI = lllllllllllllllllIIllIlllllIIlIl.getUuid();
        if (players.containsKey((Object)lllllllllllllllllIIllIlllllIIllI)) {
            return players.getBoolean((Object)lllllllllllllllllIIllIlllllIIllI);
        }
        List<UUID> lllllllllllllllllIIllIlllllIIIll = toCheck;
        synchronized (lllllllllllllllllIIllIlllllIIIll) {
            toCheck.add(lllllllllllllllllIIllIlllllIIllI);
        }
        lllllllllllllllllIIllIlllllIIIll = players;
        synchronized (lllllllllllllllllIIllIlllllIIIll) {
            players.put((Object)lllllllllllllllllIIllIlllllIIllI, false);
        }
        return false;
    }
}

