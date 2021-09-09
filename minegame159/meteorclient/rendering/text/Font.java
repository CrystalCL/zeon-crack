/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.texture.AbstractTexture
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.stb.STBTTFontinfo
 *  org.lwjgl.stb.STBTTPackContext
 *  org.lwjgl.stb.STBTTPackedchar
 *  org.lwjgl.stb.STBTTPackedchar$Buffer
 *  org.lwjgl.stb.STBTruetype
 *  org.lwjgl.system.MemoryStack
 */
package minegame159.meteorclient.rendering.text;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.rendering.text.CharData;
import minegame159.meteorclient.utils.render.ByteTexture;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.client.texture.AbstractTexture;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

public class Font {
    public final /* synthetic */ AbstractTexture texture;
    private final /* synthetic */ float scale;
    private final /* synthetic */ int height;
    private final /* synthetic */ float ascent;
    private final /* synthetic */ CharData[] charData;

    public double getHeight() {
        Font lllllllllllllllllIIlIlllllllIlIl;
        return lllllllllllllllllIIlIlllllllIlIl.height;
    }

    public double getWidth(String lllllllllllllllllIIllIIIIIIIIIIl, int lllllllllllllllllIIlIlllllllllII) {
        double lllllllllllllllllIIlIlllllllllll = 0.0;
        for (int lllllllllllllllllIIllIIIIIIIIIll = 0; lllllllllllllllllIIllIIIIIIIIIll < lllllllllllllllllIIlIlllllllllII; ++lllllllllllllllllIIllIIIIIIIIIll) {
            Font lllllllllllllllllIIlIllllllllllI;
            int lllllllllllllllllIIllIIIIIIIIlIl = lllllllllllllllllIIllIIIIIIIIIIl.charAt(lllllllllllllllllIIllIIIIIIIIIll);
            if (lllllllllllllllllIIllIIIIIIIIlIl < 32 || lllllllllllllllllIIllIIIIIIIIlIl > 128) {
                lllllllllllllllllIIllIIIIIIIIlIl = 32;
            }
            CharData lllllllllllllllllIIllIIIIIIIIlII = lllllllllllllllllIIlIllllllllllI.charData[lllllllllllllllllIIllIIIIIIIIlIl - 32];
            lllllllllllllllllIIlIlllllllllll += (double)lllllllllllllllllIIllIIIIIIIIlII.xAdvance;
        }
        return lllllllllllllllllIIlIlllllllllll;
    }

