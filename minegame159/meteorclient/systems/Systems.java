/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.accounts.Accounts;
import minegame159.meteorclient.systems.commands.Commands;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.macros.Macros;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.profiles.Profiles;
import minegame159.meteorclient.systems.waypoints.Waypoints;

public class Systems {
    private static final /* synthetic */ List<Runnable> preLoadTasks;
    private static /* synthetic */ System<?> config;
    private static final /* synthetic */ Map<Class<? extends System>, System<?>> systems;

    public static void save() {
        Systems.save(null);
    }

    public static void save(File llllllllllllllllllIlIIllIlIIllIl) {
        MeteorClient.LOG.info("Saving");
        long llllllllllllllllllIlIIllIlIIllII = java.lang.System.currentTimeMillis();
        for (System<?> llllllllllllllllllIlIIllIlIIlllI : systems.values()) {
            llllllllllllllllllIlIIllIlIIlllI.save(llllllllllllllllllIlIIllIlIIllIl);
        }
        MeteorClient.LOG.info("Saved in {} milliseconds.", (Object)(java.lang.System.currentTimeMillis() - llllllllllllllllllIlIIllIlIIllII));
    }

    public static void load() {
        Systems.load(null);
    }

    public static void addPreLoadTask(Runnable llllllllllllllllllIlIIllIlIIIllI) {
        preLoadTasks.add(llllllllllllllllllIlIIllIlIIIllI);
    }

    public static <T extends System<?>> T get(Class<T> llllllllllllllllllIlIIllIIllIlll) {
        return (T)systems.get(llllllllllllllllllIlIIllIIllIlll);
    }

    static {
        systems = new HashMap();
        preLoadTasks = new ArrayList<Runnable>(1);
    }

    public static void init() {
        config = Systems.add(new Config());
        config.load();
        config.init();
        Systems.add(new Modules());
        Systems.add(new Commands());
        Systems.add(new Friends());
        Systems.add(new Macros());
        Systems.add(new Accounts());
        Systems.add(new Waypoints());
        Systems.add(new Profiles());
        for (System<?> llllllllllllllllllIlIIllIlIllIII : systems.values()) {
            if (llllllllllllllllllIlIIllIlIllIII == config) continue;
            llllllllllllllllllIlIIllIlIllIII.init();
        }
    }

    public static void load(File llllllllllllllllllIlIIllIIlllllI) {
        MeteorClient.LOG.info("Loading");
        long llllllllllllllllllIlIIllIIllllIl = java.lang.System.currentTimeMillis();
        for (Runnable runnable : preLoadTasks) {
            runnable.run();
        }
        for (System system : systems.values()) {
            if (system == config) continue;
            system.load(llllllllllllllllllIlIIllIIlllllI);
        }
        MeteorClient.LOG.info("Loaded in {} milliseconds", (Object)(java.lang.System.currentTimeMillis() - llllllllllllllllllIlIIllIIllllIl));
    }

    private static System<?> add(System<?> llllllllllllllllllIlIIllIlIlIIll) {
        systems.put(llllllllllllllllllIlIIllIlIlIIll.getClass(), llllllllllllllllllIlIIllIlIlIIll);
        MeteorClient.EVENT_BUS.subscribe(llllllllllllllllllIlIIllIlIlIIll);
        return llllllllllllllllllIlIIllIlIlIIll;
    }

    public Systems() {
        Systems llllllllllllllllllIlIIllIlIllIll;
    }
}

