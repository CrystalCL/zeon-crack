/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets.pressable;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.renderer.packer.GuiTexture;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorButton
extends WButton
implements MeteorWidget {
    public WMeteorButton(String llllllllllllllllllllllIlllIIlIIl, GuiTexture llllllllllllllllllllllIlllIIIlIl) {
        super(llllllllllllllllllllllIlllIIlIIl, llllllllllllllllllllllIlllIIIlIl);
        WMeteorButton llllllllllllllllllllllIlllIIIlll;
    }

    @Override
    protected void onRender(GuiRenderer llllllllllllllllllllllIllIllllIl, double llllllllllllllllllllllIllIllllII, double llllllllllllllllllllllIllIlllIll, double llllllllllllllllllllllIllIlllIlI) {
        WMeteorButton llllllllllllllllllllllIllIlllllI;
        MeteorGuiTheme llllllllllllllllllllllIllIlllIIl = llllllllllllllllllllllIllIlllllI.theme();
        double llllllllllllllllllllllIllIlllIII = llllllllllllllllllllllIllIlllllI.pad();
        llllllllllllllllllllllIllIlllllI.renderBackground(llllllllllllllllllllllIllIllllIl, llllllllllllllllllllllIllIlllllI, llllllllllllllllllllllIllIlllllI.pressed, llllllllllllllllllllllIllIlllllI.mouseOver);
        if (llllllllllllllllllllllIllIlllllI.text != null) {
            llllllllllllllllllllllIllIllllIl.text(llllllllllllllllllllllIllIlllllI.text, llllllllllllllllllllllIllIlllllI.x + llllllllllllllllllllllIllIlllllI.width / 2.0 - llllllllllllllllllllllIllIlllllI.textWidth / 2.0, llllllllllllllllllllllIllIlllllI.y + llllllllllllllllllllllIllIlllIII, llllllllllllllllllllllIllIlllIIl.textColor.get(), false);
        } else {
            double llllllllllllllllllllllIllIllllll = llllllllllllllllllllllIllIlllIIl.textHeight();
            llllllllllllllllllllllIllIllllIl.quad(llllllllllllllllllllllIllIlllllI.x + llllllllllllllllllllllIllIlllllI.width / 2.0 - llllllllllllllllllllllIllIllllll / 2.0, llllllllllllllllllllllIllIlllllI.y + llllllllllllllllllllllIllIlllIII, llllllllllllllllllllllIllIllllll, llllllllllllllllllllllIllIllllll, llllllllllllllllllllllIllIlllllI.texture, (Color)llllllllllllllllllllllIllIlllIIl.textColor.get());
        }
    }
}

