/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.SwordItem
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.block.entity.BlockEntity
 *  net.minecraft.block.entity.BedBlockEntity
 *  net.minecraft.network.packet.s2c.play.DisconnectS2CPacket
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
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;

public class AutoLog
extends Module {
    private final /* synthetic */ StaticListener staticListener;
    private final /* synthetic */ Setting<Boolean> smartToggle;
    private final /* synthetic */ Setting<Boolean> onlyTrusted;
    private final /* synthetic */ Setting<Boolean> crystalLog;
    private final /* synthetic */ Setting<Integer> health;
    private final /* synthetic */ Setting<Boolean> toggleOff;
    private final /* synthetic */ Setting<Boolean> smart;
    private final /* synthetic */ Setting<Integer> range;
    private final /* synthetic */ Setting<Boolean> instantDeath;
    private final /* synthetic */ SettingGroup sgGeneral;

    public AutoLog() {
        super(Categories.Combat, "auto-log", "Automatically disconnects you when certain requirements are met.");
        AutoLog lllllllllllllllllllIIlIIIllIlIIl;
        lllllllllllllllllllIIlIIIllIlIIl.sgGeneral = lllllllllllllllllllIIlIIIllIlIIl.settings.getDefaultGroup();
        lllllllllllllllllllIIlIIIllIlIIl.health = lllllllllllllllllllIIlIIIllIlIIl.sgGeneral.add(new IntSetting.Builder().name("health").description("Automatically disconnects when health is lower or equal to this value.").defaultValue(6).min(0).max(20).sliderMax(20).build());
        lllllllllllllllllllIIlIIIllIlIIl.smart = lllllllllllllllllllIIlIIIllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("smart").description("Disconnects when you're about to take enough damage to kill you.").defaultValue(true).build());
        lllllllllllllllllllIIlIIIllIlIIl.onlyTrusted = lllllllllllllllllllIIlIIIllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("only-trusted").description("Disconnects when a player not on your friends list appears in render distance.").defaultValue(false).build());
        lllllllllllllllllllIIlIIIllIlIIl.instantDeath = lllllllllllllllllllIIlIIIllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("32K").description("Disconnects when a player near you can instantly kill you.").defaultValue(false).build());
        lllllllllllllllllllIIlIIIllIlIIl.crystalLog = lllllllllllllllllllIIlIIIllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("crystal-nearby").description("Disconnects when a crystal appears near you.").defaultValue(false).build());
        lllllllllllllllllllIIlIIIllIlIIl.range = lllllllllllllllllllIIlIIIllIlIIl.sgGeneral.add(new IntSetting.Builder().name("range").description("How close a crystal has to be to you before you disconnect.").defaultValue(4).min(1).max(10).sliderMax(5).build());
        lllllllllllllllllllIIlIIIllIlIIl.smartToggle = lllllllllllllllllllIIlIIIllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("smart-toggle").description("Disables Auto Log after a low-health logout. WILL re-enable once you heal.").defaultValue(false).build());
        lllllllllllllllllllIIlIIIllIlIIl.toggleOff = lllllllllllllllllllIIlIIIllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("toggle-off").description("Disables Auto Log after usage.").defaultValue(true).build());
        lllllllllllllllllllIIlIIIllIlIIl.staticListener = lllllllllllllllllllIIlIIIllIlIIl.new StaticListener();
    }

    private void enableHealthListener() {
        AutoLog lllllllllllllllllllIIlIIIlIIlIlI;
        MeteorClient.EVENT_BUS.subscribe(lllllllllllllllllllIIlIIIlIIlIlI.staticListener);
    }

    private double getHealthReduction() {
        double lllllllllllllllllllIIlIIIlIllIII;
        AutoLog lllllllllllllllllllIIlIIIlIlIlII;
        double lllllllllllllllllllIIlIIIlIlIIll = 0.0;
        for (Entity lllllllllllllllllllIIlIIIlIllIIl : lllllllllllllllllllIIlIIIlIlIlII.mc.world.getEntities()) {
            if (lllllllllllllllllllIIlIIIlIllIIl instanceof EndCrystalEntity && lllllllllllllllllllIIlIIIlIlIIll < DamageCalcUtils.crystalDamage((LivingEntity)lllllllllllllllllllIIlIIIlIlIlII.mc.player, lllllllllllllllllllIIlIIIlIllIIl.getPos())) {
                lllllllllllllllllllIIlIIIlIlIIll = DamageCalcUtils.crystalDamage((LivingEntity)lllllllllllllllllllIIlIIIlIlIlII.mc.player, lllllllllllllllllllIIlIIIlIllIIl.getPos());
                continue;
            }
            if (!(lllllllllllllllllllIIlIIIlIllIIl instanceof PlayerEntity) || !(lllllllllllllllllllIIlIIIlIlIIll < DamageCalcUtils.getSwordDamage((PlayerEntity)lllllllllllllllllllIIlIIIlIllIIl, true)) || !Friends.get().notTrusted((PlayerEntity)lllllllllllllllllllIIlIIIlIllIIl) || !(lllllllllllllllllllIIlIIIlIlIlII.mc.player.getPos().distanceTo(lllllllllllllllllllIIlIIIlIllIIl.getPos()) < 5.0) || !(((PlayerEntity)lllllllllllllllllllIIlIIIlIllIIl).getActiveItem().getItem() instanceof SwordItem)) continue;
            lllllllllllllllllllIIlIIIlIlIIll = DamageCalcUtils.getSwordDamage((PlayerEntity)lllllllllllllllllllIIlIIIlIllIIl, true);
        }
        if (!Modules.get().isActive(NoFall.class) && lllllllllllllllllllIIlIIIlIlIlII.mc.player.fallDistance > 3.0f && (lllllllllllllllllllIIlIIIlIllIII = (double)lllllllllllllllllllIIlIIIlIlIlII.mc.player.fallDistance * 0.5) > lllllllllllllllllllIIlIIIlIlIIll && !EntityUtils.isAboveWater((Entity)lllllllllllllllllllIIlIIIlIlIlII.mc.player)) {
            lllllllllllllllllllIIlIIIlIlIIll = lllllllllllllllllllIIlIIIlIllIII;
        }
        if (Utils.getDimension() != Dimension.Overworld) {
            for (BlockEntity lllllllllllllllllllIIlIIIlIlIlIl : lllllllllllllllllllIIlIIIlIlIlII.mc.world.blockEntities) {
                BlockPos lllllllllllllllllllIIlIIIlIlIlll = lllllllllllllllllllIIlIIIlIlIlIl.getPos();
                Vec3d lllllllllllllllllllIIlIIIlIlIllI = new Vec3d((double)lllllllllllllllllllIIlIIIlIlIlll.getX(), (double)lllllllllllllllllllIIlIIIlIlIlll.getY(), (double)lllllllllllllllllllIIlIIIlIlIlll.getZ());
                if (!(lllllllllllllllllllIIlIIIlIlIlIl instanceof BedBlockEntity) || !(lllllllllllllllllllIIlIIIlIlIIll < DamageCalcUtils.bedDamage((LivingEntity)lllllllllllllllllllIIlIIIlIlIlII.mc.player, lllllllllllllllllllIIlIIIlIlIllI))) continue;
                lllllllllllllllllllIIlIIIlIlIIll = DamageCalcUtils.bedDamage((LivingEntity)lllllllllllllllllllIIlIIIlIlIlII.mc.player, lllllllllllllllllllIIlIIIlIlIllI);
            }
        }
        return lllllllllllllllllllIIlIIIlIlIIll;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllIIlIIIllIIIll) {
        AutoLog lllllllllllllllllllIIlIIIllIIlII;
        if (lllllllllllllllllllIIlIIIllIIlII.mc.player.getHealth() <= 0.0f) {
            lllllllllllllllllllIIlIIIllIIlII.toggle();
            return;
        }
        if (lllllllllllllllllllIIlIIIllIIlII.mc.player.getHealth() <= (float)lllllllllllllllllllIIlIIIllIIlII.health.get().intValue()) {
            lllllllllllllllllllIIlIIIllIIlII.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText(String.valueOf(new StringBuilder().append("[AutoLog] Health was lower than ").append(lllllllllllllllllllIIlIIIllIIlII.health.get()).append(".")))));
            if (lllllllllllllllllllIIlIIIllIIlII.smartToggle.get().booleanValue()) {
                lllllllllllllllllllIIlIIIllIIlII.toggle();
                lllllllllllllllllllIIlIIIllIIlII.enableHealthListener();
            }
        }
        if (lllllllllllllllllllIIlIIIllIIlII.smart.get().booleanValue() && (double)(lllllllllllllllllllIIlIIIllIIlII.mc.player.getHealth() + lllllllllllllllllllIIlIIIllIIlII.mc.player.getAbsorptionAmount()) - lllllllllllllllllllIIlIIIllIIlII.getHealthReduction() < (double)lllllllllllllllllllIIlIIIllIIlII.health.get().intValue()) {
            lllllllllllllllllllIIlIIIllIIlII.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText(String.valueOf(new StringBuilder().append("[AutoLog] Health was going to be lower than ").append(lllllllllllllllllllIIlIIIllIIlII.health.get()).append(".")))));
            if (lllllllllllllllllllIIlIIIllIIlII.toggleOff.get().booleanValue()) {
                lllllllllllllllllllIIlIIIllIIlII.toggle();
            }
        }
        for (Entity lllllllllllllllllllIIlIIIllIIlIl : lllllllllllllllllllIIlIIIllIIlII.mc.world.getEntities()) {
            if (lllllllllllllllllllIIlIIIllIIlIl instanceof PlayerEntity && lllllllllllllllllllIIlIIIllIIlIl.getUuid() != lllllllllllllllllllIIlIIIllIIlII.mc.player.getUuid()) {
                if (lllllllllllllllllllIIlIIIllIIlII.onlyTrusted.get().booleanValue() && lllllllllllllllllllIIlIIIllIIlIl != lllllllllllllllllllIIlIIIllIIlII.mc.player && Friends.get().notTrusted((PlayerEntity)lllllllllllllllllllIIlIIIllIIlIl)) {
                    lllllllllllllllllllIIlIIIllIIlII.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("[AutoLog] A non-trusted player appeared in your render distance.")));
                    if (!lllllllllllllllllllIIlIIIllIIlII.toggleOff.get().booleanValue()) break;
                    lllllllllllllllllllIIlIIIllIIlII.toggle();
                    break;
                }
                if (lllllllllllllllllllIIlIIIllIIlII.mc.player.distanceTo(lllllllllllllllllllIIlIIIllIIlIl) < 8.0f && lllllllllllllllllllIIlIIIllIIlII.instantDeath.get().booleanValue() && DamageCalcUtils.getSwordDamage((PlayerEntity)lllllllllllllllllllIIlIIIllIIlIl, true) > (double)(lllllllllllllllllllIIlIIIllIIlII.mc.player.getHealth() + lllllllllllllllllllIIlIIIllIIlII.mc.player.getAbsorptionAmount())) {
                    lllllllllllllllllllIIlIIIllIIlII.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("[AutoLog] Anti-32k measures.")));
                    if (!lllllllllllllllllllIIlIIIllIIlII.toggleOff.get().booleanValue()) break;
                    lllllllllllllllllllIIlIIIllIIlII.toggle();
                    break;
                }
            }
            if (!(lllllllllllllllllllIIlIIIllIIlIl instanceof EndCrystalEntity) || !(lllllllllllllllllllIIlIIIllIIlII.mc.player.distanceTo(lllllllllllllllllllIIlIIIllIIlIl) < (float)lllllllllllllllllllIIlIIIllIIlII.range.get().intValue()) || !lllllllllllllllllllIIlIIIllIIlII.crystalLog.get().booleanValue()) continue;
            lllllllllllllllllllIIlIIIllIIlII.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("[AutoLog] End Crystal appeared within specified range.")));
            if (!lllllllllllllllllllIIlIIIllIIlII.toggleOff.get().booleanValue()) continue;
            lllllllllllllllllllIIlIIIllIIlII.toggle();
        }
    }

    private void disableHealthListener() {
        AutoLog lllllllllllllllllllIIlIIIlIIlIII;
        MeteorClient.EVENT_BUS.unsubscribe(lllllllllllllllllllIIlIIIlIIlIII.staticListener);
    }

    private class StaticListener {
        @EventHandler
        private void healthListener(TickEvent.Post lIIlIlIlIllIII) {
            StaticListener lIIlIlIlIllIIl;
            if (lIIlIlIlIllIIl.AutoLog.this.isActive()) {
                lIIlIlIlIllIIl.AutoLog.this.disableHealthListener();
            } else if (Utils.canUpdate() && !((AutoLog)lIIlIlIlIllIIl.AutoLog.this).mc.player.isDead() && ((AutoLog)lIIlIlIlIllIIl.AutoLog.this).mc.player.getHealth() >= (float)((Integer)lIIlIlIlIllIIl.AutoLog.this.health.get()).intValue()) {
                lIIlIlIlIllIIl.AutoLog.this.toggle();
                lIIlIlIlIllIIl.AutoLog.this.disableHealthListener();
            }
        }

        private StaticListener() {
            StaticListener lIIlIlIlIlllIl;
        }
    }
}

