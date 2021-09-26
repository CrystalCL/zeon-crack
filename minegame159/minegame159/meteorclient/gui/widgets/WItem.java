/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.widgets.WWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.GlStateManager;

public class WItem
extends WWidget {
    protected ItemStack itemStack;

    @Override
    protected void onCalculateSize() {
        double d;
        this.width = d = this.theme.scale(32.0);
        this.height = d;
    }

    @Override
    protected void onRender(GuiRenderer guiRenderer, double d, double d2, double d3) {
        guiRenderer.post(this::lambda$onRender$0);
    }

    private void lambda$onRender$0() {
        GlStateManager.enableTexture();
        DiffuseLighting.enable();
        GlStateManager.enableDepthTest();
        double d = this.theme.scale(2.0);
        GlStateManager.pushMatrix();
        GlStateManager.scaled((double)d, (double)d, (double)1.0);
        GlStateManager.translated((double)(this.x / d), (double)(this.y / d), (double)0.0);
        MinecraftClient.getInstance().getItemRenderer().renderGuiItemIcon(this.itemStack, 0, 0);
        GlStateManager.popMatrix();
    }

    public WItem(ItemStack ItemStack2) {
        this.itemStack = ItemStack2;
    }

    public void set(ItemStack ItemStack2) {
        this.itemStack = ItemStack2;
    }
}

