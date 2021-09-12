/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class WatermarkHud
extends DoubleTextHudElement {
    public WatermarkHud(HUD hUD) {
        super(hUD, "watermark", "Displays a Meteor Client watermark.", "Crystal Client ");
    }

    @Override
    protected String getRight() {
        return Config.get().version.getOriginalString();
    }
}

