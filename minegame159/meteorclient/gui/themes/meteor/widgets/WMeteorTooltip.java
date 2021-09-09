/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WTooltip;

public class WMeteorTooltip
extends WTooltip
implements MeteorWidget {
    public WMeteorTooltip(String lllIIIllIlIllII) {
        super(lllIIIllIlIllII);
        WMeteorTooltip lllIIIllIlIllIl;
    }

    @Override
    protected void onRender(GuiRenderer lllIIIllIlIIllI, double lllIIIllIlIIlIl, double lllIIIllIlIIlII, double lllIIIllIlIIIll) {
        WMeteorTooltip lllIIIllIlIIlll;
        lllIIIllIlIIllI.quad(lllIIIllIlIIlll, lllIIIllIlIIlll.theme().backgroundColor.get());
    }
}

