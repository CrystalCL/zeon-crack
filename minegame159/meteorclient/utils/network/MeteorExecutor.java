/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteorExecutor {
    private static /* synthetic */ ExecutorService executor;

    public static void init() {
        executor = Executors.newSingleThreadExecutor();
    }

    public static void execute(Runnable llIIlIlIIIIIlll) {
        executor.execute(llIIlIlIIIIIlll);
    }

    public MeteorExecutor() {
        MeteorExecutor llIIlIlIIIIlIlI;
    }
}

