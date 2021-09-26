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
    private final Setting<Integer> delay;
    private final SettingGroup sgGeneral;
    private PlayerEntity target;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> chatInfo;
    BlockPos pos;
    private final Setting<Double> range;
    private final Setting<Boolean> backswap;
    private final Setting<SettingColor> lineColor;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Mode> b;
    private final Setting<Boolean> render;
    private int ticks;
    private final Setting<Boolean> swap;
    private final SettingGroup sgRender;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<SettingColor> sideColor;

    private void lambda$onActivate$0(BlockPos BlockPos2) {
        this.mine(BlockPos2);
    }

    private void mine(BlockPos BlockPos2) {
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
        int n2 = this.mc.player.inventory.selectedSlot;
        if (this.b.get() == Mode.Normal) {
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, BlockPos2, Direction.UP));
            if (this.swap.get().booleanValue()) {
                Ezz.swap(n);
            }
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, BlockPos2, Direction.UP));
            if (this.backswap.get().booleanValue()) {
                Ezz.swap(n2);
            }
        } else if (this.b.get() == Mode.Fast) {
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, BlockPos2, Direction.UP));
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, BlockPos2, Direction.UP));
            while (this.b.get() == Mode.Fast && this.target != null) {
                if (this.swap.get().booleanValue()) {
                    Ezz.swap(n);
                }
                this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, BlockPos2, Direction.UP));
                if (!this.backswap.get().booleanValue() || this.target != null) continue;
                Ezz.swap(n2);
            }
        }
    }

    public InstaAutoCity() {
        super(Categories.Exclusive, "insta-auto-city", "Break player's surround.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay per ticks").defaultValue(3).min(0).sliderMax(27).build());
        this.b = this.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("The mode.").defaultValue(Mode.Normal).build());
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The range.").defaultValue(5.0).min(0.0).sliderMax(20.0).build());
        this.swap = this.sgGeneral.add(new BoolSetting.Builder().name("swap").description("Switches to a pickaxe automatically.").defaultValue(true).build());
        this.backswap = this.sgGeneral.add(new BoolSetting.Builder().name("back-swap").description("Swap to a selected automatically.").defaultValue(true).build());
        this.chatInfo = this.sgGeneral.add(new BoolSetting.Builder().name("chat-info").description("Info.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("See on the city block.").defaultValue(true).build());
        this.sgRender = this.settings.createGroup("Render");
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 255)).build());
        this.pos = null;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!this.render.get().booleanValue() || this.pos == null) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.pos, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    @Override
    public String getInfoString() {
        if (this.target != null) {
            return this.target.getEntityName();
        }
        return null;
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
            if (-1 <= 1) continue;
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
        this.target = CityUtils.getPlayerTarget(this.range.get());
        BlockPos BlockPos2 = CityUtils.getTargetBlock(this.target);
        if (this.target == null || BlockPos2 == null) {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(this, "Target block not found... disabling.", new Object[0]);
            }
        } else {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleInfo(this, String.valueOf(new StringBuilder().append("Break city ").append(this.target.getGameProfile().getName())), new Object[0]);
            }
            if (MathHelper.sqrt((double)this.mc.player.squaredDistanceTo((double)BlockPos2.getX(), (double)BlockPos2.getY(), (double)BlockPos2.getZ())) > this.mc.interactionManager.getReachDistance()) {
                if (this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleError(this, "Target block out of reach... disabling.", new Object[0]);
                }
                this.toggle();
                return;
            }
            if (this.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(BlockPos2), Rotations.getPitch(BlockPos2), () -> this.lambda$onActivate$0(BlockPos2));
            } else {
                this.mine(BlockPos2);
            }
        }
        this.toggle();
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Fast;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Normal;

        private static Mode[] $values() {
            return new Mode[]{Normal, Fast};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            Normal = new Mode();
            Fast = new Mode();
            $VALUES = Mode.$values();
        }
    }
}

