/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.class_1268;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1511;
import net.minecraft.class_1657;
import net.minecraft.class_1743;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1812;
import net.minecraft.class_1829;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2879;
import net.minecraft.class_2885;
import net.minecraft.class_3419;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

public class CrystalAura
extends Module {
    private final Setting<Double> minHealth;
    private final Setting<Boolean> smartDelay;
    private final List<Double> crystalList;
    private final Setting<Double> verticalRange;
    private int placeDelayLeft;
    private final Setting<SettingColor> sideColor;
    private final Setting<Boolean> support;
    private final SettingGroup sgRender;
    private final Setting<Double> maxDamage;
    private final Setting<Double> placeWallsRange;
    private final Setting<Boolean> pauseOnEat;
    private boolean canSupport;
    private final Setting<TargetMode> targetMode;
    private final SettingGroup sgMisc;
    private final Map<class_1511, List<Double>> crystalMap;
    private final Setting<Double> minDamage;
    private final Setting<Double> placeRange;
    private final Setting<Mode> breakMode;
    private static final Vec3 pos;
    private double lastDamage;
    private final Setting<Boolean> swing;
    private final Setting<Mode> placeMode;
    private class_1309 target;
    private class_1511 bestBreak;
    private final Setting<Boolean> ignoreWalls;
    private final Setting<Boolean> facePlace;
    private final Setting<Double> facePlaceHealth;
    private int supportDelayLeft;
    private final Setting<Boolean> surroundHold;
    private class_1511 heldCrystal;
    private final Setting<RotationMode> rotationMode;
    private class_243 bestBlock;
    private final List<Integer> removalQueue;
    private final Setting<Integer> breakDelay;
    private final Setting<Double> damageScale;
    private int preSlot;
    private final Setting<Double> healthDifference;
    private final Setting<Boolean> surroundBreak;
    private final Setting<Double> breakRange;
    private final Setting<Integer> numberOfDamages;
    private double bestDamage;
    private final Setting<Integer> supportDelay;
    private final Setting<Boolean> pauseOnMine;
    private boolean locked;
    private final SettingGroup sgTarget;
    private final Setting<Boolean> resetRotations;
    private final Setting<SettingColor> lineColor;
    private final Setting<Boolean> spamFacePlace;
    private boolean broken;
    private final Setting<Boolean> strictLook;
    private final Setting<Integer> renderTimer;
    private final Setting<Boolean> multiTarget;
    private final Setting<CancelCrystalMode> cancelCrystalMode;
    private final Setting<Double> targetRange;
    private final Setting<SettingColor> damageColor;
    private final Setting<SwitchMode> switchMode;
    private final Setting<Double> facePlaceDurability;
    private final Pool<RenderBlock> renderBlockPool;
    static final boolean $assertionsDisabled;
    private final Setting<Boolean> antiWeakness;
    private final Setting<Object2BooleanMap<class_1299<?>>> entities;
    private final Setting<Boolean> rayTrace;
    private final Setting<Boolean> switchBack;
    private final SettingGroup sgPlace;
    private final Setting<Boolean> oldPlace;
    private final Setting<Boolean> pauseOnDrink;
    private int breakDelayLeft;
    private final Setting<Integer> placeDelay;
    private final Setting<Boolean> place;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Boolean> multiPlace;
    private final List<RenderBlock> renderBlocks;
    private int supportSlot;
    private final SettingGroup sgRotations;
    private final SettingGroup sgPause;
    private final Setting<Boolean> render;
    private final Setting<Integer> roundDamage;
    private final Setting<Boolean> supportBackup;
    private final SettingGroup sgBreak;
    private final Setting<Boolean> renderDamage;

    @Override
    public String getInfoString() {
        if (this.target != null && this.target instanceof class_1657) {
            return this.target.method_5820();
        }
        if (this.target != null) {
            return this.target.method_5864().method_5897().getString();
        }
        return null;
    }

    static Setting access$100(CrystalAura crystalAura) {
        return crystalAura.renderTimer;
    }

    /*
     * Unable to fully structure code
     */
    private class_243 findOpen(class_1309 var1_1) {
        block7: {
            block8: {
                block6: {
                    if (!CrystalAura.$assertionsDisabled && this.mc.field_1724 == null) {
                        throw new AssertionError();
                    }
                    var2_2 = 0;
                    var3_3 = 0;
                    if (!this.isValid(var1_1.method_24515().method_10069(1, -1, 0))) break block6;
                    v0 = new class_2382(var1_1.method_24515().method_10263() + 1, var1_1.method_24515().method_10264() - 1, var1_1.method_24515().method_10260());
                    if (!(Math.sqrt(this.mc.field_1724.method_24515().method_10262(v0)) < this.placeRange.get())) break block6;
                    var2_2 = 1;
                    break block7;
                }
                if (!this.isValid(var1_1.method_24515().method_10069(-1, -1, 0))) break block8;
                v1 = new class_2382(var1_1.method_24515().method_10263() - 1, var1_1.method_24515().method_10264() - 1, var1_1.method_24515().method_10260());
                if (!(Math.sqrt(this.mc.field_1724.method_24515().method_10262(v1)) < this.placeRange.get())) break block8;
                var2_2 = -1;
                break block7;
            }
            if (!this.isValid(var1_1.method_24515().method_10069(0, -1, 1))) ** GOTO lbl-1000
            v2 = new class_2382(var1_1.method_24515().method_10263(), var1_1.method_24515().method_10264() - 1, var1_1.method_24515().method_10260() + 1);
            if (Math.sqrt(this.mc.field_1724.method_24515().method_10262(v2)) < this.placeRange.get()) {
                var3_3 = 1;
            } else if (this.isValid(var1_1.method_24515().method_10069(0, -1, -1))) {
                v3 = new class_2382(var1_1.method_24515().method_10263(), var1_1.method_24515().method_10264() - 1, var1_1.method_24515().method_10260() - 1);
                if (Math.sqrt(this.mc.field_1724.method_24515().method_10262(v3)) < this.placeRange.get()) {
                    var3_3 = -1;
                }
            }
        }
        if (var2_2 != 0 || var3_3 != 0) {
            return new class_243((double)var1_1.method_24515().method_10263() + 0.5 + (double)var2_2, (double)(var1_1.method_24515().method_10264() - 1), (double)var1_1.method_24515().method_10260() + 0.5 + (double)var3_3);
        }
        return null;
    }

    private boolean lambda$getCrystalStream$4(class_1297 class_12972) {
        return (double)class_12972.method_5739((class_1297)this.mc.field_1724) <= this.breakRange.get();
    }

    private void lambda$onTick$1(Integer n) {
        this.mc.field_1687.method_2945(n.intValue());
    }

    private void doSwitch() {
        int n;
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301 && (n = InvUtils.findItemWithCount((class_1792)class_1802.field_8301).slot) != -1 && n < 9) {
            this.preSlot = this.mc.field_1724.field_7514.field_7545;
            this.mc.field_1724.field_7514.field_7545 = n;
        }
    }

    private boolean isValid(class_2338 class_23382) {
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        return (this.canSupport && this.isEmpty(class_23382) && class_23382.method_10264() - this.target.method_24515().method_10264() == -1 && this.supportDelayLeft <= 0 || this.mc.field_1687.method_8320(class_23382).method_26204() == class_2246.field_9987 || this.mc.field_1687.method_8320(class_23382).method_26204() == class_2246.field_10540) && this.isEmpty(class_23382.method_10069(0, 1, 0));
    }

    private double lambda$singleBreak$8(class_1297 class_12972) {
        return DamageCalcUtils.crystalDamage(this.target, class_12972.method_19538());
    }

    private static boolean lambda$findTarget$13(class_1297 class_12972) {
        return !(class_12972 instanceof class_1657) || Friends.get().attack((class_1657)class_12972);
    }

    private class_1511 findBestCrystal(Map<class_1511, List<Double>> map) {
        block7: {
            double d;
            block6: {
                this.bestDamage = 0.0;
                d = 0.0;
                if (this.targetMode.get() != TargetMode.HighestXDamages) break block6;
                for (Map.Entry<class_1511, List<Double>> entry : map.entrySet()) {
                    for (int i = 0; i < entry.getValue().size() && i < this.numberOfDamages.get(); ++i) {
                        d += entry.getValue().get(i).doubleValue();
                        if (!false) continue;
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
            for (Map.Entry<class_1511, List<Double>> entry : map.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); ++i) {
                    d += entry.getValue().get(i).doubleValue();
                    if (!false) continue;
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

    @EventHandler(priority=100)
    private void onPlaySound(PlaySoundEvent playSoundEvent) {
        if (playSoundEvent.sound.method_4774().method_14840().equals(class_3419.field_15245.method_14840()) && playSoundEvent.sound.method_4775().method_12832().equals("entity.generic.explode") && this.cancelCrystalMode.get() == CancelCrystalMode.Sound) {
            this.removalQueue.forEach(this::lambda$onPlaySound$2);
            this.removalQueue.clear();
        }
    }

    static Setting access$200(CrystalAura crystalAura) {
        return crystalAura.sideColor;
    }

    @Override
    public void onActivate() {
        this.preSlot = -1;
        this.placeDelayLeft = 0;
        this.breakDelayLeft = 0;
        this.heldCrystal = null;
        this.locked = false;
        this.broken = false;
    }

    static Setting access$400(CrystalAura crystalAura) {
        return crystalAura.shapeMode;
    }

    private void placeBlock(class_243 class_2432, class_1268 class_12682) {
        Object object;
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1761 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        if (this.mc.field_1687.method_22347(new class_2338(class_2432))) {
            PlayerUtils.placeBlock(new class_2338(class_2432), this.supportSlot, class_1268.field_5808);
            this.supportDelayLeft = this.supportDelay.get();
        }
        class_2338 class_23382 = new class_2338(class_2432);
        class_2350 class_23502 = this.rayTraceCheck(class_23382, true);
        if (this.rotationMode.get() == RotationMode.Place || this.rotationMode.get() == RotationMode.Both) {
            object = PlayerUtils.calculateAngle(this.strictLook.get() != false ? new class_243((double)class_23382.method_10263() + 0.5 + (double)class_23502.method_10163().method_10263() * 1.0 / 2.0, (double)class_23382.method_10264() + 0.5 + (double)class_23502.method_10163().method_10264() * 1.0 / 2.0, (double)class_23382.method_10260() + 0.5 + (double)class_23502.method_10163().method_10260() * 1.0 / 2.0) : class_2432.method_1031(0.5, 1.0, 0.5));
            Rotations.rotate(object[0], object[1], 25, () -> this.lambda$placeBlock$19(class_12682, class_23502, class_2432));
        } else {
            this.mc.field_1724.field_3944.method_2883((class_2596)new class_2885(class_12682, new class_3965(this.mc.field_1724.method_19538(), class_23502, new class_2338(class_2432), false)));
            if (this.swing.get().booleanValue()) {
                this.mc.field_1724.method_6104(class_12682);
            } else {
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2879(class_12682));
            }
        }
        if (this.render.get().booleanValue()) {
            object = this.renderBlockPool.get();
            ((RenderBlock)object).reset(class_2432);
            RenderBlock.access$002((RenderBlock)object, DamageCalcUtils.crystalDamage(this.target, this.bestBlock.method_1031(0.5, 1.0, 0.5)));
            this.renderBlocks.add((RenderBlock)object);
        }
    }

    private boolean lambda$getCrystalStream$5(class_1297 class_12972) {
        return this.shouldBreak((class_1511)class_12972);
    }

    static Vec3 access$600() {
        return pos;
    }

    private boolean lambda$findTarget$12(class_1297 class_12972) {
        return class_12972 != this.mc.field_1724;
    }

    private float getTotalHealth(class_1657 class_16572) {
        return class_16572.method_6032() + class_16572.method_6067();
    }

    private Stream<class_1297> getCrystalStream() {
        return Streams.stream((Iterable)this.mc.field_1687.method_18112()).filter(CrystalAura::lambda$getCrystalStream$3).filter(this::lambda$getCrystalStream$4).filter(class_1297::method_5805).filter(this::lambda$getCrystalStream$5).filter(this::lambda$getCrystalStream$6).filter(this::lambda$getCrystalStream$7);
    }

    private static class_1309 lambda$findTarget$18(class_1297 class_12972) {
        return (class_1309)class_12972;
    }

    private boolean isEmpty(class_2338 class_23382) {
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        return this.mc.field_1687.method_8320(class_23382).method_26215() && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260(), (double)class_23382.method_10263() + 1.0, (double)class_23382.method_10264() + 2.0, (double)class_23382.method_10260() + 1.0)).isEmpty();
    }

    private boolean lambda$findTarget$16(class_1297 class_12972) {
        return (double)class_12972.method_5739((class_1297)this.mc.field_1724) <= this.targetRange.get() * 2.0;
    }

    private void hitCrystal(class_1511 class_15112) {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1761 == null) {
            throw new AssertionError();
        }
        int n = this.mc.field_1724.field_7514.field_7545;
        if (this.mc.field_1724.method_6088().containsKey(class_1294.field_5911) && this.antiWeakness.get().booleanValue()) {
            for (int i = 0; i < 9; ++i) {
                if (!(this.mc.field_1724.field_7514.method_5438(i).method_7909() instanceof class_1829) && !(this.mc.field_1724.field_7514.method_5438(i).method_7909() instanceof class_1743)) continue;
                this.mc.field_1724.field_7514.field_7545 = i;
                break;
            }
        }
        if (this.rotationMode.get() == RotationMode.Break || this.rotationMode.get() == RotationMode.Both) {
            float[] fArray = PlayerUtils.calculateAngle(class_15112.method_19538());
            Rotations.rotate(fArray[0], fArray[1], 30, () -> this.lambda$hitCrystal$11(class_15112, n));
        } else {
            this.attackCrystal(class_15112, n);
        }
        this.broken = true;
        this.breakDelayLeft = this.breakDelay.get();
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

    private boolean isSurrounded(class_1309 class_13092) {
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        return !this.mc.field_1687.method_8320(class_13092.method_24515().method_10069(1, 0, 0)).method_26215() && !this.mc.field_1687.method_8320(class_13092.method_24515().method_10069(-1, 0, 0)).method_26215() && !this.mc.field_1687.method_8320(class_13092.method_24515().method_10069(0, 0, 1)).method_26215() && !this.mc.field_1687.method_8320(class_13092.method_24515().method_10069(0, 0, -1)).method_26215();
    }

    static {
        $assertionsDisabled = !CrystalAura.class.desiredAssertionStatus();
        pos = new Vec3();
    }

    static Setting access$500(CrystalAura crystalAura) {
        return crystalAura.renderDamage;
    }

    private class_243 findOpenSurround(class_1309 class_13092) {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        int n = 0;
        int n2 = 0;
        if (this.validSurroundBreak(class_13092, 2, 0)) {
            n = 2;
        } else if (this.validSurroundBreak(class_13092, -2, 0)) {
            n = -2;
        } else if (this.validSurroundBreak(class_13092, 0, 2)) {
            n2 = 2;
        } else if (this.validSurroundBreak(class_13092, 0, -2)) {
            n2 = -2;
        }
        if (n != 0 || n2 != 0) {
            return new class_243((double)class_13092.method_24515().method_10263() + 0.5 + (double)n, (double)(class_13092.method_24515().method_10264() - 1), (double)class_13092.method_24515().method_10260() + 0.5 + (double)n2);
        }
        return null;
    }

    private void lambda$multiBreak$10(class_1297 class_12972) {
        for (class_1297 class_12973 : this.mc.field_1687.method_18112()) {
            if (class_12973 == this.mc.field_1724 || !this.entities.get().getBoolean((Object)class_12973.method_5864()) || !((double)this.mc.field_1724.method_5739(class_12973) <= this.targetRange.get()) || !class_12973.method_5805() || !(class_12973 instanceof class_1309) || class_12973 instanceof class_1657 && !Friends.get().attack((class_1657)class_12973)) continue;
            this.crystalList.add(DamageCalcUtils.crystalDamage((class_1309)class_12973, class_12972.method_19538()));
        }
        if (!this.crystalList.isEmpty()) {
            this.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
            this.crystalMap.put((class_1511)class_12972, new ArrayList<Double>(this.crystalList));
            this.crystalList.clear();
        }
    }

    private void findFacePlace(class_1309 class_13092) {
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        class_2338 class_23382 = class_13092.method_24515();
        if (this.mc.field_1687.method_8320(class_23382.method_10069(1, 1, 0)).method_26215() && Math.sqrt(this.mc.field_1724.method_24515().method_10262((class_2382)class_23382.method_10069(1, 1, 0))) <= this.placeRange.get() && this.getDamagePlace(class_23382.method_10069(1, 1, 0))) {
            this.bestBlock = class_13092.method_19538().method_1031(1.0, 0.0, 0.0);
        } else if (this.mc.field_1687.method_8320(class_23382.method_10069(-1, 1, 0)).method_26215() && Math.sqrt(this.mc.field_1724.method_24515().method_10262((class_2382)class_23382.method_10069(-1, 1, 0))) <= this.placeRange.get() && this.getDamagePlace(class_23382.method_10069(-1, 1, 0))) {
            this.bestBlock = class_13092.method_19538().method_1031(-1.0, 0.0, 0.0);
        } else if (this.mc.field_1687.method_8320(class_23382.method_10069(0, 1, 1)).method_26215() && Math.sqrt(this.mc.field_1724.method_24515().method_10262((class_2382)class_23382.method_10069(0, 1, 1))) <= this.placeRange.get() && this.getDamagePlace(class_23382.method_10069(0, 1, 1))) {
            this.bestBlock = class_13092.method_19538().method_1031(0.0, 0.0, 1.0);
        } else if (this.mc.field_1687.method_8320(class_23382.method_10069(0, 1, -1)).method_26215() && Math.sqrt(this.mc.field_1724.method_24515().method_10262((class_2382)class_23382.method_10069(0, 1, -1))) <= this.placeRange.get() && this.getDamagePlace(class_23382.method_10069(0, 1, -1))) {
            this.bestBlock = class_13092.method_19538().method_1031(0.0, 0.0, -1.0);
        }
    }

    private boolean shouldBreak(class_1511 class_15112) {
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        return this.heldCrystal == null || this.surroundHold.get() == false && this.surroundBreak.get() == false || this.placeDelayLeft <= 0 && (!this.heldCrystal.method_24515().equals((Object)class_15112.method_24515()) || this.mc.field_1687.method_17742(new class_3959(this.target.method_19538(), this.heldCrystal.method_19538(), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.target)).method_17783() == class_239.class_240.field_1333 || (double)this.target.method_5739((class_1297)this.heldCrystal) > 1.5 && !this.isSurrounded(this.target));
    }

    private void doHeldCrystal() {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (this.switchMode.get() != SwitchMode.None) {
            this.doSwitch();
        }
        if (this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301) {
            return;
        }
        this.bestDamage = DamageCalcUtils.crystalDamage(this.target, this.bestBlock.method_1031(0.0, 1.0, 0.0));
        this.heldCrystal = new class_1511((class_1937)this.mc.field_1687, this.bestBlock.field_1352, this.bestBlock.field_1351 + 1.0, this.bestBlock.field_1350);
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

    public CrystalAura() {
        super(Categories.Combat, "crystal-aura", "Automatically places and breaks crystals to damage other players.");
        this.sgPlace = this.settings.createGroup("Place");
        this.sgBreak = this.settings.createGroup("Break");
        this.sgTarget = this.settings.createGroup("Target");
        this.sgPause = this.settings.createGroup("Pause");
        this.sgRotations = this.settings.createGroup("Rotations");
        this.sgMisc = this.settings.createGroup("Misc");
        this.sgRender = this.settings.createGroup("Render");
        this.placeDelay = this.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The amount of delay in ticks before placing.").defaultValue(2).min(0).sliderMax(10).build());
        this.placeMode = this.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The placement mode for crystals.").defaultValue(Mode.Safe).build());
        this.placeRange = this.sgPlace.add(new DoubleSetting.Builder().name("place-range").description("The radius in which crystals can be placed in.").defaultValue(4.5).min(0.0).sliderMax(7.0).build());
        this.placeWallsRange = this.sgPlace.add(new DoubleSetting.Builder().name("place-walls-range").description("The radius in which crystals can be placed through walls.").defaultValue(3.0).min(0.0).sliderMax(7.0).build());
        this.place = this.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Crystal Aura to place crystals.").defaultValue(true).build());
        this.multiPlace = this.sgPlace.add(new BoolSetting.Builder().name("multi-place").description("Allows Crystal Aura to place multiple crystals.").defaultValue(false).build());
        this.rayTrace = this.sgPlace.add(new BoolSetting.Builder().name("ray-trace").description("Whether or not to place through walls.").defaultValue(false).build());
        this.minDamage = this.sgPlace.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage the crystal will place.").defaultValue(5.5).build());
        this.minHealth = this.sgPlace.add(new DoubleSetting.Builder().name("min-health").description("The minimum health you have to be for it to place.").defaultValue(15.0).build());
        this.surroundBreak = this.sgPlace.add(new BoolSetting.Builder().name("surround-break").description("Places a crystal next to a surrounded player and keeps it there so they cannot use Surround again.").defaultValue(false).build());
        this.surroundHold = this.sgPlace.add(new BoolSetting.Builder().name("surround-hold").description("Places a crystal next to a player so they cannot use Surround.").defaultValue(false).build());
        this.oldPlace = this.sgPlace.add(new BoolSetting.Builder().name("1.12-place").description("Won't place in one block holes to help compatibility with some servers.").defaultValue(false).build());
        this.facePlace = this.sgPlace.add(new BoolSetting.Builder().name("face-place").description("Will face-place when target is below a certain health or armor durability threshold.").defaultValue(true).build());
        this.spamFacePlace = this.sgPlace.add(new BoolSetting.Builder().name("spam-face-place").description("Places faster when someone is below the face place health (Requires Smart Delay).").defaultValue(false).build());
        this.facePlaceHealth = this.sgPlace.add(new DoubleSetting.Builder().name("face-place-health").description("The health required to face-place.").defaultValue(8.0).min(1.0).max(36.0).build());
        this.facePlaceDurability = this.sgPlace.add(new DoubleSetting.Builder().name("face-place-durability").description("The durability threshold to be able to face-place.").defaultValue(2.0).min(1.0).max(100.0).sliderMax(100.0).build());
        this.support = this.sgPlace.add(new BoolSetting.Builder().name("support").description("Places a block in the air and crystals on it. Helps with killing players that are flying.").defaultValue(false).build());
        this.supportDelay = this.sgPlace.add(new IntSetting.Builder().name("support-delay").description("The delay between support blocks being placed.").defaultValue(5).min(0).sliderMax(10).build());
        this.supportBackup = this.sgPlace.add(new BoolSetting.Builder().name("support-backup").description("Makes it so support only works if there are no other options.").defaultValue(true).build());
        this.breakDelay = this.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The amount of delay in ticks before breaking.").defaultValue(1).min(0).sliderMax(10).build());
        this.breakMode = this.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The type of break mode for crystals.").defaultValue(Mode.Safe).build());
        this.breakRange = this.sgBreak.add(new DoubleSetting.Builder().name("break-range").description("The maximum range that crystals can be to be broken.").defaultValue(5.0).min(0.0).sliderMax(7.0).build());
        this.ignoreWalls = this.sgBreak.add(new BoolSetting.Builder().name("ray-trace").description("Whether or not to break through walls.").defaultValue(false).build());
        this.cancelCrystalMode = this.sgBreak.add(new EnumSetting.Builder().name("cancel-crystal").description("Mode to use for the crystals to be removed from the world.").defaultValue(CancelCrystalMode.Hit).build());
        this.entities = this.sgTarget.add(new EntityTypeListSetting.Builder().name("entities").description("The entities to attack.").defaultValue((Object2BooleanMap<class_1299<?>>)Utils.asObject2BooleanOpenHashMap(class_1299.field_6097)).onlyAttackable().build());
        this.targetRange = this.sgTarget.add(new DoubleSetting.Builder().name("target-range").description("The maximum range the entity can be to be targeted.").defaultValue(7.0).min(0.0).sliderMax(10.0).build());
        this.targetMode = this.sgTarget.add(new EnumSetting.Builder().name("target-mode").description("The way you target multiple targets.").defaultValue(TargetMode.HighestXDamages).build());
        this.numberOfDamages = this.sgTarget.add(new IntSetting.Builder().name("number-of-damages").description("The number to replace 'x' with in HighestXDamages.").defaultValue(3).min(2).sliderMax(10).build());
        this.multiTarget = this.sgTarget.add(new BoolSetting.Builder().name("multi-targeting").description("Will calculate damage for all entities and pick a block based on target mode.").defaultValue(false).build());
        this.pauseOnEat = this.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses Crystal Aura while eating.").defaultValue(false).build());
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
        this.crystalMap = new HashMap<class_1511, List<Double>>();
        this.crystalList = new ArrayList<Double>();
        this.removalQueue = new ArrayList<Integer>();
        this.bestBreak = null;
        this.renderBlockPool = new Pool<RenderBlock>(this::lambda$new$0);
        this.renderBlocks = new ArrayList<RenderBlock>();
        this.broken = false;
    }

    static Setting access$800(CrystalAura crystalAura) {
        return crystalAura.roundDamage;
    }

    private void attackCrystal(class_1511 class_15112, int n) {
        this.mc.field_1761.method_2918((class_1657)this.mc.field_1724, (class_1297)class_15112);
        this.removalQueue.add(class_15112.method_5628());
        if (this.swing.get().booleanValue()) {
            this.mc.field_1724.method_6104(this.getHand());
        } else {
            this.mc.field_1724.field_3944.method_2883((class_2596)new class_2879(this.getHand()));
        }
        this.mc.field_1724.field_7514.field_7545 = n;
        if (this.heldCrystal != null && class_15112.method_24515().equals((Object)this.heldCrystal.method_24515())) {
            this.heldCrystal = null;
            this.locked = false;
        }
    }

    private boolean lambda$getCrystalStream$6(class_1297 class_12972) {
        return this.ignoreWalls.get() == false || this.mc.field_1724.method_6057(class_12972);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean getDamagePlace(class_2338 class_23382) {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (this.placeMode.get() == Mode.Suicide) return true;
        class_243 class_2432 = new class_243((double)class_23382.method_10263() + 0.5, (double)class_23382.method_10264(), (double)class_23382.method_10260() + 0.5);
        if (!(DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, class_2432) <= this.maxDamage.get())) return false;
        class_243 class_2433 = new class_243((double)class_23382.method_10263() + 0.5, (double)class_23382.method_10264(), (double)class_23382.method_10260() + 0.5);
        if (!((double)this.getTotalHealth((class_1657)this.mc.field_1724) - DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, class_2433) >= this.minHealth.get())) return false;
        return true;
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
        if (this.mc.field_1724.method_6115() && (this.mc.field_1724.method_6047().method_7909().method_19263() || this.mc.field_1724.method_6079().method_7909().method_19263()) && this.pauseOnEat.get().booleanValue() || this.mc.field_1761.method_2923() && this.pauseOnMine.get().booleanValue() || this.mc.field_1724.method_6115() && (this.mc.field_1724.method_6047().method_7909() instanceof class_1812 || this.mc.field_1724.method_6079().method_7909() instanceof class_1812) && this.pauseOnDrink.get().booleanValue()) {
            return;
        }
        if (this.locked && this.heldCrystal != null && (!this.surroundBreak.get().booleanValue() && this.target.method_24515().method_10262(new class_2382(this.heldCrystal.method_23317(), this.heldCrystal.method_23318(), this.heldCrystal.method_23321())) == 4.0 || !this.surroundHold.get().booleanValue() && this.target.method_24515().method_10262(new class_2382(this.heldCrystal.method_23317(), this.heldCrystal.method_23318(), this.heldCrystal.method_23321())) == 2.0)) {
            this.heldCrystal = null;
            this.locked = false;
        }
        if (this.heldCrystal != null && (double)this.mc.field_1724.method_5739((class_1297)this.heldCrystal) > this.breakRange.get()) {
            this.heldCrystal = null;
            this.locked = false;
        }
        boolean bl = false;
        if (this.heldCrystal != null) {
            for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
                if (!(class_12972 instanceof class_1511) || this.heldCrystal == null || !class_12972.method_24515().equals((Object)this.heldCrystal.method_24515())) continue;
                bl = true;
                break;
            }
            if (!bl) {
                this.heldCrystal = null;
                this.locked = false;
            }
        }
        boolean bl2 = false;
        if ((double)this.getTotalHealth((class_1657)this.mc.field_1724) <= this.minHealth.get() && this.placeMode.get() != Mode.Suicide) {
            return;
        }
        if (this.target != null && this.heldCrystal != null && this.placeDelayLeft <= 0 && this.mc.field_1687.method_17742(new class_3959(this.target.method_19538(), this.heldCrystal.method_19538(), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.target)).method_17783() == class_239.class_240.field_1333) {
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
        if (this.switchMode.get() == SwitchMode.None && this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301) {
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
            if (this.surroundHold.get().booleanValue() && this.heldCrystal == null && ((n3 = InvUtils.findItemWithCount((class_1792)class_1802.field_8301).slot) != -1 && n3 < 9 || this.mc.field_1724.method_6079().method_7909() == class_1802.field_8301)) {
                this.bestBlock = this.findOpen(this.target);
                if (this.bestBlock != null) {
                    this.doHeldCrystal();
                    return;
                }
            }
            if (this.surroundBreak.get().booleanValue() && this.heldCrystal == null && this.isSurrounded(this.target) && ((n2 = InvUtils.findItemWithCount((class_1792)class_1802.field_8301).slot) != -1 && n2 < 9 || this.mc.field_1724.method_6079().method_7909() == class_1802.field_8301)) {
                this.bestBlock = this.findOpenSurround(this.target);
                if (this.bestBlock != null) {
                    this.doHeldCrystal();
                    return;
                }
            }
            if (((n = InvUtils.findItemWithCount((class_1792)class_1802.field_8301).slot) == -1 || n > 9) && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301) {
                return;
            }
            this.findValidBlocks(this.target);
            if (this.bestBlock == null) {
                this.findFacePlace(this.target);
            }
            if (this.bestBlock == null) {
                return;
            }
            if (this.facePlace.get().booleanValue() && Math.sqrt(this.target.method_5707(this.bestBlock)) <= 2.0) {
                if ((double)(this.target.method_6032() + this.target.method_6067()) < this.facePlaceHealth.get()) {
                    bl2 = true;
                } else {
                    Iterable iterable = this.target.method_5661();
                    for (class_1799 class_17992 : iterable) {
                        if (class_17992 == null || class_17992.method_7960() || !((double)(class_17992.method_7936() - class_17992.method_7919()) / (double)class_17992.method_7936() * 100.0 <= this.facePlaceDurability.get())) continue;
                        bl2 = true;
                    }
                }
            }
            if (this.bestBlock != null && (this.bestDamage >= this.minDamage.get() && !this.locked || bl2)) {
                if (this.switchMode.get() != SwitchMode.None) {
                    this.doSwitch();
                }
                if (this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301) {
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
            if (this.switchMode.get() == SwitchMode.Spoof && this.preSlot != this.mc.field_1724.field_7514.field_7545 && this.preSlot != -1) {
                this.mc.field_1724.field_7514.field_7545 = this.preSlot;
            }
        }
    }

    private static boolean lambda$getCrystalStream$3(class_1297 class_12972) {
        return class_12972 instanceof class_1511;
    }

    static Setting access$700(CrystalAura crystalAura) {
        return crystalAura.damageScale;
    }

    private void lambda$placeBlock$19(class_1268 class_12682, class_2350 class_23502, class_243 class_2432) {
        this.mc.field_1724.field_3944.method_2883((class_2596)new class_2885(class_12682, new class_3965(this.mc.field_1724.method_19538(), class_23502, new class_2338(class_2432), false)));
        if (this.swing.get().booleanValue()) {
            this.mc.field_1724.method_6104(class_12682);
        } else {
            this.mc.field_1724.field_3944.method_2883((class_2596)new class_2879(class_12682));
        }
    }

    private void multiBreak() {
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        this.crystalMap.clear();
        this.crystalList.clear();
        this.getCrystalStream().forEach(this::lambda$multiBreak$10);
        class_1511 class_15112 = this.findBestCrystal(this.crystalMap);
        if (class_15112 != null) {
            this.hitCrystal(class_15112);
        }
    }

    private double lambda$findTarget$17(class_1297 class_12972) {
        return class_12972.method_5739((class_1297)this.mc.field_1724);
    }

    private void findValidBlocks(class_1309 class_13092) {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        this.bestBlock = new class_243(0.0, 0.0, 0.0);
        this.bestDamage = 0.0;
        class_243 class_2432 = new class_243(0.0, 0.0, 0.0);
        double d = 0.0;
        class_2338 class_23382 = this.mc.field_1724.method_24515();
        this.canSupport = false;
        this.crystalMap.clear();
        this.crystalList.clear();
        if (this.support.get().booleanValue()) {
            for (int i = 0; i < 9; ++i) {
                if (this.mc.field_1724.field_7514.method_5438(i).method_7909() != class_1802.field_8281) continue;
                this.canSupport = true;
                this.supportSlot = i;
                break;
            }
        }
        for (double d2 = (double)class_23382.method_10263() - this.placeRange.get(); d2 < (double)class_23382.method_10263() + this.placeRange.get(); d2 += 1.0) {
            for (double d3 = (double)class_23382.method_10260() - this.placeRange.get(); d3 < (double)class_23382.method_10260() + this.placeRange.get(); d3 += 1.0) {
                for (double d4 = (double)class_23382.method_10264() - this.verticalRange.get(); d4 < (double)class_23382.method_10264() + this.verticalRange.get(); d4 += 1.0) {
                    class_243 class_2433 = new class_243(Math.floor(d2), Math.floor(d4), Math.floor(d3));
                    if (!this.isValid(new class_2338(class_2433)) || !this.getDamagePlace(new class_2338(class_2433).method_10084()) || this.oldPlace.get().booleanValue() && !this.isEmpty(new class_2338(class_2433.method_1031(0.0, 2.0, 0.0))) || this.rayTrace.get().booleanValue() && !(class_2433.method_1022(new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + (double)this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), this.mc.field_1724.method_23321())) <= this.placeWallsRange.get()) && this.rayTraceCheck(new class_2338(class_2433), false) == null) continue;
                    if (!this.multiTarget.get().booleanValue()) {
                        if (this.isEmpty(new class_2338(class_2433)) && d < DamageCalcUtils.crystalDamage(class_13092, class_2433.method_1031(0.5, 1.0, 0.5))) {
                            class_2432 = class_2433;
                            d = DamageCalcUtils.crystalDamage(class_13092, class_2433.method_1031(0.5, 1.0, 0.5));
                            continue;
                        }
                        if (this.isEmpty(new class_2338(class_2433)) || !(this.bestDamage < DamageCalcUtils.crystalDamage(class_13092, class_2433.method_1031(0.5, 1.0, 0.5)))) continue;
                        this.bestBlock = class_2433;
                        this.bestDamage = DamageCalcUtils.crystalDamage(class_13092, this.bestBlock.method_1031(0.5, 1.0, 0.5));
                        continue;
                    }
                    for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
                        if (class_12972 == this.mc.field_1724 || !this.entities.get().getBoolean((Object)class_12972.method_5864()) || !((double)this.mc.field_1724.method_5739(class_12972) <= this.targetRange.get()) || !class_12972.method_5805() || !(class_12972 instanceof class_1309) || class_12972 instanceof class_1657 && !Friends.get().attack((class_1657)class_12972)) continue;
                        this.crystalList.add(DamageCalcUtils.crystalDamage((class_1309)class_12972, class_2433.method_1031(0.5, 1.0, 0.5)));
                    }
                    if (this.crystalList.isEmpty()) continue;
                    this.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
                    this.crystalMap.put(new class_1511((class_1937)this.mc.field_1687, class_2433.field_1352, class_2433.field_1351, class_2433.field_1350), new ArrayList<Double>(this.crystalList));
                    this.crystalList.clear();
                }
            }
        }
        if (this.multiTarget.get().booleanValue()) {
            class_1511 class_15112 = this.findBestCrystal(this.crystalMap);
            this.bestBlock = class_15112 != null && this.bestDamage > this.minDamage.get() ? class_15112.method_19538() : null;
        } else if (this.bestDamage < this.minDamage.get()) {
            this.bestBlock = null;
        }
        if (this.support.get().booleanValue() && (this.bestBlock == null || this.bestDamage < d && !this.supportBackup.get().booleanValue())) {
            this.bestBlock = class_2432;
        }
    }

    static Setting access$900(CrystalAura crystalAura) {
        return crystalAura.damageColor;
    }

    private void lambda$onPlaySound$2(Integer n) {
        this.mc.field_1687.method_2945(n.intValue());
    }

    private void singleBreak() {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        this.getCrystalStream().max(Comparator.comparingDouble(this::lambda$singleBreak$8)).ifPresent(this::lambda$singleBreak$9);
    }

    private boolean lambda$getCrystalStream$7(class_1297 class_12972) {
        return this.isSafe(class_12972.method_19538());
    }

    public class_1268 getHand() {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        class_1268 class_12682 = class_1268.field_5808;
        if (this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() == class_1802.field_8301) {
            class_12682 = class_1268.field_5810;
        }
        return class_12682;
    }

    private RenderBlock lambda$new$0() {
        return new RenderBlock(this, null);
    }

    @Override
    public void onDeactivate() {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        if (this.switchBack.get().booleanValue() && this.preSlot != -1) {
            this.mc.field_1724.field_7514.field_7545 = this.preSlot;
        }
        for (RenderBlock renderBlock : this.renderBlocks) {
            this.renderBlockPool.free(renderBlock);
        }
        this.renderBlocks.clear();
        if (this.target != null && this.resetRotations.get().booleanValue() && (this.rotationMode.get() == RotationMode.Both || this.rotationMode.get() == RotationMode.Place || this.rotationMode.get() == RotationMode.Break)) {
            RotationUtils.packetRotate(this.mc.field_1724.field_6031, this.mc.field_1724.field_5965);
        }
    }

    private boolean lambda$findTarget$15(class_1297 class_12972) {
        return this.entities.get().getBoolean((Object)class_12972.method_5864());
    }

    @EventHandler
    private void onEntityRemoved(EntityRemovedEvent entityRemovedEvent) {
        if (this.heldCrystal == null) {
            return;
        }
        if (entityRemovedEvent.entity.method_24515().equals((Object)this.heldCrystal.method_24515())) {
            this.heldCrystal = null;
            this.locked = false;
        }
    }

    private boolean isSafe(class_243 class_2432) {
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        return this.breakMode.get() != Mode.Safe || (double)this.getTotalHealth((class_1657)this.mc.field_1724) - DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, class_2432) > this.minHealth.get() && DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, class_2432) < this.maxDamage.get();
    }

    static Setting access$300(CrystalAura crystalAura) {
        return crystalAura.lineColor;
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean validSurroundBreak(class_1309 class_13092, int n, int n2) {
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.field_1724 == null) {
            throw new AssertionError();
        }
        class_243 class_2432 = new class_243((double)class_13092.method_24515().method_10263() + 0.5, (double)class_13092.method_24515().method_10264(), (double)class_13092.method_24515().method_10260() + 0.5);
        if (!this.isValid(class_13092.method_24515().method_10069(n, -1, n2))) return false;
        if (this.mc.field_1687.method_8320(class_13092.method_24515().method_10069(n / 2, 0, n2 / 2)).method_26204() == class_2246.field_9987) return false;
        if (!this.isSafe(class_2432.method_1031((double)n, 0.0, (double)n2))) return false;
        class_2382 class_23822 = new class_2382(class_13092.method_24515().method_10263() + n, class_13092.method_24515().method_10264() - 1, class_13092.method_24515().method_10260() + n2);
        if (!(Math.sqrt(this.mc.field_1724.method_24515().method_10262(class_23822)) < this.placeRange.get())) return false;
        if (this.mc.field_1687.method_17742(new class_3959(class_13092.method_19538(), class_13092.method_19538().method_1031((double)n, 0.0, (double)n2), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)class_13092)).method_17783() == class_239.class_240.field_1333) return false;
        return true;
    }

    private void lambda$singleBreak$9(class_1297 class_12972) {
        this.hitCrystal((class_1511)class_12972);
    }

    private void lambda$hitCrystal$11(class_1511 class_15112, int n) {
        this.attackCrystal(class_15112, n);
    }

    @EventHandler(priority=100)
    private void onTick(TickEvent.Post post) {
        if (this.cancelCrystalMode.get() == CancelCrystalMode.Hit) {
            this.removalQueue.forEach(this::lambda$onTick$1);
            this.removalQueue.clear();
        }
    }

    private static boolean lambda$findTarget$14(class_1297 class_12972) {
        return class_12972 instanceof class_1309;
    }

    private class_2350 rayTraceCheck(class_2338 class_23382, boolean bl) {
        class_243 class_2432 = new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + (double)this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), this.mc.field_1724.method_23321());
        for (class_2350 class_23502 : class_2350.values()) {
            class_3959 class_39592 = new class_3959(class_2432, new class_243((double)class_23382.method_10263() + 0.5 + (double)class_23502.method_10163().method_10263() * 0.5, (double)class_23382.method_10264() + 0.5 + (double)class_23502.method_10163().method_10264() * 0.5, (double)class_23382.method_10260() + 0.5 + (double)class_23502.method_10163().method_10260() * 0.5), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
            class_3965 class_39652 = this.mc.field_1687.method_17742(class_39592);
            if (class_39652 == null || class_39652.method_17783() != class_239.class_240.field_1332 || !class_39652.method_17777().equals((Object)class_23382)) continue;
            return class_23502;
        }
        if (bl) {
            if ((double)class_23382.method_10264() > class_2432.field_1351) {
                return class_2350.field_11033;
            }
            return class_2350.field_11036;
        }
        return null;
    }

    private void findTarget() {
        if (!$assertionsDisabled && this.mc.field_1687 == null) {
            throw new AssertionError();
        }
        Optional<class_1309> optional = Streams.stream((Iterable)this.mc.field_1687.method_18112()).filter(class_1297::method_5805).filter(this::lambda$findTarget$12).filter(CrystalAura::lambda$findTarget$13).filter(CrystalAura::lambda$findTarget$14).filter(this::lambda$findTarget$15).filter(this::lambda$findTarget$16).min(Comparator.comparingDouble(this::lambda$findTarget$17)).map(CrystalAura::lambda$findTarget$18);
        if (!optional.isPresent()) {
            this.target = null;
            return;
        }
        this.target = optional.get();
    }

    public static final class TargetMode
    extends Enum<TargetMode> {
        private static final TargetMode[] $VALUES;
        public static final /* enum */ TargetMode MostDamage;
        public static final /* enum */ TargetMode HighestXDamages;

        public static TargetMode[] values() {
            return (TargetMode[])$VALUES.clone();
        }

        static {
            MostDamage = new TargetMode();
            HighestXDamages = new TargetMode();
            $VALUES = TargetMode.$values();
        }

        private static TargetMode[] $values() {
            return new TargetMode[]{MostDamage, HighestXDamages};
        }

        public static TargetMode valueOf(String string) {
            return Enum.valueOf(TargetMode.class, string);
        }
    }

    public static final class RotationMode
    extends Enum<RotationMode> {
        public static final /* enum */ RotationMode Place = new RotationMode();
        private static final RotationMode[] $VALUES;
        public static final /* enum */ RotationMode Break;
        public static final /* enum */ RotationMode None;
        public static final /* enum */ RotationMode Both;

        public static RotationMode valueOf(String string) {
            return Enum.valueOf(RotationMode.class, string);
        }

        static {
            Break = new RotationMode();
            Both = new RotationMode();
            None = new RotationMode();
            $VALUES = RotationMode.$values();
        }

        public static RotationMode[] values() {
            return (RotationMode[])$VALUES.clone();
        }

        private static RotationMode[] $values() {
            return new RotationMode[]{Place, Break, Both, None};
        }
    }

    private class RenderBlock {
        private int z;
        private int y;
        private double damage;
        private int x;
        final CrystalAura this$0;
        private int timer;

        public void render3D() {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.x, this.y, this.z, 1.0, (Color)CrystalAura.access$200(this.this$0).get(), (Color)CrystalAura.access$300(this.this$0).get(), (ShapeMode)((Object)CrystalAura.access$400(this.this$0).get()), 0);
        }

        private RenderBlock(CrystalAura crystalAura) {
            this.this$0 = crystalAura;
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

        RenderBlock(CrystalAura crystalAura, 1 var2_2) {
            this(crystalAura);
        }

        public void reset(class_243 class_2432) {
            this.x = class_3532.method_15357((double)class_2432.method_10216());
            this.y = class_3532.method_15357((double)class_2432.method_10214());
            this.z = class_3532.method_15357((double)class_2432.method_10215());
            this.timer = (Integer)CrystalAura.access$100(this.this$0).get();
        }

        public void render2D() {
            if (((Boolean)CrystalAura.access$500(this.this$0).get()).booleanValue()) {
                CrystalAura.access$600().set((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5);
                if (NametagUtils.to2D(CrystalAura.access$600(), (Double)CrystalAura.access$700(this.this$0).get())) {
                    NametagUtils.begin(CrystalAura.access$600());
                    TextRenderer.get().begin(1.0, false, true);
                    String string = String.valueOf(Math.round(this.damage));
                    switch ((Integer)CrystalAura.access$800(this.this$0).get()) {
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
                    TextRenderer.get().render(string, -d, 0.0, (Color)CrystalAura.access$900(this.this$0).get());
                    TextRenderer.get().end();
                    NametagUtils.end();
                }
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Suicide;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Safe;

        private static Mode[] $values() {
            return new Mode[]{Safe, Suicide};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            Safe = new Mode();
            Suicide = new Mode();
            $VALUES = Mode.$values();
        }
    }

    public static final class SwitchMode
    extends Enum<SwitchMode> {
        private static final SwitchMode[] $VALUES;
        public static final /* enum */ SwitchMode None;
        public static final /* enum */ SwitchMode Auto;
        public static final /* enum */ SwitchMode Spoof;

        static {
            Auto = new SwitchMode();
            Spoof = new SwitchMode();
            None = new SwitchMode();
            $VALUES = SwitchMode.$values();
        }

        private static SwitchMode[] $values() {
            return new SwitchMode[]{Auto, Spoof, None};
        }

        public static SwitchMode[] values() {
            return (SwitchMode[])$VALUES.clone();
        }

        public static SwitchMode valueOf(String string) {
            return Enum.valueOf(SwitchMode.class, string);
        }
    }

    public static final class CancelCrystalMode
    extends Enum<CancelCrystalMode> {
        private static final CancelCrystalMode[] $VALUES;
        public static final /* enum */ CancelCrystalMode Sound;
        public static final /* enum */ CancelCrystalMode Hit;

        static {
            Sound = new CancelCrystalMode();
            Hit = new CancelCrystalMode();
            $VALUES = CancelCrystalMode.$values();
        }

        private static CancelCrystalMode[] $values() {
            return new CancelCrystalMode[]{Sound, Hit};
        }

        public static CancelCrystalMode[] values() {
            return (CancelCrystalMode[])$VALUES.clone();
        }

        public static CancelCrystalMode valueOf(String string) {
            return Enum.valueOf(CancelCrystalMode.class, string);
        }
    }
}

