/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets.pressable;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;

public class WMeteorPlus
extends WPlus
implements MeteorWidget {
    public WMeteorPlus() {
        WMeteorPlus lllIIIIIllIII;
    }

    @Override
    protected void onRender(GuiRenderer lllIIIIIIlIIl, double lllIIIIIlIIII, double lllIIIIIIllll, double lllIIIIIIlllI) {
        WMeteorPlus lllIIIIIIlIlI;
        MeteorGuiTheme lllIIIIIIllIl = lllIIIIIIlIlI.theme();
        double lllIIIIIIllII = lllIIIIIIlIlI.pad();
        double lllIIIIIIlIll = lllIIIIIIllIl.scale(3.0);
        lllIIIIIIlIlI.renderBackground(lllIIIIIIlIIl, lllIIIIIIlIlI, lllIIIIIIlIlI.pressed, lllIIIIIIlIlI.mouseOver);
        lllIIIIIIlIIl.quad(lllIIIIIIlIlI.x + lllIIIIIIllII, lllIIIIIIlIlI.y + lllIIIIIIlIlI.height / 2.0 - lllIIIIIIlIll / 2.0, lllIIIIIIlIlI.width - lllIIIIIIllII * 2.0, lllIIIIIIlIll, lllIIIIIIllIl.plusColor.get());
        lllIIIIIIlIIl.quad(lllIIIIIIlIlI.x + lllIIIIIIlIlI.width / 2.0 - lllIIIIIIlIll / 2.0, lllIIIIIIlIlI.y + lllIIIIIIllII, lllIIIIIIlIll, lllIIIIIIlIlI.height - lllIIIIIIllII * 2.0, lllIIIIIIllIl.plusColor.get());
    }
}

