/*
 * Decompiled with CFR 0.151.
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
    private void onAttackEntity(PlayerEntity PlayerEntity2, Entity Entity2, CallbackInfo callbackInfo) {
        AttackEntityEvent attackEntityEvent = MeteorClient.EVENT_BUS.post(AttackEntityEvent.get(Entity2));
        if (attackEntityEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"attackBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAttackBlock(BlockPos BlockPos2, Direction Direction2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        StartBreakingBlockEvent startBreakingBlockEvent = MeteorClient.EVENT_BUS.post(StartBreakingBlockEvent.get(BlockPos2, Direction2));
        if (startBreakingBlockEvent.isCancelled()) {
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method={"getReachDistance"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (Modules.get().isActive(Reach.class)) {
            callbackInfoReturnable.setReturnValue((Object)Float.valueOf(Modules.get().get(Reach.class).getReach()));
        }
    }

    @Redirect(method={"updateBlockBreakingProgress"}, at=@At(value="FIELD", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode=181))
    private void onMethod_2902SetField_3716Proxy(ClientPlayerInteractionManager ClientPlayerInteractionManager2, int n) {
        if (Modules.get().isActive(Nuker.class)) {
            n = 0;
        }
        if (Modules.get().isActive(NoBreakDelay.class)) {
            n = 0;
        }
        this.blockBreakingCooldown = n;
    }

    @Redirect(method={"attackBlock"}, at=@At(value="FIELD", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode=181))
    private void onAttackBlockSetField_3719Proxy(ClientPlayerInteractionManager ClientPlayerInteractionManager2, int n) {
        if (Modules.get().isActive(Nuker.class)) {
            n = 0;
        }
        this.blockBreakingCooldown = n;
    }

    @Inject(method={"breakBlock"}, at={@At(value="HEAD")})
    private void onBreakBlock(BlockPos BlockPos2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        MeteorClient.EVENT_BUS.post(BreakBlockEvent.get(BlockPos2));
    }

    @Inject(method={"interactItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void onInteractItem(PlayerEntity PlayerEntity2, World World2, Hand Hand2, CallbackInfoReturnable<ActionResult> callbackInfoReturnable) {
        InteractItemEvent interactItemEvent = MeteorClient.EVENT_BUS.post(InteractItemEvent.get(Hand2));
        if (interactItemEvent.toReturn != null) {
            callbackInfoReturnable.setReturnValue((Object)interactItemEvent.toReturn);
        }
    }

    @Override
    public void syncSelectedSlot2() {
        this.syncSelectedSlot();
    }
}

