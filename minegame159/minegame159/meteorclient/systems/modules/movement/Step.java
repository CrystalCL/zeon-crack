/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import baritone.api.BaritoneAPI;
import com.google.common.collect.Streams;
import java.util.Comparator;
import java.util.Optional;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;

public class Step
extends Module {
    static final boolean $assertionsDisabled = !Step.class.desiredAssertionStatus();
    private final Setting<Boolean> safeStep;
    private final SettingGroup sgGeneral;
    private final Setting<ActiveWhen> activeWhen;
    private float prevStepHeight;
    public final Setting<Double> height;
    private final Setting<Integer> stepHealth;
    private boolean prevBaritoneAssumeStep;

    private double lambda$getExplosionDamage$1(Entity Entity2) {
        return DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Entity2.getPos());
    }

    private float getHealth() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        return this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
    }

    private static boolean lambda$getExplosionDamage$0(Entity Entity2) {
        return Entity2 instanceof EndCrystalEntity;
    }

    private Double lambda$getExplosionDamage$3(EndCrystalEntity EndCrystalEntity2) {
        return DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, EndCrystalEntity2.getPos());
    }

    public Step() {
        super(Categories.Movement, "step", "Allows you to walk up full blocks instantly.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.height = this.sgGeneral.add(new DoubleSetting.Builder().name("height").description("Step height.").defaultValue(1.0).min(0.0).build());
        this.activeWhen = this.sgGeneral.add(new EnumSetting.Builder().name("active-when").description("Step is active when you meet these requirements.").defaultValue(ActiveWhen.Always).build());
        this.safeStep = this.sgGeneral.add(new BoolSetting.Builder().name("safe-step").description("Doesn't let you step out of a hole if you are low on health or there is a crystal nearby.").defaultValue(false).build());
        this.stepHealth = this.sgGeneral.add(new IntSetting.Builder().name("step-health").description("The health you stop being able to step at.").defaultValue(5).min(1).max(36).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        boolean bl = this.activeWhen.get() == ActiveWhen.Always || this.activeWhen.get() == ActiveWhen.Sneaking && this.mc.player.isSneaking() || this.activeWhen.get() == ActiveWhen.NotSneaking && !this.mc.player.isSneaking();
        this.mc.player.setBoundingBox(this.mc.player.getBoundingBox().offset(0.0, 1.0, 0.0));
        this.mc.player.stepHeight = bl && (this.safeStep.get() == false || this.getHealth() > (float)this.stepHealth.get().intValue() && (double)this.getHealth() - this.getExplosionDamage() > (double)this.stepHealth.get().intValue()) ? this.height.get().floatValue() : this.prevStepHeight;
        this.mc.player.setBoundingBox(this.mc.player.getBoundingBox().offset(0.0, -1.0, 0.0));
    }

    @Override
    public void onActivate() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        this.prevStepHeight = this.mc.player.stepHeight;
        this.prevBaritoneAssumeStep = (Boolean)BaritoneAPI.getSettings().assumeStep.value;
        BaritoneAPI.getSettings().assumeStep.value = true;
    }

    private double getExplosionDamage() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        Optional<EndCrystalEntity> optional = Streams.stream((Iterable)this.mc.world.getEntities()).filter(Step::lambda$getExplosionDamage$0).filter(Entity::isAlive).max(Comparator.comparingDouble(this::lambda$getExplosionDamage$1)).map(Step::lambda$getExplosionDamage$2);
        return optional.map(this::lambda$getExplosionDamage$3).orElse(0.0);
    }

    @Override
    public void onDeactivate() {
        if (this.mc.player != null) {
            this.mc.player.stepHeight = this.prevStepHeight;
        }
        BaritoneAPI.getSettings().assumeStep.value = this.prevBaritoneAssumeStep;
    }

    private static EndCrystalEntity lambda$getExplosionDamage$2(Entity Entity2) {
        return (EndCrystalEntity)Entity2;
    }

    public static final class ActiveWhen
    extends Enum<ActiveWhen> {
        public static final /* enum */ ActiveWhen NotSneaking;
        public static final /* enum */ ActiveWhen Sneaking;
        public static final /* enum */ ActiveWhen Always;
        private static final ActiveWhen[] $VALUES;

        public static ActiveWhen[] values() {
            return (ActiveWhen[])$VALUES.clone();
        }

        static {
            Always = new ActiveWhen();
            Sneaking = new ActiveWhen();
            NotSneaking = new ActiveWhen();
            $VALUES = ActiveWhen.$values();
        }

        private static ActiveWhen[] $values() {
            return new ActiveWhen[]{Always, Sneaking, NotSneaking};
        }

        public static ActiveWhen valueOf(String string) {
            return Enum.valueOf(ActiveWhen.class, string);
        }
    }
}

