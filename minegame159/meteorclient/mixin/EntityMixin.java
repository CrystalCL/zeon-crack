/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.MovementType
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.world.BlockView
 *  net.minecraft.world.World
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.fluid.FluidState
 *  net.minecraft.client.network.ClientPlayerEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.LivingEntityMoveEvent;
import minegame159.meteorclient.events.entity.player.JumpVelocityMultiplierEvent;
import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.Hitboxes;
import minegame159.meteorclient.systems.modules.movement.NoSlow;
import minegame159.meteorclient.systems.modules.movement.Velocity;
import minegame159.meteorclient.systems.modules.render.ESP;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.Outlines;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Entity.class})
public abstract class EntityMixin {
    @Shadow
    public World world;

    @Shadow
    public abstract BlockPos getBlockPos();

    @Shadow
    protected abstract BlockPos getVelocityAffectingPos();

    @Shadow
    public abstract void setVelocity(double var1, double var3, double var5);

    @Inject(method={"setVelocityClient"}, at={@At(value="HEAD")}, cancellable=true)
    private void onSetVelocityClient(double x, double y, double z, CallbackInfo info) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (this != player) {
            return;
        }
        Velocity velocity = Modules.get().get(Velocity.class);
        if (!velocity.isActive() || !velocity.entities.get().booleanValue()) {
            return;
        }
        double deltaX = x - player.getVelocity().x;
        double deltaY = y - player.getVelocity().y;
        double deltaZ = z - player.getVelocity().z;
        this.setVelocity(player.getVelocity().x + deltaX * velocity.getHorizontal(), player.getVelocity().y + deltaY * velocity.getVertical(), player.getVelocity().z + deltaZ * velocity.getHorizontal());
        info.cancel();
    }

    @Redirect(method={"updateMovementInFluid"}, at=@At(value="INVOKE", target="Lnet/minecraft/fluid/FluidState;getVelocity(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d updateMovementInFluidFluidStateGetVelocity(FluidState state, BlockView world, BlockPos pos) {
        Vec3d vec = state.getVelocity(world, pos);
        Velocity velocity = Modules.get().get(Velocity.class);
        if (velocity.isActive() && velocity.liquids.get().booleanValue()) {
            vec = vec.multiply(velocity.getHorizontal(), velocity.getVertical(), velocity.getHorizontal());
        }
        return vec;
    }

    @Inject(method={"getJumpVelocityMultiplier"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetJumpVelocityMultiplier(CallbackInfoReturnable<Float> info) {
        if (this == MinecraftClient.getInstance().player) {
            float f = this.world.getBlockState(this.getBlockPos()).getBlock().getJumpVelocityMultiplier();
            float g = this.world.getBlockState(this.getVelocityAffectingPos()).getBlock().getJumpVelocityMultiplier();
            float a = (double)f == 1.0 ? g : f;
            JumpVelocityMultiplierEvent event = MeteorClient.EVENT_BUS.post(JumpVelocityMultiplierEvent.get());
            info.setReturnValue((Object)Float.valueOf(a * event.multiplier));
        }
    }

    @Redirect(method={"addVelocity"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d addVelocityVec3dAddProxy(Vec3d vec3d, double x, double y, double z) {
        Velocity velocity = Modules.get().get(Velocity.class);
        if (this != MinecraftClient.getInstance().player || Utils.isReleasingTrident || !velocity.entities.get().booleanValue()) {
            return vec3d.add(x, y, z);
        }
        return vec3d.add(x * velocity.getHorizontal(), y * velocity.getVertical(), z * velocity.getHorizontal());
    }

    @Inject(method={"move"}, at={@At(value="HEAD")})
    private void onMove(MovementType type, Vec3d movement, CallbackInfo info) {
        if (this == MinecraftClient.getInstance().player) {
            MeteorClient.EVENT_BUS.post(PlayerMoveEvent.get(type, movement));
        } else if (this instanceof LivingEntity) {
            MeteorClient.EVENT_BUS.post(LivingEntityMoveEvent.get((LivingEntity)this, movement));
        }
    }

    @Inject(method={"getTeamColorValue"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetTeamColorValue(CallbackInfoReturnable<Integer> info) {
        if (Outlines.renderingOutlines) {
            info.setReturnValue((Object)Modules.get().get(ESP.class).getColor((Entity)this).getPacked());
        }
    }

    @Redirect(method={"getVelocityMultiplier"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
    private Block getVelocityMultiplierGetBlockProxy(BlockState blockState) {
        if (blockState.getBlock() == Blocks.SOUL_SAND && Modules.get().get(NoSlow.class).soulSand()) {
            return Blocks.STONE;
        }
        return blockState.getBlock();
    }

    @Inject(method={"isInvisibleTo(Lnet/minecraft/entity/player/PlayerEntity;)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void isInvisibleToCanceller(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (player == null) {
            info.setReturnValue((Object)false);
        }
        if (Modules.get().isActive(ESP.class) && Modules.get().get(ESP.class).showInvis.get().booleanValue()) {
            info.setReturnValue((Object)false);
        }
    }

    @Inject(method={"getTargetingMargin"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetTargetingMargin(CallbackInfoReturnable<Float> info) {
        double v = Modules.get().get(Hitboxes.class).getEntityValue((Entity)this);
        if (v != 0.0) {
            info.setReturnValue((Object)Float.valueOf((float)v));
        }
    }
}

