package crystal.addons.modules.hud.client;

import meteordevelopment.meteorclient.systems.modules.render.hud.HUD;
import meteordevelopment.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class WatermarkHud extends DoubleTextHudElement {
    public WatermarkHud(HUD hud) {
        super(hud, "CrystalCL-watermark", "Display CrystalCL Watermark!.", "");
    }

    protected String getRight() {
        return "CrystalCL 0.3";
    }
}