    public Font(ByteBuffer lllllllllllllllllIIllIIIIIIlIllI, int lllllllllllllllllIIllIIIIIIlllII) {
        Font lllllllllllllllllIIllIIIIIIlIlll;
        lllllllllllllllllIIllIIIIIIlIlll.height = lllllllllllllllllIIllIIIIIIlllII;
        STBTTFontinfo lllllllllllllllllIIllIIIIIIllIll = STBTTFontinfo.create();
        STBTruetype.stbtt_InitFont((STBTTFontinfo)lllllllllllllllllIIllIIIIIIllIll, (ByteBuffer)lllllllllllllllllIIllIIIIIIlIllI);
        lllllllllllllllllIIllIIIIIIlIlll.charData = new CharData[128];
        STBTTPackedchar.Buffer lllllllllllllllllIIllIIIIIIllIlI = STBTTPackedchar.create((int)lllllllllllllllllIIllIIIIIIlIlll.charData.length);
        ByteBuffer lllllllllllllllllIIllIIIIIIllIIl = BufferUtils.createByteBuffer((int)0x400000);
        STBTTPackContext lllllllllllllllllIIllIIIIIIllIII = STBTTPackContext.create();
        STBTruetype.stbtt_PackBegin((STBTTPackContext)lllllllllllllllllIIllIIIIIIllIII, (ByteBuffer)lllllllllllllllllIIllIIIIIIllIIl, (int)2048, (int)2048, (int)0, (int)1);
        STBTruetype.stbtt_PackSetOversampling((STBTTPackContext)lllllllllllllllllIIllIIIIIIllIII, (int)2, (int)2);
        STBTruetype.stbtt_PackFontRange((STBTTPackContext)lllllllllllllllllIIllIIIIIIllIII, (ByteBuffer)lllllllllllllllllIIllIIIIIIlIllI, (int)0, (float)lllllllllllllllllIIllIIIIIIlllII, (int)32, (STBTTPackedchar.Buffer)lllllllllllllllllIIllIIIIIIllIlI);
        STBTruetype.stbtt_PackEnd((STBTTPackContext)lllllllllllllllllIIllIIIIIIllIII);
        lllllllllllllllllIIllIIIIIIlIlll.texture = new ByteTexture(2048, 2048, lllllllllllllllllIIllIIIIIIllIIl, ByteTexture.Format.A, ByteTexture.Filter.Linear, ByteTexture.Filter.Linear);
        lllllllllllllllllIIllIIIIIIlIlll.scale = STBTruetype.stbtt_ScaleForPixelHeight((STBTTFontinfo)lllllllllllllllllIIllIIIIIIllIll, (float)lllllllllllllllllIIllIIIIIIlllII);
        try (MemoryStack lllllllllllllllllIIllIIIIIlIIIll = MemoryStack.stackPush();){
            IntBuffer lllllllllllllllllIIllIIIIIlIIlII = lllllllllllllllllIIllIIIIIlIIIll.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics((STBTTFontinfo)lllllllllllllllllIIllIIIIIIllIll, (IntBuffer)lllllllllllllllllIIllIIIIIlIIlII, null, null);
            lllllllllllllllllIIllIIIIIIlIlll.ascent = lllllllllllllllllIIllIIIIIlIIlII.get(0);
        }
        for (int lllllllllllllllllIIllIIIIIIlllll = 0; lllllllllllllllllIIllIIIIIIlllll < lllllllllllllllllIIllIIIIIIlIlll.charData.length; ++lllllllllllllllllIIllIIIIIIlllll) {
            STBTTPackedchar lllllllllllllllllIIllIIIIIlIIIlI = (STBTTPackedchar)lllllllllllllllllIIllIIIIIIllIlI.get(lllllllllllllllllIIllIIIIIIlllll);
            float lllllllllllllllllIIllIIIIIlIIIIl = 4.8828125E-4f;
            float lllllllllllllllllIIllIIIIIlIIIII = 4.8828125E-4f;
            lllllllllllllllllIIllIIIIIIlIlll.charData[lllllllllllllllllIIllIIIIIIlllll] = new CharData(lllllllllllllllllIIllIIIIIlIIIlI.xoff(), lllllllllllllllllIIllIIIIIlIIIlI.yoff(), lllllllllllllllllIIllIIIIIlIIIlI.xoff2(), lllllllllllllllllIIllIIIIIlIIIlI.yoff2(), (float)lllllllllllllllllIIllIIIIIlIIIlI.x0() * lllllllllllllllllIIllIIIIIlIIIIl, (float)lllllllllllllllllIIllIIIIIlIIIlI.y0() * lllllllllllllllllIIllIIIIIlIIIII, (float)lllllllllllllllllIIllIIIIIlIIIlI.x1() * lllllllllllllllllIIllIIIIIlIIIIl, (float)lllllllllllllllllIIllIIIIIlIIIlI.y1() * lllllllllllllllllIIllIIIIIlIIIII, lllllllllllllllllIIllIIIIIlIIIlI.xadvance());
        }
    }

