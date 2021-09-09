/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.ItemPlacementContext
 *  net.minecraft.block.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
    private void onPlace(ItemPlacementContext context, BlockState state, CallbackInfoReturnable<Boolean> info) {
        MeteorClient.EVENT_BUS.post(PlaceBlockEvent.get(context.getBlockPos(), state.getBlock()));
    }
}

