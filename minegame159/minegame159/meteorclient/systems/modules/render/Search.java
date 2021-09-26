/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.client.MinecraftClient;

public class Search
extends Module {
    private final Setting<List<Block>> blocks;
    private final SettingGroup sgTracers;
    private final SettingGroup sgRender;
    private final Setting<SettingColor> tracersColor;
    private static final Mutable BLOCK_POS = new Mutable();
    private DimensionType lastDimension;
    private final Long2ObjectArrayMap<MyChunk> chunks;
    private final Setting<Boolean> fullBlock;
    private final Setting<SettingColor> color;
    private final Setting<Boolean> tracersEnabled;
    private final Pool<MyBlock> blockPool;
    private final LongList toRemove;
    private final SettingGroup sgGeneral;
    private final LongList toUpdate;
    private final Mutable blockPos;

    static MinecraftClient access$300(Search search) {
        return search.mc;
    }

    private void searchViewDistance() {
        int n = this.mc.options.viewDistance;
        for (int i = this.mc.player.chunkX - n; i <= this.mc.player.chunkX + n; ++i) {
            for (int j = this.mc.player.chunkZ - n; j <= this.mc.player.chunkZ + n; ++j) {
                if (!this.mc.world.getChunkManager().isChunkLoaded(i, j)) continue;
                this.searchChunk((Chunk)this.mc.world.getChunk(i, j), null);
                if (2 >= 0) continue;
                return;
            }
            if (null == null) continue;
            return;
        }
    }

    static MinecraftClient access$700(Search search) {
        return search.mc;
    }

    static Setting access$1500(Search search) {
        return search.tracersColor;
    }

    static MinecraftClient access$600(Search search) {
        return search.mc;
    }

    static Mutable access$800() {
        return BLOCK_POS;
    }

    private void searchChunk(Chunk Chunk2, ChunkDataEvent chunkDataEvent) {
        MeteorExecutor.execute(() -> this.lambda$searchChunk$2(Chunk2, chunkDataEvent));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        Long2ObjectArrayMap<MyChunk> long2ObjectArrayMap = this.chunks;
        synchronized (long2ObjectArrayMap) {
            long l;
            this.toRemove.clear();
            LongIterator longIterator = this.chunks.keySet().iterator();
            while (longIterator.hasNext()) {
                l = (Long)longIterator.next();
                MyChunk myChunk = (MyChunk)this.chunks.get(l);
                if (myChunk.shouldBeDeleted()) {
                    this.toRemove.add(l);
                    continue;
                }
                myChunk.render(renderEvent);
            }
            longIterator = this.toRemove.iterator();
            while (longIterator.hasNext()) {
                l = (Long)longIterator.next();
                this.chunks.remove(l);
            }
            return;
        }
    }

    private MyChunk lambda$onBlockUpdate$3(int n, int n2, long l) {
        return new MyChunk(this, n, n2);
    }

    public Search() {
        super(Categories.Render, "search", "Searches for specified blocks.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.sgTracers = this.settings.createGroup("Tracers");
        this.chunks = new Long2ObjectArrayMap();
        this.blocks = this.sgGeneral.add(new BlockListSetting.Builder().name("blocks").description("Blocks to search for.").defaultValue(new ArrayList<Block>(0)).onChanged(this::lambda$new$0).build());
        this.color = this.sgRender.add(new ColorSetting.Builder().name("color").description("The color.").defaultValue(new SettingColor(0, 255, 200)).build());
        this.fullBlock = this.sgRender.add(new BoolSetting.Builder().name("full-block").description("If outlines will be rendered as full blocks.").defaultValue(false).build());
        this.tracersEnabled = this.sgTracers.add(new BoolSetting.Builder().name("tracers-enabled").description("Draws lines to the blocks.").defaultValue(false).build());
        this.tracersColor = this.sgTracers.add(new ColorSetting.Builder().name("tracers-color").description("The color of the tracers.").defaultValue(new SettingColor(225, 225, 225)).build());
        this.blockPool = new Pool<MyBlock>(this::lambda$new$1);
        this.toRemove = new LongArrayList();
        this.toUpdate = new LongArrayList();
        this.blockPos = new Mutable();
    }

    static void access$100(Search search, int n, int n2) {
        search.addToUpdate(n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void lambda$new$0(List list) {
        if (Utils.canUpdate() && this.isActive()) {
            Long2ObjectArrayMap<MyChunk> long2ObjectArrayMap = this.chunks;
            synchronized (long2ObjectArrayMap) {
                for (MyChunk myChunk : this.chunks.values()) {
                    myChunk.dispose();
                }
                this.chunks.clear();
            }
            this.searchViewDistance();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @EventHandler
    private void onTick(TickEvent.Post post) {
        Long2ObjectArrayMap<MyChunk> long2ObjectArrayMap;
        if (this.lastDimension != this.mc.world.getDimension()) {
            long2ObjectArrayMap = this.chunks;
            synchronized (long2ObjectArrayMap) {
                for (MyChunk myChunk : this.chunks.values()) {
                    myChunk.dispose();
                }
                this.chunks.clear();
            }
        }
        long2ObjectArrayMap = this.chunks;
        synchronized (long2ObjectArrayMap) {
            LongListIterator longListIterator = this.toUpdate.iterator();
            while (true) {
                if (!longListIterator.hasNext()) {
                    this.toUpdate.clear();
                    // MONITOREXIT @DISABLED, blocks:[3, 5, 6] lbl17 : MonitorExitStatement: MONITOREXIT : var2_2
                    this.lastDimension = this.mc.world.getDimension();
                    return;
                }
                long l = (Long)longListIterator.next();
                MyChunk myChunk = (MyChunk)this.chunks.get(l);
                if (myChunk == null) continue;
                myChunk.update();
            }
        }
    }

    static MinecraftClient access$500(Search search) {
        return search.mc;
    }

    static MinecraftClient access$200(Search search) {
        return search.mc;
    }

    static MinecraftClient access$400(Search search) {
        return search.mc;
    }

    static Pool access$000(Search search) {
        return search.blockPool;
    }

    @EventHandler
    private void onChunkData(ChunkDataEvent chunkDataEvent) {
        this.searchChunk((Chunk)chunkDataEvent.chunk, chunkDataEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void lambda$onBlockUpdate$4(BlockPos BlockPos2, BlockState BlockState2) {
        int n = BlockPos2.getX() >> 4;
        int n2 = BlockPos2.getZ() >> 4;
        long l = ChunkPos.toLong((int)n, (int)n2);
        Long2ObjectArrayMap<MyChunk> long2ObjectArrayMap = this.chunks;
        synchronized (long2ObjectArrayMap) {
            if (this.blocks.get().contains(BlockState2.getBlock())) {
                ((MyChunk)this.chunks.computeIfAbsent(l, arg_0 -> this.lambda$onBlockUpdate$3(n, n2, arg_0))).add(BlockPos2, true);
            } else {
                MyChunk myChunk = (MyChunk)this.chunks.get(l);
                if (myChunk == null) return;
                myChunk.remove(BlockPos2);
            }
            return;
        }
    }

    static MinecraftClient access$900(Search search) {
        return search.mc;
    }

    static Setting access$1300(Search search) {
        return search.color;
    }

    static Setting access$1000(Search search) {
        return search.fullBlock;
    }

    private void addToUpdate(int n, int n2) {
        long l = ChunkPos.toLong((int)n, (int)n2);
        if (this.chunks.containsKey(l) && !this.toUpdate.contains(l)) {
            this.toUpdate.add(l);
        }
    }

    static MinecraftClient access$1100(Search search) {
        return search.mc;
    }

    public void onBlockUpdate(BlockPos BlockPos2, BlockState BlockState2) {
        MeteorExecutor.execute(() -> this.lambda$onBlockUpdate$4(BlockPos2, BlockState2));
    }

    private MyBlock lambda$new$1() {
        return new MyBlock(this, null);
    }

    static Mutable access$1200(Search search) {
        return search.blockPos;
    }

    static Setting access$1400(Search search) {
        return search.tracersEnabled;
    }

    @Override
    public void onDeactivate() {
        for (MyChunk myChunk : this.chunks.values()) {
            myChunk.dispose();
        }
        this.chunks.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void lambda$searchChunk$2(Chunk Chunk2, ChunkDataEvent chunkDataEvent) {
        MyChunk myChunk = new MyChunk(this, Chunk2.getPos().x, Chunk2.getPos().z);
        int i = Chunk2.getPos().getStartX();
        while (true) {
            if (i <= Chunk2.getPos().getEndX()) {
            } else {
                Long2ObjectArrayMap<MyChunk> long2ObjectArrayMap = this.chunks;
                // MONITORENTER : long2ObjectArrayMap
                if (MyChunk.access$1600(myChunk).size() > 0) {
                    this.chunks.put(Chunk2.getPos().toLong(), (Object)myChunk);
                }
                // MONITOREXIT : long2ObjectArrayMap
                if (chunkDataEvent == null) return;
                ChunkDataEvent.returnChunkDataEvent(chunkDataEvent);
                return;
            }
            for (int j = Chunk2.getPos().getStartZ(); j <= Chunk2.getPos().getEndZ(); ++j) {
                int n = Chunk2.getHeightmap(Type.WORLD_SURFACE).get(i - Chunk2.getPos().getStartX(), j - Chunk2.getPos().getStartZ());
                for (int k = 0; k < n; ++k) {
                    this.blockPos.set(i, k, j);
                    BlockState BlockState2 = Chunk2.getBlockState((BlockPos)this.blockPos);
                    if (!this.blocks.get().contains(BlockState2.getBlock())) continue;
                    myChunk.add((BlockPos)this.blockPos, false);
                }
            }
            ++i;
        }
    }

    @Override
    public void onActivate() {
        this.lastDimension = this.mc.world.getDimension();
        this.searchViewDistance();
    }

    private class MyChunk {
        private final List<MyBlock> blocks;
        private final int x;
        private final int z;
        final Search this$0;

        static List access$1600(MyChunk myChunk) {
            return myChunk.blocks;
        }

        private void addToUpdateChunk(BlockPos BlockPos2) {
            Search.access$100(this.this$0, this.x, this.z);
            double d = Math.abs(BlockPos2.getX() + (BlockPos2.getX() < 0 ? 1 : 0)) % 16;
            double d2 = Math.abs(BlockPos2.getZ() + (BlockPos2.getZ() < 0 ? 1 : 0)) % 16;
            if (d == 15.0) {
                Search.access$100(this.this$0, this.x + (BlockPos2.getX() < 0 ? -1 : 1), this.z);
            } else if (d == 0.0) {
                Search.access$100(this.this$0, this.x - (BlockPos2.getX() < 0 ? -1 : 1), this.z);
            }
            if (d2 == 15.0) {
                Search.access$100(this.this$0, this.x, this.z + (BlockPos2.getZ() < 0 ? -1 : 1));
            } else if (d2 == 0.0) {
                Search.access$100(this.this$0, this.x, this.z - (BlockPos2.getZ() < 0 ? -1 : 1));
            }
        }

        public void add(BlockPos BlockPos2, boolean bl) {
            if (bl) {
                for (MyBlock myBlock : this.blocks) {
                    if (!myBlock.equals(BlockPos2)) continue;
                    return;
                }
            }
            MyBlock myBlock = (MyBlock)Search.access$000(this.this$0).get();
            myBlock.set(BlockPos2);
            this.blocks.add(myBlock);
            this.addToUpdateChunk(BlockPos2);
        }

        public boolean shouldBeDeleted() {
            int n = Search.access$200((Search)this.this$0).options.viewDistance + 1;
            return this.x > Search.access$300((Search)this.this$0).player.chunkX + n || this.x < Search.access$400((Search)this.this$0).player.chunkX - n || this.z > Search.access$500((Search)this.this$0).player.chunkZ + n || this.z < Search.access$600((Search)this.this$0).player.chunkZ - n;
        }

        public void remove(BlockPos BlockPos2) {
            for (int i = 0; i < this.blocks.size(); ++i) {
                MyBlock myBlock = this.blocks.get(i);
                if (!myBlock.equals(BlockPos2)) continue;
                this.blocks.remove(i);
                return;
            }
            this.addToUpdateChunk(BlockPos2);
        }

        public MyChunk(Search search, int n, int n2) {
            this.this$0 = search;
            this.blocks = new ArrayList<MyBlock>();
            this.x = n;
            this.z = n2;
        }

        public void render(RenderEvent renderEvent) {
            for (MyBlock myBlock : this.blocks) {
                myBlock.render(renderEvent);
            }
        }

        public void update() {
            for (MyBlock myBlock : this.blocks) {
                myBlock.updateNeighbours();
            }
        }

        public void dispose() {
            for (MyBlock myBlock : this.blocks) {
                Search.access$000(this.this$0).free(myBlock);
            }
            this.blocks.clear();
        }
    }

    private class MyBlock {
        private BlockState state;
        private int x;
        private static final int BA_RI;
        private static final int BO_LE;
        private static final int TO_FO;
        private static final int LE;
        private static final int TO_RI;
        private static final int TO;
        private static final int TO_BA;
        private static final int BA_LE;
        private static final int FO;
        private static final int BO;
        private static final int FO_LE;
        private static final int BO_BA;
        private int y;
        private static final int FO_RI;
        private static final int RI;
        private static final int TO_LE;
        private static final int BO_FO;
        private int neighbours;
        private static final int BO_RI;
        private int z;
        final Search this$0;
        private static final int BA;

        public boolean equals(BlockPos BlockPos2) {
            return this.x == BlockPos2.getX() && this.y == BlockPos2.getY() && this.z == BlockPos2.getZ();
        }

        public void render(RenderEvent renderEvent) {
            double d = this.x;
            double d2 = this.y;
            double d3 = this.z;
            double d4 = this.x + 1;
            double d5 = this.y + 1;
            double d6 = this.z + 1;
            boolean bl = true;
            if (!((Boolean)Search.access$1000(this.this$0).get()).booleanValue()) {
                VoxelShape VoxelShape2 = this.state.getOutlineShape((BlockView)Search.access$1100((Search)this.this$0).world, (BlockPos)Search.access$1200(this.this$0));
                bl = Block.isShapeFullCube((VoxelShape)VoxelShape2);
                if (!VoxelShape2.isEmpty()) {
                    d = (double)this.x + VoxelShape2.getMin(Direction.class_2351.X);
                    d2 = (double)this.y + VoxelShape2.getMin(Direction.class_2351.Y);
                    d3 = (double)this.z + VoxelShape2.getMin(Direction.class_2351.Z);
                    d4 = (double)this.x + VoxelShape2.getMax(Direction.class_2351.X);
                    d5 = (double)this.y + VoxelShape2.getMax(Direction.class_2351.Y);
                    d6 = (double)this.z + VoxelShape2.getMax(Direction.class_2351.Z);
                }
            }
            if (bl) {
                if ((this.neighbours & 0x80) != 128 && (this.neighbours & 0x20) != 32 || (this.neighbours & 0x80) == 128 && (this.neighbours & 0x20) == 32 && (this.neighbours & 0x40) != 64) {
                    Renderer.LINES.line(d, d2, d3, d, d5, d3, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 0x80) != 128 && (this.neighbours & 2) != 2 || (this.neighbours & 0x80) == 128 && (this.neighbours & 2) == 2 && (this.neighbours & 0x100) != 256) {
                    Renderer.LINES.line(d, d2, d6, d, d5, d6, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 8) != 8 && (this.neighbours & 0x20) != 32 || (this.neighbours & 8) == 8 && (this.neighbours & 0x20) == 32 && (this.neighbours & 0x10) != 16) {
                    Renderer.LINES.line(d4, d2, d3, d4, d5, d3, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 8) != 8 && (this.neighbours & 2) != 2 || (this.neighbours & 8) == 8 && (this.neighbours & 2) == 2 && (this.neighbours & 4) != 4) {
                    Renderer.LINES.line(d4, d2, d6, d4, d5, d6, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 0x20) != 32 && (this.neighbours & 0x4000) != 16384 || (this.neighbours & 0x20) != 32 && (this.neighbours & 0x10000) == 65536) {
                    Renderer.LINES.line(d, d2, d3, d4, d2, d3, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 2) != 2 && (this.neighbours & 0x4000) != 16384 || (this.neighbours & 2) != 2 && (this.neighbours & 0x8000) == 32768) {
                    Renderer.LINES.line(d, d2, d6, d4, d2, d6, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 0x20) != 32 && (this.neighbours & 0x200) != 512 || (this.neighbours & 0x20) != 32 && (this.neighbours & 0x800) == 2048) {
                    Renderer.LINES.line(d, d5, d3, d4, d5, d3, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 2) != 2 && (this.neighbours & 0x200) != 512 || (this.neighbours & 2) != 2 && (this.neighbours & 0x400) == 1024) {
                    Renderer.LINES.line(d, d5, d6, d4, d5, d6, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 0x80) != 128 && (this.neighbours & 0x4000) != 16384 || (this.neighbours & 0x80) != 128 && (this.neighbours & 0x40000) == 262144) {
                    Renderer.LINES.line(d, d2, d3, d, d2, d6, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 8) != 8 && (this.neighbours & 0x4000) != 16384 || (this.neighbours & 8) != 8 && (this.neighbours & 0x20000) == 131072) {
                    Renderer.LINES.line(d4, d2, d3, d4, d2, d6, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 0x80) != 128 && (this.neighbours & 0x200) != 512 || (this.neighbours & 0x80) != 128 && (this.neighbours & 0x2000) == 8192) {
                    Renderer.LINES.line(d, d5, d3, d, d5, d6, (Color)Search.access$1300(this.this$0).get());
                }
                if ((this.neighbours & 8) != 8 && (this.neighbours & 0x200) != 512 || (this.neighbours & 8) != 8 && (this.neighbours & 0x1000) == 4096) {
                    Renderer.LINES.line(d4, d5, d3, d4, d5, d6, (Color)Search.access$1300(this.this$0).get());
                }
            } else {
                Renderer.LINES.boxEdges(d, d2, d3, d4, d5, d6, (Color)Search.access$1300(this.this$0).get(), 0);
            }
            if (((Boolean)Search.access$1400(this.this$0).get()).booleanValue()) {
                RenderUtils.drawTracerToPos(new BlockPos(this.x, this.y, this.z), (Color)Search.access$1500(this.this$0).get(), renderEvent);
            }
        }

        static {
            BA_LE = 64;
            BO_RI = 131072;
            TO = 512;
            TO_FO = 1024;
            FO_RI = 4;
            BA_RI = 16;
            FO = 2;
            TO_BA = 2048;
            BO_FO = 32768;
            FO_LE = 256;
            BO = 16384;
            TO_LE = 8192;
            BA = 32;
            LE = 128;
            BO_LE = 262144;
            TO_RI = 4096;
            RI = 8;
            BO_BA = 65536;
        }

        private boolean isBlock(double d, double d2, double d3) {
            Search.access$800().set((double)this.x + d, (double)this.y + d2, (double)this.z + d3);
            return Search.access$900((Search)this.this$0).world.getBlockState((BlockPos)Search.access$800()).getBlock() == this.state.getBlock();
        }

        MyBlock(Search search, 1 var2_2) {
            this(search);
        }

        private MyBlock(Search search) {
            this.this$0 = search;
        }

        public void set(BlockPos BlockPos2) {
            this.x = BlockPos2.getX();
            this.y = BlockPos2.getY();
            this.z = BlockPos2.getZ();
            this.state = Search.access$700((Search)this.this$0).world.getBlockState(BlockPos2);
            this.updateNeighbours();
        }

        public void updateNeighbours() {
            this.neighbours = 0;
            if (this.isBlock(0.0, 0.0, 1.0)) {
                this.neighbours |= 2;
            }
            if (this.isBlock(1.0, 0.0, 1.0)) {
                this.neighbours |= 4;
            }
            if (this.isBlock(1.0, 0.0, 0.0)) {
                this.neighbours |= 8;
            }
            if (this.isBlock(1.0, 0.0, -1.0)) {
                this.neighbours |= 0x10;
            }
            if (this.isBlock(0.0, 0.0, -1.0)) {
                this.neighbours |= 0x20;
            }
            if (this.isBlock(-1.0, 0.0, -1.0)) {
                this.neighbours |= 0x40;
            }
            if (this.isBlock(-1.0, 0.0, 0.0)) {
                this.neighbours |= 0x80;
            }
            if (this.isBlock(-1.0, 0.0, 1.0)) {
                this.neighbours |= 0x100;
            }
            if (this.isBlock(0.0, 1.0, 0.0)) {
                this.neighbours |= 0x200;
            }
            if (this.isBlock(0.0, 1.0, 1.0)) {
                this.neighbours |= 0x400;
            }
            if (this.isBlock(0.0, 1.0, -1.0)) {
                this.neighbours |= 0x800;
            }
            if (this.isBlock(1.0, 1.0, 0.0)) {
                this.neighbours |= 0x1000;
            }
            if (this.isBlock(-1.0, 1.0, 0.0)) {
                this.neighbours |= 0x2000;
            }
            if (this.isBlock(0.0, -1.0, 0.0)) {
                this.neighbours |= 0x4000;
            }
            if (this.isBlock(0.0, -1.0, 1.0)) {
                this.neighbours |= 0x8000;
            }
            if (this.isBlock(0.0, -1.0, -1.0)) {
                this.neighbours |= 0x10000;
            }
            if (this.isBlock(1.0, -1.0, 0.0)) {
                this.neighbours |= 0x20000;
            }
            if (this.isBlock(-1.0, -1.0, 0.0)) {
                this.neighbours |= 0x40000;
            }
        }
    }
}

