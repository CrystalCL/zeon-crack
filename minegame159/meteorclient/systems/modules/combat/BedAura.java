/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.BedItem
 *  net.minecraft.block.BedBlock
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.hit.BlockHitResult
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
import net.minecraft.block.BedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.BlockHitResult;

public class BedAura
extends Module {
    private final /* synthetic */ Setting<Boolean> place;
    private final /* synthetic */ Setting<Boolean> autoMove;
    private final /* synthetic */ Setting<Safety> placeMode;
    private final /* synthetic */ Setting<SortPriority> priority;
    private final /* synthetic */ Setting<Boolean> pauseOnDrink;
    private /* synthetic */ Direction direction;
    private final /* synthetic */ Setting<Integer> autoMoveSlot;
    private final /* synthetic */ Setting<Boolean> swapBack;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Double> minDamage;
    private /* synthetic */ int breakDelayLeft;
    private final /* synthetic */ Setting<Double> minHealth;
    private final /* synthetic */ SettingGroup sgPlace;
    private final /* synthetic */ Setting<Boolean> pauseOnEat;
    private final /* synthetic */ Setting<Double> maxSelfDamage;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Boolean> noSwing;
    private final /* synthetic */ Setting<Integer> breakDelay;
    private final /* synthetic */ SettingGroup sgBreak;
    private final /* synthetic */ SettingGroup sgPause;
    private final /* synthetic */ Setting<Double> targetRange;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<Integer> placeDelay;
    private final /* synthetic */ Setting<Boolean> autoSwitch;
    private final /* synthetic */ Setting<Boolean> render;
    private /* synthetic */ int placeDelayLeft;
    private /* synthetic */ Stage stage;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ SettingGroup sgMisc;
    private final /* synthetic */ Setting<Safety> breakMode;
    private final /* synthetic */ Setting<Boolean> pauseOnMine;
    private /* synthetic */ BlockPos bestPos;

    private boolean checkPlace(Direction llllIlIlIIlIllI, PlayerEntity llllIlIlIIlIlIl, boolean llllIlIlIIlIlII) {
        BedAura llllIlIlIIlIlll;
        BlockPos llllIlIlIIlIIll;
        BlockPos BlockPos2 = llllIlIlIIlIIll = llllIlIlIIlIlII ? llllIlIlIIlIlIl.getBlockPos().up() : llllIlIlIIlIlIl.getBlockPos();
        if (llllIlIlIIlIlll.mc.world.getBlockState(llllIlIlIIlIIll).getMaterial().isReplaceable() && BlockUtils.canPlace(llllIlIlIIlIIll.offset(llllIlIlIIlIllI)) && (llllIlIlIIlIlll.placeMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)llllIlIlIIlIlIl, Utils.vec3d(llllIlIlIIlIIll)) >= llllIlIlIIlIlll.minDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)llllIlIlIIlIlll.mc.player, Utils.vec3d(llllIlIlIIlIIll.offset(llllIlIlIIlIllI))) < llllIlIlIIlIlll.maxSelfDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)llllIlIlIIlIlll.mc.player, Utils.vec3d(llllIlIlIIlIIll)) < llllIlIlIIlIlll.maxSelfDamage.get())) {
            llllIlIlIIlIlll.direction = llllIlIlIIlIllI;
            return true;
        }
        return false;
    }

    private void breakBed(BlockPos llllIlIlIlIlIlI) {
        BedAura llllIlIlIlIlIII;
        if (llllIlIlIlIlIlI == null) {
            return;
        }
        boolean llllIlIlIlIlIIl = llllIlIlIlIlIII.mc.player.isSneaking();
        if (llllIlIlIlIlIIl) {
            llllIlIlIlIlIII.mc.player.input.sneaking = false;
        }
        llllIlIlIlIlIII.mc.interactionManager.interactBlock(llllIlIlIlIlIII.mc.player, llllIlIlIlIlIII.mc.world, Hand.OFF_HAND, new BlockHitResult(llllIlIlIlIlIII.mc.player.getPos(), Direction.UP, llllIlIlIlIlIII.bestPos, false));
        if (llllIlIlIlIlIIl) {
            llllIlIlIlIlIII.mc.player.input.sneaking = true;
        }
    }

    private float yawFromDir(Direction llllIlIIllIllII) {
        switch (llllIlIIllIllII) {
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

    private void placeBed(BlockPos llllIlIlIllIlIl) {
        Hand llllIlIlIllIIll;
        int llllIlIlIllIlII;
        BedAura llllIlIlIllIllI;
        if (llllIlIlIllIlIl == null || InvUtils.findItemInAll(llllIlIIlIIIIIl -> llllIlIIlIIIIIl.getItem() instanceof BedItem) == -1) {
            return;
        }
        if (llllIlIlIllIllI.autoMove.get().booleanValue()) {
            llllIlIlIllIllI.doAutoMove();
        }
        if ((llllIlIlIllIlII = InvUtils.findItemInHotbar(llllIlIIlIIIlII -> llllIlIIlIIIlII.getItem() instanceof BedItem)) == -1) {
            return;
        }
        if (llllIlIlIllIllI.autoSwitch.get().booleanValue()) {
            llllIlIlIllIllI.mc.player.inventory.selectedSlot = llllIlIlIllIlII;
        }
        if ((llllIlIlIllIIll = InvUtils.getHand(llllIlIIlIIlIII -> llllIlIIlIIlIII.getItem() instanceof BedItem)) == null) {
            return;
        }
        Rotations.rotate(llllIlIlIllIllI.yawFromDir(llllIlIlIllIllI.direction), llllIlIlIllIllI.mc.player.pitch, () -> {
            BedAura llllIlIIlIlIIIl;
            BlockUtils.place(llllIlIlIllIlIl, llllIlIlIllIIll, llllIlIlIllIlII, false, 100, llllIlIIlIlIIIl.noSwing.get() == false, true, llllIlIIlIlIIIl.autoSwitch.get(), llllIlIIlIlIIIl.swapBack.get());
        });
    }

    @EventHandler
    private void onRender(RenderEvent llllIlIIllIIIll) {
        BedAura llllIlIIllIIlII;
        if (llllIlIIllIIlII.render.get().booleanValue() && llllIlIIllIIlII.bestPos != null) {
            int llllIlIIllIIlll = llllIlIIllIIlII.bestPos.getX();
            int llllIlIIllIIllI = llllIlIIllIIlII.bestPos.getY();
            int llllIlIIllIIlIl = llllIlIIllIIlII.bestPos.getZ();
            switch (llllIlIIllIIlII.direction) {
                case NORTH: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllIlIIllIIlll, llllIlIIllIIllI, llllIlIIllIIlIl, llllIlIIllIIlll + 1, (double)llllIlIIllIIllI + 0.6, llllIlIIllIIlIl + 2, llllIlIIllIIlII.sideColor.get(), llllIlIIllIIlII.lineColor.get(), llllIlIIllIIlII.shapeMode.get(), 0);
                    break;
                }
                case SOUTH: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllIlIIllIIlll, llllIlIIllIIllI, llllIlIIllIIlIl - 1, llllIlIIllIIlll + 1, (double)llllIlIIllIIllI + 0.6, llllIlIIllIIlIl + 1, llllIlIIllIIlII.sideColor.get(), llllIlIIllIIlII.lineColor.get(), llllIlIIllIIlII.shapeMode.get(), 0);
                    break;
                }
                case EAST: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllIlIIllIIlll - 1, llllIlIIllIIllI, llllIlIIllIIlIl, llllIlIIllIIlll + 1, (double)llllIlIIllIIllI + 0.6, llllIlIIllIIlIl + 1, llllIlIIllIIlII.sideColor.get(), llllIlIIllIIlII.lineColor.get(), llllIlIIllIIlII.shapeMode.get(), 0);
                    break;
                }
                case WEST: {
                    Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllIlIIllIIlll, llllIlIIllIIllI, llllIlIIllIIlIl, llllIlIIllIIlll + 2, (double)llllIlIIllIIllI + 0.6, llllIlIIllIIlIl + 1, llllIlIIllIIlII.sideColor.get(), llllIlIIllIIlII.lineColor.get(), llllIlIIllIIlII.shapeMode.get(), 0);
                }
            }
        }
    }

    private void doAutoMove() {
        if (InvUtils.findItemInHotbar(llllIlIIlIlIlll -> llllIlIIlIlIlll.getItem() instanceof BedItem) == -1) {
            BedAura llllIlIIlllIIIl;
            int llllIlIIlllIIll = InvUtils.findItemInMain(llllIlIIlIllIlI -> llllIlIIlIllIlI.getItem() instanceof BedItem);
            InvUtils.move().from(llllIlIIlllIIll).toHotbar(llllIlIIlllIIIl.autoMoveSlot.get() - 1);
        }
    }

    private boolean checkBreak(Direction llllIlIIllllllI, PlayerEntity llllIlIIlllllIl, boolean llllIlIIlllIlll) {
        BedAura llllIlIIlllllll;
        BlockPos llllIlIIllllIll;
        BlockPos BlockPos2 = llllIlIIllllIll = llllIlIIlllIlll ? llllIlIIlllllIl.getBlockPos().up() : llllIlIIlllllIl.getBlockPos();
        if (llllIlIIlllllll.mc.world.getBlockState(llllIlIIllllIll).getBlock() instanceof BedBlock && llllIlIIlllllll.mc.world.getBlockState(llllIlIIllllIll.offset(llllIlIIllllllI)).getBlock() instanceof BedBlock && (llllIlIIlllllll.breakMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)llllIlIIlllllIl, Utils.vec3d(llllIlIIllllIll)) >= llllIlIIlllllll.minDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)llllIlIIlllllll.mc.player, Utils.vec3d(llllIlIIllllIll.offset(llllIlIIllllllI))) < llllIlIIlllllll.maxSelfDamage.get() && DamageCalcUtils.bedDamage((LivingEntity)llllIlIIlllllll.mc.player, Utils.vec3d(llllIlIIllllIll)) < llllIlIIlllllll.maxSelfDamage.get())) {
            llllIlIIlllllll.direction = llllIlIIllllllI;
            return true;
        }
        return false;
    }

    @Override
    public String getInfoString() {
        BedAura llllIlIIlIlllII;
        if (llllIlIIlIlllII.target != null) {
            return llllIlIIlIlllII.target.getEntityName();
        }
        return null;
    }

    private BlockPos getBreakPos(PlayerEntity llllIlIlIIIlIIl) {
        BedAura llllIlIlIIIIlll;
        BlockPos llllIlIlIIIlIII = llllIlIlIIIlIIl.getBlockPos();
        if (llllIlIlIIIIlll.checkBreak(Direction.NORTH, llllIlIlIIIlIIl, true)) {
            return llllIlIlIIIlIII.up().north();
        }
        if (llllIlIlIIIIlll.checkBreak(Direction.SOUTH, llllIlIlIIIlIIl, true)) {
            return llllIlIlIIIlIII.up().south();
        }
        if (llllIlIlIIIIlll.checkBreak(Direction.EAST, llllIlIlIIIlIIl, true)) {
            return llllIlIlIIIlIII.up().east();
        }
        if (llllIlIlIIIIlll.checkBreak(Direction.WEST, llllIlIlIIIlIIl, true)) {
            return llllIlIlIIIlIII.up().west();
        }
        if (llllIlIlIIIIlll.checkBreak(Direction.NORTH, llllIlIlIIIlIIl, false)) {
            return llllIlIlIIIlIII.north();
        }
        if (llllIlIlIIIIlll.checkBreak(Direction.SOUTH, llllIlIlIIIlIIl, false)) {
            return llllIlIlIIIlIII.south();
        }
        if (llllIlIlIIIIlll.checkBreak(Direction.EAST, llllIlIlIIIlIIl, false)) {
            return llllIlIlIIIlIII.east();
        }
        if (llllIlIlIIIIlll.checkBreak(Direction.WEST, llllIlIlIIIlIIl, false)) {
            return llllIlIlIIIlIII.west();
        }
        return null;
    }

    private BlockPos getPlacePos(PlayerEntity llllIlIlIlIIIIl) {
        BedAura llllIlIlIlIIIlI;
        BlockPos llllIlIlIlIIIII = llllIlIlIlIIIIl.getBlockPos();
        if (llllIlIlIlIIIlI.checkPlace(Direction.NORTH, llllIlIlIlIIIIl, true)) {
            return llllIlIlIlIIIII.up().north();
        }
        if (llllIlIlIlIIIlI.checkPlace(Direction.SOUTH, llllIlIlIlIIIIl, true)) {
            return llllIlIlIlIIIII.up().south();
        }
        if (llllIlIlIlIIIlI.checkPlace(Direction.EAST, llllIlIlIlIIIIl, true)) {
            return llllIlIlIlIIIII.up().east();
        }
        if (llllIlIlIlIIIlI.checkPlace(Direction.WEST, llllIlIlIlIIIIl, true)) {
            return llllIlIlIlIIIII.up().west();
        }
        if (llllIlIlIlIIIlI.checkPlace(Direction.NORTH, llllIlIlIlIIIIl, false)) {
            return llllIlIlIlIIIII.north();
        }
        if (llllIlIlIlIIIlI.checkPlace(Direction.SOUTH, llllIlIlIlIIIIl, false)) {
            return llllIlIlIlIIIII.south();
        }
        if (llllIlIlIlIIIlI.checkPlace(Direction.EAST, llllIlIlIlIIIIl, false)) {
            return llllIlIlIlIIIII.east();
        }
        if (llllIlIlIlIIIlI.checkPlace(Direction.WEST, llllIlIlIlIIIIl, false)) {
            return llllIlIlIlIIIII.west();
        }
        return null;
    }

    @EventHandler
    private void onTick(TickEvent.Post llllIlIlIllllII) {
        BedAura llllIlIlIlllIll;
        if (llllIlIlIlllIll.mc.world.getDimension().isBedWorking()) {
            ChatUtils.moduleError(llllIlIlIlllIll, "You are in the Overworld... disabling!", new Object[0]);
            llllIlIlIlllIll.toggle();
            return;
        }
        if (PlayerUtils.shouldPause(llllIlIlIlllIll.pauseOnMine.get(), llllIlIlIlllIll.pauseOnEat.get(), llllIlIlIlllIll.pauseOnDrink.get())) {
            return;
        }
        if ((double)EntityUtils.getTotalHealth((PlayerEntity)llllIlIlIlllIll.mc.player) <= llllIlIlIlllIll.minHealth.get()) {
            return;
        }
        llllIlIlIlllIll.target = EntityUtils.getPlayerTarget(llllIlIlIlllIll.targetRange.get(), llllIlIlIlllIll.priority.get(), false);
        if (llllIlIlIlllIll.target == null) {
            llllIlIlIlllIll.bestPos = null;
            return;
        }
        if (llllIlIlIlllIll.place.get().booleanValue() && InvUtils.findItemInAll(llllIlIIIlllllI -> llllIlIIIlllllI.getItem() instanceof BedItem) != -1) {
            switch (llllIlIlIlllIll.stage) {
                case Placing: {
                    llllIlIlIlllIll.bestPos = llllIlIlIlllIll.getPlacePos(llllIlIlIlllIll.target);
                    if (llllIlIlIlllIll.placeDelayLeft > 0) {
                        --llllIlIlIlllIll.placeDelayLeft;
                    } else {
                        llllIlIlIlllIll.placeBed(llllIlIlIlllIll.bestPos);
                        llllIlIlIlllIll.placeDelayLeft = llllIlIlIlllIll.placeDelay.get();
                        llllIlIlIlllIll.stage = Stage.Breaking;
                    }
                }
                case Breaking: {
                    llllIlIlIlllIll.bestPos = llllIlIlIlllIll.getBreakPos(llllIlIlIlllIll.target);
                    if (llllIlIlIlllIll.breakDelayLeft > 0) {
                        --llllIlIlIlllIll.breakDelayLeft;
                        break;
                    }
                    llllIlIlIlllIll.breakBed(llllIlIlIlllIll.bestPos);
                    llllIlIlIlllIll.breakDelayLeft = llllIlIlIlllIll.breakDelay.get();
                    llllIlIlIlllIll.stage = Stage.Placing;
                }
            }
        } else {
            llllIlIlIlllIll.bestPos = llllIlIlIlllIll.getBreakPos(llllIlIlIlllIll.target);
            if (llllIlIlIlllIll.breakDelayLeft > 0) {
                --llllIlIlIlllIll.breakDelayLeft;
            } else {
                llllIlIlIlllIll.breakDelayLeft = llllIlIlIlllIll.breakDelay.get();
                llllIlIlIlllIll.breakBed(llllIlIlIlllIll.bestPos);
            }
        }
    }

    public BedAura() {
        super(Categories.Combat, "bed-aura", "Automatically places and explodes beds in the Nether and End.");
        BedAura llllIlIllIIIIll;
        llllIlIllIIIIll.sgPlace = llllIlIllIIIIll.settings.createGroup("Place");
        llllIlIllIIIIll.sgBreak = llllIlIllIIIIll.settings.createGroup("Break");
        llllIlIllIIIIll.sgPause = llllIlIllIIIIll.settings.createGroup("Pause");
        llllIlIllIIIIll.sgMisc = llllIlIllIIIIll.settings.createGroup("Misc");
        llllIlIllIIIIll.sgRender = llllIlIllIIIIll.settings.createGroup("Render");
        llllIlIllIIIIll.place = llllIlIllIIIIll.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Bed Aura to place beds.").defaultValue(true).build());
        llllIlIllIIIIll.placeMode = llllIlIllIIIIll.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The way beds are allowed to be placed near you.").defaultValue(Safety.Safe).build());
        llllIlIllIIIIll.placeDelay = llllIlIllIIIIll.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The tick delay for placing beds.").defaultValue(9).min(0).sliderMax(20).build());
        llllIlIllIIIIll.breakDelay = llllIlIllIIIIll.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The tick delay for breaking beds.").defaultValue(0).min(0).sliderMax(20).build());
        llllIlIllIIIIll.breakMode = llllIlIllIIIIll.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The way beds are allowed to be broken near you.").defaultValue(Safety.Safe).build());
        llllIlIllIIIIll.pauseOnEat = llllIlIllIIIIll.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
        llllIlIllIIIIll.pauseOnDrink = llllIlIllIIIIll.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
        llllIlIllIIIIll.pauseOnMine = llllIlIllIIIIll.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
        llllIlIllIIIIll.targetRange = llllIlIllIIIIll.sgMisc.add(new DoubleSetting.Builder().name("range").description("The maximum range for players to be targeted.").defaultValue(4.0).min(0.0).sliderMax(5.0).build());
        llllIlIllIIIIll.autoSwitch = llllIlIllIIIIll.sgMisc.add(new BoolSetting.Builder().name("auto-switch").description("Switches to a bed automatically.").defaultValue(true).build());
        llllIlIllIIIIll.swapBack = llllIlIllIIIIll.sgMisc.add(new BoolSetting.Builder().name("swap-back").description("Switches back to previous slot after placing.").defaultValue(true).build());
        llllIlIllIIIIll.autoMove = llllIlIllIIIIll.sgMisc.add(new BoolSetting.Builder().name("auto-move").description("Moves beds into a selected hotbar slot.").defaultValue(false).build());
        llllIlIllIIIIll.autoMoveSlot = llllIlIllIIIIll.sgMisc.add(new IntSetting.Builder().name("auto-move-slot").description("The slot Auto Move moves beds to.").defaultValue(9).min(1).sliderMin(1).max(9).sliderMax(9).build());
        llllIlIllIIIIll.noSwing = llllIlIllIIIIll.sgMisc.add(new BoolSetting.Builder().name("no-swing").description("Disables hand swings clientside.").defaultValue(false).build());
        llllIlIllIIIIll.minDamage = llllIlIllIIIIll.sgMisc.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage to inflict on your target.").defaultValue(7.0).min(0.0).sliderMax(20.0).max(20.0).build());
        llllIlIllIIIIll.maxSelfDamage = llllIlIllIIIIll.sgMisc.add(new DoubleSetting.Builder().name("max-self-damage").description("The maximum damage to inflict on yourself.").defaultValue(7.0).min(0.0).sliderMax(20.0).max(20.0).build());
        llllIlIllIIIIll.minHealth = llllIlIllIIIIll.sgMisc.add(new DoubleSetting.Builder().name("min-health").description("The minimum health required for Bed Aura to work.").defaultValue(4.0).min(0.0).sliderMax(36.0).max(36.0).build());
        llllIlIllIIIIll.priority = llllIlIllIIIIll.sgMisc.add(new EnumSetting.Builder().name("priority").description("How to select the player to target.").defaultValue(SortPriority.LowestHealth).build());
        llllIlIllIIIIll.render = llllIlIllIIIIll.sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block where it is placing a bed.").defaultValue(true).build());
        llllIlIllIIIIll.sideColor = llllIlIllIIIIll.sgRender.add(new ColorSetting.Builder().name("place-side-color").description("The side color for positions to be placed.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
        llllIlIllIIIIll.lineColor = llllIlIllIIIIll.sgRender.add(new ColorSetting.Builder().name("place-line-color").description("The line color for positions to be placed.").defaultValue(new SettingColor(15, 255, 211, 255)).build());
        llllIlIllIIIIll.shapeMode = llllIlIllIIIIll.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    }

    @Override
    public void onActivate() {
        BedAura llllIlIlIllllll;
        llllIlIlIllllll.stage = llllIlIlIllllll.place.get() != false ? Stage.Placing : Stage.Breaking;
        llllIlIlIllllll.bestPos = null;
        llllIlIlIllllll.direction = Direction.EAST;
        llllIlIlIllllll.placeDelayLeft = llllIlIlIllllll.placeDelay.get();
        llllIlIlIllllll.breakDelayLeft = llllIlIlIllllll.placeDelay.get();
    }

    private static final class Stage
    extends Enum<Stage> {
        private static final /* synthetic */ Stage[] $VALUES;
        public static final /* synthetic */ /* enum */ Stage Placing;
        public static final /* synthetic */ /* enum */ Stage Breaking;

        public static Stage[] values() {
            return (Stage[])$VALUES.clone();
        }

        private Stage() {
            Stage lllllIIIlIllll;
        }

        static {
            Placing = new Stage();
            Breaking = new Stage();
            $VALUES = Stage.$values();
        }

        public static Stage valueOf(String lllllIIIllIIll) {
            return Enum.valueOf(Stage.class, lllllIIIllIIll);
        }

        private static /* synthetic */ Stage[] $values() {
            return new Stage[]{Placing, Breaking};
        }
    }
}

