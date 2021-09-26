/*
 * Decompiled with CFR 0.151.
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
    public static final Identifier BUTTON_TEXTURE = new Identifier("meteor-client", "meteor-button.png");
    protected final PressAction onPress;
    protected final TooltipSupplier tooltipSupplier;
    public static final TooltipSupplier EMPTY = MeteorButtonWidget::lambda$static$0;

    public void renderButton(MatrixStack MatrixStack2, int n, int n2, float f) {
        this.customRender(MatrixStack2);
        if (this.isHovered()) {
            this.renderToolTip(MatrixStack2, n, n2);
        }
    }

    public MeteorButtonWidget(int n, int n2, int n3, int n4, Text Text2, PressAction pressAction, TooltipSupplier tooltipSupplier) {
        super(n, n2, n3, n4, Text2);
        this.onPress = pressAction;
        this.tooltipSupplier = tooltipSupplier;
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    public void customRender(MatrixStack MatrixStack2) {
        MinecraftClient MinecraftClient2 = MinecraftClient.getInstance();
        TextRenderer TextRenderer2 = MinecraftClient2.textRenderer;
        MinecraftClient2.getTextureManager().bindTexture(BUTTON_TEXTURE);
        int n = this.active ? 0xFFFFFF : 0xA0A0A0;
        RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        MeteorButtonWidget.drawTexture((MatrixStack)MatrixStack2, (int)this.x, (int)this.y, (float)0.0f, (float)(this.isHovered() ? 12.0f : 0.0f), (int)this.width, (int)this.height, (int)40, (int)24);
        MeteorButtonWidget.drawCenteredText((MatrixStack)MatrixStack2, (TextRenderer)TextRenderer2, (Text)this.getMessage(), (int)(this.x + this.width / 2), (int)(this.y + (this.height - 8) / 2), (int)(n | MathHelper.ceil((float)(this.alpha * 255.0f)) << 24));
    }

    public MeteorButtonWidget(int n, int n2, int n3, int n4, Text Text2, PressAction pressAction) {
        this(n, n2, n3, n4, Text2, pressAction, EMPTY);
    }

    public void renderToolTip(MatrixStack MatrixStack2, int n, int n2) {
        this.tooltipSupplier.onTooltip(this, MatrixStack2, n, n2);
    }

    private static void lambda$static$0(MeteorButtonWidget meteorButtonWidget, MatrixStack MatrixStack2, int n, int n2) {
    }

    public static interface TooltipSupplier {
        public void onTooltip(MeteorButtonWidget var1, MatrixStack var2, int var3, int var4);
    }

    public static interface PressAction {
        public void onPress(MeteorButtonWidget var1);
    }
}

