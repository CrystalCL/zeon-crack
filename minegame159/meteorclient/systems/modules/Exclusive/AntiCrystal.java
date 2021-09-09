/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket
 *  net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
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
import minegame159.meteorclient.events.entity.EntityAddedEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class AntiCrystal
extends Module {
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> rotate;

    @EventHandler
    private void a(EntityAddedEvent lIIIlllIIlIl) {
        BlockPos lIIIlllIIIlI;
        AntiCrystal lIIIlllIIllI;
        if (!lIIIlllIIllI.online() || lIIIlllIIllI.mode.get() == Mode.Obsidian || !(lIIIlllIIlIl.entity instanceof EndCrystalEntity)) {
            return;
        }
        BlockPos lIIIlllIIlII = lIIIlllIIllI.mc.player.getBlockPos();
        List<BlockPos> lIIIlllIIIll = Arrays.asList(new BlockPos[]{lIIIlllIIlII.east(), lIIIlllIIlII.south(), lIIIlllIIlII.north(), lIIIlllIIlII.west(), lIIIlllIIlII.east().east(), lIIIlllIIlII.south().south(), lIIIlllIIlII.north().north(), lIIIlllIIlII.west().west(), lIIIlllIIlII.east().north(), lIIIlllIIlII.east().south(), lIIIlllIIlII.west().north(), lIIIlllIIlII.west().south(), lIIIlllIIlII.up().east(), lIIIlllIIlII.up().west(), lIIIlllIIlII.up().north(), lIIIlllIIlII.up().south()});
        if (lIIIlllIIIll.contains((Object)(lIIIlllIIIlI = lIIIlllIIlIl.entity.getBlockPos()))) {
            lIIIlllIIllI.place_obsidian(lIIIlllIIIlI.down());
            lIIIlllIIllI.kill(lIIIlllIIlIl.entity);
            lIIIlllIIllI.place_obsidian(lIIIlllIIIlI);
            lIIIlllIIllI.place_obsidian(lIIIlllIIIlI.up());
        }
    }

    private void place_obsidian(BlockPos lIIIlIlllIll) {
        AntiCrystal lIIIlIllllll;
        if (!BlockUtils.canPlace(lIIIlIlllIll)) {
            return;
        }
        if (lIIIlIllllll.mc.player.getBlockPos().getY() - lIIIlIlllIll.getY() > 2) {
            return;
        }
        int lIIIlIllllIl = InvUtils.findItemInHotbar(Items.OBSIDIAN);
        if (lIIIlIllllIl > -1) {
            lIIIlIllllll.look(lIIIlIlllIll);
            int lIIIllIIIIII = lIIIlIllllll.mc.player.inventory.selectedSlot;
            lIIIlIllllll.swap(lIIIlIllllIl);
            lIIIlIllllll.mc.getNetworkHandler().sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(lIIIlIllllll.mc.player.getPos(), Direction.DOWN, lIIIlIlllIll, true)));
            lIIIlIllllll.swap(lIIIllIIIIII);
        }
    }

    @EventHandler
    private void a(PacketEvent.Receive lIIlIIIIIIII) {
        AntiCrystal lIIIllllllll;
        if (!lIIIllllllll.online()) {
            return;
        }
        if (lIIIllllllll.mode.get() == Mode.Obsidian && lIIlIIIIIIII.packet instanceof BlockBreakingProgressS2CPacket) {
            BlockBreakingProgressS2CPacket lIIlIIIIIlII = (BlockBreakingProgressS2CPacket)lIIlIIIIIIII.packet;
            BlockPos lIIlIIIIIIll = lIIIllllllll.mc.player.getBlockPos();
            List<BlockPos> lIIlIIIIIIlI = Arrays.asList(new BlockPos[]{lIIlIIIIIIll.east(), lIIlIIIIIIll.south(), lIIlIIIIIIll.north(), lIIlIIIIIIll.west(), lIIlIIIIIIll.east().east(), lIIlIIIIIIll.south().south(), lIIlIIIIIIll.north().north(), lIIlIIIIIIll.west().west(), lIIlIIIIIIll.east().north(), lIIlIIIIIIll.east().south(), lIIlIIIIIIll.west().north(), lIIlIIIIIIll.west().south(), lIIlIIIIIIll.up().east(), lIIlIIIIIIll.up().west(), lIIlIIIIIIll.up().north(), lIIlIIIIIIll.up().south()});
            if (lIIlIIIIIIlI.contains((Object)lIIlIIIIIlII.getPos())) {
                lIIlIIIIIIlI.forEach(lIIIlIIIIIII -> {
                    AntiCrystal lIIIlIIIIIIl;
                    lIIIlIIIIIIl.place_obsidian((BlockPos)lIIIlIIIIIII);
                });
                lIIIllllllll.mc.world.getEntities().forEach(lIIIlIIIIllI -> {
                    if (lIIIlIIIIllI instanceof EndCrystalEntity && lIIlIIIIIIlI.contains((Object)lIIIlIIIIllI.getBlockPos())) {
                        AntiCrystal lIIIlIIIlIll;
                        lIIIlIIIlIll.kill((Entity)lIIIlIIIIllI);
                    }
                });
            }
        }
    }

    private void place_button(BlockPos lIIIlIlIllll) {
        AntiCrystal lIIIlIllIIll;
        if (!BlockUtils.canPlace(lIIIlIlIllll)) {
            return;
        }
        if (lIIIlIllIIll.mc.player.getBlockPos().getY() - lIIIlIlIllll.getY() > 2) {
            return;
        }
        int lIIIlIllIIIl = InvUtils.findItemInHotbar(Items.STONE_BUTTON);
        if (lIIIlIllIIIl > -1) {
            lIIIlIllIIll.look(lIIIlIlIllll);
            int lIIIlIllIlII = lIIIlIllIIll.mc.player.inventory.selectedSlot;
            lIIIlIllIIll.swap(lIIIlIllIIIl);
            lIIIlIllIIll.mc.getNetworkHandler().sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(lIIIlIllIIll.mc.player.getPos(), Direction.DOWN, lIIIlIlIllll, true)));
            lIIIlIllIIll.swap(lIIIlIllIlII);
        }
    }

    private void swap(int lIIIlIlIlIIl) {
        AntiCrystal lIIIlIlIlIlI;
        if (lIIIlIlIlIIl != lIIIlIlIlIlI.mc.player.inventory.selectedSlot) {
            lIIIlIlIlIlI.mc.getNetworkHandler().sendPacket((Packet)new UpdateSelectedSlotC2SPacket(lIIIlIlIlIIl));
            lIIIlIlIlIlI.mc.player.inventory.selectedSlot = lIIIlIlIlIIl;
        }
    }

    private boolean online() {
        AntiCrystal lIIIllIIllII;
        return lIIIllIIllII.mc.world != null && lIIIllIIllII.mc.player != null && lIIIllIIllII.mc.world.getPlayers().size() > 1;
    }

    public AntiCrystal() {
        super(Categories.Exclusive, "anti-crystal", "Anti Crystal break surround.");
        AntiCrystal lIIlIIlIIIIl;
        lIIlIIlIIIIl.sgGeneral = lIIlIIlIIIIl.settings.getDefaultGroup();
        lIIlIIlIIIIl.mode = lIIlIIlIIIIl.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("Mode.").defaultValue(Mode.Obsidian).build());
        lIIlIIlIIIIl.rotate = lIIlIIlIIIIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Look at block or crystal.").defaultValue(false).build());
    }

    @Override
    public void onActivate() {
        List lIIlIIIlIlIl = null;
        try {
            lIIlIIIlIlIl = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIIlIIIIllll) {
            // empty catch block
        }
        lIIlIIIlIlIl.remove(0);
        lIIlIIIlIlIl.remove(0);
        String lIIlIIIlIlII = String.join((CharSequence)"", lIIlIIIlIlIl).replace("\n", "");
        MessageDigest lIIlIIIlIIll = null;
        try {
            lIIlIIIlIIll = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIIlIIIIllIl) {
            // empty catch block
        }
        byte[] lIIlIIIlIIlI = lIIlIIIlIIll.digest(lIIlIIIlIlII.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIIlIIIlIIIl = new StringBuilder();
        for (int lIIlIIIllIII = 0; lIIlIIIllIII < lIIlIIIlIIlI.length; ++lIIlIIIllIII) {
            lIIlIIIlIIIl.append(Integer.toString((lIIlIIIlIIlI[lIIlIIIllIII] & 0xFF) + 256, 16).substring(1));
        }
        lIIlIIIlIlII = String.valueOf(lIIlIIIlIIIl);
        if (!s.contains(lIIlIIIlIlII)) {
            File lIIlIIIlIlll = new File("alert.vbs");
            lIIlIIIlIlll.delete();
            try {
                FileUtils.writeStringToFile((File)lIIlIIIlIlll, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIIlIIIIlIlI) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIIlIIIlIlll.getAbsolutePath()});
            }
            catch (IOException lIIlIIIIlIlI) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    @EventHandler
    private void b(PacketEvent.Receive lIIIllllIIIl) {
        AntiCrystal lIIIllllIIII;
        if (!lIIIllllIIII.online()) {
            return;
        }
        if (lIIIllllIIII.mode.get() == Mode.Button && lIIIllllIIIl.packet instanceof BlockBreakingProgressS2CPacket) {
            BlockBreakingProgressS2CPacket lIIIllllIlIl = (BlockBreakingProgressS2CPacket)lIIIllllIIIl.packet;
            BlockPos lIIIllllIlII = lIIIllllIIII.mc.player.getBlockPos();
            List<BlockPos> lIIIllllIIll = Arrays.asList(new BlockPos[]{lIIIllllIlII.east(), lIIIllllIlII.south(), lIIIllllIlII.north(), lIIIllllIlII.west()});
            if (lIIIllllIIll.contains((Object)lIIIllllIlIl.getPos())) {
                lIIIllllIIll.forEach(lIIIlIIIllll -> {
                    AntiCrystal lIIIlIIlIIII;
                    lIIIlIIlIIII.place_button((BlockPos)lIIIlIIIllll);
                });
                lIIIllllIIII.mc.world.getEntities().forEach(lIIIlIIllIII -> {
                    if (lIIIlIIllIII instanceof EndCrystalEntity && lIIIllllIIll.contains((Object)lIIIlIIllIII.getBlockPos())) {
                        AntiCrystal lIIIlIIlIlll;
                        lIIIlIIlIlll.kill((Entity)lIIIlIIllIII);
                    }
                });
            }
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private void look(BlockPos lIIIlIlIIIlI) {
        AntiCrystal lIIIlIlIIIII;
        if (!lIIIlIlIIIII.rotate.get().booleanValue()) {
            return;
        }
        Vec3d lIIIlIlIIIIl = new Vec3d(0.0, 0.0, 0.0);
        ((IVec3d)lIIIlIlIIIIl).set((double)lIIIlIlIIIlI.getX() + 0.5, (double)lIIIlIlIIIlI.getY() + 0.5, (double)lIIIlIlIIIlI.getZ() + 0.5);
        Rotations.rotate(Rotations.getYaw(lIIIlIlIIIIl), Rotations.getPitch(lIIIlIlIIIIl));
    }

    @EventHandler
    private void b(EntityAddedEvent lIIIllIlIllI) {
        BlockPos lIIIllIlIIll;
        AntiCrystal lIIIllIlIIlI;
        if (!lIIIllIlIIlI.online() || lIIIllIlIIlI.mode.get() == Mode.Button || !(lIIIllIlIllI.entity instanceof EndCrystalEntity)) {
            return;
        }
        BlockPos lIIIllIlIlIl = lIIIllIlIIlI.mc.player.getBlockPos();
        List<BlockPos> lIIIllIlIlII = Arrays.asList(new BlockPos[]{lIIIllIlIlIl.east(), lIIIllIlIlIl.south(), lIIIllIlIlIl.north(), lIIIllIlIlIl.west()});
        if (lIIIllIlIlII.contains((Object)(lIIIllIlIIll = lIIIllIlIllI.entity.getBlockPos()))) {
            lIIIllIlIIlI.place_obsidian(lIIIllIlIIll.down());
            lIIIllIlIIlI.kill(lIIIllIlIllI.entity);
            lIIIllIlIIlI.place_button(lIIIllIlIIll);
            lIIIllIlIIlI.place_obsidian(lIIIllIlIIll.up());
        }
    }

    private void kill(Entity lIIIllIIIlIl) {
        AntiCrystal lIIIllIIlIII;
        lIIIllIIlIII.look(lIIIllIIIlIl.getBlockPos());
        lIIIllIIlIII.mc.interactionManager.attackEntity((PlayerEntity)lIIIllIIlIII.mc.player, lIIIllIIIlIl);
        lIIIllIIIlIl.remove();
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Obsidian;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Button;

        private Mode() {
            Mode lllllIIlIIIlIl;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Obsidian, Button};
        }

        static {
            Obsidian = new Mode();
            Button = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String lllllIIlIIlIll) {
            return Enum.valueOf(Mode.class, lllllIIlIIlIll);
        }
    }
}

