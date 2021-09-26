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
    private final Setting<Boolean> crystal;
    private int ticks;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final SettingGroup sgPause;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> lineColor;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> pauseOnMine;
    private final SettingGroup sgRender;
    private final Setting<SettingColor> sideColor;
    private final Setting<Mode> FastKillMode;
    private final Mutable blockPos;
    private final Setting<Boolean> pauseOnEat;
    private final Setting<Boolean> pauseOnDrink;
    private Direction direction;
    private final Setting<Boolean> render;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Integer> tickDelay;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.mc.player.isUsingItem() && (this.mc.player.getMainHandStack().getItem().isFood() || this.mc.player.getOffHandStack().getItem().isFood()) && this.pauseOnEat.get().booleanValue() || this.mc.interactionManager.isBreakingBlock() && this.pauseOnMine.get().booleanValue() || this.mc.player.isUsingItem() && (this.mc.player.getMainHandStack().getItem() instanceof PotionItem || this.mc.player.getOffHandStack().getItem() instanceof PotionItem) && this.pauseOnDrink.get().booleanValue()) {
            return;
        }
        if (this.ticks >= this.tickDelay.get()) {
            this.ticks = 0;
            if (!this.mc.world.getBlockState((BlockPos)this.blockPos).isAir()) {
                if (this.crystal.get().booleanValue() && this.mc.world.getBlockState((BlockPos)this.blockPos).getBlock().is(Blocks.OBSIDIAN)) {
                    Hand Hand2 = InvUtils.getHand(Items.END_CRYSTAL);
                    int n = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
                    int n2 = this.mc.player.inventory.selectedSlot;
                    if (Hand2 != Hand.OFF_HAND && n != -1) {
                        this.mc.player.inventory.selectedSlot = n;
                    }
                    this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand2, new BlockHitResult(this.mc.player.getPos(), this.direction, (BlockPos)this.blockPos, false));
                    if (Hand2 != Hand.OFF_HAND) {
                        this.mc.player.inventory.selectedSlot = n2;
                    }
                }
                if (this.rotate.get().booleanValue()) {
                    Rotations.rotate(Rotations.getYaw((BlockPos)this.blockPos), Rotations.getPitch((BlockPos)this.blockPos), this::lambda$onTick$0);
                } else {
                    this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, (BlockPos)this.blockPos, this.direction));
                }
                this.mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
        } else {
            ++this.ticks;
        }
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!this.render.get().booleanValue() || this.blockPos.getY() == -1) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (BlockPos)this.blockPos, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    private void lambda$onTick$0() {
        this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, (BlockPos)this.blockPos, this.direction));
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent startBreakingBlockEvent) {
        this.direction = startBreakingBlockEvent.direction;
        this.blockPos.set((Vec3i)startBreakingBlockEvent.blockPos);
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
            if (3 > 0) continue;
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
        this.ticks = 0;
        this.blockPos.set(0, -1, 0);
    }

    public FastKill() {
        super(Categories.Exclusive, "fast-kill", "Fast kill for crystal.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.sgPause = this.settings.createGroup("Pause");
        this.tickDelay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay between place and kill.").defaultValue(3).min(0).sliderMax(10).build());
        this.FastKillMode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode of FastKill.").defaultValue(Mode.AntiSurround).build());
        this.crystal = this.sgGeneral.add(new BoolSetting.Builder().name("kill-mode").description("Fast Kill players.").defaultValue(true).build());
        this.pauseOnEat = this.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses Crystal Aura while eating.").defaultValue(true).build());
        this.pauseOnDrink = this.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses Crystal Aura while drinking a potion.").defaultValue(false).build());
        this.pauseOnMine = this.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses Crystal Aura while mining blocks.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Faces the blocks.").defaultValue(true).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("color-1").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("color-2").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        this.blockPos = new Mutable(0, -1, 0);
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Fast;
        public static final /* enum */ Mode None;
        public static final /* enum */ Mode Legit;
        public static final /* enum */ Mode AntiSurround;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            AntiSurround = new Mode();
            Fast = new Mode();
            Legit = new Mode();
            None = new Mode();
            $VALUES = Mode.$values();
        }

        private static Mode[] $values() {
            return new Mode[]{AntiSurround, Fast, Legit, None};
        }
    }
}

