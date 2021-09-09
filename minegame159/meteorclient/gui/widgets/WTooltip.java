/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.widgets.WRoot;
import minegame159.meteorclient.gui.widgets.containers.WContainer;

public abstract class WTooltip
extends WContainer
implements WRoot {
    protected /* synthetic */ String text;
    private /* synthetic */ boolean valid;

    @Override
    public void invalidate() {
        lIIIllIllIlllII.valid = false;
    }

    public WTooltip(String lIIIllIlllIIIIl) {
        WTooltip lIIIllIlllIIIlI;
        lIIIllIlllIIIlI.text = lIIIllIlllIIIIl;
    }

    @Override
    public void init() {
        WTooltip lIIIllIllIllllI;
        lIIIllIllIllllI.add(lIIIllIllIllllI.theme.label(lIIIllIllIllllI.text)).pad(4.0);
    }

    @Override
    public boolean render(GuiRenderer lIIIllIllIlIlII, double lIIIllIllIIlllI, double lIIIllIllIIllIl, double lIIIllIllIlIIIl) {
        WTooltip lIIIllIllIlIIII;
        if (!lIIIllIllIlIIII.valid) {
            lIIIllIllIlIIII.calculateSize();
            lIIIllIllIlIIII.calculateWidgetPositions();
            lIIIllIllIlIIII.valid = true;
        }
        return super.render(lIIIllIllIlIlII, lIIIllIllIIlllI, lIIIllIllIIllIl, lIIIllIllIlIIIl);
    }
}

