/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.enchantment.ProtectionEnchantment
 *  net.minecraft.world.GameMode
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Box
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.shape.VoxelShapes
 *  net.minecraft.util.shape.VoxelShapes6
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$class_2829
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$class_2830
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.fluid.Fluids
 *  net.minecraft.block.Material
 */
package minegame159.meteorclient.systems.modules.movement;

import baritone.api.BaritoneAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.CanWalkOnFluidEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.FluidCollisionShapeEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.EntityUtils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.world.GameMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShapes6;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.fluid.Fluids;
import net.minecraft.block.Material;

public class Jesus
extends Module {
    private final /* synthetic */ Setting<Integer> dipIntoLavaHeight;
    private final /* synthetic */ SettingGroup sgLava;
    private final /* synthetic */ Setting<Boolean> walkOnWater;
    private final /* synthetic */ Setting<Boolean> disableOnSneakForWater;
    private final /* synthetic */ Setting<Integer> dipIntoWaterHeight;
    private final /* synthetic */ Mutable blockPos;
    private /* synthetic */ boolean preBaritoneAssumeWalkOnLava;
    private final /* synthetic */ Setting<Boolean> disableOnSneakForLava;
    private /* synthetic */ boolean preBaritoneAssumeWalkOnWater;
    private final /* synthetic */ Setting<Boolean> dipIntoLava;
    private final /* synthetic */ Setting<Boolean> dipIntoWaterIfBurning;
    private final /* synthetic */ SettingGroup sgWater;
    private final /* synthetic */ Setting<Boolean> dipIntoLavaIfFireResistance;
    private final /* synthetic */ Setting<Boolean> fireResistanceSafeMode;
    private final /* synthetic */ Setting<Boolean> dipIntoWater;
    private /* synthetic */ int packetTimer;
    private final /* synthetic */ Setting<Boolean> walkOnLava;
    private /* synthetic */ int tickTimer;

    @Override
    public void onDeactivate() {
        Jesus lIIlIIIIIlIIlI;
        BaritoneAPI.getSettings().assumeWalkOnWater.value = lIIlIIIIIlIIlI.preBaritoneAssumeWalkOnWater;
        BaritoneAPI.getSettings().assumeWalkOnLava.value = lIIlIIIIIlIIlI.preBaritoneAssumeWalkOnLava;
    }

    public Jesus() {
        super(Categories.Movement, "jesus", "Walk on liquids like Jesus.");
        Jesus lIIlIIIIIllIII;
        lIIlIIIIIllIII.sgWater = lIIlIIIIIllIII.settings.createGroup("Water");
        lIIlIIIIIllIII.sgLava = lIIlIIIIIllIII.settings.createGroup("Lava");
        lIIlIIIIIllIII.walkOnWater = lIIlIIIIIllIII.sgWater.add(new BoolSetting.Builder().name("walk-on-water").description("Lets you walk on water.").defaultValue(true).build());
        lIIlIIIIIllIII.disableOnSneakForWater = lIIlIIIIIllIII.sgWater.add(new BoolSetting.Builder().name("disable-on-sneak-for-water").description("Lets you go under the water when your sneak key is held.").defaultValue(true).build());
        lIIlIIIIIllIII.dipIntoWater = lIIlIIIIIllIII.sgWater.add(new BoolSetting.Builder().name("dip-into-water").description("Lets you go under the water when you fall over a certain height.").defaultValue(true).build());
        lIIlIIIIIllIII.dipIntoWaterHeight = lIIlIIIIIllIII.sgWater.add(new IntSetting.Builder().name("dip-into-water-height").description("Maximum safe height.").defaultValue(4).min(1).max(255).sliderMin(3).sliderMax(21).build());
        lIIlIIIIIllIII.dipIntoWaterIfBurning = lIIlIIIIIllIII.sgWater.add(new BoolSetting.Builder().name("dip-into-water-if-burning").description("Lets you go under the water when you are burning.").defaultValue(true).build());
        lIIlIIIIIllIII.walkOnLava = lIIlIIIIIllIII.sgLava.add(new BoolSetting.Builder().name("walk-on-lava").description("Lets you walk on lava.").defaultValue(true).build());
        lIIlIIIIIllIII.disableOnSneakForLava = lIIlIIIIIllIII.sgLava.add(new BoolSetting.Builder().name("disable-on-sneak-for-lava").description("Lets you go under the lava when your sneak key is held.").defaultValue(false).build());
        lIIlIIIIIllIII.dipIntoLava = lIIlIIIIIllIII.sgLava.add(new BoolSetting.Builder().name("dip-into-lava").description("Lets you go under the lava when you fall over than certain height.").defaultValue(false).build());
        lIIlIIIIIllIII.dipIntoLavaHeight = lIIlIIIIIllIII.sgLava.add(new IntSetting.Builder().name("dip-into-lava-height").description("Maximum safe height.").defaultValue(15).min(1).max(255).sliderMin(3).sliderMax(21).build());
        lIIlIIIIIllIII.dipIntoLavaIfFireResistance = lIIlIIIIIllIII.sgLava.add(new BoolSetting.Builder().name("dip-if-fire-resistance").description("Lets you go under the lava if you have Fire Resistance effect.").defaultValue(true).build());
        lIIlIIIIIllIII.fireResistanceSafeMode = lIIlIIIIIllIII.sgLava.add(new BoolSetting.Builder().name("fire-resistance-safe-mode").description("Prevents being in lava when the Fire Resistance effect is nearly over.").defaultValue(true).build());
        lIIlIIIIIllIII.blockPos = new Mutable();
        lIIlIIIIIllIII.tickTimer = 10;
        lIIlIIIIIllIII.packetTimer = 0;
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send lIIIllllllIlII) {
        PlayerMoveC2SPacket.class_2830 lIIIlllllIllll;
        Jesus lIIIllllllIlIl;
        if (!(lIIIllllllIlII.packet instanceof PlayerMoveC2SPacket)) {
            return;
        }
        if (lIIIllllllIlIl.mc.player.isTouchingWater() && !lIIIllllllIlIl.waterShouldBeSolid()) {
            return;
        }
        if (lIIIllllllIlIl.mc.player.isInLava() && !lIIIllllllIlIl.lavaShouldBeSolid()) {
            return;
        }
        PlayerMoveC2SPacket lIIIllllllIIll = (PlayerMoveC2SPacket)lIIIllllllIlII.packet;
        if (!(lIIIllllllIIll instanceof PlayerMoveC2SPacket.class_2829) && !(lIIIllllllIIll instanceof PlayerMoveC2SPacket.class_2830)) {
            return;
        }
        if (lIIIllllllIlIl.mc.player.isTouchingWater() || lIIIllllllIlIl.mc.player.isInLava() || lIIIllllllIlIl.mc.player.fallDistance > 3.0f || !lIIIllllllIlIl.isOverLiquid()) {
            return;
        }
        if (lIIIllllllIlIl.mc.player.input.movementForward == 0.0f && lIIIllllllIlIl.mc.player.input.movementSideways == 0.0f) {
            lIIIllllllIlII.cancel();
            return;
        }
        if (lIIIllllllIlIl.packetTimer++ < 4) {
            return;
        }
        lIIIllllllIlIl.packetTimer = 0;
        lIIIllllllIlII.cancel();
        double lIIIllllllIIlI = lIIIllllllIIll.getX(0.0);
        double lIIIllllllIIIl = lIIIllllllIIll.getY(0.0) + 0.05;
        double lIIIllllllIIII = lIIIllllllIIll.getZ(0.0);
        if (lIIIllllllIIll instanceof PlayerMoveC2SPacket.class_2829) {
            PlayerMoveC2SPacket.class_2829 lIIIllllllIllI = new PlayerMoveC2SPacket.class_2829(lIIIllllllIIlI, lIIIllllllIIIl, lIIIllllllIIII, true);
        } else {
            lIIIlllllIllll = new PlayerMoveC2SPacket.class_2830(lIIIllllllIIlI, lIIIllllllIIIl, lIIIllllllIIII, lIIIllllllIIll.getYaw(0.0f), lIIIllllllIIll.getPitch(0.0f), true);
        }
        lIIIllllllIlIl.mc.getNetworkHandler().getConnection().send((VoxelShapes6)lIIIlllllIllll);
    }

    private boolean isOverLiquid() {
        Jesus lIIIllllIlIlIl;
        boolean lIIIllllIlIlII = false;
        boolean lIIIllllIlIIll = false;
        List lIIIllllIlIIlI = lIIIllllIlIlIl.mc.world.getBlockCollisions((Entity)lIIIllllIlIlIl.mc.player, lIIIllllIlIlIl.mc.player.getBoundingBox().offset(0.0, -0.5, 0.0)).map(VoxelShape::getBoundingBox).collect(Collectors.toCollection(ArrayList::new));
        for (Box lIIIllllIlIllI : lIIIllllIlIIlI) {
            lIIIllllIlIlIl.blockPos.set(MathHelper.lerp((double)0.5, (double)lIIIllllIlIllI.minX, (double)lIIIllllIlIllI.maxX), MathHelper.lerp((double)0.5, (double)lIIIllllIlIllI.minY, (double)lIIIllllIlIllI.maxY), MathHelper.lerp((double)0.5, (double)lIIIllllIlIllI.minZ, (double)lIIIllllIlIllI.maxZ));
            Material lIIIllllIlIlll = lIIIllllIlIlIl.mc.world.getBlockState((BlockPos)lIIIllllIlIlIl.blockPos).getMaterial();
            if (lIIIllllIlIlll == Material.WATER || lIIIllllIlIlll == Material.LAVA) {
                lIIIllllIlIlII = true;
                continue;
            }
            if (lIIIllllIlIlll == Material.AIR) continue;
            lIIIllllIlIIll = true;
        }
        return lIIIllllIlIlII && !lIIIllllIlIIll;
    }

    private boolean waterShouldBeSolid() {
        Jesus lIIIlllllIIllI;
        return !(lIIIlllllIIllI.walkOnWater.get() == false || lIIIlllllIIllI.disableOnSneakForWater.get() != false && lIIIlllllIIllI.mc.options.keySneak.isPressed() || lIIIlllllIIllI.dipIntoWater.get() != false && lIIIlllllIIllI.mc.player.fallDistance > (float)lIIIlllllIIllI.dipIntoWaterHeight.get().intValue() || lIIIlllllIIllI.dipIntoWaterIfBurning.get() != false && lIIIlllllIIllI.mc.player.isOnFire() || EntityUtils.getGameMode((PlayerEntity)lIIIlllllIIllI.mc.player) == GameMode.SPECTATOR || lIIIlllllIIllI.mc.player.abilities.flying);
    }

    private boolean lavaIsSafe() {
        Jesus lIIIlllllIIIll;
        if (!lIIIlllllIIIll.dipIntoLavaIfFireResistance.get().booleanValue()) {
            return false;
        }
        return lIIIlllllIIIll.mc.player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && (lIIIlllllIIIll.fireResistanceSafeMode.get() == false || lIIIlllllIIIll.mc.player.getStatusEffect(StatusEffects.FIRE_RESISTANCE).getDuration() > ProtectionEnchantment.transformFireDuration((LivingEntity)lIIIlllllIIIll.mc.player, (int)300));
    }

    @EventHandler
    private void onCanWalkOnFluid(CanWalkOnFluidEvent lIIlIIIIIIIllI) {
        Jesus lIIlIIIIIIIlll;
        if (lIIlIIIIIIIllI.entity != lIIlIIIIIIIlll.mc.player) {
            return;
        }
        if ((lIIlIIIIIIIllI.fluid == Fluids.WATER || lIIlIIIIIIIllI.fluid == Fluids.FLOWING_WATER) && lIIlIIIIIIIlll.waterShouldBeSolid()) {
            lIIlIIIIIIIllI.walkOnFluid = true;
        } else if ((lIIlIIIIIIIllI.fluid == Fluids.LAVA || lIIlIIIIIIIllI.fluid == Fluids.FLOWING_LAVA) && lIIlIIIIIIIlll.lavaShouldBeSolid()) {
            lIIlIIIIIIIllI.walkOnFluid = true;
        }
    }

    private boolean lavaShouldBeSolid() {
        Jesus lIIIllllIlllll;
        return !(lIIIllllIlllll.walkOnLava.get() == false || (lIIIllllIlllll.disableOnSneakForLava.get() != false || lIIIllllIlllll.lavaIsSafe()) && lIIIllllIlllll.mc.options.keySneak.isPressed() || lIIIllllIlllll.dipIntoLava.get() != false && lIIIllllIlllll.mc.player.fallDistance > (float)lIIIllllIlllll.dipIntoLavaHeight.get().intValue() || lIIIllllIlllll.lavaIsSafe() && lIIIllllIlllll.mc.player.fallDistance > 3.0f || EntityUtils.getGameMode((PlayerEntity)lIIIllllIlllll.mc.player) == GameMode.SPECTATOR || lIIIllllIlllll.mc.player.abilities.flying);
    }

    @EventHandler
    private void onFluidCollisionShape(FluidCollisionShapeEvent lIIlIIIIIIIIII) {
        Jesus lIIlIIIIIIIIIl;
        if (lIIlIIIIIIIIII.state.getMaterial() == Material.WATER && !lIIlIIIIIIIIIl.mc.player.isTouchingWater() && lIIlIIIIIIIIIl.waterShouldBeSolid()) {
            lIIlIIIIIIIIII.shape = VoxelShapes.fullCube();
        } else if (lIIlIIIIIIIIII.state.getMaterial() == Material.LAVA && !lIIlIIIIIIIIIl.mc.player.isInLava() && lIIlIIIIIIIIIl.lavaShouldBeSolid()) {
            lIIlIIIIIIIIII.shape = VoxelShapes.fullCube();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post lIIlIIIIIIllIl) {
        Jesus lIIlIIIIIIlllI;
        if (lIIlIIIIIIlllI.mc.player.isTouchingWater() && !lIIlIIIIIIlllI.waterShouldBeSolid()) {
            return;
        }
        if (lIIlIIIIIIlllI.mc.player.isInLava() && !lIIlIIIIIIlllI.lavaShouldBeSolid()) {
            return;
        }
        if (lIIlIIIIIIlllI.mc.player.isTouchingWater() || lIIlIIIIIIlllI.mc.player.isInLava()) {
            Vec3d lIIlIIIIIIllll = lIIlIIIIIIlllI.mc.player.getVelocity();
            ((IVec3d)lIIlIIIIIIllll).set(lIIlIIIIIIllll.x, 0.11, lIIlIIIIIIllll.z);
            lIIlIIIIIIlllI.tickTimer = 0;
            return;
        }
        Vec3d lIIlIIIIIIllII = lIIlIIIIIIlllI.mc.player.getVelocity();
        if (lIIlIIIIIIlllI.tickTimer == 0) {
            ((IVec3d)lIIlIIIIIIllII).set(lIIlIIIIIIllII.x, 0.3, lIIlIIIIIIllII.z);
        } else if (lIIlIIIIIIlllI.tickTimer == 1) {
            ((IVec3d)lIIlIIIIIIllII).set(lIIlIIIIIIllII.x, 0.0, lIIlIIIIIIllII.z);
        }
        ++lIIlIIIIIIlllI.tickTimer;
    }

    @Override
    public void onActivate() {
        Jesus lIIlIIIIIlIlIl;
        lIIlIIIIIlIlIl.preBaritoneAssumeWalkOnWater = (Boolean)BaritoneAPI.getSettings().assumeWalkOnWater.value;
        lIIlIIIIIlIlIl.preBaritoneAssumeWalkOnLava = (Boolean)BaritoneAPI.getSettings().assumeWalkOnLava.value;
        BaritoneAPI.getSettings().assumeWalkOnWater.value = lIIlIIIIIlIlIl.walkOnWater.get();
        BaritoneAPI.getSettings().assumeWalkOnLava.value = lIIlIIIIIlIlIl.walkOnLava.get();
    }
}

