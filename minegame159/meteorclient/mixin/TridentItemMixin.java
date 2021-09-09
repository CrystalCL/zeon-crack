/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.TridentItem
 *  net.minecraft.world.World
 *  net.minecraft.client.MinecraftClient
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
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
    private void onStoppedUsingHead(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
        if (user == MinecraftClient.getInstance().player) {
            Utils.isReleasingTrident = true;
        }
    }

    @Inject(method={"onStoppedUsing"}, at={@At(value="TAIL")})
    private void onStoppedUsingTail(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
        if (user == MinecraftClient.getInstance().player) {
            Utils.isReleasingTrident = false;
        }
    }
}

