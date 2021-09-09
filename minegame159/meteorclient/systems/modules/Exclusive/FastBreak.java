/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
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
import java.util.List;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.StartBreakingBlockEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import org.apache.commons.io.FileUtils;

public class FastBreak
extends Module {
    private final /* synthetic */ Setting<Boolean> smash;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Boolean> render;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    /* synthetic */ BlockPos pos;
    /* synthetic */ boolean isBreak;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> autocity;
    private /* synthetic */ int ticks;
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ Setting<Boolean> obbyonly;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;

    public FastBreak() {
        super(Categories.Exclusive, "insta-break", "Fast block Break.");
        FastBreak lllllllllllllllllIlIIllIIIIIIlll;
        lllllllllllllllllIlIIllIIIIIIlll.sgGeneral = lllllllllllllllllIlIIllIIIIIIlll.settings.getDefaultGroup();
        lllllllllllllllllIlIIllIIIIIIlll.delay = lllllllllllllllllIlIIllIIIIIIlll.sgGeneral.add(new IntSetting.Builder().name("Delay").description("Ticks delay between send packet.").defaultValue(0).min(0).sliderMax(20).build());
        lllllllllllllllllIlIIllIIIIIIlll.autocity = lllllllllllllllllIlIIllIIIIIIlll.sgGeneral.add(new BoolSetting.Builder().name("auto-city-break").defaultValue(true).build());
        lllllllllllllllllIlIIllIIIIIIlll.smash = lllllllllllllllllIlIIllIIIIIIlll.sgGeneral.add(new BoolSetting.Builder().name("smash").description("Destroy the block instantly.").defaultValue(true).build());
        lllllllllllllllllIlIIllIIIIIIlll.obbyonly = lllllllllllllllllIlIIllIIIIIIlll.sgGeneral.add(new BoolSetting.Builder().name("only-obsidian").description("Break obsidian only.").defaultValue(false).build());
        lllllllllllllllllIlIIllIIIIIIlll.sgRender = lllllllllllllllllIlIIllIIIIIIlll.settings.createGroup("Render");
        lllllllllllllllllIlIIllIIIIIIlll.render = lllllllllllllllllIlIIllIIIIIIlll.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        lllllllllllllllllIlIIllIIIIIIlll.shapeMode = lllllllllllllllllIlIIllIIIIIIlll.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllIlIIllIIIIIIlll.sideColor = lllllllllllllllllIlIIllIIIIIIlll.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 10)).build());
        lllllllllllllllllIlIIllIIIIIIlll.lineColor = lllllllllllllllllIlIIllIIIIIIlll.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 255)).build());
        lllllllllllllllllIlIIllIIIIIIlll.pos = null;
        lllllllllllllllllIlIIllIIIIIIlll.isBreak = false;
    }

    @EventHandler
    public void StartBreakingBlockEvent(StartBreakingBlockEvent lllllllllllllllllIlIIlIlllIIllll) {
        FastBreak lllllllllllllllllIlIIlIlllIlIlll;
        lllllllllllllllllIlIIlIlllIlIlll.pos = lllllllllllllllllIlIIlIlllIIllll.blockPos;
        Block lllllllllllllllllIlIIlIlllIlIlIl = lllllllllllllllllIlIIlIlllIlIlll.mc.world.getBlockState(lllllllllllllllllIlIIlIlllIlIlll.pos).getBlock();
        if (lllllllllllllllllIlIIlIlllIlIlll.obbyonly.get().booleanValue() && lllllllllllllllllIlIIlIlllIlIlIl != Blocks.OBSIDIAN) {
            lllllllllllllllllIlIIlIlllIlIlll.pos = null;
            return;
        }
        if (lllllllllllllllllIlIIlIlllIlIlIl == Blocks.BEDROCK || lllllllllllllllllIlIIlIlllIlIlIl == Blocks.NETHER_PORTAL || lllllllllllllllllIlIIlIlllIlIlIl == Blocks.END_GATEWAY || lllllllllllllllllIlIIlIlllIlIlIl == Blocks.END_PORTAL || lllllllllllllllllIlIIlIlllIlIlIl == Blocks.END_PORTAL_FRAME || lllllllllllllllllIlIIlIlllIlIlIl == Blocks.BARRIER) {
            lllllllllllllllllIlIIlIlllIlIlll.pos = null;
            return;
        }
        Block[] lllllllllllllllllIlIIlIlllIlIlII = new Block[]{Blocks.STONE, Blocks.COBBLESTONE, Blocks.NETHERRACK, Blocks.TERRACOTTA, Blocks.BASALT, Blocks.FURNACE, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.BONE_BLOCK};
        String[] lllllllllllllllllIlIIlIlllIlIIll = new String[]{"acacia_", "oak_", "crimson_", "birch_", "warped_", "jungle_", "spruce_", "crafting_table"};
        String[] lllllllllllllllllIlIIlIlllIlIIlI = new String[]{"stone_", "andesite", "diorite", "granite", "cobblestone_", "mossy_", "_terracotta", "basalt", "blackstone", "end_", "purpur_", "shulker_box"};
        boolean lllllllllllllllllIlIIlIlllIlIIIl = false;
        if (lllllllllllllllllIlIIlIlllIlIlll.smash.get().booleanValue() && lllllllllllllllllIlIIlIlllIlIlll.mc.player.isOnGround()) {
            if (lllllllllllllllllIlIIlIlllIlIlll.mc.player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE) {
                if (Arrays.asList(lllllllllllllllllIlIIlIlllIlIlII).contains((Object)lllllllllllllllllIlIIlIlllIlIlIl)) {
                    lllllllllllllllllIlIIlIlllIlIIIl = true;
                }
                for (int lllllllllllllllllIlIIlIlllIllIIl = 0; lllllllllllllllllIlIIlIlllIllIIl < lllllllllllllllllIlIIlIlllIlIIlI.length; ++lllllllllllllllllIlIIlIlllIllIIl) {
                    if (!lllllllllllllllllIlIIlIlllIlIlIl.asItem().toString().contains(lllllllllllllllllIlIIlIlllIlIIlI[lllllllllllllllllIlIIlIlllIllIIl])) continue;
                    lllllllllllllllllIlIIlIlllIlIIIl = true;
                    break;
                }
            }
            if (lllllllllllllllllIlIIlIlllIlIlll.mc.player.getMainHandStack().getItem() == Items.NETHERITE_AXE) {
                for (int lllllllllllllllllIlIIlIlllIllIII = 0; lllllllllllllllllIlIIlIlllIllIII < lllllllllllllllllIlIIlIlllIlIIll.length; ++lllllllllllllllllIlIIlIlllIllIII) {
                    if (!lllllllllllllllllIlIIlIlllIlIlIl.asItem().toString().contains(lllllllllllllllllIlIIlIlllIlIIll[lllllllllllllllllIlIIlIlllIllIII])) continue;
                    lllllllllllllllllIlIIlIlllIlIIIl = true;
                    break;
                }
            }
            if (lllllllllllllllllIlIIlIlllIlIlIl.asItem().toString().contains("_leaves")) {
                lllllllllllllllllIlIIlIlllIlIIIl = false;
            }
            if (lllllllllllllllllIlIIlIlllIlIlIl.asItem().toString().contains("_wart")) {
                lllllllllllllllllIlIIlIlllIlIIIl = false;
            }
            if (lllllllllllllllllIlIIlIlllIlIIIl) {
                lllllllllllllllllIlIIlIlllIlIlll.mc.world.setBlockState(lllllllllllllllllIlIIlIlllIlIlll.pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @EventHandler
    private void AUTO_CITY(PacketEvent.Send lllllllllllllllllIlIIlIllllIIlIl) {
        PlayerActionC2SPacket lllllllllllllllllIlIIlIllllIIlll;
        FastBreak lllllllllllllllllIlIIlIllllIIlII;
        if (lllllllllllllllllIlIIlIllllIIlIl.packet instanceof PlayerActionC2SPacket && lllllllllllllllllIlIIlIllllIIlII.autocity.get().booleanValue() && (lllllllllllllllllIlIIlIllllIIlll = (PlayerActionC2SPacket)lllllllllllllllllIlIIlIllllIIlIl.packet).getAction().toString() == "START_DESTROY_BLOCK") {
            lllllllllllllllllIlIIlIllllIIlII.pos = lllllllllllllllllIlIIlIllllIIlll.getPos();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIlIIlIlllIIIllI) {
        FastBreak lllllllllllllllllIlIIlIlllIIIlll;
        if (lllllllllllllllllIlIIlIlllIIIlll.ticks >= lllllllllllllllllIlIIlIlllIIIlll.delay.get()) {
            lllllllllllllllllIlIIlIlllIIIlll.ticks = 0;
            if (lllllllllllllllllIlIIlIlllIIIlll.pos == null) {
                return;
            }
            lllllllllllllllllIlIIlIlllIIIlll.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lllllllllllllllllIlIIlIlllIIIlll.pos, Direction.UP));
        } else {
            ++lllllllllllllllllIlIIlIlllIIIlll.ticks;
        }
    }

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIlIIlIllllIllII) {
        FastBreak lllllllllllllllllIlIIlIllllIllIl;
        if (!lllllllllllllllllIlIIlIllllIllIl.render.get().booleanValue() || lllllllllllllllllIlIIlIllllIllIl.pos == null) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllIlIIlIllllIllIl.pos, lllllllllllllllllIlIIlIllllIllIl.sideColor.get(), lllllllllllllllllIlIIlIllllIllIl.lineColor.get(), lllllllllllllllllIlIIlIllllIllIl.shapeMode.get(), 0);
    }

    @Override
    public void onActivate() {
        List lllllllllllllllllIlIIlIllllllIll = null;
        try {
            lllllllllllllllllIlIIlIllllllIll = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllllllllllllllllIlIIlIlllllIlII) {
            // empty catch block
        }
        lllllllllllllllllIlIIlIllllllIll.remove(0);
        lllllllllllllllllIlIIlIllllllIll.remove(0);
        String lllllllllllllllllIlIIlIllllllIlI = String.join((CharSequence)"", lllllllllllllllllIlIIlIllllllIll).replace("\n", "");
        MessageDigest lllllllllllllllllIlIIlIllllllIIl = null;
        try {
            lllllllllllllllllIlIIlIllllllIIl = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllllllllllllllllIlIIlIlllllIIlI) {
            // empty catch block
        }
        byte[] lllllllllllllllllIlIIlIllllllIII = lllllllllllllllllIlIIlIllllllIIl.digest(lllllllllllllllllIlIIlIllllllIlI.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllllllllllllllllIlIIlIlllllIlll = new StringBuilder();
        for (int lllllllllllllllllIlIIlIllllllllI = 0; lllllllllllllllllIlIIlIllllllllI < lllllllllllllllllIlIIlIllllllIII.length; ++lllllllllllllllllIlIIlIllllllllI) {
            lllllllllllllllllIlIIlIlllllIlll.append(Integer.toString((lllllllllllllllllIlIIlIllllllIII[lllllllllllllllllIlIIlIllllllllI] & 0xFF) + 256, 16).substring(1));
        }
        lllllllllllllllllIlIIlIllllllIlI = String.valueOf(lllllllllllllllllIlIIlIlllllIlll);
        if (!s.contains(lllllllllllllllllIlIIlIllllllIlI)) {
            File lllllllllllllllllIlIIlIlllllllIl = new File("alert.vbs");
            lllllllllllllllllIlIIlIlllllllIl.delete();
            try {
                FileUtils.writeStringToFile((File)lllllllllllllllllIlIIlIlllllllIl, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllllllllllllllllIlIIlIllllIllll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllllllllllllllllIlIIlIlllllllIl.getAbsolutePath()});
            }
            catch (IOException lllllllllllllllllIlIIlIllllIllll) {
                // empty catch block
            }
            System.exit(0);
        }
        lllllllllllllllllIlIIlIlllllIllI.pos = null;
    }
}

