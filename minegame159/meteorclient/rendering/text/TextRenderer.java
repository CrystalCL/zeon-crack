/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.rendering.text;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.rendering.text.VanillaTextRenderer;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.render.color.Color;

public interface TextRenderer {
    default public double getWidth(String llllllllllllllllllllIIlIIllIllII) {
        TextRenderer llllllllllllllllllllIIlIIllIllIl;
        return llllllllllllllllllllIIlIIllIllIl.getWidth(llllllllllllllllllllIIlIIllIllII, llllllllllllllllllllIIlIIllIllII.length());
    }

    public void begin(double var1, boolean var3, boolean var4);

    default public void begin(double llllllllllllllllllllIIlIIllllIII) {
        TextRenderer llllllllllllllllllllIIlIIlllIlll;
        llllllllllllllllllllIIlIIlllIlll.begin(llllllllllllllllllllIIlIIllllIII, false, false);
    }

    public double render(String var1, double var2, double var4, Color var6, boolean var7);

    public void end();

    default public void begin() {
        TextRenderer llllllllllllllllllllIIlIIlllIIll;
        llllllllllllllllllllIIlIIlllIIll.begin(1.0, false, false);
    }

    public double getWidth(String var1, int var2);

    public double getHeight();

    default public double render(String llllllllllllllllllllIIlIIllIIIll, double llllllllllllllllllllIIlIIlIlllIl, double llllllllllllllllllllIIlIIlIlllII, Color llllllllllllllllllllIIlIIlIllIll) {
        TextRenderer llllllllllllllllllllIIlIIllIIlII;
        return llllllllllllllllllllIIlIIllIIlII.render(llllllllllllllllllllIIlIIllIIIll, llllllllllllllllllllIIlIIlIlllIl, llllllllllllllllllllIIlIIlIlllII, llllllllllllllllllllIIlIIlIllIll, false);
    }

    public void setAlpha(double var1);

    public static TextRenderer get() {
        return Config.get().customFont ? MeteorClient.FONT : VanillaTextRenderer.INSTANCE;
    }

    public boolean isBuilding();

    default public void beginBig() {
        TextRenderer llllllllllllllllllllIIlIIlllIIII;
        llllllllllllllllllllIIlIIlllIIII.begin(1.0, false, true);
    }
}

