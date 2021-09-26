/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.client.texture.TextureUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30C;

public class ByteTexture
extends AbstractTexture {
    private void upload(int n, int n2, byte[] byArray, Format format, Filter filter, Filter filter2) {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)byArray.length).put(byArray);
        byteBuffer.flip();
        this.upload(n, n2, byteBuffer, format, filter, filter2);
    }

    private void upload(int n, int n2, ByteBuffer byteBuffer, Format format, Filter filter, Filter filter2) {
        TextureUtil.allocate((int)this.getGlId(), (int)n, (int)n2);
        this.bindTexture();
        GL30C.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL30C.glTexParameteri((int)3553, (int)10243, (int)10497);
        GL30C.glTexParameteri((int)3553, (int)10241, (int)filter.toOpenGL());
        GL30C.glTexParameteri((int)3553, (int)10240, (int)filter2.toOpenGL());
        GL30C.glTexImage2D((int)3553, (int)0, (int)format.toOpenGL(), (int)n, (int)n2, (int)0, (int)format.toOpenGL(), (int)5121, (ByteBuffer)byteBuffer);
    }

    public void load(ResourceManager ResourceManager2) throws IOException {
    }

    public ByteTexture(int n, int n2, byte[] byArray, Format format, Filter filter, Filter filter2) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.lambda$new$0(n, n2, byArray, format, filter, filter2));
        } else {
            this.upload(n, n2, byArray, format, filter, filter2);
        }
    }

    public ByteTexture(int n, int n2, ByteBuffer byteBuffer, Format format, Filter filter, Filter filter2) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.lambda$new$1(n, n2, byteBuffer, format, filter, filter2));
        } else {
            this.upload(n, n2, byteBuffer, format, filter, filter2);
        }
    }

    private void lambda$new$1(int n, int n2, ByteBuffer byteBuffer, Format format, Filter filter, Filter filter2) {
        this.upload(n, n2, byteBuffer, format, filter, filter2);
    }

    private void lambda$new$0(int n, int n2, byte[] byArray, Format format, Filter filter, Filter filter2) {
        this.upload(n, n2, byArray, format, filter, filter2);
    }

    public static final class Filter
    extends Enum<Filter> {
        public static final /* enum */ Filter Nearest = new Filter();
        public static final /* enum */ Filter Linear = new Filter();
        private static final Filter[] $VALUES = Filter.$values();

        public static Filter[] values() {
            return (Filter[])$VALUES.clone();
        }

        public static Filter valueOf(String string) {
            return Enum.valueOf(Filter.class, string);
        }

        public int toOpenGL() {
            return this == Nearest ? 9728 : 9729;
        }

        private static Filter[] $values() {
            return new Filter[]{Nearest, Linear};
        }
    }

    public static final class Format
    extends Enum<Format> {
        public static final /* enum */ Format A = new Format();
        public static final /* enum */ Format RGB = new Format();
        public static final /* enum */ Format RGBA = new Format();
        private static final Format[] $VALUES = Format.$values();

        public static Format valueOf(String string) {
            return Enum.valueOf(Format.class, string);
        }

        private static Format[] $values() {
            return new Format[]{A, RGB, RGBA};
        }

        public int toOpenGL() {
            switch (1.$SwitchMap$minegame159$meteorclient$utils$render$ByteTexture$Format[this.ordinal()]) {
                case 1: {
                    return 6406;
                }
                case 2: {
                    return 6407;
                }
                case 3: {
                    return 6408;
                }
            }
            return 0;
        }

        public static Format[] values() {
            return (Format[])$VALUES.clone();
        }
    }
}

