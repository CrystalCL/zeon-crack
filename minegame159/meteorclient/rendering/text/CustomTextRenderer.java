/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.render.VertexFormats
 *  org.lwjgl.BufferUtils
 */
package minegame159.meteorclient.rendering.text;

import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.rendering.text.Font;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.BufferUtils;

public class CustomTextRenderer
implements TextRenderer {
    private /* synthetic */ boolean scaleOnly;
    private static final /* synthetic */ Color SHADOW_COLOR;
    private final /* synthetic */ Font[] fonts;
    private /* synthetic */ boolean building;
    private final /* synthetic */ MeshBuilder mb;
    private /* synthetic */ double scale;
    private /* synthetic */ Font font;

    @Override
    public boolean isBuilding() {
        CustomTextRenderer llllllllllllllllllllIlIllIIlIIlI;
        return llllllllllllllllllllIlIllIIlIIlI.building;
    }

    @Override
    public void setAlpha(double llllllllllllllllllllIlIlllIlIlll) {
        llllllllllllllllllllIlIlllIllIII.mb.alpha = llllllllllllllllllllIlIlllIlIlll;
    }

    @Override
    public double render(String llllllllllllllllllllIlIllIlIIIll, double llllllllllllllllllllIlIllIIllIlI, double llllllllllllllllllllIlIllIIllIIl, Color llllllllllllllllllllIlIllIIllIII, boolean llllllllllllllllllllIlIllIIlllll) {
        double llllllllllllllllllllIlIllIIlllIl;
        CustomTextRenderer llllllllllllllllllllIlIllIIlllII;
        boolean llllllllllllllllllllIlIllIIllllI = llllllllllllllllllllIlIllIIlllII.building;
        if (!llllllllllllllllllllIlIllIIllllI) {
            llllllllllllllllllllIlIllIIlllII.begin();
        }
        if (llllllllllllllllllllIlIllIIlllll) {
            double llllllllllllllllllllIlIllIlIIlIl = llllllllllllllllllllIlIllIIlllII.font.render(llllllllllllllllllllIlIllIIlllII.mb, llllllllllllllllllllIlIllIlIIIll, llllllllllllllllllllIlIllIIllIlI + 1.0, llllllllllllllllllllIlIllIIllIIl + 1.0, SHADOW_COLOR, llllllllllllllllllllIlIllIIlllII.scale);
            llllllllllllllllllllIlIllIIlllII.font.render(llllllllllllllllllllIlIllIIlllII.mb, llllllllllllllllllllIlIllIlIIIll, llllllllllllllllllllIlIllIIllIlI, llllllllllllllllllllIlIllIIllIIl, llllllllllllllllllllIlIllIIllIII, llllllllllllllllllllIlIllIIlllII.scale);
        } else {
            llllllllllllllllllllIlIllIIlllIl = llllllllllllllllllllIlIllIIlllII.font.render(llllllllllllllllllllIlIllIIlllII.mb, llllllllllllllllllllIlIllIlIIIll, llllllllllllllllllllIlIllIIllIlI, llllllllllllllllllllIlIllIIllIIl, llllllllllllllllllllIlIllIIllIII, llllllllllllllllllllIlIllIIlllII.scale);
        }
        if (!llllllllllllllllllllIlIllIIllllI) {
            llllllllllllllllllllIlIllIIlllII.end();
        }
        return llllllllllllllllllllIlIllIIlllIl;
    }

    @Override
    public double getHeight() {
        CustomTextRenderer llllllllllllllllllllIlIllIllIIIl;
        Font llllllllllllllllllllIlIllIllIIII = llllllllllllllllllllIlIllIllIIIl.building ? llllllllllllllllllllIlIllIllIIIl.font : llllllllllllllllllllIlIllIllIIIl.fonts[0];
        return llllllllllllllllllllIlIllIllIIII.getHeight() * llllllllllllllllllllIlIllIllIIIl.scale;
    }

    @Override
    public void begin(double llllllllllllllllllllIlIlllIIIlII, boolean llllllllllllllllllllIlIlllIIlIII, boolean llllllllllllllllllllIlIlllIIIIlI) {
        CustomTextRenderer llllllllllllllllllllIlIlllIIIlIl;
        if (llllllllllllllllllllIlIlllIIIlIl.building) {
            throw new RuntimeException("CustomTextRenderer.begin() called twice");
        }
        if (!llllllllllllllllllllIlIlllIIlIII) {
            llllllllllllllllllllIlIlllIIIlIl.mb.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR_TEXTURE);
        }
        if (llllllllllllllllllllIlIlllIIIIlI) {
            llllllllllllllllllllIlIlllIIIlIl.font = llllllllllllllllllllIlIlllIIIlIl.fonts[llllllllllllllllllllIlIlllIIIlIl.fonts.length - 1];
        } else {
            boolean llllllllllllllllllllIlIlllIIlIll;
            double llllllllllllllllllllIlIlllIIllII = Math.floor(llllllllllllllllllllIlIlllIIIlII * 10.0) / 10.0;
            if (llllllllllllllllllllIlIlllIIllII >= 3.0) {
                int llllllllllllllllllllIlIlllIlIIII = 5;
            } else if (llllllllllllllllllllIlIlllIIllII >= 2.5) {
                int llllllllllllllllllllIlIlllIIllll = 4;
            } else if (llllllllllllllllllllIlIlllIIllII >= 2.0) {
                int llllllllllllllllllllIlIlllIIlllI = 3;
            } else if (llllllllllllllllllllIlIlllIIllII >= 1.5) {
                int llllllllllllllllllllIlIlllIIllIl = 2;
            } else {
                llllllllllllllllllllIlIlllIIlIll = true;
            }
            llllllllllllllllllllIlIlllIIIlIl.font = llllllllllllllllllllIlIlllIIIlIl.fonts[llllllllllllllllllllIlIlllIIlIll - true];
        }
        llllllllllllllllllllIlIlllIIIlIl.building = true;
        llllllllllllllllllllIlIlllIIIlIl.scaleOnly = llllllllllllllllllllIlIlllIIlIII;
        double llllllllllllllllllllIlIlllIIIllI = llllllllllllllllllllIlIlllIIIlIl.font.getHeight() / 18.0;
        llllllllllllllllllllIlIlllIIIlIl.scale = 1.0 + (llllllllllllllllllllIlIlllIIIlII - llllllllllllllllllllIlIlllIIIllI) / llllllllllllllllllllIlIlllIIIllI;
    }

    public CustomTextRenderer(File llllllllllllllllllllIlIllllIIlII) {
        CustomTextRenderer llllllllllllllllllllIlIllllIIIIl;
        llllllllllllllllllllIlIllllIIIIl.mb = new MeshBuilder(16384);
        byte[] llllllllllllllllllllIlIllllIIIll = Utils.readBytes(llllllllllllllllllllIlIllllIIlII);
        ByteBuffer llllllllllllllllllllIlIllllIIIlI = BufferUtils.createByteBuffer((int)llllllllllllllllllllIlIllllIIIll.length).put(llllllllllllllllllllIlIllllIIIll);
        llllllllllllllllllllIlIllllIIIIl.fonts = new Font[5];
        for (int llllllllllllllllllllIlIllllIIllI = 0; llllllllllllllllllllIlIllllIIllI < llllllllllllllllllllIlIllllIIIIl.fonts.length; ++llllllllllllllllllllIlIllllIIllI) {
            ((Buffer)llllllllllllllllllllIlIllllIIIlI).flip();
            llllllllllllllllllllIlIllllIIIIl.fonts[llllllllllllllllllllIlIllllIIllI] = new Font(llllllllllllllllllllIlIllllIIIlI, (int)Math.round(18.0 * ((double)llllllllllllllllllllIlIllllIIllI * 0.5 + 1.0)));
        }
        llllllllllllllllllllIlIllllIIIIl.mb.texture = true;
    }

    static {
        SHADOW_COLOR = new Color(60, 60, 60, 180);
    }

    @Override
    public void end() {
        CustomTextRenderer llllllllllllllllllllIlIllIIIllll;
        if (!llllllllllllllllllllIlIllIIIllll.building) {
            throw new RuntimeException("CustomTextRenderer.end() called without calling begin()");
        }
        if (!llllllllllllllllllllIlIllIIIllll.scaleOnly) {
            llllllllllllllllllllIlIllIIIllll.font.texture.bindTexture();
            llllllllllllllllllllIlIllIIIllll.mb.end();
        }
        llllllllllllllllllllIlIllIIIllll.building = false;
        llllllllllllllllllllIlIllIIIllll.scale = 1.0;
    }

    @Override
    public double getWidth(String llllllllllllllllllllIlIllIlllIlI, int llllllllllllllllllllIlIllIllIlIl) {
        CustomTextRenderer llllllllllllllllllllIlIllIllIlll;
        Font llllllllllllllllllllIlIllIlllIII = llllllllllllllllllllIlIllIllIlll.building ? llllllllllllllllllllIlIllIllIlll.font : llllllllllllllllllllIlIllIllIlll.fonts[0];
        return llllllllllllllllllllIlIllIlllIII.getWidth(llllllllllllllllllllIlIllIlllIlI, llllllllllllllllllllIlIllIllIlIl) * llllllllllllllllllllIlIllIllIlll.scale;
    }
}

