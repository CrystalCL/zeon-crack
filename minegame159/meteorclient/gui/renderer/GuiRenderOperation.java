/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.renderer;

import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.render.color.Color;

public abstract class GuiRenderOperation<T extends GuiRenderOperation<T>> {
    protected /* synthetic */ double y;
    protected /* synthetic */ double x;
    protected /* synthetic */ Color color;

    public GuiRenderOperation() {
        GuiRenderOperation llllllllllllllllIlIlIlllIIlIlIlI;
    }

    public void set(double llllllllllllllllIlIlIlllIIlIIIII, double llllllllllllllllIlIlIlllIIIlllll, Color llllllllllllllllIlIlIlllIIlIIIlI) {
        llllllllllllllllIlIlIlllIIlIIlIl.x = llllllllllllllllIlIlIlllIIlIIIII;
        llllllllllllllllIlIlIlllIIlIIlIl.y = llllllllllllllllIlIlIlllIIIlllll;
        llllllllllllllllIlIlIlllIIlIIlIl.color = llllllllllllllllIlIlIlllIIlIIIlI;
    }

    public void run(Pool<T> llllllllllllllllIlIlIlllIIIllIlI) {
        GuiRenderOperation llllllllllllllllIlIlIlllIIIllIll;
        llllllllllllllllIlIlIlllIIIllIll.onRun();
        llllllllllllllllIlIlIlllIIIllIlI.free(llllllllllllllllIlIlIlllIIIllIll);
    }

    protected abstract void onRun();
}

