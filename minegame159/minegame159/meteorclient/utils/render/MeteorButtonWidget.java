/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_3532;
import net.minecraft.class_4264;
import net.minecraft.class_4587;

public class MeteorButtonWidget
extends class_4264 {
    public static final class_2960 BUTTON_TEXTURE = new class_2960("meteor-client", "meteor-button.png");
    protected final PressAction onPress;
    protected final TooltipSupplier tooltipSupplier;
    public static final TooltipSupplier EMPTY = MeteorButtonWidget::lambda$static$0;

    public void method_25359(class_4587 class_45872, int n, int n2, float f) {
        this.customRender(class_45872);
        if (this.method_25367()) {
            this.method_25352(class_45872, n, n2);
        }
    }

    public MeteorButtonWidget(int n, int n2, int n3, int n4, class_2561 class_25612, PressAction pressAction, TooltipSupplier tooltipSupplier) {
        super(n, n2, n3, n4, class_25612);
        this.onPress = pressAction;
        this.tooltipSupplier = tooltipSupplier;
    }

    public void method_25306() {
        this.onPress.onPress(this);
    }

    public void customRender(class_4587 class_45872) {
        class_310 class_3102 = class_310.method_1551();
        class_327 class_3272 = class_3102.field_1772;
        class_3102.method_1531().method_22813(BUTTON_TEXTURE);
        int n = this.field_22763 ? 0xFFFFFF : 0xA0A0A0;
        RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        MeteorButtonWidget.method_25290((class_4587)class_45872, (int)this.field_22760, (int)this.field_22761, (float)0.0f, (float)(this.method_25367() ? 12.0f : 0.0f), (int)this.field_22758, (int)this.field_22759, (int)40, (int)24);
        MeteorButtonWidget.method_27534((class_4587)class_45872, (class_327)class_3272, (class_2561)this.method_25369(), (int)(this.field_22760 + this.field_22758 / 2), (int)(this.field_22761 + (this.field_22759 - 8) / 2), (int)(n | class_3532.method_15386((float)(this.field_22765 * 255.0f)) << 24));
    }

    public MeteorButtonWidget(int n, int n2, int n3, int n4, class_2561 class_25612, PressAction pressAction) {
        this(n, n2, n3, n4, class_25612, pressAction, EMPTY);
    }

    public void method_25352(class_4587 class_45872, int n, int n2) {
        this.tooltipSupplier.onTooltip(this, class_45872, n, n2);
    }

    private static void lambda$static$0(MeteorButtonWidget meteorButtonWidget, class_4587 class_45872, int n, int n2) {
    }

    public static interface TooltipSupplier {
        public void onTooltip(MeteorButtonWidget var1, class_4587 var2, int var3, int var4);
    }

    public static interface PressAction {
        public void onPress(MeteorButtonWidget var1);
    }
}

