/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> disableOnSneakForWater;
    private final Setting<Boolean> dipIntoWaterIfBurning;
    private final Mutable blockPos;
    private final Setting<Boolean> dipIntoLava;
    private final Setting<Integer> dipIntoLavaHeight;
    private final Setting<Boolean> dipIntoLavaIfFireResistance;
    private final Setting<Boolean> disableOnSneakForLava;
    private final Setting<Boolean> fireResistanceSafeMode;
    private boolean preBaritoneAssumeWalkOnWater;
    private final SettingGroup sgLava;
    private final Setting<Boolean> walkOnLava;
    private final Setting<Boolean> walkOnWater;
    private final Setting<Boolean> dipIntoWater;
    private final Setting<Integer> dipIntoWaterHeight;
    private final SettingGroup sgWater;
    private boolean preBaritoneAssumeWalkOnLava;
    private int tickTimer;
    private int packetTimer;

    public Jesus() {
        super(Categories.Movement, "jesus", "Walk on liquids like Jesus.");
        this.sgWater = this.settings.createGroup("Water");
        this.sgLava = this.settings.createGroup("Lava");
        this.walkOnWater = this.sgWater.add(new BoolSetting.Builder().name("walk-on-water").description("Lets you walk on water.").defaultValue(true).build());
        this.disableOnSneakForWater = this.sgWater.add(new BoolSetting.Builder().name("disable-on-sneak-for-water").description("Lets you go under the water when your sneak key is held.").defaultValue(true).build());
        this.dipIntoWater = this.sgWater.add(new BoolSetting.Builder().name("dip-into-water").description("Lets you go under the water when you fall over a certain height.").defaultValue(true).build());
        this.dipIntoWaterHeight = this.sgWater.add(new IntSetting.Builder().name("dip-into-water-height").description("Maximum safe height.").defaultValue(4).min(1).max(255).sliderMin(3).sliderMax(21).build());
        this.dipIntoWaterIfBurning = this.sgWater.add(new BoolSetting.Builder().name("dip-into-water-if-burning").description("Lets you go under the water when you are burning.").defaultValue(true).build());
        this.walkOnLava = this.sgLava.add(new BoolSetting.Builder().name("walk-on-lava").description("Lets you walk on lava.").defaultValue(true).build());
        this.disableOnSneakForLava = this.sgLava.add(new BoolSetting.Builder().name("disable-on-sneak-for-lava").description("Lets you go under the lava when your sneak key is held.").defaultValue(false).build());
        this.dipIntoLava = this.sgLava.add(new BoolSetting.Builder().name("dip-into-lava").description("Lets you go under the lava when you fall over than certain height.").defaultValue(false).build());
        this.dipIntoLavaHeight = this.sgLava.add(new IntSetting.Builder().name("dip-into-lava-height").description("Maximum safe height.").defaultValue(15).min(1).max(255).sliderMin(3).sliderMax(21).build());
        this.dipIntoLavaIfFireResistance = this.sgLava.add(new BoolSetting.Builder().name("dip-if-fire-resistance").description("Lets you go under the lava if you have Fire Resistance effect.").defaultValue(true).build());
        this.fireResistanceSafeMode = this.sgLava.add(new BoolSetting.Builder().name("fire-resistance-safe-mode").description("Prevents being in lava when the Fire Resistance effect is nearly over.").defaultValue(true).build());
        this.blockPos = new Mutable();
        this.tickTimer = 10;
        this.packetTimer = 0;
    }

    private boolean waterShouldBeSolid() {
        return !(this.walkOnWater.get() == false || this.disableOnSneakForWater.get() != false && this.mc.options.keySneak.isPressed() || this.dipIntoWater.get() != false && this.mc.player.fallDistance > (float)this.dipIntoWaterHeight.get().intValue() || this.dipIntoWaterIfBurning.get() != false && this.mc.player.isOnFire() || EntityUtils.getGameMode((PlayerEntity)this.mc.player) == GameMode.SPECTATOR || this.mc.player.abilities.flying);
    }

    private boolean isOverLiquid() {
        boolean bl = false;
        boolean bl2 = false;
        List list = this.mc.world.getBlockCollisions((Entity)this.mc.player, this.mc.player.getBoundingBox().offset(0.0, -0.5, 0.0)).map(VoxelShape::getBoundingBox).collect(Collectors.toCollection(ArrayList::new));
        for (Box Box3 : list) {
            this.blockPos.set(MathHelper.lerp((double)0.5, (double)Box3.minX, (double)Box3.maxX), MathHelper.lerp((double)0.5, (double)Box3.minY, (double)Box3.maxY), MathHelper.lerp((double)0.5, (double)Box3.minZ, (double)Box3.maxZ));
            Material Material2 = this.mc.world.getBlockState((BlockPos)this.blockPos).getMaterial();
            if (Material2 == Material.WATER || Material2 == Material.LAVA) {
                bl = true;
                continue;
            }
            if (Material2 == Material.AIR) continue;
            bl2 = true;
        }
        return bl && !bl2;
    }

    @EventHandler
    private void onFluidCollisionShape(FluidCollisionShapeEvent fluidCollisionShapeEvent) {
        if (fluidCollisionShapeEvent.state.getMaterial() == Material.WATER && !this.mc.player.isTouchingWater() && this.waterShouldBeSolid()) {
            fluidCollisionShapeEvent.shape = VoxelShapes.fullCube();
        } else if (fluidCollisionShapeEvent.state.getMaterial() == Material.LAVA && !this.mc.player.isInLava() && this.lavaShouldBeSolid()) {
            fluidCollisionShapeEvent.shape = VoxelShapes.fullCube();
        }
    }

    private boolean lavaShouldBeSolid() {
        return !(this.walkOnLava.get() == false || (this.disableOnSneakForLava.get() != false || this.lavaIsSafe()) && this.mc.options.keySneak.isPressed() || this.dipIntoLava.get() != false && this.mc.player.fallDistance > (float)this.dipIntoLavaHeight.get().intValue() || this.lavaIsSafe() && this.mc.player.fallDistance > 3.0f || EntityUtils.getGameMode((PlayerEntity)this.mc.player) == GameMode.SPECTATOR || this.mc.player.abilities.flying);
    }

    @EventHandler
    private void onCanWalkOnFluid(CanWalkOnFluidEvent canWalkOnFluidEvent) {
        if (canWalkOnFluidEvent.entity != this.mc.player) {
            return;
        }
        if ((canWalkOnFluidEvent.fluid == Fluids.WATER || canWalkOnFluidEvent.fluid == Fluids.FLOWING_WATER) && this.waterShouldBeSolid()) {
            canWalkOnFluidEvent.walkOnFluid = true;
        } else if ((canWalkOnFluidEvent.fluid == Fluids.LAVA || canWalkOnFluidEvent.fluid == Fluids.FLOWING_LAVA) && this.lavaShouldBeSolid()) {
            canWalkOnFluidEvent.walkOnFluid = true;
        }
    }

    @Override
    public void onActivate() {
        this.preBaritoneAssumeWalkOnWater = (Boolean)BaritoneAPI.getSettings().assumeWalkOnWater.value;
        this.preBaritoneAssumeWalkOnLava = (Boolean)BaritoneAPI.getSettings().assumeWalkOnLava.value;
        BaritoneAPI.getSettings().assumeWalkOnWater.value = this.walkOnWater.get();
        BaritoneAPI.getSettings().assumeWalkOnLava.value = this.walkOnLava.get();
    }

    private boolean lavaIsSafe() {
        if (!this.dipIntoLavaIfFireResistance.get().booleanValue()) {
            return false;
        }
        return this.mc.player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && (this.fireResistanceSafeMode.get() == false || this.mc.player.getStatusEffect(StatusEffects.FIRE_RESISTANCE).getDuration() > ProtectionEnchantment.transformFireDuration((LivingEntity)this.mc.player, (int)300));
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (!(send.packet instanceof PlayerMoveC2SPacket)) {
            return;
        }
        if (this.mc.player.isTouchingWater() && !this.waterShouldBeSolid()) {
            return;
        }
        if (this.mc.player.isInLava() && !this.lavaShouldBeSolid()) {
            return;
        }
        PlayerMoveC2SPacket PlayerMoveC2SPacket2 = (PlayerMoveC2SPacket)send.packet;
        if (!(PlayerMoveC2SPacket2 instanceof PlayerMoveC2SPacket.class_2829) && !(PlayerMoveC2SPacket2 instanceof PlayerMoveC2SPacket.class_2830)) {
            return;
        }
        if (this.mc.player.isTouchingWater() || this.mc.player.isInLava() || this.mc.player.fallDistance > 3.0f || !this.isOverLiquid()) {
            return;
        }
        if (this.mc.player.input.movementForward == 0.0f && this.mc.player.input.movementSideways == 0.0f) {
            send.cancel();
            return;
        }
        if (this.packetTimer++ < 4) {
            return;
        }
        this.packetTimer = 0;
        send.cancel();
        double d = PlayerMoveC2SPacket2.getX(0.0);
        double d2 = PlayerMoveC2SPacket2.getY(0.0) + 0.05;
        double d3 = PlayerMoveC2SPacket2.getZ(0.0);
        Object object = PlayerMoveC2SPacket2 instanceof PlayerMoveC2SPacket.class_2829 ? new PlayerMoveC2SPacket.class_2829(d, d2, d3, true) : new PlayerMoveC2SPacket.class_2830(d, d2, d3, PlayerMoveC2SPacket2.getYaw(0.0f), PlayerMoveC2SPacket2.getPitch(0.0f), true);
        this.mc.getNetworkHandler().getConnection().send((VoxelShapes6)object);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.player.isTouchingWater() && !this.waterShouldBeSolid()) {
            return;
        }
        if (this.mc.player.isInLava() && !this.lavaShouldBeSolid()) {
            return;
        }
        if (this.mc.player.isTouchingWater() || this.mc.player.isInLava()) {
            Vec3d Vec3d2 = this.mc.player.getVelocity();
            ((IVec3d)Vec3d2).set(Vec3d2.x, 0.11, Vec3d2.z);
            this.tickTimer = 0;
            return;
        }
        Vec3d Vec3d3 = this.mc.player.getVelocity();
        if (this.tickTimer == 0) {
            ((IVec3d)Vec3d3).set(Vec3d3.x, 0.3, Vec3d3.z);
        } else if (this.tickTimer == 1) {
            ((IVec3d)Vec3d3).set(Vec3d3.x, 0.0, Vec3d3.z);
        }
        ++this.tickTimer;
    }

    @Override
    public void onDeactivate() {
        BaritoneAPI.getSettings().assumeWalkOnWater.value = this.preBaritoneAssumeWalkOnWater;
        BaritoneAPI.getSettings().assumeWalkOnLava.value = this.preBaritoneAssumeWalkOnLava;
    }
}

