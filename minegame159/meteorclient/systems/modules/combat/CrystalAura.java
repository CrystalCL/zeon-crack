/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Streams
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.AxeItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.PotionItem
 *  net.minecraft.item.SwordItem
 *  net.minecraft.world.World
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Box
 *  net.minecraft.util.math.Box2
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
 *  net.minecraft.sound.SoundCategory
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$class_242
 *  net.minecraft.world.RaycastContext$class_3960
 *  net.minecraft.util.hit.BlockHitResult
 */
package minegame159.meteorclient.systems.modules.combat;

import com.google.common.collect.Streams;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.EntityRemovedEvent;
import minegame159.meteorclient.events.entity.player.SendMovementPacketsEvent;
import minegame159.meteorclient.events.render.Render2DEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.PlaySoundEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.misc.Vec3;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.RotationUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.NametagUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Hand;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Box2;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;

public class CrystalAura
extends Module {
    private final /* synthetic */ Setting<Double> facePlaceDurability;
    private final /* synthetic */ Setting<Integer> supportDelay;
    private final /* synthetic */ Setting<Boolean> surroundHold;
    private final /* synthetic */ Setting<Integer> renderTimer;
    private final /* synthetic */ List<RenderBlock> renderBlocks;
    private /* synthetic */ int preSlot;
    private final /* synthetic */ Setting<Integer> numberOfDamages;
    private final /* synthetic */ Setting<Boolean> swing;
    private /* synthetic */ boolean locked;
    private /* synthetic */ LivingEntity target;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<Boolean> spamFacePlace;
    private final /* synthetic */ Setting<Boolean> facePlace;
    private final /* synthetic */ SettingGroup sgMisc;
    private final /* synthetic */ Setting<Boolean> surroundBreak;
    private final /* synthetic */ Setting<RotationMode> rotationMode;
    private final /* synthetic */ Setting<Integer> breakDelay;
    private final /* synthetic */ SettingGroup sgPlace;
    private final /* synthetic */ Setting<Integer> roundDamage;
    private final /* synthetic */ Setting<SwitchMode> switchMode;
    private final /* synthetic */ Setting<Mode> breakMode;
    private final /* synthetic */ List<Integer> removalQueue;
    private final /* synthetic */ SettingGroup sgRotations;
    private final /* synthetic */ Setting<Double> breakRange;
    private final /* synthetic */ Map<EndCrystalEntity, List<Double>> crystalMap;
    private final /* synthetic */ Setting<Double> minDamage;
    private final /* synthetic */ Setting<Double> facePlaceHealth;
    private /* synthetic */ int placeDelayLeft;
    private final /* synthetic */ Setting<Double> placeWallsRange;
    private final /* synthetic */ Setting<Boolean> resetRotations;
    private /* synthetic */ boolean canSupport;
    private final /* synthetic */ SettingGroup sgTarget;
    private /* synthetic */ int supportDelayLeft;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Boolean> rayTrace;
    private final /* synthetic */ Setting<Boolean> renderDamage;
    private final /* synthetic */ Setting<Boolean> pauseOnDrink;
    private final /* synthetic */ SettingGroup sgBreak;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private /* synthetic */ boolean broken;
    private final /* synthetic */ Setting<Double> healthDifference;
    private /* synthetic */ double bestDamage;
    private final /* synthetic */ Setting<Boolean> oldPlace;
    private /* synthetic */ EndCrystalEntity bestBreak;
    private final /* synthetic */ Setting<Boolean> antiWeakness;
    private final /* synthetic */ Setting<Boolean> strictLook;
    private final /* synthetic */ Setting<Double> placeRange;
    private final /* synthetic */ Setting<Boolean> ignoreWalls;
    private final /* synthetic */ Setting<Double> verticalRange;
    private final /* synthetic */ SettingGroup sgPause;
    private /* synthetic */ int breakDelayLeft;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<Double> damageScale;
    private final /* synthetic */ Setting<Double> targetRange;
    private final /* synthetic */ Setting<Integer> placeDelay;
    private static final /* synthetic */ Vec3 pos;
    private final /* synthetic */ Setting<Boolean> multiPlace;
    private final /* synthetic */ Setting<Boolean> support;
    private final /* synthetic */ Setting<SettingColor> damageColor;
    private final /* synthetic */ Setting<Boolean> pauseOnEat;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Boolean> smartDelay;
    private final /* synthetic */ Setting<Boolean> switchBack;
    private final /* synthetic */ Setting<Boolean> multiTarget;
    private final /* synthetic */ Setting<TargetMode> targetMode;
    private final /* synthetic */ Pool<RenderBlock> renderBlockPool;
    private /* synthetic */ int supportSlot;
    private /* synthetic */ double lastDamage;
    private final /* synthetic */ Setting<Boolean> place;
    private final /* synthetic */ Setting<CancelCrystalMode> cancelCrystalMode;
    private /* synthetic */ Vec3d bestBlock;
    private final /* synthetic */ Setting<Boolean> supportBackup;
    private final /* synthetic */ Setting<Boolean> pauseOnMine;
    private /* synthetic */ EndCrystalEntity heldCrystal;
    private final /* synthetic */ Setting<Double> maxDamage;
    private final /* synthetic */ List<Double> crystalList;
    private final /* synthetic */ Setting<Mode> placeMode;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Double> minHealth;

    private void hitCrystal(EndCrystalEntity lIlllIIllIllIIl) {
        CrystalAura lIlllIIllIllIlI;
        assert (lIlllIIllIllIlI.mc.player != null);
        assert (lIlllIIllIllIlI.mc.world != null);
        assert (lIlllIIllIllIlI.mc.interactionManager != null);
        int lIlllIIllIllIll = lIlllIIllIllIlI.mc.player.inventory.selectedSlot;
        if (lIlllIIllIllIlI.mc.player.getActiveStatusEffects().containsKey((Object)StatusEffects.WEAKNESS) && lIlllIIllIllIlI.antiWeakness.get().booleanValue()) {
            for (int lIlllIIllIlllll = 0; lIlllIIllIlllll < 9; ++lIlllIIllIlllll) {
                if (!(lIlllIIllIllIlI.mc.player.inventory.getStack(lIlllIIllIlllll).getItem() instanceof SwordItem) && !(lIlllIIllIllIlI.mc.player.inventory.getStack(lIlllIIllIlllll).getItem() instanceof AxeItem)) continue;
                lIlllIIllIllIlI.mc.player.inventory.selectedSlot = lIlllIIllIlllll;
                break;
            }
        }
        if (lIlllIIllIllIlI.rotationMode.get() == RotationMode.Break || lIlllIIllIllIlI.rotationMode.get() == RotationMode.Both) {
            float[] lIlllIIllIllllI = PlayerUtils.calculateAngle(lIlllIIllIllIIl.getPos());
            Rotations.rotate(lIlllIIllIllllI[0], lIlllIIllIllllI[1], 30, () -> {
                CrystalAura lIllIllllIlllIl;
                lIllIllllIlllIl.attackCrystal(lIlllIIllIllIIl, lIlllIIllIllIll);
            });
        } else {
            lIlllIIllIllIlI.attackCrystal(lIlllIIllIllIIl, lIlllIIllIllIll);
        }
        lIlllIIllIllIlI.broken = true;
        lIlllIIllIllIlI.breakDelayLeft = lIlllIIllIllIlI.breakDelay.get();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean getDamagePlace(BlockPos lIlllIIIllllIll) {
        CrystalAura lIlllIIIlllllII;
        assert (lIlllIIIlllllII.mc.player != null);
        if (lIlllIIIlllllII.placeMode.get() == Mode.Suicide) return true;
        Vec3d Vec3d2 = new Vec3d((double)lIlllIIIllllIll.getX() + 0.5, (double)lIlllIIIllllIll.getY(), (double)lIlllIIIllllIll.getZ() + 0.5);
        if (!(DamageCalcUtils.crystalDamage((LivingEntity)lIlllIIIlllllII.mc.player, Vec3d2) <= lIlllIIIlllllII.maxDamage.get())) return false;
        Vec3d Vec3d3 = new Vec3d((double)lIlllIIIllllIll.getX() + 0.5, (double)lIlllIIIllllIll.getY(), (double)lIlllIIIllllIll.getZ() + 0.5);
        if (!((double)lIlllIIIlllllII.getTotalHealth((PlayerEntity)lIlllIIIlllllII.mc.player) - DamageCalcUtils.crystalDamage((LivingEntity)lIlllIIIlllllII.mc.player, Vec3d3) >= lIlllIIIlllllII.minHealth.get())) return false;
        return true;
    }

    public CrystalAura() {
        super(Categories.Combat, "crystal-aura", "Automatically places and breaks crystals to damage other players.");
        CrystalAura lIlllIlIlIIlIlI;
        lIlllIlIlIIlIlI.sgPlace = lIlllIlIlIIlIlI.settings.createGroup("Place");
        lIlllIlIlIIlIlI.sgBreak = lIlllIlIlIIlIlI.settings.createGroup("Break");
        lIlllIlIlIIlIlI.sgTarget = lIlllIlIlIIlIlI.settings.createGroup("Target");
        lIlllIlIlIIlIlI.sgPause = lIlllIlIlIIlIlI.settings.createGroup("Pause");
        lIlllIlIlIIlIlI.sgRotations = lIlllIlIlIIlIlI.settings.createGroup("Rotations");
        lIlllIlIlIIlIlI.sgMisc = lIlllIlIlIIlIlI.settings.createGroup("Misc");
        lIlllIlIlIIlIlI.sgRender = lIlllIlIlIIlIlI.settings.createGroup("Render");
        lIlllIlIlIIlIlI.placeDelay = lIlllIlIlIIlIlI.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The amount of delay in ticks before placing.").defaultValue(2).min(0).sliderMax(10).build());
        lIlllIlIlIIlIlI.placeMode = lIlllIlIlIIlIlI.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The placement mode for crystals.").defaultValue(Mode.Safe).build());
        lIlllIlIlIIlIlI.placeRange = lIlllIlIlIIlIlI.sgPlace.add(new DoubleSetting.Builder().name("place-range").description("The radius in which crystals can be placed in.").defaultValue(4.5).min(0.0).sliderMax(7.0).build());
        lIlllIlIlIIlIlI.placeWallsRange = lIlllIlIlIIlIlI.sgPlace.add(new DoubleSetting.Builder().name("place-walls-range").description("The radius in which crystals can be placed through walls.").defaultValue(3.0).min(0.0).sliderMax(7.0).build());
        lIlllIlIlIIlIlI.place = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Crystal Aura to place crystals.").defaultValue(true).build());
        lIlllIlIlIIlIlI.multiPlace = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("multi-place").description("Allows Crystal Aura to place multiple crystals.").defaultValue(false).build());
        lIlllIlIlIIlIlI.rayTrace = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("ray-trace").description("Whether or not to place through walls.").defaultValue(false).build());
        lIlllIlIlIIlIlI.minDamage = lIlllIlIlIIlIlI.sgPlace.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage the crystal will place.").defaultValue(5.5).build());
        lIlllIlIlIIlIlI.minHealth = lIlllIlIlIIlIlI.sgPlace.add(new DoubleSetting.Builder().name("min-health").description("The minimum health you have to be for it to place.").defaultValue(15.0).build());
        lIlllIlIlIIlIlI.surroundBreak = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("surround-break").description("Places a crystal next to a surrounded player and keeps it there so they cannot use Surround again.").defaultValue(false).build());
        lIlllIlIlIIlIlI.surroundHold = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("surround-hold").description("Places a crystal next to a player so they cannot use Surround.").defaultValue(false).build());
        lIlllIlIlIIlIlI.oldPlace = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("1.12-place").description("Won't place in one block holes to help compatibility with some servers.").defaultValue(false).build());
        lIlllIlIlIIlIlI.facePlace = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("face-place").description("Will face-place when target is below a certain health or armor durability threshold.").defaultValue(true).build());
        lIlllIlIlIIlIlI.spamFacePlace = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("spam-face-place").description("Places faster when someone is below the face place health (Requires Smart Delay).").defaultValue(false).build());
        lIlllIlIlIIlIlI.facePlaceHealth = lIlllIlIlIIlIlI.sgPlace.add(new DoubleSetting.Builder().name("face-place-health").description("The health required to face-place.").defaultValue(8.0).min(1.0).max(36.0).build());
        lIlllIlIlIIlIlI.facePlaceDurability = lIlllIlIlIIlIlI.sgPlace.add(new DoubleSetting.Builder().name("face-place-durability").description("The durability threshold to be able to face-place.").defaultValue(2.0).min(1.0).max(100.0).sliderMax(100.0).build());
        lIlllIlIlIIlIlI.support = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("support").description("Places a block in the air and crystals on it. Helps with killing players that are flying.").defaultValue(false).build());
        lIlllIlIlIIlIlI.supportDelay = lIlllIlIlIIlIlI.sgPlace.add(new IntSetting.Builder().name("support-delay").description("The delay between support blocks being placed.").defaultValue(5).min(0).sliderMax(10).build());
        lIlllIlIlIIlIlI.supportBackup = lIlllIlIlIIlIlI.sgPlace.add(new BoolSetting.Builder().name("support-backup").description("Makes it so support only works if there are no other options.").defaultValue(true).build());
        lIlllIlIlIIlIlI.breakDelay = lIlllIlIlIIlIlI.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The amount of delay in ticks before breaking.").defaultValue(1).min(0).sliderMax(10).build());
        lIlllIlIlIIlIlI.breakMode = lIlllIlIlIIlIlI.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The type of break mode for crystals.").defaultValue(Mode.Safe).build());
        lIlllIlIlIIlIlI.breakRange = lIlllIlIlIIlIlI.sgBreak.add(new DoubleSetting.Builder().name("break-range").description("The maximum range that crystals can be to be broken.").defaultValue(5.0).min(0.0).sliderMax(7.0).build());
        lIlllIlIlIIlIlI.ignoreWalls = lIlllIlIlIIlIlI.sgBreak.add(new BoolSetting.Builder().name("ray-trace").description("Whether or not to break through walls.").defaultValue(false).build());
        lIlllIlIlIIlIlI.cancelCrystalMode = lIlllIlIlIIlIlI.sgBreak.add(new EnumSetting.Builder().name("cancel-crystal").description("Mode to use for the crystals to be removed from the world.").defaultValue(CancelCrystalMode.Hit).build());
        lIlllIlIlIIlIlI.entities = lIlllIlIlIIlIlI.sgTarget.add(new EntityTypeListSetting.Builder().name("entities").description("The entities to attack.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PLAYER})).onlyAttackable().build());
        lIlllIlIlIIlIlI.targetRange = lIlllIlIlIIlIlI.sgTarget.add(new DoubleSetting.Builder().name("target-range").description("The maximum range the entity can be to be targeted.").defaultValue(7.0).min(0.0).sliderMax(10.0).build());
        lIlllIlIlIIlIlI.targetMode = lIlllIlIlIIlIlI.sgTarget.add(new EnumSetting.Builder().name("target-mode").description("The way you target multiple targets.").defaultValue(TargetMode.HighestXDamages).build());
        lIlllIlIlIIlIlI.numberOfDamages = lIlllIlIlIIlIlI.sgTarget.add(new IntSetting.Builder().name("number-of-damages").description("The number to replace 'x' with in HighestXDamages.").defaultValue(3).min(2).sliderMax(10).build());
        lIlllIlIlIIlIlI.multiTarget = lIlllIlIlIIlIlI.sgTarget.add(new BoolSetting.Builder().name("multi-targeting").description("Will calculate damage for all entities and pick a block based on target mode.").defaultValue(false).build());
        lIlllIlIlIIlIlI.pauseOnEat = lIlllIlIlIIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses Crystal Aura while eating.").defaultValue(false).build());
        lIlllIlIlIIlIlI.pauseOnDrink = lIlllIlIlIIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses Crystal Aura while drinking a potion.").defaultValue(false).build());
        lIlllIlIlIIlIlI.pauseOnMine = lIlllIlIlIIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses Crystal Aura while mining blocks.").defaultValue(false).build());
        lIlllIlIlIIlIlI.rotationMode = lIlllIlIlIIlIlI.sgRotations.add(new EnumSetting.Builder().name("rotation-mode").description("The method of rotating when using Crystal Aura.").defaultValue(RotationMode.Place).build());
        lIlllIlIlIIlIlI.strictLook = lIlllIlIlIIlIlI.sgRotations.add(new BoolSetting.Builder().name("strict-look").description("Looks at exactly where you're placing.").defaultValue(true).build());
        lIlllIlIlIIlIlI.resetRotations = lIlllIlIlIIlIlI.sgRotations.add(new BoolSetting.Builder().name("reset-rotations").description("Resets rotations once Crystal Aura is disabled.").defaultValue(false).build());
        lIlllIlIlIIlIlI.switchMode = lIlllIlIlIIlIlI.sgMisc.add(new EnumSetting.Builder().name("switch-mode").description("How to switch items.").defaultValue(SwitchMode.Auto).build());
        lIlllIlIlIIlIlI.switchBack = lIlllIlIlIIlIlI.sgMisc.add(new BoolSetting.Builder().name("switch-back").description("Switches back to your previous slot when disabling Crystal Aura.").defaultValue(true).build());
        lIlllIlIlIIlIlI.verticalRange = lIlllIlIlIIlIlI.sgMisc.add(new DoubleSetting.Builder().name("vertical-range").description("The maximum vertical range for placing/breaking end crystals. May kill performance if this value is higher than 3.").min(0.0).defaultValue(3.0).max(7.0).build());
        lIlllIlIlIIlIlI.maxDamage = lIlllIlIlIIlIlI.sgMisc.add(new DoubleSetting.Builder().name("max-damage").description("The maximum self-damage allowed.").defaultValue(3.0).build());
        lIlllIlIlIIlIlI.smartDelay = lIlllIlIlIIlIlI.sgMisc.add(new BoolSetting.Builder().name("smart-delay").description("Reduces crystal consumption when doing large amounts of damage. (Can tank performance on lower-end PCs).").defaultValue(false).build());
        lIlllIlIlIIlIlI.healthDifference = lIlllIlIlIIlIlI.sgMisc.add(new DoubleSetting.Builder().name("damage-increase").description("The damage increase for smart delay to work.").defaultValue(5.0).min(0.0).max(20.0).build());
        lIlllIlIlIIlIlI.antiWeakness = lIlllIlIlIIlIlI.sgMisc.add(new BoolSetting.Builder().name("anti-weakness").description("Switches to tools to break crystals instead of your fist.").defaultValue(true).build());
        lIlllIlIlIIlIlI.swing = lIlllIlIlIIlIlI.sgRender.add(new BoolSetting.Builder().name("swing").description("Renders your swing client-side.").defaultValue(true).build());
        lIlllIlIlIIlIlI.render = lIlllIlIlIIlIlI.sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block under where it is placing a crystal.").defaultValue(true).build());
        lIlllIlIlIIlIlI.shapeMode = lIlllIlIlIIlIlI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
        lIlllIlIlIIlIlI.sideColor = lIlllIlIlIIlIlI.sgRender.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 255, 255, 75)).build());
        lIlllIlIlIIlIlI.lineColor = lIlllIlIlIIlIlI.sgRender.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        lIlllIlIlIIlIlI.renderDamage = lIlllIlIlIIlIlI.sgRender.add(new BoolSetting.Builder().name("render-damage").description("Renders the damage of the crystal where it is placing.").defaultValue(true).build());
        lIlllIlIlIIlIlI.roundDamage = lIlllIlIlIIlIlI.sgRender.add(new IntSetting.Builder().name("round-damage").description("Round damage to x decimal places.").defaultValue(2).min(0).max(3).sliderMax(3).build());
        lIlllIlIlIIlIlI.damageScale = lIlllIlIlIIlIlI.sgRender.add(new DoubleSetting.Builder().name("damage-scale").description("The scale of the damage text.").defaultValue(1.4).min(0.0).sliderMax(5.0).build());
        lIlllIlIlIIlIlI.damageColor = lIlllIlIlIIlIlI.sgRender.add(new ColorSetting.Builder().name("damage-color").description("The color of the damage text.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        lIlllIlIlIIlIlI.renderTimer = lIlllIlIlIIlIlI.sgRender.add(new IntSetting.Builder().name("timer").description("The amount of time between changing the block render.").defaultValue(0).min(0).sliderMax(10).build());
        lIlllIlIlIIlIlI.placeDelayLeft = lIlllIlIlIIlIlI.placeDelay.get();
        lIlllIlIlIIlIlI.breakDelayLeft = lIlllIlIlIIlIlI.breakDelay.get();
        lIlllIlIlIIlIlI.bestDamage = 0.0;
        lIlllIlIlIIlIlI.lastDamage = 0.0;
        lIlllIlIlIIlIlI.heldCrystal = null;
        lIlllIlIlIIlIlI.locked = false;
        lIlllIlIlIIlIlI.supportSlot = 0;
        lIlllIlIlIIlIlI.supportDelayLeft = lIlllIlIlIIlIlI.supportDelay.get();
        lIlllIlIlIIlIlI.crystalMap = new HashMap<EndCrystalEntity, List<Double>>();
        lIlllIlIlIIlIlI.crystalList = new ArrayList<Double>();
        lIlllIlIlIIlIlI.removalQueue = new ArrayList<Integer>();
        lIlllIlIlIIlIlI.bestBreak = null;
        lIlllIlIlIIlIlI.renderBlockPool = new Pool<RenderBlock>(() -> {
            CrystalAura lIllIlllIIllIII;
            return lIllIlllIIllIII.new RenderBlock();
        });
        lIlllIlIlIIlIlI.renderBlocks = new ArrayList<RenderBlock>();
        lIlllIlIlIIlIlI.broken = false;
    }

    @Override
    public String getInfoString() {
        CrystalAura lIlllIIIIIIlllI;
        if (lIlllIIIIIIlllI.target != null && lIlllIIIIIIlllI.target instanceof PlayerEntity) {
            return lIlllIIIIIIlllI.target.getEntityName();
        }
        if (lIlllIIIIIIlllI.target != null) {
            return lIlllIIIIIIlllI.target.getType().getName().getString();
        }
        return null;
    }

    @Override
    public void onActivate() {
        lIlllIlIlIIlIII.preSlot = -1;
        lIlllIlIlIIlIII.placeDelayLeft = 0;
        lIlllIlIlIIlIII.breakDelayLeft = 0;
        lIlllIlIlIIlIII.heldCrystal = null;
        lIlllIlIlIIlIII.locked = false;
        lIlllIlIlIIlIII.broken = false;
    }

    @EventHandler
    private void onRender2D(Render2DEvent lIlllIlIIIIllII) {
        CrystalAura lIlllIlIIIIllIl;
        if (!lIlllIlIIIIllIl.render.get().booleanValue()) {
            return;
        }
        for (RenderBlock lIlllIlIIIIlllI : lIlllIlIIIIllIl.renderBlocks) {
            lIlllIlIIIIlllI.render2D();
        }
    }

    private void doSwitch() {
        int lIlllIIllIIIlIl;
        CrystalAura lIlllIIllIIIIll;
        assert (lIlllIIllIIIIll.mc.player != null);
        if (lIlllIIllIIIIll.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && lIlllIIllIIIIll.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL && (lIlllIIllIIIlIl = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && lIlllIIllIIIlIl < 9) {
            lIlllIIllIIIIll.preSlot = lIlllIIllIIIIll.mc.player.inventory.selectedSlot;
            lIlllIIllIIIIll.mc.player.inventory.selectedSlot = lIlllIIllIIIlIl;
        }
    }

    @EventHandler(priority=100)
    private void onTick(TickEvent.Post lIlllIlIIllIllI) {
        CrystalAura lIlllIlIIllIlIl;
        if (lIlllIlIIllIlIl.cancelCrystalMode.get() == CancelCrystalMode.Hit) {
            lIlllIlIIllIlIl.removalQueue.forEach(lIllIlllIIllIlI -> {
                CrystalAura lIllIlllIIllIll;
                lIllIlllIIllIll.mc.world.removeEntity(lIllIlllIIllIlI.intValue());
            });
            lIlllIlIIllIlIl.removalQueue.clear();
        }
    }

    private boolean isSurrounded(LivingEntity lIlllIIIIIlIlll) {
        CrystalAura lIlllIIIIIllIII;
        assert (lIlllIIIIIllIII.mc.world != null);
        return !lIlllIIIIIllIII.mc.world.getBlockState(lIlllIIIIIlIlll.getBlockPos().add(1, 0, 0)).isAir() && !lIlllIIIIIllIII.mc.world.getBlockState(lIlllIIIIIlIlll.getBlockPos().add(-1, 0, 0)).isAir() && !lIlllIIIIIllIII.mc.world.getBlockState(lIlllIIIIIlIlll.getBlockPos().add(0, 0, 1)).isAir() && !lIlllIIIIIllIII.mc.world.getBlockState(lIlllIIIIIlIlll.getBlockPos().add(0, 0, -1)).isAir();
    }

    private void doHeldCrystal() {
        CrystalAura lIlllIIllIIIIII;
        assert (lIlllIIllIIIIII.mc.player != null);
        if (lIlllIIllIIIIII.switchMode.get() != SwitchMode.None) {
            lIlllIIllIIIIII.doSwitch();
        }
        if (lIlllIIllIIIIII.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && lIlllIIllIIIIII.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        lIlllIIllIIIIII.bestDamage = DamageCalcUtils.crystalDamage(lIlllIIllIIIIII.target, lIlllIIllIIIIII.bestBlock.add(0.0, 1.0, 0.0));
        lIlllIIllIIIIII.heldCrystal = new EndCrystalEntity((World)lIlllIIllIIIIII.mc.world, lIlllIIllIIIIII.bestBlock.x, lIlllIIllIIIIII.bestBlock.y + 1.0, lIlllIIllIIIIII.bestBlock.z);
        lIlllIIllIIIIII.locked = true;
        if (!lIlllIIllIIIIII.smartDelay.get().booleanValue()) {
            lIlllIIllIIIIII.placeDelayLeft = lIlllIIllIIIIII.placeDelay.get();
        } else {
            lIlllIIllIIIIII.lastDamage = lIlllIIllIIIIII.bestDamage;
            if (lIlllIIllIIIIII.placeDelayLeft <= 0) {
                lIlllIIllIIIIII.placeDelayLeft = 10;
            }
        }
        lIlllIIllIIIIII.placeBlock(lIlllIIllIIIIII.bestBlock, lIlllIIllIIIIII.getHand());
    }

    @Override
    public void onDeactivate() {
        CrystalAura lIlllIlIlIIIIIl;
        assert (lIlllIlIlIIIIIl.mc.player != null);
        if (lIlllIlIlIIIIIl.switchBack.get().booleanValue() && lIlllIlIlIIIIIl.preSlot != -1) {
            lIlllIlIlIIIIIl.mc.player.inventory.selectedSlot = lIlllIlIlIIIIIl.preSlot;
        }
        for (RenderBlock lIlllIlIlIIIIll : lIlllIlIlIIIIIl.renderBlocks) {
            lIlllIlIlIIIIIl.renderBlockPool.free(lIlllIlIlIIIIll);
        }
        lIlllIlIlIIIIIl.renderBlocks.clear();
        if (lIlllIlIlIIIIIl.target != null && lIlllIlIlIIIIIl.resetRotations.get().booleanValue() && (lIlllIlIlIIIIIl.rotationMode.get() == RotationMode.Both || lIlllIlIlIIIIIl.rotationMode.get() == RotationMode.Place || lIlllIlIlIIIIIl.rotationMode.get() == RotationMode.Break)) {
            RotationUtils.packetRotate(lIlllIlIlIIIIIl.mc.player.yaw, lIlllIlIlIIIIIl.mc.player.pitch);
        }
    }

    @EventHandler
    private void onEntityRemoved(EntityRemovedEvent lIlllIlIIlllIIl) {
        CrystalAura lIlllIlIIllllII;
        if (lIlllIlIIllllII.heldCrystal == null) {
            return;
        }
        if (lIlllIlIIlllIIl.entity.getBlockPos().equals((Object)lIlllIlIIllllII.heldCrystal.getBlockPos())) {
            lIlllIlIIllllII.heldCrystal = null;
            lIlllIlIIllllII.locked = false;
        }
    }

    private Vec3d findOpenSurround(LivingEntity lIlllIIIllIlIIl) {
        CrystalAura lIlllIIIllIIllI;
        assert (lIlllIIIllIIllI.mc.player != null);
        assert (lIlllIIIllIIllI.mc.world != null);
        int lIlllIIIllIlIII = 0;
        int lIlllIIIllIIlll = 0;
        if (lIlllIIIllIIllI.validSurroundBreak(lIlllIIIllIlIIl, 2, 0)) {
            lIlllIIIllIlIII = 2;
        } else if (lIlllIIIllIIllI.validSurroundBreak(lIlllIIIllIlIIl, -2, 0)) {
            lIlllIIIllIlIII = -2;
        } else if (lIlllIIIllIIllI.validSurroundBreak(lIlllIIIllIlIIl, 0, 2)) {
            lIlllIIIllIIlll = 2;
        } else if (lIlllIIIllIIllI.validSurroundBreak(lIlllIIIllIlIIl, 0, -2)) {
            lIlllIIIllIIlll = -2;
        }
        if (lIlllIIIllIlIII != 0 || lIlllIIIllIIlll != 0) {
            return new Vec3d((double)lIlllIIIllIlIIl.getBlockPos().getX() + 0.5 + (double)lIlllIIIllIlIII, (double)(lIlllIIIllIlIIl.getBlockPos().getY() - 1), (double)lIlllIIIllIlIIl.getBlockPos().getZ() + 0.5 + (double)lIlllIIIllIIlll);
        }
        return null;
    }

    static {
        pos = new Vec3();
    }

    private void attackCrystal(EndCrystalEntity lIlllIIllIlIIlI, int lIlllIIllIlIIIl) {
        CrystalAura lIlllIIllIlIIll;
        lIlllIIllIlIIll.mc.interactionManager.attackEntity((PlayerEntity)lIlllIIllIlIIll.mc.player, (Entity)lIlllIIllIlIIlI);
        lIlllIIllIlIIll.removalQueue.add(lIlllIIllIlIIlI.getEntityId());
        if (lIlllIIllIlIIll.swing.get().booleanValue()) {
            lIlllIIllIlIIll.mc.player.swingHand(lIlllIIllIlIIll.getHand());
        } else {
            lIlllIIllIlIIll.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(lIlllIIllIlIIll.getHand()));
        }
        lIlllIIllIlIIll.mc.player.inventory.selectedSlot = lIlllIIllIlIIIl;
        if (lIlllIIllIlIIll.heldCrystal != null && lIlllIIllIlIIlI.getBlockPos().equals((Object)lIlllIIllIlIIll.heldCrystal.getBlockPos())) {
            lIlllIIllIlIIll.heldCrystal = null;
            lIlllIIllIlIIll.locked = false;
        }
    }

    @EventHandler(priority=100)
    private void onTick(SendMovementPacketsEvent.Pre lIlllIlIIlIIlII) {
        CrystalAura lIlllIlIIlIIlIl;
        Iterator<RenderBlock> lIlllIlIIlIllII = lIlllIlIIlIIlIl.renderBlocks.iterator();
        while (lIlllIlIIlIllII.hasNext()) {
            RenderBlock lIlllIlIIlIllIl = lIlllIlIIlIllII.next();
            if (!lIlllIlIIlIllIl.shouldRemove()) continue;
            lIlllIlIIlIllII.remove();
            lIlllIlIIlIIlIl.renderBlockPool.free(lIlllIlIIlIllIl);
        }
        --lIlllIlIIlIIlIl.placeDelayLeft;
        --lIlllIlIIlIIlIl.breakDelayLeft;
        --lIlllIlIIlIIlIl.supportDelayLeft;
        if (lIlllIlIIlIIlIl.target == null) {
            lIlllIlIIlIIlIl.heldCrystal = null;
            lIlllIlIIlIIlIl.locked = false;
        }
        if (lIlllIlIIlIIlIl.mc.player.isUsingItem() && (lIlllIlIIlIIlIl.mc.player.getMainHandStack().getItem().isFood() || lIlllIlIIlIIlIl.mc.player.getOffHandStack().getItem().isFood()) && lIlllIlIIlIIlIl.pauseOnEat.get().booleanValue() || lIlllIlIIlIIlIl.mc.interactionManager.isBreakingBlock() && lIlllIlIIlIIlIl.pauseOnMine.get().booleanValue() || lIlllIlIIlIIlIl.mc.player.isUsingItem() && (lIlllIlIIlIIlIl.mc.player.getMainHandStack().getItem() instanceof PotionItem || lIlllIlIIlIIlIl.mc.player.getOffHandStack().getItem() instanceof PotionItem) && lIlllIlIIlIIlIl.pauseOnDrink.get().booleanValue()) {
            return;
        }
        if (lIlllIlIIlIIlIl.locked && lIlllIlIIlIIlIl.heldCrystal != null && (!lIlllIlIIlIIlIl.surroundBreak.get().booleanValue() && lIlllIlIIlIIlIl.target.getBlockPos().getSquaredDistance(new Box2(lIlllIlIIlIIlIl.heldCrystal.getX(), lIlllIlIIlIIlIl.heldCrystal.getY(), lIlllIlIIlIIlIl.heldCrystal.getZ())) == 4.0 || !lIlllIlIIlIIlIl.surroundHold.get().booleanValue() && lIlllIlIIlIIlIl.target.getBlockPos().getSquaredDistance(new Box2(lIlllIlIIlIIlIl.heldCrystal.getX(), lIlllIlIIlIIlIl.heldCrystal.getY(), lIlllIlIIlIIlIl.heldCrystal.getZ())) == 2.0)) {
            lIlllIlIIlIIlIl.heldCrystal = null;
            lIlllIlIIlIIlIl.locked = false;
        }
        if (lIlllIlIIlIIlIl.heldCrystal != null && (double)lIlllIlIIlIIlIl.mc.player.distanceTo((Entity)lIlllIlIIlIIlIl.heldCrystal) > lIlllIlIIlIIlIl.breakRange.get()) {
            lIlllIlIIlIIlIl.heldCrystal = null;
            lIlllIlIIlIIlIl.locked = false;
        }
        boolean lIlllIlIIlIIIll = false;
        if (lIlllIlIIlIIlIl.heldCrystal != null) {
            for (Entity lIlllIlIIlIlIll : lIlllIlIIlIIlIl.mc.world.getEntities()) {
                if (!(lIlllIlIIlIlIll instanceof EndCrystalEntity) || lIlllIlIIlIIlIl.heldCrystal == null || !lIlllIlIIlIlIll.getBlockPos().equals((Object)lIlllIlIIlIIlIl.heldCrystal.getBlockPos())) continue;
                lIlllIlIIlIIIll = true;
                break;
            }
            if (!lIlllIlIIlIIIll) {
                lIlllIlIIlIIlIl.heldCrystal = null;
                lIlllIlIIlIIlIl.locked = false;
            }
        }
        boolean lIlllIlIIlIIIlI = false;
        if ((double)lIlllIlIIlIIlIl.getTotalHealth((PlayerEntity)lIlllIlIIlIIlIl.mc.player) <= lIlllIlIIlIIlIl.minHealth.get() && lIlllIlIIlIIlIl.placeMode.get() != Mode.Suicide) {
            return;
        }
        if (lIlllIlIIlIIlIl.target != null && lIlllIlIIlIIlIl.heldCrystal != null && lIlllIlIIlIIlIl.placeDelayLeft <= 0 && lIlllIlIIlIIlIl.mc.world.raycast(new RaycastContext(lIlllIlIIlIIlIl.target.getPos(), lIlllIlIIlIIlIl.heldCrystal.getPos(), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)lIlllIlIIlIIlIl.target)).getType() == Type.MISS) {
            lIlllIlIIlIIlIl.locked = false;
        }
        if (lIlllIlIIlIIlIl.heldCrystal == null) {
            lIlllIlIIlIIlIl.locked = false;
        }
        if (lIlllIlIIlIIlIl.locked && !lIlllIlIIlIIlIl.facePlace.get().booleanValue()) {
            return;
        }
        if (!lIlllIlIIlIIlIl.multiTarget.get().booleanValue()) {
            lIlllIlIIlIIlIl.findTarget();
            if (lIlllIlIIlIIlIl.target == null) {
                return;
            }
            if (lIlllIlIIlIIlIl.breakDelayLeft <= 0) {
                lIlllIlIIlIIlIl.singleBreak();
            }
        } else if (lIlllIlIIlIIlIl.breakDelayLeft <= 0) {
            lIlllIlIIlIIlIl.multiBreak();
        }
        if (lIlllIlIIlIIlIl.broken) {
            lIlllIlIIlIIlIl.broken = false;
            return;
        }
        if (!(lIlllIlIIlIIlIl.smartDelay.get().booleanValue() || lIlllIlIIlIIlIl.placeDelayLeft <= 0 || (lIlllIlIIlIIlIl.surroundHold.get().booleanValue() || lIlllIlIIlIIlIl.target == null || lIlllIlIIlIIlIl.surroundBreak.get().booleanValue() && lIlllIlIIlIIlIl.isSurrounded(lIlllIlIIlIIlIl.target)) && lIlllIlIIlIIlIl.heldCrystal == null || lIlllIlIIlIIlIl.spamFacePlace.get().booleanValue())) {
            return;
        }
        if (lIlllIlIIlIIlIl.switchMode.get() == SwitchMode.None && lIlllIlIIlIIlIl.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && lIlllIlIIlIIlIl.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        if (lIlllIlIIlIIlIl.place.get().booleanValue()) {
            int lIlllIlIIlIIllI;
            int lIlllIlIIlIlIIl;
            int lIlllIlIIlIlIlI;
            if (lIlllIlIIlIIlIl.target == null) {
                return;
            }
            if (!lIlllIlIIlIIlIl.multiPlace.get().booleanValue() && lIlllIlIIlIIlIl.getCrystalStream().count() > 0L) {
                return;
            }
            if (lIlllIlIIlIIlIl.surroundHold.get().booleanValue() && lIlllIlIIlIIlIl.heldCrystal == null && ((lIlllIlIIlIlIlI = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && lIlllIlIIlIlIlI < 9 || lIlllIlIIlIIlIl.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL)) {
                lIlllIlIIlIIlIl.bestBlock = lIlllIlIIlIIlIl.findOpen(lIlllIlIIlIIlIl.target);
                if (lIlllIlIIlIIlIl.bestBlock != null) {
                    lIlllIlIIlIIlIl.doHeldCrystal();
                    return;
                }
            }
            if (lIlllIlIIlIIlIl.surroundBreak.get().booleanValue() && lIlllIlIIlIIlIl.heldCrystal == null && lIlllIlIIlIIlIl.isSurrounded(lIlllIlIIlIIlIl.target) && ((lIlllIlIIlIlIIl = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && lIlllIlIIlIlIIl < 9 || lIlllIlIIlIIlIl.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL)) {
                lIlllIlIIlIIlIl.bestBlock = lIlllIlIIlIIlIl.findOpenSurround(lIlllIlIIlIIlIl.target);
                if (lIlllIlIIlIIlIl.bestBlock != null) {
                    lIlllIlIIlIIlIl.doHeldCrystal();
                    return;
                }
            }
            if (((lIlllIlIIlIIllI = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) == -1 || lIlllIlIIlIIllI > 9) && lIlllIlIIlIIlIl.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                return;
            }
            lIlllIlIIlIIlIl.findValidBlocks(lIlllIlIIlIIlIl.target);
            if (lIlllIlIIlIIlIl.bestBlock == null) {
                lIlllIlIIlIIlIl.findFacePlace(lIlllIlIIlIIlIl.target);
            }
            if (lIlllIlIIlIIlIl.bestBlock == null) {
                return;
            }
            if (lIlllIlIIlIIlIl.facePlace.get().booleanValue() && Math.sqrt(lIlllIlIIlIIlIl.target.squaredDistanceTo(lIlllIlIIlIIlIl.bestBlock)) <= 2.0) {
                if ((double)(lIlllIlIIlIIlIl.target.getHealth() + lIlllIlIIlIIlIl.target.getAbsorptionAmount()) < lIlllIlIIlIIlIl.facePlaceHealth.get()) {
                    lIlllIlIIlIIIlI = true;
                } else {
                    Iterable lIlllIlIIlIIlll = lIlllIlIIlIIlIl.target.getArmorItems();
                    for (ItemStack lIlllIlIIlIlIII : lIlllIlIIlIIlll) {
                        if (lIlllIlIIlIlIII == null || lIlllIlIIlIlIII.isEmpty() || !((double)(lIlllIlIIlIlIII.getMaxDamage() - lIlllIlIIlIlIII.getDamage()) / (double)lIlllIlIIlIlIII.getMaxDamage() * 100.0 <= lIlllIlIIlIIlIl.facePlaceDurability.get())) continue;
                        lIlllIlIIlIIIlI = true;
                    }
                }
            }
            if (lIlllIlIIlIIlIl.bestBlock != null && (lIlllIlIIlIIlIl.bestDamage >= lIlllIlIIlIIlIl.minDamage.get() && !lIlllIlIIlIIlIl.locked || lIlllIlIIlIIIlI)) {
                if (lIlllIlIIlIIlIl.switchMode.get() != SwitchMode.None) {
                    lIlllIlIIlIIlIl.doSwitch();
                }
                if (lIlllIlIIlIIlIl.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && lIlllIlIIlIIlIl.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                    return;
                }
                if (!lIlllIlIIlIIlIl.smartDelay.get().booleanValue()) {
                    lIlllIlIIlIIlIl.placeDelayLeft = lIlllIlIIlIIlIl.placeDelay.get();
                    lIlllIlIIlIIlIl.placeBlock(lIlllIlIIlIIlIl.bestBlock, lIlllIlIIlIIlIl.getHand());
                } else if (lIlllIlIIlIIlIl.smartDelay.get().booleanValue() && (lIlllIlIIlIIlIl.placeDelayLeft <= 0 || lIlllIlIIlIIlIl.bestDamage - lIlllIlIIlIIlIl.lastDamage > lIlllIlIIlIIlIl.healthDifference.get() || lIlllIlIIlIIlIl.spamFacePlace.get().booleanValue() && lIlllIlIIlIIIlI)) {
                    lIlllIlIIlIIlIl.lastDamage = lIlllIlIIlIIlIl.bestDamage;
                    lIlllIlIIlIIlIl.placeBlock(lIlllIlIIlIIlIl.bestBlock, lIlllIlIIlIIlIl.getHand());
                    if (lIlllIlIIlIIlIl.placeDelayLeft <= 0) {
                        lIlllIlIIlIIlIl.placeDelayLeft = 10;
                    }
                }
            }
            if (lIlllIlIIlIIlIl.switchMode.get() == SwitchMode.Spoof && lIlllIlIIlIIlIl.preSlot != lIlllIlIIlIIlIl.mc.player.inventory.selectedSlot && lIlllIlIIlIIlIl.preSlot != -1) {
                lIlllIlIIlIIlIl.mc.player.inventory.selectedSlot = lIlllIlIIlIIlIl.preSlot;
            }
        }
    }

    private float getTotalHealth(PlayerEntity lIlllIIIIlIlIlI) {
        return lIlllIIIIlIlIlI.getHealth() + lIlllIIIIlIlIlI.getAbsorptionAmount();
    }

    private void singleBreak() {
        CrystalAura lIlllIIlllllllI;
        assert (lIlllIIlllllllI.mc.player != null);
        assert (lIlllIIlllllllI.mc.world != null);
        lIlllIIlllllllI.getCrystalStream().max(Comparator.comparingDouble(lIllIllllIIIIll -> {
            CrystalAura lIllIllllIIIlII;
            return DamageCalcUtils.crystalDamage(lIllIllllIIIlII.target, lIllIllllIIIIll.getPos());
        })).ifPresent(lIllIllllIIIlll -> {
            CrystalAura lIllIllllIIlIlI;
            lIllIllllIIlIlI.hitCrystal((EndCrystalEntity)lIllIllllIIIlll);
        });
    }

    public Hand getHand() {
        CrystalAura lIlllIIIIIlIIlI;
        assert (lIlllIIIIIlIIlI.mc.player != null);
        Hand lIlllIIIIIlIIll = Hand.MAIN_HAND;
        if (lIlllIIIIIlIIlI.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && lIlllIIIIIlIIlI.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL) {
            lIlllIIIIIlIIll = Hand.OFF_HAND;
        }
        return lIlllIIIIIlIIll;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean validSurroundBreak(LivingEntity lIlllIIIIllIllI, int lIlllIIIIllIlIl, int lIlllIIIIllIlII) {
        CrystalAura lIlllIIIIllllII;
        assert (lIlllIIIIllllII.mc.world != null);
        assert (lIlllIIIIllllII.mc.player != null);
        Vec3d lIlllIIIIlllIII = new Vec3d((double)lIlllIIIIllIllI.getBlockPos().getX() + 0.5, (double)lIlllIIIIllIllI.getBlockPos().getY(), (double)lIlllIIIIllIllI.getBlockPos().getZ() + 0.5);
        if (!lIlllIIIIllllII.isValid(lIlllIIIIllIllI.getBlockPos().add(lIlllIIIIllIlIl, -1, lIlllIIIIllIlII))) return false;
        if (lIlllIIIIllllII.mc.world.getBlockState(lIlllIIIIllIllI.getBlockPos().add(lIlllIIIIllIlIl / 2, 0, lIlllIIIIllIlII / 2)).getBlock() == Blocks.BEDROCK) return false;
        if (!lIlllIIIIllllII.isSafe(lIlllIIIIlllIII.add((double)lIlllIIIIllIlIl, 0.0, (double)lIlllIIIIllIlII))) return false;
        Box2 Box22 = new Box2(lIlllIIIIllIllI.getBlockPos().getX() + lIlllIIIIllIlIl, lIlllIIIIllIllI.getBlockPos().getY() - 1, lIlllIIIIllIllI.getBlockPos().getZ() + lIlllIIIIllIlII);
        if (!(Math.sqrt(lIlllIIIIllllII.mc.player.getBlockPos().getSquaredDistance(Box22)) < lIlllIIIIllllII.placeRange.get())) return false;
        if (lIlllIIIIllllII.mc.world.raycast(new RaycastContext(lIlllIIIIllIllI.getPos(), lIlllIIIIllIllI.getPos().add((double)lIlllIIIIllIlIl, 0.0, (double)lIlllIIIIllIlII), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)lIlllIIIIllIllI)).getType() == Type.MISS) return false;
        return true;
    }

    private Stream<Entity> getCrystalStream() {
        CrystalAura lIlllIlIIIIIIII;
        return Streams.stream((Iterable)lIlllIlIIIIIIII.mc.world.getEntities()).filter(lIllIlllIlIIllI -> lIllIlllIlIIllI instanceof EndCrystalEntity).filter(lIllIlllIlIlIIl -> {
            CrystalAura lIllIlllIlIllII;
            return (double)lIllIlllIlIlIIl.distanceTo((Entity)lIllIlllIlIllII.mc.player) <= lIllIlllIlIllII.breakRange.get();
        }).filter(Entity::isAlive).filter(lIllIlllIlIllll -> {
            CrystalAura lIllIlllIllIIII;
            return lIllIlllIllIIII.shouldBreak((EndCrystalEntity)lIllIlllIlIllll);
        }).filter(lIllIlllIllIlIl -> {
            CrystalAura lIllIlllIlllIII;
            return lIllIlllIlllIII.ignoreWalls.get() == false || lIllIlllIlllIII.mc.player.canSee(lIllIlllIllIlIl);
        }).filter(lIllIlllIllllIl -> {
            CrystalAura lIllIlllIllllII;
            return lIllIlllIllllII.isSafe(lIllIlllIllllIl.getPos());
        });
    }

    private boolean shouldBreak(EndCrystalEntity lIlllIIIIIlllIl) {
        CrystalAura lIlllIIIIlIIIII;
        assert (lIlllIIIIlIIIII.mc.world != null);
        return lIlllIIIIlIIIII.heldCrystal == null || lIlllIIIIlIIIII.surroundHold.get() == false && lIlllIIIIlIIIII.surroundBreak.get() == false || lIlllIIIIlIIIII.placeDelayLeft <= 0 && (!lIlllIIIIlIIIII.heldCrystal.getBlockPos().equals((Object)lIlllIIIIIlllIl.getBlockPos()) || lIlllIIIIlIIIII.mc.world.raycast(new RaycastContext(lIlllIIIIlIIIII.target.getPos(), lIlllIIIIlIIIII.heldCrystal.getPos(), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)lIlllIIIIlIIIII.target)).getType() == Type.MISS || (double)lIlllIIIIlIIIII.target.distanceTo((Entity)lIlllIIIIlIIIII.heldCrystal) > 1.5 && !lIlllIIIIlIIIII.isSurrounded(lIlllIIIIlIIIII.target));
    }

    private boolean isValid(BlockPos lIlllIIIlIlllll) {
        CrystalAura lIlllIIIlIllllI;
        assert (lIlllIIIlIllllI.mc.world != null);
        return (lIlllIIIlIllllI.canSupport && lIlllIIIlIllllI.isEmpty(lIlllIIIlIlllll) && lIlllIIIlIlllll.getY() - lIlllIIIlIllllI.target.getBlockPos().getY() == -1 && lIlllIIIlIllllI.supportDelayLeft <= 0 || lIlllIIIlIllllI.mc.world.getBlockState(lIlllIIIlIlllll).getBlock() == Blocks.BEDROCK || lIlllIIIlIllllI.mc.world.getBlockState(lIlllIIIlIlllll).getBlock() == Blocks.OBSIDIAN) && lIlllIIIlIllllI.isEmpty(lIlllIIIlIlllll.add(0, 1, 0));
    }

    private boolean isEmpty(BlockPos lIlllIIIIlIIIll) {
        CrystalAura lIlllIIIIlIIlII;
        assert (lIlllIIIIlIIlII.mc.world != null);
        return lIlllIIIIlIIlII.mc.world.getBlockState(lIlllIIIIlIIIll).isAir() && lIlllIIIIlIIlII.mc.world.getOtherEntities(null, new Box((double)lIlllIIIIlIIIll.getX(), (double)lIlllIIIIlIIIll.getY(), (double)lIlllIIIIlIIIll.getZ(), (double)lIlllIIIIlIIIll.getX() + 1.0, (double)lIlllIIIIlIIIll.getY() + 2.0, (double)lIlllIIIIlIIIll.getZ() + 1.0)).isEmpty();
    }

    private void findTarget() {
        CrystalAura lIlllIIllIIlIll;
        assert (lIlllIIllIIlIll.mc.world != null);
        Optional<LivingEntity> lIlllIIllIIlIlI = Streams.stream((Iterable)lIlllIIllIIlIll.mc.world.getEntities()).filter(Entity::isAlive).filter(lIllIlllllIIIll -> {
            CrystalAura lIllIlllllIIIlI;
            return lIllIlllllIIIll != lIllIlllllIIIlI.mc.player;
        }).filter(lIllIlllllIIlll -> !(lIllIlllllIIlll instanceof PlayerEntity) || Friends.get().attack((PlayerEntity)lIllIlllllIIlll)).filter(lIllIlllllIlIll -> lIllIlllllIlIll instanceof LivingEntity).filter(lIllIlllllIllIl -> {
            CrystalAura lIllIlllllIlllI;
            return lIllIlllllIlllI.entities.get().getBoolean((Object)lIllIlllllIllIl.getType());
        }).filter(lIllIllllllIlIl -> {
            CrystalAura lIllIllllllIlII;
            return (double)lIllIllllllIlIl.distanceTo((Entity)lIllIllllllIlII.mc.player) <= lIllIllllllIlII.targetRange.get() * 2.0;
        }).min(Comparator.comparingDouble(lIllIlllllllIll -> {
            CrystalAura lIllIlllllllIlI;
            return lIllIlllllllIll.distanceTo((Entity)lIllIlllllllIlI.mc.player);
        })).map(lIlllIIIIIIIIII -> (LivingEntity)lIlllIIIIIIIIII);
        if (!lIlllIIllIIlIlI.isPresent()) {
            lIlllIIllIIlIll.target = null;
            return;
        }
        lIlllIIllIIlIll.target = lIlllIIllIIlIlI.get();
    }

    @EventHandler
    private void onRender(RenderEvent lIlllIlIIIlIlIl) {
        CrystalAura lIlllIlIIIlIlII;
        if (!lIlllIlIIIlIlII.render.get().booleanValue()) {
            return;
        }
        for (RenderBlock lIlllIlIIIlIlll : lIlllIlIIIlIlII.renderBlocks) {
            lIlllIlIIIlIlll.render3D();
        }
    }

    private void findValidBlocks(LivingEntity lIlllIIlIIllIII) {
        CrystalAura lIlllIIlIIlIlII;
        assert (lIlllIIlIIlIlII.mc.player != null);
        assert (lIlllIIlIIlIlII.mc.world != null);
        lIlllIIlIIlIlII.bestBlock = new Vec3d(0.0, 0.0, 0.0);
        lIlllIIlIIlIlII.bestDamage = 0.0;
        Vec3d lIlllIIlIIlIlll = new Vec3d(0.0, 0.0, 0.0);
        double lIlllIIlIIlIllI = 0.0;
        BlockPos lIlllIIlIIlIlIl = lIlllIIlIIlIlII.mc.player.getBlockPos();
        lIlllIIlIIlIlII.canSupport = false;
        lIlllIIlIIlIlII.crystalMap.clear();
        lIlllIIlIIlIlII.crystalList.clear();
        if (lIlllIIlIIlIlII.support.get().booleanValue()) {
            for (int lIlllIIlIlIIIII = 0; lIlllIIlIlIIIII < 9; ++lIlllIIlIlIIIII) {
                if (lIlllIIlIIlIlII.mc.player.inventory.getStack(lIlllIIlIlIIIII).getItem() != Items.OBSIDIAN) continue;
                lIlllIIlIIlIlII.canSupport = true;
                lIlllIIlIIlIlII.supportSlot = lIlllIIlIlIIIII;
                break;
            }
        }
        for (double lIlllIIlIIllIll = (double)lIlllIIlIIlIlIl.getX() - lIlllIIlIIlIlII.placeRange.get(); lIlllIIlIIllIll < (double)lIlllIIlIIlIlIl.getX() + lIlllIIlIIlIlII.placeRange.get(); lIlllIIlIIllIll += 1.0) {
            for (double lIlllIIlIIlllII = (double)lIlllIIlIIlIlIl.getZ() - lIlllIIlIIlIlII.placeRange.get(); lIlllIIlIIlllII < (double)lIlllIIlIIlIlIl.getZ() + lIlllIIlIIlIlII.placeRange.get(); lIlllIIlIIlllII += 1.0) {
                for (double lIlllIIlIIlllIl = (double)lIlllIIlIIlIlIl.getY() - lIlllIIlIIlIlII.verticalRange.get(); lIlllIIlIIlllIl < (double)lIlllIIlIIlIlIl.getY() + lIlllIIlIIlIlII.verticalRange.get(); lIlllIIlIIlllIl += 1.0) {
                    Vec3d lIlllIIlIIllllI = new Vec3d(Math.floor(lIlllIIlIIllIll), Math.floor(lIlllIIlIIlllIl), Math.floor(lIlllIIlIIlllII));
                    if (!lIlllIIlIIlIlII.isValid(new BlockPos(lIlllIIlIIllllI)) || !lIlllIIlIIlIlII.getDamagePlace(new BlockPos(lIlllIIlIIllllI).up()) || lIlllIIlIIlIlII.oldPlace.get().booleanValue() && !lIlllIIlIIlIlII.isEmpty(new BlockPos(lIlllIIlIIllllI.add(0.0, 2.0, 0.0))) || lIlllIIlIIlIlII.rayTrace.get().booleanValue() && !(lIlllIIlIIllllI.distanceTo(new Vec3d(lIlllIIlIIlIlII.mc.player.getX(), lIlllIIlIIlIlII.mc.player.getY() + (double)lIlllIIlIIlIlII.mc.player.getEyeHeight(lIlllIIlIIlIlII.mc.player.getPose()), lIlllIIlIIlIlII.mc.player.getZ())) <= lIlllIIlIIlIlII.placeWallsRange.get()) && lIlllIIlIIlIlII.rayTraceCheck(new BlockPos(lIlllIIlIIllllI), false) == null) continue;
                    if (!lIlllIIlIIlIlII.multiTarget.get().booleanValue()) {
                        if (lIlllIIlIIlIlII.isEmpty(new BlockPos(lIlllIIlIIllllI)) && lIlllIIlIIlIllI < DamageCalcUtils.crystalDamage(lIlllIIlIIllIII, lIlllIIlIIllllI.add(0.5, 1.0, 0.5))) {
                            lIlllIIlIIlIlll = lIlllIIlIIllllI;
                            lIlllIIlIIlIllI = DamageCalcUtils.crystalDamage(lIlllIIlIIllIII, lIlllIIlIIllllI.add(0.5, 1.0, 0.5));
                            continue;
                        }
                        if (lIlllIIlIIlIlII.isEmpty(new BlockPos(lIlllIIlIIllllI)) || !(lIlllIIlIIlIlII.bestDamage < DamageCalcUtils.crystalDamage(lIlllIIlIIllIII, lIlllIIlIIllllI.add(0.5, 1.0, 0.5)))) continue;
                        lIlllIIlIIlIlII.bestBlock = lIlllIIlIIllllI;
                        lIlllIIlIIlIlII.bestDamage = DamageCalcUtils.crystalDamage(lIlllIIlIIllIII, lIlllIIlIIlIlII.bestBlock.add(0.5, 1.0, 0.5));
                        continue;
                    }
                    for (Entity lIlllIIlIIlllll : lIlllIIlIIlIlII.mc.world.getEntities()) {
                        if (lIlllIIlIIlllll == lIlllIIlIIlIlII.mc.player || !lIlllIIlIIlIlII.entities.get().getBoolean((Object)lIlllIIlIIlllll.getType()) || !((double)lIlllIIlIIlIlII.mc.player.distanceTo(lIlllIIlIIlllll) <= lIlllIIlIIlIlII.targetRange.get()) || !lIlllIIlIIlllll.isAlive() || !(lIlllIIlIIlllll instanceof LivingEntity) || lIlllIIlIIlllll instanceof PlayerEntity && !Friends.get().attack((PlayerEntity)lIlllIIlIIlllll)) continue;
                        lIlllIIlIIlIlII.crystalList.add(DamageCalcUtils.crystalDamage((LivingEntity)lIlllIIlIIlllll, lIlllIIlIIllllI.add(0.5, 1.0, 0.5)));
                    }
                    if (lIlllIIlIIlIlII.crystalList.isEmpty()) continue;
                    lIlllIIlIIlIlII.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
                    lIlllIIlIIlIlII.crystalMap.put(new EndCrystalEntity((World)lIlllIIlIIlIlII.mc.world, lIlllIIlIIllllI.x, lIlllIIlIIllllI.y, lIlllIIlIIllllI.z), new ArrayList<Double>(lIlllIIlIIlIlII.crystalList));
                    lIlllIIlIIlIlII.crystalList.clear();
                }
            }
        }
        if (lIlllIIlIIlIlII.multiTarget.get().booleanValue()) {
            EndCrystalEntity lIlllIIlIIllIlI = lIlllIIlIIlIlII.findBestCrystal(lIlllIIlIIlIlII.crystalMap);
            lIlllIIlIIlIlII.bestBlock = lIlllIIlIIllIlI != null && lIlllIIlIIlIlII.bestDamage > lIlllIIlIIlIlII.minDamage.get() ? lIlllIIlIIllIlI.getPos() : null;
        } else if (lIlllIIlIIlIlII.bestDamage < lIlllIIlIIlIlII.minDamage.get()) {
            lIlllIIlIIlIlII.bestBlock = null;
        }
        if (lIlllIIlIIlIlII.support.get().booleanValue() && (lIlllIIlIIlIlII.bestBlock == null || lIlllIIlIIlIlII.bestDamage < lIlllIIlIIlIllI && !lIlllIIlIIlIlII.supportBackup.get().booleanValue())) {
            lIlllIIlIIlIlII.bestBlock = lIlllIIlIIlIlll;
        }
    }

    private void placeBlock(Vec3d lIlllIIlIllIlIl, Hand lIlllIIlIllIlII) {
        CrystalAura lIlllIIlIllIIIl;
        assert (lIlllIIlIllIIIl.mc.player != null);
        assert (lIlllIIlIllIIIl.mc.interactionManager != null);
        assert (lIlllIIlIllIIIl.mc.world != null);
        if (lIlllIIlIllIIIl.mc.world.isAir(new BlockPos(lIlllIIlIllIlIl))) {
            PlayerUtils.placeBlock(new BlockPos(lIlllIIlIllIlIl), lIlllIIlIllIIIl.supportSlot, Hand.MAIN_HAND);
            lIlllIIlIllIIIl.supportDelayLeft = lIlllIIlIllIIIl.supportDelay.get();
        }
        BlockPos lIlllIIlIllIIll = new BlockPos(lIlllIIlIllIlIl);
        Direction lIlllIIlIllIIlI = lIlllIIlIllIIIl.rayTraceCheck(lIlllIIlIllIIll, true);
        if (lIlllIIlIllIIIl.rotationMode.get() == RotationMode.Place || lIlllIIlIllIIIl.rotationMode.get() == RotationMode.Both) {
            float[] lIlllIIlIlllIII = PlayerUtils.calculateAngle(lIlllIIlIllIIIl.strictLook.get() != false ? new Vec3d((double)lIlllIIlIllIIll.getX() + 0.5 + (double)lIlllIIlIllIIlI.getVector().getX() * 1.0 / 2.0, (double)lIlllIIlIllIIll.getY() + 0.5 + (double)lIlllIIlIllIIlI.getVector().getY() * 1.0 / 2.0, (double)lIlllIIlIllIIll.getZ() + 0.5 + (double)lIlllIIlIllIIlI.getVector().getZ() * 1.0 / 2.0) : lIlllIIlIllIlIl.add(0.5, 1.0, 0.5));
            Rotations.rotate(lIlllIIlIlllIII[0], lIlllIIlIlllIII[1], 25, () -> {
                CrystalAura lIlllIIIIIIIlIl;
                lIlllIIIIIIIlIl.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(lIlllIIlIllIlII, new BlockHitResult(lIlllIIIIIIIlIl.mc.player.getPos(), lIlllIIlIllIIlI, new BlockPos(lIlllIIlIllIlIl), false)));
                if (lIlllIIIIIIIlIl.swing.get().booleanValue()) {
                    lIlllIIIIIIIlIl.mc.player.swingHand(lIlllIIlIllIlII);
                } else {
                    lIlllIIIIIIIlIl.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(lIlllIIlIllIlII));
                }
            });
        } else {
            lIlllIIlIllIIIl.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(lIlllIIlIllIlII, new BlockHitResult(lIlllIIlIllIIIl.mc.player.getPos(), lIlllIIlIllIIlI, new BlockPos(lIlllIIlIllIlIl), false)));
            if (lIlllIIlIllIIIl.swing.get().booleanValue()) {
                lIlllIIlIllIIIl.mc.player.swingHand(lIlllIIlIllIlII);
            } else {
                lIlllIIlIllIIIl.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(lIlllIIlIllIlII));
            }
        }
        if (lIlllIIlIllIIIl.render.get().booleanValue()) {
            RenderBlock lIlllIIlIllIlll = lIlllIIlIllIIIl.renderBlockPool.get();
            lIlllIIlIllIlll.reset(lIlllIIlIllIlIl);
            lIlllIIlIllIlll.damage = DamageCalcUtils.crystalDamage(lIlllIIlIllIIIl.target, lIlllIIlIllIIIl.bestBlock.add(0.5, 1.0, 0.5));
            lIlllIIlIllIIIl.renderBlocks.add(lIlllIIlIllIlll);
        }
    }

    private void multiBreak() {
        CrystalAura lIlllIIlllllIII;
        assert (lIlllIIlllllIII.mc.world != null);
        assert (lIlllIIlllllIII.mc.player != null);
        lIlllIIlllllIII.crystalMap.clear();
        lIlllIIlllllIII.crystalList.clear();
        lIlllIIlllllIII.getCrystalStream().forEach(lIllIllllIlIIIl -> {
            CrystalAura lIllIllllIlIIII;
            for (Entity lIllIllllIlIIll : lIllIllllIlIIII.mc.world.getEntities()) {
                if (lIllIllllIlIIll == lIllIllllIlIIII.mc.player || !lIllIllllIlIIII.entities.get().getBoolean((Object)lIllIllllIlIIll.getType()) || !((double)lIllIllllIlIIII.mc.player.distanceTo(lIllIllllIlIIll) <= lIllIllllIlIIII.targetRange.get()) || !lIllIllllIlIIll.isAlive() || !(lIllIllllIlIIll instanceof LivingEntity) || lIllIllllIlIIll instanceof PlayerEntity && !Friends.get().attack((PlayerEntity)lIllIllllIlIIll)) continue;
                lIllIllllIlIIII.crystalList.add(DamageCalcUtils.crystalDamage((LivingEntity)lIllIllllIlIIll, lIllIllllIlIIIl.getPos()));
            }
            if (!lIllIllllIlIIII.crystalList.isEmpty()) {
                lIllIllllIlIIII.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
                lIllIllllIlIIII.crystalMap.put((EndCrystalEntity)lIllIllllIlIIIl, new ArrayList<Double>(lIllIllllIlIIII.crystalList));
                lIllIllllIlIIII.crystalList.clear();
            }
        });
        EndCrystalEntity lIlllIIlllllIIl = lIlllIIlllllIII.findBestCrystal(lIlllIIlllllIII.crystalMap);
        if (lIlllIIlllllIIl != null) {
            lIlllIIlllllIII.hitCrystal(lIlllIIlllllIIl);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Vec3d findOpen(LivingEntity lIlllIIIlllIIIl) {
        block6: {
            block7: {
                block5: {
                    if (!CrystalAura.$assertionsDisabled && lIlllIIIlllIIlI.mc.player == null) {
                        throw new AssertionError();
                    }
                    lIlllIIIlllIlII = 0;
                    lIlllIIIlllIIll = 0;
                    if (!lIlllIIIlllIIlI.isValid(lIlllIIIlllIIIl.getBlockPos().add(1, -1, 0))) break block5;
                    v0 = new Box2(lIlllIIIlllIIIl.getBlockPos().getX() + 1, lIlllIIIlllIIIl.getBlockPos().getY() - 1, lIlllIIIlllIIIl.getBlockPos().getZ());
                    if (!(Math.sqrt(lIlllIIIlllIIlI.mc.player.getBlockPos().getSquaredDistance(v0)) < lIlllIIIlllIIlI.placeRange.get())) break block5;
                    lIlllIIIlllIlII = 1;
                    break block6;
                }
                if (!lIlllIIIlllIIlI.isValid(lIlllIIIlllIIIl.getBlockPos().add(-1, -1, 0))) break block7;
                v1 = new Box2(lIlllIIIlllIIIl.getBlockPos().getX() - 1, lIlllIIIlllIIIl.getBlockPos().getY() - 1, lIlllIIIlllIIIl.getBlockPos().getZ());
                if (!(Math.sqrt(lIlllIIIlllIIlI.mc.player.getBlockPos().getSquaredDistance(v1)) < lIlllIIIlllIIlI.placeRange.get())) break block7;
                lIlllIIIlllIlII = -1;
                break block6;
            }
            if (!lIlllIIIlllIIlI.isValid(lIlllIIIlllIIIl.getBlockPos().add(0, -1, 1))) ** GOTO lbl-1000
            v2 = new Box2(lIlllIIIlllIIIl.getBlockPos().getX(), lIlllIIIlllIIIl.getBlockPos().getY() - 1, lIlllIIIlllIIIl.getBlockPos().getZ() + 1);
            if (Math.sqrt(lIlllIIIlllIIlI.mc.player.getBlockPos().getSquaredDistance(v2)) < lIlllIIIlllIIlI.placeRange.get()) {
                lIlllIIIlllIIll = 1;
            } else if (lIlllIIIlllIIlI.isValid(lIlllIIIlllIIIl.getBlockPos().add(0, -1, -1))) {
                v3 = new Box2(lIlllIIIlllIIIl.getBlockPos().getX(), lIlllIIIlllIIIl.getBlockPos().getY() - 1, lIlllIIIlllIIIl.getBlockPos().getZ() - 1);
                if (Math.sqrt(lIlllIIIlllIIlI.mc.player.getBlockPos().getSquaredDistance(v3)) < lIlllIIIlllIIlI.placeRange.get()) {
                    lIlllIIIlllIIll = -1;
                }
            }
        }
        if (lIlllIIIlllIlII != 0) return new Vec3d((double)lIlllIIIlllIIIl.getBlockPos().getX() + 0.5 + (double)lIlllIIIlllIlII, (double)(lIlllIIIlllIIIl.getBlockPos().getY() - 1), (double)lIlllIIIlllIIIl.getBlockPos().getZ() + 0.5 + (double)lIlllIIIlllIIll);
        if (lIlllIIIlllIIll == 0) return null;
        return new Vec3d((double)lIlllIIIlllIIIl.getBlockPos().getX() + 0.5 + (double)lIlllIIIlllIlII, (double)(lIlllIIIlllIIIl.getBlockPos().getY() - 1), (double)lIlllIIIlllIIIl.getBlockPos().getZ() + 0.5 + (double)lIlllIIIlllIIll);
    }

    private void findFacePlace(LivingEntity lIlllIIlIIIIIlI) {
        CrystalAura lIlllIIlIIIIllI;
        assert (lIlllIIlIIIIllI.mc.world != null);
        assert (lIlllIIlIIIIllI.mc.player != null);
        BlockPos lIlllIIlIIIIlII = lIlllIIlIIIIIlI.getBlockPos();
        if (lIlllIIlIIIIllI.mc.world.getBlockState(lIlllIIlIIIIlII.add(1, 1, 0)).isAir() && Math.sqrt(lIlllIIlIIIIllI.mc.player.getBlockPos().getSquaredDistance((Box2)lIlllIIlIIIIlII.add(1, 1, 0))) <= lIlllIIlIIIIllI.placeRange.get() && lIlllIIlIIIIllI.getDamagePlace(lIlllIIlIIIIlII.add(1, 1, 0))) {
            lIlllIIlIIIIllI.bestBlock = lIlllIIlIIIIIlI.getPos().add(1.0, 0.0, 0.0);
        } else if (lIlllIIlIIIIllI.mc.world.getBlockState(lIlllIIlIIIIlII.add(-1, 1, 0)).isAir() && Math.sqrt(lIlllIIlIIIIllI.mc.player.getBlockPos().getSquaredDistance((Box2)lIlllIIlIIIIlII.add(-1, 1, 0))) <= lIlllIIlIIIIllI.placeRange.get() && lIlllIIlIIIIllI.getDamagePlace(lIlllIIlIIIIlII.add(-1, 1, 0))) {
            lIlllIIlIIIIllI.bestBlock = lIlllIIlIIIIIlI.getPos().add(-1.0, 0.0, 0.0);
        } else if (lIlllIIlIIIIllI.mc.world.getBlockState(lIlllIIlIIIIlII.add(0, 1, 1)).isAir() && Math.sqrt(lIlllIIlIIIIllI.mc.player.getBlockPos().getSquaredDistance((Box2)lIlllIIlIIIIlII.add(0, 1, 1))) <= lIlllIIlIIIIllI.placeRange.get() && lIlllIIlIIIIllI.getDamagePlace(lIlllIIlIIIIlII.add(0, 1, 1))) {
            lIlllIIlIIIIllI.bestBlock = lIlllIIlIIIIIlI.getPos().add(0.0, 0.0, 1.0);
        } else if (lIlllIIlIIIIllI.mc.world.getBlockState(lIlllIIlIIIIlII.add(0, 1, -1)).isAir() && Math.sqrt(lIlllIIlIIIIllI.mc.player.getBlockPos().getSquaredDistance((Box2)lIlllIIlIIIIlII.add(0, 1, -1))) <= lIlllIIlIIIIllI.placeRange.get() && lIlllIIlIIIIllI.getDamagePlace(lIlllIIlIIIIlII.add(0, 1, -1))) {
            lIlllIIlIIIIllI.bestBlock = lIlllIIlIIIIIlI.getPos().add(0.0, 0.0, -1.0);
        }
    }

    private EndCrystalEntity findBestCrystal(Map<EndCrystalEntity, List<Double>> lIlllIIlllIlIll) {
        CrystalAura lIlllIIlllIlIIl;
        block7: {
            double lIlllIIlllIlIlI;
            block6: {
                lIlllIIlllIlIIl.bestDamage = 0.0;
                lIlllIIlllIlIlI = 0.0;
                if (lIlllIIlllIlIIl.targetMode.get() != TargetMode.HighestXDamages) break block6;
                for (Map.Entry<EndCrystalEntity, List<Double>> lIlllIIlllIllll : lIlllIIlllIlIll.entrySet()) {
                    for (int lIlllIIllllIIII = 0; lIlllIIllllIIII < lIlllIIlllIllll.getValue().size() && lIlllIIllllIIII < lIlllIIlllIlIIl.numberOfDamages.get(); ++lIlllIIllllIIII) {
                        lIlllIIlllIlIlI += lIlllIIlllIllll.getValue().get(lIlllIIllllIIII).doubleValue();
                    }
                    if (lIlllIIlllIlIIl.bestDamage < lIlllIIlllIlIlI) {
                        lIlllIIlllIlIIl.bestDamage = lIlllIIlllIlIlI;
                        lIlllIIlllIlIIl.bestBreak = lIlllIIlllIllll.getKey();
                    }
                    lIlllIIlllIlIlI = 0.0;
                }
                break block7;
            }
            if (lIlllIIlllIlIIl.targetMode.get() != TargetMode.MostDamage) break block7;
            for (Map.Entry<EndCrystalEntity, List<Double>> lIlllIIlllIllIl : lIlllIIlllIlIll.entrySet()) {
                for (int lIlllIIlllIlllI = 0; lIlllIIlllIlllI < lIlllIIlllIllIl.getValue().size(); ++lIlllIIlllIlllI) {
                    lIlllIIlllIlIlI += lIlllIIlllIllIl.getValue().get(lIlllIIlllIlllI).doubleValue();
                }
                if (lIlllIIlllIlIIl.bestDamage < lIlllIIlllIlIlI) {
                    lIlllIIlllIlIIl.bestDamage = lIlllIIlllIlIlI;
                    lIlllIIlllIlIIl.bestBreak = lIlllIIlllIllIl.getKey();
                }
                lIlllIIlllIlIlI = 0.0;
            }
        }
        return lIlllIIlllIlIIl.bestBreak;
    }

    private Direction rayTraceCheck(BlockPos lIlllIIIlIIlllI, boolean lIlllIIIlIIllIl) {
        CrystalAura lIlllIIIlIIlIll;
        Vec3d lIlllIIIlIIllII = new Vec3d(lIlllIIIlIIlIll.mc.player.getX(), lIlllIIIlIIlIll.mc.player.getY() + (double)lIlllIIIlIIlIll.mc.player.getEyeHeight(lIlllIIIlIIlIll.mc.player.getPose()), lIlllIIIlIIlIll.mc.player.getZ());
        for (Direction lIlllIIIlIlIIII : Direction.values()) {
            RaycastContext lIlllIIIlIlIIlI = new RaycastContext(lIlllIIIlIIllII, new Vec3d((double)lIlllIIIlIIlllI.getX() + 0.5 + (double)lIlllIIIlIlIIII.getVector().getX() * 0.5, (double)lIlllIIIlIIlllI.getY() + 0.5 + (double)lIlllIIIlIlIIII.getVector().getY() * 0.5, (double)lIlllIIIlIIlllI.getZ() + 0.5 + (double)lIlllIIIlIlIIII.getVector().getZ() * 0.5), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)lIlllIIIlIIlIll.mc.player);
            BlockHitResult lIlllIIIlIlIIIl = lIlllIIIlIIlIll.mc.world.raycast(lIlllIIIlIlIIlI);
            if (lIlllIIIlIlIIIl == null || lIlllIIIlIlIIIl.getType() != Type.BLOCK || !lIlllIIIlIlIIIl.getBlockPos().equals((Object)lIlllIIIlIIlllI)) continue;
            return lIlllIIIlIlIIII;
        }
        if (lIlllIIIlIIllIl) {
            if ((double)lIlllIIIlIIlllI.getY() > lIlllIIIlIIllII.y) {
                return Direction.DOWN;
            }
            return Direction.UP;
        }
        return null;
    }

    private boolean isSafe(Vec3d lIlllIIIIlIllIl) {
        CrystalAura lIlllIIIIllIIII;
        assert (lIlllIIIIllIIII.mc.player != null);
        return lIlllIIIIllIIII.breakMode.get() != Mode.Safe || (double)lIlllIIIIllIIII.getTotalHealth((PlayerEntity)lIlllIIIIllIIII.mc.player) - DamageCalcUtils.crystalDamage((LivingEntity)lIlllIIIIllIIII.mc.player, lIlllIIIIlIllIl) > lIlllIIIIllIIII.minHealth.get() && DamageCalcUtils.crystalDamage((LivingEntity)lIlllIIIIllIIII.mc.player, lIlllIIIIlIllIl) < lIlllIIIIllIIII.maxDamage.get();
    }

    @EventHandler(priority=100)
    private void onPlaySound(PlaySoundEvent lIlllIlIIIIIlIl) {
        CrystalAura lIlllIlIIIIIlII;
        if (lIlllIlIIIIIlIl.sound.getCategory().getName().equals(SoundCategory.BLOCKS.getName()) && lIlllIlIIIIIlIl.sound.getId().getPath().equals("entity.generic.explode") && lIlllIlIIIIIlII.cancelCrystalMode.get() == CancelCrystalMode.Sound) {
            lIlllIlIIIIIlII.removalQueue.forEach(lIllIlllIlIIIlI -> {
                CrystalAura lIllIlllIlIIIIl;
                lIllIlllIlIIIIl.mc.world.removeEntity(lIllIlllIlIIIlI.intValue());
            });
            lIlllIlIIIIIlII.removalQueue.clear();
        }
    }

    public static final class TargetMode
    extends Enum<TargetMode> {
        private static final /* synthetic */ TargetMode[] $VALUES;
        public static final /* synthetic */ /* enum */ TargetMode HighestXDamages;
        public static final /* synthetic */ /* enum */ TargetMode MostDamage;

        public static TargetMode[] values() {
            return (TargetMode[])$VALUES.clone();
        }

        private TargetMode() {
            TargetMode lllllllllllllllllIIIlllIIllIlIIl;
        }

        static {
            MostDamage = new TargetMode();
            HighestXDamages = new TargetMode();
            $VALUES = TargetMode.$values();
        }

        private static /* synthetic */ TargetMode[] $values() {
            return new TargetMode[]{MostDamage, HighestXDamages};
        }

        public static TargetMode valueOf(String lllllllllllllllllIIIlllIIllIllIl) {
            return Enum.valueOf(TargetMode.class, lllllllllllllllllIIIlllIIllIllIl);
        }
    }

    private class RenderBlock {
        private /* synthetic */ int timer;
        private /* synthetic */ int x;
        private /* synthetic */ int y;
        private /* synthetic */ int z;
        private /* synthetic */ double damage;

        public void render2D() {
            RenderBlock llllIIlIIlIIIII;
            if (((Boolean)llllIIlIIlIIIII.CrystalAura.this.renderDamage.get()).booleanValue()) {
                pos.set((double)llllIIlIIlIIIII.x + 0.5, (double)llllIIlIIlIIIII.y + 0.5, (double)llllIIlIIlIIIII.z + 0.5);
                if (NametagUtils.to2D(pos, (Double)llllIIlIIlIIIII.CrystalAura.this.damageScale.get())) {
                    NametagUtils.begin(pos);
                    TextRenderer.get().begin(1.0, false, true);
                    String llllIIlIIlIIIll = String.valueOf(Math.round(llllIIlIIlIIIII.damage));
                    switch ((Integer)llllIIlIIlIIIII.CrystalAura.this.roundDamage.get()) {
                        case 0: {
                            llllIIlIIlIIIll = String.valueOf(Math.round(llllIIlIIlIIIII.damage));
                            break;
                        }
                        case 1: {
                            llllIIlIIlIIIll = String.valueOf((double)Math.round(llllIIlIIlIIIII.damage * 10.0) / 10.0);
                            break;
                        }
                        case 2: {
                            llllIIlIIlIIIll = String.valueOf((double)Math.round(llllIIlIIlIIIII.damage * 100.0) / 100.0);
                            break;
                        }
                        case 3: {
                            llllIIlIIlIIIll = String.valueOf((double)Math.round(llllIIlIIlIIIII.damage * 1000.0) / 1000.0);
                        }
                    }
                    double llllIIlIIlIIIlI = TextRenderer.get().getWidth(llllIIlIIlIIIll) / 2.0;
                    TextRenderer.get().render(llllIIlIIlIIIll, -llllIIlIIlIIIlI, 0.0, (Color)llllIIlIIlIIIII.CrystalAura.this.damageColor.get());
                    TextRenderer.get().end();
                    NametagUtils.end();
                }
            }
        }

        public void render3D() {
            RenderBlock llllIIlIIlIlIII;
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllIIlIIlIlIII.x, llllIIlIIlIlIII.y, llllIIlIIlIlIII.z, 1.0, (Color)llllIIlIIlIlIII.CrystalAura.this.sideColor.get(), (Color)llllIIlIIlIlIII.CrystalAura.this.lineColor.get(), (ShapeMode)((Object)llllIIlIIlIlIII.CrystalAura.this.shapeMode.get()), 0);
        }

        private RenderBlock() {
            RenderBlock llllIIlIIllIlIl;
        }

        public boolean shouldRemove() {
            RenderBlock llllIIlIIlIlIlI;
            if (llllIIlIIlIlIlI.timer <= 0) {
                return true;
            }
            --llllIIlIIlIlIlI.timer;
            return false;
        }

        public void reset(Vec3d llllIIlIIlIllIl) {
            RenderBlock llllIIlIIlIlllI;
            llllIIlIIlIlllI.x = MathHelper.floor((double)llllIIlIIlIllIl.getX());
            llllIIlIIlIlllI.y = MathHelper.floor((double)llllIIlIIlIllIl.getY());
            llllIIlIIlIlllI.z = MathHelper.floor((double)llllIIlIIlIllIl.getZ());
            llllIIlIIlIlllI.timer = (Integer)llllIIlIIlIlllI.CrystalAura.this.renderTimer.get();
        }
    }

    public static final class CancelCrystalMode
    extends Enum<CancelCrystalMode> {
        private static final /* synthetic */ CancelCrystalMode[] $VALUES;
        public static final /* synthetic */ /* enum */ CancelCrystalMode Hit;
        public static final /* synthetic */ /* enum */ CancelCrystalMode Sound;

        private CancelCrystalMode() {
            CancelCrystalMode llllllllllllllllIlllIlIlIIlIlIll;
        }

        static {
            Sound = new CancelCrystalMode();
            Hit = new CancelCrystalMode();
            $VALUES = CancelCrystalMode.$values();
        }

        public static CancelCrystalMode[] values() {
            return (CancelCrystalMode[])$VALUES.clone();
        }

        public static CancelCrystalMode valueOf(String llllllllllllllllIlllIlIlIIllIIIl) {
            return Enum.valueOf(CancelCrystalMode.class, llllllllllllllllIlllIlIlIIllIIIl);
        }

        private static /* synthetic */ CancelCrystalMode[] $values() {
            return new CancelCrystalMode[]{Sound, Hit};
        }
    }

    public static final class RotationMode
    extends Enum<RotationMode> {
        public static final /* synthetic */ /* enum */ RotationMode None;
        public static final /* synthetic */ /* enum */ RotationMode Both;
        public static final /* synthetic */ /* enum */ RotationMode Break;
        public static final /* synthetic */ /* enum */ RotationMode Place;
        private static final /* synthetic */ RotationMode[] $VALUES;

        static {
            Place = new RotationMode();
            Break = new RotationMode();
            Both = new RotationMode();
            None = new RotationMode();
            $VALUES = RotationMode.$values();
        }

        private static /* synthetic */ RotationMode[] $values() {
            return new RotationMode[]{Place, Break, Both, None};
        }

        private RotationMode() {
            RotationMode llIIllIlllllIIl;
        }

        public static RotationMode valueOf(String llIIllIlllllllI) {
            return Enum.valueOf(RotationMode.class, llIIllIlllllllI);
        }

        public static RotationMode[] values() {
            return (RotationMode[])$VALUES.clone();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Safe;
        public static final /* synthetic */ /* enum */ Mode Suicide;

        public static Mode valueOf(String llllllllllllllllllIIIllIIIIIIllI) {
            return Enum.valueOf(Mode.class, llllllllllllllllllIIIllIIIIIIllI);
        }

        private Mode() {
            Mode llllllllllllllllllIIIllIIIIIIIIl;
        }

        static {
            Safe = new Mode();
            Suicide = new Mode();
            $VALUES = Mode.$values();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Safe, Suicide};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }

    public static final class SwitchMode
    extends Enum<SwitchMode> {
        public static final /* synthetic */ /* enum */ SwitchMode None;
        public static final /* synthetic */ /* enum */ SwitchMode Spoof;
        public static final /* synthetic */ /* enum */ SwitchMode Auto;
        private static final /* synthetic */ SwitchMode[] $VALUES;

        public static SwitchMode valueOf(String llllllllllllllllIlIlllllllIllIll) {
            return Enum.valueOf(SwitchMode.class, llllllllllllllllIlIlllllllIllIll);
        }

        static {
            Auto = new SwitchMode();
            Spoof = new SwitchMode();
            None = new SwitchMode();
            $VALUES = SwitchMode.$values();
        }

        public static SwitchMode[] values() {
            return (SwitchMode[])$VALUES.clone();
        }

        private SwitchMode() {
            SwitchMode llllllllllllllllIlIlllllllIlIlIl;
        }

        private static /* synthetic */ SwitchMode[] $values() {
            return new SwitchMode[]{Auto, Spoof, None};
        }
    }
}

