/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.client.render.DiffuseLighting
 *  net.minecraft.client.MinecraftClient
 *  com.mojang.blaze3d.platform.GlStateManager
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
    protected /* synthetic */ ItemStack itemStack;

    @Override
    protected void onCalculateSize() {
        WItem lIIlIlIIllllIl;
        double lIIlIlIIllllII;
        lIIlIlIIllllIl.width = lIIlIlIIllllII = lIIlIlIIllllIl.theme.scale(32.0);
        lIIlIlIIllllIl.height = lIIlIlIIllllII;
    }

    public WItem(ItemStack lIIlIlIlIIIIlI) {
        WItem lIIlIlIlIIIIll;
        lIIlIlIlIIIIll.itemStack = lIIlIlIlIIIIlI;
    }

    public void set(ItemStack lIIlIlIIlIlIll) {
        lIIlIlIIlIlllI.itemStack = lIIlIlIIlIlIll;
    }

    @Override
    protected void onRender(GuiRenderer lIIlIlIIllIIIl, double lIIlIlIIllIlIl, double lIIlIlIIllIlII, double lIIlIlIIllIIll) {
        WItem lIIlIlIIllIIlI;
        lIIlIlIIllIIIl.post(() -> {
            WItem lIIlIlIIlIlIII;
            GlStateManager.enableTexture();
            DiffuseLighting.enable();
            GlStateManager.enableDepthTest();
            double lIIlIlIIlIIlll = lIIlIlIIlIlIII.theme.scale(2.0);
            GlStateManager.pushMatrix();
            GlStateManager.scaled((double)lIIlIlIIlIIlll, (double)lIIlIlIIlIIlll, (double)1.0);
            GlStateManager.translated((double)(lIIlIlIIlIlIII.x / lIIlIlIIlIIlll), (double)(lIIlIlIIlIlIII.y / lIIlIlIIlIIlll), (double)0.0);
            MinecraftClient.getInstance().getItemRenderer().renderGuiItemIcon(lIIlIlIIlIlIII.itemStack, 0, 0);
            GlStateManager.popMatrix();
        });
    }
}