    public double render(MeshBuilder lllllllllllllllllIIlIllllllIIllI, String lllllllllllllllllIIlIllllllIIlIl, double lllllllllllllllllIIlIlllllIlllIl, double lllllllllllllllllIIlIlllllIlllII, Color lllllllllllllllllIIlIlllllIllIll, double lllllllllllllllllIIlIllllllIIIIl) {
        Font lllllllllllllllllIIlIllllllIIlll;
        lllllllllllllllllIIlIlllllIlllII += (double)(lllllllllllllllllIIlIllllllIIlll.ascent * lllllllllllllllllIIlIllllllIIlll.scale) * lllllllllllllllllIIlIllllllIIIIl;
        for (int lllllllllllllllllIIlIllllllIlIII = 0; lllllllllllllllllIIlIllllllIlIII < lllllllllllllllllIIlIllllllIIlIl.length(); ++lllllllllllllllllIIlIllllllIlIII) {
            int lllllllllllllllllIIlIllllllIlIlI = lllllllllllllllllIIlIllllllIIlIl.charAt(lllllllllllllllllIIlIllllllIlIII);
            if (lllllllllllllllllIIlIllllllIlIlI < 32 || lllllllllllllllllIIlIllllllIlIlI > 128) {
                lllllllllllllllllIIlIllllllIlIlI = 32;
            }
            CharData lllllllllllllllllIIlIllllllIlIIl = lllllllllllllllllIIlIllllllIIlll.charData[lllllllllllllllllIIlIllllllIlIlI - 32];
            lllllllllllllllllIIlIllllllIIllI.pos(lllllllllllllllllIIlIlllllIlllIl + (double)lllllllllllllllllIIlIllllllIlIIl.x0 * lllllllllllllllllIIlIllllllIIIIl, lllllllllllllllllIIlIlllllIlllII + (double)lllllllllllllllllIIlIllllllIlIIl.y0 * lllllllllllllllllIIlIllllllIIIIl, 0.0).color(lllllllllllllllllIIlIlllllIllIll).texture(lllllllllllllllllIIlIllllllIlIIl.u0, lllllllllllllllllIIlIllllllIlIIl.v0).endVertex();
            lllllllllllllllllIIlIllllllIIllI.pos(lllllllllllllllllIIlIlllllIlllIl + (double)lllllllllllllllllIIlIllllllIlIIl.x1 * lllllllllllllllllIIlIllllllIIIIl, lllllllllllllllllIIlIlllllIlllII + (double)lllllllllllllllllIIlIllllllIlIIl.y0 * lllllllllllllllllIIlIllllllIIIIl, 0.0).color(lllllllllllllllllIIlIlllllIllIll).texture(lllllllllllllllllIIlIllllllIlIIl.u1, lllllllllllllllllIIlIllllllIlIIl.v0).endVertex();
            lllllllllllllllllIIlIllllllIIllI.pos(lllllllllllllllllIIlIlllllIlllIl + (double)lllllllllllllllllIIlIllllllIlIIl.x1 * lllllllllllllllllIIlIllllllIIIIl, lllllllllllllllllIIlIlllllIlllII + (double)lllllllllllllllllIIlIllllllIlIIl.y1 * lllllllllllllllllIIlIllllllIIIIl, 0.0).color(lllllllllllllllllIIlIlllllIllIll).texture(lllllllllllllllllIIlIllllllIlIIl.u1, lllllllllllllllllIIlIllllllIlIIl.v1).endVertex();
            lllllllllllllllllIIlIllllllIIllI.pos(lllllllllllllllllIIlIlllllIlllIl + (double)lllllllllllllllllIIlIllllllIlIIl.x0 * lllllllllllllllllIIlIllllllIIIIl, lllllllllllllllllIIlIlllllIlllII + (double)lllllllllllllllllIIlIllllllIlIIl.y0 * lllllllllllllllllIIlIllllllIIIIl, 0.0).color(lllllllllllllllllIIlIlllllIllIll).texture(lllllllllllllllllIIlIllllllIlIIl.u0, lllllllllllllllllIIlIllllllIlIIl.v0).endVertex();
            lllllllllllllllllIIlIllllllIIllI.pos(lllllllllllllllllIIlIlllllIlllIl + (double)lllllllllllllllllIIlIllllllIlIIl.x1 * lllllllllllllllllIIlIllllllIIIIl, lllllllllllllllllIIlIlllllIlllII + (double)lllllllllllllllllIIlIllllllIlIIl.y1 * lllllllllllllllllIIlIllllllIIIIl, 0.0).color(lllllllllllllllllIIlIlllllIllIll).texture(lllllllllllllllllIIlIllllllIlIIl.u1, lllllllllllllllllIIlIllllllIlIIl.v1).endVertex();
            lllllllllllllllllIIlIllllllIIllI.pos(lllllllllllllllllIIlIlllllIlllIl + (double)lllllllllllllllllIIlIllllllIlIIl.x0 * lllllllllllllllllIIlIllllllIIIIl, lllllllllllllllllIIlIlllllIlllII + (double)lllllllllllllllllIIlIllllllIlIIl.y1 * lllllllllllllllllIIlIllllllIIIIl, 0.0).color(lllllllllllllllllIIlIlllllIllIll).texture(lllllllllllllllllIIlIllllllIlIIl.u0, lllllllllllllllllIIlIllllllIlIIl.v1).endVertex();
            lllllllllllllllllIIlIlllllIlllIl += (double)lllllllllllllllllIIlIllllllIlIIl.xAdvance * lllllllllllllllllIIlIllllllIIIIl;
        }
        return lllllllllllllllllIIlIlllllIlllIl;
    }
}

