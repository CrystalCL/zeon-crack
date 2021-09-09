/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.MinecraftClient
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
    private static final /* synthetic */ Pool<Callback> callbackPool;
    private static /* synthetic */ int hRadius;
    private static /* synthetic */ int vRadius;
    private static final /* synthetic */ List<Runnable> afterCallbacks;
    private static /* synthetic */ boolean disableCurrent;
    private static final /* synthetic */ List<Callback> callbacks;
    private static final /* synthetic */ Mutable blockPos;

    static {
        callbackPool = new Pool<Callback>(() -> new Callback());
        callbacks = new ArrayList<Callback>();
        afterCallbacks = new ArrayList<Runnable>();
        blockPos = new Mutable();
    }

    public BlockIterator() {
        BlockIterator llIIllIlllIllIl;
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(BlockIterator.class);
    }

    public static void register(int llIIllIlIllllIl, int llIIllIlIllllII, BiConsumer<BlockPos, BlockState> llIIllIlIllIlll) {
        hRadius = Math.max(hRadius, llIIllIlIllllIl);
        vRadius = Math.max(vRadius, llIIllIlIllllII);
        Callback llIIllIlIlllIlI = callbackPool.get();
        llIIllIlIlllIlI.function = llIIllIlIllIlll;
        llIIllIlIlllIlI.hRadius = llIIllIlIllllIl;
        llIIllIlIlllIlI.vRadius = llIIllIlIllllII;
        callbacks.add(llIIllIlIlllIlI);
    }

    @EventHandler(priority=-201)
    private static void onTick(TickEvent.Pre llIIllIllIlIIll) {
        if (!Utils.canUpdate()) {
            return;
        }
        MinecraftClient llIIllIllIlIIlI = MinecraftClient.getInstance();
        int llIIllIllIlIIIl = (int)llIIllIllIlIIlI.player.getX();
        int llIIllIllIlIIII = (int)llIIllIllIlIIlI.player.getY();
        int llIIllIllIIllll = (int)llIIllIllIlIIlI.player.getZ();
        for (int llIIllIllIlIllI = llIIllIllIlIIIl - hRadius; llIIllIllIlIllI <= llIIllIllIlIIIl + hRadius; ++llIIllIllIlIllI) {
            for (int llIIllIllIlIlll = llIIllIllIIllll - hRadius; llIIllIllIlIlll <= llIIllIllIIllll + hRadius; ++llIIllIllIlIlll) {
                for (int llIIllIllIllIII = Math.max(0, llIIllIllIlIIII - vRadius); llIIllIllIllIII <= llIIllIllIlIIII + vRadius && llIIllIllIllIII <= 255; ++llIIllIllIllIII) {
                    blockPos.set(llIIllIllIlIllI, llIIllIllIllIII, llIIllIllIlIlll);
                    BlockState llIIllIllIlllII = llIIllIllIlIIlI.world.getBlockState((BlockPos)blockPos);
                    int llIIllIllIllIll = Math.abs(llIIllIllIlIllI - llIIllIllIlIIIl);
                    int llIIllIllIllIlI = Math.abs(llIIllIllIllIII - llIIllIllIlIIII);
                    int llIIllIllIllIIl = Math.abs(llIIllIllIlIlll - llIIllIllIIllll);
                    Iterator<Callback> llIIllIllIlllIl = callbacks.iterator();
                    while (llIIllIllIlllIl.hasNext()) {
                        Callback llIIllIllIllllI = llIIllIllIlllIl.next();
                        if (llIIllIllIllIll > llIIllIllIllllI.hRadius || llIIllIllIllIlI > llIIllIllIllllI.vRadius || llIIllIllIllIIl > llIIllIllIllllI.hRadius) continue;
                        disableCurrent = false;
                        llIIllIllIllllI.function.accept((BlockPos)blockPos, llIIllIllIlllII);
                        if (!disableCurrent) continue;
                        llIIllIllIlllIl.remove();
                    }
                }
            }
        }
        hRadius = 0;
        vRadius = 0;
        for (Callback llIIllIllIlIlIl : callbacks) {
            callbackPool.free(llIIllIllIlIlIl);
        }
        callbacks.clear();
        for (Runnable llIIllIllIlIlII : afterCallbacks) {
            llIIllIllIlIlII.run();
        }
        afterCallbacks.clear();
    }

    public static void after(Runnable llIIllIlIllIlII) {
        afterCallbacks.add(llIIllIlIllIlII);
    }

    public static void disableCurrent() {
        disableCurrent = true;
    }

    private static class Callback {
        public /* synthetic */ BiConsumer<BlockPos, BlockState> function;
        public /* synthetic */ int hRadius;
        public /* synthetic */ int vRadius;

        private Callback() {
            Callback lllIlllIlllII;
        }
    }
}

