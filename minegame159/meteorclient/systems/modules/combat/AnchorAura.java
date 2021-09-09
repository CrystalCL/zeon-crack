/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.math.Vec3d
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
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.BlockHitResult;

public class AnchorAura
extends Module {
    private final /* synthetic */ Setting<SettingColor> breakLineColor;
    private final /* synthetic */ Setting<Boolean> pauseOnMine;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ SettingGroup sgBreak;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Double> breakRange;
    private final /* synthetic */ Setting<Integer> placeDelay;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<SettingColor> breakSideColor;
    private /* synthetic */ int placeDelayLeft;
    private final /* synthetic */ Setting<Double> maxDamage;
    private final /* synthetic */ Setting<Integer> breakDelay;
    private final /* synthetic */ Setting<Boolean> place;
    private final /* synthetic */ SettingGroup sgPlace;
    private final /* synthetic */ SettingGroup sgPause;
    private final /* synthetic */ SettingGroup sgMisc;
    private final /* synthetic */ Setting<Double> minHealth;
    private final /* synthetic */ Setting<Safety> placeMode;
    private final /* synthetic */ Setting<Boolean> pauseOnEat;
    private final /* synthetic */ Setting<Double> targetRange;
    private final /* synthetic */ Setting<Safety> breakMode;
    private final /* synthetic */ Setting<Boolean> renderBreak;
    private /* synthetic */ int breakDelayLeft;
    private final /* synthetic */ Setting<RotationMode> rotationMode;
    private final /* synthetic */ Setting<Boolean> pauseOnDrink;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Double> placeRange;
    private final /* synthetic */ Setting<SortPriority> priority;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Boolean> renderPlace;
    private final /* synthetic */ Setting<PlaceMode> placePositions;

    private BlockPos findPlacePos(BlockPos llllllllllllllllllIlIllllIlIIIIl) {
        AnchorAura llllllllllllllllllIlIllllIlIIIlI;
        switch (llllllllllllllllllIlIllllIlIIIlI.placePositions.get()) {
            case All: {
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.down())) {
                    return llllllllllllllllllIlIllllIlIIIIl.down();
                }
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.up(2))) {
                    return llllllllllllllllllIlIllllIlIIIIl.up(2);
                }
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.add(1, 0, 0))) {
                    return llllllllllllllllllIlIllllIlIIIIl.add(1, 0, 0);
                }
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.add(-1, 0, 0))) {
                    return llllllllllllllllllIlIllllIlIIIIl.add(-1, 0, 0);
                }
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.add(0, 0, 1))) {
                    return llllllllllllllllllIlIllllIlIIIIl.add(0, 0, 1);
                }
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.add(0, 0, -1))) {
                    return llllllllllllllllllIlIllllIlIIIIl.add(0, 0, -1);
                }
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.add(1, 1, 0))) {
                    return llllllllllllllllllIlIllllIlIIIIl.add(1, 1, 0);
                }
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.add(-1, -1, 0))) {
                    return llllllllllllllllllIlIllllIlIIIIl.add(-1, -1, 0);
                }
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.add(0, 1, 1))) {
                    return llllllllllllllllllIlIllllIlIIIIl.add(0, 1, 1);
                }
                if (!llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.add(0, 0, -1))) break;
                return llllllllllllllllllIlIllllIlIIIIl.add(0, 0, -1);
            }
            case Above: {
                if (!llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.up(2))) break;
                return llllllllllllllllllIlIllllIlIIIIl.up(2);
            }
            case AboveAndBelow: {
                if (llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.down())) {
                    return llllllllllllllllllIlIllllIlIIIIl.down();
                }
                if (!llllllllllllllllllIlIllllIlIIIlI.isValidPlace(llllllllllllllllllIlIllllIlIIIIl.up(2))) break;
                return llllllllllllllllllIlIllllIlIIIIl.up(2);
            }
        }
        return null;
    }

    private boolean isValidBreak(BlockPos llllllllllllllllllIlIllllIIIIIll) {
        AnchorAura llllllllllllllllllIlIllllIIIIlII;
        return llllllllllllllllllIlIllllIIIIlII.mc.world.getBlockState(llllllllllllllllllIlIllllIIIIIll).getBlock() == Blocks.RESPAWN_ANCHOR && Math.sqrt(llllllllllllllllllIlIllllIIIIlII.mc.player.getBlockPos().getSquaredDistance((Vec3i)llllllllllllllllllIlIllllIIIIIll)) <= llllllllllllllllllIlIllllIIIIlII.breakRange.get() && llllllllllllllllllIlIllllIIIIlII.getDamageBreak(llllllllllllllllllIlIllllIIIIIll);
    }

    @Override
    public void onDeactivate() {
        llllllllllllllllllIlIllllIllllII.target = null;
    }

    private void breakAnchor(BlockPos llllllllllllllllllIlIlllIlllIlIl, int llllllllllllllllllIlIlllIllllIII, int llllllllllllllllllIlIlllIlllIlll) {
        AnchorAura llllllllllllllllllIlIlllIlllIllI;
        if (llllllllllllllllllIlIlllIlllIlIl == null || llllllllllllllllllIlIlllIlllIllI.mc.world.getBlockState(llllllllllllllllllIlIlllIlllIlIl).getBlock() != Blocks.RESPAWN_ANCHOR) {
            return;
        }
        if (llllllllllllllllllIlIlllIllllIII != -1 && llllllllllllllllllIlIlllIlllIlll != -1) {
            int llllllllllllllllllIlIlllIllllIll = llllllllllllllllllIlIlllIlllIllI.mc.player.inventory.selectedSlot;
            llllllllllllllllllIlIlllIlllIllI.mc.player.inventory.selectedSlot = llllllllllllllllllIlIlllIllllIII;
            llllllllllllllllllIlIlllIlllIllI.mc.interactionManager.interactBlock(llllllllllllllllllIlIlllIlllIllI.mc.player, llllllllllllllllllIlIlllIlllIllI.mc.world, Hand.MAIN_HAND, new BlockHitResult(new Vec3d((double)llllllllllllllllllIlIlllIlllIlIl.getX() + 0.5, (double)llllllllllllllllllIlIlllIlllIlIl.getY() + 0.5, (double)llllllllllllllllllIlIlllIlllIlIl.getZ() + 0.5), Direction.UP, llllllllllllllllllIlIlllIlllIlIl, true));
            llllllllllllllllllIlIlllIlllIllI.mc.player.inventory.selectedSlot = llllllllllllllllllIlIlllIlllIlll;
            llllllllllllllllllIlIlllIlllIllI.mc.interactionManager.interactBlock(llllllllllllllllllIlIlllIlllIllI.mc.player, llllllllllllllllllIlIlllIlllIllI.mc.world, Hand.MAIN_HAND, new BlockHitResult(new Vec3d((double)llllllllllllllllllIlIlllIlllIlIl.getX() + 0.5, (double)llllllllllllllllllIlIlllIlllIlIl.getY() + 0.5, (double)llllllllllllllllllIlIlllIlllIlIl.getZ() + 0.5), Direction.UP, llllllllllllllllllIlIlllIlllIlIl, true));
            llllllllllllllllllIlIlllIlllIllI.mc.player.inventory.selectedSlot = llllllllllllllllllIlIlllIllllIll;
        }
    }

    private boolean getDamageBreak(BlockPos llllllllllllllllllIlIllllIIIllll) {
        AnchorAura llllllllllllllllllIlIllllIIlIIII;
        return llllllllllllllllllIlIllllIIlIIII.breakMode.get() == Safety.Suicide || DamageCalcUtils.anchorDamage((LivingEntity)llllllllllllllllllIlIllllIIlIIII.mc.player, Utils.vec3d(llllllllllllllllllIlIllllIIIllll.add(0.5, 0.5, 0.5))) <= llllllllllllllllllIlIllllIIlIIII.maxDamage.get();
    }

    public AnchorAura() {
        super(Categories.Combat, "anchor-aura", "Automatically places and breaks Respawn Anchors to harm entities.");
        AnchorAura llllllllllllllllllIlIlllllIIIIlI;
        llllllllllllllllllIlIlllllIIIIlI.sgPlace = llllllllllllllllllIlIlllllIIIIlI.settings.createGroup("Place");
        llllllllllllllllllIlIlllllIIIIlI.sgBreak = llllllllllllllllllIlIlllllIIIIlI.settings.createGroup("Break");
        llllllllllllllllllIlIlllllIIIIlI.sgPause = llllllllllllllllllIlIlllllIIIIlI.settings.createGroup("Pause");
        llllllllllllllllllIlIlllllIIIIlI.sgMisc = llllllllllllllllllIlIlllllIIIIlI.settings.createGroup("Misc");
        llllllllllllllllllIlIlllllIIIIlI.sgRender = llllllllllllllllllIlIlllllIIIIlI.settings.createGroup("Render");
        llllllllllllllllllIlIlllllIIIIlI.placeDelay = llllllllllllllllllIlIlllllIIIIlI.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The tick delay between placing anchors.").defaultValue(2).min(0).max(20).build());
        llllllllllllllllllIlIlllllIIIIlI.placeMode = llllllllllllllllllIlIlllllIIIIlI.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The way anchors are allowed to be placed near you.").defaultValue(Safety.Safe).build());
        llllllllllllllllllIlIlllllIIIIlI.placeRange = llllllllllllllllllIlIlllllIIIIlI.sgPlace.add(new DoubleSetting.Builder().name("place-range").description("The radius in which anchors are placed in.").defaultValue(3.0).min(0.0).sliderMax(5.0).build());
        llllllllllllllllllIlIlllllIIIIlI.placePositions = llllllllllllllllllIlIlllllIIIIlI.sgPlace.add(new EnumSetting.Builder().name("placement-positions").description("Where the Anchors will be placed on the entity.").defaultValue(PlaceMode.AboveAndBelow).build());
        llllllllllllllllllIlIlllllIIIIlI.place = llllllllllllllllllIlIlllllIIIIlI.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Anchor Aura to place anchors.").defaultValue(true).build());
        llllllllllllllllllIlIlllllIIIIlI.breakDelay = llllllllllllllllllIlIlllllIIIIlI.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The tick delay between breaking anchors.").defaultValue(10).min(0).max(10).build());
        llllllllllllllllllIlIlllllIIIIlI.breakMode = llllllllllllllllllIlIlllllIIIIlI.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The way anchors are allowed to be broken near you.").defaultValue(Safety.Safe).build());
        llllllllllllllllllIlIlllllIIIIlI.breakRange = llllllllllllllllllIlIlllllIIIIlI.sgBreak.add(new DoubleSetting.Builder().name("break-range").description("The radius in which anchors are broken in.").defaultValue(3.0).min(0.0).sliderMax(5.0).build());
        llllllllllllllllllIlIlllllIIIIlI.pauseOnEat = llllllllllllllllllIlIlllllIIIIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
        llllllllllllllllllIlIlllllIIIIlI.pauseOnDrink = llllllllllllllllllIlIlllllIIIIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
        llllllllllllllllllIlIlllllIIIIlI.pauseOnMine = llllllllllllllllllIlIlllllIIIIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
        llllllllllllllllllIlIlllllIIIIlI.rotationMode = llllllllllllllllllIlIlllllIIIIlI.sgMisc.add(new EnumSetting.Builder().name("rotation-mode").description("The mode to rotate you server-side.").defaultValue(RotationMode.Both).build());
        llllllllllllllllllIlIlllllIIIIlI.targetRange = llllllllllllllllllIlIlllllIIIIlI.sgMisc.add(new DoubleSetting.Builder().name("target-range").description("The radius in which players get targeted.").defaultValue(4.0).min(0.0).sliderMax(5.0).build());
        llllllllllllllllllIlIlllllIIIIlI.priority = llllllllllllllllllIlIlllllIIIIlI.sgMisc.add(new EnumSetting.Builder().name("priority").description("How to select the player to target.").defaultValue(SortPriority.LowestHealth).build());
        llllllllllllllllllIlIlllllIIIIlI.maxDamage = llllllllllllllllllIlIlllllIIIIlI.sgMisc.add(new DoubleSetting.Builder().name("max-self-damage").description("The maximum self-damage allowed.").defaultValue(8.0).build());
        llllllllllllllllllIlIlllllIIIIlI.minHealth = llllllllllllllllllIlIlllllIIIIlI.sgMisc.add(new DoubleSetting.Builder().name("min-health").description("The minimum health you have to be for Anchor Aura to work.").defaultValue(15.0).build());
        llllllllllllllllllIlIlllllIIIIlI.renderPlace = llllllllllllllllllIlIlllllIIIIlI.sgRender.add(new BoolSetting.Builder().name("render-place").description("Renders the block where it is placing an anchor.").defaultValue(true).build());
        llllllllllllllllllIlIlllllIIIIlI.sideColor = llllllllllllllllllIlIlllllIIIIlI.sgRender.add(new ColorSetting.Builder().name("place-side-color").description("The side color for positions to be placed.").defaultValue(new SettingColor(255, 0, 0, 75)).build());
        llllllllllllllllllIlIlllllIIIIlI.lineColor = llllllllllllllllllIlIlllllIIIIlI.sgRender.add(new ColorSetting.Builder().name("place-line-color").description("The line color for positions to be placed.").defaultValue(new SettingColor(255, 0, 0, 255)).build());
        llllllllllllllllllIlIlllllIIIIlI.renderBreak = llllllllllllllllllIlIlllllIIIIlI.sgRender.add(new BoolSetting.Builder().name("render-break").description("Renders the block where it is breaking an anchor.").defaultValue(true).build());
        llllllllllllllllllIlIlllllIIIIlI.breakSideColor = llllllllllllllllllIlIlllllIIIIlI.sgRender.add(new ColorSetting.Builder().name("break-side-color").description("The side color for anchors to be broken.").defaultValue(new SettingColor(255, 0, 0, 75)).build());
        llllllllllllllllllIlIlllllIIIIlI.breakLineColor = llllllllllllllllllIlIlllllIIIIlI.sgRender.add(new ColorSetting.Builder().name("break-line-color").description("The line color for anchors to be broken.").defaultValue(new SettingColor(255, 0, 0, 255)).build());
        llllllllllllllllllIlIlllllIIIIlI.shapeMode = llllllllllllllllllIlIlllllIIIIlI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    }

    private boolean isValidPlace(BlockPos llllllllllllllllllIlIllllIIIlIIl) {
        AnchorAura llllllllllllllllllIlIllllIIIlIII;
        return llllllllllllllllllIlIllllIIIlIII.mc.world.getBlockState(llllllllllllllllllIlIllllIIIlIIl).isAir() && Math.sqrt(llllllllllllllllllIlIllllIIIlIII.mc.player.getBlockPos().getSquaredDistance((Vec3i)llllllllllllllllllIlIllllIIIlIIl)) <= llllllllllllllllllIlIllllIIIlIII.placeRange.get() && llllllllllllllllllIlIllllIIIlIII.getDamagePlace(llllllllllllllllllIlIllllIIIlIIl);
    }

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllllIlIllllIllIIll) {
        BlockPos llllllllllllllllllIlIllllIllIlIl;
        BlockPos llllllllllllllllllIlIllllIllIllI;
        AnchorAura llllllllllllllllllIlIllllIllIlII;
        if (llllllllllllllllllIlIllllIllIlII.mc.world.getDimension().isRespawnAnchorWorking()) {
            ChatUtils.moduleError(llllllllllllllllllIlIllllIllIlII, "You are in the Nether... disabling.", new Object[0]);
            llllllllllllllllllIlIllllIllIlII.toggle();
            return;
        }
        if (PlayerUtils.shouldPause(llllllllllllllllllIlIllllIllIlII.pauseOnMine.get(), llllllllllllllllllIlIllllIllIlII.pauseOnEat.get(), llllllllllllllllllIlIllllIllIlII.pauseOnDrink.get())) {
            return;
        }
        if ((double)EntityUtils.getTotalHealth((PlayerEntity)llllllllllllllllllIlIllllIllIlII.mc.player) <= llllllllllllllllllIlIllllIllIlII.minHealth.get()) {
            return;
        }
        if (EntityUtils.isInvalid(llllllllllllllllllIlIllllIllIlII.target, llllllllllllllllllIlIllllIllIlII.targetRange.get())) {
            llllllllllllllllllIlIllllIllIlII.target = EntityUtils.getPlayerTarget(llllllllllllllllllIlIllllIllIlII.targetRange.get(), llllllllllllllllllIlIllllIllIlII.priority.get(), false);
        }
        if (llllllllllllllllllIlIllllIllIlII.target == null) {
            return;
        }
        int llllllllllllllllllIlIllllIllIIlI = InvUtils.findItemInHotbar(Items.RESPAWN_ANCHOR);
        int llllllllllllllllllIlIllllIllIIIl = InvUtils.findItemInHotbar(Items.GLOWSTONE);
        if (llllllllllllllllllIlIllllIllIIlI == -1 || llllllllllllllllllIlIllllIllIIIl == -1) {
            return;
        }
        if (llllllllllllllllllIlIllllIllIlII.breakDelayLeft >= llllllllllllllllllIlIllllIllIlII.breakDelay.get() && (llllllllllllllllllIlIllllIllIllI = llllllllllllllllllIlIllllIllIlII.findBreakPos(llllllllllllllllllIlIllllIllIlII.target.getBlockPos())) != null) {
            llllllllllllllllllIlIllllIllIlII.mc.player.setSneaking(false);
            llllllllllllllllllIlIllllIllIlII.mc.options.keySneak.setPressed(false);
            if (llllllllllllllllllIlIllllIllIlII.rotationMode.get() == RotationMode.Both || llllllllllllllllllIlIllllIllIlII.rotationMode.get() == RotationMode.Break) {
                Rotations.rotate(Rotations.getYaw(llllllllllllllllllIlIllllIllIllI), Rotations.getPitch(llllllllllllllllllIlIllllIllIllI), 50, () -> {
                    AnchorAura llllllllllllllllllIlIlllIllIIllI;
                    llllllllllllllllllIlIlllIllIIllI.breakAnchor(llllllllllllllllllIlIllllIllIllI, llllllllllllllllllIlIllllIllIIIl, llllllllllllllllllIlIllllIllIIlI);
                });
            } else {
                llllllllllllllllllIlIllllIllIlII.breakAnchor(llllllllllllllllllIlIllllIllIllI, llllllllllllllllllIlIllllIllIIIl, llllllllllllllllllIlIllllIllIIlI);
            }
            llllllllllllllllllIlIllllIllIlII.breakDelayLeft = 0;
        }
        if (llllllllllllllllllIlIllllIllIlII.placeDelayLeft >= llllllllllllllllllIlIllllIllIlII.placeDelay.get() && llllllllllllllllllIlIllllIllIlII.place.get().booleanValue() && (llllllllllllllllllIlIllllIllIlIl = llllllllllllllllllIlIllllIllIlII.findPlacePos(llllllllllllllllllIlIllllIllIlII.target.getBlockPos())) != null) {
            llllllllllllllllllIlIllllIllIlII.mc.player.setSneaking(false);
            llllllllllllllllllIlIllllIllIlII.mc.options.keySneak.setPressed(false);
            BlockUtils.place(llllllllllllllllllIlIllllIllIlIl, Hand.MAIN_HAND, llllllllllllllllllIlIllllIllIIlI, llllllllllllllllllIlIllllIllIlII.rotationMode.get() == RotationMode.Place || llllllllllllllllllIlIllllIllIlII.rotationMode.get() == RotationMode.Both, 50, false);
            llllllllllllllllllIlIllllIllIlII.placeDelayLeft = 0;
        }
        ++llllllllllllllllllIlIllllIllIlII.placeDelayLeft;
        ++llllllllllllllllllIlIllllIllIlII.breakDelayLeft;
    }

    @Override
    public String getInfoString() {
        AnchorAura llllllllllllllllllIlIlllIllIllll;
        if (llllllllllllllllllIlIlllIllIllll.target != null) {
            return llllllllllllllllllIlIlllIllIllll.target.getEntityName();
        }
        return null;
    }

    private BlockPos findBreakPos(BlockPos llllllllllllllllllIlIllllIIllIll) {
        AnchorAura llllllllllllllllllIlIllllIIllIlI;
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.down())) {
            return llllllllllllllllllIlIllllIIllIll.down();
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.up(2))) {
            return llllllllllllllllllIlIllllIIllIll.up(2);
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.add(1, 0, 0))) {
            return llllllllllllllllllIlIllllIIllIll.add(1, 0, 0);
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.add(-1, 0, 0))) {
            return llllllllllllllllllIlIllllIIllIll.add(-1, 0, 0);
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.add(0, 0, 1))) {
            return llllllllllllllllllIlIllllIIllIll.add(0, 0, 1);
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.add(0, 0, -1))) {
            return llllllllllllllllllIlIllllIIllIll.add(0, 0, -1);
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.add(1, 1, 0))) {
            return llllllllllllllllllIlIllllIIllIll.add(1, 1, 0);
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.add(-1, -1, 0))) {
            return llllllllllllllllllIlIllllIIllIll.add(-1, -1, 0);
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.add(0, 1, 1))) {
            return llllllllllllllllllIlIllllIIllIll.add(0, 1, 1);
        }
        if (llllllllllllllllllIlIllllIIllIlI.isValidBreak(llllllllllllllllllIlIllllIIllIll.add(0, 0, -1))) {
            return llllllllllllllllllIlIllllIIllIll.add(0, 0, -1);
        }
        return null;
    }

    @Override
    public void onActivate() {
        llllllllllllllllllIlIllllIllllll.placeDelayLeft = 0;
        llllllllllllllllllIlIllllIllllll.breakDelayLeft = 0;
        llllllllllllllllllIlIllllIllllll.target = null;
    }

    @EventHandler
    private void onRender(RenderEvent llllllllllllllllllIlIllllIlIIlll) {
        AnchorAura llllllllllllllllllIlIllllIlIlIII;
        if (llllllllllllllllllIlIllllIlIlIII.target != null) {
            BlockPos llllllllllllllllllIlIllllIlIlIIl;
            BlockPos llllllllllllllllllIlIllllIlIlIlI;
            if (llllllllllllllllllIlIllllIlIlIII.renderPlace.get().booleanValue() && (llllllllllllllllllIlIllllIlIlIlI = llllllllllllllllllIlIllllIlIlIII.findPlacePos(llllllllllllllllllIlIllllIlIlIII.target.getBlockPos())) != null) {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllllllllllllllllIlIllllIlIlIlI.getX(), llllllllllllllllllIlIllllIlIlIlI.getY(), llllllllllllllllllIlIllllIlIlIlI.getZ(), 1.0, llllllllllllllllllIlIllllIlIlIII.sideColor.get(), llllllllllllllllllIlIllllIlIlIII.lineColor.get(), llllllllllllllllllIlIllllIlIlIII.shapeMode.get(), 0);
            }
            if (llllllllllllllllllIlIllllIlIlIII.renderBreak.get().booleanValue() && (llllllllllllllllllIlIllllIlIlIIl = llllllllllllllllllIlIllllIlIlIII.findBreakPos(llllllllllllllllllIlIllllIlIlIII.target.getBlockPos())) != null) {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllllllllllllllllIlIllllIlIlIIl.getX(), llllllllllllllllllIlIllllIlIlIIl.getY(), llllllllllllllllllIlIllllIlIlIIl.getZ(), 1.0, llllllllllllllllllIlIllllIlIlIII.breakSideColor.get(), llllllllllllllllllIlIllllIlIlIII.breakLineColor.get(), llllllllllllllllllIlIllllIlIlIII.shapeMode.get(), 0);
            }
        }
    }

    private boolean getDamagePlace(BlockPos llllllllllllllllllIlIllllIIlIlIl) {
        AnchorAura llllllllllllllllllIlIllllIIlIlII;
        return llllllllllllllllllIlIllllIIlIlII.placeMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)llllllllllllllllllIlIllllIIlIlII.mc.player, Utils.vec3d(llllllllllllllllllIlIllllIIlIlIl.add(0.5, 0.5, 0.5))) <= llllllllllllllllllIlIllllIIlIlII.maxDamage.get();
    }

    public static final class PlaceMode
    extends Enum<PlaceMode> {
        public static final /* synthetic */ /* enum */ PlaceMode All;
        public static final /* synthetic */ /* enum */ PlaceMode Above;
        public static final /* synthetic */ /* enum */ PlaceMode AboveAndBelow;
        private static final /* synthetic */ PlaceMode[] $VALUES;

        public static PlaceMode valueOf(String lllllllllllllllllIllIllIIIllIllI) {
            return Enum.valueOf(PlaceMode.class, lllllllllllllllllIllIllIIIllIllI);
        }

        public static PlaceMode[] values() {
            return (PlaceMode[])$VALUES.clone();
        }

        private PlaceMode() {
            PlaceMode lllllllllllllllllIllIllIIIllIIIl;
        }

        private static /* synthetic */ PlaceMode[] $values() {
            return new PlaceMode[]{Above, AboveAndBelow, All};
        }

        static {
            Above = new PlaceMode();
            AboveAndBelow = new PlaceMode();
            All = new PlaceMode();
            $VALUES = PlaceMode.$values();
        }
    }

    public static final class RotationMode
    extends Enum<RotationMode> {
        public static final /* synthetic */ /* enum */ RotationMode Both;
        private static final /* synthetic */ RotationMode[] $VALUES;
        public static final /* synthetic */ /* enum */ RotationMode None;
        public static final /* synthetic */ /* enum */ RotationMode Break;
        public static final /* synthetic */ /* enum */ RotationMode Place;

        private RotationMode() {
            RotationMode llllllllllllllllllIlIlIlllllIIII;
        }

        public static RotationMode valueOf(String llllllllllllllllllIlIlIlllllIlII) {
            return Enum.valueOf(RotationMode.class, llllllllllllllllllIlIlIlllllIlII);
        }

        private static /* synthetic */ RotationMode[] $values() {
            return new RotationMode[]{Place, Break, Both, None};
        }

        public static RotationMode[] values() {
            return (RotationMode[])$VALUES.clone();
        }

        static {
            Place = new RotationMode();
            Break = new RotationMode();
            Both = new RotationMode();
            None = new RotationMode();
            $VALUES = RotationMode.$values();
        }
    }
}

