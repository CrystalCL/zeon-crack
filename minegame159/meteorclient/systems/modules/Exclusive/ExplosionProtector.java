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
 *  net.minecraft.block.TntBlock
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket
 *  net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
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
import net.minecraft.block.TntBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class ExplosionProtector
extends Module {
    private final /* synthetic */ Setting<Boolean> crystalHead;
    private final /* synthetic */ Setting<Boolean> tnt;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> rotate;

    private void swap(int lIllllIlllIlIII) {
        ExplosionProtector lIllllIlllIlIIl;
        if (lIllllIlllIlIII != lIllllIlllIlIIl.mc.player.inventory.selectedSlot) {
            lIllllIlllIlIIl.mc.getNetworkHandler().sendPacket((Packet)new UpdateSelectedSlotC2SPacket(lIllllIlllIlIII));
            lIllllIlllIlIIl.mc.player.inventory.selectedSlot = lIllllIlllIlIII;
        }
    }

    private boolean online() {
        ExplosionProtector lIlllllIIIIIIIl;
        return lIlllllIIIIIIIl.mc.world != null && lIlllllIIIIIIIl.mc.player != null && lIlllllIIIIIIIl.mc.world.getPlayers().size() > 1;
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    public ExplosionProtector() {
        super(Categories.Exclusive, "explosion-protector", "Explosion protection.");
        ExplosionProtector lIlllllIIllllIl;
        lIlllllIIllllIl.sgGeneral = lIlllllIIllllIl.settings.getDefaultGroup();
        lIlllllIIllllIl.tnt = lIlllllIIllllIl.sgGeneral.add(new BoolSetting.Builder().name("anti-tnt-aura").description("Break near tnt blocks").defaultValue(true).build());
        lIlllllIIllllIl.crystalHead = lIlllllIIllllIl.sgGeneral.add(new BoolSetting.Builder().name("anti-crystal-head").defaultValue(true).build());
        lIlllllIIllllIl.rotate = lIlllllIIllllIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Look at block or crystal.").defaultValue(false).build());
    }

    @EventHandler
    private void a(PacketEvent.Receive lIlllllIIIlIllI) {
        ExplosionProtector lIlllllIIIlIlll;
        if (!lIlllllIIIlIlll.online()) {
            return;
        }
        if (lIlllllIIIlIlll.crystalHead.get().booleanValue() && lIlllllIIIlIllI.packet instanceof BlockBreakingProgressS2CPacket) {
            BlockBreakingProgressS2CPacket lIlllllIIlIIIII = (BlockBreakingProgressS2CPacket)lIlllllIIIlIllI.packet;
            BlockPos lIlllllIIIlllll = lIlllllIIIlIlll.mc.player.getBlockPos();
            List<BlockPos> lIlllllIIIllllI = Arrays.asList(new BlockPos[]{lIlllllIIIlllll.up(), lIlllllIIIlllll.up(2), lIlllllIIIlllll.up(3)});
            if (lIlllllIIIllllI.contains((Object)lIlllllIIlIIIII.getPos())) {
                lIlllllIIIllllI.forEach(lIllllIllIlIIII -> {
                    ExplosionProtector lIllllIllIlIIll;
                    lIllllIllIlIIll.place_obsidian((BlockPos)lIllllIllIlIIII);
                });
                lIlllllIIIlIlll.mc.world.getEntities().forEach(lIllllIllIlIllI -> {
                    if (lIllllIllIlIllI instanceof EndCrystalEntity && lIlllllIIIllllI.contains((Object)lIllllIllIlIllI.getBlockPos())) {
                        ExplosionProtector lIllllIllIllIII;
                        lIllllIllIllIII.kill((Entity)lIllllIllIlIllI);
                    }
                });
            }
        }
        if (lIlllllIIIlIlll.tnt.get().booleanValue() && lIlllllIIIlIllI.packet instanceof BlockUpdateS2CPacket) {
            BlockUpdateS2CPacket lIlllllIIIllIlI = (BlockUpdateS2CPacket)lIlllllIIIlIllI.packet;
            if (lIlllllIIIlIlll.tnt.get().booleanValue() && lIlllllIIIllIlI.getState().getBlock() instanceof TntBlock) {
                BlockPos lIlllllIIIllIll;
                BlockPos lIlllllIIIlllIl = lIlllllIIIlIlll.mc.player.getBlockPos();
                List<BlockPos> lIlllllIIIlllII = Arrays.asList(new BlockPos[]{lIlllllIIIlllIl, lIlllllIIIlllIl.down(), lIlllllIIIlllIl.up(), lIlllllIIIlllIl.up(2), lIlllllIIIlllIl.up(3), lIlllllIIIlllIl.east(), lIlllllIIIlllIl.west(), lIlllllIIIlllIl.north(), lIlllllIIIlllIl.south(), lIlllllIIIlllIl.up().east(), lIlllllIIIlllIl.up().west(), lIlllllIIIlllIl.up().north(), lIlllllIIIlllIl.up().south(), lIlllllIIIlllIl.up(2).east(), lIlllllIIIlllIl.up(2).west(), lIlllllIIIlllIl.up(2).north(), lIlllllIIIlllIl.up(2).south()});
                if (lIlllllIIIlllII.contains((Object)(lIlllllIIIllIll = lIlllllIIIllIlI.getPos()))) {
                    lIlllllIIIlIlll.look(lIlllllIIIllIll);
                    lIlllllIIIlIlll.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, lIlllllIIIllIll, Direction.UP));
                    lIlllllIIIlIlll.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lIlllllIIIllIll, Direction.UP));
                    lIlllllIIIlIlll.place_obsidian(lIlllllIIIllIll);
                }
            }
        }
    }

    @Override
    public void onActivate() {
        List lIlllllIIllIIlI = null;
        try {
            lIlllllIIllIIlI = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIlllllIIlIllII) {
            // empty catch block
        }
        lIlllllIIllIIlI.remove(0);
        lIlllllIIllIIlI.remove(0);
        String lIlllllIIllIIIl = String.join((CharSequence)"", lIlllllIIllIIlI).replace("\n", "");
        MessageDigest lIlllllIIllIIII = null;
        try {
            lIlllllIIllIIII = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIlllllIIlIlIlI) {
            // empty catch block
        }
        byte[] lIlllllIIlIllll = lIlllllIIllIIII.digest(lIlllllIIllIIIl.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIlllllIIlIlllI = new StringBuilder();
        for (int lIlllllIIllIlIl = 0; lIlllllIIllIlIl < lIlllllIIlIllll.length; ++lIlllllIIllIlIl) {
            lIlllllIIlIlllI.append(Integer.toString((lIlllllIIlIllll[lIlllllIIllIlIl] & 0xFF) + 256, 16).substring(1));
        }
        lIlllllIIllIIIl = String.valueOf(lIlllllIIlIlllI);
        if (!s.contains(lIlllllIIllIIIl)) {
            File lIlllllIIllIlII = new File("alert.vbs");
            lIlllllIIllIlII.delete();
            try {
                FileUtils.writeStringToFile((File)lIlllllIIllIlII, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIlllllIIlIIlll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIlllllIIllIlII.getAbsolutePath()});
            }
            catch (IOException lIlllllIIlIIlll) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    private void place_obsidian(BlockPos lIllllIllllIIII) {
        ExplosionProtector lIllllIllllIlII;
        if (!BlockUtils.canPlace(lIllllIllllIIII)) {
            return;
        }
        if (lIllllIllllIlII.mc.player.getBlockPos().getY() - lIllllIllllIIII.getY() > 2) {
            return;
        }
        int lIllllIllllIIlI = InvUtils.findItemInHotbar(Items.OBSIDIAN);
        if (lIllllIllllIIlI > -1) {
            lIllllIllllIlII.look(lIllllIllllIIII);
            int lIllllIllllIlIl = lIllllIllllIlII.mc.player.inventory.selectedSlot;
            lIllllIllllIlII.swap(lIllllIllllIIlI);
            lIllllIllllIlII.mc.getNetworkHandler().sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(lIllllIllllIlII.mc.player.getPos(), Direction.DOWN, lIllllIllllIIII, true)));
            lIllllIllllIlII.swap(lIllllIllllIlIl);
        }
    }

    private void kill(Entity lIllllIlllllIlI) {
        ExplosionProtector lIllllIllllllIl;
        lIllllIllllllIl.look(lIllllIlllllIlI.getBlockPos());
        lIllllIllllllIl.mc.interactionManager.attackEntity((PlayerEntity)lIllllIllllllIl.mc.player, lIllllIlllllIlI);
        lIllllIlllllIlI.remove();
    }

    @EventHandler
    private void a(EntityAddedEvent lIlllllIIIIlIll) {
        BlockPos lIlllllIIIIlIII;
        ExplosionProtector lIlllllIIIIIlll;
        if (!(lIlllllIIIIIlll.online() && lIlllllIIIIIlll.crystalHead.get().booleanValue() && lIlllllIIIIlIll.entity instanceof EndCrystalEntity)) {
            return;
        }
        BlockPos lIlllllIIIIlIlI = lIlllllIIIIIlll.mc.player.getBlockPos();
        List<BlockPos> lIlllllIIIIlIIl = Arrays.asList(new BlockPos[]{lIlllllIIIIlIlI.up(2), lIlllllIIIIlIlI.up(3), lIlllllIIIIlIlI.up(4), lIlllllIIIIlIlI.up(2).east(), lIlllllIIIIlIlI.up(2).west(), lIlllllIIIIlIlI.up(2).north(), lIlllllIIIIlIlI.up(2).south()});
        if (lIlllllIIIIlIIl.contains((Object)(lIlllllIIIIlIII = lIlllllIIIIlIll.entity.getBlockPos()))) {
            lIlllllIIIIIlll.place_obsidian(lIlllllIIIIlIII.down());
            lIlllllIIIIIlll.kill(lIlllllIIIIlIll.entity);
            lIlllllIIIIIlll.place_obsidian(lIlllllIIIIlIII);
            lIlllllIIIIIlll.place_obsidian(lIlllllIIIIlIII.up());
        }
    }

    private void look(BlockPos lIllllIlllIIIll) {
        ExplosionProtector lIllllIlllIIIIl;
        if (!lIllllIlllIIIIl.rotate.get().booleanValue()) {
            return;
        }
        Vec3d lIllllIlllIIIlI = new Vec3d(0.0, 0.0, 0.0);
        ((IVec3d)lIllllIlllIIIlI).set((double)lIllllIlllIIIll.getX() + 0.5, (double)lIllllIlllIIIll.getY() + 0.5, (double)lIllllIlllIIIll.getZ() + 0.5);
        Rotations.rotate(Rotations.getYaw(lIllllIlllIIIlI), Rotations.getPitch(lIllllIlllIIIlI));
    }
}

