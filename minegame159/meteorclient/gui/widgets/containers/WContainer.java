/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.Mouse
 */
package minegame159.meteorclient.gui.widgets.containers;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

public abstract class WContainer
extends WWidget {
    public final /* synthetic */ List<Cell<?>> cells;

    @Override
    public boolean mouseClicked(double llllIllIIIllIl, double llllIllIIlIIIl, int llllIllIIIlIll, boolean llllIllIIIllll) {
        WContainer llllIllIIIlllI;
        try {
            for (Cell<?> llllIllIIlIlII : llllIllIIIlllI.cells) {
                if (!llllIllIIIlllI.propagateEvents((WWidget)llllIllIIlIlII.widget()) || !((WWidget)llllIllIIlIlII.widget()).mouseClicked(llllIllIIIllIl, llllIllIIlIIIl, llllIllIIIlIll, llllIllIIIllll)) continue;
                llllIllIIIllll = true;
            }
        }
        catch (ConcurrentModificationException llllIllIIIlIIl) {
            // empty catch block
        }
        return super.mouseClicked(llllIllIIIllIl, llllIllIIlIIIl, llllIllIIIlIll, llllIllIIIllll) || llllIllIIIllll;
    }

    @Override
    public boolean render(GuiRenderer llllIllIllIIll, double llllIllIllIlll, double llllIllIllIllI, double llllIllIllIlIl) {
        WContainer llllIllIllIlII;
        if (super.render(llllIllIllIIll, llllIllIllIlll, llllIllIllIllI, llllIllIllIlIl)) {
            return true;
        }
        for (Cell<?> llllIllIlllIlI : llllIllIllIlII.cells) {
            if (((WWidget)llllIllIlllIlI.widget()).y > (double)Utils.getWindowHeight()) break;
            llllIllIllIlII.renderWidget((WWidget)llllIllIlllIlI.widget(), llllIllIllIIll, llllIllIllIlll, llllIllIllIllI, llllIllIllIlIl);
        }
        return false;
    }

    @Override
    public boolean charTyped(char llllIlIIllIlIl) {
        WContainer llllIlIIllIlII;
        try {
            for (Cell<?> llllIlIIllIlll : llllIlIIllIlII.cells) {
                if (!llllIlIIllIlII.propagateEvents((WWidget)llllIlIIllIlll.widget()) || !((WWidget)llllIlIIllIlll.widget()).charTyped(llllIlIIllIlIl)) continue;
                return true;
            }
        }
        catch (ConcurrentModificationException llllIlIIllIIlI) {
            // empty catch block
        }
        return super.charTyped(llllIlIIllIlIl);
    }

    @Override
    protected void onCalculateWidgetPositions() {
        WContainer llllIlllIIIlII;
        for (Cell<?> llllIlllIIIllI : llllIlllIIIlII.cells) {
            llllIlllIIIllI.x = llllIlllIIIlII.x + llllIlllIIIllI.padLeft();
            llllIlllIIIllI.y = llllIlllIIIlII.y + llllIlllIIIllI.padTop();
            llllIlllIIIllI.width = llllIlllIIIlII.width - llllIlllIIIllI.padLeft() - llllIlllIIIllI.padRight();
            llllIlllIIIllI.height = llllIlllIIIlII.height - llllIlllIIIllI.padTop() - llllIlllIIIllI.padBottom();
            llllIlllIIIllI.alignWidget();
        }
    }

    @Override
    public void calculateSize() {
        WContainer llllIlllIlllII;
        for (Cell<?> llllIlllIllllI : llllIlllIlllII.cells) {
            ((WWidget)llllIlllIllllI.widget()).calculateSize();
        }
        super.calculateSize();
    }

    @Override
    public boolean mouseReleased(double llllIlIllllIll, double llllIlIllllllI, int llllIlIllllIIl) {
        WContainer llllIllIIIIIII;
        try {
            for (Cell<?> llllIllIIIIIIl : llllIllIIIIIII.cells) {
                if (!llllIllIIIIIII.propagateEvents((WWidget)llllIllIIIIIIl.widget()) || !((WWidget)llllIllIIIIIIl.widget()).mouseReleased(llllIlIllllIll, llllIlIllllllI, llllIlIllllIIl)) continue;
                return true;
            }
        }
        catch (ConcurrentModificationException llllIlIllllIII) {
            // empty catch block
        }
        return super.mouseReleased(llllIlIllllIll, llllIlIllllllI, llllIlIllllIIl);
    }

    @Override
    public void move(double llllIlllllIllI, double llllIllllllIII) {
        WContainer llllIllllllIlI;
        super.move(llllIlllllIllI, llllIllllllIII);
        for (Cell<?> llllIllllllIll : llllIllllllIlI.cells) {
            llllIllllllIll.move(llllIlllllIllI, llllIllllllIII);
        }
    }

    @Override
    public void mouseMoved(double llllIlIllIllIl, double llllIlIllIllII, double llllIlIllIIllI, double llllIlIllIIlIl) {
        WContainer llllIlIllIlIIl;
        try {
            for (Cell<?> llllIlIllIllll : llllIlIllIlIIl.cells) {
                if (!llllIlIllIlIIl.propagateEvents((WWidget)llllIlIllIllll.widget())) continue;
                ((WWidget)llllIlIllIllll.widget()).mouseMoved(llllIlIllIllIl, llllIlIllIllII, llllIlIllIIllI, llllIlIllIIlIl);
            }
        }
        catch (ConcurrentModificationException llllIlIllIIlII) {
            // empty catch block
        }
        super.mouseMoved(llllIlIllIllIl, llllIlIllIllII, llllIlIllIIllI, llllIlIllIIlIl);
    }

    @Override
    public void calculateWidgetPositions() {
        WContainer llllIlllIIllIl;
        super.calculateWidgetPositions();
        for (Cell<?> llllIlllIIlllI : llllIlllIIllIl.cells) {
            ((WWidget)llllIlllIIlllI.widget()).calculateWidgetPositions();
        }
    }

    public WContainer() {
        WContainer lllllIIIIIllIl;
        lllllIIIIIllIl.cells = new ArrayList();
    }

    @Override
    public boolean keyRepeated(int llllIlIlIIIIlI, int llllIlIlIIIIIl) {
        WContainer llllIlIlIIIIII;
        try {
            for (Cell<?> llllIlIlIIIlII : llllIlIlIIIIII.cells) {
                if (!llllIlIlIIIIII.propagateEvents((WWidget)llllIlIlIIIlII.widget()) || !((WWidget)llllIlIlIIIlII.widget()).keyRepeated(llllIlIlIIIIlI, llllIlIlIIIIIl)) continue;
                return true;
            }
        }
        catch (ConcurrentModificationException llllIlIIllllIl) {
            // empty catch block
        }
        return llllIlIlIIIIII.onKeyRepeated(llllIlIlIIIIlI, llllIlIlIIIIIl);
    }

    protected void renderWidget(WWidget llllIllIlIIlll, GuiRenderer llllIllIlIIIIl, double llllIllIlIIIII, double llllIllIIlllll, double llllIllIIllllI) {
        llllIllIlIIlll.render(llllIllIlIIIIl, llllIllIlIIIII, llllIllIIlllll, llllIllIIllllI);
    }

    @Override
    public void mouseScrolled(double llllIlIlIlllII) {
        WContainer llllIlIlIllIll;
        try {
            for (Cell<?> llllIlIlIllllI : llllIlIlIllIll.cells) {
                if (!llllIlIlIllIll.propagateEvents((WWidget)llllIlIlIllllI.widget())) continue;
                ((WWidget)llllIlIlIllllI.widget()).mouseScrolled(llllIlIlIlllII);
            }
        }
        catch (ConcurrentModificationException llllIlIlIllIIl) {
            // empty catch block
        }
        super.mouseScrolled(llllIlIlIlllII);
    }

    @Override
    protected void onCalculateSize() {
        WContainer llllIlllIlIlII;
        llllIlllIlIlII.width = 0.0;
        llllIlllIlIlII.height = 0.0;
        for (Cell<?> llllIlllIlIllI : llllIlllIlIlII.cells) {
            llllIlllIlIlII.width = Math.max(llllIlllIlIlII.width, llllIlllIlIllI.padLeft() + ((WWidget)llllIlllIlIllI.widget()).width + llllIlllIlIllI.padRight());
            llllIlllIlIlII.height = Math.max(llllIlllIlIlII.height, llllIlllIlIllI.padTop() + ((WWidget)llllIlllIlIllI.widget()).height + llllIlllIlIllI.padBottom());
        }
    }

    public void clear() {
        WContainer lllllIIIIIIIlI;
        if (lllllIIIIIIIlI.cells.size() > 0) {
            lllllIIIIIIIlI.cells.clear();
            lllllIIIIIIIlI.invalidate();
        }
    }

    public <T extends WWidget> Cell<T> add(T lllllIIIIIlIII) {
        WContainer lllllIIIIIlIIl;
        lllllIIIIIlIII.parent = lllllIIIIIlIIl;
        lllllIIIIIlIII.theme = lllllIIIIIlIIl.theme;
        Cell<T> lllllIIIIIIlll = new Cell<T>(lllllIIIIIlIII).centerY();
        lllllIIIIIlIIl.cells.add(lllllIIIIIIlll);
        lllllIIIIIlIII.init();
        lllllIIIIIlIIl.invalidate();
        return lllllIIIIIIlll;
    }

    public void moveCells(double llllIllllIIllI, double llllIllllIlIII) {
        WContainer llllIllllIIlll;
        for (Cell<?> llllIllllIlIll : llllIllllIIlll.cells) {
            llllIllllIlIll.move(llllIllllIIllI, llllIllllIlIII);
            Mouse llllIllllIllII = MinecraftClient.getInstance().mouse;
            ((WWidget)llllIllllIlIll.widget()).mouseMoved(llllIllllIllII.getX(), llllIllllIllII.getY(), llllIllllIllII.getX(), llllIllllIllII.getY());
        }
    }

    @Override
    public boolean keyPressed(int llllIlIlIIllIl, int llllIlIlIIllII) {
        WContainer llllIlIlIIlllI;
        try {
            for (Cell<?> llllIlIlIlIIlI : llllIlIlIIlllI.cells) {
                if (!llllIlIlIIlllI.propagateEvents((WWidget)llllIlIlIlIIlI.widget()) || !((WWidget)llllIlIlIlIIlI.widget()).keyPressed(llllIlIlIIllIl, llllIlIlIIllII)) continue;
                return true;
            }
        }
        catch (ConcurrentModificationException llllIlIlIIlIll) {
            // empty catch block
        }
        return llllIlIlIIlllI.onKeyPressed(llllIlIlIIllIl, llllIlIlIIllII);
    }

    protected boolean propagateEvents(WWidget llllIllIIlllII) {
        return true;
    }
}

