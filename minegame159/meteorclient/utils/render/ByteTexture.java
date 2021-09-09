/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.resource.ResourceManager
 *  net.minecraft.client.texture.TextureUtil
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL30C
 */
package minegame159.meteorclient.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.client.texture.TextureUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30C;

public class ByteTexture
extends AbstractTexture {
    public void load(ResourceManager lllllllllllllllllIllIllIIllIIlIl) throws IOException {
    }

    public ByteTexture(int lllllllllllllllllIllIllIlIlIlllI, int lllllllllllllllllIllIllIlIlIllIl, byte[] lllllllllllllllllIllIllIlIllIIll, Format lllllllllllllllllIllIllIlIllIIlI, Filter lllllllllllllllllIllIllIlIlIlIlI, Filter lllllllllllllllllIllIllIlIllIIII) {
        ByteTexture lllllllllllllllllIllIllIlIlIllll;
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> {
                ByteTexture lllllllllllllllllIllIllIIlIIIIIl;
                lllllllllllllllllIllIllIIlIIIIIl.upload(lllllllllllllllllIllIllIlIlIlllI, lllllllllllllllllIllIllIlIlIllIl, lllllllllllllllllIllIllIlIllIIll, lllllllllllllllllIllIllIlIllIIlI, lllllllllllllllllIllIllIlIlIlIlI, lllllllllllllllllIllIllIlIllIIII);
            });
        } else {
            lllllllllllllllllIllIllIlIlIllll.upload(lllllllllllllllllIllIllIlIlIlllI, lllllllllllllllllIllIllIlIlIllIl, lllllllllllllllllIllIllIlIllIIll, lllllllllllllllllIllIllIlIllIIlI, lllllllllllllllllIllIllIlIlIlIlI, lllllllllllllllllIllIllIlIllIIII);
        }
    }

    private void upload(int lllllllllllllllllIllIllIIlllIIll, int lllllllllllllllllIllIllIIllIlIll, ByteBuffer lllllllllllllllllIllIllIIlllIIIl, Format lllllllllllllllllIllIllIIllIlIIl, Filter lllllllllllllllllIllIllIIllIllll, Filter lllllllllllllllllIllIllIIllIIlll) {
        ByteTexture lllllllllllllllllIllIllIIlllIlII;
        TextureUtil.allocate((int)lllllllllllllllllIllIllIIlllIlII.getGlId(), (int)lllllllllllllllllIllIllIIlllIIll, (int)lllllllllllllllllIllIllIIllIlIll);
        lllllllllllllllllIllIllIIlllIlII.bindTexture();
        GL30C.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL30C.glTexParameteri((int)3553, (int)10243, (int)10497);
        GL30C.glTexParameteri((int)3553, (int)10241, (int)lllllllllllllllllIllIllIIllIllll.toOpenGL());
        GL30C.glTexParameteri((int)3553, (int)10240, (int)lllllllllllllllllIllIllIIllIIlll.toOpenGL());
        GL30C.glTexImage2D((int)3553, (int)0, (int)lllllllllllllllllIllIllIIllIlIIl.toOpenGL(), (int)lllllllllllllllllIllIllIIlllIIll, (int)lllllllllllllllllIllIllIIllIlIll, (int)0, (int)lllllllllllllllllIllIllIIllIlIIl.toOpenGL(), (int)5121, (ByteBuffer)lllllllllllllllllIllIllIIlllIIIl);
    }

    public ByteTexture(int lllllllllllllllllIllIllIlIlIIIII, int lllllllllllllllllIllIllIlIIllIII, ByteBuffer lllllllllllllllllIllIllIlIIlIlll, Format lllllllllllllllllIllIllIlIIlllIl, Filter lllllllllllllllllIllIllIlIIlllII, Filter lllllllllllllllllIllIllIlIIllIll) {
        ByteTexture lllllllllllllllllIllIllIlIlIIIIl;
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> {
                ByteTexture lllllllllllllllllIllIllIIlIlllIl;
                lllllllllllllllllIllIllIIlIlllIl.upload(lllllllllllllllllIllIllIlIlIIIII, lllllllllllllllllIllIllIlIIllIII, lllllllllllllllllIllIllIlIIlIlll, lllllllllllllllllIllIllIlIIlllIl, lllllllllllllllllIllIllIlIIlllII, lllllllllllllllllIllIllIlIIllIll);
            });
        } else {
            lllllllllllllllllIllIllIlIlIIIIl.upload(lllllllllllllllllIllIllIlIlIIIII, lllllllllllllllllIllIllIlIIllIII, lllllllllllllllllIllIllIlIIlIlll, lllllllllllllllllIllIllIlIIlllIl, lllllllllllllllllIllIllIlIIlllII, lllllllllllllllllIllIllIlIIllIll);
        }
    }

    private void upload(int lllllllllllllllllIllIllIlIIIlIlI, int lllllllllllllllllIllIllIlIIIIIIl, byte[] lllllllllllllllllIllIllIlIIIIIII, Format lllllllllllllllllIllIllIIlllllll, Filter lllllllllllllllllIllIllIlIIIIllI, Filter lllllllllllllllllIllIllIIlllllIl) {
        ByteTexture lllllllllllllllllIllIllIlIIIIIll;
        ByteBuffer lllllllllllllllllIllIllIlIIIIlII = BufferUtils.createByteBuffer((int)lllllllllllllllllIllIllIlIIIIIII.length).put(lllllllllllllllllIllIllIlIIIIIII);
        ((Buffer)lllllllllllllllllIllIllIlIIIIlII).flip();
        lllllllllllllllllIllIllIlIIIIIll.upload(lllllllllllllllllIllIllIlIIIlIlI, lllllllllllllllllIllIllIlIIIIIIl, lllllllllllllllllIllIllIlIIIIlII, lllllllllllllllllIllIllIIlllllll, lllllllllllllllllIllIllIlIIIIllI, lllllllllllllllllIllIllIIlllllIl);
    }

    public static final class Filter
    extends Enum<Filter> {
        private static final /* synthetic */ Filter[] $VALUES;
        public static final /* synthetic */ /* enum */ Filter Nearest;
        public static final /* synthetic */ /* enum */ Filter Linear;

        private static /* synthetic */ Filter[] $values() {
            return new Filter[]{Nearest, Linear};
        }

        public static Filter[] values() {
            return (Filter[])$VALUES.clone();
        }

        static {
            Nearest = new Filter();
            Linear = new Filter();
            $VALUES = Filter.$values();
        }

        private Filter() {
            Filter lllllIllllIlIII;
        }

        public static Filter valueOf(String lllllIllllIllII) {
            return Enum.valueOf(Filter.class, lllllIllllIllII);
        }

        public int toOpenGL() {
            Filter lllllIllllIIIlI;
            return lllllIllllIIIlI == Nearest ? 9728 : 9729;
        }
    }

    public static final class Format
    extends Enum<Format> {
        public static final /* synthetic */ /* enum */ Format RGBA;
        public static final /* synthetic */ /* enum */ Format RGB;
        public static final /* synthetic */ /* enum */ Format A;
        private static final /* synthetic */ Format[] $VALUES;

        public static Format[] values() {
            return (Format[])$VALUES.clone();
        }

        private Format() {
            Format lllllllllllllllllIIIllIllIlllIIl;
        }

        public int toOpenGL() {
            Format lllllllllllllllllIIIllIllIllIlII;
            switch (lllllllllllllllllIIIllIllIllIlII) {
                case A: {
                    return 6406;
                }
                case RGB: {
                    return 6407;
                }
                case RGBA: {
                    return 6408;
                }
            }
            return 0;
        }

        static {
            A = new Format();
            RGB = new Format();
            RGBA = new Format();
            $VALUES = Format.$values();
        }

        public static Format valueOf(String lllllllllllllllllIIIllIllIllllIl) {
            return Enum.valueOf(Format.class, lllllllllllllllllIIIllIllIllllIl);
        }

        private static /* synthetic */ Format[] $values() {
            return new Format[]{A, RGB, RGBA};
        }
    }
}

