/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import minegame159.meteorclient.utils.world.TickRate;

public class TpsHud
extends DoubleTextHudElement {
    public TpsHud(HUD llllllllllllllllllIlIllIlIlllIIl) {
        super(llllllllllllllllllIlIllIlIlllIIl, "tps", "Displays the server's TPS.", "TPS: ");
        TpsHud llllllllllllllllllIlIllIlIlllIlI;
    }

    @Override
    protected String getRight() {
        return String.format("%.1f", Float.valueOf(TickRate.INSTANCE.getTickRate()));
    }
}

