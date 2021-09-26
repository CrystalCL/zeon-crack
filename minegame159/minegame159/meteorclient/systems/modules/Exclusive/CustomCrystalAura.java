/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> render;
    private final Setting<Boolean> multiPlace;
    private final SettingGroup sgRotations;
    private double bestDamage;
    private final Setting<Boolean> surroundHold;
    private final Setting<Mode> placeMode;
    private final Setting<SwitchMode> switchMode;
    private final Setting<Boolean> switchBack;
    private final Map<EndCrystalEntity, List<Double>> crystalMap;
    private final Setting<Boolean> renderDamage;
    private final SettingGroup sgMisc;
    private int supportSlot;
    private final Setting<Mode> breakMode;
    private final Setting<Double> maxDamage;
    private final SettingGroup sgRender;
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final Setting<Integer> supportDelay;
    private final Setting<Boolean> surroundBreak;
    private final Setting<Boolean> rotate;
    private double lastDamage;
    private final Pool<RenderBlock> renderBlockPool;
    private static List<String> s;
    private int supportDelayLeft;
    private final SettingGroup sgTarget;
    private final Setting<Boolean> multiTarget;
    private final Setting<SettingColor> sideColor;
    private Vec3d bestBlock;
    private boolean canSupport;
    private final Setting<Boolean> supportBackup;
    private final Setting<Integer> placeDelay;
    static final boolean $assertionsDisabled;
    private final Setting<Double> healthDifference;
    private final Setting<Integer> renderTimer;
    private final Setting<Boolean> oldPlace;
    private final Setting<Boolean> pauseOnEat;
    private EndCrystalEntity bestBreak;
    private final Setting<Boolean> pauseOnDrink;
    private final List<RenderBlock> renderBlocks;
    private final Setting<Double> breakRange;
    private final Setting<Double> placeRange;
    private final Setting<Boolean> place;
    private final SettingGroup sgBreak;
    private final Setting<Double> targetRange;
    private final Setting<RotationMode> rotationMode;
    private final Setting<Integer> range;
    private final Setting<Double> placeWallsRange;
    private final List<Double> crystalList;
    private final Setting<ShapeMode> shapeMode;
    private final SettingGroup sgPlace;
    private final Setting<SettingColor> lineColor;
    private final Setting<Boolean> strictLook;
    private final Setting<Double> facePlaceDurability;
    private EndCrystalEntity heldCrystal;
    private final Setting<Double> damageScale;
    private final Setting<SettingColor> damageColor;
    private final SettingGroup sgPause;
    private final Setting<Double> verticalRange;
    private final Setting<Integer> roundDamage;
    private final Setting<Double> facePlaceHealth;
    private boolean broken;
    private int placeDelayLeft;
    private LivingEntity target;
    private final Setting<Boolean> swing;
    private final List<Integer> removalQueue;
    private static final Vec3 pos;
    private final Setting<Double> minHealth;
    private final Setting<Double> minDamage;
    private final Setting<Boolean> resetRotations;
    private final Setting<Integer> breakDelay;
    private final Setting<Boolean> facePlace;
    private int preSlot;
    private final Setting<Boolean> support;
    private final Setting<Boolean> ignoreWalls;
    private final Setting<Boolean> rayTrace;
    private final Setting<Boolean> smartDelay;
    private PlayerEntity target1;
    private final Setting<TargetMode> targetMode;
    private final Setting<Boolean> fullBreak;
    private boolean locked;
    private int breakDelayLeft;
    private final Setting<Boolean> pauseOnMine;
    private final Setting<Integer> numberOfDamages;
    private boolean placed;
    private final Setting<Boolean> spamFacePlace;
    private List<BlockPos> placePositions;
    private final Setting<CancelCrystalMode> cancelCrystalMode;
    private final Setting<Boolean> antiWeakness;

    private void lambda$hitCrystal$11(EndCrystalEntity EndCrystalEntity2, int n) {
        this.attackCrystal(EndCrystalEntity2, n);
    }

    public BlockPos SetRelative(int n, int n2, int n3) {
        return new BlockPos(this.mc.player.getX() + (double)n, this.mc.player.getY() + (double)n2, this.mc.player.getZ() + (double)n3);
    }

    public Hand getHand() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        Hand Hand2 = Hand.MAIN_HAND;
        if (this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && this.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL) {
            Hand2 = Hand.OFF_HAND;
        }
        return Hand2;
    }

    static Setting access$700(CustomCrystalAura customCrystalAura) {
        return customCrystalAura.damageScale;
    }

    private void findTarget() {
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        Optional<LivingEntity> optional = Streams.stream((Iterable)this.mc.world.getEntities()).filter(Entity::isAlive).filter(this::lambda$findTarget$12).filter(CustomCrystalAura::lambda$findTarget$13).filter(CustomCrystalAura::lambda$findTarget$14).filter(this::lambda$findTarget$15).filter(this::lambda$findTarget$16).min(Comparator.comparingDouble(this::lambda$findTarget$17)).map(CustomCrystalAura::lambda$findTarget$18);
        if (!optional.isPresent()) {
            this.target = null;
            return;
        }
        this.target = optional.get();
    }

    private double lambda$findTarget$17(Entity Entity2) {
        return Entity2.distanceTo((Entity)this.mc.player);
    }

    private void singleBreak() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        this.getCrystalStream().max(Comparator.comparingDouble(this::lambda$singleBreak$8)).ifPresent(this::lambda$singleBreak$9);
    }

    private void findValidBlocks(LivingEntity LivingEntity2) {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        this.bestBlock = new Vec3d(0.0, 0.0, 0.0);
        this.bestDamage = 0.0;
        Vec3d Vec3d2 = new Vec3d(0.0, 0.0, 0.0);
        double d = 0.0;
        BlockPos BlockPos2 = this.mc.player.getBlockPos();
        this.canSupport = false;
        this.crystalMap.clear();
        this.crystalList.clear();
        if (this.support.get().booleanValue()) {
            for (int i = 0; i < 9; ++i) {
                if (this.mc.player.inventory.getStack(i).getItem() != Items.OBSIDIAN) continue;
                this.canSupport = true;
                this.supportSlot = i;
                break;
            }
        }
        for (double d2 = (double)BlockPos2.getX() - this.placeRange.get(); d2 < (double)BlockPos2.getX() + this.placeRange.get(); d2 += 1.0) {
            for (double d3 = (double)BlockPos2.getZ() - this.placeRange.get(); d3 < (double)BlockPos2.getZ() + this.placeRange.get(); d3 += 1.0) {
                for (double d4 = (double)BlockPos2.getY() - this.verticalRange.get(); d4 < (double)BlockPos2.getY() + this.verticalRange.get(); d4 += 1.0) {
                    Vec3d Vec3d3 = new Vec3d(Math.floor(d2), Math.floor(d4), Math.floor(d3));
                    if (!this.isValid(new BlockPos(Vec3d3)) || !this.getDamagePlace(new BlockPos(Vec3d3).up()) || this.oldPlace.get().booleanValue() && !this.isEmpty(new BlockPos(Vec3d3.add(0.0, 2.0, 0.0))) || this.rayTrace.get().booleanValue() && !(Vec3d3.distanceTo(new Vec3d(this.mc.player.getX(), this.mc.player.getY() + (double)this.mc.player.getEyeHeight(this.mc.player.getPose()), this.mc.player.getZ())) <= this.placeWallsRange.get()) && this.rayTraceCheck(new BlockPos(Vec3d3), false) == null) continue;
                    if (!this.multiTarget.get().booleanValue()) {
                        if (this.isEmpty(new BlockPos(Vec3d3)) && d < DamageCalcUtils.crystalDamage(LivingEntity2, Vec3d3.add(0.5, 1.0, 0.5))) {
                            Vec3d2 = Vec3d3;
                            d = DamageCalcUtils.crystalDamage(LivingEntity2, Vec3d3.add(0.5, 1.0, 0.5));
                            continue;
                        }
                        if (this.isEmpty(new BlockPos(Vec3d3)) || !(this.bestDamage < DamageCalcUtils.crystalDamage(LivingEntity2, Vec3d3.add(0.5, 1.0, 0.5)))) continue;
                        this.bestBlock = Vec3d3;
                        this.bestDamage = DamageCalcUtils.crystalDamage(LivingEntity2, this.bestBlock.add(0.5, 1.0, 0.5));
                        continue;
                    }
                    for (Entity Entity2 : this.mc.world.getEntities()) {
                        if (Entity2 == this.mc.player || !this.entities.get().getBoolean((Object)Entity2.getType()) || !((double)this.mc.player.distanceTo(Entity2) <= this.targetRange.get()) || !Entity2.isAlive() || !(Entity2 instanceof LivingEntity) || Entity2 instanceof PlayerEntity && !Friends.get().attack((PlayerEntity)Entity2)) continue;
                        this.crystalList.add(DamageCalcUtils.crystalDamage((LivingEntity)Entity2, Vec3d3.add(0.5, 1.0, 0.5)));
                    }
                    if (this.crystalList.isEmpty()) continue;
                    this.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
                    this.crystalMap.put(new EndCrystalEntity((World)this.mc.world, Vec3d3.x, Vec3d3.y, Vec3d3.z), new ArrayList<Double>(this.crystalList));
                    this.crystalList.clear();
                }
            }
        }
        if (this.multiTarget.get().booleanValue()) {
            EndCrystalEntity EndCrystalEntity2 = this.findBestCrystal(this.crystalMap);
            this.bestBlock = EndCrystalEntity2 != null && this.bestDamage > this.minDamage.get() ? EndCrystalEntity2.getPos() : null;
        } else if (this.bestDamage < this.minDamage.get()) {
            this.bestBlock = null;
        }
        if (this.support.get().booleanValue() && (this.bestBlock == null || this.bestDamage < d && !this.supportBackup.get().booleanValue())) {
            this.bestBlock = Vec3d2;
        }
    }

    private boolean lambda$getCrystalStream$4(Entity Entity2) {
        return (double)Entity2.distanceTo((Entity)this.mc.player) <= this.breakRange.get();
    }

    @EventHandler(priority=100)
    private void onTick(TickEvent.Post post) {
        if (this.cancelCrystalMode.get() == CancelCrystalMode.Hit) {
            this.removalQueue.forEach(this::lambda$onTick$1);
            this.removalQueue.clear();
        }
    }

    private boolean isEmpty(BlockPos BlockPos2) {
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        return this.mc.world.getBlockState(BlockPos2).isAir() && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.getX(), (double)BlockPos2.getY(), (double)BlockPos2.getZ(), (double)BlockPos2.getX() + 1.0, (double)BlockPos2.getY() + 2.0, (double)BlockPos2.getZ() + 1.0)).isEmpty();
    }

    private void findPlacePos(PlayerEntity PlayerEntity2) {
        this.placePositions.clear();
        BlockPos BlockPos2 = this.target.getBlockPos();
        this.add(BlockPos2.add(2, 0, 1));
    }

    private EndCrystalEntity findBestCrystal(Map<EndCrystalEntity, List<Double>> map) {
        block7: {
            double d;
            block6: {
                this.bestDamage = 0.0;
                d = 0.0;
                if (this.targetMode.get() != TargetMode.HighestXDamages) break block6;
                for (Map.Entry<EndCrystalEntity, List<Double>> entry : map.entrySet()) {
                    for (int i = 0; i < entry.getValue().size() && i < this.numberOfDamages.get(); ++i) {
                        d += entry.getValue().get(i).doubleValue();
                        if (-4 < 0) continue;
                        return null;
                    }
                    if (this.bestDamage < d) {
                        this.bestDamage = d;
                        this.bestBreak = entry.getKey();
                    }
                    d = 0.0;
                }
                break block7;
            }
            if (this.targetMode.get() != TargetMode.MostDamage) break block7;
            for (Map.Entry<EndCrystalEntity, List<Double>> entry : map.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); ++i) {
                    d += entry.getValue().get(i).doubleValue();
                    if (-1 < 3) continue;
                    return null;
                }
                if (this.bestDamage < d) {
                    this.bestDamage = d;
                    this.bestBreak = entry.getKey();
                }
                d = 0.0;
            }
        }
        return this.bestBreak;
    }

    static Setting access$400(CustomCrystalAura customCrystalAura) {
        return customCrystalAura.shapeMode;
    }

    private boolean lambda$getCrystalStream$6(Entity Entity2) {
        return this.ignoreWalls.get() == false || this.mc.player.canSee(Entity2);
    }

    private boolean shouldBreak(EndCrystalEntity EndCrystalEntity2) {
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        return this.heldCrystal == null || this.surroundHold.get() == false && this.surroundBreak.get() == false || this.placeDelayLeft <= 0 && (!this.heldCrystal.getBlockPos().equals((Object)EndCrystalEntity2.getBlockPos()) || this.mc.world.raycast(new RaycastContext(this.target.getPos(), this.heldCrystal.getPos(), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)this.target)).getType() == HitResult.class_240.MISS || (double)this.target.distanceTo((Entity)this.heldCrystal) > 1.5 && !this.isSurrounded(this.target));
    }

    private void doHeldCrystal() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (this.switchMode.get() != SwitchMode.None) {
            this.doSwitch();
        }
        if (this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && this.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        this.bestDamage = DamageCalcUtils.crystalDamage(this.target, this.bestBlock.add(0.0, 1.0, 0.0));
        this.heldCrystal = new EndCrystalEntity((World)this.mc.world, this.bestBlock.x, this.bestBlock.y + 1.0, this.bestBlock.z);
        this.locked = true;
        if (!this.smartDelay.get().booleanValue()) {
            this.placeDelayLeft = this.placeDelay.get();
        } else {
            this.lastDamage = this.bestDamage;
            if (this.placeDelayLeft <= 0) {
                this.placeDelayLeft = 10;
            }
        }
        this.placeBlock(this.bestBlock, this.getHand());
    }

    private void findFacePlace(LivingEntity LivingEntity2) {
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        BlockPos BlockPos2 = LivingEntity2.getBlockPos();
        if (this.mc.world.getBlockState(BlockPos2.add(1, 1, 0)).isAir() && Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance((Box2)BlockPos2.add(1, 1, 0))) <= this.placeRange.get() && this.getDamagePlace(BlockPos2.add(1, 1, 0))) {
            this.bestBlock = LivingEntity2.getPos().add(1.0, 0.0, 0.0);
        } else if (this.mc.world.getBlockState(BlockPos2.add(-1, 1, 0)).isAir() && Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance((Box2)BlockPos2.add(-1, 1, 0))) <= this.placeRange.get() && this.getDamagePlace(BlockPos2.add(-1, 1, 0))) {
            this.bestBlock = LivingEntity2.getPos().add(-1.0, 0.0, 0.0);
        } else if (this.mc.world.getBlockState(BlockPos2.add(0, 1, 1)).isAir() && Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance((Box2)BlockPos2.add(0, 1, 1))) <= this.placeRange.get() && this.getDamagePlace(BlockPos2.add(0, 1, 1))) {
            this.bestBlock = LivingEntity2.getPos().add(0.0, 0.0, 1.0);
        } else if (this.mc.world.getBlockState(BlockPos2.add(0, 1, -1)).isAir() && Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance((Box2)BlockPos2.add(0, 1, -1))) <= this.placeRange.get() && this.getDamagePlace(BlockPos2.add(0, 1, -1))) {
            this.bestBlock = LivingEntity2.getPos().add(0.0, 0.0, -1.0);
        }
    }

    static Setting access$100(CustomCrystalAura customCrystalAura) {
        return customCrystalAura.renderTimer;
    }

    static Setting access$800(CustomCrystalAura customCrystalAura) {
        return customCrystalAura.roundDamage;
    }

    static {
        $assertionsDisabled = !CustomCrystalAura.class.desiredAssertionStatus();
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
        pos = new Vec3();
    }

    private boolean lambda$findTarget$15(Entity Entity2) {
        return this.entities.get().getBoolean((Object)Entity2.getType());
    }

    private void doSwitch() {
        int n;
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && this.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL && (n = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && n < 9) {
            this.preSlot = this.mc.player.inventory.selectedSlot;
            this.mc.player.inventory.selectedSlot = n;
        }
    }

    private Vec3d findOpenSurround(LivingEntity LivingEntity2) {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        int n = 0;
        int n2 = 0;
        if (this.validSurroundBreak(LivingEntity2, 2, 0)) {
            n = 2;
        } else if (this.validSurroundBreak(LivingEntity2, -2, 0)) {
            n = -2;
        } else if (this.validSurroundBreak(LivingEntity2, 0, 2)) {
            n2 = 2;
        } else if (this.validSurroundBreak(LivingEntity2, 0, -2)) {
            n2 = -2;
        }
        if (n != 0 || n2 != 0) {
            return new Vec3d((double)LivingEntity2.getBlockPos().getX() + 0.5 + (double)n, (double)(LivingEntity2.getBlockPos().getY() - 1), (double)LivingEntity2.getBlockPos().getZ() + 0.5 + (double)n2);
        }
        return null;
    }

    private Stream<Entity> getCrystalStream() {
        return Streams.stream((Iterable)this.mc.world.getEntities()).filter(CustomCrystalAura::lambda$getCrystalStream$3).filter(this::lambda$getCrystalStream$4).filter(Entity::isAlive).filter(this::lambda$getCrystalStream$5).filter(this::lambda$getCrystalStream$6).filter(this::lambda$getCrystalStream$7);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean validSurroundBreak(LivingEntity LivingEntity2, int n, int n2) {
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        Vec3d Vec3d2 = new Vec3d((double)LivingEntity2.getBlockPos().getX() + 0.5, (double)LivingEntity2.getBlockPos().getY(), (double)LivingEntity2.getBlockPos().getZ() + 0.5);
        if (!this.isValid(LivingEntity2.getBlockPos().add(n, -1, n2))) return false;
        if (this.mc.world.getBlockState(LivingEntity2.getBlockPos().add(n / 2, 0, n2 / 2)).getBlock() == Blocks.BEDROCK) return false;
        if (!this.isSafe(Vec3d2.add((double)n, 0.0, (double)n2))) return false;
        Box2 Box22 = new Box2(LivingEntity2.getBlockPos().getX() + n, LivingEntity2.getBlockPos().getY() - 1, LivingEntity2.getBlockPos().getZ() + n2);
        if (!(Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance(Box22)) < this.placeRange.get())) return false;
        if (this.mc.world.raycast(new RaycastContext(LivingEntity2.getPos(), LivingEntity2.getPos().add((double)n, 0.0, (double)n2), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)LivingEntity2)).getType() == HitResult.class_240.MISS) return false;
        return true;
    }

    static Setting access$200(CustomCrystalAura customCrystalAura) {
        return customCrystalAura.sideColor;
    }

    private boolean isSurrounded(LivingEntity LivingEntity2) {
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        return !this.mc.world.getBlockState(LivingEntity2.getBlockPos().add(1, 0, 0)).isAir() && !this.mc.world.getBlockState(LivingEntity2.getBlockPos().add(-1, 0, 0)).isAir() && !this.mc.world.getBlockState(LivingEntity2.getBlockPos().add(0, 0, 1)).isAir() && !this.mc.world.getBlockState(LivingEntity2.getBlockPos().add(0, 0, -1)).isAir();
    }

    /*
     * Unable to fully structure code
     */
    private Vec3d findOpen(LivingEntity var1_1) {
        block7: {
            block8: {
                block6: {
                    if (!CustomCrystalAura.$assertionsDisabled && this.mc.player == null) {
                        throw new AssertionError();
                    }
                    var2_2 = 0;
                    var3_3 = 0;
                    if (!this.isValid(var1_1.getBlockPos().add(1, -1, 0))) break block6;
                    v0 = new Box2(var1_1.getBlockPos().getX() + 1, var1_1.getBlockPos().getY() - 1, var1_1.getBlockPos().getZ());
                    if (!(Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance(v0)) < this.placeRange.get())) break block6;
                    var2_2 = 1;
                    break block7;
                }
                if (!this.isValid(var1_1.getBlockPos().add(-1, -1, 0))) break block8;
                v1 = new Box2(var1_1.getBlockPos().getX() - 1, var1_1.getBlockPos().getY() - 1, var1_1.getBlockPos().getZ());
                if (!(Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance(v1)) < this.placeRange.get())) break block8;
                var2_2 = -1;
                break block7;
            }
            if (!this.isValid(var1_1.getBlockPos().add(0, -1, 1))) ** GOTO lbl-1000
            v2 = new Box2(var1_1.getBlockPos().getX(), var1_1.getBlockPos().getY() - 1, var1_1.getBlockPos().getZ() + 1);
            if (Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance(v2)) < this.placeRange.get()) {
                var3_3 = 1;
            } else if (this.isValid(var1_1.getBlockPos().add(0, -1, -1))) {
                v3 = new Box2(var1_1.getBlockPos().getX(), var1_1.getBlockPos().getY() - 1, var1_1.getBlockPos().getZ() - 1);
                if (Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance(v3)) < this.placeRange.get()) {
                    var3_3 = -1;
                }
            }
        }
        if (var2_2 != 0 || var3_3 != 0) {
            return new Vec3d((double)var1_1.getBlockPos().getX() + 0.5 + (double)var2_2, (double)(var1_1.getBlockPos().getY() - 1), (double)var1_1.getBlockPos().getZ() + 0.5 + (double)var3_3);
        }
        return null;
    }

    static Vec3 access$600() {
        return pos;
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
            if (!false) continue;
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
        this.preSlot = -1;
        this.placeDelayLeft = 0;
        this.breakDelayLeft = 0;
        this.heldCrystal = null;
        this.locked = false;
        this.broken = false;
    }

    private void lambda$multiBreak$10(Entity Entity2) {
        for (Entity Entity3 : this.mc.world.getEntities()) {
            if (Entity3 == this.mc.player || !this.entities.get().getBoolean((Object)Entity3.getType()) || !((double)this.mc.player.distanceTo(Entity3) <= this.targetRange.get()) || !Entity3.isAlive() || !(Entity3 instanceof LivingEntity) || Entity3 instanceof PlayerEntity && !Friends.get().attack((PlayerEntity)Entity3)) continue;
            this.crystalList.add(DamageCalcUtils.crystalDamage((LivingEntity)Entity3, Entity2.getPos()));
        }
        if (!this.crystalList.isEmpty()) {
            this.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
            this.crystalMap.put((EndCrystalEntity)Entity2, new ArrayList<Double>(this.crystalList));
            this.crystalList.clear();
        }
    }

    public CustomCrystalAura() {
        super(Categories.Exclusive, "crystal-aura+", "AntiSurround crystal.");
        this.sgPlace = this.settings.createGroup("Place");
        this.sgBreak = this.settings.createGroup("Break");
        this.sgTarget = this.settings.createGroup("Target");
        this.sgPause = this.settings.createGroup("Pause");
        this.sgRotations = this.settings.createGroup("Rotations");
        this.sgMisc = this.settings.createGroup("Misc");
        this.sgRender = this.settings.createGroup("Render");
        this.placeDelay = this.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The amount of delay in ticks before placing.").defaultValue(0).min(0).sliderMax(10).build());
        this.placeMode = this.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The placement mode for crystals.").defaultValue(Mode.Safe).build());
        this.placeRange = this.sgPlace.add(new DoubleSetting.Builder().name("place-range").description("The radius in which crystals can be placed in.").defaultValue(5.0).min(0.0).sliderMax(7.0).build());
        this.placeWallsRange = this.sgPlace.add(new DoubleSetting.Builder().name("place-walls-range").description("The radius in which crystals can be placed through walls.").defaultValue(5.0).min(0.0).sliderMax(7.0).build());
        this.place = this.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Crystal Aura to place crystals.").defaultValue(true).build());
        this.multiPlace = this.sgPlace.add(new BoolSetting.Builder().name("multi-place").description("Allows Crystal Aura to place multiple crystals.").defaultValue(false).build());
        this.rayTrace = this.sgPlace.add(new BoolSetting.Builder().name("ray-trace").description("Whether or not to place through walls.").defaultValue(false).build());
        this.minDamage = this.sgPlace.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage the crystal will place.").defaultValue(3.5).build());
        this.minHealth = this.sgPlace.add(new DoubleSetting.Builder().name("min-health").description("The minimum health you have to be for it to place.").defaultValue(4.0).build());
        this.range = this.sgPlace.add(new IntSetting.Builder().name("range").description("The radius players can be break.").defaultValue(5).sliderMin(0).sliderMax(10).build());
        this.surroundBreak = this.sgPlace.add(new BoolSetting.Builder().name("surround-break").description("Places a crystal next to a surrounded player and keeps it there so they cannot use Surround again.").defaultValue(true).build());
        this.fullBreak = this.sgPlace.add(new BoolSetting.Builder().name("full-break").description("Break a fullSurround(ExtraSurround, Piramyd, FullSurround).").defaultValue(true).build());
        this.rotate = this.sgPlace.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(false).build());
        this.surroundHold = this.sgPlace.add(new BoolSetting.Builder().name("surround-hold").description("Places a crystal next to a player so they cannot use Surround.").defaultValue(true).build());
        this.oldPlace = this.sgPlace.add(new BoolSetting.Builder().name("1.12-place").description("Won't place in one block holes to help compatibility with some servers.").defaultValue(false).build());
        this.facePlace = this.sgPlace.add(new BoolSetting.Builder().name("face-place").description("Will face-place when target is below a certain health or armor durability threshold.").defaultValue(true).build());
        this.spamFacePlace = this.sgPlace.add(new BoolSetting.Builder().name("spam-face-place").description("Places faster when someone is below the face place health (Requires Smart Delay).").defaultValue(true).build());
        this.facePlaceHealth = this.sgPlace.add(new DoubleSetting.Builder().name("face-place-health").description("The health required to face-place.").defaultValue(36.0).min(1.0).max(36.0).build());
        this.facePlaceDurability = this.sgPlace.add(new DoubleSetting.Builder().name("face-place-durability").description("The durability threshold to be able to face-place.").defaultValue(100.0).min(1.0).max(100.0).sliderMax(100.0).build());
        this.support = this.sgPlace.add(new BoolSetting.Builder().name("support").description("Places a block in the air and crystals on it. Helps with killing players that are flying.").defaultValue(false).build());
        this.supportDelay = this.sgPlace.add(new IntSetting.Builder().name("support-delay").description("The delay between support blocks being placed.").defaultValue(5).min(0).sliderMax(10).build());
        this.supportBackup = this.sgPlace.add(new BoolSetting.Builder().name("support-backup").description("Makes it so support only works if there are no other options.").defaultValue(true).build());
        this.breakDelay = this.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The amount of delay in ticks before breaking.").defaultValue(0).min(0).sliderMax(10).build());
        this.breakMode = this.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The type of break mode for crystals.").defaultValue(Mode.Safe).build());
        this.breakRange = this.sgBreak.add(new DoubleSetting.Builder().name("break-range").description("The maximum range that crystals can be to be broken.").defaultValue(6.0).min(0.0).sliderMax(7.0).build());
        this.ignoreWalls = this.sgBreak.add(new BoolSetting.Builder().name("ray-trace").description("Whether or not to break through walls.").defaultValue(false).build());
        this.cancelCrystalMode = this.sgBreak.add(new EnumSetting.Builder().name("cancel-crystal").description("Mode to use for the crystals to be removed from the world.").defaultValue(CancelCrystalMode.Hit).build());
        this.entities = this.sgTarget.add(new EntityTypeListSetting.Builder().name("entities").description("The entities to attack.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(EntityType.PLAYER)).onlyAttackable().build());
        this.targetRange = this.sgTarget.add(new DoubleSetting.Builder().name("target-range").description("The maximum range the entity can be to be targeted.").defaultValue(7.0).min(0.0).sliderMax(10.0).build());
        this.targetMode = this.sgTarget.add(new EnumSetting.Builder().name("target-mode").description("The way you target multiple targets.").defaultValue(TargetMode.HighestXDamages).build());
        this.numberOfDamages = this.sgTarget.add(new IntSetting.Builder().name("number-of-damages").description("The number to replace 'x' with in HighestXDamages.").defaultValue(3).min(2).sliderMax(10).build());
        this.multiTarget = this.sgTarget.add(new BoolSetting.Builder().name("multi-targeting").description("Will calculate damage for all entities and pick a block based on target mode.").defaultValue(false).build());
        this.pauseOnEat = this.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses Crystal Aura while eating.").defaultValue(true).build());
        this.pauseOnDrink = this.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses Crystal Aura while drinking a potion.").defaultValue(false).build());
        this.pauseOnMine = this.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses Crystal Aura while mining blocks.").defaultValue(false).build());
        this.rotationMode = this.sgRotations.add(new EnumSetting.Builder().name("rotation-mode").description("The method of rotating when using Crystal Aura.").defaultValue(RotationMode.Place).build());
        this.strictLook = this.sgRotations.add(new BoolSetting.Builder().name("strict-look").description("Looks at exactly where you're placing.").defaultValue(true).build());
        this.resetRotations = this.sgRotations.add(new BoolSetting.Builder().name("reset-rotations").description("Resets rotations once Crystal Aura is disabled.").defaultValue(false).build());
        this.switchMode = this.sgMisc.add(new EnumSetting.Builder().name("switch-mode").description("How to switch items.").defaultValue(SwitchMode.Auto).build());
        this.switchBack = this.sgMisc.add(new BoolSetting.Builder().name("switch-back").description("Switches back to your previous slot when disabling Crystal Aura.").defaultValue(true).build());
        this.verticalRange = this.sgMisc.add(new DoubleSetting.Builder().name("vertical-range").description("The maximum vertical range for placing/breaking end crystals. May kill performance if this value is higher than 3.").min(0.0).defaultValue(3.0).max(7.0).build());
        this.maxDamage = this.sgMisc.add(new DoubleSetting.Builder().name("max-damage").description("The maximum self-damage allowed.").defaultValue(3.0).build());
        this.smartDelay = this.sgMisc.add(new BoolSetting.Builder().name("smart-delay").description("Reduces crystal consumption when doing large amounts of damage. (Can tank performance on lower-end PCs).").defaultValue(false).build());
        this.healthDifference = this.sgMisc.add(new DoubleSetting.Builder().name("damage-increase").description("The damage increase for smart delay to work.").defaultValue(5.0).min(0.0).max(20.0).build());
        this.antiWeakness = this.sgMisc.add(new BoolSetting.Builder().name("anti-weakness").description("Switches to tools to break crystals instead of your fist.").defaultValue(true).build());
        this.swing = this.sgRender.add(new BoolSetting.Builder().name("swing").description("Renders your swing client-side.").defaultValue(true).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block under where it is placing a crystal.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 255, 255, 75)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        this.renderDamage = this.sgRender.add(new BoolSetting.Builder().name("render-damage").description("Renders the damage of the crystal where it is placing.").defaultValue(true).build());
        this.roundDamage = this.sgRender.add(new IntSetting.Builder().name("round-damage").description("Round damage to x decimal places.").defaultValue(2).min(0).max(3).sliderMax(3).build());
        this.damageScale = this.sgRender.add(new DoubleSetting.Builder().name("damage-scale").description("The scale of the damage text.").defaultValue(1.4).min(0.0).sliderMax(5.0).build());
        this.damageColor = this.sgRender.add(new ColorSetting.Builder().name("damage-color").description("The color of the damage text.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
        this.renderTimer = this.sgRender.add(new IntSetting.Builder().name("timer").description("The amount of time between changing the block render.").defaultValue(0).min(0).sliderMax(10).build());
        this.placeDelayLeft = this.placeDelay.get();
        this.breakDelayLeft = this.breakDelay.get();
        this.bestDamage = 0.0;
        this.lastDamage = 0.0;
        this.heldCrystal = null;
        this.locked = false;
        this.supportSlot = 0;
        this.supportDelayLeft = this.supportDelay.get();
        this.crystalMap = new HashMap<EndCrystalEntity, List<Double>>();
        this.crystalList = new ArrayList<Double>();
        this.removalQueue = new ArrayList<Integer>();
        this.placePositions = new ArrayList<BlockPos>();
        this.bestBreak = null;
        this.renderBlockPool = new Pool<RenderBlock>(this::lambda$new$0);
        this.renderBlocks = new ArrayList<RenderBlock>();
        this.broken = false;
    }

    private void hitCrystal(EndCrystalEntity EndCrystalEntity2) {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.interactionManager == null) {
            throw new AssertionError();
        }
        int n = this.mc.player.inventory.selectedSlot;
        if (this.mc.player.getActiveStatusEffects().containsKey(StatusEffects.WEAKNESS) && this.antiWeakness.get().booleanValue()) {
            for (int i = 0; i < 9; ++i) {
                if (!(this.mc.player.inventory.getStack(i).getItem() instanceof SwordItem) && !(this.mc.player.inventory.getStack(i).getItem() instanceof AxeItem)) continue;
                this.mc.player.inventory.selectedSlot = i;
                break;
            }
        }
        if (this.rotationMode.get() == RotationMode.Break || this.rotationMode.get() == RotationMode.Both) {
            float[] fArray = PlayerUtils.calculateAngle(EndCrystalEntity2.getPos());
            Rotations.rotate(fArray[0], fArray[1], 30, () -> this.lambda$hitCrystal$11(EndCrystalEntity2, n));
        } else {
            this.attackCrystal(EndCrystalEntity2, n);
        }
        this.broken = true;
        this.breakDelayLeft = this.breakDelay.get();
    }

    private void lambda$singleBreak$9(Entity Entity2) {
        this.hitCrystal((EndCrystalEntity)Entity2);
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!this.render.get().booleanValue()) {
            return;
        }
        for (RenderBlock renderBlock : this.renderBlocks) {
            renderBlock.render3D();
        }
    }

    @EventHandler
    private void onRender2D(Render2DEvent render2DEvent) {
        if (!this.render.get().booleanValue()) {
            return;
        }
        for (RenderBlock renderBlock : this.renderBlocks) {
            renderBlock.render2D();
        }
    }

    private boolean isValid(BlockPos BlockPos2) {
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        return (this.canSupport && this.isEmpty(BlockPos2) && BlockPos2.getY() - this.target.getBlockPos().getY() == -1 && this.supportDelayLeft <= 0 || this.mc.world.getBlockState(BlockPos2).getBlock() == Blocks.BEDROCK || this.mc.world.getBlockState(BlockPos2).getBlock() == Blocks.OBSIDIAN) && this.isEmpty(BlockPos2.add(0, 1, 0));
    }

    private static boolean lambda$findTarget$13(Entity Entity2) {
        return !(Entity2 instanceof PlayerEntity) || Friends.get().attack((PlayerEntity)Entity2);
    }

    private boolean lambda$getCrystalStream$7(Entity Entity2) {
        return this.isSafe(Entity2.getPos());
    }

    @Override
    public String getInfoString() {
        if (this.target != null && this.target instanceof PlayerEntity) {
            return this.target.getEntityName();
        }
        if (this.target != null) {
            return this.target.getType().getName().getString();
        }
        return null;
    }

    private void lambda$placeBlock$19(Hand Hand2, Direction Direction2, Vec3d Vec3d2) {
        this.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand2, new BlockHitResult(this.mc.player.getPos(), Direction2, new BlockPos(Vec3d2), false)));
        if (this.swing.get().booleanValue()) {
            this.mc.player.swingHand(Hand2);
        } else {
            this.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(Hand2));
        }
    }

    private float getTotalHealth(PlayerEntity PlayerEntity2) {
        return PlayerEntity2.getHealth() + PlayerEntity2.getAbsorptionAmount();
    }

    private boolean isSafe(Vec3d Vec3d2) {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        return this.breakMode.get() != Mode.Safe || (double)this.getTotalHealth((PlayerEntity)this.mc.player) - DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Vec3d2) > this.minHealth.get() && DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Vec3d2) < this.maxDamage.get();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean getDamagePlace(BlockPos BlockPos2) {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (this.placeMode.get() == Mode.Suicide) return true;
        Vec3d Vec3d2 = new Vec3d((double)BlockPos2.getX() + 0.5, (double)BlockPos2.getY(), (double)BlockPos2.getZ() + 0.5);
        if (!(DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Vec3d2) <= this.maxDamage.get())) return false;
        Vec3d Vec3d3 = new Vec3d((double)BlockPos2.getX() + 0.5, (double)BlockPos2.getY(), (double)BlockPos2.getZ() + 0.5);
        if (!((double)this.getTotalHealth((PlayerEntity)this.mc.player) - DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Vec3d3) >= this.minHealth.get())) return false;
        return true;
    }

    private RenderBlock lambda$new$0() {
        return new RenderBlock(this, null);
    }

    private static LivingEntity lambda$findTarget$18(Entity Entity2) {
        return (LivingEntity)Entity2;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.fullBreak.get().booleanValue()) {
            this.target = CityUtils.getPlayerTarget(this.placeRange.get());
            if (this.target == null || this.mc.player.distanceTo((Entity)this.target) > (float)this.range.get().intValue()) {
                return;
            }
            this.placed = false;
            this.placePositions.clear();
            int n = -1;
            n = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
            if (n == -1) {
                return;
            }
            this.findPlacePos(this.target1);
            for (int i = 0; i < this.placePositions.size(); ++i) {
                BlockPos BlockPos2 = this.placePositions.get(this.placePositions.size() - 1);
                if (!BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotate.get(), 50, true)) continue;
                this.placePositions.remove(BlockPos2);
                this.placed = true;
                if (!false) continue;
                return;
            }
        }
    }

    static Setting access$500(CustomCrystalAura customCrystalAura) {
        return customCrystalAura.renderDamage;
    }

    @EventHandler(priority=100)
    private void onPlaySound(PlaySoundEvent playSoundEvent) {
        if (playSoundEvent.sound.getCategory().getName().equals(SoundCategory.BLOCKS.getName()) && playSoundEvent.sound.getId().getPath().equals("entity.generic.explode") && this.cancelCrystalMode.get() == CancelCrystalMode.Sound) {
            this.removalQueue.forEach(this::lambda$onPlaySound$2);
            this.removalQueue.clear();
        }
    }

    private void add(BlockPos BlockPos2) {
        if (!this.placePositions.contains(BlockPos2) && this.mc.world.getBlockState(BlockPos2).getMaterial().isReplaceable() && this.mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), BlockPos2, ShapeContext.absent())) {
            this.placePositions.add(BlockPos2);
        }
    }

    private boolean lambda$getCrystalStream$5(Entity Entity2) {
        return this.shouldBreak((EndCrystalEntity)Entity2);
    }

    private void lambda$onPlaySound$2(Integer n) {
        this.mc.world.removeEntity(n.intValue());
    }

    private boolean lambda$findTarget$12(Entity Entity2) {
        return Entity2 != this.mc.player;
    }

    private boolean lambda$findTarget$16(Entity Entity2) {
        return (double)Entity2.distanceTo((Entity)this.mc.player) <= this.targetRange.get() * 2.0;
    }

    private static boolean lambda$findTarget$14(Entity Entity2) {
        return Entity2 instanceof LivingEntity;
    }

    private void lambda$onTick$1(Integer n) {
        this.mc.world.removeEntity(n.intValue());
    }

    private Direction rayTraceCheck(BlockPos BlockPos2, boolean bl) {
        Vec3d Vec3d2 = new Vec3d(this.mc.player.getX(), this.mc.player.getY() + (double)this.mc.player.getEyeHeight(this.mc.player.getPose()), this.mc.player.getZ());
        for (Direction Direction2 : Direction.values()) {
            RaycastContext RaycastContext2 = new RaycastContext(Vec3d2, new Vec3d((double)BlockPos2.getX() + 0.5 + (double)Direction2.getVector().getX() * 0.5, (double)BlockPos2.getY() + 0.5 + (double)Direction2.getVector().getY() * 0.5, (double)BlockPos2.getZ() + 0.5 + (double)Direction2.getVector().getZ() * 0.5), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)this.mc.player);
            BlockHitResult BlockHitResult2 = this.mc.world.raycast(RaycastContext2);
            if (BlockHitResult2 == null || BlockHitResult2.getType() != HitResult.class_240.BLOCK || !BlockHitResult2.getBlockPos().equals((Object)BlockPos2)) continue;
            return Direction2;
        }
        if (bl) {
            if ((double)BlockPos2.getY() > Vec3d2.y) {
                return Direction.DOWN;
            }
            return Direction.UP;
        }
        return null;
    }

    static Setting access$300(CustomCrystalAura customCrystalAura) {
        return customCrystalAura.lineColor;
    }

    private static boolean lambda$getCrystalStream$3(Entity Entity2) {
        return Entity2 instanceof EndCrystalEntity;
    }

    private void placeBlock(Vec3d Vec3d2, Hand Hand2) {
        Object object;
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.interactionManager == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        if (this.mc.world.isAir(new BlockPos(Vec3d2))) {
            PlayerUtils.placeBlock(new BlockPos(Vec3d2), this.supportSlot, Hand.MAIN_HAND);
            this.supportDelayLeft = this.supportDelay.get();
        }
        BlockPos BlockPos2 = new BlockPos(Vec3d2);
        Direction Direction2 = this.rayTraceCheck(BlockPos2, true);
        if (this.rotationMode.get() == RotationMode.Place || this.rotationMode.get() == RotationMode.Both) {
            object = PlayerUtils.calculateAngle(this.strictLook.get() != false ? new Vec3d((double)BlockPos2.getX() + 0.5 + (double)Direction2.getVector().getX() * 1.0 / 2.0, (double)BlockPos2.getY() + 0.5 + (double)Direction2.getVector().getY() * 1.0 / 2.0, (double)BlockPos2.getZ() + 0.5 + (double)Direction2.getVector().getZ() * 1.0 / 2.0) : Vec3d2.add(0.5, 1.0, 0.5));
            Rotations.rotate(object[0], object[1], 25, () -> this.lambda$placeBlock$19(Hand2, Direction2, Vec3d2));
        } else {
            this.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand2, new BlockHitResult(this.mc.player.getPos(), Direction2, new BlockPos(Vec3d2), false)));
            if (this.swing.get().booleanValue()) {
                this.mc.player.swingHand(Hand2);
            } else {
                this.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(Hand2));
            }
        }
        if (this.render.get().booleanValue()) {
            object = this.renderBlockPool.get();
            ((RenderBlock)object).reset(Vec3d2);
            RenderBlock.access$002((RenderBlock)object, DamageCalcUtils.crystalDamage(this.target, this.bestBlock.add(0.5, 1.0, 0.5)));
            this.renderBlocks.add((RenderBlock)object);
        }
    }

    static Setting access$900(CustomCrystalAura customCrystalAura) {
        return customCrystalAura.damageColor;
    }

    @Override
    public void onDeactivate() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (this.switchBack.get().booleanValue() && this.preSlot != -1) {
            this.mc.player.inventory.selectedSlot = this.preSlot;
        }
        for (RenderBlock renderBlock : this.renderBlocks) {
            this.renderBlockPool.free(renderBlock);
        }
        this.renderBlocks.clear();
        if (this.target != null && this.resetRotations.get().booleanValue() && (this.rotationMode.get() == RotationMode.Both || this.rotationMode.get() == RotationMode.Place || this.rotationMode.get() == RotationMode.Break)) {
            RotationUtils.packetRotate(this.mc.player.yaw, this.mc.player.pitch);
        }
    }

    @EventHandler(priority=100)
    private void onTick(SendMovementPacketsEvent.Pre pre) {
        Iterator<RenderBlock> iterator = this.renderBlocks.iterator();
        while (iterator.hasNext()) {
            RenderBlock renderBlock = iterator.next();
            if (!renderBlock.shouldRemove()) continue;
            iterator.remove();
            this.renderBlockPool.free(renderBlock);
        }
        --this.placeDelayLeft;
        --this.breakDelayLeft;
        --this.supportDelayLeft;
        if (this.target == null) {
            this.heldCrystal = null;
            this.locked = false;
        }
        if (this.mc.player.isUsingItem() && (this.mc.player.getMainHandStack().getItem().isFood() || this.mc.player.getOffHandStack().getItem().isFood()) && this.pauseOnEat.get().booleanValue() || this.mc.interactionManager.isBreakingBlock() && this.pauseOnMine.get().booleanValue() || this.mc.player.isUsingItem() && (this.mc.player.getMainHandStack().getItem() instanceof PotionItem || this.mc.player.getOffHandStack().getItem() instanceof PotionItem) && this.pauseOnDrink.get().booleanValue()) {
            return;
        }
        if (this.locked && this.heldCrystal != null && (!this.surroundBreak.get().booleanValue() && this.target.getBlockPos().getSquaredDistance(new Box2(this.heldCrystal.getX(), this.heldCrystal.getY(), this.heldCrystal.getZ())) == 4.0 || !this.surroundHold.get().booleanValue() && this.target.getBlockPos().getSquaredDistance(new Box2(this.heldCrystal.getX(), this.heldCrystal.getY(), this.heldCrystal.getZ())) == 2.0)) {
            this.heldCrystal = null;
            this.locked = false;
        }
        if (this.heldCrystal != null && (double)this.mc.player.distanceTo((Entity)this.heldCrystal) > this.breakRange.get()) {
            this.heldCrystal = null;
            this.locked = false;
        }
        boolean bl = false;
        if (this.heldCrystal != null) {
            for (Entity Entity2 : this.mc.world.getEntities()) {
                if (!(Entity2 instanceof EndCrystalEntity) || this.heldCrystal == null || !Entity2.getBlockPos().equals((Object)this.heldCrystal.getBlockPos())) continue;
                bl = true;
                break;
            }
            if (!bl) {
                this.heldCrystal = null;
                this.locked = false;
            }
        }
        boolean bl2 = false;
        if ((double)this.getTotalHealth((PlayerEntity)this.mc.player) <= this.minHealth.get() && this.placeMode.get() != Mode.Suicide) {
            return;
        }
        if (this.target != null && this.heldCrystal != null && this.placeDelayLeft <= 0 && this.mc.world.raycast(new RaycastContext(this.target.getPos(), this.heldCrystal.getPos(), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)this.target)).getType() == HitResult.class_240.MISS) {
            this.locked = false;
        }
        if (this.heldCrystal == null) {
            this.locked = false;
        }
        if (this.locked && !this.facePlace.get().booleanValue()) {
            return;
        }
        if (!this.multiTarget.get().booleanValue()) {
            this.findTarget();
            if (this.target == null) {
                return;
            }
            if (this.breakDelayLeft <= 0) {
                this.singleBreak();
            }
        } else if (this.breakDelayLeft <= 0) {
            this.multiBreak();
        }
        if (this.broken) {
            this.broken = false;
            return;
        }
        if (!(this.smartDelay.get().booleanValue() || this.placeDelayLeft <= 0 || (this.surroundHold.get().booleanValue() || this.target == null || this.surroundBreak.get().booleanValue() && this.isSurrounded(this.target)) && this.heldCrystal == null || this.spamFacePlace.get().booleanValue())) {
            return;
        }
        if (this.switchMode.get() == SwitchMode.None && this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && this.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        if (this.place.get().booleanValue()) {
            int n;
            int n2;
            int n3;
            if (this.target == null) {
                return;
            }
            if (!this.multiPlace.get().booleanValue() && this.getCrystalStream().count() > 0L) {
                return;
            }
            if (this.surroundHold.get().booleanValue() && this.heldCrystal == null && ((n3 = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && n3 < 9 || this.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL)) {
                this.bestBlock = this.findOpen(this.target);
                if (this.bestBlock != null) {
                    this.doHeldCrystal();
                    return;
                }
            }
            if (this.surroundBreak.get().booleanValue() && this.heldCrystal == null && this.isSurrounded(this.target) && ((n2 = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) != -1 && n2 < 9 || this.mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL)) {
                this.bestBlock = this.findOpenSurround(this.target);
                if (this.bestBlock != null) {
                    this.doHeldCrystal();
                    return;
                }
            }
            if (((n = InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).slot) == -1 || n > 9) && this.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                return;
            }
            this.findValidBlocks(this.target);
            if (this.bestBlock == null) {
                this.findFacePlace(this.target);
            }
            if (this.bestBlock == null) {
                return;
            }
            if (this.facePlace.get().booleanValue() && Math.sqrt(this.target.squaredDistanceTo(this.bestBlock)) <= 2.0) {
                if ((double)(this.target.getHealth() + this.target.getAbsorptionAmount()) < this.facePlaceHealth.get()) {
                    bl2 = true;
                } else {
                    Iterable iterable = this.target.getArmorItems();
                    for (ItemStack ItemStack2 : iterable) {
                        if (ItemStack2 == null || ItemStack2.isEmpty() || !((double)(ItemStack2.getMaxDamage() - ItemStack2.getDamage()) / (double)ItemStack2.getMaxDamage() * 100.0 <= this.facePlaceDurability.get())) continue;
                        bl2 = true;
                    }
                }
            }
            if (this.bestBlock != null && (this.bestDamage >= this.minDamage.get() && !this.locked || bl2)) {
                if (this.switchMode.get() != SwitchMode.None) {
                    this.doSwitch();
                }
                if (this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && this.mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                    return;
                }
                if (!this.smartDelay.get().booleanValue()) {
                    this.placeDelayLeft = this.placeDelay.get();
                    this.placeBlock(this.bestBlock, this.getHand());
                } else if (this.smartDelay.get().booleanValue() && (this.placeDelayLeft <= 0 || this.bestDamage - this.lastDamage > this.healthDifference.get() || this.spamFacePlace.get().booleanValue() && bl2)) {
                    this.lastDamage = this.bestDamage;
                    this.placeBlock(this.bestBlock, this.getHand());
                    if (this.placeDelayLeft <= 0) {
                        this.placeDelayLeft = 10;
                    }
                }
            }
            if (this.switchMode.get() == SwitchMode.Spoof && this.preSlot != this.mc.player.inventory.selectedSlot && this.preSlot != -1) {
                this.mc.player.inventory.selectedSlot = this.preSlot;
            }
        }
    }

    private double lambda$singleBreak$8(Entity Entity2) {
        return DamageCalcUtils.crystalDamage(this.target, Entity2.getPos());
    }

    private void attackCrystal(EndCrystalEntity EndCrystalEntity2, int n) {
        this.mc.interactionManager.attackEntity((PlayerEntity)this.mc.player, (Entity)EndCrystalEntity2);
        this.removalQueue.add(EndCrystalEntity2.getEntityId());
        if (this.swing.get().booleanValue()) {
            this.mc.player.swingHand(this.getHand());
        } else {
            this.mc.player.networkHandler.sendPacket((Packet)new HandSwingC2SPacket(this.getHand()));
        }
        this.mc.player.inventory.selectedSlot = n;
        if (this.heldCrystal != null && EndCrystalEntity2.getBlockPos().equals((Object)this.heldCrystal.getBlockPos())) {
            this.heldCrystal = null;
            this.locked = false;
        }
    }

    private void multiBreak() {
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        this.crystalMap.clear();
        this.crystalList.clear();
        this.getCrystalStream().forEach(this::lambda$multiBreak$10);
        EndCrystalEntity EndCrystalEntity2 = this.findBestCrystal(this.crystalMap);
        if (EndCrystalEntity2 != null) {
            this.hitCrystal(EndCrystalEntity2);
        }
    }

    public static final class TargetMode
    extends Enum<TargetMode> {
        public static final /* enum */ TargetMode HighestXDamages;
        public static final /* enum */ TargetMode MostDamage;
        private static final TargetMode[] $VALUES;

        static {
            MostDamage = new TargetMode();
            HighestXDamages = new TargetMode();
            $VALUES = TargetMode.$values();
        }

        public static TargetMode valueOf(String string) {
            return Enum.valueOf(TargetMode.class, string);
        }

        public static TargetMode[] values() {
            return (TargetMode[])$VALUES.clone();
        }

        private static TargetMode[] $values() {
            return new TargetMode[]{MostDamage, HighestXDamages};
        }
    }

    public static final class RotationMode
    extends Enum<RotationMode> {
        public static final /* enum */ RotationMode Both;
        public static final /* enum */ RotationMode Break;
        private static final RotationMode[] $VALUES;
        public static final /* enum */ RotationMode None;
        public static final /* enum */ RotationMode Place;

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

        public static RotationMode valueOf(String string) {
            return Enum.valueOf(RotationMode.class, string);
        }

        private static RotationMode[] $values() {
            return new RotationMode[]{Place, Break, Both, None};
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode None;
        public static final /* enum */ Mode Safe;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Suicide;

        static {
            Safe = new Mode();
            Suicide = new Mode();
            None = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        private static Mode[] $values() {
            return new Mode[]{Safe, Suicide, None};
        }
    }

    public static final class SwitchMode
    extends Enum<SwitchMode> {
        public static final /* enum */ SwitchMode None;
        public static final /* enum */ SwitchMode Spoof;
        public static final /* enum */ SwitchMode Auto;
        private static final SwitchMode[] $VALUES;

        public static SwitchMode[] values() {
            return (SwitchMode[])$VALUES.clone();
        }

        private static SwitchMode[] $values() {
            return new SwitchMode[]{Auto, Spoof, None};
        }

        public static SwitchMode valueOf(String string) {
            return Enum.valueOf(SwitchMode.class, string);
        }

        static {
            Auto = new SwitchMode();
            Spoof = new SwitchMode();
            None = new SwitchMode();
            $VALUES = SwitchMode.$values();
        }
    }

    public static final class CancelCrystalMode
    extends Enum<CancelCrystalMode> {
        private static final CancelCrystalMode[] $VALUES;
        public static final /* enum */ CancelCrystalMode Sound;
        public static final /* enum */ CancelCrystalMode Hit;

        private static CancelCrystalMode[] $values() {
            return new CancelCrystalMode[]{Sound, Hit};
        }

        public static CancelCrystalMode[] values() {
            return (CancelCrystalMode[])$VALUES.clone();
        }

        public static CancelCrystalMode valueOf(String string) {
            return Enum.valueOf(CancelCrystalMode.class, string);
        }

        static {
            Sound = new CancelCrystalMode();
            Hit = new CancelCrystalMode();
            $VALUES = CancelCrystalMode.$values();
        }
    }

    private class RenderBlock {
        final CustomCrystalAura this$0;
        private int z;
        private int timer;
        private double damage;
        private int x;
        private int y;

        public void reset(Vec3d Vec3d2) {
            this.x = MathHelper.floor((double)Vec3d2.getX());
            this.y = MathHelper.floor((double)Vec3d2.getY());
            this.z = MathHelper.floor((double)Vec3d2.getZ());
            this.timer = (Integer)CustomCrystalAura.access$100(this.this$0).get();
        }

        public void render2D() {
            if (((Boolean)CustomCrystalAura.access$500(this.this$0).get()).booleanValue()) {
                CustomCrystalAura.access$600().set((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5);
                if (NametagUtils.to2D(CustomCrystalAura.access$600(), (Double)CustomCrystalAura.access$700(this.this$0).get())) {
                    NametagUtils.begin(CustomCrystalAura.access$600());
                    TextRenderer.get().begin(1.0, false, true);
                    String string = String.valueOf(Math.round(this.damage));
                    switch ((Integer)CustomCrystalAura.access$800(this.this$0).get()) {
                        case 0: {
                            string = String.valueOf(Math.round(this.damage));
                            break;
                        }
                        case 1: {
                            string = String.valueOf((double)Math.round(this.damage * 10.0) / 10.0);
                            break;
                        }
                        case 2: {
                            string = String.valueOf((double)Math.round(this.damage * 100.0) / 100.0);
                            break;
                        }
                        case 3: {
                            string = String.valueOf((double)Math.round(this.damage * 1000.0) / 1000.0);
                        }
                    }
                    double d = TextRenderer.get().getWidth(string) / 2.0;
                    TextRenderer.get().render(string, -d, 0.0, (Color)CustomCrystalAura.access$900(this.this$0).get());
                    TextRenderer.get().end();
                    NametagUtils.end();
                }
            }
        }

        private RenderBlock(CustomCrystalAura customCrystalAura) {
            this.this$0 = customCrystalAura;
        }

        static double access$002(RenderBlock renderBlock, double d) {
            renderBlock.damage = d;
            return renderBlock.damage;
        }

        public boolean shouldRemove() {
            if (this.timer <= 0) {
                return true;
            }
            --this.timer;
            return false;
        }

        RenderBlock(CustomCrystalAura customCrystalAura, 1 var2_2) {
            this(customCrystalAura);
        }

        public void render3D() {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.x, this.y, this.z, 1.0, (Color)CustomCrystalAura.access$200(this.this$0).get(), (Color)CustomCrystalAura.access$300(this.this$0).get(), (ShapeMode)((Object)CustomCrystalAura.access$400(this.this$0).get()), 0);
        }
    }
}

