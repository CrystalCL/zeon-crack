/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.DropItemsEvent;
import minegame159.meteorclient.events.entity.player.ClipAtLedgeEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.Anchor;
import minegame159.meteorclient.systems.modules.player.SpeedMine;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerEntity.class})
public class PlayerEntityMixin {
    @Inject(method={"clipAtLedge"}, at={@At(value="HEAD")}, cancellable=true)
    protected void clipAtLedge(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        ClipAtLedgeEvent clipAtLedgeEvent = MeteorClient.EVENT_BUS.post(ClipAtLedgeEvent.get());
        if (clipAtLedgeEvent.isSet()) {
            callbackInfoReturnable.setReturnValue((Object)clipAtLedgeEvent.isClip());
        }
    }

    @Inject(method={"dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;"}, at={@At(value="HEAD")})
    private void onDropItem(ItemStack ItemStack2, boolean bl, boolean bl2, CallbackInfoReturnable<ItemEntity> callbackInfoReturnable) {
        MeteorClient.EVENT_BUS.post(DropItemsEvent.get(ItemStack2));
    }

    @Inject(method={"getBlockBreakingSpeed"}, at={@At(value="RETURN")}, cancellable=true)
    public void onGetBlockBreakingSpeed(BlockState BlockState2, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        SpeedMine speedMine = Modules.get().get(SpeedMine.class);
        if (!speedMine.isActive() || speedMine.mode.get() != SpeedMine.Mode.Normal) {
            return;
        }
        callbackInfoReturnable.setReturnValue((Object)Float.valueOf((float)((double)((Float)callbackInfoReturnable.getReturnValue()).floatValue() * speedMine.modifier.get())));
    }

    @Inject(method={"jump"}, at={@At(value="HEAD")}, cancellable=true)
    public void dontJump(CallbackInfo callbackInfo) {
        Anchor anchor = Modules.get().get(Anchor.class);
        if (anchor.isActive() && anchor.cancelJump) {
            callbackInfo.cancel();
        }
    }
}

