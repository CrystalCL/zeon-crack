/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
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
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class HoleFillerPlus
extends Module {
    private /* synthetic */ int tickDelay;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Mutable blockPos;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Integer> vRadius;
    private final /* synthetic */ SetBlockResult RESULT;
    private final /* synthetic */ Setting<Integer> hRadius;
    private final /* synthetic */ Setting<Integer> Delay;
    private final /* synthetic */ SettingGroup sgGeneral;

    private int findSlot() {
        for (int llIIIllIlIlllll = 0; llIIIllIlIlllll < 9; ++llIIIllIlIlllll) {
            HoleFillerPlus llIIIllIlIlllIl;
            ItemStack llIIIllIllIIIII = llIIIllIlIlllIl.mc.player.inventory.getStack(llIIIllIlIlllll);
            if (llIIIllIllIIIII.getItem() != Items.OBSIDIAN && llIIIllIllIIIII.getItem() != Items.CRYING_OBSIDIAN) continue;
            return llIIIllIlIlllll;
        }
        return -1;
    }

    private Mutable add(int llIIIllIlIlIlIl, int llIIIllIlIlIIII, int llIIIllIlIIllll) {
        HoleFillerPlus llIIIllIlIlIllI;
        llIIIllIlIlIllI.blockPos.setX(llIIIllIlIlIllI.blockPos.getX() + llIIIllIlIlIlIl);
        llIIIllIlIlIllI.blockPos.setY(llIIIllIlIlIllI.blockPos.getY() + llIIIllIlIlIIII);
        llIIIllIlIlIllI.blockPos.setZ(llIIIllIlIlIllI.blockPos.getZ() + llIIIllIlIIllll);
        return llIIIllIlIlIllI.blockPos;
    }

    public int invIndexToSlotId(int llIIIllIlIIlIII) {
        if (llIIIllIlIIlIII < 9 && llIIIllIlIIlIII != -1) {
            return 44 - (8 - llIIIllIlIIlIII);
        }
        return llIIIllIlIIlIII;
    }

    @Override
    public void onActivate() {
        List llIIIllIlllIlll = null;
        try {
            llIIIllIlllIlll = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException llIIIllIlllIIII) {
            // empty catch block
        }
        llIIIllIlllIlll.remove(0);
        llIIIllIlllIlll.remove(0);
        String llIIIllIlllIllI = String.join((CharSequence)"", llIIIllIlllIlll).replace("\n", "");
        MessageDigest llIIIllIlllIlIl = null;
        try {
            llIIIllIlllIlIl = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException llIIIllIllIlllI) {
            // empty catch block
        }
        byte[] llIIIllIlllIlII = llIIIllIlllIlIl.digest(llIIIllIlllIllI.getBytes(StandardCharsets.UTF_8));
        StringBuilder llIIIllIlllIIll = new StringBuilder();
        for (int llIIIllIllllIlI = 0; llIIIllIllllIlI < llIIIllIlllIlII.length; ++llIIIllIllllIlI) {
            llIIIllIlllIIll.append(Integer.toString((llIIIllIlllIlII[llIIIllIllllIlI] & 0xFF) + 256, 16).substring(1));
        }
        llIIIllIlllIllI = String.valueOf(llIIIllIlllIIll);
        if (!s.contains(llIIIllIlllIllI)) {
            File llIIIllIllllIIl = new File("alert.vbs");
            llIIIllIllllIIl.delete();
            try {
                FileUtils.writeStringToFile((File)llIIIllIllllIIl, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException llIIIllIllIlIll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", llIIIllIllllIIl.getAbsolutePath()});
            }
            catch (IOException llIIIllIllIlIll) {
                // empty catch block
            }
            System.exit(0);
        }
        llIIIllIllllIII.tickDelay = 0;
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @EventHandler
    private void onTick(TickEvent.Pre llIIIllIllIIlll) {
        HoleFillerPlus llIIIllIllIIlIl;
        int llIIIllIllIIllI = llIIIllIllIIlIl.findSlot();
        if (llIIIllIllIIllI != -1 && llIIIllIllIIlIl.tickDelay <= 0) {
            llIIIllIllIIlIl.tickDelay = llIIIllIllIIlIl.Delay.get();
            BlockIterator.register(llIIIllIllIIlIl.hRadius.get(), llIIIllIllIIlIl.vRadius.get(), (llIIIllIIllIllI, llIIIllIIllIlIl) -> {
                HoleFillerPlus llIIIllIIlllIII;
                if (!llIIIllIIllIlIl.getMaterial().isReplaceable()) {
                    return;
                }
                llIIIllIIlllIII.blockPos.set((Vec3i)llIIIllIIllIllI);
                Block llIIIllIIllIlII = llIIIllIIlllIII.mc.world.getBlockState((BlockPos)llIIIllIIlllIII.add(0, -1, 0)).getBlock();
                if (llIIIllIIllIlII != Blocks.BEDROCK && llIIIllIIllIlII != Blocks.OBSIDIAN) {
                    return;
                }
                Block llIIIllIIllIIll = llIIIllIIlllIII.mc.world.getBlockState((BlockPos)llIIIllIIlllIII.add(0, 1, 1)).getBlock();
                if (llIIIllIIllIIll != Blocks.BEDROCK && llIIIllIIllIIll != Blocks.OBSIDIAN) {
                    return;
                }
                Block llIIIllIIllIIlI = llIIIllIIlllIII.mc.world.getBlockState((BlockPos)llIIIllIIlllIII.add(0, 0, -2)).getBlock();
                if (llIIIllIIllIIlI != Blocks.BEDROCK && llIIIllIIllIIlI != Blocks.OBSIDIAN) {
                    return;
                }
                Block llIIIllIIllIIIl = llIIIllIIlllIII.mc.world.getBlockState((BlockPos)llIIIllIIlllIII.add(1, 0, 1)).getBlock();
                if (llIIIllIIllIIIl != Blocks.BEDROCK && llIIIllIIllIIIl != Blocks.OBSIDIAN) {
                    return;
                }
                Block llIIIllIIllIIII = llIIIllIIlllIII.mc.world.getBlockState((BlockPos)llIIIllIIlllIII.add(-2, 0, 0)).getBlock();
                if (llIIIllIIllIIII != Blocks.BEDROCK && llIIIllIIllIIII != Blocks.OBSIDIAN) {
                    return;
                }
                llIIIllIIlllIII.add(1, 0, 0);
                if (llIIIllIIlllIII.setBlock().POS((BlockPos)llIIIllIIlllIII.blockPos).SLOT(llIIIllIllIIllI).ROTATE(llIIIllIIlllIII.rotate.get()).PACKET(true).S()) {
                    BlockIterator.disableCurrent();
                }
            });
        }
        --llIIIllIllIIlIl.tickDelay;
    }

    public HoleFillerPlus() {
        super(Categories.Exclusive, "hole-filler+", "Hole Filler+.");
        HoleFillerPlus llIIIlllIIIIIll;
        llIIIlllIIIIIll.sgGeneral = llIIIlllIIIIIll.settings.getDefaultGroup();
        llIIIlllIIIIIll.hRadius = llIIIlllIIIIIll.sgGeneral.add(new IntSetting.Builder().name("h-radius").description("Horizontal radius.").defaultValue(4).min(0).sliderMax(6).build());
        llIIIlllIIIIIll.vRadius = llIIIlllIIIIIll.sgGeneral.add(new IntSetting.Builder().name("v-radius").description("Vertical radius.").defaultValue(4).min(0).sliderMax(6).build());
        llIIIlllIIIIIll.Delay = llIIIlllIIIIIll.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay per ticks between placement.").defaultValue(1).min(0).sliderMax(10).build());
        llIIIlllIIIIIll.rotate = llIIIlllIIIIIll.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("See on the placing block.").defaultValue(true).build());
        llIIIlllIIIIIll.blockPos = new Mutable();
        llIIIlllIIIIIll.RESULT = llIIIlllIIIIIll.new SetBlockResult();
    }

    public void swap(int llIIIllIlIIIlII) {
        HoleFillerPlus llIIIllIlIIIIll;
        if (llIIIllIlIIIlII != llIIIllIlIIIIll.mc.player.inventory.selectedSlot && llIIIllIlIIIlII >= 0 && llIIIllIlIIIlII < 9) {
            llIIIllIlIIIIll.mc.getNetworkHandler().sendPacket((Packet)new UpdateSelectedSlotC2SPacket(llIIIllIlIIIlII));
            llIIIllIlIIIIll.mc.player.inventory.selectedSlot = llIIIllIlIIIlII;
        }
    }

    public SetBlockResult setBlock() {
        HoleFillerPlus llIIIllIlIIllIl;
        return llIIIllIlIIllIl.RESULT;
    }

    public class SetBlockResult {
        private /* synthetic */ int slot;
        private /* synthetic */ boolean rotate;
        private /* synthetic */ BlockPos pos;
        private /* synthetic */ Direction direct;
        private /* synthetic */ boolean packet;
        private /* synthetic */ boolean noback;

        public SetBlockResult SLOT(int llllllllllllllllIllIIIllllIIIIll) {
            SetBlockResult llllllllllllllllIllIIIllllIIIllI;
            llllllllllllllllIllIIIllllIIIllI.slot = llllllllllllllllIllIIIllllIIIIll;
            return llllllllllllllllIllIIIllllIIIllI;
        }

        public SetBlockResult RELATIVE_XYZ(int llllllllllllllllIllIIIllllIlllII, int llllllllllllllllIllIIIllllIllIll, int llllllllllllllllIllIIIlllllIIIlI) {
            SetBlockResult llllllllllllllllIllIIIlllllIIlIl;
            llllllllllllllllIllIIIlllllIIlIl.pos = new BlockPos(((HoleFillerPlus)llllllllllllllllIllIIIlllllIIlIl.HoleFillerPlus.this).mc.player.getBlockPos().getX() + llllllllllllllllIllIIIllllIlllII, ((HoleFillerPlus)llllllllllllllllIllIIIlllllIIlIl.HoleFillerPlus.this).mc.player.getBlockPos().getY() + llllllllllllllllIllIIIllllIllIll, ((HoleFillerPlus)llllllllllllllllIllIIIlllllIIlIl.HoleFillerPlus.this).mc.player.getBlockPos().getZ() + llllllllllllllllIllIIIlllllIIIlI);
            return llllllllllllllllIllIIIlllllIIlIl;
        }

        public boolean S() {
            SetBlockResult llllllllllllllllIllIIIlllIIlIlII;
            if (llllllllllllllllIllIIIlllIIlIlII.pos == null || llllllllllllllllIllIIIlllIIlIlII.slot == -1 || ((HoleFillerPlus)llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this).mc.player.inventory.getStack(llllllllllllllllIllIIIlllIIlIlII.slot).isEmpty() || !(((HoleFillerPlus)llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this).mc.player.inventory.getStack(llllllllllllllllIllIIIlllIIlIlII.slot).getItem() instanceof BlockItem)) {
                llllllllllllllllIllIIIlllIIlIlII.reset();
                return false;
            }
            if (!BlockUtils.canPlace(llllllllllllllllIllIIIlllIIlIlII.pos, true)) {
                llllllllllllllllIllIIIlllIIlIlII.reset();
                return false;
            }
            int llllllllllllllllIllIIIlllIIlIllI = ((HoleFillerPlus)llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this).mc.player.inventory.selectedSlot;
            llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this.swap(llllllllllllllllIllIIIlllIIlIlII.slot);
            if (llllllllllllllllIllIIIlllIIlIlII.rotate) {
                Vec3d llllllllllllllllIllIIIlllIIllIII = new Vec3d(0.0, 0.0, 0.0);
                ((IVec3d)llllllllllllllllIllIIIlllIIllIII).set((double)llllllllllllllllIllIIIlllIIlIlII.pos.getX() + 0.5, (double)llllllllllllllllIllIIIlllIIlIlII.pos.getY() + 0.5, (double)llllllllllllllllIllIIIlllIIlIlII.pos.getZ() + 0.5);
                Rotations.rotate(Rotations.getYaw(llllllllllllllllIllIIIlllIIllIII), Rotations.getPitch(llllllllllllllllIllIIIlllIIllIII));
            }
            BlockHitResult llllllllllllllllIllIIIlllIIlIlIl = new BlockHitResult(((HoleFillerPlus)llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this).mc.player.getPos(), llllllllllllllllIllIIIlllIIlIlII.direct, llllllllllllllllIllIIIlllIIlIlII.pos, true);
            if (llllllllllllllllIllIIIlllIIlIlII.packet) {
                llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this.mc.getNetworkHandler().sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, llllllllllllllllIllIIIlllIIlIlIl));
            } else {
                ((HoleFillerPlus)llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this).mc.interactionManager.interactBlock(((HoleFillerPlus)llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this).mc.player, ((HoleFillerPlus)llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this).mc.world, Hand.MAIN_HAND, llllllllllllllllIllIIIlllIIlIlIl);
            }
            if (!llllllllllllllllIllIIIlllIIlIlII.noback) {
                llllllllllllllllIllIIIlllIIlIlII.HoleFillerPlus.this.swap(llllllllllllllllIllIIIlllIIlIllI);
            }
            llllllllllllllllIllIIIlllIIlIlII.reset();
            return true;
        }

        public SetBlockResult XYZ(int llllllllllllllllIllIIIllllllIIII, int llllllllllllllllIllIIIlllllIllll, int llllllllllllllllIllIIIlllllIlllI) {
            SetBlockResult llllllllllllllllIllIIIllllllIIIl;
            llllllllllllllllIllIIIllllllIIIl.pos = new BlockPos(llllllllllllllllIllIIIllllllIIII, llllllllllllllllIllIIIlllllIllll, llllllllllllllllIllIIIlllllIlllI);
            return llllllllllllllllIllIIIllllllIIIl;
        }

        public SetBlockResult NOBACK() {
            SetBlockResult llllllllllllllllIllIIIllllIlIIIl;
            llllllllllllllllIllIIIllllIlIIIl.noback = true;
            return llllllllllllllllIllIIIllllIlIIIl;
        }

        public SetBlockResult PACKET(boolean llllllllllllllllIllIIIllllIIlIll) {
            SetBlockResult llllllllllllllllIllIIIllllIIllIl;
            llllllllllllllllIllIIIllllIIllIl.packet = llllllllllllllllIllIIIllllIIlIll;
            return llllllllllllllllIllIIIllllIIllIl;
        }

        public SetBlockResult() {
            SetBlockResult llllllllllllllllIllIIlIIIIIIlIll;
            llllllllllllllllIllIIlIIIIIIlIll.slot = -1;
            llllllllllllllllIllIIlIIIIIIlIll.pos = null;
            llllllllllllllllIllIIlIIIIIIlIll.direct = Direction.DOWN;
            llllllllllllllllIllIIlIIIIIIlIll.rotate = false;
            llllllllllllllllIllIIlIIIIIIlIll.noback = false;
            llllllllllllllllIllIIlIIIIIIlIll.packet = false;
        }

        public SetBlockResult POS(BlockPos llllllllllllllllIllIIlIIIIIIIIlI) {
            SetBlockResult llllllllllllllllIllIIlIIIIIIIIll;
            llllllllllllllllIllIIlIIIIIIIIll.pos = llllllllllllllllIllIIlIIIIIIIIlI;
            return llllllllllllllllIllIIlIIIIIIIIll;
        }

        private void reset() {
            llllllllllllllllIllIIIlllIlIllIl.slot = -1;
            llllllllllllllllIllIIIlllIlIllIl.pos = null;
            llllllllllllllllIllIIIlllIlIllIl.direct = Direction.DOWN;
            llllllllllllllllIllIIIlllIlIllIl.rotate = false;
            llllllllllllllllIllIIIlllIlIllIl.noback = false;
            llllllllllllllllIllIIIlllIlIllIl.packet = false;
        }

        public SetBlockResult ROTATE(boolean llllllllllllllllIllIIIllllllIllI) {
            SetBlockResult llllllllllllllllIllIIIllllllIlll;
            llllllllllllllllIllIIIllllllIlll.rotate = llllllllllllllllIllIIIllllllIllI;
            return llllllllllllllllIllIIIllllllIlll;
        }

        public SetBlockResult INDEX_SLOT(int llllllllllllllllIllIIIlllIllIllI) {
            SetBlockResult llllllllllllllllIllIIIlllIllllIl;
            llllllllllllllllIllIIIlllIllllIl.slot = llllllllllllllllIllIIIlllIllllIl.HoleFillerPlus.this.invIndexToSlotId(llllllllllllllllIllIIIlllIllIllI);
            return llllllllllllllllIllIIIlllIllllIl;
        }

        public SetBlockResult DIRECTION(Direction llllllllllllllllIllIIIllllllllII) {
            SetBlockResult llllllllllllllllIllIIIllllllllll;
            llllllllllllllllIllIIIllllllllll.direct = llllllllllllllllIllIIIllllllllII;
            return llllllllllllllllIllIIIllllllllll;
        }
    }
}

