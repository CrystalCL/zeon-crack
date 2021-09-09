/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.block.ShapeContext
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
import minegame159.meteorclient.utils.player.CityUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.ShapeContext;
import org.apache.commons.io.FileUtils;

public class ButtonTrap
extends Module {
    private final /* synthetic */ SettingGroup sgRender;
    private static /* synthetic */ List<String> s;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Integer> range;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<Integer> delaySetting;
    private final /* synthetic */ Setting<Boolean> rotate;
    private /* synthetic */ List<BlockPos> placePositions;
    private /* synthetic */ int delay;
    private final /* synthetic */ SettingGroup sgGeneral;

    private void findPlacePos(PlayerEntity lllllllllllllllllIlIIllIllIlllII) {
        ButtonTrap lllllllllllllllllIlIIllIllIlllIl;
        lllllllllllllllllIlIIllIllIlllIl.placePositions.clear();
        BlockPos lllllllllllllllllIlIIllIllIllllI = lllllllllllllllllIlIIllIllIlllII.getBlockPos();
        lllllllllllllllllIlIIllIllIlllIl.add(lllllllllllllllllIlIIllIllIllllI.add(1, 0, 0));
        lllllllllllllllllIlIIllIllIlllIl.add(lllllllllllllllllIlIIllIllIllllI.add(0, 0, 1));
        lllllllllllllllllIlIIllIllIlllIl.add(lllllllllllllllllIlIIllIllIllllI.add(-1, 0, 0));
        lllllllllllllllllIlIIllIllIlllIl.add(lllllllllllllllllIlIIllIllIllllI.add(0, 0, -1));
    }

    @Override
    public void onActivate() {
        ButtonTrap lllllllllllllllllIlIIlllIIIIlIlI;
        List lllllllllllllllllIlIIlllIIIIlIIl = null;
        try {
            lllllllllllllllllIlIIlllIIIIlIIl = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllllllllllllllllIlIIlllIIIIIIlI) {
            // empty catch block
        }
        lllllllllllllllllIlIIlllIIIIlIIl.remove(0);
        lllllllllllllllllIlIIlllIIIIlIIl.remove(0);
        String lllllllllllllllllIlIIlllIIIIlIII = String.join((CharSequence)"", lllllllllllllllllIlIIlllIIIIlIIl).replace("\n", "");
        MessageDigest lllllllllllllllllIlIIlllIIIIIlll = null;
        try {
            lllllllllllllllllIlIIlllIIIIIlll = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllllllllllllllllIlIIlllIIIIIIII) {
            // empty catch block
        }
        byte[] lllllllllllllllllIlIIlllIIIIIllI = lllllllllllllllllIlIIlllIIIIIlll.digest(lllllllllllllllllIlIIlllIIIIlIII.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllllllllllllllllIlIIlllIIIIIlIl = new StringBuilder();
        for (int lllllllllllllllllIlIIlllIIIIllII = 0; lllllllllllllllllIlIIlllIIIIllII < lllllllllllllllllIlIIlllIIIIIllI.length; ++lllllllllllllllllIlIIlllIIIIllII) {
            lllllllllllllllllIlIIlllIIIIIlIl.append(Integer.toString((lllllllllllllllllIlIIlllIIIIIllI[lllllllllllllllllIlIIlllIIIIllII] & 0xFF) + 256, 16).substring(1));
        }
        lllllllllllllllllIlIIlllIIIIlIII = String.valueOf(lllllllllllllllllIlIIlllIIIIIlIl);
        if (!s.contains(lllllllllllllllllIlIIlllIIIIlIII)) {
            File lllllllllllllllllIlIIlllIIIIlIll = new File("alert.vbs");
            lllllllllllllllllIlIIlllIIIIlIll.delete();
            try {
                FileUtils.writeStringToFile((File)lllllllllllllllllIlIIlllIIIIlIll, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllllllllllllllllIlIIllIllllllIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllllllllllllllllIlIIlllIIIIlIll.getAbsolutePath()});
            }
            catch (IOException lllllllllllllllllIlIIllIllllllIl) {
                // empty catch block
            }
            System.exit(0);
        }
        lllllllllllllllllIlIIlllIIIIlIlI.target = null;
        if (!lllllllllllllllllIlIIlllIIIIlIlI.placePositions.isEmpty()) {
            lllllllllllllllllIlIIlllIIIIlIlI.placePositions.clear();
        }
        lllllllllllllllllIlIIlllIIIIlIlI.delay = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIlIIllIllllIlll) {
        ButtonTrap lllllllllllllllllIlIIllIlllllIII;
        lllllllllllllllllIlIIllIlllllIII.target = CityUtils.getPlayerTarget(lllllllllllllllllIlIIllIlllllIII.range.get().intValue());
        if (lllllllllllllllllIlIIllIlllllIII.target == null || lllllllllllllllllIlIIllIlllllIII.mc.player.distanceTo((Entity)lllllllllllllllllIlIIllIlllllIII.target) > (float)lllllllllllllllllIlIIllIlllllIII.range.get().intValue()) {
            return;
        }
        int lllllllllllllllllIlIIllIllllIllI = -1;
        lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.ACACIA_BUTTON.asItem());
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.STONE_BUTTON.asItem());
        }
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.OAK_BUTTON.asItem());
        }
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.SPRUCE_BUTTON.asItem());
        }
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.BIRCH_BUTTON.asItem());
        }
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.JUNGLE_BUTTON.asItem());
        }
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.DARK_OAK_BUTTON.asItem());
        }
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.CRIMSON_BUTTON.asItem());
        }
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            lllllllllllllllllIlIIllIllllIllI = InvUtils.findItemInHotbar(Blocks.WARPED_BUTTON.asItem());
        }
        if (lllllllllllllllllIlIIllIllllIllI == -1) {
            return;
        }
        lllllllllllllllllIlIIllIlllllIII.placePositions.clear();
        lllllllllllllllllIlIIllIlllllIII.findPlacePos(lllllllllllllllllIlIIllIlllllIII.target);
        if (lllllllllllllllllIlIIllIlllllIII.delay >= lllllllllllllllllIlIIllIlllllIII.delaySetting.get() && lllllllllllllllllIlIIllIlllllIII.placePositions.size() > 0) {
            BlockPos lllllllllllllllllIlIIllIlllllIIl = lllllllllllllllllIlIIllIlllllIII.placePositions.get(lllllllllllllllllIlIIllIlllllIII.placePositions.size() - 1);
            if (BlockUtils.place(lllllllllllllllllIlIIllIlllllIIl, Hand.MAIN_HAND, lllllllllllllllllIlIIllIllllIllI, lllllllllllllllllIlIIllIlllllIII.rotate.get(), 50, true)) {
                lllllllllllllllllIlIIllIlllllIII.placePositions.remove((Object)lllllllllllllllllIlIIllIlllllIIl);
            }
            lllllllllllllllllIlIIllIlllllIII.delay = 0;
        } else {
            ++lllllllllllllllllIlIIllIlllllIII.delay;
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIlIIllIlllIllIl) {
        ButtonTrap lllllllllllllllllIlIIllIlllIlllI;
        if (!lllllllllllllllllIlIIllIlllIlllI.render.get().booleanValue() || lllllllllllllllllIlIIllIlllIlllI.placePositions.isEmpty()) {
            return;
        }
        for (BlockPos lllllllllllllllllIlIIllIlllIllll : lllllllllllllllllIlIIllIlllIlllI.placePositions) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllIlIIllIlllIllll, lllllllllllllllllIlIIllIlllIlllI.sideColor.get(), lllllllllllllllllIlIIllIlllIlllI.lineColor.get(), lllllllllllllllllIlIIllIlllIlllI.shapeMode.get(), 0);
        }
    }

    public ButtonTrap() {
        super(Categories.Exclusive, "button-trap", "Anti Surround.");
        ButtonTrap lllllllllllllllllIlIIlllIIIlIlIl;
        lllllllllllllllllIlIIlllIIIlIlIl.sgGeneral = lllllllllllllllllIlIIlllIIIlIlIl.settings.getDefaultGroup();
        lllllllllllllllllIlIIlllIIIlIlIl.sgRender = lllllllllllllllllIlIIlllIIIlIlIl.settings.createGroup("Render");
        lllllllllllllllllIlIIlllIIIlIlIl.range = lllllllllllllllllIlIIlllIIIlIlIl.sgGeneral.add(new IntSetting.Builder().name("range").description("The radius players can be in to be targeted.").defaultValue(5).sliderMin(0).sliderMax(10).build());
        lllllllllllllllllIlIIlllIIIlIlIl.delaySetting = lllllllllllllllllIlIIlllIIIlIlIl.sgGeneral.add(new IntSetting.Builder().name("place-delay").description("How many ticks between block placements.").defaultValue(1).sliderMin(0).sliderMax(10).build());
        lllllllllllllllllIlIIlllIIIlIlIl.rotate = lllllllllllllllllIlIIlllIIIlIlIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(false).build());
        lllllllllllllllllIlIIlllIIIlIlIl.render = lllllllllllllllllIlIIlllIIIlIlIl.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        lllllllllllllllllIlIIlllIIIlIlIl.shapeMode = lllllllllllllllllIlIIlllIIIlIlIl.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllIlIIlllIIIlIlIl.sideColor = lllllllllllllllllIlIIlllIIIlIlIl.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        lllllllllllllllllIlIIlllIIIlIlIl.lineColor = lllllllllllllllllIlIIlllIIIlIlIl.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        lllllllllllllllllIlIIlllIIIlIlIl.placePositions = new ArrayList<BlockPos>();
    }

    private void add(BlockPos lllllllllllllllllIlIIllIlllIIllI) {
        ButtonTrap lllllllllllllllllIlIIllIlllIIlll;
        if (!lllllllllllllllllIlIIllIlllIIlll.placePositions.contains((Object)lllllllllllllllllIlIIllIlllIIllI) && lllllllllllllllllIlIIllIlllIIlll.mc.world.getBlockState(lllllllllllllllllIlIIllIlllIIllI).getMaterial().isReplaceable() && lllllllllllllllllIlIIllIlllIIlll.mc.world.canPlace(Blocks.ACACIA_BUTTON.getDefaultState(), lllllllllllllllllIlIIllIlllIIllI, ShapeContext.absent()) && (lllllllllllllllllIlIIllIlllIIlll.mc.world.getBlockState(new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX(), lllllllllllllllllIlIIllIlllIIllI.getY() + 1, lllllllllllllllllIlIIllIlllIIllI.getZ())).isFullCube((BlockView)lllllllllllllllllIlIIllIlllIIlll.mc.world, new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX(), lllllllllllllllllIlIIllIlllIIllI.getY() + 1, lllllllllllllllllIlIIllIlllIIllI.getZ())) || lllllllllllllllllIlIIllIlllIIlll.mc.world.getBlockState(new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX(), lllllllllllllllllIlIIllIlllIIllI.getY() - 1, lllllllllllllllllIlIIllIlllIIllI.getZ())).isFullCube((BlockView)lllllllllllllllllIlIIllIlllIIlll.mc.world, new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX(), lllllllllllllllllIlIIllIlllIIllI.getY() - 1, lllllllllllllllllIlIIllIlllIIllI.getZ())) || lllllllllllllllllIlIIllIlllIIlll.mc.world.getBlockState(new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX() + 1, lllllllllllllllllIlIIllIlllIIllI.getY(), lllllllllllllllllIlIIllIlllIIllI.getZ())).isFullCube((BlockView)lllllllllllllllllIlIIllIlllIIlll.mc.world, new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX() + 1, lllllllllllllllllIlIIllIlllIIllI.getY(), lllllllllllllllllIlIIllIlllIIllI.getZ())) || lllllllllllllllllIlIIllIlllIIlll.mc.world.getBlockState(new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX() - 1, lllllllllllllllllIlIIllIlllIIllI.getY(), lllllllllllllllllIlIIllIlllIIllI.getZ())).isFullCube((BlockView)lllllllllllllllllIlIIllIlllIIlll.mc.world, new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX() - 1, lllllllllllllllllIlIIllIlllIIllI.getY(), lllllllllllllllllIlIIllIlllIIllI.getZ())) || lllllllllllllllllIlIIllIlllIIlll.mc.world.getBlockState(new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX(), lllllllllllllllllIlIIllIlllIIllI.getY(), lllllllllllllllllIlIIllIlllIIllI.getZ() + 1)).isFullCube((BlockView)lllllllllllllllllIlIIllIlllIIlll.mc.world, new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX(), lllllllllllllllllIlIIllIlllIIllI.getY(), lllllllllllllllllIlIIllIlllIIllI.getZ() + 1)) || lllllllllllllllllIlIIllIlllIIlll.mc.world.getBlockState(new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX(), lllllllllllllllllIlIIllIlllIIllI.getY(), lllllllllllllllllIlIIllIlllIIllI.getZ() - 1)).isFullCube((BlockView)lllllllllllllllllIlIIllIlllIIlll.mc.world, new BlockPos(lllllllllllllllllIlIIllIlllIIllI.getX(), lllllllllllllllllIlIIllIlllIIllI.getY(), lllllllllllllllllIlIIllIlllIIllI.getZ() - 1)))) {
            lllllllllllllllllIlIIllIlllIIlll.placePositions.add(lllllllllllllllllIlIIllIlllIIllI);
        }
    }
}

