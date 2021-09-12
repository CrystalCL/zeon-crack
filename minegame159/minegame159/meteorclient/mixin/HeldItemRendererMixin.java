/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import com.google.common.base.MoreObjects;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.HandView;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_4493;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_759.class})
public abstract class HeldItemRendererMixin {
    @ModifyVariable(method={"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"}, at=@At(value="STORE", ordinal=0), index=6)
    private float modifySwing(float f) {
        HandView handView = Modules.get().get(HandView.class);
        class_310 class_3102 = Utils.mc;
        class_1268 class_12682 = (class_1268)MoreObjects.firstNonNull((Object)class_3102.field_1724.field_6266, (Object)class_1268.field_5808);
        if (handView.isActive()) {
            if (class_12682 == class_1268.field_5810 && !class_3102.field_1724.method_6079().method_7960()) {
                return f + handView.offSwing.get().floatValue();
            }
            if (class_12682 == class_1268.field_5808 && !class_3102.field_1724.method_6047().method_7960()) {
                return f + handView.mainSwing.get().floatValue();
            }
        }
        return f;
    }

    @Inject(method={"renderFirstPersonItem"}, at={@At(value="TAIL", target="Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")})
    private void sex(class_742 class_7422, float f, float f2, class_1268 class_12682, float f3, class_1799 class_17992, float f4, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        HandView handView = Modules.get().get(HandView.class);
        if (!handView.isActive()) {
            return;
        }
        class_4493.method_21937((double)handView.scaleX.get(), (double)handView.scaleY.get(), (double)handView.scaleZ.get());
        class_4493.method_21938((double)handView.posX.get(), (double)handView.posY.get(), (double)handView.posZ.get());
        class_4493.method_21981((float)(handView.rotationY.get().floatValue() * 360.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        class_4493.method_21981((float)(-(handView.rotationX.get().floatValue() * 360.0f)), (float)0.0f, (float)1.0f, (float)0.0f);
        class_4493.method_21981((float)(handView.rotationZ.get().floatValue() * 360.0f), (float)0.0f, (float)0.0f, (float)1.0f);
    }
}

