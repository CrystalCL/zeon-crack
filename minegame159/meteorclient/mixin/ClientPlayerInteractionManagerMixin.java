/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.util.ActionResult
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.world.World
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.client.network.ClientPlayerInteractionManager
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
import minegame159.meteorclient.events.entity.player.AttackEntityEvent;
import minegame159.meteorclient.events.entity.player.BreakBlockEvent;
import minegame159.meteorclient.events.entity.player.InteractItemEvent;
import minegame159.meteorclient.events.entity.player.StartBreakingBlockEvent;
import minegame159.meteorclient.mixininterface.IClientPlayerInteractionManager;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.NoBreakDelay;
import minegame159.meteorclient.systems.modules.player.Reach;
import minegame159.meteorclient.systems.modules.world.Nuker;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ClientPlayerInteractionManager.class})
public abstract class ClientPlayerInteractionManagerMixin
implements IClientPlayerInteractionManager {
    @Shadow
    private int blockBreakingCooldown;

    @Shadow
    protected abstract void syncSelectedSlot();

    @Inject(method={"attackEntity"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo info) {
        AttackEntityEvent event = MeteorClient.EVENT_BUS.post(AttackEntityEvent.get(target));
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method={"attackBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAttackBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> info) {
        StartBreakingBlockEvent event = MeteorClient.EVENT_BUS.post(StartBreakingBlockEvent.get(blockPos, direction));
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method={"getReachDistance"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> info) {
        if (Modules.get().isActive(Reach.class)) {
            info.setReturnValue((Object)Float.valueOf(Modules.get().get(Reach.class).getReach()));
        }
    }

    @Redirect(method={"updateBlockBreakingProgress"}, at=@At(value="FIELD", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode=181))
    private void onMethod_2902SetField_3716Proxy(ClientPlayerInteractionManager interactionManager, int value) {
        if (Modules.get().isActive(Nuker.class)) {
            value = 0;
        }
        if (Modules.get().isActive(NoBreakDelay.class)) {
            value = 0;
        }
        this.blockBreakingCooldown = value;
    }

    @Redirect(method={"attackBlock"}, at=@At(value="FIELD", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode=181))
    private void onAttackBlockSetField_3719Proxy(ClientPlayerInteractionManager interactionManager, int value) {
        if (Modules.get().isActive(Nuker.class)) {
            value = 0;
        }
        this.blockBreakingCooldown = value;
    }

    @Inject(method={"breakBlock"}, at={@At(value="HEAD")})
    private void onBreakBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> info) {
        MeteorClient.EVENT_BUS.post(BreakBlockEvent.get(blockPos));
    }

    @Inject(method={"interactItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void onInteractItem(PlayerEntity player, World world, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        InteractItemEvent event = MeteorClient.EVENT_BUS.post(InteractItemEvent.get(hand));
        if (event.toReturn != null) {
            info.setReturnValue((Object)event.toReturn);
        }
    }

    @Override
    public void syncSelectedSlot2() {
        this.syncSelectedSlot();
    }
}

