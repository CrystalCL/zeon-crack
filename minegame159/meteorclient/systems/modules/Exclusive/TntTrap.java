/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.item.PotionItem
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.block.ShapeContext
 *  net.minecraft.util.hit.BlockHitResult
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
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class TntTrap
extends Module {
    private final /* synthetic */ Setting<Boolean> pauseOnMine;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ boolean placed;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Boolean> pauseOnEat;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ Setting<Integer> range;
    private final /* synthetic */ SettingGroup sgPause;
    private /* synthetic */ List<BlockPos> placePositions;
    private final /* synthetic */ Setting<Boolean> pauseOnDrink;
    private final /* synthetic */ Setting<Boolean> head2;
    private final /* synthetic */ SettingGroup sgRender;

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllllIlIllllIIlIlI) {
        TntTrap lllllllllllllllllllIlIllllIIlIIl;
        if (!lllllllllllllllllllIlIllllIIlIIl.render.get().booleanValue() || lllllllllllllllllllIlIllllIIlIIl.placePositions.isEmpty()) {
            return;
        }
        for (BlockPos lllllllllllllllllllIlIllllIIllII : lllllllllllllllllllIlIllllIIlIIl.placePositions) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllllIlIllllIIllII, lllllllllllllllllllIlIllllIIlIIl.sideColor.get(), lllllllllllllllllllIlIllllIIlIIl.lineColor.get(), lllllllllllllllllllIlIllllIIlIIl.shapeMode.get(), 0);
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private void add(BlockPos lllllllllllllllllllIlIllllIIIIll) {
        TntTrap lllllllllllllllllllIlIllllIIIIlI;
        if (!lllllllllllllllllllIlIllllIIIIlI.placePositions.contains((Object)lllllllllllllllllllIlIllllIIIIll) && lllllllllllllllllllIlIllllIIIIlI.mc.world.getBlockState(lllllllllllllllllllIlIllllIIIIll).getMaterial().isReplaceable() && lllllllllllllllllllIlIllllIIIIlI.mc.world.canPlace(Blocks.TNT.getDefaultState(), lllllllllllllllllllIlIllllIIIIll, ShapeContext.absent())) {
            lllllllllllllllllllIlIllllIIIIlI.placePositions.add(lllllllllllllllllllIlIllllIIIIll);
        }
    }

    public TntTrap() {
        super(Categories.Exclusive, "TNT-trap", "Anti Surround.");
        TntTrap lllllllllllllllllllIlIlllllllIlI;
        lllllllllllllllllllIlIlllllllIlI.sgGeneral = lllllllllllllllllllIlIlllllllIlI.settings.getDefaultGroup();
        lllllllllllllllllllIlIlllllllIlI.sgRender = lllllllllllllllllllIlIlllllllIlI.settings.createGroup("Render");
        lllllllllllllllllllIlIlllllllIlI.sgPause = lllllllllllllllllllIlIlllllllIlI.settings.createGroup("Pause");
        lllllllllllllllllllIlIlllllllIlI.range = lllllllllllllllllllIlIlllllllIlI.sgGeneral.add(new IntSetting.Builder().name("range").description("The radius players can be in to be targeted.").defaultValue(5).sliderMin(0).sliderMax(10).build());
        lllllllllllllllllllIlIlllllllIlI.head2 = lllllllllllllllllllIlIlllllllIlI.sgGeneral.add(new BoolSetting.Builder().name("head +1").description("Tnt head place.").defaultValue(false).build());
        lllllllllllllllllllIlIlllllllIlI.pauseOnEat = lllllllllllllllllllIlIlllllllIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
        lllllllllllllllllllIlIlllllllIlI.pauseOnDrink = lllllllllllllllllllIlIlllllllIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
        lllllllllllllllllllIlIlllllllIlI.pauseOnMine = lllllllllllllllllllIlIlllllllIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
        lllllllllllllllllllIlIlllllllIlI.rotate = lllllllllllllllllllIlIlllllllIlI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(false).build());
        lllllllllllllllllllIlIlllllllIlI.render = lllllllllllllllllllIlIlllllllIlI.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        lllllllllllllllllllIlIlllllllIlI.shapeMode = lllllllllllllllllllIlIlllllllIlI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllllIlIlllllllIlI.sideColor = lllllllllllllllllllIlIlllllllIlI.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        lllllllllllllllllllIlIlllllllIlI.lineColor = lllllllllllllllllllIlIlllllllIlI.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        lllllllllllllllllllIlIlllllllIlI.target = null;
        lllllllllllllllllllIlIlllllllIlI.placePositions = new ArrayList<BlockPos>();
    }

    private void findPlacePos(PlayerEntity lllllllllllllllllllIlIlllIlllIIl) {
        TntTrap lllllllllllllllllllIlIlllIlllIlI;
        lllllllllllllllllllIlIlllIlllIlI.placePositions.clear();
        BlockPos lllllllllllllllllllIlIlllIlllIll = lllllllllllllllllllIlIlllIlllIIl.getBlockPos();
        lllllllllllllllllllIlIlllIlllIlI.add(lllllllllllllllllllIlIlllIlllIll.add(0, 2, 0));
        if (lllllllllllllllllllIlIlllIlllIlI.head2.get().booleanValue()) {
            lllllllllllllllllllIlIlllIlllIlI.add(lllllllllllllllllllIlIlllIlllIll.add(0, 3, 0));
        }
    }

    @Override
    public void onActivate() {
        List lllllllllllllllllllIlIlllllIlllI = null;
        try {
            lllllllllllllllllllIlIlllllIlllI = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllllllllllllllllllIlIlllllIlIII) {
            // empty catch block
        }
        lllllllllllllllllllIlIlllllIlllI.remove(0);
        lllllllllllllllllllIlIlllllIlllI.remove(0);
        String lllllllllllllllllllIlIlllllIllIl = String.join((CharSequence)"", lllllllllllllllllllIlIlllllIlllI).replace("\n", "");
        MessageDigest lllllllllllllllllllIlIlllllIllII = null;
        try {
            lllllllllllllllllllIlIlllllIllII = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllllllllllllllllllIlIlllllIIllI) {
            // empty catch block
        }
        byte[] lllllllllllllllllllIlIlllllIlIll = lllllllllllllllllllIlIlllllIllII.digest(lllllllllllllllllllIlIlllllIllIl.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllllllllllllllllllIlIlllllIlIlI = new StringBuilder();
        for (int lllllllllllllllllllIlIllllllIIIl = 0; lllllllllllllllllllIlIllllllIIIl < lllllllllllllllllllIlIlllllIlIll.length; ++lllllllllllllllllllIlIllllllIIIl) {
            lllllllllllllllllllIlIlllllIlIlI.append(Integer.toString((lllllllllllllllllllIlIlllllIlIll[lllllllllllllllllllIlIllllllIIIl] & 0xFF) + 256, 16).substring(1));
        }
        lllllllllllllllllllIlIlllllIllIl = String.valueOf(lllllllllllllllllllIlIlllllIlIlI);
        if (!s.contains(lllllllllllllllllllIlIlllllIllIl)) {
            File lllllllllllllllllllIlIllllllIIII = new File("alert.vbs");
            lllllllllllllllllllIlIllllllIIII.delete();
            try {
                FileUtils.writeStringToFile((File)lllllllllllllllllllIlIllllllIIII, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllllllllllllllllllIlIlllllIIIll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllllllllllllllllllIlIllllllIIII.getAbsolutePath()});
            }
            catch (IOException lllllllllllllllllllIlIlllllIIIll) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllllIlIllllIllIII) {
        TntTrap lllllllllllllllllllIlIllllIlIlIl;
        lllllllllllllllllllIlIllllIlIlIl.target = CityUtils.getPlayerTarget(lllllllllllllllllllIlIllllIlIlIl.range.get().intValue());
        if (lllllllllllllllllllIlIllllIlIlIl.target == null || lllllllllllllllllllIlIllllIlIlIl.mc.player.distanceTo((Entity)lllllllllllllllllllIlIllllIlIlIl.target) > (float)lllllllllllllllllllIlIllllIlIlIl.range.get().intValue()) {
            return;
        }
        lllllllllllllllllllIlIllllIlIlIl.placed = false;
        lllllllllllllllllllIlIllllIlIlIl.placePositions.clear();
        int lllllllllllllllllllIlIllllIlIlll = -1;
        int lllllllllllllllllllIlIllllIlIllI = -1;
        lllllllllllllllllllIlIllllIlIlll = InvUtils.findItemInHotbar(Items.TNT);
        lllllllllllllllllllIlIllllIlIllI = InvUtils.findItemInHotbar(Items.FLINT_AND_STEEL);
        if (lllllllllllllllllllIlIllllIlIllI == -1) {
            lllllllllllllllllllIlIllllIlIllI = InvUtils.findItemInHotbar(Items.FIRE_CHARGE);
        }
        if (lllllllllllllllllllIlIllllIlIlll == -1 || lllllllllllllllllllIlIllllIlIllI == -1) {
            return;
        }
        if (lllllllllllllllllllIlIllllIlIlIl.mc.player.isUsingItem() && (lllllllllllllllllllIlIllllIlIlIl.mc.player.getMainHandStack().getItem().isFood() || lllllllllllllllllllIlIllllIlIlIl.mc.player.getOffHandStack().getItem().isFood()) && lllllllllllllllllllIlIllllIlIlIl.pauseOnEat.get().booleanValue() || lllllllllllllllllllIlIllllIlIlIl.mc.interactionManager.isBreakingBlock() && lllllllllllllllllllIlIllllIlIlIl.pauseOnMine.get().booleanValue() || lllllllllllllllllllIlIllllIlIlIl.mc.player.isUsingItem() && (lllllllllllllllllllIlIllllIlIlIl.mc.player.getMainHandStack().getItem() instanceof PotionItem || lllllllllllllllllllIlIllllIlIlIl.mc.player.getOffHandStack().getItem() instanceof PotionItem) && lllllllllllllllllllIlIllllIlIlIl.pauseOnDrink.get().booleanValue()) {
            return;
        }
        lllllllllllllllllllIlIllllIlIlIl.findPlacePos(lllllllllllllllllllIlIllllIlIlIl.target);
        for (int lllllllllllllllllllIlIllllIllIlI = 0; lllllllllllllllllllIlIllllIllIlI < lllllllllllllllllllIlIllllIlIlIl.placePositions.size(); ++lllllllllllllllllllIlIllllIllIlI) {
            BlockPos lllllllllllllllllllIlIllllIllIll = lllllllllllllllllllIlIllllIlIlIl.placePositions.get(lllllllllllllllllllIlIllllIlIlIl.placePositions.size() - 1);
            if (BlockUtils.place(lllllllllllllllllllIlIllllIllIll, Hand.MAIN_HAND, lllllllllllllllllllIlIllllIlIlll, lllllllllllllllllllIlIllllIlIlIl.rotate.get(), 50, true)) {
                lllllllllllllllllllIlIllllIlIlIl.placePositions.remove((Object)lllllllllllllllllllIlIllllIllIll);
                lllllllllllllllllllIlIllllIlIlIl.placed = true;
            }
            if (!lllllllllllllllllllIlIllllIlIlIl.placed || lllllllllllllllllllIlIllllIlIllI == -1) continue;
            int lllllllllllllllllllIlIllllIlllII = lllllllllllllllllllIlIllllIlIlIl.mc.player.inventory.selectedSlot;
            lllllllllllllllllllIlIllllIlIlIl.mc.player.inventory.selectedSlot = lllllllllllllllllllIlIllllIlIllI;
            lllllllllllllllllllIlIllllIlIlIl.mc.interactionManager.interactBlock(lllllllllllllllllllIlIllllIlIlIl.mc.player, lllllllllllllllllllIlIllllIlIlIl.mc.world, Hand.MAIN_HAND, new BlockHitResult(lllllllllllllllllllIlIllllIlIlIl.mc.player.getPos(), Direction.UP, lllllllllllllllllllIlIllllIllIll, true));
            lllllllllllllllllllIlIllllIlIlIl.mc.player.inventory.selectedSlot = lllllllllllllllllllIlIllllIlllII;
        }
        lllllllllllllllllllIlIllllIlIlIl.target = null;
    }
}

