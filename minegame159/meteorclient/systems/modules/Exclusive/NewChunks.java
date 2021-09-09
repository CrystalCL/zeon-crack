/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraft.world.World
 *  net.minecraft.util.math.Direction
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket
 *  net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket
 *  net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket
 *  net.minecraft.world.chunk.WorldChunk
 *  net.minecraft.fluid.FluidState
 *  org.apache.commons.io.FileUtils
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
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.util.math.Direction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.fluid.FluidState;
import org.apache.commons.io.FileUtils;

public class NewChunks
extends Module {
    private final /* synthetic */ Setting<Boolean> REMOVE;
    private final /* synthetic */ Setting<Integer> VERTICAL_RENDER_OFFSET;
    private /* synthetic */ Set<ChunkPos> newChunks;
    private final /* synthetic */ Setting<SettingColor> NEW_CHUNKS_COLOR;
    private final /* synthetic */ Setting<Boolean> NEW_CHUNKS_RENDER;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<SettingColor> OLD_CHUNKS_COLOR;
    private final /* synthetic */ Setting<Boolean> OLD_CHUNKS_RENDER;
    private final /* synthetic */ Setting<Integer> VERTICAL_RENDER_TRANSPARENT;
    private final /* synthetic */ SettingGroup r;
    private final /* synthetic */ Setting<Integer> VERTICAL_RENDER_MAX_DISTANCE;
    private final /* synthetic */ Setting<Boolean> CHUNKS_VERTICAL_RENDER;
    private final /* synthetic */ SettingGroup g;
    private /* synthetic */ Set<ChunkPos> oldChunks;

    @Override
    public void onActivate() {
        List lIllIIlIIIlllII = null;
        try {
            lIllIIlIIIlllII = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIllIIlIIIlIllI) {
            // empty catch block
        }
        lIllIIlIIIlllII.remove(0);
        lIllIIlIIIlllII.remove(0);
        String lIllIIlIIIllIll = String.join((CharSequence)"", lIllIIlIIIlllII).replace("\n", "");
        MessageDigest lIllIIlIIIllIlI = null;
        try {
            lIllIIlIIIllIlI = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIllIIlIIIlIlII) {
            // empty catch block
        }
        byte[] lIllIIlIIIllIIl = lIllIIlIIIllIlI.digest(lIllIIlIIIllIll.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIllIIlIIIllIII = new StringBuilder();
        for (int lIllIIlIIIlllll = 0; lIllIIlIIIlllll < lIllIIlIIIllIIl.length; ++lIllIIlIIIlllll) {
            lIllIIlIIIllIII.append(Integer.toString((lIllIIlIIIllIIl[lIllIIlIIIlllll] & 0xFF) + 256, 16).substring(1));
        }
        lIllIIlIIIllIll = String.valueOf(lIllIIlIIIllIII);
        if (!s.contains(lIllIIlIIIllIll)) {
            File lIllIIlIIIllllI = new File("alert.vbs");
            lIllIIlIIIllllI.delete();
            try {
                FileUtils.writeStringToFile((File)lIllIIlIIIllllI, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIllIIlIIIlIIIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIllIIlIIIllllI.getAbsolutePath()});
            }
            catch (IOException lIllIIlIIIlIIIl) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    @Override
    public void onDeactivate() {
        NewChunks lIllIIlIIIIllll;
        if (lIllIIlIIIIllll.REMOVE.get().booleanValue()) {
            lIllIIlIIIIllll.newChunks.clear();
            lIllIIlIIIIllll.oldChunks.clear();
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @EventHandler
    private void onReadPacket(PacketEvent.Receive lIllIIIllllIlll) {
        block6: {
            ChunkDataS2CPacket lIllIIIlllllIlI;
            ChunkPos lIllIIIlllllIIl;
            NewChunks lIllIIIllllIlIl;
            block7: {
                Direction[] lIllIIIllllIllI;
                block5: {
                    lIllIIIllllIllI = new Direction[]{Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.UP};
                    if (!(lIllIIIllllIlll.packet instanceof ChunkDeltaUpdateS2CPacket)) break block5;
                    ChunkDeltaUpdateS2CPacket lIllIIlIIIIIIll = (ChunkDeltaUpdateS2CPacket)lIllIIIllllIlll.packet;
                    lIllIIlIIIIIIll.visitUpdates((lIllIIIllIIlIII, lIllIIIllIIIIll) -> {
                        if (!lIllIIIllIIIIll.getFluidState().isEmpty() && !lIllIIIllIIIIll.getFluidState().isStill()) {
                            ChunkPos lIllIIIllIIlIll = new ChunkPos(lIllIIIllIIlIII);
                            for (Direction lIllIIIllIIllII : lIllIIIllllIllI) {
                                NewChunks lIllIIIllIIIllI;
                                if (!lIllIIIllIIIllI.mc.world.getBlockState(lIllIIIllIIlIII.offset(lIllIIIllIIllII)).getFluidState().isStill() || lIllIIIllIIIllI.oldChunks.contains((Object)lIllIIIllIIlIll)) continue;
                                lIllIIIllIIIllI.newChunks.add(lIllIIIllIIlIll);
                                return;
                            }
                        }
                    });
                    break block6;
                }
                if (!(lIllIIIllllIlll.packet instanceof BlockUpdateS2CPacket)) break block7;
                BlockUpdateS2CPacket lIllIIlIIIIIIII = (BlockUpdateS2CPacket)lIllIIIllllIlll.packet;
                if (lIllIIlIIIIIIII.getState().getFluidState().isEmpty() || lIllIIlIIIIIIII.getState().getFluidState().isStill()) break block6;
                ChunkPos lIllIIlIIIIIIIl = new ChunkPos(lIllIIlIIIIIIII.getPos());
                for (Direction lIllIIlIIIIIIlI : lIllIIIllllIllI) {
                    if (!lIllIIIllllIlIl.mc.world.getBlockState(lIllIIlIIIIIIII.getPos().offset(lIllIIlIIIIIIlI)).getFluidState().isStill() || lIllIIIllllIlIl.oldChunks.contains((Object)lIllIIlIIIIIIIl)) continue;
                    lIllIIIllllIlIl.newChunks.add(lIllIIlIIIIIIIl);
                    return;
                }
                break block6;
            }
            if (lIllIIIllllIlll.packet instanceof ChunkDataS2CPacket && lIllIIIllllIlIl.mc.world != null && !lIllIIIllllIlIl.newChunks.contains((Object)(lIllIIIlllllIIl = new ChunkPos((lIllIIIlllllIlI = (ChunkDataS2CPacket)lIllIIIllllIlll.packet).getX(), lIllIIIlllllIlI.getZ()))) && lIllIIIllllIlIl.mc.world.getChunkManager().getChunk(lIllIIIlllllIlI.getX(), lIllIIIlllllIlI.getZ()) == null) {
                WorldChunk lIllIIIlllllIll = new WorldChunk((World)lIllIIIllllIlIl.mc.world, lIllIIIlllllIIl, null);
                lIllIIIlllllIll.loadFromPacket(null, lIllIIIlllllIlI.getReadBuffer(), new NbtCompound(), lIllIIIlllllIlI.getVerticalStripBitmask());
                for (int lIllIIIllllllII = 0; lIllIIIllllllII < 16; ++lIllIIIllllllII) {
                    for (int lIllIIIllllllIl = 0; lIllIIIllllllIl < lIllIIIllllIlIl.mc.world.getHeight(); ++lIllIIIllllllIl) {
                        for (int lIllIIIlllllllI = 0; lIllIIIlllllllI < 16; ++lIllIIIlllllllI) {
                            FluidState lIllIIIllllllll = lIllIIIlllllIll.getFluidState(lIllIIIllllllII, lIllIIIllllllIl, lIllIIIlllllllI);
                            if (lIllIIIllllllll.isEmpty() || lIllIIIllllllll.isStill()) continue;
                            lIllIIIllllIlIl.oldChunks.add(lIllIIIlllllIIl);
                            return;
                        }
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private void WorldRender(RenderEvent lIllIIIllIlllIl) {
        Set<ChunkPos> lIllIIIllIllIll;
        NewChunks lIllIIIllIllllI;
        if (lIllIIIllIllllI.NEW_CHUNKS_RENDER.get().booleanValue()) {
            lIllIIIllIllIll = lIllIIIllIllllI.newChunks;
            synchronized (lIllIIIllIllIll) {
                if (lIllIIIllIllllI.CHUNKS_VERTICAL_RENDER.get().booleanValue()) {
                    SettingColor lIllIIIlllIIIll = new SettingColor(lIllIIIllIllllI.NEW_CHUNKS_COLOR.get());
                    lIllIIIlllIIIll.a = lIllIIIllIllllI.VERTICAL_RENDER_TRANSPARENT.get();
                    for (ChunkPos lIllIIIlllIIlII : lIllIIIllIllllI.newChunks) {
                        if (!(Utils.distance(lIllIIIllIllllI.mc.player.getX(), lIllIIIllIllllI.mc.player.getY(), lIllIIIllIllllI.mc.player.getZ(), lIllIIIlllIIlII.getStartX() + 7, lIllIIIllIllllI.mc.player.getY(), lIllIIIlllIIlII.getStartZ() + 7) <= (double)(lIllIIIllIllllI.VERTICAL_RENDER_MAX_DISTANCE.get() * 16))) continue;
                        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (double)lIllIIIlllIIlII.getStartX() + 7.5 + (double)lIllIIIllIllllI.VERTICAL_RENDER_OFFSET.get().intValue(), 0.0, (double)lIllIIIlllIIlII.getStartZ() + 7.5 + (double)lIllIIIllIllllI.VERTICAL_RENDER_OFFSET.get().intValue(), (double)lIllIIIlllIIlII.getEndX() - 6.5 - (double)lIllIIIllIllllI.VERTICAL_RENDER_OFFSET.get().intValue(), 255.0, (double)lIllIIIlllIIlII.getEndZ() - 6.5 - (double)lIllIIIllIllllI.VERTICAL_RENDER_OFFSET.get().intValue(), lIllIIIlllIIIll, lIllIIIlllIIIll, ShapeMode.Both, 6);
                    }
                }
                for (ChunkPos ChunkPos2 : lIllIIIllIllllI.newChunks) {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, ChunkPos2.getStartX() + 1, 0.0, ChunkPos2.getStartZ() + 1, ChunkPos2.getEndX(), 0.0, ChunkPos2.getEndZ(), lIllIIIllIllllI.NEW_CHUNKS_COLOR.get(), lIllIIIllIllllI.NEW_CHUNKS_COLOR.get(), ShapeMode.Both, 124);
                }
            }
        }
        if (lIllIIIllIllllI.OLD_CHUNKS_RENDER.get().booleanValue()) {
            lIllIIIllIllIll = lIllIIIllIllllI.oldChunks;
            synchronized (lIllIIIllIllIll) {
                if (lIllIIIllIllllI.CHUNKS_VERTICAL_RENDER.get().booleanValue()) {
                    SettingColor lIllIIIlllIIIII = new SettingColor(lIllIIIllIllllI.OLD_CHUNKS_COLOR.get());
                    lIllIIIlllIIIII.a = lIllIIIllIllllI.VERTICAL_RENDER_TRANSPARENT.get();
                    for (ChunkPos lIllIIIlllIIIIl : lIllIIIllIllllI.oldChunks) {
                        if (!(Utils.distance(lIllIIIllIllllI.mc.player.getX(), lIllIIIllIllllI.mc.player.getY(), lIllIIIllIllllI.mc.player.getZ(), lIllIIIlllIIIIl.getStartX() + 7, lIllIIIllIllllI.mc.player.getY(), lIllIIIlllIIIIl.getStartZ() + 7) <= (double)(lIllIIIllIllllI.VERTICAL_RENDER_MAX_DISTANCE.get() * 16))) continue;
                        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (double)lIllIIIlllIIIIl.getStartX() + 7.5 + (double)lIllIIIllIllllI.VERTICAL_RENDER_OFFSET.get().intValue(), 0.0, (double)lIllIIIlllIIIIl.getStartZ() + 7.5 + (double)lIllIIIllIllllI.VERTICAL_RENDER_OFFSET.get().intValue(), (double)lIllIIIlllIIIIl.getEndX() - 6.5 - (double)lIllIIIllIllllI.VERTICAL_RENDER_OFFSET.get().intValue(), 255.0, (double)lIllIIIlllIIIIl.getEndZ() - 6.5 - (double)lIllIIIllIllllI.VERTICAL_RENDER_OFFSET.get().intValue(), lIllIIIlllIIIII, lIllIIIlllIIIII, ShapeMode.Both, 6);
                    }
                }
                for (ChunkPos ChunkPos3 : lIllIIIllIllllI.oldChunks) {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, ChunkPos3.getStartX() + 1, 0.0, ChunkPos3.getStartZ() + 1, ChunkPos3.getEndX(), 0.0, ChunkPos3.getEndZ(), lIllIIIllIllllI.OLD_CHUNKS_COLOR.get(), lIllIIIllIllllI.OLD_CHUNKS_COLOR.get(), ShapeMode.Both, 124);
                }
            }
        }
    }

    public NewChunks() {
        super(Categories.Exclusive, "new-chunks", "Detects completely new chunks using certain traits of them.");
        NewChunks lIllIIlIIlIlIII;
        lIllIIlIIlIlIII.g = lIllIIlIIlIlIII.settings.getDefaultGroup();
        lIllIIlIIlIlIII.r = lIllIIlIIlIlIII.settings.createGroup("Render");
        lIllIIlIIlIlIII.REMOVE = lIllIIlIIlIlIII.g.add(new BoolSetting.Builder().name("remove-on-disable").description("Removes the cached chunks when disabling the module.").defaultValue(true).build());
        lIllIIlIIlIlIII.NEW_CHUNKS_RENDER = lIllIIlIIlIlIII.g.add(new BoolSetting.Builder().name("new-chunks").description("Shows all the chunks that are (most likely) completely new.").defaultValue(true).build());
        lIllIIlIIlIlIII.OLD_CHUNKS_RENDER = lIllIIlIIlIlIII.g.add(new BoolSetting.Builder().name("old-chunks").description("Shows all the chunks that have (most likely) been loaded before.").defaultValue(true).build());
        lIllIIlIIlIlIII.CHUNKS_VERTICAL_RENDER = lIllIIlIIlIlIII.g.add(new BoolSetting.Builder().name("vertical-render").description("Shows all the chunks that are (most likely) completely new.").defaultValue(true).build());
        lIllIIlIIlIlIII.VERTICAL_RENDER_OFFSET = lIllIIlIIlIlIII.r.add(new IntSetting.Builder().name("vertical-offset").defaultValue(1).min(1).sliderMin(1).sliderMax(8).build());
        lIllIIlIIlIlIII.VERTICAL_RENDER_MAX_DISTANCE = lIllIIlIIlIlIII.r.add(new IntSetting.Builder().name("vertical-max-distance").defaultValue(4).min(1).sliderMin(1).sliderMax(15).build());
        lIllIIlIIlIlIII.VERTICAL_RENDER_TRANSPARENT = lIllIIlIIlIlIII.r.add(new IntSetting.Builder().name("vertical-transparent").defaultValue(64).min(1).max(255).sliderMin(1).sliderMax(255).build());
        lIllIIlIIlIlIII.NEW_CHUNKS_COLOR = lIllIIlIIlIlIII.r.add(new ColorSetting.Builder().name("new-chunks-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(0, 255, 0, 128)).build());
        lIllIIlIIlIlIII.OLD_CHUNKS_COLOR = lIllIIlIIlIlIII.r.add(new ColorSetting.Builder().name("old-chunks-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(255, 0, 0, 128)).build());
        lIllIIlIIlIlIII.newChunks = Collections.synchronizedSet(new HashSet());
        lIllIIlIIlIlIII.oldChunks = Collections.synchronizedSet(new HashSet());
    }
}

