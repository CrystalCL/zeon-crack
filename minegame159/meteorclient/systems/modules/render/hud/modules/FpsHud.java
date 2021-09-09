/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.mixin.MinecraftClientAccessor;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class FpsHud
extends DoubleTextHudElement {
    public FpsHud(HUD llllllllllllllllllIllIllllIlIlII) {
        super(llllllllllllllllllIllIllllIlIlII, "fps", "Displays your FPS.", "FPS: ");
        FpsHud llllllllllllllllllIllIllllIlIlIl;
    }

    @Override
    protected String getRight() {
        FpsHud llllllllllllllllllIllIllllIIllll;
        return Integer.toString(((MinecraftClientAccessor)llllllllllllllllllIllIllllIIllll.mc).getFps());
    }
}

