/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WMultiLabel;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorMultiLabel
extends WMultiLabel
implements MeteorWidget {
    public WMeteorMultiLabel(String llllllIlllIllI, boolean llllllIlllIlIl, double llllIIlIllIIIl) {
        super(llllllIlllIllI, llllllIlllIlIl, llllIIlIllIIIl);
        WMeteorMultiLabel llllllIlllIIll;
    }

    @Override
    protected void onRender(GuiRenderer llllIIlIlIIIlI, double llllIIlIlIlIII, double llllIIlIlIIlll, double llllIIlIlIIllI) {
        WMeteorMultiLabel llllIIlIlIIIll;
        double llllIIlIlIIlIl = llllIIlIlIIIll.theme.textHeight(llllIIlIlIIIll.title);
        Color llllIIlIlIIlII = llllIIlIlIIIll.theme().textColor.get();
        for (int llllIIlIlIlIll = 0; llllIIlIlIlIll < llllIIlIlIIIll.lines.size(); ++llllIIlIlIlIll) {
            llllIIlIlIIIlI.text((String)llllIIlIlIIIll.lines.get(llllIIlIlIlIll), llllIIlIlIIIll.x, llllIIlIlIIIll.y + llllIIlIlIIlIl * (double)llllIIlIlIlIll, llllIIlIlIIlII, false);
        }
    }
}

