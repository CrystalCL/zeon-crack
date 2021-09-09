/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.world.TickRate;

public class LagNotifierHud
extends DoubleTextHudElement {
    private static final /* synthetic */ Color YELLOW;
    private static final /* synthetic */ Color AMBER;
    private static final /* synthetic */ Color RED;

    public LagNotifierHud(HUD llllllllllllllllllIlIlIIIlIlIlIl) {
        super(llllllllllllllllllIlIlIIIlIlIlIl, "lag-notifier", "Displays if the server is lagging in ticks.", "Time since last tick ");
        LagNotifierHud llllllllllllllllllIlIlIIIlIlIllI;
    }

    @Override
    protected String getRight() {
        LagNotifierHud llllllllllllllllllIlIlIIIlIIlllI;
        if (llllllllllllllllllIlIlIIIlIIlllI.isInEditor()) {
            llllllllllllllllllIlIlIIIlIIlllI.rightColor = RED;
            llllllllllllllllllIlIlIIIlIIlllI.visible = true;
            return "4,3";
        }
        float llllllllllllllllllIlIlIIIlIIllll = TickRate.INSTANCE.getTimeSinceLastTick();
        llllllllllllllllllIlIlIIIlIIlllI.rightColor = llllllllllllllllllIlIlIIIlIIllll > 10.0f ? RED : (llllllllllllllllllIlIlIIIlIIllll > 3.0f ? AMBER : YELLOW);
        llllllllllllllllllIlIlIIIlIIlllI.visible = llllllllllllllllllIlIlIIIlIIllll >= 1.0f;
        return String.format("%.1f", Float.valueOf(llllllllllllllllllIlIlIIIlIIllll));
    }

    static {
        RED = new Color(225, 45, 45);
        AMBER = new Color(235, 158, 52);
        YELLOW = new Color(255, 255, 5);
    }
}

