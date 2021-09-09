/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets.pressable;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.pressable.WTriangle;

public class WMeteorTriangle
extends WTriangle
implements MeteorWidget {
    @Override
    protected void onRender(GuiRenderer lllllllllllllllllIlIllIIIlllllIl, double lllllllllllllllllIlIllIIlIIIIIIl, double lllllllllllllllllIlIllIIlIIIIIII, double lllllllllllllllllIlIllIIIlllllll) {
        WMeteorTriangle lllllllllllllllllIlIllIIlIIIIIll;
        lllllllllllllllllIlIllIIIlllllIl.rotatedQuad(lllllllllllllllllIlIllIIlIIIIIll.x, lllllllllllllllllIlIllIIlIIIIIll.y, lllllllllllllllllIlIllIIlIIIIIll.width, lllllllllllllllllIlIllIIlIIIIIll.height, lllllllllllllllllIlIllIIlIIIIIll.rotation, GuiRenderer.TRIANGLE, lllllllllllllllllIlIllIIlIIIIIll.theme().backgroundColor.get(lllllllllllllllllIlIllIIlIIIIIll.pressed, lllllllllllllllllIlIllIIlIIIIIll.mouseOver));
    }

    public WMeteorTriangle() {
        WMeteorTriangle lllllllllllllllllIlIllIIlIIIIlll;
    }
}

