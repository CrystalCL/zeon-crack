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
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    boolean firtDone;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> rotate;
    private final Setting<Integer> delay;
    private final Setting<Double> range;
    BlockPos pos;
    boolean isDone;
    int pause;

    private void s(String string) {
        LogManager.getLogger().info(string);
    }

    @EventHandler
    private void AntiClick(PacketEvent.Send send) {
        if (send.packet instanceof PlayerActionC2SPacket) {
            PlayerActionC2SPacket PlayerActionC2SPacket2 = (PlayerActionC2SPacket)send.packet;
            this.s(String.valueOf(new StringBuilder().append(PlayerActionC2SPacket2.getAction()).append(" ").append(PlayerActionC2SPacket2.getPos())));
            if (!(PlayerActionC2SPacket2.getAction() != Action.START_DESTROY_BLOCK && PlayerActionC2SPacket2.getAction() != Action.STOP_DESTROY_BLOCK && PlayerActionC2SPacket2.getAction() != Action.ABORT_DESTROY_BLOCK || Ezz.equalsBlockPos(PlayerActionC2SPacket2.getPos(), this.pos))) {
                this.s("cancel!");
                send.cancel();
            }
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
            if (-4 <= 0) continue;
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

    public AutoCrystalHead() {
        super(Categories.Exclusive, "auto-crystal-head", "Auto Crystal Head.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The amount of delay in ticks before placing.").defaultValue(4).min(0).sliderMax(20).build());
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The break range.").defaultValue(5.0).min(0.0).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces the blocks being mined.").defaultValue(false).build());
        this.pos = null;
        this.pause = 0;
        this.isDone = false;
        this.firtDone = false;
    }

    @EventHandler
    private void BlockUpdate(PacketEvent.Receive receive) {
        if (!(receive.packet instanceof BlockUpdateS2CPacket)) {
            return;
        }
        BlockUpdateS2CPacket BlockUpdateS2CPacket2 = (BlockUpdateS2CPacket)receive.packet;
        if (Ezz.equalsBlockPos(BlockUpdateS2CPacket2.getPos(), this.pos) && BlockUpdateS2CPacket2.getState().isAir()) {
            this.isDone = true;
        }
    }

    private boolean placeCrystal(PlayerEntity PlayerEntity2, BlockPos BlockPos2) {
        BlockPos BlockPos3 = new BlockPos(PlayerEntity2.getBlockPos().getX(), PlayerEntity2.getBlockPos().getY() + 3, PlayerEntity2.getBlockPos().getZ());
        if (!BlockUtils.canPlace(BlockPos3, true)) {
            return false;
        }
        if (!this.mc.world.getBlockState(BlockPos3).isAir()) {
            return false;
        }
        int n = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
        if (n == -1) {
            ChatUtils.moduleError(this, "There are no crystals in the quick access bar!", new Object[0]);
            this.toggle();
            return false;
        }
        Ezz.interact(BlockPos2, n, Direction.DOWN);
        return true;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.mc.world == null || this.mc.player == null) {
            return;
        }
        if (this.pause > 0) {
            --this.pause;
            return;
        }
        this.pause = this.delay.get();
        PlayerEntity PlayerEntity2 = CityUtils.getPlayerTarget(7.0);
        if (PlayerEntity2 == null) {
            return;
        }
        BlockPos BlockPos2 = new BlockPos(PlayerEntity2.getBlockPos().getX(), PlayerEntity2.getBlockPos().getY() + 2, PlayerEntity2.getBlockPos().getZ());
        if (Ezz.DistanceTo(BlockPos2) > this.range.get()) {
            return;
        }
        BlockPos BlockPos3 = new BlockPos(PlayerEntity2.getBlockPos().getX(), PlayerEntity2.getBlockPos().getY() + 1, PlayerEntity2.getBlockPos().getZ());
        if (this.mc.world.getBlockState(BlockPos3).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(BlockPos3).getBlock() == Blocks.BEDROCK) {
            return;
        }
        BlockPos BlockPos4 = new BlockPos(PlayerEntity2.getBlockPos().getX(), PlayerEntity2.getBlockPos().getY() + 3, PlayerEntity2.getBlockPos().getZ());
        if (!this.mc.world.getBlockState(BlockPos2).isAir() && this.mc.world.getBlockState(BlockPos2).getBlock() != Blocks.OBSIDIAN) {
            return;
        }
        int n = InvUtils.findItemInHotbar(Items.IRON_PICKAXE);
        if (n == -1) {
            n = InvUtils.findItemInHotbar(Items.NETHERITE_PICKAXE);
        }
        if (n == -1) {
            n = InvUtils.findItemInHotbar(Items.DIAMOND_PICKAXE);
        }
        if (n == -1) {
            ChatUtils.moduleError(this, "There is no pickaxe in the quick access bar!", new Object[0]);
            this.toggle();
            return;
        }
        if (this.mc.world.getBlockState(BlockPos2).getBlock() != Blocks.OBSIDIAN) {
            Ezz.BlockPlace(BlockPos2, InvUtils.findItemInHotbar(Items.OBSIDIAN), this.rotate.get());
        }
        if (!Ezz.equalsBlockPos(this.pos, BlockPos2)) {
            this.pos = BlockPos2;
            Ezz.swap(n);
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, this.pos, Direction.UP));
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, this.pos, Direction.UP));
            this.isDone = false;
            return;
        }
        if (!this.isDone) {
            return;
        }
        EndCrystalEntity EndCrystalEntity2 = null;
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2 instanceof EndCrystalEntity) || !Ezz.equalsBlockPos(Entity2.getBlockPos(), BlockPos4)) continue;
            EndCrystalEntity2 = (EndCrystalEntity)Entity2;
            break;
        }
        if (EndCrystalEntity2 != null) {
            if (this.rotate.get().booleanValue()) {
                Object object = PlayerUtils.calculateAngle(EndCrystalEntity2.getPos());
                Rotations.rotate((double)object[0], (double)object[1]);
            }
            int n2 = this.mc.player.inventory.selectedSlot;
            Ezz.swap(n);
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, this.pos, Direction.UP));
            Ezz.swap(n2);
            Ezz.attackEntity((Entity)EndCrystalEntity2);
            return;
        }
        this.placeCrystal(PlayerEntity2, BlockPos2);
    }
}

