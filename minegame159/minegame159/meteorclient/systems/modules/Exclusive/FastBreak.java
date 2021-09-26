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
    private final Setting<Boolean> smash;
    private final Setting<SettingColor> lineColor;
    boolean isBreak;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<ShapeMode> shapeMode;
    private final SettingGroup sgRender;
    private final Setting<Boolean> obbyonly;
    private final Setting<SettingColor> sideColor;
    private final Setting<Boolean> autocity;
    BlockPos pos;
    private int ticks;
    private final Setting<Boolean> render;
    private final SettingGroup sgGeneral;
    private final Setting<Integer> delay;

    @EventHandler
    public void StartBreakingBlockEvent(StartBreakingBlockEvent startBreakingBlockEvent) {
        this.pos = startBreakingBlockEvent.blockPos;
        Block Block2 = this.mc.world.getBlockState(this.pos).getBlock();
        if (this.obbyonly.get().booleanValue() && Block2 != Blocks.OBSIDIAN) {
            this.pos = null;
            return;
        }
        if (Block2 == Blocks.BEDROCK || Block2 == Blocks.NETHER_PORTAL || Block2 == Blocks.END_GATEWAY || Block2 == Blocks.END_PORTAL || Block2 == Blocks.END_PORTAL_FRAME || Block2 == Blocks.BARRIER) {
            this.pos = null;
            return;
        }
        Block[] BlockArray = new Block[]{Blocks.STONE, Blocks.COBBLESTONE, Blocks.NETHERRACK, Blocks.TERRACOTTA, Blocks.BASALT, Blocks.FURNACE, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.BONE_BLOCK};
        String[] stringArray = new String[]{"acacia_", "oak_", "crimson_", "birch_", "warped_", "jungle_", "spruce_", "crafting_table"};
        String[] stringArray2 = new String[]{"stone_", "andesite", "diorite", "granite", "cobblestone_", "mossy_", "_terracotta", "basalt", "blackstone", "end_", "purpur_", "shulker_box"};
        boolean bl = false;
        if (this.smash.get().booleanValue() && this.mc.player.isOnGround()) {
            int n;
            if (this.mc.player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE) {
                if (Arrays.asList(BlockArray).contains(Block2)) {
                    bl = true;
                }
                for (n = 0; n < stringArray2.length; ++n) {
                    if (!Block2.asItem().toString().contains(stringArray2[n])) continue;
                    bl = true;
                    break;
                }
            }
            if (this.mc.player.getMainHandStack().getItem() == Items.NETHERITE_AXE) {
                for (n = 0; n < stringArray.length; ++n) {
                    if (!Block2.asItem().toString().contains(stringArray[n])) continue;
                    bl = true;
                    break;
                }
            }
            if (Block2.asItem().toString().contains("_leaves")) {
                bl = false;
            }
            if (Block2.asItem().toString().contains("_wart")) {
                bl = false;
            }
            if (bl) {
                this.mc.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.ticks >= this.delay.get()) {
            this.ticks = 0;
            if (this.pos == null) {
                return;
            }
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, this.pos, Direction.UP));
        } else {
            ++this.ticks;
        }
    }

    @EventHandler
    private void AUTO_CITY(PacketEvent.Send send) {
        PlayerActionC2SPacket PlayerActionC2SPacket2;
        if (send.packet instanceof PlayerActionC2SPacket && this.autocity.get().booleanValue() && (PlayerActionC2SPacket2 = (PlayerActionC2SPacket)send.packet).getAction().toString() == "START_DESTROY_BLOCK") {
            this.pos = PlayerActionC2SPacket2.getPos();
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
            if (null == null) continue;
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
        this.pos = null;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!this.render.get().booleanValue() || this.pos == null) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.pos, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    public FastBreak() {
        super(Categories.Exclusive, "insta-break", "Fast block Break.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("Delay").description("Ticks delay between send packet.").defaultValue(0).min(0).sliderMax(20).build());
        this.autocity = this.sgGeneral.add(new BoolSetting.Builder().name("auto-city-break").defaultValue(true).build());
        this.smash = this.sgGeneral.add(new BoolSetting.Builder().name("smash").description("Destroy the block instantly.").defaultValue(true).build());
        this.obbyonly = this.sgGeneral.add(new BoolSetting.Builder().name("only-obsidian").description("Break obsidian only.").defaultValue(false).build());
        this.sgRender = this.settings.createGroup("Render");
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 255)).build());
        this.pos = null;
        this.isBreak = false;
    }
}

