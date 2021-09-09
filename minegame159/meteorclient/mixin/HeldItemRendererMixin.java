/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  net.minecraft.util.Hand
 *  net.minecraft.item.ItemStack
 *  net.minecraft.client.MinecraftClient
 *  com.mojang.blaze3d.platform.GlStateManager
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  net.minecraft.client.render.item.HeldItemRenderer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import com.google.common.base.MoreObjects;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.HandView;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.util.Hand;
import net.minecraft.item.ItemStack;
import net.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={HeldItemRenderer.class})
public abstract class HeldItemRendererMixin {
    @ModifyVariable(method={"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"}, at=@At(value="STORE", ordinal=0), index=6)
    private float modifySwing(float swingProgress) {
        HandView module = Modules.get().get(HandView.class);
        MinecraftClient mc = Utils.mc;
        Hand hand = (Hand)MoreObjects.firstNonNull((Object)mc.player.preferredHand, (Object)Hand.MAIN_HAND);
        if (module.isActive()) {
            if (hand == Hand.OFF_HAND && !mc.player.getOffHandStack().isEmpty()) {
                return swingProgress + module.offSwing.get().floatValue();
            }
            if (hand == Hand.MAIN_HAND && !mc.player.getMainHandStack().isEmpty()) {
                return swingProgress + module.mainSwing.get().floatValue();
            }
        }
        return swingProgress;
    }

    @Inject(method={"renderFirstPersonItem"}, at={@At(value="TAIL", target="Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")})
    private void sex(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        HandView module = Modules.get().get(HandView.class);
        if (!module.isActive()) {
            return;
        }
        GlStateManager.scaled((double)module.scaleX.get(), (double)module.scaleY.get(), (double)module.scaleZ.get());
        GlStateManager.translated((double)module.posX.get(), (double)module.posY.get(), (double)module.posZ.get());
        GlStateManager.rotatef((float)(module.rotationY.get().floatValue() * 360.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotatef((float)(-(module.rotationX.get().floatValue() * 360.0f)), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotatef((float)(module.rotationZ.get().floatValue() * 360.0f), (float)0.0f, (float)0.0f, (float)1.0f);
    }
}

