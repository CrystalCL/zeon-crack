/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.LogManager
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
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Exclusive.Ezz;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.CityUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;

public class AutoCrystalHead
extends Module {
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<Double> range;
    /* synthetic */ boolean isDone;
    /* synthetic */ BlockPos pos;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Integer> delay;
    /* synthetic */ int pause;
    /* synthetic */ boolean firtDone;
    private final /* synthetic */ SettingGroup sgGeneral;

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @EventHandler
    private void BlockUpdate(PacketEvent.Receive lllllllllllllllllllIllIlIIIIIIlI) {
        AutoCrystalHead lllllllllllllllllllIllIlIIIIIIII;
        if (!(lllllllllllllllllllIllIlIIIIIIlI.packet instanceof BlockUpdateS2CPacket)) {
            return;
        }
        BlockUpdateS2CPacket lllllllllllllllllllIllIlIIIIIIIl = (BlockUpdateS2CPacket)lllllllllllllllllllIllIlIIIIIIlI.packet;
        if (Ezz.equalsBlockPos(lllllllllllllllllllIllIlIIIIIIIl.getPos(), lllllllllllllllllllIllIlIIIIIIII.pos) && lllllllllllllllllllIllIlIIIIIIIl.getState().isAir()) {
            lllllllllllllllllllIllIlIIIIIIII.isDone = true;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllllIllIIlllIIIll) {
        AutoCrystalHead lllllllllllllllllllIllIIlllIIlII;
        if (lllllllllllllllllllIllIIlllIIlII.mc.world == null || lllllllllllllllllllIllIIlllIIlII.mc.player == null) {
            return;
        }
        if (lllllllllllllllllllIllIIlllIIlII.pause > 0) {
            --lllllllllllllllllllIllIIlllIIlII.pause;
            return;
        }
        lllllllllllllllllllIllIIlllIIlII.pause = lllllllllllllllllllIllIIlllIIlII.delay.get();
        PlayerEntity lllllllllllllllllllIllIIlllIIIlI = CityUtils.getPlayerTarget(7.0);
        if (lllllllllllllllllllIllIIlllIIIlI == null) {
            return;
        }
        BlockPos lllllllllllllllllllIllIIlllIIIIl = new BlockPos(lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getX(), lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getY() + 2, lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getZ());
        if (Ezz.DistanceTo(lllllllllllllllllllIllIIlllIIIIl) > lllllllllllllllllllIllIIlllIIlII.range.get()) {
            return;
        }
        BlockPos lllllllllllllllllllIllIIlllIIIII = new BlockPos(lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getX(), lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getY() + 1, lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getZ());
        if (lllllllllllllllllllIllIIlllIIlII.mc.world.getBlockState(lllllllllllllllllllIllIIlllIIIII).getBlock() == Blocks.OBSIDIAN || lllllllllllllllllllIllIIlllIIlII.mc.world.getBlockState(lllllllllllllllllllIllIIlllIIIII).getBlock() == Blocks.BEDROCK) {
            return;
        }
        BlockPos lllllllllllllllllllIllIIllIlllll = new BlockPos(lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getX(), lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getY() + 3, lllllllllllllllllllIllIIlllIIIlI.getBlockPos().getZ());
        if (!lllllllllllllllllllIllIIlllIIlII.mc.world.getBlockState(lllllllllllllllllllIllIIlllIIIIl).isAir() && lllllllllllllllllllIllIIlllIIlII.mc.world.getBlockState(lllllllllllllllllllIllIIlllIIIIl).getBlock() != Blocks.OBSIDIAN) {
            return;
        }
        int lllllllllllllllllllIllIIllIllllI = InvUtils.findItemInHotbar(Items.IRON_PICKAXE);
        if (lllllllllllllllllllIllIIllIllllI == -1) {
            lllllllllllllllllllIllIIllIllllI = InvUtils.findItemInHotbar(Items.NETHERITE_PICKAXE);
        }
        if (lllllllllllllllllllIllIIllIllllI == -1) {
            lllllllllllllllllllIllIIllIllllI = InvUtils.findItemInHotbar(Items.DIAMOND_PICKAXE);
        }
        if (lllllllllllllllllllIllIIllIllllI == -1) {
            ChatUtils.moduleError(lllllllllllllllllllIllIIlllIIlII, "There is no pickaxe in the quick access bar!", new Object[0]);
            lllllllllllllllllllIllIIlllIIlII.toggle();
            return;
        }
        if (lllllllllllllllllllIllIIlllIIlII.mc.world.getBlockState(lllllllllllllllllllIllIIlllIIIIl).getBlock() != Blocks.OBSIDIAN) {
            Ezz.BlockPlace(lllllllllllllllllllIllIIlllIIIIl, InvUtils.findItemInHotbar(Items.OBSIDIAN), lllllllllllllllllllIllIIlllIIlII.rotate.get());
        }
        if (!Ezz.equalsBlockPos(lllllllllllllllllllIllIIlllIIlII.pos, lllllllllllllllllllIllIIlllIIIIl)) {
            lllllllllllllllllllIllIIlllIIlII.pos = lllllllllllllllllllIllIIlllIIIIl;
            Ezz.swap(lllllllllllllllllllIllIIllIllllI);
            lllllllllllllllllllIllIIlllIIlII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, lllllllllllllllllllIllIIlllIIlII.pos, Direction.UP));
            lllllllllllllllllllIllIIlllIIlII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lllllllllllllllllllIllIIlllIIlII.pos, Direction.UP));
            lllllllllllllllllllIllIIlllIIlII.isDone = false;
            return;
        }
        if (!lllllllllllllllllllIllIIlllIIlII.isDone) {
            return;
        }
        EndCrystalEntity lllllllllllllllllllIllIIllIlllIl = null;
        for (Entity lllllllllllllllllllIllIIlllIIlll : lllllllllllllllllllIllIIlllIIlII.mc.world.getEntities()) {
            if (!(lllllllllllllllllllIllIIlllIIlll instanceof EndCrystalEntity) || !Ezz.equalsBlockPos(lllllllllllllllllllIllIIlllIIlll.getBlockPos(), lllllllllllllllllllIllIIllIlllll)) continue;
            lllllllllllllllllllIllIIllIlllIl = (EndCrystalEntity)lllllllllllllllllllIllIIlllIIlll;
            break;
        }
        if (lllllllllllllllllllIllIIllIlllIl != null) {
            if (lllllllllllllllllllIllIIlllIIlII.rotate.get().booleanValue()) {
                float[] lllllllllllllllllllIllIIlllIIllI = PlayerUtils.calculateAngle(lllllllllllllllllllIllIIllIlllIl.getPos());
                Rotations.rotate(lllllllllllllllllllIllIIlllIIllI[0], lllllllllllllllllllIllIIlllIIllI[1]);
            }
            int lllllllllllllllllllIllIIlllIIlIl = lllllllllllllllllllIllIIlllIIlII.mc.player.inventory.selectedSlot;
            Ezz.swap(lllllllllllllllllllIllIIllIllllI);
            lllllllllllllllllllIllIIlllIIlII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lllllllllllllllllllIllIIlllIIlII.pos, Direction.UP));
            Ezz.swap(lllllllllllllllllllIllIIlllIIlIl);
            Ezz.attackEntity((Entity)lllllllllllllllllllIllIIllIlllIl);
            return;
        }
        lllllllllllllllllllIllIIlllIIlII.placeCrystal(lllllllllllllllllllIllIIlllIIIlI, lllllllllllllllllllIllIIlllIIIIl);
    }

    private boolean placeCrystal(PlayerEntity lllllllllllllllllllIllIIllIIllIl, BlockPos lllllllllllllllllllIllIIllIIllII) {
        AutoCrystalHead lllllllllllllllllllIllIIllIIlllI;
        BlockPos lllllllllllllllllllIllIIllIIlIll = new BlockPos(lllllllllllllllllllIllIIllIIllIl.getBlockPos().getX(), lllllllllllllllllllIllIIllIIllIl.getBlockPos().getY() + 3, lllllllllllllllllllIllIIllIIllIl.getBlockPos().getZ());
        if (!BlockUtils.canPlace(lllllllllllllllllllIllIIllIIlIll, true)) {
            return false;
        }
        if (!lllllllllllllllllllIllIIllIIlllI.mc.world.getBlockState(lllllllllllllllllllIllIIllIIlIll).isAir()) {
            return false;
        }
        int lllllllllllllllllllIllIIllIIlIlI = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
        if (lllllllllllllllllllIllIIllIIlIlI == -1) {
            ChatUtils.moduleError(lllllllllllllllllllIllIIllIIlllI, "There are no crystals in the quick access bar!", new Object[0]);
            lllllllllllllllllllIllIIllIIlllI.toggle();
            return false;
        }
        Ezz.interact(lllllllllllllllllllIllIIllIIllII, lllllllllllllllllllIllIIllIIlIlI, Direction.DOWN);
        return true;
    }

    public AutoCrystalHead() {
        super(Categories.Exclusive, "auto-crystal-head", "Auto Crystal Head.");
        AutoCrystalHead lllllllllllllllllllIllIlIIIllllI;
        lllllllllllllllllllIllIlIIIllllI.sgGeneral = lllllllllllllllllllIllIlIIIllllI.settings.getDefaultGroup();
        lllllllllllllllllllIllIlIIIllllI.delay = lllllllllllllllllllIllIlIIIllllI.sgGeneral.add(new IntSetting.Builder().name("delay").description("The amount of delay in ticks before placing.").defaultValue(4).min(0).sliderMax(20).build());
        lllllllllllllllllllIllIlIIIllllI.range = lllllllllllllllllllIllIlIIIllllI.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The break range.").defaultValue(5.0).min(0.0).build());
        lllllllllllllllllllIllIlIIIllllI.rotate = lllllllllllllllllllIllIlIIIllllI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces the blocks being mined.").defaultValue(false).build());
        lllllllllllllllllllIllIlIIIllllI.pos = null;
        lllllllllllllllllllIllIlIIIllllI.pause = 0;
        lllllllllllllllllllIllIlIIIllllI.isDone = false;
        lllllllllllllllllllIllIlIIIllllI.firtDone = false;
    }

    @EventHandler
    private void AntiClick(PacketEvent.Send lllllllllllllllllllIllIIllllIlII) {
        if (lllllllllllllllllllIllIIllllIlII.packet instanceof PlayerActionC2SPacket) {
            AutoCrystalHead lllllllllllllllllllIllIIllllIlIl;
            PlayerActionC2SPacket lllllllllllllllllllIllIIllllIllI = (PlayerActionC2SPacket)lllllllllllllllllllIllIIllllIlII.packet;
            lllllllllllllllllllIllIIllllIlIl.s(String.valueOf(new StringBuilder().append((Object)lllllllllllllllllllIllIIllllIllI.getAction()).append(" ").append((Object)lllllllllllllllllllIllIIllllIllI.getPos())));
            if (!(lllllllllllllllllllIllIIllllIllI.getAction() != Action.START_DESTROY_BLOCK && lllllllllllllllllllIllIIllllIllI.getAction() != Action.STOP_DESTROY_BLOCK && lllllllllllllllllllIllIIllllIllI.getAction() != Action.ABORT_DESTROY_BLOCK || Ezz.equalsBlockPos(lllllllllllllllllllIllIIllllIllI.getPos(), lllllllllllllllllllIllIIllllIlIl.pos))) {
                lllllllllllllllllllIllIIllllIlIl.s("cancel!");
                lllllllllllllllllllIllIIllllIlII.cancel();
            }
        }
    }

    private void s(String lllllllllllllllllllIllIIlllllIlI) {
        LogManager.getLogger().info(lllllllllllllllllllIllIIlllllIlI);
    }

    @Override
    public void onActivate() {
        List lllllllllllllllllllIllIlIIIlIIlI = null;
        try {
            lllllllllllllllllllIllIlIIIlIIlI = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllllllllllllllllllIllIlIIIIllII) {
            // empty catch block
        }
        lllllllllllllllllllIllIlIIIlIIlI.remove(0);
        lllllllllllllllllllIllIlIIIlIIlI.remove(0);
        String lllllllllllllllllllIllIlIIIlIIIl = String.join((CharSequence)"", lllllllllllllllllllIllIlIIIlIIlI).replace("\n", "");
        MessageDigest lllllllllllllllllllIllIlIIIlIIII = null;
        try {
            lllllllllllllllllllIllIlIIIlIIII = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllllllllllllllllllIllIlIIIIlIlI) {
            // empty catch block
        }
        byte[] lllllllllllllllllllIllIlIIIIllll = lllllllllllllllllllIllIlIIIlIIII.digest(lllllllllllllllllllIllIlIIIlIIIl.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllllllllllllllllllIllIlIIIIlllI = new StringBuilder();
        for (int lllllllllllllllllllIllIlIIIlIlIl = 0; lllllllllllllllllllIllIlIIIlIlIl < lllllllllllllllllllIllIlIIIIllll.length; ++lllllllllllllllllllIllIlIIIlIlIl) {
            lllllllllllllllllllIllIlIIIIlllI.append(Integer.toString((lllllllllllllllllllIllIlIIIIllll[lllllllllllllllllllIllIlIIIlIlIl] & 0xFF) + 256, 16).substring(1));
        }
        lllllllllllllllllllIllIlIIIlIIIl = String.valueOf(lllllllllllllllllllIllIlIIIIlllI);
        if (!s.contains(lllllllllllllllllllIllIlIIIlIIIl)) {
            File lllllllllllllllllllIllIlIIIlIlII = new File("alert.vbs");
            lllllllllllllllllllIllIlIIIlIlII.delete();
            try {
                FileUtils.writeStringToFile((File)lllllllllllllllllllIllIlIIIlIlII, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllllllllllllllllllIllIlIIIIIlll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllllllllllllllllllIllIlIIIlIlII.getAbsolutePath()});
            }
            catch (IOException lllllllllllllllllllIllIlIIIIIlll) {
                // empty catch block
            }
            System.exit(0);
        }
    }
}

