/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.Items
 *  net.minecraft.item.PotionItem
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
 *  net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
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
import minegame159.meteorclient.events.entity.player.StartBreakingBlockEvent;
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
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class FastKill
extends Module {
    private /* synthetic */ int ticks;
    private final /* synthetic */ Setting<Boolean> pauseOnMine;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Boolean> pauseOnEat;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<Boolean> pauseOnDrink;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Mode> FastKillMode;
    private final /* synthetic */ Setting<Integer> tickDelay;
    private final /* synthetic */ Mutable blockPos;
    private final /* synthetic */ Setting<Boolean> crystal;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ SettingGroup sgPause;
    private /* synthetic */ Direction direction;
    private final /* synthetic */ Setting<SettingColor> sideColor;

    @EventHandler
    private void onTick(TickEvent.Pre lllIlllIlIIIIlI) {
        FastKill lllIlllIlIIIIll;
        if (lllIlllIlIIIIll.mc.player.isUsingItem() && (lllIlllIlIIIIll.mc.player.getMainHandStack().getItem().isFood() || lllIlllIlIIIIll.mc.player.getOffHandStack().getItem().isFood()) && lllIlllIlIIIIll.pauseOnEat.get().booleanValue() || lllIlllIlIIIIll.mc.interactionManager.isBreakingBlock() && lllIlllIlIIIIll.pauseOnMine.get().booleanValue() || lllIlllIlIIIIll.mc.player.isUsingItem() && (lllIlllIlIIIIll.mc.player.getMainHandStack().getItem() instanceof PotionItem || lllIlllIlIIIIll.mc.player.getOffHandStack().getItem() instanceof PotionItem) && lllIlllIlIIIIll.pauseOnDrink.get().booleanValue()) {
            return;
        }
        if (lllIlllIlIIIIll.ticks >= lllIlllIlIIIIll.tickDelay.get()) {
            lllIlllIlIIIIll.ticks = 0;
            if (!lllIlllIlIIIIll.mc.world.getBlockState((BlockPos)lllIlllIlIIIIll.blockPos).isAir()) {
                if (lllIlllIlIIIIll.crystal.get().booleanValue() && lllIlllIlIIIIll.mc.world.getBlockState((BlockPos)lllIlllIlIIIIll.blockPos).getBlock().is(Blocks.OBSIDIAN)) {
                    Hand lllIlllIlIIIllI = InvUtils.getHand(Items.END_CRYSTAL);
                    int lllIlllIlIIIlIl = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
                    int lllIlllIlIIIlII = lllIlllIlIIIIll.mc.player.inventory.selectedSlot;
                    if (lllIlllIlIIIllI != Hand.OFF_HAND && lllIlllIlIIIlIl != -1) {
                        lllIlllIlIIIIll.mc.player.inventory.selectedSlot = lllIlllIlIIIlIl;
                    }
                    lllIlllIlIIIIll.mc.interactionManager.interactBlock(lllIlllIlIIIIll.mc.player, lllIlllIlIIIIll.mc.world, lllIlllIlIIIllI, new BlockHitResult(lllIlllIlIIIIll.mc.player.getPos(), lllIlllIlIIIIll.direction, (BlockPos)lllIlllIlIIIIll.blockPos, false));
                    if (lllIlllIlIIIllI != Hand.OFF_HAND) {
                        lllIlllIlIIIIll.mc.player.inventory.selectedSlot = lllIlllIlIIIlII;
                    }
                }
                if (lllIlllIlIIIIll.rotate.get().booleanValue()) {
                    Rotations.rotate(Rotations.getYaw((BlockPos)lllIlllIlIIIIll.blockPos), Rotations.getPitch((BlockPos)lllIlllIlIIIIll.blockPos), () -> {
                        FastKill lllIlllIIlllIII;
                        lllIlllIIlllIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, (BlockPos)lllIlllIIlllIII.blockPos, lllIlllIIlllIII.direction));
                    });
                } else {
                    lllIlllIlIIIIll.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, (BlockPos)lllIlllIlIIIIll.blockPos, lllIlllIlIIIIll.direction));
                }
                lllIlllIlIIIIll.mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
        } else {
            ++lllIlllIlIIIIll.ticks;
        }
    }

    @EventHandler
    private void onRender(RenderEvent lllIlllIIlllIll) {
        FastKill lllIlllIIlllIlI;
        if (!lllIlllIIlllIlI.render.get().booleanValue() || lllIlllIIlllIlI.blockPos.getY() == -1) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (BlockPos)lllIlllIIlllIlI.blockPos, lllIlllIIlllIlI.sideColor.get(), lllIlllIIlllIlI.lineColor.get(), lllIlllIIlllIlI.shapeMode.get(), 0);
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    public FastKill() {
        super(Categories.Exclusive, "fast-kill", "Fast kill for crystal.");
        FastKill lllIlllIllIlIlI;
        lllIlllIllIlIlI.sgGeneral = lllIlllIllIlIlI.settings.getDefaultGroup();
        lllIlllIllIlIlI.sgRender = lllIlllIllIlIlI.settings.createGroup("Render");
        lllIlllIllIlIlI.sgPause = lllIlllIllIlIlI.settings.createGroup("Pause");
        lllIlllIllIlIlI.tickDelay = lllIlllIllIlIlI.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay between place and kill.").defaultValue(3).min(0).sliderMax(10).build());
        lllIlllIllIlIlI.FastKillMode = lllIlllIllIlIlI.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode of FastKill.").defaultValue(Mode.AntiSurround).build());
        lllIlllIllIlIlI.crystal = lllIlllIllIlIlI.sgGeneral.add(new BoolSetting.Builder().name("kill-mode").description("Fast Kill players.").defaultValue(true).build());
        lllIlllIllIlIlI.pauseOnEat = lllIlllIllIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses Crystal Aura while eating.").defaultValue(true).build());
        lllIlllIllIlIlI.pauseOnDrink = lllIlllIllIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses Crystal Aura while drinking a potion.").defaultValue(false).build());
        lllIlllIllIlIlI.pauseOnMine = lllIlllIllIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses Crystal Aura while mining blocks.").defaultValue(false).build());
        lllIlllIllIlIlI.rotate = lllIlllIllIlIlI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Faces the blocks.").defaultValue(true).build());
        lllIlllIllIlIlI.render = lllIlllIllIlIlI.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        lllIlllIllIlIlI.shapeMode = lllIlllIllIlIlI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllIlllIllIlIlI.sideColor = lllIlllIllIlIlI.sgRender.add(new ColorSetting.Builder().name("color-1").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        lllIlllIllIlIlI.lineColor = lllIlllIllIlIlI.sgRender.add(new ColorSetting.Builder().name("color-2").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        lllIlllIllIlIlI.blockPos = new Mutable(0, -1, 0);
    }

    @Override
    public void onActivate() {
        FastKill lllIlllIlIllllI;
        List lllIlllIlIlllIl = null;
        try {
            lllIlllIlIlllIl = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllIlllIlIlIllI) {
            // empty catch block
        }
        lllIlllIlIlllIl.remove(0);
        lllIlllIlIlllIl.remove(0);
        String lllIlllIlIlllII = String.join((CharSequence)"", lllIlllIlIlllIl).replace("\n", "");
        MessageDigest lllIlllIlIllIll = null;
        try {
            lllIlllIlIllIll = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllIlllIlIlIlII) {
            // empty catch block
        }
        byte[] lllIlllIlIllIlI = lllIlllIlIllIll.digest(lllIlllIlIlllII.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllIlllIlIllIIl = new StringBuilder();
        for (int lllIlllIllIIIII = 0; lllIlllIllIIIII < lllIlllIlIllIlI.length; ++lllIlllIllIIIII) {
            lllIlllIlIllIIl.append(Integer.toString((lllIlllIlIllIlI[lllIlllIllIIIII] & 0xFF) + 256, 16).substring(1));
        }
        lllIlllIlIlllII = String.valueOf(lllIlllIlIllIIl);
        if (!s.contains(lllIlllIlIlllII)) {
            File lllIlllIlIlllll = new File("alert.vbs");
            lllIlllIlIlllll.delete();
            try {
                FileUtils.writeStringToFile((File)lllIlllIlIlllll, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllIlllIlIlIIIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllIlllIlIlllll.getAbsolutePath()});
            }
            catch (IOException lllIlllIlIlIIIl) {
                // empty catch block
            }
            System.exit(0);
        }
        lllIlllIlIllllI.ticks = 0;
        lllIlllIlIllllI.blockPos.set(0, -1, 0);
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent lllIlllIlIIllIl) {
        FastKill lllIlllIlIIllII;
        lllIlllIlIIllII.direction = lllIlllIlIIllIl.direction;
        lllIlllIlIIllII.blockPos.set((Vec3i)lllIlllIlIIllIl.blockPos);
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Fast;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Legit;
        public static final /* synthetic */ /* enum */ Mode AntiSurround;
        public static final /* synthetic */ /* enum */ Mode None;

        public static Mode valueOf(String lIIIllIllIIIlll) {
            return Enum.valueOf(Mode.class, lIIIllIllIIIlll);
        }

        private Mode() {
            Mode lIIIllIllIIIIll;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            AntiSurround = new Mode();
            Fast = new Mode();
            Legit = new Mode();
            None = new Mode();
            $VALUES = Mode.$values();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{AntiSurround, Fast, Legit, None};
        }
    }
}

