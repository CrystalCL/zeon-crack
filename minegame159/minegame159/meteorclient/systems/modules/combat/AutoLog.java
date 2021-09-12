/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.NoFall;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.world.Dimension;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1511;
import net.minecraft.class_1657;
import net.minecraft.class_1829;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2585;
import net.minecraft.class_2587;
import net.minecraft.class_2661;
import net.minecraft.class_310;

public class AutoLog
extends Module {
    private final Setting<Boolean> crystalLog;
    private final Setting<Boolean> instantDeath;
    private final StaticListener staticListener;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> smartToggle;
    private final Setting<Boolean> toggleOff;
    private final Setting<Integer> range;
    private final Setting<Boolean> smart;
    private final Setting<Boolean> onlyTrusted;
    private final Setting<Integer> health;

    private double getHealthReduction() {
        double d;
        double d2 = 0.0;
        for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
            if (class_12972 instanceof class_1511 && d2 < DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, class_12972.method_19538())) {
                d2 = DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, class_12972.method_19538());
                continue;
            }
            if (!(class_12972 instanceof class_1657) || !(d2 < DamageCalcUtils.getSwordDamage((class_1657)class_12972, true)) || !Friends.get().notTrusted((class_1657)class_12972) || !(this.mc.field_1724.method_19538().method_1022(class_12972.method_19538()) < 5.0) || !(((class_1657)class_12972).method_6030().method_7909() instanceof class_1829)) continue;
            d2 = DamageCalcUtils.getSwordDamage((class_1657)class_12972, true);
        }
        if (!Modules.get().isActive(NoFall.class) && this.mc.field_1724.field_6017 > 3.0f && (d = (double)this.mc.field_1724.field_6017 * 0.5) > d2 && !EntityUtils.isAboveWater((class_1297)this.mc.field_1724)) {
            d2 = d;
        }
        if (Utils.getDimension() != Dimension.Overworld) {
            for (class_1297 class_12972 : this.mc.field_1687.field_9231) {
                class_2338 class_23382 = class_12972.method_11016();
                class_243 class_2432 = new class_243((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260());
                if (!(class_12972 instanceof class_2587) || !(d2 < DamageCalcUtils.bedDamage((class_1309)this.mc.field_1724, class_2432))) continue;
                d2 = DamageCalcUtils.bedDamage((class_1309)this.mc.field_1724, class_2432);
            }
        }
        return d2;
    }

    public AutoLog() {
        super(Categories.Combat, "auto-log", "Automatically disconnects you when certain requirements are met.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.health = this.sgGeneral.add(new IntSetting.Builder().name("health").description("Automatically disconnects when health is lower or equal to this value.").defaultValue(6).min(0).max(20).sliderMax(20).build());
        this.smart = this.sgGeneral.add(new BoolSetting.Builder().name("smart").description("Disconnects when you're about to take enough damage to kill you.").defaultValue(true).build());
        this.onlyTrusted = this.sgGeneral.add(new BoolSetting.Builder().name("only-trusted").description("Disconnects when a player not on your friends list appears in render distance.").defaultValue(false).build());
        this.instantDeath = this.sgGeneral.add(new BoolSetting.Builder().name("32K").description("Disconnects when a player near you can instantly kill you.").defaultValue(false).build());
        this.crystalLog = this.sgGeneral.add(new BoolSetting.Builder().name("crystal-nearby").description("Disconnects when a crystal appears near you.").defaultValue(false).build());
        this.range = this.sgGeneral.add(new IntSetting.Builder().name("range").description("How close a crystal has to be to you before you disconnect.").defaultValue(4).min(1).max(10).sliderMax(5).build());
        this.smartToggle = this.sgGeneral.add(new BoolSetting.Builder().name("smart-toggle").description("Disables Auto Log after a low-health logout. WILL re-enable once you heal.").defaultValue(false).build());
        this.toggleOff = this.sgGeneral.add(new BoolSetting.Builder().name("toggle-off").description("Disables Auto Log after usage.").defaultValue(true).build());
        this.staticListener = new StaticListener(this, null);
    }

    static Setting access$300(AutoLog autoLog) {
        return autoLog.health;
    }

    private void enableHealthListener() {
        MeteorClient.EVENT_BUS.subscribe(this.staticListener);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.field_1724.method_6032() <= 0.0f) {
            this.toggle();
            return;
        }
        if (this.mc.field_1724.method_6032() <= (float)this.health.get().intValue()) {
            this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585(String.valueOf(new StringBuilder().append("[AutoLog] Health was lower than ").append(this.health.get()).append(".")))));
            if (this.smartToggle.get().booleanValue()) {
                this.toggle();
                this.enableHealthListener();
            }
        }
        if (this.smart.get().booleanValue() && (double)(this.mc.field_1724.method_6032() + this.mc.field_1724.method_6067()) - this.getHealthReduction() < (double)this.health.get().intValue()) {
            this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585(String.valueOf(new StringBuilder().append("[AutoLog] Health was going to be lower than ").append(this.health.get()).append(".")))));
            if (this.toggleOff.get().booleanValue()) {
                this.toggle();
            }
        }
        for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
            if (class_12972 instanceof class_1657 && class_12972.method_5667() != this.mc.field_1724.method_5667()) {
                if (this.onlyTrusted.get().booleanValue() && class_12972 != this.mc.field_1724 && Friends.get().notTrusted((class_1657)class_12972)) {
                    this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585("[AutoLog] A non-trusted player appeared in your render distance.")));
                    if (!this.toggleOff.get().booleanValue()) break;
                    this.toggle();
                    break;
                }
                if (this.mc.field_1724.method_5739(class_12972) < 8.0f && this.instantDeath.get().booleanValue() && DamageCalcUtils.getSwordDamage((class_1657)class_12972, true) > (double)(this.mc.field_1724.method_6032() + this.mc.field_1724.method_6067())) {
                    this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585("[AutoLog] Anti-32k measures.")));
                    if (!this.toggleOff.get().booleanValue()) break;
                    this.toggle();
                    break;
                }
            }
            if (!(class_12972 instanceof class_1511) || !(this.mc.field_1724.method_5739(class_12972) < (float)this.range.get().intValue()) || !this.crystalLog.get().booleanValue()) continue;
            this.mc.field_1724.field_3944.method_11083(new class_2661((class_2561)new class_2585("[AutoLog] End Crystal appeared within specified range.")));
            if (!this.toggleOff.get().booleanValue()) continue;
            this.toggle();
        }
    }

    private void disableHealthListener() {
        MeteorClient.EVENT_BUS.unsubscribe(this.staticListener);
    }

    static void access$000(AutoLog autoLog) {
        autoLog.disableHealthListener();
    }

    static class_310 access$100(AutoLog autoLog) {
        return autoLog.mc;
    }

    static class_310 access$200(AutoLog autoLog) {
        return autoLog.mc;
    }

    private class StaticListener {
        final AutoLog this$0;

        private StaticListener(AutoLog autoLog) {
            this.this$0 = autoLog;
        }

        @EventHandler
        private void healthListener(TickEvent.Post post) {
            if (this.this$0.isActive()) {
                AutoLog.access$000(this.this$0);
            } else if (Utils.canUpdate() && !AutoLog.access$100((AutoLog)this.this$0).field_1724.method_29504() && AutoLog.access$200((AutoLog)this.this$0).field_1724.method_6032() >= (float)((Integer)AutoLog.access$300(this.this$0).get()).intValue()) {
                this.this$0.toggle();
                AutoLog.access$000(this.this$0);
            }
        }

        StaticListener(AutoLog autoLog, 1 var2_2) {
            this(autoLog);
        }
    }
}

