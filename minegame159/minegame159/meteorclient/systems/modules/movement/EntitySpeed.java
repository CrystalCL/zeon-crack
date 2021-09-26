/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.LivingEntityMoveEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class EntitySpeed
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> inWater;
    private final Setting<Double> speed;
    private final Setting<Boolean> onlyOnGround;

    public EntitySpeed() {
        super(Categories.Movement, "entity-speed", "Makes you go faster when riding entities.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.speed = this.sgGeneral.add(new DoubleSetting.Builder().name("speed").description("Horizontal speed in blocks per second.").defaultValue(10.0).min(0.0).sliderMax(50.0).build());
        this.onlyOnGround = this.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Use speed only when standing on a block.").defaultValue(false).build());
        this.inWater = this.sgGeneral.add(new BoolSetting.Builder().name("in-water").description("Use speed when in water.").defaultValue(false).build());
    }

    @EventHandler
    private void onLivingEntityMove(LivingEntityMoveEvent livingEntityMoveEvent) {
        if (livingEntityMoveEvent.entity.getPrimaryPassenger() != this.mc.player) {
            return;
        }
        LivingEntity LivingEntity2 = livingEntityMoveEvent.entity;
        if (this.onlyOnGround.get().booleanValue() && !LivingEntity2.isOnGround()) {
            return;
        }
        if (!this.inWater.get().booleanValue() && LivingEntity2.isTouchingWater()) {
            return;
        }
        Vec3d Vec3d2 = PlayerUtils.getHorizontalVelocity(this.speed.get());
        ((IVec3d)livingEntityMoveEvent.movement).setXZ(Vec3d2.x, Vec3d2.z);
    }
}

