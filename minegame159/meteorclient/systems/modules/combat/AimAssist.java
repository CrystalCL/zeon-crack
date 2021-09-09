/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.util.math.MathHelper
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
    private /* synthetic */ Entity target;
    private final /* synthetic */ SettingGroup sgSpeed;
    private final /* synthetic */ Vec3 vec3d1;
    private final /* synthetic */ Setting<Double> range;
    private final /* synthetic */ Setting<Boolean> ignoreWalls;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Target> bodyTarget;
    private final /* synthetic */ Setting<Boolean> friends;
    private final /* synthetic */ Setting<Double> speed;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ Setting<Boolean> instant;
    private final /* synthetic */ Setting<SortPriority> priority;

    private void setVec3dToTargetPoint(Vec3 lllllllllllllllllIlIIIIIllIIIIlI, Entity lllllllllllllllllIlIIIIIllIIIlIl, double lllllllllllllllllIlIIIIIllIIIlII) {
        AimAssist lllllllllllllllllIlIIIIIllIIIlll;
        lllllllllllllllllIlIIIIIllIIIIlI.set(lllllllllllllllllIlIIIIIllIIIlIl, lllllllllllllllllIlIIIIIllIIIlII);
        switch (lllllllllllllllllIlIIIIIllIIIlll.bodyTarget.get()) {
            case Head: {
                lllllllllllllllllIlIIIIIllIIIIlI.add(0.0, lllllllllllllllllIlIIIIIllIIIlIl.getEyeHeight(lllllllllllllllllIlIIIIIllIIIlIl.getPose()), 0.0);
                break;
            }
            case Body: {
                lllllllllllllllllIlIIIIIllIIIIlI.add(0.0, lllllllllllllllllIlIIIIIllIIIlIl.getEyeHeight(lllllllllllllllllIlIIIIIllIIIlIl.getPose()) / 2.0f, 0.0);
            }
        }
    }

    private void aim(Entity lllllllllllllllllIlIIIIIllIllllI, double lllllllllllllllllIlIIIIIllIlIIll, boolean lllllllllllllllllIlIIIIIllIlllII) {
        AimAssist lllllllllllllllllIlIIIIIllIlIllI;
        lllllllllllllllllIlIIIIIllIlIllI.setVec3dToTargetPoint(lllllllllllllllllIlIIIIIllIlIllI.vec3d1, lllllllllllllllllIlIIIIIllIllllI, lllllllllllllllllIlIIIIIllIlIIll);
        double lllllllllllllllllIlIIIIIllIllIll = lllllllllllllllllIlIIIIIllIlIllI.vec3d1.x - lllllllllllllllllIlIIIIIllIlIllI.mc.player.getX();
        double lllllllllllllllllIlIIIIIllIllIlI = lllllllllllllllllIlIIIIIllIlIllI.vec3d1.z - lllllllllllllllllIlIIIIIllIlIllI.mc.player.getZ();
        double lllllllllllllllllIlIIIIIllIllIIl = lllllllllllllllllIlIIIIIllIlIllI.vec3d1.y - (lllllllllllllllllIlIIIIIllIlIllI.mc.player.getY() + (double)lllllllllllllllllIlIIIIIllIlIllI.mc.player.getEyeHeight(lllllllllllllllllIlIIIIIllIlIllI.mc.player.getPose()));
        double lllllllllllllllllIlIIIIIllIllIII = Math.toDegrees(Math.atan2(lllllllllllllllllIlIIIIIllIllIlI, lllllllllllllllllIlIIIIIllIllIll)) - 90.0;
        if (lllllllllllllllllIlIIIIIllIlllII) {
            lllllllllllllllllIlIIIIIllIlIllI.mc.player.yaw = (float)lllllllllllllllllIlIIIIIllIllIII;
        } else {
            double lllllllllllllllllIlIIIIIlllIIIll = MathHelper.wrapDegrees((double)(lllllllllllllllllIlIIIIIllIllIII - (double)lllllllllllllllllIlIIIIIllIlIllI.mc.player.yaw));
            double lllllllllllllllllIlIIIIIlllIIIlI = lllllllllllllllllIlIIIIIllIlIllI.speed.get() * (double)(lllllllllllllllllIlIIIIIlllIIIll >= 0.0 ? 1 : -1) * lllllllllllllllllIlIIIIIllIlIIll;
            if (lllllllllllllllllIlIIIIIlllIIIlI >= 0.0 && lllllllllllllllllIlIIIIIlllIIIlI > lllllllllllllllllIlIIIIIlllIIIll || lllllllllllllllllIlIIIIIlllIIIlI < 0.0 && lllllllllllllllllIlIIIIIlllIIIlI < lllllllllllllllllIlIIIIIlllIIIll) {
                lllllllllllllllllIlIIIIIlllIIIlI = lllllllllllllllllIlIIIIIlllIIIll;
            }
            lllllllllllllllllIlIIIIIllIlIllI.mc.player.yaw = (float)((double)lllllllllllllllllIlIIIIIllIlIllI.mc.player.yaw + lllllllllllllllllIlIIIIIlllIIIlI);
        }
        double lllllllllllllllllIlIIIIIllIlIlll = Math.sqrt(lllllllllllllllllIlIIIIIllIllIll * lllllllllllllllllIlIIIIIllIllIll + lllllllllllllllllIlIIIIIllIllIlI * lllllllllllllllllIlIIIIIllIllIlI);
        lllllllllllllllllIlIIIIIllIllIII = -Math.toDegrees(Math.atan2(lllllllllllllllllIlIIIIIllIllIIl, lllllllllllllllllIlIIIIIllIlIlll));
        if (lllllllllllllllllIlIIIIIllIlllII) {
            lllllllllllllllllIlIIIIIllIlIllI.mc.player.pitch = (float)lllllllllllllllllIlIIIIIllIllIII;
        } else {
            double lllllllllllllllllIlIIIIIlllIIIIl = MathHelper.wrapDegrees((double)(lllllllllllllllllIlIIIIIllIllIII - (double)lllllllllllllllllIlIIIIIllIlIllI.mc.player.pitch));
            double lllllllllllllllllIlIIIIIlllIIIII = lllllllllllllllllIlIIIIIllIlIllI.speed.get() * (double)(lllllllllllllllllIlIIIIIlllIIIIl >= 0.0 ? 1 : -1) * lllllllllllllllllIlIIIIIllIlIIll;
            if (lllllllllllllllllIlIIIIIlllIIIII >= 0.0 && lllllllllllllllllIlIIIIIlllIIIII > lllllllllllllllllIlIIIIIlllIIIIl || lllllllllllllllllIlIIIIIlllIIIII < 0.0 && lllllllllllllllllIlIIIIIlllIIIII < lllllllllllllllllIlIIIIIlllIIIIl) {
                lllllllllllllllllIlIIIIIlllIIIII = lllllllllllllllllIlIIIIIlllIIIIl;
            }
            lllllllllllllllllIlIIIIIllIlIllI.mc.player.pitch = (float)((double)lllllllllllllllllIlIIIIIllIlIllI.mc.player.pitch + lllllllllllllllllIlIIIIIlllIIIII);
        }
    }

    @Override
    public String getInfoString() {
        AimAssist lllllllllllllllllIlIIIIIlIlllllI;
        if (lllllllllllllllllIlIIIIIlIlllllI.target != null && lllllllllllllllllIlIIIIIlIlllllI.target instanceof PlayerEntity) {
            return lllllllllllllllllIlIIIIIlIlllllI.target.getEntityName();
        }
        if (lllllllllllllllllIlIIIIIlIlllllI.target != null) {
            return lllllllllllllllllIlIIIIIlIlllllI.target.getType().getName().getString();
        }
        return null;
    }

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIlIIIIIlllllIll) {
        AimAssist lllllllllllllllllIlIIIIIllllllIl;
        if (lllllllllllllllllIlIIIIIllllllIl.target != null) {
            lllllllllllllllllIlIIIIIllllllIl.aim(lllllllllllllllllIlIIIIIllllllIl.target, lllllllllllllllllIlIIIIIlllllIll.tickDelta, lllllllllllllllllIlIIIIIllllllIl.instant.get());
        }
    }

    public AimAssist() {
        super(Categories.Combat, "aim-assist", "Automatically aims at entities.");
        AimAssist lllllllllllllllllIlIIIIlIIIIllll;
        lllllllllllllllllIlIIIIlIIIIllll.sgGeneral = lllllllllllllllllIlIIIIlIIIIllll.settings.getDefaultGroup();
        lllllllllllllllllIlIIIIlIIIIllll.sgSpeed = lllllllllllllllllIlIIIIlIIIIllll.settings.createGroup("Aim Speed");
        lllllllllllllllllIlIIIIlIIIIllll.range = lllllllllllllllllIlIIIIlIIIIllll.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The range at which an entity can be targeted.").defaultValue(5.0).min(0.0).build());
        lllllllllllllllllIlIIIIlIIIIllll.entities = lllllllllllllllllIlIIIIlIIIIllll.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to aim at.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PLAYER})).build());
        lllllllllllllllllIlIIIIlIIIIllll.friends = lllllllllllllllllIlIIIIlIIIIllll.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Whether or not to aim at friends.").defaultValue(false).build());
        lllllllllllllllllIlIIIIlIIIIllll.ignoreWalls = lllllllllllllllllIlIIIIlIIIIllll.sgGeneral.add(new BoolSetting.Builder().name("ignore-walls").description("Whether or not to ignore aiming through walls.").defaultValue(false).build());
        lllllllllllllllllIlIIIIlIIIIllll.priority = lllllllllllllllllIlIIIIlIIIIllll.sgGeneral.add(new EnumSetting.Builder().name("priority").description("How to select target from entities in range.").defaultValue(SortPriority.LowestHealth).build());
        lllllllllllllllllIlIIIIlIIIIllll.bodyTarget = lllllllllllllllllIlIIIIlIIIIllll.sgGeneral.add(new EnumSetting.Builder().name("target").description("Which part of the entities body to aim at.").defaultValue(Target.Body).build());
        lllllllllllllllllIlIIIIlIIIIllll.instant = lllllllllllllllllIlIIIIlIIIIllll.sgSpeed.add(new BoolSetting.Builder().name("instant-look").description("Instantly looks at the entity.").defaultValue(false).build());
        lllllllllllllllllIlIIIIlIIIIllll.speed = lllllllllllllllllIlIIIIlIIIIllll.sgSpeed.add(new DoubleSetting.Builder().name("speed").description("How fast to aim at the entity.").defaultValue(5.0).min(0.0).build());
        lllllllllllllllllIlIIIIlIIIIllll.vec3d1 = new Vec3();
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIlIIIIlIIIIlIIl) {
        AimAssist lllllllllllllllllIlIIIIlIIIIlIlI;
        lllllllllllllllllIlIIIIlIIIIlIlI.target = EntityUtils.get(lllllllllllllllllIlIIIIIlIlllIIl -> {
            AimAssist lllllllllllllllllIlIIIIIlIlllIII;
            if (!lllllllllllllllllIlIIIIIlIlllIIl.isAlive()) {
                return false;
            }
            if ((double)lllllllllllllllllIlIIIIIlIlllIII.mc.player.distanceTo(lllllllllllllllllIlIIIIIlIlllIIl) >= lllllllllllllllllIlIIIIIlIlllIII.range.get()) {
                return false;
            }
            if (!lllllllllllllllllIlIIIIIlIlllIII.ignoreWalls.get().booleanValue() && !PlayerUtils.canSeeEntity(lllllllllllllllllIlIIIIIlIlllIIl)) {
                return false;
            }
            if (lllllllllllllllllIlIIIIIlIlllIIl == lllllllllllllllllIlIIIIIlIlllIII.mc.player || !lllllllllllllllllIlIIIIIlIlllIII.entities.get().getBoolean((Object)lllllllllllllllllIlIIIIIlIlllIIl.getType())) {
                return false;
            }
            if (lllllllllllllllllIlIIIIIlIlllIIl instanceof PlayerEntity && !lllllllllllllllllIlIIIIIlIlllIII.friends.get().booleanValue()) {
                return Friends.get().attack((PlayerEntity)lllllllllllllllllIlIIIIIlIlllIIl);
            }
            return true;
        }, lllllllllllllllllIlIIIIlIIIIlIlI.priority.get());
    }
}

