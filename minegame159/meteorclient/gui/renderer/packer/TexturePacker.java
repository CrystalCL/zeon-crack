/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.texture.TextureUtil
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.stb.STBImage
 *  org.lwjgl.stb.STBImageResize
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 */
package minegame159.meteorclient.gui.renderer.packer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.gui.renderer.packer.GuiTexture;
import minegame159.meteorclient.gui.renderer.packer.TextureRegion;
import minegame159.meteorclient.utils.render.ByteTexture;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class TexturePacker {
    private final /* synthetic */ List<Image> images;
    private static final /* synthetic */ MinecraftClient mc;
    private static final /* synthetic */ int maxWidth;

    private void addResized(GuiTexture lllllllllllllllllIIlIIllIlllIllI, ByteBuffer lllllllllllllllllIIlIIllIllIlIll, int lllllllllllllllllIIlIIllIlllIlII, int lllllllllllllllllIIlIIllIlllIIll, int lllllllllllllllllIIlIIllIlllIIlI) {
        TexturePacker lllllllllllllllllIIlIIllIlllIlll;
        double lllllllllllllllllIIlIIllIlllIIIl = (double)lllllllllllllllllIIlIIllIlllIIlI / (double)lllllllllllllllllIIlIIllIlllIlII;
        int lllllllllllllllllIIlIIllIlllIIII = (int)((double)lllllllllllllllllIIlIIllIlllIIll * lllllllllllllllllIIlIIllIlllIIIl);
        ByteBuffer lllllllllllllllllIIlIIllIllIllll = BufferUtils.createByteBuffer((int)(lllllllllllllllllIIlIIllIlllIIlI * lllllllllllllllllIIlIIllIlllIIII * 4));
        STBImageResize.stbir_resize_uint8((ByteBuffer)lllllllllllllllllIIlIIllIllIlIll, (int)lllllllllllllllllIIlIIllIlllIlII, (int)lllllllllllllllllIIlIIllIlllIIll, (int)0, (ByteBuffer)lllllllllllllllllIIlIIllIllIllll, (int)lllllllllllllllllIIlIIllIlllIIlI, (int)lllllllllllllllllIIlIIllIlllIIII, (int)0, (int)4);
        TextureRegion lllllllllllllllllIIlIIllIllIlllI = new TextureRegion(lllllllllllllllllIIlIIllIlllIIlI, lllllllllllllllllIIlIIllIlllIIII);
        lllllllllllllllllIIlIIllIlllIllI.add(lllllllllllllllllIIlIIllIllIlllI);
        lllllllllllllllllIIlIIllIlllIlll.images.add(new Image(lllllllllllllllllIIlIIllIllIllll, lllllllllllllllllIIlIIllIllIlllI, lllllllllllllllllIIlIIllIlllIIlI, lllllllllllllllllIIlIIllIlllIIII, false));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GuiTexture add(Identifier lllllllllllllllllIIlIIlllIIIlllI) {
        try {
            InputStream lllllllllllllllllIIlIIlllIIlIlII = mc.getResourceManager().getResource(lllllllllllllllllIIlIIlllIIIlllI).getInputStream();
            GuiTexture lllllllllllllllllIIlIIlllIIlIIll = new GuiTexture();
            try (MemoryStack lllllllllllllllllIIlIIlllIIlIlIl = MemoryStack.stackPush();){
                ByteBuffer lllllllllllllllllIIlIIlllIIlIllI = null;
                try {
                    TexturePacker lllllllllllllllllIIlIIlllIIIllll;
                    lllllllllllllllllIIlIIlllIIlIllI = TextureUtil.readAllToByteBuffer((InputStream)lllllllllllllllllIIlIIlllIIlIlII);
                    ((Buffer)lllllllllllllllllIIlIIlllIIlIllI).rewind();
                    IntBuffer lllllllllllllllllIIlIIlllIIllllI = lllllllllllllllllIIlIIlllIIlIlIl.mallocInt(1);
                    IntBuffer lllllllllllllllllIIlIIlllIIlllIl = lllllllllllllllllIIlIIlllIIlIlIl.mallocInt(1);
                    IntBuffer lllllllllllllllllIIlIIlllIIlllII = lllllllllllllllllIIlIIlllIIlIlIl.mallocInt(1);
                    ByteBuffer lllllllllllllllllIIlIIlllIIllIll = STBImage.stbi_load_from_memory((ByteBuffer)lllllllllllllllllIIlIIlllIIlIllI, (IntBuffer)lllllllllllllllllIIlIIlllIIllllI, (IntBuffer)lllllllllllllllllIIlIIlllIIlllIl, (IntBuffer)lllllllllllllllllIIlIIlllIIlllII, (int)4);
                    int lllllllllllllllllIIlIIlllIIllIlI = lllllllllllllllllIIlIIlllIIllllI.get(0);
                    int lllllllllllllllllIIlIIlllIIllIIl = lllllllllllllllllIIlIIlllIIlllIl.get(0);
                    TextureRegion lllllllllllllllllIIlIIlllIIllIII = new TextureRegion(lllllllllllllllllIIlIIlllIIllIlI, lllllllllllllllllIIlIIlllIIllIIl);
                    lllllllllllllllllIIlIIlllIIlIIll.add(lllllllllllllllllIIlIIlllIIllIII);
                    lllllllllllllllllIIlIIlllIIIllll.images.add(new Image(lllllllllllllllllIIlIIlllIIllIll, lllllllllllllllllIIlIIlllIIllIII, lllllllllllllllllIIlIIlllIIllIlI, lllllllllllllllllIIlIIlllIIllIIl, true));
                    if (lllllllllllllllllIIlIIlllIIllIlI > 20) {
                        lllllllllllllllllIIlIIlllIIIllll.addResized(lllllllllllllllllIIlIIlllIIlIIll, lllllllllllllllllIIlIIlllIIllIll, lllllllllllllllllIIlIIlllIIllIlI, lllllllllllllllllIIlIIlllIIllIIl, 20);
                    }
                    if (lllllllllllllllllIIlIIlllIIllIlI > 32) {
                        lllllllllllllllllIIlIIlllIIIllll.addResized(lllllllllllllllllIIlIIlllIIlIIll, lllllllllllllllllIIlIIlllIIllIll, lllllllllllllllllIIlIIlllIIllIlI, lllllllllllllllllIIlIIlllIIllIIl, 32);
                    }
                    if (lllllllllllllllllIIlIIlllIIllIlI > 48) {
                        lllllllllllllllllIIlIIlllIIIllll.addResized(lllllllllllllllllIIlIIlllIIlIIll, lllllllllllllllllIIlIIlllIIllIll, lllllllllllllllllIIlIIlllIIllIlI, lllllllllllllllllIIlIIlllIIllIIl, 48);
                    }
                }
                catch (IOException lllllllllllllllllIIlIIlllIIlIlll) {
                    lllllllllllllllllIIlIIlllIIlIlll.printStackTrace();
                }
                finally {
                    MemoryUtil.memFree((Buffer)lllllllllllllllllIIlIIlllIIlIllI);
                }
            }
            return lllllllllllllllllIIlIIlllIIlIIll;
        }
        catch (IOException lllllllllllllllllIIlIIlllIIlIIlI) {
            lllllllllllllllllIIlIIlllIIlIIlI.printStackTrace();
            return null;
        }
    }

    public ByteTexture pack() {
        TexturePacker lllllllllllllllllIIlIIllIlIIllll;
        int lllllllllllllllllIIlIIllIlIlIlII = 0;
        int lllllllllllllllllIIlIIllIlIlIIll = 0;
        int lllllllllllllllllIIlIIllIlIlIIlI = 0;
        int lllllllllllllllllIIlIIllIlIlIIIl = 0;
        for (Image lllllllllllllllllIIlIIllIlIllIIl : lllllllllllllllllIIlIIllIlIIllll.images) {
            if (lllllllllllllllllIIlIIllIlIlIIlI + lllllllllllllllllIIlIIllIlIllIIl.width > 2048) {
                lllllllllllllllllIIlIIllIlIlIlII = Math.max(lllllllllllllllllIIlIIllIlIlIlII, lllllllllllllllllIIlIIllIlIlIIlI);
                lllllllllllllllllIIlIIllIlIlIIll += lllllllllllllllllIIlIIllIlIlIIIl;
                lllllllllllllllllIIlIIllIlIlIIlI = 0;
                lllllllllllllllllIIlIIllIlIlIIIl = 0;
            }
            lllllllllllllllllIIlIIllIlIllIIl.x = 1 + lllllllllllllllllIIlIIllIlIlIIlI;
            lllllllllllllllllIIlIIllIlIllIIl.y = 1 + lllllllllllllllllIIlIIllIlIlIIll;
            lllllllllllllllllIIlIIllIlIlIIlI += 1 + lllllllllllllllllIIlIIllIlIllIIl.width + 1;
            lllllllllllllllllIIlIIllIlIlIIIl = Math.max(lllllllllllllllllIIlIIllIlIlIIIl, 1 + lllllllllllllllllIIlIIllIlIllIIl.height + 1);
        }
        lllllllllllllllllIIlIIllIlIlIlII = Math.max(lllllllllllllllllIIlIIllIlIlIlII, lllllllllllllllllIIlIIllIlIlIIlI);
        ByteBuffer lllllllllllllllllIIlIIllIlIlIIII = BufferUtils.createByteBuffer((int)(lllllllllllllllllIIlIIllIlIlIlII * (lllllllllllllllllIIlIIllIlIlIIll += lllllllllllllllllIIlIIllIlIlIIIl) * 4));
        for (Image lllllllllllllllllIIlIIllIlIlIllI : lllllllllllllllllIIlIIllIlIIllll.images) {
            byte[] lllllllllllllllllIIlIIllIlIlIlll = new byte[lllllllllllllllllIIlIIllIlIlIllI.width * 4];
            for (int lllllllllllllllllIIlIIllIlIllIII = 0; lllllllllllllllllIIlIIllIlIllIII < lllllllllllllllllIIlIIllIlIlIllI.height; ++lllllllllllllllllIIlIIllIlIllIII) {
                ((Buffer)lllllllllllllllllIIlIIllIlIlIllI.buffer).position(lllllllllllllllllIIlIIllIlIllIII * lllllllllllllllllIIlIIllIlIlIlll.length);
                lllllllllllllllllIIlIIllIlIlIllI.buffer.get(lllllllllllllllllIIlIIllIlIlIlll);
                ((Buffer)lllllllllllllllllIIlIIllIlIlIIII).position(((lllllllllllllllllIIlIIllIlIlIllI.y + lllllllllllllllllIIlIIllIlIllIII) * lllllllllllllllllIIlIIllIlIlIlII + lllllllllllllllllIIlIIllIlIlIllI.x) * 4);
                lllllllllllllllllIIlIIllIlIlIIII.put(lllllllllllllllllIIlIIllIlIlIlll);
            }
            ((Buffer)lllllllllllllllllIIlIIllIlIlIllI.buffer).rewind();
            lllllllllllllllllIIlIIllIlIlIllI.free();
            lllllllllllllllllIIlIIllIlIlIllI.region.x1 = (double)lllllllllllllllllIIlIIllIlIlIllI.x / (double)lllllllllllllllllIIlIIllIlIlIlII;
            lllllllllllllllllIIlIIllIlIlIllI.region.y1 = (double)lllllllllllllllllIIlIIllIlIlIllI.y / (double)lllllllllllllllllIIlIIllIlIlIIll;
            lllllllllllllllllIIlIIllIlIlIllI.region.x2 = (double)(lllllllllllllllllIIlIIllIlIlIllI.x + lllllllllllllllllIIlIIllIlIlIllI.width) / (double)lllllllllllllllllIIlIIllIlIlIlII;
            lllllllllllllllllIIlIIllIlIlIllI.region.y2 = (double)(lllllllllllllllllIIlIIllIlIlIllI.y + lllllllllllllllllIIlIIllIlIlIllI.height) / (double)lllllllllllllllllIIlIIllIlIlIIll;
        }
        ((Buffer)lllllllllllllllllIIlIIllIlIlIIII).rewind();
        return new ByteTexture(lllllllllllllllllIIlIIllIlIlIlII, lllllllllllllllllIIlIIllIlIlIIll, lllllllllllllllllIIlIIllIlIlIIII, ByteTexture.Format.RGBA, ByteTexture.Filter.Linear, ByteTexture.Filter.Linear);
    }

    static {
        maxWidth = 2048;
        mc = MinecraftClient.getInstance();
    }

    public TexturePacker() {
        TexturePacker lllllllllllllllllIIlIIlllIlIllIl;
        lllllllllllllllllIIlIIlllIlIllIl.images = new ArrayList<Image>();
    }

    private static class Image {
        public final /* synthetic */ TextureRegion region;
        private final /* synthetic */ boolean stb;
        public final /* synthetic */ int width;
        public /* synthetic */ int y;
        public final /* synthetic */ int height;
        public final /* synthetic */ ByteBuffer buffer;
        public /* synthetic */ int x;

        public void free() {
            Image lIllllllIIllI;
            if (lIllllllIIllI.stb) {
                STBImage.stbi_image_free((ByteBuffer)lIllllllIIllI.buffer);
            }
        }

        public Image(ByteBuffer lIllllllIllII, TextureRegion lIlllllllIIIl, int lIllllllIlIlI, int lIllllllIllll, boolean lIllllllIlllI) {
            Image lIlllllllIIll;
            lIlllllllIIll.buffer = lIllllllIllII;
            lIlllllllIIll.region = lIlllllllIIIl;
            lIlllllllIIll.width = lIllllllIlIlI;
            lIlllllllIIll.height = lIllllllIllll;
            lIlllllllIIll.stb = lIllllllIlllI;
        }
    }
}

