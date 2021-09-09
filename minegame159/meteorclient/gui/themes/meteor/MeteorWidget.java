/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.utils.BaseWidget;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.utils.render.color.SettingColor;

public interface MeteorWidget
extends BaseWidget {
    default public MeteorGuiTheme theme() {
        MeteorWidget lllllllllllllllllIlllIIlIlllllII;
        return (MeteorGuiTheme)lllllllllllllllllIlllIIlIlllllII.getTheme();
    }

    default public void renderBackground(GuiRenderer lllllllllllllllllIlllIIlIllIlIIl, WWidget lllllllllllllllllIlllIIlIlllIIII, boolean lllllllllllllllllIlllIIlIllIIlll, boolean lllllllllllllllllIlllIIlIllIIllI) {
        MeteorWidget lllllllllllllllllIlllIIlIllIlIlI;
        MeteorGuiTheme lllllllllllllllllIlllIIlIllIllIl = lllllllllllllllllIlllIIlIllIlIlI.theme();
        double lllllllllllllllllIlllIIlIllIllII = lllllllllllllllllIlllIIlIllIllIl.scale(2.0);
        lllllllllllllllllIlllIIlIllIlIIl.quad(lllllllllllllllllIlllIIlIlllIIII.x + lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIlllIIII.y + lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIlllIIII.width - lllllllllllllllllIlllIIlIllIllII * 2.0, lllllllllllllllllIlllIIlIlllIIII.height - lllllllllllllllllIlllIIlIllIllII * 2.0, lllllllllllllllllIlllIIlIllIllIl.backgroundColor.get(lllllllllllllllllIlllIIlIllIIlll, lllllllllllllllllIlllIIlIllIIllI));
        SettingColor lllllllllllllllllIlllIIlIllIlIll = lllllllllllllllllIlllIIlIllIllIl.outlineColor.get(lllllllllllllllllIlllIIlIllIIlll, lllllllllllllllllIlllIIlIllIIllI);
        lllllllllllllllllIlllIIlIllIlIIl.quad(lllllllllllllllllIlllIIlIlllIIII.x, lllllllllllllllllIlllIIlIlllIIII.y, lllllllllllllllllIlllIIlIlllIIII.width, lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIllIlIll);
        lllllllllllllllllIlllIIlIllIlIIl.quad(lllllllllllllllllIlllIIlIlllIIII.x, lllllllllllllllllIlllIIlIlllIIII.y + lllllllllllllllllIlllIIlIlllIIII.height - lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIlllIIII.width, lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIllIlIll);
        lllllllllllllllllIlllIIlIllIlIIl.quad(lllllllllllllllllIlllIIlIlllIIII.x, lllllllllllllllllIlllIIlIlllIIII.y + lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIlllIIII.height - lllllllllllllllllIlllIIlIllIllII * 2.0, lllllllllllllllllIlllIIlIllIlIll);
        lllllllllllllllllIlllIIlIllIlIIl.quad(lllllllllllllllllIlllIIlIlllIIII.x + lllllllllllllllllIlllIIlIlllIIII.width - lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIlllIIII.y + lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIllIllII, lllllllllllllllllIlllIIlIlllIIII.height - lllllllllllllllllIlllIIlIllIllII * 2.0, lllllllllllllllllIlllIIlIllIlIll);
    }
}

