package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class AntiGhost extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Boolean> predictState = sgGeneral.add(new BoolSetting.Builder().name("predict-block-state").description("predict block state").defaultValue(true).build());

    AtomicBoolean manual = new AtomicBoolean(false);
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private final Map<BlockPos, BlockState> sblocks = new ConcurrentHashMap();
    private final Map<BlockPos, Long> dblocks = new ConcurrentHashMap();
    private final Map<BlockPos, BlockState> bblocks = new ConcurrentHashMap();

    public AntiGhost() {
        super(CrystalCL.Exc, "anti-ghost-block", "Only for pvp!");
    }

    public void onActivate() {
        ClearInfo();
    }

    @EventHandler(priority = 200)
    private void onTick(TickEvent.Pre event) {
        dblocks.entrySet().stream().filter((entry) -> entry.getValue() > GetCurTime()).map(Entry::getKey).forEach(this::SyncBlockState);
    }

    private void onSetBlockStateOld(SetBlockStateEvent event) {
        if (!manual.get()) {
            BlockPos block = event.pos;
            BlockState synced_state = sblocks.get(block);
            if (synced_state != null && synced_state == event.newState) {
                dblocks.remove(block);
                if (event.oldState != null && event.oldState.getBlock() instanceof BedBlock && event.newState.getMaterial().isReplaceable()) {
                    BlockState state = event.oldState;
                    Direction dir = BedBlock.getOppositePartDirection(state);
                    SyncBlockState(block.offset(dir));
                }
            } else if (predictState.get()) {
                dblocks.put(block, GetResponseTime());
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = 200)
    private void onSetBlockState(SetBlockStateEvent event) {
        if (!manual.get()) {
            BlockPos block = event.pos;
            BlockState synced_state = sblocks.get(block);
            if (synced_state == null && event.oldState != null) {
                sblocks.put(block, event.oldState);
            } else if (synced_state == event.newState) {
                dblocks.remove(block);
                if (event.oldState != null && event.oldState.getBlock() instanceof BedBlock && event.newState.isAir()) {
                    RemoveBlock(block.offset(BedBlock.getOppositePartDirection(event.oldState)), false);
                }
            } else if (predictState.get()) {
                dblocks.put(block, GetResponseTime());
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = 200)
    private void onPacketReceive(PacketEvent.Receive event) {
        assert mc.world != null;
        assert mc.player != null;

        if (event.packet instanceof BlockUpdateS2CPacket packet) {
            sblocks.put(packet.getPos(), packet.getState());
        } else if (event.packet instanceof ChunkDeltaUpdateS2CPacket packet) {
            Map var10001 = sblocks;
            Objects.requireNonNull(var10001);
            packet.visitUpdates(var10001::put);
        } else if (event.packet instanceof PlayerActionResponseS2CPacket packet) {
            sblocks.put(packet.pos(), packet.state());
        } else if (event.packet instanceof BlockEventS2CPacket packet) {
            BlockPos pos = packet.getPos();
            Block block = packet.getBlock();
            BlockState synced_state = sblocks.get(pos);
            if (synced_state == null) {
                return;
            }

            if (synced_state.isOf(block)) {
                return;
            }

            mc.world.setBlockStateWithoutNeighborUpdates(pos, block.getDefaultState());
        } else if (event.packet instanceof UnloadChunkS2CPacket packet) {
            ChunkPos unload_chunk_pos = new ChunkPos(packet.getX(), packet.getZ());
            sblocks.keySet().removeIf((b) -> (new ChunkPos(b)).equals(unload_chunk_pos));
            dblocks.keySet().removeIf((b) -> (new ChunkPos(b)).equals(unload_chunk_pos));
            bblocks.keySet().removeIf((b) -> (new ChunkPos(b)).equals(unload_chunk_pos));
        } else if (event.packet instanceof GameJoinS2CPacket) {
            ClearInfo();
        }
    }

    private boolean CustomEquals(BlockState state1, BlockState state2) {
        return state1.equals(state2);
    }

    private void ClearInfo() {
        sblocks.clear();
        dblocks.clear();
        bblocks.clear();
    }

    public void SyncBlockState(BlockPos block) {
        assert mc.world != null;

        BlockState client_state = mc.world.getBlockState(block);
        BlockState server_state = sblocks.get(block);
        if (server_state == null) {
            RemoveBlock(block, false);
        } else if (server_state != client_state) {
            mc.world.setBlockStateWithoutNeighborUpdates(block, server_state);
        }
    }

    public void RemoveBlock(BlockPos block, boolean move) {
        assert mc.world != null;

        manual.set(true);
        mc.world.removeBlock(block, move);
        manual.set(false);
    }

    public void SetBlockState(BlockPos block, BlockState state) {
        assert mc.world != null;

        manual.set(true);
        mc.world.setBlockStateWithoutNeighborUpdates(block, state);
        manual.set(false);
    }

    public boolean IsBlockSynced(BlockPos block) {
        return !dblocks.containsKey(block);
    }

    public static long GetCurTime() {
        return Util.getMeasuringTimeMs();
    }

    public static long GetLatency3() {
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        return Math.max(playerListEntry.getLatency(), GetIntervalPerTick()) * 2L + GetIntervalPerTick() * 10L;
    }

    public static long GetIntervalPerTick() {
        long interval = 50L;
        return (Modules.get().get(ExtraSurround.class)).isActive() ? interval : 50L;
    }

    public static long GetResponseTime() {
        return Util.getMeasuringTimeMs() + GetLatency3();
    }
}
