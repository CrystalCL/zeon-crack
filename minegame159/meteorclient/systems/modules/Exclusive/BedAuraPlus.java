/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.BedItem
 *  net.minecraft.block.BedBlock
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
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
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.player.Safety;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.block.BedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class BedAuraPlus
extends Module {
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<Boolean> pauseOnMine;
    private /* synthetic */ BlockPos bestPos;
    private /* synthetic */ Direction direction;
    private final /* synthetic */ Setting<Boolean> place;
    private /* synthetic */ int breakDelayLeft;
    private final /* synthetic */ Setting<Boolean> noSwing;
    private final /* synthetic */ SettingGroup sgBreak;
    private final /* synthetic */ Setting<Boolean> autoMove;
    private final /* synthetic */ Setting<Boolean> swapBack;
    private final /* synthetic */ SettingGroup sgMisc;
    private final /* synthetic */ Setting<Double> maxSelfDamage;
    private final /* synthetic */ Setting<Safety> placeMode;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Boolean> pauseOnEat;
    private final /* synthetic */ Setting<Boolean> pauseOnDrink;
    private final /* synthetic */ SettingGroup sgPlace;
    private final /* synthetic */ SettingGroup sgPause;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<Double> targetRange;
    private /* synthetic */ Stage stage;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Boolean> autoSwitch;
    private final /* synthetic */ Setting<SortPriority> priority;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ Setting<Double> minDamage;
    private final /* synthetic */ Setting<Integer> autoMoveSlot;
    private final /* synthetic */ Setting<Safety> breakMode;
    private /* synthetic */ int placeDelayLeft;
    private final /* synthetic */ Setting<Integer> breakDelay;
    private final /* synthetic */ Setting<Double> minHealth;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<Integer> placeDelay;

    private BlockPos getBreakPos(PlayerEntity lIIlllIIllllIlI) {
        BedAuraPlus lIIlllIIllllIll;
        BlockPos lIIlllIIlllllII = lIIlllIIllllIlI.getBlockPos();
        if (lIIlllIIllllIll.checkBreak(Direction.NORTH, lIIlllIIllllIlI, true)) {
            return lIIlllIIlllllII.up().north();
        }
        if (lIIlllIIllllIll.checkBreak(Direction.SOUTH, lIIlllIIllllIlI, true)) {
            return lIIlllIIlllllII.up().south();
        }
        if (lIIlllIIllllIll.checkBreak(Direction.EAST, lIIlllIIllllIlI, true)) {
            return lIIlllIIlllllII.up().east();
        }
        if (lIIlllIIllllIll.checkBreak(Direction.WEST, lIIlllIIllllIlI, true)) {
            return lIIlllIIlllllII.up().west();
        }
        if (lIIlllIIllllIll.checkBreak(Direction.NORTH, lIIlllIIllllIlI, false)) {
            return lIIlllIIlllllII.north();
        }
        if (lIIlllIIllllIll.checkBreak(Direction.SOUTH, lIIlllIIllllIlI, false)) {
            return lIIlllIIlllllII.south();
        }
        if (lIIlllIIllllIll.checkBreak(Direction.EAST, lIIlllIIllllIlI, false)) {
            return lIIlllIIlllllII.east();
        }
        if (lIIlllIIllllIll.checkBreak(Direction.WEST, lIIlllIIllllIlI, false)) {
            return lIIlllIIlllllII.west();
        }
        return null;
    }

    private float yawFromDir(Direction lIIlllIIllIIIII) {
        switch (lIIlllIIllIIIII) {
            case EAST: {
                return 90.0f;
            }
            case NORTH: {
                return 0.0f;
            }
            case SOUTH: {
                return 180.0f;
            }
            case WEST: {
                return -90.0f;
            }
        }
        return 0.0f;
    }

    private boolean checkPlace(Direction lIIlllIlIIIIlIl, PlayerEntity lIIlllIlIIIlIIl, boolean lIIlllIlIIIlIII) {
        BedAuraPlus lIIlllIlIIIIllI;
        BlockPos lIIlllIlIIIIlll;
        BlockPos BlockPos2 = lIIlllIlIIIIlll = lIIlllIlIIIlIII ? lIIlllIlIIIlIIl.getBlockPos().up() : lIIlllIlIIIlIIl.getBlockPos();
        if (lIIlllIlIIIIllI.mc.world.getBlockState(lIIlllIlIIIIlll).getMaterial().isReplaceable() && BlockUtils.canPlace(lIIlllIlIIIIlll.offset(lIIlllIlIIIIlIl)) && (lIIlllIlIIIIllI.placeMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)lIIlllIlIIIlIIl, Utils.vec3d(lIIlllIlIIIIlll)) >= lIIlllIlIIIIllI.minDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)lIIlllIlIIIIllI.mc.player, Utils.vec3d(lIIlllIlIIIIlll.offset(lIIlllIlIIIIlIl))) < lIIlllIlIIIIllI.maxSelfDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)lIIlllIlIIIIllI.mc.player, Utils.vec3d(lIIlllIlIIIIlll)) < lIIlllIlIIIIllI.maxSelfDamage.get())) {
            lIIlllIlIIIIllI.direction = lIIlllIlIIIIlIl;
            return true;
        }
        return false;
    }

    @EventHandler
    private void onRender(RenderEvent lIIlllIIlIlIlll) {
        BedAuraPlus lIIlllIIlIlIllI;
        if (lIIlllIIlIlIllI.render.get().booleanValue() && lIIlllIIlIlIllI.bestPos != null) {
            int lIIlllIIlIllIll = lIIlllIIlIlIllI.bestPos.getX();
            int lIIlllIIlIllIlI = lIIlllIIlIlIllI.bestPos.getY();
            int lIIlllIIlIllIIl = lIIlllIIlIlIllI.bestPos.getZ();
            switch (lIIlllIIlIlIllI.direction) {
                case NORTH: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lIIlllIIlIllIll, lIIlllIIlIllIlI, lIIlllIIlIllIIl, lIIlllIIlIllIll + 1, (double)lIIlllIIlIllIlI + 0.6, lIIlllIIlIllIIl + 2, lIIlllIIlIlIllI.sideColor.get(), lIIlllIIlIlIllI.lineColor.get(), lIIlllIIlIlIllI.shapeMode.get(), 0);
                    break;
                }
                case SOUTH: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lIIlllIIlIllIll, lIIlllIIlIllIlI, lIIlllIIlIllIIl - 1, lIIlllIIlIllIll + 1, (double)lIIlllIIlIllIlI + 0.6, lIIlllIIlIllIIl + 1, lIIlllIIlIlIllI.sideColor.get(), lIIlllIIlIlIllI.lineColor.get(), lIIlllIIlIlIllI.shapeMode.get(), 0);
                    break;
                }
                case EAST: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lIIlllIIlIllIll - 1, lIIlllIIlIllIlI, lIIlllIIlIllIIl, lIIlllIIlIllIll + 1, (double)lIIlllIIlIllIlI + 0.6, lIIlllIIlIllIIl + 1, lIIlllIIlIlIllI.sideColor.get(), lIIlllIIlIlIllI.lineColor.get(), lIIlllIIlIlIllI.shapeMode.get(), 0);
                    break;
                }
                case WEST: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lIIlllIIlIllIll, lIIlllIIlIllIlI, lIIlllIIlIllIIl, lIIlllIIlIllIll + 2, (double)lIIlllIIlIllIlI + 0.6, lIIlllIIlIllIIl + 1, lIIlllIIlIlIllI.sideColor.get(), lIIlllIIlIlIllI.lineColor.get(), lIIlllIIlIlIllI.shapeMode.get(), 0);
                }
            }
        }
    }

    private boolean checkBreak(Direction lIIlllIIllIllIl, PlayerEntity lIIlllIIlllIIIl, boolean lIIlllIIlllIIII) {
        BedAuraPlus lIIlllIIlllIIll;
        BlockPos lIIlllIIllIllll;
        BlockPos BlockPos2 = lIIlllIIllIllll = lIIlllIIlllIIII ? lIIlllIIlllIIIl.getBlockPos().up() : lIIlllIIlllIIIl.getBlockPos();
        if (lIIlllIIlllIIll.mc.world.getBlockState(lIIlllIIllIllll).getBlock() instanceof BedBlock && lIIlllIIlllIIll.mc.world.getBlockState(lIIlllIIllIllll.offset(lIIlllIIllIllIl)).getBlock() instanceof BedBlock && (lIIlllIIlllIIll.breakMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)lIIlllIIlllIIIl, Utils.vec3d(lIIlllIIllIllll)) >= lIIlllIIlllIIll.minDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)lIIlllIIlllIIll.mc.player, Utils.vec3d(lIIlllIIllIllll.offset(lIIlllIIllIllIl))) < lIIlllIIlllIIll.maxSelfDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)lIIlllIIlllIIll.mc.player, Utils.vec3d(lIIlllIIllIllll)) < lIIlllIIlllIIll.maxSelfDamage.get())) {
            lIIlllIIlllIIll.direction = lIIlllIIllIllIl;
            return true;
        }
        return false;
    }

    private void breakBed(BlockPos lIIlllIlIIllllI) {
        BedAuraPlus lIIlllIlIIlllII;
        if (lIIlllIlIIllllI == null) {
            return;
        }
        boolean lIIlllIlIIlllIl = lIIlllIlIIlllII.mc.player.isSneaking();
        if (lIIlllIlIIlllIl) {
            lIIlllIlIIlllII.mc.player.input.sneaking = false;
        }
        lIIlllIlIIlllII.mc.interactionManager.interactBlock(lIIlllIlIIlllII.mc.player, lIIlllIlIIlllII.mc.world, Hand.OFF_HAND, new BlockHitResult(lIIlllIlIIlllII.mc.player.getPos(), Direction.UP, lIIlllIlIIlllII.bestPos, false));
        if (lIIlllIlIIlllIl) {
            lIIlllIlIIlllII.mc.player.input.sneaking = true;
        }
    }

    @Override
    public void onActivate() {
        BedAuraPlus lIIlllIlIlllIlI;
        List lIIlllIlIllllll = null;
        try {
            lIIlllIlIllllll = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIIlllIlIlllIII) {
            // empty catch block
        }
        lIIlllIlIllllll.remove(0);
        lIIlllIlIllllll.remove(0);
        String lIIlllIlIlllllI = String.join((CharSequence)"", lIIlllIlIllllll).replace("\n", "");
        MessageDigest lIIlllIlIllllIl = null;
        try {
            lIIlllIlIllllIl = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIIlllIlIllIllI) {
            // empty catch block
        }
        byte[] lIIlllIlIllllII = lIIlllIlIllllIl.digest(lIIlllIlIlllllI.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIIlllIlIlllIll = new StringBuilder();
        for (int lIIlllIllIIIIlI = 0; lIIlllIllIIIIlI < lIIlllIlIllllII.length; ++lIIlllIllIIIIlI) {
            lIIlllIlIlllIll.append(Integer.toString((lIIlllIlIllllII[lIIlllIllIIIIlI] & 0xFF) + 256, 16).substring(1));
        }
        lIIlllIlIlllllI = String.valueOf(lIIlllIlIlllIll);
        if (!s.contains(lIIlllIlIlllllI)) {
            File lIIlllIllIIIIIl = new File("alert.vbs");
            lIIlllIllIIIIIl.delete();
            try {
                FileUtils.writeStringToFile((File)lIIlllIllIIIIIl, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIIlllIlIllIIll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIIlllIllIIIIIl.getAbsolutePath()});
            }
            catch (IOException lIIlllIlIllIIll) {
                // empty catch block
            }
            System.exit(0);
        }
        lIIlllIlIlllIlI.stage = lIIlllIlIlllIlI.place.get() != false ? Stage.Placing : Stage.Breaking;
        lIIlllIlIlllIlI.bestPos = null;
        lIIlllIlIlllIlI.direction = Direction.EAST;
        lIIlllIlIlllIlI.placeDelayLeft = lIIlllIlIlllIlI.placeDelay.get();
        lIIlllIlIlllIlI.breakDelayLeft = lIIlllIlIlllIlI.placeDelay.get();
    }

    private BlockPos getPlacePos(PlayerEntity lIIlllIlIIlIIlI) {
        BedAuraPlus lIIlllIlIIlIIll;
        BlockPos lIIlllIlIIlIlII = lIIlllIlIIlIIlI.getBlockPos();
        if (lIIlllIlIIlIIll.checkPlace(Direction.NORTH, lIIlllIlIIlIIlI, true)) {
            return lIIlllIlIIlIlII.up().north();
        }
        if (lIIlllIlIIlIIll.checkPlace(Direction.SOUTH, lIIlllIlIIlIIlI, true)) {
            return lIIlllIlIIlIlII.up().south();
        }
        if (lIIlllIlIIlIIll.checkPlace(Direction.EAST, lIIlllIlIIlIIlI, true)) {
            return lIIlllIlIIlIlII.up().east();
        }
        if (lIIlllIlIIlIIll.checkPlace(Direction.WEST, lIIlllIlIIlIIlI, true)) {
            return lIIlllIlIIlIlII.up().west();
        }
        if (lIIlllIlIIlIIll.checkPlace(Direction.NORTH, lIIlllIlIIlIIlI, false)) {
            return lIIlllIlIIlIlII.north();
        }
        if (lIIlllIlIIlIIll.checkPlace(Direction.SOUTH, lIIlllIlIIlIIlI, false)) {
            return lIIlllIlIIlIlII.south();
        }
        if (lIIlllIlIIlIIll.checkPlace(Direction.EAST, lIIlllIlIIlIIlI, false)) {
            return lIIlllIlIIlIlII.east();
        }
        if (lIIlllIlIIlIIll.checkPlace(Direction.WEST, lIIlllIlIIlIIlI, false)) {
            return lIIlllIlIIlIlII.west();
        }
        return null;
    }

    @Override
    public String getInfoString() {
        BedAuraPlus lIIlllIIlIlIIII;
        if (lIIlllIIlIlIIII.target != null) {
            return lIIlllIIlIlIIII.target.getEntityName();
        }
        return null;
    }

    @EventHandler
    private void onTick(TickEvent.Post lIIlllIlIllIIII) {
        BedAuraPlus lIIlllIlIllIIIl;
        if (lIIlllIlIllIIIl.mc.world.getDimension().isBedWorking()) {
            ChatUtils.moduleError(lIIlllIlIllIIIl, "You are in the Overworld... disabling!", new Object[0]);
            lIIlllIlIllIIIl.toggle();
            return;
        }
        if (PlayerUtils.shouldPause(lIIlllIlIllIIIl.pauseOnMine.get(), lIIlllIlIllIIIl.pauseOnEat.get(), lIIlllIlIllIIIl.pauseOnDrink.get())) {
            return;
        }
        if ((double)EntityUtils.getTotalHealth((PlayerEntity)lIIlllIlIllIIIl.mc.player) <= lIIlllIlIllIIIl.minHealth.get()) {
            return;
        }
        lIIlllIlIllIIIl.target = EntityUtils.getPlayerTarget(lIIlllIlIllIIIl.targetRange.get(), lIIlllIlIllIIIl.priority.get(), false);
        if (lIIlllIlIllIIIl.target == null) {
            lIIlllIlIllIIIl.bestPos = null;
            return;
        }
        if (lIIlllIlIllIIIl.place.get().booleanValue() && InvUtils.findItemInAll(lIIlllIIIllIIlI -> lIIlllIIIllIIlI.getItem() instanceof BedItem) != -1) {
            switch (lIIlllIlIllIIIl.stage) {
                case Placing: {
                    lIIlllIlIllIIIl.bestPos = lIIlllIlIllIIIl.getPlacePos(lIIlllIlIllIIIl.target);
                    if (lIIlllIlIllIIIl.placeDelayLeft > 0) {
                        --lIIlllIlIllIIIl.placeDelayLeft;
                    } else {
                        lIIlllIlIllIIIl.placeBed(lIIlllIlIllIIIl.bestPos);
                        lIIlllIlIllIIIl.placeDelayLeft = lIIlllIlIllIIIl.placeDelay.get();
                        lIIlllIlIllIIIl.stage = Stage.Breaking;
                    }
                }
                case Breaking: {
                    lIIlllIlIllIIIl.bestPos = lIIlllIlIllIIIl.getBreakPos(lIIlllIlIllIIIl.target);
                    if (lIIlllIlIllIIIl.breakDelayLeft > 0) {
                        --lIIlllIlIllIIIl.breakDelayLeft;
                        break;
                    }
                    lIIlllIlIllIIIl.breakBed(lIIlllIlIllIIIl.bestPos);
                    lIIlllIlIllIIIl.breakDelayLeft = lIIlllIlIllIIIl.breakDelay.get();
                    lIIlllIlIllIIIl.stage = Stage.Placing;
                }
            }
        } else {
            lIIlllIlIllIIIl.bestPos = lIIlllIlIllIIIl.getBreakPos(lIIlllIlIllIIIl.target);
            if (lIIlllIlIllIIIl.breakDelayLeft > 0) {
                --lIIlllIlIllIIIl.breakDelayLeft;
            } else {
                lIIlllIlIllIIIl.breakDelayLeft = lIIlllIlIllIIIl.breakDelay.get();
                lIIlllIlIllIIIl.breakBed(lIIlllIlIllIIIl.bestPos);
            }
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private void doAutoMove() {
        if (InvUtils.findItemInHotbar(lIIlllIIlIIlIll -> lIIlllIIlIIlIll.getItem() instanceof BedItem) == -1) {
            BedAuraPlus lIIlllIIllIIllI;
            int lIIlllIIllIIlll = InvUtils.findItemInMain(lIIlllIIlIIllIl -> lIIlllIIlIIllIl.getItem() instanceof BedItem);
            InvUtils.move().from(lIIlllIIllIIlll).toHotbar(lIIlllIIllIIllI.autoMoveSlot.get() - 1);
        }
    }

    private void placeBed(BlockPos lIIlllIlIlIlIIl) {
        Hand lIIlllIlIlIIlll;
        int lIIlllIlIlIlIII;
        BedAuraPlus lIIlllIlIlIIllI;
        if (lIIlllIlIlIlIIl == null || InvUtils.findItemInAll(lIIlllIIIllIllI -> lIIlllIIIllIllI.getItem() instanceof BedItem) == -1) {
            return;
        }
        if (lIIlllIlIlIIllI.autoMove.get().booleanValue()) {
            lIIlllIlIlIIllI.doAutoMove();
        }
        if ((lIIlllIlIlIlIII = InvUtils.findItemInHotbar(lIIlllIIIlllIII -> lIIlllIIIlllIII.getItem() instanceof BedItem)) == -1) {
            return;
        }
        if (lIIlllIlIlIIllI.autoSwitch.get().booleanValue()) {
            lIIlllIlIlIIllI.mc.player.inventory.selectedSlot = lIIlllIlIlIlIII;
        }
        if ((lIIlllIlIlIIlll = InvUtils.getHand(lIIlllIIIllllII -> lIIlllIIIllllII.getItem() instanceof BedItem)) == null) {
            return;
        }
        Rotations.rotate(lIIlllIlIlIIllI.yawFromDir(lIIlllIlIlIIllI.direction), lIIlllIlIlIIllI.mc.player.pitch, () -> {
            BedAuraPlus lIIlllIIlIIIlIl;
            BlockUtils.place(lIIlllIlIlIlIIl, lIIlllIlIlIIlll, lIIlllIlIlIlIII, false, 100, lIIlllIIlIIIlIl.noSwing.get() == false, true, lIIlllIIlIIIlIl.autoSwitch.get(), lIIlllIIlIIIlIl.swapBack.get());
        });
    }

    public BedAuraPlus() {
        super(Categories.Exclusive, "bed-aura+", "Bed Aura+");
        BedAuraPlus lIIlllIllIIlIll;
        lIIlllIllIIlIll.sgPlace = lIIlllIllIIlIll.settings.createGroup("Place");
        lIIlllIllIIlIll.sgBreak = lIIlllIllIIlIll.settings.createGroup("Break");
        lIIlllIllIIlIll.sgPause = lIIlllIllIIlIll.settings.createGroup("Pause");
        lIIlllIllIIlIll.sgMisc = lIIlllIllIIlIll.settings.createGroup("Misc");
        lIIlllIllIIlIll.sgRender = lIIlllIllIIlIll.settings.createGroup("Render");
        lIIlllIllIIlIll.place = lIIlllIllIIlIll.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Bed Aura to place beds.").defaultValue(true).build());
        lIIlllIllIIlIll.placeMode = lIIlllIllIIlIll.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The way beds are allowed to be placed near you.").defaultValue(Safety.Safe).build());
        lIIlllIllIIlIll.placeDelay = lIIlllIllIIlIll.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The tick delay for placing beds.").defaultValue(9).min(0).sliderMax(20).build());
        lIIlllIllIIlIll.breakDelay = lIIlllIllIIlIll.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The tick delay for breaking beds.").defaultValue(0).min(0).sliderMax(20).build());
        lIIlllIllIIlIll.breakMode = lIIlllIllIIlIll.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The way beds are allowed to be broken near you.").defaultValue(Safety.Safe).build());
        lIIlllIllIIlIll.pauseOnEat = lIIlllIllIIlIll.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
        lIIlllIllIIlIll.pauseOnDrink = lIIlllIllIIlIll.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
        lIIlllIllIIlIll.pauseOnMine = lIIlllIllIIlIll.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
        lIIlllIllIIlIll.targetRange = lIIlllIllIIlIll.sgMisc.add(new DoubleSetting.Builder().name("range").description("The maximum range for players to be targeted.").defaultValue(4.0).min(0.0).sliderMax(5.0).build());
        lIIlllIllIIlIll.autoSwitch = lIIlllIllIIlIll.sgMisc.add(new BoolSetting.Builder().name("switch").description("Switches to a bed automatically.").defaultValue(true).build());
        lIIlllIllIIlIll.swapBack = lIIlllIllIIlIll.sgMisc.add(new BoolSetting.Builder().name("swap").description("Switches back to previous slot after placing.").defaultValue(true).build());
        lIIlllIllIIlIll.autoMove = lIIlllIllIIlIll.sgMisc.add(new BoolSetting.Builder().name("move").description("Moves beds into a selected slot.").defaultValue(false).build());
        lIIlllIllIIlIll.autoMoveSlot = lIIlllIllIIlIll.sgMisc.add(new IntSetting.Builder().name("move-slot").description("The slot Auto Move.").defaultValue(9).min(1).sliderMin(1).max(9).sliderMax(9).build());
        lIIlllIllIIlIll.noSwing = lIIlllIllIIlIll.sgMisc.add(new BoolSetting.Builder().name("no-swing").description("Disables hand swings clientside.").defaultValue(false).build());
        lIIlllIllIIlIll.minDamage = lIIlllIllIIlIll.sgMisc.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage to inflict on your target.").defaultValue(7.0).min(0.0).sliderMax(20.0).max(20.0).build());
        lIIlllIllIIlIll.maxSelfDamage = lIIlllIllIIlIll.sgMisc.add(new DoubleSetting.Builder().name("max-self-dmg").description(".").defaultValue(7.0).min(0.0).sliderMax(20.0).max(20.0).build());
        lIIlllIllIIlIll.minHealth = lIIlllIllIIlIll.sgMisc.add(new DoubleSetting.Builder().name("min-health").description("The minimum health required for Bed Aura to work.").defaultValue(4.0).min(0.0).sliderMax(36.0).max(36.0).build());
        lIIlllIllIIlIll.priority = lIIlllIllIIlIll.sgMisc.add(new EnumSetting.Builder().name("priority").description("How to select the player to target.").defaultValue(SortPriority.LowestHealth).build());
        lIIlllIllIIlIll.render = lIIlllIllIIlIll.sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block where it is placing a bed.").defaultValue(true).build());
        lIIlllIllIIlIll.sideColor = lIIlllIllIIlIll.sgRender.add(new ColorSetting.Builder().name("place-side-color").description("The side color for positions to be placed.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
        lIIlllIllIIlIll.lineColor = lIIlllIllIIlIll.sgRender.add(new ColorSetting.Builder().name("place-line-color").description("The line color for positions to be placed.").defaultValue(new SettingColor(15, 255, 211, 255)).build());
        lIIlllIllIIlIll.shapeMode = lIIlllIllIIlIll.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    }

    private static final class Stage
    extends Enum<Stage> {
        private static final /* synthetic */ Stage[] $VALUES;
        public static final /* synthetic */ /* enum */ Stage Breaking;
        public static final /* synthetic */ /* enum */ Stage Placing;

        public static Stage[] values() {
            return (Stage[])$VALUES.clone();
        }

        private static /* synthetic */ Stage[] $values() {
            return new Stage[]{Placing, Breaking};
        }

        private Stage() {
            Stage llllllllllllllllIlllIIllllIIllll;
        }

        static {
            Placing = new Stage();
            Breaking = new Stage();
            $VALUES = Stage.$values();
        }

        public static Stage valueOf(String llllllllllllllllIlllIIllllIlIlIl) {
            return Enum.valueOf(Stage.class, llllllllllllllllIlllIIllllIlIlIl);
        }
    }
}

