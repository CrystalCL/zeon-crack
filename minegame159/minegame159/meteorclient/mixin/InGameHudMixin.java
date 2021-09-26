/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.render.Render2DEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.NoRender;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={InGameHud.class})
public abstract class InGameHudMixin {
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    public abstract void clear();

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerInventory;getArmorStack(I)Lnet/minecraft/item/ItemStack;")})
    private void onRender(MatrixStack MatrixStack2, float f, CallbackInfo callbackInfo) {
        this.client.getProfiler().push("meteor-client_render_2d");
        RenderSystem.pushMatrix();
        Utils.unscaledProjection();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
        RenderSystem.lineWidth((float)1.0f);
        GL11.glEnable((int)2848);
        MeteorClient.EVENT_BUS.post(Render2DEvent.get(this.scaledWidth, this.scaledHeight, f));
        GL11.glDisable((int)2848);
        RenderSystem.lineWidth((float)1.0f);
        Utils.scaledProjection();
        RenderSystem.popMatrix();
        this.client.getProfiler().pop();
    }

    @Inject(method={"renderStatusEffectOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderStatusEffectOverlay(CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noPotionIcons()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderPortalOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderPortalOverlay(float f, CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noPortalOverlay()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderPumpkinOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderPumpkinOverlay(CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noPumpkinOverlay()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderVignetteOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderVignetteOverlay(Entity Entity2, CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noVignette()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderScoreboardSidebar"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderScoreboardSidebar(MatrixStack MatrixStack2, ScoreboardObjective ScoreboardObjective2, CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noScoreboard()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderCrosshair"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderCrosshair(MatrixStack MatrixStack2, CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noCrosshair()) {
            callbackInfo.cancel();
        }
    }
}

