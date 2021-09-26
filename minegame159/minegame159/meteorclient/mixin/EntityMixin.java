/*
 * Decompiled with CFR 0.151.
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
    private void onSetVelocityClient(double d, double d2, double d3, CallbackInfo callbackInfo) {
        ClientPlayerEntity ClientPlayerEntity2 = MinecraftClient.getInstance().player;
        if (this != ClientPlayerEntity2) {
            return;
        }
        Velocity velocity = Modules.get().get(Velocity.class);
        if (!velocity.isActive() || !velocity.entities.get().booleanValue()) {
            return;
        }
        double d4 = d - ClientPlayerEntity2.getVelocity().x;
        double d5 = d2 - ClientPlayerEntity2.getVelocity().y;
        double d6 = d3 - ClientPlayerEntity2.getVelocity().z;
        this.setVelocity(ClientPlayerEntity2.getVelocity().x + d4 * velocity.getHorizontal(), ClientPlayerEntity2.getVelocity().y + d5 * velocity.getVertical(), ClientPlayerEntity2.getVelocity().z + d6 * velocity.getHorizontal());
        callbackInfo.cancel();
    }

    @Redirect(method={"updateMovementInFluid"}, at=@At(value="INVOKE", target="Lnet/minecraft/fluid/FluidState;getVelocity(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d updateMovementInFluidFluidStateGetVelocity(FluidState FluidState2, BlockView BlockView2, BlockPos BlockPos2) {
        Vec3d Vec3d2 = FluidState2.getVelocity(BlockView2, BlockPos2);
        Velocity velocity = Modules.get().get(Velocity.class);
        if (velocity.isActive() && velocity.liquids.get().booleanValue()) {
            Vec3d2 = Vec3d2.multiply(velocity.getHorizontal(), velocity.getVertical(), velocity.getHorizontal());
        }
        return Vec3d2;
    }

    @Inject(method={"getJumpVelocityMultiplier"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetJumpVelocityMultiplier(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (this == MinecraftClient.getInstance().player) {
            float f = this.world.getBlockState(this.getBlockPos()).getBlock().getJumpVelocityMultiplier();
            float f2 = this.world.getBlockState(this.getVelocityAffectingPos()).getBlock().getJumpVelocityMultiplier();
            float f3 = (double)f == 1.0 ? f2 : f;
            JumpVelocityMultiplierEvent jumpVelocityMultiplierEvent = MeteorClient.EVENT_BUS.post(JumpVelocityMultiplierEvent.get());
            callbackInfoReturnable.setReturnValue((Object)Float.valueOf(f3 * jumpVelocityMultiplierEvent.multiplier));
        }
    }

    @Redirect(method={"addVelocity"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d addVelocityVec3dAddProxy(Vec3d Vec3d2, double d, double d2, double d3) {
        Velocity velocity = Modules.get().get(Velocity.class);
        if (this != MinecraftClient.getInstance().player || Utils.isReleasingTrident || !velocity.entities.get().booleanValue()) {
            return Vec3d2.add(d, d2, d3);
        }
        return Vec3d2.add(d * velocity.getHorizontal(), d2 * velocity.getVertical(), d3 * velocity.getHorizontal());
    }

    @Inject(method={"move"}, at={@At(value="HEAD")})
    private void onMove(MovementType MovementType2, Vec3d Vec3d2, CallbackInfo callbackInfo) {
        if (this == MinecraftClient.getInstance().player) {
            MeteorClient.EVENT_BUS.post(PlayerMoveEvent.get(MovementType2, Vec3d2));
        } else if (this instanceof LivingEntity) {
            MeteorClient.EVENT_BUS.post(LivingEntityMoveEvent.get((LivingEntity)this, Vec3d2));
        }
    }

    @Inject(method={"getTeamColorValue"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetTeamColorValue(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (Outlines.renderingOutlines) {
            callbackInfoReturnable.setReturnValue((Object)Modules.get().get(ESP.class).getColor((Entity)this).getPacked());
        }
    }

    @Redirect(method={"getVelocityMultiplier"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
    private Block getVelocityMultiplierGetBlockProxy(BlockState BlockState2) {
        if (BlockState2.getBlock() == Blocks.SOUL_SAND && Modules.get().get(NoSlow.class).soulSand()) {
            return Blocks.STONE;
        }
        return BlockState2.getBlock();
    }

    @Inject(method={"isInvisibleTo(Lnet/minecraft/entity/player/PlayerEntity;)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void isInvisibleToCanceller(PlayerEntity PlayerEntity2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (PlayerEntity2 == null) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
        if (Modules.get().isActive(ESP.class) && Modules.get().get(ESP.class).showInvis.get().booleanValue()) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }

    @Inject(method={"getTargetingMargin"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetTargetingMargin(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        double d = Modules.get().get(Hitboxes.class).getEntityValue((Entity)this);
        if (d != 0.0) {
            callbackInfoReturnable.setReturnValue((Object)Float.valueOf((float)d));
        }
    }
}

