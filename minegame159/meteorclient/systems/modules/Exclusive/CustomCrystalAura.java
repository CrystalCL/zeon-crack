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
 *  net.minecraft.block.ShapeContext
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$class_242
 *  net.minecraft.world.RaycastContext$class_3960
 *  net.minecraft.util.hit.BlockHitResult
 *  org.apache.commons.io.FileUtils
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import com.google.common.collect.Streams;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import meteordevelopment.orbit.EventHandler;
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
import minegame159.meteorclient.utils.player.CityUtils;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.RotationUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.NametagUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
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
import net.minecraft.block.ShapeContext;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FileUtils;

public class CustomCrystalAura
extends Module {
    private /* synthetic */ boolean locked;
    private final /* synthetic */ Setting<Integer> numberOfDamages;
    private /* synthetic */ boolean canSupport;
    private final /* synthetic */ Setting<Boolean> antiWeakness;
    private /* synthetic */ boolean broken;
    private final /* synthetic */ Setting<Double> breakRange;
    private final /* synthetic */ Setting<Boolean> fullBreak;
    private final /* synthetic */ Setting<SettingColor> damageColor;
    private final /* synthetic */ Setting<SwitchMode> switchMode;
    private /* synthetic */ EndCrystalEntity heldCrystal;
    private /* synthetic */ boolean placed;
    private final /* synthetic */ Setting<Boolean> renderDamage;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<Boolean> strictLook;
    private final /* synthetic */ Setting<RotationMode> rotationMode;
    private final /* synthetic */ Setting<Double> facePlaceHealth;
    private final /* synthetic */ Setting<Boolean> facePlace;
    private final /* synthetic */ Setting<Boolean> switchBack;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ Setting<Double> facePlaceDurability;
    private final /* synthetic */ Setting<Integer> breakDelay;
    private /* synthetic */ int supportSlot;
    private final /* synthetic */ Setting<Boolean> pauseOnDrink;
    private final /* synthetic */ Setting<Boolean> pauseOnMine;
    private /* synthetic */ int supportDelayLeft;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Boolean> surroundBreak;
    private final /* synthetic */ SettingGroup sgTarget;
    private final /* synthetic */ Setting<TargetMode> targetMode;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private /* synthetic */ EndCrystalEntity bestBreak;
    private /* synthetic */ int breakDelayLeft;
    private /* synthetic */ List<BlockPos> placePositions;
    private final /* synthetic */ Setting<Boolean> ignoreWalls;
    private static final /* synthetic */ Vec3 pos;
    private final /* synthetic */ Setting<Boolean> smartDelay;
    private final /* synthetic */ Setting<Integer> range;
    private final /* synthetic */ Setting<Boolean> multiTarget;
    private final /* synthetic */ Setting<Mode> placeMode;
    private final /* synthetic */ Setting<Boolean> place;
    private /* synthetic */ double lastDamage;
    private final /* synthetic */ Setting<CancelCrystalMode> cancelCrystalMode;
    private final /* synthetic */ SettingGroup sgPause;
    private final /* synthetic */ Setting<Double> targetRange;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Double> placeRange;
    private final /* synthetic */ Setting<Mode> breakMode;
    private final /* synthetic */ Setting<Integer> roundDamage;
    private final /* synthetic */ Setting<Boolean> spamFacePlace;
    private final /* synthetic */ Setting<Boolean> rayTrace;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<Boolean> resetRotations;
    private final /* synthetic */ Setting<Integer> placeDelay;
    private final /* synthetic */ Setting<Boolean> pauseOnEat;
    private /* synthetic */ LivingEntity target;
    private /* synthetic */ Vec3d bestBlock;
    private /* synthetic */ double bestDamage;
    private final /* synthetic */ Setting<Boolean> swing;
    private final /* synthetic */ Map<EndCrystalEntity, List<Double>> crystalMap;
    private final /* synthetic */ List<Double> crystalList;
    private final /* synthetic */ Setting<Integer> supportDelay;
    private final /* synthetic */ SettingGroup sgPlace;
    private final /* synthetic */ Setting<Double> placeWallsRange;
    private final /* synthetic */ SettingGroup sgMisc;
    private /* synthetic */ PlayerEntity target1;
    private final /* synthetic */ Setting<Boolean> supportBackup;
    private final /* synthetic */ Setting<Double> damageScale;
    private final /* synthetic */ Setting<Boolean> support;
    private final /* synthetic */ SettingGroup sgBreak;
    private final /* synthetic */ List<RenderBlock> renderBlocks;
    private final /* synthetic */ Setting<Double> minHealth;
    private final /* synthetic */ Setting<Boolean> oldPlace;
    private final /* synthetic */ Setting<Double> healthDifference;
    private final /* synthetic */ Setting<Boolean> multiPlace;
    private /* synthetic */ int preSlot;
    private final /* synthetic */ List<Integer> removalQueue;
    private final /* synthetic */ Setting<Integer> renderTimer;
    private final /* synthetic */ Setting<Double> minDamage;
    private final /* synthetic */ Setting<Double> verticalRange;
    private final /* synthetic */ Pool<RenderBlock> renderBlockPool;
    private final /* synthetic */ Setting<Double> maxDamage;
    private /* synthetic */ int placeDelayLeft;
    private final /* synthetic */ Setting<Boolean> surroundHold;
    private final /* synthetic */ SettingGroup sgRotations;

    private void doHeldCrystal() {
        CustomCrystalAura llllllllllllllllIlllIIlIIlIIIIll;
        assert (llllllllllllllllIlllIIlIIlIIIIll.mc.player != null);
        if (llllllllllllllllIlllIIlIIlIIIIll.switchMode.get() != SwitchMode.None) {
            llllllllllllllllIlllIIlIIlIIIIll.doSwitch();
        }
        if (llllllllllllllllIlllIIlIIlIIIIll.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && llllllllllllllllIlllIIlIIlIIIIll.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        llllllllllllllllIlllIIlIIlIIIIll.bestDamage = DamageCalcUtils.crystalDamage(llllllllllllllllIlllIIlIIlIIIIll.target, llllllllllllllllIlllIIlIIlIIIIll.bestBlock.add(0.0, 1.0, 0.0));
        llllllllllllllllIlllIIlIIlIIIIll.heldCrystal = new EndCrystalEntity((World)llllllllllllllllIlllIIlIIlIIIIll.mc.world, llllllllllllllllIlllIIlIIlIIIIll.bestBlock.x, llllllllllllllllIlllIIlIIlIIIIll.bestBlock.y + 1.0, llllllllllllllllIlllIIlIIlIIIIll.bestBlock.z);
        llllllllllllllllIlllIIlIIlIIIIll.locked = true;
        if (!llllllllllllllllIlllIIlIIlIIIIll.smartDelay.get().booleanValue()) {
            llllllllllllllllIlllIIlIIlIIIIll.placeDelayLeft = llllllllllllllllIlllIIlIIlIIIIll.placeDelay.get();
        } else {
            llllllllllllllllIlllIIlIIlIIIIll.lastDamage = llllllllllllllllIlllIIlIIlIIIIll.bestDamage;
            if (llllllllllllllllIlllIIlIIlIIIIll.placeDelayLeft <= 0) {
                llllllllllllllllIlllIIlIIlIIIIll.placeDelayLeft = 10;
            }
        }
        llllllllllllllllIlllIIlIIlIIIIll.placeBlock(llllllllllllllllIlllIIlIIlIIIIll.bestBlock, llllllllllllllllIlllIIlIIlIIIIll.getHand());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean getDamagePlace(BlockPos llllllllllllllllIlllIIIlllllllll) {
        CustomCrystalAura llllllllllllllllIlllIIlIIIIIIIlI;
        assert (llllllllllllllllIlllIIlIIIIIIIlI.mc.player != null);
        if (llllllllllllllllIlllIIlIIIIIIIlI.placeMode.get() == Mode.Suicide) return true;
        Vec3d Vec3d2 = new Vec3d((double)llllllllllllllllIlllIIIlllllllll.getX() + 0.5, (double)llllllllllllllllIlllIIIlllllllll.getY(), (double)llllllllllllllllIlllIIIlllllllll.getZ() + 0.5);
        if (!(DamageCalcUtils.crystalDamage((LivingEntity)llllllllllllllllIlllIIlIIIIIIIlI.mc.player, Vec3d2) <= llllllllllllllllIlllIIlIIIIIIIlI.maxDamage.get())) return false;
        Vec3d Vec3d3 = new Vec3d((double)llllllllllllllllIlllIIIlllllllll.getX() + 0.5, (double)llllllllllllllllIlllIIIlllllllll.getY(), (double)llllllllllllllllIlllIIIlllllllll.getZ() + 0.5);
        if (!((double)llllllllllllllllIlllIIlIIIIIIIlI.getTotalHealth((PlayerEntity)llllllllllllllllIlllIIlIIIIIIIlI.mc.player) - DamageCalcUtils.crystalDamage((LivingEntity)llllllllllllllllIlllIIlIIIIIIIlI.mc.player, Vec3d3) >= llllllllllllllllIlllIIlIIIIIIIlI.minHealth.get())) return false;
        return true;
    }

    private void add(BlockPos llllllllllllllllIlllIIIllIIIIIII) {
        CustomCrystalAura llllllllllllllllIlllIIIllIIIIIIl;
        if (!llllllllllllllllIlllIIIllIIIIIIl.placePositions.contains((Object)llllllllllllllllIlllIIIllIIIIIII) && llllllllllllllllIlllIIIllIIIIIIl.mc.world.getBlockState(llllllllllllllllIlllIIIllIIIIIII).getMaterial().isReplaceable() && llllllllllllllllIlllIIIllIIIIIIl.mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), llllllllllllllllIlllIIIllIIIIIII, ShapeContext.absent())) {
            llllllllllllllllIlllIIIllIIIIIIl.placePositions.add(llllllllllllllllIlllIIIllIIIIIII);
        }
    }

    @EventHandler(priority=100)
    private void onTick(TickEvent.Post llllllllllllllllIlllIIlIlIlllIlI) {
        CustomCrystalAura llllllllllllllllIlllIIlIlIlllIIl;
        if (llllllllllllllllIlllIIlIlIlllIIl.cancelCrystalMode.get() == CancelCrystalMode.Hit) {
            llllllllllllllllIlllIIlIlIlllIIl.removalQueue.forEach(llllllllllllllllIlllIIIlIIIIIlIl -> {
                CustomCrystalAura llllllllllllllllIlllIIIlIIIIIllI;
                llllllllllllllllIlllIIIlIIIIIllI.mc.world.removeEntity(llllllllllllllllIlllIIIlIIIIIlIl.intValue());
            });
            llllllllllllllllIlllIIlIlIlllIIl.removalQueue.clear();
        }
    }

    private Stream<Entity> getCrystalStream() {
        CustomCrystalAura llllllllllllllllIlllIIlIlIIIIlIl;
        return Streams.stream((Iterable)llllllllllllllllIlllIIlIlIIIIlIl.mc.world.getEntities()).filter(llllllllllllllllIlllIIIlIIIlIIlI -> llllllllllllllllIlllIIIlIIIlIIlI instanceof EndCrystalEntity).filter(llllllllllllllllIlllIIIlIIIlIlII -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIIIlIlIl;
            return (double)llllllllllllllllIlllIIIlIIIlIlII.distanceTo((Entity)llllllllllllllllIlllIIIlIIIlIlIl.mc.player) <= llllllllllllllllIlllIIIlIIIlIlIl.breakRange.get();
        }).filter(Entity::isAlive).filter(llllllllllllllllIlllIIIlIIIllIlI -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIIIllIll;
            return llllllllllllllllIlllIIIlIIIllIll.shouldBreak((EndCrystalEntity)llllllllllllllllIlllIIIlIIIllIlI);
        }).filter(llllllllllllllllIlllIIIlIIlIIIII -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIIlIIIIl;
            return llllllllllllllllIlllIIIlIIlIIIIl.ignoreWalls.get() == false || llllllllllllllllIlllIIIlIIlIIIIl.mc.player.canSee(llllllllllllllllIlllIIIlIIlIIIII);
        }).filter(llllllllllllllllIlllIIIlIIlIIllI -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIIlIIlll;
            return llllllllllllllllIlllIIIlIIlIIlll.isSafe(llllllllllllllllIlllIIIlIIlIIllI.getPos());
        });
    }

    private boolean isValid(BlockPos llllllllllllllllIlllIIIllllIIIll) {
        CustomCrystalAura llllllllllllllllIlllIIIllllIIIlI;
        assert (llllllllllllllllIlllIIIllllIIIlI.mc.world != null);
        return (llllllllllllllllIlllIIIllllIIIlI.canSupport && llllllllllllllllIlllIIIllllIIIlI.isEmpty(llllllllllllllllIlllIIIllllIIIll) && llllllllllllllllIlllIIIllllIIIll.getY() - llllllllllllllllIlllIIIllllIIIlI.target.getBlockPos().getY() == -1 && llllllllllllllllIlllIIIllllIIIlI.supportDelayLeft <= 0 || llllllllllllllllIlllIIIllllIIIlI.mc.world.getBlockState(llllllllllllllllIlllIIIllllIIIll).getBlock() == Blocks.BEDROCK || llllllllllllllllIlllIIIllllIIIlI.mc.world.getBlockState(llllllllllllllllIlllIIIllllIIIll).getBlock() == Blocks.OBSIDIAN) && llllllllllllllllIlllIIIllllIIIlI.isEmpty(llllllllllllllllIlllIIIllllIIIll.add(0, 1, 0));
    }

    private Direction rayTraceCheck(BlockPos llllllllllllllllIlllIIIlllIIlllI, boolean llllllllllllllllIlllIIIlllIlIIIl) {
        CustomCrystalAura llllllllllllllllIlllIIIlllIlIIll;
        Vec3d llllllllllllllllIlllIIIlllIlIIII = new Vec3d(llllllllllllllllIlllIIIlllIlIIll.mc.player.getX(), llllllllllllllllIlllIIIlllIlIIll.mc.player.getY() + (double)llllllllllllllllIlllIIIlllIlIIll.mc.player.getEyeHeight(llllllllllllllllIlllIIIlllIlIIll.mc.player.getPose()), llllllllllllllllIlllIIIlllIlIIll.mc.player.getZ());
        for (Direction llllllllllllllllIlllIIIlllIlIlII : Direction.values()) {
            RaycastContext llllllllllllllllIlllIIIlllIlIllI = new RaycastContext(llllllllllllllllIlllIIIlllIlIIII, new Vec3d((double)llllllllllllllllIlllIIIlllIIlllI.getX() + 0.5 + (double)llllllllllllllllIlllIIIlllIlIlII.getVector().getX() * 0.5, (double)llllllllllllllllIlllIIIlllIIlllI.getY() + 0.5 + (double)llllllllllllllllIlllIIIlllIlIlII.getVector().getY() * 0.5, (double)llllllllllllllllIlllIIIlllIIlllI.getZ() + 0.5 + (double)llllllllllllllllIlllIIIlllIlIlII.getVector().getZ() * 0.5), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)llllllllllllllllIlllIIIlllIlIIll.mc.player);
            BlockHitResult llllllllllllllllIlllIIIlllIlIlIl = llllllllllllllllIlllIIIlllIlIIll.mc.world.raycast(llllllllllllllllIlllIIIlllIlIllI);
            if (llllllllllllllllIlllIIIlllIlIlIl == null || llllllllllllllllIlllIIIlllIlIlIl.getType() != Type.BLOCK || !llllllllllllllllIlllIIIlllIlIlIl.getBlockPos().equals((Object)llllllllllllllllIlllIIIlllIIlllI)) continue;
            return llllllllllllllllIlllIIIlllIlIlII;
        }
        if (llllllllllllllllIlllIIIlllIlIIIl) {
            if ((double)llllllllllllllllIlllIIIlllIIlllI.getY() > llllllllllllllllIlllIIIlllIlIIII.y) {
                return Direction.DOWN;
            }
            return Direction.UP;
        }
        return null;
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
        pos = new Vec3();
    }

    private void findPlacePos(PlayerEntity llllllllllllllllIlllIIIlIlllllII) {
        CustomCrystalAura llllllllllllllllIlllIIIlIllllIlI;
        llllllllllllllllIlllIIIlIllllIlI.placePositions.clear();
        BlockPos llllllllllllllllIlllIIIlIllllIll = llllllllllllllllIlllIIIlIllllIlI.target.getBlockPos();
        llllllllllllllllIlllIIIlIllllIlI.add(llllllllllllllllIlllIIIlIllllIll.add(2, 0, 1));
    }

    public BlockPos SetRelative(int llllllllllllllllIlllIIIllIIIlIII, int llllllllllllllllIlllIIIllIIIlIll, int llllllllllllllllIlllIIIllIIIIllI) {
        CustomCrystalAura llllllllllllllllIlllIIIllIIIllIl;
        return new BlockPos(llllllllllllllllIlllIIIllIIIllIl.mc.player.getX() + (double)llllllllllllllllIlllIIIllIIIlIII, llllllllllllllllIlllIIIllIIIllIl.mc.player.getY() + (double)llllllllllllllllIlllIIIllIIIlIll, llllllllllllllllIlllIIIllIIIllIl.mc.player.getZ() + (double)llllllllllllllllIlllIIIllIIIIllI);
    }

    @EventHandler
    private void onRender(RenderEvent llllllllllllllllIlllIIlIlIIllIIl) {
        CustomCrystalAura llllllllllllllllIlllIIlIlIIllIII;
        if (!llllllllllllllllIlllIIlIlIIllIII.render.get().booleanValue()) {
            return;
        }
        for (RenderBlock llllllllllllllllIlllIIlIlIIllIll : llllllllllllllllIlllIIlIlIIllIII.renderBlocks) {
            llllllllllllllllIlllIIlIlIIllIll.render3D();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Vec3d findOpen(LivingEntity llllllllllllllllIlllIIIlllllIlIl) {
        block6: {
            block7: {
                block5: {
                    if (!CustomCrystalAura.$assertionsDisabled && llllllllllllllllIlllIIIllllllIlI.mc.player == null) {
                        throw new AssertionError();
                    }
                    llllllllllllllllIlllIIIllllllIII = 0;
                    llllllllllllllllIlllIIIlllllIlll = 0;
                    if (!llllllllllllllllIlllIIIllllllIlI.isValid(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().add(1, -1, 0))) break block5;
                    v0 = new Box2(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getX() + 1, llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getY() - 1, llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getZ());
                    if (!(Math.sqrt(llllllllllllllllIlllIIIllllllIlI.mc.player.getBlockPos().getSquaredDistance(v0)) < llllllllllllllllIlllIIIllllllIlI.placeRange.get())) break block5;
                    llllllllllllllllIlllIIIllllllIII = 1;
                    break block6;
                }
                if (!llllllllllllllllIlllIIIllllllIlI.isValid(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().add(-1, -1, 0))) break block7;
                v1 = new Box2(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getX() - 1, llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getY() - 1, llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getZ());
                if (!(Math.sqrt(llllllllllllllllIlllIIIllllllIlI.mc.player.getBlockPos().getSquaredDistance(v1)) < llllllllllllllllIlllIIIllllllIlI.placeRange.get())) break block7;
                llllllllllllllllIlllIIIllllllIII = -1;
                break block6;
            }
            if (!llllllllllllllllIlllIIIllllllIlI.isValid(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().add(0, -1, 1))) ** GOTO lbl-1000
            v2 = new Box2(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getX(), llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getY() - 1, llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getZ() + 1);
            if (Math.sqrt(llllllllllllllllIlllIIIllllllIlI.mc.player.getBlockPos().getSquaredDistance(v2)) < llllllllllllllllIlllIIIllllllIlI.placeRange.get()) {
                llllllllllllllllIlllIIIlllllIlll = 1;
            } else if (llllllllllllllllIlllIIIllllllIlI.isValid(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().add(0, -1, -1))) {
                v3 = new Box2(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getX(), llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getY() - 1, llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getZ() - 1);
                if (Math.sqrt(llllllllllllllllIlllIIIllllllIlI.mc.player.getBlockPos().getSquaredDistance(v3)) < llllllllllllllllIlllIIIllllllIlI.placeRange.get()) {
                    llllllllllllllllIlllIIIlllllIlll = -1;
                }
            }
        }
        if (llllllllllllllllIlllIIIllllllIII != 0) return new Vec3d((double)llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getX() + 0.5 + (double)llllllllllllllllIlllIIIllllllIII, (double)(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getY() - 1), (double)llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getZ() + 0.5 + (double)llllllllllllllllIlllIIIlllllIlll);
        if (llllllllllllllllIlllIIIlllllIlll == 0) return null;
        return new Vec3d((double)llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getX() + 0.5 + (double)llllllllllllllllIlllIIIllllllIII, (double)(llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getY() - 1), (double)llllllllllllllllIlllIIIlllllIlIl.getBlockPos().getZ() + 0.5 + (double)llllllllllllllllIlllIIIlllllIlll);
    }

    private void hitCrystal(EndCrystalEntity llllllllllllllllIlllIIlIIllIIIII) {
        CustomCrystalAura llllllllllllllllIlllIIlIIlIllllI;
        assert (llllllllllllllllIlllIIlIIlIllllI.mc.player != null);
        assert (llllllllllllllllIlllIIlIIlIllllI.mc.world != null);
        assert (llllllllllllllllIlllIIlIIlIllllI.mc.interactionManager != null);
        int llllllllllllllllIlllIIlIIlIlllll = llllllllllllllllIlllIIlIIlIllllI.mc.player.inventory.selectedSlot;
        if (llllllllllllllllIlllIIlIIlIllllI.mc.player.getActiveStatusEffects().containsKey((Object)StatusEffects.WEAKNESS) && llllllllllllllllIlllIIlIIlIllllI.antiWeakness.get().booleanValue()) {
            for (int llllllllllllllllIlllIIlIIllIIIll = 0; llllllllllllllllIlllIIlIIllIIIll < 9; ++llllllllllllllllIlllIIlIIllIIIll) {
                if (!(llllllllllllllllIlllIIlIIlIllllI.mc.player.inventory.getStack(llllllllllllllllIlllIIlIIllIIIll).getItem() instanceof SwordItem) && !(llllllllllllllllIlllIIlIIlIllllI.mc.player.inventory.getStack(llllllllllllllllIlllIIlIIllIIIll).getItem() instanceof AxeItem)) continue;
                llllllllllllllllIlllIIlIIlIllllI.mc.player.inventory.selectedSlot = llllllllllllllllIlllIIlIIllIIIll;
                break;
            }
        }
        if (llllllllllllllllIlllIIlIIlIllllI.rotationMode.get() == RotationMode.Break || llllllllllllllllIlllIIlIIlIllllI.rotationMode.get() == RotationMode.Both) {
            float[] llllllllllllllllIlllIIlIIllIIIlI = PlayerUtils.calculateAngle(llllllllllllllllIlllIIlIIllIIIII.getPos());
            Rotations.rotate(llllllllllllllllIlllIIlIIllIIIlI[0], llllllllllllllllIlllIIlIIllIIIlI[1], 30, () -> {
                CustomCrystalAura llllllllllllllllIlllIIIlIlIIIlIl;
                llllllllllllllllIlllIIIlIlIIIlIl.attackCrystal(llllllllllllllllIlllIIlIIllIIIII, llllllllllllllllIlllIIlIIlIlllll);
            });
        } else {
            llllllllllllllllIlllIIlIIlIllllI.attackCrystal(llllllllllllllllIlllIIlIIllIIIII, llllllllllllllllIlllIIlIIlIlllll);
        }
        llllllllllllllllIlllIIlIIlIllllI.broken = true;
        llllllllllllllllIlllIIlIIlIllllI.breakDelayLeft = llllllllllllllllIlllIIlIIlIllllI.breakDelay.get();
    }

    private void doSwitch() {
        int llllllllllllllllIlllIIlIIlIIlIIl;
        CustomCrystalAura llllllllllllllllIlllIIlIIlIIlIII;
        assert (llllllllllllllllIlllIIlIIlIIlIII.mc.player != null);
        if (llllllllllllllllIlllIIlIIlIIlIII.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && llllllllllllllllIlllIIlIIlIIlIII.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL && (llllllllllllllllIlllIIlIIlIIlIIl = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && llllllllllllllllIlllIIlIIlIIlIIl < 9) {
            llllllllllllllllIlllIIlIIlIIlIII.preSlot = llllllllllllllllIlllIIlIIlIIlIII.mc.player.inventory.selectedSlot;
            llllllllllllllllIlllIIlIIlIIlIII.mc.player.inventory.selectedSlot = llllllllllllllllIlllIIlIIlIIlIIl;
        }
    }

    @EventHandler(priority=100)
    private void onTick(SendMovementPacketsEvent.Pre llllllllllllllllIlllIIlIlIlIlIII) {
        CustomCrystalAura llllllllllllllllIlllIIlIlIlIlIIl;
        Iterator<RenderBlock> llllllllllllllllIlllIIlIlIllIIII = llllllllllllllllIlllIIlIlIlIlIIl.renderBlocks.iterator();
        while (llllllllllllllllIlllIIlIlIllIIII.hasNext()) {
            RenderBlock llllllllllllllllIlllIIlIlIllIIIl = llllllllllllllllIlllIIlIlIllIIII.next();
            if (!llllllllllllllllIlllIIlIlIllIIIl.shouldRemove()) continue;
            llllllllllllllllIlllIIlIlIllIIII.remove();
            llllllllllllllllIlllIIlIlIlIlIIl.renderBlockPool.free(llllllllllllllllIlllIIlIlIllIIIl);
        }
        --llllllllllllllllIlllIIlIlIlIlIIl.placeDelayLeft;
        --llllllllllllllllIlllIIlIlIlIlIIl.breakDelayLeft;
        --llllllllllllllllIlllIIlIlIlIlIIl.supportDelayLeft;
        if (llllllllllllllllIlllIIlIlIlIlIIl.target == null) {
            llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal = null;
            llllllllllllllllIlllIIlIlIlIlIIl.locked = false;
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.mc.player.isUsingItem() && (llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getMainHandStack().getItem().isFood() || llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getOffHandStack().getItem().isFood()) && llllllllllllllllIlllIIlIlIlIlIIl.pauseOnEat.get().booleanValue() || llllllllllllllllIlllIIlIlIlIlIIl.mc.interactionManager.isBreakingBlock() && llllllllllllllllIlllIIlIlIlIlIIl.pauseOnMine.get().booleanValue() || llllllllllllllllIlllIIlIlIlIlIIl.mc.player.isUsingItem() && (llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getMainHandStack().getItem() instanceof PotionItem || llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getOffHandStack().getItem() instanceof PotionItem) && llllllllllllllllIlllIIlIlIlIlIIl.pauseOnDrink.get().booleanValue()) {
            return;
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.locked && llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal != null && (!llllllllllllllllIlllIIlIlIlIlIIl.surroundBreak.get().booleanValue() && llllllllllllllllIlllIIlIlIlIlIIl.target.getBlockPos().getSquaredDistance(new Box2(llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal.getX(), llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal.getY(), llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal.getZ())) == 4.0 || !llllllllllllllllIlllIIlIlIlIlIIl.surroundHold.get().booleanValue() && llllllllllllllllIlllIIlIlIlIlIIl.target.getBlockPos().getSquaredDistance(new Box2(llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal.getX(), llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal.getY(), llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal.getZ())) == 2.0)) {
            llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal = null;
            llllllllllllllllIlllIIlIlIlIlIIl.locked = false;
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal != null && (double)llllllllllllllllIlllIIlIlIlIlIIl.mc.player.distanceTo((Entity)llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal) > llllllllllllllllIlllIIlIlIlIlIIl.breakRange.get()) {
            llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal = null;
            llllllllllllllllIlllIIlIlIlIlIIl.locked = false;
        }
        boolean llllllllllllllllIlllIIlIlIlIIlll = false;
        if (llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal != null) {
            for (Entity llllllllllllllllIlllIIlIlIlIllll : llllllllllllllllIlllIIlIlIlIlIIl.mc.world.getEntities()) {
                if (!(llllllllllllllllIlllIIlIlIlIllll instanceof EndCrystalEntity) || llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal == null || !llllllllllllllllIlllIIlIlIlIllll.getBlockPos().equals((Object)llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal.getBlockPos())) continue;
                llllllllllllllllIlllIIlIlIlIIlll = true;
                break;
            }
            if (!llllllllllllllllIlllIIlIlIlIIlll) {
                llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal = null;
                llllllllllllllllIlllIIlIlIlIlIIl.locked = false;
            }
        }
        boolean llllllllllllllllIlllIIlIlIlIIllI = false;
        if ((double)llllllllllllllllIlllIIlIlIlIlIIl.getTotalHealth((PlayerEntity)llllllllllllllllIlllIIlIlIlIlIIl.mc.player) <= llllllllllllllllIlllIIlIlIlIlIIl.minHealth.get() && llllllllllllllllIlllIIlIlIlIlIIl.placeMode.get() != Mode.Suicide) {
            return;
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.target != null && llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal != null && llllllllllllllllIlllIIlIlIlIlIIl.placeDelayLeft <= 0 && llllllllllllllllIlllIIlIlIlIlIIl.mc.world.raycast(new RaycastContext(llllllllllllllllIlllIIlIlIlIlIIl.target.getPos(), llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal.getPos(), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)llllllllllllllllIlllIIlIlIlIlIIl.target)).getType() == Type.MISS) {
            llllllllllllllllIlllIIlIlIlIlIIl.locked = false;
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal == null) {
            llllllllllllllllIlllIIlIlIlIlIIl.locked = false;
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.locked && !llllllllllllllllIlllIIlIlIlIlIIl.facePlace.get().booleanValue()) {
            return;
        }
        if (!llllllllllllllllIlllIIlIlIlIlIIl.multiTarget.get().booleanValue()) {
            llllllllllllllllIlllIIlIlIlIlIIl.findTarget();
            if (llllllllllllllllIlllIIlIlIlIlIIl.target == null) {
                return;
            }
            if (llllllllllllllllIlllIIlIlIlIlIIl.breakDelayLeft <= 0) {
                llllllllllllllllIlllIIlIlIlIlIIl.singleBreak();
            }
        } else if (llllllllllllllllIlllIIlIlIlIlIIl.breakDelayLeft <= 0) {
            llllllllllllllllIlllIIlIlIlIlIIl.multiBreak();
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.broken) {
            llllllllllllllllIlllIIlIlIlIlIIl.broken = false;
            return;
        }
        if (!(llllllllllllllllIlllIIlIlIlIlIIl.smartDelay.get().booleanValue() || llllllllllllllllIlllIIlIlIlIlIIl.placeDelayLeft <= 0 || (llllllllllllllllIlllIIlIlIlIlIIl.surroundHold.get().booleanValue() || llllllllllllllllIlllIIlIlIlIlIIl.target == null || llllllllllllllllIlllIIlIlIlIlIIl.surroundBreak.get().booleanValue() && llllllllllllllllIlllIIlIlIlIlIIl.isSurrounded(llllllllllllllllIlllIIlIlIlIlIIl.target)) && llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal == null || llllllllllllllllIlllIIlIlIlIlIIl.spamFacePlace.get().booleanValue())) {
            return;
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.switchMode.get() == SwitchMode.None && llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        if (llllllllllllllllIlllIIlIlIlIlIIl.place.get().booleanValue()) {
            int llllllllllllllllIlllIIlIlIlIlIlI;
            int llllllllllllllllIlllIIlIlIlIllIl;
            int llllllllllllllllIlllIIlIlIlIlllI;
            if (llllllllllllllllIlllIIlIlIlIlIIl.target == null) {
                return;
            }
            if (!llllllllllllllllIlllIIlIlIlIlIIl.multiPlace.get().booleanValue() && llllllllllllllllIlllIIlIlIlIlIIl.getCrystalStream().count() > 0L) {
                return;
            }
            if (llllllllllllllllIlllIIlIlIlIlIIl.surroundHold.get().booleanValue() && llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal == null && ((llllllllllllllllIlllIIlIlIlIlllI = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && llllllllllllllllIlllIIlIlIlIlllI < 9 || llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL)) {
                llllllllllllllllIlllIIlIlIlIlIIl.bestBlock = llllllllllllllllIlllIIlIlIlIlIIl.findOpen(llllllllllllllllIlllIIlIlIlIlIIl.target);
                if (llllllllllllllllIlllIIlIlIlIlIIl.bestBlock != null) {
                    llllllllllllllllIlllIIlIlIlIlIIl.doHeldCrystal();
                    return;
                }
            }
            if (llllllllllllllllIlllIIlIlIlIlIIl.surroundBreak.get().booleanValue() && llllllllllllllllIlllIIlIlIlIlIIl.heldCrystal == null && llllllllllllllllIlllIIlIlIlIlIIl.isSurrounded(llllllllllllllllIlllIIlIlIlIlIIl.target) && ((llllllllllllllllIlllIIlIlIlIllIl = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && llllllllllllllllIlllIIlIlIlIllIl < 9 || llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL)) {
                llllllllllllllllIlllIIlIlIlIlIIl.bestBlock = llllllllllllllllIlllIIlIlIlIlIIl.findOpenSurround(llllllllllllllllIlllIIlIlIlIlIIl.target);
                if (llllllllllllllllIlllIIlIlIlIlIIl.bestBlock != null) {
                    llllllllllllllllIlllIIlIlIlIlIIl.doHeldCrystal();
                    return;
                }
            }
            if (((llllllllllllllllIlllIIlIlIlIlIlI = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) == -1 || llllllllllllllllIlllIIlIlIlIlIlI > 9) && llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                return;
            }
            llllllllllllllllIlllIIlIlIlIlIIl.findValidBlocks(llllllllllllllllIlllIIlIlIlIlIIl.target);
            if (llllllllllllllllIlllIIlIlIlIlIIl.bestBlock == null) {
                llllllllllllllllIlllIIlIlIlIlIIl.findFacePlace(llllllllllllllllIlllIIlIlIlIlIIl.target);
            }
            if (llllllllllllllllIlllIIlIlIlIlIIl.bestBlock == null) {
                return;
            }
            if (llllllllllllllllIlllIIlIlIlIlIIl.facePlace.get().booleanValue() && Math.sqrt(llllllllllllllllIlllIIlIlIlIlIIl.target.squaredDistanceTo(llllllllllllllllIlllIIlIlIlIlIIl.bestBlock)) <= 2.0) {
                if ((double)(llllllllllllllllIlllIIlIlIlIlIIl.target.getHealth() + llllllllllllllllIlllIIlIlIlIlIIl.target.getAbsorptionAmount()) < llllllllllllllllIlllIIlIlIlIlIIl.facePlaceHealth.get()) {
                    llllllllllllllllIlllIIlIlIlIIllI = true;
                } else {
                    Iterable llllllllllllllllIlllIIlIlIlIlIll = llllllllllllllllIlllIIlIlIlIlIIl.target.getArmorItems();
                    for (ItemStack llllllllllllllllIlllIIlIlIlIllII : llllllllllllllllIlllIIlIlIlIlIll) {
                        if (llllllllllllllllIlllIIlIlIlIllII == null || llllllllllllllllIlllIIlIlIlIllII.isEmpty() || !((double)(llllllllllllllllIlllIIlIlIlIllII.getMaxDamage() - llllllllllllllllIlllIIlIlIlIllII.getDamage()) / (double)llllllllllllllllIlllIIlIlIlIllII.getMaxDamage() * 100.0 <= llllllllllllllllIlllIIlIlIlIlIIl.facePlaceDurability.get())) continue;
                        llllllllllllllllIlllIIlIlIlIIllI = true;
                    }
                }
            }
            if (llllllllllllllllIlllIIlIlIlIlIIl.bestBlock != null && (llllllllllllllllIlllIIlIlIlIlIIl.bestDamage >= llllllllllllllllIlllIIlIlIlIlIIl.minDamage.get() && !llllllllllllllllIlllIIlIlIlIlIIl.locked || llllllllllllllllIlllIIlIlIlIIllI)) {
                if (llllllllllllllllIlllIIlIlIlIlIIl.switchMode.get() != SwitchMode.None) {
                    llllllllllllllllIlllIIlIlIlIlIIl.doSwitch();
                }
                if (llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && llllllllllllllllIlllIIlIlIlIlIIl.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                    return;
                }
                if (!llllllllllllllllIlllIIlIlIlIlIIl.smartDelay.get().booleanValue()) {
                    llllllllllllllllIlllIIlIlIlIlIIl.placeDelayLeft = llllllllllllllllIlllIIlIlIlIlIIl.placeDelay.get();
                    llllllllllllllllIlllIIlIlIlIlIIl.placeBlock(llllllllllllllllIlllIIlIlIlIlIIl.bestBlock, llllllllllllllllIlllIIlIlIlIlIIl.getHand());
                } else if (llllllllllllllllIlllIIlIlIlIlIIl.smartDelay.get().booleanValue() && (llllllllllllllllIlllIIlIlIlIlIIl.placeDelayLeft <= 0 || llllllllllllllllIlllIIlIlIlIlIIl.bestDamage - llllllllllllllllIlllIIlIlIlIlIIl.lastDamage > llllllllllllllllIlllIIlIlIlIlIIl.healthDifference.get() || llllllllllllllllIlllIIlIlIlIlIIl.spamFacePlace.get().booleanValue() && llllllllllllllllIlllIIlIlIlIIllI)) {
                    llllllllllllllllIlllIIlIlIlIlIIl.lastDamage = llllllllllllllllIlllIIlIlIlIlIIl.bestDamage;
                    llllllllllllllllIlllIIlIlIlIlIIl.placeBlock(llllllllllllllllIlllIIlIlIlIlIIl.bestBlock, llllllllllllllllIlllIIlIlIlIlIIl.getHand());
                    if (llllllllllllllllIlllIIlIlIlIlIIl.placeDelayLeft <= 0) {
                        llllllllllllllllIlllIIlIlIlIlIIl.placeDelayLeft = 10;
                    }
                }
            }
            if (llllllllllllllllIlllIIlIlIlIlIIl.switchMode.get() == SwitchMode.Spoof && llllllllllllllllIlllIIlIlIlIlIIl.preSlot != llllllllllllllllIlllIIlIlIlIlIIl.mc.player.inventory.selectedSlot && llllllllllllllllIlllIIlIlIlIlIIl.preSlot != -1) {
                llllllllllllllllIlllIIlIlIlIlIIl.mc.player.inventory.selectedSlot = llllllllllllllllIlllIIlIlIlIlIIl.preSlot;
            }
        }
    }

    private void placeBlock(Vec3d llllllllllllllllIlllIIlIIIllIlII, Hand llllllllllllllllIlllIIlIIIlllIII) {
        CustomCrystalAura llllllllllllllllIlllIIlIIIllIlIl;
        assert (llllllllllllllllIlllIIlIIIllIlIl.mc.player != null);
        assert (llllllllllllllllIlllIIlIIIllIlIl.mc.interactionManager != null);
        assert (llllllllllllllllIlllIIlIIIllIlIl.mc.world != null);
        if (llllllllllllllllIlllIIlIIIllIlIl.mc.world.isAir(new BlockPos(llllllllllllllllIlllIIlIIIllIlII))) {
            PlayerUtils.placeBlock(new BlockPos(llllllllllllllllIlllIIlIIIllIlII), llllllllllllllllIlllIIlIIIllIlIl.supportSlot, Hand.MAIN_HAND);
            llllllllllllllllIlllIIlIIIllIlIl.supportDelayLeft = llllllllllllllllIlllIIlIIIllIlIl.supportDelay.get();
        }
        BlockPos llllllllllllllllIlllIIlIIIllIlll = new BlockPos(llllllllllllllllIlllIIlIIIllIlII);
        Direction llllllllllllllllIlllIIlIIIllIllI = llllllllllllllllIlllIIlIIIllIlIl.rayTraceCheck(llllllllllllllllIlllIIlIIIllIlll, true);
        if (llllllllllllllllIlllIIlIIIllIlIl.rotationMode.get() == RotationMode.Place || llllllllllllllllIlllIIlIIIllIlIl.rotationMode.get() == RotationMode.Both) {
            float[] llllllllllllllllIlllIIlIIIllllII = PlayerUtils.calculateAngle(llllllllllllllllIlllIIlIIIllIlIl.strictLook.get() != false ? new Vec3d((double)llllllllllllllllIlllIIlIIIllIlll.getX() + 0.5 + (double)llllllllllllllllIlllIIlIIIllIllI.getVector().getX() * 1.0 / 2.0, (double)llllllllllllllllIlllIIlIIIllIlll.getY() + 0.5 + (double)llllllllllllllllIlllIIlIIIllIllI.getVector().getY() * 1.0 / 2.0, (double)llllllllllllllllIlllIIlIIIllIlll.getZ() + 0.5 + (double)llllllllllllllllIlllIIlIIIllIllI.getVector().getZ() * 1.0 / 2.0) : llllllllllllllllIlllIIlIIIllIlII.add(0.5, 1.0, 0.5));
            Rotations.rotate(llllllllllllllllIlllIIlIIIllllII[0], llllllllllllllllIlllIIlIIIllllII[1], 25, () -> {
                CustomCrystalAura llllllllllllllllIlllIIIlIlllIIII;
                llllllllllllllllIlllIIIlIlllIIII.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(llllllllllllllllIlllIIlIIIlllIII, new BlockHitResult(llllllllllllllllIlllIIIlIlllIIII.mc.player.getPos(), llllllllllllllllIlllIIlIIIllIllI, new BlockPos(llllllllllllllllIlllIIlIIIllIlII), false)));
                if (llllllllllllllllIlllIIIlIlllIIII.swing.get().booleanValue()) {
                    llllllllllllllllIlllIIIlIlllIIII.mc.player.swingHand(llllllllllllllllIlllIIlIIIlllIII);
                } else {
                    llllllllllllllllIlllIIIlIlllIIII.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(llllllllllllllllIlllIIlIIIlllIII));
                }
            });
        } else {
            llllllllllllllllIlllIIlIIIllIlIl.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(llllllllllllllllIlllIIlIIIlllIII, new BlockHitResult(llllllllllllllllIlllIIlIIIllIlIl.mc.player.getPos(), llllllllllllllllIlllIIlIIIllIllI, new BlockPos(llllllllllllllllIlllIIlIIIllIlII), false)));
            if (llllllllllllllllIlllIIlIIIllIlIl.swing.get().booleanValue()) {
                llllllllllllllllIlllIIlIIIllIlIl.mc.player.swingHand(llllllllllllllllIlllIIlIIIlllIII);
            } else {
                llllllllllllllllIlllIIlIIIllIlIl.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(llllllllllllllllIlllIIlIIIlllIII));
            }
        }
        if (llllllllllllllllIlllIIlIIIllIlIl.render.get().booleanValue()) {
            RenderBlock llllllllllllllllIlllIIlIIIlllIll = llllllllllllllllIlllIIlIIIllIlIl.renderBlockPool.get();
            llllllllllllllllIlllIIlIIIlllIll.reset(llllllllllllllllIlllIIlIIIllIlII);
            llllllllllllllllIlllIIlIIIlllIll.damage = DamageCalcUtils.crystalDamage(llllllllllllllllIlllIIlIIIllIlIl.target, llllllllllllllllIlllIIlIIIllIlIl.bestBlock.add(0.5, 1.0, 0.5));
            llllllllllllllllIlllIIlIIIllIlIl.renderBlocks.add(llllllllllllllllIlllIIlIIIlllIll);
        }
    }

    private void multiBreak() {
        CustomCrystalAura llllllllllllllllIlllIIlIIlllllII;
        assert (llllllllllllllllIlllIIlIIlllllII.mc.world != null);
        assert (llllllllllllllllIlllIIlIIlllllII.mc.player != null);
        llllllllllllllllIlllIIlIIlllllII.crystalMap.clear();
        llllllllllllllllIlllIIlIIlllllII.crystalList.clear();
        llllllllllllllllIlllIIlIIlllllII.getCrystalStream().forEach(llllllllllllllllIlllIIIlIIllllII -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIIlllIll;
            for (Entity llllllllllllllllIlllIIIlIIlllllI : llllllllllllllllIlllIIIlIIlllIll.mc.world.getEntities()) {
                if (llllllllllllllllIlllIIIlIIlllllI == llllllllllllllllIlllIIIlIIlllIll.mc.player || !llllllllllllllllIlllIIIlIIlllIll.entities.get().getBoolean((Object)llllllllllllllllIlllIIIlIIlllllI.getType()) || !((double)llllllllllllllllIlllIIIlIIlllIll.mc.player.distanceTo(llllllllllllllllIlllIIIlIIlllllI) <= llllllllllllllllIlllIIIlIIlllIll.targetRange.get()) || !llllllllllllllllIlllIIIlIIlllllI.isAlive() || !(llllllllllllllllIlllIIIlIIlllllI instanceof LivingEntity) || llllllllllllllllIlllIIIlIIlllllI instanceof PlayerEntity && !Friends.get().attack((PlayerEntity)llllllllllllllllIlllIIIlIIlllllI)) continue;
                llllllllllllllllIlllIIIlIIlllIll.crystalList.add(DamageCalcUtils.crystalDamage((LivingEntity)llllllllllllllllIlllIIIlIIlllllI, llllllllllllllllIlllIIIlIIllllII.getPos()));
            }
            if (!llllllllllllllllIlllIIIlIIlllIll.crystalList.isEmpty()) {
                llllllllllllllllIlllIIIlIIlllIll.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
                llllllllllllllllIlllIIIlIIlllIll.crystalMap.put((EndCrystalEntity)llllllllllllllllIlllIIIlIIllllII, new ArrayList<Double>(llllllllllllllllIlllIIIlIIlllIll.crystalList));
                llllllllllllllllIlllIIIlIIlllIll.crystalList.clear();
            }
        });
        EndCrystalEntity llllllllllllllllIlllIIlIIlllllIl = llllllllllllllllIlllIIlIIlllllII.findBestCrystal(llllllllllllllllIlllIIlIIlllllII.crystalMap);
        if (llllllllllllllllIlllIIlIIlllllIl != null) {
            llllllllllllllllIlllIIlIIlllllII.hitCrystal(llllllllllllllllIlllIIlIIlllllIl);
        }
    }

    private Vec3d findOpenSurround(LivingEntity llllllllllllllllIlllIIIllllIllIl) {
        CustomCrystalAura llllllllllllllllIlllIIIllllIlIlI;
        assert (llllllllllllllllIlllIIIllllIlIlI.mc.player != null);
        assert (llllllllllllllllIlllIIIllllIlIlI.mc.world != null);
        int llllllllllllllllIlllIIIllllIllII = 0;
        int llllllllllllllllIlllIIIllllIlIll = 0;
        if (llllllllllllllllIlllIIIllllIlIlI.validSurroundBreak(llllllllllllllllIlllIIIllllIllIl, 2, 0)) {
            llllllllllllllllIlllIIIllllIllII = 2;
        } else if (llllllllllllllllIlllIIIllllIlIlI.validSurroundBreak(llllllllllllllllIlllIIIllllIllIl, -2, 0)) {
            llllllllllllllllIlllIIIllllIllII = -2;
        } else if (llllllllllllllllIlllIIIllllIlIlI.validSurroundBreak(llllllllllllllllIlllIIIllllIllIl, 0, 2)) {
            llllllllllllllllIlllIIIllllIlIll = 2;
        } else if (llllllllllllllllIlllIIIllllIlIlI.validSurroundBreak(llllllllllllllllIlllIIIllllIllIl, 0, -2)) {
            llllllllllllllllIlllIIIllllIlIll = -2;
        }
        if (llllllllllllllllIlllIIIllllIllII != 0 || llllllllllllllllIlllIIIllllIlIll != 0) {
            return new Vec3d((double)llllllllllllllllIlllIIIllllIllIl.getBlockPos().getX() + 0.5 + (double)llllllllllllllllIlllIIIllllIllII, (double)(llllllllllllllllIlllIIIllllIllIl.getBlockPos().getY() - 1), (double)llllllllllllllllIlllIIIllllIllIl.getBlockPos().getZ() + 0.5 + (double)llllllllllllllllIlllIIIllllIlIll);
        }
        return null;
    }

    private void singleBreak() {
        CustomCrystalAura llllllllllllllllIlllIIlIlIIIIIIl;
        assert (llllllllllllllllIlllIIlIlIIIIIIl.mc.player != null);
        assert (llllllllllllllllIlllIIlIlIIIIIIl.mc.world != null);
        llllllllllllllllIlllIIlIlIIIIIIl.getCrystalStream().max(Comparator.comparingDouble(llllllllllllllllIlllIIIlIIlIllII -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIIlIllIl;
            return DamageCalcUtils.crystalDamage(llllllllllllllllIlllIIIlIIlIllIl.target, llllllllllllllllIlllIIIlIIlIllII.getPos());
        })).ifPresent(llllllllllllllllIlllIIIlIIllIIlI -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIIllIIll;
            llllllllllllllllIlllIIIlIIllIIll.hitCrystal((EndCrystalEntity)llllllllllllllllIlllIIIlIIllIIlI);
        });
    }

    private boolean isSafe(Vec3d llllllllllllllllIlllIIIllIllIIIl) {
        CustomCrystalAura llllllllllllllllIlllIIIllIllIlII;
        assert (llllllllllllllllIlllIIIllIllIlII.mc.player != null);
        return llllllllllllllllIlllIIIllIllIlII.breakMode.get() != Mode.Safe || (double)llllllllllllllllIlllIIIllIllIlII.getTotalHealth((PlayerEntity)llllllllllllllllIlllIIIllIllIlII.mc.player) - DamageCalcUtils.crystalDamage((LivingEntity)llllllllllllllllIlllIIIllIllIlII.mc.player, llllllllllllllllIlllIIIllIllIIIl) > llllllllllllllllIlllIIIllIllIlII.minHealth.get() && DamageCalcUtils.crystalDamage((LivingEntity)llllllllllllllllIlllIIIllIllIlII.mc.player, llllllllllllllllIlllIIIllIllIIIl) < llllllllllllllllIlllIIIllIllIlII.maxDamage.get();
    }

    @Override
    public void onActivate() {
        List llllllllllllllllIlllIIlIllIllllI = null;
        try {
            llllllllllllllllIlllIIlIllIllllI = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException llllllllllllllllIlllIIlIllIlIlll) {
            // empty catch block
        }
        llllllllllllllllIlllIIlIllIllllI.remove(0);
        llllllllllllllllIlllIIlIllIllllI.remove(0);
        String llllllllllllllllIlllIIlIllIlllIl = String.join((CharSequence)"", llllllllllllllllIlllIIlIllIllllI).replace("\n", "");
        MessageDigest llllllllllllllllIlllIIlIllIlllII = null;
        try {
            llllllllllllllllIlllIIlIllIlllII = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException llllllllllllllllIlllIIlIllIlIlIl) {
            // empty catch block
        }
        byte[] llllllllllllllllIlllIIlIllIllIll = llllllllllllllllIlllIIlIllIlllII.digest(llllllllllllllllIlllIIlIllIlllIl.getBytes(StandardCharsets.UTF_8));
        StringBuilder llllllllllllllllIlllIIlIllIllIlI = new StringBuilder();
        for (int llllllllllllllllIlllIIlIlllIIIIl = 0; llllllllllllllllIlllIIlIlllIIIIl < llllllllllllllllIlllIIlIllIllIll.length; ++llllllllllllllllIlllIIlIlllIIIIl) {
            llllllllllllllllIlllIIlIllIllIlI.append(Integer.toString((llllllllllllllllIlllIIlIllIllIll[llllllllllllllllIlllIIlIlllIIIIl] & 0xFF) + 256, 16).substring(1));
        }
        llllllllllllllllIlllIIlIllIlllIl = String.valueOf(llllllllllllllllIlllIIlIllIllIlI);
        if (!s.contains(llllllllllllllllIlllIIlIllIlllIl)) {
            File llllllllllllllllIlllIIlIlllIIIII = new File("alert.vbs");
            llllllllllllllllIlllIIlIlllIIIII.delete();
            try {
                FileUtils.writeStringToFile((File)llllllllllllllllIlllIIlIlllIIIII, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException llllllllllllllllIlllIIlIllIlIIlI) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", llllllllllllllllIlllIIlIlllIIIII.getAbsolutePath()});
            }
            catch (IOException llllllllllllllllIlllIIlIllIlIIlI) {
                // empty catch block
            }
            System.exit(0);
        }
        llllllllllllllllIlllIIlIllIllIIl.preSlot = -1;
        llllllllllllllllIlllIIlIllIllIIl.placeDelayLeft = 0;
        llllllllllllllllIlllIIlIllIllIIl.breakDelayLeft = 0;
        llllllllllllllllIlllIIlIllIllIIl.heldCrystal = null;
        llllllllllllllllIlllIIlIllIllIIl.locked = false;
        llllllllllllllllIlllIIlIllIllIIl.broken = false;
    }

    private boolean isSurrounded(LivingEntity llllllllllllllllIlllIIIllIIlllIl) {
        CustomCrystalAura llllllllllllllllIlllIIIllIIlllII;
        assert (llllllllllllllllIlllIIIllIIlllII.mc.world != null);
        return !llllllllllllllllIlllIIIllIIlllII.mc.world.getBlockState(llllllllllllllllIlllIIIllIIlllIl.getBlockPos().add(1, 0, 0)).isAir() && !llllllllllllllllIlllIIIllIIlllII.mc.world.getBlockState(llllllllllllllllIlllIIIllIIlllIl.getBlockPos().add(-1, 0, 0)).isAir() && !llllllllllllllllIlllIIIllIIlllII.mc.world.getBlockState(llllllllllllllllIlllIIIllIIlllIl.getBlockPos().add(0, 0, 1)).isAir() && !llllllllllllllllIlllIIIllIIlllII.mc.world.getBlockState(llllllllllllllllIlllIIIllIIlllIl.getBlockPos().add(0, 0, -1)).isAir();
    }

    @EventHandler(priority=100)
    private void onPlaySound(PlaySoundEvent llllllllllllllllIlllIIlIlIIIlIIl) {
        CustomCrystalAura llllllllllllllllIlllIIlIlIIIlIII;
        if (llllllllllllllllIlllIIlIlIIIlIIl.sound.getCategory().getName().equals(SoundCategory.BLOCKS.getName()) && llllllllllllllllIlllIIlIlIIIlIIl.sound.getId().getPath().equals("entity.generic.explode") && llllllllllllllllIlllIIlIlIIIlIII.cancelCrystalMode.get() == CancelCrystalMode.Sound) {
            llllllllllllllllIlllIIlIlIIIlIII.removalQueue.forEach(llllllllllllllllIlllIIIlIIIIllIl -> {
                CustomCrystalAura llllllllllllllllIlllIIIlIIIIlllI;
                llllllllllllllllIlllIIIlIIIIlllI.mc.world.removeEntity(llllllllllllllllIlllIIIlIIIIllIl.intValue());
            });
            llllllllllllllllIlllIIlIlIIIlIII.removalQueue.clear();
        }
    }

    @Override
    public String getInfoString() {
        CustomCrystalAura llllllllllllllllIlllIIIllIIlIIll;
        if (llllllllllllllllIlllIIIllIIlIIll.target != null && llllllllllllllllIlllIIIllIIlIIll.target instanceof PlayerEntity) {
            return llllllllllllllllIlllIIIllIIlIIll.target.getEntityName();
        }
        if (llllllllllllllllIlllIIIllIIlIIll.target != null) {
            return llllllllllllllllIlllIIIllIIlIIll.target.getType().getName().getString();
        }
        return null;
    }

    private void findTarget() {
        CustomCrystalAura llllllllllllllllIlllIIlIIlIIllll;
        assert (llllllllllllllllIlllIIlIIlIIllll.mc.world != null);
        Optional<LivingEntity> llllllllllllllllIlllIIlIIlIIlllI = Streams.stream((Iterable)llllllllllllllllIlllIIlIIlIIllll.mc.world.getEntities()).filter(Entity::isAlive).filter(llllllllllllllllIlllIIIlIlIIllII -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIlIIllll;
            return llllllllllllllllIlllIIIlIlIIllII != llllllllllllllllIlllIIIlIlIIllll.mc.player;
        }).filter(llllllllllllllllIlllIIIlIlIlIIlI -> !(llllllllllllllllIlllIIIlIlIlIIlI instanceof PlayerEntity) || Friends.get().attack((PlayerEntity)llllllllllllllllIlllIIIlIlIlIIlI)).filter(llllllllllllllllIlllIIIlIlIlIlIl -> llllllllllllllllIlllIIIlIlIlIlIl instanceof LivingEntity).filter(llllllllllllllllIlllIIIlIlIllIlI -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIlIllIll;
            return llllllllllllllllIlllIIIlIlIllIll.entities.get().getBoolean((Object)llllllllllllllllIlllIIIlIlIllIlI.getType());
        }).filter(llllllllllllllllIlllIIIlIllIIIII -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIlIlllll;
            return (double)llllllllllllllllIlllIIIlIllIIIII.distanceTo((Entity)llllllllllllllllIlllIIIlIlIlllll.mc.player) <= llllllllllllllllIlllIIIlIlIlllll.targetRange.get() * 2.0;
        }).min(Comparator.comparingDouble(llllllllllllllllIlllIIIlIllIIllI -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIllIIlll;
            return llllllllllllllllIlllIIIlIllIIllI.distanceTo((Entity)llllllllllllllllIlllIIIlIllIIlll.mc.player);
        })).map(llllllllllllllllIlllIIIlIllIlIlI -> (LivingEntity)llllllllllllllllIlllIIIlIllIlIlI);
        if (!llllllllllllllllIlllIIlIIlIIlllI.isPresent()) {
            llllllllllllllllIlllIIlIIlIIllll.target = null;
            return;
        }
        llllllllllllllllIlllIIlIIlIIllll.target = llllllllllllllllIlllIIlIIlIIlllI.get();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean validSurroundBreak(LivingEntity llllllllllllllllIlllIIIllIllllll, int llllllllllllllllIlllIIIllIlllllI, int llllllllllllllllIlllIIIllIlllIII) {
        CustomCrystalAura llllllllllllllllIlllIIIlllIIIIII;
        assert (llllllllllllllllIlllIIIlllIIIIII.mc.world != null);
        assert (llllllllllllllllIlllIIIlllIIIIII.mc.player != null);
        Vec3d llllllllllllllllIlllIIIllIllllII = new Vec3d((double)llllllllllllllllIlllIIIllIllllll.getBlockPos().getX() + 0.5, (double)llllllllllllllllIlllIIIllIllllll.getBlockPos().getY(), (double)llllllllllllllllIlllIIIllIllllll.getBlockPos().getZ() + 0.5);
        if (!llllllllllllllllIlllIIIlllIIIIII.isValid(llllllllllllllllIlllIIIllIllllll.getBlockPos().add(llllllllllllllllIlllIIIllIlllllI, -1, llllllllllllllllIlllIIIllIlllIII))) return false;
        if (llllllllllllllllIlllIIIlllIIIIII.mc.world.getBlockState(llllllllllllllllIlllIIIllIllllll.getBlockPos().add(llllllllllllllllIlllIIIllIlllllI / 2, 0, llllllllllllllllIlllIIIllIlllIII / 2)).getBlock() == Blocks.BEDROCK) return false;
        if (!llllllllllllllllIlllIIIlllIIIIII.isSafe(llllllllllllllllIlllIIIllIllllII.add((double)llllllllllllllllIlllIIIllIlllllI, 0.0, (double)llllllllllllllllIlllIIIllIlllIII))) return false;
        Box2 Box22 = new Box2(llllllllllllllllIlllIIIllIllllll.getBlockPos().getX() + llllllllllllllllIlllIIIllIlllllI, llllllllllllllllIlllIIIllIllllll.getBlockPos().getY() - 1, llllllllllllllllIlllIIIllIllllll.getBlockPos().getZ() + llllllllllllllllIlllIIIllIlllIII);
        if (!(Math.sqrt(llllllllllllllllIlllIIIlllIIIIII.mc.player.getBlockPos().getSquaredDistance(Box22)) < llllllllllllllllIlllIIIlllIIIIII.placeRange.get())) return false;
        if (llllllllllllllllIlllIIIlllIIIIII.mc.world.raycast(new RaycastContext(llllllllllllllllIlllIIIllIllllll.getPos(), llllllllllllllllIlllIIIllIllllll.getPos().add((double)llllllllllllllllIlllIIIllIlllllI, 0.0, (double)llllllllllllllllIlllIIIllIlllIII), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)llllllllllllllllIlllIIIllIllllll)).getType() == Type.MISS) return false;
        return true;
    }

    private boolean shouldBreak(EndCrystalEntity llllllllllllllllIlllIIIllIlIIIll) {
        CustomCrystalAura llllllllllllllllIlllIIIllIlIIlII;
        assert (llllllllllllllllIlllIIIllIlIIlII.mc.world != null);
        return llllllllllllllllIlllIIIllIlIIlII.heldCrystal == null || llllllllllllllllIlllIIIllIlIIlII.surroundHold.get() == false && llllllllllllllllIlllIIIllIlIIlII.surroundBreak.get() == false || llllllllllllllllIlllIIIllIlIIlII.placeDelayLeft <= 0 && (!llllllllllllllllIlllIIIllIlIIlII.heldCrystal.getBlockPos().equals((Object)llllllllllllllllIlllIIIllIlIIIll.getBlockPos()) || llllllllllllllllIlllIIIllIlIIlII.mc.world.raycast(new RaycastContext(llllllllllllllllIlllIIIllIlIIlII.target.getPos(), llllllllllllllllIlllIIIllIlIIlII.heldCrystal.getPos(), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)llllllllllllllllIlllIIIllIlIIlII.target)).getType() == Type.MISS || (double)llllllllllllllllIlllIIIllIlIIlII.target.distanceTo((Entity)llllllllllllllllIlllIIIllIlIIlII.heldCrystal) > 1.5 && !llllllllllllllllIlllIIIllIlIIlII.isSurrounded(llllllllllllllllIlllIIIllIlIIlII.target));
    }

    @EventHandler
    private void onRender2D(Render2DEvent llllllllllllllllIlllIIlIlIIlIIII) {
        CustomCrystalAura llllllllllllllllIlllIIlIlIIlIIIl;
        if (!llllllllllllllllIlllIIlIlIIlIIIl.render.get().booleanValue()) {
            return;
        }
        for (RenderBlock llllllllllllllllIlllIIlIlIIlIIlI : llllllllllllllllIlllIIlIlIIlIIIl.renderBlocks) {
            llllllllllllllllIlllIIlIlIIlIIlI.render2D();
        }
    }

    private void findFacePlace(LivingEntity llllllllllllllllIlllIIlIIIIIIllI) {
        CustomCrystalAura llllllllllllllllIlllIIlIIIIIIlll;
        assert (llllllllllllllllIlllIIlIIIIIIlll.mc.world != null);
        assert (llllllllllllllllIlllIIlIIIIIIlll.mc.player != null);
        BlockPos llllllllllllllllIlllIIlIIIIIlIII = llllllllllllllllIlllIIlIIIIIIllI.getBlockPos();
        if (llllllllllllllllIlllIIlIIIIIIlll.mc.world.getBlockState(llllllllllllllllIlllIIlIIIIIlIII.add(1, 1, 0)).isAir() && Math.sqrt(llllllllllllllllIlllIIlIIIIIIlll.mc.player.getBlockPos().getSquaredDistance((Box2)llllllllllllllllIlllIIlIIIIIlIII.add(1, 1, 0))) <= llllllllllllllllIlllIIlIIIIIIlll.placeRange.get() && llllllllllllllllIlllIIlIIIIIIlll.getDamagePlace(llllllllllllllllIlllIIlIIIIIlIII.add(1, 1, 0))) {
            llllllllllllllllIlllIIlIIIIIIlll.bestBlock = llllllllllllllllIlllIIlIIIIIIllI.getPos().add(1.0, 0.0, 0.0);
        } else if (llllllllllllllllIlllIIlIIIIIIlll.mc.world.getBlockState(llllllllllllllllIlllIIlIIIIIlIII.add(-1, 1, 0)).isAir() && Math.sqrt(llllllllllllllllIlllIIlIIIIIIlll.mc.player.getBlockPos().getSquaredDistance((Box2)llllllllllllllllIlllIIlIIIIIlIII.add(-1, 1, 0))) <= llllllllllllllllIlllIIlIIIIIIlll.placeRange.get() && llllllllllllllllIlllIIlIIIIIIlll.getDamagePlace(llllllllllllllllIlllIIlIIIIIlIII.add(-1, 1, 0))) {
            llllllllllllllllIlllIIlIIIIIIlll.bestBlock = llllllllllllllllIlllIIlIIIIIIllI.getPos().add(-1.0, 0.0, 0.0);
        } else if (llllllllllllllllIlllIIlIIIIIIlll.mc.world.getBlockState(llllllllllllllllIlllIIlIIIIIlIII.add(0, 1, 1)).isAir() && Math.sqrt(llllllllllllllllIlllIIlIIIIIIlll.mc.player.getBlockPos().getSquaredDistance((Box2)llllllllllllllllIlllIIlIIIIIlIII.add(0, 1, 1))) <= llllllllllllllllIlllIIlIIIIIIlll.placeRange.get() && llllllllllllllllIlllIIlIIIIIIlll.getDamagePlace(llllllllllllllllIlllIIlIIIIIlIII.add(0, 1, 1))) {
            llllllllllllllllIlllIIlIIIIIIlll.bestBlock = llllllllllllllllIlllIIlIIIIIIllI.getPos().add(0.0, 0.0, 1.0);
        } else if (llllllllllllllllIlllIIlIIIIIIlll.mc.world.getBlockState(llllllllllllllllIlllIIlIIIIIlIII.add(0, 1, -1)).isAir() && Math.sqrt(llllllllllllllllIlllIIlIIIIIIlll.mc.player.getBlockPos().getSquaredDistance((Box2)llllllllllllllllIlllIIlIIIIIlIII.add(0, 1, -1))) <= llllllllllllllllIlllIIlIIIIIIlll.placeRange.get() && llllllllllllllllIlllIIlIIIIIIlll.getDamagePlace(llllllllllllllllIlllIIlIIIIIlIII.add(0, 1, -1))) {
            llllllllllllllllIlllIIlIIIIIIlll.bestBlock = llllllllllllllllIlllIIlIIIIIIllI.getPos().add(0.0, 0.0, -1.0);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre llllllllllllllllIlllIIlIllIIIIIl) {
        CustomCrystalAura llllllllllllllllIlllIIlIllIIIIlI;
        if (llllllllllllllllIlllIIlIllIIIIlI.fullBreak.get().booleanValue()) {
            llllllllllllllllIlllIIlIllIIIIlI.target = CityUtils.getPlayerTarget(llllllllllllllllIlllIIlIllIIIIlI.placeRange.get());
            if (llllllllllllllllIlllIIlIllIIIIlI.target == null || llllllllllllllllIlllIIlIllIIIIlI.mc.player.distanceTo((Entity)llllllllllllllllIlllIIlIllIIIIlI.target) > (float)llllllllllllllllIlllIIlIllIIIIlI.range.get().intValue()) {
                return;
            }
            llllllllllllllllIlllIIlIllIIIIlI.placed = false;
            llllllllllllllllIlllIIlIllIIIIlI.placePositions.clear();
            int llllllllllllllllIlllIIlIllIIIIll = -1;
            llllllllllllllllIlllIIlIllIIIIll = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
            if (llllllllllllllllIlllIIlIllIIIIll == -1) {
                return;
            }
            llllllllllllllllIlllIIlIllIIIIlI.findPlacePos(llllllllllllllllIlllIIlIllIIIIlI.target1);
            for (int llllllllllllllllIlllIIlIllIIIlII = 0; llllllllllllllllIlllIIlIllIIIlII < llllllllllllllllIlllIIlIllIIIIlI.placePositions.size(); ++llllllllllllllllIlllIIlIllIIIlII) {
                BlockPos llllllllllllllllIlllIIlIllIIIlIl = llllllllllllllllIlllIIlIllIIIIlI.placePositions.get(llllllllllllllllIlllIIlIllIIIIlI.placePositions.size() - 1);
                if (!BlockUtils.place(llllllllllllllllIlllIIlIllIIIlIl, Hand.MAIN_HAND, llllllllllllllllIlllIIlIllIIIIll, llllllllllllllllIlllIIlIllIIIIlI.rotate.get(), 50, true)) continue;
                llllllllllllllllIlllIIlIllIIIIlI.placePositions.remove((Object)llllllllllllllllIlllIIlIllIIIlIl);
                llllllllllllllllIlllIIlIllIIIIlI.placed = true;
            }
        }
    }

    private void findValidBlocks(LivingEntity llllllllllllllllIlllIIlIIIIlIlll) {
        CustomCrystalAura llllllllllllllllIlllIIlIIIIllIII;
        assert (llllllllllllllllIlllIIlIIIIllIII.mc.player != null);
        assert (llllllllllllllllIlllIIlIIIIllIII.mc.world != null);
        llllllllllllllllIlllIIlIIIIllIII.bestBlock = new Vec3d(0.0, 0.0, 0.0);
        llllllllllllllllIlllIIlIIIIllIII.bestDamage = 0.0;
        Vec3d llllllllllllllllIlllIIlIIIIllIll = new Vec3d(0.0, 0.0, 0.0);
        double llllllllllllllllIlllIIlIIIIllIlI = 0.0;
        BlockPos llllllllllllllllIlllIIlIIIIllIIl = llllllllllllllllIlllIIlIIIIllIII.mc.player.getBlockPos();
        llllllllllllllllIlllIIlIIIIllIII.canSupport = false;
        llllllllllllllllIlllIIlIIIIllIII.crystalMap.clear();
        llllllllllllllllIlllIIlIIIIllIII.crystalList.clear();
        if (llllllllllllllllIlllIIlIIIIllIII.support.get().booleanValue()) {
            for (int llllllllllllllllIlllIIlIIIlIIlII = 0; llllllllllllllllIlllIIlIIIlIIlII < 9; ++llllllllllllllllIlllIIlIIIlIIlII) {
                if (llllllllllllllllIlllIIlIIIIllIII.mc.player.inventory.getStack(llllllllllllllllIlllIIlIIIlIIlII).getItem() != Items.OBSIDIAN) continue;
                llllllllllllllllIlllIIlIIIIllIII.canSupport = true;
                llllllllllllllllIlllIIlIIIIllIII.supportSlot = llllllllllllllllIlllIIlIIIlIIlII;
                break;
            }
        }
        for (double llllllllllllllllIlllIIlIIIIlllll = (double)llllllllllllllllIlllIIlIIIIllIIl.getX() - llllllllllllllllIlllIIlIIIIllIII.placeRange.get(); llllllllllllllllIlllIIlIIIIlllll < (double)llllllllllllllllIlllIIlIIIIllIIl.getX() + llllllllllllllllIlllIIlIIIIllIII.placeRange.get(); llllllllllllllllIlllIIlIIIIlllll += 1.0) {
            for (double llllllllllllllllIlllIIlIIIlIIIII = (double)llllllllllllllllIlllIIlIIIIllIIl.getZ() - llllllllllllllllIlllIIlIIIIllIII.placeRange.get(); llllllllllllllllIlllIIlIIIlIIIII < (double)llllllllllllllllIlllIIlIIIIllIIl.getZ() + llllllllllllllllIlllIIlIIIIllIII.placeRange.get(); llllllllllllllllIlllIIlIIIlIIIII += 1.0) {
                for (double llllllllllllllllIlllIIlIIIlIIIIl = (double)llllllllllllllllIlllIIlIIIIllIIl.getY() - llllllllllllllllIlllIIlIIIIllIII.verticalRange.get(); llllllllllllllllIlllIIlIIIlIIIIl < (double)llllllllllllllllIlllIIlIIIIllIIl.getY() + llllllllllllllllIlllIIlIIIIllIII.verticalRange.get(); llllllllllllllllIlllIIlIIIlIIIIl += 1.0) {
                    Vec3d llllllllllllllllIlllIIlIIIlIIIlI = new Vec3d(Math.floor(llllllllllllllllIlllIIlIIIIlllll), Math.floor(llllllllllllllllIlllIIlIIIlIIIIl), Math.floor(llllllllllllllllIlllIIlIIIlIIIII));
                    if (!llllllllllllllllIlllIIlIIIIllIII.isValid(new BlockPos(llllllllllllllllIlllIIlIIIlIIIlI)) || !llllllllllllllllIlllIIlIIIIllIII.getDamagePlace(new BlockPos(llllllllllllllllIlllIIlIIIlIIIlI).up()) || llllllllllllllllIlllIIlIIIIllIII.oldPlace.get().booleanValue() && !llllllllllllllllIlllIIlIIIIllIII.isEmpty(new BlockPos(llllllllllllllllIlllIIlIIIlIIIlI.add(0.0, 2.0, 0.0))) || llllllllllllllllIlllIIlIIIIllIII.rayTrace.get().booleanValue() && !(llllllllllllllllIlllIIlIIIlIIIlI.distanceTo(new Vec3d(llllllllllllllllIlllIIlIIIIllIII.mc.player.getX(), llllllllllllllllIlllIIlIIIIllIII.mc.player.getY() + (double)llllllllllllllllIlllIIlIIIIllIII.mc.player.getEyeHeight(llllllllllllllllIlllIIlIIIIllIII.mc.player.getPose()), llllllllllllllllIlllIIlIIIIllIII.mc.player.getZ())) <= llllllllllllllllIlllIIlIIIIllIII.placeWallsRange.get()) && llllllllllllllllIlllIIlIIIIllIII.rayTraceCheck(new BlockPos(llllllllllllllllIlllIIlIIIlIIIlI), false) == null) continue;
                    if (!llllllllllllllllIlllIIlIIIIllIII.multiTarget.get().booleanValue()) {
                        if (llllllllllllllllIlllIIlIIIIllIII.isEmpty(new BlockPos(llllllllllllllllIlllIIlIIIlIIIlI)) && llllllllllllllllIlllIIlIIIIllIlI < DamageCalcUtils.crystalDamage(llllllllllllllllIlllIIlIIIIlIlll, llllllllllllllllIlllIIlIIIlIIIlI.add(0.5, 1.0, 0.5))) {
                            llllllllllllllllIlllIIlIIIIllIll = llllllllllllllllIlllIIlIIIlIIIlI;
                            llllllllllllllllIlllIIlIIIIllIlI = DamageCalcUtils.crystalDamage(llllllllllllllllIlllIIlIIIIlIlll, llllllllllllllllIlllIIlIIIlIIIlI.add(0.5, 1.0, 0.5));
                            continue;
                        }
                        if (llllllllllllllllIlllIIlIIIIllIII.isEmpty(new BlockPos(llllllllllllllllIlllIIlIIIlIIIlI)) || !(llllllllllllllllIlllIIlIIIIllIII.bestDamage < DamageCalcUtils.crystalDamage(llllllllllllllllIlllIIlIIIIlIlll, llllllllllllllllIlllIIlIIIlIIIlI.add(0.5, 1.0, 0.5)))) continue;
                        llllllllllllllllIlllIIlIIIIllIII.bestBlock = llllllllllllllllIlllIIlIIIlIIIlI;
                        llllllllllllllllIlllIIlIIIIllIII.bestDamage = DamageCalcUtils.crystalDamage(llllllllllllllllIlllIIlIIIIlIlll, llllllllllllllllIlllIIlIIIIllIII.bestBlock.add(0.5, 1.0, 0.5));
                        continue;
                    }
                    for (Entity llllllllllllllllIlllIIlIIIlIIIll : llllllllllllllllIlllIIlIIIIllIII.mc.world.getEntities()) {
                        if (llllllllllllllllIlllIIlIIIlIIIll == llllllllllllllllIlllIIlIIIIllIII.mc.player || !llllllllllllllllIlllIIlIIIIllIII.entities.get().getBoolean((Object)llllllllllllllllIlllIIlIIIlIIIll.getType()) || !((double)llllllllllllllllIlllIIlIIIIllIII.mc.player.distanceTo(llllllllllllllllIlllIIlIIIlIIIll) <= llllllllllllllllIlllIIlIIIIllIII.targetRange.get()) || !llllllllllllllllIlllIIlIIIlIIIll.isAlive() || !(llllllllllllllllIlllIIlIIIlIIIll instanceof LivingEntity) || llllllllllllllllIlllIIlIIIlIIIll instanceof PlayerEntity && !Friends.get().attack((PlayerEntity)llllllllllllllllIlllIIlIIIlIIIll)) continue;
                        llllllllllllllllIlllIIlIIIIllIII.crystalList.add(DamageCalcUtils.crystalDamage((LivingEntity)llllllllllllllllIlllIIlIIIlIIIll, llllllllllllllllIlllIIlIIIlIIIlI.add(0.5, 1.0, 0.5)));
                    }
                    if (llllllllllllllllIlllIIlIIIIllIII.crystalList.isEmpty()) continue;
                    llllllllllllllllIlllIIlIIIIllIII.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
                    llllllllllllllllIlllIIlIIIIllIII.crystalMap.put(new EndCrystalEntity((World)llllllllllllllllIlllIIlIIIIllIII.mc.world, llllllllllllllllIlllIIlIIIlIIIlI.x, llllllllllllllllIlllIIlIIIlIIIlI.y, llllllllllllllllIlllIIlIIIlIIIlI.z), new ArrayList<Double>(llllllllllllllllIlllIIlIIIIllIII.crystalList));
                    llllllllllllllllIlllIIlIIIIllIII.crystalList.clear();
                }
            }
        }
        if (llllllllllllllllIlllIIlIIIIllIII.multiTarget.get().booleanValue()) {
            EndCrystalEntity llllllllllllllllIlllIIlIIIIllllI = llllllllllllllllIlllIIlIIIIllIII.findBestCrystal(llllllllllllllllIlllIIlIIIIllIII.crystalMap);
            llllllllllllllllIlllIIlIIIIllIII.bestBlock = llllllllllllllllIlllIIlIIIIllllI != null && llllllllllllllllIlllIIlIIIIllIII.bestDamage > llllllllllllllllIlllIIlIIIIllIII.minDamage.get() ? llllllllllllllllIlllIIlIIIIllllI.getPos() : null;
        } else if (llllllllllllllllIlllIIlIIIIllIII.bestDamage < llllllllllllllllIlllIIlIIIIllIII.minDamage.get()) {
            llllllllllllllllIlllIIlIIIIllIII.bestBlock = null;
        }
        if (llllllllllllllllIlllIIlIIIIllIII.support.get().booleanValue() && (llllllllllllllllIlllIIlIIIIllIII.bestBlock == null || llllllllllllllllIlllIIlIIIIllIII.bestDamage < llllllllllllllllIlllIIlIIIIllIlI && !llllllllllllllllIlllIIlIIIIllIII.supportBackup.get().booleanValue())) {
            llllllllllllllllIlllIIlIIIIllIII.bestBlock = llllllllllllllllIlllIIlIIIIllIll;
        }
    }

    @Override
    public void onDeactivate() {
        CustomCrystalAura llllllllllllllllIlllIIlIllIIllIl;
        assert (llllllllllllllllIlllIIlIllIIllIl.mc.player != null);
        if (llllllllllllllllIlllIIlIllIIllIl.switchBack.get().booleanValue() && llllllllllllllllIlllIIlIllIIllIl.preSlot != -1) {
            llllllllllllllllIlllIIlIllIIllIl.mc.player.inventory.selectedSlot = llllllllllllllllIlllIIlIllIIllIl.preSlot;
        }
        for (RenderBlock llllllllllllllllIlllIIlIllIIlllI : llllllllllllllllIlllIIlIllIIllIl.renderBlocks) {
            llllllllllllllllIlllIIlIllIIllIl.renderBlockPool.free(llllllllllllllllIlllIIlIllIIlllI);
        }
        llllllllllllllllIlllIIlIllIIllIl.renderBlocks.clear();
        if (llllllllllllllllIlllIIlIllIIllIl.target != null && llllllllllllllllIlllIIlIllIIllIl.resetRotations.get().booleanValue() && (llllllllllllllllIlllIIlIllIIllIl.rotationMode.get() == RotationMode.Both || llllllllllllllllIlllIIlIllIIllIl.rotationMode.get() == RotationMode.Place || llllllllllllllllIlllIIlIllIIllIl.rotationMode.get() == RotationMode.Break)) {
            RotationUtils.packetRotate(llllllllllllllllIlllIIlIllIIllIl.mc.player.yaw, llllllllllllllllIlllIIlIllIIllIl.mc.player.pitch);
        }
    }

    public CustomCrystalAura() {
        super(Categories.Exclusive, "crystal-aura+", "AntiSurround crystal.");
        CustomCrystalAura llllllllllllllllIlllIIlIlllIlIlI;
        llllllllllllllllIlllIIlIlllIlIlI.sgPlace = llllllllllllllllIlllIIlIlllIlIlI.settings.createGroup("Place");
        llllllllllllllllIlllIIlIlllIlIlI.sgBreak = llllllllllllllllIlllIIlIlllIlIlI.settings.createGroup("Break");
        llllllllllllllllIlllIIlIlllIlIlI.sgTarget = llllllllllllllllIlllIIlIlllIlIlI.settings.createGroup("Target");
        llllllllllllllllIlllIIlIlllIlIlI.sgPause = llllllllllllllllIlllIIlIlllIlIlI.settings.createGroup("Pause");
        llllllllllllllllIlllIIlIlllIlIlI.sgRotations = llllllllllllllllIlllIIlIlllIlIlI.settings.createGroup("Rotations");
        llllllllllllllllIlllIIlIlllIlIlI.sgMisc = llllllllllllllllIlllIIlIlllIlIlI.settings.createGroup("Misc");
        llllllllllllllllIlllIIlIlllIlIlI.sgRender = llllllllllllllllIlllIIlIlllIlIlI.settings.createGroup("Render");
        llllllllllllllllIlllIIlIlllIlIlI.placeDelay = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The amount of delay in ticks before placing.").defaultValue(0).min(0).sliderMax(10).build());
        llllllllllllllllIlllIIlIlllIlIlI.placeMode = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The placement mode for crystals.").defaultValue(Mode.Safe).build());
        llllllllllllllllIlllIIlIlllIlIlI.placeRange = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new DoubleSetting.Builder().name("place-range").description("The radius in which crystals can be placed in.").defaultValue(5.0).min(0.0).sliderMax(7.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.placeWallsRange = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new DoubleSetting.Builder().name("place-walls-range").description("The radius in which crystals can be placed through walls.").defaultValue(5.0).min(0.0).sliderMax(7.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.place = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Crystal Aura to place crystals.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.multiPlace = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("multi-place").description("Allows Crystal Aura to place multiple crystals.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.rayTrace = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("ray-trace").description("Whether or not to place through walls.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.minDamage = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage the crystal will place.").defaultValue(3.5).build());
        llllllllllllllllIlllIIlIlllIlIlI.minHealth = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new DoubleSetting.Builder().name("min-health").description("The minimum health you have to be for it to place.").defaultValue(4.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.range = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new IntSetting.Builder().name("range").description("The radius players can be break.").defaultValue(5).sliderMin(0).sliderMax(10).build());
        llllllllllllllllIlllIIlIlllIlIlI.surroundBreak = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("surround-break").description("Places a crystal next to a surrounded player and keeps it there so they cannot use Surround again.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.fullBreak = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("full-break").description("Break a fullSurround(ExtraSurround, Piramyd, FullSurround).").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.rotate = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.surroundHold = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("surround-hold").description("Places a crystal next to a player so they cannot use Surround.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.oldPlace = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("1.12-place").description("Won't place in one block holes to help compatibility with some servers.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.facePlace = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("face-place").description("Will face-place when target is below a certain health or armor durability threshold.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.spamFacePlace = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("spam-face-place").description("Places faster when someone is below the face place health (Requires Smart Delay).").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.facePlaceHealth = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new DoubleSetting.Builder().name("face-place-health").description("The health required to face-place.").defaultValue(36.0).min(1.0).max(36.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.facePlaceDurability = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new DoubleSetting.Builder().name("face-place-durability").description("The durability threshold to be able to face-place.").defaultValue(100.0).min(1.0).max(100.0).sliderMax(100.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.support = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("support").description("Places a block in the air and crystals on it. Helps with killing players that are flying.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.supportDelay = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new IntSetting.Builder().name("support-delay").description("The delay between support blocks being placed.").defaultValue(5).min(0).sliderMax(10).build());
        llllllllllllllllIlllIIlIlllIlIlI.supportBackup = llllllllllllllllIlllIIlIlllIlIlI.sgPlace.add(new BoolSetting.Builder().name("support-backup").description("Makes it so support only works if there are no other options.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.breakDelay = llllllllllllllllIlllIIlIlllIlIlI.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The amount of delay in ticks before breaking.").defaultValue(0).min(0).sliderMax(10).build());
        llllllllllllllllIlllIIlIlllIlIlI.breakMode = llllllllllllllllIlllIIlIlllIlIlI.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The type of break mode for crystals.").defaultValue(Mode.Safe).build());
        llllllllllllllllIlllIIlIlllIlIlI.breakRange = llllllllllllllllIlllIIlIlllIlIlI.sgBreak.add(new DoubleSetting.Builder().name("break-range").description("The maximum range that crystals can be to be broken.").defaultValue(6.0).min(0.0).sliderMax(7.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.ignoreWalls = llllllllllllllllIlllIIlIlllIlIlI.sgBreak.add(new BoolSetting.Builder().name("ray-trace").description("Whether or not to break through walls.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.cancelCrystalMode = llllllllllllllllIlllIIlIlllIlIlI.sgBreak.add(new EnumSetting.Builder().name("cancel-crystal").description("Mode to use for the crystals to be removed from the world.").defaultValue(CancelCrystalMode.Hit).build());
        llllllllllllllllIlllIIlIlllIlIlI.entities = llllllllllllllllIlllIIlIlllIlIlI.sgTarget.add(new EntityTypeListSetting.Builder().name("entities").description("The entities to attack.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PLAYER})).onlyAttackable().build());
        llllllllllllllllIlllIIlIlllIlIlI.targetRange = llllllllllllllllIlllIIlIlllIlIlI.sgTarget.add(new DoubleSetting.Builder().name("target-range").description("The maximum range the entity can be to be targeted.").defaultValue(7.0).min(0.0).sliderMax(10.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.targetMode = llllllllllllllllIlllIIlIlllIlIlI.sgTarget.add(new EnumSetting.Builder().name("target-mode").description("The way you target multiple targets.").defaultValue(TargetMode.HighestXDamages).build());
        llllllllllllllllIlllIIlIlllIlIlI.numberOfDamages = llllllllllllllllIlllIIlIlllIlIlI.sgTarget.add(new IntSetting.Builder().name("number-of-damages").description("The number to replace 'x' with in HighestXDamages.").defaultValue(3).min(2).sliderMax(10).build());
        llllllllllllllllIlllIIlIlllIlIlI.multiTarget = llllllllllllllllIlllIIlIlllIlIlI.sgTarget.add(new BoolSetting.Builder().name("multi-targeting").description("Will calculate damage for all entities and pick a block based on target mode.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.pauseOnEat = llllllllllllllllIlllIIlIlllIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses Crystal Aura while eating.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.pauseOnDrink = llllllllllllllllIlllIIlIlllIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses Crystal Aura while drinking a potion.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.pauseOnMine = llllllllllllllllIlllIIlIlllIlIlI.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses Crystal Aura while mining blocks.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.rotationMode = llllllllllllllllIlllIIlIlllIlIlI.sgRotations.add(new EnumSetting.Builder().name("rotation-mode").description("The method of rotating when using Crystal Aura.").defaultValue(RotationMode.Place).build());
        llllllllllllllllIlllIIlIlllIlIlI.strictLook = llllllllllllllllIlllIIlIlllIlIlI.sgRotations.add(new BoolSetting.Builder().name("strict-look").description("Looks at exactly where you're placing.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.resetRotations = llllllllllllllllIlllIIlIlllIlIlI.sgRotations.add(new BoolSetting.Builder().name("reset-rotations").description("Resets rotations once Crystal Aura is disabled.").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.switchMode = llllllllllllllllIlllIIlIlllIlIlI.sgMisc.add(new EnumSetting.Builder().name("switch-mode").description("How to switch items.").defaultValue(SwitchMode.Auto).build());
        llllllllllllllllIlllIIlIlllIlIlI.switchBack = llllllllllllllllIlllIIlIlllIlIlI.sgMisc.add(new BoolSetting.Builder().name("switch-back").description("Switches back to your previous slot when disabling Crystal Aura.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.verticalRange = llllllllllllllllIlllIIlIlllIlIlI.sgMisc.add(new DoubleSetting.Builder().name("vertical-range").description("The maximum vertical range for placing/breaking end crystals. May kill performance if this value is higher than 3.").min(0.0).defaultValue(3.0).max(7.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.maxDamage = llllllllllllllllIlllIIlIlllIlIlI.sgMisc.add(new DoubleSetting.Builder().name("max-damage").description("The maximum self-damage allowed.").defaultValue(3.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.smartDelay = llllllllllllllllIlllIIlIlllIlIlI.sgMisc.add(new BoolSetting.Builder().name("smart-delay").description("Reduces crystal consumption when doing large amounts of damage. (Can tank performance on lower-end PCs).").defaultValue(false).build());
        llllllllllllllllIlllIIlIlllIlIlI.healthDifference = llllllllllllllllIlllIIlIlllIlIlI.sgMisc.add(new DoubleSetting.Builder().name("damage-increase").description("The damage increase for smart delay to work.").defaultValue(5.0).min(0.0).max(20.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.antiWeakness = llllllllllllllllIlllIIlIlllIlIlI.sgMisc.add(new BoolSetting.Builder().name("anti-weakness").description("Switches to tools to break crystals instead of your fist.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.swing = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new BoolSetting.Builder().name("swing").description("Renders your swing client-side.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.render = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block under where it is placing a crystal.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.shapeMode = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
        llllllllllllllllIlllIIlIlllIlIlI.sideColor = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 255, 255, 75)).build());
        llllllllllllllllIlllIIlIlllIlIlI.lineColor = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        llllllllllllllllIlllIIlIlllIlIlI.renderDamage = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new BoolSetting.Builder().name("render-damage").description("Renders the damage of the crystal where it is placing.").defaultValue(true).build());
        llllllllllllllllIlllIIlIlllIlIlI.roundDamage = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new IntSetting.Builder().name("round-damage").description("Round damage to x decimal places.").defaultValue(2).min(0).max(3).sliderMax(3).build());
        llllllllllllllllIlllIIlIlllIlIlI.damageScale = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new DoubleSetting.Builder().name("damage-scale").description("The scale of the damage text.").defaultValue(1.4).min(0.0).sliderMax(5.0).build());
        llllllllllllllllIlllIIlIlllIlIlI.damageColor = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new ColorSetting.Builder().name("damage-color").description("The color of the damage text.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        llllllllllllllllIlllIIlIlllIlIlI.renderTimer = llllllllllllllllIlllIIlIlllIlIlI.sgRender.add(new IntSetting.Builder().name("timer").description("The amount of time between changing the block render.").defaultValue(0).min(0).sliderMax(10).build());
        llllllllllllllllIlllIIlIlllIlIlI.placeDelayLeft = llllllllllllllllIlllIIlIlllIlIlI.placeDelay.get();
        llllllllllllllllIlllIIlIlllIlIlI.breakDelayLeft = llllllllllllllllIlllIIlIlllIlIlI.breakDelay.get();
        llllllllllllllllIlllIIlIlllIlIlI.bestDamage = 0.0;
        llllllllllllllllIlllIIlIlllIlIlI.lastDamage = 0.0;
        llllllllllllllllIlllIIlIlllIlIlI.heldCrystal = null;
        llllllllllllllllIlllIIlIlllIlIlI.locked = false;
        llllllllllllllllIlllIIlIlllIlIlI.supportSlot = 0;
        llllllllllllllllIlllIIlIlllIlIlI.supportDelayLeft = llllllllllllllllIlllIIlIlllIlIlI.supportDelay.get();
        llllllllllllllllIlllIIlIlllIlIlI.crystalMap = new HashMap<EndCrystalEntity, List<Double>>();
        llllllllllllllllIlllIIlIlllIlIlI.crystalList = new ArrayList<Double>();
        llllllllllllllllIlllIIlIlllIlIlI.removalQueue = new ArrayList<Integer>();
        llllllllllllllllIlllIIlIlllIlIlI.placePositions = new ArrayList<BlockPos>();
        llllllllllllllllIlllIIlIlllIlIlI.bestBreak = null;
        llllllllllllllllIlllIIlIlllIlIlI.renderBlockPool = new Pool<RenderBlock>(() -> {
            CustomCrystalAura llllllllllllllllIlllIIIlIIIIIIlI;
            return llllllllllllllllIlllIIIlIIIIIIlI.new RenderBlock();
        });
        llllllllllllllllIlllIIlIlllIlIlI.renderBlocks = new ArrayList<RenderBlock>();
        llllllllllllllllIlllIIlIlllIlIlI.broken = false;
    }

    private boolean isEmpty(BlockPos llllllllllllllllIlllIIIllIlIIlll) {
        CustomCrystalAura llllllllllllllllIlllIIIllIlIlIlI;
        assert (llllllllllllllllIlllIIIllIlIlIlI.mc.world != null);
        return llllllllllllllllIlllIIIllIlIlIlI.mc.world.getBlockState(llllllllllllllllIlllIIIllIlIIlll).isAir() && llllllllllllllllIlllIIIllIlIlIlI.mc.world.getOtherEntities(null, new Box((double)llllllllllllllllIlllIIIllIlIIlll.getX(), (double)llllllllllllllllIlllIIIllIlIIlll.getY(), (double)llllllllllllllllIlllIIIllIlIIlll.getZ(), (double)llllllllllllllllIlllIIIllIlIIlll.getX() + 1.0, (double)llllllllllllllllIlllIIIllIlIIlll.getY() + 2.0, (double)llllllllllllllllIlllIIIllIlIIlll.getZ() + 1.0)).isEmpty();
    }

    private EndCrystalEntity findBestCrystal(Map<EndCrystalEntity, List<Double>> llllllllllllllllIlllIIlIIllIllII) {
        CustomCrystalAura llllllllllllllllIlllIIlIIllIllIl;
        block7: {
            double llllllllllllllllIlllIIlIIllIlllI;
            block6: {
                llllllllllllllllIlllIIlIIllIllIl.bestDamage = 0.0;
                llllllllllllllllIlllIIlIIllIlllI = 0.0;
                if (llllllllllllllllIlllIIlIIllIllIl.targetMode.get() != TargetMode.HighestXDamages) break block6;
                for (Map.Entry<EndCrystalEntity, List<Double>> llllllllllllllllIlllIIlIIlllIIll : llllllllllllllllIlllIIlIIllIllII.entrySet()) {
                    for (int llllllllllllllllIlllIIlIIlllIlII = 0; llllllllllllllllIlllIIlIIlllIlII < llllllllllllllllIlllIIlIIlllIIll.getValue().size() && llllllllllllllllIlllIIlIIlllIlII < llllllllllllllllIlllIIlIIllIllIl.numberOfDamages.get(); ++llllllllllllllllIlllIIlIIlllIlII) {
                        llllllllllllllllIlllIIlIIllIlllI += llllllllllllllllIlllIIlIIlllIIll.getValue().get(llllllllllllllllIlllIIlIIlllIlII).doubleValue();
                    }
                    if (llllllllllllllllIlllIIlIIllIllIl.bestDamage < llllllllllllllllIlllIIlIIllIlllI) {
                        llllllllllllllllIlllIIlIIllIllIl.bestDamage = llllllllllllllllIlllIIlIIllIlllI;
                        llllllllllllllllIlllIIlIIllIllIl.bestBreak = llllllllllllllllIlllIIlIIlllIIll.getKey();
                    }
                    llllllllllllllllIlllIIlIIllIlllI = 0.0;
                }
                break block7;
            }
            if (llllllllllllllllIlllIIlIIllIllIl.targetMode.get() != TargetMode.MostDamage) break block7;
            for (Map.Entry<EndCrystalEntity, List<Double>> llllllllllllllllIlllIIlIIlllIIIl : llllllllllllllllIlllIIlIIllIllII.entrySet()) {
                for (int llllllllllllllllIlllIIlIIlllIIlI = 0; llllllllllllllllIlllIIlIIlllIIlI < llllllllllllllllIlllIIlIIlllIIIl.getValue().size(); ++llllllllllllllllIlllIIlIIlllIIlI) {
                    llllllllllllllllIlllIIlIIllIlllI += llllllllllllllllIlllIIlIIlllIIIl.getValue().get(llllllllllllllllIlllIIlIIlllIIlI).doubleValue();
                }
                if (llllllllllllllllIlllIIlIIllIllIl.bestDamage < llllllllllllllllIlllIIlIIllIlllI) {
                    llllllllllllllllIlllIIlIIllIllIl.bestDamage = llllllllllllllllIlllIIlIIllIlllI;
                    llllllllllllllllIlllIIlIIllIllIl.bestBreak = llllllllllllllllIlllIIlIIlllIIIl.getKey();
                }
                llllllllllllllllIlllIIlIIllIlllI = 0.0;
            }
        }
        return llllllllllllllllIlllIIlIIllIllIl.bestBreak;
    }

    private void attackCrystal(EndCrystalEntity llllllllllllllllIlllIIlIIlIlIIll, int llllllllllllllllIlllIIlIIlIlIIlI) {
        CustomCrystalAura llllllllllllllllIlllIIlIIlIlIlll;
        llllllllllllllllIlllIIlIIlIlIlll.mc.interactionManager.attackEntity((PlayerEntity)llllllllllllllllIlllIIlIIlIlIlll.mc.player, (Entity)llllllllllllllllIlllIIlIIlIlIIll);
        llllllllllllllllIlllIIlIIlIlIlll.removalQueue.add(llllllllllllllllIlllIIlIIlIlIIll.getEntityId());
        if (llllllllllllllllIlllIIlIIlIlIlll.swing.get().booleanValue()) {
            llllllllllllllllIlllIIlIIlIlIlll.mc.player.swingHand(llllllllllllllllIlllIIlIIlIlIlll.getHand());
        } else {
            llllllllllllllllIlllIIlIIlIlIlll.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(llllllllllllllllIlllIIlIIlIlIlll.getHand()));
        }
        llllllllllllllllIlllIIlIIlIlIlll.mc.player.inventory.selectedSlot = llllllllllllllllIlllIIlIIlIlIIlI;
        if (llllllllllllllllIlllIIlIIlIlIlll.heldCrystal != null && llllllllllllllllIlllIIlIIlIlIIll.getBlockPos().equals((Object)llllllllllllllllIlllIIlIIlIlIlll.heldCrystal.getBlockPos())) {
            llllllllllllllllIlllIIlIIlIlIlll.heldCrystal = null;
            llllllllllllllllIlllIIlIIlIlIlll.locked = false;
        }
    }

    private float getTotalHealth(PlayerEntity llllllllllllllllIlllIIIllIlIlllI) {
        return llllllllllllllllIlllIIIllIlIlllI.getHealth() + llllllllllllllllIlllIIIllIlIlllI.getAbsorptionAmount();
    }

    public Hand getHand() {
        CustomCrystalAura llllllllllllllllIlllIIIllIIlIllI;
        assert (llllllllllllllllIlllIIIllIIlIllI.mc.player != null);
        Hand llllllllllllllllIlllIIIllIIlIlll = Hand.MAIN_HAND;
        if (llllllllllllllllIlllIIIllIIlIllI.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && llllllllllllllllIlllIIIllIIlIllI.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL) {
            llllllllllllllllIlllIIIllIIlIlll = Hand.OFF_HAND;
        }
        return llllllllllllllllIlllIIIllIIlIlll;
    }

    private class RenderBlock {
        private /* synthetic */ int z;
        private /* synthetic */ int y;
        private /* synthetic */ int x;
        private /* synthetic */ int timer;
        private /* synthetic */ double damage;

        public void render3D() {
            RenderBlock lIlIIlllIIIIlI;
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lIlIIlllIIIIlI.x, lIlIIlllIIIIlI.y, lIlIIlllIIIIlI.z, 1.0, (Color)lIlIIlllIIIIlI.CustomCrystalAura.this.sideColor.get(), (Color)lIlIIlllIIIIlI.CustomCrystalAura.this.lineColor.get(), (ShapeMode)((Object)lIlIIlllIIIIlI.CustomCrystalAura.this.shapeMode.get()), 0);
        }

        private RenderBlock() {
            RenderBlock lIlIIlllIIllll;
        }

        public void render2D() {
            RenderBlock lIlIIllIllllII;
            if (((Boolean)lIlIIllIllllII.CustomCrystalAura.this.renderDamage.get()).booleanValue()) {
                pos.set((double)lIlIIllIllllII.x + 0.5, (double)lIlIIllIllllII.y + 0.5, (double)lIlIIllIllllII.z + 0.5);
                if (NametagUtils.to2D(pos, (Double)lIlIIllIllllII.CustomCrystalAura.this.damageScale.get())) {
                    NametagUtils.begin(pos);
                    TextRenderer.get().begin(1.0, false, true);
                    String lIlIIllIlllllI = String.valueOf(Math.round(lIlIIllIllllII.damage));
                    switch ((Integer)lIlIIllIllllII.CustomCrystalAura.this.roundDamage.get()) {
                        case 0: {
                            lIlIIllIlllllI = String.valueOf(Math.round(lIlIIllIllllII.damage));
                            break;
                        }
                        case 1: {
                            lIlIIllIlllllI = String.valueOf((double)Math.round(lIlIIllIllllII.damage * 10.0) / 10.0);
                            break;
                        }
                        case 2: {
                            lIlIIllIlllllI = String.valueOf((double)Math.round(lIlIIllIllllII.damage * 100.0) / 100.0);
                            break;
                        }
                        case 3: {
                            lIlIIllIlllllI = String.valueOf((double)Math.round(lIlIIllIllllII.damage * 1000.0) / 1000.0);
                        }
                    }
                    double lIlIIllIllllIl = TextRenderer.get().getWidth(lIlIIllIlllllI) / 2.0;
                    TextRenderer.get().render(lIlIIllIlllllI, -lIlIIllIllllIl, 0.0, (Color)lIlIIllIllllII.CustomCrystalAura.this.damageColor.get());
                    TextRenderer.get().end();
                    NametagUtils.end();
                }
            }
        }

        public boolean shouldRemove() {
            RenderBlock lIlIIlllIIIlIl;
            if (lIlIIlllIIIlIl.timer <= 0) {
                return true;
            }
            --lIlIIlllIIIlIl.timer;
            return false;
        }

        public void reset(Vec3d lIlIIlllIIlIlI) {
            RenderBlock lIlIIlllIIlIIl;
            lIlIIlllIIlIIl.x = MathHelper.floor((double)lIlIIlllIIlIlI.getX());
            lIlIIlllIIlIIl.y = MathHelper.floor((double)lIlIIlllIIlIlI.getY());
            lIlIIlllIIlIIl.z = MathHelper.floor((double)lIlIIlllIIlIlI.getZ());
            lIlIIlllIIlIIl.timer = (Integer)lIlIIlllIIlIIl.CustomCrystalAura.this.renderTimer.get();
        }
    }

    public static final class TargetMode
    extends Enum<TargetMode> {
        public static final /* synthetic */ /* enum */ TargetMode MostDamage;
        private static final /* synthetic */ TargetMode[] $VALUES;
        public static final /* synthetic */ /* enum */ TargetMode HighestXDamages;

        private TargetMode() {
            TargetMode llllllllllllllllIllIllIllIlIllll;
        }

        static {
            MostDamage = new TargetMode();
            HighestXDamages = new TargetMode();
            $VALUES = TargetMode.$values();
        }

        public static TargetMode valueOf(String llllllllllllllllIllIllIllIllIlII) {
            return Enum.valueOf(TargetMode.class, llllllllllllllllIllIllIllIllIlII);
        }

        private static /* synthetic */ TargetMode[] $values() {
            return new TargetMode[]{MostDamage, HighestXDamages};
        }

        public static TargetMode[] values() {
            return (TargetMode[])$VALUES.clone();
        }
    }

    public static final class CancelCrystalMode
    extends Enum<CancelCrystalMode> {
        public static final /* synthetic */ /* enum */ CancelCrystalMode Hit;
        private static final /* synthetic */ CancelCrystalMode[] $VALUES;
        public static final /* synthetic */ /* enum */ CancelCrystalMode Sound;

        private CancelCrystalMode() {
            CancelCrystalMode llllllllllllllllllIIIllllIIlIIII;
        }

        public static CancelCrystalMode[] values() {
            return (CancelCrystalMode[])$VALUES.clone();
        }

        public static CancelCrystalMode valueOf(String llllllllllllllllllIIIllllIIlIlII) {
            return Enum.valueOf(CancelCrystalMode.class, llllllllllllllllllIIIllllIIlIlII);
        }

        static {
            Sound = new CancelCrystalMode();
            Hit = new CancelCrystalMode();
            $VALUES = CancelCrystalMode.$values();
        }

        private static /* synthetic */ CancelCrystalMode[] $values() {
            return new CancelCrystalMode[]{Sound, Hit};
        }
    }

    public static final class RotationMode
    extends Enum<RotationMode> {
        public static final /* synthetic */ /* enum */ RotationMode Place;
        public static final /* synthetic */ /* enum */ RotationMode None;
        private static final /* synthetic */ RotationMode[] $VALUES;
        public static final /* synthetic */ /* enum */ RotationMode Both;
        public static final /* synthetic */ /* enum */ RotationMode Break;

        public static RotationMode valueOf(String lIlIlllIllIIIII) {
            return Enum.valueOf(RotationMode.class, lIlIlllIllIIIII);
        }

        private static /* synthetic */ RotationMode[] $values() {
            return new RotationMode[]{Place, Break, Both, None};
        }

        static {
            Place = new RotationMode();
            Break = new RotationMode();
            Both = new RotationMode();
            None = new RotationMode();
            $VALUES = RotationMode.$values();
        }

        private RotationMode() {
            RotationMode lIlIlllIlIllIll;
        }

        public static RotationMode[] values() {
            return (RotationMode[])$VALUES.clone();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Suicide;
        public static final /* synthetic */ /* enum */ Mode None;
        public static final /* synthetic */ /* enum */ Mode Safe;
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private Mode() {
            Mode llllllllllllllllIllllIlIIIIIIIIl;
        }

        static {
            Safe = new Mode();
            Suicide = new Mode();
            None = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String llllllllllllllllIllllIlIIIIIIllI) {
            return Enum.valueOf(Mode.class, llllllllllllllllIllllIlIIIIIIllI);
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Safe, Suicide, None};
        }
    }

    public static final class SwitchMode
    extends Enum<SwitchMode> {
        public static final /* synthetic */ /* enum */ SwitchMode Spoof;
        private static final /* synthetic */ SwitchMode[] $VALUES;
        public static final /* synthetic */ /* enum */ SwitchMode None;
        public static final /* synthetic */ /* enum */ SwitchMode Auto;

        public static SwitchMode[] values() {
            return (SwitchMode[])$VALUES.clone();
        }

        private static /* synthetic */ SwitchMode[] $values() {
            return new SwitchMode[]{Auto, Spoof, None};
        }

        private SwitchMode() {
            SwitchMode lllllllllllllllllIIIlllIlIIllIll;
        }

        static {
            Auto = new SwitchMode();
            Spoof = new SwitchMode();
            None = new SwitchMode();
            $VALUES = SwitchMode.$values();
        }

        public static SwitchMode valueOf(String lllllllllllllllllIIIlllIlIlIIIII) {
            return Enum.valueOf(SwitchMode.class, lllllllllllllllllIIIlllIlIlIIIII);
        }
    }
}

