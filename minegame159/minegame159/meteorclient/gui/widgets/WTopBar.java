/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.Tabs;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WPressable;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

public abstract class WTopBar
extends WHorizontalList {
    @Override
    public void init() {
        for (Tab tab : Tabs.get()) {
            this.add(new WTopBarButton(this, tab));
        }
    }

    public WTopBar() {
        this.spacing = 0.0;
    }

    protected abstract Color getButtonColor(boolean var1, boolean var2);

    protected abstract Color getNameColor();

    protected class WTopBarButton
    extends WPressable {
        private final Tab tab;
        final WTopBar this$0;

        @Override
        protected void onPressed(int n) {
            Screen Screen2 = Utils.mc.currentScreen;
            if (!(Screen2 instanceof TabScreen) || ((TabScreen)Screen2).tab != this.tab) {
                double d = Utils.mc.mouse.getX();
                double d2 = Utils.mc.mouse.getY();
                this.tab.openScreen(this.theme);
                GLFW.glfwSetCursorPos((long)Utils.mc.getWindow().getHandle(), (double)d, (double)d2);
            }
        }

        public WTopBarButton(WTopBar wTopBar, Tab tab) {
            this.this$0 = wTopBar;
            this.tab = tab;
        }

        @Override
        protected void onCalculateSize() {
            double d = this.pad();
            this.width = d + this.theme.textWidth(this.tab.name) + d;
            this.height = d + this.theme.textHeight() + d;
        }

        @Override
        protected void onRender(GuiRenderer guiRenderer, double d, double d2, double d3) {
            double d4 = this.pad();
            Color color = this.this$0.getButtonColor(this.pressed || Utils.mc.currentScreen instanceof TabScreen && ((TabScreen)Utils.mc.currentScreen).tab == this.tab, this.mouseOver);
            guiRenderer.quad(this.x, this.y, this.width, this.height, color);
            guiRenderer.text(this.tab.name, this.x + d4, this.y + d4, this.this$0.getNameColor(), false);
        }
    }
}

