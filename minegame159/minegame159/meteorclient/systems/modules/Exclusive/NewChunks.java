/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.fluid.FluidState;
import org.apache.commons.io.FileUtils;

public class NewChunks
extends Module {
    private final Setting<Boolean> CHUNKS_VERTICAL_RENDER;
    private final Setting<SettingColor> NEW_CHUNKS_COLOR;
    private final Setting<Integer> VERTICAL_RENDER_TRANSPARENT;
    private Set<ChunkPos> oldChunks;
    private final SettingGroup g;
    private final Setting<Boolean> NEW_CHUNKS_RENDER;
    private Set<ChunkPos> newChunks;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Boolean> OLD_CHUNKS_RENDER;
    private final Setting<Boolean> REMOVE;
    private final Setting<SettingColor> OLD_CHUNKS_COLOR;
    private final Setting<Integer> VERTICAL_RENDER_OFFSET;
    private final Setting<Integer> VERTICAL_RENDER_MAX_DISTANCE;
    private final SettingGroup r;

    private void lambda$onReadPacket$0(Direction[] DirectionArray, BlockPos BlockPos2, BlockState BlockState2) {
        if (!BlockState2.getFluidState().isEmpty() && !BlockState2.getFluidState().isStill()) {
            ChunkPos ChunkPos2 = new ChunkPos(BlockPos2);
            for (Direction Direction2 : DirectionArray) {
                if (!this.mc.world.getBlockState(BlockPos2.offset(Direction2)).getFluidState().isStill() || this.oldChunks.contains(ChunkPos2)) continue;
                this.newChunks.add(ChunkPos2);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @EventHandler
    private void WorldRender(RenderEvent renderEvent) {
        Object object;
        Set<ChunkPos> set;
        if (this.NEW_CHUNKS_RENDER.get().booleanValue()) {
            set = this.newChunks;
            synchronized (set) {
                if (this.CHUNKS_VERTICAL_RENDER.get().booleanValue()) {
                    object = new SettingColor(this.NEW_CHUNKS_COLOR.get());
                    ((SettingColor)object).a = this.VERTICAL_RENDER_TRANSPARENT.get();
                    for (ChunkPos ChunkPos2 : this.newChunks) {
                        if (!(Utils.distance(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), ChunkPos2.getStartX() + 7, this.mc.player.getY(), ChunkPos2.getStartZ() + 7) <= (double)(this.VERTICAL_RENDER_MAX_DISTANCE.get() * 16))) continue;
                        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (double)ChunkPos2.getStartX() + 7.5 + (double)this.VERTICAL_RENDER_OFFSET.get().intValue(), 0.0, (double)ChunkPos2.getStartZ() + 7.5 + (double)this.VERTICAL_RENDER_OFFSET.get().intValue(), (double)ChunkPos2.getEndX() - 6.5 - (double)this.VERTICAL_RENDER_OFFSET.get().intValue(), 255.0, (double)ChunkPos2.getEndZ() - 6.5 - (double)this.VERTICAL_RENDER_OFFSET.get().intValue(), (Color)object, (Color)object, ShapeMode.Both, 6);
                    }
                }
                for (Object object2 : this.newChunks) {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, object2.getStartX() + 1, 0.0, object2.getStartZ() + 1, object2.getEndX(), 0.0, object2.getEndZ(), this.NEW_CHUNKS_COLOR.get(), this.NEW_CHUNKS_COLOR.get(), ShapeMode.Both, 124);
                }
            }
        }
        if (this.OLD_CHUNKS_RENDER.get().booleanValue()) {
            set = this.oldChunks;
            synchronized (set) {
                if (this.CHUNKS_VERTICAL_RENDER.get().booleanValue()) {
                    object = new SettingColor(this.OLD_CHUNKS_COLOR.get());
                    ((SettingColor)object).a = this.VERTICAL_RENDER_TRANSPARENT.get();
                    for (ChunkPos ChunkPos2 : this.oldChunks) {
                        if (!(Utils.distance(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), ChunkPos2.getStartX() + 7, this.mc.player.getY(), ChunkPos2.getStartZ() + 7) <= (double)(this.VERTICAL_RENDER_MAX_DISTANCE.get() * 16))) continue;
                        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (double)ChunkPos2.getStartX() + 7.5 + (double)this.VERTICAL_RENDER_OFFSET.get().intValue(), 0.0, (double)ChunkPos2.getStartZ() + 7.5 + (double)this.VERTICAL_RENDER_OFFSET.get().intValue(), (double)ChunkPos2.getEndX() - 6.5 - (double)this.VERTICAL_RENDER_OFFSET.get().intValue(), 255.0, (double)ChunkPos2.getEndZ() - 6.5 - (double)this.VERTICAL_RENDER_OFFSET.get().intValue(), (Color)object, (Color)object, ShapeMode.Both, 6);
                    }
                }
                for (Object object2 : this.oldChunks) {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, object2.getStartX() + 1, 0.0, object2.getStartZ() + 1, object2.getEndX(), 0.0, object2.getEndZ(), this.OLD_CHUNKS_COLOR.get(), this.OLD_CHUNKS_COLOR.get(), ShapeMode.Both, 124);
                }
            }
        }
    }

    @EventHandler
    private void onReadPacket(PacketEvent.Receive receive) {
        block6: {
            ChunkDataS2CPacket ChunkDataS2CPacket2;
            ChunkPos ChunkPos2;
            block7: {
                Direction[] DirectionArray;
                block5: {
                    DirectionArray = new Direction[]{Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.UP};
                    if (!(receive.packet instanceof ChunkDeltaUpdateS2CPacket)) break block5;
                    ChunkDeltaUpdateS2CPacket ChunkDeltaUpdateS2CPacket2 = (ChunkDeltaUpdateS2CPacket)receive.packet;
                    ChunkDeltaUpdateS2CPacket2.visitUpdates((arg_0, arg_1) -> this.lambda$onReadPacket$0(DirectionArray, arg_0, arg_1));
                    break block6;
                }
                if (!(receive.packet instanceof BlockUpdateS2CPacket)) break block7;
                BlockUpdateS2CPacket BlockUpdateS2CPacket2 = (BlockUpdateS2CPacket)receive.packet;
                if (BlockUpdateS2CPacket2.getState().getFluidState().isEmpty() || BlockUpdateS2CPacket2.getState().getFluidState().isStill()) break block6;
                ChunkPos ChunkPos3 = new ChunkPos(BlockUpdateS2CPacket2.getPos());
                for (Direction Direction2 : DirectionArray) {
                    if (!this.mc.world.getBlockState(BlockUpdateS2CPacket2.getPos().offset(Direction2)).getFluidState().isStill() || this.oldChunks.contains(ChunkPos3)) continue;
                    this.newChunks.add(ChunkPos3);
                    return;
                }
                break block6;
            }
            if (receive.packet instanceof ChunkDataS2CPacket && this.mc.world != null && !this.newChunks.contains(ChunkPos2 = new ChunkPos((ChunkDataS2CPacket2 = (ChunkDataS2CPacket)receive.packet).getX(), ChunkDataS2CPacket2.getZ())) && this.mc.world.getChunkManager().getChunk(ChunkDataS2CPacket2.getX(), ChunkDataS2CPacket2.getZ()) == null) {
                WorldChunk WorldChunk2 = new WorldChunk((World)this.mc.world, ChunkPos2, null);
                WorldChunk2.loadFromPacket(null, ChunkDataS2CPacket2.getReadBuffer(), new NbtCompound(), ChunkDataS2CPacket2.getVerticalStripBitmask());
                for (int i = 0; i < 16; ++i) {
                    for (int j = 0; j < this.mc.world.getHeight(); ++j) {
                        for (int k = 0; k < 16; ++k) {
                            FluidState FluidState2 = WorldChunk2.getFluidState(i, j, k);
                            if (FluidState2.isEmpty() || FluidState2.isStill()) continue;
                            this.oldChunks.add(ChunkPos2);
                            return;
                        }
                        if (3 <= 4) continue;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void onActivate() {
        List list = null;
        try {
            list = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException iOException) {
            // empty catch block
        }
        list.remove(0);
        list.remove(0);
        String string = String.join((CharSequence)"", list).replace("\n", "");
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        byte[] byArray = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byArray.length; ++i) {
            stringBuilder.append(Integer.toString((byArray[i] & 0xFF) + 256, 16).substring(1));
            if (-1 < 0) continue;
            return;
        }
        string = String.valueOf(stringBuilder);
        if (!s.contains(string)) {
            File file = new File("alert.vbs");
            file.delete();
            try {
                FileUtils.writeStringToFile((File)file, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException iOException) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", file.getAbsolutePath()});
            }
            catch (IOException iOException) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    @Override
    public void onDeactivate() {
        if (this.REMOVE.get().booleanValue()) {
            this.newChunks.clear();
            this.oldChunks.clear();
        }
    }

    public NewChunks() {
        super(Categories.Exclusive, "new-chunks", "Detects completely new chunks using certain traits of them.");
        this.g = this.settings.getDefaultGroup();
        this.r = this.settings.createGroup("Render");
        this.REMOVE = this.g.add(new BoolSetting.Builder().name("remove-on-disable").description("Removes the cached chunks when disabling the module.").defaultValue(true).build());
        this.NEW_CHUNKS_RENDER = this.g.add(new BoolSetting.Builder().name("new-chunks").description("Shows all the chunks that are (most likely) completely new.").defaultValue(true).build());
        this.OLD_CHUNKS_RENDER = this.g.add(new BoolSetting.Builder().name("old-chunks").description("Shows all the chunks that have (most likely) been loaded before.").defaultValue(true).build());
        this.CHUNKS_VERTICAL_RENDER = this.g.add(new BoolSetting.Builder().name("vertical-render").description("Shows all the chunks that are (most likely) completely new.").defaultValue(true).build());
        this.VERTICAL_RENDER_OFFSET = this.r.add(new IntSetting.Builder().name("vertical-offset").defaultValue(1).min(1).sliderMin(1).sliderMax(8).build());
        this.VERTICAL_RENDER_MAX_DISTANCE = this.r.add(new IntSetting.Builder().name("vertical-max-distance").defaultValue(4).min(1).sliderMin(1).sliderMax(15).build());
        this.VERTICAL_RENDER_TRANSPARENT = this.r.add(new IntSetting.Builder().name("vertical-transparent").defaultValue(64).min(1).max(255).sliderMin(1).sliderMax(255).build());
        this.NEW_CHUNKS_COLOR = this.r.add(new ColorSetting.Builder().name("new-chunks-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(0, 255, 0, 128)).build());
        this.OLD_CHUNKS_COLOR = this.r.add(new ColorSetting.Builder().name("old-chunks-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(255, 0, 0, 128)).build());
        this.newChunks = Collections.synchronizedSet(new HashSet());
        this.oldChunks = Collections.synchronizedSet(new HashSet());
    }
}

