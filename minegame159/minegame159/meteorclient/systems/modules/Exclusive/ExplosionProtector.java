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
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> tnt;
    private final Setting<Boolean> crystalHead;
    private final SettingGroup sgGeneral;

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
            if (2 >= 0) continue;
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
    }

    @EventHandler
    private void a(PacketEvent.Receive receive) {
        List<BlockPos> list;
        BlockPos BlockPos2;
        BlockBreakingProgressS2CPacket BlockBreakingProgressS2CPacket2;
        if (!this.online()) {
            return;
        }
        if (this.crystalHead.get().booleanValue() && receive.packet instanceof BlockBreakingProgressS2CPacket) {
            BlockBreakingProgressS2CPacket2 = (BlockBreakingProgressS2CPacket)receive.packet;
            BlockPos2 = this.mc.player.getBlockPos();
            list = Arrays.asList(BlockPos2.up(), BlockPos2.up(2), BlockPos2.up(3));
            if (list.contains(BlockBreakingProgressS2CPacket2.getPos())) {
                list.forEach(this::lambda$a$0);
                this.mc.world.getEntities().forEach(arg_0 -> this.lambda$a$1(list, arg_0));
            }
        }
        if (this.tnt.get().booleanValue() && receive.packet instanceof BlockUpdateS2CPacket) {
            BlockPos BlockPos3;
            BlockBreakingProgressS2CPacket2 = (BlockUpdateS2CPacket)receive.packet;
            if (this.tnt.get().booleanValue() && BlockBreakingProgressS2CPacket2.getState().getBlock() instanceof TntBlock && (list = Arrays.asList(BlockPos2 = this.mc.player.getBlockPos(), BlockPos2.down(), BlockPos2.up(), BlockPos2.up(2), BlockPos2.up(3), BlockPos2.east(), BlockPos2.west(), BlockPos2.north(), BlockPos2.south(), BlockPos2.up().east(), BlockPos2.up().west(), BlockPos2.up().north(), BlockPos2.up().south(), BlockPos2.up(2).east(), BlockPos2.up(2).west(), BlockPos2.up(2).north(), BlockPos2.up(2).south())).contains(BlockPos3 = BlockBreakingProgressS2CPacket2.getPos())) {
                this.look(BlockPos3);
                this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, BlockPos3, Direction.UP));
                this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, BlockPos3, Direction.UP));
                this.place_obsidian(BlockPos3);
            }
        }
    }

    private boolean online() {
        return this.mc.world != null && this.mc.player != null && this.mc.world.getPlayers().size() > 1;
    }

    private void lambda$a$0(BlockPos BlockPos2) {
        this.place_obsidian(BlockPos2);
    }

    private void kill(Entity Entity2) {
        this.look(Entity2.getBlockPos());
        this.mc.interactionManager.attackEntity((PlayerEntity)this.mc.player, Entity2);
        Entity2.remove();
    }

    private void place_obsidian(BlockPos BlockPos2) {
        if (!BlockUtils.canPlace(BlockPos2)) {
            return;
        }
        if (this.mc.player.getBlockPos().getY() - BlockPos2.getY() > 2) {
            return;
        }
        int n = InvUtils.findItemInHotbar(Items.OBSIDIAN);
        if (n > -1) {
            this.look(BlockPos2);
            int n2 = this.mc.player.inventory.selectedSlot;
            this.swap(n);
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(this.mc.player.getPos(), Direction.DOWN, BlockPos2, true)));
            this.swap(n2);
        }
    }

    private void lambda$a$1(List list, Entity Entity2) {
        if (Entity2 instanceof EndCrystalEntity && list.contains(Entity2.getBlockPos())) {
            this.kill(Entity2);
        }
    }

    private void swap(int n) {
        if (n != this.mc.player.inventory.selectedSlot) {
            this.mc.getNetworkHandler().sendPacket((Packet)new UpdateSelectedSlotC2SPacket(n));
            this.mc.player.inventory.selectedSlot = n;
        }
    }

    @EventHandler
    private void a(EntityAddedEvent entityAddedEvent) {
        BlockPos BlockPos2;
        if (!(this.online() && this.crystalHead.get().booleanValue() && entityAddedEvent.entity instanceof EndCrystalEntity)) {
            return;
        }
        BlockPos BlockPos3 = this.mc.player.getBlockPos();
        List<BlockPos> list = Arrays.asList(BlockPos3.up(2), BlockPos3.up(3), BlockPos3.up(4), BlockPos3.up(2).east(), BlockPos3.up(2).west(), BlockPos3.up(2).north(), BlockPos3.up(2).south());
        if (list.contains(BlockPos2 = entityAddedEvent.entity.getBlockPos())) {
            this.place_obsidian(BlockPos2.down());
            this.kill(entityAddedEvent.entity);
            this.place_obsidian(BlockPos2);
            this.place_obsidian(BlockPos2.up());
        }
    }

    public ExplosionProtector() {
        super(Categories.Exclusive, "explosion-protector", "Explosion protection.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.tnt = this.sgGeneral.add(new BoolSetting.Builder().name("anti-tnt-aura").description("Break near tnt blocks").defaultValue(true).build());
        this.crystalHead = this.sgGeneral.add(new BoolSetting.Builder().name("anti-crystal-head").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Look at block or crystal.").defaultValue(false).build());
    }

    private void look(BlockPos BlockPos2) {
        if (!this.rotate.get().booleanValue()) {
            return;
        }
        Vec3d Vec3d2 = new Vec3d(0.0, 0.0, 0.0);
        ((IVec3d)Vec3d2).set((double)BlockPos2.getX() + 0.5, (double)BlockPos2.getY() + 0.5, (double)BlockPos2.getZ() + 0.5);
        Rotations.rotate(Rotations.getYaw(Vec3d2), Rotations.getPitch(Vec3d2));
    }
}

