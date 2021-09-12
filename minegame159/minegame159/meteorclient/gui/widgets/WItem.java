/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.widgets.WWidget;
import net.minecraft.class_1799;
import net.minecraft.class_308;
import net.minecraft.class_310;
import net.minecraft.class_4493;

public class WItem
extends WWidget {
    protected class_1799 itemStack;

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
        class_4493.method_21910();
        class_308.method_22890();
        class_4493.method_22050();
        double d = this.theme.scale(2.0);
        class_4493.method_21926();
        class_4493.method_21937((double)d, (double)d, (double)1.0);
        class_4493.method_21938((double)(this.x / d), (double)(this.y / d), (double)0.0);
        class_310.method_1551().method_1480().method_4010(this.itemStack, 0, 0);
        class_4493.method_21928();
    }

    public WItem(class_1799 class_17992) {
        this.itemStack = class_17992;
    }

    public void set(class_1799 class_17992) {
        this.itemStack = class_17992;
    }
}

