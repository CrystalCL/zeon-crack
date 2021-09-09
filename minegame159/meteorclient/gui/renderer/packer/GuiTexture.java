/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.renderer.packer;

import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.gui.renderer.packer.TextureRegion;

public class GuiTexture {
    private final /* synthetic */ List<TextureRegion> regions;

    public TextureRegion get(double llllllllllllllllllIlllllIllllIIl, double llllllllllllllllllIlllllIllllIII) {
        GuiTexture llllllllllllllllllIlllllIllllIlI;
        double llllllllllllllllllIlllllIlllIlll = Math.sqrt(llllllllllllllllllIlllllIllllIIl * llllllllllllllllllIlllllIllllIIl + llllllllllllllllllIlllllIllllIII * llllllllllllllllllIlllllIllllIII);
        double llllllllllllllllllIlllllIlllIllI = Double.MAX_VALUE;
        TextureRegion llllllllllllllllllIlllllIlllIlIl = null;
        for (TextureRegion llllllllllllllllllIlllllIllllIll : llllllllllllllllllIlllllIllllIlI.regions) {
            double llllllllllllllllllIlllllIlllllII = Math.abs(llllllllllllllllllIlllllIlllIlll - llllllllllllllllllIlllllIllllIll.diagonal);
            if (!(llllllllllllllllllIlllllIlllllII < llllllllllllllllllIlllllIlllIllI)) continue;
            llllllllllllllllllIlllllIlllIllI = llllllllllllllllllIlllllIlllllII;
            llllllllllllllllllIlllllIlllIlIl = llllllllllllllllllIlllllIllllIll;
        }
        return llllllllllllllllllIlllllIlllIlIl;
    }

    public GuiTexture() {
        GuiTexture llllllllllllllllllIllllllIIIllII;
        llllllllllllllllllIllllllIIIllII.regions = new ArrayList<TextureRegion>(2);
    }

    void add(TextureRegion llllllllllllllllllIllllllIIIlIII) {
        GuiTexture llllllllllllllllllIllllllIIIIlll;
        llllllllllllllllllIllllllIIIIlll.regions.add(llllllllllllllllllIllllllIIIlIII);
    }
}

