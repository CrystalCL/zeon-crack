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
import minegame159.meteorclient.events.world.TickEvent;
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
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.player.Safety;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class BedAuraPlus
extends Module {
    private final Setting<Double> minDamage;
    private final SettingGroup sgRender;
    private final Setting<Boolean> autoSwitch;
    private final SettingGroup sgPlace;
    private final SettingGroup sgMisc;
    private final Setting<SortPriority> priority;
    private final Setting<Integer> placeDelay;
    private final Setting<Boolean> pauseOnEat;
    private int breakDelayLeft;
    private PlayerEntity target;
    private final Setting<Boolean> render;
    private final SettingGroup sgBreak;
    private Stage stage;
    private final Setting<Boolean> autoMove;
    private final Setting<Boolean> pauseOnDrink;
    private BlockPos bestPos;
    private final Setting<Double> targetRange;
    private final Setting<Boolean> pauseOnMine;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private int placeDelayLeft;
    private final Setting<Integer> breakDelay;
    private final Setting<SettingColor> sideColor;
    private final Setting<Boolean> place;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Safety> placeMode;
    private final Setting<SettingColor> lineColor;
    private final Setting<Double> maxSelfDamage;
    private final Setting<Boolean> swapBack;
    private final Setting<Safety> breakMode;
    private final Setting<Boolean> noSwing;
    private final Setting<Double> minHealth;
    private final Setting<Integer> autoMoveSlot;
    private Direction direction;
    private final SettingGroup sgPause;

    private boolean checkBreak(Direction Direction2, PlayerEntity PlayerEntity2, boolean bl) {
        BlockPos BlockPos2;
        BlockPos BlockPos3 = BlockPos2 = bl ? PlayerEntity2.getBlockPos().up() : PlayerEntity2.getBlockPos();
        if (this.mc.world.getBlockState(BlockPos2).getBlock() instanceof BedBlock && this.mc.world.getBlockState(BlockPos2.offset(Direction2)).getBlock() instanceof BedBlock && (this.breakMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)PlayerEntity2, Utils.vec3d(BlockPos2)) >= this.minDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Utils.vec3d(BlockPos2.offset(Direction2))) < this.maxSelfDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Utils.vec3d(BlockPos2)) < this.maxSelfDamage.get())) {
            this.direction = Direction2;
            return true;
        }
        return false;
    }

    private static boolean lambda$onTick$0(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.render.get().booleanValue() && this.bestPos != null) {
            int n = this.bestPos.getX();
            int n2 = this.bestPos.getY();
            int n3 = this.bestPos.getZ();
            switch (this.direction) {
                case NORTH: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3, n + 1, (double)n2 + 0.6, n3 + 2, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
                    break;
                }
                case SOUTH: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3 - 1, n + 1, (double)n2 + 0.6, n3 + 1, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
                    break;
                }
                case EAST: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n - 1, n2, n3, n + 1, (double)n2 + 0.6, n3 + 1, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
                    break;
                }
                case WEST: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3, n + 2, (double)n2 + 0.6, n3 + 1, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
                }
            }
        }
    }

    private float yawFromDir(Direction Direction2) {
        switch (Direction2) {
            case EAST: {
                return 90.0f;
            }
            case NORTH: {
                return 0.0f;
            }
            case SOUTH: {
                return 180.0f;
            }
            case WEST: {
                return -90.0f;
            }
        }
        return 0.0f;
    }

    private void breakBed(BlockPos BlockPos2) {
        if (BlockPos2 == null) {
            return;
        }
        boolean bl = this.mc.player.isSneaking();
        if (bl) {
            this.mc.player.input.sneaking = false;
        }
        this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.OFF_HAND, new BlockHitResult(this.mc.player.getPos(), Direction.UP, this.bestPos, false));
        if (bl) {
            this.mc.player.input.sneaking = true;
        }
    }

    @Override
    public String getInfoString() {
        if (this.target != null) {
            return this.target.getEntityName();
        }
        return null;
    }

    private static boolean lambda$doAutoMove$6(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private static boolean lambda$placeBed$3(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private BlockPos getBreakPos(PlayerEntity PlayerEntity2) {
        BlockPos BlockPos2 = PlayerEntity2.getBlockPos();
        if (this.checkBreak(Direction.NORTH, PlayerEntity2, true)) {
            return BlockPos2.up().north();
        }
        if (this.checkBreak(Direction.SOUTH, PlayerEntity2, true)) {
            return BlockPos2.up().south();
        }
        if (this.checkBreak(Direction.EAST, PlayerEntity2, true)) {
            return BlockPos2.up().east();
        }
        if (this.checkBreak(Direction.WEST, PlayerEntity2, true)) {
            return BlockPos2.up().west();
        }
        if (this.checkBreak(Direction.NORTH, PlayerEntity2, false)) {
            return BlockPos2.north();
        }
        if (this.checkBreak(Direction.SOUTH, PlayerEntity2, false)) {
            return BlockPos2.south();
        }
        if (this.checkBreak(Direction.EAST, PlayerEntity2, false)) {
            return BlockPos2.east();
        }
        if (this.checkBreak(Direction.WEST, PlayerEntity2, false)) {
            return BlockPos2.west();
        }
        return null;
    }

    private static boolean lambda$placeBed$1(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private static boolean lambda$placeBed$2(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private boolean checkPlace(Direction Direction2, PlayerEntity PlayerEntity2, boolean bl) {
        BlockPos BlockPos2;
        BlockPos BlockPos3 = BlockPos2 = bl ? PlayerEntity2.getBlockPos().up() : PlayerEntity2.getBlockPos();
        if (this.mc.world.getBlockState(BlockPos2).getMaterial().isReplaceable() && BlockUtils.canPlace(BlockPos2.offset(Direction2)) && (this.placeMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)PlayerEntity2, Utils.vec3d(BlockPos2)) >= this.minDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Utils.vec3d(BlockPos2.offset(Direction2))) < this.maxSelfDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Utils.vec3d(BlockPos2)) < this.maxSelfDamage.get())) {
            this.direction = Direction2;
            return true;
        }
        return false;
    }

    public BedAuraPlus() {
        super(Categories.Exclusive, "bed-aura+", "Bed Aura+");
        this.sgPlace = this.settings.createGroup("Place");
        this.sgBreak = this.settings.createGroup("Break");
        this.sgPause = this.settings.createGroup("Pause");
        this.sgMisc = this.settings.createGroup("Misc");
        this.sgRender = this.settings.createGroup("Render");
        this.place = this.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Bed Aura to place beds.").defaultValue(true).build());
        this.placeMode = this.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The way beds are allowed to be placed near you.").defaultValue(Safety.Safe).build());
        this.placeDelay = this.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The tick delay for placing beds.").defaultValue(9).min(0).sliderMax(20).build());
        this.breakDelay = this.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The tick delay for breaking beds.").defaultValue(0).min(0).sliderMax(20).build());
        this.breakMode = this.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The way beds are allowed to be broken near you.").defaultValue(Safety.Safe).build());
        this.pauseOnEat = this.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
        this.pauseOnDrink = this.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
        this.pauseOnMine = this.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
        this.targetRange = this.sgMisc.add(new DoubleSetting.Builder().name("range").description("The maximum range for players to be targeted.").defaultValue(4.0).min(0.0).sliderMax(5.0).build());
        this.autoSwitch = this.sgMisc.add(new BoolSetting.Builder().name("switch").description("Switches to a bed automatically.").defaultValue(true).build());
        this.swapBack = this.sgMisc.add(new BoolSetting.Builder().name("swap").description("Switches back to previous slot after placing.").defaultValue(true).build());
        this.autoMove = this.sgMisc.add(new BoolSetting.Builder().name("move").description("Moves beds into a selected slot.").defaultValue(false).build());
        this.autoMoveSlot = this.sgMisc.add(new IntSetting.Builder().name("move-slot").description("The slot Auto Move.").defaultValue(9).min(1).sliderMin(1).max(9).sliderMax(9).build());
        this.noSwing = this.sgMisc.add(new BoolSetting.Builder().name("no-swing").description("Disables hand swings clientside.").defaultValue(false).build());
        this.minDamage = this.sgMisc.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage to inflict on your target.").defaultValue(7.0).min(0.0).sliderMax(20.0).max(20.0).build());
        this.maxSelfDamage = this.sgMisc.add(new DoubleSetting.Builder().name("max-self-dmg").description(".").defaultValue(7.0).min(0.0).sliderMax(20.0).max(20.0).build());
        this.minHealth = this.sgMisc.add(new DoubleSetting.Builder().name("min-health").description("The minimum health required for Bed Aura to work.").defaultValue(4.0).min(0.0).sliderMax(36.0).max(36.0).build());
        this.priority = this.sgMisc.add(new EnumSetting.Builder().name("priority").description("How to select the player to target.").defaultValue(SortPriority.LowestHealth).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block where it is placing a bed.").defaultValue(true).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("place-side-color").description("The side color for positions to be placed.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("place-line-color").description("The line color for positions to be placed.").defaultValue(new SettingColor(15, 255, 211, 255)).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    }

    private BlockPos getPlacePos(PlayerEntity PlayerEntity2) {
        BlockPos BlockPos2 = PlayerEntity2.getBlockPos();
        if (this.checkPlace(Direction.NORTH, PlayerEntity2, true)) {
            return BlockPos2.up().north();
        }
        if (this.checkPlace(Direction.SOUTH, PlayerEntity2, true)) {
            return BlockPos2.up().south();
        }
        if (this.checkPlace(Direction.EAST, PlayerEntity2, true)) {
            return BlockPos2.up().east();
        }
        if (this.checkPlace(Direction.WEST, PlayerEntity2, true)) {
            return BlockPos2.up().west();
        }
        if (this.checkPlace(Direction.NORTH, PlayerEntity2, false)) {
            return BlockPos2.north();
        }
        if (this.checkPlace(Direction.SOUTH, PlayerEntity2, false)) {
            return BlockPos2.south();
        }
        if (this.checkPlace(Direction.EAST, PlayerEntity2, false)) {
            return BlockPos2.east();
        }
        if (this.checkPlace(Direction.WEST, PlayerEntity2, false)) {
            return BlockPos2.west();
        }
        return null;
    }

    private void placeBed(BlockPos BlockPos2) {
        Hand Hand2;
        int n;
        if (BlockPos2 == null || InvUtils.findItemInAll(BedAuraPlus::lambda$placeBed$1) == -1) {
            return;
        }
        if (this.autoMove.get().booleanValue()) {
            this.doAutoMove();
        }
        if ((n = InvUtils.findItemInHotbar(BedAuraPlus::lambda$placeBed$2)) == -1) {
            return;
        }
        if (this.autoSwitch.get().booleanValue()) {
            this.mc.player.inventory.selectedSlot = n;
        }
        if ((Hand2 = InvUtils.getHand(BedAuraPlus::lambda$placeBed$3)) == null) {
            return;
        }
        Rotations.rotate(this.yawFromDir(this.direction), this.mc.player.pitch, () -> this.lambda$placeBed$4(BlockPos2, Hand2, n));
    }

    private static boolean lambda$doAutoMove$5(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private void doAutoMove() {
        if (InvUtils.findItemInHotbar(BedAuraPlus::lambda$doAutoMove$5) == -1) {
            int n = InvUtils.findItemInMain(BedAuraPlus::lambda$doAutoMove$6);
            InvUtils.move().from(n).toHotbar(this.autoMoveSlot.get() - 1);
        }
    }

    private void lambda$placeBed$4(BlockPos BlockPos2, Hand Hand2, int n) {
        BlockUtils.place(BlockPos2, Hand2, n, false, 100, this.noSwing.get() == false, true, this.autoSwitch.get(), this.swapBack.get());
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
            if (-4 < 0) continue;
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
        this.stage = this.place.get() != false ? Stage.Placing : Stage.Breaking;
        this.bestPos = null;
        this.direction = Direction.EAST;
        this.placeDelayLeft = this.placeDelay.get();
        this.breakDelayLeft = this.placeDelay.get();
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.world.getDimension().isBedWorking()) {
            ChatUtils.moduleError(this, "You are in the Overworld... disabling!", new Object[0]);
            this.toggle();
            return;
        }
        if (PlayerUtils.shouldPause(this.pauseOnMine.get(), this.pauseOnEat.get(), this.pauseOnDrink.get())) {
            return;
        }
        if ((double)EntityUtils.getTotalHealth((PlayerEntity)this.mc.player) <= this.minHealth.get()) {
            return;
        }
        this.target = EntityUtils.getPlayerTarget(this.targetRange.get(), this.priority.get(), false);
        if (this.target == null) {
            this.bestPos = null;
            return;
        }
        if (this.place.get().booleanValue() && InvUtils.findItemInAll(BedAuraPlus::lambda$onTick$0) != -1) {
            switch (this.stage) {
                case Placing: {
                    this.bestPos = this.getPlacePos(this.target);
                    if (this.placeDelayLeft > 0) {
                        --this.placeDelayLeft;
                    } else {
                        this.placeBed(this.bestPos);
                        this.placeDelayLeft = this.placeDelay.get();
                        this.stage = Stage.Breaking;
                    }
                }
                case Breaking: {
                    this.bestPos = this.getBreakPos(this.target);
                    if (this.breakDelayLeft > 0) {
                        --this.breakDelayLeft;
                        break;
                    }
                    this.breakBed(this.bestPos);
                    this.breakDelayLeft = this.breakDelay.get();
                    this.stage = Stage.Placing;
                }
            }
        } else {
            this.bestPos = this.getBreakPos(this.target);
            if (this.breakDelayLeft > 0) {
                --this.breakDelayLeft;
            } else {
                this.breakDelayLeft = this.breakDelay.get();
                this.breakBed(this.bestPos);
            }
        }
    }

    private static final class Stage
    extends Enum<Stage> {
        public static final /* enum */ Stage Placing = new Stage();
        public static final /* enum */ Stage Breaking = new Stage();
        private static final Stage[] $VALUES = Stage.$values();

        private static Stage[] $values() {
            return new Stage[]{Placing, Breaking};
        }

        public static Stage[] values() {
            return (Stage[])$VALUES.clone();
        }

        public static Stage valueOf(String string) {
            return Enum.valueOf(Stage.class, string);
        }
    }
}

