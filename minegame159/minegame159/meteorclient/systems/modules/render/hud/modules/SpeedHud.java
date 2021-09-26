/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import minegame159.meteorclient.systems.modules.world.Timer;

public class SpeedHud
extends DoubleTextHudElement {
    public SpeedHud(HUD hUD) {
        super(hUD, "speed", "Displays your horizontal speed.", "Speed: ");
    }

    @Override
    protected String getRight() {
        if (this.isInEditor()) {
            return "0,0";
        }
        double d = Math.abs(this.mc.player.getX() - this.mc.player.prevX);
        double d2 = Math.abs(this.mc.player.getZ() - this.mc.player.prevZ);
        double d3 = Math.sqrt(d * d + d2 * d2);
        if (Modules.get().isActive(Timer.class)) {
            d3 *= Modules.get().get(Timer.class).getMultiplier();
        }
        return String.format("%.1f", d3 * 20.0);
    }
}

