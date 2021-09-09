/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class WatermarkHud
extends DoubleTextHudElement {
    @Override
    protected String getRight() {
        return Config.get().version.getOriginalString();
    }

    public WatermarkHud(HUD llIIllIIIIlIlll) {
        super(llIIllIIIIlIlll, "watermark", "Displays a Meteor Client watermark.", "Crystal Client ");
        WatermarkHud llIIllIIIIllIII;
    }
}

