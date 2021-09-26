/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

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

public class BedAura
extends Module {
    private final Setting<Boolean> autoMove;
    private final SettingGroup sgRender;
    private final Setting<SettingColor> lineColor;
    private final SettingGroup sgPlace;
    private final Setting<SortPriority> priority;
    private final Setting<Double> minHealth;
    private final Setting<Boolean> render;
    private final Setting<Boolean> pauseOnEat;
    private final Setting<Integer> placeDelay;
    private Direction direction;
    private final SettingGroup sgMisc;
    private Stage stage;
    private final Setting<Boolean> autoSwitch;
    private final Setting<Boolean> place;
    private BlockPos bestPos;
    private final Setting<Boolean> pauseOnDrink;
    private final Setting<SettingColor> sideColor;
    private final Setting<Double> minDamage;
    private final SettingGroup sgPause;
    private PlayerEntity target;
    private final Setting<Double> maxSelfDamage;
    private final SettingGroup sgBreak;
    private final Setting<Integer> breakDelay;
    private final Setting<Boolean> noSwing;
    private final Setting<Boolean> pauseOnMine;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Safety> placeMode;
    private final Setting<Double> targetRange;
    private int breakDelayLeft;
    private final Setting<Boolean> swapBack;
    private final Setting<Safety> breakMode;
    private int placeDelayLeft;
    private final Setting<Integer> autoMoveSlot;

    private float yawFromDir(Direction Direction2) {
        switch (1.$SwitchMap$net$minecraft$util$math$Direction[Direction2.ordinal()]) {
            case 1: {
                return 90.0f;
            }
            case 2: {
                return 0.0f;
            }
            case 3: {
                return 180.0f;
            }
            case 4: {
                return -90.0f;
            }
        }
        return 0.0f;
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

    public BedAura() {
        super(Categories.Combat, "bed-aura", "Automatically places and explodes beds in the Nether and End.");
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
        this.autoSwitch = this.sgMisc.add(new BoolSetting.Builder().name("auto-switch").description("Switches to a bed automatically.").defaultValue(true).build());
        this.swapBack = this.sgMisc.add(new BoolSetting.Builder().name("swap-back").description("Switches back to previous slot after placing.").defaultValue(true).build());
        this.autoMove = this.sgMisc.add(new BoolSetting.Builder().name("auto-move").description("Moves beds into a selected hotbar slot.").defaultValue(false).build());
        this.autoMoveSlot = this.sgMisc.add(new IntSetting.Builder().name("auto-move-slot").description("The slot Auto Move moves beds to.").defaultValue(9).min(1).sliderMin(1).max(9).sliderMax(9).build());
        this.noSwing = this.sgMisc.add(new BoolSetting.Builder().name("no-swing").description("Disables hand swings clientside.").defaultValue(false).build());
        this.minDamage = this.sgMisc.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage to inflict on your target.").defaultValue(7.0).min(0.0).sliderMax(20.0).max(20.0).build());
        this.maxSelfDamage = this.sgMisc.add(new DoubleSetting.Builder().name("max-self-damage").description("The maximum damage to inflict on yourself.").defaultValue(7.0).min(0.0).sliderMax(20.0).max(20.0).build());
        this.minHealth = this.sgMisc.add(new DoubleSetting.Builder().name("min-health").description("The minimum health required for Bed Aura to work.").defaultValue(4.0).min(0.0).sliderMax(36.0).max(36.0).build());
        this.priority = this.sgMisc.add(new EnumSetting.Builder().name("priority").description("How to select the player to target.").defaultValue(SortPriority.LowestHealth).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block where it is placing a bed.").defaultValue(true).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("place-side-color").description("The side color for positions to be placed.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("place-line-color").description("The line color for positions to be placed.").defaultValue(new SettingColor(15, 255, 211, 255)).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
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

    private static boolean lambda$onTick$0(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private void placeBed(BlockPos BlockPos2) {
        Hand Hand2;
        int n;
        if (BlockPos2 == null || InvUtils.findItemInAll(BedAura::lambda$placeBed$1) == -1) {
            return;
        }
        if (this.autoMove.get().booleanValue()) {
            this.doAutoMove();
        }
        if ((n = InvUtils.findItemInHotbar(BedAura::lambda$placeBed$2)) == -1) {
            return;
        }
        if (this.autoSwitch.get().booleanValue()) {
            this.mc.player.inventory.selectedSlot = n;
        }
        if ((Hand2 = InvUtils.getHand(BedAura::lambda$placeBed$3)) == null) {
            return;
        }
        Rotations.rotate(this.yawFromDir(this.direction), this.mc.player.pitch, () -> this.lambda$placeBed$4(BlockPos2, Hand2, n));
    }

    private static boolean lambda$placeBed$3(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private static boolean lambda$placeBed$2(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private void doAutoMove() {
        if (InvUtils.findItemInHotbar(BedAura::lambda$doAutoMove$5) == -1) {
            int n = InvUtils.findItemInMain(BedAura::lambda$doAutoMove$6);
            InvUtils.move().from(n).toHotbar(this.autoMoveSlot.get() - 1);
        }
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

    private void lambda$placeBed$4(BlockPos BlockPos2, Hand Hand2, int n) {
        BlockUtils.place(BlockPos2, Hand2, n, false, 100, this.noSwing.get() == false, true, this.autoSwitch.get(), this.swapBack.get());
    }

    @Override
    public String getInfoString() {
        if (this.target != null) {
            return this.target.getEntityName();
        }
        return null;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.render.get().booleanValue() && this.bestPos != null) {
            int n = this.bestPos.getX();
            int n2 = this.bestPos.getY();
            int n3 = this.bestPos.getZ();
            switch (1.$SwitchMap$net$minecraft$util$math$Direction[this.direction.ordinal()]) {
                case 2: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3, n + 1, (double)n2 + 0.6, n3 + 2, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
                    break;
                }
                case 3: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3 - 1, n + 1, (double)n2 + 0.6, n3 + 1, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
                    break;
                }
                case 1: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n - 1, n2, n3, n + 1, (double)n2 + 0.6, n3 + 1, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
                    break;
                }
                case 4: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, n, n2, n3, n + 2, (double)n2 + 0.6, n3 + 1, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
                }
            }
        }
    }

    @Override
    public void onActivate() {
        this.stage = this.place.get() != false ? Stage.Placing : Stage.Breaking;
        this.bestPos = null;
        this.direction = Direction.EAST;
        this.placeDelayLeft = this.placeDelay.get();
        this.breakDelayLeft = this.placeDelay.get();
    }

    private boolean checkBreak(Direction Direction2, PlayerEntity PlayerEntity2, boolean bl) {
        BlockPos BlockPos2;
        BlockPos BlockPos3 = BlockPos2 = bl ? PlayerEntity2.getBlockPos().up() : PlayerEntity2.getBlockPos();
        if (this.mc.world.getBlockState(BlockPos2).getBlock() instanceof BedBlock && this.mc.world.getBlockState(BlockPos2.offset(Direction2)).getBlock() instanceof BedBlock && (this.breakMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)PlayerEntity2, Utils.vec3d(BlockPos2)) >= this.minDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Utils.vec3d(BlockPos2.offset(Direction2))) < this.maxSelfDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Utils.vec3d(BlockPos2)) < this.maxSelfDamage.get())) {
            this.direction = Direction2;
            return true;
        }
        return false;
    }

    private static boolean lambda$doAutoMove$5(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
    }

    private static boolean lambda$placeBed$1(ItemStack ItemStack2) {
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

    private static boolean lambda$doAutoMove$6(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BedItem;
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
        if (this.place.get().booleanValue() && InvUtils.findItemInAll(BedAura::lambda$onTick$0) != -1) {
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
        private static final Stage[] $VALUES;
        public static final /* enum */ Stage Placing;
        public static final /* enum */ Stage Breaking;

        public static Stage valueOf(String string) {
            return Enum.valueOf(Stage.class, string);
        }

        private static Stage[] $values() {
            return new Stage[]{Placing, Breaking};
        }

        public static Stage[] values() {
            return (Stage[])$VALUES.clone();
        }

        static {
            Placing = new Stage();
            Breaking = new Stage();
            $VALUES = Stage.$values();
        }
    }
}

