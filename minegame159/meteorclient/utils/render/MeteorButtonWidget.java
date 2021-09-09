/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.text.Text
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.font.TextRenderer
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.client.gui.widget.PressableWidget
 *  net.minecraft.client.util.math.MatrixStack
 */
package minegame159.meteorclient.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;

public class MeteorButtonWidget
extends PressableWidget {
    protected final /* synthetic */ PressAction onPress;
    public static final /* synthetic */ TooltipSupplier EMPTY;
    public static final /* synthetic */ Identifier BUTTON_TEXTURE;
    protected final /* synthetic */ TooltipSupplier tooltipSupplier;

    static {
        BUTTON_TEXTURE = new Identifier("meteor-client", "meteor-button.png");
        EMPTY = (lllllllllllllllllIlIllIlllllIlll, lllllllllllllllllIlIllIlllllIlIl, lllllllllllllllllIlIllIlllllIIll, lllllllllllllllllIlIllIlllllIIlI) -> {};
    }

    public void onPress() {
        MeteorButtonWidget lllllllllllllllllIlIlllIIIlIIlII;
        lllllllllllllllllIlIlllIIIlIIlII.onPress.onPress(lllllllllllllllllIlIlllIIIlIIlII);
    }

    public void renderToolTip(MatrixStack lllllllllllllllllIlIllIlllllllIl, int lllllllllllllllllIlIllIlllllllII, int lllllllllllllllllIlIllIllllllIlI) {
        MeteorButtonWidget lllllllllllllllllIlIlllIIIIIIIlI;
        lllllllllllllllllIlIlllIIIIIIIlI.tooltipSupplier.onTooltip(lllllllllllllllllIlIlllIIIIIIIlI, lllllllllllllllllIlIllIlllllllIl, lllllllllllllllllIlIllIlllllllII, lllllllllllllllllIlIllIllllllIlI);
    }

    public MeteorButtonWidget(int lllllllllllllllllIlIlllIIlIIlIll, int lllllllllllllllllIlIlllIIlIIlIlI, int lllllllllllllllllIlIlllIIlIIIIlI, int lllllllllllllllllIlIlllIIlIIIIIl, Text lllllllllllllllllIlIlllIIlIIIIII, PressAction lllllllllllllllllIlIlllIIlIIIllI) {
        lllllllllllllllllIlIlllIIlIIllII(lllllllllllllllllIlIlllIIlIIlIll, lllllllllllllllllIlIlllIIlIIlIlI, lllllllllllllllllIlIlllIIlIIIIlI, lllllllllllllllllIlIlllIIlIIIIIl, lllllllllllllllllIlIlllIIlIIIIII, lllllllllllllllllIlIlllIIlIIIllI, EMPTY);
        MeteorButtonWidget lllllllllllllllllIlIlllIIlIIllII;
    }

    public void customRender(MatrixStack lllllllllllllllllIlIlllIIIIIlIll) {
        MeteorButtonWidget lllllllllllllllllIlIlllIIIIIllII;
        MinecraftClient lllllllllllllllllIlIlllIIIIIllll = MinecraftClient.getInstance();
        TextRenderer lllllllllllllllllIlIlllIIIIIlllI = lllllllllllllllllIlIlllIIIIIllll.textRenderer;
        lllllllllllllllllIlIlllIIIIIllll.getTextureManager().bindTexture(BUTTON_TEXTURE);
        int lllllllllllllllllIlIlllIIIIIllIl = lllllllllllllllllIlIlllIIIIIllII.active ? 0xFFFFFF : 0xA0A0A0;
        RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        MeteorButtonWidget.drawTexture((MatrixStack)lllllllllllllllllIlIlllIIIIIlIll, (int)lllllllllllllllllIlIlllIIIIIllII.x, (int)lllllllllllllllllIlIlllIIIIIllII.y, (float)0.0f, (float)(lllllllllllllllllIlIlllIIIIIllII.isHovered() ? 12.0f : 0.0f), (int)lllllllllllllllllIlIlllIIIIIllII.width, (int)lllllllllllllllllIlIlllIIIIIllII.height, (int)40, (int)24);
        MeteorButtonWidget.drawCenteredText((MatrixStack)lllllllllllllllllIlIlllIIIIIlIll, (TextRenderer)lllllllllllllllllIlIlllIIIIIlllI, (Text)lllllllllllllllllIlIlllIIIIIllII.getMessage(), (int)(lllllllllllllllllIlIlllIIIIIllII.x + lllllllllllllllllIlIlllIIIIIllII.width / 2), (int)(lllllllllllllllllIlIlllIIIIIllII.y + (lllllllllllllllllIlIlllIIIIIllII.height - 8) / 2), (int)(lllllllllllllllllIlIlllIIIIIllIl | MathHelper.ceil((float)(lllllllllllllllllIlIlllIIIIIllII.alpha * 255.0f)) << 24));
    }

    public MeteorButtonWidget(int lllllllllllllllllIlIlllIIIllIlIl, int lllllllllllllllllIlIlllIIIlIllII, int lllllllllllllllllIlIlllIIIllIIll, int lllllllllllllllllIlIlllIIIllIIlI, Text lllllllllllllllllIlIlllIIIlIlIIl, PressAction lllllllllllllllllIlIlllIIIllIIII, TooltipSupplier lllllllllllllllllIlIlllIIIlIIlll) {
        super(lllllllllllllllllIlIlllIIIllIlIl, lllllllllllllllllIlIlllIIIlIllII, lllllllllllllllllIlIlllIIIllIIll, lllllllllllllllllIlIlllIIIllIIlI, lllllllllllllllllIlIlllIIIlIlIIl);
        MeteorButtonWidget lllllllllllllllllIlIlllIIIlIlllI;
        lllllllllllllllllIlIlllIIIlIlllI.onPress = lllllllllllllllllIlIlllIIIllIIII;
        lllllllllllllllllIlIlllIIIlIlllI.tooltipSupplier = lllllllllllllllllIlIlllIIIlIIlll;
    }

    public void renderButton(MatrixStack lllllllllllllllllIlIlllIIIIllIIl, int lllllllllllllllllIlIlllIIIIlllIl, int lllllllllllllllllIlIlllIIIIlllII, float lllllllllllllllllIlIlllIIIIllIll) {
        MeteorButtonWidget lllllllllllllllllIlIlllIIIIllIlI;
        lllllllllllllllllIlIlllIIIIllIlI.customRender(lllllllllllllllllIlIlllIIIIllIIl);
        if (lllllllllllllllllIlIlllIIIIllIlI.isHovered()) {
            lllllllllllllllllIlIlllIIIIllIlI.renderToolTip(lllllllllllllllllIlIlllIIIIllIIl, lllllllllllllllllIlIlllIIIIlllIl, lllllllllllllllllIlIlllIIIIlllII);
        }
    }

    public static interface PressAction {
        public void onPress(MeteorButtonWidget var1);
    }

    public static interface TooltipSupplier {
        public void onTooltip(MeteorButtonWidget var1, MatrixStack var2, int var3, int var4);
    }
}

