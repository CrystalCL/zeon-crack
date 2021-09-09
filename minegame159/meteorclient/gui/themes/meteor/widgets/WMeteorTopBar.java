/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WTopBar;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorTopBar
extends WTopBar
implements MeteorWidget {
    @Override
    protected Color getNameColor() {
        WMeteorTopBar lIlIllIlllIIl;
        return lIlIllIlllIIl.theme().textColor.get();
    }

    @Override
    protected Color getButtonColor(boolean lIlIllIllllll, boolean lIlIllIlllIll) {
        WMeteorTopBar lIlIllIllllIl;
        return lIlIllIllllIl.theme().backgroundColor.get(lIlIllIllllll, lIlIllIlllIll);
    }

    public WMeteorTopBar() {
        WMeteorTopBar lIlIlllIIIlII;
    }
}

