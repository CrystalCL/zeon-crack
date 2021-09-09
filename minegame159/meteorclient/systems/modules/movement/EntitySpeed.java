/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.util.math.Vec3d
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> onlyOnGround;
    private final /* synthetic */ Setting<Double> speed;
    private final /* synthetic */ Setting<Boolean> inWater;

    public EntitySpeed() {
        super(Categories.Movement, "entity-speed", "Makes you go faster when riding entities.");
        EntitySpeed llIIIllIIIIlIl;
        llIIIllIIIIlIl.sgGeneral = llIIIllIIIIlIl.settings.getDefaultGroup();
        llIIIllIIIIlIl.speed = llIIIllIIIIlIl.sgGeneral.add(new DoubleSetting.Builder().name("speed").description("Horizontal speed in blocks per second.").defaultValue(10.0).min(0.0).sliderMax(50.0).build());
        llIIIllIIIIlIl.onlyOnGround = llIIIllIIIIlIl.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Use speed only when standing on a block.").defaultValue(false).build());
        llIIIllIIIIlIl.inWater = llIIIllIIIIlIl.sgGeneral.add(new BoolSetting.Builder().name("in-water").description("Use speed when in water.").defaultValue(false).build());
    }

    @EventHandler
    private void onLivingEntityMove(LivingEntityMoveEvent llIIIlIlllllll) {
        EntitySpeed llIIIllIIIIIII;
        if (llIIIlIlllllll.entity.getPrimaryPassenger() != llIIIllIIIIIII.mc.player) {
            return;
        }
        LivingEntity llIIIlIllllllI = llIIIlIlllllll.entity;
        if (llIIIllIIIIIII.onlyOnGround.get().booleanValue() && !llIIIlIllllllI.isOnGround()) {
            return;
        }
        if (!llIIIllIIIIIII.inWater.get().booleanValue() && llIIIlIllllllI.isTouchingWater()) {
            return;
        }
        Vec3d llIIIlIlllllIl = PlayerUtils.getHorizontalVelocity(llIIIllIIIIIII.speed.get());
        ((IVec3d)llIIIlIlllllll.movement).setXZ(llIIIlIlllllIl.x, llIIIlIlllllIl.z);
    }
}

