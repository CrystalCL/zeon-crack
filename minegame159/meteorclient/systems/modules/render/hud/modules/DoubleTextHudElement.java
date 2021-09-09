/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.render.color.Color;

public abstract class DoubleTextHudElement
extends HudElement {
    private /* synthetic */ double leftWidth;
    private /* synthetic */ String right;
    protected /* synthetic */ Color rightColor;
    private /* synthetic */ String left;
    protected /* synthetic */ boolean visible;

    protected void setLeft(String lllllllllllllllllIIllIIIIIllIIlI) {
        lllllllllllllllllIIllIIIIIllIlIl.left = lllllllllllllllllIIllIIIIIllIIlI;
        lllllllllllllllllIIllIIIIIllIlIl.leftWidth = 0.0;
    }

    protected abstract String getRight();

    public DoubleTextHudElement(HUD lllllllllllllllllIIllIIIIlIIllIl, String lllllllllllllllllIIllIIIIlIlIIIl, String lllllllllllllllllIIllIIIIlIIlIll, String lllllllllllllllllIIllIIIIlIIlIlI) {
        super(lllllllllllllllllIIllIIIIlIIllIl, lllllllllllllllllIIllIIIIlIlIIIl, lllllllllllllllllIIllIIIIlIIlIll);
        DoubleTextHudElement lllllllllllllllllIIllIIIIlIlIIll;
        lllllllllllllllllIIllIIIIlIlIIll.visible = true;
        lllllllllllllllllIIllIIIIlIlIIll.rightColor = lllllllllllllllllIIllIIIIlIIllIl.secondaryColor.get();
        lllllllllllllllllIIllIIIIlIlIIll.left = lllllllllllllllllIIllIIIIlIIlIlI;
    }

    @Override
    public void render(HudRenderer lllllllllllllllllIIllIIIIIlllIlI) {
        DoubleTextHudElement lllllllllllllllllIIllIIIIIllllll;
        if (!lllllllllllllllllIIllIIIIIllllll.visible) {
            return;
        }
        double lllllllllllllllllIIllIIIIIllllIl = lllllllllllllllllIIllIIIIIllllll.box.getX();
        double lllllllllllllllllIIllIIIIIllllII = lllllllllllllllllIIllIIIIIllllll.box.getY();
        lllllllllllllllllIIllIIIIIlllIlI.text(lllllllllllllllllIIllIIIIIllllll.left, lllllllllllllllllIIllIIIIIllllIl, lllllllllllllllllIIllIIIIIllllII, lllllllllllllllllIIllIIIIIllllll.hud.primaryColor.get());
        lllllllllllllllllIIllIIIIIlllIlI.text(lllllllllllllllllIIllIIIIIllllll.right, lllllllllllllllllIIllIIIIIllllIl + lllllllllllllllllIIllIIIIIllllll.leftWidth, lllllllllllllllllIIllIIIIIllllII, lllllllllllllllllIIllIIIIIllllll.rightColor);
    }

    @Override
    public void update(HudRenderer lllllllllllllllllIIllIIIIlIIIlII) {
        DoubleTextHudElement lllllllllllllllllIIllIIIIlIIIlIl;
        lllllllllllllllllIIllIIIIlIIIlIl.right = lllllllllllllllllIIllIIIIlIIIlIl.getRight();
        lllllllllllllllllIIllIIIIlIIIlIl.leftWidth = lllllllllllllllllIIllIIIIlIIIlII.textWidth(lllllllllllllllllIIllIIIIlIIIlIl.left);
        lllllllllllllllllIIllIIIIlIIIlIl.box.setSize(lllllllllllllllllIIllIIIIlIIIlIl.leftWidth + lllllllllllllllllIIllIIIIlIIIlII.textWidth(lllllllllllllllllIIllIIIIlIIIlIl.right), lllllllllllllllllIIllIIIIlIIIlII.textHeight());
    }
}

