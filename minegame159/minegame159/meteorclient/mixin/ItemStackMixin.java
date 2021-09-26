/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.player.FinishUsingItem;
import minegame159.meteorclient.events.entity.player.StoppedUsingItemEvent;
import minegame159.meteorclient.events.game.GetTooltipEvent;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.world.World;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={ItemStack.class})
public abstract class ItemStackMixin {
    @Inject(method={"getTooltip"}, at={@At(value="TAIL")}, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onGetTooltip(PlayerEntity PlayerEntity2, TooltipContext TooltipContext2, CallbackInfoReturnable<List<Text>> callbackInfoReturnable, List<Text> list) {
        if (Utils.canUpdate()) {
            MeteorClient.EVENT_BUS.post(GetTooltipEvent.Append.get((ItemStack)this, list));
        }
    }

    @Inject(method={"finishUsing"}, at={@At(value="HEAD")})
    private void onFinishUsing(World World2, LivingEntity LivingEntity2, CallbackInfoReturnable<ItemStack> callbackInfoReturnable) {
        if (LivingEntity2 == MinecraftClient.getInstance().player) {
            MeteorClient.EVENT_BUS.post(FinishUsingItem.get((ItemStack)this));
        }
    }

    @Inject(method={"onStoppedUsing"}, at={@At(value="HEAD")})
    private void onStoppedUsing(World World2, LivingEntity LivingEntity2, int n, CallbackInfo callbackInfo) {
        if (LivingEntity2 == MinecraftClient.getInstance().player) {
            MeteorClient.EVENT_BUS.post(StoppedUsingItemEvent.get((ItemStack)this));
        }
    }
}

