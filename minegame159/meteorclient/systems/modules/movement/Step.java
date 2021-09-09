/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  com.google.common.collect.Streams
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.decoration.EndCrystalEntity
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
    private final /* synthetic */ Setting<ActiveWhen> activeWhen;
    private final /* synthetic */ Setting<Integer> stepHealth;
    public final /* synthetic */ Setting<Double> height;
    private /* synthetic */ boolean prevBaritoneAssumeStep;
    private final /* synthetic */ Setting<Boolean> safeStep;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ float prevStepHeight;

    private double getExplosionDamage() {
        Step lllllllllllllllllIlIllIllIIIIIIl;
        assert (lllllllllllllllllIlIllIllIIIIIIl.mc.player != null);
        assert (lllllllllllllllllIlIllIllIIIIIIl.mc.world != null);
        Optional<EndCrystalEntity> lllllllllllllllllIlIllIllIIIIIlI = Streams.stream((Iterable)lllllllllllllllllIlIllIllIIIIIIl.mc.world.getEntities()).filter(lllllllllllllllllIlIllIlIllIlllI -> lllllllllllllllllIlIllIlIllIlllI instanceof EndCrystalEntity).filter(Entity::isAlive).max(Comparator.comparingDouble(lllllllllllllllllIlIllIlIlllIIll -> {
            Step lllllllllllllllllIlIllIlIlllIIlI;
            return DamageCalcUtils.crystalDamage((LivingEntity)lllllllllllllllllIlIllIlIlllIIlI.mc.player, lllllllllllllllllIlIllIlIlllIIll.getPos());
        })).map(lllllllllllllllllIlIllIlIlllIlll -> (EndCrystalEntity)lllllllllllllllllIlIllIlIlllIlll);
        return lllllllllllllllllIlIllIllIIIIIlI.map(lllllllllllllllllIlIllIlIlllllII -> {
            Step lllllllllllllllllIlIllIlIllllIll;
            return DamageCalcUtils.crystalDamage((LivingEntity)lllllllllllllllllIlIllIlIllllIll.mc.player, lllllllllllllllllIlIllIlIlllllII.getPos());
        }).orElse(0.0);
    }

    private float getHealth() {
        Step lllllllllllllllllIlIllIllIIIIllI;
        assert (lllllllllllllllllIlIllIllIIIIllI.mc.player != null);
        return lllllllllllllllllIlIllIllIIIIllI.mc.player.getHealth() + lllllllllllllllllIlIllIllIIIIllI.mc.player.getAbsorptionAmount();
    }

    @Override
    public void onActivate() {
        Step lllllllllllllllllIlIllIllIIlIlII;
        assert (lllllllllllllllllIlIllIllIIlIlII.mc.player != null);
        lllllllllllllllllIlIllIllIIlIlII.prevStepHeight = lllllllllllllllllIlIllIllIIlIlII.mc.player.stepHeight;
        lllllllllllllllllIlIllIllIIlIlII.prevBaritoneAssumeStep = (Boolean)BaritoneAPI.getSettings().assumeStep.value;
        BaritoneAPI.getSettings().assumeStep.value = true;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIlIllIllIIIllll) {
        Step lllllllllllllllllIlIllIllIIIllIl;
        boolean lllllllllllllllllIlIllIllIIIlllI = lllllllllllllllllIlIllIllIIIllIl.activeWhen.get() == ActiveWhen.Always || lllllllllllllllllIlIllIllIIIllIl.activeWhen.get() == ActiveWhen.Sneaking && lllllllllllllllllIlIllIllIIIllIl.mc.player.isSneaking() || lllllllllllllllllIlIllIllIIIllIl.activeWhen.get() == ActiveWhen.NotSneaking && !lllllllllllllllllIlIllIllIIIllIl.mc.player.isSneaking();
        lllllllllllllllllIlIllIllIIIllIl.mc.player.setBoundingBox(lllllllllllllllllIlIllIllIIIllIl.mc.player.getBoundingBox().offset(0.0, 1.0, 0.0));
        lllllllllllllllllIlIllIllIIIllIl.mc.player.stepHeight = lllllllllllllllllIlIllIllIIIlllI && (lllllllllllllllllIlIllIllIIIllIl.safeStep.get() == false || lllllllllllllllllIlIllIllIIIllIl.getHealth() > (float)lllllllllllllllllIlIllIllIIIllIl.stepHealth.get().intValue() && (double)lllllllllllllllllIlIllIllIIIllIl.getHealth() - lllllllllllllllllIlIllIllIIIllIl.getExplosionDamage() > (double)lllllllllllllllllIlIllIllIIIllIl.stepHealth.get().intValue()) ? lllllllllllllllllIlIllIllIIIllIl.height.get().floatValue() : lllllllllllllllllIlIllIllIIIllIl.prevStepHeight;
        lllllllllllllllllIlIllIllIIIllIl.mc.player.setBoundingBox(lllllllllllllllllIlIllIllIIIllIl.mc.player.getBoundingBox().offset(0.0, -1.0, 0.0));
    }

    @Override
    public void onDeactivate() {
        Step lllllllllllllllllIlIllIllIIIlIlI;
        if (lllllllllllllllllIlIllIllIIIlIlI.mc.player != null) {
            lllllllllllllllllIlIllIllIIIlIlI.mc.player.stepHeight = lllllllllllllllllIlIllIllIIIlIlI.prevStepHeight;
        }
        BaritoneAPI.getSettings().assumeStep.value = lllllllllllllllllIlIllIllIIIlIlI.prevBaritoneAssumeStep;
    }

    public Step() {
        super(Categories.Movement, "step", "Allows you to walk up full blocks instantly.");
        Step lllllllllllllllllIlIllIllIIlIllI;
        lllllllllllllllllIlIllIllIIlIllI.sgGeneral = lllllllllllllllllIlIllIllIIlIllI.settings.getDefaultGroup();
        lllllllllllllllllIlIllIllIIlIllI.height = lllllllllllllllllIlIllIllIIlIllI.sgGeneral.add(new DoubleSetting.Builder().name("height").description("Step height.").defaultValue(1.0).min(0.0).build());
        lllllllllllllllllIlIllIllIIlIllI.activeWhen = lllllllllllllllllIlIllIllIIlIllI.sgGeneral.add(new EnumSetting.Builder().name("active-when").description("Step is active when you meet these requirements.").defaultValue(ActiveWhen.Always).build());
        lllllllllllllllllIlIllIllIIlIllI.safeStep = lllllllllllllllllIlIllIllIIlIllI.sgGeneral.add(new BoolSetting.Builder().name("safe-step").description("Doesn't let you step out of a hole if you are low on health or there is a crystal nearby.").defaultValue(false).build());
        lllllllllllllllllIlIllIllIIlIllI.stepHealth = lllllllllllllllllIlIllIllIIlIllI.sgGeneral.add(new IntSetting.Builder().name("step-health").description("The health you stop being able to step at.").defaultValue(5).min(1).max(36).build());
    }

    public static final class ActiveWhen
    extends Enum<ActiveWhen> {
        private static final /* synthetic */ ActiveWhen[] $VALUES;
        public static final /* synthetic */ /* enum */ ActiveWhen NotSneaking;
        public static final /* synthetic */ /* enum */ ActiveWhen Always;
        public static final /* synthetic */ /* enum */ ActiveWhen Sneaking;

        private static /* synthetic */ ActiveWhen[] $values() {
            return new ActiveWhen[]{Always, Sneaking, NotSneaking};
        }

        public static ActiveWhen valueOf(String llllllllllllllllllllIlIlIIIlIlIl) {
            return Enum.valueOf(ActiveWhen.class, llllllllllllllllllllIlIlIIIlIlIl);
        }

        private ActiveWhen() {
            ActiveWhen llllllllllllllllllllIlIlIIIlIIII;
        }

        public static ActiveWhen[] values() {
            return (ActiveWhen[])$VALUES.clone();
        }

        static {
            Always = new ActiveWhen();
            Sneaking = new ActiveWhen();
            NotSneaking = new ActiveWhen();
            $VALUES = ActiveWhen.$values();
        }
    }
}

