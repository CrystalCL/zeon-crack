/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class DurabilityHud
extends DoubleTextHudElement {
    public DurabilityHud(HUD llllllllllllllllllllIIlIIlIlIIll) {
        super(llllllllllllllllllllIIlIIlIlIIll, "durability", "Displays durability of the item you are holding.", "Durability: ");
        DurabilityHud llllllllllllllllllllIIlIIlIlIlII;
    }

    @Override
    protected String getRight() {
        DurabilityHud llllllllllllllllllllIIlIIlIIlllI;
        if (llllllllllllllllllllIIlIIlIIlllI.isInEditor()) {
            return "159";
        }
        Integer llllllllllllllllllllIIlIIlIIllll = null;
        if (!llllllllllllllllllllIIlIIlIIlllI.mc.player.getMainHandStack().isEmpty() && llllllllllllllllllllIIlIIlIIlllI.mc.player.getMainHandStack().isDamageable()) {
            llllllllllllllllllllIIlIIlIIllll = llllllllllllllllllllIIlIIlIIlllI.mc.player.getMainHandStack().getMaxDamage() - llllllllllllllllllllIIlIIlIIlllI.mc.player.getMainHandStack().getDamage();
        }
        return llllllllllllllllllllIIlIIlIIllll == null ? "" : llllllllllllllllllllIIlIIlIIllll.toString();
    }
}

