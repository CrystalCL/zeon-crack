/*
 * Decompiled with CFR 0.151.
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
    private float modifySwing(float f) {
        HandView handView = Modules.get().get(HandView.class);
        MinecraftClient MinecraftClient2 = Utils.mc;
        Hand Hand2 = (Hand)MoreObjects.firstNonNull((Object)MinecraftClient2.player.preferredHand, (Object)Hand.MAIN_HAND);
        if (handView.isActive()) {
            if (Hand2 == Hand.OFF_HAND && !MinecraftClient2.player.getOffHandStack().isEmpty()) {
                return f + handView.offSwing.get().floatValue();
            }
            if (Hand2 == Hand.MAIN_HAND && !MinecraftClient2.player.getMainHandStack().isEmpty()) {
                return f + handView.mainSwing.get().floatValue();
            }
        }
        return f;
    }

    @Inject(method={"renderFirstPersonItem"}, at={@At(value="TAIL", target="Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")})
    private void sex(AbstractClientPlayerEntity AbstractClientPlayerEntity2, float f, float f2, Hand Hand2, float f3, ItemStack ItemStack2, float f4, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, CallbackInfo callbackInfo) {
        HandView handView = Modules.get().get(HandView.class);
        if (!handView.isActive()) {
            return;
        }
        GlStateManager.scaled((double)handView.scaleX.get(), (double)handView.scaleY.get(), (double)handView.scaleZ.get());
        GlStateManager.translated((double)handView.posX.get(), (double)handView.posY.get(), (double)handView.posZ.get());
        GlStateManager.rotatef((float)(handView.rotationY.get().floatValue() * 360.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotatef((float)(-(handView.rotationX.get().floatValue() * 360.0f)), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotatef((float)(handView.rotationZ.get().floatValue() * 360.0f), (float)0.0f, (float)0.0f, (float)1.0f);
    }
}

