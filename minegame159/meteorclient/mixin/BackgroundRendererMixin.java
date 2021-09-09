/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.render.Camera
 *  com.mojang.blaze3d.platform.GlStateManager.FogMode
 *  net.minecraft.client.render.BackgroundRenderer
 *  net.minecraft.client.render.BackgroundRenderer$class_4596
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
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
    private static void onApplyFog(Camera camera, BackgroundRenderer.class_4596 fogType, float viewDistance, boolean thickFog, CallbackInfo info) {
        if ((Modules.get().get(NoRender.class).noFog() || Modules.get().isActive(Xray.class)) && fogType == BackgroundRenderer.class_4596.FOG_TERRAIN) {
            RenderSystem.fogStart((float)(viewDistance * 4.0f));
            RenderSystem.fogEnd((float)(viewDistance * 4.25f));
            RenderSystem.fogMode((FogMode)FogMode.LINEAR);
            RenderSystem.setupNvFogDistance();
        }
    }
}

