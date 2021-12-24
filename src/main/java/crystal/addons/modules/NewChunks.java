package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Iterator;
import java.util.Set;

public class NewChunks extends Module {
    private final SettingGroup g = settings.getDefaultGroup();
    private final SettingGroup r = settings.createGroup("Render");
    private final Setting<Mode> b = g.add(new EnumSetting.Builder<Mode>().name("Mode").description("Detects completely new chunks using mobs/liquids/both.").defaultValue(Mode.Both).build());
    private final Setting<Boolean> REMOVE = g.add(new BoolSetting.Builder().name("remove-on-disable").description("Removes the cached chunks when disabling the module.").defaultValue(true).build());
    private final Setting<Boolean> NEW_CHUNKS_RENDER = g.add(new BoolSetting.Builder().name("new-chunks").description("Shows all the chunks that are (most likely) completely new.").defaultValue(true).build());
    private final Setting<Boolean> OLD_CHUNKS_RENDER = g.add(new BoolSetting.Builder().name("old-chunks").description("Shows all the chunks that have (most likely) been loaded before.").defaultValue(true).build());
    private final Setting<Boolean> CHUNKS_VERTICAL_RENDER = g.add(new BoolSetting.Builder().name("vertical-render").description("Shows all the chunks that are (most likely) completely new.").defaultValue(true).build());
    private final Setting<Integer> VERTICAL_RENDER_OFFSET = r.add(new IntSetting.Builder().name("vertical-offset").defaultValue(1).min(1).sliderMin(1).sliderMax(8).build());
    private final Setting<Integer> VERTICAL_RENDER_MAX_DISTANCE = r.add(new IntSetting.Builder().name("vertical-max-distance").defaultValue(4).min(1).sliderMin(1).sliderMax(15).build());
    private final Setting<Integer> VERTICAL_RENDER_TRANSPARENT = r.add(new IntSetting.Builder().name("vertical-transparent").defaultValue(64).min(1).max(255).sliderMin(1).sliderMax(255).build());
    private final Setting<SettingColor> NEW_CHUNKS_COLOR = r.add(new ColorSetting.Builder().name("new-chunks-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(0, 255, 0, 128)).build());
    private final Setting<SettingColor> OLD_CHUNKS_COLOR = r.add(new ColorSetting.Builder().name("old-chunks-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(255, 0, 0, 128)).build());

    private Set<ChunkPos> newChunks;
    private Set<ChunkPos> oldChunks;

    public NewChunks() {
        super(CrystalCL.PvE, "new-chunks", "Detects completely new chunks using certain traits of them.");
    }

    public void onDeactivate() {
        if (REMOVE.get()) {
            newChunks.clear();
            oldChunks.clear();
        }
    }

    @EventHandler
    private void onReadPacket(PacketEvent.Receive e) {
        if (b.get() == Mode.Liquids || b.get() == Mode.Both) {
            Direction[] searchDirs = new Direction[]{Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.UP};
            if (e.packet instanceof ChunkDeltaUpdateS2CPacket p) {
                p.visitUpdates((posx, state) -> {
                    if (!state.getFluidState().isEmpty() && !state.getFluidState().isStill()) {
                        ChunkPos chunkPos = new ChunkPos(posx);
                        Direction[] var5 = searchDirs;
                        int var6 = searchDirs.length;

                        for (int var7 = 0; var7 < var6; ++var7) {
                            Direction dir = var5[var7];
                            if (mc.world.getBlockState(posx.offset(dir)).getFluidState().isStill() && !oldChunks.contains(chunkPos)) {
                                newChunks.add(chunkPos);
                                return;
                            }
                        }
                    }

                });
            } else {
                ChunkPos pos;
                int x;
                int y;
                if (e.packet instanceof BlockUpdateS2CPacket p) {
                    if (!p.getState().getFluidState().isEmpty() && !p.getState().getFluidState().isStill()) {
                        pos = new ChunkPos(p.getPos());
                        Direction[] var5 = searchDirs;
                        x = searchDirs.length;

                        for (y = 0; y < x; y++) {
                            Direction dir = var5[y];
                            if (mc.world.getBlockState(p.getPos().offset(dir)).getFluidState().isStill() && !oldChunks.contains(pos)) {
                                newChunks.add(pos);
                                return;
                            }
                        }
                    }
                } else if (e.packet instanceof ChunkDataS2CPacket p && mc.world != null) {
                    pos = new ChunkPos(p.getX(), p.getZ());
                    if (!newChunks.contains(pos) && mc.world.getChunkManager().getChunk(p.getX(), p.getZ()) == null) {
                        WorldChunk chunk = new WorldChunk(mc.world, pos);
                        chunk.loadFromPacket(p.getChunkData().getSectionsDataBuf(), new NbtCompound(), p.getChunkData().getBlockEntities(p.getX(), p.getZ()));

                        for (x = 0; x < 16; x++) {
                            for (y = 0; y < mc.world.getHeight(); y++) {
                                for (int z = 0; z < 16; z++) {
                                    FluidState fluid = chunk.getFluidState(x, y, z);
                                    if (!fluid.isEmpty() && !fluid.isStill()) {
                                        oldChunks.add(pos);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void WorldRender(Render3DEvent e) {
        SettingColor trans;
        Iterator var4;
        ChunkPos s;
        Iterator var10;
        if (NEW_CHUNKS_RENDER.get()) {
            synchronized (newChunks) {
                if (CHUNKS_VERTICAL_RENDER.get()) {
                    trans = new SettingColor(NEW_CHUNKS_COLOR.get());
                    trans.a = VERTICAL_RENDER_TRANSPARENT.get();
                    var4 = newChunks.iterator();

                    while (var4.hasNext()) {
                        s = (ChunkPos) var4.next();
                        if (Utils.distance(mc.player.getX(), mc.player.getY(), mc.player.getZ(), (s.getStartX() + 7), mc.player.getY(), (s.getStartZ() + 7)) <= (VERTICAL_RENDER_MAX_DISTANCE.get() * 16)) {
                            e.renderer.box(s.getStartX() + 7.5D + VERTICAL_RENDER_OFFSET.get(), 0.0D, s.getStartZ() + 7.5D + VERTICAL_RENDER_OFFSET.get(), s.getEndX() - 6.5D - VERTICAL_RENDER_OFFSET.get(), 255.0D, s.getEndZ() - 6.5D - VERTICAL_RENDER_OFFSET.get(), trans, trans, ShapeMode.Both, 6);
                        }
                    }
                }

                var10 = newChunks.iterator();

                while (var10.hasNext()) {
                    s = (ChunkPos) var10.next();
                    e.renderer.box((s.getStartX() + 1), 0.0D, (s.getStartZ() + 1), s.getEndX(), 0.0D, s.getEndZ(), NEW_CHUNKS_COLOR.get(), NEW_CHUNKS_COLOR.get(), ShapeMode.Both, 124);
                }
            }
        }

        if (OLD_CHUNKS_RENDER.get()) {
            synchronized (oldChunks) {
                if (CHUNKS_VERTICAL_RENDER.get()) {
                    trans = new SettingColor(OLD_CHUNKS_COLOR.get());
                    trans.a = VERTICAL_RENDER_TRANSPARENT.get();
                    var4 = oldChunks.iterator();

                    while (var4.hasNext()) {
                        s = (ChunkPos) var4.next();
                        if (Utils.distance(mc.player.getX(), mc.player.getY(), mc.player.getZ(), (s.getStartX() + 7), mc.player.getY(), (s.getStartZ() + 7)) <= (VERTICAL_RENDER_MAX_DISTANCE.get() * 16)) {
                            e.renderer.box(s.getStartX() + 7.5D + VERTICAL_RENDER_OFFSET.get(), 0.0D, s.getStartZ() + 7.5D + VERTICAL_RENDER_OFFSET.get(), s.getEndX() - 6.5D - VERTICAL_RENDER_OFFSET.get(), 255.0D, s.getEndZ() - 6.5D - VERTICAL_RENDER_OFFSET.get(), trans, trans, ShapeMode.Both, 6);
                        }
                    }
                }

                var10 = oldChunks.iterator();

                while (var10.hasNext()) {
                    s = (ChunkPos) var10.next();
                    e.renderer.box((s.getStartX() + 1), 0.0D, (s.getStartZ() + 1), s.getEndX(), 0.0D, s.getEndZ(), OLD_CHUNKS_COLOR.get(), OLD_CHUNKS_COLOR.get(), ShapeMode.Both, 124);
                }
            }
        }
    }

    public enum Mode {
        Mobs,
        Liquids,
        Both
    }
}
