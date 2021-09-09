/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
 *  net.minecraft.util.math.MathHelper
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
import minegame159.meteorclient.systems.modules.Exclusive.Ezz;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.CityUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.io.FileUtils;

public class InstaAutoCity
extends Module {
    private final /* synthetic */ Setting<Mode> b;
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ Setting<Boolean> backswap;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ Setting<Double> range;
    private final /* synthetic */ SettingGroup sgGeneral;
    /* synthetic */ BlockPos pos;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private /* synthetic */ int ticks;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<Boolean> chatInfo;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<Boolean> swap;

    public InstaAutoCity() {
        super(Categories.Exclusive, "insta-auto-city", "Break player's surround.");
        InstaAutoCity lllIlIllIllI;
        lllIlIllIllI.sgGeneral = lllIlIllIllI.settings.getDefaultGroup();
        lllIlIllIllI.delay = lllIlIllIllI.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay per ticks").defaultValue(3).min(0).sliderMax(27).build());
        lllIlIllIllI.b = lllIlIllIllI.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("The mode.").defaultValue(Mode.Normal).build());
        lllIlIllIllI.range = lllIlIllIllI.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The range.").defaultValue(5.0).min(0.0).sliderMax(20.0).build());
        lllIlIllIllI.swap = lllIlIllIllI.sgGeneral.add(new BoolSetting.Builder().name("swap").description("Switches to a pickaxe automatically.").defaultValue(true).build());
        lllIlIllIllI.backswap = lllIlIllIllI.sgGeneral.add(new BoolSetting.Builder().name("back-swap").description("Swap to a selected automatically.").defaultValue(true).build());
        lllIlIllIllI.chatInfo = lllIlIllIllI.sgGeneral.add(new BoolSetting.Builder().name("chat-info").description("Info.").defaultValue(false).build());
        lllIlIllIllI.rotate = lllIlIllIllI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("See on the city block.").defaultValue(true).build());
        lllIlIllIllI.sgRender = lllIlIllIllI.settings.createGroup("Render");
        lllIlIllIllI.render = lllIlIllIllI.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        lllIlIllIllI.shapeMode = lllIlIllIllI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllIlIllIllI.sideColor = lllIlIllIllI.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 10)).build());
        lllIlIllIllI.lineColor = lllIlIllIllI.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 255)).build());
        lllIlIllIllI.pos = null;
    }

    @Override
    public String getInfoString() {
        InstaAutoCity lllIlIIIlIlI;
        if (lllIlIIIlIlI.target != null) {
            return lllIlIIIlIlI.target.getEntityName();
        }
        return null;
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    @Override
    public void onActivate() {
        InstaAutoCity lllIlIlIIlII;
        List lllIlIlIlIlI = null;
        try {
            lllIlIlIlIlI = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllIlIlIIIlI) {
            // empty catch block
        }
        lllIlIlIlIlI.remove(0);
        lllIlIlIlIlI.remove(0);
        String lllIlIlIlIIl = String.join((CharSequence)"", lllIlIlIlIlI).replace("\n", "");
        MessageDigest lllIlIlIlIII = null;
        try {
            lllIlIlIlIII = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllIlIlIIIII) {
            // empty catch block
        }
        byte[] lllIlIlIIlll = lllIlIlIlIII.digest(lllIlIlIlIIl.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllIlIlIIllI = new StringBuilder();
        for (int lllIlIlIllIl = 0; lllIlIlIllIl < lllIlIlIIlll.length; ++lllIlIlIllIl) {
            lllIlIlIIllI.append(Integer.toString((lllIlIlIIlll[lllIlIlIllIl] & 0xFF) + 256, 16).substring(1));
        }
        lllIlIlIlIIl = String.valueOf(lllIlIlIIllI);
        if (!s.contains(lllIlIlIlIIl)) {
            File lllIlIlIllII = new File("alert.vbs");
            lllIlIlIllII.delete();
            try {
                FileUtils.writeStringToFile((File)lllIlIlIllII, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllIlIIlllIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllIlIlIllII.getAbsolutePath()});
            }
            catch (IOException lllIlIIlllIl) {
                // empty catch block
            }
            System.exit(0);
        }
        lllIlIlIIlII.target = CityUtils.getPlayerTarget(lllIlIlIIlII.range.get());
        BlockPos lllIlIlIIlIl = CityUtils.getTargetBlock(lllIlIlIIlII.target);
        if (lllIlIlIIlII.target == null || lllIlIlIIlIl == null) {
            if (lllIlIlIIlII.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(lllIlIlIIlII, "Target block not found... disabling.", new Object[0]);
            }
        } else {
            if (lllIlIlIIlII.chatInfo.get().booleanValue()) {
                ChatUtils.moduleInfo(lllIlIlIIlII, String.valueOf(new StringBuilder().append("Break city ").append(lllIlIlIIlII.target.getGameProfile().getName())), new Object[0]);
            }
            if (MathHelper.sqrt((double)lllIlIlIIlII.mc.player.squaredDistanceTo((double)lllIlIlIIlIl.getX(), (double)lllIlIlIIlIl.getY(), (double)lllIlIlIIlIl.getZ())) > lllIlIlIIlII.mc.interactionManager.getReachDistance()) {
                if (lllIlIlIIlII.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleError(lllIlIlIIlII, "Target block out of reach... disabling.", new Object[0]);
                }
                lllIlIlIIlII.toggle();
                return;
            }
            if (lllIlIlIIlII.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(lllIlIlIIlIl), Rotations.getPitch(lllIlIlIIlIl), () -> {
                    InstaAutoCity lllIlIIIIlIl;
                    lllIlIIIIlIl.mine(lllIlIlIIlIl);
                });
            } else {
                lllIlIlIIlII.mine(lllIlIlIIlIl);
            }
        }
        lllIlIlIIlII.toggle();
    }

    @EventHandler
    private void onRender(RenderEvent lllIlIIllIlI) {
        InstaAutoCity lllIlIIllIll;
        if (!lllIlIIllIll.render.get().booleanValue() || lllIlIIllIll.pos == null) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllIlIIllIll.pos, lllIlIIllIll.sideColor.get(), lllIlIIllIll.lineColor.get(), lllIlIIllIll.shapeMode.get(), 0);
    }

    private void mine(BlockPos lllIlIIIllll) {
        InstaAutoCity lllIlIIlIIII;
        int lllIlIIlIIlI = InvUtils.findItemInHotbar(Items.IRON_PICKAXE);
        if (lllIlIIlIIlI == -1) {
            lllIlIIlIIlI = InvUtils.findItemInHotbar(Items.NETHERITE_PICKAXE);
        }
        if (lllIlIIlIIlI == -1) {
            lllIlIIlIIlI = InvUtils.findItemInHotbar(Items.DIAMOND_PICKAXE);
        }
        if (lllIlIIlIIlI == -1) {
            ChatUtils.moduleError(lllIlIIlIIII, "There is no pickaxe in the quick access bar!", new Object[0]);
            lllIlIIlIIII.toggle();
            return;
        }
        int lllIlIIlIIIl = lllIlIIlIIII.mc.player.inventory.selectedSlot;
        if (lllIlIIlIIII.b.get() == Mode.Normal) {
            lllIlIIlIIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, lllIlIIIllll, Direction.UP));
            if (lllIlIIlIIII.swap.get().booleanValue()) {
                Ezz.swap(lllIlIIlIIlI);
            }
            lllIlIIlIIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lllIlIIIllll, Direction.UP));
            if (lllIlIIlIIII.backswap.get().booleanValue()) {
                Ezz.swap(lllIlIIlIIIl);
            }
        } else if (lllIlIIlIIII.b.get() == Mode.Fast) {
            lllIlIIlIIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, lllIlIIIllll, Direction.UP));
            lllIlIIlIIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lllIlIIIllll, Direction.UP));
            while (lllIlIIlIIII.b.get() == Mode.Fast && lllIlIIlIIII.target != null) {
                if (lllIlIIlIIII.swap.get().booleanValue()) {
                    Ezz.swap(lllIlIIlIIlI);
                }
                lllIlIIlIIII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, lllIlIIIllll, Direction.UP));
                if (!lllIlIIlIIII.backswap.get().booleanValue() || lllIlIIlIIII.target != null) continue;
                Ezz.swap(lllIlIIlIIIl);
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Normal;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Fast;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Normal, Fast};
        }

        private Mode() {
            Mode lIlllIIllIIII;
        }

        public static Mode valueOf(String lIlllIIllIlII) {
            return Enum.valueOf(Mode.class, lIlllIIllIlII);
        }

        static {
            Normal = new Mode();
            Fast = new Mode();
            $VALUES = Mode.$values();
        }
    }
}

