/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Pool;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_310;

public class BlockIterator {
    private static int vRadius;
    private static boolean disableCurrent;
    private static final class_2338.class_2339 blockPos;
    private static final List<Runnable> afterCallbacks;
    private static final List<Callback> callbacks;
    private static int hRadius;
    private static final Pool<Callback> callbackPool;

    private static Callback lambda$static$0() {
        return new Callback(null);
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(BlockIterator.class);
    }

    public static void disableCurrent() {
        disableCurrent = true;
    }

    public static void after(Runnable runnable) {
        afterCallbacks.add(runnable);
    }

    static {
        callbackPool = new Pool<Callback>(BlockIterator::lambda$static$0);
        callbacks = new ArrayList<Callback>();
        afterCallbacks = new ArrayList<Runnable>();
        blockPos = new class_2338.class_2339();
    }

    @EventHandler(priority=-201)
    private static void onTick(TickEvent.Pre pre) {
        if (!Utils.canUpdate()) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        int n = (int)class_3102.field_1724.method_23317();
        int n2 = (int)class_3102.field_1724.method_23318();
        int n3 = (int)class_3102.field_1724.method_23321();
        for (int i = n - hRadius; i <= n + hRadius; ++i) {
            for (int j = n3 - hRadius; j <= n3 + hRadius; ++j) {
                for (int k = Math.max(0, n2 - vRadius); k <= n2 + vRadius && k <= 255; ++k) {
                    blockPos.method_10103(i, k, j);
                    class_2680 class_26802 = class_3102.field_1687.method_8320((class_2338)blockPos);
                    int n4 = Math.abs(i - n);
                    int n5 = Math.abs(k - n2);
                    int n6 = Math.abs(j - n3);
                    Iterator<Callback> iterator = callbacks.iterator();
                    while (iterator.hasNext()) {
                        Callback callback = iterator.next();
                        if (n4 > callback.hRadius || n5 > callback.vRadius || n6 > callback.hRadius) continue;
                        disableCurrent = false;
                        callback.function.accept((class_2338)blockPos, class_26802);
                        if (!disableCurrent) continue;
                        iterator.remove();
                    }
                    if (3 != -1) continue;
                    return;
                }
                if (0 <= 2) continue;
                return;
            }
            if (3 > 1) continue;
            return;
        }
        hRadius = 0;
        vRadius = 0;
        for (Callback callback : callbacks) {
            callbackPool.free(callback);
        }
        callbacks.clear();
        for (Runnable runnable : afterCallbacks) {
            runnable.run();
        }
        afterCallbacks.clear();
    }

    public static void register(int n, int n2, BiConsumer<class_2338, class_2680> biConsumer) {
        hRadius = Math.max(hRadius, n);
        vRadius = Math.max(vRadius, n2);
        Callback callback = callbackPool.get();
        callback.function = biConsumer;
        callback.hRadius = n;
        callback.vRadius = n2;
        callbacks.add(callback);
    }

    private static class Callback {
        public int hRadius;
        public int vRadius;
        public BiConsumer<class_2338, class_2680> function;

        private Callback() {
        }

        Callback(1 var1_1) {
            this();
        }
    }
}

