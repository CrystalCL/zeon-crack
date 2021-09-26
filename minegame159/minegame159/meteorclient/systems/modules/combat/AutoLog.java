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
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.client.MinecraftClient;

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
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (Entity2 instanceof EndCrystalEntity && d2 < DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Entity2.getPos())) {
                d2 = DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Entity2.getPos());
                continue;
            }
            if (!(Entity2 instanceof PlayerEntity) || !(d2 < DamageCalcUtils.getSwordDamage((PlayerEntity)Entity2, true)) || !Friends.get().notTrusted((PlayerEntity)Entity2) || !(this.mc.player.getPos().distanceTo(Entity2.getPos()) < 5.0) || !(((PlayerEntity)Entity2).getActiveItem().getItem() instanceof SwordItem)) continue;
            d2 = DamageCalcUtils.getSwordDamage((PlayerEntity)Entity2, true);
        }
        if (!Modules.get().isActive(NoFall.class) && this.mc.player.fallDistance > 3.0f && (d = (double)this.mc.player.fallDistance * 0.5) > d2 && !EntityUtils.isAboveWater((Entity)this.mc.player)) {
            d2 = d;
        }
        if (Utils.getDimension() != Dimension.Overworld) {
            for (Entity Entity2 : this.mc.world.blockEntities) {
                BlockPos BlockPos2 = Entity2.getPos();
                Vec3d Vec3d2 = new Vec3d((double)BlockPos2.getX(), (double)BlockPos2.getY(), (double)BlockPos2.getZ());
                if (!(Entity2 instanceof BedBlockEntity) || !(d2 < DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Vec3d2))) continue;
                d2 = DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Vec3d2);
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
        if (this.mc.player.getHealth() <= 0.0f) {
            this.toggle();
            return;
        }
        if (this.mc.player.getHealth() <= (float)this.health.get().intValue()) {
            this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText(String.valueOf(new StringBuilder().append("[AutoLog] Health was lower than ").append(this.health.get()).append(".")))));
            if (this.smartToggle.get().booleanValue()) {
                this.toggle();
                this.enableHealthListener();
            }
        }
        if (this.smart.get().booleanValue() && (double)(this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) - this.getHealthReduction() < (double)this.health.get().intValue()) {
            this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText(String.valueOf(new StringBuilder().append("[AutoLog] Health was going to be lower than ").append(this.health.get()).append(".")))));
            if (this.toggleOff.get().booleanValue()) {
                this.toggle();
            }
        }
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (Entity2 instanceof PlayerEntity && Entity2.getUuid() != this.mc.player.getUuid()) {
                if (this.onlyTrusted.get().booleanValue() && Entity2 != this.mc.player && Friends.get().notTrusted((PlayerEntity)Entity2)) {
                    this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("[AutoLog] A non-trusted player appeared in your render distance.")));
                    if (!this.toggleOff.get().booleanValue()) break;
                    this.toggle();
                    break;
                }
                if (this.mc.player.distanceTo(Entity2) < 8.0f && this.instantDeath.get().booleanValue() && DamageCalcUtils.getSwordDamage((PlayerEntity)Entity2, true) > (double)(this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount())) {
                    this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("[AutoLog] Anti-32k measures.")));
                    if (!this.toggleOff.get().booleanValue()) break;
                    this.toggle();
                    break;
                }
            }
            if (!(Entity2 instanceof EndCrystalEntity) || !(this.mc.player.distanceTo(Entity2) < (float)this.range.get().intValue()) || !this.crystalLog.get().booleanValue()) continue;
            this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("[AutoLog] End Crystal appeared within specified range.")));
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

    static MinecraftClient access$100(AutoLog autoLog) {
        return autoLog.mc;
    }

    static MinecraftClient access$200(AutoLog autoLog) {
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
            } else if (Utils.canUpdate() && !AutoLog.access$100((AutoLog)this.this$0).player.isDead() && AutoLog.access$200((AutoLog)this.this$0).player.getHealth() >= (float)((Integer)AutoLog.access$300(this.this$0).get()).intValue()) {
                this.this$0.toggle();
                AutoLog.access$000(this.this$0);
            }
        }

        StaticListener(AutoLog autoLog, 1 var2_2) {
            this(autoLog);
        }
    }
}

