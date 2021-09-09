/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.Utils;

public class PositionHud
extends HudElement {
    private /* synthetic */ double left2Width;
    private /* synthetic */ String right1;
    private /* synthetic */ double left1Width;
    private /* synthetic */ String right2;
    private final /* synthetic */ String left1 = "Pos: ";
    private /* synthetic */ String left2;

    public PositionHud(HUD llllllllllllllllIllIllIlIIIlllIl) {
        super(llllllllllllllllIllIllIlIIIlllIl, "coords", "Displays your coordinates in the world.");
        PositionHud llllllllllllllllIllIllIlIIlIIIII;
        llllllllllllllllIllIllIlIIlIIIII.left1 = "Pos: ";
    }

    @Override
    public void render(HudRenderer llllllllllllllllIllIllIlIIIIIIll) {
        PositionHud llllllllllllllllIllIllIlIIIIIlII;
        double llllllllllllllllIllIllIlIIIIIIlI = llllllllllllllllIllIllIlIIIIIlII.box.getX();
        double llllllllllllllllIllIllIlIIIIIIIl = llllllllllllllllIllIllIlIIIIIlII.box.getY();
        if (llllllllllllllllIllIllIlIIIIIlII.left2 != null) {
            llllllllllllllllIllIllIlIIIIIIll.text(llllllllllllllllIllIllIlIIIIIlII.left2, llllllllllllllllIllIllIlIIIIIIlI, llllllllllllllllIllIllIlIIIIIIIl, llllllllllllllllIllIllIlIIIIIlII.hud.primaryColor.get());
            llllllllllllllllIllIllIlIIIIIIll.text(llllllllllllllllIllIllIlIIIIIlII.right2, llllllllllllllllIllIllIlIIIIIIlI + llllllllllllllllIllIllIlIIIIIlII.left2Width, llllllllllllllllIllIllIlIIIIIIIl, llllllllllllllllIllIllIlIIIIIlII.hud.secondaryColor.get());
        }
        double llllllllllllllllIllIllIlIIIIIIII = llllllllllllllllIllIllIlIIIIIlII.box.alignX(llllllllllllllllIllIllIlIIIIIlII.left1Width + llllllllllllllllIllIllIlIIIIIIll.textWidth(llllllllllllllllIllIllIlIIIIIlII.right1));
        double llllllllllllllllIllIllIIllllllll = llllllllllllllllIllIllIlIIIIIIll.textHeight() + 2.0;
        llllllllllllllllIllIllIlIIIIIIll.text("Pos: ", llllllllllllllllIllIllIlIIIIIIlI + llllllllllllllllIllIllIlIIIIIIII, llllllllllllllllIllIllIlIIIIIIIl + llllllllllllllllIllIllIIllllllll, llllllllllllllllIllIllIlIIIIIlII.hud.primaryColor.get());
        llllllllllllllllIllIllIlIIIIIIll.text(llllllllllllllllIllIllIlIIIIIlII.right1, llllllllllllllllIllIllIlIIIIIIlI + llllllllllllllllIllIllIlIIIIIIII + llllllllllllllllIllIllIlIIIIIlII.left1Width, llllllllllllllllIllIllIlIIIIIIIl + llllllllllllllllIllIllIIllllllll, llllllllllllllllIllIllIlIIIIIlII.hud.secondaryColor.get());
    }

    @Override
    public void update(HudRenderer llllllllllllllllIllIllIlIIIIllll) {
        PositionHud llllllllllllllllIllIllIlIIIlIIII;
        llllllllllllllllIllIllIlIIIlIIII.left1Width = llllllllllllllllIllIllIlIIIIllll.textWidth("Pos: ");
        llllllllllllllllIllIllIlIIIlIIII.left2 = null;
        if (llllllllllllllllIllIllIlIIIlIIII.isInEditor()) {
            llllllllllllllllIllIllIlIIIlIIII.right1 = "0,0 0,0 0,0";
            llllllllllllllllIllIllIlIIIlIIII.box.setSize(llllllllllllllllIllIllIlIIIlIIII.left1Width + llllllllllllllllIllIllIlIIIIllll.textWidth(llllllllllllllllIllIllIlIIIlIIII.right1), llllllllllllllllIllIllIlIIIIllll.textHeight() * 2.0 + 2.0);
            return;
        }
        double llllllllllllllllIllIllIlIIIlIlII = llllllllllllllllIllIllIlIIIlIIII.mc.gameRenderer.getCamera().getPos().x;
        double llllllllllllllllIllIllIlIIIlIIll = llllllllllllllllIllIllIlIIIlIIII.mc.gameRenderer.getCamera().getPos().y - (double)llllllllllllllllIllIllIlIIIlIIII.mc.player.getEyeHeight(llllllllllllllllIllIllIlIIIlIIII.mc.player.getPose());
        double llllllllllllllllIllIllIlIIIlIIlI = llllllllllllllllIllIllIlIIIlIIII.mc.gameRenderer.getCamera().getPos().z;
        llllllllllllllllIllIllIlIIIlIIII.right1 = String.format("%.1f %.1f %.1f", llllllllllllllllIllIllIlIIIlIlII, llllllllllllllllIllIllIlIIIlIIll, llllllllllllllllIllIllIlIIIlIIlI);
        switch (Utils.getDimension()) {
            case Overworld: {
                llllllllllllllllIllIllIlIIIlIIII.left2 = "Nether Pos: ";
                llllllllllllllllIllIllIlIIIlIIII.right2 = String.format("%.1f %.1f %.1f", llllllllllllllllIllIllIlIIIlIlII / 8.0, llllllllllllllllIllIllIlIIIlIIll, llllllllllllllllIllIllIlIIIlIIlI / 8.0);
                break;
            }
            case Nether: {
                llllllllllllllllIllIllIlIIIlIIII.left2 = "Overworld Pos: ";
                llllllllllllllllIllIllIlIIIlIIII.right2 = String.format("%.1f %.1f %.1f", llllllllllllllllIllIllIlIIIlIlII * 8.0, llllllllllllllllIllIllIlIIIlIIll, llllllllllllllllIllIllIlIIIlIIlI * 8.0);
            }
        }
        double llllllllllllllllIllIllIlIIIlIIIl = llllllllllllllllIllIllIlIIIlIIII.left1Width + llllllllllllllllIllIllIlIIIIllll.textWidth(llllllllllllllllIllIllIlIIIlIIII.right1);
        if (llllllllllllllllIllIllIlIIIlIIII.left2 != null) {
            llllllllllllllllIllIllIlIIIlIIII.left2Width = llllllllllllllllIllIllIlIIIIllll.textWidth(llllllllllllllllIllIllIlIIIlIIII.left2);
            llllllllllllllllIllIllIlIIIlIIIl = Math.max(llllllllllllllllIllIllIlIIIlIIIl, llllllllllllllllIllIllIlIIIlIIII.left2Width + llllllllllllllllIllIllIlIIIIllll.textWidth(llllllllllllllllIllIllIlIIIlIIII.right2));
        }
        llllllllllllllllIllIllIlIIIlIIII.box.setSize(llllllllllllllllIllIllIlIIIlIIIl, llllllllllllllllIllIllIlIIIIllll.textHeight() * 2.0 + 2.0);
    }
}

