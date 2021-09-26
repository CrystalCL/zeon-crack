/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.Ambience;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.LightningEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LightningEntityRenderer.class})
public class LightningEntityRendererMixin {
    @Inject(method={"method_23183"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onSetLightningVertex(Matrix4f Matrix4f2, VertexConsumer VertexConsumer2, float f, float f2, int n, float f3, float f4, float f5, float f6, float f7, float f8, float f9, boolean bl, boolean bl2, boolean bl3, boolean bl4, CallbackInfo callbackInfo) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeLightningColor.get().booleanValue()) {
            Color color = ambience.lightningColor.get();
            VertexConsumer2.vertex(Matrix4f2, f + (bl ? f9 : -f9), (float)(n * 16), f2 + (bl2 ? f9 : -f9)).color((float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, 0.3f).next();
            VertexConsumer2.vertex(Matrix4f2, f3 + (bl ? f8 : -f8), (float)((n + 1) * 16), f4 + (bl2 ? f8 : -f8)).color((float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, 0.3f).next();
            VertexConsumer2.vertex(Matrix4f2, f3 + (bl3 ? f8 : -f8), (float)((n + 1) * 16), f4 + (bl4 ? f8 : -f8)).color((float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, 0.3f).next();
            VertexConsumer2.vertex(Matrix4f2, f + (bl3 ? f9 : -f9), (float)(n * 16), f2 + (bl4 ? f9 : -f9)).color((float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, 0.3f).next();
            callbackInfo.cancel();
        }
    }
}

