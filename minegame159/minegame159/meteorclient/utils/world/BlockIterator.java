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
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;

public class BlockIterator {
    private static int vRadius;
    private static boolean disableCurrent;
    private static final Mutable blockPos;
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
        blockPos = new Mutable();
    }

    @EventHandler(priority=-201)
    private static void onTick(TickEvent.Pre pre) {
        if (!Utils.canUpdate()) {
            return;
        }
        MinecraftClient MinecraftClient2 = MinecraftClient.getInstance();
        int n = (int)MinecraftClient2.player.getX();
        int n2 = (int)MinecraftClient2.player.getY();
        int n3 = (int)MinecraftClient2.player.getZ();
        for (int i = n - hRadius; i <= n + hRadius; ++i) {
            for (int j = n3 - hRadius; j <= n3 + hRadius; ++j) {
                for (int k = Math.max(0, n2 - vRadius); k <= n2 + vRadius && k <= 255; ++k) {
                    blockPos.set(i, k, j);
                    BlockState BlockState2 = MinecraftClient2.world.getBlockState((BlockPos)blockPos);
                    int n4 = Math.abs(i - n);
                    int n5 = Math.abs(k - n2);
                    int n6 = Math.abs(j - n3);
                    Iterator<Callback> iterator = callbacks.iterator();
                    while (iterator.hasNext()) {
                        Callback callback = iterator.next();
                        if (n4 > callback.hRadius || n5 > callback.vRadius || n6 > callback.hRadius) continue;
                        disableCurrent = false;
                        callback.function.accept((BlockPos)blockPos, BlockState2);
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

    public static void register(int n, int n2, BiConsumer<BlockPos, BlockState> biConsumer) {
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
        public BiConsumer<BlockPos, BlockState> function;

        private Callback() {
        }

        Callback(1 var1_1) {
            this();
        }
    }
}

