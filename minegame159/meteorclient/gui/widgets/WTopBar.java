/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 *  org.lwjgl.glfw.GLFW
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
    protected abstract Color getButtonColor(boolean var1, boolean var2);

    public WTopBar() {
        WTopBar lllllllllllllllllIIIIllIIIIlIlll;
        lllllllllllllllllIIIIllIIIIlIlll.spacing = 0.0;
    }

    @Override
    public void init() {
        for (Tab lllllllllllllllllIIIIllIIIIlIIll : Tabs.get()) {
            WTopBar lllllllllllllllllIIIIllIIIIlIIIl;
            lllllllllllllllllIIIIllIIIIlIIIl.add(lllllllllllllllllIIIIllIIIIlIIIl.new WTopBarButton(lllllllllllllllllIIIIllIIIIlIIll));
        }
    }

    protected abstract Color getNameColor();

    protected class WTopBarButton
    extends WPressable {
        private final /* synthetic */ Tab tab;

        @Override
        protected void onRender(GuiRenderer llllllllllllllllllllllIIIIlIIlII, double llllllllllllllllllllllIIIIlIIIll, double llllllllllllllllllllllIIIIlIIIlI, double llllllllllllllllllllllIIIIlIIIIl) {
            WTopBarButton llllllllllllllllllllllIIIIIllllI;
            double llllllllllllllllllllllIIIIlIIIII = llllllllllllllllllllllIIIIIllllI.pad();
            Color llllllllllllllllllllllIIIIIlllll = llllllllllllllllllllllIIIIIllllI.WTopBar.this.getButtonColor(llllllllllllllllllllllIIIIIllllI.pressed || Utils.mc.currentScreen instanceof TabScreen && ((TabScreen)Utils.mc.currentScreen).tab == llllllllllllllllllllllIIIIIllllI.tab, llllllllllllllllllllllIIIIIllllI.mouseOver);
            llllllllllllllllllllllIIIIlIIlII.quad(llllllllllllllllllllllIIIIIllllI.x, llllllllllllllllllllllIIIIIllllI.y, llllllllllllllllllllllIIIIIllllI.width, llllllllllllllllllllllIIIIIllllI.height, llllllllllllllllllllllIIIIIlllll);
            llllllllllllllllllllllIIIIlIIlII.text(llllllllllllllllllllllIIIIIllllI.tab.name, llllllllllllllllllllllIIIIIllllI.x + llllllllllllllllllllllIIIIlIIIII, llllllllllllllllllllllIIIIIllllI.y + llllllllllllllllllllllIIIIlIIIII, llllllllllllllllllllllIIIIIllllI.WTopBar.this.getNameColor(), false);
        }

        @Override
        protected void onCalculateSize() {
            WTopBarButton llllllllllllllllllllllIIIIlllIlI;
            double llllllllllllllllllllllIIIIlllIIl = llllllllllllllllllllllIIIIlllIlI.pad();
            llllllllllllllllllllllIIIIlllIlI.width = llllllllllllllllllllllIIIIlllIIl + llllllllllllllllllllllIIIIlllIlI.theme.textWidth(llllllllllllllllllllllIIIIlllIlI.tab.name) + llllllllllllllllllllllIIIIlllIIl;
            llllllllllllllllllllllIIIIlllIlI.height = llllllllllllllllllllllIIIIlllIIl + llllllllllllllllllllllIIIIlllIlI.theme.textHeight() + llllllllllllllllllllllIIIIlllIIl;
        }

        @Override
        protected void onPressed(int llllllllllllllllllllllIIIIlIllll) {
            WTopBarButton llllllllllllllllllllllIIIIllIIII;
            Screen llllllllllllllllllllllIIIIlIlllI = Utils.mc.currentScreen;
            if (!(llllllllllllllllllllllIIIIlIlllI instanceof TabScreen) || ((TabScreen)llllllllllllllllllllllIIIIlIlllI).tab != llllllllllllllllllllllIIIIllIIII.tab) {
                double llllllllllllllllllllllIIIIllIIlI = Utils.mc.mouse.getX();
                double llllllllllllllllllllllIIIIllIIIl = Utils.mc.mouse.getY();
                llllllllllllllllllllllIIIIllIIII.tab.openScreen(llllllllllllllllllllllIIIIllIIII.theme);
                GLFW.glfwSetCursorPos((long)Utils.mc.getWindow().getHandle(), (double)llllllllllllllllllllllIIIIllIIlI, (double)llllllllllllllllllllllIIIIllIIIl);
            }
        }

        public WTopBarButton(Tab llllllllllllllllllllllIIIIllllIl) {
            WTopBarButton llllllllllllllllllllllIIIlIIIIlI;
            llllllllllllllllllllllIIIlIIIIlI.tab = llllllllllllllllllllllIIIIllllIl;
        }
    }
}

