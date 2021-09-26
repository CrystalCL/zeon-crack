/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.player.PlaceBlockEvent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockItem.class})
public class BlockItemMixin {
    @Inject(method={"place(Lnet/minecraft/item/ItemPlacementContext;Lnet/minecraft/block/BlockState;)Z"}, at={@At(value="HEAD")})
    private void onPlace(ItemPlacementContext ItemPlacementContext2, BlockState BlockState2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        MeteorClient.EVENT_BUS.post(PlaceBlockEvent.get(ItemPlacementContext2.getBlockPos(), BlockState2.getBlock()));
    }
}

