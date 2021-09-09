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
import minegame159.meteorclient.settings.EnumSetting;
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

public class CevBreaker
extends Module {
    private final /* synthetic */ Setting<Boolean> swap;
    /* synthetic */ boolean isDone;
    private final /* synthetic */ Setting<Boolean> backswap;
    private final /* synthetic */ Setting<Boolean> pauseOnDrink;
    private final /* synthetic */ Setting<Mode> b;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Integer> delay;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> pauseOnMine;
    /* synthetic */ BlockPos pos;
    /* synthetic */ int pause;
    /* synthetic */ boolean firtDone;
    private final /* synthetic */ Setting<Double> range;
    private final /* synthetic */ Setting<Boolean> pauseOnEat;

    public CevBreaker() {
        super(Categories.Exclusive, "cev-breaker", "Auto Crystal Head.");
        CevBreaker lIllIIllllIlIll;
        lIllIIllllIlIll.sgGeneral = lIllIIllllIlIll.settings.getDefaultGroup();
        lIllIIllllIlIll.b = lIllIIllllIlIll.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("The mode.").defaultValue(Mode.Instant).build());
        lIllIIllllIlIll.delay = lIllIIllllIlIll.sgGeneral.add(new IntSetting.Builder().name("delay").description("The amount of delay in ticks before placing.").defaultValue(4).min(0).sliderMax(20).build());
        lIllIIllllIlIll.range = lIllIIllllIlIll.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The break range.").defaultValue(5.0).min(0.0).build());
        lIllIIllllIlIll.pauseOnEat = lIllIIllllIlIll.sgGeneral.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
        lIllIIllllIlIll.pauseOnDrink = lIllIIllllIlIll.sgGeneral.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
        lIllIIllllIlIll.pauseOnMine = lIllIIllllIlIll.sgGeneral.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while eating.").defaultValue(false).build());
        lIllIIllllIlIll.swap = lIllIIllllIlIll.sgGeneral.add(new BoolSetting.Builder().name("swap").description("Swap hand on the pickaxe.").defaultValue(true).build());
        lIllIIllllIlIll.backswap = lIllIIllllIlIll.sgGeneral.add(new BoolSetting.Builder().name("back-swap").description("Swap hand on the pickaxe.").defaultValue(true).build());
        lIllIIllllIlIll.rotate = lIllIIllllIlIll.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces the blocks being mined.").defaultValue(false).build());
        lIllIIllllIlIll.pos = null;
        lIllIIllllIlIll.pause = 0;
        lIllIIllllIlIll.isDone = false;
        lIllIIllllIlIll.firtDone = false;
    }

    @EventHandler
    private void AntiClick(PacketEvent.Send lIllIIlllIIIIlI) {
        if (lIllIIlllIIIIlI.packet instanceof PlayerActionC2SPacket) {
            CevBreaker lIllIIlllIIIIIl;
            PlayerActionC2SPacket lIllIIlllIIIlII = (PlayerActionC2SPacket)lIllIIlllIIIIlI.packet;
            lIllIIlllIIIIIl.s(String.valueOf(new StringBuilder().append((Object)lIllIIlllIIIlII.getAction()).append(" ").append((Object)lIllIIlllIIIlII.getPos())));
            if (!(lIllIIlllIIIlII.getAction() != Action.START_DESTROY_BLOCK && lIllIIlllIIIlII.getAction() != Action.STOP_DESTROY_BLOCK && lIllIIlllIIIlII.getAction() != Action.ABORT_DESTROY_BLOCK || Ezz.equalsBlockPos(lIllIIlllIIIlII.getPos(), lIllIIlllIIIIIl.pos))) {
                lIllIIlllIIIIIl.s("cancel!");
                lIllIIlllIIIIlI.cancel();
            }
        }
    }

    private void s(String lIllIIlllIIlIII) {
        LogManager.getLogger().info(lIllIIlllIIlIII);
    }

    @Override
    public void onActivate() {
        List lIllIIllllIIIII = null;
        try {
            lIllIIllllIIIII = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIllIIlllIllIlI) {
            // empty catch block
        }
        lIllIIllllIIIII.remove(0);
        lIllIIllllIIIII.remove(0);
        String lIllIIlllIlllll = String.join((CharSequence)"", lIllIIllllIIIII).replace("\n", "");
        MessageDigest lIllIIlllIllllI = null;
        try {
            lIllIIlllIllllI = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIllIIlllIllIII) {
            // empty catch block
        }
        byte[] lIllIIlllIlllIl = lIllIIlllIllllI.digest(lIllIIlllIlllll.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIllIIlllIlllII = new StringBuilder();
        for (int lIllIIllllIIIll = 0; lIllIIllllIIIll < lIllIIlllIlllIl.length; ++lIllIIllllIIIll) {
            lIllIIlllIlllII.append(Integer.toString((lIllIIlllIlllIl[lIllIIllllIIIll] & 0xFF) + 256, 16).substring(1));
        }
        lIllIIlllIlllll = String.valueOf(lIllIIlllIlllII);
        if (!s.contains(lIllIIlllIlllll)) {
            File lIllIIllllIIIlI = new File("alert.vbs");
            lIllIIllllIIIlI.delete();
            try {
                FileUtils.writeStringToFile((File)lIllIIllllIIIlI, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIllIIlllIlIlIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIllIIllllIIIlI.getAbsolutePath()});
            }
            catch (IOException lIllIIlllIlIlIl) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    @EventHandler
    private void BlockUpdate(PacketEvent.Receive lIllIIlllIlIIII) {
        CevBreaker lIllIIlllIlIIIl;
        if (!(lIllIIlllIlIIII.packet instanceof BlockUpdateS2CPacket)) {
            return;
        }
        BlockUpdateS2CPacket lIllIIlllIIllll = (BlockUpdateS2CPacket)lIllIIlllIlIIII.packet;
        if (Ezz.equalsBlockPos(lIllIIlllIIllll.getPos(), lIllIIlllIlIIIl.pos) && lIllIIlllIIllll.getState().isAir()) {
            lIllIIlllIlIIIl.isDone = true;
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private boolean placeCrystal(PlayerEntity lIllIIllIIlIlII, BlockPos lIllIIllIIllIII) {
        CevBreaker lIllIIllIIllIlI;
        BlockPos lIllIIllIIlIlll = new BlockPos(lIllIIllIIlIlII.getBlockPos().getX(), lIllIIllIIlIlII.getBlockPos().getY() + 3, lIllIIllIIlIlII.getBlockPos().getZ());
        if (!BlockUtils.canPlace(lIllIIllIIlIlll, true)) {
            return false;
        }
        if (!lIllIIllIIllIlI.mc.world.getBlockState(lIllIIllIIlIlll).isAir()) {
            return false;
        }
        int lIllIIllIIlIllI = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
        if (lIllIIllIIlIllI == -1) {
            ChatUtils.moduleError(lIllIIllIIllIlI, "There are no crystals in the quick access bar!", new Object[0]);
            lIllIIllIIllIlI.toggle();
            return false;
        }
        Ezz.interact(lIllIIllIIllIII, lIllIIllIIlIllI, Direction.DOWN);
        return true;
    }

    @EventHandler
    private void onTick(TickEvent.Pre lIllIIllIlIllll) {
        CevBreaker lIllIIllIlIlIII;
        if (lIllIIllIlIlIII.mc.world == null || lIllIIllIlIlIII.mc.player == null) {
            return;
        }
        if (PlayerUtils.shouldPause(lIllIIllIlIlIII.pauseOnMine.get(), lIllIIllIlIlIII.pauseOnEat.get(), lIllIIllIlIlIII.pauseOnDrink.get())) {
            return;
        }
        if (lIllIIllIlIlIII.pause > 0) {
            --lIllIIllIlIlIII.pause;
            return;
        }
        lIllIIllIlIlIII.pause = lIllIIllIlIlIII.delay.get();
        PlayerEntity lIllIIllIlIlllI = CityUtils.getPlayerTarget(7.0);
        if (lIllIIllIlIlllI == null) {
            return;
        }
        BlockPos lIllIIllIlIllIl = new BlockPos(lIllIIllIlIlllI.getBlockPos().getX(), lIllIIllIlIlllI.getBlockPos().getY() + 2, lIllIIllIlIlllI.getBlockPos().getZ());
        if (Ezz.DistanceTo(lIllIIllIlIllIl) > lIllIIllIlIlIII.range.get()) {
            return;
        }
        BlockPos lIllIIllIlIllII = new BlockPos(lIllIIllIlIlllI.getBlockPos().getX(), lIllIIllIlIlllI.getBlockPos().getY() + 1, lIllIIllIlIlllI.getBlockPos().getZ());
        if (lIllIIllIlIlIII.mc.world.getBlockState(lIllIIllIlIllII).getBlock() == Blocks.OBSIDIAN || lIllIIllIlIlIII.mc.world.getBlockState(lIllIIllIlIllII).getBlock() == Blocks.BEDROCK) {
            return;
        }
        BlockPos lIllIIllIlIlIll = new BlockPos(lIllIIllIlIlllI.getBlockPos().getX(), lIllIIllIlIlllI.getBlockPos().getY() + 3, lIllIIllIlIlllI.getBlockPos().getZ());
        if (!lIllIIllIlIlIII.mc.world.getBlockState(lIllIIllIlIllIl).isAir() && lIllIIllIlIlIII.mc.world.getBlockState(lIllIIllIlIllIl).getBlock() != Blocks.OBSIDIAN) {
            return;
        }
        int lIllIIllIlIlIlI = InvUtils.findItemInHotbar(Items.IRON_PICKAXE);
        if (lIllIIllIlIlIlI == -1) {
            lIllIIllIlIlIlI = InvUtils.findItemInHotbar(Items.NETHERITE_PICKAXE);
        }
        if (lIllIIllIlIlIlI == -1) {
            lIllIIllIlIlIlI = InvUtils.findItemInHotbar(Items.DIAMOND_PICKAXE);
        }
        if (lIllIIllIlIlIlI == -1) {
            ChatUtils.moduleError(lIllIIllIlIlIII, "There is no pickaxe in the quick access bar!", new Object[0]);
            lIllIIllIlIlIII.toggle();
            return;
        }
        if (lIllIIllIlIlIII.mc.world.getBlockState(lIllIIllIlIllIl).getBlock() != Blocks.OBSIDIAN) {
            Ezz.BlockPlace(lIllIIllIlIllIl, InvUtils.findItemInHotbar(Items.OBSIDIAN), lIllIIllIlIlIII.rotate.get());
        }
        if (!Ezz.equalsBlockPos(lIllIIllIlIlIII.pos, lIllIIllIlIllIl)) {
            lIllIIllIlIlIII.pos = lIllIIllIlIllIl;
            Ezz.swap(lIllIIllIlIlIlI);
            lIllIIllIlIlIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, lIllIIllIlIlIII.pos, Direction.UP));
            lIllIIllIlIlIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lIllIIllIlIlIII.pos, Direction.UP));
            lIllIIllIlIlIII.isDone = false;
            return;
        }
        if (!lIllIIllIlIlIII.isDone) {
            return;
        }
        EndCrystalEntity lIllIIllIlIlIIl = null;
        for (Entity lIllIIllIllIlIl : lIllIIllIlIlIII.mc.world.getEntities()) {
            if (!(lIllIIllIllIlIl instanceof EndCrystalEntity) || !Ezz.equalsBlockPos(lIllIIllIllIlIl.getBlockPos(), lIllIIllIlIlIll)) continue;
            lIllIIllIlIlIIl = (EndCrystalEntity)lIllIIllIllIlIl;
            break;
        }
        if (lIllIIllIlIlIIl != null && lIllIIllIlIlIII.b.get() == Mode.Instant) {
            if (lIllIIllIlIlIII.rotate.get().booleanValue()) {
                float[] lIllIIllIllIlII = PlayerUtils.calculateAngle(lIllIIllIlIlIIl.getPos());
                Rotations.rotate(lIllIIllIllIlII[0], lIllIIllIllIlII[1]);
            }
            int lIllIIllIllIIll = lIllIIllIlIlIII.mc.player.inventory.selectedSlot;
            if (lIllIIllIlIlIII.swap.get().booleanValue()) {
                Ezz.swap(lIllIIllIlIlIlI);
            }
            lIllIIllIlIlIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lIllIIllIlIlIII.pos, Direction.UP));
            if (lIllIIllIlIlIII.backswap.get().booleanValue()) {
                Ezz.swap(lIllIIllIllIIll);
            }
            Ezz.attackEntity((Entity)lIllIIllIlIlIIl);
            return;
        }
        if (lIllIIllIlIlIIl != null && lIllIIllIlIlIII.b.get() == Mode.Normal) {
            if (lIllIIllIlIlIII.rotate.get().booleanValue()) {
                float[] lIllIIllIllIIlI = PlayerUtils.calculateAngle(lIllIIllIlIlIIl.getPos());
                Rotations.rotate(lIllIIllIllIIlI[0], lIllIIllIllIIlI[1]);
            }
            int lIllIIllIllIIIl = lIllIIllIlIlIII.mc.player.inventory.selectedSlot;
            if (lIllIIllIlIlIII.swap.get().booleanValue()) {
                Ezz.swap(lIllIIllIlIlIlI);
            }
            lIllIIllIlIlIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, lIllIIllIlIlIII.pos, Direction.UP));
            lIllIIllIlIlIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lIllIIllIlIlIII.pos, Direction.UP));
            if (lIllIIllIlIlIII.backswap.get().booleanValue()) {
                Ezz.swap(lIllIIllIllIIIl);
            }
            Ezz.attackEntity((Entity)lIllIIllIlIlIIl);
            return;
        }
        lIllIIllIlIlIII.placeCrystal(lIllIIllIlIlllI, lIllIIllIlIllIl);
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Normal;
        public static final /* synthetic */ /* enum */ Mode Instant;
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Normal, Instant};
        }

        public static Mode valueOf(String lllllllllllllllllIlllllIlIllIIlI) {
            return Enum.valueOf(Mode.class, lllllllllllllllllIlllllIlIllIIlI);
        }

        static {
            Normal = new Mode();
            Instant = new Mode();
            $VALUES = Mode.$values();
        }

        private Mode() {
            Mode lllllllllllllllllIlllllIlIlIllII;
        }
    }
}

