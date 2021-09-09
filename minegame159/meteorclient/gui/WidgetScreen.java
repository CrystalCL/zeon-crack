/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.util.math.MatrixStack
 */
package minegame159.meteorclient.gui;

import java.util.function.Consumer;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.gui.GuiKeyEvents;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.renderer.GuiDebugRenderer;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WRoot;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WContainer;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.rendering.Matrices;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.CursorStyle;
import minegame159.meteorclient.utils.misc.input.Input;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public abstract class WidgetScreen
extends Screen {
    protected /* synthetic */ Screen parent;
    private static final /* synthetic */ GuiRenderer RENDERER;
    public /* synthetic */ Runnable taskAfterRender;
    private /* synthetic */ boolean closed;
    public /* synthetic */ double animProgress;
    public /* synthetic */ boolean locked;
    private /* synthetic */ double lastMouseX;
    private final /* synthetic */ WContainer root;
    private /* synthetic */ double lastMouseY;
    private static final /* synthetic */ GuiDebugRenderer DEBUG_RENDERER;
    private /* synthetic */ boolean onClose;
    protected final /* synthetic */ GuiTheme theme;
    private /* synthetic */ boolean debug;

    protected void onClosed() {
    }

    public <W extends WWidget> Cell<W> add(W lllllllllllllllllllIlIIIllIIIIll) {
        WidgetScreen lllllllllllllllllllIlIIIllIIIllI;
        return lllllllllllllllllllIlIIIllIIIllI.root.add(lllllllllllllllllllIlIIIllIIIIll);
    }

    public void resize(MinecraftClient lllllllllllllllllllIlIIIIIlIlIII, int lllllllllllllllllllIlIIIIIlIlllI, int lllllllllllllllllllIlIIIIIlIIlII) {
        WidgetScreen lllllllllllllllllllIlIIIIIllIIlI;
        super.resize(lllllllllllllllllllIlIIIIIlIlIII, lllllllllllllllllllIlIIIIIlIlllI, lllllllllllllllllllIlIIIIIlIIlII);
        lllllllllllllllllllIlIIIIIllIIlI.root.invalidate();
    }

    static {
        RENDERER = new GuiRenderer();
        DEBUG_RENDERER = new GuiDebugRenderer();
    }

    private void loopWidgets(WWidget lllllllllllllllllllIlIIIIIIlIIlI, Consumer<WWidget> lllllllllllllllllllIlIIIIIIlIIIl) {
        lllllllllllllllllllIlIIIIIIlIIIl.accept(lllllllllllllllllllIlIIIIIIlIIlI);
        if (lllllllllllllllllllIlIIIIIIlIIlI instanceof WContainer) {
            for (Cell<?> lllllllllllllllllllIlIIIIIIlIlII : ((WContainer)lllllllllllllllllllIlIIIIIIlIIlI).cells) {
                WidgetScreen lllllllllllllllllllIlIIIIIIlIIll;
                lllllllllllllllllllIlIIIIIIlIIll.loopWidgets((WWidget)lllllllllllllllllllIlIIIIIIlIlII.widget(), lllllllllllllllllllIlIIIIIIlIIIl);
            }
        }
    }

    public boolean mouseReleased(double lllllllllllllllllllIlIIIlIIlllll, double lllllllllllllllllllIlIIIlIlIIIll, int lllllllllllllllllllIlIIIlIlIIIlI) {
        WidgetScreen lllllllllllllllllllIlIIIlIlIIIII;
        if (lllllllllllllllllllIlIIIlIlIIIII.locked) {
            return false;
        }
        double lllllllllllllllllllIlIIIlIlIIIIl = Utils.mc.getWindow().getScaleFactor();
        return lllllllllllllllllllIlIIIlIlIIIII.root.mouseReleased(lllllllllllllllllllIlIIIlIIlllll *= lllllllllllllllllllIlIIIlIlIIIIl, lllllllllllllllllllIlIIIlIlIIIll *= lllllllllllllllllllIlIIIlIlIIIIl, lllllllllllllllllllIlIIIlIlIIIlI);
    }

    public void onClose() {
        WidgetScreen lllllllllllllllllllIlIIIIIIllllI;
        if (!lllllllllllllllllllIlIIIIIIllllI.locked) {
            boolean lllllllllllllllllllIlIIIIIlIIIII = lllllllllllllllllllIlIIIIIIllllI.onClose;
            lllllllllllllllllllIlIIIIIIllllI.onClose = true;
            lllllllllllllllllllIlIIIIIIllllI.removed();
            lllllllllllllllllllIlIIIIIIllllI.onClose = lllllllllllllllllllIlIIIIIlIIIII;
        }
    }

    public void invalidate() {
        WidgetScreen lllllllllllllllllllIlIIIlIllllIl;
        lllllllllllllllllllIlIIIlIllllIl.root.invalidate();
    }

    public boolean charTyped(char lllllllllllllllllllIlIIIIlIlllll, int lllllllllllllllllllIlIIIIlIllllI) {
        WidgetScreen lllllllllllllllllllIlIIIIlIlllIl;
        if (lllllllllllllllllllIlIIIIlIlllIl.locked) {
            return false;
        }
        return lllllllllllllllllllIlIIIIlIlllIl.root.charTyped(lllllllllllllllllllIlIIIIlIlllll);
    }

    public boolean mouseScrolled(double lllllllllllllllllllIlIIIlIIIIllI, double lllllllllllllllllllIlIIIlIIIlIIl, double lllllllllllllllllllIlIIIlIIIlIII) {
        WidgetScreen lllllllllllllllllllIlIIIlIIIlIll;
        if (lllllllllllllllllllIlIIIlIIIlIll.locked) {
            return false;
        }
        lllllllllllllllllllIlIIIlIIIlIll.root.mouseScrolled(lllllllllllllllllllIlIIIlIIIlIII);
        return super.mouseScrolled(lllllllllllllllllllIlIIIlIIIIllI, lllllllllllllllllllIlIIIlIIIlIIl, lllllllllllllllllllIlIIIlIIIlIII);
    }

    public boolean mouseClicked(double lllllllllllllllllllIlIIIlIllIIll, double lllllllllllllllllllIlIIIlIllIIlI, int lllllllllllllllllllIlIIIlIllIIIl) {
        WidgetScreen lllllllllllllllllllIlIIIlIllIlII;
        if (lllllllllllllllllllIlIIIlIllIlII.locked) {
            return false;
        }
        double lllllllllllllllllllIlIIIlIllIIII = Utils.mc.getWindow().getScaleFactor();
        return lllllllllllllllllllIlIIIlIllIlII.root.mouseClicked(lllllllllllllllllllIlIIIlIllIIll *= lllllllllllllllllllIlIIIlIllIIII, lllllllllllllllllllIlIIIlIllIIlI *= lllllllllllllllllllIlIIIlIllIIII, lllllllllllllllllllIlIIIlIllIIIl, false);
    }

    public boolean keyReleased(int lllllllllllllllllllIlIIIIllllIlI, int lllllllllllllllllllIlIIIIllllIIl, int lllllllllllllllllllIlIIIIllllIII) {
        WidgetScreen lllllllllllllllllllIlIIIIllllIll;
        if (lllllllllllllllllllIlIIIIllllIll.locked) {
            return false;
        }
        if ((lllllllllllllllllllIlIIIIllllIII == 2 || lllllllllllllllllllIlIIIIllllIII == 8) && lllllllllllllllllllIlIIIIllllIlI == 57) {
            lllllllllllllllllllIlIIIIllllIll.debug = !lllllllllllllllllllIlIIIIllllIll.debug;
            return true;
        }
        return super.keyReleased(lllllllllllllllllllIlIIIIllllIlI, lllllllllllllllllllIlIIIIllllIIl, lllllllllllllllllllIlIIIIllllIII);
    }

    public void removed() {
        WidgetScreen lllllllllllllllllllIlIIIIIIllIlI;
        if (!lllllllllllllllllllIlIIIIIIllIlI.closed) {
            lllllllllllllllllllIlIIIIIIllIlI.closed = true;
            lllllllllllllllllllIlIIIIIIllIlI.onClosed();
            Input.setCursorStyle(CursorStyle.Default);
            lllllllllllllllllllIlIIIIIIllIlI.loopWidgets(lllllllllllllllllllIlIIIIIIllIlI.root, lllllllllllllllllllIlIIIIIIIIIll -> {
                WTextBox lllllllllllllllllllIlIIIIIIIIlII;
                if (lllllllllllllllllllIlIIIIIIIIIll instanceof WTextBox && (lllllllllllllllllllIlIIIIIIIIlII = (WTextBox)lllllllllllllllllllIlIIIIIIIIIll).isFocused()) {
                    lllllllllllllllllllIlIIIIIIIIlII.setFocused(false);
                }
            });
            MeteorClient.EVENT_BUS.unsubscribe((Object)lllllllllllllllllllIlIIIIIIllIlI);
            if (lllllllllllllllllllIlIIIIIIllIlI.onClose) {
                Utils.mc.openScreen(lllllllllllllllllllIlIIIIIIllIlI.parent);
            }
        }
    }

    public boolean shouldCloseOnEsc() {
        WidgetScreen lllllllllllllllllllIlIIIIIIIlIII;
        return !lllllllllllllllllllIlIIIIIIIlIII.locked;
    }

    public void keyRepeated(int lllllllllllllllllllIlIIIIllIIlII, int lllllllllllllllllllIlIIIIllIIllI) {
        WidgetScreen lllllllllllllllllllIlIIIIllIlIII;
        if (lllllllllllllllllllIlIIIIllIlIII.locked) {
            return;
        }
        lllllllllllllllllllIlIIIIllIlIII.root.keyRepeated(lllllllllllllllllllIlIIIIllIIlII, lllllllllllllllllllIlIIIIllIIllI);
    }

    protected void onRenderBefore(float lllllllllllllllllllIlIIIIIllllII) {
    }

    public boolean keyPressed(int lllllllllllllllllllIlIIIIllIlllI, int lllllllllllllllllllIlIIIIlllIIIl, int lllllllllllllllllllIlIIIIllIllII) {
        WidgetScreen lllllllllllllllllllIlIIIIlllIIll;
        if (lllllllllllllllllllIlIIIIlllIIll.locked) {
            return false;
        }
        return lllllllllllllllllllIlIIIIlllIIll.root.keyPressed(lllllllllllllllllllIlIIIIllIlllI, lllllllllllllllllllIlIIIIllIllII) || super.keyPressed(lllllllllllllllllllIlIIIIllIlllI, lllllllllllllllllllIlIIIIlllIIIl, lllllllllllllllllllIlIIIIllIllII);
    }

    public WidgetScreen(GuiTheme lllllllllllllllllllIlIIIllIIllIl, String lllllllllllllllllllIlIIIllIIllII) {
        super((Text)new LiteralText(lllllllllllllllllllIlIIIllIIllII));
        WidgetScreen lllllllllllllllllllIlIIIllIIlIll;
        lllllllllllllllllllIlIIIllIIlIll.parent = Utils.mc.currentScreen;
        lllllllllllllllllllIlIIIllIIlIll.root = new WFullScreenRoot();
        lllllllllllllllllllIlIIIllIIlIll.theme = lllllllllllllllllllIlIIIllIIllIl;
        lllllllllllllllllllIlIIIllIIlIll.root.theme = lllllllllllllllllllIlIIIllIIllIl;
        if (lllllllllllllllllllIlIIIllIIlIll.parent != null) {
            lllllllllllllllllllIlIIIllIIlIll.animProgress = 1.0;
            if (lllllllllllllllllllIlIIIllIIlIll instanceof TabScreen && lllllllllllllllllllIlIIIllIIlIll.parent instanceof TabScreen) {
                lllllllllllllllllllIlIIIllIIlIll.parent = ((TabScreen)lllllllllllllllllllIlIIIllIIlIll.parent).parent;
            }
        }
    }

    public boolean isPauseScreen() {
        return false;
    }

    protected void init() {
        WidgetScreen lllllllllllllllllllIlIIIlIlllIlI;
        MeteorClient.EVENT_BUS.subscribe((Object)lllllllllllllllllllIlIIIlIlllIlI);
        lllllllllllllllllllIlIIIlIlllIlI.loopWidgets(lllllllllllllllllllIlIIIlIlllIlI.root, lllllllllllllllllllIIllllllllllI -> {
            if (lllllllllllllllllllIIllllllllllI instanceof WTextBox) {
                GuiKeyEvents.setPostKeyEvents(((WTextBox)lllllllllllllllllllIIllllllllllI).isFocused());
            }
        });
        lllllllllllllllllllIlIIIlIlllIlI.closed = false;
    }

    public void mouseMoved(double lllllllllllllllllllIlIIIlIIlIIlI, double lllllllllllllllllllIlIIIlIIlIIIl) {
        WidgetScreen lllllllllllllllllllIlIIIlIIlIIll;
        if (lllllllllllllllllllIlIIIlIIlIIll.locked) {
            return;
        }
        double lllllllllllllllllllIlIIIlIIlIlII = Utils.mc.getWindow().getScaleFactor();
        lllllllllllllllllllIlIIIlIIlIIll.root.mouseMoved(lllllllllllllllllllIlIIIlIIlIIlI *= lllllllllllllllllllIlIIIlIIlIlII, lllllllllllllllllllIlIIIlIIlIIIl *= lllllllllllllllllllIlIIIlIIlIlII, lllllllllllllllllllIlIIIlIIlIIll.lastMouseX, lllllllllllllllllllIlIIIlIIlIIll.lastMouseY);
        lllllllllllllllllllIlIIIlIIlIIll.lastMouseX = lllllllllllllllllllIlIIIlIIlIIlI;
        lllllllllllllllllllIlIIIlIIlIIll.lastMouseY = lllllllllllllllllllIlIIIlIIlIIIl;
    }

    public void render(MatrixStack lllllllllllllllllllIlIIIIlIIIIll, int lllllllllllllllllllIlIIIIlIIIIlI, int lllllllllllllllllllIlIIIIlIIIIIl, float lllllllllllllllllllIlIIIIlIIIlll) {
        WidgetScreen lllllllllllllllllllIlIIIIlIIllIl;
        if (!Utils.canUpdate()) {
            lllllllllllllllllllIlIIIIlIIllIl.renderBackground(lllllllllllllllllllIlIIIIlIIIIll);
        }
        double lllllllllllllllllllIlIIIIlIIIllI = Utils.mc.getWindow().getScaleFactor();
        lllllllllllllllllllIlIIIIlIIIIlI = (int)((double)lllllllllllllllllllIlIIIIlIIIIlI * lllllllllllllllllllIlIIIIlIIIllI);
        lllllllllllllllllllIlIIIIlIIIIIl = (int)((double)lllllllllllllllllllIlIIIIlIIIIIl * lllllllllllllllllllIlIIIIlIIIllI);
        lllllllllllllllllllIlIIIIlIIllIl.animProgress += (double)(lllllllllllllllllllIlIIIIlIIIlll / 20.0f * 14.0f);
        lllllllllllllllllllIlIIIIlIIllIl.animProgress = Utils.clamp(lllllllllllllllllllIlIIIIlIIllIl.animProgress, 0.0, 1.0);
        Utils.unscaledProjection();
        Matrices.begin(new MatrixStack());
        lllllllllllllllllllIlIIIIlIIllIl.onRenderBefore(lllllllllllllllllllIlIIIIlIIIlll);
        WidgetScreen.RENDERER.theme = lllllllllllllllllllIlIIIIlIIllIl.theme;
        lllllllllllllllllllIlIIIIlIIllIl.theme.beforeRender();
        RENDERER.begin();
        RENDERER.setAlpha(lllllllllllllllllllIlIIIIlIIllIl.animProgress);
        lllllllllllllllllllIlIIIIlIIllIl.root.render(RENDERER, lllllllllllllllllllIlIIIIlIIIIlI, lllllllllllllllllllIlIIIIlIIIIIl, lllllllllllllllllllIlIIIIlIIIlll / 20.0f);
        RENDERER.setAlpha(1.0);
        RENDERER.end();
        boolean lllllllllllllllllllIlIIIIlIIIlIl = RENDERER.renderTooltip(lllllllllllllllllllIlIIIIlIIIIlI, lllllllllllllllllllIlIIIIlIIIIIl, lllllllllllllllllllIlIIIIlIIIlll / 20.0f);
        if (lllllllllllllllllllIlIIIIlIIllIl.debug) {
            DEBUG_RENDERER.render(lllllllllllllllllllIlIIIIlIIllIl.root);
            if (lllllllllllllllllllIlIIIIlIIIlIl) {
                DEBUG_RENDERER.render(WidgetScreen.RENDERER.tooltipWidget);
            }
        }
        Utils.scaledProjection();
        if (lllllllllllllllllllIlIIIIlIIllIl.taskAfterRender != null) {
            lllllllllllllllllllIlIIIIlIIllIl.taskAfterRender.run();
            lllllllllllllllllllIlIIIIlIIllIl.taskAfterRender = null;
        }
    }

    public void clear() {
        WidgetScreen lllllllllllllllllllIlIIIllIIIIII;
        lllllllllllllllllllIlIIIllIIIIII.root.clear();
    }

    private static class WFullScreenRoot
    extends WContainer
    implements WRoot {
        private /* synthetic */ boolean valid;

        @Override
        protected void onCalculateSize() {
            lllllllllllllllllIlllIIIIIllllIl.width = Utils.getWindowWidth();
            lllllllllllllllllIlllIIIIIllllIl.height = Utils.getWindowHeight();
        }

        @Override
        protected void onCalculateWidgetPositions() {
            WFullScreenRoot lllllllllllllllllIlllIIIIIllIllI;
            for (Cell lllllllllllllllllIlllIIIIIlllIII : lllllllllllllllllIlllIIIIIllIllI.cells) {
                lllllllllllllllllIlllIIIIIlllIII.x = 0.0;
                lllllllllllllllllIlllIIIIIlllIII.y = 0.0;
                lllllllllllllllllIlllIIIIIlllIII.width = lllllllllllllllllIlllIIIIIllIllI.width;
                lllllllllllllllllIlllIIIIIlllIII.height = lllllllllllllllllIlllIIIIIllIllI.height;
                lllllllllllllllllIlllIIIIIlllIII.alignWidget();
            }
        }

        @Override
        public boolean render(GuiRenderer lllllllllllllllllIlllIIIIIlIlIII, double lllllllllllllllllIlllIIIIIlIllII, double lllllllllllllllllIlllIIIIIlIlIll, double lllllllllllllllllIlllIIIIIlIIlIl) {
            WFullScreenRoot lllllllllllllllllIlllIIIIIlIlIIl;
            if (!lllllllllllllllllIlllIIIIIlIlIIl.valid) {
                lllllllllllllllllIlllIIIIIlIlIIl.calculateSize();
                lllllllllllllllllIlllIIIIIlIlIIl.calculateWidgetPositions();
                lllllllllllllllllIlllIIIIIlIlIIl.valid = true;
                lllllllllllllllllIlllIIIIIlIlIIl.mouseMoved(Utils.mc.mouse.getX(), Utils.mc.mouse.getY(), Utils.mc.mouse.getX(), Utils.mc.mouse.getY());
            }
            return super.render(lllllllllllllllllIlllIIIIIlIlIII, lllllllllllllllllIlllIIIIIlIllII, lllllllllllllllllIlllIIIIIlIlIll, lllllllllllllllllIlllIIIIIlIIlIl);
        }

        @Override
        public void invalidate() {
            lllllllllllllllllIlllIIIIIllllll.valid = false;
        }

        private WFullScreenRoot() {
            WFullScreenRoot lllllllllllllllllIlllIIIIlIIIIlI;
        }
    }
}

