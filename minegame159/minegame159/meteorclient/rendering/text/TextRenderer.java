/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.rendering.text;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.rendering.text.VanillaTextRenderer;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.render.color.Color;

public interface TextRenderer {
    public boolean isBuilding();

    default public double getWidth(String string) {
        return this.getWidth(string, string.length());
    }

    default public double render(String string, double d, double d2, Color color) {
        return this.render(string, d, d2, color, false);
    }

    public double getHeight();

    default public void begin() {
        this.begin(1.0, false, false);
    }

    public void end();

    default public void begin(double d) {
        this.begin(d, false, false);
    }

    public double render(String var1, double var2, double var4, Color var6, boolean var7);

    public static TextRenderer get() {
        return Config.get().customFont ? MeteorClient.FONT : VanillaTextRenderer.INSTANCE;
    }

    default public void beginBig() {
        this.begin(1.0, false, true);
    }

    public double getWidth(String var1, int var2);

    public void setAlpha(double var1);

    public void begin(double var1, boolean var3, boolean var4);
}

