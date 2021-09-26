/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class DurabilityHud
extends DoubleTextHudElement {
    public DurabilityHud(HUD hUD) {
        super(hUD, "durability", "Displays durability of the item you are holding.", "Durability: ");
    }

    @Override
    protected String getRight() {
        if (this.isInEditor()) {
            return "159";
        }
        Integer n = null;
        if (!this.mc.player.getMainHandStack().isEmpty() && this.mc.player.getMainHandStack().isDamageable()) {
            n = this.mc.player.getMainHandStack().getMaxDamage() - this.mc.player.getMainHandStack().getDamage();
        }
        return n == null ? "" : n.toString();
    }
}

