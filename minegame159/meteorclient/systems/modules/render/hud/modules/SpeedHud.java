/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import minegame159.meteorclient.systems.modules.world.Timer;

public class SpeedHud
extends DoubleTextHudElement {
    @Override
    protected String getRight() {
        SpeedHud lllllllllllllllllIllllllIlIIIIlI;
        if (lllllllllllllllllIllllllIlIIIIlI.isInEditor()) {
            return "0,0";
        }
        double lllllllllllllllllIllllllIlIIIIIl = Math.abs(lllllllllllllllllIllllllIlIIIIlI.mc.player.getX() - lllllllllllllllllIllllllIlIIIIlI.mc.player.prevX);
        double lllllllllllllllllIllllllIlIIIIII = Math.abs(lllllllllllllllllIllllllIlIIIIlI.mc.player.getZ() - lllllllllllllllllIllllllIlIIIIlI.mc.player.prevZ);
        double lllllllllllllllllIllllllIIllllll = Math.sqrt(lllllllllllllllllIllllllIlIIIIIl * lllllllllllllllllIllllllIlIIIIIl + lllllllllllllllllIllllllIlIIIIII * lllllllllllllllllIllllllIlIIIIII);
        if (Modules.get().isActive(Timer.class)) {
            lllllllllllllllllIllllllIIllllll *= Modules.get().get(Timer.class).getMultiplier();
        }
        return String.format("%.1f", lllllllllllllllllIllllllIIllllll * 20.0);
    }

    public SpeedHud(HUD lllllllllllllllllIllllllIlIIIlll) {
        super(lllllllllllllllllIllllllIlIIIlll, "speed", "Displays your horizontal speed.", "Speed: ");
        SpeedHud lllllllllllllllllIllllllIlIIlIlI;
    }
}

