package crystal.addons.modules.hud.client;

import meteordevelopment.meteorclient.renderer.GL;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.render.hud.HUD;
import meteordevelopment.meteorclient.systems.modules.render.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.render.hud.modules.HudElement;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.render.color.RainbowColor;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Identifier;

public class IconHud extends HudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> scale = sgGeneral.add((((new DoubleSetting.Builder()).name("scale")).description("The scale.")).defaultValue(2.0D).min(1.0D).sliderMin(1.0D).sliderMax(5.0D).build());
    public final Setting<Boolean> chroma = sgGeneral.add(((((new BoolSetting.Builder()).name("chroma")).description("Chroma logo animation.")).defaultValue(false)).build());
    private final Setting<Double> chromaSpeed = sgGeneral.add((((new DoubleSetting.Builder()).name("chroma-speed")).description("Speed of the chroma animation.")).defaultValue(0.09D).min(0.01D).sliderMax(5.0D).decimalPlaces(2).build());
    private final Setting<SettingColor> color = sgGeneral.add(((((new ColorSetting.Builder()).name("background-color")).description("Color of the background.")).defaultValue(new SettingColor(255, 255, 255))).build());

    private static final RainbowColor RAINBOW = new RainbowColor();
    private static final Identifier LOGO = new Identifier("crystal", "hud/logo.png");
    private static final Identifier LOGO_FLAT = new Identifier("crystal", "hud/logo_flat.png");

    public IconHud(HUD hud) {
        super(hud, "Logo", "Displays a logo of CrystalCL Addon.");
    }

    public void update(HudRenderer renderer) {
        box.setSize(78.0D * scale.get(), 78.0D * scale.get());
    }

    public void render(HudRenderer renderer) {
        if (Utils.canUpdate()) {
            double x = box.getX();
            double y = box.getY();
            int w = (int) box.width;
            int h = (int) box.height;
            if (chroma.get()) {
                GL.bindTexture(LOGO_FLAT);
            } else {
                GL.bindTexture(LOGO);
            }

            Renderer2D.TEXTURE.begin();
            if (chroma.get()) {
                RAINBOW.setSpeed(chromaSpeed.get() / 100.0D);
                Renderer2D.TEXTURE.texQuad(x, y, w, h, RAINBOW.getNext());
            } else {
                Renderer2D.TEXTURE.texQuad(x, y, w, h, color.get());
            }

            Renderer2D.TEXTURE.render(null);
        }
    }
}
