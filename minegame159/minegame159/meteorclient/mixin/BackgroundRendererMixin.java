/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.NoRender;
import minegame159.meteorclient.systems.modules.render.Xray;
import net.minecraft.client.render.Camera;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BackgroundRenderer.class})
public class BackgroundRendererMixin {
    @Inject(method={"applyFog"}, at={@At(value="TAIL")}, cancellable=true)
    private static void onApplyFog(Camera Camera2, BackgroundRenderer.class_4596 class_45962, float f, boolean bl, CallbackInfo callbackInfo) {
        if ((Modules.get().get(NoRender.class).noFog() || Modules.get().isActive(Xray.class)) && class_45962 == BackgroundRenderer.class_4596.FOG_TERRAIN) {
            RenderSystem.fogStart((float)(f * 4.0f));
            RenderSystem.fogEnd((float)(f * 4.25f));
            RenderSystem.fogMode((GlStateManager.class_1028)GlStateManager.class_1028.LINEAR);
            RenderSystem.setupNvFogDistance();
        }
    }
}

