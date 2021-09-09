/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorLabel
extends WLabel
implements MeteorWidget {
    public WMeteorLabel(String lllllllllllllllllllIlllIlllllIIl, boolean lllllllllllllllllllIlllIlllllIII) {
        super(lllllllllllllllllllIlllIlllllIIl, lllllllllllllllllllIlllIlllllIII);
        WMeteorLabel lllllllllllllllllllIlllIlllllIlI;
    }

    @Override
    protected void onRender(GuiRenderer lllllllllllllllllllIlllIllllIlII, double lllllllllllllllllllIlllIllllIIll, double lllllllllllllllllllIlllIllllIIlI, double lllllllllllllllllllIlllIllllIIIl) {
        WMeteorLabel lllllllllllllllllllIlllIllllIlIl;
        if (!lllllllllllllllllllIlllIllllIlIl.text.isEmpty()) {
            lllllllllllllllllllIlllIllllIlII.text(lllllllllllllllllllIlllIllllIlIl.text, lllllllllllllllllllIlllIllllIlIl.x, lllllllllllllllllllIlllIllllIlIl.y, lllllllllllllllllllIlllIllllIlIl.color != null ? lllllllllllllllllllIlllIllllIlIl.color : (lllllllllllllllllllIlllIllllIlIl.title ? (Color)lllllllllllllllllllIlllIllllIlIl.theme().titleTextColor.get() : (Color)lllllllllllllllllllIlllIllllIlIl.theme().textColor.get()), lllllllllllllllllllIlllIllllIlIl.title);
        }
    }
}

