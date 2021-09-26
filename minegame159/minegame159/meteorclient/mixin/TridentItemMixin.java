/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={TridentItem.class})
public class TridentItemMixin {
    @Inject(method={"onStoppedUsing"}, at={@At(value="HEAD")})
    private void onStoppedUsingHead(ItemStack ItemStack2, World World2, LivingEntity LivingEntity2, int n, CallbackInfo callbackInfo) {
        if (LivingEntity2 == MinecraftClient.getInstance().player) {
            Utils.isReleasingTrident = true;
        }
    }

    @Inject(method={"onStoppedUsing"}, at={@At(value="TAIL")})
    private void onStoppedUsingTail(ItemStack ItemStack2, World World2, LivingEntity LivingEntity2, int n, CallbackInfo callbackInfo) {
        if (LivingEntity2 == MinecraftClient.getInstance().player) {
            Utils.isReleasingTrident = false;
        }
    }
}

