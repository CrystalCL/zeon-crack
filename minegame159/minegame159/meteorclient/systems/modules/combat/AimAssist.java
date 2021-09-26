/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.entity.Target;
import minegame159.meteorclient.utils.misc.Vec3;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class AimAssist
extends Module {
    private final Setting<Boolean> friends;
    private final SettingGroup sgGeneral;
    private final Setting<Double> speed;
    private final SettingGroup sgSpeed;
    private final Vec3 vec3d1;
    private final Setting<Boolean> instant;
    private final Setting<Target> bodyTarget;
    private final Setting<Double> range;
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
    private Entity target;
    private final Setting<Boolean> ignoreWalls;
    private final Setting<SortPriority> priority;

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.target != null) {
            this.aim(this.target, renderEvent.tickDelta, this.instant.get());
        }
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

    private boolean lambda$onTick$0(Entity Entity2) {
        if (!Entity2.isAlive()) {
            return false;
        }
        if ((double)this.mc.player.distanceTo(Entity2) >= this.range.get()) {
            return false;
        }
        if (!this.ignoreWalls.get().booleanValue() && !PlayerUtils.canSeeEntity(Entity2)) {
            return false;
        }
        if (Entity2 == this.mc.player || !this.entities.get().getBoolean((Object)Entity2.getType())) {
            return false;
        }
        if (Entity2 instanceof PlayerEntity && !this.friends.get().booleanValue()) {
            return Friends.get().attack((PlayerEntity)Entity2);
        }
        return true;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.target = EntityUtils.get(this::lambda$onTick$0, this.priority.get());
    }

    private void aim(Entity Entity2, double d, boolean bl) {
        double d2;
        double d3;
        this.setVec3dToTargetPoint(this.vec3d1, Entity2, d);
        double d4 = this.vec3d1.x - this.mc.player.getX();
        double d5 = this.vec3d1.z - this.mc.player.getZ();
        double d6 = this.vec3d1.y - (this.mc.player.getY() + (double)this.mc.player.getEyeHeight(this.mc.player.getPose()));
        double d7 = Math.toDegrees(Math.atan2(d5, d4)) - 90.0;
        if (bl) {
            this.mc.player.yaw = (float)d7;
        } else {
            d3 = MathHelper.wrapDegrees((double)(d7 - (double)this.mc.player.yaw));
            d2 = this.speed.get() * (double)(d3 >= 0.0 ? 1 : -1) * d;
            if (d2 >= 0.0 && d2 > d3 || d2 < 0.0 && d2 < d3) {
                d2 = d3;
            }
            this.mc.player.yaw = (float)((double)this.mc.player.yaw + d2);
        }
        double d8 = Math.sqrt(d4 * d4 + d5 * d5);
        d7 = -Math.toDegrees(Math.atan2(d6, d8));
        if (bl) {
            this.mc.player.pitch = (float)d7;
        } else {
            d3 = MathHelper.wrapDegrees((double)(d7 - (double)this.mc.player.pitch));
            d2 = this.speed.get() * (double)(d3 >= 0.0 ? 1 : -1) * d;
            if (d2 >= 0.0 && d2 > d3 || d2 < 0.0 && d2 < d3) {
                d2 = d3;
            }
            this.mc.player.pitch = (float)((double)this.mc.player.pitch + d2);
        }
    }

    private void setVec3dToTargetPoint(Vec3 vec3, Entity Entity2, double d) {
        vec3.set(Entity2, d);
        switch (1.$SwitchMap$minegame159$meteorclient$utils$entity$Target[this.bodyTarget.get().ordinal()]) {
            case 1: {
                vec3.add(0.0, Entity2.getEyeHeight(Entity2.getPose()), 0.0);
                break;
            }
            case 2: {
                vec3.add(0.0, Entity2.getEyeHeight(Entity2.getPose()) / 2.0f, 0.0);
            }
        }
    }

    public AimAssist() {
        super(Categories.Combat, "aim-assist", "Automatically aims at entities.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgSpeed = this.settings.createGroup("Aim Speed");
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The range at which an entity can be targeted.").defaultValue(5.0).min(0.0).build());
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to aim at.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(EntityType.PLAYER)).build());
        this.friends = this.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Whether or not to aim at friends.").defaultValue(false).build());
        this.ignoreWalls = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-walls").description("Whether or not to ignore aiming through walls.").defaultValue(false).build());
        this.priority = this.sgGeneral.add(new EnumSetting.Builder().name("priority").description("How to select target from entities in range.").defaultValue(SortPriority.LowestHealth).build());
        this.bodyTarget = this.sgGeneral.add(new EnumSetting.Builder().name("target").description("Which part of the entities body to aim at.").defaultValue(Target.Body).build());
        this.instant = this.sgSpeed.add(new BoolSetting.Builder().name("instant-look").description("Instantly looks at the entity.").defaultValue(false).build());
        this.speed = this.sgSpeed.add(new DoubleSetting.Builder().name("speed").description("How fast to aim at the entity.").defaultValue(5.0).min(0.0).build());
        this.vec3d1 = new Vec3();
    }
}

