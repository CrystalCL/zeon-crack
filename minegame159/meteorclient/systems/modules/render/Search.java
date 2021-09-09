/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongList
 *  it.unimi.dsi.fastutil.longs.LongListIterator
 *  net.minecraft.world.BlockView
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Direction.Axis
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.block.BlockState
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraft.world.dimension.DimensionType
 *  net.minecraft.world.Heightmap.Type
 */
package minegame159.meteorclient.systems.modules.render;

import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.ChunkDataEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import minegame159.meteorclient.utils.render.RenderUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.Heightmap;

public class Search
extends Module {
    private final /* synthetic */ Setting<List<Block>> blocks;
    private final /* synthetic */ LongList toUpdate;
    private final /* synthetic */ SettingGroup sgRender;
    private /* synthetic */ DimensionType lastDimension;
    private final /* synthetic */ SettingGroup sgTracers;
    private final /* synthetic */ Mutable blockPos;
    private static final /* synthetic */ Mutable BLOCK_POS;
    private final /* synthetic */ Setting<Boolean> fullBlock;
    private final /* synthetic */ LongList toRemove;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> tracersEnabled;
    private final /* synthetic */ Setting<SettingColor> color;
    private final /* synthetic */ Long2ObjectArrayMap<MyChunk> chunks;
    private final /* synthetic */ Setting<SettingColor> tracersColor;
    private final /* synthetic */ Pool<MyBlock> blockPool;

    @Override
    public void onDeactivate() {
        Search lIlllIlllII;
        for (MyChunk lIlllIllllI : lIlllIlllII.chunks.values()) {
            lIlllIllllI.dispose();
        }
        lIlllIlllII.chunks.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private void onRender(RenderEvent lIllIIlIlll) {
        Search lIllIIllIII;
        Long2ObjectArrayMap<MyChunk> lIllIIlIlII = lIllIIllIII.chunks;
        synchronized (lIllIIlIlII) {
            lIllIIllIII.toRemove.clear();
            LongIterator lIllIIlIIll = lIllIIllIII.chunks.keySet().iterator();
            while (lIllIIlIIll.hasNext()) {
                long lIllIIllIlI = (Long)lIllIIlIIll.next();
                MyChunk lIllIIllIll = (MyChunk)lIllIIllIII.chunks.get(lIllIIllIlI);
                if (lIllIIllIll.shouldBeDeleted()) {
                    lIllIIllIII.toRemove.add(lIllIIllIlI);
                    continue;
                }
                lIllIIllIll.render(lIllIIlIlll);
            }
            lIllIIlIIll = lIllIIllIII.toRemove.iterator();
            while (lIllIIlIIll.hasNext()) {
                long lIllIIllIIl = (Long)lIllIIlIIll.next();
                lIllIIllIII.chunks.remove(lIllIIllIIl);
            }
        }
    }

    @EventHandler
    private void onChunkData(ChunkDataEvent lIlllIIlIlI) {
        Search lIlllIIlIIl;
        lIlllIIlIIl.searchChunk((Chunk)lIlllIIlIlI.chunk, lIlllIIlIlI);
    }

    static {
        BLOCK_POS = new Mutable();
    }

    private void addToUpdate(int lIllIIIlIlI, int lIllIIIlIIl) {
        Search lIllIIIIlll;
        long lIllIIIlIII = ChunkPos.toLong((int)lIllIIIlIlI, (int)lIllIIIlIIl);
        if (lIllIIIIlll.chunks.containsKey(lIllIIIlIII) && !lIllIIIIlll.toUpdate.contains(lIllIIIlIII)) {
            lIllIIIIlll.toUpdate.add(lIllIIIlIII);
        }
    }

    public void onBlockUpdate(BlockPos lIllIlllIlI, BlockState lIllIllIllI) {
        Search lIllIlllIll;
        MeteorExecutor.execute(() -> {
            Search lIlIllllIIl;
            int lIlIlllIllI = lIllIlllIlI.getX() >> 4;
            int lIlIlllIlIl = lIllIlllIlI.getZ() >> 4;
            long lIlIlllIlII = ChunkPos.toLong((int)lIlIlllIllI, (int)lIlIlllIlIl);
            Long2ObjectArrayMap<MyChunk> lIlIllIllIl = lIlIllllIIl.chunks;
            synchronized (lIlIllIllIl) {
                if (lIlIllllIIl.blocks.get().contains((Object)lIllIllIllI.getBlock())) {
                    ((MyChunk)lIlIllllIIl.chunks.computeIfAbsent(lIlIlllIlII, lIlIllIIlII -> {
                        Search lIlIllIIlll;
                        return lIlIllIIlll.new MyChunk(lIlIlllIllI, lIlIlllIlIl);
                    })).add(lIllIlllIlI, true);
                } else {
                    MyChunk lIlIllllIlI = (MyChunk)lIlIllllIIl.chunks.get(lIlIlllIlII);
                    if (lIlIllllIlI != null) {
                        lIlIllllIlI.remove(lIllIlllIlI);
                    }
                }
            }
        });
    }

    @Override
    public void onActivate() {
        Search lIllllIIIlI;
        lIllllIIIlI.lastDimension = lIllllIIIlI.mc.world.getDimension();
        lIllllIIIlI.searchViewDistance();
    }

    private void searchViewDistance() {
        Search lIlllIlIIIl;
        int lIlllIlIIlI = lIlllIlIIIl.mc.options.viewDistance;
        for (int lIlllIlIlII = lIlllIlIIIl.mc.player.chunkX - lIlllIlIIlI; lIlllIlIlII <= lIlllIlIIIl.mc.player.chunkX + lIlllIlIIlI; ++lIlllIlIlII) {
            for (int lIlllIlIlIl = lIlllIlIIIl.mc.player.chunkZ - lIlllIlIIlI; lIlllIlIlIl <= lIlllIlIIIl.mc.player.chunkZ + lIlllIlIIlI; ++lIlllIlIlIl) {
                if (!lIlllIlIIIl.mc.world.getChunkManager().isChunkLoaded(lIlllIlIlII, lIlllIlIlIl)) continue;
                lIlllIlIIIl.searchChunk((Chunk)lIlllIlIIIl.mc.world.getChunk(lIlllIlIlII, lIlllIlIlIl), null);
            }
        }
    }

    private void searchChunk(Chunk lIlllIIIIll, ChunkDataEvent lIlllIIIIlI) {
        Search lIlllIIIIIl;
        MeteorExecutor.execute(() -> {
            Search lIlIlIIllIl;
            MyChunk lIlIlIIlllI = lIlIlIIllIl.new MyChunk(lIlIlIlIIII.getPos().x, lIlIlIlIIII.getPos().z);
            for (int lIlIlIlIIlI = lIlllIIIIll.getPos().getStartX(); lIlIlIlIIlI <= lIlllIIIIll.getPos().getEndX(); ++lIlIlIlIIlI) {
                for (int lIlIlIlIIll = lIlllIIIIll.getPos().getStartZ(); lIlIlIlIIll <= lIlllIIIIll.getPos().getEndZ(); ++lIlIlIlIIll) {
                    int lIlIlIlIlII = lIlllIIIIll.getHeightmap(Type.WORLD_SURFACE).get(lIlIlIlIIlI - lIlllIIIIll.getPos().getStartX(), lIlIlIlIIll - lIlllIIIIll.getPos().getStartZ());
                    for (int lIlIlIlIlIl = 0; lIlIlIlIlIl < lIlIlIlIlII; ++lIlIlIlIlIl) {
                        lIlIlIIllIl.blockPos.set(lIlIlIlIIlI, lIlIlIlIlIl, lIlIlIlIIll);
                        BlockState lIlIlIlIllI = lIlllIIIIll.getBlockState((BlockPos)lIlIlIIllIl.blockPos);
                        if (!lIlIlIIllIl.blocks.get().contains((Object)lIlIlIlIllI.getBlock())) continue;
                        lIlIlIIlllI.add((BlockPos)lIlIlIIllIl.blockPos, false);
                    }
                }
            }
            Long2ObjectArrayMap<MyChunk> long2ObjectArrayMap = lIlIlIIllIl.chunks;
            synchronized (long2ObjectArrayMap) {
                if (lIlIlIIlllI.blocks.size() > 0) {
                    lIlIlIIllIl.chunks.put(lIlllIIIIll.getPos().toLong(), (Object)lIlIlIIlllI);
                }
            }
            if (lIlllIIIIlI != null) {
                ChunkDataEvent.returnChunkDataEvent(lIlllIIIIlI);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private void onTick(TickEvent.Post lIllIlIlIlI) {
        Long2ObjectArrayMap<MyChunk> lIllIlIlIII;
        Search lIllIlIlIIl;
        if (lIllIlIlIIl.lastDimension != lIllIlIlIIl.mc.world.getDimension()) {
            lIllIlIlIII = lIllIlIlIIl.chunks;
            synchronized (lIllIlIlIII) {
                for (MyChunk lIllIlIlllI : lIllIlIlIIl.chunks.values()) {
                    lIllIlIlllI.dispose();
                }
                lIllIlIlIIl.chunks.clear();
            }
        }
        lIllIlIlIII = lIllIlIlIIl.chunks;
        synchronized (lIllIlIlIII) {
            LongListIterator lIllIlIIlll = lIllIlIlIIl.toUpdate.iterator();
            while (lIllIlIIlll.hasNext()) {
                long lIllIlIllII = (Long)lIllIlIIlll.next();
                MyChunk lIllIlIllIl = (MyChunk)lIllIlIlIIl.chunks.get(lIllIlIllII);
                if (lIllIlIllIl == null) continue;
                lIllIlIllIl.update();
            }
            lIllIlIlIIl.toUpdate.clear();
        }
        lIllIlIlIIl.lastDimension = lIllIlIlIIl.mc.world.getDimension();
    }

    public Search() {
        super(Categories.Render, "search", "Searches for specified blocks.");
        Search lIllllIIllI;
        lIllllIIllI.sgGeneral = lIllllIIllI.settings.getDefaultGroup();
        lIllllIIllI.sgRender = lIllllIIllI.settings.createGroup("Render");
        lIllllIIllI.sgTracers = lIllllIIllI.settings.createGroup("Tracers");
        lIllllIIllI.chunks = new Long2ObjectArrayMap();
        lIllllIIllI.blocks = lIllllIIllI.sgGeneral.add(new BlockListSetting.Builder().name("blocks").description("Blocks to search for.").defaultValue(new ArrayList<Block>(0)).onChanged(lIlIIlllIIl -> {
            Search lIlIIlllIlI;
            if (Utils.canUpdate() && lIlIIlllIlI.isActive()) {
                Long2ObjectArrayMap<MyChunk> lIlIIllIlll = lIlIIlllIlI.chunks;
                synchronized (lIlIIllIlll) {
                    for (MyChunk lIlIIlllIll : lIlIIlllIlI.chunks.values()) {
                        lIlIIlllIll.dispose();
                    }
                    lIlIIlllIlI.chunks.clear();
                }
                lIlIIlllIlI.searchViewDistance();
            }
        }).build());
        lIllllIIllI.color = lIllllIIllI.sgRender.add(new ColorSetting.Builder().name("color").description("The color.").defaultValue(new SettingColor(0, 255, 200)).build());
        lIllllIIllI.fullBlock = lIllllIIllI.sgRender.add(new BoolSetting.Builder().name("full-block").description("If outlines will be rendered as full blocks.").defaultValue(false).build());
        lIllllIIllI.tracersEnabled = lIllllIIllI.sgTracers.add(new BoolSetting.Builder().name("tracers-enabled").description("Draws lines to the blocks.").defaultValue(false).build());
        lIllllIIllI.tracersColor = lIllllIIllI.sgTracers.add(new ColorSetting.Builder().name("tracers-color").description("The color of the tracers.").defaultValue(new SettingColor(225, 225, 225)).build());
        lIllllIIllI.blockPool = new Pool<MyBlock>(() -> {
            Search lIlIlIIIIlI;
            return lIlIlIIIIlI.new MyBlock();
        });
        lIllllIIllI.toRemove = new LongArrayList();
        lIllllIIllI.toUpdate = new LongArrayList();
        lIllllIIllI.blockPos = new Mutable();
    }

    private class MyBlock {
        private static final /* synthetic */ int TO;
        private /* synthetic */ int z;
        private static final /* synthetic */ int BO_RI;
        private static final /* synthetic */ int TO_RI;
        private /* synthetic */ int neighbours;
        private static final /* synthetic */ int BO_BA;
        private static final /* synthetic */ int FO_LE;
        private static final /* synthetic */ int BA_RI;
        private static final /* synthetic */ int TO_LE;
        private static final /* synthetic */ int BO;
        private static final /* synthetic */ int FO;
        private /* synthetic */ int y;
        private static final /* synthetic */ int BO_LE;
        private static final /* synthetic */ int BA_LE;
        private static final /* synthetic */ int LE;
        private /* synthetic */ BlockState state;
        private static final /* synthetic */ int TO_BA;
        private static final /* synthetic */ int TO_FO;
        private static final /* synthetic */ int BO_FO;
        private static final /* synthetic */ int RI;
        private static final /* synthetic */ int FO_RI;
        private /* synthetic */ int x;
        private static final /* synthetic */ int BA;

        public void updateNeighbours() {
            MyBlock lllllllllllllllllIIIlIlIIlIIIIIl;
            lllllllllllllllllIIIlIlIIlIIIIIl.neighbours = 0;
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(0.0, 0.0, 1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 2;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(1.0, 0.0, 1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 4;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(1.0, 0.0, 0.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 8;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(1.0, 0.0, -1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x10;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(0.0, 0.0, -1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x20;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(-1.0, 0.0, -1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x40;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(-1.0, 0.0, 0.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x80;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(-1.0, 0.0, 1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x100;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(0.0, 1.0, 0.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x200;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(0.0, 1.0, 1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x400;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(0.0, 1.0, -1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x800;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(1.0, 1.0, 0.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x1000;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(-1.0, 1.0, 0.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x2000;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(0.0, -1.0, 0.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x4000;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(0.0, -1.0, 1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x8000;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(0.0, -1.0, -1.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x10000;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(1.0, -1.0, 0.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x20000;
            }
            if (lllllllllllllllllIIIlIlIIlIIIIIl.isBlock(-1.0, -1.0, 0.0)) {
                lllllllllllllllllIIIlIlIIlIIIIIl.neighbours |= 0x40000;
            }
        }

        private MyBlock() {
            MyBlock lllllllllllllllllIIIlIlIIlIIlIll;
        }

        static {
            BO_RI = 131072;
            BA_LE = 64;
            BO = 16384;
            BA = 32;
            BO_BA = 65536;
            BA_RI = 16;
            TO = 512;
            FO_LE = 256;
            TO_BA = 2048;
            FO_RI = 4;
            LE = 128;
            RI = 8;
            BO_FO = 32768;
            BO_LE = 262144;
            FO = 2;
            TO_RI = 4096;
            TO_FO = 1024;
            TO_LE = 8192;
        }

        public void set(BlockPos lllllllllllllllllIIIlIlIIlIIIlII) {
            MyBlock lllllllllllllllllIIIlIlIIlIIIlll;
            lllllllllllllllllIIIlIlIIlIIIlll.x = lllllllllllllllllIIIlIlIIlIIIlII.getX();
            lllllllllllllllllIIIlIlIIlIIIlll.y = lllllllllllllllllIIIlIlIIlIIIlII.getY();
            lllllllllllllllllIIIlIlIIlIIIlll.z = lllllllllllllllllIIIlIlIIlIIIlII.getZ();
            lllllllllllllllllIIIlIlIIlIIIlll.state = ((Search)lllllllllllllllllIIIlIlIIlIIIlll.Search.this).mc.world.getBlockState(lllllllllllllllllIIIlIlIIlIIIlII);
            lllllllllllllllllIIIlIlIIlIIIlll.updateNeighbours();
        }

        public boolean equals(BlockPos lllllllllllllllllIIIlIlIIIIlIIll) {
            MyBlock lllllllllllllllllIIIlIlIIIIlIlII;
            return lllllllllllllllllIIIlIlIIIIlIlII.x == lllllllllllllllllIIIlIlIIIIlIIll.getX() && lllllllllllllllllIIIlIlIIIIlIlII.y == lllllllllllllllllIIIlIlIIIIlIIll.getY() && lllllllllllllllllIIIlIlIIIIlIlII.z == lllllllllllllllllIIIlIlIIIIlIIll.getZ();
        }

        public void render(RenderEvent lllllllllllllllllIIIlIlIIIlIlIII) {
            MyBlock lllllllllllllllllIIIlIlIIIlIlIIl;
            double lllllllllllllllllIIIlIlIIIlIIlll = lllllllllllllllllIIIlIlIIIlIlIIl.x;
            double lllllllllllllllllIIIlIlIIIlIIllI = lllllllllllllllllIIIlIlIIIlIlIIl.y;
            double lllllllllllllllllIIIlIlIIIlIIlIl = lllllllllllllllllIIIlIlIIIlIlIIl.z;
            double lllllllllllllllllIIIlIlIIIlIIlII = lllllllllllllllllIIIlIlIIIlIlIIl.x + 1;
            double lllllllllllllllllIIIlIlIIIlIIIll = lllllllllllllllllIIIlIlIIIlIlIIl.y + 1;
            double lllllllllllllllllIIIlIlIIIlIIIlI = lllllllllllllllllIIIlIlIIIlIlIIl.z + 1;
            boolean lllllllllllllllllIIIlIlIIIlIIIIl = true;
            if (!((Boolean)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.fullBlock.get()).booleanValue()) {
                VoxelShape lllllllllllllllllIIIlIlIIIlIlIlI = lllllllllllllllllIIIlIlIIIlIlIIl.state.getOutlineShape((BlockView)((Search)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this).mc.world, (BlockPos)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.blockPos);
                lllllllllllllllllIIIlIlIIIlIIIIl = Block.isShapeFullCube((VoxelShape)lllllllllllllllllIIIlIlIIIlIlIlI);
                if (!lllllllllllllllllIIIlIlIIIlIlIlI.isEmpty()) {
                    lllllllllllllllllIIIlIlIIIlIIlll = (double)lllllllllllllllllIIIlIlIIIlIlIIl.x + lllllllllllllllllIIIlIlIIIlIlIlI.getMin(Axis.X);
                    lllllllllllllllllIIIlIlIIIlIIllI = (double)lllllllllllllllllIIIlIlIIIlIlIIl.y + lllllllllllllllllIIIlIlIIIlIlIlI.getMin(Axis.Y);
                    lllllllllllllllllIIIlIlIIIlIIlIl = (double)lllllllllllllllllIIIlIlIIIlIlIIl.z + lllllllllllllllllIIIlIlIIIlIlIlI.getMin(Axis.Z);
                    lllllllllllllllllIIIlIlIIIlIIlII = (double)lllllllllllllllllIIIlIlIIIlIlIIl.x + lllllllllllllllllIIIlIlIIIlIlIlI.getMax(Axis.X);
                    lllllllllllllllllIIIlIlIIIlIIIll = (double)lllllllllllllllllIIIlIlIIIlIlIIl.y + lllllllllllllllllIIIlIlIIIlIlIlI.getMax(Axis.Y);
                    lllllllllllllllllIIIlIlIIIlIIIlI = (double)lllllllllllllllllIIIlIlIIIlIlIIl.z + lllllllllllllllllIIIlIlIIIlIlIlI.getMax(Axis.Z);
                }
            }
            if (lllllllllllllllllIIIlIlIIIlIIIIl) {
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x80) != 128 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20) != 32 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x80) == 128 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20) == 32 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x40) != 64) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIlIl, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x80) != 128 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 2) != 2 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x80) == 128 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 2) == 2 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x100) != 256) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIIlI, lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 8) != 8 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20) != 32 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 8) == 8 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20) == 32 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x10) != 16) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIlIl, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 8) != 8 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 2) != 2 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 8) == 8 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 2) == 2 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 4) != 4) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIIlI, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20) != 32 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x4000) != 16384 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20) != 32 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x10000) == 65536) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIlIl, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 2) != 2 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x4000) != 16384 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 2) != 2 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x8000) == 32768) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIIlI, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20) != 32 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x200) != 512 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20) != 32 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x800) == 2048) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIlIl, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 2) != 2 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x200) != 512 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 2) != 2 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x400) == 1024) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIIlI, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x80) != 128 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x4000) != 16384 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x80) != 128 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x40000) == 262144) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 8) != 8 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x4000) != 16384 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 8) != 8 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x20000) == 131072) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x80) != 128 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x200) != 512 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x80) != 128 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x2000) == 8192) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
                if ((lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 8) != 8 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x200) != 512 || (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 8) != 8 && (lllllllllllllllllIIIlIlIIIlIlIIl.neighbours & 0x1000) == 4096) {
                    Renderer.LINES.line(lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get());
                }
            } else {
                Renderer.LINES.boxEdges(lllllllllllllllllIIIlIlIIIlIIlll, lllllllllllllllllIIIlIlIIIlIIllI, lllllllllllllllllIIIlIlIIIlIIlIl, lllllllllllllllllIIIlIlIIIlIIlII, lllllllllllllllllIIIlIlIIIlIIIll, lllllllllllllllllIIIlIlIIIlIIIlI, (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.color.get(), 0);
            }
            if (((Boolean)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.tracersEnabled.get()).booleanValue()) {
                RenderUtils.drawTracerToPos(new BlockPos(lllllllllllllllllIIIlIlIIIlIlIIl.x, lllllllllllllllllIIIlIlIIIlIlIIl.y, lllllllllllllllllIIIlIlIIIlIlIIl.z), (Color)lllllllllllllllllIIIlIlIIIlIlIIl.Search.this.tracersColor.get(), lllllllllllllllllIIIlIlIIIlIlIII);
            }
        }

        private boolean isBlock(double lllllllllllllllllIIIlIlIIIllIlll, double lllllllllllllllllIIIlIlIIIlllIlI, double lllllllllllllllllIIIlIlIIIllIlIl) {
            MyBlock lllllllllllllllllIIIlIlIIIllllII;
            BLOCK_POS.set((double)lllllllllllllllllIIIlIlIIIllllII.x + lllllllllllllllllIIIlIlIIIllIlll, (double)lllllllllllllllllIIIlIlIIIllllII.y + lllllllllllllllllIIIlIlIIIlllIlI, (double)lllllllllllllllllIIIlIlIIIllllII.z + lllllllllllllllllIIIlIlIIIllIlIl);
            return ((Search)lllllllllllllllllIIIlIlIIIllllII.Search.this).mc.world.getBlockState((BlockPos)BLOCK_POS).getBlock() == lllllllllllllllllIIIlIlIIIllllII.state.getBlock();
        }
    }

    private class MyChunk {
        private final /* synthetic */ int z;
        private final /* synthetic */ List<MyBlock> blocks;
        private final /* synthetic */ int x;

        public void render(RenderEvent llIIIIIlIIlllIl) {
            MyChunk llIIIIIlIIllllI;
            for (MyBlock llIIIIIlIlIIIIl : llIIIIIlIIllllI.blocks) {
                llIIIIIlIlIIIIl.render(llIIIIIlIIlllIl);
            }
        }

        public MyChunk(int llIIIIIllIlllII, int llIIIIIllIlllll) {
            MyChunk llIIIIIllIllllI;
            llIIIIIllIllllI.blocks = new ArrayList<MyBlock>();
            llIIIIIllIllllI.x = llIIIIIllIlllII;
            llIIIIIllIllllI.z = llIIIIIllIlllll;
        }

        public void update() {
            MyChunk llIIIIIlIlIlIIl;
            for (MyBlock llIIIIIlIlIlIlI : llIIIIIlIlIlIIl.blocks) {
                llIIIIIlIlIlIlI.updateNeighbours();
            }
        }

        private void addToUpdateChunk(BlockPos llIIIIIlIlllIlI) {
            MyChunk llIIIIIlIlllIll;
            llIIIIIlIlllIll.Search.this.addToUpdate(llIIIIIlIlllIll.x, llIIIIIlIlllIll.z);
            double llIIIIIlIlllIIl = Math.abs(llIIIIIlIlllIlI.getX() + (llIIIIIlIlllIlI.getX() < 0 ? 1 : 0)) % 16;
            double llIIIIIlIlllIII = Math.abs(llIIIIIlIlllIlI.getZ() + (llIIIIIlIlllIlI.getZ() < 0 ? 1 : 0)) % 16;
            if (llIIIIIlIlllIIl == 15.0) {
                llIIIIIlIlllIll.Search.this.addToUpdate(llIIIIIlIlllIll.x + (llIIIIIlIlllIlI.getX() < 0 ? -1 : 1), llIIIIIlIlllIll.z);
            } else if (llIIIIIlIlllIIl == 0.0) {
                llIIIIIlIlllIll.Search.this.addToUpdate(llIIIIIlIlllIll.x - (llIIIIIlIlllIlI.getX() < 0 ? -1 : 1), llIIIIIlIlllIll.z);
            }
            if (llIIIIIlIlllIII == 15.0) {
                llIIIIIlIlllIll.Search.this.addToUpdate(llIIIIIlIlllIll.x, llIIIIIlIlllIll.z + (llIIIIIlIlllIlI.getZ() < 0 ? -1 : 1));
            } else if (llIIIIIlIlllIII == 0.0) {
                llIIIIIlIlllIll.Search.this.addToUpdate(llIIIIIlIlllIll.x, llIIIIIlIlllIll.z - (llIIIIIlIlllIlI.getZ() < 0 ? -1 : 1));
            }
        }

        public void remove(BlockPos llIIIIIllIIIIlI) {
            MyChunk llIIIIIllIIIIll;
            for (int llIIIIIllIIIllI = 0; llIIIIIllIIIllI < llIIIIIllIIIIll.blocks.size(); ++llIIIIIllIIIllI) {
                MyBlock llIIIIIllIIIlll = llIIIIIllIIIIll.blocks.get(llIIIIIllIIIllI);
                if (!llIIIIIllIIIlll.equals(llIIIIIllIIIIlI)) continue;
                llIIIIIllIIIIll.blocks.remove(llIIIIIllIIIllI);
                return;
            }
            llIIIIIllIIIIll.addToUpdateChunk(llIIIIIllIIIIlI);
        }

        public boolean shouldBeDeleted() {
            MyChunk llIIIIIlIllIIIl;
            int llIIIIIlIllIIII = ((Search)llIIIIIlIllIIIl.Search.this).mc.options.viewDistance + 1;
            return llIIIIIlIllIIIl.x > ((Search)llIIIIIlIllIIIl.Search.this).mc.player.chunkX + llIIIIIlIllIIII || llIIIIIlIllIIIl.x < ((Search)llIIIIIlIllIIIl.Search.this).mc.player.chunkX - llIIIIIlIllIIII || llIIIIIlIllIIIl.z > ((Search)llIIIIIlIllIIIl.Search.this).mc.player.chunkZ + llIIIIIlIllIIII || llIIIIIlIllIIIl.z < ((Search)llIIIIIlIllIIIl.Search.this).mc.player.chunkZ - llIIIIIlIllIIII;
        }

        public void add(BlockPos llIIIIIllIIllll, boolean llIIIIIllIlIIlI) {
            MyChunk llIIIIIllIlIlII;
            if (llIIIIIllIlIIlI) {
                for (MyBlock llIIIIIllIlIlIl : llIIIIIllIlIlII.blocks) {
                    if (!llIIIIIllIlIlIl.equals(llIIIIIllIIllll)) continue;
                    return;
                }
            }
            MyBlock llIIIIIllIlIIIl = (MyBlock)llIIIIIllIlIlII.Search.this.blockPool.get();
            llIIIIIllIlIIIl.set(llIIIIIllIIllll);
            llIIIIIllIlIlII.blocks.add(llIIIIIllIlIIIl);
            llIIIIIllIlIlII.addToUpdateChunk(llIIIIIllIIllll);
        }

        public void dispose() {
            MyChunk llIIIIIlIIlIlIl;
            for (MyBlock llIIIIIlIIlIlll : llIIIIIlIIlIlIl.blocks) {
                llIIIIIlIIlIlIl.Search.this.blockPool.free(llIIIIIlIIlIlll);
            }
            llIIIIIlIIlIlIl.blocks.clear();
        }
    }
}

