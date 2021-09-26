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
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockIterator;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class HoleFillerPlus
extends Module {
    private final Setting<Integer> vRadius;
    private final Mutable blockPos;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Integer> hRadius;
    private int tickDelay;
    private final SetBlockResult RESULT;
    private final Setting<Integer> Delay;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> rotate;

    private Mutable add(int n, int n2, int n3) {
        this.blockPos.setX(this.blockPos.getX() + n);
        this.blockPos.setY(this.blockPos.getY() + n2);
        this.blockPos.setZ(this.blockPos.getZ() + n3);
        return this.blockPos;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = this.findSlot();
        if (n != -1 && this.tickDelay <= 0) {
            this.tickDelay = this.Delay.get();
            BlockIterator.register(this.hRadius.get(), this.vRadius.get(), (arg_0, arg_1) -> this.lambda$onTick$0(n, arg_0, arg_1));
        }
        --this.tickDelay;
    }

    static MinecraftClient access$200(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    static MinecraftClient access$300(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    private void lambda$onTick$0(int n, BlockPos BlockPos2, BlockState BlockState2) {
        if (!BlockState2.getMaterial().isReplaceable()) {
            return;
        }
        this.blockPos.set((Vec3i)BlockPos2);
        Block Block2 = this.mc.world.getBlockState((BlockPos)this.add(0, -1, 0)).getBlock();
        if (Block2 != Blocks.BEDROCK && Block2 != Blocks.OBSIDIAN) {
            return;
        }
        Block Block3 = this.mc.world.getBlockState((BlockPos)this.add(0, 1, 1)).getBlock();
        if (Block3 != Blocks.BEDROCK && Block3 != Blocks.OBSIDIAN) {
            return;
        }
        Block Block4 = this.mc.world.getBlockState((BlockPos)this.add(0, 0, -2)).getBlock();
        if (Block4 != Blocks.BEDROCK && Block4 != Blocks.OBSIDIAN) {
            return;
        }
        Block Block5 = this.mc.world.getBlockState((BlockPos)this.add(1, 0, 1)).getBlock();
        if (Block5 != Blocks.BEDROCK && Block5 != Blocks.OBSIDIAN) {
            return;
        }
        Block Block6 = this.mc.world.getBlockState((BlockPos)this.add(-2, 0, 0)).getBlock();
        if (Block6 != Blocks.BEDROCK && Block6 != Blocks.OBSIDIAN) {
            return;
        }
        this.add(1, 0, 0);
        if (this.setBlock().POS((BlockPos)this.blockPos).SLOT(n).ROTATE(this.rotate.get()).PACKET(true).S()) {
            BlockIterator.disableCurrent();
        }
    }

    static MinecraftClient access$000(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    public void swap(int n) {
        if (n != this.mc.player.inventory.selectedSlot && n >= 0 && n < 9) {
            this.mc.getNetworkHandler().sendPacket((Packet)new UpdateSelectedSlotC2SPacket(n));
            this.mc.player.inventory.selectedSlot = n;
        }
    }

    static MinecraftClient access$400(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    private int findSlot() {
        for (int i = 0; i < 9; ++i) {
            ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
            if (ItemStack2.getItem() != Items.OBSIDIAN && ItemStack2.getItem() != Items.CRYING_OBSIDIAN) continue;
            return i;
        }
        return -1;
    }

    static MinecraftClient access$900(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    public int invIndexToSlotId(int n) {
        if (n < 9 && n != -1) {
            return 44 - (8 - n);
        }
        return n;
    }

    static MinecraftClient access$800(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    public HoleFillerPlus() {
        super(Categories.Exclusive, "hole-filler+", "Hole Filler+.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.hRadius = this.sgGeneral.add(new IntSetting.Builder().name("h-radius").description("Horizontal radius.").defaultValue(4).min(0).sliderMax(6).build());
        this.vRadius = this.sgGeneral.add(new IntSetting.Builder().name("v-radius").description("Vertical radius.").defaultValue(4).min(0).sliderMax(6).build());
        this.Delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay per ticks between placement.").defaultValue(1).min(0).sliderMax(10).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("See on the placing block.").defaultValue(true).build());
        this.blockPos = new Mutable();
        this.RESULT = new SetBlockResult(this);
    }

    static MinecraftClient access$600(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    static MinecraftClient access$100(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    static MinecraftClient access$700(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    static MinecraftClient access$1000(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    public SetBlockResult setBlock() {
        return this.RESULT;
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
        this.tickDelay = 0;
    }

    static MinecraftClient access$500(HoleFillerPlus holeFillerPlus) {
        return holeFillerPlus.mc;
    }

    public class SetBlockResult {
        private int slot;
        private boolean rotate;
        private boolean noback;
        private boolean packet;
        final HoleFillerPlus this$0;
        private BlockPos pos;
        private Direction direct;

        public SetBlockResult NOBACK() {
            this.noback = true;
            return this;
        }

        public SetBlockResult INDEX_SLOT(int n) {
            this.slot = this.this$0.invIndexToSlotId(n);
            return this;
        }

        public SetBlockResult(HoleFillerPlus holeFillerPlus) {
            this.this$0 = holeFillerPlus;
            this.slot = -1;
            this.pos = null;
            this.direct = Direction.DOWN;
            this.rotate = false;
            this.noback = false;
            this.packet = false;
        }

        public SetBlockResult RELATIVE_XYZ(int n, int n2, int n3) {
            this.pos = new BlockPos(HoleFillerPlus.access$000((HoleFillerPlus)this.this$0).player.getBlockPos().getX() + n, HoleFillerPlus.access$100((HoleFillerPlus)this.this$0).player.getBlockPos().getY() + n2, HoleFillerPlus.access$200((HoleFillerPlus)this.this$0).player.getBlockPos().getZ() + n3);
            return this;
        }

        public SetBlockResult PACKET(boolean bl) {
            this.packet = bl;
            return this;
        }

        private void reset() {
            this.slot = -1;
            this.pos = null;
            this.direct = Direction.DOWN;
            this.rotate = false;
            this.noback = false;
            this.packet = false;
        }

        public SetBlockResult XYZ(int n, int n2, int n3) {
            this.pos = new BlockPos(n, n2, n3);
            return this;
        }

        public SetBlockResult DIRECTION(Direction Direction2) {
            this.direct = Direction2;
            return this;
        }

        public SetBlockResult ROTATE(boolean bl) {
            this.rotate = bl;
            return this;
        }

        public SetBlockResult SLOT(int n) {
            this.slot = n;
            return this;
        }

        public SetBlockResult POS(BlockPos BlockPos2) {
            this.pos = BlockPos2;
            return this;
        }

        public boolean S() {
            Vec3d Vec3d2;
            if (this.pos == null || this.slot == -1 || HoleFillerPlus.access$300((HoleFillerPlus)this.this$0).player.inventory.getStack(this.slot).isEmpty() || !(HoleFillerPlus.access$400((HoleFillerPlus)this.this$0).player.inventory.getStack(this.slot).getItem() instanceof BlockItem)) {
                this.reset();
                return false;
            }
            if (!BlockUtils.canPlace(this.pos, true)) {
                this.reset();
                return false;
            }
            int n = HoleFillerPlus.access$500((HoleFillerPlus)this.this$0).player.inventory.selectedSlot;
            this.this$0.swap(this.slot);
            if (this.rotate) {
                Vec3d2 = new Vec3d(0.0, 0.0, 0.0);
                ((IVec3d)Vec3d2).set((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5);
                Rotations.rotate(Rotations.getYaw(Vec3d2), Rotations.getPitch(Vec3d2));
            }
            Vec3d2 = new BlockHitResult(HoleFillerPlus.access$600((HoleFillerPlus)this.this$0).player.getPos(), this.direct, this.pos, true);
            if (this.packet) {
                HoleFillerPlus.access$700(this.this$0).getNetworkHandler().sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, (BlockHitResult)Vec3d2));
            } else {
                HoleFillerPlus.access$1000((HoleFillerPlus)this.this$0).interactionManager.interactBlock(HoleFillerPlus.access$800((HoleFillerPlus)this.this$0).player, HoleFillerPlus.access$900((HoleFillerPlus)this.this$0).world, Hand.MAIN_HAND, (BlockHitResult)Vec3d2);
            }
            if (!this.noback) {
                this.this$0.swap(n);
            }
            this.reset();
            return true;
        }
    }
}

